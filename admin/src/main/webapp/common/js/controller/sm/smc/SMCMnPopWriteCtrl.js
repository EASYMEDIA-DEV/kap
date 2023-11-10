define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smc/SMCMnPopWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    var callbackAjaxDelete = function(data){

        if (parseInt(data.respCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        }
        else
        {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };


    // set model
    ctrl.model = {
        id : {
            // do something...
            odtmYn : {
                event : {
                    change : function(){
                        var trgtObj = jQuery(this).closest("fieldset");

                        if (jQuery(this).is(":checked"))
                        {
                            jQuery(trgtObj).find(".datetimepicker_strtDt, .datetimepicker_endDt").addClass("notRequired").prop("disabled", true);
                            jQuery(".input-group").find("input").prop("disabled", true).val("");
                            jQuery(".input-group").siblings("select").prop("disabled", true).find("option:eq(0)").prop("selected", true);
                        }
                        else
                        {
                            jQuery(trgtObj).find(".datetimepicker_strtDt, .datetimepicker_endDt").removeClass("notRequired").prop("disabled", false);
                            jQuery(".input-group").find("input").prop("disabled", false);
                            jQuery(".input-group").siblings("select").prop("disabled", false);
                        }

                        jQuery(trgtObj).find(".datetimepicker_strtDt").datetimepicker("setOptions", { /* maxDate : false */ });
                        jQuery(trgtObj).find(".datetimepicker_strtDt").datetimepicker("reset").val("");

                        jQuery(trgtObj).find(".datetimepicker_endDt").datetimepicker("setOptions", { minDate : false });
                        jQuery(trgtObj).find(".datetimepicker_endDt").datetimepicker("reset").val("");
                    }
                }
            }
        },
        classname : {
            // do something...
            tagCd : {
                event : {
                    change : function(){
                        //var gubun = jQuery(this).val();
                        var tagCd =  $("input[name='tagCd']:checked").val();
                        if (tagCd == "img")
                        {
                            jQuery("#imgArea").show().find(".dropzone").removeClass("notRequired");
                            jQuery('#htmlArea').hide().find("textarea").removeClass("ckeditorRequired");
                        }
                        else if (tagCd == "html")
                        {
                            jQuery("#imgArea").hide().find(".dropzone").addClass("notRequired");
                            jQuery('#htmlArea').show().find("textarea").addClass("ckeditorRequired");
                        }
                    }
                }
            }
        },
        immediately : function(){

            var odtmYn =  $("input[name='odtmYn']:checked").val();
            if (odtmYn !== 'Y') {
                $(".datetimepicker_strtDt").val(new Date().toLocaleDateString().replace(/\./g, '').replace(/\s/g, '-'));
                $(".datetimepicker_endDt").val(new Date().toLocaleDateString().replace(/\./g, '').replace(/\s/g, '-'));
            }

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("imageExtns"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });

            /* Editor Setting */
            jQuery("textarea[id^='cntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                });
            });

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                }

            });

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true, editorChk = true;

                    jQuery(".dropzone").not(".notRequired").each(function(i){
                        if (jQuery(this).children(".dz-preview").length == 0)
                        {
                            alert(jQuery(this).data("titl") + "를 첨부해주세요.");
                            jQuery(this)[0].scrollIntoView();
                            isValid = false;
                            return false;
                        }
                    });

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1)
                        {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.sm.smc.html"));

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
                customfunc : function(obj, tagid, okval, msg){

                    if (tagid == "ptupEndMi")
                    {
                        var ptupStrtDtm = jQuery("#expsStrtDtm").val().replace(/-/gi, "");
                        var ptupEndDtm = jQuery("#expsEndDtm").val().replace(/-/gi, "");
                        var ptupStrtHh = jQuery("#ptupStrtHh").val();
                        var ptupStrtMi = jQuery("#ptupStrtMi").val();
                        var ptupEndHh = jQuery("#ptupEndHh").val();
                        var ptupEndMi = jQuery("#ptupEndMi").val();

                        ptupStrtDtm = parseInt(ptupStrtDtm + ptupStrtHh + ptupStrtMi);
                        ptupEndDtm = parseInt(ptupEndDtm + ptupEndHh + ptupEndMi);

                        if (ptupStrtDtm > ptupEndDtm)
                        {
                            alert("시작일이 종료일보다 이후일 수 없습니다");
                            jQuery(obj).focus();
                            return false;
                        }
                    }

                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        if($formObj.find(".dropzone.dz-started").size() > 0)
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

    // execute model
    ctrl.exec();

    return ctrl;
});

