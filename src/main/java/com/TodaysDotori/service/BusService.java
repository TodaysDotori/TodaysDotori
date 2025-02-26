package com.TodaysDotori.service;


import com.TodaysDotori.dto.bus.BusStop;

import java.util.List;

public interface BusService {
    List<BusStop> getNearBusStopByPos(double lat, double lon);
}
