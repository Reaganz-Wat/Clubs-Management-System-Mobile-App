package com.example.cms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class select_club_to_join extends AppCompatActivity {

    public static final String USE_PREFERENCES = "UserPreferences";
    TextView clubname, clubdescription, clubCreator;
    String joinClubURL = MyConstants.BASE_URL + "/joinClubs.php";
    String fetchClubDetailsURL = MyConstants.BASE_URL + "/fetchClubs.php";
    Button joinClub;
    String club_id, student_id;
    HashMap<String, String> clubNameAndID;
    SharedPreferences sharedPreferences;
    String responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_club_to_join);

        clubname = findViewById(R.id.join_club_name);
        clubdescription = findViewById(R.id.join_club_description);
        clubCreator = findViewById(R.id.join_club_creator);
        joinClub = findViewById(R.id.join_club_button);
        sharedPreferences = getSharedPreferences(USE_PREFERENCES, Context.MODE_PRIVATE);

        getAllDataFromIntent();
        //fetchClubDetail();
        //createClub();

    }

    public void getAllDataFromIntent(){
        String c_name = getIntent().getStringExtra("club_name");
        String c_description = getIntent().getStringExtra("description");
        String c_creator = getIntent().getStringExtra("created_by");

        // set the text to the value got from the intents
        clubname.setText(c_name);
        clubdescription.setText(c_description);
        clubCreator.setText(c_creator);
    }

    public void createClub(){

        joinClub.setOnClickListener(new View.OnClickListener() {
            // HINTS ...
            // get the id of the logged user from the SharedPreferences
            int id = sharedPreferences.getInt("id", 0);
            String clubNameID = getIntent().getStringExtra("club_name");

            // get the id of the club from the HashMap
            String student_id1 = clubNameAndID.get(clubNameID);

            @Override
            public void onClick(View view) {
                sendDataToDatabase(club_id, student_id1);
            }
        });
    }
    public void sendDataToDatabase(String clubID, String studentID){
        // send data to database to join the club
        OkHttpClient client = new OkHttpClient();
        RequestBody form = new FormBody.Builder()
                .add("club_id", clubID)
                .add("student_id", String.valueOf(studentID))
                .build();
        Request request = new Request.Builder()
                .url(joinClubURL)
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
                            Toast.makeText(select_club_to_join.this, "Clubs Joined Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
    }

    public void fetchClubDetail(){
        // fetch the club details
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fetchClubDetailsURL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    responseData = response.body().string();
                    saveDataToHashMap(responseData);
                }
            }
        });
    }

    public void saveDataToHashMap(String responseData){
        // this receives responseData from the API call inform of a string and then uses this string to get the JSONArray part of it
        try {
            JSONArray jsonArray = new JSONArray(responseData); // [{"key1", "value1"}, {"key2", "value2"}}, {"key3", "value3"}] are JSONArray

            for (int i = 0; i < jsonArray.length(); i ++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);  // {"key1", "value1"} for i = 0
                String clubID = jsonObject.getString("ClubID");
                String clubName = jsonObject.getString("ClubName");

                clubNameAndID.put(clubName, clubID);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}