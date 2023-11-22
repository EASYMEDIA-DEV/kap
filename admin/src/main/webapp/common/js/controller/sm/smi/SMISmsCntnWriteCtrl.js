define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smi/SMISmsCntnWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    var dupCodeChk = true;

    // set model
    ctrl.model = {
        id : {
            smsCntnCd : {
                event : {
                    change : function () {
                        var smsCntnCd = $("#smsCntnCd").val()
                        var ajaxData = {
                            smsCntnCd : smsCntnCd,
                        }

                        console.log(JSON.stringify(ajaxData, null, 2));
                        $formObj.serializeArray().forEach(function(field) {
                            ajaxData[field.name] = field.value;
                        });

                        $.ajax({
                            type: "post",
                            url: "./codeDupCheck",
                            dataType: "json",
                            data: ajaxData,
                            success: function(r) {
                                if(r.dupChk == 'Y') {
                                    dupCodeChk = true;
                                    alert("이미 등록된 SMS 구분입니다.");
                                    $("#smsCntnCd option:eq(0)").prop("selected", true);
                                } else {
                                    dupCodeChk = false;
                                }
                            }
                        })
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {
            // 유효성 검사
            $formObj.validation({
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        cmmCtrl.frmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(actionMsg);
                                location.replace("./list");
                            }
                            actionUrl = "./list";
                        }, actionUrl, $formObj, "post", "json")
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

