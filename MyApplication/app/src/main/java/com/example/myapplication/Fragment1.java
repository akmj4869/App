package com.example.myapplication;

import static android.content.Context.INPUT_METHOD_SERVICE;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fragment1 extends Fragment {

    static ArrayList<numberItem> listItems = new ArrayList<>();
    NumberDataAdapter numberDataAdapter;
    static RecyclerView numberRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String loadJsonFile() {
        String json;
        try {
            InputStream inputStream = requireActivity().getAssets().open("jsons/example.json");
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
        AtomicBoolean isFABOpen = new AtomicBoolean(false);
        ImageButton plusButton = view.findViewById(R.id.plus);
        LinearLayout set = view.findViewById(R.id.set);
        set.setVisibility(View.GONE);
        EditText nameEdit = view.findViewById(R.id.name);
        EditText numberEdit = view.findViewById(R.id.number);
        Button add = view.findViewById(R.id.add);
        if (listItems.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(loadJsonFile());
                JSONArray jsonArray = jsonObject.getJSONArray("numbers");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj;
                    obj = jsonArray.getJSONObject(i);
                    String name = obj.getString("name");
                    String number = obj.getString("number");
                    listItems.add(new numberItem(name, number));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        nameEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
        numberRecyclerView = view.findViewById(R.id.recyclerView);
        numberDataAdapter = new NumberDataAdapter(listItems);
        layoutManager = new LinearLayoutManager(getActivity());
        numberRecyclerView.setLayoutManager(layoutManager);
        numberRecyclerView.setAdapter(numberDataAdapter);
        DividerItemDecoration dividerItemDecorator = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        numberRecyclerView.addItemDecoration(dividerItemDecorator);
        plusButton.setOnClickListener(v -> {
            if (isFABOpen.get()) {
                plusButton.setImageResource(R.drawable.plus);
                set.setVisibility(View.GONE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                isFABOpen.set(false);
            } else {
                set.setVisibility(View.VISIBLE);
                plusButton.setImageResource(R.drawable.menu);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                isFABOpen.set(true);
            }
        });
        add.setOnClickListener(v -> {
            String n = nameEdit.getText().toString().trim();
            String m = numberEdit.getText().toString().trim();
            if (n.length() != 0 && m.length() != 0) {
                listItems.add(new numberItem(n, m));
                nameEdit.setText(null);
                numberEdit.setText(null);
            }
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            plusButton.setImageResource(R.drawable.plus);
            set.setVisibility(View.GONE);
            isFABOpen.set(false);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        numberDataAdapter.notifyDataSetChanged();
    }

}