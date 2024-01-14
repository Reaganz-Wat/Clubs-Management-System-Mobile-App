package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class studentDashboard extends AppCompatActivity {

    // Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView imageView;
    TextView profileUsername;
    private static final String USER_PREFERENCES = "UserPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        go_to_settings();
        go_to_profile();
        go_to_clubs();
        to_to_sportsTeam();
        display_clubs_to_join();
        openNavigationDrawer();
        clickItemsDrawer();
        logout_student();
        setUserProfile();
    }

    public void go_to_settings(){
        CardView cardView = findViewById(R.id.setting_screen);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentDashboard.this, settings_screen.class);
                startActivity(intent);
            }
        });
    }
    public void display_clubs_to_join(){
        CardView cardView = findViewById(R.id.joinClubs);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentDashboard.this, display_clubs_to_join.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_profile(){
        CardView cardView = findViewById(R.id.studentProfile);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentDashboard.this, profile_screen.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_clubs(){
        CardView cardView = findViewById(R.id.clubs_screen);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentDashboard.this, clubs.class);
                startActivity(intent);
            }
        });
    }
    public void to_to_sportsTeam(){
        CardView sportsTeam = findViewById(R.id.sports_team_screen);
        sportsTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentDashboard.this, student_sportsteam.class);
                startActivity(intent);
            }
        });
    }
    public void logout_student(){
        CardView logout = findViewById(R.id.student_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear user details from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // here you can now log out of the dashboard
                Intent intent = new Intent(studentDashboard.this, login_screen.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void openNavigationDrawer(){
        imageView = findViewById(R.id.menuBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }
   public void clickItemsDrawer(){
        navigationView = findViewById(R.id.nav_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.comments){
                    Toast.makeText(studentDashboard.this, "Comments has been clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.aboutUs) {
                    Toast.makeText(studentDashboard.this, "About us is clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.share) {
                    Toast.makeText(studentDashboard.this, "Share has been clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.feedback) {
                    Toast.makeText(studentDashboard.this, "Sending Feedback", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
   }
   public void setUserProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        profileUsername = findViewById(R.id.profile_usernameText);
        profileUsername.setText("Hi " + username);
   }

}