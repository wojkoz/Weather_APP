package com.kc.myweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String key = "kk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button check_weather = findViewById(R.id.check_weather);

        SharedPreferences sharedPref = getSharedPreferences(WeatherActivity.sharedKeyCityName,Context.MODE_PRIVATE);

        String city_def = sharedPref.getString(WeatherActivity.sharedKey, " ");
        EditText a = findViewById(R.id.city_input);
        a.setText(city_def);

        if(isNetworkAvailable()) {
            check_weather.setEnabled(true);
        }
        else{
            check_weather.setEnabled(false);
            Toast.makeText(this, "No Internet access!",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void showCityWeather(View view) {
        EditText a = findViewById(R.id.city_input);
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(key, a.getText().toString());
        startActivity(intent);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
