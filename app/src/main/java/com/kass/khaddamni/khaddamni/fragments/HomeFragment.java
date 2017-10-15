package com.kass.khaddamni.khaddamni.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;
import com.kass.khaddamni.khaddamni.AddOffreActivity;
import com.kass.khaddamni.khaddamni.OfferActivity;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.adapter.OffreAdapter;
import com.kass.khaddamni.khaddamni.entities.Offre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;


public class HomeFragment extends Fragment {
    ListView listViewOffre;
    List<Offre> listOffre= new ArrayList<>();
    FloatingActionButton buttonAddOffer;
    String url = LINK+"getAll.php";
    String urlLiked=LINK+"getFavorites.php";
    View v;
    List<String> countApllied= new ArrayList<>();
    OffreAdapter offreAdapter;
    List<Offre> listFavourite= new ArrayList<>();
    StringRequest stringRequest;
    String idUser;
    RequestQueue rq;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       v =inflater.inflate(R.layout.fragment_home, container, false);

        listViewOffre = (ListView) v.findViewById(R.id.listViewOffre);
        buttonAddOffer= (FloatingActionButton) v.findViewById(R.id.buttonAddOffer);


        listViewOffre.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    buttonAddOffer.animate().cancel();
                    buttonAddOffer.hide(true);
                }else{
                    buttonAddOffer.animate().cancel();
                    buttonAddOffer.show(true);
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        idUser = preferences.getString("id", "");



        rq = Volley.newRequestQueue(getContext());
        offreAdapter=new OffreAdapter(v.getContext(), R.layout.list_item_offre, listOffre,countApllied,listFavourite);

        listViewOffre.setAdapter(offreAdapter);

   final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        /*getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);*/
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);

                try {
                    JSONArray j = new JSONArray(response);
                  //  pDialog.hide();
                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        Offre e1 = new Offre(et.getInt("id"), et.getString("titre"), et.getString("description"), et.getString("place"), et.getString("date"), et.getString("idUser"));
                        listOffre.add(e1);
                        countApllied.add(et.getString("nombre"));

                    }
                    pDialog.hide();

                   // pDialog.show();
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
                                System.out.println("fou9el pdialog");

                                System.out.println("ta7t pdialog");
                                offreAdapter.notifyDataSetChanged();


                               // getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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




                    System.out.println(listOffre);



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

        System.out.println("hedhi el lisa ");
        System.out.println(listOffre);




        buttonAddOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(getContext(), AddOffreActivity.class);
                startActivity(i);



            }
        });


        listViewOffre.setClickable(true);
        listViewOffre.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = listViewOffre.getItemAtPosition(position);

                 Offre o1=(Offre) o;
                System.out.println("omar lehne el id");
                System.out.println( o1.getId());
                Intent i= new Intent(getContext(), OfferActivity.class);

                Bundle b= new Bundle();
                b.putSerializable("offre", o1);
                i.putExtras(b);

                startActivity(i);

               // System.out.println( o1.get);
            }
        });







        return v;
    }






}
