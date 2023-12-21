define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/SMDAPsnIfController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("frmData");
    var getList = function(){
         var actForm = {};
         var psnifSeq = $("#psnIfSelectList").val();
         actForm.psnifSeq = psnifSeq;
         cmmCtrl.jsonAjax(function(data){
             var cntn = data.prsnDtl["cntn"];
             $(".cntnArea").html(cntn);
          }, "./search", actForm, "json");
    }

    // set model
    ctrl.model = {
        id : {
            psnIfSelectList: {
                event: {
                    change: function () {
                        $formObj.find("#psnifSeq").val($(this).val());
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