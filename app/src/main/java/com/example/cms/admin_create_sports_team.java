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

public class admin_create_sports_team extends AppCompatActivity {

    RecyclerView recyclerView;
    adminCreateSportsTeamAdapter adapter;
    Toolbar toolbar;
    String responseData;
    String requestURL = MyConstants.BASE_URL + "/fetchSportsTeam.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_sports_team);

        toolBar();
    }

    public void toolBar() {
        toolbar = findViewById(R.id.adminCreateSportsToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Sports Teams");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            createAdapter();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_create_sports_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.createST) {
            // go to the create sports teams input activity
            Intent intent = new Intent(admin_create_sports_team.this, admin_create_sports_input_layout.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createAdapter(){
        recyclerView = findViewById(R.id.adminCreateSports);
        ArrayList<adminCreateSportsTeamModal> arrayList = new ArrayList <adminCreateSportsTeamModal> ();
        adapter = new adminCreateSportsTeamAdapter(this, arrayList);


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(admin_create_sports_team.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
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
                                String team_name = jsonObject.getString("TeamName");
                                String coach = jsonObject.getString("Coach");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        arrayList.add(new adminCreateSportsTeamModal("Team: " + team_name, "Coach: " + coach));
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(admin_create_sports_team.this, "Empty or Invalid response", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(admin_create_sports_team.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
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