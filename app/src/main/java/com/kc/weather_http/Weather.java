package com.kc.weather_http;

import com.google.gson.annotations.SerializedName;

public class Weather {
    private int cod;
    @SerializedName("main")
    private WeatherJsonObject weatherJsonObject;

    public int getCod() {
        return cod;
    }
    public String getTemp(){
        return weatherJsonObject.getTemp().toString() + " C";
    }
    public String getPressure() {
        return weatherJsonObject.getPressure().toString() + "hPa";
    }

    public String getHumidity() {
        return weatherJsonObject.getHumidity().toString() + " %";
    }

    public String getTemp_min() {
        return weatherJsonObject.getTemp_min().toString() + " C";
    }

    public String getTemp_max() {
        return weatherJsonObject.getTemp_max().toString() + " C";
    }
}
