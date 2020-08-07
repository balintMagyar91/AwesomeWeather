package com.maba.awesomeweather.model.owm.api.onecall;

public class WeatherData {
    private int id;
    private String main;
    private String description;
    private String icon;

    public WeatherData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return "http://openweathermap.org/img/wn/"+icon+"@2x.png";
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
