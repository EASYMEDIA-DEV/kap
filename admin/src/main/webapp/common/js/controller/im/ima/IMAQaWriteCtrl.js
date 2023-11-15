define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/im/ima/IMAQaWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    // set model
    ctrl.model = {
        id : {

        },
        classname : {

        },
        immediately : function() {

            /* File Dropzone Setting */
            /*$formObj.find(".dropzone").each(function(){
                console.log($formObj.find(".dropzone").length);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });*/
            $(".dropzone").each(function(){
                cmmCtrl.setDropzone($(this), {
                    maxFileCnt  : $(this).data("maxFileCnt"),
                    maxFileSize : $(this).data("maxFileSize"),
                    fileExtn    : $(this).data("fileExtn"),
                    fileFieldNm : $(this).data("fileFieldNm")
                })
            });

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true;

                    var cntsVal = jQuery("#rplyCntn").val().length;

                    if (cntsVal < 1) {
                    alert(msgCtrl.getMsg("fail.co.cog.cnts"));

                    jQuery("#rplyCntn").focus();

                    isValid = false;
                    }

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./insert";
                        var actionMsg = msgCtrl.getMsg("success.ins");
                        if($formObj.find(".dropzone").size() > 0)
                        {
                           cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                                actionUrl = "./list";
                            }, actionUrl, $formObj, "post", "json")
                        }
                    }
                },
                msg : {
                    empty : {
                        text : " 입력해주세요."
                    }
                }
            });

            //답변 완료 시 dropzone 셋팅
            $(document).ready(function () {
                if ($("#rsumeCd").val() == "ACK") {
                    /*$formObj.find(".dropzone").each(function () {
                        var trgtObj = $(this);

                        trgtObj.find('input[name="fileAlt"]').prop('readonly', true);

                        $('.dz-remove').hide();
                        $(".dz-default.dz-message").hide();
                    });*/

                    $(".dz-hidden-input").prop("disabled",true);
                    $(".dropzone").removeClass("dz-clickable");
                    $(".dz-default.dz-message").hide();

                    var targetNode = document.body;

                    // Mutation Observer 생성 및 콜백 함수 정의
                    var observer = new MutationObserver(function(mutations) {
                        mutations.forEach(function(mutation) {
                            // 노드가 추가되었을 때의 로직
                            if (mutation.type === 'childList' && mutation.addedNodes.length > 0) {
                                // 추가된 노드 중에서 원하는 요소를 찾음
                                mutation.addedNodes.forEach(function(addedNode) {
                                    if ($(addedNode).hasClass('dz-preview')) {
                                        // 새로운 드롭존 요소가 추가되었을 때의 동작 수행
                                        $('.dz-preview').find('input[name="fileAlt"]').prop('readonly', true);

                                        $('.dz-remove').hide();
                                    }
                                });
                            }
                        });
                    });
                    // 구성 옵션
                    var config = { childList: true, subtree: true };
                    // Mutation Observer를 대상 노드에 연결
                    observer.observe(targetNode, config);
                }
                else if($("#rsumeCd").val() != "ACK") {
                    $(".dz-hidden-input:first").prop("disabled",true);
                    $("#qaDrop .dropzone").removeClass("dz-clickable");
                    $("#qaDrop .dz-default.dz-message").hide();

                    // var targetNode = document.body;
                    var targetNode = document.getElementById("qaDrop");

                    // Mutation Observer 생성 및 콜백 함수 정의
                    var observer = new MutationObserver(function(mutations) {
                        mutations.forEach(function(mutation) {
                            // 노드가 추가되었을 때의 로직
                            if (mutation.type === 'childList' && mutation.addedNodes.length > 0) {
                                // 추가된 노드 중에서 원하는 요소를 찾음
                                mutation.addedNodes.forEach(function(addedNode) {
                                    if ($(addedNode).hasClass('dz-preview')) {
                                        // 새로운 드롭존 요소가 추가되었을 때의 동작 수행
                                        $('.dz-preview').find('input[name="fileAlt"]').prop('readonly', true);

                                        $('.dz-remove').hide();
                                    }
                                });
                            }
                        });
                    });
                    // 구성 옵션
                    var config = { childList: true, subtree: true };
                    // Mutation Observer를 대상 노드에 연결
                    observer.observe(targetNode, config);
                }
            });


        }
    };
    ctrl.exec();

    return ctrl;
});

