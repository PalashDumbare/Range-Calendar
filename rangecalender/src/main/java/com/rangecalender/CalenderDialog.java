package com.rangecalender;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import java.util.Calendar;

public class CalenderDialog extends Dialog implements CalenderDayClicked {

    private RecyclerView dateList;
    private Calendar calendar;
    private Context context;
    private final String TAG = CalenderDialog.class.getSimpleName();

    public CalenderDialog( Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        this.context = context;
        setContentView(R.layout.calender_dialog);
        init();
        show();
    }

    private void init() {
        dateList = findViewById(R.id.calenderDates);
        dateList.setLayoutManager(new GridLayoutManager(context,7));
        calendar = Calendar.getInstance();
        try {
            CalenderAdapter calenderAdapter = new CalenderAdapter(Date_Utils.getDaysBetweenDates(Date_Utils.getFirstDateOfPreviousMonth(), Date_Utils.getLastDateOfPreviousMonth()),this);
            dateList.setAdapter(calenderAdapter);
        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onDayClicked(MyCalender calender) {

    }
}
