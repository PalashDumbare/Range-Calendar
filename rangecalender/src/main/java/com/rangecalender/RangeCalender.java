package com.rangecalender;

import android.content.Context;

public class RangeCalender {
    private Context context;

    public RangeCalender(Context context) {
        this.context = context;
    }


    public void show(){
        CalenderDialog calenderDialog = new CalenderDialog(context);
        calenderDialog.show();
    }

}
