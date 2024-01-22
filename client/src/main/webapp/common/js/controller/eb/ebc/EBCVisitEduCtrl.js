define(["ezCtrl", "ezVald", "ezFile"], function(ezCtrl, ezVald, ezFile) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebc/EBCVisitEduCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이

    // 파일 체크
    var extnCheck = function(obj, extns, maxSize)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;

        if (!fileObj)
        {
            isFile = false;
        }
        else
        {
            var file;
            file = obj.files[0];

            var fileExtn = file.name.split(".").pop();

            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                //파일확장자 체크
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find('.empty-txt').text("");
                alert('첨부 가능한 파일 확장자가 아닙니다.');

                isFile = false;
            } else {
                //파일용량 체크
                if (typeof obj.files != "undefined")
                {
                    var fileSize = file.size;
                    var maxFileSize = maxSize * 1024 * 1024;

                    if (fileSize > maxFileSize)
                    {
                        $('#'+fileId).val("");
                        $('#'+fileId).closest(".form-group").find('.empty-txt').text("");
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }
                }
            }

            if (isFile) {
                $('#'+fileId).closest(".form-group").find('.empty-txt').text(obj.files[0].name);
            }
        }
    };

    var textCntCheck = function (id, cntId) {
        var content = $("#"+id).val();
        var cntElement = $("#"+cntId);

        if(content.length == 0 || content == '') {
            cntElement.text('0자')
        } else {
            cntElement.text(content.length + '자')
        }
        if (content.length > 500) {
            alert('500자까지 입력 가능합니다.');
            $(this).val($(this).val().substring(0, 500));
            cntElement.text('500자');
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
        }, './step2/changeAppctnFldCd', actForm, "text");
    }



    // set model
    ctrl.model = {
        id : {
            appctnRsn : {
                event: {
                    keyup : function() {
                        textCntCheck('appctnRsn', 'appctnRsnTextCnt');
                    }
                }
            },
            appctnThemeCntn : {
                event: {
                    keyup : function() {
                        textCntCheck('appctnThemeCntn', 'appctnThemeCntnTextCnt');
                    }
                }
            },
            ptcptTrgtCntn : {
                event: {
                    keyup : function() {
                        textCntCheck('ptcptTrgtCntn', 'ptcptTrgtCntnTextCnt');
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
                            location.href = "./apply/step1";
                        } else if($("#memCd").val() == "CS") {
                            alert("위원회원은 해당 서비스를 이용할 수 없습니다.");
                            location.href = url;
                        } else if($("#memCd").val() != "" && $("#memCd").val() != "CP") {
                            alert("방문교육 신청은 부품사 회원만 신청 가능합니다.");
                            location.href = url;
                        } else if($("#memCd").val() == "") {
                            if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                location.href = "/login?rtnUrl=%2Feducation%2Fvisit%2Fapply%2Fstep1";
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
                        if ($("#appctnRsn").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_003"));
                            $("#appctnRsn").focus();
                            return false;
                        }

                        if($("#appctnFldCd").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_004"));
                            $("#appctnFldCd").focus();
                            return false;
                        }

                        if($("#appctnThemeCntn").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_005"));
                            $("#appctnThemeCntn").focus();
                            return false;
                        }

                        if($("#hopeDt").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_006"));
                            $("#hopeDt").focus();
                            return false;
                        }

                        if($("#placeZipcode").val() == '' || $("#placeBscAddr").val() == '' || $("#placeDtlAddr").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_007"));
                            $("#placeDtlAddr").focus();
                            return false;
                        }

                        if($("#ptcptTrgtCntn").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_008"));
                            $("#ptcptTrgtCntn").focus();
                            return false;
                        }

                        if($("#ptcptCnt").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_009"));
                            $("#ptcptCnt").focus();
                            return false;
                        }

                        if($("#ptcptHh").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.input.al_010"));
                            $("#ptcptHh").focus();
                            return false;
                        }
                        // controller에 json으로 넘길 form값
                        cmmCtrl.fileFrm(function(data){
                            //콜백함수. 페이지 이동
                           location.href = "./complete";
                        }, "./insert", $formObj, "json");
                    }
                }
            },
            searchFile : {
                event : {
                    change : function() {
                        extnCheck(this, "jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip", 50);
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

            cmmCtrl.setCalendar();
        }
    };

    ctrl.exec();

    return ctrl;
});