define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPESqCertiManageBtnCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            // 페이징 처리
            paymentInfoManagPopupBtn : {
                event : {
                    click : function() {
                        cmmCtrl.frmAjax(function(data) {
                            $("#paymentInfoManagPopupFrm").html(data);
                            openPopup('paymentInfoManagPopup')
                        }, "./complete/apply", $formObj, "GET", "html");
                    }
                }
            },
        },
        classname : {

        },
        immediately : function() {

        }
    };

    ctrl.exec();

    return ctrl;
});