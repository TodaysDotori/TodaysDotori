package com.TodaysDotori.dto.bus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SeoulBusStop extends BusStop {
    private String arsId; // 정류소 고유번호
    private double posX; // 정류소 좌표 X (GRS80)
    private double posY; // 정류소 좌표 Y (GRS80)
    private double dist; // 거리
    private String stationTp; // 정류소 타입

    public SeoulBusStop(
            String stationId
            , String stationNm
            , double gpsY
            , double gpsX
            , String arsId
            , double posX
            , double posY
            , double dist
            , String stationTp
    ) {
        super(stationId, stationNm, gpsY, gpsX);

        this.arsId = arsId;
        this.posX = posX;
        this.posY = posY;
        this.dist = dist;
        this.stationTp = stationTp;
    }
}
