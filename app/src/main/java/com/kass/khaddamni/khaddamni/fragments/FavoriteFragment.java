package com.kass.khaddamni.khaddamni.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.kass.khaddamni.khaddamni.R;
import static com.kass.khaddamni.khaddamni.R.drawable.about;
import static com.kass.khaddamni.khaddamni.R.drawable.ic_launcher;


public class FavoriteFragment extends Fragment {


    View view1;

    MaterialViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        view1 = inflater.inflate(R.layout.fragment_favorite,null);

        mViewPager = (MaterialViewPager) view1.findViewById(R.id.materialViewPager);

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 5) {
                    case 0:

                        return FavouriteOffersFragment.newInstance();
                    case 1:
                        return AppliedForFragment.newInstance();
                    case 2:
                        return YourOffersFragment.newInstance();
                    default:
                        return FavouriteOffersFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return "Favourite";
                    case 1:
                        return "You applied for";
                    case 2:
                        return "Your offers";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.green,
                                ResourcesCompat.getDrawable(getResources(), ic_launcher, null));
                    case 1:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.blue,
                                ResourcesCompat.getDrawable(getResources(), ic_launcher, null));
                    case 2:
                        return HeaderDesign.fromColorResAndDrawable(
                                R.color.cyan,
                                ResourcesCompat.getDrawable(getResources(), ic_launcher, null));
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());


        return view1;
    }







}
