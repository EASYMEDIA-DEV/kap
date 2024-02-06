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
            cancelBtn : {
                event : {
                    click : function() {
                        if (confirm(msgCtrl.getMsg("confirm.cancel"))) {
                            location.href ="/my-page/main";
                        }
                    }
                }
            },

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
                            var ajaxData = {
                            }
                            jQuery("#formWthdrwSuccess").serializeArray().forEach(function (field) {
                                if (field.name == '_csrf') {
                                    ajaxData[field.name] = field.value;
                                }
                            });

                                jQuery(".loading-area").stop().fadeIn(200);
                                jQuery.ajax({
                                    url: "/my-page/member/intrduction/confirm-comp",
                                    type: "post",
                                    timeout: 30000,
                                    data: ajaxData,
                                    dataType: "json",
                                    cache: false,
                                    success: function (data, status, xhr) {
                                        if (data.data.chk) {
                                            alert("참여 중인 사업이 " + data.data.count + " 건 있습니다. 소속부품사 변경이 불가합니다.\n")
                                            return false;
                                        } else {
                                            cmmCtrl.frmAjax(function (respObj) {
                                                alert(msgCtrl.getMsg("success.mp.wthdrw.al_001"));
                                                document.getElementById("formWthdrwSuccess").submit();
                                            }, "/my-page/member/wthdrw/update-wthdrw", $formObj, "POST", "json", '', false);
                                        }
                                    },
                                    error: function (data, status, xhr) {
                                        $(".loading-area").stop().fadeOut(200);
                                        return false;
                                    },
                                    complete: function () {
                                        $(".loading-area").stop().fadeOut(200);
                                    }
                                });


                        }
                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});