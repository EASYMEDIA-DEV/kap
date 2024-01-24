define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wba/WBAStepMakeCtrl"
    };

    var $formObj = jQuery("#frmData");
    var stepOptnContainerHtml;

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    //사업단계 html
    var stepInitHtml = "";
    //사업단계 순차 순번
    var stepListSize = 0;
    ctrl.model = {
        id : {
            btnExamWrite :{
                event :{
                    click: function(){
                        ctrl.obj.append(stepInitHtml);
                    }
                }
            }
        },
        classname : {
            btnStepWrite :{
                event :{
                    click: function(){
                        var stepLength = ctrl.obj.find(".stepList").length;

                        if (stepLength < 20) {
                            $(this).closest(".stepList").after(stepInitHtml);
                            ctrl.obj.find(".stepList").each(function(index, row){
                                $(this).find(".stepNm").text("단계 " + (index+1));
                                $(this).find('.stepName').empty().append('<input type="text" class="form-control input-s notRequired" name="stageNm" placeholder="단계명" title="단계명">');
                            })
                            /*ctrl.obj.find(".stepList").last().find(".stepNm").text("단계 " + (ctrl.obj.find(".stepList").size()));
                            ctrl.obj.find(".stepList").last().find('.stepName').empty().append('<input type="text" class="form-control input-s notRequired" name="stageNm" placeholder="단계명" title="단계명">');
                            ctrl.obj.find(".stepList").last().find('.fileBtn');
                            ctrl.obj.find(".stepList").last().find('.fileBtn')[0].dataset.filedsize = ctrl.obj.find(".stepList").size();*/


                            var trgtObj = $(this).closest(".stepList").next().find(".dropzone");

                            cmmCtrl.setDropzone(trgtObj, {
                                maxFileCnt  : trgtObj.data("maxFileCnt"),
                                maxFileSize : trgtObj.data("maxFileSize"),
                                fileExtn    : trgtObj.data("imageExtns"),
                            });
                        } else {
                            alert('단계는 최대 20개까지 등록가능합니다.');
                        }
                    }
                }
            },
            btnStepDelete :{
                event :{
                    click: function(){
                        if (confirm ('단계를 삭제하시겠습니까?')){
                            $(this).parents(".stepList").remove();
                            ctrl.obj.find(".stepList").each(function(index, row){
                                $(this).find(".stepNm").text("단계 " + (index+1));
                            })
                        };
                    }
                }
            },
            btnAddFile :{
                event :{
                    click: function(){
                        var fileNameContainerHtml = $(stepInitHtml).find(".fileNameContainer").html();
                        var trgtContainer = $(this).parents(".fileNameContainer");
                        trgtContainer.append(fileNameContainerHtml);
                    }
                }
            },
            btnDeleteFile :{
                event :{
                    click: function(){
                        if($(this).parents(".fileNameContainer").find(".fileNameContainerList").size() == 1){
                            alert( msgCtrl.getMsg("fail.wb.deleteFileName") );
                        }else{
                            $(this).parents(".fileNameContainerList").last().remove();
                        }
                    }
                }
            },
            fileBtn : {
                event : {
                    click: function() {

                        var fileCk = $(this).is(":checked");

                        if (fileCk) {
                            $(this).closest(".stepList").find('.fileListDepth').remove();

                            var trgtContainer = $(this).parents(".stepFileList");
                            trgtContainer.append(stepOptnContainerHtml);
                            $(this).closest(".stepList").find('.fileListDepth').find("input[name=optnNm]").prop('readonly',true);

                            var stepList = $(this).closest(".stepList");
                            stepList.find('.dropzone').css("pointer-events","none").css("background-color","#eee");

                        } else {

                            var stepList = $(this).closest(".stepList");
                            stepList.find('.dropzone').css("pointer-events","auto").css("background-color","");

                            $(this).closest(".stepList").find('.fileListDepth').find("input[name=optnNm]").prop('readonly',false);
                            var trgtObj = $(this).closest(".stepList").find(".fileListDepth").find(".dropzone");

                            cmmCtrl.setDropzone(trgtObj, {
                                maxFileCnt  : trgtObj.data("maxFileCnt"),
                                maxFileSize : trgtObj.data("maxFileSize"),
                                fileExtn    : trgtObj.data("imageExtns"),
                            });
                        }
                    }
                }
            },
            btnAddOptn :{
                event :{
                    click: function(){
                        var fileCk =  $(this).closest(".stepList").find("input[name=fileYn]").is(":checked");

                        if(!fileCk) {
                            var trgtContainer = $(this).parents(".stepFileList");
                            trgtContainer.append(stepOptnContainerHtml);

                            var trgtObj = $(this).parents(".stepFileList").find(".dropzone").last();

                            cmmCtrl.setDropzone(trgtObj, {
                                maxFileCnt  : trgtObj.data("maxFileCnt"),
                                maxFileSize : trgtObj.data("maxFileSize"),
                                fileExtn    : trgtObj.data("imageExtns"),
                            });
                        }
                    }
                }
            },
            btnDeleteOptn :{
                event :{
                    click: function(){
                        var fileCk =  $(this).closest(".stepList").find("input[name=fileYn]").is(":checked");
                        if(!fileCk) {
                            if ($(this).closest(".stepList").find(".fileListDepth").size() == 1) {
                                alert(msgCtrl.getMsg("fail.wb.deleteFileName"));
                            } else {
                                $(this).closest(".fileListDepth").remove();
                            }
                        }
                    }
                }
            },
        },
        immediately : function(){
            stepInitHtml = ctrl.obj.find(".stepInitHtml").html();
            ctrl.obj.find(".stepInitHtml").remove();
            if($.trim($("input[name=detailsKey]").val()) == ""){
                var writeHtml = ctrl.obj.append(stepInitHtml);
                writeHtml.find(".stepNm").text("단계 " + $(".stepList").size());
                writeHtml.find(".btnStepDelete").remove();
            }
            ctrl.obj.show();

            stepOptnContainerHtml = $(stepInitHtml).find(".fileDiv").html()
            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("imageExtns"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });

            stepListSize = $(".stepList").size();
        }
    };
    // execute model
    ctrl.exec();
    return ctrl;
});

