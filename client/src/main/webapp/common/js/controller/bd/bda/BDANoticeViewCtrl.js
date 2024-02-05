define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bda/BDANoticeViewCtrl"
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
                        $formObj.find("input[name=mainPostYn]").val($(this).data("nextMainPostYn"));
                        location.href = "./view?" + $formObj.serialize();
                    }
                }
            },
            prevBtn : {
                event : {
                    click : function() {
                        $formObj.find("input[name=detailsKey]").val($(this).data("prevSeq"));
                        $formObj.find("input[name=mainPostYn]").val($(this).data("prevMainPostYn"));
                        location.href = "./view?" + $formObj.serialize();
                    }
                }
            },
            listBtn : {
                event : {
                    click : function(){
                        location.href = "./list?" + $formObj.serialize();
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

