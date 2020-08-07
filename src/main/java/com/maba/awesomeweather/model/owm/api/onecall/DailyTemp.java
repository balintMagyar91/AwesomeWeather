package com.maba.awesomeweather.model.owm.api.onecall;

public class DailyTemp {
    private double day;
    private double min;
    private double max;
    private double eve;
    private double morn;
    private double night;

    public DailyTemp() {
    }

    public int getDay() {
        return (int)Math.round(day);
    }

    public void setDay(double day) {
        this.day = day;
    }

    public int getMin() {
        return (int)Math.round(min);
    }

    public void setMin(double min) {
        this.min = min;
    }

    public int getMax() {
        return (int)Math.round(max);
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getEve() {
        return (int)Math.round(eve);
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public int getMorn() {
        return (int)Math.round(morn);
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public int getNight() {
        return (int)Math.round(night);
    }

    public void setNight(double night) {
        this.night = night;
    }
}
