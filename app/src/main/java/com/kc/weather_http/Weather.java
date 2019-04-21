package com.kc.weather_http;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    public WeatherJsonObject weatherJsonObject;
}
