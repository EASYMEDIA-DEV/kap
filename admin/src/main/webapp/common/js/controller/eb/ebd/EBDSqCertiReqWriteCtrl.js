define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/eb/ebd/EBDSqCertiReqWriteCtrl"
    };
    var $formObj = jQuery("#frmData");
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // set model
    ctrl.model = {
        id : {

        },
        classname : {
            listBtn : {
                event : {
                    click : function() {
                        if(confirm(msgCtrl.getMsg("confirm.list"))){
                            location.href="./list?" + $(this).data("strPam");
                        }
                    }
                }
            }
        },
        immediately : function(){
            // 유효성 검사
            $formObj.validation({
                before: function(){
                    ctrl.obj.find("input[name=rtrnRsn]").removeClass("notRequired");
                    ctrl.obj.find("input[name=rtrnRsn]").addClass("notRequired");
                    if(ctrl.obj.find("select[name=issueCd]").val() =="EBD_SQ_C"){
                        ctrl.obj.find("input[name=rtrnRsn]").removeClass("notRequired");
                    }
                },
                after : function() {
                    var isValid = true
                    return isValid;
                },
                customfunc : function(obj, tagid, okval, msg){

                },
                async : {
                    use : true,
                    func : function (){
                        if(confirm(msgCtrl.getMsg("confirm.sve"))){
                            cmmCtrl.frmAjax(function(data){

                            }, "./update", $formObj, "post")
                        }
                    }
                },
                msg : {
                    empty : {
                        text : " 입력해주세요."
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

