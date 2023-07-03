package com.example.myapplication;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.os.Environment.DIRECTORY_PICTURES;

public class Camera extends AppCompatActivity {

    private int REQUEST_CAMERA = 1;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private Bitmap bitmap;
    private ImageButton capture, save, trashbin;
    private ImageView image;
    private EditText filename;
    private Intent data = null;
    String currentDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerapage);

        capture = findViewById(R.id.capture);
        save = findViewById(R.id.save);
        image = findViewById(R.id.image);
        trashbin = findViewById(R.id.trashbin);
        filename = findViewById(R.id.filename);

        currentDir = getFilesDir().toString();
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        data = result.getData();
                        bitmap = (Bitmap) data.getExtras().get("data");
                        if (bitmap == null){
                            Toast.makeText(this, "No Image", Toast.LENGTH_SHORT).show();
                        } else {
                            image.setImageBitmap(bitmap);
                        }

                    }
                });
        capture.setOnClickListener(v -> captureImage());
        save.setOnClickListener(v -> {
            try {
                saveImage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        trashbin.setOnClickListener(v -> throwImage());

    }

    private void captureImage(){
        if (ContextCompat.checkSelfPermission(this, permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
        }
    }

    private void saveImage() throws IOException {
        String f = "";
        String filenames = "";
        if (bitmap == null){
            Toast.makeText(this, "No Image", Toast.LENGTH_SHORT).show();
            return;
        }
        File storageDir = new File(currentDir + "/imagefiles/");
        if (!storageDir.exists()) storageDir.mkdirs();
        if (!filename.getText().toString().isEmpty()) {
            String imagepath = currentDir + "/imagefiles/filetexts.txt";
            f = filename.getText() + ".jpg";
            File imgfile = new File(imagepath);
            if (!imgfile.exists()) {
                imgfile.createNewFile();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(imgfile, true);
                filenames = f + "\n";
                fileOutputStream.write(filenames.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No filename", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            File imageDir = new File(currentDir + "/images");
            if (!storageDir.exists())
                storageDir.mkdirs();
            FileOutputStream output = null;
            try {
                File imagefile = new File(imageDir, f);
                output = new FileOutputStream(imagefile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                output.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert (output != null);
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Fragment2.imageDataAdapter.addItem(filenames);
            Log.e(TAG, "Capture Saved");
            Toast.makeText(this, "Capture Saved ", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.w(TAG, "Capture Saving Error!", e);
            Toast.makeText(this, "Save failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void throwImage(){
        image.setImageBitmap(null);
        bitmap = null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
    }
}
