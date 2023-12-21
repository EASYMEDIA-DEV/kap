define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/consult/CONsultingIndexController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // set model
    ctrl.model = {
        id : {

        },
        classname : {
            //검색 레이어에서 선택시 호출
            open : {
                event : {
                    click : function(){
                       $(".memberDetailsPopup ").show();
                       $("body").addClass("stop-scroll");
                       $(".dimd").css("z-index", `${ dimdIdxNum + $(".layer-popup:visible").length }`).stop(true, true).fadeIn(300);
                    }
                }
            }
        },
        immediately : function() {

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});