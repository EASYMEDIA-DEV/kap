define(["ezCtrl", "ezVald", "ezFile"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cbb/CBBManageConsultCompleteCtrl"
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
                console.log(data)
                var completeInfo = JSON.parse(data);
                var regDtm = completeInfo.regDtm;
                var name = completeInfo.name;
                var cmpnNm = completeInfo.cmpnNm;
                var appctnFldNm = completeInfo.appctnFldNm;
                var etcNm = completeInfo.etcNm;
                var cbsnCd = completeInfo.cbsnCd

                $(".regDtm").text(regDtm);
                $(".name").text(name);
                $(".cmpnNm").text(cmpnNm);
                $(".appctnFldNm").text(appctnFldNm);

            }, './completeInfo', info, "text");
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});