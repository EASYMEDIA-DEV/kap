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
            // 평가원 신청
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
            // 평가원 보기
            paymentInfoViewPopupBtn : {
                event : {
                    click : function() {
                        openPopup('paymentInfoViewPopupFrm');
                        if($.trim( $(".paymentInfoViewPopupFrm").find(".idntfnPhotoFileSeqImage").data("src")) != ""){
                            $(".paymentInfoViewPopupFrm").find(".idntfnPhotoFileSeqImage").attr("src",  $(".paymentInfoViewPopupFrm").find(".idntfnPhotoFileSeqImage").data("src"));
                        }
                        $(".paymentInfoViewPopupFrm").on("onModalOpenComplete", function(){
                            flowTxtImgFn();
                        })

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