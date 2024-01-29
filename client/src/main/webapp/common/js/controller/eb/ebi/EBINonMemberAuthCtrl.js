define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebi/EBINonMemberAuthCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    // function


    // set model
    ctrl.model = {
        id : {

            //휴대폰 자동 하이픈
            hpNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, '');
                        const phoneLen = phoneNumber.length;

                        if (phoneLen > 3 && phoneLen <= 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                        } else if (phoneLen > 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },

            //조회 버튼
            btnSearch : {
                event : {
                    click : function() {

                        if ($("#name").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.auth.al_002"));
                            $("#name").focus();
                            return false;
                        }
                        if ($("#hpNo").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.auth.al_003"));
                            $("#hpNo").focus();
                            return false;
                        }
                        if ($("#emailSrt").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.auth.al_004"));
                            $("#emailSrt").focus();
                            return false;
                        }
                        if ($("#emailEnd").val() == '') {
                            alert(msgCtrl.getMsg("fail.eb.ebi.auth.al_004"));
                            $("#emailEnd").focus();
                            return false;
                        }

                        var email = '' + $("#emailSrt").val() + '@' + $("#emailEnd").val();
                        var regExr = /^[_a-zA-Z0-9-\.\_]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$/;

                        if(regExr.test(email)) {
                            $("#email").val(email);
                        }
                        else {
                            alert("이메일 주소를 다시 확인 해주세요.");
                            $("#emailSrt").focus();
                            return false;
                        }

                        $formObj.attr("action", "./list");
                        $formObj.submit();

                        /*cmmCtrl.frmAjax(function(data){
                            /!*if(data.respCnt > 0) {*!/
                                /!*var hpNo = $("#hpNo").val().replace(/-/g, '');
                                $("#hpNo").val(hpNo);*!/

                                $formObj.attr("action", "./list");
                                $formObj.submit();
                            /!*}
                            else {
                                alert(msgCtrl.getMsg("fail.eb.ebi.auth.al_001"));
                                location.reload();
                            }*!/
                        }, "./search", $formObj, "post", "json");*/

                    }
                }
            },

            //이메일 도메인 변경
            emailEndSelect : {
                event : {
                    change : function() {
                        var selectedEmailDomain = $(this).val();

                        if(selectedEmailDomain == "") {
                            $("#emailEnd").attr("readonly", false);
                            $("#emailEnd").val("");
                        }
                        else {
                            $("#emailEnd").attr("readonly", true);
                            $("#emailEnd").val(selectedEmailDomain);
                        }
                    }
                }
            }

        },
        classname : {



        },
        immediately : function() {



        }
    };


    ctrl.exec();

    return ctrl;
});

