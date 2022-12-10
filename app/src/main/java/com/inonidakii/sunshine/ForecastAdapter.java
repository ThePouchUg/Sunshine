package com.inonidakii.sunshine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    private String[] mWeatherData;

    // TODO (3) Create a final private ForecastAdapterOnClickHandler called mClickHandler

    // TODO (1) Add an interface called ForecastAdapterOnClickHandler
    // TODO (2) Within that interface, define a void method that access a String as a parameter

    // TODO (4) Add a ForecastAdapterOnClickHandler as a parameter to the constructor and store it in mClickHandler
    public ForecastAdapter() {

    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent   The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                 can use this viewType integer to provide a different layout. See
     *                 {@link androidx.recyclerview.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                 for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
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

    // TODO (5) Implement OnClickListener in the ForecastAdapterViewHolder class

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mWeatherTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherTextView = itemView.findViewById(R.id.tv_weather_data);
            // TODO (7) Call setOnClickListener on the view passed into the constructor (use 'this' as the OnClickListener)
        }
        // TODO (6) Override onClick, passing the clicked day's data to mClickHandler via its onClick method
    }
}
