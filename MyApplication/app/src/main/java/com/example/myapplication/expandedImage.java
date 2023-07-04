package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
public class expandedImage extends AppCompatActivity {

    GestureDetector gestureDetector;
    GalleryAdapter galleryAdapter;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagepage);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageButton delete = findViewById(R.id.delete);
        TextView imagename = findViewById(R.id.imagename);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        galleryAdapter = new GalleryAdapter(this, Fragment2.listItems);
        imagename.setText(Fragment2.listItems.get(position).replace(".jpg", ""));
        viewPager.setAdapter(galleryAdapter);
        viewPager.setCurrentItem(position);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        viewPager.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
        delete.setOnClickListener(v -> {
            if (Fragment2.listItems.isEmpty()) {
                Toast.makeText(this, "no more images!", Toast.LENGTH_SHORT).show();
                return;
            }
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < 0 || currentItem >= Fragment2.listItems.size()){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                return;
            }
            Fragment2.listItems.remove(currentItem);
            galleryAdapter.notifyDataSetChanged();
            viewPager.setAdapter(galleryAdapter);
            if (Fragment2.listItems.size() == 0){
                viewPager.setAdapter(null);
                imagename.setText("");
            }
            else if (currentItem == Fragment2.listItems.size()){
                viewPager.setCurrentItem(currentItem - 1, true);
                imagename.setText(Fragment2.listItems.get(currentItem - 1).replace(".jpg", ""));
            } else {
                viewPager.setCurrentItem(currentItem, true);
                imagename.setText(Fragment2.listItems.get(currentItem).replace(".jpg", ""));
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
                if (e2.getY() - e1.getY() > 5 && Math.abs(velocityY) > 5) {
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
