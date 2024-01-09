define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbb/WBBARoundWriteCtrl"
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
                        if(confirm("선택한 게시물을 " + msgCtrl.getMsg("confirm.del"))){
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
                after : function() {
                    var chk = true;

                    jQuery.ajax({
                        url : "./episdChk",
                        type : "POST",
                        timeout: 30000,
                        data : $formObj.serializeArray(),
                        dataType : "json",
                        async: false,
                        cache : false,
                        success : function(data, status, xhr){
                            if(data.respCnt > 0 ){
                                alert("이미 등록된 회차가 존재합니다.");
                                chk = false;
                            }
                        }
                    });

                    return chk;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


                        var wbRoundMstDTO = {};
                        wbRoundMstDTO.detailsKey = ctrl.obj.find("#detailsKey").val();
                        wbRoundMstDTO.bsnCd = ctrl.obj.find("#bsnCd").val();
                        wbRoundMstDTO.year = ctrl.obj.find("#year").val();
                        wbRoundMstDTO.episd = ctrl.obj.find("#episd").val();
                        wbRoundMstDTO.accsStrtDtm = ctrl.obj.find("#accsStrtDtm").val() +" "+ctrl.obj.find("#accsStrtHour").val() +":00:00";
                        wbRoundMstDTO.accsEndDtm = ctrl.obj.find("#accsEndDtm").val() +" "+ctrl.obj.find("#accsEndHour").val()+":59:59";


                        wbRoundMstDTO.bsnStrtDtm = ctrl.obj.find("#bsnStrtDtm").val() +" "+ctrl.obj.find("#bsnStrtHour").val()+":00:00";
                        wbRoundMstDTO.bsnEndDtm = ctrl.obj.find("#bsnEndDtm").val() +" "+ctrl.obj.find("#bsnEndHour").val()+":59:59";

                        wbRoundMstDTO.expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();

                        cmmCtrl.jsonAjax(function(data){
                            alert(actionMsg);
                            location.href = "./list";
                        }, actionUrl, wbRoundMstDTO, "text")

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

