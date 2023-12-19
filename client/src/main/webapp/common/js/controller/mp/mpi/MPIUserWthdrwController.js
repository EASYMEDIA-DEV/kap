define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpi/MPIUserWthdrwController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = jQuery("#formUserWthdrwSubmit");

    // set model
    ctrl.model = {
        id : {
            // do something...


        },
        classname : {
            // do something...
            wthdrwRsnCd : {
                event : {
                    change : function() {
                        if($(this).val() == 'MEM_WTHDRW_005') {
                               $("#wthdrwRsnEtcNm").attr('disabled',false);
                        } else {
                            $("#wthdrwRsnEtcNm").val("");
                            $("#wthdrwRsnEtcNm").attr('disabled',true);

                        }
                    }
                }
            }


        },
        immediately : function() {

            $formObj.validation({
                msg : {
                    confirm : {
                        init : ""
                    }
                },
                after : function(){
                    if($(".wthdrwRsnCd").val() =='MEM_WTHDRW_005') {
                        if( $("#wthdrwRsnEtcNm").val()=="") {
                            alert(msgCtrl.getMsg("fail.mp.mpi.al_003"));
                            return false;
                        }
                    }
                    return true;
                },
                async : {
                    use : true,

                    func : function (){
                        if (confirm(msgCtrl.getMsg("confirm.wthdrw"))) {
                            cmmCtrl.frmAjax(function(respObj) {
                                alert(msgCtrl.getMsg("success.mp.wthdrw.al_001"));
                                var today = new Date();
                                var year = today.getFullYear();
                                var month = ('0' + (today.getMonth() + 1)).slice(-2);
                                var day = ('0' + today.getDate()).slice(-2);
                                var hours = ('0' + today.getHours()).slice(-2);
                                var minutes = ('0' + today.getMinutes()).slice(-2);
                                $("#regDtm").val(year + "-" + month + "-" + day + " " + hours + ":" + minutes);
                                document.getElementById("formWthdrwSuccess").submit();
                                if(respObj.respCnt > 0){

                                }
                            }, "/my-page/member/wthdrw/update-wthdrw", $formObj, "POST", "json",'',false);

                        }
                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});