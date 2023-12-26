define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/EBCVisitEduCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("frmData");
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var textCntCheck = function (id) {
        var content = $(this).val();
        if(content.length == 0 || content == '') {
            id.text('0자')
        } else {
            id.text(content.length + '자')
        }

        if (content.length > 500) {
            alert('500자까지 입력 가능합니다.');
            $(this).val($(this).val().substring(0, 500));
            id.text('500자');
        }
    }

    var changeAppctnFldCd = function () {

        var actForm = {};
        var appctnFldCd = $("select[id='appctnFldCd']").val();
        actForm.appctnFldCd = appctnFldCd;
        cmmCtrl.jsonAjax(function(data){
            ctrl.obj.find(".checkBoxArea").html(data);

            if(appctnFldCd == 'EBC_VISIT_CD01002') {
                $("#techBox").css('display', 'block');
            } else {
                $("#techBox").css('display', 'none');
            }
            $(".checkBoxArea").validation({});
        }, './two-step/changeAppctnFldCd', actForm, "text");
    }



    // set model
    ctrl.model = {
        id : {
            appctnRsn : {
                event: {
                    keyup : function() {
                        var content = $(this).val();
                        if(content.length == 0 || content == '') {
                            $('#appctnRsnTextCnt').text('0자')
                        } else {
                            $('#appctnRsnTextCnt').text(content.length + '자')
                        }

                        if (content.length > 500) {
                            alert('500자까지 입력 가능합니다.');
                            $(this).val($(this).val().substring(0, 500));
                            $('#appctnRsnTextCnt').text('500자');
                        }
                    }
                }
            },
            appctnThemeCntn : {
                event: {
                    keyup : function() {
                        var content = $(this).val();
                        if(content.length == 0 || content == '') {
                            $('#appctnThemeCntnTextCnt').text('0자')
                        } else {
                            $('#appctnThemeCntnTextCnt').text(content.length + '자')
                        }

                        if (content.length > 500) {
                            alert('500자까지 입력 가능합니다.');
                            $(this).val($(this).val().substring(0, 500));
                            $('#appctnThemeCntnTextCnt').text('500자');
                        }
                    }
                }
            },
            ptcptTrgtCntn : {
                event: {
                    keyup : function() {
                        var content = $(this).val();
                        if(content.length == 0 || content == '') {
                            $('#ptcptTrgtCntnTextCnt').text('0자')
                        } else {
                            $('#ptcptTrgtCntnTextCnt').text(content.length + '자')
                        }

                        if (content.length > 500) {
                            alert('500자까지 입력 가능합니다.');
                            $(this).val($(this).val().substring(0, 500));
                            $('#ptcptTrgtCntnTextCnt').text('500자');
                        }
                    }
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"placeZipcode","placeBscAddr","placeDtlAddr");
                    }
                }
            },
            appctnFldCd : {
                event : {
                    change : function () {
                        changeAppctnFldCd();
                    }
                }
            },
            applyBtn : {
                event : {
                    click : function () {
                        var url = "./index";
                        if($("#memCd").val() == "CP") {
                            location.href = "./apply/one-step";
                        } else if($("#memCd").val() != "" && $("#memCd").val() != "CP") {
                            alert("방문교육 신청은 부품사 회원만 신청 가능합니다.");
                            location.href = url;
                        } else if($("#memCd").val() == "") {
                            if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                location.href = "/login";
                            }
                        }
                    }
                }
            },
            // 상세주소 입력 시 체크박스 해제
            placeDtlAddr : {
                event : {
                    input : function () {
                        $("#samePlaceBtn").prop("checked", false);
                    }
                }
            },
            applyCompleteBtn : {
                event : {
                    click : function () {
                        // controller에 json으로 넘길 form값
                        var actForm = {};

                        var memSeq = $("#memSeq").val();
                        var appctnBsnmNo = $("#appctnBsnmNo").val();
                        var appctnRsn = $("#appctnRsn").val();
                        var appctnFldCd = $("#appctnFldCd").val();
                        var appctnThemeCntn = $("#appctnThemeCntn").val();
                        var hopeDt = $("#hopeDt").val();
                        var placeZipcode = $("#placeZipcode").val();
                        var placeBscAddr = $("#placeBscAddr").val();
                        var placeDtlAddr = $("#placeDtlAddr").val();
                        var edctnPlaceAddr = $("#edctnPlaceAddr").val();
                        var ptcptTrgtCntn = $("#ptcptTrgtCntn").val();
                        var ptcptCnt = $("#ptcptCnt").val();
                        var ptcptHh = $("#ptcptHh").val();
                        var itrdcFileSeq = $("#itrdcFileSeq").val();

                        actForm.memSeq = memSeq;
                        actForm.appctnBsnmNo = appctnBsnmNo;
                        actForm.appctnRsn = appctnRsn;
                        actForm.appctnFldCd = appctnFldCd;
                        actForm.appctnThemeCntn = appctnThemeCntn;
                        actForm.hopeDt = hopeDt;
                        actForm.placeZipcode = placeZipcode;
                        actForm.placeBscAddr = placeBscAddr;
                        actForm.placeDtlAddr = placeDtlAddr;
                        actForm.edctnPlaceAddr = edctnPlaceAddr;
                        actForm.ptcptTrgtCntn = ptcptTrgtCntn;
                        actForm.ptcptCnt = ptcptCnt;
                        actForm.ptcptHh = ptcptHh;
                        actForm.itrdcFileSeq = itrdcFileSeq;

                        var appctnTypeCdList = new Array();
                        $(".checkBoxArea input[type='checkbox']:checked").each(function(){
                            appctnTypeCdList.push($(this).val());
                        });
                        actForm.appctnTypeCdList = appctnTypeCdList;

                        if ($("#appctnRsn").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_003"));
                            return false;
                        }

                        if($("#appctnFldCd").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_004"));
                            return false;
                        }

                        // ㄴ 체크 박스 선택 확인도 추가하기

                        if($("#appctnThemeCntn").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_005"));
                            return false;
                        }

                        if($("#hopeDt").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_006"));
                            return false;
                        }

                        if($("#placeZipcode").val() == '' || $("#placeBscAddr").val() == '' || $("#placeDtlAddr").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_007"));
                            return false;
                        }

                        if($("#ptcptTrgtCntn").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_008"));
                            return false;
                        }

                        if($("#ptcptCnt").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_009"));
                            return false;
                        }

                        if($("#ptcptHh").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_010"));
                            return false;
                        }

                        if($("#itrdcFile").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_011"));
                            return false;
                        }

                        cmmCtrl.jsonAjax(function(data){
                            var info = {};
                            if (data.respCnt > 0) {
                                info.applyCompleteYn = "Y";
                            } else {
                                info.applyCompleteYn = "N";
                            }
                            location.href = "./complete";
                        }, "./insert", actForm, "text");
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {

            var originPartsZipCode = $("input[name=zipcode]").val();
            var originPartsBscAddr = $("input[name=bscAddr]").val();
            var originPartsDtlAddr = $("input[name=dtlAddr]").val();

            $("#samePlaceBtn").change(function() {
                // 체크된 경우
                if ($(this).is(":checked")) {
                    $("input[name=placeZipcode]").val(originPartsZipCode);
                    $("input[name=placeBscAddr]").val(originPartsBscAddr);
                    $("input[name=placeDtlAddr]").val(originPartsDtlAddr);
                }

                var samePlaceAddrArr = $("input[name=placeBscAddr]").val().split(' ');
                var edctnPlaceAddr;
                if(samePlaceAddrArr[1] != undefined){
                    edctnPlaceAddr =  samePlaceAddrArr[0] + ' ' + samePlaceAddrArr[1];
                } else {
                    edctnPlaceAddr = samePlaceAddrArr[0];
                }

                $("input[name=edctnPlaceAddr]").val(edctnPlaceAddr);
            });

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true

                    jQuery(".dropzone").not(".notRequired").each(function(i){
                        if (jQuery(this).children(".dz-preview").length == 0)
                        {
                            alert(jQuery(this).data("titl") + "를 첨부해주세요.");
                            jQuery(this)[0].scrollIntoView();
                            isValid = false;
                            return false;
                        }
                    });

                    return isValid;
                },
                async : {
                    use : true,
                    func : function () {
                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});