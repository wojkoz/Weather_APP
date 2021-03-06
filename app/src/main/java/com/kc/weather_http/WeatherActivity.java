package com.kc.weather_http;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.time.LocalTime;
import java.time.ZoneId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class WeatherActivity extends AppCompatActivity {
    private TextView time, temp, pressure, humidity, temp_min, temp_max, city_name;
    public static final String sharedKey = "KEY";
    public static final String sharedKeyCityName = "City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        String city = intent.getStringExtra(MainActivity.key);

        city_name = findViewById(R.id.city_name);
        city_name.setText(city);

        time = findViewById(R.id.time);
        time.setText(String.valueOf(LocalTime.now(ZoneId.of("Europe/Warsaw")).withNano(0)));
        temp = findViewById(R.id.temp_c);
        pressure = findViewById(R.id.pressure_c);
        humidity = findViewById(R.id.humidity_c);
        temp_min = findViewById(R.id.temp_min_c);
        temp_max = findViewById(R.id.temp_max_c);

        getWeatherStatus(city + ",pl");

    }
    private void getWeatherStatus(String q){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi json = retrofit.create(JsonPlaceHolderApi.class);

        final String appid = "749561a315b14523a8f5f1ef95e45864";
        Call<Weather> call = json.getWeather(q,appid, "metric");

        call.enqueue(new Callback<Weather>() {
            @Override
            @EverythingIsNonNull
            public void onResponse( Call<Weather> call, Response<Weather> response) {
                if(!response.isSuccessful()) {
                    if(response.code() == 404){
                        finish();
                        return;
                    }
                    time.setText(response.code());
                    return;
                }

                Weather weather = response.body();

                if (weather != null) {
                    temp.setText(weather.getTemp());
                }else
                    temp.setText(R.string._null);

                if (weather != null) {
                    pressure.setText(weather.getPressure());
                }else
                    pressure.setText(R.string._null);

                if (weather != null) {
                    humidity.setText(weather.getHumidity());
                }else
                    humidity.setText(R.string._null);

                if (weather != null) {
                    temp_min.setText(weather.getTemp_min());
                }else
                    temp_min.setText(R.string._null);

                if (weather != null) {
                    temp_max.setText(weather.getTemp_max());
                }else
                    temp_max.setText(R.string._null);

                SharedPreferences sharedPref = getSharedPreferences(sharedKeyCityName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(sharedKey, city_name.getText().toString());
                editor.apply();


            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<Weather> call, Throwable throwable) {
                time.setText(throwable.getMessage());
            }
        });
    }
}
