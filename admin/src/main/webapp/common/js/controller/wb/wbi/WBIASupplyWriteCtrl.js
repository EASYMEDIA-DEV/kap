define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbi/WBIASupplyWriteCtrl"
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
                            cmmCtrl.frmAjax(function(respObj) {
                                if(respObj.optEpisdCnt == '0'){
                                    cmmCtrl.frmAjax(function(data){
                                        if(data.respCnt > 0){
                                            alert(msgCtrl.getMsg("success.del.target.none"));
                                            location.replace("./list");
                                        }
                                    }, "./delete", $formObj, "post", "json")
                                }else{
                                    alert("신청정보가 존재하여 삭제할 수 없습니다.");
                                }
                            }, "/mngwserc/wb/wbia/getRsumeCnt", $formObj, "post", "json")
                        }
                    }
                }
            }
        },
        classname : {

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

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true, editorChk = true;

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


                        var wbRoundMstDTO = {};
                        wbRoundMstDTO.detailsKey = ctrl.obj.find("#detailsKey").val();
                        wbRoundMstDTO.bsnCd = ctrl.obj.find("#bsnCd").val();
                        wbRoundMstDTO.year = ctrl.obj.find("#year").val();
                        wbRoundMstDTO.episd = ctrl.obj.find("#episd").val();

                        wbRoundMstDTO.accsStrtDtm = ctrl.obj.find("#accsStrtDtm").val() +" "+ctrl.obj.find("#accsStrtHour").val() +":00:00";
                        wbRoundMstDTO.accsEndDtm = ctrl.obj.find("#accsEndDtm").val() +" "+ctrl.obj.find("#accsEndHour").val()+":00:00";

                        wbRoundMstDTO.bsnStrtDtm = ctrl.obj.find("#year").val() + "-01-01 00:00:00";
                        wbRoundMstDTO.bsnEndDtm = ctrl.obj.find("#year").val() +"-12-31 23:59:59 ";

                        wbRoundMstDTO.expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();

                        wbRoundMstDTO.giveList = new Array();

                        var bfreYear = $("#bfreYear").val();
                        var bfreExpsYn = $("#bfreExpsYn").val();
                        var bfreAccsStrtDtm = $("#bfreAccsStrtDtm").val() +" "+ctrl.obj.find("#accsStrtHour").val() +":00:00";
                        var bfreAccsEndDtm = $("#bfreAccsEndDtm").val() +" "+ctrl.obj.find("#accsEndHour").val()+":00:00";
                        var bfreEpisd = $("#bfreEpisd").val();
                        cmmCtrl.frmAjax(function(respObj) {
                            var episdCnt = respObj.optEpisdCnt[0];

                            if(actionUrl.indexOf('update') != -1 ){
                                if(episdCnt >= 1 && bfreYear != wbRoundMstDTO.year){
                                    alert("이미 등록된 회차입니다.");
                                }else{
                                    /*cmmCtrl.frmAjax(function(respObj) {
                                        if(respObj.optEpisdCnt == '0' ||
                                            (wbRoundMstDTO.expsYn != bfreExpsYn && bfreAccsStrtDtm == wbRoundMstDTO.accsStrtDtm && bfreAccsEndDtm == wbRoundMstDTO.accsEndDtm
                                                && bfreYear == wbRoundMstDTO.year && bfreEpisd == wbRoundMstDTO.episd) ){*/
                                            cmmCtrl.jsonAjax(function(data){
                                                var info = JSON.parse(data);
                                                if(info.respCnt > 0) {
                                                    alert(actionMsg);
                                                    location.href = "./list";
                                                }
                                                else {
                                                    alert("회차 정보 수정에 실패했습니다.");
                                                }
                                            }, actionUrl, wbRoundMstDTO, "text")
                                        /*}else{
                                            alert("신청정보가 존재하여 수정할 수 없습니다.");
                                        }
                                    }, "/mngwserc/wb/wbia/getRsumeCnt", $formObj, "post", "json")*/
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
                        }, "/mngwserc/wb/wbia/getEpisdCnt", $formObj, "post", "json")
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

