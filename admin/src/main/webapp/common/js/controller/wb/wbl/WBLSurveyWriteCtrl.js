define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLSurveyWriteCtrl"
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


            // 유효성 검사
            $formObj.validation({
                after : function() {
                    return true;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


                        var svMst = {};
                        svMst.year = $formObj.find("select[name=year] option:selected").val();
                        svMst.episd = $formObj.find("select[name=episd] option:selected").val();
                        svMst.partCmpnNm1 = $formObj.find("input[name=partCmpnNm1]").val();
                        svMst.partCmpnCd1 = $formObj.find("input[name=partCmpnCd1]").val();
                        svMst.partCmpnNm2 = $formObj.find("input[name=partCmpnNm2]").val();
                        svMst.partCmpnCd2 = $formObj.find("input[name=partCmpnCd2]").val();
                        svMst.rprsntNm = $formObj.find("input[name=rprsntNm]").val();
                        svMst.bsnmRegNo = $formObj.find("input[name=bsnmRegNo]").val();
                        svMst.picNm = $formObj.find("input[name=picNm]").val();
                        svMst.telNo = $formObj.find("input[name=telNo]").val();
                        svMst.email = $formObj.find("input[name=email]").val();

                        console.log(svMst)
                        cmmCtrl.jsonAjax(function(data){
                           location.replace("./list");
                        }, actionUrl, svMst, "text")
                    }
                }
            });
        }
    };
    ctrl.exec();

    return ctrl;
});

