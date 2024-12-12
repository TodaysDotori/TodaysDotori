package com.TodaysDotori.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LocationServiceImpl implements LocationService {
    private final WebClient webClient;

    public LocationServiceImpl(WebClient.Builder webClientBuilder) {
        // OpenStreetMap API Base URL 설정
        this.webClient = webClientBuilder.baseUrl("https://nominatim.openstreetmap.org").build();
    }

    @Override
    public String getReverseGeocode(double lat, double lon) {
        String apiUrl = String.format("/reverse?format=json&lat=%f&lon=%f", lat, lon);

        return this.webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
