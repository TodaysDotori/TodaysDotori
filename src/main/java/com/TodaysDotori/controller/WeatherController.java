package com.TodaysDotori.controller;

import com.TodaysDotori.domain.Weather;
import com.TodaysDotori.repository.WeatherRepository;
import com.TodaysDotori.service.LocationService;
import com.TodaysDotori.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;
    private final LocationService locationService;

    @Autowired
    public WeatherController(WeatherRepository weatherRepository, WeatherService weatherService, LocationService locationService) {
        this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
        this.locationService = locationService;
    }

    @GetMapping("/today")
    public String getWeather(Model model) {
        try {

//            String address = locationService.getReverseGeocode(lat, lon);
//            String[] parts = address.split(" ");
//
//            System.out.println(parts[0] + parts[1] + parts[2]);
//
//            String administrativeLevel1 = parts[0];
//            String administrativeLevel2 = parts[1];
//            String administrativeLevel3 = parts[2];
//
//            Optional<Weather> locationOptional = weatherRepository.findByAdministrativeLevel1AndAdministrativeLevel2AndAdministrativeLevel3(
//                    administrativeLevel1, administrativeLevel2, administrativeLevel3
//            );
//
//            Weather location = locationOptional.orElseThrow(() -> new RuntimeException("Weather data not found"));
//
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String weatherData = weatherService.getWeather(today);

            model.addAttribute("today", today);
            model.addAttribute("weather", weatherData);

            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "날씨 정보를 가져오는 중 오류가 발생했습니다.");
            return "weather";
        }
    }
}
