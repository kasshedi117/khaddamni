package com.kass.khaddamni.khaddamni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.adapter.SettingsAdapter;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class ChangePasswordActivity extends AppCompatActivity {

    // Check if no view has focus:
    String url = LINK+"editPassword.php";
    EditText oldPassword;
    EditText newPassword;
    EditText confirmNewPassword;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword=(EditText) findViewById(R.id.textEditoldpassword);
        newPassword=(EditText) findViewById(R.id.textEditnewpassword);
        confirmNewPassword=(EditText) findViewById(R.id.textEditConfirmNewPassword);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    public void buttonConfirm(View v){

        RequestQueue rq = Volley.newRequestQueue(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String login = preferences.getString("login", "");
        String pass = preferences.getString("password", "");
        editor = preferences.edit();
        System.out.println(pass);
        if(pass.equals(oldPassword.getText().toString()) && newPassword.getText().toString().equals(confirmNewPassword.getText().toString()))
        {

            url = url + "?login="+ login + "&oldpassword=" + oldPassword.getText().toString()
                    + "&newpassword=" + newPassword.getText().toString();

            System.out.println(url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    editor.putString("password",newPassword.getText().toString());
                    editor.apply();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );

            rq.add(stringRequest);
            rq.start();


            Intent SettingsIntent = new Intent(this, AllActivity.class);
            startActivity(SettingsIntent);
        }
        else if(!pass.equals(oldPassword.getText().toString()))
        {
            oldPassword.setError("wrong password");
        }
        else
        {
            confirmNewPassword.setError("write the same password");
        }
    }

    public void buttonBack(View v){

        Intent SettingsIntent = new Intent(this, AllActivity.class);
        startActivity(SettingsIntent);
    }
}
