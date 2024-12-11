package com.TodaysDotori.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "weather")
public class Weather {
    @Id
    private String id;
    private String contryType;
    private String administrativeDivisionCode;
    private String administrativeLevel1;
    private String administrativeLevel2;
    private String administrativeLevel3;
    private String gridX;
    private String gridY;
    private String longitudeDegree;
    private String longitudeMinute;
    private String longitudeSecond;
    private String latitudeDegree;
    private String latitudeMinute;
    private String latitudeSecond;
    private String longitudeSecondHundredth;
    private String latitudeSecondHundredth;
    private String updateLoation;
}
