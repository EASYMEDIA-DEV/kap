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

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});