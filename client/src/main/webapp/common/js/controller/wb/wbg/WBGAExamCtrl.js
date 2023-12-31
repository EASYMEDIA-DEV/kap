define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbg/WBGAExamCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');

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

            console.log(fileId);
            if (isFile) {
                $('#'+fileId).closest(".form-group").find('.empty-txt').text(obj.files[0].name);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            apply : {
                event : {
                    click : function() {
                        //신청페이지 로직점검
                        cmmCtrl.paramAjax(function(data){
                            if (data.resultCode == 999) {
                                if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                    location.href = "/login?rtnUrl="+encodeURIComponent(window.location.pathname);
                                }
                            } else if (data.resultCode == 100) {
                                alert('해당 사업은 부품사 회원만 신청 가능합니다.');
                            } else if (data.resultCode == 150) {
                                alert('위원회원은 해당 서비스를 이용할 수 없습니다.');
                            } else if (data.resultCode == 190) {
                                alert('1,2차 부품사만 신청가능합니다.');
                            } else if (data.resultCode == 300) {
                                if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                    location.href = "/my-page/main";
                                }
                            } else if (data.resultCode == 200) {
                                location.href = "./step1";
                            } else if (data.resultCode == 400) {
                                alert('사업 지원대상 기준을 확인해주세요.\n(지원대상 기준 : 전년도 매출액 ' + $('#pmt').val() +'억 미만)');
                            } else if (data.resultCode == 450) {
                                alert('사업 지원대상 기준을 확인해주세요.\n(지원대상 기준:당해년도 정규기술지도/생산,생산기술,\n품질 분야 경영컨설팅 참여중 또는 완료 부품사)');
                            }
                        },"./applyChecked",null, "json", false, false, "get");
                    }
                }
            },
            insert : {
                event : {
                    click : function() {
                        var valid = true;
                        var tchlgNm = $('.tchlgNm');

                        tchlgNm.each(function() {
                            if (!$(this).val()) {
                                alert('대상장비를 입력해주세요.');
                                valid = false;
                                return false;
                            }
                        });

                        if (valid) {
                            var file = $('input[type=file]');

                            file.each(function(i) {
                                if (!$(this).val()) {
                                    alert('신청서류를 모두 등록해주세요.');
                                    valid = false;
                                    return false;
                                }
                            });
                        }

                        if (valid) {
                            //이용약관 체크여부
                            if ($('#agreeChk').is(':checked')) {

                                cmmCtrl.fileFrm(function(data){
                                    //콜백함수. 페이지 이동
                                    if (data.actCnt == 999) {
                                        if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                            location.href = "/my-page/main";
                                        }
                                    } else {
                                        location.href = "./complete";
                                    }
                                }, "./insert", $formObj, "json");
                            } else {
                                alert('약관에 동의해주세요.');
                            }
                        }
                    }
                }
            },
            insertSkip : {
                event : {
                    click : function() {
                        cmmCtrl.fileFrm(function(data){
                            //콜백함수. 페이지 이동
                            if (data.actCnt == 999) {
                                if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                    location.href = "/my-page/main";
                                }
                            } else {
                                location.href = "./complete?episdSeq="+$('input[name=episdSeq]').val();
                            }
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
        immediately : function(){

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

