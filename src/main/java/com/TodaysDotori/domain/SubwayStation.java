package com.TodaysDotori.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subwayStation")
public class SubwayStation {
    @Id
    private String id;
    private String lineNum;
    private String stationCd;
    private StationName stationNm;
    private String frCode;

    public static class StationName {
        private String kor;
        private String chn;
        private String jpn;
        private String eng;

        public String getKor() {
            return kor;
        }

        public void setKor(String kor) {
            this.kor = kor;
        }

        public String getChn() {
            return chn;
        }

        public void setChn(String chn) {
            this.chn = chn;
        }

        public String getJpn() {
            return jpn;
        }

        public void setJpn(String jpn) {
            this.jpn = jpn;
        }

        public String getEng() {
            return eng;
        }

        public void setEng(String eng) {
            this.eng = eng;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getStationCd() {
        return stationCd;
    }

    public void setStationCd(String stationCd) {
        this.stationCd = stationCd;
    }

    public StationName getStationNm() {
        return stationNm;
    }

    public void setStationNm(StationName stationNm) {
        this.stationNm = stationNm;
    }

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }
}
