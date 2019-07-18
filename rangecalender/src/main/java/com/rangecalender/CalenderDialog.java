package com.rangecalender;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rangecalender.adapter.CalenderAdapter;
import com.rangecalender.adapter.YearAdapter;
import com.rangecalender.interfaces.CalenderDayClicked;
import com.rangecalender.interfaces.OnDateSelected;
import com.rangecalender.interfaces.YearPicker;
import com.rangecalender.model.MyCalender;
import com.rangecalender.utils.Date_Utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalenderDialog extends Dialog implements CalenderDayClicked, View.OnClickListener, YearPicker {
    /**/
    private RecyclerView dateList,yearList;
    private LinearLayout topBar;
    private CardView yearPickerLayout;
    private TextView monthYear,previous,next,ok,cancel,fromDateYearTxt,toDateYearTxt,fromDateTxt,toDateTxt;
    private Calendar calendar;
    private Context context;
    public int month,year;
    private CalenderAdapter calenderAdapter;
    private OnDateSelected onDateSelected;
    private LinearLayout openYearPicker;
    ArrayList<String>years;
    YearAdapter yearAdapter;
    private LinearLayoutManager yearLinearLayoutManager;
    private final String TAG = CalenderDialog.class.getSimpleName();


    public CalenderDialog(Context context,OnDateSelected onDateSelected) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        this.context = context;
        this.onDateSelected = onDateSelected;
        setContentView(R.layout.calender_dialog);
        init();
        events();
        setUpCalender();
        setOnUI();
        generateCalender();
        show();
    }

    private void setUpCalender() {
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH)+1;
        year = calendar.get(Calendar.YEAR);

        yearAdapter = new YearAdapter(years, String.valueOf(year),this);
        yearList.setAdapter(yearAdapter);


    }

    @Override
    public void onYearSelected(int year) {
        this.year = year;
        fromDateYearTxt.setText(String.valueOf(year));
        generateCalender();
        yearPickerLayout.setVisibility(View.GONE);
        yearAdapter.setSelectedYear(String.valueOf(year));
        setOnUI();

    }

    private void events() {
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        openYearPicker.setOnClickListener(this);

    }

    private void init() {
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        ok = findViewById(R.id.OkBtn);
        cancel = findViewById(R.id.cancelBtn);
        dateList = findViewById(R.id.calenderDates);
        monthYear = findViewById(R.id.date);
        topBar = findViewById(R.id.topbar);
        fromDateYearTxt = findViewById(R.id.fromDateYear);
        toDateYearTxt = findViewById(R.id.toDateYear);
        fromDateTxt = findViewById(R.id.fromDate);
        toDateTxt = findViewById(R.id.toDate);
        yearList = findViewById(R.id.yearPicker);
        yearPickerLayout = findViewById(R.id.yearPickerLayout);
        openYearPicker = findViewById(R.id.openYearPicker);
        dateList.setLayoutManager(new GridLayoutManager(context,7));
        yearLinearLayoutManager = new LinearLayoutManager(context);
        yearList.setLayoutManager(yearLinearLayoutManager);

        years = new ArrayList<>();
        for (int i =1900 ;i <= 2100  ;i++){
            years.add(i+"");
        }


    }

    private void generateCalender(){
        try {
            String fromDateStr = Date_Utils.getFirstDateOfCurrentMonth(month-1,year);
            String toDateStr = Date_Utils.getLastDateOfCurrentMonth(month,year);
            Date fromDate = new SimpleDateFormat("dd-MMM-yyyy").parse(fromDateStr);
            Date toDate = new SimpleDateFormat("dd-MMM-yyyy").parse(toDateStr);
            if (calenderAdapter!=null){
                if(calenderAdapter.getFromDate() != null && calenderAdapter.getToDate() !=null){
                    calenderAdapter = new CalenderAdapter(Date_Utils.getDaysBetweenDates(fromDate, toDate), this, calenderAdapter.getFromDate(), calenderAdapter.getToDate());
                    dateList.setAdapter(calenderAdapter);
                }else if (calenderAdapter.getFromDate() != null) {
                    calenderAdapter = new CalenderAdapter(Date_Utils.getDaysBetweenDates(fromDate, toDate), this, calenderAdapter.getFromDate(), null);
                    dateList.setAdapter(calenderAdapter);
                }
            }else {
                calenderAdapter = new CalenderAdapter(Date_Utils.getDaysBetweenDates(fromDate, toDate), this);
                dateList.setAdapter(calenderAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDayClicked( ) {
        MyCalender fromDate = calenderAdapter.getFromDate();
        MyCalender toDate = calenderAdapter.getToDate();
        fromDateYearTxt.setText(String.valueOf(fromDate.getYear()));
        fromDateTxt.setText(Date_Utils.addPrefixBeforeDateNumber(fromDate.getDay())+" "+new DateFormatSymbols().getMonths()[fromDate.getMonth()-1]+" "+fromDate.getYear());
        if (toDate !=null) {
            toDateYearTxt.setText(String.valueOf(toDate.getYear()));
            toDateTxt.setText(Date_Utils.addPrefixBeforeDateNumber(toDate.getDay())+" "+new DateFormatSymbols().getMonths()[toDate.getMonth()-1]+" "+toDate.getYear());
        }else{
            toDateTxt.setText("To Date");
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
       }else if(v.getId() == R.id.OkBtn){
           try {
               if (calenderAdapter.getFromDate() != null && calenderAdapter.getToDate() != null) {
                   onDateSelected.dateSelectedIs(calenderAdapter.getFromDate().getDate(), calenderAdapter.getToDate().getDate());
               }
               cancel();
           } catch (ParseException e) {
               Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
               e.printStackTrace();
           }
       }else if(v.getId() == R.id.openYearPicker){
           yearPickerLayout.setVisibility(View.VISIBLE);
           yearPickerLayout.bringToFront();

           yearList.scrollToPosition(yearAdapter.getItemCount()-1);
           Log.d(TAG,"sdf fjdkljfk "+yearAdapter.getYearposition());
            yearList.scrollToPosition(years.indexOf(year+""));
        }else{
           cancel();
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
