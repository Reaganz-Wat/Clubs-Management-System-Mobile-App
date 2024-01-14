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

public class admin_create_club extends AppCompatActivity {
    RecyclerView recyclerView;
    adminCreateClubAdapter adapter;
    Toolbar toolbar;
    String responseData;
    String requestURL = MyConstants.BASE_URL + "/fetchClubs.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_club);

        toolBar();
        createAdapter();
    }

    public void toolBar() {
        toolbar = findViewById(R.id.adminCreateClubToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Clubs");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.createCLUB) {
            Intent intent = new Intent(admin_create_club.this, admin_register_club.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.exitApp) {
            Toast.makeText(this, "Exitting the App", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_create_clubs_menu, menu);
        return true;
    }

    public void createAdapter(){
        recyclerView = findViewById(R.id.adminCreateClub);
        ArrayList <adminCreateClubModal> arrayList = new ArrayList <adminCreateClubModal> ();
        adapter = new adminCreateClubAdapter(this, arrayList);

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
                        Toast.makeText(admin_create_club.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
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
                                String clubName = jsonObject.getString("ClubName");
                                String description = jsonObject.getString("Description");
                                String creator = jsonObject.getString("Creator");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        arrayList.add(new adminCreateClubModal(clubName, description, "Created by: " + creator));
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(admin_create_club.this, "Empty or Invalid response", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(admin_create_club.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
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