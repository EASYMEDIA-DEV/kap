define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/sm/smc/SMCPopWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var callbackAjaxInsert = function(data) {
        if (parseInt(data.respCnt, 10) > 0) {
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

    var tab = function(tabName){
        if(tabName == 'html'){
            coEditorCtrl.eventGetEditors()
            $("#img1").attr("style", "display:none");
            $("#img2").attr("style", "display:none");
            $("#wnppYn").attr("style", "display:none");
            $("#url").attr("style", "display:none");
            $("#htmlTab").attr("style", "display:block");

            $("#img1").find(".dropzone").addClass("notRequired");
            $("#img2").find(".dropzone").addClass("notRequired");

            $('#htmlTab').find("textarea[name='cntn']").removeClass("notRequired");
        }else {
            $("#img1").attr("style", "display:block");
            $("#img2").attr("style", "display:block");
            $("#wnppYn").attr("style", "display:block");
            $("#url").attr("style", "display:block");
            $("#htmlTab").attr("style", "display:none");


            $("#img1").find(".dropzone").removeClass("notRequired");
            $("#img2").find(".dropzone").removeClass("notRequired");

            $('#htmlTab').find("textarea[name='cntn']").addClass("notRequired");
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
                        cmmCtrl.setPeriod(this, "", "", false);

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
            typeCd : {
                event : {
                    click : function() {
                        tab($(this).val());
                    }
                }
            }
        },
        immediately : function() {
            // 유효성 검사
            var $formObj = jQuery("#frmData");

            if($formObj.find("input[name='detailsKey']").val() != null){
                tab($formObj.find(".typeCd:checked").val())
            }else{
                tab('html')
            }

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
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
                        names :{
                            postStrtDtm : "게시시간을 선택하세요.",
                            postEndDtm : "게시시간을 선택하세요.",
                        },
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
                after : function(){
                    // URL 유효성 체크
                    var url = jQuery("input[name='url']");
                    if(url.val() != null && url.val() != undefined && url.val() != "") {
                        if (!cmmCtrl.checkUrl(url)) {
                            return false;
                        }
                    }
                    // 파일 등록 여부 확인
                    var isProcess = cmmCtrl.chkDropzone( $formObj );
                    return isProcess;
                },
                before : function() {
                    //에디터 내용 바인딩
                    if ($("input[name='typeCd']:checked").val() == 'html'){
                        getDext5WebEditorContentsValue();
                    }
                },
                async : {
                    use : true,
                    func : function (){
                        if (!$formObj.find("input[name='detailsKey']").val()) {
                            if($formObj.find("input[name='typeCd']:checked").val() == 'img'){
                                cmmCtrl.fileFrmAjax(callbackAjaxInsert, "./insert", $formObj, "json");
                            }else{
                                cmmCtrl.frmAjax(callbackAjaxInsert, "./insert", $formObj);
                            }
                        } else {
                            if($formObj.find("input[name='typeCd']:checked").val() == 'img'){
                                cmmCtrl.fileFrmAjax(callbackAjaxUpdate, "./update", $formObj, "json");
                            }else{
                                cmmCtrl.frmAjax(callbackAjaxUpdate, "./update", $formObj);
                            }
                        }
                    }
                }
            });
        }
    };


    ctrl.exec();

    return ctrl;
});