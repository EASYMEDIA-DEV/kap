define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/co/COGCntsViewCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // map Object
    var mapContainer = document.getElementById('locationMap'), // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
        };

    // 지도를 생성합니다
    var map = new daum.maps.Map(mapContainer, mapOption);

    // set model
    ctrl.model = {
        id : {

        },
        classname : {

        },
        immediately : function() {
            var locationMap = $("#locationMap");

            if(locationMap != null && locationMap != undefined) {

                var bscAddr = "서울특별시 용산구 원효로 74";//기본주소
                var dtlAddr = "현대자동차 원효로사옥";//상세주소

                //주소세팅 시작
                //var addrNm = $(this).prev().attr("addrNm");
                var addr = bscAddr + " " + dtlAddr; //$(this).prev().attr("addr");
                console.log(addr);

                // 주소-좌표 변환 객체를 생성합니다
                var geocoder = new daum.maps.services.Geocoder();

                // 주소로 좌표를 검색합니다
                geocoder.addressSearch(addr, function (result, status) {

                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {
                        console.log(result[0].y);
                        console.log(result[0].x);


                        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                        // 결과값으로 받은 위치를 마커로 표시합니다
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: coords
                        });

                        // 인포윈도우로 장소에 대한 설명을 표시합니다
                        /*var infowindow = new daum.maps.InfoWindow({
                            content: '<div style="width:150px;text-align:center;padding:6px 0;">' + dtlAddr + '</div>'
                        });
                        infowindow.open(map, marker);*/

                        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                        map.setCenter(coords);


                    }
                });

                //주소세팅 종료
                map.relayout();
            }


        }
    };


    ctrl.exec();

    return ctrl;
});

