define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smb/SMBMnVslWriteCtrl"
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
            },
            odtmYn : {
                event : {
                    click : function() {
                        var odtmYn = $("#odtmYn").val();
                        if(odtmYn == 'Y'){
                            $("#odtmYn").val('N');
                            $("input[name=strtDtm]").attr("disabled", false);
                            $("input[name=endDtm]").attr("disabled", false);
                            $("input[name=strtDtm]").attr("readonly", false);
                            $("input[name=endDtm]").attr("readonly", false);
                            $("input[name=strtDtm]").removeClass("notRequired");
                            $("input[name=endDtm]").removeClass("notRequired");
                            $("#odtmYn").addClass("notRequired");
                        }else if(odtmYn == 'N' || odtmYn == ''){
                            $("#odtmYn").val('Y');
                            $("input[name=strtDtm]").attr("disabled", true);
                            $("input[name=endDtm]").attr("disabled", true);
                            $("input[name=strtDtm]").attr("readonly", true);
                            $("input[name=endDtm]").attr("readonly", true);
                            $("input[name=strtDtm]").addClass("notRequired");
                            $("input[name=endDtm]").addClass("notRequired");
                            $("#odtmYn").removeClass("notRequired");
                        }
                    }
                }
            }
        },
        classname : {
            category : {
                event: {
                    click : function(){
                        var gubun = $("#gubun").val();
                        var category = $('input[name=category]:checked').val();
                        if(gubun == 'pc'){
                            if(category == 'image'){
                                $(".pcVideo").css("display", "none");
                                $(".pcImage").css("display", "block");
                            }else{
                                $(".pcImage").css("display", "none");
                                $(".pcVideo").css("display", "block");
                            }
                        }else if(gubun == 'mobile'){
                            if(category == 'image'){
                                $(".mobileVideo").css("display", "none");
                                $(".mobileImg").css("display", "block");
                            }else{
                                $(".mobileImg").css("display", "none");
                                $(".mobileVideo").css("display", "block");
                            }
                        }

                    }
                }
            }
        },
        immediately : function() {

            /* 이미지, 동영상 구분 */

            $('input[name=category]:checked').val();

            var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

            /* Editor Setting */
            jQuery("textarea[id^='cnts']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                    readOnly : _readOnly
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
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                console.log(data);
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }else if(data.respCnt == '-1'){
                                    alert(data.SMBMainVslDTO.respMsg);
                                }
                            }, actionUrl, $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                console.log(data);
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }else if(data.respCnt == '-1'){
                                    alert(data.respMsg);
                                }
                                actionUrl = "./list";
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

