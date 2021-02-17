package com.example.tourismapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tourismapp.database.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AttractionListActivity extends AppCompatActivity {

    final String TAG = "Tourist Places";
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_list);
        Log.d(TAG, " activity started");
        String fileContents = loadDataFromFile("attractions.json");
        JSONObject attractions = convertToJSON(fileContents);
        parseJSONData(attractions);
        Button logout = findViewById(R.id.button1);
        sessionManager = new SessionManager(getApplicationContext());

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //do logout of the logged in user here before procceding
                JSONObject userData = null;
                Log.d(TAG, "Inside on click");

                try {
                    userData = new JSONObject(sessionManager.readUserData());
                    JSONArray userInfoArray = userData.getJSONArray("userdata");
                    String currentLoggedInUserName = sessionManager.getCurrentUserEmail();
                    for (int i = 0; i < userInfoArray.length(); i++) {
                        String name = userInfoArray.getJSONObject(i).getString("username");
                        if (currentLoggedInUserName.equals(name)) {
                            boolean loggedInStatus = userInfoArray.getJSONObject(i).getBoolean("isLoggedIn");
                            if (loggedInStatus) {
                                userInfoArray.getJSONObject(i).put("isLoggedIn", false);
                                userInfoArray.getJSONObject(i).put("rememberMe", false);
                                Intent intent = new Intent(AttractionListActivity.this, LoginActivity.class);
                                intent.putExtra("finish", true);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                startActivity(intent);
                                finish();
                            }

                        }


                    }
                    userData.put("userdata", userInfoArray);
                    sessionManager.writeUserData(userData.toString());
                    Log.d(TAG, userData.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                finish();
            }

        });
    }

    public String loadDataFromFile(String FILENAME) {
        String jsonString;

        try {
            InputStream fileData = this.getAssets().open(FILENAME);
            int fileSize = fileData.available();
            byte[] buffer = new byte[fileSize];
            fileData.read(buffer);
            fileData.close();
            jsonString = new String(buffer, "UTF-8");
            return jsonString;

        } catch (IOException e) {
            Log.d(TAG, "Error opening file.");
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject convertToJSON(String fileData) {
        JSONObject jsonData;
        try {
            jsonData = new JSONObject(fileData);
            return jsonData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void parseJSONData(JSONObject jsonObject) {
        ArrayList<Attractions> attractions = new ArrayList<Attractions>();
        try {
            JSONArray attractionsArr = jsonObject.getJSONArray("attractions");
            for (int i = 0; i < attractionsArr.length(); i++) {
                JSONObject attractionObj = (JSONObject) attractionsArr.get(i);
                String attractionName = attractionObj.getString("name");
                String address = attractionObj.getString("address");
                String description = attractionObj.getString("description");
                int price = (int) attractionObj.getInt("price");
                String website = attractionObj.getString("website");
                String phone = attractionObj.getString("phone");
                JSONArray images = (JSONArray) attractionObj.get("images");
                String imgs[] = new String[4];
                for (int count = 0; count < images.length(); count++) {
                    imgs[count] = images.get(count).toString();
                }
                Attractions newAdd = new Attractions(attractionName, address, description, price, website, phone, imgs);
                attractions.add(newAdd);
                // reference to your listview
                ListView lv = (ListView) findViewById(R.id.lvAttractionsListView);

                lv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View view,
                                                    int position, long id) {

                                //Take action here.
                                Log.d(TAG, "CLICKED");
                                Intent intent = new Intent(AttractionListActivity.this, AttractionDetail.class);
                                intent.putExtra("attractions", attractions);
                                intent.putExtra("position", position);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                );

                // create an adapter
                AttractionsListAdapter adapter = new AttractionsListAdapter(this, attractions);
                // associate this adapter with the listview

                if (lv != null) {

                    lv.setAdapter(adapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    protected void onListItemClick(ListView l, View v, final int position, long id) {

        //Go to another activity etc
    }


}