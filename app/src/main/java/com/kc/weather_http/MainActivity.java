package com.kc.weather_http;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    static final String key = "kk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("City",Context.MODE_PRIVATE);

        String city_def = sharedPref.getString(WeatherActivity.sharedKey, " ");
        EditText a = findViewById(R.id.city_input);
        a.setText(city_def);
    }

    public void showCityWeather(View view) {
        EditText a = findViewById(R.id.city_input);
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(key, a.getText().toString());
        startActivity(intent);
    }
}
