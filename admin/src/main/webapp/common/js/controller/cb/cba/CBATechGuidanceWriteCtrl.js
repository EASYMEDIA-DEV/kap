define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function (ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/cb/cba/CBATechGuidanceWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var valChk = 'N';
    var idNum = 0;

    var search = function (page){
        //data로 치환해주어야한다.
        //cmmCtrl.setFormData($formObj);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#trsfListContainer").html(respObj);

            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#trsfListContainerTotCnt").val(totCnt);

            $(".ptcptField").validation({});


            var data = {};
            data.detailsKey = $(".detailsKey").val();
            data.detailsKey = $(".detailsKey").val();


            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "trsfListContainer", "trsfPagingContainer");

        }, "/mngwserc/cb/cba/trsfList", $formObj, "GET", "html");
    }

    var changeStts = function(){
        var bfJdgmtRslt = $("#bfJdgmtRslt").val();
        var initVstRsltCd = $("#initVstRsltCd").val();
        var guidePscndCd = $("#guidePscndCd").val();
        var guideBgnDt = $('input[name="guideBgnDt"]').val();
        var guideKickfDt = $('input[name="guideKickfDt"]').val();
        var today = new Date();
        var gbDt = new Date(guideBgnDt);
        var kickDt = new Date(guideKickfDt);
        var bfGbDt = (gbDt-today)/(24*60*60*1000);


        // 킥오프일이 없거나, 킥오프일 전까지
        if(bfGbDt<=0){
            if(!guideKickfDt || today-kickDt < 0 ){
                $(".rsumeSttsNm").text("지도착수");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_08");
                // 킥오프일 이후면서 지도 완료가 아닐 때
            }else if(today-kickDt >=0 && guidePscndCd != "GUIDE_PSCND04"){
                $(".rsumeSttsNm").text("지도중");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_09");
                if(guidePscndCd == 'GUIDE_PSCND01'){
                    $(".rsumeSttsNm").text("지도연기");
                    $(".rsumeSttsCd").val("MNGTECH_STATUS_10");
                }else if(guidePscndCd == 'GUIDE_PSCND02'){
                    $(".rsumeSttsNm").text("재단취소");
                    $(".rsumeSttsCd").val("MNGTECH_STATUS_11");
                }else if(guidePscndCd == 'GUIDE_PSCND03'){
                    $(".rsumeSttsNm").text("부품사취소");
                    $(".rsumeSttsCd").val("MNGTECH_STATUS_12");
                }
            }else if(guidePscndCd == 'GUIDE_PSCND04'){
                $(".rsumeSttsNm").text("지도 완료");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_13");
            }
        }else if(bfGbDt>0 || guideBgnDt == ''){
            if (bfJdgmtRslt == 'BF_JDGMT_RSLT05' || initVstRsltCd == 'BF_JDGMT_RSLT05') {
                $(".rsumeSttsNm").text("사용자취소");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_02");
            }else if (bfJdgmtRslt == 'BF_JDGMT_RSLT01') {
                $(".rsumeSttsNm").text("사전심사선정");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_03");
            }else if (bfJdgmtRslt == 'BF_JDGMT_RSLT02') {
                $(".rsumeSttsNm").text("사전심사탈락");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_04");
            }else if (bfJdgmtRslt == 'BF_JDGMT_RSLT03' || initVstRsltCd == 'BF_JDGMT_RSLT03') {
                $(".rsumeSttsNm").text("지원단이관");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_05");
            }else if(initVstRsltCd == 'BF_JDGMT_RSLT01'){
                $(".rsumeSttsNm").text("지도승인");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_06");
            }else if(initVstRsltCd == 'BF_JDGMT_RSLT02'){
                $(".rsumeSttsNm").text("지도불가");
                $(".rsumeSttsCd").val("MNGTECH_STATUS_07");
            }
        }
    }

    var callbackAjaxDelete = function (data) {

        if (parseInt(data.respCnt, 10) > 0) {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        } else {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    var callbackAjaxBsnmNo = function (data) {

        var detailList = JSON.parse(data);

        for (var i = 0; i < detailList.length; i++) {

            var cmpnNm = detailList[0].cmpnNm
            var bscAddr = detailList[0].bscAddr
            var dtlAddr = detailList[0].dtlAddr
            var zipcode = detailList[0].zipcode
            var bsnmNo = detailList[0].bsnmNo
            var sizeCd = detailList[0].sizeCd
            var rprsntNm = detailList[0].rprsntNm
            var cmpnTelNo = detailList[0].telNo
            var ctgryCd = detailList[0].ctgryCd
            var stbsmDt = detailList[0].stbsmDt
            var cmpnCd = detailList[0].cmpnCd
            var cmpnNfrmlNm = detailList[0].cmpnNfrmlNm

            var cmpnSlsPmt = detailList[0].slsPmt
            var cmpnSlsYear = detailList[0].slsYear
            var cmpnMpleCnt = detailList[0].mpleCnt
            var mjrPrdct1 = detailList[0].mjrPrdct1
            var mjrPrdct2 = detailList[0].mjrPrdct2
            var mjrPrdct3 = detailList[0].mjrPrdct3

            if(ctgryCd == 'COMPANY01001'){
                var qlty5StarCd = detailList[0].qlty5StarCd
                var qlty5StarYear = detailList[0].qlty5StarYear
                var pay5StarYear = detailList[0].pay5StarYear
                var pay5StarCd = detailList[0].pay5StarCd
                var tchlg5StarCd = detailList[0].tchlg5StarCd
                var tchlg5StarYear = detailList[0].tchlg5StarYear

            }else{

                var crtfnCmpnNm1 = detailList[0].crtfnCmpnNm
                var nm1 = detailList[0].nm
                var cbsnSeq1 = detailList[0].cbsnSeq
                var score1 = detailList[0].score
                var year1 = detailList[0].year

                $('#nm').val(nm1);
                $('#score').val(score1);
                $('#crtfnCmpnNm').val(crtfnCmpnNm1);
                $('#cbsnSeq').val(cbsnSeq1);

                if(detailList.length >= 2){

                    var crtfnCmpnNm2 = detailList[1].crtfnCmpnNm
                    var nm2 = detailList[1].nm
                    var cbsnSeq2 = detailList[1].cbsnSeq
                    var score2 = detailList[1].score
                    var year2 = detailList[1].year

                    $("#nm1").val(nm2);
                    $('#score1').val(score2);
                    $('#crtfnCmpnNm1').val(crtfnCmpnNm2);
                    $('#cbsnSeq1').val(cbsnSeq2);

                    if(detailList.length == 3){
                        var crtfnCmpnNm3 = detailList[2].crtfnCmpnNm
                        var nm3 = detailList[2].nm
                        var cbsnSeq3 = detailList[2].cbsnSeq
                        var score3 = detailList[2].score
                        var year3 = detailList[2].year

                        $("#nm2").val(nm3);
                        $("#score2").val(score3);
                        $("#crtfnCmpnNm2").val(crtfnCmpnNm3);
                        $("#cbsnSeq2").val(cbsnSeq3);
                    }
                }
            }

            $("#ctgryCdSelect").val(ctgryCd).prop("selected", true);
            if (ctgryCd == "COMPANY01001") {
                $(".sqInfo").hide();
                $(".fiveStar").show();
            } else {
                $(".fiveStar").hide();
                $(".sqInfo").show();
            }
            $("#yearSelect").val(year1).prop("selected", true);
            $("#yearSelect1").val(year2).prop("selected", true);
            $("#yearSelect2").val(year3).prop("selected", true);

            $("#sizeCdSelect").val(sizeCd).prop("selected", true);
            $("#qlty5StarCd").val(qlty5StarCd).prop("selected", true);
            $("#qlty5StarYear").val(qlty5StarYear).prop("selected", true);
            $("#pay5StarCd").val(pay5StarCd).prop("selected", true);
            $("#pay5StarYear").val(pay5StarYear).prop("selected", true);
            $("#tchlg5StarCd").val(tchlg5StarCd).prop("selected", true);
            $("#tchlg5StarYear").val(tchlg5StarYear).prop("selected", true);
            $("#slsYear").val(cmpnSlsYear).prop("selected", true);

            $('p[name=rprsntNm]').text(rprsntNm);
            $("#bsnmNo").val(bsnmNo);
            $('p[name=cmpnNm]').text(cmpnNm);
            $('input[name=zipcode]').val(zipcode);
            $('input[name=bscAddr]').val(bscAddr);
            $('input[name=dtlAddr]').val(dtlAddr);
            $('input[name=rprsntNm]').val(rprsntNm);
            $('input[name=stbsmDt]').val(stbsmDt);

            $('input[name=mjrPrdct1]').val(mjrPrdct1);
            $('input[name=mjrPrdct2]').val(mjrPrdct2);
            $('input[name=mjrPrdct3]').val(mjrPrdct3);

            $('input[name=slsPmt]').val(cmpnSlsPmt);
            $('input[name=mpleCnt]').val(cmpnMpleCnt);
            $('input[name=cmpnTelNo]').val(cmpnTelNo);

            $('input[name=cmpnNfrmlNm]').val(cmpnNfrmlNm);
            $('input[name=cmpnCd]').val(cmpnCd);

        }
    }
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

    var questionSet = function(){

        $(".surveyList").each(function(){
            var surveyTypeData = $(this).data('survey-type');
            var cnt = 1;
            var subCnt = 1;
            $("."+surveyTypeData).each(function(index){                         // 질문, 하위질문 번호를 구분하고 순서를 셋팅
                if ($(this).find('input[name=dpth]').val() == '2'){
                    $("."+surveyTypeData+"questionTxt:eq("+index+")").text("└질문"+eval(cnt-1)+"-"+subCnt);
                    subCnt = subCnt + 1;
                }else{
                    $("."+surveyTypeData+"questionTxt:eq("+index+")").text("질문"+cnt);
                    cnt = cnt+1;
                    subCnt = 1;
                }
            });
        });
    }

    // set model
    ctrl.model = {
        id: {
            checkBtn: {
                event: {
                    click: function () {
                        var checkNo = $("#bsnmNo").val();
                        var ajaxData = {
                            bsnmNo: checkNo
                        }

                        jQuery("#frmData").serializeArray().forEach(function (field) {
                            if (field.name == '_csrf') {
                                ajaxData[field.name] = field.value;
                            }
                        });

                        console.log(JSON.stringify(ajaxData, null, 2));

                        if (valChk) {
                            $.ajax({
                                type: "post",
                                url: './checkBsnmNo',
                                dataType: "json",
                                data: ajaxData,
                                success: function (r) {
                                    console.log(r.respCnt);
                                    console.log(r);
                                    if (r.respCnt) {
                                        alert("인증되었습니다.");
                                        $("#cmpnNm").val(r.cmpnNm);
                                        $("#rprsntNm").val(r.rprsntNm);
                                    } else {
                                        alert('DB에 등록된 정보가 없습니다. NICE 인증으로 넘어갑니다.')
                                        $("#cmpnNm").val("nice에서 가져온 회사명");
                                        $("#rprsntNm").val("nice에서 가져온 대표자명");
                                    }
                                },
                                error: function (xhr, ajaxSettings, thrownError) {
                                    alert("인증에 실패했습니다.");
                                }
                            });
                        } else {
                            alert("사업자등록번호를 인증해주세요");
                            return false;
                        }
                    }
                }
            },
            ctgryCd: {
                event: {
                    change: function () {
                        var ctgryCd = $("#ctgryCd option:selected").val();
                        if (ctgryCd == "COMPANY01001") {
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").show();
                            $(".pay5StarArea").show();
                            $(".tchlg5StarArea").show();
                        } else if (ctgryCd == "COMPANY01002") {
                            $(".sqInfoArea").show();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();
                        }
                    }
                }
            },
            pstnCdSelect: {
                event: {
                    click: function () {
                        var clickVal = $(this).val();
                        if (clickVal == 'MEM_CD01007') {
                            $(".pstnCdInput").show();
                            $("input[name='pstnNm']").removeClass("notRequired");
                        } else {
                            $(".pstnCdInput").hide();
                            $("input[name='pstnNm']").addClass("notRequired");
                        }
                    }
                }
            }, btnRefresh : {
                event : {
                    click : function(){
                    }
                }
            },
            bfJdgmtRslt : {
                event : {
                    click : function(){
                        changeStts();
                    }
                }
            },
            guidePscndCd : {
                event : {
                    click : function(){
                        changeStts();
                    }
                }
            },
            initVstRsltCd : {
                event : {
                    click: function(){
                        changeStts();
                    }
                }
            },
            submit : {
                event : {
                    click : function(){

                        var deptCd = $(".deptCd").val();
                        var pstnCd = $("#pstnCdSelect").val();
                        var ctgryCd = $(".ctgryCd").val();
                        var mainAddr = $("#mainAddr").val();
                        var subAddr = $("#subAddr").val();
                        var cmpnTelNo = $("#cmpnTelNo").val();
                        var hqZipcode = $("#hqZipcode").val();
                        var sizeCdSelect = $("#sizeCdSelect").val();
                        var rprsntNmTxt = $("#rprsntNmTxt").val();
                        var qltyPicCnt = $(".qltyPicCnt").val();
                        var zipcode = $("#zipcode").val();
                        var subAddr  = $("#subAddr ").val();
                        var rprsntApprvYn = $("input[name='rprsntApprvYn']:checked").val();
                        var appctnRsnCd = $("input[name='appctnRsnCd']:checked").val();
                        var cbsnCd = $("input[name='cbsnCd']:checked").val();
                        var appctnTypeCd = $("input[name='appctnTypeCd']:checked").val();
                        
                        if(!deptCd || deptCd == '선택'){
                            alert("부서를 선택해주세요.");
                            $("#deptCd").focus();
                            return false;
                        }else{
                            if(!pstnCd || pstnCd == '선택'){
                                alert("직급을 선택해주세요.");
                                $("#pstnCdSelect").focus();
                                return false;
                            }else{
                                if(!ctgryCd){
                                    alert("구분을 선택해주세요.");
                                    $("#ctgryCdSelect").focus();
                                    return false;
                                }else{
                                    if(!sizeCdSelect){
                                        alert("규모를 선택해주세요.")
                                        $("#sizeCdSelect").focus();
                                        return false;
                                    }else{
                                        if(!rprsntNmTxt){
                                            alert("대표자명을 입력해주세요.")
                                            $("#rprsntNmTxt").focus();
                                            return false;
                                        }else{
                                            if(!cmpnTelNo){
                                                alert("전화번호를 입력해주세요.");
                                                $("#cmpnTelNo").focus();
                                                return false;
                                            }else{
                                                if(!hqZipcode){
                                                    alert("주소를 입력해주세요.");
                                                    $("#hqZipcode").focus();
                                                    return false;
                                                }else{
                                                    // 거래처별 매출
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
                                                        alert("품질담당인원을 입력해주세요.")
                                                        $(".qltyPicCnt").focus();
                                                        return false;
                                                    }else{
                                                        if(!zipcode){
                                                            alert("주소를 입력해주세요.")
                                                            $("#zipcode").focus();
                                                            return false;
                                                        }else{
                                                            if(mainAddr == '선택'){
                                                                alert("소재 지역을 선택해주세요.");
                                                                $("#mainAddr").focus();
                                                                return false;
                                                            }else{
                                                                if(subAddr == ''){
                                                                    alert("소재 지역을 선택해주세요.");
                                                                    $("#subAddr").focus();
                                                                    return false;
                                                                }else{
                                                                    if(!rprsntApprvYn){
                                                                        alert("대표자 승인여부를 선택해주세요.");
                                                                        $("input[name='rprsntApprvYn']").focus();
                                                                        return false;
                                                                    }else{
                                                                        if(!appctnRsnCd){
                                                                            alert("신청사유를 선택해주세요.");
                                                                            $("input[name='appctnRsnCd']").focus();
                                                                            return false;
                                                                        }else{
                                                                            if(!cbsnCd){
                                                                                alert("업종을 선택해주세요.");
                                                                                $("input[name='cbsnCd']").focus();
                                                                                return false;
                                                                            }else if(cbsnCd == 'TEC_GUIDE_INDUS01'){
                                                                                alert("업종을 입력해주세요.");
                                                                                $("input[name='etcNm']").focus();
                                                                                return false;
                                                                            }else{
                                                                                if(!appctnTypeCd){
                                                                                    alert("신청사항을 선택해주세요.");
                                                                                    $("input[name='appctnTypeCd']").focus();
                                                                                    return false;
                                                                                }else{
                                                                                    var fileVal = $(".dropzone .dz-preview").length;
                                                                                    if(fileVal <2) {
                                                                                        if ($(".dropzone .dz-preview").eq(0).length == 0) {
                                                                                            alert("첨부파일을 등록해주세요.");
                                                                                            return false;
                                                                                        } else if ($(".dropzone .dz-preview").eq(1).length == 0) {
                                                                                            alert("첨부파일을 등록해주세요.");
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
                        }


                        var actionUrl = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update");
                        var actionMsg = msgCtrl.getMsg("success.ins");
                        var cmpnNm = $("#cmpnNmText").text();
                        var bsnmNo = $("#bsnmNoText").text();
                        var emailTxt = $("#emailTxt").text();
                        var rprsntNmTxt = $("#rprsntNmTxt").text();
                        var cbsnCd = $("input[name='cbsnCd']:checked").val();

                        var vstDt = $("input[name='vstDt']").val();
                        if (!vstDt) {
                            $("input[name='vstDt']").prop("disabled", true);
                        }

                        var guidePscndDt = $("input[name='guidePscndDt']").val();
                        if (!guidePscndDt) {
                            $("input[name='guidePscndDt']").prop("disabled", true);
                        }

                        var guideBgnDt = $("input[name='guideBgnDt']").val();
                        if (!guideBgnDt) {
                            $("input[name='guideBgnDt']").prop("disabled", true);
                        }

                        var guideKickfDt = $("input[name='guideKickfDt']").val();
                        if (!guideKickfDt) {
                            $("input[name='guideKickfDt']").prop("disabled", true);
                        }

                        var srvStrtDtm = $("input[name='srvStrtDtm']").val();
                        if (!srvStrtDtm) {
                            $("input[name='srvStrtDtm']").prop("disabled", true);
                        }

                        var srvEndDtm = $("input[name='srvEndDtm']").val();
                        if (!srvEndDtm) {
                            $("input[name='srvEndDtm']").prop("disabled", true);
                        }

                        $("#cmpnNm").val(cmpnNm);
                        $("#bsnmNo").val(bsnmNo);
                        $("#email").val(emailTxt);
                        $("#rprsntNm").val(rprsntNmTxt);
                        $("#cbsnCd").val(cbsnCd);

                        if(confirm("저장하시겠습니까?")){
                            if ($formObj.find(".dropzone").size() > 0) {
                                console.log($formObj);
                                cmmCtrl.fileFrmAjax(function (data) {
                                    //콜백함수. 페이지 이동
                                    if (data.respCnt > 0) {
                                        alert(actionMsg);
                                        location.replace("./list");
                                    }
                                }, actionUrl, $formObj, "json");
                            }
                        }else{
                            return false;
                        }
                    }
                }
            }
        },
        classname: {
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
            cmpnPlus: {
                event: {
                    click: function () {
                        var rowCnt = $(".tempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dlvryRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dlvryRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dlvryTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children(".closeLabel").css("display", "block");
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            dpndnPlus: {
                event: {
                    click: function () {
                        var rowCnt = $(".dpTempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dpndnRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dpndnRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dpTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children(".closeLabel").css("display", "block");
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            closeLabel: {
                event: {
                    click: function () {
                        $(this).parent().remove();
                    }
                }
            },
            addrSame: {
                event: {
                    click: function () {
                        var checked = $(".addrSame").is(':checked');
                        var sameVal = $(this).val();
                        if (sameVal == 'Y') {
                            $(this).val("N");
                            $(".factAddr").prop("disabled", false);
                            $("#dtlAddr").prop("readOnly", false);
                        } else {
                            $(this).val("Y");
                            $(".factAddr").prop("disabled", true);
                            $("#dtlAddr").prop("readOnly", true);
                        }
                        if (checked) {
                            var hqZipcode = $("#hqZipcode").val();
                            var hqBscAddr = $("#hqBscAddr").val();
                            var hqDtlAddr = $("#hqDtlAddr").val();
                            $("#zipcode").val(hqZipcode);
                            $("#bscAddr").val(hqBscAddr);
                            $("#dtlAddr").val(hqDtlAddr);
                        }
                    }
                }
            },
            ctgryCd: {
                event: {
                    click: function () {
                        var selectVal = $("select[name=ctgryCd]").val();
                        if (selectVal == "COMPANY01001") {
                            $(".sqInfo").hide();
                            $(".fiveStar").show();
                        } else {
                            $(".fiveStar").hide();
                            $(".sqInfo").show();
                        }
                    }
                }
            },
            cbsnCd: {
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
            btnPartUserModal: {
                event: {
                    click: function () {
                        $("#srchDivide").val("Y");
                        $(".srchGubun").remove();
                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            var cmpnMst = {};
                            cmpnMst.bsnmNo = data.bsnmNo.replaceAll("-", "");
                            cmpnMst.memSeq = data.memSeq;

                            cmmCtrl.jsonAjax(function (data) {
                                callbackAjaxBsnmNo(data);
                            }, './bsnmNoSearch', cmpnMst, "text");
                        });
                    }
                }
            },
            btnCmtSearch: {
                event: {
                    click: function () {
                        $("#cmtSrchDivide").val("Y");
                        cmmCtrl.getCmtSrchPop(function (data) {

                            $("input[name=cmssrSeq]").val(data.seq);
                            $("input[name=cmssrName]").val(data.name);
                        });

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
            },
            initVstRsltCd: {
                event: {
                    click: function () {
                        var rsltCd = $(this).val();
                        if (rsltCd == "BF_JDGMT_RSLT02") {
                            $(".rsltCntn").show();
                        } else {
                            $(".rsltCntn").hide();
                        }
                    }
                }
            },
            //설문 검색
            srvSearch: {
                event: {
                    click : function(){
                        $(".svaSurveySrchLayer").one('show.bs.modal', function() {

                            var modal = $(this);
                            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                        }).one('choice', function(data, param) {
                            var obj = param;
                            $("#listContainer3").find("td").eq(0).text(obj.typeNm);
                            $("#listContainer3").find("td").eq(1).text(obj.titl);
                            $("#srvSeq").val(obj.seq);
                        }).modal();
                    }
                }
            },tabClick : {
                event : {
                    click : function (e){
                        if(e.target.getAttribute('href') == "#techGuidance") {
                            $("#episdList").addClass("in active");
                            $("#svResult").removeClass("in active");
                        } else {
                            $("#episdList").removeClass("in active");
                            $("#svResult").addClass("in active");
                        }
                    }
                }
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
                        }
                    }
                }
            },
            //페이징 목록 갯수

            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            },
            appctnPdfDownload : {
                event : {
                    click : function(){
                        var cmpnNmText = $("#cmpnNmText").text();
                        var date = new Date();
                        var year = date.getFullYear();
                        var month = ("0" + (1 + date.getMonth())).slice(-2);
                        var day = ("0" + date.getDate()).slice(-2);
                        var fullDay = year + month + day;

                        var fileName = "기술지도 사업현황_"+cmpnNmText+"_"+fullDay+".pdf";
                        cmmCtrl.getAppctnPdfDownload(fileName);
                    }
                }
            },
            srvResult : {
                event : {
                    click : function(){
                        var pstnText = $("#pstnCdSelect option:selected").text();
                        $(".pstnText").text(pstnText);
                        var cbsnText = $("input[name='cbsnCd']:checked").parent().text().trim();
                        $(".cbsnText").text(cbsnText);
                        var guideTypeText = $("#guideTypeCd option:selected").text();
                        $(".guideTypeText").text(guideTypeText);
                        var cmssrName = $("input[name='cmssrName']").val();
                        $(".cmssrName").text(cmssrName);
                    }
                }
            },
            fltyImpvmRate : {
                event : {
                    change: function(){
                        var bfreRate = $(".fltyImpvmBfreRate").val();
                        var aftrRate = $(".fltyImpvmAftrRate").val();
                        var rslt = (bfreRate - aftrRate)/bfreRate*100;

                        $(".fltyImpvmRate").val(rslt.toFixed(1));

                    }
                }
            }, //리스트 전체 체크박스 선택시
            checkboxAll : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxSingle가 변경됨
                        var trgtArr = $(this).closest("div").find(".checkboxSingle");
                        if (trgtArr.length > 0)
                        {
                            var isChecked = false;
                            if($(this).is(":checked"))
                            {
                                isChecked = true;
                            }
                            $.each(trgtArr, function(){
                                $(this).prop("checked", isChecked);
                            })
                        }
                    }
                }
            },checkboxSingle : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxAll이 변경됨
                        var trgtObj = $(this).closest("div");
                        var allCbxCnt = trgtObj.find(".checkboxSingle").length;
                        var selCbxCnt = trgtObj.find(".checkboxSingle:checked").length;
                        if (allCbxCnt == selCbxCnt)
                        {
                            trgtObj.find(".checkboxAll").prop("checked", true);
                        }
                        else
                        {
                            trgtObj.find(".checkboxAll").prop("checked", false);
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
            }
        },
        immediately: function () {
            changeStts(); // 진행상태 변경
            questionSet();
            /* Editor Setting */
            jQuery("textarea[id^='cntn']").each(function () {
                cmmCtrl.setEditor({
                    editor: jQuery(this).attr("id"),
                    height: 400,
                });
            });

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function () {
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt: trgtObj.data("maxFileCnt"),
                    maxFileSize: trgtObj.data("maxFileSize"),
                    fileExtn: trgtObj.data("fileExtn"),
                    fileFieldNm: trgtObj.data("fileFieldNm")
                })
            });

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                }
            });

            var detailsKey = $("#detailsKey").val();
            if (detailsKey != null || detailsKey != "") {

                // 소재지역 체크 함수
                $("#mainAddr").trigger("click");
                var subAddrCd = $("#subAddr").data("subaddr");
                $("#subAddr").val(subAddrCd).prop("selected", true);

                // 신청사항 체크 함수
                var appctnTypeCd = $(".appctnTypeCd").val();
                var apSize = $(".apTypeCd").size();
                for (var i = 0; i < apSize; i++) {
                    var apVal = $(".apTypeCd").eq(i).val();
                    $("input[name=appctnTypeCd]").each(function () {
                        var chkVal = $(this).val();
                        if (apVal == chkVal) {
                            $(this).prop("checked", true);
                        }
                    })
                }
                var initVstRsltCd = $("#initVstRsltCd").val();
                if (initVstRsltCd == "BF_JDGMT_RSLT02") {
                    $(".rsltCntn").show();
                } else {
                    $(".rsltCntn").hide();
                }

                $(".tempRow").eq(0).find(".close").hide();
                $(".dpTempRow").eq(0).find(".close").hide();
                search();
            }

            // 유효성 검사
            $formObj.validation({
                after: function () {
                    var isValid = true, editorChk = true;

                    $formObj.find(".ckeditorRequired").each(function () {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1) {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.co.cog.cnts"));

                            CKEDITOR.instances[jQuery(this).prop("id")].focus();

                            // 에디터 최상단으로 스크롤 이동
                            jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

                            return false;
                        }
                    });

                    if (!editorChk) {
                        isValid = false;
                    }

                    return isValid;
                }
                ,
                async: {
                    use: true,
                    func: function () {
                        var actionUrl = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update");
                        var actionMsg = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd"));
                        var cmpnNm = $("#cmpnNmText").text();
                        var bsnmNo = $("#bsnmNoText").text();
                        var emailTxt = $("#emailTxt").text();
                        var rprsntNmTxt = $("#rprsntNmTxt").text();
                        var cbsnCd = $("input[name='cbsnCd']:checked").val();

                        var vstDt = $("input[name='vstDt']").val();
                        if (!vstDt) {
                            $("input[name='vstDt']").prop("disabled", true);
                        }

                        var guidePscndDt = $("input[name='guidePscndDt']").val();
                        if (!guidePscndDt) {
                            $("input[name='guidePscndDt']").prop("disabled", true);
                        }

                        var guideBgnDt = $("input[name='guideBgnDt']").val();
                        if (!guideBgnDt) {
                            $("input[name='guideBgnDt']").prop("disabled", true);
                        }

                        var guideKickfDt = $("input[name='guideKickfDt']").val();
                        if (!guideKickfDt) {
                            $("input[name='guideKickfDt']").prop("disabled", true);
                        }

                        var srvStrtDtm = $("input[name='srvStrtDtm']").val();
                        if (!srvStrtDtm) {
                            $("input[name='srvStrtDtm']").prop("disabled", true);
                        }

                        var srvEndDtm = $("input[name='srvEndDtm']").val();
                        if (!srvEndDtm) {
                            $("input[name='srvEndDtm']").prop("disabled", true);
                        }

                        $("#cmpnNm").val(cmpnNm);
                        $("#bsnmNo").val(bsnmNo);
                        $("#email").val(emailTxt);
                        $("#rprsntNm").val(rprsntNmTxt);
                        $("#cbsnCd").val(cbsnCd);


                        if ($formObj.find(".dropzone").size() > 0) {
                            cmmCtrl.fileFrmAjax(function (data) {
                                //콜백함수. 페이지 이동
                                if (data.respCnt > 0) {
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "json");
                        }
                    }
                },
                msg: {
                    empty: {
                        text: " 입력해주세요."
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

