define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebi/EBINonMemberApplyDetailCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    var mapContainer = document.getElementById('eduRoom'), // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
        };

    // 지도를 생성합니다
    var map = new daum.maps.Map(mapContainer, mapOption);

    // function


    // set model
    ctrl.model = {
        id : {

            //교육 담당자 팝업
            btnPicLayer : {
                event : {
                    click : function() {
                        var picNm = $(this).data("picNm");
                        var picEmail = $(this).data("picEmail");
                        var picTelNo = $(this).data("picTelNo");

                        $(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(0).find("td").text(picNm);
                        $(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(1).find("td").text(picEmail);
                        $(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(2).find("td").text(picTelNo);

                        openPopup('eduPersonInfoPopup');
                    }
                }
            },

            //교육장 팝업
            mapBtn : {
                event: {
                    click: function (e) {
                        if(jQuery(e.target).data("mapchk") == "N") {
                            openPopup('educCenterInfoPopup', e, "Y");
                        }

                        if(jQuery(e.target).data("mapchk") == "Y") {

                            var placeNm = $(e.target).data("nm");//교육장명
                            var rprsntTelNo =  $(e.target).data("rprsnttelno");//대표 전화번호
                            var zipcode = $(e.target).data("zipcode");//우편번호
                            var bscAddr = $(e.target).data("bscAddr");//기본주소
                            var dtlAddr = $(e.target).data("dtlAddr");//상세주소

                            //주소세팅 시작
                            //var addrNm = $(this).prev().attr("addrNm");
                            var addr = bscAddr + " " + dtlAddr; //$(this).prev().attr("addr");


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
                            jQuery(e.target).data("mapchk", "N")

                            $(".educCenterInfoPopup").find("table.basic-table").find("tr").eq(0).find("td").text(placeNm);//교육장명
                            $(".educCenterInfoPopup").find("table.basic-table").find("tr").eq(1).find("td").text("["+zipcode+"] " + addr);//주소
                            $(".educCenterInfoPopup").find("table.basic-table").find("tr").eq(2).find("td").text(rprsntTelNo);//대표전화
                        }

                    }
                }
            },

            btnCancel : {
                event : {
                    click : function() {
                        if(confirm(msgCtrl.getMsg("confirm.eb.ebi.auth.cf_001"))) {
                            var edctnSeq = $(this).data("edctnSeq");
                            var ptcptSeq = $(this).data("ptcptSeq");

                            $.ajax({
                                type: "post",
                                url: "./updatePtcpt",
                                dataType: "json",
                                data: {
                                    "edctnSeq" : edctnSeq,
                                    "ptcptSeq" : ptcptSeq,
                                    "_csrf": jQuery("#csrfKey").val()
                                },
                                success: function(r) {
                                    if(r.respCnt > 0) {
                                        alert(msgCtrl.getMsg("fail.eb.ebi.auth.al_005"));

                                        var url = "./list";

                                        $formObj.attr("action", url);

                                        $formObj.submit();
                                    }
                                    else {
                                        alert("신청 취소가 실패했습니다.\n다시 시도해 주세요.");
                                    }
                                }
                            })
                        }
                    }
                }
            }

        },
        classname : {

            goList : {
                event : {
                    click : function () {
                        var url = $(this).data("url");

                        $formObj.attr("action", url);

                        $formObj.submit();
                    }
                }
            },

            download : {
                event : {
                    click : function() {
                        var file = $(this).data("file");
                        location.href = file;
                    }
                }
            }

        },
        immediately : function() {



        }
    };


    ctrl.exec();

    return ctrl;
});

