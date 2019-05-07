package com.kc.myweather;

import com.google.gson.annotations.SerializedName;
import com.kc.myweather.weather_icon.Weather_icon;

public class WeatherJson {
    @SerializedName("main")
    private Weather_main weatherMain;
    @SerializedName("weather")
    private Weather_icon[] icon;

    public String getTemp(){
        return weatherMain.getTemp().toString() + " C";
    }
    public String getPressure() {
        return weatherMain.getPressure().toString() + "hPa";
    }

    public String getHumidity() {
        return weatherMain.getHumidity().toString() + " %";
    }

    public String getTemp_min() {
        return weatherMain.getTemp_min().toString() + " C";
    }

    public String getTemp_max() {
        return weatherMain.getTemp_max().toString() + " C";
    }

    public String getIconId(){
        return icon[0].getIcon();
    }
}
