define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/EBEExamWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            btnSubmit : {
                event : {
                    click : function() {
                        $formObj.submit();
                    }
                }
            },


        },
        classname : {
            textAreaSbjctRply : {
                event : {
                    click : function() {
                        // maxLength 검사
                        if ($(this).is("[maxlength]"))
                        {
                            $(this).keyup(function(event){
                                var length = cmmCtrl.checkMaxlength(this);
                                $(this).parents(".form-textarea").find(".maxlengthText").text(length);
                            });
                        }
                    }
                }
            },
        },
        immediately : function(){
            // 유효성 검사
            $formObj.validation({
                before: function(){

                },
                after : function() {
                    var isValid = true

                    return isValid;
                },
                customfunc : function(obj, tagid, okval, msg){

                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./insert";
                        var actionMsg = msgCtrl.getMsg("success.exm");
                        //데이터 json화(form전송 복잡해서...)
                        var rspnObj               = new Object();
                        var mtlccList = new Array();
                        var sbjctList = new Array();
                        var ptcptSeq        = ctrl.obj.find("input[name=ptcptSeq]").val();
                        var examSeq         = ctrl.obj.find("input[name=examSeq]").val();
                        rspnObj.ptcptSeq = ptcptSeq;
                        rspnObj.examSeq = examSeq;
                        rspnObj.mtlccList = mtlccList;
                        rspnObj.sbjctList = sbjctList;
                        ctrl.obj.find(".survey-list").each(function(i, data){
                            var srvTypeCd = $(this).data("srvTypeCd");
                            var qstnSeq   = $(this).find("input[name=qstnSeq]").val();
                            if(srvTypeCd == "EXG_A" || srvTypeCd == "EXG_B"){
                                $(this).find("input[name^=answerOption]:checked").each(function(){
                                    //객관식
                                    var mtlccObj = new Object();
                                    mtlccObj.srvTypeCd = srvTypeCd;
                                    mtlccObj.ptcptSeq = ptcptSeq;
                                    mtlccObj.examSeq = examSeq;
                                    mtlccObj.qstnSeq = qstnSeq;
                                    mtlccObj.exmplSeq = $(this).val();
                                    mtlccList.push(mtlccObj);
                                });
                            }else{
                                //주관식
                                var sbjctObj = new Object();
                                sbjctObj.srvTypeCd = srvTypeCd;
                                sbjctObj.ptcptSeq = ptcptSeq;
                                sbjctObj.examSeq = examSeq;
                                sbjctObj.qstnSeq = qstnSeq;
                                if(srvTypeCd == "EXG_C"){
                                    sbjctObj.sbjctRply = $(this).find("input[name=sbjctRply]").val();
                                }else{
                                    sbjctObj.sbjctRply = $(this).find("textarea[name=sbjctRply]").val();
                                }
                                sbjctList.push(sbjctObj);
                            }
                        })
                        cmmCtrl.jsonAjax(function(data){
                            if(data.respCnt > 0){
                                alert(actionMsg);
                                location.href="/my-page/main";
                            }
                        }, actionUrl, rspnObj, "json")
                    }
                },
                msg : {
                    empty : {
                        text : "에 응답해주세요.",
                        radio : "에 응답해주세요.",
                        checkbox : "에 응답해주세요.",
                    },
                    confirm : {
                        init : "제출하시겠습니까?"
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

