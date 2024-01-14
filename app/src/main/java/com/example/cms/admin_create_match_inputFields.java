package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class admin_create_match_inputFields extends AppCompatActivity {

    Toolbar toolbar;
    Spinner spinnerItem1, spinnerItem2;
    String fetchAllTeamsURL = MyConstants.BASE_URL + "/fetchAllTeams.php";
    HashMap <String, String> teamIDMap;
    Button createMatchBtn;
    EditText match_date;
    String createMatchURL = MyConstants.BASE_URL + "/createMatches.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_match_input_fields);

        // get the variables here
        toolbar = findViewById(R.id.matchInpufFieldsToolbar);
        spinnerItem1 = findViewById(R.id.team1_spinner);
        spinnerItem2 = findViewById(R.id.team2_spinner);
        createMatchBtn = findViewById(R.id.createMatchButton);
        match_date = findViewById(R.id.matchDate);

        // create a hashmap object
        teamIDMap = new HashMap <> ();

        // get the methods here
        toolBar();
        //teamSpinners();
        fetchAllTeams();
        createMatches();
    }

    public void toolBar(){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Create Matches");
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
                // do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void teamSpinners(){
        String[] teams = {"The gunners Football team", "Man U", "Arsenal", "Liverpool", "Chelsea", "Uganda Cranes"};
        ArrayAdapter <String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, teams);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItem1.setAdapter(adapter);
        spinnerItem2.setAdapter(adapter);
    }

    public void createMatches(){
        createMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the values
                String team1 = spinnerItem1.getSelectedItem().toString();
                String team1ID = teamIDMap.get(team1);
                String team2 = spinnerItem2.getSelectedItem().toString();
                String date = match_date.getText().toString();
                sendDateToDatabase(team1ID, team2, date);
            }
        });
    }

    public void sendDateToDatabase(String teamID, String team2, String date){
        OkHttpClient client = new OkHttpClient();
        RequestBody form = new FormBody.Builder()
                .add("teamID", teamID)
                .add("team2", team2)
                .add("date", date)
                .build();
        Request request = new Request.Builder()
                .url(createMatchURL)
                .post(form)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_create_match_inputFields.this, "Matches Created Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_create_match_inputFields.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void fetchAllTeams(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fetchAllTeamsURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String responseData = response.body().string();

                    // parse JSON data and update the UI spinners
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateSpinners(responseData);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_create_match_inputFields.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void updateSpinners(String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            ArrayList<String> teamNames = new ArrayList<>();

            // Extract team names from JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject teamObject = jsonArray.getJSONObject(i);
                String teamName = teamObject.getString("TeamName");
                String teamID = teamObject.getString("TeamID");
                teamNames.add(teamName);

                // populate the hashmap
                teamIDMap.put(teamName, teamID);
            }

            // Sort the team names in ascending order
            Collections.sort(teamNames);

            // Update Team 1 spinner
            ArrayAdapter<String> adapterTeam1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamNames);
            adapterTeam1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerItem1.setAdapter(adapterTeam1);

            // Shuffle the team names for Team 2 or use descending order
            Collections.shuffle(teamNames); // Shuffle the list
            // Alternatively, sort in descending order: Collections.sort(teamNames, Collections.reverseOrder());

            // Update Team 2 spinner
            ArrayAdapter<String> adapterTeam2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, teamNames);
            adapterTeam2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerItem2.setAdapter(adapterTeam2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}