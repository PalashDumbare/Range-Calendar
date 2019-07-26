package com.wiselap.rangecalender;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rangecalender.interfaces.OnDateSelected;
import com.rangecalender.RangeCalender;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private TextView text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RangeCalender rangeCalender = new RangeCalender(MainActivity.this,android.R.color.black, new OnDateSelected() {
                    @Override
                    public void dateSelectedIs(Date fromDate, Date toDate) {
                        text.setText(new SimpleDateFormat("dd-MMM-yyyy").format(fromDate)+" - "+new SimpleDateFormat("dd-MMM-yyyy").format(toDate));
                    }
                });
                rangeCalender.show();
            }
        });

    }
}
