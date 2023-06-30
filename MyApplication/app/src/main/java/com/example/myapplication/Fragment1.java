package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    private NumberDataAdapter numberDataAdapter;
    private ArrayList<NumberData> numberDatas;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        numberDatas = new ArrayList<>();
        numberDatas.add(new NumberData("윤현우", "010-7149-9924"));
        numberDatas.add(new NumberData("김철수", "010-0000-1111"));
        numberDatas.add(new NumberData("김이나", "010-1234-5678"));


        RecyclerView numberRecyclerView = view.findViewById(R.id.recyclerView);
        numberDataAdapter = new NumberDataAdapter(numberDatas);
        numberRecyclerView.setAdapter(numberDataAdapter);
        numberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}
