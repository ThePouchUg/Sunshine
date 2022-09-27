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

    // TODO (6) Add a TextView variable for the error message display

    // TODO (16) Add a ProgressBar variable to show and hide the progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        eWeatherTextView = findViewById(R.id.tv_weather_data);
        eLocationEditText = findViewById(R.id.et_user_location);


        // TODO (7) Find the TextView for the error message using findViewById

        // TODO (17) Find the ProgressBar using findViewById

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
        // TODO (20) Call showWeatherDataView before executing the AsyncTask
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        new GetWeatherDataTask().execute(location);

    }

    // TODO (8) Create a method called showWeatherDataView that will hide the error message and show the weather data

    // TODO (9) Create a method called showErrorMessage that will hide the weather data and show the error message
    class GetWeatherDataTask extends AsyncTask<String, Void, String[]> {

        // TODO (18) Within your AsyncTask, override the method onPreExecute and show the loading indicator

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
            // TODO (19) As soon as the data is finished loading, hide the loading indicator
            if (weatherData != null) {
                // TODO (11) If the weather data was not null, make sure the data view is visible
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                for (String weatherString : weatherData) {
                    eWeatherTextView.append(weatherString + "\n\n\n");
                }
            }
            // TODO (10) If the weather data was null, show the error message
        }
    }
}




