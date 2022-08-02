package com.inonidakii.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.inonidakii.sunshine.data.SunshinePreferences;
import com.inonidakii.sunshine.utilities.NetworkUtils;
import com.inonidakii.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView eWeatherTextView;
    EditText eLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eWeatherTextView = findViewById(R.id.tv_weather_data);
        eLocationEditText = findViewById(R.id.et_user_location);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main, menu);
        inflater.inflate(R.menu.forecast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_search) {
            loadWeatherData();
        } else if (itemId == R.id.action_refresh) {
            eWeatherTextView.setText("");
            loadWeatherData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadWeatherData() {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new GetWeatherDataTask().execute(location);

    }

    class GetWeatherDataTask extends AsyncTask<String, Void, String[]> {


        @Override
        protected String[] doInBackground(String... params) {
            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                return simpleJsonWeatherData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            if (weatherData != null) {
                /*Iterate through the array and append the strings to the textview. The reason we add
                the "\n\n\n" after the String is to give visual separation between seach String in the
                Later we 'll learn about a better way to display lists of data.
                * */
                for (String weatherString : weatherData) {
                    eWeatherTextView.append(weatherString + "\n\n\n");
                }
            }
        }
    }
}




