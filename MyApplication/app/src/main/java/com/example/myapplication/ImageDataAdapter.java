package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {
    private ArrayList<String> imageName;
    private Context context;
    private String imagePath, imageFile;
    public ImageDataAdapter(ArrayList<String> imageName) {
        this.imageName = imageName;
    }

    @NonNull
    @Override
    public ImageDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);
        context = parent.getContext();
        imagePath = context.getFilesDir().toString() + "/images";
        imageFile = context.getFilesDir().toString() + "/imagefiles/filetexts.txt";
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDataAdapter.ViewHolder holder, int position) {
        int current = position;
        String fileName = imageName.get(position);
        String filePath = imagePath + "/" + fileName;
        File imgFile = new File(filePath);
        if (imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile((imgFile.getAbsolutePath()));
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, expandedImage.class);
                intent.putExtra("filepath", imageFile);
                intent.putExtra("position", current);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageName.size();
    }

    public void addItem(String s) {
        imageName.add(s);
    }

    public void removeItem(int position) {
        imageName.remove(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
