package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class admin_create_match extends AppCompatActivity {

    Toolbar toolbar;
    String fetchAllMatchesURL = MyConstants.BASE_URL + "/fetchAllMatches.php";
    RecyclerView recyclerView;
    adminCreateClubAdapter adapter;
    String responseData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_match);

        // my variables here
        toolbar = findViewById(R.id.adminCreateMatchToolbar);
        recyclerView = findViewById(R.id.view_schedules_matches);

        // my function calls here
        toolBar();
        fetchMatchesFromDatabase();
    }

    public void toolBar(){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Matches");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_create_match_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.createMatch) {
            Intent intent = new Intent(admin_create_match.this, admin_create_match_inputFields.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchMatchesFromDatabase(){
        ArrayList<adminCreateClubModal> arrayList = new ArrayList <adminCreateClubModal> ();
        adapter = new adminCreateClubAdapter(this, arrayList);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fetchAllMatchesURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(admin_create_match.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    responseData = response.body().string();

                    try {
                        if (responseData != null && !responseData.isEmpty()){
                            JSONArray jsonArray = new JSONArray(responseData);
                            for (int i = 0; i < jsonArray.length(); i ++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String first_teamName = jsonObject.getString("TeamName");
                                String team_opponent = jsonObject.getString("Opponent");
                                String match_date = jsonObject.getString("MatchDate");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        arrayList.add(new adminCreateClubModal(first_teamName + " Vs " + team_opponent,"", "Match date: " + match_date));
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(admin_create_match.this, "Empty or Invalid response", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_create_match.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}