package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Fragment fragment0, fragment1, fragment2, fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment0 = new Fragment();
        fragment1 = new Fragment();
        fragment2 = new Fragment();
        fragment3 = new Fragment();

        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();

        TabLayout tabs = findViewById(R.id.tab);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                int position = tabs.getSelectedTabPosition();

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