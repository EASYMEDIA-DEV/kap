define(["ezCtrl", "ezVald", "ezFile"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cba/CBATechGuidanceCompleteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
        },
        immediately : function() {
            var info = {};
            info.cnstgSeq = $(".cnstgSeq").val();
            cmmCtrl.jsonAjax(function (data) {
                var completeInfo = JSON.parse(data);
                var regDtm = completeInfo.regDtm;
                var name = completeInfo.name;
                var cmpnNm = completeInfo.cmpnNm;
                var appctnFldNm = completeInfo.appctnFldNm;
                var etcNm = completeInfo.etcNm;
                var cbsnCd = completeInfo.cbsnCd

                var replacedRegDtm = regDtm.replace(/-/g, '.');
                $(".regDtm").text(replacedRegDtm.slice(0,16));
                $(".name").text(name);
                $(".cmpnNm").text(cmpnNm);
                if(cbsnCd.indexOf("METAL")>0){
                    $(".appctnFldNm").text("금속분야-"+appctnFldNm);
                }else if(cbsnCd.indexOf("NON")>0){
                    $(".appctnFldNm").text("비금속분야-"+appctnFldNm);
                }else{
                    $(".appctnFldNm").text("기타-"+etcNm);
                }

            }, './completeInfo', info, "text");
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});