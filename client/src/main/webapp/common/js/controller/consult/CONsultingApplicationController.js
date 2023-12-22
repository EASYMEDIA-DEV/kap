define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/consult/CONsultingApplicationController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var bsnmNoSearch = function(data){
        var info = {}
        info.bsnmNo = data;
        cmmCtrl.jsonAjax(function (data) {
            var coInfo = JSON.parse(data)
            console.log(coInfo.list);
            coInfo.list.forEach(function(info, i,array){
                /*console.log(array[i].bsnmNo)*/
                    if(i==0){
                        $("#cmpnNm").text(array[0].cmpnNm);
                    }
                }

            )

        }, './checkPartsCompany', info, "text");
    }

    // set model
    ctrl.model = {
        id : {

        },
        classname : {
        },
        immediately : function() {
            bsnmNoSearch($("#bsnmNo").val())
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});