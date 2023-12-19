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
                        //var mdCd = jQuery(this).val();
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

                        // controller에 json으로 넘길 form값
                        var actForm = {};

                        var fileSeq = $("#fileSeq").val();
                        var detailsKey = $("#detailsKey").val();

                        var expsStrtDtm = $("#expsStrtDtm").val();
                        var ptupStrtHh = $("#ptupStrtHh").val();
                        var ptupStrtMi = $("#ptupStrtMi").val();
                        var expsEndDtm = $("#expsEndDtm").val();
                        var ptupEndHh = $("#ptupEndHh").val();
                        var ptupEndMi = $("#ptupEndMi").val();
                        var odtmYn = $("#odtmYn").val();
                        var tagCd = $("input[name='tagCd']:checked").val();
                        var titl = $("#titl").val();
                        var cntn = $("#cntn").val();
                        var urlUrl = $("#urlUrl").val();
                        var wnppYn = $("#wnppYn").val();
                        var expsYn = $("#expsYn").val();

                        actForm.fileSeq = fileSeq;
                        actForm.detailsKey = detailsKey;
                        actForm.expsStrtDtm = expsStrtDtm;
                        actForm.ptupStrtHh = ptupStrtHh;
                        actForm.ptupStrtMi = ptupStrtMi;
                        actForm.expsEndDtm = expsEndDtm;
                        actForm.ptupEndHh = ptupEndHh;
                        actForm.ptupEndMi = ptupEndMi;
                        actForm.odtmYn = odtmYn;
                        actForm.tagCd = tagCd;
                        actForm.titl = titl;
                        actForm.cntn = cntn;
                        actForm.urlUrl = urlUrl;
                        actForm.wnppYn = wnppYn;
                        actForm.expsYn = expsYn;

                        var mdCd =  $("input[name='mdCd']").val();
                        if (mdCd == 'pc') {
                            //PC 파일 세팅
                            var pcImageFileArray = new Array();
                            if(!($(".pcImageFile").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                                $(".pcImageFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                                $(".pcImageFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                                $.each($(".pcImageFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
                                    //alt값  data에 넣어주기.
                                    data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

                                    for (let i in data) {
                                        if (data.hasOwnProperty(i)) {
                                            var temp = {};
                                            temp.fileSeq = data.fileSeq;
                                            temp.status = data.status;
                                            temp.width = data.width;
                                            temp.height = data.height;
                                            temp.webPath = data.webPath;
                                            temp.fieldNm = "fileSeq";
                                            temp.orgnFileNm = data.orgnFileNm;
                                            temp.fileDsc = data.fileDsc;
                                            temp.fileOrd = data.fileOrd;

                                            if(pcImageFileArray == "" || (pcImageFileArray[pcImageFileArray.length-1].fileOrd != temp.fileOrd)){
                                                pcImageFileArray.push(temp);
                                            }
                                        }
                                    }
                                })
                            }
                            actForm.fileList = pcImageFileArray;
                        } else {
                            //모바일 파일 세팅
                            var mblImageFileArray = new Array();
                            if(!($(".mblImageFile").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                                $(".mblImageFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                                $(".mblImageFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                                $.each($(".mblImageFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
                                    //alt값  data에 넣어주기.
                                    data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

                                    for (let i in data) {
                                        if (data.hasOwnProperty(i)) {
                                            var temp = {};
                                            temp.fileSeq = data.fileSeq;
                                            temp.status = data.status;
                                            temp.width = data.width;
                                            temp.height = data.height;
                                            temp.webPath = data.webPath;
                                            temp.fieldNm = "fileSeq";
                                            temp.orgnFileNm = data.orgnFileNm;
                                            temp.fileDsc = data.fileDsc;
                                            temp.fileOrd = data.fileOrd;

                                            if(mblImageFileArray == "" || (mblImageFileArray[mblImageFileArray.length-1].fileOrd != temp.fileOrd)){
                                                mblImageFileArray.push(temp);
                                            }
                                        }
                                    }
                                })
                            }
                            actForm.fileList = mblImageFileArray;
                        }
                        console.log(actForm);

                        cmmCtrl.jsonAjax(function(data){
                            alert("저장되었습니다.");
                            location.href = "./list";
                        }, actionUrl, actForm, "text");
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

