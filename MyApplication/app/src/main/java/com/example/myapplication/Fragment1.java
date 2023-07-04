package com.example.myapplication;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Fragment1 extends Fragment {

    static ArrayList<numberItem> listItems = new ArrayList<>();
    static NumberDataAdapter numberDataAdapter;
    static RecyclerView numberRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String loadJsonFile() {
        String json;
        try {
            InputStream inputStream = getActivity().getAssets().open("jsons/example.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        ImageButton plusButton = view.findViewById(R.id.plus);
        LinearLayout set = view.findViewById(R.id.set);
        set.setVisibility(View.GONE);
        EditText nameEdit = view.findViewById(R.id.name);
        EditText numberEdit = view.findViewById(R.id.number);
        Button add = view.findViewById(R.id.add);

        try {
            JSONObject jsonObject = new JSONObject(loadJsonFile());
            JSONArray jsonArray = jsonObject.getJSONArray("numbers");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject obj;
                obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                String number = obj.getString("number");
                listItems.add(new numberItem(name, number));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        numberRecyclerView = view.findViewById(R.id.recyclerView);
        numberDataAdapter = new NumberDataAdapter(listItems);
        layoutManager = new LinearLayoutManager(getActivity());
        numberRecyclerView.setLayoutManager(layoutManager);
        numberRecyclerView.setAdapter(numberDataAdapter);
        DividerItemDecoration dividerItemDecorator = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        numberRecyclerView.addItemDecoration(dividerItemDecorator);
        plusButton.setOnClickListener(v -> set.setVisibility(View.VISIBLE));
        add.setOnClickListener(v -> {
            String n = nameEdit.getText().toString().trim();
            String m = numberEdit.getText().toString().trim();
            listItems.add(new numberItem(n, m));
            set.setVisibility(View.GONE);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        numberDataAdapter.notifyDataSetChanged();
    }

}