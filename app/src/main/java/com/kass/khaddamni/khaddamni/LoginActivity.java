package com.kass.khaddamni.khaddamni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kass.khaddamni.khaddamni.widget.LoadingButton;

import static com.kass.khaddamni.khaddamni.Global.Global.LINK;

public class LoginActivity extends AppCompatActivity {


    private TextView mEditUserName,mEditPassword;
    private LoadingButton mLoadingBtn;
    String verifLogin="internet";
    String url = LINK+"verifLogin.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
       final RequestQueue rq = Volley.newRequestQueue(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String login = preferences.getString("login", "");
        if(!login.equals(""))
        {
            startActivity(new Intent(LoginActivity.this, AllActivity.class));
        }


        mLoadingBtn = (LoadingButton)findViewById(R.id.loading_btn);
        //when login failed, reset view to button with animation
        mLoadingBtn.setResetAfterFailed(true);
        mLoadingBtn.setAnimationEndListener(new LoadingButton.AnimationEndListener() {
            @Override
            public void onAnimationEnd(LoadingButton.AnimationType animationType) {
                if(animationType == LoadingButton.AnimationType.SUCCESSFUL){
                    startActivity(new Intent(LoginActivity.this, AllActivity.class));
                }
            }
        });

        mEditUserName = (TextView) findViewById(R.id.textViewEmail);
        mEditPassword = (TextView) findViewById(R.id.textViewPassword);


        mLoadingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                       url=url+"?userName="+mEditUserName.getText()+"&password="+mEditPassword.getText();
                System.out.println("omaromar        qsd");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        try {
                            
                             verifLogin = response;



                        } catch (NullPointerException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                         System.out.println("erreur");
                    }
                }
                );

                rq.add(stringRequest);
                rq.start();

                checkLogin();
            }
        });
    }

    private void checkLogin(){

        final String userName = mEditUserName.getText().toString();
        if(userName.length() == 0){
            mEditUserName.setError("please input user name");
            return;
        }

        final String password = mEditPassword.getText().toString();
        if(password.length() == 0){
            mEditPassword.setError("please input password");
            return;
        }

        mLoadingBtn.startLoading();
        //send login request

        //demo
        mEditUserName.setEnabled(false);
        mEditPassword.setEnabled(false);
        mLoadingBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                mEditUserName.setEnabled(true);
                mEditPassword.setEnabled(true);
                System.out.println("a"+verifLogin+"a");
                if(!verifLogin.equals("false")){
                    if(verifLogin.equals("internet"))
                    {
                        mLoadingBtn.loadingFailed();
                        Toast.makeText(getApplicationContext(),"No internet",Toast.LENGTH_SHORT).show();

                    }

             else   {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("login", mEditUserName.getText().toString());
                        editor.putString("password", mEditPassword.getText().toString());
                        editor.putString("id",verifLogin);
                        editor.apply();
                        //login success
                        mLoadingBtn.loadingSuccessful();
                    }
                 }
                else{
                    mLoadingBtn.loadingFailed();
                    Toast.makeText(getApplicationContext(),"login failed,please check username and password",Toast.LENGTH_SHORT).show();
                }
            }
        },3000);
    }

    public void buttonLoginClicked(View v)
    {
        Intent LoginIntent = new Intent(this,AllActivity.class );
        startActivity(LoginIntent);
    }
    public void buttonRegisterClicked(View v){
        Intent RegisterIntent = new Intent(this, RegisterActivity.class);
        startActivity(RegisterIntent);
    }
}
