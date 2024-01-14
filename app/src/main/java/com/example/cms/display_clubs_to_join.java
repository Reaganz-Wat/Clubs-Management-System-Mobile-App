package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
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

public class display_clubs_to_join extends AppCompatActivity {

    student_join_clubs_adapter adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String fetchClubs = MyConstants.BASE_URL + "/fetchClubs.php";
    String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_clubs_to_join);

        recyclerView = findViewById(R.id.display_all_clubs_recyclerview);
        toolbar = findViewById(R.id.display_all_clubs);
        toolBar();
        displayAllClubs();
    }
    public void toolBar(){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("All Clubs");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayAllClubs(){
        ArrayList <student_join_clubs_modal> arrayList = new ArrayList <student_join_clubs_modal> ();
        adapter = new student_join_clubs_adapter(this, arrayList);

//        arrayList.add(new student_join_clubs_modal("Debating Club", "This is the club created for debating", "Creaty by: Watmon"));
//        arrayList.add(new student_join_clubs_modal("Football Club", "This is the club created for football", "Creaty by: Reagan"));
//        arrayList.add(new student_join_clubs_modal("Netball Club", "This is the club created for netball", "Creaty by: Nyero"));

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fetchClubs)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(display_clubs_to_join.this, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    responseData = response.body().string();
                    // e.g [{"ClubID":"4","ClubName":"Rugby","Description":"This is a rugby club","Creator":"Admin"},{"ClubID":"3","ClubName":"Netball Club","Description":"This is a fantastic club","Creator":"Admin"}]

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
                                        arrayList.add(new student_join_clubs_modal(clubName, "Description: " + description, "Created by: " + creator));
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(display_clubs_to_join.this, "Empty or Invalid response", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(display_clubs_to_join.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
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