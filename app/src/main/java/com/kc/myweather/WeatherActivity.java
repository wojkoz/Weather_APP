package com.kc.myweather;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class WeatherActivity extends AppCompatActivity {
    private TextView time, temp, pressure, humidity, temp_min, temp_max, city_name;
    private ImageView image;
    public static final String sharedKey = "KEY";
    public static final String sharedKeyCityName = "City";
    private final String ICON_URL = "http://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        final String city = intent.getStringExtra(MainActivity.key);

        city_name = findViewById(R.id.city_name);
        city_name.setText(city);

        time = findViewById(R.id.time);

        temp = findViewById(R.id.temp_c);
        pressure = findViewById(R.id.pressure_c);
        humidity = findViewById(R.id.humidity_c);
        temp_min = findViewById(R.id.temp_min_c);
        temp_max = findViewById(R.id.temp_max_c);
        image = findViewById(R.id.imageView3);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            public void run(){
                getWeatherStatus(city + ",pl");
            }
        };
        timer.schedule(task, 0, 5000);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherStatus(city+",pl"); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

    }


    private void getWeatherStatus(String q){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi json = retrofit.create(JsonPlaceHolderApi.class);

        final String appid = "749561a315b14523a8f5f1ef95e45864";
        Call<WeatherJson> call = json.getWeather(q,appid, "metric");

        call.enqueue(new Callback<WeatherJson>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<WeatherJson> call, Response<WeatherJson> response) {
                if(!response.isSuccessful()) {
                    if(response.code() == 404){
                        finish();
                        return;
                    }
                    time.setText(response.code());
                    return;
                }

                WeatherJson weatherJson = response.body();

                if (weatherJson != null) {
                    temp.setText(weatherJson.getTemp());
                }else
                    temp.setText(R.string._null);

                if (weatherJson != null) {
                    pressure.setText(weatherJson.getPressure());
                }else
                    pressure.setText(R.string._null);

                if (weatherJson != null) {
                    humidity.setText(weatherJson.getHumidity());
                }else
                    humidity.setText(R.string._null);

                if (weatherJson != null) {
                    temp_min.setText(weatherJson.getTemp_min());
                }else
                    temp_min.setText(R.string._null);

                if (weatherJson != null) {
                    temp_max.setText(weatherJson.getTemp_max());
                }else
                    temp_max.setText(R.string._null);
                time.setText(String.valueOf(LocalTime.now(ZoneId.of("Europe/Warsaw")).withNano(0)));


                URL url = null;
                try {
                    url = new URL(ICON_URL + weatherJson.getIconId() + ".png");
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image.setImageBitmap(bmp);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }





                SharedPreferences sharedPref = getSharedPreferences(sharedKeyCityName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(sharedKey, city_name.getText().toString());
                editor.apply();


            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<WeatherJson> call, Throwable throwable) {
                temp.setText(throwable.getMessage());
            }
        });
    }
}
