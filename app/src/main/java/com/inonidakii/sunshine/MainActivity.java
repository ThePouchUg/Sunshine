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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inonidakii.sunshine.data.SunshinePreferences;
import com.inonidakii.sunshine.utilities.NetworkUtils;
import com.inonidakii.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Within forecast_list_item.xml //////////////////////////////////////////////////////////////
    // TO-DONE (5) Add a layout for an item in the list called forecast_list_item.xml
    // TO-DONE (6) Make the root of the layout a vertical LinearLayout
    // TO-DONE (7) Set the width of the LinearLayout to match_parent and the height to wrap_content

    // TO-DONE (8) Add a TextView with an id @+id/tv_weather_data
    // TO-DONE (9) Set the text size to 22sp
    // TO-DONE (10) Make the width and height wrap_content
    // TO-DONE (11) Give the TextView 16dp of padding

    // TO-DO (12) Add a View to the layout with a width of match_parent and a height of 1dp
    // TO-DO (13) Set the background color to #dadada
    // TO-DO (14) Set the left and right margins to 8dp
    // Within forecast_list_item.xml //////////////////////////////////////////////////////////////


    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////
    // TO-DONE (15) Add a class file called ForecastAdapter
    // TO-DONE (22) Extend RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>

    // TO-DONE (23) Create a private string array called mWeatherData

    // TO-DONE (47) Create the default constructor (we will pass in parameters in a later lesson)

    // TO-DONE (16) Create a class within ForecastAdapter called ForecastAdapterViewHolder
    // TO-DONE (17) Extend RecyclerView.ViewHolder

    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////
    // TO-DONE (18) Create a public final TextView variable called mWeatherTextView

    // TO-DONE (19) Create a constructor for this class that accepts a View as a parameter
    // TO-DONE (20) Call super(view) within the constructor for ForecastAdapterViewHolder
    // TO-DONE (21) Using view.findViewById, get a reference to this layout's TextView and save it to mWeatherTextView
    // Within ForecastAdapterViewHolder ///////////////////////////////////////////////////////////


    // TO-DONE (24) Override onCreateViewHolder
    // TO-DONE (25) Within onCreateViewHolder, inflate the list item xml into a view
    // TO-DONE (26) Within onCreateViewHolder, return a new ForecastAdapterViewHolder with the above view passed in as a parameter

    // TO-DONE (27) Override onBindViewHolder
    // TO-DONE (28) Set the text of the TextView to the weather for this list item's position

    // TO-DONE (29) Override getItemCount
    // TO-DONE (30) Return 0 if mWeatherData is null, or the size of mWeatherData if it is not null

    // TO-DONE (31) Create a setWeatherData method that saves the weatherData to mWeatherData
    // TO-DONE (32) After you save mWeatherData, call notifyDataSetChanged
    // Within ForecastAdapter.java /////////////////////////////////////////////////////////////////

    // TO-DONE (33) Delete eWeatherTextView
    private TextView errorTextView;

    // TO-DONE (34) Add a private RecyclerView variable called mRecyclerView
    private RecyclerView mRecyclerView;
    // TO-DONE (35) Add a private ForecastAdapter variable called mForecastAdapter
    private ForecastAdapter mForecastAdapter;


    private ProgressBar eWeatherProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TO-DONE (36) Delete the line where you get a reference to mWeatherTextView
        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        EditText eLocationEditText = findViewById(R.id.et_user_location);

        // TO-DONE (37) Use findViewById to get a reference to the RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview_forecast);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        errorTextView = findViewById(R.id.tv_error_message);

        // TO-DONE (38) Create layoutManager, a LinearLayoutManager with VERTICAL orientation and shouldReverseLayout == false
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        // TO-DONE (39) Set the layoutManager on mRecyclerView
        mRecyclerView.setLayoutManager(layoutManager);

        // TO-DONE (40) Use setHasFixedSize(true) on mRecyclerView to designate that all items in the list will have the same size
        mRecyclerView.setHasFixedSize(true);

        // TO-DONE (41) set mForecastAdapter equal to a new ForecastAdapter
        mForecastAdapter = new ForecastAdapter();

        // TO-DONE (42) Use mRecyclerView.setAdapter and pass in mForecastAdapter
        mRecyclerView.setAdapter(mForecastAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
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
            // TO-DONE (46) Instead of setting the text to "", set the adapter to null before refreshing
            mForecastAdapter.setWeatherData(null);
            loadWeatherData();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadWeatherData() {
        String location = SunshinePreferences.getPreferredWeatherLocation(this);
        showWeatherDataView();
        new GetWeatherDataTask().execute(location);

    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showWeatherDataView() {
        /* First, make sure the error is invisible */
        errorTextView.setVisibility(View.INVISIBLE);
        // TO-DONE (43) Show mRecyclerView, not mWeatherTextView
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the error message visible and hide the weather
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // TO-DONE (44) Hide mRecyclerView, not mWeatherTextView
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        errorTextView.setVisibility(View.VISIBLE);

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
                // TO-DONE (45) Instead of iterating through every string, use mForecastAdapter.setWeatherData and pass in the weather data
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                mForecastAdapter.setWeatherData(weatherData);
            }
            else
                showErrorMessage();
        }
    }
}




