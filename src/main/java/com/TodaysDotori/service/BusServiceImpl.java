package com.TodaysDotori.service;

import com.TodaysDotori.client.bus.SeoulBusClient;
import com.TodaysDotori.client.bus.TagoBusClient;
import com.TodaysDotori.dto.bus.BusStop;
import com.TodaysDotori.dto.bus.SeoulBusStop;
import com.TodaysDotori.dto.bus.TagoBusStop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final SeoulBusClient seoulBusClient;
    private final TagoBusClient tagoBusClient;

    @Override
    public List<BusStop> getNearBusStopByPos(double lat, double lon) {
        ArrayList<BusStop> busStopList = new ArrayList<>();
        try {
            List<SeoulBusStop> seoulBusStops = seoulBusClient.getStationsByPosList(lat, lon);
            if (!seoulBusStops.isEmpty()) {
                busStopList = new ArrayList<>(seoulBusStops);
            } else {
                List<TagoBusStop> tagoBusStops = tagoBusClient.getStationsByPosList(lat, lon);
                busStopList = new ArrayList<>(tagoBusStops);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return busStopList;
    }
}
