package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class admin_create_sports_input_layout extends AppCompatActivity {

    Spinner coachSpinner;
    String fetchAllTeachers = MyConstants.BASE_URL + "/fetchAllTeachers.php";
    String createSportTeamURL = MyConstants.BASE_URL + "/createSportsTeam.php";
    HashMap<String, String> teachersHashMap;
    EditText teamName;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_sports_input_layout);

        // finding the views variables here
        teamName = findViewById(R.id.teamNameSports);
        coachSpinner = findViewById(R.id.coachNameID);
        button = findViewById(R.id.createSportsTeamButton);
        teachersHashMap = new HashMap<>();

        // calling the methods here
        //coachNames();
        fetchTeachersAll();
        createSportsTeam();
    }

    public void coachNames(){
        String[] coaches = {"Okello", "Boniface", "Watmon", "Reagan", "Nyero", "Parker", "Kate", "Melody", "Sparrow"};
        ArrayAdapter <String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coaches);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coachSpinner.setAdapter(arrayAdapter);
    }

    public void fetchTeachersAll(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fetchAllTeachers)
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
                            Toast.makeText(admin_create_sports_input_layout.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
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
                String Name = teamObject.getString("Firstname");
                String teacherID = teamObject.getString("UserID");
                teamNames.add(Name);

                // populate the hashmap
                teachersHashMap.put(Name, teacherID);
            }

            // Sort the team names in ascending order
            Collections.sort(teamNames);

            // Update Team spinner
            ArrayAdapter <String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, teamNames);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            coachSpinner.setAdapter(arrayAdapter);

            // Shuffle the team names for Team 2 or use descending order
            Collections.shuffle(teamNames); // Shuffle the list
            // Alternatively, sort in descending order: Collections.sort(teamNames, Collections.reverseOrder());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createSportsTeam(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String team_name = teamName.getText().toString();
                String coach_name = coachSpinner.getSelectedItem().toString();
                String coachID = teachersHashMap.get(coach_name);

                sendDataToDatabase(team_name, coachID);
            }
        });
    }

    public void sendDataToDatabase(String teamName, String coachID){
        OkHttpClient client = new OkHttpClient();
        RequestBody form = new FormBody.Builder()
                .add("team_name", teamName)
                .add("coach_id", coachID)
                .build();
        Request request = new Request.Builder()
                .url(createSportTeamURL)
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
                            Toast.makeText(admin_create_sports_input_layout.this, "New Team Created Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_create_sports_input_layout.this, "Error response: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}