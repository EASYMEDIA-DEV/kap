define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/ex/exg/EXGExamUserDtlCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {

        },
        classname : {

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
                        var actionUrl = "./sbjct-update";
                        var actionMsg = msgCtrl.getMsg("success.exm");
                        //데이터 json화(form전송 복잡해서...)
                        var rspnObj               = new Object();
                        var sbjctList = new Array();
                        var ptcptSeq        = ctrl.obj.find("input[name=ptcptSeq]").val();
                        var examSeq         = ctrl.obj.find("input[name=examSeq]").val();
                        var memSeq         = ctrl.obj.find("input[name=memSeq]").val();
                        rspnObj.ptcptSeq = ptcptSeq;
                        rspnObj.examSeq = examSeq;
                        rspnObj.memSeq = memSeq;
                        rspnObj.sbjctList = sbjctList;
                        ctrl.obj.find(".survey-list").each(function(i, data){
                            var srvTypeCd = $(this).data("srvTypeCd");
                            var qstnSeq   = $(this).find("input[name=qstnSeq]").val();
                            var scord   = $(this).find("input[name=scord]").val();
                            var qstnCanswYn   = $(this).find("select[name=qstnCanswYn]").val();
                            if(srvTypeCd == "EXG_A" || srvTypeCd == "EXG_B"){

                            }else{
                                //주관식
                                var sbjctObj = new Object();
                                sbjctObj.srvTypeCd = srvTypeCd;
                                sbjctObj.ptcptSeq = ptcptSeq;
                                sbjctObj.examSeq = examSeq;
                                sbjctObj.qstnSeq = qstnSeq;
                                sbjctObj.scord = scord;
                                sbjctObj.canswYn = qstnCanswYn;
                                console.log(sbjctObj)
                                sbjctList.push(sbjctObj);
                            }
                        })
                        cmmCtrl.jsonAjax(function(data){
                            ctrl.obj.trigger("choice", null);
                            $("#exgExamUserDtlLayer").modal("hide");
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
                        init : "저장하시겠습니까?"
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

