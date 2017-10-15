package com.kass.khaddamni.khaddamni.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.akashandroid90.imageletter.MaterialLetterIcon;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.entities.Offre;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

/**
 * Created by omar on 04/11/2016.
 */

public class OffreAdapter extends ArrayAdapter<Offre> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    List<String> countApplied= new ArrayList<>();
    String urlProfileImage;
    MaterialLetterIcon imageOffer ;
    String urlLiked=LINK+"getFavorites.php",idUser;
    String urlLikeClicked;
    ShineButton imageLike;
    RequestQueue rq = Volley.newRequestQueue(getContext());
    StringRequest stringRequest;

    int exist;
    View view;
    List<Offre> listFavourite= new ArrayList<>();


    public OffreAdapter(Context context, int resourceId, List<Offre> articles, List<String> countApplied,List<Offre> listFavourite) {
        super(context, 0, articles);
        this.resourceId = resourceId;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.countApplied=countApplied;
        this.listFavourite=listFavourite;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        idUser = preferences.getString("id", "");





    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final ImageView imgLogo;
        TextView textViewTitre, textViewPlace, textViewDate,countAplly;


        view = inflater.inflate(resourceId, parent, false);

        try {
            textViewTitre = (TextView)view.findViewById(R.id.textViewTitre);
            textViewPlace= (TextView) view.findViewById(R.id.textViewPlace) ;
            textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            countAplly= (TextView) view.findViewById(R.id.countApply);
            imageLike=(ShineButton) view.findViewById(R.id.imageLike) ;
            imgLogo  =  (ImageView) view.findViewById(R.id.imageView);
            imageOffer = (MaterialLetterIcon) view.findViewById(R.id.imageOffre);

        } catch( ClassCastException e ) {
            throw e;
        }

        final Offre item = getItem(position);


        textViewTitre.setText(item.getTitre());
        textViewPlace.setText(item.getPlace());
        textViewDate.setText(item.getDate());



        for(int i=0;i<listFavourite.size(); i++)
        {


        if(listFavourite.get(i).getId()==item.getId())
        {
           imageLike.setChecked(true);

        }
        }

        urlProfileImage = LINK+"Image/"+item.getId()+".png";






        Picasso.with(getContext()).load(urlProfileImage).fit().into(imgLogo, new Callback() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Drawable d= view.getResources().getDrawable( R.drawable.unnamed);
                imgLogo.setImageDrawable(d);
            }
        });


        countAplly.setText("Appliers: "+countApplied.get(position));

       /* RequestQueue rq = Volley.newRequestQueue(getContext());



        ImageRequest ir = new ImageRequest(urlProfileImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                System.out.println("image wosslet");
                imgLogo.setImageBitmap(response);
                imageOffer.setVisibility(View.INVISIBLE);

            }
        }, 0, 0, null,
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("kass : error");
                        System.out.println(error);
                    }
        });


        rq.add(ir);
        rq.start();
*/

        /*
        imgLogo.setImageDrawable(R.drawable.about);
        */
       imageLike.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               System.out.println("hak nzelt 3al 9alb");

               exist=0;
               for(int i=0;i<listFavourite.size(); i++)
               {
                   if(listFavourite.get(i).getId()==item.getId())
                   {
                       exist=i;
                   }
               }
               if(exist!=0)
               { System.out.println("checked");
                   urlLikeClicked=LINK+"deleteFavorite.php?";





               }
               else
               {           System.out.println("not checked");
                   urlLikeClicked=LINK+"favoritesUsersOffersIos.php?";

               }

        /*       final ProgressDialog pDialog = new ProgressDialog(getContext());
               pDialog.setMessage("Loading...");
               pDialog.show();
*/

                    urlLikeClicked+="idUser="+idUser+"&idOffer="+item.getId();
               System.out.println(urlLikeClicked);
               stringRequest = new StringRequest(Request.Method.GET, urlLikeClicked, new Response.Listener<String>() {

                   @Override
                   public void onResponse(String response) {
                       System.out.println(response);
                       if(exist!=0)
                       {
                           imageLike.setChecked(false);

                                   listFavourite.remove(exist);
                         //  pDialog.hide();
                          // this.notifyAll();

                       }
                       else
                       {imageLike.setChecked(true);
                           listFavourite.add(item);
                           //pDialog.hide();

                       }


                   }
               }, new Response.ErrorListener() {

                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               }
               );

               rq.add(stringRequest);
               rq.start();
           }
       });

        return view;

    }

}
