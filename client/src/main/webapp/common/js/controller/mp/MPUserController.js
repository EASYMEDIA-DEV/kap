define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/MPUserController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    //아이디 중복확인 여부
    let dupIdChk = false;
    var $formObj = $("#formUserSubmit");
    let timeSecond =""; // 남은 시간 변수
    var intervalVar = "";

    //이메일 인증 번호
    let secretEmailAuth = "";

    let EmailChk = false;
    const email = ['naver.com' ,'google.com' ,'nate.com','daum.net']
    function emailSel() {
        email.forEach((domain) => {
            $("#emailSelect").append(`<option value="${domain}">${domain}</option>`);
        });
    }

    function emailInit() {
        EmailChk = false;
        $(".for-status-chk-email").hide();
        $(".for-status-chk-email").removeClass("satisfy");
        $(".authName").text("인증");
        $("#emailAuthChk").show();
        $("#timer").show();
    }

    // set model
    ctrl.model = {
        id : {
            // do something...
            //본인 인증
            myRegister : {
                event : {
                    click : function (){
                        jQuery.ajax({
                            url : "/nice/my-idnttvrfct",
                            type : "post",
                            data :
                                {
                                    "receivedata" : "/member/agreement&"+$("#joinType").val() //팝업 후 이동할 페이지 파라미터 추가 ex /id-find-res&ex1&ex2 최대 5개
                                },
                            success : function(data)
                            {
                                const { form } = document;
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

            //약관 -> 회원정보 이동 시  다음 페이지 이동 시
            nextPageOne : {
                event : {
                    click : function() {
                        if(!$("#allAgreeChk").prop('checked')) {
                            if(!$("#agreeChk1").prop('checked')) {
                                alert(msgCtrl.getMsg("fail.mp.agree.al_004"));
                                return false;
                            } else if(!$("#agreeChk2").prop('checked')) {
                                alert(msgCtrl.getMsg("fail.mp.agree.al_005"));
                                return false;
                            }
                        }
                        $("#agree1").val($("#allAgreeChk").prop('checked') ? 'Y' : 'N'); // 전체 약관
                        $("#trmsAgmntYn").val($("#agreeChk1").prop('checked') ? 'Y' : 'N'); // 이용약관
                        $("#psnifAgmntYn").val($("#agreeChk2").prop('checked') ? 'Y' : 'N'); // 개인정보처리방침
                        $("#psnif3AgmntYn").val($("#agreeChk3").prop('checked') ? 'Y' : 'N'); // 3자 개인정보
                        $("#fndnNtfyRcvYn").val($("#agreeChk4").prop('checked') ? 'Y' : 'N'); // 3자 개인정보

                        $("#ntfySmsRcvYn").val($("#marketingChk1").prop('checked') ? 'Y' : 'N'); // 마케팅 이메일
                        $("#ntfyEmailRcvYn").val($("#marketingChk2").prop('checked') ? 'Y' : 'N'); // 마케팅 SMS
                        let targetPage;
                        if($("#param1").val() == 'CO') {
                             targetPage = "/member/mp-user-join";
                        } else if($("#param1").val() == 'CP') {
                            targetPage = "/member/mp-member-parts-join";
                        }
                        document.getElementById("formNextOne").action = targetPage; // form의 action 속성 변경
                        document.getElementById("formNextOne").submit(); // form 제출
                    }
                }
            },

            //아이디 중복 체크
            dupId : {
                event : {
                    click : function() {

                        $(".for-status-chk-id").removeClass("satisfy");
                        if($("#id").val().trim().length > 0) {
                            cmmCtrl.frmAjax(function(respObj) {
                                if(respObj.dupChk == 'Y') {
                                    dupIdChk = true;
                                    $(".for-status-chk-id").addClass("satisfy");

                                } else {
                                    dupIdChk = false;
                                    alert(msgCtrl.getMsg("fail.mp.join.al_007"));
                                }
                            }, "/member/dup-id", $formObj, "POST", "json",'',false);

                        } else {
                            alert(msgCtrl.getMsg("fail.mp.join.al_006"));
                        }
                    }
                }
            },

            //아이디 입력시
            id : {
                event : {
                    input : function() {
                        dupIdChk = false;
                        $(".for-status-chk-id").removeClass("satisfy");
                    }
                }
            },

            //이메일 주소 변경 이벤트
            emailSelect : {
                event : {
                    change : function() {
                        if($(this).val() !=''){
                            $("#emailAddr").prop('readonly', true);
                        } else {
                            $("#emailAddr").prop('readonly', false);
                        }
                        $("#emailAddr").val($(this).val());
                    }
                }
            },

            //이메일 인증 버튼 클릭 시
            emailAuth : {
                event : {
                    click : function() {
                        EmailChk = false;
                        if($("#email-first").val() == '' || $("#emailAddr").val() ==''){
                            alert(msgCtrl.getMsg("fail.mp.join.al_009"));
                            return false;
                        }

                        let email = $("#email-first").val();
                        let emailRegex = /^\S{0,64}$/;
                        if(!emailRegex.test(email)) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                            $("#email-first").focus();
                            return false;
                        }
                        let emailAddr = $("#emailAddr").val();

                        let emailAddrRegex = /\./;
                        if(!emailAddrRegex.test(emailAddr)) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                            return false;
                        }

                        $("#email").val($("#email-first").val()+"@"+$("#emailAddr").val());
                        $("#email-auth").val($("#email-first").val()+"@"+$("#emailAddr").val());


                        var $formObj5 = $("#formUserSubmit");
                        cmmCtrl.frmAjax(function(respObj) {
                            if(respObj.dupChk == 'Y') {
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
                        }, "/member/dup-email", $formObj, "POST", "json",'',false);

                    }
                }
            },

            //이메일 인증번호 체크
            emailAuthChk : {
                 event : {
                     click : function() {
                         if(timeSecond > 0) {
                            if($("#emailAuthNum").val() == secretEmailAuth) {
                                EmailChk = true;
                                $("#emailAuthChk").hide();
                                $("#timer").hide();
                                $(".for-status-chk-email").addClass("satisfy");
                            } else {
                                alert(msgCtrl.getMsg("fail.mp.join.al_025"));
                                $(".for-status-chk-email").removeClass("satisfy");
                                EmailChk = false;
                                return false;
                            }
                         } else {
                             alert(msgCtrl.getMsg("fail.mp.join.al_024"));
                             $(".for-status-chk-email").removeClass("satisfy");
                             EmailChk = false;
                             return false;
                         }
                     }
                 }
            },
            //일반 전화번호 이벤트
            telNo : {
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
            //우편검색
            searchPostCode : {
                event : {
                    click : function() {
                        // searchPostCode();
                        cmmCtrl.searchPostCode(500,500,"zipcode","bscAddr","dtlAddr");

                    }
                }
            },
            pstnCd : {
                    event : {
                        change : function() {
                            $(".pstnNm").val('');
                            if($("#pstnCd").val()!='MEM_CD01007') {
                                $(".pstnNmDis").hide();
                            } else {
                                $(".pstnNmDis").show();
                            }
                        }
                    }
            },

        },
        classname : {
            // do something...
            btnBack : {
                event : {
                    click : function () {
                        if (confirm(msgCtrl.getMsg("confirm.backChk"))) {
                            history.back();
                        }
                    }
                }
            },
            emailChks : {
                event : {
                    input : function (event) {
                        let chars = event.target.value.replace(/[^a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/g, '');
                        event.target.value = chars;
                        emailInit();

                    }
                }
            },
            emailAddrChks : {
                event : {
                    input : function (event) {
                        emailInit();
                    }
                }
            }

        },
        immediately : function() {
            $("#timer").hide();
            $(".for-status-chk-email").hide();
            emailSel();
            $formObj.validation({
                msg : {
                    confirm : {
                        init : ""
                    }
                },
                after : function(){
                    if(!dupIdChk) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_012"));
                        return false;
                    }

                    var trgtObj = jQuery(".pwd");
                    var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])([a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]){8,16}$/;
                    if(passwordRegex.test(trgtObj.val())) {
                        $(".for-status-chk1").removeClass('error');
                    } else {
                        trgtObj.focus();
                        $(".for-status-chk1").addClass('error');
                        return false;
                    }
                    if($(".pwdSetConfirm").val() == $(".pwd").val()) {
                        $(".for-status-chk2").removeClass('error');
                    } else {
                        $(".pwdSetConfirm").focus();
                        $(".for-status-chk2").addClass('error');
                        return false;
                    }

                    let email = $("#email-first").val();
                    let emailRegex = /^\S{0,64}$/;
                    if(!emailRegex.test(email)) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                        $("#email-first").focus();
                        return false;
                    }
                    let emailAddr = $("#emailAddr").val();

                    let emailAddrRegex = /\./;
                    if(!emailAddrRegex.test(emailAddr)) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_023"));
                        return false;
                    }

                    if(!EmailChk) {
                        alert(msgCtrl.getMsg("fail.mp.join.al_015"));
                        return false;
                    }

                    if($("#memCd").val() =='CP') {
                        if($("#deptCd").val() == '') {
                            alert(msgCtrl.getMsg("fail.mp.join.al_032"));
                            return false;
                        }
                        if($("#pstnCd").val() =='') {
                            alert(msgCtrl.getMsg("fail.mp.join.al_034"));
                            return false;
                        }
                        if($("#pstnCd").val() =='MEM_CD01007' && $(".pstnNm").val() =='') {
                            alert(msgCtrl.getMsg("fail.mp.join.al_035"));
                            return false;
                        }
                    }

                    $("#email").val($("#email-first").val()+"@"+$("#emailAddr").val());
                    return true;
                },
                async : {
                    use : true,

                    func : function (){
                        cmmCtrl.frmAjax(function(respObj) {
                            var today = new Date();
                            var year = today.getFullYear();
                            var month = ('0' + (today.getMonth() + 1)).slice(-2);
                            var day = ('0' + today.getDate()).slice(-2);
                            var hours = ('0' + today.getHours()).slice(-2);
                            var minutes = ('0' + today.getMinutes()).slice(-2);
                            $("#regDtm").val(year + "-" + month + "-" + day + " " + hours + ":" + minutes);
                            document.getElementById("formSuccess").submit();
                        }, "/member/insert", $formObj, "POST", "json",'',false);
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