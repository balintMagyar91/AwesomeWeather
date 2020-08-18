package com.maba.awesomeweather.service;

import com.maba.awesomeweather.model.GeoIP;
import com.maba.awesomeweather.model.owm.api.onecall.OWMOneCallModel;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final DatabaseReader dbReader;

    private final RestTemplate restTemplate;

    private final String appid;
    private final String owmApiUrl;
    private final String osmApiHost;
    private final String ipifyApiUrl;

    @Autowired
    public WeatherServiceImpl(@Value("${owm.api.appid}") String appid,
                              @Value("${owm.api.url}") String owmApiUrl,
                              @Value("${osm.api.host}") String osmApiHost,
                              @Value("${ipify.api.url}") String ipifyApiUrl,
                              RestTemplate restTemplate) throws IOException {

        File database = new File("src/main/resources/GeoLite2/GeoLite2-City.mmdb");
        this.dbReader = new DatabaseReader.Builder(database).build();
        this.restTemplate = restTemplate;
        this.appid = appid;
        this.owmApiUrl = owmApiUrl;
        this.osmApiHost = osmApiHost;
        this.ipifyApiUrl = ipifyApiUrl;
    }

    @Override
    public OWMOneCallModel getCurrentWeatherByIpAddress(String ipAddress) {
        GeoIP geoIP = getLocationByIp(ipAddress);
        if (geoIP == null) {
            // It returns the server ip address. It is useful for testing in localhost.
            String serverIP = restTemplate.getForEntity(ipifyApiUrl, String.class).getBody();
            geoIP = getLocationByIp(serverIP);
        }
        return getOneCallModel(geoIP.getLatitude(), geoIP.getLongitude(), geoIP.getCity());
    }

    @Override
    public OWMOneCallModel getCurrentWeatherByPosition(double lat, double lon) {
        return getOneCallModel(lat, lon);
    }

    @Override
    public GeoIP getLocationByIp(String ip) {
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

    @Override
    public String getLocationByPosition(double lat, double lon) {
        String apiUrl = createUrlForOSM(lat, lon);
        HashMap<String, Map> response = restTemplate.getForObject(apiUrl, HashMap.class);
        return response.get("address").get("city").toString();
    }

    @Override
    public OWMOneCallModel getCurrentWeatherByCityName(String cityName) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(osmApiHost)
                .path("search")
                .queryParam("format", "json")
                .queryParam("city", cityName)
                .build();
        List<HashMap<String, String>> response = restTemplate.getForObject(uri.toUriString(), List.class);
        if (response.size() == 0) return null;
        double lat = Double.parseDouble(response.get(0).get("lat"));
        double lon = Double.parseDouble(response.get(0).get("lon"));
        String foundedCityName = response.get(0).get("display_name");
        return getOneCallModel(lat, lon, foundedCityName);
    }

    private OWMOneCallModel getOneCallModel(double lat, double lon) {
        String apiUrl = createUrlForOWM(lat, lon);
        return restTemplate.getForObject(apiUrl, OWMOneCallModel.class);
    }

    private OWMOneCallModel getOneCallModel(double lat, double lon, String locationName) {
        String apiUrl = createUrlForOWM(lat, lon);
        OWMOneCallModel owm = restTemplate.getForObject(apiUrl, OWMOneCallModel.class);
        owm.setLocationName(locationName);
        return owm;
    }

    private String createUrlForOWM(double lat, double lon) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(owmApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("exclude", "minutely,hourly")
                .queryParam("units", "metric")
                .queryParam("appid", appid);
        return builder.toUriString();
    }

    private String createUrlForOSM(double lat, double lon) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(osmApiHost)
                .queryParam("format", "json")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("zoom", 10);
        return builder.toUriString();
    }
}
