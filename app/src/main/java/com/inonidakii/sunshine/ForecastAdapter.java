package com.inonidakii.sunshine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    private String[] mWeatherData;

    public ForecastAdapter() {

    }

    @NonNull
    @Override
    public ForecastAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.MyViewHolder holder, int position) {
        holder.mWeatherTextView.setText(mWeatherData[position]);
    }

    @Override
    public int getItemCount() {
        if (mWeatherData != null) {
            return mWeatherData.length;
        }
        return 0;
    }

    void setWeatherData(String[] weatherData) {
        this.mWeatherData = weatherData;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mWeatherTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherTextView = itemView.findViewById(R.id.tv_weather_data);
        }
    }
}
