package com.example.cms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class profile_screen extends AppCompatActivity {

    private static final String USER_PREFERENCES = "UserPreferences";

    // get the textviews to be set from the sharedPreferences values
    TextView usernameTextView, emailTextView, firstnameTexrView, lastnameTextView, email1TextView, contactTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        backArrowOnToolBar();
        go_to_editProfile();
        get_user_details();
    }

    public void backArrowOnToolBar(){
        Toolbar toolbar = findViewById(R.id.admin_profile_toolbar); // get the toolbar using its id
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // this sets the back button on the toolbar
        }
    }

    // this methods is used to go back after the button is clicked on the toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void go_to_editProfile(){
        Button button = findViewById(R.id.admin_editProfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile_screen.this, editProfile.class);
                startActivity(intent);
            }
        });
    }
    public void get_user_details(){
        // Retrieve the username, email from the SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String firstname = sharedPreferences.getString("firstname", "");
        String lastname = sharedPreferences.getString("lastname", "");
        String email = sharedPreferences.getString("email", "");
        String contact = sharedPreferences.getString("contact", "");

        // get the usename and email textviews
        usernameTextView = findViewById(R.id.admin_profile_username);
        emailTextView = findViewById(R.id.admin_profile_email);
        email1TextView = findViewById(R.id.admin_profile_email1);
        firstnameTexrView = findViewById(R.id.profile_firstname);
        lastnameTextView = findViewById(R.id.admin_profile_lastname);
        contactTextView = findViewById(R.id.admin_profile_contact);

        usernameTextView.setText(username);
        emailTextView.setText(email);
        firstnameTexrView.setText(firstname);
        lastnameTextView.setText(lastname);
        contactTextView.setText(contact);
        email1TextView.setText(email);
    }
}