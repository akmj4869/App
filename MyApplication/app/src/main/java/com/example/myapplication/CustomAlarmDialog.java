package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class CustomAlarmDialog extends Dialog {
    public CustomAlarmDialog(@NonNull Context context, TextView time_view) {
        super(context);

        String time = (String)time_view.getText();

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        setContentView(R.layout.custom_alram_dialog);

        TimePicker timePicker1 = findViewById(R.id.time_picker1);
        TimePicker timePicker2 = findViewById(R.id.time_picker2);

        TextView fromTimebtn = findViewById(R.id.from_time);
        TextView toTimebtn = findViewById(R.id.to_time);
        TextView savebtn = findViewById(R.id.save);
        TextView canclebtn = findViewById(R.id.cancel);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        String fromTime = time.substring(0, 5);
        String toTime = time.substring(8,13);
        fromTimebtn.setText(fromTime);
        toTimebtn.setText(toTime);

        fromTimebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_edge_selected));

        timePicker1.setHour(Integer.parseInt(((String)fromTimebtn.getText()).substring(0,2)));
        timePicker1.setMinute(Integer.parseInt(((String)fromTimebtn.getText()).substring(3,5)));


        timePicker2.setHour(Integer.parseInt(((String)toTimebtn.getText()).substring(0,2)));
        timePicker2.setMinute(Integer.parseInt(((String)toTimebtn.getText()).substring(3,5)));
        fromTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromTimebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_edge_selected));
                toTimebtn.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

                timePicker1.setHour(Integer.parseInt(((String)fromTimebtn.getText()).substring(0,2)));
                timePicker1.setMinute(Integer.parseInt(((String)fromTimebtn.getText()).substring(3,5)));

                timePicker2.setVisibility(View.GONE);
                timePicker1.setVisibility(View.VISIBLE);

            }
        });

        toTimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTimebtn.setBackground(ContextCompat.getDrawable(context, R.drawable.round_edge_selected));
                fromTimebtn.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

                timePicker2.setHour(Integer.parseInt(((String)toTimebtn.getText()).substring(0,2)));
                timePicker2.setMinute(Integer.parseInt(((String)toTimebtn.getText()).substring(3,5)));

                timePicker1.setVisibility(View.GONE);
                timePicker2.setVisibility(View.VISIBLE);

            }
        });

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

                String t = (String) toTimebtn.getText();

                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                try {
                    Date toTime = parser.parse(t);
                    Date fromTime = parser.parse(String.format("%02d:%02d", i, i1));
                    if(Integer.parseInt(t.substring(0,2)) != 24 && fromTime.after(toTime)){
                        t = t.replace(t.substring(0,2), String.format("%02d",i + 1));
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                toTimebtn.setText(t);
                fromTimebtn.setText(String.format("%02d:%02d", i, i1));
            }
        });

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                String t = (String) fromTimebtn.getText();

                SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
                try {
                    Date toTime = parser.parse(t);
                    Date fromTime = parser.parse(String.format("%02d:%02d", i, i1));
                    if(Integer.parseInt(t.substring(0,2)) != 0 && fromTime.before(toTime)){
                        t = t.replace(t.substring(0,2), String.format("%02d",i - 1));
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                fromTimebtn.setText(t);
                toTimebtn.setText(String.format("%02d:%02d", i, i1));
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                time_view.setText((String)fromTimebtn.getText() + " : " + (String)toTimebtn.getText());
                dismiss();
            }
        });
        canclebtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
