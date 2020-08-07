package com.maba.awesomeweather.model.owm.api.onecall;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Weather class is the parent class of CurrentWeather and DailyWeather. The only diffrence between them is the temperature
 * type. This class contains various data to fit OpenWeatherMap One Call API.
 *
 * @see <a href="https://openweathermap.org/api/one-call-api"></a>
 *
 */
public class Weather {
    private long dt;
    private long sunrise;
    private long sunset;
    private int humidity;
    private double wind_speed;
    private int wind_deg;
    private WeatherData[] weather;

    public Weather() {
    }

    public long getDt() {
        return dt;
    }

    public String getDayOfWeek() {
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dt*1000);
    }

    public String getDayAndMonth() {
        return new SimpleDateFormat("d MMM").format(dt*1000);
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWind_speed() {
        return (int) Math.round(wind_speed*3600/1000);
    }

    public void setWind_speed(double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public int getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(int wind_deg) {
        this.wind_deg = wind_deg;
    }

    public WeatherData[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherData[] weather) {
        this.weather = weather;
    }
}
