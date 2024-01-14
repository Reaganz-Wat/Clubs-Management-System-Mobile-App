package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

public class student_sportsteam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sportsteam);

        toolBar();
    }

    // this code is for creating the toolbar for the sports_team for students
    public void toolBar(){
        Toolbar toolbar = findViewById(R.id.sports_team_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Sports Team");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}