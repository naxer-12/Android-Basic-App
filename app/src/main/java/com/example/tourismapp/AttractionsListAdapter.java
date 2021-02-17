package com.example.tourismapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AttractionsListAdapter extends ArrayAdapter<Attractions> {

    private static final String TAG = "AttractionAdapter" ;
    private Context _context;
    public AttractionsListAdapter(Context context, ArrayList<Attractions> attractions) {
        super(context, 0, attractions);
        this._context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Attractions attractions = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.attractions_list_layout, parent, false);
        }

        TextView attractionName = convertView.findViewById(R.id.tvAttractionName);
        TextView attractionAddress = convertView.findViewById(R.id.tvAttractionAddress);






        ImageView simpleImageView=convertView.findViewById(R.id.logoImageView);
        try {
            // get input stream

            InputStream ims = _context.getAssets().open(attractions.getImages()[0]);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            simpleImageView.setImageDrawable(d);
        }
        catch(IOException ex) {
        }




        attractionName.setText(attractions.name);
        attractionAddress.setText(attractions.address);

        return convertView;
    }

}