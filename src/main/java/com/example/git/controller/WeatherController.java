package com.example.git.controller;


import com.example.git.dto.WeatherInfo;
import com.example.git.dto.WeatherInfoFilterDTO;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Slf4j
public class WeatherController {
  
  //volkan branch commiti

  //conflict için ben de değişiklik yaptım

  // ✅ GET: RequestParam örneği + Cacheable
  @GetMapping("/getByParam")
  public WeatherInfo getWeatherByParam(
      @RequestParam String city,
      @RequestParam String district) {
    return new WeatherInfo(city, district, "Yağmurlu", 2d);
  }

  // ✅ GET: RequestParam örneği + Cacheable
  @GetMapping("/getList")
  public ResponseEntity<List<WeatherInfo>> getList() {
    List<WeatherInfo> list = new ArrayList<>();
    list.add(new WeatherInfo("Ankara", "Çankaya", "Yağmurlu", 2d));
    list.add(new WeatherInfo("Ankara", "Keçiören", "Yağmurlu", 2d));
    list.add(new WeatherInfo("Ankara", "Yenimahalle", "Yağmurlu", 2d));
    return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(list);

  }

  @Cacheable("list-cache")
  @GetMapping("/getListWithCache")
  public ResponseEntity<List<WeatherInfo>> getListWithCache() {
    List<WeatherInfo> list = new ArrayList<>();
    list.add(new WeatherInfo("Ankara", "Çankaya", "Yağmurlu", 2d));
    list.add(new WeatherInfo("Ankara", "Keçiören", "Yağmurlu", 2d));
    list.add(new WeatherInfo("Ankara", "Yenimahalle", "Yağmurlu", 2d));
    return ResponseEntity.ok(list);

  }

  // ✅ GET: PathVariable örneği
  @GetMapping("/{city}/{district}")
  public WeatherInfo getByPath(@PathVariable String city, @PathVariable String district) {
    return new WeatherInfo(city, district, "Yağmurlu", 2d);
  }

  // ✅ POST: Yeni veri oluştur
  @PostMapping
  public ResponseEntity<String> addWeather(@RequestBody WeatherInfo info) {
    log.error(info.toString());
    return ResponseEntity.ok("Weather added");
  }

  // ✅ POST: Yeni veri oluştur
  @PostMapping("/addWeatherWithValidation")
  public ResponseEntity<String> addWeatherWithValidation(
      @Valid @RequestBody WeatherInfoFilterDTO info) {
    log.error(info.toString());
    return ResponseEntity.ok("Weather added");
  }

  // ✅ PUT: Tüm veriyi güncelle
  @PutMapping("/{city}/{district}")
  public ResponseEntity<String> updateWeather(
      @PathVariable String city,
      @PathVariable String district,
      @RequestBody WeatherInfo updated) {
    updated.setCity(city);
    updated.setDistrict(district);
    log.error("Gelen city değeri : " + city + " / gelen district değeri : " + district);
    log.error(updated.toString());
    return ResponseEntity.ok("Weather updated with PUT");
  }

  // ✅ PATCH: Sadece sıcaklık güncelle
  @PatchMapping("/{city}/{district}/temperature")
  public ResponseEntity<String> patchTemperature(
      @PathVariable String city,
      @PathVariable String district,
      @RequestBody Map<String, Double> body) {
    WeatherInfo weatherInfo = new WeatherInfo(city, district, "Yağmurlu", 2d);
    weatherInfo.setTemperature(body.get("temperature"));
    log.error(weatherInfo.toString());
    return ResponseEntity.ok("Temperature updated with PATCH");
  }

  // ✅ DELETE: Veri sil
  @DeleteMapping("/{city}/{district}")
  public ResponseEntity<String> deleteWeather(@PathVariable String city,
      @PathVariable String district) {
    log.error("Gelen city değeri : " + city + " / gelen district değeri : " + district);
    return ResponseEntity.ok("Weather deleted");
  }

  @PostMapping(value = "/upload/image", consumes = MediaType.IMAGE_PNG_VALUE)
  public ResponseEntity<String> uploadImage(@RequestBody byte[] imageBytes) {
    return ResponseEntity.ok("Görüntü alındı, boyutu: " + imageBytes.length);
  }
}
