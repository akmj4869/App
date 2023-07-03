package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Profile extends AppCompatActivity {
    String name, number;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        TextView nameText = (TextView) findViewById(R.id.name);
        TextView numberText = (TextView) findViewById(R.id.number);
        TextView close = (TextView) findViewById(R.id.close);
        ImageView call = (ImageView) findViewById(R.id.call);
        ImageView message = (ImageView) findViewById(R.id.message);
        ImageView edit = (ImageView) findViewById(R.id.edit);

        Intent intent = getIntent();
        Bundle strings = intent.getExtras();
        name = strings.getString("name");
        number = strings.getString("number");

        nameText.setText(name);
        numberText.setText(number);
        call.setOnClickListener(v -> {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getApplicationContext(), "Call failed ", Toast.LENGTH_SHORT).show();
            }
        });
        message.setOnClickListener(v -> {
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(getApplicationContext(), "Send failed ", Toast.LENGTH_SHORT).show();
            }
        });
        close.setOnClickListener(v -> Profile.super.onBackPressed());

    }
    private void makePhoneCall() {
        String callNumber = "tel:" + number;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(callNumber)));
        finish();
    }

    private void sendSMS() {
        String smsNumber = "smsto:" + number;
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , "01234");
        smsIntent.putExtra("sms_body"  , "Test ");

        try {
            startActivity(smsIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "SMS failed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Call failed ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
