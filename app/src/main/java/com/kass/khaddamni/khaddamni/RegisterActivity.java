package com.kass.khaddamni.khaddamni;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_login;
    EditText edit_first_name;
    EditText edit_last_name;
    EditText edit_email;
    EditText edit_password;
    EditText edit_passwordConfirm;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText editTextDate;
    EditText editTextNumber;
    String url = LINK+"insertMember.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_login= (EditText) findViewById(R.id.edit_login);
        edit_first_name= (EditText) findViewById(R.id.edit_first_name);
        edit_last_name= (EditText) findViewById(R.id.edit_last_name);
        edit_email= (EditText) findViewById(R.id.edit_email);
        edit_password= (EditText) findViewById(R.id.edit_password);
        edit_passwordConfirm= (EditText) findViewById(R.id.edit_passwordConfirm);
        editTextDate= (EditText) findViewById(R.id.editTextDate);
        editTextNumber=(EditText) findViewById(R.id.editTextNumber);


        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
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
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void buttonRegisterClicked(View v){

        int checkDate=0;
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(editTextDate.getText().toString());
        } catch (Exception ex) {
            checkDate=1;
        }

        if(edit_login.getText().toString().equals("") || edit_first_name.getText().toString().equals("") ||
                edit_last_name.getText().toString().equals("") || edit_email.getText().toString().equals("") ||
                edit_password.getText().toString().equals("") || edit_passwordConfirm.getText().toString().equals("") || editTextDate.getText().toString().equals("")
                || editTextNumber.getText().toString().equals(""))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
            alertDialog.setTitle("Register");
            alertDialog.setMessage("One of the fields is empty! please fill all the fields");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();edit_login.setText("");
                        }
                    });
            alertDialog.show();
        }
        else if(checkDate == 1)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
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
        else if(! edit_password.getText().toString().equals(edit_passwordConfirm.getText().toString()) )
        {
            System.out.println(edit_password.getText().toString()+"    "+edit_passwordConfirm.getText().toString());
            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
            alertDialog.setTitle("Wrong Password");
            alertDialog.setMessage("Please type the same password 2 times!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
        }
        else {


            RequestQueue rq = Volley.newRequestQueue(this);

            url = LINK+"insertMember.php" + "?userName=" + edit_login.getText().toString().replace(" ", "+++") + "&firstName=" + edit_first_name.getText().toString().replace(" ", "+++")
                    + "&lastName=" + edit_last_name.getText().toString().replace(" ", "+++") + "&email=" + edit_email.getText().toString().replace(" ", "+++") + "&password=" + edit_password.getText().toString().replace(" ", "+++")+"&birthDate="+editTextDate.getText().toString()+"&number="+editTextNumber.getText().toString().replace(" ", "+++");

            System.out.println(url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println(response);

                    if (response.equals("false")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                        alertDialog.setTitle("New offer");
                        alertDialog.setMessage("Username already used! please try another one!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Okay",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        edit_login.setText("");
                                    }
                                });
                        alertDialog.show();
                    }

                    else {
                        Intent homeIntent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(homeIntent);
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


}
