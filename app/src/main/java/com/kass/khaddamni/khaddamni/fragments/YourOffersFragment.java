package com.kass.khaddamni.khaddamni.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.OfferActivity;
import com.kass.khaddamni.khaddamni.OfferWithUsersActivity;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.adapter.OffreAdapter;
import com.kass.khaddamni.khaddamni.entities.Offre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class YourOffersFragment extends Fragment {
    RequestQueue rq;
    StringRequest stringRequest;
    View view;
    String idUser;
    List<Offre> YourOffers= new ArrayList<>();
    List<String> countApllied= new ArrayList<>();
    ListView listViewFavourite;
    String urlYourOffers=LINK+"getUserOffersIos.php";
    OffreAdapter offreAdapter;
    List<Offre> listFavourite= new ArrayList<>();
    String urlLiked=LINK+"getFavorites.php";

    public static YourOffersFragment newInstance() {
        YourOffersFragment fragment = new YourOffersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_your_offers, null);



        listViewFavourite = (ListView) view.findViewById(R.id.listViewFavourite);
        rq = Volley.newRequestQueue(getContext());
        offreAdapter= new OffreAdapter(view.getContext(), R.layout.list_item_offre, YourOffers,countApllied,listFavourite );

        listViewFavourite.setAdapter(offreAdapter);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        idUser = preferences.getString("id", "");

        urlYourOffers=LINK+"getUserOffersIos.php?idUser="+idUser;

        stringRequest = new StringRequest(Request.Method.GET, urlYourOffers, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);
                countApllied.clear();

                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        Offre e1 = new Offre(et.getInt("id"),et.getString("titre"),  et.getString("description"),et.getString("place"),et.getString("date"),et.getString("idUser"));
                        YourOffers.add(e1);
                        countApllied.add(et.getString("nombre"));


                    }

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

                            offreAdapter.notifyDataSetChanged();


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

                    System.out.println(YourOffers);







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

        rq.add(stringRequest);
        rq.start();


        listViewFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                System.out.println("omar hak nzelt");
                Object o = listViewFavourite.getItemAtPosition(position);

                Offre o1=(Offre) o;

                System.out.println( o1.getId());
                Intent i= new Intent(getContext(), OfferWithUsersActivity.class);

                Bundle b= new Bundle();
                b.putSerializable("offre", o1);
                i.putExtras(b);

                startActivity(i);


            }
        });






        return  view;

    }






}
