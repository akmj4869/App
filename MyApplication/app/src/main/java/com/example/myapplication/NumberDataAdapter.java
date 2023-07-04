package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NumberDataAdapter extends RecyclerView.Adapter<NumberDataAdapter.ViewHolder> {
    static ArrayList<numberItem> arrayList;
    private Context context;

    public NumberDataAdapter(ArrayList<numberItem> arrayList){
        NumberDataAdapter.arrayList = arrayList;
    }
    @NonNull
    public NumberDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int current = position;
        numberItem item = arrayList.get(position);
        String name = item.getName();
        holder.name.setText(name);
        if (item.bitmap == null){
            holder.image.setImageResource(R.drawable.phone);
        } else{
            holder.image.setImageBitmap(item.bitmap);
        }
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Profile.class);
            intent.putExtra("position", current);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.profile);
        }
    }
}
