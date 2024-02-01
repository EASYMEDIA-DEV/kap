define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cbb/CBBManageConsultWriteCtrl"
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

            if (isFile) {
                $('#'+fileId).closest(".form-group").find('.empty-txt').text(obj.files[0].name);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
            sameAsHQChk : {
                event : {
                    click: function(){
                        var cmpnMst = {};
                        cmpnMst.bsnmNo = $(".bsnmNo").val().replaceAll("-", "");
                        cmmCtrl.jsonAjax(function (data) {
                            var info = JSON.parse(data)
                            $("#hqZipcode").val(info.list[0].zipcode);
                            $("#hqBscAddr").val(info.list[0].bscAddr);
                            $("#hqDtlAddr").val(info.list[0].dtlAddr);
                        }, './bsnmNoSearch', cmpnMst, "text");
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
            },
            infoSameChk : {
                event : {
                    click : function(){
                        var cmpnMst = {};
                        cmpnMst.bsnmNo = $(".bsnmNo").val().replaceAll("-", "");
                        cmmCtrl.jsonAjax(function (data) {
                            var info = JSON.parse(data)
                            $("#hqZipcode").val(info.list[0].zipcode);
                            $("#hqBscAddr").val(info.list[0].bscAddr);
                            $("#hqDtlAddr").val(info.list[0].dtlAddr);
                        }, './selectDtlInfo', cmpnMst, "text");
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
                        // 주고객사 납품 비율 삭제
                        $(this).parents(".tempRow").remove();
                        // 국내 완성차 의존율 삭제
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
            cmpnAddrSameYn : {
                event : {
                    click: function(){
                        var cmpnMst = {};
                        var cmpnAddrYn = $("#cmpnAddrSameYn").val();

                        if(cmpnAddrYn == 'N'){
                            $("#cmpnAddrSameYn").val('Y');
                            cmpnMst.bsnmNo = $(".bsnmNo").val().replaceAll("-", "");
                            cmmCtrl.jsonAjax(function (data) {
                                var info = JSON.parse(data)
                                $("#hqZipcode").val(info.list[0].zipcode);
                                $("#hqBscAddr").val(info.list[0].bscAddr);
                                $("#hqDtlAddr").val(info.list[0].dtlAddr);
                            }, './bsnmNoSearch', cmpnMst, "text");
                        }else{
                            $("#cmpnAddrSameYn").val('N');
                            $("#hqZipcode").val("");
                            $("#hqBscAddr").val("");
                            $("#hqDtlAddr").val("");
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
            }, rqstCntn : {
                event : {
                    input : function(){
                        var textLength = $(this).val().length;
                        $(".current-byte").text(textLength);
                    }
                }
            }
        },
        classname : {
            searchPostCode: {
                event: {
                    click: function () {
                        $("#cmpnAddrSameYn").val('N');
                        $("#cmpnAddrSameYn").attr("checked", false);
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
                        var cpSize = $('input[name=cpSize]:checked').val();
                        var frgnDpndnRate = $(".frgnDpndnRate").val();
                        var dmstcSlsPmt = $(".dmstcSlsPmt").val();
                        var frgnSlsPmt = $(".frgnSlsPmt").val();
                        var ttlSlsPmt = $(".ttlSlsPmt").val();
                        var carPartSlsPmt = $(".carPartSlsPmt").val();
                        var carPartXcludSlsPmt = $(".carPartXcludSlsPmt").val();
                        var cbsnCdSelect = $("#cbsnCdSelect").val();
                        var qltyPicCnt = $(".qltyPicCnt").val();
                        var hqZipcode = $("#hqZipcode").val();
                        var mainAddr = $("#mainAddr").val();
                        var subAddr = $("#subAddr").val();
                        var appctnRsnCd = $("input[name='appctnRsnCd']:checked").val();
                        var rqstCntn = $("#rqstCntn").val();


                        if(!cpSize){
                            alert("부품사 규모를 선택해주세요.");
                            $(".cpSize").focus();
                            return false;
                        }else{
                            var dlvrySize = $(".dlvryCmpnNm").length;
                            for(var i =0; i<dlvrySize; i++){
                                if(!$(".dlvryCmpnNm").eq(i).val()){
                                    alert("업체명을 입력해주세요.");
                                    $(".dlvryCmpnNm").eq(i).focus();
                                    return false;
                                }else{
                                    if(!$(".dlvryRate").eq(i).val()){
                                        alert("비율을 입력해주세요.");
                                        $(".dlvryRate").eq(i).focus();
                                        return false;
                                    }
                                }
                            }

                            //완성차 의존율
                            var dpndnSize = $(".dpndnCmpnNm").length;
                            for(var i =0; i<dpndnSize; i++){
                                if(!$(".dpndnCmpnNm").eq(i).val()){
                                    alert("업체명을 입력해주세요.");
                                    $(".dpndnCmpnNm").eq(i).focus();
                                    return false;
                                }else if(!$(".dpndnRate").eq(i).val()){
                                    alert("의존율을 입력해주세요.");
                                    $(".dpndnRate").eq(i).focus();
                                    return false;
                                }
                            }
                            if(!frgnDpndnRate){
                                alert("해외 의존율을 입력해주세요.");
                                $(".frgnDpndnRate").focus();
                                return false;
                            }else{
                                if(!dmstcSlsPmt){
                                    alert("국내 매출액을 입력해주세요.");
                                    $(".dmstcSlsPmt").focus();
                                    return false;
                                }else{
                                    if(!frgnSlsPmt){
                                        alert("해외 매출액을 입력해주세요.");
                                        $(".frgnSlsPmt").focus();
                                        return false;
                                    }else{
                                        if(!ttlSlsPmt){
                                            alert("전체 매출액을 입력해주세요.");
                                            $(".ttlSlsPmt").focus();
                                            return false;
                                        }else{
                                            if(!carPartSlsPmt){
                                                alert("자동차 부품 매출액을 입력해주세요.");
                                                $(".carPartSlsPmt").focus();
                                                return false;
                                            }else{
                                                if(!carPartXcludSlsPmt){
                                                    alert("자동차 부품 외 매출액을 입력해주세요.");
                                                    $(".carPartXcludSlsPmt").focus();
                                                    return false;
                                                }else{
                                                    if(!hqZipcode){
                                                        alert("공장주소를 입력해주세요.");
                                                        $("#hqZipcode").focus();
                                                        return false;
                                                    }else{
                                                        if(mainAddr == "선택"){
                                                            alert("소재 지역을 선택해주세요.");
                                                            $("#mainAddr").focus();
                                                            return false;
                                                        }else{
                                                            if(subAddr == ""){
                                                                alert("소재 지역을 선택해주세요.");
                                                                $("#subAddr").focus();
                                                                return false;
                                                            }else{
                                                                if(!appctnRsnCd){
                                                                    alert("신청사유를 선택해주세요.");
                                                                    $("input[name='appctnRsnCd']").focus();
                                                                    return false;
                                                                }else{
                                                                    if(!qltyPicCnt){
                                                                        alert("품질담당 인원을 입력해주세요.");
                                                                        $(".qltyPicCnt").focus();
                                                                        return false;
                                                                    }else{
                                                                        if(cbsnCdSelect == "선택"){
                                                                            alert("신청분야를 선택해주세요.");
                                                                            $("#cbsnCdSelect").focus();
                                                                            return false;
                                                                        }else{
                                                                            if(!rqstCntn){
                                                                                alert("컨설팅요청 세부내용을 입력해주세요.");
                                                                                $("#rqstCntn").focus();
                                                                                return false;
                                                                            }else{
                                                                                var searchFile = $("#searchFile").val();
                                                                                if(!searchFile) {
                                                                                    alert("회사소개서를 등록해주세요.");
                                                                                    $("#searchFile").focus();
                                                                                    return false;
                                                                                }else{
                                                                                    var searchFile1 = $("#searchFile1").val();
                                                                                    if(!searchFile1) {
                                                                                        alert("개선활동 추진계획서를 등록해주세요.");
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
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        var agree = $("#agreeChk").val();
                        if(agree == "N"){
                            alert("약관에 동의해주세요");
                            $("#agreeChk").focus();
                            return false;
                        }else{
                            if(confirm("위 정보로 사업을 신청하시겠습니까?")){
                                cmmCtrl.fileFrm(function(data){
                                    var cnstgSeq = $(".cnstgSeq").val();
                                    //콜백함수. 페이지 이동
                                    location.replace("./complete?cnstgSeq="+cnstgSeq);
                                }, "./insert", $formObj, "json");
                            }
                        }
                    }
                }
            },
            searchFile : {
                event : {
                    change: function () {
                        extnCheck(this, "jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip", 50);
                    }
                }
            },
            cancelApply : {
                event : {
                    click : function(e){
                        if(e){
                            if(confirm("현재 입력된 정보는 초기화됩니다. 계속하시겠습니까?")){
                                location.href="./content";
                            }
                        }
                    }
                }
            },
            addr : {
                event : {
                    change : function(){
                        $("#cmpnAddrSameYn").val('N');
                        $("#cmpnAddrSameYn").attr("checked", false);
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