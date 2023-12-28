define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smh/SMHSmsSendYnWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    // set model
    ctrl.model = {
        id : {
            btnSmsYn : {
                event : {
                    click : function () {
                        var frmDataObj    = $(this).closest("form");
                        var smsSendYn = frmDataObj.find("input[name='smsSendYn']:checked").val();
                        var confirmMsg = "";
                        if (smsSendYn === "Y") {
                            confirmMsg = "‘ON’로 설정 시 모든 SMS는 발송이 시작됩니다.\n설정하시겠습니까?";
                        } else {
                            confirmMsg = "‘OFF’로 설정 시 모든 SMS는 발송이 중지됩니다.\n설정하시겠습니까?";
                        }

                        if(confirm(confirmMsg))
                        {
                            cmmCtrl.frmAjax(function(respObj){
                                if(respObj.respCnt > 0){
                                    var msg = "저장되었습니다.";
                                    alert(msg);
                                }
                                else{
                                    alert(msgCtrl.getMsg("fail.act"));
                                }
                            }, "./update", frmDataObj, "POST", "json");
                        } else {

                        }
                    }
                }
            }
        },
        classname : {
            // do something...
        },
        immediately : function(){

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

