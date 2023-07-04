package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        EditText time_view = view.findViewById(R.id.time);
        EditText title_view = view.findViewById(R.id.memo_title_edit);
        EditText content_view = view.findViewById(R.id.memo_content_edit);


        time_view.setText(time = memoItem.getTime());
        title_view.setText(title = memoItem.getTitle());
        content_view.setText(content = memoItem.getPreview());


        Button modifyBtn = view.findViewById(R.id.modify_button);
        Button deleteBtn = view.findViewById(R.id.delete_button);

        modifyBtn.setOnClickListener(new Button.OnClickListener(){
            private boolean chk = false;
            @Override
            public void onClick(View view) {
                chk = !chk;
                if(chk){
                    time_view.setEnabled(chk);
                    title_view.setEnabled(chk);
                    content_view.setEnabled(chk);
                    modifyBtn.setText("저장하기");
                }
                else{
                    time_view.setEnabled(chk);
                    title_view.setEnabled(chk);
                    content_view.setEnabled(chk);
                    modifyBtn.setText("수정하기");

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

        builder.setTitle("").setMessage("선택하세요.");

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

        builder.setNeutralButton("Neutral", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getActivity(), "Neutral Click", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
};






