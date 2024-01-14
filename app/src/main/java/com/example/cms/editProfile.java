package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.provider.MediaStore;
import android.app.Activity;

public class editProfile extends AppCompatActivity {

    public static final String USE_PREFERENCES = "UserPreferences";

    EditText usernameEditText;
    EditText firstnameEditText;
    EditText lastnameEditText;
    EditText emailEditText;
    EditText contactEditText;
    Button save_changes;

    private static final int RESULT_LOAD_IMG = 1;

    // initializing the sharedPreferences
    SharedPreferences sharedPreferences;
    String editProfileUrl = MyConstants.BASE_URL + "/editStudents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // input details
        // getting the editText by ID's
        usernameEditText = findViewById(R.id.edit_username);
        firstnameEditText = findViewById(R.id.edit_firstname);
        lastnameEditText = findViewById(R.id.edit_lastname);
        emailEditText = findViewById(R.id.edit_email);
        contactEditText = findViewById(R.id.edit_contact);
        save_changes = findViewById(R.id.editProfileSaveChanges);

        sharedPreferences = getSharedPreferences(USE_PREFERENCES, Context.MODE_PRIVATE);

        toolBar();
        setEditField();
        editProfile();
    }

    public void toolBar(){
        Toolbar toolbar = findViewById(R.id.editProfile_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Edit Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // this sets the back button on the toolbar
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
    public void setEditField(){

        // get the user details from the sharedPreferences
        String username = sharedPreferences.getString("username", "");
        String firstname = sharedPreferences.getString("firstname", "");
        String lastname = sharedPreferences.getString("lastname", "");
        String email = sharedPreferences.getString("email", "");
        String contact = sharedPreferences.getString("contact", "");

        // set the editText text value to the user's data
        usernameEditText.setText(username);
        firstnameEditText.setText(firstname);
        lastnameEditText.setText(lastname);
        emailEditText.setText(email);
        contactEditText.setText(contact);

    }
    public void editProfile(){

        // get the id from the sharedPreferences
        int id = sharedPreferences.getInt("id", 0);

        Log.e("EDIT PROFILE", "Inside the editProfile");

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = usernameEditText.getText().toString();
                String firstname = firstnameEditText.getText().toString();
                String lastname = lastnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String contact = contactEditText.getText().toString();

                Log.e("ON CLICK", "Details: " + username + firstname + lastname + email + contact);

                saveToDatabase(id, username, firstname, lastname, email, contact);
            }
        });
    }

    public void saveToDatabase(int id, String username, String firstname, String lastname, String email, String contact){
        // create the client object
        OkHttpClient client = new OkHttpClient();

        // create the post form body
        RequestBody editForm = new FormBody.Builder()
                .add("id", String.valueOf(id))
                .add("username", username)
                .add("firstname", firstname)
                .add("lastname", lastname)
                .add("email", email)
                .add("contact", contact)
                .build();

        // create the request
        Request request = new Request.Builder()
                .url(editProfileUrl)
                .post(editForm)
                .build();

        // make the request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(editProfile.this, "Edit failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    // upate the sharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.putString("firstname", firstname);
                    editor.putString("lastname", lastname);
                    editor.putString("email", email);
                    editor.putString("contact", contact);
                    editor.apply();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(editProfile.this, "Profil Edited Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // finish the activity and go back to the previous screen -> view Profile screen
                    finish();

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(editProfile.this, "Server error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void pickImage(View view){
        // Create an intent to pick an image from the gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK) {
            // Handle the selected image here
            // The selected image's data can be found in the 'data' intent
            // Example: Uri selectedImageUri = data.getData();
            if (data != null) {
                Uri selectedImageUri = data.getData();

                // Now, you can set the selected image in your CircleImageView
                CircleImageView profileImageView = findViewById(R.id.profile);
                profileImageView.setImageURI(selectedImageUri);
            }
        }
    }
}