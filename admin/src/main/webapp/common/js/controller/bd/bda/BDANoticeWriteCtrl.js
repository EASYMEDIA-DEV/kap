define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bda/BDANoticeWriteCtrl"
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
            },
            odtmYn : {
                event : {
                    change : function() {
                        // var odtmYn = $("#odtmYn").val();
                        // if(odtmYn == 'Y'){
                        if(jQuery("#odtmYn").is(':checked')) {
                            $("#odtmYn").val('Y');
                            $("#expsStrtDtm").attr("disabled", true);
                            $("#expsEndDtm").attr("disabled", true);
                            $("#expsStrtDtm").addClass("notRequired");
                            $("#expsEndDtm").addClass("notRequired");
                            $("#expsStrtDtm").val("");
                            $("#expsEndDtm").val("");
                            $("#odtmYn").removeClass("notRequired");
                        }
                        // }else if(odtmYn == 'N' || odtmYn == ''){
                        else {
                            $("#odtmYn").val('N');
                            $("#expsStrtDtm").attr("disabled", false);
                            $("#expsEndDtm").attr("disabled", false);
                            $("#expsStrtDtm").removeClass("notRequired");
                            $("#expsEndDtm").removeClass("notRequired");
                            $("#odtmYn").addClass("notRequired");
                        }
                    }
                }
            },
            ntfyYn : {
                event : {
                    change : function () {
                        if(jQuery("#ntfyYn").is(':checked')) {
                            jQuery("#ntfyYn").val("Y");
                            jQuery("#ntfyYnDate").css("display", "block")
                            jQuery("#expsStrtDtm").removeClass("notRequired");
                            jQuery("#expsEndDtm").removeClass("notRequired");
                        }
                        else {
                            jQuery("#ntfyYn").val("N");
                            jQuery("#ntfyYnDate").css("display", "none")
                            jQuery("#expsStrtDtm").addClass("notRequired");
                            jQuery("#expsEndDtm").addClass("notRequired");
                        }
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {

            /* Editor Setting */
            jQuery("textarea[id^='cntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400
                });
            });

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
                    var isValid = true, editorChk = true;

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
                        jQuery(this).val(jQuery(this).val().split("<").join("~!left!~"));
                        jQuery(this).val(jQuery(this).val().split(">").join("~!right!~"));
                        jQuery(this).val(jQuery(this).val().split("\'").join("~!singlecomma!~"));
                        jQuery(this).val(jQuery(this).val().split("\"").join("~!doublecomma!~"));

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1)
                        {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.co.cog.cnts"));

                            CKEDITOR.instances[jQuery(this).prop("id")].focus();

                            // 에디터 최상단으로 스크롤 이동
                            jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

                            return false;
                        }
                    });

                    if (!editorChk)
                    {
                        isValid = false;
                    }

                    return isValid;
                },
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
                                actionUrl = "./list";
                            }, actionUrl, $formObj, "post", "json")
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

