define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/bm/bma/BMANewsWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var callbackAjaxInsert = function(data) {
        if (parseInt(data.respCnt, 10) == -999) {
            alert(msgCtrl.getMsg("fail.bm.bma.mainExps"));
            $formObj.find("input[name=mainExpsYn]").val("N").prop("checked", true)
        } else if (parseInt(data.respCnt, 10) > 0) {
            alert(msgCtrl.getMsg("success.ins"));
            location.href = "./list";
        } else {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    var callbackAjaxUpdate = function(data) {
        if (parseInt(data.respCnt, 10) > 0) {
            alert(msgCtrl.getMsg("success.upd"));
            location.href = "./list";
        } else {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    var callbackAjaxDelete = function(data) {
        alert(msgCtrl.getMsg("success.del.target.board"));
        location.href = "./list";
    };

    var callbackAjaxCount = function(data) {
        if(parseInt(data)>=5){
            alert(msgCtrl.getMsg("fail.bm.bma.mainExps"));
            $formObj.find("input[name=mainExpsYn]").val("N").prop("checked", true)
        }
    }

    var $formObj = jQuery("#frmData");

    ctrl.model = {
        id : {
            // do something...
            odtmYn : {
                event : {
                    click : function(){
                        var trgtObj = jQuery(this).closest("fieldset");

                        if($('#odtmYn').is(':checked')){
                            $("#postStrtDtm").val('');
                            $("#postStrtDtm").attr("disabled", true); //설정
                            $("#postStrtDtm").addClass("notRequired");

                            $("#postEndDtm").val('');
                            $("#postEndDtm").attr("disabled", true); //설정
                            $("#postEndDtm").addClass("notRequired");
                        }else {
                            $("#postStrtDtm").attr("disabled", false); //설정
                            $("#postStrtDtm").removeClass("notRequired");

                            $("#postEndDtm").attr("disabled", false); //설정
                            $("#postEndDtm").removeClass("notRequired");
                        }
                    }
                }
            },
            btnOneDelete : {
                event : {
                    click : function(){
                        var $formObj = jQuery("#frmData");

                        if (confirm(msgCtrl.getMsg("confirm.del"))) {
                            cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                        }
                    }
                }
            },
            btnList : {
                event : {
                    click : function() {
                        if (confirm(msgCtrl.getMsg("confirm.list"))) {
                            var strPam = jQuery("#frmData").find("input[name='strPam']").val();
                            location.href="./list?" + strPam;
                        }
                    }
                }
            }
        },
        classname : {
            mainExps : {
                event : {
                    click : function() {
                        cmmCtrl.frmAjax(callbackAjaxCount, "./count", $formObj);
                    }
                }
            }
        },
        immediately : function() {

            // 유효성 검사
            var $formObj = jQuery("#frmData");

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
                    empty : {
                        names : {
                            cntn : "내용을 등록하세요."
                        }
                    },
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
                before : function() {
                    //에디터 내용 바인딩
                    getDext5WebEditorContentsValue();
                    return true;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        if($formObj.find(".dropzone")[0].dropzone.files.length > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                } else if(data.respCnt == -999){
                                    alert(msgCtrl.getMsg("fail.bm.bma.mainExps"));
                                }
                            }, actionUrl, $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                } else if(data.respCnt == -999){
                                    alert(msgCtrl.getMsg("fail.bm.bma.mainExps"));
                                }
                            }, actionUrl, $formObj, "post", "json")
                        }
                    }
                }
            });
        }
    };


    ctrl.exec();

    return ctrl;
});