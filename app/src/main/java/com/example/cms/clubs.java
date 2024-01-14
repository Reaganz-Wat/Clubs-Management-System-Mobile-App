package com.example.cms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class clubs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);
        toolBar();
        go_to_clubs_members();
    }
    public void toolBar(){
        Toolbar toolbar = findViewById(R.id.clubs_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Clubs");
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

    public void go_to_clubs_members(){
        Button button = findViewById(R.id.moreButton1);
        TextView title = findViewById(R.id.title);
        String titleValue = title.getText().toString();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(clubs.this, club_members.class);
                intent.putExtra("CLUBS_NAME", titleValue);
                startActivity(intent);
            }
        });
    }
}