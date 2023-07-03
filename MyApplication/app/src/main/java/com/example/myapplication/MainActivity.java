package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Fragment fragment0, fragment1, fragment2, fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment0 = new Fragment0();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment0).commit();
        TabLayout tabLayout = findViewById(R.id.tab);
        tabLayout.getSelectedTabPosition();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tab.getPosition();

                Fragment selected = null;
                if (position == 0){
                    selected = fragment0;
                } else if (position == 1){
                    selected = fragment1;
                } else if (position == 2){
                    selected = fragment2;
                } else if (position == 3){
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

            }
        });
    }
}