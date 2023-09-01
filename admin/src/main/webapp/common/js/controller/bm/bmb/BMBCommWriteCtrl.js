define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bm/bmb/BMBCommWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    var tab = function(tabName){
        if(tabName == 'youtube'){
            $("#linkDiv").attr("style", "display:none");
            $("#snsDiv").attr("style", "display:none");
            $("#imgField").attr("style", "display:none");
            $("#youtubeDiv").attr("style", "display:block");

            $("#imgField").find(".dropzone").addClass("notRequired");
            $("#linkDiv").find("input[name='link']").addClass("notRequired");

            $("#snsDiv").find("textarea[name='dextCntn']").addClass("notRequired");

            $('#youtubeDiv').find("input[name='url']").removeClass("notRequired")
        } else if(tabName == 'link'){
            $("#youtubeDiv").attr("style", "display:none");
            $("#snsDiv").attr("style", "display:none");
            $("#linkDiv").attr("style", "display:block");
            $("#imgField").attr("style", "display:block");


            $("#youtubeDiv").find("input[name='url']").addClass("notRequired");
            // $("#snsDiv").find(".dropzone").addClass("notRequired");
            $("#snsDiv").find("textarea[name='dextCntn']").addClass("notRequired");

            $("#imgField").find(".dropzone").removeClass("notRequired")
            $("#linkDiv").find("input[name='link']").removeClass("notRequired");
        }else {
            coEditorCtrl.eventGetEditors()
            $("#youtubeDiv").attr("style", "display:none");
            $("#linkDiv").attr("style", "display:none");
            $("#snsDiv").attr("style", "display:block");
            $("#imgField").attr("style", "display:block");

            $("#youtubeDiv").find("input[name='url']").addClass("notRequired");
            $("#linkDiv").find(".dropzone").addClass("notRequired");
            $("#linkDiv").find("input[name='link']").addClass("notRequired");

            $("#imgField").find(".dropzone").removeClass("notRequired");
            $("#snsDiv").find("textarea[name='dextCntn']").removeClass("notRequired");
        }
    }
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
                                    alert(msgCtrl.getMsg("success.del.target.board"));
                                    location.replace("./list");
                                }
                            }, "./delete", $formObj, "post", "json")
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
            if($formObj.find("input[name='detailsKey']").val() != null){
                tab($formObj.find(".typeCd:checked").val())
            }else{
                tab('link')
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
            // 유효성 검사
            $formObj.validation({
                msg : {
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
                    if($formObj.find(".typeCd:checked").val() == "link"){
                        // URL 유효성 체크
                        if(!cmmCtrl.checkUrl(jQuery("#link"))) {
                            return false;
                        }
                    }

                    //첨부파일 필수 체크(input이 없기때문에 div의 class안 notRequired로 체크)
                    var isProcess = cmmCtrl.chkDropzone( $formObj );
                    return isProcess;
                },
                before: function(){
                    //에디터 내용 바인딩
                    if ($("input[name='typeCd']:checked").val() == 'sns'){
                        getDext5WebEditorContentsValue();
                    }
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        var typeCd = $formObj.find(".typeCd:checked").val()

                        if(typeCd == "link" || typeCd == "sns")
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

