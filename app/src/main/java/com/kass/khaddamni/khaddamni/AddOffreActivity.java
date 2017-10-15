package com.kass.khaddamni.khaddamni;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class AddOffreActivity extends AppCompatActivity {


    EditText edit_titre;
    EditText edit_description;
    EditText edit_place;
    EditText editTextDate;
    DatePicker edit_date;
    Button btn_addOffre;
  int id=23;
    Date date1;
    Calendar myCalendar;
    SimpleDateFormat simpleDate;
    DatePickerDialog.OnDateSetListener date;

    String url = LINK+"insertOffer.php";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offre);

        btn_addOffre = (Button) findViewById(R.id.btn_addOffre);
        edit_titre = (EditText) findViewById(R.id.edit_titre);
        edit_description = (EditText) findViewById(R.id.edit_description);
        edit_place = (EditText) findViewById(R.id.edit_place);


        editTextDate= (EditText) findViewById(R.id.editTextDate);



        ////////////////////////////////////////////////////Date //////////////////////////////////////



       myCalendar = Calendar.getInstance();

       date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                editTextDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        editTextDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddOffreActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


        ///////////////////////////////////////////////////////////////////////////////////////////////////









        btn_addOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkDate=0;
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDate.getText().toString());
                } catch (Exception ex) {
                  checkDate=1;
                }

                if(edit_titre.getText().toString().equals("") || edit_description.getText().toString().equals("") ||
                        edit_place.getText().toString().equals("") || editTextDate.getText().toString().equals("") )
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(AddOffreActivity.this).create();
                    alertDialog.setTitle("New offer");
                    alertDialog.setMessage("One of the fields is empty! please fill all the fields");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else if(checkDate == 1)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(AddOffreActivity.this).create();
                    alertDialog.setTitle("Wrong Date format");
                    alertDialog.setMessage("please write a date on this format: dd/mm/yyyy or double click on text field to choose date from calendar!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String idUser = preferences.getString("id", "");
                    RequestQueue rq = Volley.newRequestQueue(getBaseContext());

                    url = url + "?title=" + edit_titre.getText().toString().replace(" ", "+++") + "&description=" + edit_description.getText().toString().replace(" ", "+++")
                            + "&place=" + edit_place.getText().toString().replace(" ", "+++") + "&date=" + editTextDate.getText().toString() + "&idUser=" + idUser;

                    System.out.println(url);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            System.out.println(url);
                            System.out.println("omaromaromaromar" + response);
                            if (response.equals("existant")) {
                                edit_titre.setError("titre existant");

                                AlertDialog alertDialog = new AlertDialog.Builder(AddOffreActivity.this).create();
                                alertDialog.setTitle("New offer");
                                alertDialog.setMessage("this title already exists, please try another one!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            } else {
                                id = Integer.parseInt(response);
                                Intent i = new Intent(getBaseContext(), AddSkillsActivity.class);

                                i.putExtra("id", id);
                                startActivity(i);

                            }
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

                }

            }

        });

    }





}



