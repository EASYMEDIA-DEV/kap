define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKAFutureCarContestWirteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    console.log(ctrl)
    var $formObj = jQuery("#frmData");

    //설문지 html
    var examInitHtml = "";
    //설문지 순차 순번
    var examListSize = 0;

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
            btnExamWrite :{
                event :{
                    click: function(){
                        console.log("1");

                        console.log(examInitHtml);
                        ctrl.obj.append(examInitHtml);
                        // .examList의 마지막 자식 요소(.examQstnNm)를 선택하고 텍스트 설정
                        ctrl.obj.find(".examList").last().find(".examQstnNm").text("시상종류/참여자");

                        // <span> 요소 생성 및 추가
                        var starElement = $("<span/>", {
                            class: "star",
                            html: " *"
                        });

                        ctrl.obj.find(".examList").last().find(".examQstnNm").append(starElement);
                        console.log(starElement);
                    }
                }
            },
            btnExamDelete :{
                event :{
                    click: function(){
                        $(this).parents(".examList").remove();
                        ctrl.obj.find(".examList").each(function(index, row){
                            $(this).find(".examQstnNm").text("시상종류/참여구분");
                        })
                    }
                }
            },
        },
        immediately : function() {

            examInitHtml = ctrl.obj.find(".examHtmlTemplage").html();
            console.log(examInitHtml)
            ctrl.obj.find(".examHtmlTemplage").remove();
            if($.trim($("input[name=detailsKey]").val()) == "" || $(".examList").size()==0 ){
                var writeHtml = ctrl.obj.append(examInitHtml);
                var examQstnElement = writeHtml.find(".examQstnNm");
                // 텍스트 설정
                examQstnElement.text("시상종류/참여자");

                // <span> 요소 생성 및 추가
                var starElement = $("<span/>", {
                    class: "star",
                    html: " *"
                });

                examQstnElement.append(starElement);
                writeHtml.find(".btnExamDelete").remove();
            }
            ctrl.obj.show();

            examListSize = $(".examList").size();


            var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;
            var isAddNtfyCntn = false;
            jQuery("textarea[id^='addNtfyCntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 300,
                    readOnly : isAddNtfyCntn,
                });
            });

            $("#btn_delete").click(function () {

                var hasRegCli = $("#btn_delete").val();
                if(hasRegCli > 0){
                    alert("신청정보가 존재하여 삭제할 수 없습니다.");
                }else{
                    if (confirm(msgCtrl.getMsg("confirm.del"))) {
                        cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                    }
                }

            });

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

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
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        var WBFutureCarContestMstDTO = {};
                        WBFutureCarContestMstDTO.detailsKey = $("#detailsKey").val()
                        WBFutureCarContestMstDTO.bsnCd = $("#bsnCd").val()
                        WBFutureCarContestMstDTO.year = $("#year").val()
                        WBFutureCarContestMstDTO.episd = $("#episd").val()

                        WBFutureCarContestMstDTO.accsStrtDtm = $("#accsStrtDtm").val()
                        WBFutureCarContestMstDTO.accsEndDtm = $("#accsEndDtm").val()

                        WBFutureCarContestMstDTO.placeSeq = $("#placeSeq").val()

                        WBFutureCarContestMstDTO.bsnStrtDtm = $("#bsnStrtDtm").val()
                        WBFutureCarContestMstDTO.bsnEndDtm = $("#bsnEndDtm").val()

                        WBFutureCarContestMstDTO.expsYn = $("input[name=expsYn]:checked").val();

                        WBFutureCarContestMstDTO.addNtfyCntn = $("#addNtfyCntn").val()

                        WBFutureCarContestMstDTO.prizeList = new Array();

                        $(".prizeText").each(function(index, data){
                            var WBPrizeMstDTO = {};
                            WBPrizeMstDTO.wdcrmCd = $(this).find(".wdcrmCd").val();
                            WBPrizeMstDTO.ptcptType = $(this).find(".ptcptType").val();
                            WBPrizeMstDTO.prizePmt = $(this).find("input[name=prizePmt]").val();

                            WBFutureCarContestMstDTO.prizeList.push(WBPrizeMstDTO);
                        })
                        cmmCtrl.jsonAjax(function(data){
                            alert(actionMsg);
                            location.href = "./list";
                        }, actionUrl, WBFutureCarContestMstDTO, "text")
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