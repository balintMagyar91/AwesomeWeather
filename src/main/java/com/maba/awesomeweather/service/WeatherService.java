package com.maba.awesomeweather.service;

import com.maba.awesomeweather.model.GeoIP;
import com.maba.awesomeweather.model.owm.api.onecall.OWMOneCallModel;

public interface WeatherService {

    OWMOneCallModel getCurrentWeatherByIpAddress(String ipAddress);

    OWMOneCallModel getCurrentWeatherByPosition(double lat, double lon);

    GeoIP getLocation(String ipAddress);
}
