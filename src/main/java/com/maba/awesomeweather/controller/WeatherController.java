package com.maba.awesomeweather.controller;

import com.maba.awesomeweather.model.owm.api.onecall.OWMOneCallModel;
import com.maba.awesomeweather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @RequestMapping("/")
    public String showIndexPage(Model model, HttpServletRequest request) {
        OWMOneCallModel apiObject = weatherService.getCurrentWeatherByIpAddress(request.getRemoteAddr());
        model.addAttribute("owm", apiObject);
        return "index";
    }

    @GetMapping("/location")
    public String getWeatherByLocation(@RequestParam(value = "cityName") String cityName, Model model) {
        OWMOneCallModel apiObject = weatherService.getCurrentWeatherByCityName(cityName);
        if (apiObject != null) {
            model.addAttribute("owm", apiObject);
        }
        return "index";
    }
}
