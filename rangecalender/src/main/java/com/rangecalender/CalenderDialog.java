package com.rangecalender;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
/**
 * @author Palash Dumbare
 *
 *
 * **/
public class CalenderDialog extends Dialog implements CalenderDayClicked, View.OnClickListener, YearPicker {
    /**/
    private RecyclerView dateList,yearList;
    private LinearLayout topBarLayout,topBarMainLayout;
    private CardView yearPickerLayout;
    private TextView monthYear,previous,next,ok,cancel,fromDateYearTxt,toDateYearTxt,fromDateTxt,toDateTxt,tabLine1;
    private Calendar calendar;
    private Context context;
    public int month,year;
    private CalenderAdapter calenderAdapter;
    private OnDateSelected onDateSelected;
    private LinearLayout openYearPicker;
    ArrayList<String>years;
    YearAdapter yearAdapter;
    private LinearLayoutManager yearLinearLayoutManager;
    private Animation anim;
    private int color,width;
    private boolean setSameDateAsFromDateIfToDateNotSelected;
    private final String TAG = CalenderDialog.class.getSimpleName();


    public CalenderDialog(Context context, int color, boolean setSameDateAsFromDateIfToDateNotSelected, OnDateSelected onDateSelected) {
        super(context,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        this.context = context;
        this.onDateSelected = onDateSelected;
        this.color = color;
        this.setSameDateAsFromDateIfToDateNotSelected = setSameDateAsFromDateIfToDateNotSelected;
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
        width = context.getResources().getDisplayMetrics().widthPixels;
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        ok = findViewById(R.id.OkBtn);
        cancel = findViewById(R.id.cancelBtn);
        dateList = findViewById(R.id.calenderDates);
        monthYear = findViewById(R.id.date);
        topBarLayout = findViewById(R.id.topbarLayout);
        fromDateYearTxt = findViewById(R.id.fromDateYear);
        toDateYearTxt = findViewById(R.id.toDateYear);
        fromDateTxt = findViewById(R.id.fromDate);
        toDateTxt = findViewById(R.id.toDate);
        yearList = findViewById(R.id.yearPicker);
        yearPickerLayout = findViewById(R.id.yearPickerLayout);
        openYearPicker = findViewById(R.id.openYearPicker);
        tabLine1 = findViewById(R.id.tabLine1);
        dateList.setLayoutManager(new GridLayoutManager(context,7));
        yearLinearLayoutManager = new LinearLayoutManager(context);
        yearList.setLayoutManager(yearLinearLayoutManager);




        anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        topBarLayout.setBackgroundColor(ContextCompat.getColor(context,color));
        ok.setTextColor(ContextCompat.getColor(context,color));
        cancel.setTextColor(ContextCompat.getColor(context,color));

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
                    calenderAdapter.setCalenders(Date_Utils.getDaysBetweenDates(fromDate, toDate),calenderAdapter.getFromDate(),calenderAdapter.getToDate());
                }else if (calenderAdapter.getFromDate() != null) {
                    calenderAdapter.setCalenders(Date_Utils.getDaysBetweenDates(fromDate, toDate),calenderAdapter.getFromDate(),null);
                }else{
                    calenderAdapter.setCalenders(Date_Utils.getDaysBetweenDates(fromDate, toDate),null,null);
                }
            }else {
                calenderAdapter = new CalenderAdapter(this);
                calenderAdapter.setColor(color);
                calenderAdapter.setCalenders(Date_Utils.getDaysBetweenDates(fromDate, toDate),null,null);
                dateList.setAdapter(calenderAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * if user selects any date in calender,callback will be in this method
     * **/
    @Override
    public void onDayClicked( ) {
        MyCalender fromDate = calenderAdapter.getFromDate();
        MyCalender toDate = calenderAdapter.getToDate();
        fromDateYearTxt.setText(String.valueOf(fromDate.getYear()));
        fromDateTxt.setText(Date_Utils.addPrefixBeforeDateNumber(fromDate.getDay())+" "+new DateFormatSymbols().getMonths()[fromDate.getMonth()-1]+" "+fromDate.getYear());
        if (toDate !=null) {
            /***
             * if todate is null means user has selected toDate ,we will stop the animation if flag of same date was set false
             * */
            if (!setSameDateAsFromDateIfToDateNotSelected) {
                stopAnimation();
            }

            toDateYearTxt.setText(String.valueOf(toDate.getYear()));
            toDateTxt.setText(Date_Utils.addPrefixBeforeDateNumber(toDate.getDay())+" "+new DateFormatSymbols().getMonths()[toDate.getMonth()-1]+" "+toDate.getYear());
        }else{
            toDateTxt.setText("To Date");
            ObjectAnimator animation = ObjectAnimator.ofFloat(tabLine1, "translationX", 350f);
            animation.setDuration(500);
            animation.start();
        }

    }


    public void startAnimation(){
        toDateTxt.startAnimation(anim);
    }

    public void stopAnimation(){
        anim.cancel();
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
                    cancel();
                }else if(calenderAdapter.getFromDate()!=null && calenderAdapter.getToDate() == null){
                    if (setSameDateAsFromDateIfToDateNotSelected){
                        /**
                         * fromDate and to date will be same
                         *
                         * */
                        onDateSelected.dateSelectedIs(calenderAdapter.getFromDate().getDate(), calenderAdapter.getFromDate().getDate());
                        cancel();
                    }else {
                        startAnimation();
                    }
                }else {
                    cancel();
                }

            } catch (ParseException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else if(v.getId() == R.id.openYearPicker){
            yearPickerLayout.setVisibility(View.VISIBLE);
            yearPickerLayout.bringToFront();
            yearList.scrollToPosition(yearAdapter.getItemCount()-1);
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
