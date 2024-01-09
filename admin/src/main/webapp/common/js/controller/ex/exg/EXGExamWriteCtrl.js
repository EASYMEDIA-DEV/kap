define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/ex/exg/EXGExamWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    //과정분류 조회
    var selectCtgryCdList = function(arg){

        if($(arg).val() == "" || $(arg).val() === undefined){
            arg = $("#ctgryCd").data("ctgrycd");
        }

        // if(arg === undefined){
        //     arg = $("#ctgryCd").data("ctgrycd");
        // }

        var cdMst= {};
        cdMst.cd = $(arg).val();

        cmmCtrl.jsonAjax(function(data){
            callbackAjaxCtgryCdList(data);
        }, './classTypeList', cdMst, "text");
    }

    //과정분류 2뎁스 세팅
    var callbackAjaxCtgryCdList = function(data){

        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>선택</option>";

        for(var i =0; i < detailList.length; i++){

            var cd = detailList[i].cd;
            var cdNm = detailList[i].cdNm;

            selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
        }

        $("#ctgryCd option").remove();

        $("#ctgryCd").append(selectHtml);

        var ctgrycd = $("#ctgryCd").data("ctgrycd");

        if($('#ctgryCd option[value="'+ctgrycd+'"]').length > 0){
            $("#ctgryCd").val(ctgrycd).prop("selected", true);//조회된 과정분류값 자동선택
        }else{
            $("#ctgryCd").val("").prop("selected", true);//조회된 과정분류값 자동선택

        }

    }


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

        },
        classname : {

            classType : {
                event : {
                    change : function() {
                        selectCtgryCdList(this);
                    }
                }
            },

        },
        immediately : function(){

            $(".classType").trigger("change");

            /* Editor Setting */
            var isSmmryCntn = false;
            if($.trim($("input[name=posbChg]").val()) == "false"){
                isSmmryCntn = true;

                //수정가능여부가 false일경우 과정분류, 과정명, 제목, 시험개요 딤드처리
                $("#cd").prop("disabled", true);//과정-중분류
                $("#ctgryCd").prop("disabled", true);//과정-중분류
                $("#nm").prop("disabled", true);
                $("#titl").prop("disabled", true);
            }








            jQuery("textarea[id^='smmryCntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 300,
                    readOnly : isSmmryCntn,
                });
            });

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                }

            });

            // 유효성 검사
            $formObj.validation({
                before: function(){
                    //에디터 데이터 textare 입력
                    $formObj.find(".ckeditorRequired").each(function() {
                        $(this).val(CKEDITOR.instances[$(this).attr("id")].getData());
                    });
                },
                after : function() {
                    var isValid = true, editorChk = true, isInputType = false;

                    //질문 응답 정답여부 확인
                    $(".examListContainer").find(".examList").each(function(index, data){
                        isInputType = false;
                        var srvTypeCdVal = $(this).find(".srvTypeCd").val();
                        if(srvTypeCdVal == "EXG_A"){
                            $(this).find("input[type=radio]").each(function(indexType, dataType){
                                if($(this).is(":checked")){
                                    isInputType = true;
                                    return false;
                                }
                            })
                        }else if(srvTypeCdVal == "EXG_B"){
                            $(this).find("input[type=checkbox]").each(function(indexType, dataType){
                                if($(this).is(":checked")){
                                    isInputType = true;
                                    return false;
                                }
                            })
                        }else{
                            isInputType = true;
                        }

                        if(!isInputType){
                            alert(msgCtrl.getMsg("fail.ex.notExmOptn"));
                            isValid = false;
                            return false;
                        }
                    })
                    //배점 검증
                    var totScord = 0;
                    $("input[name=scord]").each(function(index, data){
                        totScord = totScord + Number($(this).val());
                    })
                    if(totScord > 100){
                        alert(msgCtrl.getMsg("fail.ex.notScord100"));
                        isValid = false;
                        return false;
                    }
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

                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        //데이터 json화(form전송 복잡해서...)
                        var exExamMst = {};
                        exExamMst.detailsKey = ctrl.obj.find("#detailsKey").val();
                        exExamMst.titl = ctrl.obj.find("#titl").val();

                        exExamMst.nm = ctrl.obj.find("#nm").val();
                        exExamMst.ctgryCd = ctrl.obj.find("#ctgryCd").val();


                        exExamMst.smmryCntn = ctrl.obj.find("#smmryCntn").val();
                        exExamMst.expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();
                        exExamMst.exExamQstnDtlList = new Array();

                        //질문 모든 갯수
                        exExamMst.qstnSize = $(".examList").size();
                        //보기 모든 갯수
                        exExamMst.exmplSize = $(".exmplOptnContainerList").size();
                        $(".examListContainer").find(".examList").each(function(index, data){
                            var exExamQstnDt = {};
                            exExamQstnDt.srvTypeCd = $(this).find(".srvTypeCd").val();
                            exExamQstnDt.qstnNm = $(this).find("input[name=qstnNm]").val();
                            exExamQstnDt.scord = $(this).find("input[name=scord]").val();
                            exExamQstnDt.qstnOrd = index;
                            exExamMst.qstnSize = exExamMst.qstnSize;
                            exExamQstnDt.exExamExmplDtlList = new Array();
                            $(this).find(".exmplOptnContainerList").each(function(exmplIndex, exmplData){
                                var exExamExmplDtl = {};
                                exExamMst.exmplSize = exExamMst.exmplSize + 1;
                                exExamExmplDtl.exmplNm = $(this).find("input[name=exmplNm]").val();
                                exExamExmplDtl.exmplOrd = exmplIndex;
                                //설문유형
                                if(exExamQstnDt.srvTypeCd == "EXG_A"){
                                    exExamExmplDtl.canswYn = $(this).find("input[type=radio]").is(":checked") ? "Y" : "N";
                                }else{
                                    exExamExmplDtl.canswYn = $(this).find("input[type=checkbox]").is(":checked") ? "Y" : "N";
                                }
                                exExamQstnDt.exExamExmplDtlList.push(exExamExmplDtl);
                            })
                            exExamMst.exExamQstnDtlList.push(exExamQstnDt);
                        })
                        cmmCtrl.jsonAjax(function(data){
                            location.href = "./list";
                        }, actionUrl, exExamMst, "text")
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

