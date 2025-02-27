package com.TodaysDotori.controller;

import com.TodaysDotori.domain.Weather;
import com.TodaysDotori.repository.WeatherRepository;
import com.TodaysDotori.service.LocationService;
import com.TodaysDotori.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;
    private final LocationService locationService;
    private final LocationController locationController;

    @Autowired
    public WeatherController(WeatherRepository weatherRepository, WeatherService weatherService, LocationService locationService, LocationController locationController) {
        this.weatherRepository = weatherRepository;
        this.weatherService = weatherService;
        this.locationService = locationService;
        this.locationController = locationController;
    }

    @GetMapping("/today")
    public String getWeather(Model model, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();

            // 경기도
            // String province = (String) session.getAttribute("province");
            // String city = (String) session.getAttribute("city");
            // String cityDistrict = (String) session.getAttribute("cityDistrict");

            // System.out.println("------------ province ->" + province);
            // System.out.println("------------ city ->" + city);
            // System.out.println("------------ cityDistrict ->" + cityDistrict);

            // 서울특별시
            String city = (String) session.getAttribute("city");
            String borough = (String) session.getAttribute("borough");
            String suburb = (String) session.getAttribute("suburb");

            System.out.println("------------ city ->" + city);
            System.out.println("------------ borough ->" + borough);
            System.out.println("------------ suburb ->" + suburb);

            suburb = suburb.replace('·', '.');
            System.out.println("------------ change suburb ->" + suburb);

            Weather weatherQuery = new Weather();
            weatherQuery.setAdministrativeLevel1(city);
            weatherQuery.setAdministrativeLevel2(borough);
            weatherQuery.setAdministrativeLevel3(suburb);

            Optional<Weather> data = weatherRepository.findByAdministrativeLevel1AndAdministrativeLevel2AndAdministrativeLevel3(city, borough, suburb);

            Weather weather = data.get();

            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String weatherData = weatherService.getWeather(today, weather.getGridX(), weather.getGridY());

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

