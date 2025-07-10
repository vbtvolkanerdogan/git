package com.example.git.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfoFilterDTO {

  @NotNull
  private String city;
  private String district;
  private String status; // "Güneşli", "Yağmurlu", vs.
  @Min(5)
  private Double temperature;


}