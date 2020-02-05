package com.example.demo.rest;

import com.example.demo.dto.WeatherData;
import com.example.demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
  @Autowired
  private WeatherService weatherService;

  @GetMapping(value = "/v1/weather-data/{lat}/{lon}")
  public WeatherData getWeatherDetailsByLagLot(@PathVariable final String lat, @PathVariable final String lon){
    return weatherService.getWeatherDataByLonAndLat(lat, lon);
  }
}
