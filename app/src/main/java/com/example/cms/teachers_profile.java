package com.example.cms;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class teachers_profile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView username, email, email1, firstname, lastname, contact;
    Button button;
    public static final String USE_PREFERENCES = "UserPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_profile);

        username = findViewById(R.id.teacher_profile_username);
        email = findViewById(R.id.teacher_profile_email);
        email1 = findViewById(R.id.teacher_profile_email1);
        firstname = findViewById(R.id.teacher_profile_firstname);
        lastname = findViewById(R.id.teacher_profile_lastname);
        contact = findViewById(R.id.teacher_profile_contact);
        sharedPreferences = getSharedPreferences(USE_PREFERENCES, Context.MODE_PRIVATE);

        setTeachersDetails();
    }

    public void setTeachersDetails(){
        username.setText(sharedPreferences.getString("username", ""));
        email.setText(sharedPreferences.getString("email", ""));
        email1.setText(sharedPreferences.getString("email", ""));
        firstname.setText(sharedPreferences.getString("firstname", ""));
        lastname.setText(sharedPreferences.getString("lastname", ""));
        contact.setText(sharedPreferences.getString("contact", ""));
    }

}