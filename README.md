# AwesomeWeather
Spring Boot Weather foreacast webapplication with GeoLocation features

This application shows how a weather api works and how to build a simple foreacast web application with java Spring framework.

### Application features:

- The Geolocation API allows the user to provide their location to web applications.

- If location is not available or user denied the request the application tries to figure it out from the ip address with geoip2.

- The user has the opportunity to find any location on the globe.

- Anyway the application shows the current and the next 7 days weather forecasts according to the Open Weather Map api and the detected or the choosen location.

### Before run:

- You need to download the geolite2 database for ip location: https://dev.maxmind.com/geoip/geoip2/geolite2

- You also need to set your own Open Weather Map API key to use this server: https://openweathermap.org/appid

You have to copy your API key into the application.properties 
```
owm.api.appid={your api key}
```
### Run this application:

```
mvn spring-boot:run
```

