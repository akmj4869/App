package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NumberDataAdapter extends RecyclerView.Adapter<NumberDataAdapter.ViewHolder> {
    private static ArrayList<numberItem> arrayList;
    private Context context;

    public NumberDataAdapter(ArrayList<numberItem> arrayList){
        this.arrayList = arrayList;
    }
    public NumberDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        numberItem item = arrayList.get(position);
        String name = item.getName();
        String number = item.getNumber();
        holder.name.setText(name);
        holder.image.setImageResource(R.drawable.phone);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Profile.class);
            Bundle strings = new Bundle();
            strings.putString("name", name);
            strings.putString("number", number);
            intent.putExtras(strings);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.phone);
        }
    }
}
