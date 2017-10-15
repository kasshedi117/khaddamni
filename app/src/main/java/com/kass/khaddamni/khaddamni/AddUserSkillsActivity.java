package com.kass.khaddamni.khaddamni;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import br.com.jpttrindade.tagview.OnTagClickListener;
import br.com.jpttrindade.tagview.Tag;
import br.com.jpttrindade.tagview.TagView;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class AddUserSkillsActivity extends AppCompatActivity {


    LinearLayout v;
    TagView tagView;
    AutoCompleteTextView textViewSkill;
    String url;
    int id=2;
    Button btn_addOffre;
    String idUser;
    String[] skills={"Android ","java","IOS","SQL","JDBC","Web services","IOT"};
    List<String> skillsList = new ArrayList<>();
    List<String> skillsList1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_skills);
        for(int i=0; i<skills.length;i++)
        {
            skillsList.add(skills[i]);
            skillsList1.add(skills[i]);
        }

        tagView = (br.com.jpttrindade.tagview.TagView) findViewById(R.id.tagview);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        idUser = preferences.getString("id", "");

        v=(LinearLayout) findViewById(R.id.viewgroup) ;
        textViewSkill=(AutoCompleteTextView) findViewById(R.id.skill) ;
        btn_addOffre=(Button) findViewById(R.id.btn_addSkill) ;

        ArrayAdapter adapter = new
                ArrayAdapter(this,android.R.layout.simple_list_item_1,skills);

        textViewSkill.setAdapter(adapter);
        textViewSkill.setThreshold(1);




        tagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position, int clickType) {
                switch (clickType) {
                    case TagView.ONCLICK_EDIT:
                        Log.d("DEBUG", "OnClickEdit");
                        break;
                    case TagView.ONCLICK_REMOVE:
                        Log.d("DEBUG", "OnClickRemove");
                        break;
                    case TagView.ONCLICK_DEFAULT:
                        Log.d("DEBUG", "OnClickDefault");
                }

            }
        });


        btn_addOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                System.out.println("omar rak lena");

                if(!textViewSkill.getText().toString().equals("")) {
                    if(!skillsList.contains(textViewSkill.getText().toString()))
                    {
                        if(skillsList1.contains(textViewSkill.getText().toString()))
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(AddUserSkillsActivity.this).create();
                            alertDialog.setTitle("Offer Skills");
                            alertDialog.setMessage("You already added this skill! choose another one!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        else
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(AddUserSkillsActivity.this).create();
                            alertDialog.setTitle("Offer Skills");
                            alertDialog.setMessage("This skill is not accepted!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }

                    else {


                    Tag tag = new Tag(textViewSkill.getText().toString(), Color.GRAY);

                    tagView.addTag(tag);
                        skillsList.remove(textViewSkill.getText().toString());
                    RequestQueue rq = Volley.newRequestQueue(getBaseContext());


                    url = LINK + "addUserSkill.php?idUser=" + idUser + "&skill=" + textViewSkill.getText().toString().replace(" ", "+++");
                    System.out.println(url);
                    textViewSkill.setText("");
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);

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

            }
        });


    }




    public void buttonConfirmClicked(View v)
    {
        Intent i= new Intent(getBaseContext(),AllActivity.class);
        startActivity(i);
    }
}