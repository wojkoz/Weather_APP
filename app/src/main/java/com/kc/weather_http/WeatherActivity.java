package com.kc.weather_http;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.ZoneId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    private TextView time, temp, preassure, humidity, temp_min, temp_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        String city = intent.getStringExtra(MainActivity.key);

        TextView city_name = findViewById(R.id.city_name);
        city_name.setText(city);

        time = findViewById(R.id.time);
        time.setText(String.valueOf(LocalTime.now(ZoneId.of("Europe/Warsaw")).withNano(0)));
        temp = findViewById(R.id.temp_c);
        preassure = findViewById(R.id.preassure_c);
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
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(!response.isSuccessful()){
                    time.setText(response.code());
                    return;
                }
                Weather weather = response.body();
                temp.setText(weather.weatherJsonObject.getTemp().toString() + " C");
                preassure.setText(weather.weatherJsonObject.getPressure().toString() + " hPa");
                humidity.setText(weather.weatherJsonObject.getHumidity().toString() + " %");
                temp_min.setText(weather.weatherJsonObject.getTemp_min().toString() + " C");
                temp_max.setText(weather.weatherJsonObject.getTemp_max().toString() + " C");
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable throwable) {
                finish();
            }
        });
    }
}
