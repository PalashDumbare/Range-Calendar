package com.rangecalender.model;

import com.rangecalender.utils.Date_Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCalender {
    private int day;
    private int month;
    private int year;
    private String dayName = "";

    public MyCalender() {
    }

    public MyCalender(int day, int month, int year, String dayName) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayName = dayName;
    }



    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Date getDate() throws ParseException {
        return  new SimpleDateFormat("dd-MM-yyyy").parse(Date_Utils.addPrefixBeforeDateNumber(day)+"-"+Date_Utils.addPrefixBeforeDateNumber(month)+"-"+year);
    }

    @Override
    public String toString() {
        return "MyCalender{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", dayName='" + dayName + '\'' +
                '}';
    }
}
