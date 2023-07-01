package com.example.myapplication;

import android.content.Context;
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

    public NumberDataAdapter(ArrayList<numberItem> arrayList){
        this.arrayList = arrayList;
    }

    public NumberDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        Context context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        numberItem item = arrayList.get(position);
        holder.name.setText(item.getName());
        holder.number.setText(item.getNumber());
        holder.image.setImageResource(R.drawable.phone);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            private boolean isClicked = false;
            @Override
            public void onClick(View v) {
                isClicked = !isClicked;
                if (isClicked) {
                    holder.number.setVisibility(View.VISIBLE);
                } else{
                    holder.number.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView number;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            image = itemView.findViewById(R.id.phone);
        }
    }
}
