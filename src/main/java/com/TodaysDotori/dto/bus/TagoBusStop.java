package com.TodaysDotori.dto.bus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagoBusStop extends BusStop {
    private int cityCode; // 도시코드

    public TagoBusStop(
            String nodeId
            , String nodeNm
            , double gpsLati
            , double gpsLong
            , int cityCode
    ) {
        super(nodeId, nodeNm, gpsLati, gpsLong);

        this.cityCode = cityCode;
    }
}
