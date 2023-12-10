define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPESqCertiAppliyCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            // 페이징 처리
            idntfnPhotoFileSeq : {
                event : {
                    change : function() {
                        //파일정보
                        var fileName = ctrl.obj.find("input[type=file][name="+$(this).prop("name")+"]")[0].files[0].name;
                        var fileSize = ctrl.obj.find("input[type=file][name="+$(this).prop("name")+"]")[0].files[0].size;
                        var fileExtn = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length).toLowerCase();
                        var maxSize = $(this).data("maxSize");
                        var accept  = $(this).data("accept");
                        //확장자 체크
                        if(accept.indexOf(fileExtn) == -1){
                            cmmCtrl.inputFileInit( ctrl.obj.find("input[type=file][name="+$(this).prop("name")+"]") );
                            alert(msgCtrl.getMsg("fail.file.extn"));
                            return false;
                        }
                        //파일명 길이 체크
                        var orgnFileLength = fileName.substring(1, fileName.lastIndexOf(".")+1).length;
                        if(orgnFileLength > 20){
                            cmmCtrl.inputFileInit( $("input[type=file][name="+$(this).prop("name")+"]") );
                            alert(msgCtrl.getMsg("fail.file.length"));
                            return false;
                        }
                        //파일명 사이즈
                        if(fileSize > maxSize || fileSize == 0){
                            var msg = msgCtrl.getMsg("fail.file.size");
                            cmmCtrl.inputFileInit( ctrl.obj.find("input[type=file][name="+$(this).prop("name")+"]") );
                            if(fileSize == 0){
                                msg = msgCtrl.getMsg("fail.file.no_size");
                            }
                            alert(msg);
                            return false;
                        }
                        ctrl.obj.find(".file-list-area .empty-txt").text(fileName);
                    }
                }
            },
            // 페이징 처리
            submitBtn : {
                event : {
                    click : function() {
                        $formObj.submit();
                    }
                }
            },
        },
        classname : {

        },
        immediately : function() {
            $formObj.validation({
                msg : {
                    confirm: {
                        init: msgCtrl.getMsg("confirm.sqCertiApply")
                    }
                },
                after : function() {
                    var isAfter = true;

                    return isAfter;
                },
                before : function() {

                },
                async : {
                    use : true,
                    func : function (){
                        cmmCtrl.fileFrmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(msgCtrl.getMsg("success.sqCertiApply"));
                            }
                            else
                            {
                                alert(msgCtrl.getMsg("fail.act"));
                            }
                            location.reload();
                        }, "./complete/insert", $formObj, "json", true , true);
                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});