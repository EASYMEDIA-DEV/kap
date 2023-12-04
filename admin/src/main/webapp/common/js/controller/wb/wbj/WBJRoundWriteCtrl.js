define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJRoundWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = jQuery("#frmData");

    //설문지 html
    var examInitHtml = "";
    //설문지 순차 순번
    var examListSize = 0;

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
                        ctrl.obj.append(examInitHtml);
                        // .examList의 마지막 자식 요소(.examQstnNm)를 선택하고 텍스트 설정
                        ctrl.obj.find(".examList").last().find(".examQstnNm").text("포상종류/포상금");

                        // <span> 요소 생성 및 추가
                        var starElement = $("<span/>", {
                            class: "star",
                            html: " *"
                        });

                        ctrl.obj.find(".examList").last().find(".examQstnNm").append(starElement);
                    }
                }
            },
            btnExamDelete :{
                event :{
                    click: function(){
                        $(this).parents(".examList").remove();
                        ctrl.obj.find(".examList").each(function(index, row){
                            $(this).find(".examQstnNm").text("포상종류/포상금");
                        })
                    }
                }
            },
        },
        immediately : function() {

            examInitHtml = ctrl.obj.find(".examHtmlTemplage").html();
            ctrl.obj.find(".examHtmlTemplage").remove();
            if($.trim($("input[name=detailsKey]").val()) == ""){
                var writeHtml = ctrl.obj.append(examInitHtml);
                var examQstnElement = writeHtml.find(".examQstnNm");
                // 텍스트 설정
                examQstnElement.text("포상종류/포상금");

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

            var isAddNtfyCntn = false;
            jQuery("textarea[id^='addNtfyCntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 300,
                    readOnly : isAddNtfyCntn,
                });
            });

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(function(respObj) {
                        if(respObj.optEpisdCnt == '0'){
                            cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                        }else{
                            alert("신청정보가 존재하여 삭제할 수 없습니다.");
                        }
                    }, "/mngwserc/wb/wbja/getRsumeCnt", $formObj, "post", "json")
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

                        var wbRoundMstDTO = {};
                        wbRoundMstDTO.detailsKey = $("#detailsKey").val()
                        wbRoundMstDTO.bsnCd = $("#bsnCd").val()
                        wbRoundMstDTO.year = $("#year").val()
                        wbRoundMstDTO.episd = $("#episd").val()

                        wbRoundMstDTO.accsStrtDtm = $("#accsStrtDtm").val()
                        wbRoundMstDTO.accsEndDtm = $("#accsEndDtm").val()

                        wbRoundMstDTO.placeSeq = $("#placeSeq").val()

                        wbRoundMstDTO.bsnStrtDtm = $("#bsnStrtDtm").val()
                        wbRoundMstDTO.bsnEndDtm = $("#bsnEndDtm").val()

                        wbRoundMstDTO.expsYn = $(":input:radio[name=expsYn]:checked").val();

                        wbRoundMstDTO.addNtfyCntn = $("#addNtfyCntn").val()

                        wbRoundMstDTO.prizeList = new Array();

                        $(".prizeText").each(function(index, data){
                            var wBOrderMstDto = {};
                            wBOrderMstDto.mrtsCd = $(this).find(".mrtsCd").val();
                            wBOrderMstDto.prizeCd = $(this).find(".prizeCd").val();
                            wBOrderMstDto.prizePmt = $(this).find("input[name=prizePmt]").val();

                            wbRoundMstDTO.prizeList.push(wBOrderMstDto);
                        })
                        var yearDtl = $("#yearDtl").val();
                        cmmCtrl.frmAjax(function(respObj) {
                            var episdCnt = respObj.optEpisdCnt[0];

                            if(actionUrl.indexOf('update') != -1 ){
                                if(episdCnt >= 1 && yearDtl != wbRoundMstDTO.year){
                                    alert("이미 등록된 회차입니다.");
                                }else{
                                    cmmCtrl.jsonAjax(function(data){
                                        alert(actionMsg);
                                        location.href = "./list";
                                    }, actionUrl, wbRoundMstDTO, "text")
                                }
                            }else{
                                if(episdCnt >= 1){
                                    alert("이미 등록된 회차입니다.");
                                }else{
                                    cmmCtrl.jsonAjax(function(data){
                                        alert(actionMsg);
                                        location.href = "./list";
                                    }, actionUrl, wbRoundMstDTO, "text")
                                }
                            }
                        }, "/mngwserc/wb/wbja/getEpisdCnt", $formObj, "post", "json")

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