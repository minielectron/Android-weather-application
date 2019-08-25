package com.uniqolabel.weatherapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uniqolabel.weatherapp.activities.MainActivity;
import com.uniqolabel.weatherapp.model.ForecastModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private ArrayList<ForecastModel> forecastDataList;
    private Context context;

    public ForecastAdapter( Context context, ArrayList<ForecastModel> forecastDataList) {
        this.forecastDataList = forecastDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_view_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.dateTv.setText(forecastDataList.get(i).getDate());
        holder.tempRangeTv.setText(forecastDataList.get(i).getTempRange());
        holder.dayLabel.setText(forecastDataList.get(i).getDayName());
        Glide.with(context).load(MainActivity.IMAGE_LOADING_URL+forecastDataList.get(i).getIconName()+".png").into(holder.weatherIcon);
//        holder.dateTv.setText(forecastDataList.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return forecastDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.weather_icon)
        ImageView weatherIcon;
        @BindView(R.id.day_label)
        TextView dayLabel;
        @BindView(R.id.date_tv)
        TextView dateTv;
        @BindView(R.id.temp_range_tv)
        TextView tempRangeTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
