package com.TodaysDotori.service;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    private String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String getWeather(String today, int nx, int ny) throws IOException {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String currentTime = sdf.format(calendar.getTime());

        String hourMinute = currentTime.substring(8, 12);

        int minute = Integer.parseInt(hourMinute.substring(2, 4));

        int roundedMinute = (minute / 10) * 10;
        if (minute % 10 >= 5) {
            roundedMinute += 10;
        }

        String baseTime = currentTime.substring(8, 10) + String.format("%02d", roundedMinute);

        System.out.println("-------------------------------------- baseTime : " + baseTime);
        System.out.println("-------------------------------------- nx : " + nx + " ny : " + ny);

        StringBuilder urlBuilder = new StringBuilder(apiUrl);

        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + "1");
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + "1000");
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + "JSON");
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + today);
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + baseTime);
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + nx);
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + ny);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        System.out.println("Content-Type: " + conn.getHeaderField("Content-Type"));

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        StringBuilder result = new StringBuilder();

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(String.valueOf(sb));

            JSONObject response = (JSONObject)jsonObject.get("response");
            JSONObject body = (JSONObject)response.get("body");
            JSONObject items = (JSONObject)body.get("items");

            JSONArray item = (JSONArray)items.get("item");

            for (int i = 0; i < item.size(); i++) {
                JSONObject data = (JSONObject) item.get(i);

                String category = (String) data.get("category");
                String value = (String) data.get("obsrValue");

                System.out.println("Category: " + category);
                System.out.println("Observation Value: " + value);

                if ("T1H".equals(category)) { // 기온
                    Double temperature = parseDoubleSafe(value);
                    System.out.println("Temperature: " + temperature + "°C");
                    result.append("Temperature: " + temperature + "°C\n");
                } else if ("RN1".equals(category)) { // 1시간 강수량
                    Double precipitation = parseDoubleSafe(value);
                    System.out.println("Precipitation: " + precipitation + "mm");
                    result.append("Precipitation: " + precipitation + "mm\n");
                } else if ("WSD".equals(category)) { // 풍속
                    Double windSpeed = parseDoubleSafe(value);
                    System.out.println("Wind Speed: " + windSpeed + "m/s");
                    result.append("Wind Speed: " + windSpeed + "m/s\n");
                } else if ("REH".equals(category)) { // 습도
                    Double humidity = parseDoubleSafe(value);
                    System.out.println("Humidity: " + humidity + "%");
                    result.append("Humidity: " + humidity + "%\n");
                } else if ("PTY".equals(category)) { // 강수형태
                    Double pty = parseDoubleSafe(value);
                    System.out.println("Precipitation Type: " + pty);
                    result.append("Precipitation Type: " + pty + "\n");
                } else if ("UUU".equals(category)) { // 동서바람성분
                    Double uuu = parseDoubleSafe(value);
                    System.out.println("U/U Value: " + uuu);
                    result.append("U/U Value: " + uuu + "\n");
                } else if ("VEC".equals(category)) { // 풍향
                    Double windDirection = parseDoubleSafe(value);
                    System.out.println("Wind Direction: " + windDirection + "°");
                    result.append("Wind Direction: " + windDirection + "°\n");
                } else if ("VVV".equals(category)) { // 납북바람성분
                    Double vvv = parseDoubleSafe(value);
                    System.out.println("Visibility: " + vvv);
                    result.append("Visibility: " + vvv + "\n");
                } else {
                    System.out.println("Unrecognized Category: " + category);
                    result.append("Unrecognized Category: " + category + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private Double parseDoubleSafe(String value) {
        if (value == null) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
