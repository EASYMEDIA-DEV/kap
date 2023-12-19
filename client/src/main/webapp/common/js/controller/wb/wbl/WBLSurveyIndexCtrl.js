define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLSurveyIndexCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            btnSubmit : {
                event : {
                    click : function() {
                        $formObj.submit();
                    }
                }
            },


        },
        classname : {
        },
        immediately : function(){
            // 유효성 검사
            $formObj.validation({
                before: function(){

                },
                after : function() {
                    var isValid = true

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./authCheck";
                        var wblMst = {};
                        wblMst.crtfnNo = $formObj.find("input[name=crtfnNo]").val();

                        cmmCtrl.jsonAjax(function(data){
                            var rtn = JSON.parse(data);
                            if (rtn.respCnt > 0 ){
                                location.href='./step1';
                            }else{
                                alert("인증번호가 일치하지 않습니다.");
                                return;
                            }
                        }, actionUrl, wblMst, "text")
                    }
                },
                msg : {
                    confirm : {
                        init : ""
                    }
                }

            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

