package com.kass.khaddamni.khaddamni.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kass.khaddamni.khaddamni.R;

/**
 * Created by omar on 05/11/2016.
 */

public class SettingsAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public SettingsAdapter(Activity context, String[] web, Integer[] imageId)
    {
        super(context, R.layout.list_settings, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_settings, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textViewText);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewSettings);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
