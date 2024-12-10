package com.TodaysDotori.repository;

import com.TodaysDotori.domain.SubwayStation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubwayStationRepository extends MongoRepository<SubwayStation, String> {
    SubwayStation findByStationCd(String stationCd);
}
