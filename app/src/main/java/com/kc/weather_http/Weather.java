package com.kc.weather_http;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("main")
    private Weather_main weatherMain;

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
}
