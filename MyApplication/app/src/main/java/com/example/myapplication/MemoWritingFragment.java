package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoWritingFragment extends Fragment{

    private String keyDate;

    MemoWritingFragment(String keyDate){
        this.keyDate = keyDate;
    }
    private EditText editTitleText;
    private EditText editContentText;
    private TextView editTimeText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memo_writing_fragment, container, false);

        editTitleText = view.findViewById(R.id.memo_title_edit);
        editContentText = view.findViewById(R.id.memo_content_edit);
        editTimeText = view.findViewById(R.id.time);

        FloatingActionButton button = view.findViewById(R.id.save_button);

        editTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlarmDialog alarm = new CustomAlarmDialog(getActivity(), editTimeText);
                alarm.show();
            }
        });
        button.setOnClickListener(new FloatingActionButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("memo_contain", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                String title = editTitleText.getText().toString();
                String content = editContentText.getText().toString();
                String time = editTimeText.getText().toString();

                Date nowDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
                String key = sdf.format(nowDate);
                MemoItem memoItem = new MemoItem(time, title, content, key);
                String value = pref.getString(keyDate, "NULL");
                if(value.equals("NULL")){
                    List<MemoItem> memoList = new ArrayList<>();
                    memoList.add(memoItem);
                    String json = new Gson().toJson(memoList);
                    editor.remove(key);
                    editor.putString(keyDate, json);
                    editor.commit();
                    Log.d("Writing3", json);
                }
                else{
                    List<MemoItem> memoList = new Gson().fromJson(value, new TypeToken<List<MemoItem>>(){}.getType());
                    int i = 0;
                    for(i = 0; i < memoList.size(); i++){
                        if(memoList.get(i).compareTo(memoItem))
                            break;
                    }

                    memoList.add(i, memoItem);
                    String json = new Gson().toJson(memoList);
                    editor.remove(keyDate);
                    editor.putString(keyDate, json);
                    editor.commit();

                    Log.d("Writing", json);
                }
                Log.d("Writing2", pref.getString(keyDate, "NULL"));
                getParentFragmentManager().popBackStack();
            }
        });
        return view;
    }


}
