package com.rangecalender;

import android.content.Context;

import com.rangecalender.interfaces.OnDateSelected;

import java.util.Date;

public class RangeCalender {
    private Context context;
    private OnDateSelected onDateSelected;
    private int color = R.color.design_default_color_primary;

    public RangeCalender(Context context,OnDateSelected onDateSelected) {
        this.context = context;
        this.onDateSelected = onDateSelected;
    }

    public RangeCalender(Context context,int color,OnDateSelected onDateSelected) {
        this.color = color;
        this.context = context;
        this.onDateSelected = onDateSelected;
    }

    public void show(){
        CalenderDialog calenderDialog = new CalenderDialog(context,color, new OnDateSelected() {
            @Override
            public void dateSelectedIs(Date fromDate, Date toDate) {
                onDateSelected.dateSelectedIs(fromDate,toDate);
            }
        });
        calenderDialog.show();
    }

}
