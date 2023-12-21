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

                        var tagCd = $("input[name='tagCd']:checked").val();
                        var mdCd = $("#mdCd").val();
                        var mainVslObj = {}
                            mainVslObj['_csrf'] = $("#csrfKey").val();
                            mainVslObj['mdCd'] = mdCd;
                            mainVslObj['detailsKey'] = $("#detailsKey").val();
                            mainVslObj['imgFileSeq'] = $("#imgFileSeq").val();
                            mainVslObj['videoFileSeq'] = $("#videoFileSeq").val();
                            var odtmYn = $("#odtmYn").val();
                            if(odtmYn != 'Y'){
                                mainVslObj['expsStrtDtm'] = $("#expsStrtDtm").val();
                                mainVslObj['ptupStrtHh'] = $("#ptupStrtHh").val();
                                mainVslObj['ptupStrtMi'] = $("#ptupStrtMi").val();
                                mainVslObj['expsEndDtm'] = $("#expsEndDtm").val();
                                mainVslObj['ptupEndHh'] = $("#ptupEndHh").val();
                                mainVslObj['ptupEndMi'] = $("#ptupEndMi").val();
                            }
                            mainVslObj['odtmYn'] = $("#odtmYn").val();
                            mainVslObj['titl'] = $("#titl").val();
                            mainVslObj['mnCopy'] = $("#mnCopy").val();
                            mainVslObj['mnHexCd'] = $("#mnHexCd").val();
                            mainVslObj['subCopy'] = $("#subCopy").val();
                            mainVslObj['subHexCd'] = $("#subHexCd").val();
                            mainVslObj['tagCd'] = tagCd;
                            mainVslObj['urlUrl'] =  $("#urlUrl").val();
                            mainVslObj['wnppYn'] = $("input[name='wnppYn']:checked").val();
                            mainVslObj['expsYn'] = $("input[name='expsYn']:checked").val();

                        if(mdCd == 'pc'){
                            if(tagCd == 'image'){
                                // pc 이미지
                                var fileArray = new Array();
                                if(!($(".pcImage").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                                    $(".pcImage").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                                    $(".pcImage").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                                    $.each($(".pcImage").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
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
                                                temp.fieldNm = data.fieldNm;
                                                temp.orgnFileNm = data.orgnFileNm;
                                                temp.fileDsc = data.fileDsc;
                                                temp.fileOrd = data.fileOrd;

                                                if(fileArray == "" || (fileArray[fileArray.length-1].fileOrd != temp.fileOrd)){
                                                    fileArray.push(temp);
                                                }

                                            }
                                        }

                                    })
                                }
                                mainVslObj.fileList = fileArray;
                            }else if(tagCd == 'video'){
                                //pc 동영상
                                var fileArray = new Array();
                                if(!($(".pcVideo").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                                    $(".pcVideo").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                                    $(".pcVideo").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                                    $.each($(".pcVideo").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
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
                                                temp.fieldNm = data.fieldNm;
                                                temp.orgnFileNm = data.orgnFileNm;
                                                temp.fileDsc = data.fileDsc;
                                                temp.fileOrd = data.fileOrd;

                                                if(fileArray == "" || (fileArray[fileArray.length-1].fileOrd != temp.fileOrd)){
                                                    fileArray.push(temp);
                                                }

                                            }
                                        }

                                    })
                                }
                                mainVslObj.fileList = fileArray;
                            }
                        }else if(mdCd == 'mobile'){
                            if(tagCd == 'image'){
                                // 모바일 이미지
                                var fileArray = new Array();
                                if(!($(".mobileImg").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                                    $(".mobileImg").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                                    $(".mobileImg").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                                    $.each($(".mobileImg").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
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
                                                temp.fieldNm = data.fieldNm;
                                                temp.orgnFileNm = data.orgnFileNm;
                                                temp.fileDsc = data.fileDsc;
                                                temp.fileOrd = data.fileOrd;

                                                if(fileArray == "" || (fileArray[fileArray.length-1].fileOrd != temp.fileOrd)){
                                                    fileArray.push(temp);
                                                }

                                            }
                                        }

                                    })
                                }
                                mainVslObj.fileList = fileArray;
                            }else if(tagCd == 'video'){
                                // 모바일 동영상
                                var fileArray = new Array();
                                if(!($(".mobileVideo").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                                    $(".mobileVideo").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                                    $(".mobileVideo").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                                    $.each($(".mobileVideo").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
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
                                                temp.fieldNm = data.fieldNm;
                                                temp.orgnFileNm = data.orgnFileNm;
                                                temp.fileDsc = data.fileDsc;
                                                temp.fileOrd = data.fileOrd;

                                                if(fileArray == "" || (fileArray[fileArray.length-1].fileOrd != temp.fileOrd)){
                                                    fileArray.push(temp);
                                                }

                                            }
                                        }

                                    })
                                }
                                mainVslObj.fileList = fileArray;
                            }
                        }
                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.jsonAjax(function(data){
                                var rtn = JSON.parse(data);
                                //콜백함수. 페이지 이동
                                if(rtn.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }else if(rtn.respCnt == '-1'){
                                    alert(rtn.respMsg);
                                }
                            }, actionUrl, mainVslObj, "text");
                        }
                        else
                        {
                            cmmCtrl.jsonAjax(function(data){
                                var rtn = JSON.parse(data);
                                if(rtn.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }else if(rtn.respCnt == '-1'){
                                    alert(rtn.respMsg);
                                }
                                actionUrl = "./list";
                            }, actionUrl, mainVslObj, "text")
                        }
                    }
                }
            });
        }
    };
    ctrl.exec();

    return ctrl;
});

