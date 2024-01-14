package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
public class adminDashboard extends AppCompatActivity {

    // Global variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView imageView;
    TextView profileUsername;
    CardView studentsCard, teachersCard, createClubs, sportsTeam, adminProfile, createMatch;
    private static final String USER_PREFERENCES = "UserPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        go_to_profile();
        go_to_student_view();
        go_to_teachers_view();
        go_to_create_clubs();
        go_to_sports_team();
        go_to_create_match();
        openAdminNavigationDrawer();
        onNavigationDrawerItemsClicked();
        go_to_admin_settings();
        logoutAdmin();
        setUserProfile();
    }
    public void go_to_sports_team(){
        sportsTeam = findViewById(R.id.adminSportsTeam);
        sportsTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, admin_create_sports_team.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_profile(){
        adminProfile = findViewById(R.id.adminProfile);
        adminProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, admin_profile.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_create_match(){
        createMatch = findViewById(R.id.create_matches);
        createMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, admin_create_match.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_student_view(){
        studentsCard = findViewById(R.id.students_screen);
        studentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, admin_view_students.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_create_clubs(){
        createClubs = findViewById(R.id.create_clubs_screen);
        createClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, admin_create_club.class);
                startActivity(intent);
            }
        });
    }
    public void logoutAdmin(){
        CardView logout_admin = findViewById(R.id.logout_screen_admin);
        logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear user details from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(adminDashboard.this, login_screen.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void go_to_admin_settings(){
        CardView admin_settings = findViewById(R.id.setting_screen);
        admin_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, settings_screen.class);
                startActivity(intent);
            }
        });
    }
    public void go_to_teachers_view(){
        teachersCard = findViewById(R.id.teachers_screen);
        teachersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, admin_view_teachers.class);
                startActivity(intent);
            }
        });
    }
    public void openAdminNavigationDrawer(){
        imageView = findViewById(R.id.admin_menuBar);
        drawerLayout = findViewById(R.id.admin_drawer_layout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }
    public void onNavigationDrawerItemsClicked(){
        navigationView = findViewById(R.id.admin_nav_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.admin_comments){
                    Toast.makeText(adminDashboard.this, "Comments Clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.admin_share) {
                    Toast.makeText(adminDashboard.this, "Share clicked", Toast.LENGTH_SHORT).show();
                }
                else if (item.getItemId() == R.id.admin_aboutUs){
                    Toast.makeText(adminDashboard.this, "About Us clicked", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.admin_feedback) {
                    Toast.makeText(adminDashboard.this, "Sending Feedback", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.admin_addMember) {
                    Intent intent = new Intent(adminDashboard.this, register_screen.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
    public void setUserProfile(){
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("firstname", "");

        profileUsername = findViewById(R.id.admin_profile_name);
        profileUsername.setText("Hi " + username);
    }

}