package com.kass.khaddamni.khaddamni.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.akashandroid90.imageletter.MaterialLetterIcon;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.entities.Applications;
import com.kass.khaddamni.khaddamni.entities.Offre;
import com.kass.khaddamni.khaddamni.entities.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

/**
 * Created by omar on 03/12/2016.
 */

public class UserAdapter extends ArrayAdapter<User> {

    private int resourceId = 0;
    private LayoutInflater inflater;
    String urlAccept = LINK+"changeEtat.php";
    int idOffer;
    TextView textViewName, textViewEmail;
    Button accept, refuse;
    ImageView etat,gmail;
    MaterialLetterIcon userImage;
    List<Applications> listApplications= new ArrayList<>();
    String urlProfileImage;
    MaterialLetterIcon imageProfile;


    public UserAdapter(Context context, int resourceId, List<User> articles,int idOffer, List<Applications> listApplications) {
        super(context, 0, articles);
        this.resourceId = resourceId;
        this.idOffer=idOffer;
        this.listApplications=listApplications;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final View view;




        view = inflater.inflate(resourceId, parent, false);




        System.out.println("trah chouf lehne");
        System.out.println(listApplications);


        try {
            textViewName = (TextView)view.findViewById(R.id.name);
            textViewEmail= (TextView)view.findViewById(R.id.Email);
            accept=(Button)view.findViewById(R.id.accept);
            refuse=(Button)view.findViewById(R.id.refuse);
            etat=(ImageView) view.findViewById(R.id.etat);
            gmail=(ImageView) view.findViewById(R.id.gmail);
            userImage= (MaterialLetterIcon) view.findViewById(R.id.imageProfile);
            imageProfile=(MaterialLetterIcon) view.findViewById(R.id.imageProfile);


        } catch( ClassCastException e ) {
            throw e;
        }

        final User item = getItem(position);
    textViewName.setText(item.getFirstName()+" "+item.getLastName());
        textViewEmail.setText(item.getEmail());
        userImage.setLetter(item.getFirstName().charAt(0)+"");



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d= view.getResources().getDrawable( R.drawable.accepted );
                //  etat.setImageDrawable(d);
                ((ImageView) view.findViewById(R.id.etat)).setImageDrawable(d);

                accept.setVisibility(View.GONE);
                refuse.setVisibility(View.GONE);


                RequestQueue rq = Volley.newRequestQueue(view.getContext());
                urlAccept=urlAccept+"?idOffre="+idOffer+"&idUser="+item.getId()+"&etat=1";
             /*   final ProgressDialog pDialog = new ProgressDialog(getContext());
                pDialog.setMessage("Loading...");
                pDialog.show();*/
                StringRequest stringRequestUser = new StringRequest(Request.Method.GET, urlAccept, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println(urlAccept);


                         //  pDialog.hide();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                );

                rq.add(stringRequestUser);
                rq.start();
            }
        });



        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d= view.getResources().getDrawable( R.drawable.refused );
              //  etat.setImageDrawable(d);
              ((ImageView) view.findViewById(R.id.etat)).setImageDrawable(d);
                accept.setVisibility(View.GONE);
                refuse.setVisibility(View.GONE);
                RequestQueue rq = Volley.newRequestQueue(view.getContext());
                urlAccept=urlAccept+"?idOffre="+idOffer+"&idUser="+item.getId()+"&etat=2";
                StringRequest stringRequestUser = new StringRequest(Request.Method.GET, urlAccept, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println(urlAccept);




                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                );

                rq.add(stringRequestUser);
                rq.start();
            }
        });


        System.out.println("profile mezel lena taw ");






            urlProfileImage = LINK+"profileImage/" + item.getId() + ".png";


            Picasso.with(getContext()).load(urlProfileImage).into(imageProfile);








try {
    if (listApplications.get(position).getEtat() == 1) {
        Drawable d = view.getResources().getDrawable(R.drawable.accepted);
        //  etat.setImageDrawable(d);
        ((ImageView) view.findViewById(R.id.etat)).setImageDrawable(d);
        accept.setVisibility(View.GONE);
        refuse.setVisibility(View.GONE);
    }
    if (listApplications.get(position).getEtat() == 2) {
        Drawable d = view.getResources().getDrawable(R.drawable.refused);
        //  etat.setImageDrawable(d);
        ((ImageView) view.findViewById(R.id.etat)).setImageDrawable(d);
        accept.setVisibility(View.GONE);
        refuse.setVisibility(View.GONE);
    }
}
catch (IndexOutOfBoundsException a)
{
    System.out.println( "erreur");
}


        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  ,new String[] { "omar.riahi@esprit.tn" });
                i.putExtra(Intent.EXTRA_SUBJECT, "Congratulation");
                i.putExtra(Intent.EXTRA_TEXT   , "you have been accepted for this job please check your app...");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    getContext().startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

}
