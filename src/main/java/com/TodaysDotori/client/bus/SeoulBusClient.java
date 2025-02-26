package com.TodaysDotori.client.bus;

import com.TodaysDotori.dto.bus.SeoulBusStop;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SeoulBusClient {
    @Value("${bus.api.encode.key}")
    private String ENCODE_BUS_API_KEY;

    /**
     * 좌표기반 근접 정류소 조회
     *
     * @param tmX 기준위치 X
     * @param tmY 기준위치 Y
     * @return List<SeoulBusStop>
     */
    public List<SeoulBusStop> getStationsByPosList(double tmY, double tmX) throws IOException {
        int radius = 500; // 검색반경 500미터
        String resultType = "json"; // "json" or "xml"

        StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos");
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(ENCODE_BUS_API_KEY);
        urlBuilder.append("&").append(URLEncoder.encode("tmX", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(tmX), StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("tmY", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(tmY), StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("radius", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(radius), StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("resultType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(resultType), StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        log.info("Response code: {}", conn.getResponseCode());

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

        return parseJson(sb.toString());
    }

    /**
     * JSON 응답을 List<SeoulBusStop>으로 변환
     */
    private List<SeoulBusStop> parseJson(String jsonResponse) {
        List<SeoulBusStop> busStops = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            // msgBody 추출
            JSONObject msgBody = (JSONObject) jsonObject.get("msgBody");
            if (msgBody == null) {
                return busStops;
            }

            // itemList 배열 추출
            JSONArray itemList = (JSONArray) msgBody.get("itemList");
            if (itemList == null) {
                return busStops;
            }

            // itemList 내부 객체를 순회하면서 SeoulBusStop DTO로 변환
            for (Object obj : itemList) {
                JSONObject item = (JSONObject) obj;

                String stationId = (String) item.get("stationId");
                String stationNm = (String) item.get("stationNm");
                double gpsX = Double.parseDouble((String) item.get("gpsX"));
                double gpsY = Double.parseDouble((String) item.get("gpsY"));
                String arsId = (String) item.get("arsId");
                double posX = Double.parseDouble((String) item.get("posX"));
                double posY = Double.parseDouble((String) item.get("posY"));
                double dist = Double.parseDouble((String) item.get("dist"));
                String stationTp = (String) item.get("stationTp");

                // SeoulBusStop 객체 생성 후 리스트에 추가
                SeoulBusStop busStop = new SeoulBusStop(stationId, stationNm, gpsY, gpsX, arsId, posX, posY, dist, stationTp);
                busStops.add(busStop);
            }

        } catch (ParseException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
        }

        return busStops;
    }
}
