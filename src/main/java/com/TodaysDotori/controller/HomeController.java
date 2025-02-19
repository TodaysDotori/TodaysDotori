package com.TodaysDotori.controller;

import com.TodaysDotori.repository.WeatherRepository;
import com.TodaysDotori.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final WeatherController weatherController;
    private final LocationController locationController;
    private final LocationService locationService;
    private final WeatherRepository weatherRepository;

    @Autowired
    public HomeController(WeatherController weatherController, LocationController locationController, LocationService locationService, WeatherRepository weatherRepository) {
        this.weatherController = weatherController;
        this.locationController = locationController;
        this.locationService = locationService;
        this.weatherRepository = weatherRepository;
    }

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        try {
            // TODO 확인 필요 weatherController.getWeather(model, request);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "날씨 정보를 가져오는 중 오류가 발생했습니다.");
            return "index";
        }
    }
}