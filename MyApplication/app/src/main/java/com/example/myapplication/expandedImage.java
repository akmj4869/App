package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class expandedImage extends AppCompatActivity {
    ArrayList<String> paths = new ArrayList<>();
    ViewPager viewPager;
    GestureDetector gestureDetector;

    GalleryAdapter galleryAdapter;
    String filepath;
    ImageButton delete;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepage);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        filepath = intent.getStringExtra("filepath");
        position = intent.getIntExtra("position", 0);

        File imgfile = new File(filepath);
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(imgfile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        try{
            String line;
            while((line = reader.readLine()) != null){
                paths.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        galleryAdapter = new GalleryAdapter(this, paths);
        viewPager.setAdapter(galleryAdapter);
        viewPager.setCurrentItem(position);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        viewPager.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        delete.setOnClickListener(v -> {
            paths.remove(position);
            galleryAdapter.notifyDataSetChanged();
            viewPager.setAdapter(galleryAdapter);
            viewPager.setCurrentItem(position+ 1, true);
            Fragment2.imageDataAdapter.removeItem(position);
            Fragment2.imageDataAdapter.notifyDataSetChanged();
        });
        delete.bringToFront();
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                float slope = (e1.getY() - e2.getY()) / (e1.getX() - e2.getX());
                float angle = (float) Math.atan(slope);
                float angleInDegree = (float) Math.toDegrees(angle);
                // left to right
                if (e2.getY() - e1.getY() > 10 && Math.abs(velocityY) > 10) {
                    if ((angleInDegree > 45 && angleInDegree < 135)) {
                        expandedImage.super.onBackPressed();
                        overridePendingTransition(
                                R.anim.none, R.anim.fadeout);
                        finish();
                    }
                }
                return true;
            } catch (Exception ignored) {
            }
            return false;
        }
    }
}
