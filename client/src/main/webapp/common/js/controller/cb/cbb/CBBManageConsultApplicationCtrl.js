define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cbb/CBBManageConsultApplicationCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    var bsnmNoSearch = function(data){
        var info = {}
        info.bsnmNo = data;
        cmmCtrl.jsonAjax(function (data) {
            var coInfo = JSON.parse(data)
            $("#cmpnNm").text(coInfo.list[0].cmpnNm);
            $("#rprsntNm").text(coInfo.list[0].rprsntNm);
            $("#ctgryNm").text(coInfo.list[0].ctgryNm);
            $("#sizeNm").text(coInfo.list[0].sizeNm);

            var ctgryCd = coInfo.list[0].ctgryCd;
            if(ctgryCd == "COMPANY01002"){
                $(".sqInfo").show();
                if(coInfo.list.length > 0) {
                    for(var i=0; i <coInfo.list.length; i++){
                        if(coInfo.list[0].nm != "") {
                            if(coInfo.list[i].nm){
                                var html = "<p>"+(i+1)+'. '+coInfo.list[i].nm+' / '+coInfo.list[i].score+' / '+coInfo.list[i].year+' 년 / '+coInfo.list[i].crtfnCmpnNm+"</p>";
                                var replaceHtml = html.replaceAll("undefined", "-")
                                document.getElementById("sqInfo").innerHTML += replaceHtml
                            }
                        }else{
                            document.getElementById("sqInfo").innerHTML += "<p>-</p>"
                            break;
                        }
                    }
                }
                else {
                    document.getElementById("sqInfo").innerHTML += "<p>-</p>"
                }
            }else if(ctgryCd == "COMPANY01001"){

                $(".fiveStar").show();

                // 품질 5스타
                if(coInfo.list[0].qlty5StarCdNm){
                    $("#qlty5Star").text(coInfo.list[0].qlty5StarCdNm+" / "+coInfo.list[0].qlty5StarYear);
                }else{
                    $("#qlty5Star").text("-");
                }

                // 납입 5스타
                if(coInfo.list[0].pay5StarCdNm){
                    $("#pay5Star").text(coInfo.list[0].pay5StarCdNm+" / "+coInfo.list[0].pay5StarYear);
                }else{
                    $("#pay5Star").text("-");
                }

                //기술 5스타
                if(coInfo.list[0].tchlg5StarCdNm){
                    $("#techlg5Star").text(coInfo.list[0].tchlg5StarCdNm+" / "+coInfo.list[0].tchlg5StarYear);
                }else{
                    $("#techlg5Star").text("-");
                }

            }
            $("#ctgryCd").val(ctgryCd);
            $("#stbsmDt").text(coInfo.list[0].stbsmDt);
            $("#telNo").text(coInfo.list[0].telNo);
            $("#dtlAddr").text("("+coInfo.list[0].zipcode+") "+coInfo.list[0].bscAddr+" "+coInfo.list[0].dtlAddr);
            $("#slsPmt").text(coInfo.list[0].slsPmt+"억 원"+" ("+coInfo.list[0].slsYear+"년)");
            $("#mpleCnt").text(coInfo.list[0].mpleCnt+"명");
            if(coInfo.list[0].mjrPrdct1){
                $("#mjrPrdct").text("① "+coInfo.list[0].mjrPrdct1);
                if(coInfo.list[0].mjrPrdct2){
                    $("#mjrPrdct").text("① "+coInfo.list[0].mjrPrdct1+" ② "+coInfo.list[0].mjrPrdct2);
                    if(coInfo.list[0].mjrPrdct3){
                        $("#mjrPrdct").text("① "+coInfo.list[0].mjrPrdct1+" ② "+coInfo.list[0].mjrPrdct2+" ③ "+coInfo.list[0].mjrPrdct3);
                    }
                }
            }else{
                $("#mjrPrdct").text("-");
            }
        }, './checkPartsCompany', info, "text");
    }

    var memberSearch = function(data){
        var memInfo = {};
        memInfo.detailsKey = data;
        cmmCtrl.jsonAjax(function (data) {
            var memInfo = JSON.parse(data)
            $(".appDeptNm").text(memInfo.deptCdNm+" ("+memInfo.deptDtlNm+")");
        }, './selectDtlInfo', memInfo, "text");
    };

    // set model
    ctrl.model = {
        id : {
            infoSameChk : {
                event : {
                    click : function(){
                        var checked = $("#infoSameChk").is(':checked');
                        var appName = $(".appName").text();
                        var appHpNo = $(".appHpNo").text();
                        var appTelNo = $(".appTelNo").text();
                        var appEmail = $(".appEmail").text();
                        var appDeptNm = $(".appDeptNm").text();
                        var appPstnCdNm = $(".appPstnCdNm").text();

                        if(checked){
                            $(".picName").val(appName);
                            $(".cmssrHpNo").val(appHpNo);
                            $(".cmssrCmpnTelNo").val(appTelNo);
                            var splitMail = appEmail.split("@");
                            $(".id").val(splitMail[0]);
                            $(".address").val(splitMail[1]);
                            $(".picDeptNm").val(appDeptNm);
                            $(".picPstnNm").val(appPstnCdNm);
                        }else{
                            $(".picName").val("");
                            $(".cmssrHpNo").val("");
                            $(".cmssrCmpnTelNo").val("");
                            $(".id").val("");
                            $(".address").val("");
                            $(".picDeptNm").val("");
                            $(".picPstnNm").val("");
                        }
                    }
                }
            },
            addressSelect : {
                event : {
                    click : function(){
                        var select = $("#addressSelect option").index($("#addressSelect option:selected"));
                        if(!select == '0'){
                            $(".address").val($(this).val());
                            $(".address").attr("readonly", true);
                        }else{
                            $(".address").attr("readonly", false);
                        }
                    }
                }
            }
        },
        classname : {
            consInfoApply : {
                event : {
                    click : function(){
                        var picName = $(".picName").val();
                        var cmssrHpNo = $(".cmssrHpNo").val();
                        var id = $(".id").val();
                        var address = $(".address").val();
                        var cmssrCmpnTelNo = $(".cmssrCmpnTelNo").val();
                        var picDeptNm = $(".picDeptNm").val();
                        var picPstnNm = $(".picPstnNm").val();
                        var ctgryCd = $("#ctgryCd").val();

                        if(!picName){
                            alert("이름을 입력해주세요.")
                            $(".picName").focus();
                            return false;
                        }else{
                            if(!cmssrHpNo){
                                alert("휴대폰번호를 입력해주세요.");
                                $(".cmssrHpNo").focus();
                                return false;
                            }else{
                                if(!id){
                                    alert("이메일을 입력해주세요.");
                                    $(".id").foucus();
                                    return false;
                                }else{
                                    if(!address){
                                        alert("이메일을 입력해주세요.");
                                        $(".address").foucus();
                                        return false;
                                    }else{
                                        if(!cmssrCmpnTelNo){
                                            alert("회사 전화번호를 입력해주세요.");
                                            $(".cmssrCmpnTelNo").foucus();
                                            return false;
                                        }else{
                                            if(!picDeptNm){
                                                alert("부서를 입력해주세요.");
                                                $(".picDeptNm").foucus();
                                                return false;
                                            }else{
                                                if(!picPstnNm){
                                                    alert("직급을 입력해주세요.");
                                                    $(".picPstnNm").foucus();
                                                    return false;
                                                }else{
                                                    if(!ctgryCd == "COMPANY01002" || !ctgryCd == "COMPANY01001"){
                                                        alert("1, 2차 부품사만 신청 가능합니다.");
                                                        return false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        var cmssrInfo = {}
                        cmssrInfo.picName = picName;
                        cmssrInfo.cmssrHpNo = cmssrHpNo;
                        cmssrInfo.picEmail = id+"@"+address;
                        cmssrInfo.cmssrCmpnTelNo = cmssrCmpnTelNo;
                        cmssrInfo.picDeptNm = picDeptNm;
                        cmssrInfo.picPstnNm = picPstnNm;
                        cmssrInfo.ctgryCd = ctgryCd;

                        $("#consultFrm").attr("action","./consInfoApply");
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'bsnmNo', value:$("#bsnmNo").val() }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'picName', value:cmssrInfo.picName }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'cmssrHpNo', value:cmssrInfo.cmssrHpNo }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'picEmail', value:cmssrInfo.picEmail }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'cmssrCmpnTelNo', value:cmssrInfo.cmssrCmpnTelNo }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'picDeptNm', value:cmssrInfo.picDeptNm }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'picPstnNm', value:cmssrInfo.picPstnNm }));
                        $("#consultFrm").append($('<input/>', {type: 'hidden', name: 'ctgryCd', value:cmssrInfo.ctgryCd }));
                        $("#consultFrm").submit();

                    }
                }
            },
            cmssrInfo : {
                event : {
                    change : function(){
                        $("#infoSameChk").attr("checked", false);
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
            }
        },
        immediately : function() {
            bsnmNoSearch($("#bsnmNo").val());
            memberSearch($(".memSeq").val());
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});