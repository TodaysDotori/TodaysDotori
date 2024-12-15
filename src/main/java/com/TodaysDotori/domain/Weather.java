package com.TodaysDotori.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "weather")
public class Weather {
    @Id
    private String id;
    private String contryType;
    private String administrativeDivisionCode;
    private String administrativeLevel1;
    private String administrativeLevel2;
    private String administrativeLevel3;
    private int gridX;
    private int gridY;
    private String longitudeDegree;
    private String longitudeMinute;
    private String longitudeSecond;
    private String latitudeDegree;
    private String latitudeMinute;
    private String latitudeSecond;
    private String longitudeSecondHundredth;
    private String latitudeSecondHundredth;
    private String updateLoation;

    public String getUpdateLoation() {
        return updateLoation;
    }

    public void setUpdateLoation(String updateLoation) {
        this.updateLoation = updateLoation;
    }

    public String getLatitudeSecondHundredth() {
        return latitudeSecondHundredth;
    }

    public void setLatitudeSecondHundredth(String latitudeSecondHundredth) {
        this.latitudeSecondHundredth = latitudeSecondHundredth;
    }

    public String getLongitudeSecondHundredth() {
        return longitudeSecondHundredth;
    }

    public void setLongitudeSecondHundredth(String longitudeSecondHundredth) {
        this.longitudeSecondHundredth = longitudeSecondHundredth;
    }

    public String getLatitudeSecond() {
        return latitudeSecond;
    }

    public void setLatitudeSecond(String latitudeSecond) {
        this.latitudeSecond = latitudeSecond;
    }

    public String getLatitudeMinute() {
        return latitudeMinute;
    }

    public void setLatitudeMinute(String latitudeMinute) {
        this.latitudeMinute = latitudeMinute;
    }

    public String getLatitudeDegree() {
        return latitudeDegree;
    }

    public void setLatitudeDegree(String latitudeDegree) {
        this.latitudeDegree = latitudeDegree;
    }

    public String getLongitudeSecond() {
        return longitudeSecond;
    }

    public void setLongitudeSecond(String longitudeSecond) {
        this.longitudeSecond = longitudeSecond;
    }

    public String getLongitudeMinute() {
        return longitudeMinute;
    }

    public void setLongitudeMinute(String longitudeMinute) {
        this.longitudeMinute = longitudeMinute;
    }

    public String getLongitudeDegree() {
        return longitudeDegree;
    }

    public void setLongitudeDegree(String longitudeDegree) {
        this.longitudeDegree = longitudeDegree;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public String getAdministrativeLevel3() {
        return administrativeLevel3;
    }

    public void setAdministrativeLevel3(String administrativeLevel3) {
        this.administrativeLevel3 = administrativeLevel3;
    }

    public String getAdministrativeLevel2() {
        return administrativeLevel2;
    }

    public void setAdministrativeLevel2(String administrativeLevel2) {
        this.administrativeLevel2 = administrativeLevel2;
    }

    public String getAdministrativeLevel1() {
        return administrativeLevel1;
    }

    public void setAdministrativeLevel1(String administrativeLevel1) {
        this.administrativeLevel1 = administrativeLevel1;
    }

    public String getAdministrativeDivisionCode() {
        return administrativeDivisionCode;
    }

    public void setAdministrativeDivisionCode(String administrativeDivisionCode) {
        this.administrativeDivisionCode = administrativeDivisionCode;
    }

    public String getContryType() {
        return contryType;
    }

    public void setContryType(String contryType) {
        this.contryType = contryType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
