define(["ezCtrl","ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbf/WBFBRegisterCompanyEditCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    var workChk = true;

    var $formObj = ctrl.obj.find("#frmData").eq(0);

    /* 전송 Form */
    var $sendFormData = $('#sendForm');

    var $basicData = $formObj.find("#basicData").eq(0);
    var $dataSpprt = $formObj.find("#frmDataSpprt").eq(0);
    var $dataRsumeTask = $formObj.find("#frmDataRsumeTask").eq(0);

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    let setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail'].list];

        /* 사업자번호 변경 */
        let dataBsnmNo = rtnData['bsnmNo'];
        rtnData['changeBsnmNo'] = dataBsnmNo.slice(0,3) + '-' + dataBsnmNo.slice(3,5) + '-' + dataBsnmNo.slice(5);

        /* Input Hidden Tag Value  */
        $($basicData).find(`input[type=hidden][name=memSeq]`).val(rtnData['memSeq']);
        $($basicData).find(`input[type=hidden][name=id]`).val(rtnData['id']);

        /* id(name) 구조 */
        rtnData['nameAndId'] = `${rtnData['name']}(${rtnData['id']})`;

        /* 사용자_회사 정보 */
        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                let target = $basicData.find(`[id=${el}]`);
                if(target !== undefined) {
                    let tagName = target.prop('tagName');
                    /* SELECT || INPUT || P 태그 공통화 */
                    if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                    if(tagName === 'P') target.html(rtnData[el]);
                }
            }
        });

        fieldShowFn(rtnData['ctgryCd']);

        /* SQ List */
        rtnDataCompList.forEach((compList, idx)=>{
            let targetArea = $formObj.find(`[id=sqInfoArea${idx}]`); /* SQ 구역 선택 */
            let target = $(targetArea);
            target.find('#cbsnSeq').val(compList['cbsnSeq']);
            target.find('#nm').val(compList['nm']);
            target.find('#score').val(compList['score']);
            target.find('#year').val(compList['year']);
            target.find('#crtfnCmpnNm').val(compList['crtfnCmpnNm']);
        });
    }

    /* 회차 검색 Ajax */
    let selEpisdList = () => {
        let optEpisd = $formObj.find('#optEpisd');
        if($formObj.find('#optYear').val() !== ''){
            cmmCtrl.frmAjax(function(respObj) {
                /* return data input */
                let html = '<option value="">회차 전체</option>';
                respObj.optEpisdList.forEach((el) => {
                    html += '<option value="'+el+'">'+el+'</option>';
                })
                optEpisd.empty().append(html);
            }, "/mngwserc/wb/wbfb/getEplisds", $formObj, "post", "json")
        } else {
            let html = '<option value="">회차 전체</option>';
            optEpisd.empty().append(html);
        }
    }

    /* 사업 과제, 유형 Ajax*/
    let selOptnList = () => {
        let optAsigt = $formObj.find('#optAsigt');
        let optBsin = $formObj.find('#optBsin');
        let hiddenTag = $formObj.find(`input[type=hidden][name=episdSeq]`);

        if($formObj.find('#optEpisd').val() !== '') {
            cmmCtrl.frmAjax(function (respObj) {
                /* return data input */

                let asigtList = respObj.optnCategList.asigtList;
                let bsinList = respObj.optnCategList.bsinList;
                hiddenTag.val(respObj.optnCategList.episdSeq);

                let html = '<option value="">선택</option>';
                asigtList.forEach((el, idx) => {
                    html += '<option value="' + el.bsnOptnSeq + '">' + el.optnNm + '</option>';
                    optAsigt.empty().append(html);
                });

                html = '<option value="">선택</option>';
                bsinList.forEach((el, idx) => {
                    html += '<option value="' + el.bsnOptnSeq + '">' + el.optnNm + '</option>';
                    optBsin.empty().append(html);
                });

            }, "/mngwserc/wb/wbfb/getOptns", $formObj, "post", "json")
        } else {
            /* 값 초기화 */
            hiddenTag.val('');
            let html = '<option value="">선택</option>';
            optAsigt.empty().append(html);
            optBsin.empty().append(html);
        }
    }

    /* 신청자 변경 이력 Get */
    let searchTrnsfList = function (page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {

            $formObj.find("#trsfListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "trsfListContainer", "trsfPagingContainer");

        }, "/mngwserc/wb/wbfb/getTrnsf", $formObj, "GET", "html");
    }

    /* 구분값 변경에 따른 SQ 영역 Event Fn l*/
    let fieldShowFn = (value) => {
        let fieldSQ = $formObj.find('#fieldSQ');
        let fieldStart = $formObj.find('#fieldStart');
        if( value != "null" && value != ""){
            if(value == 'COMPANY01001') {
                fieldSQ.hide();
                fieldStart.show();
            }
            if(value == 'COMPANY01002') {
                fieldSQ.show();
                fieldStart.hide();
            }
        }
    }

    /* 선급금 영역 Show-Hide */
    let pmndvPmtViewShowFn = () => {
        let checkedVal = $formObj.find('input[name=pmndvPmtYn]:checked').val();
        let pmndvPmtViwe = $formObj.find('#pmndvPmtView');

        if(checkedVal === 'Y') {
            pmndvPmtViwe.css('display', 'block');
        } else {
            pmndvPmtViwe.css('display', 'none');
        }
    }

    /* 현재 단계가 아닐 경우 disable 처리 js */
    let setDisable = (function() {
        let paint = function(panel) {
            /*$(panel).find('.panel-body')
                .css('pointer-events', 'none')
                .css('cursor', 'default');*/


            $(panel).find('.panel-body').find("fieldset").each(function(){

                if($(this).find("div.dropzone ").length == 0){
                    $(this).css('pointer-events', 'none').css('cursor', 'default');
                }

            });
        }
        let disPaint = function(panel) {
            // $(panel).find('.panel-body').attr('style', '');
            $(panel).find('.panel-body').find("fieldset").each(function(){
                    $(this).attr('style', '');
            });
        }
        /* All panel disable */
        let init = function() {
            $formObj.find('.panel').not('[data-sttsCd*="PRO_TYPE03"]').each(function(idx, el) {
                paint(el);
            });
        }
        /* 현재 진행 단계 관리자상태 값 확인 후 show/hide */
        let optCheck = function(panel) {
            let select = $(panel).find('.mngSttsCd');
            let optLeng = select.find('option').length;
            let lastOpt = select.find('option').eq(optLeng-1).val();
            let selopt = select.find('option:selected').val();

            if(lastOpt == selopt) {
                paint(panel);
            } else {
                disPaint(panel);
            }
        }
        return{
            init : init,
            check : optCheck,
            show : disPaint,
            hide : paint
        }
    })();

    /* 회차 검색 ajax value setting */
    let setValue = function(area, data) {
        let input = $(area).find("input,select,p");
        input.each(function(idx, el) {
            let target = $(el);
            let [tagName, dataKey] = [target.prop('tagName'), target.data('name')];
            let dataValue = data[dataKey];

            if(dataKey != undefined && dataValue != undefined) {
                if(tagName === 'SELECT' || tagName === 'INPUT') target.val((dataValue || ''));
                if(tagName === 'P') target.html((dataValue || ''));
            }
        });
    }

    /* 회차 검색 Ajax */
    let initPageSet = () => {

        setDisable.init();
        let checkedVal = $formObj.find('input[name=pmndvPmtYn]:checked').val();

        let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();

        /*현재 진행상태 관리자상태 값 check*/
        if(checkedVal == 'Y'){
            $dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03001]`).find('a[role="button"]').click();
        } else {
            $dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03002]`).find('a[role="button"]').click();
        }

        $dataRsumeTask.find(`.panel[data-sttsCd=${nowRsumeTaskCd}]`).find('a[role="button"]').click();
        /*현재 상태 panel show*/
        setDisable.check($dataRsumeTask.find(`.panel[data-sttsCd=${nowRsumeTaskCd}]`));

        $formObj.find(".dropzone").each(function(){
            var trgtObj = $(this);
            cmmCtrl.setDropzone(trgtObj, {
                maxFileCnt  : trgtObj.data("maxFileCnt"),
                maxFileSize : trgtObj.data("maxFileSize"),
                fileExtn    : trgtObj.data("fileExtn"),
                fileFieldNm : trgtObj.data("fileFieldNm")
            })
        });

        searchTrnsfList(1);

    }


    /* 총 가격 계산 */
    let sumPriceForTtlPmt = function(event) {
        let panelBody = $(event).closest('.panel-body');

        let totalPrice = 0;

        panelBody.find('.priceVal').each(function(idx, el) {
            let element = $(el);
            let price = Number(element.val().replaceAll(",","")) || Number(0);
            totalPrice += price;
        });

        if(panelBody.find('.ttlPmt') != undefined){
            panelBody.find('.ttlPmt').val(totalPrice.toLocaleString('ko-KR'));
        }
    }

    let removeComma = function(commaObj) {
        if(commaObj != null && commaObj.length != 0 && commaObj != undefined) {
            $(commaObj).find('.comma').each(function(idx, el) {
                let price = $(el).val().replaceAll(",", "");
                $(el).val(price);
            });
        }
    }

    let fnpstnNmShow = function(pstnCd) {
        if(pstnCd == 'MEM_CD01007'){
            $("#pstnNm").css("display", "block");
        }else{
            $("#pstnNm").val("");
            $("#pstnNm").css("display", "none");
        }
    }

    let rtrnRsnCntnShow = function(mngSttsCd) {
        if(mngSttsCd =='PRO_TYPE02001_02_002'){
            $(".rtrnRsnCntn1").show().attr('disabled',false);
        }else if(mngSttsCd =='PRO_TYPE02001_02_004'){
            $(".rtrnRsnCntn1").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn1").hide().attr('disabled',true);
        }

        if(mngSttsCd =='PRO_TYPE02002_02_003'){
            $(".rtrnRsnCntn2").show().attr('disabled',false);
        }else if(mngSttsCd =='PRO_TYPE02002_02_004'){
            $(".rtrnRsnCntn2").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn2").hide().attr('disabled',true);
        }

        if(mngSttsCd =='PRO_TYPE02003_02_002'){
            $(".rtrnRsnCntn3").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn3").hide().attr('disabled',true);
        }

        if(mngSttsCd =='PRO_TYPE02004_02_002'){
            $(".rtrnRsnCntn4").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn4").hide().attr('disabled',true);
        }

        if(mngSttsCd =='PRO_TYPE02005_02_002'){
            $(".rtrnRsnCntn5").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn5").hide().attr('disabled',true);
        }

        if(mngSttsCd =='PRO_TYPE02007_02_002'){
            $(".rtrnRsnCntn7").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn7").hide().attr('disabled',true);
        }

        if(mngSttsCd =='PRO_TYPE02008_02_003'){
            $(".rtrnRsnCntn8").show().attr('disabled',false);
        }else if(mngSttsCd =='PRO_TYPE02008_02_004'){
            $(".rtrnRsnCntn8").show().attr('disabled',false);
        }else{
            $(".rtrnRsnCntn8").hide().attr('disabled',true);
        }
    }


    // set model
    ctrl.model = {
        id : {
            btnUpdAdmMemo : {
                event : {
                    click : function() {
                        let appctnSeq = $basicData.find('input[type=hidden][name=appctnSeq]').val();
                        if(appctnSeq != '') {
                            let wBFBRegisterDTO = {}
                            wBFBRegisterDTO.admMemo = $('#admMemo').val();
                            wBFBRegisterDTO.appctnSeq = appctnSeq

                            cmmCtrl.jsonAjax(function(respObj) {
                                var rtnData = JSON.parse(respObj);
                                if(rtnData.respCnt > 0) {
                                    alert(msgCtrl.getMsg("success.upd"));
                                    location.replace("./list");
                                }
                            }, "/mngwserc/wb/wbfb/updAdmMemo", wBFBRegisterDTO, "text")
                        }
                    }
                }
            },
            offerBsnmNo : {
                event : {
                    input : function() {
                        workChk = false;
                    }
                }
            },
            bsnmNoAuth :{
                event : {
                    click : function() {
                        if($('#offerBsnmNo').val() != '') {
                            jQuery.ajax({
                                url : "/mngwserc/nice/comp-chk",
                                type : "post",
                                data :
                                    {
                                        "compNum" : $("#offerBsnmNo").val()
                                    },
                                success : function(data)
                                {
                                    if(data.rsp_cd=='P000') {
                                        if(data.result_cd == '01') {
                                            if(data.comp_status == '1') {
                                                $("#offerCmpnNm").val(data.comp_name);
                                                workChk = true;
                                            } else {
                                                alert(msgCtrl.getMsg("fail.mp.mpa.al_015"));
                                                $("#offerCmpnNm").val("");
                                                $("#offerBsnmNo").val("");
                                                workChk = false;
                                            }
                                        } else {
                                            alert(msgCtrl.getMsg("fail.mp.mpa.al_015"));
                                            $("#offerCmpnNm").val("");
                                            $("#offerBsnmNo").val("");
                                            workChk = false;
                                        }
                                    } else {
                                        alert(msgCtrl.getMsg("fail.mp.mpa.al_015"));
                                        $("#offerCmpnNm").val("");
                                        $("#offerBsnmNo").val("");
                                        workChk = false;
                                    }
                                },
                                error : function(xhr, ajaxSettings, thrownError)
                                {
                                    cmmCtrl.errorAjax(xhr);
                                    jQuery.jstree.rollback(data.rlbk);
                                }
                            });

                            /*
                            var wBFBRegisterSearchDTO = {}
                            wBFBRegisterSearchDTO.offerBsnmNo = $('#offerBsnmNo').val();
                            cmmCtrl.jsonAjax(function(respObj) {
                                var rtnData = JSON.parse(respObj);

                                if(rtnData.cmpnCount == 0){
                                    $dataRsumeTask.find('#offerCmpnNm').val('');
                                    alert('공급기업 사업자등록번호 확인해주세요.');
                                } else {
                                    $dataRsumeTask.find('#offerCmpnNm').val(rtnData.cmpnNm);
                                }
                            }, "/mngwserc/wb/wbfb/getBsnmNoCheck", wBFBRegisterSearchDTO, "text")
                            */

                        }
                    }
                }
            },
            ctgryCd : {
                event : {
                    change : function() {
                        fieldShowFn($(this).val());
                    }
                }
            },
            optYear : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($formObj);
                        selEpisdList();
                    },
                }
            },
            optEpisd : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($formObj);
                        selOptnList();
                    },
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width, height,"zipcode","bscAddr","dtlAddr");
                    }
                }
            },
            //PDF
            appctnPdfDownload : {
                event : {
                    click : function(){

                        var cmpnNm = $("#cmpnNm").html();
                        var today = new Date();

                        var date = today.getFullYear() +""+ today.getMonth()+1 +""+ today.getDate();
                        var fileName = "스마트공장구축_사업현황_"+ cmpnNm +"_"+ date + ".pdf";
                        cmmCtrl.getAppctnPdfDownload(fileName);
                    }
                }
            },
            pstnCd : {
                event : {
                    change : function() {
                        var pstnCd = $(this).val();
                        fnpstnNmShow(pstnCd);
                    }
                }
            },
        },
        classname : {
            priceVal : {
                event : {
                    change : function() {
                        sumPriceForTtlPmt(this);
                    },
                    keyup : function() {
                        sumPriceForTtlPmt(this);
                    }
                },
            },
            pmndvPmtYn : {
                event : {
                    click : function() {
                        pmndvPmtViewShowFn();
                    }
                },
            },
            mngSttsCd : {
                event : {
                    change : function() {
                        rtrnRsnCntnShow($(this).val());
                    }
                }
            },
            // 회원검색 모달
            btnPartUserModal: {
                event: {
                    click: function () {
                        /* 공통 모달 - 상생 사용 처리 */
                        $('.mpbMemberPartsSocietySrchLayer #srchPage').val('WB');
                        let appctnSeqVal = $basicData.find('input[type=hidden][name=appctnSeq]').val() ;
                        $(".mpbMemberPartsSocietySrchLayer #srchAppctnSeq").val(appctnSeqVal);

                        var url = window.location.href;
                        var pattern = /\/wb\/(\w+)/;
                        var match = url.match(pattern);
                        if (match) {
                            var wbSubUrl = match[1];
                        }

                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            $formObj.find('#memSeq').val(data.memSeq);
                            cmmCtrl.frmAjax(function(respObj) {
                                /* return data input */
                                setInputValue(respObj);
                                fnpstnNmShow($('#pstnCd').val());
                            }, "/mngwserc/wb/" + wbSubUrl + "/selModalDetail", $formObj, "post", "json");
                        });
                    }
                }
            },
            // 위원검색 모달
            btnCmtSearch: {
                event: {
                    click: function () {
                        $('.mpcLecturerSrchLayer .modal-title #title').html("▣ 강사 및 위탁위원 검색");
                        let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();
                        let rsumeTask = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`);
                        cmmCtrl.getLecturerLayerPop(function (data) {
                            rsumeTask.find('.chkCmssrSeq').val(data.seq);
                            rsumeTask.find('.chkCmssrNm').val(data.name);
                        });

                    }
                }
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") !== "null" ){
                            $('#trnsfField').find("input[name=pageIndex]").val($(this).attr("value"));
                            searchTrnsfList(1);
                        }
                    }
                }
            },
            trsfListRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $('#trnsfField').find("input[name=listRowSize]").val($(this).val());
                        searchTrnsfList(1);
                    }
                }
            },
            telNumber : {
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
        },
        immediately : function() {
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);

            /* 페이지 기본 setting */
            initPageSet();
            /* 선급금 해당 여부 show/hide */
            pmndvPmtViewShowFn();
            /* 구분 값에 따른 show/hide */
            fieldShowFn($('#ctgryCd').val());
            /* 직급 값에 따른 show/hide */
            fnpstnNmShow($('#pstnCd').val());
            /* 반려사유 show/hide */
            // rtrnRsnCntnShow($('.mngCd').val());
            rtrnRsnCntnShow($('#frmDataRsumeTask').data("mngStts"));


            $formObj.validation({
                after : function() {
                    var isValid = true;

                    if( $("#telNo").val().length !=0 && $("#telNo").val().length < 11 ) {
                        alert(msgCtrl.getMsg("fail.mp.mpb.al_011"));
                        isValid = false;
                        return false;
                    }
                    if( $("#compTel").val().length !=0 && $("#compTel").val().length < 11 ) {
                        alert(msgCtrl.getMsg("fail.mp.mpb.al_014"));
                        isValid = false;
                        return false;
                    }

                    let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();
                    let mngCd = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`).find(".mngCd").val();  //현재 태스크 관리자 상태값
                    let dropzoneTask = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`).find(".dropzone");
                    //완료보고 태스크의 관리자 상태가 접수 전이면 첨부파일 유효성 검사 패스
                    if(nowRsumeTaskCd == "PRO_TYPE02008" && mngCd == "PRO_TYPE02008_02_001") {

                    }
                    else if(dropzoneTask.length > 0) {
                        dropzoneTask.each(function(idx, el) {
                            if($(el).find('.dz-preview').length < 1) {
                                alert(msgCtrl.getMsg("fail.notFileRequired"));
                                isValid = false;
                                return false;
                            }
                        });
                    }

                    var sbrdnBsnmNo = $("#sbrdnBsnmNo").val();
                    if(sbrdnBsnmNo == null || sbrdnBsnmNo == ''){
                        jQuery.ajax({
                            url : "./getBsnmNoCnt",
                            type : "POST",
                            timeout: 30000,
                            data : $basicData.serializeArray(),
                            dataType : "json",
                            async: false,
                            cache : false,
                            success : function(data, status, xhr){
                                if(data.respCnt > 0 ){
                                    alert("이미 해당 회차에 신청한 부품사입니다.");
                                    isValid = false;
                                    return;
                                }
                            }
                        });
                    }else{
                        jQuery.ajax({
                            url : "./getSbrdnBsnmNoCnt",
                            type : "POST",
                            timeout: 30000,
                            data : $basicData.serializeArray(),
                            dataType : "json",
                            async: false,
                            cache : false,
                            success : function(data, status, xhr){
                                if(data.respCnt > 0 ){
                                    alert("이미 해당 회차에 신청한 부품사입니다.\n (종된사업장 중복)");
                                    isValid = false;
                                    return;
                                }
                            }
                        });
                    }

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){

                        let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();
                        let rsumeTaskData = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`);

                        removeComma($dataSpprt);
                        removeComma(rsumeTaskData);

                        /* 회차 정보 ~ 선급금 해당 여부*/
                        $sendFormData.append($basicData);

                        /* 선급금 지급 */
                        $sendFormData.append($dataSpprt);

                        /* 부품사 관리 등록 */
                        $sendFormData.append(rsumeTaskData);

                        if($sendFormData.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.upd"));
                                }
                                location.replace("./list");
                            }, "./update", $sendFormData, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.upd"));
                                }
                                location.replace("./list");
                            }, "./update", $sendFormData, "post", "json");
                        }
                    }
                },
                msg : {
                    empty : {
                        text : " 입력해주세요."
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});


