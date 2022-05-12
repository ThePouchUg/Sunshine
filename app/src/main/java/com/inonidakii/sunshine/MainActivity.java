package com.inonidakii.sunshine;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView eWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eWeatherTextView = findViewById(R.id.tv_weather_data);

        // TODO (4) Delete the dummy weather data. You will be getting REAL data from the Internet in this lesson.
        String[] thorsNotes = {"Rainy", "Flooded", "Icy", "Frozen", "Muddy", "Slippery"};

        // TODO (3) Delete the for loop that populates the TextView with dummy data
        for (String weather : thorsNotes) {
            eWeatherTextView.append(weather + "\n");
        }

        // TODO (9) Call loadWeatherData to perform the network request to get the weather

    }

    // TODO (8) Create a method that will get the user's preferred location and execute your new AsyncTask and call it loadWeatherData

    // TODO (5) Create a class that extends AsyncTask to perform network requests
    // TODO (6) Override the doInBackground method to perform your network requests
    // TODO (7) Override the onPostExecute method to display the results of the network request

}