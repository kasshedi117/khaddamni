package com.kass.khaddamni.khaddamni.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.OfferActivity;
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


public class AppliedForFragment extends Fragment {

    List<Offre> appliedFor= new ArrayList<>();
    ListView listViewFavourite;
    String urlOffersYouAppliedFor=LINK+"getAppliedIos.php";
    RequestQueue rq;
    StringRequest stringRequest;
    View view;
    String idUser;
    List<String> countApllied= new ArrayList<>();
    List<String> listEtatOffre= new ArrayList<>();
    OffreAppliedForAdapter offreAppliedForAdapter;

    public static AppliedForFragment newInstance() {
        AppliedForFragment fragment = new AppliedForFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_applied_for, null);



        listViewFavourite = (ListView) view.findViewById(R.id.listViewFavourite);


        rq = Volley.newRequestQueue(getContext());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        idUser = preferences.getString("id", "");

        urlOffersYouAppliedFor=urlOffersYouAppliedFor+"?idUser="+idUser;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlOffersYouAppliedFor, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {
                System.out.println("chouflena");
                System.out.println(response);
                countApllied.clear();


                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        Offre e = new Offre(et.getInt("id"),et.getString("titre"),  et.getString("description"),et.getString("place"),et.getString("date"),et.getString("idUser"));
                        appliedFor.add(e);
                        listEtatOffre.add(et.getString("etat"));
                        countApllied.add(et.getString("nombre"));
                    }
                    System.out.println("el  lista applied hay lena");
                    System.out.println(appliedFor);
                    offreAppliedForAdapter=new OffreAppliedForAdapter(view.getContext(), R.layout.list_item_offre_applied_for, appliedFor,listEtatOffre,countApllied);
                    listViewFavourite.setAdapter(offreAppliedForAdapter);

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




        return  view;
    }

}
