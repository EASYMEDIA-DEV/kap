define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdb/BDBCompanyNewsViewCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            nextBtn : {
                event : {
                    click : function() {
                        $formObj.find("input[name=detailsKey]").val($(this).data("nextSeq"));
                        location.href = "./view?" + $formObj.serialize();
                    }
                }
            },
            prevBtn : {
                event : {
                    click : function() {
                        $formObj.find("input[name=detailsKey]").val($(this).data("prevSeq"));
                        location.href = "./view";
                    }
                }
            },
            listBtn : {
                event : {
                    click : function(){
                        location.href = "./list";
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {

        }
    };
    ctrl.exec();

    return ctrl;
});

