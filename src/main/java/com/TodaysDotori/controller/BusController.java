package com.TodaysDotori.controller;

import com.TodaysDotori.dto.bus.BusStop;
import com.TodaysDotori.service.BusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/bus")
@Slf4j
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;

    @GetMapping(value = "/getNearBusStop")
    public ResponseEntity<?> test(
            HttpServletRequest request
    ) throws IOException {
        HttpSession session = request.getSession();

        // 세션에서 lat, lon 값 가져옴
        Object latObj = session.getAttribute("lat");
        Object lonObj = session.getAttribute("lon");

        // Null인 경우가 있어 체크 ( double로 캐스팅할때 null pointer exception 발생할 수 있음 )
        if (latObj == null || lonObj == null) {
            log.error("세션에 필요한 값이 존재하지 않습니다.");
            return null; // TODO 수정필요!
        }

        double lat = (latObj instanceof Double) ? (double) latObj : Double.parseDouble(String.valueOf(latObj));
        double lon = (lonObj instanceof Double) ? (double) lonObj : Double.parseDouble(String.valueOf(lonObj));

        List<BusStop> busStops = busService.getNearBusStopByPos(lat, lon);

        return ResponseEntity.ok(busStops);
    }
}


