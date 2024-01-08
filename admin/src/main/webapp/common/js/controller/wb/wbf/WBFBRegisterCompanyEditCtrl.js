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
    let selPartUser; /* 선택 사용자 ID*/

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
        rtnData['bsnmNo'] = dataBsnmNo.slice(0,3) + '-' + dataBsnmNo.slice(3,5) + '-' + dataBsnmNo.slice(5);

        /* Input Hidden Tag Value  */
        $($basicData).find(`input[type=hidden][name=memSeq]`).val(rtnData['memSeq']);
        $($basicData).find(`input[type=hidden][name=id]`).val(rtnData['id']);
        $($basicData).find(`input[type=hidden][name=bsnmNo]`).val(dataBsnmNo);

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
                fieldSQ.find("input,select").prop('disabled', true);
                fieldStart.show();
                fieldStart.find("input,select").prop('disabled', false);
            }
            if(value == 'COMPANY01002') {
                fieldSQ.show();
                fieldSQ.find("input,select").prop('disabled', false);
                fieldStart.hide();
                fieldStart.find("input,select").prop('disabled', true);
            }
        } else {
            fieldSQ.hide();
            fieldSQ.find("input,select").prop('disabled', true);
            fieldStart.hide();
            fieldStart.find("input,select").prop('disabled', true);
        }
    }

    /* 선급금 영역 Show-Hide */
    let pmndvPmtViewShowFn = () => {
        let checkedVal = $formObj.find('input[name=pmndvPmtYn]:checked').val();
        let pmndvPmtViwe = $formObj.find('#pmndvPmtView');

        if(checkedVal === 'Y') {
            pmndvPmtViwe.css('display', 'block');
            setDisable.show($dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03001]`));
        } else {
            pmndvPmtViwe.css('display', 'none');
            setDisable.hide($dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03001]`));
            setDisable.show($dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03002]`));
        }
    }

    /* 현재 단계가 아닐 경우 disable 처리 js */
    let setDisable = (function() {
        let paint = function(panel) {
            $(panel).find('.panel-body')
                .css('pointer-events', 'none')
                .css('cursor', 'default')
        }
        let disPaint = function(panel) {
            $(panel).find('.panel-body').attr('style', '');
        }
        /* All panel disable */
        let init = function() {
            $formObj.find('.panel').not('[data-sttsCd="PRO_TYPE03001"]').each(function(idx, el) {
                paint(el);
            });
        }
        /* 현재 진행 단계 관리자상태 값 확인 후 show/hide */
        let optCheck = function(panel) {
            let select = $(panel).find('select[data-name=mngSttsCd]');
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

        let nowSpprtCd = $sendFormData.find('input[type=hidden][name=nowSpprtCd]').val();
        let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();

        /*현재 진행상태 관리자상태 값 check*/
        $dataSpprt.find(`.panel[data-sttsCd=${nowSpprtCd}]`).find('a[role="button"]').click();
        $dataRsumeTask.find(`.panel[data-sttsCd=${nowRsumeTaskCd}]`).find('a[role="button"]').click();
        /*현재 상태 panel show*/
        setDisable.check($dataSpprt.find(`.panel[data-sttsCd=${nowSpprtCd}]`));
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

        /* datetimepicker init */
        let date = new Date();
        let dateVal = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();
        $('input[class*=datetimepicker]').each(function(idx, el) {
            if($(el).val() == '') {
                $(el).val(dateVal);
            }
        })
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

        if(panelBody.find('input[data-name=ttlPmt]') != undefined){
            panelBody.find(`input[data-name=ttlPmt]`).val(totalPrice.toLocaleString('ko-KR'));
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

    // set model
    ctrl.model = {
        id : {
            bsnmNoAuth :{
                event : {
                    click : function() {
                        cmmCtrl.frmAjax(function(respObj) {
                            if(respObj.rtnData.cmpnCount == 0){
                                $dataRsumeTask.find('input[data-name="offerCmpnNm"]').val('');
                                alert('공급기업 사업자등록번호 확인해주세요.');
                            } else {
                                $dataRsumeTask.find('input[data-name="offerCmpnNm"]').val(respObj.rtnData.cmpnNm);
                            }
                        }, "/mngwserc/wb/wbfb/getBsnmNoCheck", $formObj, "post", "json")
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
                        cmmCtrl.searchPostCode(width, height,"zipCode","bscAddr","dtlAddr");
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
            }
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
            // 회원검색 모달
            btnPartUserModal: {
                event: {
                    click: function () {
                        $("#srchDivide").val("Y");
                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            cmmCtrl.frmAjax(function (respObj) {
                                $formObj.find('#memSeq').val(data.memSeq);
                                if(respObj.rtnData == 0){
                                    cmmCtrl.frmAjax(function(respObj) {
                                        /* return data input */
                                        setInputValue(respObj);
                                    }, "/mngwserc/wb/selModalDetail", $formObj, "post", "json");
                                } else {
                                    alert("이관 이력이 있는 회원은 선택이 불가합니다.");
                                    return false;
                                }
                            }, "/mngwserc/wb/partUserChk", $formObj, "post", "json");
                        });
                    }
                }
            },
            // 위원검색 모달
            btnCmtSearch: {
                event: {
                    click: function () {
                        let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();
                        let rsumeTask = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`);
                        cmmCtrl.getCmtSrchPop(function (data) {
                            rsumeTask.find('input[data-name=chkCmssrSeq]').val(data.seq);
                            rsumeTask.find('input[data-name=chkCmssrNm]').val(data.name);
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

            $formObj.validation({
                after : function() {
                    var isValid = true;

                    let nowSpprtCd = $sendFormData.find('input[type=hidden][name=nowSpprtCd]').val();
                    let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();

                    let dropzoneSpprt = $dataSpprt.find(`[data-sttsCd=${nowSpprtCd}]`).find(".dropzone");
                    if(dropzoneSpprt.length > 0) {
                        dropzoneSpprt.each(function(idx, el) {
                            if($(el).find('.dz-preview').length < 1) {
                                isValid = false;
                            }
                        });
                    }

                    let dropzoneTask = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`).find(".dropzone");
                    if(dropzoneTask.length > 0) {
                        dropzoneTask.each(function(idx, el) {
                            if($(el).find('.dz-preview').length < 1) {
                                isValid = false;
                            }
                        });
                    }

                    if(!isValid) {
                        alert(msgCtrl.getMsg("fail.notFileRequired"));
                    }

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){

                        let nowSpprtCd = $sendFormData.find('input[type=hidden][name=nowSpprtCd]').val();
                        let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();

                        let defaultSpprtData = $dataSpprt.find(`[data-sttsCd='PRO_TYPE03001']`);
                        let spprtData = $dataSpprt.find(`[data-sttsCd=${nowSpprtCd}]`);
                        let rsumeTaskData = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`);

                        removeComma(defaultSpprtData);
                        removeComma(spprtData);
                        removeComma(rsumeTaskData);

                        /* 회차 정보 ~ 선급금 해당 여부*/
                        $sendFormData.append($($basicData));

                        /* 선급금 지급 */
                        $sendFormData.append(defaultSpprtData);
                        /* 현재 지급 상태 */
                        if(nowSpprtCd != 'PRO_TYPE03001'){
                            $sendFormData.append(spprtData);
                        }

                        /* 부품사 관리 등록 */
                        $sendFormData.append(rsumeTaskData);

                        /* 관리자 메모 */
                        $sendFormData.append($('<input/>', {type: 'hidden', name: 'admMemo', value: $('textarea[name=admMemo]').val() }));

                        if($formObj.find(".dropzone").size() > 0)
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


