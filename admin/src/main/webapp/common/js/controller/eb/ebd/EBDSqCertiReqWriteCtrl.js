define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/eb/ebd/EBDSqCertiReqWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = ctrl.obj.find("form").first();
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
            },
            issueCd : {
                event : {
                    change : function() {
                        if($(this).val() == "EBD_SQ_C"){
                            ctrl.obj.find(".rtrnRsnContainer").show();
                            ctrl.obj.find(".jdgmtNoContainer").hide();
                        }else{
                            ctrl.obj.find(".rtrnRsnContainer").hide();
                            ctrl.obj.find(".jdgmtNoContainer").show();
                        }
                    }
                }
            },
        },
        immediately : function(){

            //반려사유, 자격증 번호 노출
            ctrl.obj.find("select[name=issueCd]").change();

            // 유효성 검사
            $formObj.validation({
                before: function(){
                    //검증 초기화
                    ctrl.obj.find("input[name=rtrnRsn]").removeClass("notRequired");
                    ctrl.obj.find("input[name=rtrnRsn]").addClass("notRequired");
                    ctrl.obj.find("input[name=jdgmtNo]").removeClass("notRequired");
                    ctrl.obj.find("input[name=jdgmtNo]").addClass("notRequired");
                    //발급상태가 반려일때
                    //반려사유 필수
                    if(ctrl.obj.find("select[name=issueCd]").val() =="EBD_SQ_C"){
                        ctrl.obj.find("input[name=rtrnRsn]").removeClass("notRequired");
                    }
                    //발급상태가 발급일때
                    //자격증 번호 필수
                    if(ctrl.obj.find("select[name=issueCd]").val() =="EBD_SQ_I"){
                        ctrl.obj.find("input[name=jdgmtNo]").removeClass("notRequired");
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
                        cmmCtrl.frmAjax(function(data){
                            location.href = "./list";
                        }, "./update", $formObj, "post")
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

