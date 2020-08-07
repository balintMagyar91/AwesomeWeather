function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
          $("#message").html("Please allow location access for more precise weather information.");
          break;
        case error.POSITION_UNAVAILABLE:
          $("#message").html("Your device doesn't support location access.");
          break;
        case error.TIMEOUT:
          $("#message").html("The request to get your location timed out.");
          break;
        case error.UNKNOWN_ERROR:
          $("#message").html("Sorry but an unknown error occurred. :(");
          break;
    }
}

function setCityAndCountryName(lat, lon) {
    $.get("https://nominatim.openstreetmap.org/reverse?format=json&lat="+lat+"&lon="+lon+"&zoom=10", function(data) {
        country = data.address.country;
        city = data.address.city;
        $("#city").html(country+", "+city);
    });
}

function setWeatherData(result) {
    $("#raw-data").html(JSON.stringify(result));
    var lat = result.lat;
    var lon = result.lon;
    setCityAndCountryName(lat, lon);
    $("#description").html(result.current.weather[0].description);
    $("#wicon").attr('src', result.current.weather[0].icon);
    $("#temp").html(result.current.temp+"°C");
    $("#day").html(result.current.dayOfWeek);
    $("#date").html(result.current.dayAndMonth);
    $("#humidity").append(result.current.humidity + "%");
    $("#wind-speed").append(result.current.wind_speed + "km/h");
    for(var i=0; i<=7; i++) {
        $("#day"+i).html(result.daily[i].dayOfWeek);
        $("#day"+i+"icon").attr('src', result.daily[i].weather[0].icon);
        $("#day"+i+"day").html(result.daily[i].temp.max+"°C");
        $("#day"+i+"night").html(result.daily[i].temp.min+"°C");
    }
}

function getWeatherDataByIP(ip) {
    $.ajax({
      url: "GeoIP",
      type: "POST",
      data: $.param( {ipAddress : ip} ),
      success: setWeatherData
    });
}

function getWeatherDataByPosition(pos) {
    $.ajax({
      url: "Position",
      type: "POST",
      data: $.param( {lat : pos.coords.latitude, lon : pos.coords.longitude} ),
      success: setWeatherData
    });
}

 function locationError(error) {
    showError(error);
    $.get( "https://api.ipify.org?format=json", function( data ) {
        getWeatherDataByIP(data.ip);
    });
 }

$(document).ready(function(){
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            $("#message").html("");
            getWeatherDataByPosition(position);
        }, locationError);
    } else {
        $("#message").html("Geolocation is not supported by this browser.");
    }
});