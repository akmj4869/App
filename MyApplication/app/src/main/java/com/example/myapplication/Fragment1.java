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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    private NumberDataAdapter numberDataAdapter;
    private ArrayList<numberItem> numberItems;
    private RecyclerView numberRecyclerView;


    private String loadJsonFile() {
        String json = null;
        try {
            InputStream inputStream = getActivity().getAssets().open("jsons/example.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        ArrayList<numberItem> listItems = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJsonFile());
            JSONArray jsonArray = jsonObject.getJSONArray("numbers");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject obj;
                obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                String number = obj.getString("number");
                numberItem listItem;

                listItem = new numberItem(name, number);
                listItems.add(listItem);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        numberRecyclerView = view.findViewById(R.id.recyclerView);
        numberDataAdapter = new NumberDataAdapter(listItems);
        numberRecyclerView.setLayoutManager((RecyclerView.LayoutManager) new LinearLayoutManager(getActivity()));
        numberRecyclerView.setAdapter(numberDataAdapter);

        return view;
    }
}
