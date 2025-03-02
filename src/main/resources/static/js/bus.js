$(function () {
    // ** ============================ 버튼 탭 ( 버스 or 정류소 ) ===========================================================
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

    // ** ============================ functions ===========================================================
    // * HTTP REQUEST
    $(document).on('click', '#find-near-stop-btn', () => {
        $.ajax({
            url: '/api/bus/getNearBusStop',
            method: 'GET',
            data: {},
            success: function (data) {
                updateBusStopList(data);
                $('.near-stop-modal').modal('show'); // 모달 표시
            },
            error: function (xhr, status, error) {
                console.error("API 호출 오류:", error);
            }
        })
    })

    const fetchBusArrivalInfo = (stopId) => {
        $.ajax({
            url: `/api/bus/arrivalBusInfo?stopId=${stopId}`,
            method: 'GET',
            success: function (data) {
                console.log("================> data : ", data);
                // updateArrivalInfoUI(stopId, data);
            },
            error: function (xhr, status, error) {
                console.error(`🚨 API 호출 오류 (정류소 ${stopId}) :`, error);
            }
        });
    };

    // * JS FUNCTION
    const updateBusStopList = (busStops) => {
        let busStopList = $('#bus-stop-list');
        busStopList.empty(); // 기존 목록 초기화

        busStops.forEach((stop, index) => {
            console.log('📍==> stop :', stop);

            // 정류소 기본 정보
            let stopName = stop.busStopName;
            let stopId = stop.arsId; // TODO [chan] 서울지역과 지방 버스 고유번호가 달라서 추후에 개발 예정
            let lat = stop.lat;
            let lng = stop.lng;

            let listItem = `
                <div class="bus-stop-item" 
                    data-lat="${lat}" 
                    data-lng="${lng}" 
                    data-name="${stopName}" 
                    data-id="${stopId}"
                >
                    <div>
                        <p class="bus-stop-name">${stopName}</p>
                        <p class="bus-stop-id">${stopId}</p>
                    </div>
                    <button class="favorite-btn">☆</button>
                </div>
            `;

            busStopList.append(listItem);
        });
    }

    const updateFavoriteStopsUI = () => {
        let favoriteStops = JSON.parse(localStorage.getItem('favoriteStops')) || [];
        let stopList = $('#stop-info #favorite-bus-list');

        stopList.empty(); // 기존 목록 초기화

        if (favoriteStops.length === 0) {
            stopList.append('<li>등록된 즐겨찾기 정류장이 없습니다.</li>');
            return;
        }

        favoriteStops.forEach(stop => {
            let listItem = `
                <div>
                    <div class="favorite-stop">
                        <span class="favorite-stop-name">${stop.name} ( ${stop.id} )</span>
                        <div class="remove-favorite-bus-stop-btn" data-id="${stop.id}">
                            <i class="bi bi-trash"></i>
                        </div>
                    </div>
                    <div class="arrival-bus-info"></div>
                </div>
            `;
            stopList.append(listItem);

            fetchBusArrivalInfo(stop.id);
        });
    };

    updateFavoriteStopsUI();
    $(document).on('click', '.favorite-btn', function () {
        let busStopItem = $(this).closest('.bus-stop-item');
        let stopData = {
            name: busStopItem.data('name'),
            id: busStopItem.data('id'),
            lat: busStopItem.data('lat'),
            lng: busStopItem.data('lng')
        };

        // 브라우저 로컬스토리지에 저장 ( 개인회된 DB구조가 없기때문에... )
        let favoriteStops = JSON.parse(localStorage.getItem('favoriteStops')) || [];

        // DUP 체크
        const isDup = favoriteStops.some(stop => stop.id === stopData.id);
        if (!isDup) {
            favoriteStops.push(stopData);

            localStorage.setItem('favoriteStops', JSON.stringify(favoriteStops));

            updateFavoriteStopsUI();
        }
    })
    $(document).on('click', '.remove-favorite-bus-stop-btn', function () {
        let stopId = $(this).data('id');
        let favoriteStops = JSON.parse(localStorage.getItem('favoriteStops')) || [];

        // 해당 정류장 제거 후 다시 저장
        favoriteStops = favoriteStops.filter(stop => stop.id !== stopId);
        localStorage.setItem('favoriteStops', JSON.stringify(favoriteStops));

        updateFavoriteStopsUI(); // UI 업데이트
    });

    // ** ============================ TOAST 부분 로직 ===========================================================
    const toastTrigger = document.getElementById('liveToastBtn')
    const toastLiveExample = document.getElementById('liveToast')

    if (toastTrigger) {
        const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
        toastTrigger.addEventListener('click', () => {
            toastBootstrap.show()
        })
    }
})