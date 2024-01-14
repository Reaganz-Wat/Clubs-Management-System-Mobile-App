package com.example.cms;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class teachersDashboard extends AppCompatActivity {

    private static final String USER_PREFERENCES = "UserPreferences";
    TextView profileUsername;

    CardView cardView, profileCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_dashboard);

        cardView = findViewById(R.id.teachers_clubs_screen);
        profileCards = findViewById(R.id.teachersProfile);
        go_to_profile();
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teachersDashboard.this, teachers_create_clubs.class);
                startActivity(intent);
            }
        });
    }

    public void setUserProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        profileUsername = findViewById(R.id.showTeacherName);
        profileUsername.setText("Hi " + username);
    }

    public void go_to_profile(){
        profileCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teachersDashboard.this, teachers_profile.class);
                startActivity(intent);
            }
        });
    }
}