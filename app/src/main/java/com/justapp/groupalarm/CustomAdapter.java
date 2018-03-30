package com.justapp.groupalarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shagunbandi on 28/03/18.
 */

class CustomAdapter extends ArrayAdapter<String>{

    public CustomAdapter(@NonNull Context context, String[] foods) {
        super(context, R.layout.cutom_row, foods);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customView = myInflator.inflate(R.layout.cutom_row, parent, false);

        String singleFoodItem = getItem(position);
        TextView myText = (TextView) customView.findViewById(R.id.TVAlarm);

        myText.setText(singleFoodItem);
        return customView;
    }
}
