package com.kass.khaddamni.khaddamni.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.OfferActivity;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.entities.Applications;
import com.kass.khaddamni.khaddamni.entities.Offre;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 04/12/2016.
 */

public class OffreAppliedForAdapter
        extends ArrayAdapter<Offre> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    View view;
    Offre item;
    List<Applications> listApplications= new ArrayList<>();
    String urlApplications;
    List<Offre> listOffers= new ArrayList();
    List<String> listEtatOffre;
    List<String> countApplied;
    Button contact;




    public OffreAppliedForAdapter(Context context, int resourceId, List<Offre> articles, List<String> listEtatOffre, List<String> countApplied) {
        super(context, 0, articles);
        this.resourceId = resourceId;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listOffers=articles;
        this.listEtatOffre=listEtatOffre;
        this.countApplied=countApplied;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        //ImageView imgLogo;
        TextView textViewTitre, textViewPlace, textViewDate, countAplly;

        view = inflater.inflate(resourceId, parent, false);

        try {
            textViewTitre = (TextView)view.findViewById(R.id.textViewTitre);
            textViewPlace= (TextView) view.findViewById(R.id.textViewPlace) ;
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            countAplly= (TextView) view.findViewById(R.id.countApply);


        } catch( ClassCastException e ) {
            throw e;
        }

        item = getItem(position);


        textViewTitre.setText(item.getTitre());
        textViewPlace.setText(item.getPlace());
        textViewDate.setText(item.getDate());
        countAplly.setText("Appliers: "+countApplied.get(position));


        try {
            if (Integer.parseInt(listEtatOffre.get(position)) == 1) {
                Drawable d = view.getResources().getDrawable(R.drawable.accepted);
                //  etat.setImageDrawable(d);
                ((ImageView) view.findViewById(R.id.etatOffre)).setImageDrawable(d);

            }
            if (Integer.parseInt(listEtatOffre.get(position)) == 2) {
                Drawable d = view.getResources().getDrawable(R.drawable.refused);
                //  etat.setImageDrawable(d);
                ((ImageView) view.findViewById(R.id.etatOffre)).setImageDrawable(d);

            }
        }
        catch (IndexOutOfBoundsException a)
        {
            System.out.println( "erreur");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(),OfferActivity.class);

                Bundle b= new Bundle();
                b.putSerializable("offre", listOffers.get(position));
                i.putExtras(b);

               getContext().startActivity(i);

            }
        });












        return view;
    }
}
