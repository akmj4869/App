package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.List;

public class MemoReadingFragment extends Fragment {
    private String keyDate;
    private String keyMemo;
    private String time;
    private String title;
    private String content;
    private int idx;
    private boolean isFABOpen = false;
    public MemoReadingFragment(String keyDate, String keyMemo){
        this.keyDate = keyDate;
        this.keyMemo = keyMemo;
    }


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memo_reading_fragment, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("memo_contain", MODE_PRIVATE);
        String value = pref.getString(keyDate, "NULL");

        List<MemoItem> memoList = new Gson().fromJson(value, new TypeToken<List<MemoItem>>(){}.getType());

        Integer j = memoList.size();
        int i = 0;
        MemoItem memoItem = null;

        for(i = 0; i < memoList.size(); i++){
            memoItem = memoList.get(i);
            if(memoItem.getKey().equals(keyMemo))
                break;
        }

        idx = i;

        TextView date_view = view.findViewById(R.id.date);
        TextView time_view = view.findViewById(R.id.time);
        EditText title_view = view.findViewById(R.id.memo_title_edit);
        EditText content_view = view.findViewById(R.id.memo_content_edit);


        time_view.setText(time = memoItem.getTime());
        title_view.setText(title = memoItem.getTitle());
        content_view.setText(content = memoItem.getPreview());


        FloatingActionButton modifyBtn = view.findViewById(R.id.modify_button);
        FloatingActionButton deleteBtn = view.findViewById(R.id.delete_button);
        FloatingActionButton menuBtn = view.findViewById(R.id.fab_main);

        final Animation showFab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        final Animation hideFab = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);


        time_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                         // xml 레이아웃 파일과 연
                // 버튼: 커스텀 다이얼로그 띄우기
                CustomAlarmDialog alarm = new CustomAlarmDialog(getActivity(), time_view);
                alarm.show();
            }
        });
        time_view.setClickable(false);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (!isFABOpen) {
                    modifyBtn.setVisibility(View.VISIBLE);
                    deleteBtn.setVisibility(View.VISIBLE);
                    modifyBtn.startAnimation(showFab);
                    deleteBtn.startAnimation(showFab);
                    menuBtn.setImageResource(R.drawable.cancel);
                    isFABOpen = true;
                } else {
                    modifyBtn.startAnimation(hideFab);
                    deleteBtn.startAnimation(hideFab);
                    modifyBtn.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);
                    menuBtn.setImageResource(R.drawable.menu);
                    isFABOpen = false;
                }
            }
        });
        modifyBtn.setOnClickListener(new Button.OnClickListener(){
            private boolean chk = false;
            @Override
            public void onClick(View view) {
                chk = !chk;
                if(chk){
                    time_view.setClickable(chk);
                    title_view.setEnabled(chk);
                    content_view.setEnabled(chk);
                    modifyBtn.setImageResource(R.drawable.check);
                }
                else{
                    time_view.setClickable(chk);
                    title_view.setEnabled(chk);
                    content_view.setEnabled(chk);

                    modifyBtn.setImageResource(R.drawable.modify);
                    MemoItem modifiedMemoItem = memoList.get(idx);
                    modifiedMemoItem.setPreview(content_view.getText().toString());
                    modifiedMemoItem.setTime(time_view.getText().toString());
                    modifiedMemoItem.setTitle(title_view.getText().toString());

                    memoList.remove(memoList.get(idx));
                    memoList.add(modifiedMemoItem);

                    time_view.setText(time = modifiedMemoItem.getTime());
                    title_view.setText(title = modifiedMemoItem.getTitle());
                    content_view.setText(content = modifiedMemoItem.getPreview());


                    SharedPreferences pref = getActivity().getSharedPreferences("memo_contain", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    String json = new Gson().toJson(memoList);
                    editor.putString(keyDate, json);
                    editor.commit();
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                OnClickHandler(view);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void OnClickHandler(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("").setMessage("정말 삭제하시겠습니까?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                Toast.makeText(getActivity(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getActivity().getSharedPreferences("memo_contain", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                String json = pref.getString(keyDate, "NULL");
                List<MemoItem> memoList = new Gson().fromJson(json, new TypeToken<List<MemoItem>>(){}.getType());
                memoList.remove(memoList.get(idx));
                json = new Gson().toJson(memoList);
                editor.putString(keyDate, json);
                editor.commit();

                getParentFragmentManager().popBackStack();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getActivity(), "Cancel Click", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
};






