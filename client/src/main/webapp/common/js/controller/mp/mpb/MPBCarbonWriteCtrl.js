define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBCarbonWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var fileInput = "";
    var firstFileFlag = 0;

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
                // $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                // $('#'+fileId).closest(".form-group").find('.file-list-area').remove();
                alert('첨부 가능한 파일 확장자가 아닙니다.');

                /*var fileHtml = '<div class="file-list-area">'
                                    + '<p class="empty-txt">선택된 파일 없음</p>'
                                    + '</div>';

                $('#'+fileId).closest(".form-group").find('.file-list-area-wrap').append(fileHtml);*/
                $("#"+fileId+"").removeAttr("data-gtm-form-interact-field-id");

                isFile = false;
                return false;
            } else {
                //파일용량 체크
                if (typeof obj.files != "undefined")
                {
                    var fileSize = file.size;
                    var maxFileSize = maxSize * 1024 * 1024;

                    if (fileSize > maxFileSize)
                    {
                        $('#'+fileId).val("");
                        // $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                        // $('#'+fileId).closest(".form-group").find('.file-list-area').remove();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");

                        /*var fileHtml = '<div class="file-list-area">'
                                            + '<p class="empty-txt">선택된 파일 없음</p>'
                                            + '</div>';

                        $('#'+fileId).closest(".form-group").find('.file-list-area-wrap').append(fileHtml);*/
                        $("#"+fileId+"").removeAttr("data-gtm-form-interact-field-id");

                        isFile = false;
                        return false;
                    }
                }
            }
            //파일 개수 체크
            if($('#'+fileId).closest(".form-group").find(".file-list-area-wrap").children().length >= 5) {
                alert('파일 첨부는 5개까지 가능합니다.');
                return false;
            }

            /* 2024-07-29 수정 */
            if (isFile) {
                // if($('#frmData2').closest('div').hasClass('active')) {
                // if($(".paymentInfoManagPopup").css("display") != "block") {
                if($(".paymentInfoManagPopup a.active span").text().trim() != "선급금") {

                    if(firstFileFlag == 0) {
                        $('#' + fileId).closest(".form-group").find(".file-list-area-wrap").children().first().remove();
                        firstFileFlag = -1;
                    }

                    // if(fileId == "fileSearch0" || fileId == "fileSearch1" || fileId == "fileSearch2" || fileId == "fileSearch3" || fileId == "fileSearch4") {
                    if(fileId.includes("FileSearch")) {

                        /*if($('#'+fileId).closest(".form-group").find('.file-list-area').length == 1){
                            $('#'+fileId).closest(".form-group").find('.file-list-area').remove();
                        }*/

                        fileInput = jQuery(obj).clone(true);
                        var fileHtml = '<div class="file-list-area attached"><div class="file-list"><p class="file-name"><span class="name">' + fileName + '</span>';
                        fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                        fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button" data-file-id="' + fileId + '"></button></div></div>';
                        //$('#'+fileId).closest(".form-group").find(".file-list-area-wrap").find('.file-list-area').addClass("attached");
                        $('#' + fileId).closest(".form-group").find(".file-list-area-wrap").append(fileHtml);

                    }

                }else{
                    $('#'+fileId).closest(".form-group").find('.file-list').remove();

                    fileInput = jQuery(obj).clone(true);
                    var fileHtml = '<div class="file-list"><p class="file-name"><span class="name">' + fileName + '</span>';
                    fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                    fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button></div>';
                    $('#'+fileId).closest(".form-group").find('.file-list-area').addClass("attached");
                    $('#'+fileId).closest(".form-group").find('.file-list-area').append(fileHtml);
                }

            }
        }
    };

    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipcode","bscAddr","dtlAddr");
                    }
                }
            },
            spprtPmt : {
                event : {
                    change : function() {
                        var a = parseInt($(this).val().replaceAll(",",""));
                        var b = parseInt($("#phswPmt").val().replaceAll(",",""));

                        if(isNaN(a)){
                            a=0;
                        }
                        if(isNaN(b)){
                            b=0;
                        }

                        var sum = a + b;

                        $("#sum").val(sum);
                        $("#sum").keyup();

                        $("#ttlPmt").val(sum);
                    }
                }
            },
            phswPmt : {
                event : {
                    change : function() {
                        var a = parseInt($(this).val().replaceAll(",",""));
                        var b = parseInt($("#spprtPmt").val().replaceAll(",",""));

                        if(isNaN(a)){
                            a=0;
                        }
                        if(isNaN(b)){
                            b=0;
                        }

                        var sum = a + b ;

                        $("#sum").val(sum);
                        $("#sum").keyup();

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
                        var file = $formObj.find('.searchFile'); //2024-07-25 수정
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

                        //2024-07-25 수정 s
                        if (status == "접수전") {
                            var isFileAttached = false;
                            file.each(function() {
                                if ($(this).val() != "") {
                                    isFileAttached = true;
                                    return false; // 하나라도 첨부되면 루프 중단
                                }
                            });
                            if (!isFileAttached) {
                                alert("신청서류를 모두 등록해주세요.");
                                focus(".fileForm");
                                return false;
                            }
                        }
                        //2024-07-25 수정 e


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

                        //검수보고
                        if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01006_01_001'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01006_01_004');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01006_02_001');
                        } else if($formObj.find("#appctnSttsCd"+id).val() == 'PRO_TYPE01006_01_005'){
                            $formObj.find("#appctnSttsCd"+id).val('PRO_TYPE01006_01_006');
                            $formObj.find("#mngSttsCd"+id).val('PRO_TYPE01006_02_001');
                        }

                        if (valid) {
                            if (confirm("저장 후 내용을 수정할 수 없습니다.\n" + "저장하시겠습니까?")) {
                                $(".loading-area").stop().fadeIn(200);
                                cmmCtrl.fileFrm(function (data) {
                                    if (data.respCnt == 100) {
                                        alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                    }
                                    $(".loading-area").stop().fadeOut(200);
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
                            var tabFlag = $formObj.find(".tabFlag").val();

                            if($formObj.find("#accsDt1").val() == "") {
                                alert("접수일을 입력해주세요");
                                $formObj.find("#accsDt1").focus();
                                return;
                            }
                            if($formObj.find("#bankNm1").val() == "") {
                                alert("은행명을 입력해주세요");
                                $formObj.find("#bankNm1").focus();
                                return;
                            }
                            if($formObj.find("#acntNo1").val() == "") {
                                alert("계좌번호를 입력해주세요");
                                $formObj.find("#acntNo1").focus();
                                return;
                            }
                            if($formObj.find("#dpsitNm1").val() == "") {
                                alert("예금주를 입력해주세요");
                                $formObj.find("#dpsitNm1").focus();
                                return;
                            }

                            if (tabFlag == "insert") {
                                if ($formObj.find("#spprtAppctnFileSeq").val() == "") {
                                    alert("지원금신청서를 등록해주세요");
                                    $formObj.find("#spprtAppctnFileSeq").focus();
                                    return;
                                }
                                if ($formObj.find("#agrmtFileSeq").val() == "") {
                                    alert("협약서를 등록해주세요");
                                    $formObj.find("#agrmtFileSeq").focus();
                                    return;
                                }
                                if ($formObj.find("#grnteInsrncFileSeq").val() == "") {
                                    alert("보증보험증을 등록해주세요");
                                    $formObj.find("#grnteInsrncFileSeq").focus();
                                    return;
                                }
                            }

                        }else if($("#spprt2").css("display") == 'block'){
                            $formObj = $('#spprtform2');
                            var tabFlag = $formObj.find(".tabFlag").val();
                            var file = $formObj.find('.searchFile'); //2024-07-29 추가

                            if($formObj.find("#accsDt2").val() == "") {
                                alert("접수일을 입력해주세요");
                                $formObj.find("#accsDt2").focus();
                                return;
                            }
                            if($formObj.find("#bankNm2").val() == "") {
                                alert("은행명을 입력해주세요");
                                $formObj.find("#bankNm2").focus();
                                return;
                            }
                            if($formObj.find("#acntNo2").val() == "") {
                                alert("계좌번호를 입력해주세요");
                                $formObj.find("#acntNo2").focus();
                                return;
                            }
                            if($formObj.find("#dpsitNm2").val() == "") {
                                alert("예금주를 입력해주세요");
                                $formObj.find("#dpsitNm2").focus();
                                return;
                            }

                            if (tabFlag == "insert") {
                                /* 2024-07-29 수정 */
                                /*if ($formObj.find("#spprtAppctnFileSeq1").val() == "") {
                                    alert("지원금신청서를 등록해주세요");
                                    $formObj.find("#spprtAppctnFileSeq1").focus();
                                    return;
                                }
                                if ($formObj.find("#blingFileSeq").val() == "") {
                                    alert("거래명세서를 등록해주세요");
                                    $formObj.find("#blingFileSeq").focus();
                                    return;
                                }
                                if ($formObj.find("#slsFileSeq").val() == "") {
                                    alert("매출전표를 등록해주세요");
                                    $formObj.find("#slsFileSeq").focus();
                                    return;
                                }
                                if ($formObj.find("#insptChkFileSeq").val() == "") {
                                    alert("검수확인서를 등록해주세요");
                                    $formObj.find("#insptChkFileSeq").focus();
                                    return;
                                }*/
                                var isFileAttached = false;
                                file.each(function() {
                                    if ($(this).val() != "") {
                                        isFileAttached = true;
                                        return false; // 하나라도 첨부되면 루프 중단
                                    }
                                });
                                if (!isFileAttached) {
                                    alert("신청서류를 모두 등록해주세요.");
                                    focus(".fileForm");
                                    return false;
                                }
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

                        /*var tabFlag = $formObj.find(".tabFlag").val();

                        if (tabFlag == "insert") {
                            file.each(function(i) {
                                if (!$(this).val()) {
                                    alert('신청서류를 모두 등록해주세요.');
                                    valid = false;
                                    return false;
                                }
                            });
                        }*/

                        if (valid) {
                            if(confirm("저장하시겠습니까?")) {
                                $(".loading-area").stop().fadeIn(200);
                                cmmCtrl.fileFrm(function (data) {
                                    if (data.respCnt == 100) {
                                        alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                    }
                                    $(".loading-area").stop().fadeOut(200);
                                    $(".btn-role-close").click();
                                }, "./update", $formObj, "json");
                            }
                        }
                    }
                }
            },

            fileForm : {
                event : {
                    click : function(e) {
                        //2024-07-29 추가
                        if($(this).closest(".form-group").find(".file-list-area-wrap").children().length >= 5) {
                            alert('파일 첨부는 5개까지 가능합니다.');
                            return false;
                        }

                        var breckIdx = 0;
                        var idx = 0;

                        $(this).closest("div").find("input:file").each(function(){

                            if($(this).val() == ""){
                                breckIdx = idx;
                                return false;
                            }else{
                                idx++;
                            }

                        });

                        var forValue = $(this).attr("for").length == 15 ? $(this).attr("for").slice(0, -1) : $(this).attr("for");
                        $(this).attr("for", forValue + breckIdx);

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

                        if($(this).closest(".form-group").find(".file-list-area-wrap").length == 0){

                            $(this).closest(".form-group").find("input[type=file]").val("");
                            $(this).closest(".form-group").find('.file-list-area').removeClass("attached");
                            $(this).closest(".form-group").find('.file-list').remove();

                        }else{

                            $(this).closest(".file-list-area").remove();

                            var fileId = $(this).data("fileId");

                            $("#"+fileId+"").val("");
                            $("#"+fileId+"").removeAttr("data-gtm-form-interact-field-id");

                            //$(this).closest(".form-group").find("input[type=file]").val("");
                            //$(this).closest(".form-group").find('.file-list-area').removeClass("attached");
                            //$(this).closest(".form-group").find('.file-list').remove();

                            if($("#"+fileId+"").closest(".form-group").find('.file-list-area-wrap').children().length < 1) {
                                var resetHtml = '<div class="file-list-area">'
                                    + '<p class="empty-txt">선택된 파일 없음</p>'
                                    + '</div>';
                                $("#"+fileId+"").closest(".form-group").find('.file-list-area-wrap').append(resetHtml);

                                firstFileFlag = 0
                            }
                        }

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

