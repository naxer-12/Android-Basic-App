package com.example.tourismapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AttractionListActivity extends AppCompatActivity {

    final String TAG = "Tourist Places";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String fileContents = loadDataFromFile("attractions.json");
        JSONObject attractions = convertToJSON(fileContents);
        parseJSONData(attractions);

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
            Log.d(TAG,"Error opening file.");
            e.printStackTrace();
            return null;
        }
    }
    public JSONObject convertToJSON(String fileData) {
        JSONObject jsonData;
        try {
            jsonData = new JSONObject(fileData);
            return jsonData;
        }catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void parseJSONData(JSONObject jsonObject) {
        ArrayList<Attractions> attractions = new ArrayList<Attractions>();
        try {
            JSONArray attractionsArr = jsonObject.getJSONArray("attractions");
            for (int i = 0; i < attractionsArr.length();i++) {
                JSONObject attractionObj = (JSONObject) attractionsArr.get(i);
                String attractionName = attractionObj.getString("name");
                String address = attractionObj.getString("address");
                String description = attractionObj.getString("description");
                int price = (int) attractionObj.getInt("price");
                String website = attractionObj.getString("website");
                String phone = attractionObj.getString("phone");

                attractions.add(new Attractions(attractionName, address, description, price, website, phone));
                 // reference to your listview
                ListView lv = findViewById(R.id.lvAttractionsListView);

                // create an adapter
                AttractionsListAdapter adapter = new AttractionsListAdapter(this,attractions);
                // associate this adapter with the listview
                lv.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}