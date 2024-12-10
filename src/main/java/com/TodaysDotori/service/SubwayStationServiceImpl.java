package com.TodaysDotori.service;

import com.TodaysDotori.domain.SubwayStation;
import com.TodaysDotori.repository.SubwayStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubwayStationServiceImpl implements SubwayStationService {
    private final SubwayStationRepository subwayStationRepository;

    @Autowired
    public SubwayStationServiceImpl(SubwayStationRepository subwayStationRepository) {
        this.subwayStationRepository = subwayStationRepository;
    }

    @Override
    public SubwayStation getSubwayStationInfoByStationCd(String stationCd) {
        return subwayStationRepository.findByStationCd(stationCd);
    }
}
