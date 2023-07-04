package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarFragment extends Fragment implements MemoAdapter.OnItemClickListener {

    private MemoAdapter memoAdapter;
    private RecyclerView memoRecyclerView;
    private ArrayList<MemoItem> memoItems;
    private RecyclerView.LayoutManager layoutManager;
    private String key;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_view, container, false);



        CalendarView calendarView = view.findViewById(R.id.calendarView);

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        key = sdf.format(nowDate);

        Log.d("onCreate", key);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // todo
                key = String.format("%d-%02d-%02d", year, month+1, dayOfMonth);
                Log.d("Bbutton", key);
                List<MemoItem> memoItems = loadData();

                for(MemoItem i : memoItems)
                    Log.d("Button", i.getTitle());
                memoAdapter.setData(memoItems);
                memoAdapter.notifyDataSetChanged();
            }
        });

        memoRecyclerView = view.findViewById(R.id.schedules);

        memoItems = new ArrayList<>();
        memoAdapter = new MemoAdapter(memoItems, getActivity());
        memoAdapter.setOnItemClickListener(this);
        layoutManager = new LinearLayoutManager(getActivity());


        memoRecyclerView.setAdapter(memoAdapter);
        memoRecyclerView.setLayoutManager(layoutManager);


        List<MemoItem> memoList = loadData();
        memoAdapter.setData(memoList);


        memoAdapter.notifyDataSetChanged();



        FloatingActionButton button = view.findViewById(R.id.plusButton) ;
        button.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment = new MemoWritingFragment(key);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onItemClick(String keyMemo) {
        Fragment fragment = new MemoReadingFragment(key, keyMemo);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    public void onResume() {
        super.onResume();
        // Reload data
        List<MemoItem> newMemoItems = loadData();
        memoAdapter.setData(newMemoItems);
        Log.d("onResume", key);
        for(MemoItem i : newMemoItems)
            Log.d("onResume", i.getTitle());
        memoAdapter.notifyDataSetChanged();
    }

    private List<MemoItem> loadData(){

        SharedPreferences pref = getActivity().getSharedPreferences("memo_contain", MODE_PRIVATE);
        String value = pref.getString(key, "NULL");
        if(value.equals("NULL")) return new ArrayList<>();

        Log.d("loadData",value);
        List<MemoItem> memoList = new Gson().fromJson(value, new TypeToken<List<MemoItem>>(){}.getType());
        for(MemoItem i : memoList)
            Log.d("content", i.getTitle());
        return memoList;
    }
}
