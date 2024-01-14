package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class club_members extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_members);
        toolBar();
    }

    public void toolBar() {
        Toolbar toolbar = findViewById(R.id.club_members_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Club Members");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // this sets the back button on the toolbar
        }
    }

    // this is for creating the menu for the club members
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.club_members_menu, menu);
        return true;
    }

    // arrow back when pressed goes back to the previous page

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        } else if (item.getItemId() == R.id.menu_activities) {
            Intent intent = new Intent(club_members.this, club_activities.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_about_us) {
            Toast.makeText(club_members.this, "About Us Screen", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.menu_home) {
            Toast.makeText(club_members.this, "Home Screen", Toast.LENGTH_SHORT).show();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }
}