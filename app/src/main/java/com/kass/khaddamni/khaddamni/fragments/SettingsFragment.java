package com.kass.khaddamni.khaddamni.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kass.khaddamni.khaddamni.AllActivity;
import com.kass.khaddamni.khaddamni.ChangePasswordActivity;
import com.kass.khaddamni.khaddamni.LoginActivity;
import com.kass.khaddamni.khaddamni.R;
import com.kass.khaddamni.khaddamni.adapter.SettingsAdapter;


public class SettingsFragment extends Fragment {

    ListView listSettings;
    SettingsAdapter adapterSettings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);




        listSettings=(ListView) v.findViewById(R.id.listViewSettings) ;

        String texte[]= {"Change password","About","LogOut"};
        Integer image[]= {R.drawable.unnamed,R.drawable.about,R.drawable.logout};

        listSettings.setAdapter(new SettingsAdapter(getActivity(), texte, image));


        listSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
                if(pos==0)
                {
                    Intent PasswordIntent = new Intent(getContext(), ChangePasswordActivity.class);
                    startActivity(PasswordIntent);
                }
                else if(pos==1)
                {

                }
                else
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    String login = preferences.getString("login", "");
                    if(!login.equals(""))
                    {
                        startActivity(new Intent(getContext(), AllActivity.class));
                        editor.putString("login","");
                        editor.putString("password","");
                        editor.apply();
                    }
                    Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }
            }
        });








        return v;
    }




}
