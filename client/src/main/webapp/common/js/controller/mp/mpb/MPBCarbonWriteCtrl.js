define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBCarbonWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
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
                $('#'+fileId).closest(".form-group").find('.file-list-area').remove();
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
                        $('#'+fileId).closest(".form-group").find('.file-list-area').remove();
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
            },
            popOpen : {
                event : {
                    click : function() {
                        openPopup('paymentInfoManagPopup');
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
                        var status = $(this).data("status");
                        var file = $formObj.find('input[type=file]');
                        var valid = true;

                        //신청
                        if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01001_01_002'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01001_01_003');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01001_02_001');
                        }

                        if(id == 1){
                            if($formObj.find("#zipCode").val() == "" ||
                                $formObj.find("#dtlAddr").val() == ""){
                                alert("신청내용을 모두 입력해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if(id == 2){
                            if($formObj.find("#spprtPmt").val() == "" ||
                                $formObj.find("#phswPmt").val() == ""){
                                alert("신청내용을 모두 입력해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if (status == "접수전") {
                            file.each(function (i) {
                                if (!$(this).val()) {
                                    alert('신청서류를 모두 등록해주세요.');
                                    valid = false;
                                    return false;
                                }
                            });
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
                            if (confirm("저장 후 내용을 수정할 수 없습니다.\n" + "저장하시겠습니까?")) {
                                cmmCtrl.fileFrm(function (data) {
                                    if (data.respCnt == 100) {
                                        alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                    }
                                    location.href = "./list";
                                }, "./update", $formObj, "json");
                            }
                        }
                    }
                }
            },
            spprtModify : {
                event : {
                    click : function() {
                        var $formObj;

                        if($("#spprt1").css("display") == 'block'){
                            $formObj = $('#spprtform1');

                            if($formObj.find("#accsDt1").val() == "" ||
                                $formObj.find("#bankNm1").val() == ""  ||
                                $formObj.find("#acntNo1").val() == "" ||
                                $formObj.find("#dpsitNm1").val() == ""){
                                alert("신청내용을 모두 입력해주세요.");
                                return;
                            }
                        }else if($("#spprt2").css("display") == 'block'){
                            $formObj = $('#spprtform2');

                            if($formObj.find("#accsDt2").val() == "" ||
                                $formObj.find("#bankNm2").val()== "" ||
                                $formObj.find("#acntNo2").val()== "" ||
                                $formObj.find("#dpsitNm2").val()== "" ){
                                alert("신청내용을 모두 입력해주세요.");
                                return;
                            }
                        }

                        var file = $formObj.find('input[type=file]');
                        var valid = true;

                        //선급금
                        if($formObj.find("#spprtAppctnSttsCd1").val() == 'PRO_TYPE03001_01_001'){
                            $formObj.find("#spprtAppctnSttsCd1").val('PRO_TYPE03001_01_002');
                            $formObj.find("#spprtMngSttsCd1").val('PRO_TYPE03001_02_002');
                        }else if($formObj.find("#appctnSttsCd1").val() == 'PRO_TYPE03001_01_003'){
                            $formObj.find("#spprtAppctnSttsCd1").val('PRO_TYPE03001_01_004');
                            $formObj.find("#spprtMngSttsCd1").val('PRO_TYPE03001_02_002');
                        }

                        //지원금
                        if($formObj.find("#spprtAppctnSttsCd2").val() == 'PRO_TYPE03002_01_001'){
                            $formObj.find("#spprtAppctnSttsCd2").val('PRO_TYPE03002_01_002');
                            $formObj.find("#spprtMngSttsCd2").val('PRO_TYPE03002_02_002');
                        }else if($formObj.find("#spprtAppctnSttsCd2").val() == 'PRO_TYPE03002_01_003'){
                            $formObj.find("#spprtAppctnSttsCd2").val('PRO_TYPE03002_01_004');
                            $formObj.find("#spprtMngSttsCd2").val('PRO_TYPE03002_02_002');
                        }

                        var tabFlag = $formObj.find(".tabFlag").val();

                        if (tabFlag == "insert") {
                            file.each(function(i) {
                                if (!$(this).val()) {
                                    alert('신청서류를 모두 등록해주세요.');
                                    valid = false;
                                    return false;
                                }
                            });
                        }

                        if (valid) {
                            if(confirm("저장하시겠습니까?")) {
                                cmmCtrl.fileFrm(function (data) {
                                    if (data.respCnt == 100) {
                                        alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                    }
                                    $(".btn-role-close").click();
                                }, "./update", $formObj, "json");
                            }
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
                        $(this).closest(".form-group").find('.file-list-area').removeClass("attached");
                        $(this).closest(".form-group").find('.file-list').remove();
                    }
                }
            }
        },
        immediately : function(){
            cmmCtrl.setCalendar();
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

