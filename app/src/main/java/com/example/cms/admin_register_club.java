package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class admin_register_club extends AppCompatActivity {

    EditText clubNameEditText, clubDescriptionEditText;
    Button createClubButton;
    private static final String USER_PREFERENCES = "UserPreferences";
    String requestURL = MyConstants.BASE_URL + "/createClubs.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register_club);

        toolBar();
        createClub();
    }

    public void toolBar(){
        Toolbar toolbar = findViewById(R.id.admin_register_club_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Create Clubs");
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

    public void createClub(){
        // getting the id of the creator of the club from the sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);

        // getting other details from the views
        clubNameEditText = findViewById(R.id.team_name);
        clubDescriptionEditText = findViewById(R.id.create_description);
        createClubButton = findViewById(R.id.create_club_button);
        createClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String club_name = clubNameEditText.getText().toString();
                String club_description = clubDescriptionEditText.getText().toString();
                int creatorID = sharedPreferences.getInt("id", 0);

                saveClubToDatabase(creatorID, club_name, club_description);
            }
        });
    }

    public void saveClubToDatabase(int creatorID, String club_name, String club_description){
        // create the client
        OkHttpClient client = new OkHttpClient();

        // create the form body
        RequestBody form = new FormBody.Builder()
                .add("club_name", club_name)
                .add("club_description", club_description)
                .add("creatorID", String.valueOf(creatorID))
                .build();

        // create the request
        Request request = new Request.Builder()
                .url(requestURL)
                .post(form)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(admin_register_club.this, "Failed from Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_register_club.this, "Club Created Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // this is very important when troubleshooting the connection returned from the database api
                            Toast.makeText(admin_register_club.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}