define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJRoundWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = jQuery("#frmData");

    //설문지 순차 순번
    var examListSize = 0;

    examListSize = $(".examList").size();

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
            room :{
                event :{
                    click: function(){
                        cmmCtrl.getEduRoomLayerPop(function(data){
                        console.log(data);

                            var seq = data.seq
                            var titl = data.titl // 장소명
                            var rgnsNm = data.rgnsNm // 지역
                            var bscAddr = data.addr; // 주소
                            var rprsntTelNo = data.rprsntTelNo; // 대표번호

                            $("input[name=placeSeq]").val(seq);

                            var tc = new Array(); // 배열 생성
                            tc.push({titl : titl, rgnsName : rgnsNm, bscAddr : bscAddr ,rprsntTelNo : rprsntTelNo }); // 배열 값 넣기

                            // 해당 지역 선택 시 테이블 td 그리기
                            var html = '';

                            for(var i = 0; i < 1; i++){
                                html += '<tr>';
                                html += '<td class=\"text-center\">'+tc[i].titl+'</td>';
                                html += '<td class=\"text-center\">'+tc[i].rgnsName+'</td>';
                                html += '<td class=\"text-center\">'+tc[i].bscAddr+'</td>';
                                html += '<td class=\"text-center\">'+tc[i].rprsntTelNo+'</td>';
                                html += '</tr>';
                            }

                            $("#listContainer").empty();
                            $("#listContainer").append(html);
                    });

                    }
                }
            },
            btnExamWrite :{
                event :{
                    click: function(){
                        examListSize++;

                        // examList를 복제
                        var clonedExamList = $(this).closest(".examList").clone();

                        // 내부의 입력 값 초기화
                        clonedExamList.find('input').val('');

                        // 내부의 라벨 내용을 지우기 (빈 문자열로 설정)
                        clonedExamList.find('.examQstnNm').text('');

                        // 복제한 examList를 examListContainer에 추가
                        $(".examListContainer").append(clonedExamList);
                    }
                }
            },
            btnExamDelete :{
                event :{
                    click: function(){
                        if(examListSize == 1){
                            alert("삭제할수없음");
                        }else{
                            $(this).parents(".examList").remove();

                            // examList를 복제
                            var clonedExamList = $(this).closest(".examList").clone();

                            // 내부의 입력 값 초기화
                            clonedExamList.find('select, input').val('');

                            // 내부의 라벨 내용을 지우기 (빈 문자열로 설정)
                            clonedExamList.find('.examQstnNm').text('');

                            examListSize--;
                        }
                    }
                }
            },
        },
        immediately : function() {
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

                    });

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

                        wbRoundMstDTO.accsStrtDtm = $("#accsStrtDtm").val() + " " + $("#accsStrtHour").val() +":00:00"
                        wbRoundMstDTO.accsEndDtm = $("#accsEndDtm").val() +" " + $("#accsEndHour").val()+":59:59";

                        wbRoundMstDTO.placeSeq = $("#placeSeq").val()

                        wbRoundMstDTO.bsnStrtDtm = $("#bsnStrtDtm").val() + " " + $("#bsnStrtHour").val()+":00:00";
                        wbRoundMstDTO.bsnEndDtm = $("#bsnEndDtm").val() + " " + $("#bsnEndHour").val()+":59:59";

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
                        var bfreYear = $("#bfreYear").val();
                        var bfreExpsYn = $("#bfreExpsYn").val();
                        var bfrePlaceSeq = $("#bfrePlaceSeq").val();
                        var bfreAddNtfyCntn = $("#bfreAddNtfyCntn").val();
                        var bfreBsnStrtDtm = $("#bfreBsnStrtDtm").val();
                        var bfreBsnEndDtm = $("#bfreBsnEndDtm").val();
                        var bfreAccsStrtDtm = $("#bfreAccsStrtDtm").val();
                        var bfreAccsEndDtm = $("#bfreAccsEndDtm").val();
                        cmmCtrl.frmAjax(function(respObj) {
                            var episdCnt = respObj.optEpisdCnt[0];
                            if(actionUrl.indexOf('update') != -1 ){
                                if(episdCnt >= 1 && bfreYear != wbRoundMstDTO.year){
                                    alert("이미 등록된 회차입니다.");
                                }else {
                                    cmmCtrl.frmAjax(function(respObj) {
                                        if (respObj.optEpisdCnt == '0' || (
                                            (bfreExpsYn != wbRoundMstDTO.expsYn || bfrePlaceSeq != wbRoundMstDTO.placeSeq || bfreAddNtfyCntn != wbRoundMstDTO.addNtfyCntn) &&
                                            bfreAccsStrtDtm == wbRoundMstDTO.accsStrtDtm && bfreAccsEndDtm == wbRoundMstDTO.accsEndDtm &&
                                            bfreBsnStrtDtm == wbRoundMstDTO.bsnStrtDtm && bfreBsnEndDtm == wbRoundMstDTO.accsEndDtm &&
                                            bfreYear == wbRoundMstDTO.year
                                        )) {
                                            cmmCtrl.jsonAjax(function (data) {
                                                alert(actionMsg);
                                                location.href = "./list";
                                            }, actionUrl, wbRoundMstDTO, "text");
                                        } else {
                                            alert("신청정보가 존재하여 수정할 수 없습니다.");
                                        }
                                    }, "/mngwserc/wb/wbja/getRsumeCnt", $formObj, "post", "json")
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