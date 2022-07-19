package com.inonidakii.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.inonidakii.sunshine.utilities.NetworkUtils;

import java.io.IOException;
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            loadWeatherData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadWeatherData() {
        String request = eLocationEditText.getText().toString();
        if (!request.equals("")) {
            URL requestUrl = NetworkUtils.buildUrl(request);
            new GetWeatherDataTask().execute(requestUrl);
        } else {
            eWeatherTextView.setText("Enter request en try again");
        }

    }

    class GetWeatherDataTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = NetworkUtils.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "check your connection en try again", Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String weatherData) {
            super.onPostExecute(weatherData);
            if (weatherData != null && !weatherData.equals("")) {
                eWeatherTextView.setText(weatherData);
            } else {
                eWeatherTextView.setText("Check your query and try again");
            }


        }
    }

}