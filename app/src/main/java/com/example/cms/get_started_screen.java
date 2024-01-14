package com.example.cms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class get_started_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_screen);
        getStarted();
    }

    public void getStarted(){
        Button button = findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(get_started_screen.this, login_screen.class);
                startActivity(intent);
            }
        });
    }
}