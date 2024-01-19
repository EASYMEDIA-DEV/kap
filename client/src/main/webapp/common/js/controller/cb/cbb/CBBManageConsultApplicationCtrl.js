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
                for(var i=0; i <coInfo.list.length; i++){
                    if(coInfo.list[i].nm){
                    document.getElementById("sqInfo").innerHTML +=
                    "<p>"+coInfo.list[i].nm+' /'+coInfo.list[i].score+' /'+coInfo.list[i].year+' /'+coInfo.list[i].crtfnCmpnNm+"</p>";
                    }
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

            $("#stbsmDt").text(coInfo.list[0].stbsmDt);
            $("#telNo").text(coInfo.list[0].telNo);
            $("#dtlAddr").text("("+coInfo.list[0].zipcode+")"+coInfo.list[0].dtlAddr);
            $("#slsPmt").text(coInfo.list[0].slsPmt+"억 원"+" ("+coInfo.list[0].slsYear+"년)");
            $("#mpleCnt").text(coInfo.list[0].mpleCnt+"명");
            $("#mjrPrdct").text("① "+coInfo.list[0].mjrPrdct1);

            if(coInfo.list[0].mjrPrdct2){
                $("#mjrPrdct").text("① "+coInfo.list[0].mjrPrdct1+" ② "+coInfo.list[0].mjrPrdct2);
                if(coInfo.list[0].mjrPrdct3){
                    $("#mjrPrdct").text("① "+coInfo.list[0].mjrPrdct1+" ② "+coInfo.list[0].mjrPrdct2+" ③ "+coInfo.list[0].mjrPrdct3);
                }
            }
        }, './checkPartsCompany', info, "text");
    }

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
            consInfoAppl : {
                event : {
                    click : function(){
                        var picName = $(".picName").val();
                        var cmssrHpNo = $(".cmssrHpNo").val();
                        var id = $(".id").val();
                        var address = $(".address").val();
                        var cmssrCmpnTelNo = $(".cmssrCmpnTelNo").val();
                        var picDeptNm = $(".picDeptNm").val();
                        var picPstnNm = $(".picPstnNm").val();

                        location.href= "./consInfoAppl?bsnmNo="+$("#bsnmNo").val();
                    }
                }
            },
            cmssrInfo : {
                event : {
                    change : function(){
                        $("#infoSameChk").attr("checked", false);
                    }
                }
            }
        },
        immediately : function() {
            bsnmNoSearch($("#bsnmNo").val());
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});