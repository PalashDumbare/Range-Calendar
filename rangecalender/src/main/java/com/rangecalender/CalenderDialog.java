package com.rangecalender;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalenderDialog extends Dialog implements CalenderDayClicked, View.OnClickListener {

    private RecyclerView dateList;
    private TextView monthYear,previous,next;
    private Calendar calendar;
    private Context context;
    private int month,year;
    private CalenderAdapter calenderAdapter;
    private ArrayList<MyCalender>myCalenderArrayList;
    MyCalender myCalender;
    private final String TAG = CalenderDialog.class.getSimpleName();

    public CalenderDialog( Context context) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        this.context = context;
        setContentView(R.layout.calender_dialog);
        init();
        events();
        show();
    }

    private void events() {
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    private void init() {
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        dateList = findViewById(R.id.calenderDates);
        monthYear = findViewById(R.id.date);
        dateList.setLayoutManager(new GridLayoutManager(context,7));
         calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
         myCalender = new MyCalender();
        myCalender.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        myCalender.setMonth(calendar.get(Calendar.MONTH));
        myCalender.setYear(calendar.get(Calendar.YEAR));
         setOnUI();
        generateCalender();
    }

    private void generateCalender(){
        try {

            String fromDateStr = Date_Utils.getFirstDateOfCurrentMonth(month-1,year);
            String toDateStr = Date_Utils.getLastDateOfCurrentMonth(month,year);
            Date fromDate = new SimpleDateFormat("dd-MMM-yyyy").parse(fromDateStr);
            Date toDate = new SimpleDateFormat("dd-MMM-yyyy").parse(toDateStr);


           calenderAdapter = new CalenderAdapter(Date_Utils.getDaysBetweenDates(fromDate,toDate), this, myCalender,myCalender);
            dateList.setAdapter(calenderAdapter);



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDayClicked(MyCalender calender) {
        MyCalender fromDateAsPerAdapter = calenderAdapter.getFromDate();
        try {
             if (calender.getDate().before(fromDateAsPerAdapter.getDate())){
                 calenderAdapter.swapDate(calender,fromDateAsPerAdapter);

             }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void setFromDate(Date fromDate){
        MyCalender myCalender = new MyCalender();
        myCalender.setDay(fromDate.getDay());
        myCalender.setMonth(fromDate.getMonth());
        myCalender.setYear(fromDate.getYear());
        calenderAdapter.setFromDate(myCalender);
    }


    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.next){
           goToNextMonth();
       }else if(v.getId() == R.id.previous){
           goToPreviousMonth();
       }
    }

    private void goToPreviousMonth() {
        if (month!=1) {
            month = month - 1;
        }else{
            month = 12;
            year = year-1;
        }
        setOnUI();
    }

    private void goToNextMonth() {
         if (month!=12) {
            month = month + 1;
        }else{
            month = 1;
            year = year+1;
        }
        setOnUI();
    }


    private void setOnUI(){
         monthYear.setText(new DateFormatSymbols().getMonths()[month-1]+" "+year);
         generateCalender();
    }
}
