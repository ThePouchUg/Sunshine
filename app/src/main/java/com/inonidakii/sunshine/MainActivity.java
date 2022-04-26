package com.inonidakii.sunshine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView eWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eWeatherTextView = findViewById(R.id.tv_weather_data);

        String [] thorsNotes = {"Rainy", "Flooded", "Icy", "Frozen", "Muddy", "Slippery"};

        for (String weather:thorsNotes) {
            eWeatherTextView.append(weather + "\n");
        }

    }
}