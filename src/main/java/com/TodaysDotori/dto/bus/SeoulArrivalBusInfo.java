package com.TodaysDotori.dto.bus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeoulArrivalBusInfo {
    String adirection; // 방향

    String arrmsg1; // 첫번째도착예정버스의 도착정보메세지
    String arrmsg2; // 두번째도착예정버스의 도착정보메세지
    String arrmsgSec1; // 첫번째도착예정버스의 도착정보메시지
    String arrmsgSec2; // 두번째도착예정버스의 도착정보메시지

    String busRouteId; // 노선ID

    int busType1; // 첫번째도착예정버스의 차량유형 (0:일반버스, 1:저상)
    int busType2; // 두번째도착예정버스의 차량유형 (0:일반버스, 1:저상)

    String firstTm; // 첫차시간

    String isArrive1; // 첫번째도착예정버스의 최종 정류소 도착출발여부(0:운행,1:도착)
    String isArrive2; // 두번째도착예정버스의 최종 정류소 도착출발여부(0:운행,1:도착)
    boolean ifFullFlag1; // 첫번째도착예정버스의 만차여부
    boolean isFullFlag2; // 두번째도착예정버스의 만차여부

    boolean isLast1; // 첫번째도착예정버스의 막차여부
    boolean isLast2; // 두번째도착예정버스의 막차여부

    String lastTm; // 막차시간
    boolean nextBus; // 막차운행여부

    String nxtStn; // 다음정류장순번

    double posX; // 정류소 좌표X
    double posY; // 정류소 좌표Y

    String repTm1; // 첫번째도착예정버스의 최종 보고 시간
    String repTm2; // 두번째도착예정버스의 최종 보고 시간
    int rerdieDiv1; // 첫번째도착예정버스의 재차구분
    int rerdieDiv2; // 두번째도착예정버스의 재차구분
    int rerideNum1;
    int rerideNum2;

    int routeType; // 노선유형(1:공항, 2:마을, 3:간선, 4:지선, 5:순환, 6:광역, 7:인천, 8:경기, 9:폐지, 0:공용)
    String rtNm; // 노선명
    String sectNm; // 구간명

    int sectOrd1; // 첫번째도착예정버스의 현재구간 순번
    int sectOrd2; // 두번째도착예정버스의 현재구간 순번

    String stationList; // 정류소 리스트

    double gpxX; // 정류소 X좌표
    double gpxY; // 정류소 Y좌표
    int stationTp; // 정류소 타입(0:공용, 1:일반형 시내/농어촌버스, 2:좌석형 시내/농어촌버스, 3:직행좌석형 시내/농어촌버스, 4:일반형 시외버스, 5:좌석형 시외버스, 6:고속형 시외버스, 7:마을버스)
    String arsId; // 정류소고유번호
    int staOrd; // 요청정류소순번
    String stationNm1; // 첫번째도착예정버스의 최종 정류소명
    String stationNm2; // 두번째도착예정버스의 최종 정류소명
    String stId; // 정류소 고유 ID
    String stNm; // 정류소명
    int term; // 배차간격

    String traSpd1; // 첫번째도착예정버스의 여행속도
    String traSpd2; // 두번째도착예정버스의 여행속도
    String traTime1; // 첫번째도착예정버스의 여행시간
    String traTime2; // 두번째도착예정버스의 여행시간

    String vehId1; // 첫번째도착예정버스ID
    String vehId2; // 두번째도착예정버스ID

    String deTourAt; // 우회여부(00:일반, 11:우회)
}
