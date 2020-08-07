package com.maba.awesomeweather.model.owm.api.onecall;

public class OWMOneCallModel {
    private double lat;
    private double lon;
    private String timezone;
    private String timezone_offset;
    private CurrentWeather current;
    private DailyWeather[] daily;

    public OWMOneCallModel() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(String timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }

    public DailyWeather[] getDaily() {
        return daily;
    }

    public void setDaily(DailyWeather[] daily) {
        this.daily = daily;
    }
}
