package com.kass.khaddamni.khaddamni;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.adapter.OffreAdapter;
import com.kass.khaddamni.khaddamni.adapter.UserAdapter;
import com.kass.khaddamni.khaddamni.entities.Applications;
import com.kass.khaddamni.khaddamni.entities.Offre;
import com.kass.khaddamni.khaddamni.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nesto.tagview.OnTagClickListener;
import nesto.tagview.OnTagLongClickListener;
import nesto.tagview.Tag;
import nesto.tagview.TagView;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class OfferWithUsersActivity extends AppCompatActivity {


    TextView titre;
    TextView description;
    TextView place;
    TextView date;
    TagView tagView;
    LinearLayout skillLinearLayout;
    String urlGetUser = LINK+"getUser.php";
    String url = LINK+"getSkills.php";
    String urlListUsers = LINK+"getUserForOfferId.php";
    String urlApplications;
    String urlApply = LINK+"applyIos.php";
    Button apply;
    Offre offre;
    String mail;
    List<User> listUser= new ArrayList<>();
    ArrayList<Tag> skills;
    ListView listViewuser;
    List<Applications> listApplications= new ArrayList<>();
    RequestQueue rq;


    UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_with_users);
        tagView = new TagView(getBaseContext());
        skillLinearLayout = (LinearLayout) findViewById(R.id.skills);
        skillLinearLayout.addView(tagView);
        apply = (Button) findViewById(R.id.buttonAplly);
        listViewuser=(ListView) findViewById(R.id.listProfiles);


        listViewuser.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });



        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        skills = new ArrayList<>();

        offre = (Offre) bundle.getSerializable("offre");
        urlListUsers=urlListUsers+"?id="+offre.getId();
        titre = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        place = (TextView) findViewById(R.id.place);
        date = (TextView) findViewById(R.id.date);
        titre.setText(offre.getTitre());
        description.setText(offre.getDescrition());
        place.setText(offre.getPlace());
        date.setText(offre.getDate());


        ////////////////////////////////////////////////////////////GET APPLICATIONS/////////////////////////////////////////
         rq = Volley.newRequestQueue(getBaseContext());

        urlApplications = LINK+"getApplications.php?idOffer="+offre.getId();
        userAdapter =new UserAdapter(getBaseContext(), R.layout.list_item_user, listUser,offre.getId(),listApplications);
        listViewuser.setAdapter(userAdapter);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApplications, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);

                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        Applications app = new Applications(et.getInt("id"),et.getInt("idOffer"),et.getInt("idUser"),et.getInt("etat"));
                        listApplications.add(app);
                        /////////////////////////////////////////


                       StringRequest stringRequest = new StringRequest(Request.Method.GET, urlListUsers, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);

                                try {
                                    JSONArray j = new JSONArray(response);

                                    for (int i = 0; i < j.length(); i++) {
                                        JSONObject et = j.getJSONObject(i);

                                        User user = new User(et.getInt("id"),et.getString("lastName"),  et.getString("firstName"),et.getString("userName"),et.getString("email"));
                                        listUser.add(user);
                                    }

                                    System.out.println(listUser);
                        userAdapter.notifyDataSetChanged();

                 /*   User u1= new User(10,"omar","omar","omar","omar");
                    User u2= new User(11,"omar","omar","omar","omar");
                    User u3= new User(12,"omar","omar","omar","omar");
        listUser.add(u1);
        listUser.add(u2);
        listUser.add(u3);*/




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









                        ///////////////////////////








                    }
                    System.out.println("lena tawa");

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


        ///////////////////////////////////////////////////////////////////////////////////////////////





   System.out.println(offre.getId());

        url = url + "?id=" + offre.getId();
        System.out.println(url);
      stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

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
                                    @Override
                                    public void tagClicked(String item) {
                                        // do whatever you like
                                    }
                                })
                                .setListener(new OnTagLongClickListener() {
                                    @Override
                                    public void tagLongClicked(String item) {
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

                        tagView.addTags(skills);


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

        rq.add(stringRequest);
        rq.start();










    }
}