package com.example.demo.dto;

public class Main {
  private String temp;

  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }

  public String getPressure() {
    return pressure;
  }

  public void setPressure(String pressure) {
    this.pressure = pressure;
  }

  public String getTemp_min() {
    return temp_min;
  }

  public void setTemp_min(String temp_min) {
    this.temp_min = temp_min;
  }

  public String getTemp_max() {
    return temp_max;
  }

  public void setTemp_max(String temp_max) {
    this.temp_max = temp_max;
  }

  public String getHumidity() {
    return humidity;
  }

  public void setHumidity(String humidity) {
    this.humidity = humidity;
  }

  private String pressure;
  private String temp_min;
  private String temp_max;
  private String humidity;


}
