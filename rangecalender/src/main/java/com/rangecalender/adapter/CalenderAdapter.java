package com.rangecalender.adapter;


import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.rangecalender.model.MyCalender;
import com.rangecalender.R;
import com.rangecalender.interfaces.CalenderDayClicked;

import java.util.ArrayList;
import java.util.Date;


public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {

    private ArrayList<MyCalender>calenders;
    private Context context;
    private CalenderDayClicked dayClickedListener;
    private MyCalender fromDate,toDate;
    private int fromDateSelectedPosition = -1,toDateSelectedPosition = -1;
    private int color ;
    private final String TAG = CalenderAdapter.class.getSimpleName();


    public CalenderAdapter(CalenderDayClicked dayClickedListener) {
        this.dayClickedListener = dayClickedListener;
    }


    public void setCalenders(ArrayList<MyCalender> calenders,MyCalender fromDate,MyCalender toDate) {
        this.calenders = calenders;
        this.toDate = toDate;
        this.fromDate = fromDate;
        int s = fixLocation(calenders.get(0));
        for (int i = 0; i < s; i++) {
            this.calenders.add(i, null);
        }
        notifyDataSetChanged();
    }




    public void setColor(int color){
        this.color = color;
    }



    public int fixLocation(MyCalender wiseLapCalender){
        if (wiseLapCalender.getDayName().equals("Mon")){
            return 1;
        }else if(wiseLapCalender.getDayName().equals("Tue")){
            return 2;
        }else if(wiseLapCalender.getDayName().equals("Wed")){
            return 3;
        }else if(wiseLapCalender.getDayName().equals("Thu")){
            return 4;
        }else if(wiseLapCalender.getDayName().equals("Fri")){
            return 5;
        }else if(wiseLapCalender.getDayName().equals("Sat")){
            return 6;
        }
        return 0;
    }




    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_row_for_date,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {



        if (calenders.get(position) == null){
            holder.rootLayout.setVisibility(View.GONE);
        } else{
            holder.dateTxt.setText(String.valueOf(calenders.get(position).getDay()));
        }
        if ( calenders.get(position)!=null &&
                fromDate!=null && fromDate.getDay() == calenders.get(position).getDay()
                && fromDate.getMonth() == calenders.get(position).getMonth()
                && fromDate.getYear() == calenders.get(position).getYear()){
            fromDateSelectedPosition = position;
         }
        if ( calenders.get(position)!=null && toDate != null &&  toDate.getDay() == calenders.get(position).getDay() && toDate.getMonth() == calenders.get(position).getMonth()
                && toDate.getYear() == calenders.get(position).getYear()){
            toDateSelectedPosition = position;

        }
        hightLightItem(holder,position);

        if (fromDate != null && toDate != null){
            if (calenders.get(position) !=null){
                try {
                    Date date = calenders.get(position).getDate();
                    if (date.after(fromDate.getDate()) && date.before(toDate.getDate())){
                        holder.dateTxt.setBackgroundColor(ContextCompat.getColor(context,android.R.color.darker_gray));
                        holder.dateTxt.setTextColor(ContextCompat.getColor(context,android.R.color.white));
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }



        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromDate == null && toDate == null){
                    fromDateSelectedPosition = position;
                    fromDate = calenders.get(position);
                 }else if(fromDate != null && toDate ==null){
                    toDate = calenders.get(position);
                    try {
                        if (fromDate.getDate().after(toDate.getDate())) {
                            fromDateSelectedPosition = position;
                            fromDate = calenders.get(position);
                            toDate = null;
                            toDateSelectedPosition = -1;
                        }else{
                            toDateSelectedPosition = position;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                 } else if(fromDate !=null && toDate!=null){
                        fromDateSelectedPosition = position;
                        fromDate = calenders.get(position);
                        toDate = null;
                        toDateSelectedPosition = -1;

                }
                dayClickedListener.onDayClicked();
                notifyDataSetChanged();
            }
        });



    }


    private void hightLightItem(ViewHolder holder,int position){
          if (fromDateSelectedPosition == position || toDateSelectedPosition == position){
            holder.dateTxt.setBackgroundColor(ContextCompat.getColor(context,color));
            holder.dateTxt.setTextColor(ContextCompat.getColor(context,android.R.color.white));
        }else{
            holder.dateTxt.setBackgroundColor(ContextCompat.getColor(context,android.R.color.white));
            holder.dateTxt.setTextColor(ContextCompat.getColor(context,android.R.color.black));
        }
    }



    public MyCalender getFromDate(){
        return fromDate;
    }

    public MyCalender getToDate() {
        return toDate;
    }

    public void setFromDate(MyCalender fromDate){
        this.fromDate = fromDate;
        notifyDataSetChanged();
    }
    public void setToDate(MyCalender toDate) {
        this.toDate = toDate;
        notifyDataSetChanged();
    }

    public void swapDate(MyCalender fromDate,MyCalender toDate){
        this.fromDate = fromDate;
        this.toDate = toDate;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return calenders.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout rootLayout;
        private TextView dateTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.rootlayout);
            dateTxt = itemView.findViewById(R.id.date);
        }
    }

}
