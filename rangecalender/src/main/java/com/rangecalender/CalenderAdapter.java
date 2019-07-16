package com.rangecalender;


import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.ViewHolder> {

    private ArrayList<MyCalender>calenders;
    private Context context;
    private  CalenderDayClicked dayClickedListener;
    private final String TAG = CalenderAdapter.class.getSimpleName();

    public CalenderAdapter(ArrayList<MyCalender> calenders, CalenderDayClicked dayClickedListener) {
        this.calenders = calenders;
        this.dayClickedListener = dayClickedListener;
        int s = fixLocation(calenders.get(0));
        for (int i=0;i<s;i++){
            calenders.add(i,null);
        }

        Log.d(TAG,"CHECKING CALENDERS "+calenders);
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


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_row_for_date,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (calenders.get(position) == null){
            holder.rootLayout.setVisibility(View.GONE);
        } else{
            holder.dateTxt.setText(String.valueOf(calenders.get(position).getDay()));
        }



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
