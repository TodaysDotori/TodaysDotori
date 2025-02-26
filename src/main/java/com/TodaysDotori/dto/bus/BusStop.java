package com.TodaysDotori.dto.bus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusStop {
    private String busStopId;  // 정류소 ID
    private String busStopName; // 정류소 이름
    private double lat; // 위도
    private double lng; // 경도
}
