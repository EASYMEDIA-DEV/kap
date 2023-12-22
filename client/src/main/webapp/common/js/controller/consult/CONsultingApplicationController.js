define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/consult/CONsultingApplicationController"
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
            bsnmNoSearch($("#bsnmNo").val())
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});