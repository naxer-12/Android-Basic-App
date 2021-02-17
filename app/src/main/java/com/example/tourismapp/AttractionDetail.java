package com.example.tourismapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourismapp.R;

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

        if(getIntent().getExtras() != null) {
            attractions = (ArrayList<Attractions> ) getIntent().getSerializableExtra("attractions");
            position =   getIntent().getIntExtra("position",0);
        }

        Log.d(TAG,"STARTED: "+position);
        Log.d(TAG,attractions.get(position).name);

        TextView attractionName =  findViewById(R.id.title);
        TextView attractionContact =  findViewById(R.id.phone);
        TextView attractionWebsite =  findViewById(R.id.website);
        TextView attractionDescription =  findViewById(R.id.description);
        Button goback =  findViewById(R.id.button1);
        Button logout =  findViewById(R.id.button2);



        attractionName.setText(attractions.get(position).name);
        attractionContact.setText(attractions.get(position).phone);
        attractionWebsite.setText(attractions.get(position).website);
        attractionDescription.setText(attractions.get(position).description);

        ImageView simpleImageView = findViewById(R.id.attractionImage);

        ratingbar=(RatingBar)findViewById(R.id.ratingBar);

        try {
            // get input stream
            InputStream ims = getAssets().open(attractions.get(position).getImages()[0]);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            simpleImageView.setImageDrawable(d);
        }
        catch(IOException ex) {
        }
        goback.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //do save the rating for the user in json here
                String rating=String.valueOf(ratingbar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                finish();

            }

        });

        logout.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                //do save the rating for the user in json here
                //do logout of the logged in user here before procceding
                String rating=String.valueOf(ratingbar.getRating());
                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
                finish();
            }

        });

    }


}