$(function () {
    let map, markerGroup;

    $(document).on('click', '#find-near-stop-btn', () => {
        $.ajax({
            url: '/api/bus/getNearBusStop',
            method: 'GET',
            data: {},
            success: function (data) {
                console.log('==> success :', data);
                updateBusStopList(data);
                // updateMapMarkers(data);
                $('.near-stop-modal').modal('show'); // 모달 표시
            },
            error: function (xhr, status, error) {
                console.error("API 호출 오류:", error);
            }
        })
    })

    function updateBusStopList(busStops) {
        let busStopList = $('#bus-stop-list');
        busStopList.empty(); // 기존 목록 초기화

        busStops.forEach((stop, index) => {
            console.log('==> stop :', stop);

            let stopName = stop.busStopName;
            let stopId = stop.arsId;

            let listItem = `
                <div class="bus-stop-item" data-lat="${stop.lat}" data-lng="${stop.lng}">
                    <div>
                        <p class="bus-stop-name">${stopName}</p>
                        <p class="bus-stop-id">${stopId}</p>
                    </div>
                    <button class="favorite-btn">☆</button>
                </div>
            `;

            busStopList.append(listItem);
        });
        // 목록 클릭 시 지도에서 해당 위치로 이동
        // $('.bus-stop-item').on('click', function () {
        //     let lat = $(this).data('lat');
        //     let lng = $(this).data('lng');
        //     map.setView([lat, lng], 15);
        // });
    }

    // function updateMapMarkers(busStops) {
    //     if (!map) {
    //         map = L.map('bus-stop-map').setView([busStops[0].lat, busStops[0].lng], 14); // 기본 위치 설정
    //         L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    //             attribution: '&copy; OpenStreetMap contributors'
    //         }).addTo(map);
    //         markerGroup = L.layerGroup().addTo(map);
    //     } else {
    //         markerGroup.clearLayers();
    //     }
    //
    //     busStops.forEach((stop) => {
    //         let marker = L.marker([stop.lat, stop.lng])
    //             .bindPopup(`<b>${stop.busStopName}</b><br>ID: ${stop.busStopId}`)
    //             .addTo(markerGroup);
    //     });
    // }

    const toastTrigger = document.getElementById('liveToastBtn')
    const toastLiveExample = document.getElementById('liveToast')

    if (toastTrigger) {
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
        toastTrigger.addEventListener('click', () => {
            toastBootstrap.show()
        })
    }

    document.getElementById('favorite-bus-btn').addEventListener('click', function () {
        document.getElementById('bus-info').style.display = 'block';
        document.getElementById('stop-info').style.display = 'none';
        this.classList.add('active');
        document.getElementById('favorite-stop-btn').classList.remove('active');
    });

    document.getElementById('favorite-stop-btn').addEventListener('click', function () {
        document.getElementById('bus-info').style.display = 'none';
        document.getElementById('stop-info').style.display = 'block';
        this.classList.add('active');
        document.getElementById('favorite-bus-btn').classList.remove('active');
    });
})