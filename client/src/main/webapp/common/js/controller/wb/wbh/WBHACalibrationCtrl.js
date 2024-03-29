define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbh/WBHACalibrationCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var fileInput = "";

    // 파일 체크
    var extnCheck = function(obj, extns, maxSize)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;
        var fileArea = $('#'+fileId).closest(".form-group").find('.file-btn-area');

        if (!fileObj)
        {
            isFile = false;
            $('#'+fileId).remove();
            fileArea.prepend(fileInput);
        }
        else
        {
            var file;
            file = obj.files[0];

            var fileExtn = file.name.split(".").pop();
            var fileName = file.name.split(".")[0];

            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                //파일확장자 체크
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                $('#'+fileId).closest(".form-group").find('.file-list').remove();
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
                        $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                        $('#'+fileId).closest(".form-group").find('.file-list').remove();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }
                }
            }

            if (isFile) {
                $('#'+fileId).closest(".form-group").find('.file-list').remove();
                fileInput = jQuery(obj).clone(true);
                var fileHtml = '<div class="file-list"><p class="file-name"><span class="name">' + fileName + '</span>';
                fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button></div>';
                $('#'+fileId).closest(".form-group").find('.file-list-area').addClass("attached");
                $('#'+fileId).closest(".form-group").find('.file-list-area').append(fileHtml);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            fileDown : {
                event : {
                    click : function(e) {
                        $(".loading-area").stop().fadeIn(200);
                        var url = $(this).data("url");

                        async function downloadFile() {
                            await new Promise(resolve => setTimeout(resolve, 200)); // 200ms 대기
                            location.href = url;
                            $(".loading-area").stop().fadeOut(200);
                        }

                        downloadFile();
                    }
                }
            },
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
                                    location.href = "/my-page/coexistence/list";
                                }
                            } else if (data.resultCode == 200) {
                                location.href = "./step1";
                            } else if (data.resultCode == 400) {
                                alert('사업 지원대상 기준을 확인해주세요.\n(지원대상 기준 : 전년도 매출액 ' + $('#pmt').val() +'억 미만)');
                                location.href = '/my-page/member/intrduction/modify-page?applyYn=Y';
                            } else if (data.resultCode == 450) {
                                alert('사업 지원대상 기준을 확인해주세요. (컨설팅 내역)');
                            } else if (data.resultCode == 460) {
                                alert('소속 부품사의 사업자등록번호로 이미 신청한 사업입니다.');
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
                            //이용약관 체크여부
                            if ($('#agreeChk').is(':checked')) {
                                if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                    $(".loading-area").stop().fadeIn(200);
                                    cmmCtrl.fileFrm(function(data){
                                        //콜백함수. 페이지 이동
                                        if (data.actCnt == 999) {
                                            if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                                location.href = "/my-page/coexistence/list";
                                            }
                                        } else {
                                            location.href = "./complete?episdSeq=" + $('input[name=episdSeq]').val();
                                        }
                                        $(".loading-area").stop().fadeOut(200);
                                    }, "./insert", $formObj, "json");
                                }
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
                        $(".loading-area").stop().fadeIn(200);
                        cmmCtrl.fileFrm(function(data){
                            //콜백함수. 페이지 이동
                            if (data.actCnt == 999) {
                                if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                    location.href = "/my-page/coexistence/list";
                                }
                            } else {
                                if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                    location.href = "./complete?episdSeq=" + $('input[name=episdSeq]').val();
                                }
                            }
                            $(".loading-area").stop().fadeOut(200);
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
            },
            fileDelete : {
                event : {
                    click : function() {
                        $(this).closest(".form-group").find("input[type=file]").val("");
                        $(this).closest(".form-group").find('.file-list-area').removeClass("attached");
                        $(this).closest(".form-group").find('.file-list').remove();
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

