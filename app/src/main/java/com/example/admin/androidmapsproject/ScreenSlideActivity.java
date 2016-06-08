package com.example.admin.androidmapsproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ScreenSlideActivity extends FragmentActivity implements AboutFragment.OnFragmentInteractionListener,
        MapsFragment.OnFragmentInteractionListener,
        MyRoutes.OnFragmentInteractionListener,
        MyFriendsFragment.OnFragmentInteractionListener{
    //Variables used for the class
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  static RouteApi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = new RouteApi();
        try {
            ArrayList<Route> a = api.GetAll();
            //Log.d("array", a.get(1).getRouteName());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        //Toggle all the widgets used
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CustomAdapter adapterTabs = new CustomAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterTabs);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
        //Add tab with viewpager

        tabLayout.setupWithViewPager(viewPager);
        //Override the on tab selected actions
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
        //adapterTabs.setTabTitlesToIcons();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screen_slide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //The selected item is logOUt
        //You must log out to see the listView with the updated items
        if (id == R.id.logOut) {
            //Back up to the last activity
            onBackPressed();
            //End Activity
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //Communication between fragments
    }

    //The adapter for the pager
    //You must log out to see the listView with the updated items
    public class CustomAdapter extends FragmentPagerAdapter {
        String titles[] = getResources().getStringArray(R.array.titles);
        private Map<Integer, String> mFragmentTags;//Used as intent to try to fix the updateLisView problem on ViewPagers

        public CustomAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
            mFragmentTags = new HashMap<>();
        }

        //Selec te action on each tab selection
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    /*Intent intent = new Intent(ScreenSlideActivity.this,MapsActivity.class);
                    startActivity(intent);*/
                    return new AboutFragment();
                    //FragmentAbout
                case 1:
                    return new MapsFragment();
                    //Fragment List
                case 2:
                    return new MyRoutes();
                    //Fragment Manage
                case 3:
                    return new MyFriendsFragment();
                //Fragment Manage
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if (obj instanceof Fragment) {
                Fragment f = (Fragment) obj;
                String tag = f.getTag();
                mFragmentTags.put(position, tag);
            }
            return obj;
        }



    }
}
