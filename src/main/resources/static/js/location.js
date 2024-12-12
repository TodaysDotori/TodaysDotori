const getLocationInfo = () => {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            function (position) {
                const lat = position.coords.latitude;  // 위도
                const lon = position.coords.longitude; // 경도

                $.ajax({
                    url: '/location/reverse-geocode',
                    method: 'GET',
                    data: {lat, lon},
                    success: function (data) {
                        if (typeof data === 'string') {
                            data = JSON.parse(data);
                        }

                        const {city, borough, suburb, road} = data.address;

                        $('#location-info').text(city + ' ' + borough + ' ' + suburb + ' ' + road);
                    },
                    error: function (xhr, status, error) {
                        console.error("API 호출 오류:", error);
                        $('#location-info').text("주소 정보를 가져오는 데 실패했습니다.");
                    }
                });
            },
            function (error) {
                console.error("위치 접근 오류:", error);
                $('#location-info').text("위치 정보를 가져올 수 없습니다.");
            }
        );
    } else {
        $('#location-info').text("Geolocation이 지원되지 않는 브라우저입니다.");
    }
}
$(() => {
    getLocationInfo();
})