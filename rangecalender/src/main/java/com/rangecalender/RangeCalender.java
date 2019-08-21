package com.rangecalender;

import android.content.Context;

import com.rangecalender.interfaces.OnDateSelected;

import java.util.Date;
/***
 * @author Palash Dumbare
 * This will be the class which will be interacted by the client class
 *
 * **/
public class RangeCalender {
    private Context context;
    private OnDateSelected onDateSelected;
    private int color = R.color.design_default_color_primary;
    private boolean setSameDateAsFromDateIfToDateNotSelected = false;


    public RangeCalender(Context context,int color,OnDateSelected onDateSelected) {
        this.color = color;
        this.context = context;
        this.onDateSelected = onDateSelected;
    }
    /**
     * @param b : if set true, todate selection will not be compulsary and blinking animation will not be there,
     *          todate will be same as fromDate.
     * **/
    public void setSetSameDateAsFromDateIfToDateNotSelected(boolean b){
        this.setSameDateAsFromDateIfToDateNotSelected = b;
    }

    public void show(){

        CalenderDialog calenderDialog = new CalenderDialog(context,color, setSameDateAsFromDateIfToDateNotSelected,new OnDateSelected() {
            @Override
            public void dateSelectedIs(Date fromDate, Date toDate) {
                onDateSelected.dateSelectedIs(fromDate,toDate);
            }
        });
        calenderDialog.show();
    }

}
