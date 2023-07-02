package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

public class expandedImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String imageData = intent.getStringExtra("file");

        setContentView(R.layout.expanded_image);
        ImageView image = findViewById(R.id.expanded_image);
        image.bringToFront();

        InputStream inputStream1 = null;
        try {
            inputStream1 = getAssets().open(imageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Drawable d1 = Drawable.createFromStream(inputStream1, null);
        image.setImageDrawable(d1);

        Button button = findViewById(R.id.close);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                expandedImage.super.onBackPressed();
            }
        });
    }
}
