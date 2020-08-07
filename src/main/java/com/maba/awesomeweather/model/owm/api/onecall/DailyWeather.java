package com.maba.awesomeweather.model.owm.api.onecall;

/**
 * DailyWeather class is fit OpenWeatherMap One Call API.
 * It can handle the api respnose "daily" params.
 *
 * @see <a href="https://openweathermap.org/api/one-call-api"></a>
 */
public class DailyWeather extends Weather {
    private DailyTemp temp;

    public DailyTemp getTemp() {
        return temp;
    }

    public void setTemp(DailyTemp temp) {
        this.temp = temp;
    }
}
