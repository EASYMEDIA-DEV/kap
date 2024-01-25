define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBFutureCarWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    //파일 체크
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
            var fileName = file.name.split(".")[0];

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
                $('#'+fileId).closest(".form-group").find('.empty-txt').toggleClass("file-name")
                $('#'+fileId).closest(".form-group").find('.empty-txt').text(obj.files[0].name);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {

        },
        classname : {
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
                        $(this).closest(".form-group").find('.file-list').empty();
                    }
                }
            },
            btnUpdate : {
                event : {
                    click : function() {

                        var $formObj = $(this).closest('form');
                        var valid = true;

                        var file = $formObj.find('input[type=file]');
                        file.each(function(i) {
                            if (!$(this).val()) {
                                alert('신청서를 등록해주세요.');
                                valid = false;
                                return false;
                            }
                        });

                        if (valid) {
                            if(confirm("저장 후 내용을 수정할 수 없습니다.\n저장하시겠습니까?")) {
                                 cmmCtrl.fileFrm(function (data) {
                                    if (data.respCnt > 0) {
                                         location.href = "/my-page/coexistence/list";
                                     }
                                 }, "./update", $formObj, "json");
                            }
                        }
                    }
                }
            },
        },
        immediately : function(){
            // 심사 상태에 따른 색상.
            $('.rsumeColor').each(function() {
                var rsumeText = $(this).text().trim();

                if( rsumeText == "접수완료" || rsumeText == "통과") {
                    $(this).closest('p.box-label.bigger').addClass("accepting");
                }else if( rsumeText == "탈락"){
                    $(this).closest('p.box-label.bigger').addClass("arr");
                }else {
                    $(this).closest('p.box-label.bigger').addClass("waiting");
                }
            });

            //서류 진행 상태 확인
            var laststep = !($(".lastStep").data("laststep") === undefined || $(".lastStep").data("laststep") == '') ? $(".lastStep").data("laststep") : 1;
            var step = laststep -1
            $('.list-item:eq('+step+')').addClass("active");

            $("#rsumeFrm  [name='rsumeOrd']").val(laststep);
            $("#rsumeFrm  [name='appctnSttsCd']").val($("#mngSttsCd"+laststep).val());
            $("#rsumeFrm  [name='mngSttsCd']").val($("#mngSttsCd"+laststep).val());

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});


