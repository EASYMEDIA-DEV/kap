define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smj/SMJFormWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");


    var tabOne = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
        }, "/mngwserc/sm/smj/select-tab-one", $formObj, "POST", "html",'',false);
    }
    var tabTwo = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
        }, "/mngwserc/sm/smj/write", $formObj, "POST", "html",'',false);
    }
    // set model
    ctrl.model = {
        id : {
            consultTab : {
                event : {
                    click : function () {
                        $formObj.find("#typeCd").val("BUSINESS01");
                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            },
            winBusinessTab : {
                event : {
                    click : function () {
                        $formObj.find("#typeCd").val("BUSINESS02");
                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

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
            // 유효성 검사
            $formObj.validation({
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./write" + $formObj.serialize());
                                }
                                location.replace("./write?" + $formObj.serialize());
                            }, actionUrl, $formObj, "json");
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
    ctrl.exec();

    return ctrl;
});

