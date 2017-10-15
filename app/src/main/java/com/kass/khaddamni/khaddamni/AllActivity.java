package com.kass.khaddamni.khaddamni;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kass.khaddamni.khaddamni.fragments.AppliedForFragment;
import com.kass.khaddamni.khaddamni.fragments.FavoriteFragment;
import com.kass.khaddamni.khaddamni.fragments.HomeFragment;
import com.kass.khaddamni.khaddamni.fragments.ProfileFragment;
import com.kass.khaddamni.khaddamni.fragments.SettingsFragment;
import com.kass.khaddamni.khaddamni.fragments.YourOffersFragment;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;

public class AllActivity extends FragmentActivity{

    Button buttonHome , buttonFavorite , buttonProfile , buttonSettings;

    Controller controller;
    int[] testColors = {0xFF00796B,0xFF5B4947,0xFF00796B,0xFF5B4947,0xFFF57C00};
    List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
//        ActionBar actionBar = this.getActionBar();
//        actionBar.hide();

        /*
        buttonHome  = (Button) findViewById(R.id.buttonHome);
        buttonFavorite  = (Button) findViewById(R.id.buttonFavorite);
        buttonProfile  = (Button) findViewById(R.id.buttonProfile);
        buttonSettings  = (Button) findViewById(R.id.buttonSettings);
        */

        initFragment();


        if (findViewById(R.id.contentFragment) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }


            // Create a new Fragment to be placed in the activity layout
            HomeFragment firstFragment = new HomeFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFragment, firstFragment).commit();
        /*
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new HomeFragment()).commit();
         */
        }

        setContentView(R.layout.activity_all);
        BottomTabTest();
    }



    private void initFragment()
    {

    }

    private void BottomTabTest()
    {
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.tab);

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(android.R.drawable.ic_menu_search)
                .setText("Home")
                .setSelectedColor(testColors[0])
                .setTag("A")
                .build();

        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(android.R.drawable.ic_menu_send, "Favourite",testColors[1])
                .addTabItem(android.R.drawable.ic_menu_compass, "Profile",testColors[2])
                .addTabItem(android.R.drawable.ic_menu_help, "Settings",testColors[3])
//                .setMode(TabLayoutMode.HIDE_TEXT)
//                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("A",2);
//       controller.setDisplayOval(0,true);

        controller.addTabItemClickListener(listener);
    }



    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag)
        {
            if(index==0)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new HomeFragment()).commit();
            }
            if(index==1)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new FavoriteFragment()).commit();
            }
            else if(index==2)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new ProfileFragment()).commit();
            }
            else if(index==3)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new SettingsFragment()).commit();
            }
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
            finish();
            startActivity(getIntent());
        }
    };
/*

    public void buttonHomeClicked(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new HomeFragment()).commit();
        buttonHome.setBackground(getResources().getDrawable( R.drawable.login_btn_pressed ));
        buttonFavorite.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonProfile.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonSettings.setBackground(getResources().getDrawable( R.drawable.login_btn ));
    }

    public void buttonFavouriteClicked(View v){
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new FavoriteFragment()).commit();
        buttonHome.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonFavorite.setBackground(getResources().getDrawable( R.drawable.login_btn_pressed ));
        buttonProfile.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonSettings.setBackground(getResources().getDrawable( R.drawable.login_btn ));
    }

    public void buttonProfileClicked(View v)
    {
         getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new ProfileFragment()).commit();
        buttonHome.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonFavorite.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonProfile.setBackground(getResources().getDrawable( R.drawable.login_btn_pressed ));
        buttonSettings.setBackground(getResources().getDrawable( R.drawable.login_btn ));
    }

    public void buttonSettingsClicked(View v)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment,new SettingsFragment()).commit();
        buttonHome.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonFavorite.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonProfile.setBackground(getResources().getDrawable( R.drawable.login_btn ));
        buttonSettings.setBackground(getResources().getDrawable( R.drawable.login_btn_pressed ));
    }*/
}
