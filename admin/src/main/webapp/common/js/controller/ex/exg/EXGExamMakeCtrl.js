define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/ex/exg/EXGExamMakeCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    //설문지 html
    var examInitHtml = "";
    //설문지 순차 순번
    var examListSize = 0;
    ctrl.model = {
        id : {
            btnExamWrite :{
                event :{
                    click: function(){
                        ctrl.obj.append(examInitHtml);
                    }
                }
            }
        },
        classname : {
            btnExamWrite :{
                event :{
                    click: function(){
                        ctrl.obj.append(examInitHtml);
                        ctrl.obj.find(".examList").last().find(".examQstnNm").text("질문 " + (ctrl.obj.find(".examList").size()));
                        ctrl.obj.find(".examList").last().find("input[type=radio]").attr("name", "radio" + (++examListSize));
                    }
                }
            },
            btnExamDelete :{
                event :{
                    click: function(){
                        $(this).parents(".examList").remove();
                        ctrl.obj.find(".examList").each(function(index, row){
                            $(this).find(".examQstnNm").text("질문 " + (index+1));
                        })
                    }
                }
            },
            srvTypeCd :{
                event :{
                    change: function(){
                        var exmplContainerHtml = $(examInitHtml).find(".exmplContainer").html();
                        var trgtContainer = $(this).parents(".examList");
                        var srvTypeCdVal = $(this).val();
                        if(srvTypeCdVal == "EXG_A" || srvTypeCdVal == "EXG_B"){
                            trgtContainer.find(".exmplContainer").html(exmplContainerHtml);
                            trgtContainer.find(".checkbox").hide();
                            trgtContainer.find(".radio").hide();
                            if($(this).val() == "EXG_A"){
                                trgtContainer.find(".radio").show();
                            }else{
                                trgtContainer.find(".checkbox").show();
                            }
                        }else{
                            trgtContainer.find(".exmplContainer").html("");
                        }
                    }
                }
            },
            btnAddOptn :{
                event :{
                    click: function(){
                        var exmplOptnContainerHtml = $(examInitHtml).find(".exmplOptnContainer").html();
                        var srvTypeCdVal = $(this).parents(".examList").find(".srvTypeCd").val();
                        var trgtContainer = $(this).parents(".exmplOptnContainer");
                        trgtContainer.append(exmplOptnContainerHtml);
                        trgtContainer.find(".exmplOptnContainerList").last().find(".checkbox").hide();
                        trgtContainer.find(".exmplOptnContainerList").last().find(".radio").hide();
                        if(srvTypeCdVal == "EXG_A"){
                            trgtContainer.find(".exmplOptnContainerList").last().find(".radio").show();
                            var radioNum = parseInt(trgtContainer.find(".exmplOptnContainerList").first().find("input[type=radio]").attr("name").replace("radio", ""));
                            trgtContainer.find(".exmplOptnContainerList").last().find("input[type=radio]").attr("name", "radio" + radioNum);
                        }else{
                            trgtContainer.find(".exmplOptnContainerList").last().find(".checkbox").show();
                        }
                    }
                }
            },
            btnDeleteOptn :{
                event :{
                    click: function(){
                        if($(this).parents(".exmplContainer").find(".exmplOptnContainerList").size() == 1){
                            alert( msgCtrl.getMsg("fail.ex.deleteExamOptn") );
                        }else{
                            $(this).parents(".exmplOptnContainerList").remove();
                        }
                    }
                }
            },
        },
        immediately : function(){
            examInitHtml = ctrl.obj.html();
            ctrl.obj.html("");
            var writeHtml = ctrl.obj.append(examInitHtml);
            writeHtml.find(".examQstnNm").text("질문 " + $(".examList").size());
            writeHtml.find("input[type=radio]").attr("name", "radio" + (++examListSize));
            writeHtml.find(".btnExamDelete").remove();
            ctrl.obj.show();
        }
    };
    // execute model
    ctrl.exec();
    return ctrl;
});

