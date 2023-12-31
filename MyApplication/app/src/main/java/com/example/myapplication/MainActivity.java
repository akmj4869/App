package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    Fragment fragment1, fragment2, fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new CalendarFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment1).commit();
        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.getSelectedTabPosition();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();


                    Fragment selected = null;
                    if (position == 0){
                        selected = fragment1;
                    } else if (position == 1){
                        selected = fragment2;
                    } else if (position == 2){
                        selected = fragment3;
                    }
                    assert selected != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();


                    Fragment selected = null;
                if (position == 0){
                    selected = fragment1;
                } else if (position == 1){
                    selected = fragment2;
                } else if (position == 2){
                    selected = fragment3;
                }
                    assert selected != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                }


        });

    }
}