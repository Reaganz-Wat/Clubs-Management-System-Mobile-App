package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

public class club_activities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_activities);

        toolBar();
    }

    public void toolBar(){

        // creates the toolbar for the club activities
        Toolbar toolbar = findViewById(R.id.club_activites_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Club Activities");
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