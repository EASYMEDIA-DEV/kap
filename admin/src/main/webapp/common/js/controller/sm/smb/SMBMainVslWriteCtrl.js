define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smb/SMBMainVslWriteCtrl"
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
            // 상시여부
            odtmYn : {
                event : {
                    change : function() {
                        var isCheck = $(this).is(":checked");
                        cmmCtrl.setPeriod(this, "", "", false);
                        if(isCheck) {
                            $formObj.find(".datetimepicker_strtDtm, .datetimepicker_endDtm").val("");
                            $formObj.find(".datetimepicker_strtDtm, .datetimepicker_endDtm").addClass("notRequired").prop("disabled", true);
                        } else {
                            $formObj.find(".datetimepicker_strtDtm, .datetimepicker_endDtm").removeClass("notRequired").prop("disabled", false);
                        }
                    }
                }
            },
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
            /* File Dropzone Setting */
            $(".dropzone").each(function(){
                var trgtObj = $(this);

                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });

            $formObj.validation({
                msg : {
                    confirm : {
                        custom : [
                            { action: "insert", message: msgCtrl.getMsg("confirm.ins")},
                            { action: "update", message: msgCtrl.getMsg("confirm.upd")}
                        ]
                    },
                    empty : {
                        names :{
                            postStrtDtm : "게시시간을 선택하세요.",
                            postEndDtm : "게시시간을 선택하세요.",
                        }
                    }
                },
                after : function(){
                    // 링크 유효성 체크 (값이 있을 때 확인)
                    var url = jQuery("#url");
                    if(url.val() != null && url.val() != undefined && url.val() != "") {
                        if (!cmmCtrl.checkUrl(url)) {
                            return false;
                        }
                    }

                    // 첨부파일 필수 체크(input이 없기때문에 div의 class안 notRequired로 체크)
                    var isProcess = cmmCtrl.chkDropzone($formObj);
                    return isProcess;
                },
                async : {
                    use : true,
                    func : function () {
                        var actionUrl = !$.trim($formObj.find("input[name='detailsKey']").val()) ? "insert" : "update";
                        cmmCtrl.fileFrmAjax(callbackAjaxSave, actionUrl, $formObj, "json");
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