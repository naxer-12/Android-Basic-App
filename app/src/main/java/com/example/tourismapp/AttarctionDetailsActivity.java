package com.example.tourismapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AttarctionDetailsActivity extends AppCompatActivity  {
    private Button mLogoutButton;
    private Button mBackButton;
    String TAG = "AttractionDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Attraction Details Screen");
    }
}
