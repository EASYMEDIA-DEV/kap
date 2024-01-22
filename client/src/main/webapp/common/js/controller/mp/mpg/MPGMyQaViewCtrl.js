define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpg/MPGMyQaViewCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //function


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

