package com.TodaysDotori.repository;

import com.TodaysDotori.domain.SubwayStation;
import com.TodaysDotori.domain.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WeatherRepository extends MongoRepository<Weather, String> {
    Optional<Weather> findByAdministrativeLevel1AndAdministrativeLevel2AndAdministrativeLevel3(
            String administrativeLevel1,
            String administrativeLevel2,
            String administrativeLevel3
    );
}
