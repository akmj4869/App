package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class expandedImage extends AppCompatActivity {
    ViewPager viewPager;
    GestureDetector gestureDetector;

    int[] images = {R.drawable.sample, R.drawable.sample2, R.drawable.sample3, R.drawable.sample4, R.drawable.sample5,
            R.drawable.sample6, R.drawable.sample7, R.drawable.sample8, R.drawable.sample9, R.drawable.sample10};

    GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepage);

        Intent intent = getIntent();
        int position = intent.getIntExtra("file", 0);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        galleryAdapter = new GalleryAdapter(this, images);
        viewPager.setAdapter(galleryAdapter);
        viewPager.setCurrentItem(position);

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        viewPager.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
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
                                R.anim.fadein, R.anim.none);
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
