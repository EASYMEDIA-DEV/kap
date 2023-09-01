define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/co/COSampleWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    // set model
    ctrl.model = {
        id : {
            btn_delete : {
                event : {
                    click : function() {
                        //삭제
                        if(confirm(msgCtrl.getMsg("confirm.del"))){
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.del.target.none"));
                                    location.replace("./list");
                                }
                            }, "./delete", $formObj, "post", "json")
                        }
                    }
                }
            }
            , odtmYn : {
                event : {
                    click : function() {
                        //상시
                        var isCheck = $(this).is(":checked");
                        if(isCheck){
                            $formObj.find(".datetimepicker_strtDtm").prop("disabled", true);
                            $formObj.find(".datetimepicker_endDtm").prop("disabled", true);

                            $formObj.find(".datetimepicker_strtDtm").val("");
                            $formObj.find(".datetimepicker_endDtm").val("");
                        }
                        else
                        {
                            $formObj.find(".datetimepicker_strtDtm").prop("disabled", false);
                            $formObj.find(".datetimepicker_endDtm").prop("disabled", false);
                        }
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {

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
                after : function(){
                    //첨부파일 필수 체크(input이 없기때문에 div의 class안 notRequired로 체크)
                    var isProcess = cmmCtrl.chkDropzone( $formObj );
                    return isProcess;
                },
                before: function(){
                    //에디터 내용 바인딩
                    getDext5WebEditorContentsValue();
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "/mngwserc/co/insert" : "/mngwserc/co/update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "post", "json")
                        }
                    },
                    error: function(e){
                        console.log(e);
                    }
                }
            });

        }
    };

    ctrl.exec();

    return ctrl;
});

