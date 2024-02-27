define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/COReqMemCmpnWrite"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // set model
    ctrl.model = {
        id : {
            memTelNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen >= 3 && phoneLen <= 6) {
                            phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                        } else if (phoneLen > 6) {
                            if (phoneLen == 9) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
            cmpnTelNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen > 3 && phoneLen <= 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                        } else if (phoneLen > 7) {
                            if (phoneLen == 10) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            }
        },
        classname : {
            cmpnZipcodeSrchBtn:{
                event : {
                    click : function() {
                        // searchPostCode();
                        cmmCtrl.searchPostCode(500,600,"cmpnContainerCmpnZipcode","cmpnContainerCmpnBscAddr","cmpnContainerCmpnDtlAddr");
                    }
                }
            },
            memPstnCd :{
                event : {
                    change: function(){
                        if($(this).val() == "MEM_CD01007"){
                            ctrl.obj.find(".memPstnCdEtc").show();
                        }else{
                            ctrl.obj.find(".memPstnCdEtc").hide();
                        }
                    }
                }
            }
        },
        immediately : function() {
            cmmCtrl.paramAjax(function (data) {
                ctrl.obj.find(".memName").text( data.memName + "("+data.memId+")" );
                ctrl.obj.find("input[name=memEmail]").val( data.memEmail );
                //부서
                var memCdList = data.cdDtlList.MEM_CD;
                ctrl.obj.find("select[name=memDeptCd]").append("<option value=\"\">선택</option>");
                for(var q = 0; q < memCdList.length ; q++){
                    if(memCdList[q].cd.indexOf("MEM_CD02") > -1 && memCdList[q].dpth == 3){
                        ctrl.obj.find("select[name=memDeptCd]").append("<option value=\""+memCdList[q].cd+"\" "+(data.memDeptCd == memCdList[q].cd ? "selected" : "")+">"+memCdList[q].cdNm+"</option>")
                    }
                }
                ctrl.obj.find("input[name=memDeptDtlNm]").val( data.memDeptDtlNm );
                //직급
                var memCdList = data.cdDtlList.MEM_CD;
                ctrl.obj.find("select[name=memPstnCd]").append("<option value=\"\">선택</option>");
                for(var q = 0; q < memCdList.length ; q++){
                    if(memCdList[q].cd.indexOf("MEM_CD01") > -1 && memCdList[q].dpth == 3){
                        ctrl.obj.find("select[name=memPstnCd]").append("<option value=\""+memCdList[q].cd+"\" "+(data.memPstnCd == memCdList[q].cd ? "selected" : "")+">"+memCdList[q].cdNm+"</option>")
                    }
                }
                //직급 기타
                if(data.memPstnCd == "MEM_CD01007"){
                    ctrl.obj.find(".memPstnCdEtc").show();
                    ctrl.obj.find("input[name=memPstnNm]").val( data.memPstnNm);
                }

                ctrl.obj.find(".memHpNo").text( data.memHpNo);
                ctrl.obj.find("input[name=memTelNo]").val( data.memTelNo );
                //회사정보
                if(data.memMemCd == "CP"){
                    ctrl.obj.find(".cmpnNm").text( data.cmpnNm);
                    memCdList = data.cdDtlList.COMPANY_TYPE;
                    ctrl.obj.find("select[name=cmpnCtgryCd]").append("<option value=\"\">선택</option>");
                    for(var q = 0; q < memCdList.length ; q++){
                        if(memCdList[q].cd.indexOf("COMPANY01") > -1 && memCdList[q].dpth == 3){
                            ctrl.obj.find("select[name=cmpnCtgryCd]").append("<option value=\""+memCdList[q].cd+"\" "+(data.cmpnCtgryCd == memCdList[q].cd ? "selected" : "")+">"+memCdList[q].cdNm+"</option>")
                        }
                    }
                    ctrl.obj.find("select[name=cmpnSizeCd]").append("<option value=\"\">선택</option>");
                    for(var q = 0; q < memCdList.length ; q++){
                        if(memCdList[q].cd.indexOf("COMPANY02") > -1 && memCdList[q].dpth == 3){
                            ctrl.obj.find("select[name=cmpnSizeCd]").append("<option value=\""+memCdList[q].cd+"\" "+(data.cmpnSizeCd == memCdList[q].cd ? "selected" : "")+">"+memCdList[q].cdNm+"</option>")
                        }
                    }
                    ctrl.obj.find("input[name=cmpnRprsntNm]").val( data.cmpnRprsntNm );
                    ctrl.obj.find("input[name=cmpnStbsmDt]").val( data.cmpnStbsmDt );
                    ctrl.obj.find("input[name=cmpnTelNo]").val( data.cmpnTelNo );
                    ctrl.obj.find(".cmpnBsnmNo").text( data.cmpnBsnmNo);
                    ctrl.obj.find("input[name=cmpnZipcode]").val( data.cmpnZipcode );
                    ctrl.obj.find("input[name=cmpnBscAddr]").val( data.cmpnBscAddr );
                    ctrl.obj.find("input[name=cmpnDtlAddr]").val( data.cmpnDtlAddr );
                    ctrl.obj.find("input[name=cmpnSlsPmt]").val( data.cmpnSlsPmt );
                    memCdList = data.cdDtlList.CO_YEAR_CD;
                    ctrl.obj.find("select[name=cmpnSlsYear]").append("<option value=\"\">선택</option>");
                    for(var q = 0; q < memCdList.length ; q++){
                        ctrl.obj.find("select[name=cmpnSlsYear]").append("<option value=\""+memCdList[q].cd+"\" "+(data.cmpnSlsYear == memCdList[q].cd ? "selected" : "")+">"+memCdList[q].cdNm+"</option>")
                    }
                    ctrl.obj.find("input[name=cmpnMpleCnt]").val( data.cmpnMpleCnt );
                    ctrl.obj.find("input[name=cmpnMjrPrdct1]").val( data.cmpnMjrPrdct1 );
                    ctrl.obj.find("input[name=cmpnMjrPrdct2]").val( data.cmpnMjrPrdct2 );
                    ctrl.obj.find("input[name=cmpnMjrPrdct3]").val( data.cmpnMjrPrdct3 );
                }else{
                    ctrl.obj.find(".cmpnContainer").remove();
                }
            }, "./get-member-company-info", {memSeq: ctrl.obj.data("memSeq")}, "json", false, true, "get");
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});