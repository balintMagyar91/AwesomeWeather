package com.maba.awesomeweather.service;

import com.maba.awesomeweather.model.GeoIP;
import com.maba.awesomeweather.model.owm.api.onecall.OWMOneCallModel;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class WeatherServiceImpl implements WeatherService {

    private DatabaseReader dbReader;

    private RestTemplate restTemplate;

    private String appid;

    private String owmApiUrl;

    @Autowired
    public WeatherServiceImpl(@Value("${owm.api.appid}") String appid,
                              @Value("${owm.api.url}") String owmApiUrl) throws IOException {

        File database = new File("src/main/resources/GeoLite2/GeoLite2-City.mmdb");
        this.dbReader = new DatabaseReader.Builder(database).build();
        this.restTemplate = new RestTemplate();
        this.appid = appid;
        this.owmApiUrl = owmApiUrl;
    }

    @Override
    public OWMOneCallModel getCurrentWeatherByIpAddress(String ipAddress) {
        GeoIP geoIP = getLocation(ipAddress);
        return getOneCallModel(geoIP.getLatitude(), geoIP.getLongitude());
    }

    @Override
    public OWMOneCallModel getCurrentWeatherByPosition(double lat, double lon) {
        return getOneCallModel(lat, lon);
    }

    @Override
    public GeoIP getLocation(String ip) {
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CityResponse response = dbReader.city(ipAddress);

            String cityName = response.getCity().getName();
            double latitude = response.getLocation().getLatitude();
            double longitude = response.getLocation().getLongitude();
            return new GeoIP(ip, cityName, latitude, longitude);
        } catch (Exception e) {
            return null;
        }
    }

    private OWMOneCallModel getOneCallModel(double lat, double lon) {
        String apiUrl = createUrlForOWM(lat, lon);
        return restTemplate.getForObject(apiUrl, OWMOneCallModel.class);
    }

    private String createUrlForOWM(double lat, double lon) {
        String url = owmApiUrl;
        url += "?lat="+lat;
        url += "&lon="+lon;
        url += "&exclude="+"minutely,hourly";
        url += "&units="+"metric";
        //url += "&lang="+"hu";
        url += "&appid="+appid;
        return url;
    }
}
