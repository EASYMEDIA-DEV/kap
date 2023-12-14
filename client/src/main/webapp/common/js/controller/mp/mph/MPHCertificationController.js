define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mph/MPHCertificationController"
    };

    let timeSecond =""; // 남은 시간 변수
    var intervalVar = "";

    //이메일 인증 번호
    let secretEmailAuth = "";
    let width = 500;
    let height = 500;
    let emailChk = true;

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    const email = ['naver.com' ,'google.com' ,'nate.com','daum.net']
    function emailSel() {
        email.forEach((domain) => {

            const emailAddr = $("#oldEmailAddr").val();
            const isSelected = emailAddr === domain ? "selected" : "";
            if(isSelected == "selected") {
                $("#emailAddrNew").prop("readonly" , true);
            }
            $("#emailSelect").append(`<option value="${domain}" ${isSelected}>${domain}</option>`);
        });
    }

    function callbackAjaxLogin(data) {
        if(data.data.passwordChk) {
            location.replace('/my-page/member/modify-page/' + data.data.memCd);
        } else {
            alert(msgCtrl.getMsg("fail.mp.mph.al_002"));
            return false;
        }
    }

    // set model
    ctrl.model = {
        id : {
            //본인 인증
            myRegister : {
                event : {
                    click : function (){

                        jQuery.ajax({
                            url : "/nice/my-idnttvrfct",
                            type : "post",
                            data :
                                {
                                    "receivedata" : "no&"+$("#ci").val() //팝업 후 이동할 페이지 파라미터 추가 ex /id-find-res&ex1&ex2 최대 5개
                                },
                            success : function(data)
                            {
                                const {form} = document;

                                const option = `status=no, menubar=no, toolbar=no, resizable=no, width=500, height=600`;
                                document.getElementById('enc_data').value = data.enc_data; // enc_data 값을 설정
                                document.getElementById('integrity_value').value = data.integrity_value; // integrity_value 값을 설정
                                document.getElementById('token_version_id').value = data.token_version_id; // integrity_value 값을 설정

                                window.open('', 'nicePopup', option);

                                form.target = 'nicePopup';
                                document.getElementById('form').submit();

                            },
                            error : function(xhr, ajaxSettings, thrownError)
                            {
                                cmmCtrl.errorAjax(xhr);
                                jQuery.jstree.rollback(data.rlbk);
                            }
                        });
                    }
                }
            },
            //이메일 주소 변경 이벤트
            emailSelect : {
                event : {
                    change : function() {
                        if($(this).val() !=''){
                            $("#emailAddrNew").prop('readonly' ,true);
                        } else {
                            $("#emailAddrNew").prop('readonly' ,false);
                        }

                        if($(this).val() != $("#oldEmailAddr").val()) {
                            $(".newEmailAuth").show();
                            emailChk = false;
                        } else {
                            $(".newEmailAuth").hide();
                            $("#emailAuthDis").hide();
                            emailChk = true;
                        }

                        $("#emailAddrNew").val($(this).val());
                    }
                }
            },
            //이메일 입력 창
            emailNameNew : {
                event : {
                    input : function() {
                        if($(this).val() != $("#oldEmailName").val() || $("#emailAddrNew").val() != $("#oldEmailAddr").val()) {
                            $(".newEmailAuth").show();
                            emailChk = false;
                        } else {
                            $(".newEmailAuth").hide();
                            $("#emailAuthDis").hide();
                            emailChk = true;
                        }
                    }
                }
            },
            //이메일 주소 입력 창
            emailAddrNew : {
                event : {
                    input : function() {
                        if($(this).val() != $("#oldEmailAddr").val() || $("#emailNameNew").val() !=$("#oldEmailName").val() ) {
                            $(".newEmailAuth").show();
                            emailChk = false;
                        } else {
                            $(".newEmailAuth").hide();
                            $("#emailAuthDis").hide();
                            emailChk = true;
                        }
                    }
                }
            },

            //이메일 인증 버튼 클릭 시
            emailAuth : {
                event : {
                    click : function() {
                        if($("#emailNameNew").val() == '' || $("#emailAddrNew").val() ==''){
                            alert(msgCtrl.getMsg("fail.mp.join.al_009"));
                            return false;
                        }

                        let email = $("#emailNameNew").val();
                        let emailRegex = /^\S{0,64}$/;
                        if(!emailRegex.test(email)) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                            $("#emailNameNew").focus();
                            return false;
                        }
                        let emailAddr = $("#emailAddrNew").val();

                        let emailAddrRegex = /\./;
                        if(!emailAddrRegex.test(emailAddr)) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                            return false;
                        }

                        $("#email").val($("#emailNameNew").val()+"@"+$("#emailAddrNew").val());
                        $("#email-auth").val($("#emailNameNew").val()+"@"+$("#emailAddrNew").val());


                        var $formObj5 = $("#formUserSubmit");
                        cmmCtrl.frmAjax(function(respObj) {
                            if(respObj.dupChk == 'Y') {
                                $("#emailAuthDis").show();
                                $("#timer").show();
                                $("#emailAuthChk").show();
                                $(".for-status-chk-email").show();
                                $(".authName").text("재인증");
                                timeSecond = 300;
                                $('#timer span').text(getTimeString(timeSecond));
                                cmmCtrl.frmAjax(function (data){
                                    secretEmailAuth = data;
                                }, "/member/email-auth-number", $formObj5, "post", "json", true);

                                if (intervalVar != undefined) {
                                    clearInterval(intervalVar);
                                }
                                $("#emailAuthDis").show();
                                intervalVar = setInterval(function() {
                                    if (timeSecond != 0) {
                                        timeSecond = timeSecond - 1;
                                        $('#timer span').text(getTimeString(timeSecond));
                                    } else {
                                        clearInterval(intervalVar);
                                        intervalVar = false;
                                    }
                                }, 1000);
                            } else {
                                alert(msgCtrl.getMsg("fail.mp.join.al_017"));
                            }
                        }, "/member/dup-email", $formObj5, "POST", "json",'',false);

                    }
                }
            },

            //이메일 인증번호 체크
            emailAuthChk : {
                event : {
                    click : function() {
                        if(timeSecond > 0) {
                            if($("#emailAuthNum").val() == secretEmailAuth) {
                                emailChk = true;
                                $("#emailAuthChk").hide();
                                $("#timer").hide();
                                $(".for-status-chk-email").addClass("satisfy");
                            } else {
                                emailChk = false;
                                alert(msgCtrl.getMsg("fail.mp.join.al_025"));
                                $(".for-status-chk-email").removeClass("satisfy");
                                return false;
                            }
                        } else {
                            emailChk = false;
                            alert(msgCtrl.getMsg("fail.mp.join.al_024"));
                            $(".for-status-chk-email").removeClass("satisfy");
                            return false;
                        }
                    }
                }
            },
            //주소 검색
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"zipcode","bscAddr","dtlAddr");

                    }
                }
            },

            telNoInput : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
            btnParts : {
                event : {
                    click : function() {

                        openPopup('switchingMemberPopup',this);

                    }
                }
            },

        },
        classname : {

        },

        immediately : function() {

            emailSel();

            var $formObj = $("#formPasswordConfirm");
            $formObj.validation({
                msg : {
                    confirm : {
                        init : ""
                    }
                },
                async : {
                    use : true,
                    func : function(){
                        cmmCtrl.frmAjax(callbackAjaxLogin, "/my-page/member/confirm-password", $formObj, "POST", "json", true);
                    }
                }
            });

            var $formObj6 = $("#formUserSubmit");
            $formObj6.validation({
                msg : {
                    confirm : {
                        init : ""
                    }
                },
                after : function(){
                    var trgtObj = jQuery("#pwd");
                    var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])([a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]){8,16}$/;
                    if ($("#pwd").val().trim() != "" || $("#pwdConfirm").val().trim() != "") {
                        if (passwordRegex.test(trgtObj.val())) {
                            $(".for-status-chk1").removeClass('error');
                        } else {
                            trgtObj.focus();
                            $(".for-status-chk1").addClass('error');
                            return false;
                        }
                        if ($("#pwdConfirm").val().trim() == $("#pwd").val().trim()) {
                            $(".for-status-chk2").removeClass('error');
                        } else {
                            $("#pwdConfirm").focus();
                            $(".for-status-chk2").addClass('error');
                            return false;
                        }
                    }
                    let email = $("emailNameNew").val();
                    let emailRegex = /^\S{0,64}$/;
                    if(!emailRegex.test(email)) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                        $("#emailNameNew").focus();
                        return false;
                    }
                    let emailAddr = $("#emailAddrNew").val();

                    let emailAddrRegex = /\./;
                    if(!emailAddrRegex.test(emailAddr)) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                        return false;
                    }
                    if(!emailChk) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_015"));
                        return false;
                    }
                    // if($("#memCd").val() =='CP') {
                    //     if($("#deptCd").val() == '') {
                    //         alert(msgCtrl.getMsg("fail.mp.join.al_032"));
                    //         return false;
                    //     }
                    //     if($("#pstnCd").val() =='') {
                    //         alert(msgCtrl.getMsg("fail.mp.join.al_034"));
                    //         return false;
                    //     }
                    //     if($("#pstnCd").val() =='MEM_CD01007' && $(".pstnNm").val() =='') {
                    //         alert(msgCtrl.getMsg("fail.mp.join.al_035"));
                    //         return false;
                    //     }
                    // }
                    //

                    $("#formName").val($("#name").text());
                    $("#formBirth").val($("#birth").text());
                    $("#formGndr").val($("#gndr").text()=='남'?'1':'2');
                    $("#formHpNo").val($("#hpNo").text());
                    $("#formEmail").val($("#emailNameNew").val() + "@" + $("#emailAddrNew").val());
                    $("#formNtfyEmailRcvYn").val($("input[name='ntfyEmailRcvYn']:checked").val())
                    $("#formNtfySmsRcvYn").val($("input[name='ntfySmsRcvYn']:checked").val())

                    return true;
                },
                async : {
                    use : true,

                    func : function (){
                        if(confirm(msgCtrl.getMsg("confirm.upd2"))){
                            cmmCtrl.frmAjax(function(respObj) {
                                //TODO 페이지 이동
                                alert(msgCtrl.getMsg("success.upd2"));
                                location.reload();
                            }, "/my-page/member/update", $formObj6, "POST", "json",'',false);
                        }
                    }
                }
            });
        }
    };

    var getTimeString = function (second) {
        var minute = '' + (Math.floor(second / 60));
        var dividedSec = second % 60;
        if (dividedSec < 10) {
            dividedSec = '0' + dividedSec;
        }
        // ex) 3:00 -> 3분
        return minute + ":" + dividedSec;
    }
    ctrl.exec();

    return ctrl;
});