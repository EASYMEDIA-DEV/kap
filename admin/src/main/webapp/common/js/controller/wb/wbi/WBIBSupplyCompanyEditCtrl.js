define(["ezCtrl","ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbi/WBIBSupplyCompanyEditCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    let selPartUser; /* 선택 사용자 ID*/

    var $formObj = ctrl.obj.find("form").eq(0);
    // form Object
    var $modalObj = $(".part-modal");
    //modalForm
    var $modalFormObj = $modalObj.find("form").eq(0);

    // 구분 코드로 영역 노출
    var ctgryCd = $("#ctgryCd").val();

    let fieldSQ = $formObj.find('#fieldSQ');
    let fieldStart = $formObj.find('#fieldStart');
    if( ctgryCd != "null" && ctgryCd != ""){
        if(ctgryCd == 'COMPANY01001') {
            fieldSQ.hide();
            fieldSQ.find("input,select").prop('disabled', true);
            fieldStart.show();
            fieldStart.find("input,select").prop('disabled', false);
        }
        if(ctgryCd == 'COMPANY01002') {
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
    // 부적합 사유
    var mngSttsCd = $("#mngSttsCd option:selected").val();
    if(mngSttsCd == 'PRO_TYPE06001_02_001' || mngSttsCd == 'PRO_TYPE06001_02_004'){
        document.getElementById("rtrnRsnCntn").style.display = "none";
    }

    // 신청 상태값
    var mngSttsCd = $("#mngSttsCd option:selected").val();
    if(mngSttsCd == 'PRO_TYPE06001_02_002' || mngSttsCd == 'PRO_TYPE06001_02_003'){
        document.getElementById("rtrnRsnCntn").style.display = "block";
    }else{
        document.getElementById("rtrnRsnCntn").style.display = "none";
    }

    if(mngSttsCd == 'PRO_TYPE06001_02_001'){
        document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_001";
    }else if(mngSttsCd == 'PRO_TYPE06001_02_002'){
        document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_002";
    }else if(mngSttsCd == 'PRO_TYPE06001_02_003'){
        document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_005";
    }else if(mngSttsCd == 'PRO_TYPE06001_02_004'){
        document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_006";
    }

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail'].list];

        /* 사업자번호 변경 */
        let dataBsnmNo = rtnData['bsnmNo'];
        rtnData['bsnmNo'] = dataBsnmNo.slice(0,3) + '-' + dataBsnmNo.slice(3,5) + '-' + dataBsnmNo.slice(5);

        rtnData['nameAndId'] = `${rtnData['name']}(${rtnData['id']})`;
        /* Input Hidden Tag Value  */
        $formObj.find(`input[type=hidden][name=id]`).val(rtnData['id']);
        $formObj.find(`input[type=hidden][name=bsnmNo]`).val(dataBsnmNo);

        /* 사용자_회사 정보 */
        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                let target = $formObj.find(`[id=${el}]`);
                if(target !== undefined) {
                    let tagName = target.prop('tagName');
                    /* SELECT || INPUT || P 태그 공통화 */
                    if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                    if(tagName === 'P') target.html(rtnData[el]);
                }
            }
        });

        fieldShowFn(rtnData['ctgryCd']);
        $("#befeCtgryCd").val(rtnData['ctgryCd']);

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
            }, "/mngwserc/wb/wbib/getEplisds", $formObj, "post", "json")
        } else {
            let html = '<option value="">회차 전체</option>';
            optEpisd.empty().append(html);
        }
    }

    /*/!* 해당 회차의 episdSeq 값 가져오기 *!/
    let selOptnList = () => {
        let hiddenTag = $formObj.find(`input[type=hidden][name=episdSeq]`);

        if($formObj.find('#optEpisd').val() !== '') {
            cmmCtrl.frmAjax(function (respObj) {
                /!* return data input *!/
                hiddenTag.val(respObj.optnCategList.episdSeq);
            }, "/mngwserc/wb/wbib/getEpisdSeq", $formObj, "post", "json")
        } else {
            /!* 값 초기화 *!/
            hiddenTag.val('');
        }
    }*/

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

    var trnsfSearch = function (page) {
        //data로 치환해주어야한다.
        cmmCtrl.setFormData($formObj);

        if (page != undefined) {
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function (respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#changeList").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "changeList", "changePaging");
        }, "./changeList.ajax", $formObj, "POST", "html");
    }

    let fnpstnNmShow = function(pstnCd) {
        if(pstnCd == 'MEM_CD01007'){
            $("#pstnNm").css("display", "block");
        }else{
            $("#pstnNm").val("");
            $("#pstnNm").css("display", "none");
        }
    }
    // set model
    ctrl.model = {
        id : {
            btnUpdAdmMemo : {
                event : {
                    click : function() {
                        let appctnSeq = $formObj.find('input[type=hidden][name=appctnSeq]').val();
                        if(appctnSeq != '') {
                            let wBIBSupplyDTO = {}
                            wBIBSupplyDTO.admMemo = $('#admMemo').val();
                            wBIBSupplyDTO.appctnSeq = appctnSeq;

                            cmmCtrl.jsonAjax(function(respObj) {
                                var rtnData = JSON.parse(respObj);
                                if(rtnData.respCnt > 0) {
                                    alert(msgCtrl.getMsg("success.upd"));
                                    location.replace("./list");
                                }
                            }, "/mngwserc/wb/wbib/updAdmMemo", wBIBSupplyDTO, "text")
                        }
                    }
                }
            },
            ctgryCd : {
                event : {
                    change : function() {
                        fieldShowFn($(this).val());

                        var ctgryCd = $(this).val();

                        if(ctgryCd == 'COMPANY01001'){
                            $(".companyCate1").css("display", "block");
                            $(".SQ").val("");
                            $(".companyCate2").css("display", "none");
                        }else if(ctgryCd == 'COMPANY01002'){
                            $(".companyCate1").css("display", "none");
                            $(".starType").val("");
                            $(".companyCate2").css("display", "block");
                        }
                    }
                }
            },
            optYear : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($modalFormObj);
                        selEpisdList();
                    },
                }
            },
            /*optEpisd : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($modalFormObj);
                        selOptnList();
                    },
                }
            },*/
            mngSttsCd :{
                event : {
                    change : function(){
                        var mngSttsCd = $("#mngSttsCd option:selected").val();
                        if(mngSttsCd == 'PRO_TYPE06001_02_002' || mngSttsCd == 'PRO_TYPE06001_02_003'){
                            document.getElementById("rtrnRsnCntn").style.display = "block";
                        }else{
                            document.getElementById("rtrnRsnCntn").style.display = "none";
                        }

                        if(mngSttsCd == 'PRO_TYPE06001_02_002'){
                            document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_002";
                        }else if(mngSttsCd == 'PRO_TYPE06001_02_003'){
                            document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_005";
                        }else if(mngSttsCd == 'PRO_TYPE06001_02_004'){
                            document.getElementById("appctnSttsCd").value = "PRO_TYPE06001_01_006";
                        }
                    }
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width, height,"zipcode","bscAddr","dtlAddr");
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
            // 회원검색 모달
            btnPartUserModal: {
                event: {
                    click: function () {
                        /* 공통 모달 - 상생 사용 처리 */
                        $('.mpbMemberPartsSocietySrchLayer #srchPage').val('WB');
                        let appctnSeqVal = $formObj.find('input[type=hidden][name=appctnSeq]').val() ;
                        $(".mpbMemberPartsSocietySrchLayer #srchAppctnSeq").val(appctnSeqVal);

                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            $formObj.find('#memSeq').val(data.memSeq);
                            cmmCtrl.frmAjax(function(respObj) {
                                /* return data input */
                                setInputValue(respObj);
                                fnpstnNmShow($('#pstnCd').val());
                            }, "/mngwserc/wb/selModalDetail", $formObj, "post", "json");
                        });
                    }
                }
            },
            appctnPdfDownload : {
                event : {
                    click : function(){
                        var cmpnNm = $("#cmpnNm").val()
                        var fileName = "공금망안정화기금 사업 현황" + cmpnNm + ".pdf";
                        cmmCtrl.getAppctnPdfDownload(fileName);
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
                        //페이징 이동
                        if ($(this).closest('#changePaging').length > 0) {
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            trnsfSearch($(this).attr("value"));
                        } else {
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
            var pstnCd = $("#pstnCd").val();

            if(pstnCd =='MEM_CD01007'){
                $("#pstnNm").css("display", "block");
            }else{
                $("#pstnNm").val("");
                $("#pstnNm").css("display", "none");
            }
            //폼 데이터 처리
            cmmCtrl.setFormData($modalFormObj);
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });

            trnsfSearch(1);

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

                    if($(".dropzone .dz-preview").length < 1) {
                        alert(msgCtrl.getMsg("fail.notFileRequired"));
                        isValid = false;
                        return false;
                    }

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){

                        var beforeUser = $formObj.find("input[name='bfreMemSeq']").val();
                        var afterUser = $formObj.find("input[name='memSeq']").val();

                        if (beforeUser != afterUser) {
                            $formObj.find("input[name='memSeq']").val(afterUser);
                            $formObj.find("input[name='bfreMemSeq']").val(beforeUser);
                            $formObj.find("input[name='aftrMemSeq']").val(afterUser);
                            $formObj.find("input[name='userLogYn']").val('Y');
                            $formObj.find("input[name='bsnmNo']").val($formObj.find("input[name='bsnmNo']").val());
                        }

                        $formObj.admMemo = $("#admMemo").val();
                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                console.log(data.respCnt);
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.ins"));
                                    location.replace("./list");
                                }
                            }, "./update", $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                console.log(data.respCnt);

                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.ins"));
                                    location.replace("./list");
                                }
                            }, "./update", $formObj, "post", "json")
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


