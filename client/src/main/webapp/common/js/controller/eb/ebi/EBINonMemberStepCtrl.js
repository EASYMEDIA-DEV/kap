define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebi/EBINonMemberStepCtrl"
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

    //사업자등록 체크
    let bsnmChk = false;

    // function


    // set model
    ctrl.model = {
        id : {

            ptcptBsnmNo : {
                event : {
                    input : function() {
                        if($("#authCheck").val() == 'Y') {
                            $("#ptcptBsnmNo").val("");
                            $("#ptcptCmpnNm").val("");
                            $("#authCheck").val("");
                            $("#ptcptBsnmNo").parent().parent().parent().removeClass("satisfy");
                        }

                        $("#ptcptBsnmNo").val($("#ptcptBsnmNo").val().replace(/[^0-9]/g, ''));
                        oninput="this.value=this.value.replace(/[^0-9]/g, '')"
                    }
                }
            },

            //사업자 등록 번호 인증
            btnCmpnChk : {
                event : {
                    click : function() {
                        //1. db를 조회 한다.
                        if($("#ptcptBsnmNo").val() =='' || $("#ptcptBsnmNo").val() == undefined) {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_001"));
                            $("#ptcptBsnmNo").focus();
                            bsnmChk = false;
                            return false;
                        }

                        jQuery.ajax({
                            url : "/nice/comp-chk",
                            type : "POST",
                            data :
                                {
                                    "compNum" : $("#ptcptBsnmNo").val()
                                },
                            success : function(data)
                            {
                                if(data.rsp_cd=='P000') {
                                    if(data.result_cd == '01') {
                                        if(data.comp_status == '1') {
                                            $("#ptcptCmpnNm").val(data.comp_name);
                                            $("#authCheck").val("Y");
                                            $("#ptcptBsnmNo").parent().parent().parent().addClass("satisfy");
                                            bsnmChk = true;
                                        } else {
                                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_002"));
                                            $("#ptcptBsnmNo").val("");
                                            $("#authCheck").val("");
                                            $("#ptcptBsnmNo").parent().parent().parent().removeClass("satisfy");
                                            bsnmChk = false;
                                            return false;
                                        }
                                    } else {
                                        alert(msgCtrl.getMsg("fail.eb.ebi.step.al_002"));
                                        $("#ptcptBsnmNo").val("");
                                        $("#authCheck").val("");
                                        $("#ptcptBsnmNo").parent().parent().parent().removeClass("satisfy");
                                        bsnmChk = false;
                                        return false;
                                    }
                                } else {
                                    alert(msgCtrl.getMsg("fail.eb.ebi.step.al_002"));
                                    $("#ptcptBsnmNo").val("");
                                    $("#authCheck").val("");
                                    $("#ptcptBsnmNo").parent().parent().parent().removeClass("satisfy");
                                    bsnmChk = false;
                                    return false;
                                }
                            },
                            error : function(xhr, ajaxSettings, thrownError)
                            {
                                cmmCtrl.errorAjax(xhr);
                                jQuery.jstree.rollback(data.rlbk);
                            }
                        });
                    }
                }
            },

            //휴대폰 자동 하이픈
            hpNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, '');
                        const phoneLen = phoneNumber.length;

                        if (phoneLen > 3 && phoneLen <= 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                        } else if (phoneLen > 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },

            //이메일 도메인 변경
            emailEndSelect : {
                event : {
                    change : function() {
                        var selectedEmailDomain = $(this).val();

                        if(selectedEmailDomain == "") {
                            $("#emailEnd").attr("readonly", false);
                            $("#emailEnd").val("");
                        }
                        else {
                            $("#emailEnd").attr("readonly", true);
                            $("#emailEnd").val(selectedEmailDomain);
                        }
                    }
                }
            },

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

            //직급 기타
            pstnCd : {
                event : {
                    change : function() {
                        $("#pstnNm").val("");

                        if($(this).val() == "MEM_CD01007") {
                            $("#pstnNmChk").css('display', 'block');
                        }
                        else {
                            $("#pstnNmChk").css('display', 'none');
                        }
                    }
                }
            },

            //신청 버튼
            btnApply : {
                event : {
                    click : function() {

                        if ($("#ptcptBsnmNo").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_001"));
                            $("#ptcptBsnmNo").focus();
                            return false;
                        }
                        if ($("#authCheck").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_003"));
                            $("#ptcptBsnmNo").focus();
                            return false;
                        }
                        if ($("#ptcptCmpnNm").val() == '') {
                            alert("사업자등록번호를 다시 인증해 주세요.");
                            $("#ptcptBsnmNo").focus();
                            return false;
                        }
                        if ($("#name").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_004"));
                            $("#name").focus();
                            return false;
                        }
                        if ($("#deptCd").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_005"));
                            $("#deptCd").focus();
                            return false;
                        }
                        if ($("#deptDtlNm").val() == '') {
                            alert("부서 상세를 입력해 주세요.");
                            $("#deptDtlNm").focus();
                            return false;
                        }
                        if ($("#pstnCd").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_006"));
                            $("#pstnCd").focus();
                            return false;
                        }
                        if ($("#pstnCd").val() == "MEM_CD01007" && $("#pstnNm").val() == '') {
                            alert("직급 상세를 입력해 주세요.");
                            $("#pstnNm").focus();
                            return false;
                        }
                        if ($("#hpNo").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_007"));
                            $("#hpNo").focus();
                            return false;
                        }
                        if ($("#emailSrt").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_008"));
                            $("#emailSrt").focus();
                            return false;
                        }
                        if ($("#emailEnd").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_008"));
                            $("#emailEnd").focus();
                            return false;
                        }

                        var email = '' + $("#emailSrt").val() + '@' + $("#emailEnd").val();
                        var regExr = /^[_a-zA-Z0-9-\.\_]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$/;

                        if(regExr.test(email)) {
                            $("#email").val(email);
                        }
                        else {
                            alert("이메일 주소를 다시 확인 해주세요.");
                            $("#emailEnd").focus();
                            return false;
                        }

                        if (!$("#agreeChk").prop("checked")) {
                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_009"));
                            $("#agreeChk").focus();
                            return false;
                        }

                        var seqObj = {};
                        seqObj.edctnSeq = $("#detailsKey").val();

                        //정원수 체크
                        cmmCtrl.jsonAjax(function(data){
                            if(data !=""){
                                var rtn = JSON.parse(data);
                                //정원여유
                                if(rtn.fxnumStta == "S"){
                                    cmmCtrl.frmAjax(function(rtnData){
                                        console.log(rtnData);
                                        // var rtnData = resultData.rtnData;
                                        //과정 수정/삭제
                                        if(rtnData.regStat == "N"){
                                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_011"));
                                            location.replace("./detail?detailsKey=" + $("#edctnSeq").val());
                                        }
                                        //신청 중복
                                        else if(rtnData.regStat == "F"){
                                            alert(msgCtrl.getMsg("fail.eb.ebi.step.al_012"));
                                            return false;
                                        }
                                        //신청 완료
                                        else if(rtnData.regStat == "S"){
                                            $("#regDtm").val(rtnData.regDtm);
                                            $("#frmGo").attr("action", "./step2");
                                            $("#frmGo").submit();
                                        }
                                    }, "./setPtcptInfo", $formObj, "post", "json");
                                }
                                //정원초과
                                else{
                                    alert(msgCtrl.getMsg("fail.eb.ebi.step.al_010"));
                                    return false;
                                }
                            }
                        }, "./fxnumChk", seqObj, "text")

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
                        if(confirm(msgCtrl.getMsg("confirm.eb.ebi.step.cf_001"))) {
                            location.href = $(this).data("url");
                        }
                    }
                }
            }

        },
        classname : {



        },
        immediately : function() {



        }
    };


    ctrl.exec();

    return ctrl;
});

