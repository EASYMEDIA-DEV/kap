define(["ezCtrl", "ezVald", "ezFile"], function(ezCtrl, ezVald, ezFile) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPESqCertiAppliyCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
            // 페이징 처리
            submitBtn : {
                event : {
                    click : function() {
                        $formObj.submit();
                    }
                }
            },
        },
        classname : {
            fileRequired :{
                event : {
                    change : function() {
                        console.log("1111")
                    }
                }
            }
        },
        immediately : function() {
            //모달 열릴때
            ctrl.obj.on("onModalOpen", function(data){
                //파일 초기화
                $formObj.find("input[type=file]").fileInit();
                ctrl.obj.find(".file-list-area .empty-txt").text("");
            })
            //파일 설정
            $formObj.find("input[type=file]").fileUpload({
                loading:true,
                sync:true
            },function(data){
                //해당 input file 객체에 data(tempFileData) 응답 값이 저장
                if(data != undefined && data.length > 0){
                    if(ctrl.obj.find(".idntfnPhotoFileSeqImage").size() > 0)
                    {
                        //이미지 보여주기
                        ctrl.obj.find(".idntfnPhotoFileSeqImage").prop("src", data[0].webPath)
                        ctrl.obj.find(".idntfnPhotoFileSeqImage").data("src", data[0].webPath)
                    }
                    else
                    {
                        var fileLen = data[0].orgnFileNm.length;
                        var extn    = data[0].orgnFileNm.lastIndexOf(".");
                        ctrl.obj.find(".file-list-area .file-name .name").text(data[0].orgnFileNm.substring(0, extn));
                        ctrl.obj.find(".file-list-area .file-name .unit").text(data[0].orgnFileNm.substring(extn+1, fileLen));
                    }
                }
            });
            var actionUrl = "./complete/insert";
            if($formObj.find("input[type=file]").data("value") != undefined){
                actionUrl = "./complete/update";
            }
            $formObj.validation({
                msg : {
                    confirm: {
                        init: msgCtrl.getMsg("confirm.sqCertiApply")
                    }
                },
                after : function() {
                    var isAfter = true;

                    return isAfter;
                },
                before : function() {

                },
                async : {
                    use : true,
                    func : function (){
                        cmmCtrl.fileFrmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(msgCtrl.getMsg("success.sqCertiApply"));
                            }
                            else
                            {
                                alert(msgCtrl.getMsg("fail.act"));
                            }
                            location.reload();
                        }, actionUrl, $formObj, "json", true , true);
                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});