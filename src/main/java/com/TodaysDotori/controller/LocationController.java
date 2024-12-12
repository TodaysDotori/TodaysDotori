package com.TodaysDotori.controller;

import com.TodaysDotori.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/location")
public class LocationController {
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "reverse-geocode")
    public ResponseEntity<String> getReverseGeocodeLocation(@RequestParam("lat") double lat, @RequestParam("lon") double lon) {
        try {
            String address = locationService.getReverseGeocode(lat, lon);
            return ResponseEntity.ok(address);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("주소 정보를 가져오는 중 오류가 발생했습니다.");
        }
    }

    /**
     * 반환되는 데이터 Sample (브라우저에서는 이 값을 받아 사용함, 문자열로 반환되기 때문에 JSON.parse해서 사용해야함.)
     * {
     *     "place_id": 206583610,
     *     "licence": "Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright",
     *     "osm_type": "way",
     *     "osm_id": 380080703,
     *     "lat": "37.637596755910245",
     *     "lon": "127.02612751935854",
     *     "class": "highway",
     *     "type": "residential",
     *     "place_rank": 26,
     *     "importance": 0.05340631342030972,
     *     "addresstype": "road",
     *     "name": "오패산로79길",
     *     "display_name": "오패산로79길, 번1동, 강북구, 서울특별시, 01065, 대한민국",
     *     "address": {
     *         "road": "오패산로79길",
     *         "suburb": "번1동",
     *         "borough": "강북구",
     *         "city": "서울특별시",
     *         "ISO3166-2-lvl4": "KR-11",
     *         "postcode": "01065",
     *         "country": "대한민국",
     *         "country_code": "kr"
     *     },
     *     "boundingbox": [
     *         "37.6357368",
     *         "37.6376159",
     *         "127.0242161",
     *         "127.0261478"
     *     ]
     * }
     */
}
