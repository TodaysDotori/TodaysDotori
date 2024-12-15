$(document).ready(() => {
    const container = $('#bike-map');
    const options = {
        center: new kakao.maps.LatLng(lat, lon),
        level: 3
    };
    const map = new kakao.maps.Map(container[0], options);

    const markerPosition  = new kakao.maps.LatLng(lat, lon);

    const marker = new kakao.maps.Marker({
        position: markerPosition
    });

    marker.setMap(map);
})
