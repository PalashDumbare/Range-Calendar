package com.rangecalender.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rangecalender.R;
import com.rangecalender.interfaces.YearPicker;

import java.util.ArrayList;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {
    private ArrayList<String> years;
    private String selectedYear;
    private Context context;
    private int yearposition = -1;
    private YearPicker yearPicker;
    public YearAdapter(ArrayList<String> years,String selectedYear,YearPicker yearPicker) {
        this.years = years;
        this.selectedYear = selectedYear;
        this.yearPicker = yearPicker;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_for_year,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.year.setText(years.get(i));
        if (selectedYear.equals(years.get(i))){
            viewHolder.year.setTextColor(ContextCompat.getColor(context,android.R.color.black));
            viewHolder.year.setTextSize(18);
            yearposition = i;
        }else{
            viewHolder.year.setTextColor(ContextCompat.getColor(context,android.R.color.darker_gray));
            viewHolder.year.setTextSize(15);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearPicker.onYearSelected(Integer.parseInt(years.get(i)));
            }
        });
    }

    public int getYearposition() {
        return yearposition;
    }

    @Override
    public int getItemCount() {
        return years.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView year;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.year);
        }
    }
}
