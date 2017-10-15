package com.kass.khaddamni.khaddamni.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.adapter.OffreAdapter;
import com.kass.khaddamni.khaddamni.adapter.OffreAppliedForAdapter;
import com.kass.khaddamni.khaddamni.entities.Offre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class FavouriteOffersFragment extends Fragment {

    List<Offre> favouriteOffers= new ArrayList<>();
    ListView listViewFavourite;
    String urlFavouriteOffers=LINK+"getFavorites.php";
    RequestQueue rq;

    View view;
    String idUser;
    String url=LINK+"getFavorites.php";
    List<String> countApllied= new ArrayList<>();
    //List<String> listEtatOffre= new ArrayList<>();
    OffreAdapter offreAdapter;
    List<Offre> listFavourite= new ArrayList<>();
    StringRequest stringRequest;

    public static FavouriteOffersFragment newInstance() {
        FavouriteOffersFragment fragment = new FavouriteOffersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_favourite_offers, null);



        listViewFavourite = (ListView) view.findViewById(R.id.listViewFavourite);


        rq = Volley.newRequestQueue(getContext());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        idUser = preferences.getString("id", "");
        System.out.println("tawa rak lena thabet");

        urlFavouriteOffers=urlFavouriteOffers+"?idUser="+idUser;
        System.out.println(urlFavouriteOffers);


        offreAdapter= new OffreAdapter(view.getContext(), R.layout.list_item_offre, favouriteOffers,countApllied,listFavourite);
        listViewFavourite.setAdapter(offreAdapter);


        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        stringRequest = new StringRequest(Request.Method.GET, urlFavouriteOffers, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                System.out.println("chouflena el Favourite");
                System.out.println(response);
                countApllied.clear();


                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        Offre e = new Offre(et.getInt("id"),et.getString("titre"),  et.getString("description"),et.getString("place"),et.getString("date"),et.getString("idUser"));
                        favouriteOffers.add(e);
                       // listEtatOffre.add(et.getString("etat"));
                        countApllied.add(et.getString("nombre"));
                    }

                    pDialog.hide();







                } catch (JSONException e) {
                    e.printStackTrace();
                }


                url=url+"?idUser="+idUser;
                stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
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


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        rq.add(stringRequest);
        rq.start();




        return  view;
    }

}