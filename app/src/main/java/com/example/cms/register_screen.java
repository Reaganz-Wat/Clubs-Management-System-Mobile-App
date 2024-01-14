package com.example.cms;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.IOException;
import android.util.Log;

public class register_screen extends AppCompatActivity {

    // Define the URL of your PHP script
    private static final String PHP_SCRIPT_URL = "http://192.168.43.134/clubs/register_users.php";
    private static final String PHP_SCRIPT_STUDENT_URL = MyConstants.BASE_URL + "/registerStudents.php";
    private static final String PHP_SCRIPT_TEACHERS_URL = MyConstants.BASE_URL + "/registerTeachers.php";

    // Define the input fields
    private EditText usernameEditText, firstnameEditText, lastnameEditText, emailEditText, passwordEditText, contactEditText, genderEditText, dateOfBirthEditText;
    private Spinner userTypeSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        registerToolBar();
        sendDataToDatabase();
        spinnerItems();
    }
    public void registerToolBar(){
        Toolbar toolbar = findViewById(R.id.register_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Registration");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void spinnerItems(){
        userTypeSpinner = findViewById(R.id.register_spinner);
        String[] userTypes = {"Student", "Teacher"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(arrayAdapter);
    }

    // this is where the database connection should go

    public void sendDataToDatabase(){

        // Initialize your UI elements
        usernameEditText = findViewById(R.id.register_username);
        firstnameEditText = findViewById(R.id.register_firstname);
        lastnameEditText = findViewById(R.id.register_lastname);
        emailEditText = findViewById(R.id.register_email);
        passwordEditText = findViewById(R.id.register_password);
        userTypeSpinner = findViewById(R.id.register_spinner);
        contactEditText = findViewById(R.id.register_contact);
        dateOfBirthEditText = findViewById(R.id.register_dateOfBirth);
        genderEditText =findViewById(R.id.register_gender);

        // Handle the button click to submit the data
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer();
            }
        });
    }

    private void sendDataToServer() {
        // Retrieve data from input fields
        String username = usernameEditText.getText().toString();
        String firstname = firstnameEditText.getText().toString();
        String lastname = lastnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String userType = userTypeSpinner.getSelectedItem().toString();
        String contactnumber = contactEditText.getText().toString();
        String dateOfBirth = dateOfBirthEditText.getText().toString();
        String gender = genderEditText.getText().toString();


        // Display a Toast message with the input values
//        String message = "Username: " + username + "\n" +
//                "First Name: " + firstname + "\n" +
//                "Last Name: " + lastname + "\n" +
//                "Email: " + email + "\n" +
//                "Password: " + password + "\n" +
//                "User Type: " + userType;
//
//        showToastMessage(message);


        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();



        if ("Student".equals(userType)){

            // Create a FormBody for POST data
            RequestBody formBody = new FormBody.Builder()
                    .add("firstname", firstname)
                    .add("lastname", lastname)
                    .add("email", email)
                    .add("contactnumber", contactnumber)
                    .add("username", username)
                    .add("password", password)
                    .add("gender", gender)
                    .add("date_of_birth", dateOfBirth)
                    .add("usertype", userType)
                    .build();

            // Create a POST request
            Request request = new Request.Builder()
                    .url(PHP_SCRIPT_STUDENT_URL)
                    .post(formBody)
                    .build();

            // Send the request asynchronously
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    // Handle network error
                    Log.e("OkHttp", "Network error: " + e.getMessage());
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // Handle the response from the server
                    if (response.isSuccessful()) {
                        final String responseData = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Update the UI with the response data
                                Log.d("OkHttp", "Response: " + responseData);
                                // You can display a Toast or perform other UI updates here
                                showToastMessage("Registered successfully");
                                finish();
                            }
                        });
                    } else {
                        // Handle an unsuccessful response (e.g., server error)
                        Log.e("OkHttp", "Error response: " + response.code());
                        showToastMessage("Error response: " + response.code());
                    }
                }
            });
        }
        else {

            // Create a FormBody for POST data
            RequestBody formBody = new FormBody.Builder()
                    .add("firstname", firstname)
                    .add("lastname", lastname)
                    .add("contactnumber", contactnumber)
                    .add("email", email)
                    .add("username", username)
                    .add("password", password)
                    .add("usertype", userType)
                    .build();

            // Create a POST request
            Request request = new Request.Builder()
                    .url(PHP_SCRIPT_TEACHERS_URL)
                    .post(formBody)
                    .build();

            // Send the request asynchronously
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    // Handle network error
                    Log.e("OkHttp", "Network error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(register_screen.this, "Network Error: ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // Handle the response from the server
                    if (response.isSuccessful()) {
                        final String responseData = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Update the UI with the response data
                                Log.d("OkHttp", "Response: " + responseData);
                                // You can display a Toast or perform other UI updates here
                                showToastMessage("Registered successfully");

                                // finish the activity
                                finish();
                            }
                        });
                    } else {
                        // Handle an unsuccessful response (e.g., server error)
                        Log.e("OkHttp", "Error response: " + response.code());
                        showToastMessage("Error response: " + response.code());
                    }
                }
            });
        }
    }

    private void showToastMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(register_screen.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}