package com.TodaysDotori;

import com.TodaysDotori.controller.WeatherController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final WeatherController weatherController;

    @Autowired
    public HomeController(WeatherController weatherController) {
        this.weatherController = weatherController;
    }

    @GetMapping("/")
    public String home(Model model) {
        try {
            weatherController.getWeather(model);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "날씨 정보를 가져오는 중 오류가 발생했습니다.");
            return "index";
        }
    }
}