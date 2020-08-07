package com.maba.awesomeweather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeatherController {

    @RequestMapping("/")
    public String showIndexPage(){
        return "index";
    }
}
