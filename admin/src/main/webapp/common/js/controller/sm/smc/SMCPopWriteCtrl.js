define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smc/SMCPopWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    var callbackAjaxInsert = function(data){
        if (parseInt(data.actCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.ins"));
            location.href = "./list";
        }
        else
        {
            if(data.excessCntYn == "Y"){
                alert(msgCtrl.getMsg("fail.sm.smb.insert"));
            }else{
                alert(msgCtrl.getMsg("fail.act"));
            }
        }
    };

    var callbackAjaxUpdate = function(data){
        if (parseInt(data.actCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.upd"));
            location.href = "./list";
        }
        else
        {
            if(data.excessCntYn == "Y"){
                alert(msgCtrl.getMsg("fail.sm.smb.insert"));
            }else{
                alert(msgCtrl.getMsg("fail.act"));
            }
        }
    };

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
                            jQuery(trgtObj).find(".datetimepicker_strtDtm, .datetimepicker_endDtm").addClass("notRequired").prop("disabled", true);
                        }
                        else
                        {
                            jQuery(trgtObj).find(".datetimepicker_strtDtm, .datetimepicker_endDtm").removeClass("notRequired").prop("disabled", false);
                        }

                        jQuery(trgtObj).find(".datetimepicker_strtDtm").datetimepicker("setOptions", { /* maxDate : false */ });
                        jQuery(trgtObj).find(".datetimepicker_strtDtm").datetimepicker("reset").val("");

                        jQuery(trgtObj).find(".datetimepicker_endDtm").datetimepicker("setOptions", { minDate : false });
                        jQuery(trgtObj).find(".datetimepicker_endDtm").datetimepicker("reset").val("");
                    }
                }
            }
        },
        classname : {
            // do something...
            typeCd : {
                event : {
                    change : function(){
                        //var gubun = jQuery(this).val();
                        var typeCd =  $("input[name='typeCd']:checked").val();
                        if (typeCd == "image")
                        {

                            ctrl.obj.find(".linkUrlContainer").show();
                            ctrl.obj.find(".linkUrlContainer").find("input[type=text]").val("");
                            jQuery("#imageArea").show().find(".dropzone").removeClass("notRequired");
                            jQuery('#htmlArea').hide().find("textarea").removeClass("ckeditorRequired");
                        }
                        else if (typeCd == "html")
                        {
                            ctrl.obj.find(".linkUrlContainer").hide();
                            ctrl.obj.find(".linkUrlContainer").find("input[type=text]").val("");

                            jQuery("#imageArea").hide().find(".dropzone").addClass("notRequired");
                            jQuery('#htmlArea').show().find("textarea").addClass("ckeditorRequired");
                        }
                    }
                }
            }
        },
        immediately : function(){
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

            /* Editor Setting */
            jQuery("textarea[id^='cnts']").each(function(){
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
                            alert(jQuery(this).data("titl") + "를 등록해주세요.");
                            jQuery(this)[0].scrollIntoView();
                            isValid = false;
                            return false;
                        }
                    });

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

                            alert(msgCtrl.getMsg("fail.noCntn"));

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
                }
                ,
                async : {
                    use : true,
                    func : function(){
                        if (!$formObj.find("input[name='detailsKey']").val())
                        {
                            cmmCtrl.frmAjax(callbackAjaxInsert, "./insert", $formObj);
                        }
                        else
                        {
                            cmmCtrl.frmAjax(callbackAjaxUpdate, "./update", $formObj);
                        }
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

