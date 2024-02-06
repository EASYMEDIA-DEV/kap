define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBExamCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var equidHtml = '';
    var fileInput = "";
    var equidChagneFunction = function() {
        var equimentLength = $('.equiment').length;
        $('.equimentCnt').text(equimentLength);

        var tchlgTotalCnt = 0;
        $('.equiment').each(function() {
            tchlgTotalCnt = parseInt($(this).find('.tchlgCnt').val()) + tchlgTotalCnt;
        });

        $('.totalCnt').text(tchlgTotalCnt);
    }

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
            addBtn : {
                event : {
                    click : function() {
                        var equipLength = $('.equiment').length;

                        if (equipLength > 100) {
                            alert('대상장비는 100개까지만 추가 가능합니다.');
                        } else {
                            $('.addEquiment').last().append(equidHtml);
                            $('.equiment').last().find(".tchlgNm").attr("name","exam.euipmentList["+ equipLength +"].tchlgNm");
                            $('.equiment').last().find(".tchlgCnt").attr("name","exam.euipmentList["+ equipLength +"].tchlgCnt");
                            equidChagneFunction();
                        }
                    }
                }
            },
            deleteBtn : {
                event : {
                    click : function() {

                        $(this).parents(".equiment").remove();

                        $('.equiment').each(function(index) {
                            $(this).find(".tchlgNm").attr("name","exam.euipmentList["+ index +"].tchlgNm");
                            $(this).find(".tchlgCnt").attr("name","exam.euipmentList["+ index +"].tchlgCnt");
                        });

                        equidChagneFunction();
                    }
                }
            },
            tchlgCnt : {
                event : {
                    change : function() {
                        var cnt = $(this).val();

                        if (cnt > 100) {
                            alert('최대 수량은 100개까지 입력가능합니다.');
                            $(this).val(100);
                            $(this).parents('.amount-div').find('.plus').addClass("disabled");
                            $(this).parents('.amount-div').find('.minus').removeClass("disabled");
                        } else if (cnt <= 0) {
                            alert('최소 수량은 1개까지 입력가능합니다.');
                            $(this).val(1);
                            $(this).parents('.amount-div').find('.plus').removeClass("disabled");
                            $(this).parents('.amount-div').find('.minus').addClass("disabled");
                        } else {
                            $(this).parents('.amount-div').find('.plus').removeClass("disabled");
                            $(this).parents('.amount-div').find('.minus').removeClass("disabled");
                        }
                        equidChagneFunction();
                    }
                }
            },
            plus : {
                event : {
                    click : function () {
                        var valueCnt = $(this).parents('.amount-div').find('.tchlgCnt').val();
                        var tchlgCnt = $(this).parents('.amount-div').find('.tchlgCnt');

                        if (valueCnt == 99) {
                            $(this).parents('.amount-div').find('.plus').addClass("disabled");
                            valueCnt = parseInt(valueCnt) +1;
                            tchlgCnt.val(valueCnt);
                        } else if (valueCnt > 99) {
                            $(this).parents('.amount-div').find('.plus').addClass("disabled");
                        } else {
                            if (valueCnt == 1) {
                                $(this).parents('.amount-div').find('.minus').removeClass("disabled");
                            }
                            valueCnt = parseInt(valueCnt) +1;
                            tchlgCnt.val(valueCnt);
                        }

                        equidChagneFunction();
                    }
                }
            },
            minus : {
                event : {
                    click : function() {
                        var valueCnt = $(this).parents('.amount-div').find('.tchlgCnt').val();
                        var tchlgCnt = $(this).parents('.amount-div').find('.tchlgCnt');

                        if (valueCnt == 1) {
                            $(this).parents('.amount-div').find('.minus').addClass("disabled");
                        } else if (valueCnt == 2) {
                            $(this).parents('.amount-div').find('.minus').addClass("disabled");
                            valueCnt = parseInt(valueCnt) -1;
                            tchlgCnt.val(valueCnt);
                        } else {
                            if (valueCnt == 100) {
                                $(this).parents('.amount-div').find('.plus').removeClass("disabled");
                            }
                            valueCnt = parseInt(valueCnt) -1;
                            tchlgCnt.val(valueCnt);
                        }

                        equidChagneFunction();
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
            },
            modify : {
                event : {
                    click : function() {
                        var id = $(this).data("seq");
                        var $formObj = $('#frmData'+id);
                        var status = $(this).data("status");
                        var tchlgNm = $('.tchlgNm');
                        var valid = true;

                        if (id == 1) {
                            tchlgNm.each(function() {
                                if (!$(this).val()) {
                                    alert('대상장비를 입력해주세요.');
                                    valid = false;
                                    return false;
                                }
                            });
                        }

                        if (status == "접수전") {
                            if (valid) {
                                var file = $formObj.find('input[type=file]');

                                file.each(function (i) {
                                    if (!$(this).val()) {
                                        alert('신청서류를 모두 등록해주세요.');
                                        valid = false;
                                        return false;
                                    }
                                });
                            }
                        }

                        if (valid) {

                            $formObj.children().find('.comma').each(function () {
                                $(this).val($(this).val().replaceAll(",",""))
                            });

                            cmmCtrl.fileFrm(function(data){
                                if (data.respCnt == 100) {
                                    alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                }
                                location.href = "./list";
                            }, "./update", $formObj, "json");
                        }
                    }
                }
            },
        },
        immediately : function(){
            equidHtml = $('.equimentHtml').html();
            $('.equimentHtml').remove();
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

