package com.kass.khaddamni.khaddamni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.entities.Experience;
import com.kass.khaddamni.khaddamni.entities.Offre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 07/12/2016.
 */

public class ExperienceAdapter extends ArrayAdapter<Experience> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    View view;


    public ExperienceAdapter(Context context, int resourceId, List<Experience> articles) {
        super(context, 0, articles);
        this.resourceId = resourceId;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        TextView textViewTitre, textViewDescription, textViewCompany,textViewDuree;

        view = inflater.inflate(resourceId, parent, false);

        try {
            textViewTitre = (TextView)view.findViewById(R.id.textViewTitre);
            textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
            textViewCompany= (TextView) view.findViewById(R.id.textViewCompanyName);
            textViewDuree= (TextView) view.findViewById(R.id.textViewDuree);
        } catch( ClassCastException e ) {
            throw e;
        }

        Experience item = getItem(position);


        textViewTitre.setText(item.getTitle());
        textViewDescription.setText("Description : "+item.getDescription());
        textViewCompany.setText("Company: "+item.getCompany());
        textViewDuree.setText("Duration: "+item.getDuree());


        return view;
    }
}
