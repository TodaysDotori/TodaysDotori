package com.TodaysDotori.client.bus;

import com.TodaysDotori.dto.bus.TagoBusStop;
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
public class TagoBusClient {
    @Value("${bus.api.encode.key}")
    private String ENCODE_BUS_API_KEY;

    /**
     * 좌표기반 근접 정류소 조회
     *
     * @param gpsLati WGS84 위도좌표
     * @param gpsLong WGS84 경도좌표
     * @return List<TagoBusStop>
     */
    public List<TagoBusStop> getStationsByPosList(double gpsLati, double gpsLong) throws IOException {
        int pageNo = 1; // 페이지번호
        int numOfRows = 9999; // 한 페이지 결과 수
        String _type = "json"; // "json" or "xml"

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/BusSttnInfoInqireService/getCrdntPrxmtSttnList"); /*URL*/
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(ENCODE_BUS_API_KEY); /*Service Key*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8)); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("_type", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(_type, StandardCharsets.UTF_8)); /*데이터 타입(xml, json)*/
        urlBuilder.append("&").append(URLEncoder.encode("gpsLati", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(gpsLati), StandardCharsets.UTF_8)); /*WGS84 위도 좌표*/
        urlBuilder.append("&").append(URLEncoder.encode("gpsLong", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(gpsLong), StandardCharsets.UTF_8)); /*WGS84 경도 좌표*/

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
     * JSON 응답을 List<TagoBusStop>으로 변환
     */
    private List<TagoBusStop> parseJson(String jsonResponse) {
        List<TagoBusStop> busStops = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);

            // response 추출
            JSONObject response = (JSONObject) jsonObject.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemList = (JSONArray) items.get("item");
            if (itemList.isEmpty()) {
                return busStops;
            }

            // itemList 내부 객체를 순회하면서 SeoulBusStop DTO로 변환
            for (Object obj : itemList) {
                JSONObject item = (JSONObject) obj;

                int cityCode = ((Long) item.get("citycode")).intValue();
                String nodeId = (String) item.get("nodeid");
                String nodeNm = (String) item.get("nodenm");
                double gpsLati = (double) item.get("gpslati");
                double gpsLong = (double) item.get("gpslong");

                TagoBusStop busStop = new TagoBusStop(nodeId, nodeNm, gpsLati, gpsLong, cityCode);

                busStops.add(busStop);
            }

        } catch (ParseException e) {
            log.error("JSON 파싱 오류: {}", e.getMessage());
        }

        return busStops;
    }
}
