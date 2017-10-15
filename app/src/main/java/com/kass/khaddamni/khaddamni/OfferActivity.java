package com.kass.khaddamni.khaddamni;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.kass.khaddamni.khaddamni.adapter.OffreAdapter;
import com.kass.khaddamni.khaddamni.entities.Offre;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import nesto.tagview.OnTagClickListener;
import nesto.tagview.OnTagLongClickListener;
import nesto.tagview.Tag;
import nesto.tagview.TagView;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class OfferActivity extends AppCompatActivity {



        TextView titre;
        TextView description;
        TextView place;
        TextView date;
        TagView tagView;
        LinearLayout skillLinearLayout;
    ImageView imageOffre;
        String urlGetUser = LINK+"getUser.php";
        String url = LINK+"getSkills.php";
        String urlApply = LINK+"applyIos.php";
    String urlverifApplyOrNot = LINK+"verifApplyOrNot.php";
        Button apply;
        Offre offre;
        String mail;
    String verif="omar";
    String urlLiked=LINK+"getFavorites.php",idUser;
    String urlLikeClicked;
    ShineButton imageLike;
    int exist;
    List<Offre> listFavourite= new ArrayList<>();
    RequestQueue rq ;

        ArrayList<Tag> skills;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_offer);
            tagView = new TagView(getBaseContext());
            skillLinearLayout=(LinearLayout) findViewById(R.id.skills);
            skillLinearLayout.addView(tagView);
            apply= (Button) findViewById(R.id.buttonAplly) ;
            imageOffre=(ImageView) findViewById(R.id.imageOffre) ;
            imageLike=(ShineButton) findViewById(R.id.like);

          rq  = Volley.newRequestQueue(getBaseContext());

            Intent intent = this.getIntent();
            Bundle bundle = intent.getExtras();
            skills=new ArrayList<>();

            offre= (Offre) bundle.getSerializable("offre");
            titre=(TextView) findViewById(R.id.title);
            description=(TextView) findViewById(R.id.description);
            place=(TextView) findViewById(R.id.place);
            date=(TextView) findViewById(R.id.date);
            titre.setText(offre.getTitre());
            description.setText(offre.getDescrition());
            place.setText(offre.getPlace());
            date.setText(offre.getDate());





            RequestQueue rq1 = Volley.newRequestQueue(getBaseContext());
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
           idUser = preferences.getString("id", "");
            urlverifApplyOrNot=urlverifApplyOrNot+"?idOffer="+offre.getId()+"&idUser="+idUser;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlverifApplyOrNot, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println("hedhi el response");
                    System.out.println("aa"+response+"aa");
               verif=response;
                    if (verif.equals("true")) {
                        apply.setVisibility(View.GONE);
                    }




                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );

            rq1.add(stringRequest);
            rq1.start();



            urlLiked=urlLiked+"?idUser="+idUser;
            stringRequest = new StringRequest(Request.Method.GET, urlLiked, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println(urlLiked);
                    System.out.println("lena el response");
                    System.out.println(response);

                    try {
                        JSONArray j = new JSONArray(response);

                        for (int i = 0; i < j.length(); i++) {
                            JSONObject et = j.getJSONObject(i);

                            Offre e1 = new Offre(et.getInt("id"),et.getString("titre"),  et.getString("description"),et.getString("place"),et.getString("date"),et.getString("idUser"));
                            listFavourite.add(e1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                        for(int i=0; i<listFavourite.size();i++)
                        {
                            if(listFavourite.get(i).getId()== offre.getId())
                            {
                                imageLike.setChecked(true);
                            }
                        }

                    System.out.println("lena list el favourite elli testana fiha");
                    System.out.println(listFavourite);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );

            rq.add(stringRequest);
            rq.start();










            url=url+"?id="+offre.getId();

            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println(response);

                    try {
                        JSONArray j = new JSONArray(response);

                        for (int i = 0; i < j.length(); i++) {
                            JSONObject et = j.getJSONObject(i);

                            skills.add(new Tag(et.getString("skill")));

                            tagView.textColor(Color.argb(0xff, 0xff, 0xff, 0xff))
                                    .backgroundColor(Color.argb(0xff, 0x7f, 0x7f, 0x7f))
                                    .setListener(new OnTagClickListener() {
                                        @Override public void tagClicked(String item) {
                                            // do whatever you like
                                        }
                                    })
                                    .setListener(new OnTagLongClickListener() {
                                        @Override public void tagLongClicked(String item) {
                                            // do whatever you like
                                        }
                                    })
                                    .textSize(10)
                                    .margin(2)
                                    .padding(16)
                                    .corner(5)
                                    .itemHeight(30);
                            System.out.println("omar");
                            System.out.println(skills);






                        }

                        tagView.addTags(skills);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );

            rq.add(stringRequest1);
            rq.start();
            ////////////////////////////////////like clicked/////////////////////////////////

            imageLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("hak nzelt 3al 9alb");

                    exist=0;
                    for(int i=0;i<listFavourite.size(); i++)
                    {
                        if(listFavourite.get(i).getId()==offre.getId())
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

                    final ProgressDialog pDialog = new ProgressDialog(getBaseContext());
                    pDialog.setMessage("Loading...");
                  //  pDialog.show();


                    urlLikeClicked+="idUser="+idUser+"&idOffer="+offre.getId();
                    System.out.println(urlLikeClicked);
                   StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLikeClicked, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            if(exist!=0)
                            {
                                imageLike.setChecked(false);

                                listFavourite.remove(exist);
                             //   pDialog.hide();


                            }
                            else
                            {imageLike.setChecked(true);
                                listFavourite.add(offre);
                                pDialog.hide();

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



            ///////////////////////////////GET IMAGE/////////////////////////////


           String urlProfileImage = LINK+"Image/" + offre.getId() + ".png";

            Picasso.with(getBaseContext()).load(urlProfileImage).fit().into(imageOffre, new Callback() {

                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Drawable d= getResources().getDrawable( R.drawable.default_offer);
                    imageOffre.setImageDrawable(d);
                }
            });



     /*    rq = Volley.newRequestQueue(this);

            ImageRequest ir = new ImageRequest(urlProfileImage, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    System.out.println("imgOffer");
                    imageOffre.setImageBitmap(response);



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


        //////////////////////////////ToGetUser/////////////////////////////////////////

        urlGetUser=urlGetUser+"?id="+offre.getIdUser();
        System.out.println("lena el id user"+offre.getIdUser());

        StringRequest stringRequestUser = new StringRequest(Request.Method.GET, urlGetUser, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println(response);
                System.out.println(urlGetUser);
                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        mail=et.getString("email");

                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        rq.add(stringRequestUser);
        rq.start();


            System.out.println( verif+"hedhi el verif");








        ///////////////////////////////////////////////////////////////////////




        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String idUser = preferences.getString("id", "");
                RequestQueue rq = Volley.newRequestQueue(getBaseContext());

                urlApply = urlApply + "?idUser=" + idUser + "&idOffer=" + offre.getId();

                System.out.println(url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApply, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    apply.setEnabled(false);
                        System.out.println(url);



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("omaromaromaromar");
                        System.out.println(error);
                    }
                }
                );

                rq.add(stringRequest);
                rq.start();


      /////////////////////////////////MAIL///////////////////////////////////////////
                   System.out.println("hedha el mail"+mail);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  ,new String[] { mail });
                i.putExtra(Intent.EXTRA_SUBJECT, "Offer: "+offre.getTitre()+" in KHadamni App");
                i.putExtra(Intent.EXTRA_TEXT   , "someone applied for your offer please check your app ti answer him! thank you for your fidelity");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

///////////////////////////////////////////////////////Notification Et marche//////////////////////////////////////
           /*     NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.profile)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(getBaseContext(), AllActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(AllActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
                mNotificationManager.notify(10, mBuilder.build());*/



            }
        });






    }
}
