package com.TodaysDotori.controller;

import com.TodaysDotori.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/api/location")
public class LocationController {
    private final LocationService locationService;
    private final ObjectMapper objectMapper;

    public LocationController(LocationService locationService, ObjectMapper objectMapper) {
        this.locationService = locationService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/reverse-geocode")
    public ResponseEntity<String> getReverseGeocodeLocation(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon,
            HttpServletRequest request
    ) {
        try {
            log.info("lat : {}, lon : {}", lat, lon);

            HttpSession session = request.getSession();
            session.setAttribute("lat", lat);
            session.setAttribute("lon", lon);

            String addressJson = locationService.getReverseGeocode(lat, lon);
            Map<String, Object> addressMap = objectMapper.readValue(addressJson, Map.class);
            Map<String, String> addressDetails = (Map<String, String>) addressMap.get("address");

            saveLocationToSession(session, addressDetails);

            return ResponseEntity.ok(addressJson);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주소 정보를 가져오는 중 오류가 발생했습니다.");
        }
    }

    private void saveLocationToSession(HttpSession session, Map<String, String> addressDetails) {
        session.setAttribute("city", addressDetails.get("city"));
        session.setAttribute("borough", addressDetails.get("borough"));
        session.setAttribute("suburb", addressDetails.get("suburb"));
    }
}

/**
 * 반환되는 데이터 Sample (브라우저에서는 이 값을 받아 사용함, 문자열로 반환되기 때문에 JSON.parse해서 사용해야함.)
 * {
 * "place_id": 206583610,
 * "licence": "Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
 * "osm_type": "way",
 * "osm_id": 380080703,
 * "lat": "37.637596755910245",
 * "lon": "127.02612751935854",
 * "class": "highway",
 * "type": "residential",
 * "place_rank": 26,
 * "importance": 0.05340631342030972,
 * "addresstype": "road",
 * "name": "오패산로79길",
 * "display_name": "오패산로79길, 번1동, 강북구, 서울특별시, 01065, 대한민국",
 * "address": {
 * "road": "오패산로79길",
 * "suburb": "번1동",
 * "borough": "강북구",
 * "city": "서울특별시",
 * "ISO3166-2-lvl4": "KR-11",
 * "postcode": "01065",
 * "country": "대한민국",
 * "country_code": "kr"
 * },
 * "boundingbox": [
 * "37.6357368",
 * "37.6376159",
 * "127.0242161",
 * "127.0261478"
 * ]
 * }
 */

