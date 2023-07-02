package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView numberText = (TextView) findViewById(R.id.number);
        TextView close = (TextView) findViewById(R.id.close);

        Intent intent = getIntent();
        Bundle strings = intent.getExtras();
        String name = strings.getString("name");
        String number = strings.getString("number");

        nameText.setText(name);
        numberText.setText(number);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile.super.onBackPressed();
            }
        });

    }
}
