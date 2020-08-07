package com.maba.awesomeweather.model.owm.api.onecall;

/**
 * CurrentWeather class is fit OpenWeatherMap One Call API.
 * It can handle the api respnose "current" params.
 *
 * @see <a href="https://openweathermap.org/api/one-call-api"></a>
 */
public class CurrentWeather extends Weather {
    private double temp;
    private double feels_like;

    public int getTemp() {
        return (int) Math.round(temp);
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getFeels_like() {
        return (int) Math.round(feels_like);
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }
}
