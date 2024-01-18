define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBSecurityWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

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
            var fileName = file.name.split(".")[0];

            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                //파일확장자 체크
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find('.file-list').empty();
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
                        $('#'+fileId).closest(".form-group").find('.file-list').empty();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }
                }
            }

            if (isFile) {
                var fileHtml = '<p class="file-name"><span class="name">' + fileName + '</span>';
                fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button>';
                $('#'+fileId).closest(".form-group").find('.file-list').append(fileHtml);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
            spprtPmt : {
                event : {
                    change : function() {

                        var a = parseInt($(this).val());
                        var b = parseInt($("#phswPmt").val());
                        if(isNaN(a)){
                            a=0;
                        }else if(isNaN(b)){
                            b=0;
                        }

                        var sum = a + b;

                        $("#sum").val(sum);
                        $("#ttlPmt").val(sum);
                    }
                }
            },
            phswPmt : {
                event : {
                    change : function() {
                        var a = parseInt($(this).val());
                        var b = parseInt($("#spprtPmt").val());

                        if(isNaN(a)){
                            a=0;
                        }else if(isNaN(b)){
                            b=0;
                        }

                        var sum = a + b ;

                        $("#sum").val(sum);
                        $("#ttlPmt").val(sum);

                    }
                }
            }
        },
        classname : {
            modify : {
                event : {
                    click : function() {
                        var id = $(this).data("seq");
                        var $formObj = $('#frmData'+id);
                        var file = $formObj.find('input[type=file]');
                        var valid = true;

                        console.log($formObj);

                        file.each(function(i) {
                            if (!$(this).val()) {
                                alert('신청서류를 모두 등록해주세요.');
                                valid = false;
                                return false;
                            }
                        });

                        //신청
                        if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01001_01_002'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01001_01_003');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01001_02_001');
                        }

                        //사업계획
                        if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01002_01_001'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01002_01_002');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01002_02_001');
                        }else if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01002_01_003'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01002_01_004');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01002_02_001');
                        }

                        //완료보고
                        if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01004_01_001'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01004_01_002');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01004_02_001');
                        } else if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01004_01_003'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01004_01_004');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01004_02_001');
                        }

                        if (valid) {
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
            }
        },
        immediately : function(){

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

