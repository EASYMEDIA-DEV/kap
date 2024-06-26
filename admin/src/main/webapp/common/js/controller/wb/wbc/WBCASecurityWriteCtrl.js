define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbc/WBCASecurityWriteCtrl"
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
            }
        },
        classname : {
            insertLow : {
                event : {
                    click : function() {
                        //클릭한 로우 위치
                        var row = $(this).prev().val();
                        //전체 지급 차수
                        var giveLength = $(".give-row").length;

                        if(giveLength >= 30){
                            return;
                        }

                        let today = new Date();

                        let year = today.getFullYear(); // 년도
                        let month = today.getMonth() + 1;  // 월
                        let date = today.getDate();  // 날짜

                        var day = year+'-'+ (("00"+month.toString()).slice(-2))+"-"+(("00"+date.toString()).slice(-2));

                        //차수
                        var giveRow = giveLength+1;
                        var rowItem = '<div class="form-inline text-sm give-row">';
                        rowItem += '<label class="col-sm-1 control-label"></label>';
                        rowItem += '<div class="col-sm-11">';
                        rowItem += '<fieldset></fieldset>';
                        rowItem += '<div class="inline giveText">' +giveRow + '차수</div>';
                        rowItem += ' ';
                        rowItem += '<div class="form-group mr-sm">';
                        rowItem += '<div class="input-group">';
                        rowItem += '<input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveStrtDtList" value=\"'+day+'\" title="시작일" readonly onclick="cmmCtrl.initCalendar(this)"/>';
                        rowItem += '<span class="input-group-btn" style="z-index:0;">';
                        rowItem += '<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">';
                        rowItem += '<em class="ion-calendar"></em>';
                        rowItem += '</button>';
                        rowItem += '</span>';
                        rowItem += '<span class="input-group-addon bg-white b0">~</span>';
                        rowItem += '<input type="text" class="form-control input-sm datetimepicker_endDt" name="giveEndDtList" value=\"'+day+'\" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>';
                        rowItem += '<span class="input-group-btn" style="z-index:0;">';
                        rowItem += '<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">';
                        rowItem += '<em class="ion-calendar"></em>';
                        rowItem += '</button>';
                        rowItem += '</span>';
                        rowItem += '</div>';
                        rowItem += '</div>';
                        rowItem += '<input type="hidden" class="giveRowHid" name="giveOrd" value=\"'+ giveRow+'\">';
                        rowItem += ' ';
                        rowItem += '<button type="button" class="btn btn-info insertLow">+</button>';
                        rowItem += ' ';
                        rowItem += '<button type="button" class="btn btn-danger deleteLow">-</button>';
                        rowItem += '</div>';
                        rowItem += '</div>';


                        $('#rowInsert').append(rowItem);


                        $.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
                            jQuery(obj).datetimepicker({
                                timepicker : false,
                                format : "Y-m-d",
                                defaultDate : new Date(jQuery("body").data("curtDt")),
                                defaultTime : "00:00",
                                scrollInput : false,
                                scrollMonth : false,
                                scrollTime : false,
                                todayButton: false,
                                onSelectDate : function(selectedDate, selectedObj) {
                                    var strtDt   = selectedDate;
                                    var endDtObj = selectedObj.closest(".input-group").find(".datetimepicker_endDt");
                                    var endDt	 = new Date(endDtObj.val());

                                    if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
                                    {
                                        endDtObj.val(selectedObj.val());
                                    }

                                    endDtObj.datetimepicker("setOptions", { minDate : strtDt, value : endDtObj.val() });

                                    selectedObj.blur();
                                }
                            });
                        });

                        $.each(jQuery(".datetimepicker_endDt"), function(i, obj){
                            jQuery(obj).datetimepicker({
                                timepicker : false,
                                format : "Y-m-d",
                                defaultDate : new Date(jQuery("body").data("curtDt")),
                                defaultTime : "00:00",
                                scrollMonth : false,
                                scrollTime : false,
                                todayButton: false,
                                onSelectDate : function(selectedDate, selectedObj) {
                                    var strtDtObj = selectedObj.closest(".input-group").find(".datetimepicker_strtDt");
                                    var strtDt    = new Date(strtDtObj.val());
                                    var endDt     = selectedDate;

                                    if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
                                    {
                                        strtDtObj.val(selectedObj.val());
                                    }

                                    selectedObj.blur();
                                }
                            });
                        });


                    }
                }
            },
            deleteLow : {
                event : {
                    click : function() {
                        //클릭한 로우 위치
                        var row = $(this).prev().prev().val();

                        $(".give-row").eq(row - 1).remove();

                        for(var i =0 ; i < $(".giveText").length; i++){
                            $(".giveText").eq(i).html(i+1 +"차수");
                            $(".giveRowHid").eq(i).val(i+1);
                        }

                    }
                }
            }
        },
        immediately : function() {

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
                    
                    var chk = true;

                    jQuery.ajax({
                        url : "./episdChk",
                        type : "POST",
                        timeout: 30000,
                        data : $formObj.serializeArray(),
                        dataType : "json",
                        async: false,
                        cache : false,
                        success : function(data, status, xhr){
                            if(data.respCnt > 0 ){
                                alert("이미 등록된 회차가 존재합니다.");
                                chk = false;
                            }
                        }
                    });

                    return chk;
                },
                before : function (){
                },
                async : {
                    use : true,
                    func : function (){

                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        var expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();
                        var befeExpsYn = ctrl.obj.find("#befeExpsYn").val();

                        /*cmmCtrl.frmAjax(function(respObj){
                            if(respObj != undefined && respObj.respCnt > 0){
                                if (expsYn == befeExpsYn) {
                                    alert("신청정보가 존재하여 수정할 수 없습니다.");
                                    return;
                                }else{
                                    var wbRoundMstDTO = {};
                                    wbRoundMstDTO.detailsKey = ctrl.obj.find("#detailsKey").val();
                                    wbRoundMstDTO.bsnCd = ctrl.obj.find("#bsnCd").val();
                                    wbRoundMstDTO.expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();
                                    wbRoundMstDTO.giveList = new Array();

                                    cmmCtrl.jsonAjax(function(data){
                                        alert(actionMsg);
                                        location.href = "./list";
                                    }, actionUrl, wbRoundMstDTO, "text")
                                }
                            }else{*/
                                var wbRoundMstDTO = {};
                                wbRoundMstDTO.detailsKey = ctrl.obj.find("#detailsKey").val();
                                wbRoundMstDTO.bsnCd = ctrl.obj.find("#bsnCd").val();
                                wbRoundMstDTO.year = ctrl.obj.find("#year").val();
                                wbRoundMstDTO.episd = ctrl.obj.find("#episd").val();

                                wbRoundMstDTO.accsStrtDtm = ctrl.obj.find("#accsStrtDtm").val() +" "+ctrl.obj.find("#accsStrtHour").val() +":00:00";
                                wbRoundMstDTO.accsEndDtm = ctrl.obj.find("#accsEndDtm").val() +" "+ctrl.obj.find("#accsEndHour").val()+":00:00";

                                wbRoundMstDTO.bsnStrtDtm = ctrl.obj.find("#bsnStrtDtm").val() +" "+ctrl.obj.find("#bsnStrtHour").val()+":00:00";
                                wbRoundMstDTO.bsnEndDtm = ctrl.obj.find("#bsnEndDtm").val() +" "+ctrl.obj.find("#bsnEndHour").val()+":00:00";

                                wbRoundMstDTO.expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();

                                wbRoundMstDTO.giveList = new Array();

                                $(".giveText").each(function(index, data){
                                    var wBOrderMstDto = {};
                                    wBOrderMstDto.strtDt = $("input[name=giveStrtDtList]").eq(index).val() + ' 00:00:00';
                                    wBOrderMstDto.endDt = $("input[name=giveEndDtList]").eq(index).val() + ' 23:59:59';
                                    wBOrderMstDto.giveOrd = $("input[name=giveOrd]").eq(index).val();

                                    wbRoundMstDTO.giveList.push(wBOrderMstDto);
                                })


                                cmmCtrl.jsonAjax(function(data){
                                    alert(actionMsg);
                                    location.href = "./list";
                                }, actionUrl, wbRoundMstDTO, "text")
                            /*}
                        }, "./getAppctnCnt", $formObj, "POST", "json");*/

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
    ctrl.exec();

    return ctrl;
});

