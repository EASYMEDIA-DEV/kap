define(["ezCtrl", "ezVald", "ezFile"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cba/CBATechGuidanceWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');

    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var idNum = 0;

    var callbackAjaxAddrList = function (data) {
        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>선택</option>";

        for (var i = 0; i < detailList.length; i++) {

            var cd = detailList[i].cd;
            var cdNm = detailList[i].cdNm;

            selectHtml += "<option value='" + cd + "' >" + cdNm + "</option>";
        }

        $("#subAddr option").remove();

        $("#subAddr").append(selectHtml);

        var subAddr = $("#subAddr").data("subAddr");

        $("#subAddr").val(subAddr).prop("selected", true);

    }

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
            cmpnAddrSameYn : {
                event : {
                    click: function(){
                        var cmpnMst = {};
                        var cmpnAddrYn = $("#cmpnAddrSameYn").val();

                        if(cmpnAddrYn == 'N'){
                            $("cmpnAddrSameYn").val('Y');
                            cmpnMst.bsnmNo = $(".bsnmNo").val().replaceAll("-", "");
                            cmmCtrl.jsonAjax(function (data) {
                                var info = JSON.parse(data)
                                $("#hqZipcode").val(info.list[0].zipcode);
                                $("#hqBscAddr").val(info.list[0].bscAddr);
                                $("#hqDtlAddr").val(info.list[0].dtlAddr);
                            }, './bsnmNoSearch', cmpnMst, "text");
                        }else{
                            $("#cmpnAddrSameYn").val('N');
                        }
                    }
                }
            },
            agreeChk : {
                event : {
                    click : function(){
                        var agree = $("#agreeChk").val();
                        if(agree == "N"){
                            $("#agreeChk").val("Y");
                            $("#agreeChk").attr("checked", true);
                        }else{
                            $("#agreeChk").val("N");
                            $("#agreeChk").attr("checked", false)
                        }
                    }
                }
            }
        },
        classname : {
            searchPostCode: {
                event: {
                    click: function () {
                        var idVal = $(this).attr('id');
                        if (idVal == "hqAddr") {
                            cmmCtrl.searchPostCode(width, height, "hqZipcode", "hqBscAddr", "hqDtlAddr");
                        } else {
                            cmmCtrl.searchPostCode(width, height, "zipcode", "bscAddr", "dtlAddr");
                        }
                    }
                }
            },
            cmpnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".tempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dlvryRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dlvryRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dlvryTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children().find(".cmpnPlus").hide();
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                            $("#dlvryTempDiv").children().eq(0).find(".closeLabel").hide();
                            $('#dlvryTempDiv').children().not(':eq(0)').find(".closeLabel").show();
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            closeLabel : {
                event : {
                    click : function(){
                        //거래처별 매출 비중 삭제
                        $(this).parents(".tempRow").remove();
                        // 완성차 의존율 삭제
                        $(this).parents(".dpTempRow").remove();

                    }
                }
            },
            dpndnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".dpTempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dpndnRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dpndnRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dpTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children().find(".dpndnPlus").hide();
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                            $("#dpTempDiv").children().eq(0).find(".closeLabel").hide();
                            $('#dpTempDiv').children().not(':eq(0)').find(".closeLabel").show();
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            mainAddr: {
                event: {
                    click: function () {
                        var mainAddr = {}
                        mainAddr.cd = $(this).val();
                        cmmCtrl.jsonAjax(function (data) {
                            callbackAjaxAddrList(data);
                        }, './subAddrSelect', mainAddr, "text");
                    }
                }
            },cbsnCd: {
                event: {
                    click: function () {
                        var cbsnVal = $("input[name=cbsnCd]:checked").val();
                        if (cbsnVal == "TEC_GUIDE_INDUS01") { // 기타일 때 input 활성화
                            $("input[name=etcNm]").removeClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", false);
                        } else {
                            $("input[name=etcNm]").addClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", true);
                        }
                    }
                }
            },
            submit : {
                event : {
                    click : function(){
                        var agree = $("#agreeChk").val();
                        var qltyPicCnt = $(".qltyPicCnt").val();
                        var hqZipcode = $("#hqZipcode").val();
                        var mainAddr = $("#mainAddr").val();
                        var subAddr = $("#subAddr").val();
                        var rprsntApprvYn = $("input[name='rprsntApprvYn']:checked").val();
                        var appctnRsnCd = $("input[name='appctnRsnCd']:checked").val();
                        var cbsnCd = $("input[name='cbsnCd']:checked").val();
                        var appctnTypeCd = $("input[name='appctnTypeCd']:checked").val();

                        var dlvrySize = $(".dlvryCmpnNm").length;
                        for(var i =0; i<dlvrySize; i++){
                            if(!$(".dlvryCmpnNm").eq(i).val()){
                                alert("업체명을 입력해주세요.");
                                $(".dlvryCmpnNm").eq(i).focus();
                                return false;
                            }
                        }
                        var dlvryRateSize = $(".dlvryRate").length;
                        for(var i =0; i<dlvryRateSize; i++){
                            if(!$(".dlvryRate").eq(i).val()){
                                alert("매출비중을 입력해주세요.");
                                $(".dlvryRate").eq(i).focus();
                                return false;
                            }
                        }
                        //완성차 의존율
                        var dpndnSize = $(".dpndnCmpnNm").length;
                        for(var i =0; i<dpndnSize; i++){
                            if(!$(".dpndnCmpnNm").eq(i).val()){
                                alert("업체명을 입력해주세요.");
                                $(".dpndnCmpnNm").eq(i).focus();
                                return false;
                            }
                        }
                        var dpndnRateSize = $(".dpndnRate").length;
                        for(var i =0; i<dpndnRateSize; i++){
                            if(!$(".dpndnRate").eq(i).val()){
                                alert("매출비중을 입력해주세요.");
                                $(".dlvryRate").eq(i).focus();
                                return false;
                            }
                        }
                        if(!qltyPicCnt){
                            alert("품질담당 인원을 입력해주세요.");
                            $(".qltyPicCnt").focus();
                            return false;
                        }else{
                            if(!hqZipcode){
                                alert("공장주소를 입력해주세요.");
                                $("#hqZipcode").focus();
                                return false;
                            }else{
                                if(!mainAddr){
                                    alert("소재 지역을 선택해주세요.");
                                    $("#mainAddr").focus();
                                    return false;
                                }else{
                                    if(!subAddr){
                                        alert("소재 지역을 선택해주세요.");
                                        $("#subAddr").val();
                                        return false;
                                    }else{
                                        if(!rprsntApprvYn){
                                            alert("대표자 승인여부를 선택하세요.");
                                            $("input[name='rprsntApprvYn']").focus();
                                            return false;
                                        }else{
                                            if(!appctnRsnCd){
                                                alert("신청사유를 선택하세요.");
                                                $("input[name='appctnRsnCd']").focus();
                                                return false;
                                            }else{
                                                if(!cbsnCd){
                                                    alert("업종을 선택하세요.");
                                                    $("input[name='cbsnCd']").focus();
                                                    return false;
                                                }else if(cbsnCd == 'TEC_GUIDE_INDUS01'){
                                                    alert("업종을 입력해주세요.");
                                                    $("input[name='etcNm']").focus();
                                                    return false;
                                                }else{
                                                    if(!appctnTypeCd){
                                                        alert("신청사항을 선택하세요.");
                                                        $("input[name='appctnTypeCd']").focus();
                                                        return false;
                                                    }else{
                                                        var searchFile = $("#searchFile").val();
                                                        if(!searchFile) {
                                                            alert("첨부파일을 등록해주세요.");
                                                            $("#searchFile").focus();
                                                            return false;
                                                        }else{
                                                            var searchFile1 = $("#searchFile1").val();
                                                            if(!searchFile1) {
                                                                alert("첨부파일을 등록해주세요.");
                                                                $("#searchFile1").focus();
                                                                return false;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if(agree == "N"){
                            alert("약관에 동의해주세요");
                            $("#agreeChk").focus();
                            return false;
                        }else{
                            cmmCtrl.fileFrm(function(data){
                                //콜백함수. 페이지 이동
                                location.replace("./complete");
                            }, "./insert", $formObj, "json");
                        }

                    }
                }
            },telRex : {
                /**
                 * 일반 전화번호 input 이벤트
                 */
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
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
        immediately : function() {
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});