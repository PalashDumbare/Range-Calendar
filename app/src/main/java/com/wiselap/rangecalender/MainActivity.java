package com.wiselap.rangecalender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rangecalender.RangeCalender;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RangeCalender rangeCalender = new RangeCalender(this);
        rangeCalender.show();
    }
}
