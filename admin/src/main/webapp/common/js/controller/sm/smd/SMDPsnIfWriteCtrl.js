define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smd/SMDPsnIfWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    // create function
    var callbackAjaxSave = function(data) {
        var successMsg = ($.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "success.ins" : "success.upd" );

        if (data.respCnt > 0){
            alert(msgCtrl.getMsg(successMsg));
            location.href = "./list";
        } else {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    var callbackAjaxDelete = function(data) {
        if (data.respCnt > 0) {
            alert(msgCtrl.getMsg("success.del.target.board"));
            location.href = "./list";
        } else {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    // set model
    ctrl.model = {
        id : {
            // 목록
            btnList : {
                event : {
                    click : function() {
                        if (confirm(msgCtrl.getMsg("confirm.list"))) {
                            var strPam = jQuery("input[name='strPam']").val();
                            location.href="./list?" + strPam;
                        }
                    }
                }
            },
            // 삭제
            btnOneDelete: {
                event: {
                    click: function () {
                        if (confirm(msgCtrl.getMsg("confirm.del"))) {
                            cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj, "post", "json");
                        }
                    }
                }
            },
        },
        classname : {

        },
        immediately : function() {
            $formObj.validation({
                msg : {
                    confirm : {
                        custom : [
                            { action: "insert", message: msgCtrl.getMsg("confirm.ins")},
                            { action: "update", message: msgCtrl.getMsg("confirm.upd")}
                        ]
                    }
                },
                before : function() {
                    //에디터 내용 바인딩
                    getDext5WebEditorContentsValue();
                },
                async : {
                    use : true,
                    func : function () {
                        var actionUrl = !$.trim($formObj.find("input[name='detailsKey']").val()) ? "insert" : "update";
                        cmmCtrl.frmAjax(callbackAjaxSave, actionUrl, $formObj, "post","json");
                    },
                    error: function(e) {

                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});