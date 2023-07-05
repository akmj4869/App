package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    int position;
    numberItem item;
    private ActivityResultLauncher<Intent> launcher;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    private static final int REQUEST_SMS = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);
        TextView nameText = findViewById(R.id.name);
        TextView numberText = findViewById(R.id.number);
        TextView close = findViewById(R.id.close);
        ImageView call = findViewById(R.id.call);
        ImageView message = findViewById(R.id.message);
        ImageView dismiss = findViewById(R.id.hide);
        CircleImageView profile = findViewById(R.id.profile);
        ImageButton gallery = findViewById(R.id.button);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        item = NumberDataAdapter.arrayList.get(position);

        nameText.setText(item.getName());
        numberText.setText(item.getNumber());
        if (item.getBitmap() != null) {
            profile.setImageBitmap(item.getBitmap());
        }
        call.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
        });
        message.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS);
        });
        dismiss.setOnClickListener(v -> {
            Delete(position);
            Profile.super.onBackPressed();
        });
        close.setOnClickListener(v -> onBackPressed());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Uri imageUri = data.getData();
                    numberItem item = NumberDataAdapter.arrayList.get(position);
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    profile.setImageBitmap(bitmap);
                    item.setBitmap(bitmap);
                    Toast.makeText(this, "Profile changed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        gallery.setOnClickListener(v -> {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            launcher.launch(galleryIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else{
            finish();
        }
    }

    private void Delete(int position) {
        Fragment1.listItems.remove(position);
    }

    private void makePhoneCall() {
        String callNumber = "tel:" + item.getNumber();
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(callNumber)));
        finish();
    }

    private void sendSMS() {
        String smsNumber = "smsto:" + item.getNumber();
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setDataAndType(Uri.parse(smsNumber), "vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , "0");
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
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(getApplicationContext(), "Call permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            } else {
                Toast.makeText(getApplicationContext(), "Send SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
