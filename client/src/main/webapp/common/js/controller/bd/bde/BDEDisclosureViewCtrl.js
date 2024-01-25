define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bde/BDEDisclosureViewCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("frmData");
    var getList = function(){
         var actForm = {};
         var dsclsrSeq = $("#dsclsrSelectList").val();
         actForm.dsclsrSeq = dsclsrSeq;
         cmmCtrl.jsonAjax(function(data){
             var cntn = data.rtnDtl["cntn"];
             $(".cntnArea").html(cntn);
          }, "./search", actForm, "json");
    }

    // set model
    ctrl.model = {
        id : {
            dsclsrSelectList: {
                event: {
                    change: function () {
                        $formObj.find("#dsclsrSeq").val($(this).val());
                        getList();
                    }
                }
            }

        },
        classname : {

        },
        immediately : function() {
            getList();
        }
    };

    ctrl.exec();

    return ctrl;
});