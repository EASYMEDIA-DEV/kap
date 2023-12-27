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

    // function
    function removeFIle(fileCnt) {
        for(var i = 0; i < fileCnt; i++) {
            $(".dz-remove")[i].click();
        }
    }

    var tagCdEvent = function(){

        var mdCd = $("#mdCd").val();
        var tagCd = $('input[name=tagCd]:checked').val();
        var uploadFileCnt = $(".dz-remove").length;

        if(mdCd == 'pc'){
            if(tagCd == 'image'){
                $(".pcVideo").css("display", "none");
                $(".pcImage").css("display", "block");
                $(this).prop("checked", "true")
            }else{
                $(".pcImage").css("display", "none");
                $(".pcVideo").css("display", "block");
                $(this).prop("checked", "true")
            }
        }else if(mdCd == 'mobile'){
            if(tagCd == 'image'){
                $(".mobileVideo").css("display", "none");
                $(".mobileImg").css("display", "block");
            }else{
                $(".mobileImg").css("display", "none");
                $(".mobileVideo").css("display", "block");
            }
        }

    }
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
                    click : function() {
                        var odtmYn = $("#odtmYn").val();
                        if(odtmYn == 'Y'){
                            $("#odtmYn").val('N');
                            $("input[name=expsStrtDtm]").attr("disabled", false);
                            $("input[name=expsEndDtm]").attr("disabled", false);
                            // $("input[name=expsStrtDtm]").attr("readonly", false);
                            // $("input[name=expsEndDtm]").attr("readonly", false);
                            $("input[name=expsStrtDtm]").removeClass("notRequired");
                            $("input[name=expsEndDtm]").removeClass("notRequired");
                            $("#odtmYn").addClass("notRequired");
                        }else if(odtmYn == 'N' || odtmYn == ''){
                            $("#odtmYn").val('Y');
                            $("input[name=expsStrtDtm]").attr("disabled", true);
                            $("input[name=expsEndDtm]").attr("disabled", true);
                            // $("input[name=expsStrtDtm]").attr("readonly", true);
                            // $("input[name=expsEndDtm]").attr("readonly", true);
                            $("input[name=expsStrtDtm]").addClass("notRequired");
                            $("input[name=expsEndDtm]").addClass("notRequired");
                            $("#odtmYn").removeClass("notRequired");
                        }
                    }
                }
            },
            mnHexCd : {
                event : {
                    change : function(){
                        var regex = /^[ㄱ-ㅎ|가-힣|\s|]+$/;
                        var mnHexCd = $("#mnHexCd").val();
                        $("#mnHexCd").val(mnHexCd.replace(" ",""));
                        if(regex.test(mnHexCd)){
                            $("#mnHexCd").val('');
                        }
                    }
                }
            },
            subHexCd: {
                event : {
                    change : function(){
                        var regex = /^[ㄱ-ㅎ|가-힣|\s|]+$/;
                        var subHexCd = $("#subHexCd").val();
                        $("#subHexCd").val(subHexCd.replace(" ",""));
                        if(regex.test(subHexCd)){
                            $("#subHexCd").val('');
                        }
                    }
                }
            }
        },
        classname : {
            tagCd : {
                event: {
                    click : function(){

                        tagCdEvent();
                    }
                }
            }
        },
        immediately : function() {

            /* 이미지, 동영상 구분 */

            tagCdEvent();
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

                    if($(".dropzone .dz-preview").length < 1) {
                        var tagCd = $('input[name=tagCd]:checked').val();
                        if(tagCd == 'image'){
                            alert("이미지를 첨부해주세요");
                            isValid = false;
                        }else{
                            alert("동영상을 첨부해주세요");
                            isValid = false;
                        }

                    }

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
                            console.log($formObj);
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }else if(data.respCnt == '-1'){
                                    alert(data.SMBMainVslDTO.respMsg);
                                    location.reload();
                                }
                            }, actionUrl, $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }else if(data.respCnt == '-1'){
                                    alert(data.respMsg);
                                    location.reload();
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

