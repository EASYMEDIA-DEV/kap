define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/SMDBTmncsController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("frmData");

    // set model
    ctrl.model = {
        id : {

        },
        classname : {

        },
        immediately : function() {
        }
    };

    ctrl.exec();

    return ctrl;
});