package com.TodaysDotori.controller;

import com.TodaysDotori.domain.SubwayStation;
import com.TodaysDotori.service.SubwayStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subwayStation")
public class SubwayStationController {
    private final SubwayStationService subwayStationService;

    @Autowired
    public SubwayStationController(SubwayStationService subwayStationService) {
        this.subwayStationService = subwayStationService;
    }

    @GetMapping(value = "/getSubwayStationInfo")
    public SubwayStation getSubwayStationInfoById(String stationCd) {
        return subwayStationService.getSubwayStationInfoByStationCd(stationCd);
    }
}
