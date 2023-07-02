package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ViewHolder> {
    private final ArrayList<String> imagePath;
    private Context context;

    public ImageDataAdapter(ArrayList<String> imagePath) {
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public ImageDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageDataAdapter.ViewHolder holder, int position) {
        String fileName = imagePath.get(position);
        int current = position;
        try{
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    context.getResources().getIdentifier(fileName, "drawable", context.getPackageName()));
            holder.imageView.setImageBitmap(bitmap);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, expandedImage.class);
                    intent.putExtra("file", current);
                    context.startActivity(intent);
                }
            });
        } catch(Exception v){
            return;
        }
    }

    @Override
    public int getItemCount() {
        return imagePath.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
