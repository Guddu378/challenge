package com.example.demo.service;

import com.example.demo.dto.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

  private RestTemplate restTemplate = new RestTemplate();

  @Value("${weather.api.key}")
  private String apiKey;

  @Value("${weather.base.url}")
  private String baseURL;


  public WeatherData getWeatherDataByLonAndLat(final String lat, final String lon){
    String URL = buildCompleteURl(lat, lon);
    WeatherData response = restTemplate.getForObject(URL, WeatherData.class);

    return response;
  }

  private String buildCompleteURl(final String lat, final String lon){
    return new StringBuilder().
        append(baseURL).
        append("lat=").
        append(lat).
        append("&lon=").
        append(lon).
        append("&appid=").
        append(apiKey).toString();
  }

}
