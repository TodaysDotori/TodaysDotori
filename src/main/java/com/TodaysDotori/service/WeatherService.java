package com.TodaysDotori.service;

import java.io.IOException;

public interface WeatherService {

    // String getWeather(String today) throws IOException;
    String getWeather(String today, int nx, int ny) throws IOException;
}
