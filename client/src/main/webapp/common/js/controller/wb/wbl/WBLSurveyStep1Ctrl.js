define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLSurveyStep1Ctrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            noSurvey : {
                event : {
                    click : function() {
                        if(confirm('해당 부품사에 대한 평가를 미참여하시겠습니까?')){

                            var wblMst = {};
                            wblMst.cxstnSrvSeq = $(this).data('cxstn-srv-seq');

                            cmmCtrl.jsonAjax(function(data){
                                var rtn = JSON.parse(data);
                                if (rtn.respCnt > 0 ){
                                    alert("해당 부품사에 대한 미참여 처리되었습니다.");
                                    location.reload();
                                }
                            }, './updateNoSurvey', wblMst, "text")

                        }
                    }
                }
            },
            goSurvey : {
                event : {
                    click : function() {

                        var wblMst = {};
                        wblMst.cxstnSrvSeq = $(this).data('cxstn-srv-seq');

                        cmmCtrl.jsonAjax(function(data){
                            var rtn = JSON.parse(data);
                            if (rtn.respCnt > 0 ){
                                location.href='./step2';
                            }
                        }, './step2Check', wblMst, "text")

                    }
                }
            },
        },
        immediately : function(){

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

