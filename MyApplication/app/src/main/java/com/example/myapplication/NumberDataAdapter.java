package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NumberDataAdapter extends RecyclerView.Adapter<NumberDataAdapter.NumberItemViewHolder> {

    private List<numberItem> datas;

    public NumberDataAdapter(List<numberItem> datas){
        this.datas = datas;
    }
    @NonNull
    @Override
    public NumberDataAdapter.NumberItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NumberDataAdapter.NumberItemViewHolder holder, int position) {
        numberItem data = datas.get(position);
        holder.name.setText(data.getName());
        holder.number.setText(data.getNumber());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class NumberItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView number;
        public NumberItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // 아이템 뷰에 필요한 View
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
        }
    }
}
