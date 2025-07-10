package com.example.git.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfo {

  private String city;
  private String district;
  private String status; // "Güneşli", "Yağmurlu", vs.
  private double temperature;


}