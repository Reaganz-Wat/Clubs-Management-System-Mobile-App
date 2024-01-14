package com.example.cms;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class login_screen extends AppCompatActivity {

    Spinner spinner;
    EditText usernameEditText, passwordEditText;
    private SharedPreferences sharedPreferences;
    private static final String USER_PREFERENCES = "UserPreferences";
    // Construct a URL for the PHP script on your server
    String loginStudentUrl = MyConstants.BASE_URL + "/loginStudents.php";  //URL for login students
    String loginAdminUrl = MyConstants.BASE_URL + "/loginAdmin.php";  //URL for login students
    String loginTeacherUrl = MyConstants.BASE_URL + "/loginTeachers.php"; // URL for login teachers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        login();
        spiinerItems();

        // initializing the shared preferences
        sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void login(){
        Button button = findViewById(R.id.loginButton);
        spinner = findViewById(R.id.spiinerItems);

        // get the username and password
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedUserType = spinner.getSelectedItem().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if (selectedUserType.equals("Student")){

                    // work on this later on
                    authenticateUser(username, password);
                    //Intent intent = new Intent(login_screen.this, studentDashboard.class);
                    //startActivity(intent);

                } else if (selectedUserType.equals("Teachers")) {

                    authenticateTeacher(username, password);
                    //Intent intent = new Intent(login_screen.this, teachersDashboard.class);
                    //startActivity(intent);
                } else if (selectedUserType.equals("Admin")) {
                    //Intent intent = new Intent(login_screen.this, adminDashboard.class);
                    //startActivity(intent);

                    // perform the server request
                    authenticateAdmin(username, password);
                }
            }
        });
    }
    public void resetPwd(View view){
        Intent intent = new Intent(this, reset_password.class);
        startActivity(intent);
    }
    public void spiinerItems(){
        Spinner spinner = findViewById(R.id.spiinerItems);
        String[] userTypes = {"Student", "Teachers", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    // this is for authenticating the user
    public void authenticateUser(String enteredUsername, String enteredPassword) {

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", enteredUsername)
                .build();

        Request request = new Request.Builder()
                .url(loginStudentUrl)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network error
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(login_screen.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    //Toast.makeText(login_screen.this, "Response", Toast.LENGTH_SHORT).show();

                    try {
                        // Parse the JSON response
                        JSONObject json = new JSONObject(responseData);

                        // Check if the user was found based on your response format
                        if (json.getBoolean("success")) {
                            // User found, get the user data
                            JSONObject userData = json.getJSONObject("user_data");

                            String storedPassword = userData.getString("Password");

                            // Compare enteredPassword with storedPassword
                            if (MD5Utils.getMD5(enteredPassword).equals(storedPassword)) {
                                // Authentication successful

                                int user_id = userData.getInt("UserID");
                                String user_firstname = userData.getString("Firstname");
                                String user_lastname = userData.getString("Lastname");
                                String user_email = userData.getString("Email");
                                String user_contact = userData.getString("Contact");

                                // Store user details in SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id", user_id);
                                editor.putString("username", enteredUsername);
                                editor.putString("firstname", user_firstname);
                                editor.putString("lastname", user_lastname);
                                editor.putString("email", user_email);
                                editor.putString("contact", user_contact);
                                editor.apply();


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something, e.g., navigate to the next activity
                                        Intent intent = new Intent(login_screen.this, studentDashboard.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                // Authentication failed: Incorrect password
                               runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       Toast.makeText(login_screen.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                   }
                               });
                            }
                        } else {
                            // Authentication failed: User not found
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(login_screen.this, "User not found", Toast.LENGTH_SHORT).show();
                               }
                           });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle server error
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(login_screen.this, "Server response: " + response.code(), Toast.LENGTH_SHORT).show();
                       }
                   });
                }
            }
        });
    }

    public void authenticateTeacher(String enteredUsername, String enteredPassword) {

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", enteredUsername)
                .build();

        Request request = new Request.Builder()
                .url(loginTeacherUrl)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network error
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(login_screen.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    //Toast.makeText(login_screen.this, "Response", Toast.LENGTH_SHORT).show();

                    try {
                        // Parse the JSON response
                        JSONObject json = new JSONObject(responseData);

                        // Check if the user was found based on your response format
                        if (json.getBoolean("success")) {
                            // User found, get the user data
                            JSONObject userData = json.getJSONObject("user_data");

                            String storedPassword = userData.getString("Password");

                            // Compare enteredPassword with storedPassword
                            if (MD5Utils.getMD5(enteredPassword).equals(storedPassword)) {
                                // Authentication successful

                                int user_id = userData.getInt("UserID");
                                String user_firstname = userData.getString("Firstname");
                                String user_lastname = userData.getString("Lastname");
                                String user_email = userData.getString("Email");
                                String user_contact = userData.getString("Contact");

                                // Store user details in SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id", user_id);
                                editor.putString("username", enteredUsername);
                                editor.putString("firstname", user_firstname);
                                editor.putString("lastname", user_lastname);
                                editor.putString("email", user_email);
                                editor.putString("contact", user_contact);
                                editor.apply();


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something, e.g., navigate to the next activity
                                        Intent intent = new Intent(login_screen.this, teachersDashboard.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                // Authentication failed: Incorrect password
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(login_screen.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            // Authentication failed: User not found
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(login_screen.this, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle server error
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login_screen.this, "Server response: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public void authenticateAdmin(String enteredUsername, String enteredPassword){

        OkHttpClient client1 = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", enteredUsername)
                .build();

        Request request = new Request.Builder()
                .url(loginAdminUrl)
                .post(formBody)
                .build();
        client1.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network error
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(login_screen.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    //Toast.makeText(login_screen.this, "Response", Toast.LENGTH_SHORT).show();

                    try {
                        // Parse the JSON response
                        JSONObject json = new JSONObject(responseData);

                        // Check if the user was found based on your response format
                        if (json.getBoolean("success")) {
                            // User found, get the user data
                            JSONObject userData = json.getJSONObject("user_data");

                            String storedPassword = userData.getString("Password");

                            // Compare enteredPassword with storedPassword
                            if (enteredPassword.equals(storedPassword)) {
                                // Authentication successful

                                // get all the user information here
                                //int user_id = userData.getInt("id");
                                //String user_firstname = userData.getString("firstname");
                                //String user_lastname = userData.getString("lastname");
                                //String user_email = userData.getString("email");
                                //String user_contact = userData.getString("contactnumber");

                                int user_id = userData.getInt("UserID");
                                String user_firstname = userData.getString("Firstname");
                                String user_lastname = userData.getString("Lastname");
                                String user_email = userData.getString("Email");
                                //String user_contact = userData.getString("Contact");

                                // Store user details in SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("id", user_id);
                                editor.putString("username", enteredUsername);
                                editor.putString("firstname", user_firstname);
                                editor.putString("lastname", user_lastname);
                                editor.putString("email", user_email);
                                //editor.putString("contact", user_contact);
                                editor.apply();


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Do something, e.g., navigate to the next activity
                                        Intent intent = new Intent(login_screen.this, adminDashboard.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                // Authentication failed: Incorrect password
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(login_screen.this, "Incorrect admin name or password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            // Authentication failed: User not found
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(login_screen.this, "User not found", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle server error
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login_screen.this, "Server response: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

}