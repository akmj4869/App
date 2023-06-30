package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NumberDataAdapter extends RecyclerView.Adapter<NumberDataAdapter.NumberDataViewHolder> {

    private List<NumberData> datas;

    public NumberDataAdapter(List<NumberData> datas){
        this.datas = datas;
    }
    @NonNull
    @Override
    public NumberDataAdapter.NumberDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberDataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.number_data, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NumberDataAdapter.NumberDataViewHolder holder, int position) {
        NumberData data = datas.get(position);
        holder.name.setText(data.getName());
        holder.number.setText(data.getNumber());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class NumberDataViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView number;
        public NumberDataViewHolder(@NonNull View itemView) {
            super(itemView);
            // 아이템 뷰에 필요한 View
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
        }
    }
}
