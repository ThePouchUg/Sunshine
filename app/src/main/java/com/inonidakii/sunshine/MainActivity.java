package com.inonidakii.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.inonidakii.sunshine.data.SunshinePreferences;
import com.inonidakii.sunshine.utilities.NetworkUtils;
import com.inonidakii.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView eWeatherTextView;
    private TextView errorTextView;



    private ProgressBar eWeatherProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        eWeatherTextView = findViewById(R.id.tv_weather_data);
        EditText eLocationEditText = findViewById(R.id.et_user_location);


        errorTextView = findViewById(R.id.tv_error_message);

        eWeatherProgressBar = findViewById(R.id.weatherPb);
        /* Once all of our views are setup, we can load the weather data. */
        loadWeatherData();

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
        showWeatherDataView();
        new GetWeatherDataTask().execute(location);

    }

    void showWeatherDataView() {
        errorTextView.setVisibility(View.INVISIBLE);
        eWeatherTextView.setVisibility(View.VISIBLE);
    }

    void showErrorMessage() {
        errorTextView.setVisibility(View.VISIBLE);
        eWeatherTextView.setVisibility(View.INVISIBLE);
    }

    class GetWeatherDataTask extends AsyncTask<String, Void, String[]> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eWeatherProgressBar.setVisibility(View.VISIBLE);
        }

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

                return OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            eWeatherProgressBar.setVisibility(View.INVISIBLE);
            if (weatherData != null) {
                showWeatherDataView();
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                for (String weatherString : weatherData) {
                    eWeatherTextView.append(weatherString + "\n\n\n");
                }
            }
            else
                showErrorMessage();
        }
    }
}




