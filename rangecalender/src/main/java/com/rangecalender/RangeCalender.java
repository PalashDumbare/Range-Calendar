package com.rangecalender;

import android.content.Context;

import com.rangecalender.interfaces.OnDateSelected;

import java.util.Date;

public class RangeCalender {
    private Context context;
    private OnDateSelected onDateSelected;

    public RangeCalender(Context context,OnDateSelected onDateSelected) {
        this.context = context;
        this.onDateSelected = onDateSelected;
    }


    public void show(){
        CalenderDialog calenderDialog = new CalenderDialog(context, new OnDateSelected() {
            @Override
            public void dateSelectedIs(Date fromDate, Date toDate) {
                onDateSelected.dateSelectedIs(fromDate,toDate);
            }
        });
        calenderDialog.show();
    }

}
