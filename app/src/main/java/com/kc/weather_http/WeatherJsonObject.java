package com.kc.weather_http;

public class WeatherJsonObject {
    private Double temp;
    private Integer pressure;
    private Integer humidity;
    private Double temp_min;
    private Double temp_max;

    public Double getTemp() {
        return temp;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }
}
