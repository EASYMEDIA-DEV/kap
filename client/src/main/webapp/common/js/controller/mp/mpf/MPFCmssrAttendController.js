define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpf/MPFCmssrAttendController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = jQuery("#formAttendSubmit");

    // set model
    ctrl.model = {
        id : {
            // do something...
            guidePartCmpn1 : {
                event : {
                    change : function () {
                        if($(this).val() == $("#guidePartCmpn2").val() && $(this).val()!="") {
                            alert("지도부품사2와 동일할수 없습니다.");
                            $("#guidePartCmpn2").val("");
                            $("#rgnsTwo").text("-");
                            $(this).val("");
                            return false;
                        }

                        if($(this).val() =="") {
                            $("#rgnsOne").text("-");
                        } else {
                            let split = $(this).val().split("/");
                            $("#rgnsOne").text(split[1]);
                        }
                    }
                }
            },

            guidePartCmpn2 : {
                event : {
                    change : function () {
                        if($(this).val() == $("#guidePartCmpn1").val()) {
                            alert("지도부품사1와 동일할수 없습니다.");
                            $("#guidePartCmpn2").val("");
                            $("#rgnsTwo").text("-");
                            return false;
                        }
                        if($(this).val() =="") {
                            $("#rgnsTwo").text("-");
                        } else {
                            let split = $(this).val().split("/");
                            $("#rgnsTwo").text(split[1]);
                        }
                    }
                }
            }


        },
        classname : {
            // do something...



        },
        immediately : function() {

            $formObj.validation({
                msg : {
                    confirm : {
                        init : ""
                    }
                },
                after : function(){
                        if( $('input:radio[name=atndcCd]:checked').val() =="CMSSR_ATTEND_001") {
                            if($("#guidePartCmpn1").val()=="" && $("input[name=etcBsntrp]").val()=="" && $("#guidePartCmpn2").val()=="") {
                                alert(msgCtrl.getMsg("fail.mp.mpf.al_005"));
                                return false;
                            }
                        }
                    return true;
                },
                async : {
                    use : true,

                    func : function (){
                            cmmCtrl.frmAjax(function(respObj) {
                                alert(msgCtrl.getMsg("success.sve"));
                                location.reload();
                            //     document.getElementById("formWthdrwSuccess").submit();
                            }, "./insert-attend", $formObj, "POST", "json",'',false);


                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});