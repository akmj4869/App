package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

public class expandedImage extends AppCompatActivity {

    private ArrayList<String> paths = new ArrayList<>();
    GestureDetector gestureDetector;
    GalleryAdapter galleryAdapter;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepage);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ImageButton delete = findViewById(R.id.delete);
        TextView imagename = findViewById(R.id.imagename);
        Intent intent = getIntent();
        String filepath = intent.getStringExtra("filepath");
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
        imagename.setText(paths.get(position).replace(".jpg", ""));
        viewPager.setAdapter(galleryAdapter);
        viewPager.setCurrentItem(position);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        viewPager.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        delete.setOnClickListener(v -> {
            if (paths.isEmpty()) {
                Toast.makeText(this, "no more images!", Toast.LENGTH_SHORT).show();
                return;
            }
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < 0 || currentItem >= paths.size()){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                return;
            }
            paths.remove(currentItem);
            Fragment2.imageDataAdapter.removeItem(currentItem);
            galleryAdapter.notifyDataSetChanged();
            viewPager.setAdapter(galleryAdapter);
            if (paths.size() == 0){
                viewPager.setAdapter(null);
                imagename.setText("");
            }
            else if (currentItem == paths.size()){
                viewPager.setCurrentItem(currentItem - 1, true);
                imagename.setText(paths.get(currentItem - 1).replace(".jpg", ""));
                Fragment2.imageDataAdapter.removeItem(currentItem);
            } else {
                viewPager.setCurrentItem(currentItem, true);
                imagename.setText(paths.get(currentItem).replace(".jpg", ""));
                Fragment2.imageDataAdapter.removeItem(currentItem);
            }
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
