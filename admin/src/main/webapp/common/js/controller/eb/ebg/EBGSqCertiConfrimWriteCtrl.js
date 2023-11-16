define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/eb/ebg/EBGSqCertiConfrimWriteCtrl"
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
                        }else{
                            ctrl.obj.find(".rtrnRsnContainer").hide();
                        }
                    }
                }
            },
        },
        immediately : function(){
            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm"),
                })
            });


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
                        cmmCtrl.fileFrmAjax(function(data){
                            //콜백함수. 페이지 이동
                            if(data.respCnt > 0){
                                //location.replace("./list");
                            }else if(data.respCnt == '-1'){
                                alert(data.SMBMainVslDTO.respMsg);
                            }
                        }, "./update", $formObj, "json");
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

