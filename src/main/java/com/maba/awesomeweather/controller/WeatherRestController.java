package com.maba.awesomeweather.controller;

import com.maba.awesomeweather.model.owm.api.onecall.OWMOneCallModel;
import com.maba.awesomeweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherRestController {

    @Autowired
    WeatherService weatherService;

    @PostMapping("/GeoIP")
    public OWMOneCallModel getLocation(@RequestParam(value="ipAddress", required=true) String ipAddress) throws Exception {
        return weatherService.getCurrentWeatherByIpAddress(ipAddress);
    }

    @PostMapping("/Position")
    public OWMOneCallModel getWeatherByPosition(@RequestParam(value="lat", required=true) double lat,
                                     @RequestParam(value="lon", required=true) double lon) throws Exception {
        return weatherService.getCurrentWeatherByPosition(lat, lon);
    }
}
