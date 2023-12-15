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

    // form Object
    var $modalObj = $(".part-modal");
    //modalForm
    var $modalFormObj = $modalObj.find("form").eq(0);

    /* 팝업 옵션 */
    let optVal = {
        '1' : '아이디',
        '2' : '이름',
        '3' : '휴대폰번호',
        '4' : '이메일',
        '5' : '최종수정자',
        '6' : '부품사명',
        '7' : '사업자등록번호',
    }

    // 목록 조회
    let search = function (page){
        if(page !== undefined){
            $modalFormObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            $modalFormObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $modalFormObj, "listContainer", "pagingContainer");
        }, "/mngwserc/wb/selModalData", $modalFormObj, "POST", "html");

    }

    let selPartUserData = function (){

        /* 위원 Modal 일 경우*/
        if($modalFormObj.find('#memCd').val() == 'CS') {

            cmmCtrl.frmAjax(function (respObj) {
                let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();
                let rsumeTask = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`);
                if(respObj.rtnData == 0){
                    cmmCtrl.frmAjax(function (respObj) {
                        $modalObj.modal("hide");
                        /* return data input */

                        rsumeTask.find('input[data-name=chkCmssrSeq]').val(respObj.rtnData.memSeq);
                        rsumeTask.find('input[data-name=chkCmssrNm]').val(respObj.rtnData.name);
                    }, "/mngwserc/wb/selModalDetail", $modalFormObj, "post", "json");
                } else {
                    alert("이관 이력이 있는 회원은 선택이 불가합니다.");
                    /* return data input */
                    rsumeTask.find('input[data-name=chkCmssrSeq]').val('');
                    rsumeTask.find('input[data-name=chkCmssrNm]').val('');
                    return false;
                }
            }, "/mngwserc/wb/partUserChk", $modalFormObj, "post", "json");
        }
        if($modalFormObj.find('#memCd').val() == 'CP'){
            /* 부품사 일 경우 */
            cmmCtrl.frmAjax(function(respObj) {
                $modalObj.modal("hide");
                /* return data input */
                setInputValue(respObj);
            }, "/mngwserc/wb/selModalDetail", $modalFormObj, "post", "json");
        }

    }

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
            shadow.show($dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03001]`));
        } else {
            pmndvPmtViwe.css('display', 'none');
            shadow.hide($dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03001]`));
            shadow.show($dataSpprt.find(`.panel[data-sttsCd=PRO_TYPE03002]`));
        }
    }


    let fnRefresh = function() {
        //FORM 데이터 전체 삭제
        let pageIndex 	= $modalObj.find("#pageIndex").val();
        let listRowSize = $modalObj.find("#listRowSize").val();
        let pageRowSize = $modalObj.find("#pageRowSize").val();
        let csrfKey 	= $modalObj.find("#csrfKey").val();
        let srchLayer 	= $modalObj.find("input[name=srchLayer]").val();
        let memCd 	= $modalObj.find("input[name=memCd]").val();
        $modalObj.clearForm();
        //FORM 전송 필수 데이터 삽입
        $modalObj.find("#pageIndex").val( pageIndex );
        $modalObj.find("#listRowSize").val( listRowSize );
        $modalObj.find(".listRowSizeContainer").val( listRowSize );
        $modalObj.find("#pageRowSize").val( pageRowSize );
        $modalObj.find("#csrfKey").val( csrfKey );
        $modalObj.find("input[name=srchLayer]").val( srchLayer );
        $modalObj.find("input[name=memCd]").val( memCd );
        //캘린더 초기화
        cmmCtrl.setPeriod($modalObj.find('.datetimepicker_strtDt, .datetimepicker_endDt'), "", "", false);

        //검색 로직 실행
        $modalObj.find("#btnSearch").click();
    }

    /* 현재 단계가 아닐 경우 disable 처리 js */
    let shadow = (function() {
        let paint = function(panel) {
            $(panel).find('.panel-body')/*
                .css('pointer-events', 'none')
                .css('cursor', 'default')*/
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
            let select = $(panel).find('select[name$=mngSttsCd]')
            let optLeng = $(select).find('option').length;
            let seloptLeng = $(select).find('option:selected').length;
            if(optLeng == seloptLeng) {
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
    let getPageInfo = () => {
        cmmCtrl.frmAjax(function(respObj) {

            let dataSpprt = respObj.rtnData.spprtDtlList;
            let dataRsumeTask = respObj.rtnData.rsumeTaskDtlList;

            /* 현재 진행 코드 Sett */
            let nowSpprtCd = dataSpprt[(dataSpprt.length-1)]['giveType'];
            $sendFormData.find('input[type=hidden][name=nowSpprtCd]').val(nowSpprtCd);
            let nowRsumeTaskCd = dataRsumeTask[(dataRsumeTask.length-1)]['rsumeSttsCd']
            $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val(nowRsumeTaskCd);

            shadow.init();

            dataSpprt.forEach(function(el, idx) {
                let panel = $dataSpprt.find(`.panel[data-sttsCd=${el['giveType']}]`);
                setValue(panel, el);
                /* 파일정보 */
                let file = panel.find('.dropzone.attachFile');
                if(file != undefined && file != null) {
                    $(file).each(function(idx, ele) {
                        let target = $(ele).find('input[type=hidden][data-hidden=fileInfo]');
                        $(el).each(function(idx, elem) {
                            if(elem[target.attr('data-name')] != undefined && elem[target.attr('data-name')] != null){
                                target.val(elem[target.attr('data-name')]);
                            }
                        });
                    });
                }
            });
            /* 현재 진행상태 관리자상태 값 check */
            shadow.check($dataSpprt.find(`.panel[data-sttsCd=${nowSpprtCd}]`));
            /* 현재 상태 pabel show */
            $dataSpprt.find(`.panel[data-sttsCd=${nowSpprtCd}]`).find('a[role="button"]').click();

            dataRsumeTask.forEach(function(el, idx) {
                let panel = $dataRsumeTask.find(`.panel[data-sttsCd=${el['rsumeSttsCd']}]`);
                setValue(panel, el);

                /* 파일정보 */
                let file = panel.find('.dropzone.attachFile');
                if(file != undefined && file != null) {
                    $(file).each(function(idx, ele) {
                        let target = $(ele).find('input[type=hidden][data-hidden=fileInfo]');
                        $(el.appctnFileInfo).each(function(idx, elem) {
                            if(elem['fileCd'] == target.attr('data-fileType')) {
                                target.val(elem['fileSeq']);
                            }
                        });
                    });
                }
            });

            let lastRsumeTask = dataRsumeTask[dataRsumeTask.length-1];
            let title = `${lastRsumeTask.rsumeSttsCdNm}(${lastRsumeTask.mngSttsCdNm})` || '';
            $dataRsumeTask.find("#rsuemTitle").html(title);

            /* 현재 진행상태 관리자상태 값 check */
            shadow.check($dataRsumeTask.find(`.panel[data-sttsCd=${nowRsumeTaskCd}]`));
            /* 현재 상태 pabel show */
            $dataRsumeTask.find(`.panel[data-sttsCd=${nowRsumeTaskCd}]`).find('a[role="button"]').click();

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

        }, "/mngwserc/wb/wbfb/getEditInfo", $formObj, "post", "json")
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
            // 회원검색 모달
            btnPartUserModal : {
                event : {
                    click: function () {
                        $modalObj.find("input[name=memCd]").val('CP');
                        $modalObj.find("#cdList").show();

                        $modalObj.find("select[data-name=f]").empty();
                        let selOpt = ['1','2','6','7','3','4','5'];
                        let opt = '<option value="">전체</option>';
                        selOpt.forEach(function(el, idx) {
                            opt += `<option value="${el}">${optVal[el]}</option>`
                        });
                        $modalObj.find("select[data-name=f]").append(opt);

                        $modalObj.find("thead tr").empty();
                        let tableHead = ['번호','아이디','이름','부품사명','구분'
                            ,'규모','사업자등록번호','휴대폰번호','이메일','가입일','최종 수정자','수정 일시'];
                        let html = '<th class="text-center"></th>';
                        tableHead.forEach(function(el, idx) {
                           html += `<th class="text-center">${el}</th>`;
                        });
                        $modalObj.find("thead tr").append(html);
                        fnRefresh();
                        search(1);
                        $modalObj.modal("show");
                    }
                }
            },
            btnSearch : {
                event: {
                    click: function () {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($modalFormObj);
                        search(1);
                    }
                }
            },
            btnModalRefresh : {
                event: {
                    click: function () {
                        fnRefresh();
                    }
                }
            },
            btnModalSelect : {
                event: {
                    click: function() {
                        let trArea = $modalFormObj.find("#listContainer input[type=checkbox]:checked");
                        if(trArea.length !== 0 || trArea != undefined){
                            selPartUser = trArea.val();
                            $modalFormObj.find("#selPartUser").val(selPartUser);
                            $modalFormObj.find('#modalAppctnSeq').val($basicData.find('input[name=appctnSeq]').val());
                            selPartUserData();
                        }
                    }
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width, height,"zipCode","bscAddr","dtlAddr");
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
            // 위원검색 모달
            btnCommUserModal : {
                event : {

                    click: function () {
                        $modalObj.find("input[name=memCd]").val('CS');
                        $modalObj.find("#cdList").hide();

                        $modalObj.find("select[data-name=f]").empty();
                        let selOpt = ['1','2','3','4','5'];
                        let opt = '<option value="">전체</option>';
                        selOpt.forEach(function(el, idx) {
                            opt += `<option value="${el}">${optVal[el]}</option>`
                        });
                        $modalObj.find("select[data-name=f]").append(opt);

                        $modalObj.find("thead tr").empty();
                        let tableHead = ['번호','이름','부품사명','부서'
                            ,'직급','휴대폰번호','이메일','최초등록자','최초 등록일시','최종 수정자','최종 수정일시'];
                        let html = '<th class="text-center"></th>';
                        tableHead.forEach(function(el, idx) {
                            html += `<th class="text-center">${el}</th>`;
                        });
                        $modalObj.find("thead tr").append(html);
                        fnRefresh();
                        search(1);
                        $modalObj.modal("show");
                    }
                }
            },
            checkboxSingle : {
                event : {
                  click : function() {
                      $modalFormObj.find('tbody').find('input[type=checkbox]').prop('checked',false);
                      $(this).prop('checked',true);
                  }
                },
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        if($(this).closest('#trsfPagingContainer').length > 0) {
                            //페이징 이동
                            if( $(this).attr("value") !== "null" ){
                                $('#trnsfField').find("input[name=pageIndex]").val($(this).attr("value"));
                                searchTrnsfList(1);
                            }
                        } else {
                            //페이징 이동
                            if( $(this).attr("value") !== "null" ){
                                $modalFormObj.find("input[name=pageIndex]").val($(this).attr("value"));
                                search();
                            }
                        }

                    }
                }
            },
            //페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $modalFormObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
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
            cmmCtrl.setFormData($modalFormObj);

            getPageInfo();

            pmndvPmtViewShowFn();
            fieldShowFn($('#ctgryCd').val());

            $formObj.validation({
                after : function() {
                    var isValid = true;

                    let nowSpprtCd = $sendFormData.find('input[type=hidden][name=nowSpprtCd]').val();
                    let nowRsumeTaskCd = $sendFormData.find('input[type=hidden][name=nowRsumeTaskCd]').val();

                    let dropzoneSpprt = $dataSpprt.find(`[data-sttsCd=${nowSpprtCd}]`).find(".dropzone");
                    dropzoneSpprt.each(function(idx, el) {
                        if($(el).find('.dz-preview').length < 1) {
                            isValid = false;
                        }
                    })

                    let dropzoneTask = $dataRsumeTask.find(`[data-sttsCd=${nowRsumeTaskCd}]`).find(".dropzone");
                    dropzoneTask.each(function(idx, el) {
                        if($(el).find('.dz-preview').length < 1) {
                            isValid = false;
                        }
                    })

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
                                    alert(msgCtrl.getMsg("success.ins"));
                                    location.replace("./list");
                                }
                            }, "./update", $sendFormData, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.ins"));
                                    location.replace("./list");
                                }
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


