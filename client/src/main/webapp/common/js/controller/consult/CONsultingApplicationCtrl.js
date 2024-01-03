define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/consult/CONsultingApplicationCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

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

        },
        classname : {
            consInfoAppl : {
                event : {
                    click : function(){
                        location.href= "./consInfoAppl?bsnmNo="+$("#bsnmNo").val();
                    }
                }
            }
        },
        immediately : function() {
            bsnmNoSearch($("#bsnmNo").val())
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});