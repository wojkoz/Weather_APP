package com.kc.myweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("weather")
    Call<WeatherJson> getWeather(
            @Query("q") String city_name,
            @Query("APPID") String appid,
            @Query("units") String units
    );
}
