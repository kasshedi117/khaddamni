package com.kass.khaddamni.khaddamni.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.kass.khaddamni.khaddamni.AddExperienceActivity;
import com.kass.khaddamni.khaddamni.AddUserSkillsActivity;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.adapter.ExperienceAdapter;
import com.kass.khaddamni.khaddamni.entities.Experience;
import com.kass.khaddamni.khaddamni.entities.User;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import info.hoang8f.widget.FButton;
import nesto.tagview.OnTagClickListener;
import nesto.tagview.OnTagLongClickListener;
import nesto.tagview.Tag;
import nesto.tagview.TagView;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class ProfileFragment extends Fragment {

    FloatingActionButton exp,addSkills,updateProfileImage;
    ImageButton editEmailbtn;
    EditText editEmail;
    TagView tagView;
    TextView textSkills, textExperience,textViewNumber;
    List<Experience> listExperience= new ArrayList<>();
    ListView listViewExperience;
    String url=LINK+"getUser.php";
    String urlSkills=LINK+"getUserSkills.php";
    String urlExperience = LINK+"getUserExperience.php";
    String urlProfileImage=LINK+"profileImage/";
    TextView name, birthDate, email;
    User u;
    LinearLayout skillRelativeLayout;
    ArrayList<Tag> skills= new ArrayList<>();
    ExperienceAdapter experienceAdapter;
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String UPLOAD_URL =LINK+"upload.php";
    AlertDialog.Builder alertadd;
    Bitmap imageChoosed=null;
    ImageView imageProfile;

    int nbrRequest =0;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String idUser = preferences.getString("id", "");


        listViewExperience=(ListView) v.findViewById(R.id.listExperience) ;
        name=(TextView) v.findViewById(R.id.textViewName) ;
        birthDate=(TextView) v.findViewById(R.id.textViewBirthDate);
        email=(TextView) v.findViewById(R.id.textViewEmail) ;
        tagView = new TagView(getContext());
        skillRelativeLayout=(LinearLayout) v.findViewById(R.id.skills);
        skillRelativeLayout.addView(tagView);
        imageProfile= (ImageView) v.findViewById(R.id.imageProfile);
        textSkills= (TextView) v.findViewById(R.id.textSkills);
        textExperience= (TextView) v.findViewById(R.id.textExperience);
        textViewNumber=(TextView) v.findViewById(R.id.textViewNumber) ;


        exp=(FloatingActionButton) v.findViewById(R.id.buttonAddExperience) ;
        addSkills=(FloatingActionButton) v.findViewById(R.id.addSkills) ;
        updateProfileImage=(FloatingActionButton) v.findViewById(R.id.updateProfileImage) ;


        RequestQueue rq = Volley.newRequestQueue(getContext());
        urlProfileImage+=idUser+".png";
        System.out.println(urlProfileImage);

       // Picasso.with(getContext()).load(urlProfileImage).into(imageProfile);
        System.out.println("imageok");
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();



        ImageRequest ir = new ImageRequest(urlProfileImage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                System.out.println("imgOffer");
                imageProfile.setImageBitmap(response);
                nbrRequest++;
                if(nbrRequest==3)
                {
                    pDialog.hide();
                }



            }
        }, 0, 0, null,
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("kass : error");
                        System.out.println(error);
                        nbrRequest++;
                        if(nbrRequest==3)
                        {
                            pDialog.hide();
                        }

                    }
                });


        rq.add(ir);
        rq.start();



         url=url+"?id="+idUser;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println(response);

                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

          u= new User(et.getInt("id"),et.getString("lastName"),et.getString("firstName"),et.getString("userName"), et.getString("email"));
                        name.setText(u.getFirstName()+" "+u.getFirstName());
                        birthDate.setText(et.getString("birthDate"));
                        email.setText(u.getEmail());
                        textViewNumber.setText(et.getString("number"));
                    }
              nbrRequest++;
                    if(nbrRequest==3)
                    {
                        pDialog.hide();
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

     experienceAdapter=new ExperienceAdapter(getContext(), R.layout.list_item_experience,listExperience);

        listViewExperience.setAdapter(experienceAdapter);


                    urlExperience+="?idUser="+idUser;
        stringRequest = new StringRequest(Request.Method.GET, urlExperience, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("experience");

                System.out.println(response);

                try {
                    JSONArray j = new JSONArray(response);

                    for (int i = 0; i < j.length(); i++) {
                        JSONObject et = j.getJSONObject(i);

                        Experience e1 = new Experience(et.getString("titre"),  et.getString("description"),et.getString("duree"),et.getString("company"),et.getString("idUser"));
                        listExperience.add(e1);


                    }

                    System.out.println(listExperience);
                    if(listExperience.isEmpty())
                    {
                        textExperience.setText("");
                    }

            experienceAdapter.notifyDataSetChanged();
    //listViewExperience.


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



       rq = Volley.newRequestQueue(getContext());

        urlSkills=urlSkills+"?id="+idUser;

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlSkills, new Response.Listener<String>() {

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
                    if(skills.isEmpty())
                        textSkills.setText("");

                    nbrRequest++;
                    if(nbrRequest==3)
                    {
                        pDialog.hide();
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

        rq.add(stringRequest1);
        rq.start();



        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), AddExperienceActivity.class);
                startActivity(i);
            }
        });
        addSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), AddUserSkillsActivity.class);
                System.out.println("omaaaaar");
                startActivity(i);
            }
        });

        updateProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             imageDialogFragment dialog = imageDialogFragment.newInstance(1);
             dialog.show(getFragmentManager(),"qs");

            }

        });

        return v;
    }

}
