package com.rangecalender;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Date_Utils {

    public static String convertDate(String date, String fromFormat, String toFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromFormat);
            SimpleDateFormat dateFormat = new SimpleDateFormat(toFormat);
            Date parse = simpleDateFormat.parse(date);
            date = dateFormat.format(parse);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }



    public static String addPrefixBeforeDateNumber(int value) {
        return value < 10 ? "0" + value : String.valueOf(value);
    }



    public static Date getFirstDateOfPreviousMonth() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.MONTH, -1);
        calender.set(Calendar.DATE, 1);
        return df.parse(df.format(calender.getTime()));
    }

    public static String getTomorrowsDate(String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        Calendar calender = Calendar.getInstance();
        int day=calender.get(Calendar.DATE)+ 1;
        calender.set(Calendar.DATE,day);
        return df.format(calender.getTime());
    }

    public static Date getLastDateOfPreviousMonth() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.MONTH, -1);
        calender.set(Calendar.DATE, calender.getActualMaximum(Calendar.DAY_OF_MONTH));
        return df.parse(df.format(calender.getTime()));
    }


    public static String getFirstDateOfCurrentMonth(int month,int year){

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.MONTH, month);
        calender.set(Calendar.YEAR,year);
        calender.set(Calendar.DATE, 1);
        return df.format(calender.getTime());
    }

    public static String getLastDateOfCurrentMonth(int month,int year) {
         SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        Calendar calender = Calendar.getInstance();
        try {
            calender.setTime(Date_Utils.convertStringToDate("01-"+month+"-"+year,"dd-MM-yyyy"));
            calender.set(Calendar.DATE,calender.getActualMaximum(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }


         return df.format(calender.getTime());
    }


    public static ArrayList<MyCalender> getDaysBetweenDates(Date startdate, Date enddate)  {
        Log.d("PALASHJ","sdsdsdd DATES "+startdate +"         "+enddate);

        ArrayList<MyCalender> dates = new ArrayList<MyCalender>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startdate);
        while (calendar.getTime().equals(enddate) || calendar.getTime().before(enddate))
        {
            int dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK);
            String dayName = "";
            if (dayOfWeek == 1){
                dayName = "Sun";
            }else if(dayOfWeek == 2){
                dayName = "Mon";
            }else if(dayOfWeek == 3){
                dayName = "Tue";
            }else if(dayOfWeek == 4){
                dayName = "Wed";
            }else if(dayOfWeek == 5){
                dayName = "Thu";
            }else if(dayOfWeek == 6){
                dayName = "Fri";
            }else if(dayOfWeek == 7){
                dayName = "Sat";
            }
            MyCalender calender = new MyCalender(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR),dayName);
            dates.add(calender);
            calendar.add(Calendar.DATE, 1);
            Log.d("PALASHJ","CHECKING DATES "+dates);
        }
        return dates;
    }



    public static Date convertStringToDate(String date,String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date);
    }

}

