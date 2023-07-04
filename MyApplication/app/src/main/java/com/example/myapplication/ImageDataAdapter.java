package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {
    private final ArrayList<String> imageName;
    private Context context;
    private String imagePath;

    public ImageDataAdapter(ArrayList<String> imageName) {
        this.imageName = imageName;
    }

    @NonNull
    @Override
    public ImageDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);
        context = parent.getContext();
        String path = context.getExternalFilesDir(null).toString();
        imagePath = path + File.separator + "images";
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDataAdapter.ViewHolder holder, int position) {
        int current = position;
        String fileName = imageName.get(position);
        File imgFile = new File(imagePath, fileName);
        if (imgFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile((imgFile.getAbsolutePath()));
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, expandedImage.class);
            intent.putExtra("position", current);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imageName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}