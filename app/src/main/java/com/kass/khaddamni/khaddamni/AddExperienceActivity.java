package com.kass.khaddamni.khaddamni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class AddExperienceActivity extends AppCompatActivity {

    EditText title,company,description,duration;
    Button addExperience;
    String url = LINK+"addExperience.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);

        title=(EditText) findViewById(R.id.editTextTitle);
        company=(EditText) findViewById(R.id.editTextCompany);
        description=(EditText) findViewById(R.id.editTextDescription);
        duration=(EditText) findViewById(R.id.editTextDuration);
        addExperience=(Button) findViewById(R.id.buttonAddExperience);


        addExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue rq = Volley.newRequestQueue(getBaseContext());
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String idUser = preferences.getString("id", "");

                System.out.println(idUser+"omar omar lehne el id");
                url = url + "?title=" + title.getText().toString().replace(" ", "+++") + "&company=" + company.getText().toString().replace(" ", "+++")
                        + "&description=" + description.getText().toString().replace(" ", "+++") + "&duration=" +duration.getText().toString().replace(" ", "+++")
                        +"&idUser="+idUser;

                System.out.println(url);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        System.out.println(url);


                        Intent i = new Intent(getBaseContext(), AllActivity.class);


                        startActivity(i);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println(error);
                    }
                }
                );

                rq.add(stringRequest);
                rq.start();

            }
        });
    }
}
