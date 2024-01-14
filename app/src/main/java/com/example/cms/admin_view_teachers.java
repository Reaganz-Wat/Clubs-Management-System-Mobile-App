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

public class admin_view_teachers extends AppCompatActivity {

    RecyclerView recyclerView;
    String responseData;
    PracticeAdapter practiceAdapter; // Declare it as a class-level variable
    String teachersUrl = MyConstants.BASE_URL + "/fetchAllTeachers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_teachers);

        toolBar();
        fetchInformation();
    }

    public void toolBar(){
        Toolbar toolbar = findViewById(R.id.view_teachers_toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Teachers");
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

    public void fetchInformation(){
        recyclerView = findViewById(R.id.recyclerViewTeachers);

        ArrayList<Practice> practiceArrayList = new ArrayList<Practice>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(teachersUrl)
                .build();

        practiceAdapter = new PracticeAdapter(this, practiceArrayList); // Initialize it here

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(admin_view_teachers.this, "On failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    responseData = response.body().string();
                    try {
                        if (responseData != null && !responseData.isEmpty()) {
                            JSONArray jsonArray = new JSONArray(responseData);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String username = jsonObject.getString("Firstname");
                                String email = jsonObject.getString("Email");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        practiceArrayList.add(new Practice(username, email, R.mipmap.teacher));
                                        practiceAdapter.notifyDataSetChanged(); // Notify the adapter
                                    }
                                });
                            }

                        } else {
                            // Handle the case where the response is empty or not valid JSON
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(admin_view_teachers.this, "Empty or invalid response", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        // Handle the JSON parsing exception
                    }
                } else {
                    // Handle the case where the response is not successful (e.g., non-2xx status code)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(admin_view_teachers.this, "Unsuccessful response", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(practiceAdapter);
    }

}