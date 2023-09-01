define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/sme/SMEDrflsWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
        },
        immediately : function() {
            // 유효성 검사
            $formObj.validation({
                msg : {
                    confirm : {
                        custom : [{
                            action : "insert",
                            message : msgCtrl.getMsg("confirm.ins")
                        },{
                            action : "update",
                            message : msgCtrl.getMsg("confirm.upd")
                        }]
                    }
                },
                before: function(){
                    //에디터 내용 바인딩
                    getDext5WebEditorContentsValue();
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./drfls");
                                }
                            }, actionUrl, $formObj, "post", "json")
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                }
            });

        }
    };

    ctrl.exec();

    return ctrl;
});

