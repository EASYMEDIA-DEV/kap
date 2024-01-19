define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdd/BDDNewsletterViewCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);


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

