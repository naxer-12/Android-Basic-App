package com.example.tourismapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismapp.R;
import com.example.tourismapp.database.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AttractionDetail extends AppCompatActivity {
    final String TAG = "Attraction details";
    ArrayList<Attractions> attractions;
    RatingBar ratingbar;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_details);

        if (getIntent().getExtras() != null) {
            attractions = (ArrayList<Attractions>) getIntent().getSerializableExtra("attractions");
            position = getIntent().getIntExtra("position", 0);
        }
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        Log.d(TAG, "STARTED: " + position);
        Log.d(TAG, attractions.get(position).name);

        TextView attractionName = findViewById(R.id.title);
        TextView attractionContact = findViewById(R.id.phone);
        TextView attractionWebsite = findViewById(R.id.website);
        TextView attractionDescription = findViewById(R.id.description);
        Button goback = findViewById(R.id.button1);
        Button logout = findViewById(R.id.button2);


        attractionName.setText(attractions.get(position).name);
        attractionContact.setText(attractions.get(position).phone);
        attractionWebsite.setText(attractions.get(position).website);
        attractionDescription.setText(attractions.get(position).description);

        ImageView simpleImageView = findViewById(R.id.attractionImage);
        ImageView simpleImageView2 = findViewById(R.id.attractionImage2);

        ratingbar = (RatingBar) findViewById(R.id.ratingBar);
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
                        JSONObject user = userInfoArray.getJSONObject(i);
                        JSONArray ratingsArray = user.getJSONArray("ratings");
                        if (ratingsArray.length() != 0) {

                            String currentSiteName = attractions.get(position).name;
                            for (int j = 0; j < ratingsArray.length(); j++) {
                                String siteName = ratingsArray.getJSONObject(j).getString("site_name");
                                if (currentSiteName.equals(siteName)) {
                                    ratingbar.setRating(Float.parseFloat(ratingsArray.getJSONObject(j).getString("rating")));
                                    break;

                                }

                            }

                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            // get input stream
            InputStream ims = getAssets().open(attractions.get(position).getImages()[0]);
            InputStream ims2 = getAssets().open(attractions.get(position).getImages()[1]);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            Drawable d2 = Drawable.createFromStream(ims2, null);

            // set image to ImageView
            simpleImageView.setImageDrawable(d);
            simpleImageView2.setImageDrawable(d2);
            ims.close();
            ims2.close();
//            ims.close();
        } catch (IOException ex) {
        }

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do save the rating for the user in json here
                String rating = String.valueOf(ratingbar.getRating());
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
                                boolean matchRating = false;
                                JSONObject user = userInfoArray.getJSONObject(i);
                                JSONObject ratingObject = new JSONObject();
                                JSONArray ratingsArray = user.getJSONArray("ratings");
                                if (ratingsArray.length() != 0) {
                                    String currentSiteName = attractions.get(position).name;
                                    for (int j = 0; j < ratingsArray.length(); j++) {
                                        String siteName = ratingsArray.getJSONObject(j).getString("site_name");
                                        if (currentSiteName.equals(siteName)) {
                                            matchRating = true;
                                            ratingsArray.getJSONObject(j).put("rating", rating);
                                            break;

                                        }

                                    }
                                    if (!matchRating) {
                                        ratingObject.put("site_name", attractions.get(position).name);
                                        ratingObject.put("rating", rating);
                                    }
                                } else {
                                    ratingObject.put("site_name", attractions.get(position).name);
                                    ratingObject.put("rating", rating);
                                }
                                if (ratingObject.length() != 0) {
                                    ratingsArray.put(ratingObject);

                                }
                                user.put("ratings", ratingsArray);
                                break;
                            }
                        }
                    }
                    sessionManager.writeUserData(userData.toString());
                    Log.d(TAG, sessionManager.readUserData());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();

            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            SessionManager sessionManager;

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AttractionDetail.this);
                builder.setTitle("Logout").
                        setMessage("You sure, that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                sessionManager = new SessionManager(getApplicationContext());
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
                                                Intent intent = new Intent(AttractionDetail.this, LoginActivity.class);
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

                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();

            }


        });

        attractionContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = attractionContact.getText().toString();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phone, null));
                startActivity(phoneIntent);
            }

        });

        attractionWebsite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse(attractionWebsite.getText().toString()));
                startActivity(Getintent);

            }
        });

    }


}