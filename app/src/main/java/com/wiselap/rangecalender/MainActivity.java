package com.wiselap.rangecalender;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rangecalender.RangeCalender;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RangeCalender rangeCalender = new RangeCalender(this);
        rangeCalender.show();
    }
}
