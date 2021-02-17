package com.example.tourismapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AttractionsListAdapter extends ArrayAdapter<Attractions> {

    public AttractionsListAdapter(Context context, ArrayList<Attractions> attractions) {
        super(context, 0, attractions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Attractions attractions = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.attractions_list_layout, parent, false);
        }

        TextView attractionName = convertView.findViewById(R.id.tvAttractionName);
        TextView attractionAddress = convertView.findViewById(R.id.tvAttractionAddress);

        attractionName.setText(attractions.name);
        attractionAddress.setText(attractions.address);

        return convertView;
    }
}