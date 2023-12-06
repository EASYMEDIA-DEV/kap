define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COLgnCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// create object
	if($.cookie('id')== undefined) {
		$("#savdIdChk").prop('checked', false);
	} else {
		$("#savdIdChk").prop('checked', true);
		$("#id").val($.cookie('id'));
	}

	const email = ['naver.com' ,'google.com' ,'daum.net']
	function emailSel() {
		email.forEach((domain) => {
			$("#emailSelect").append(`<option value="${domain}">${domain}</option>`);
		});
	}
	// 로그인 콜백
	var callbackAjaxLogin = function(data){
		var code = data.respCd;

		// code ="1410";
		if(code == "0000") {
			location.replace(data.rdctUrl);
		}
		// 계정차단
		else if (code == "1090")
		{
			alert(msgCtrl.getMsg("fail.co.login.block"));
		}
		// 로그인 오류
		else if (code == "1190")
		{
			alert(msgCtrl.getMsg("fail.co.login.notMatch"));
		}
		// 로그인 오류 횟수 초과
		else if (code == "1191")
		{
			alert(msgCtrl.getMsg("fail.co.login.countExcess"));
		}
		// 1210:임시 비밀번호, 1310: 오픈 후 첫 로그인, 1410:비밀번호 변경주기(3개월) 초과
		else if (code == "1210" || code == "1310" || code == "1410" || code == "1510")
		{
			if (code == "1210")
			{
				alert(msgCtrl.getMsg("fail.co.login.password.temporary"));
				location.replace("/change-password");
			}
			else if (code == "1310")
			{
				location.replace("/info-upd");
			}
			else if (code == "1410" || code == "1510")
			{
				alert(msgCtrl.getMsg("fail.co.login.password.changeCycle"));
				location.replace("/change-password");
			}
			
		}

		else if (code == "9999")
		{
			alert(msgCtrl.getMsg("fail.co.login.notExist"));
		}
		var subCode = code.substring(2);
		if (subCode != "00" && subCode != "10")
		{
			$("#password").val("");
			$("#id").val("").focus();
		} else {
			if($("#savdIdChk").prop('checked')) {
				$.cookie('id', $("#id").val(),{expires: 365});
			} else {
				deleteCookie("id");
			}
		}
	};

	function deleteCookie(name) {
		document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	}

	//비밀번호 변경 콜백
	var callbackAjaxPwdChng = function(data){
		var rtnCode = data.respCd;

		if (rtnCode == "00")
		{
			alert(msgCtrl.getMsg("success.co.login.password.change"));
			location.replace("/login");
		}
		else
		{
			if (rtnCode == "10")
			{
				alert(msgCtrl.getMsg("fail.co.login.password.previous_notMatch"));
				$("#password").val("").focus();
			}
			else if (rtnCode == "20")
			{
				if (rtnCode == "20")
				{
					alert(msgCtrl.getMsg("fail.co.login.password.notSamePwd"));
				}

				jQuery("#newPassword, #passwordConfirm").val("");
				jQuery("#newPassword").focus();
			}
			else if (rtnCode == "40")
			{
				alert(msgCtrl.getMsg("fail.http.status.C401"));
				location.replace("/login");
			}
		}
	};

	var callbackAjaxPwdNextChng = function (data) {
		var rtnCode = data.respCd;

		if (rtnCode == "00")
		{
			location.replace("/");
		}
	}

	//이메일 콜백
	var callbackAjaxLoginEmail = function(data){
		var rtnCode = data.respCd;
		if (rtnCode == "0000")
		{
			location.replace(data.rdctUrl);
		}
		else
		{
			// alert(msgCtrl.getMsg("success.co.login.auth.emailNotCofirm"));
			$("input[name=emailAuthNum]").val("").focus();
		}
	};

	// set model
	ctrl.model = {
		id : {
			// do something...
			//비밀번호 다음에 버튼
			nextChange : {
					event : {
						click : function() {
							if(confirm(msgCtrl.getMsg("confirm.pwdNextUpd"))) {
								var $formObj2 = $("#frmPwdChng");
								cmmCtrl.frmAjax(callbackAjaxPwdNextChng, "/change-password-next", $formObj2);
							}
						}
					}
				},
			//이메일 주소 변경 이벤트
			emailSelect : {
				event : {
					change : function() {
						$("#emailAddr").val($(this).val());
					}
				}
			},
			//본인 인증
			myRegister : {
				event : {
					click : function (){
						jQuery.ajax({
							url : "/nice/my-idnttvrfct",
							type : "post",
							data :
								{
									"receivedata" : "/id-find-res" //팝업 후 이동할 페이지
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
			btnEmail :  {
				event : {
						click : function() {
							if (confirm(msgCtrl.getMsg("confirm.emailTrans")))
							{
								var $formObj5 = $("#frmEmail");
								cmmCtrl.frmAjax(function (data){
									if(data != null || data != undefined) {
										alert(msgCtrl.getMsg("success.co.login.email.emailTrans"));
									}
								}, "/id-email-trans", $formObj5, "post", "json", true);
							}
						}
				}
			}


		},
		classname : {
			// do something...
			onlyNumber : {
				event : {
					input : function (event) {
						let numbers = event.target.value.replace(/[^0-9]/g, '');
						event.target.value = numbers;
					}
				}
			},
			nameChk : {
				event : {
					input : function (event) {
						let chars = event.target.value.replace(/[^a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
						event.target.value = chars;
					}
				}
			},
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
		},
		immediately : function() {
			emailSel();
			// 로그인 유효성 검사
			var $formObj1 = $("#frmLogin");
			$formObj1.validation({
				validateType : {
					idChk : {
						className : "idChk",
						regExr : "^[0-9a-zA-Z]+$"
					}
				},
				msg : {
					idChk : "영문/숫자만 입력하실 수 있습니다.",
					confirm : {
						init : ""
					}
				},
				async : {
	                use : true,
	                func : function(){
							cmmCtrl.frmAjax(callbackAjaxLogin, "/login", $formObj1, "POST", "json", true);
	                }
				}
			});

			//비밀번호 변경 폼
			var $formObj2 = $("#frmPwdChng"), loginId = $formObj2.data("loginId");
			$formObj2.validation({
				msg : {
					confirm : {
						init : ""
					}
				},
				after : function(){
					var trgtObj = jQuery("#newPassword");
					var passwordRegex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])([a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]){8,16}$/;
					if(passwordRegex.test(trgtObj.val())) {
						$(".for-status-chk1").removeClass('error');
					} else {
						trgtObj.focus();
						$(".for-status-chk1").addClass('error');
						return false;
					}
					if($("#passwordConfirm").val() == $("#newPassword").val()) {
						$(".for-status-chk2").removeClass('error');
					} else {
						$("#passwordConfirm").focus();
						$(".for-status-chk2").addClass('error');
						return false;
					}

					// if (trgtObj.val().indexOf(loginId) > -1)
					// {
					// 	alert(msgCtrl.getMsg("fail.co.coc.password.includeId"));
					// 	trgtObj.focus();
					// 	return false;
					// }
					//
					// if (/([a-zA-Z0-9!@#$%^&*()_\-+={}\[\]<>,.?\/])\1\1/.test(trgtObj.val()))
			        // {
					// 	alert(msgCtrl.getMsg("fail.co.coc.password.sameString"));
					// 	trgtObj.focus();
					// 	return false;
			        // }
					//
					// if (!cmmCtrl.checkContString(trgtObj.val(), 4))
					// {
					// 	alert(msgCtrl.getMsg("fail.co.coc.password.contString"));
					// 	trgtObj.focus();
					// 	return false;
					// }

					return true;
				},
				async : {
	                use : true,
	                func : function (){

						if(confirm(msgCtrl.getMsg("confirm.pwdUpd"))) {
							cmmCtrl.frmAjax(callbackAjaxPwdChng, "/change-password", $formObj2);
						}
	                }
				}
			});

			//휴대폰 으로 아이디 찾기
			var $formObj3 = $("#frmIdFind");
			$formObj3.validation({
				msg : {
					confirm : {
						init : ""
					}
				},
				after : function(){
					if($("#year-id").val().length !=4) {
						alert(msgCtrl.getMsg("fail.co.login.find.year"));
						$("#year-email").focus();
						return false;
					}
					if($("#month-id").val().length !=2 || $("#month-id").val() >=13) {
						alert(msgCtrl.getMsg("fail.co.login.find.month"));
						$("#month-id").focus();
						return false;
					}
					if($("#day-id").val().length !=2 || $("#month-id").val() >=32) {
						alert(msgCtrl.getMsg("fail.co.login.find.day"));
						$("#day-id").focus();
						return false;
					}
					if($("#hpNo").val().length != 13) {
						alert(msgCtrl.getMsg("fail.co.login.find.hp"));
						$("#hpNo").focus();
						return false;
					}
					return true;
				},
				async : {
					use : true,
					func : function (){
						$("#birthdate-id").val($("#year-id").val() + "-" + $("#month-id").val() + "-" + $("#day-id").val());
						document.getElementById('frmIdFind').submit();
					}
				}
			});

			//이메일 으로 아이디 찾기
			var $formObj4 = $("#frmIdFind-email");
			$formObj4.validation({
				msg : {
					confirm : {
						init : ""
					}
				},
				after : function(){
					if($("#year-email").val().length !=4) {
						alert(msgCtrl.getMsg("fail.co.login.find.year"));
						return false;
					}
					if($("#month-email").val().length !=2 || $("#month-id").val() >=13) {
						alert(msgCtrl.getMsg("fail.co.login.find.month"));
						return false;
					}
					if($("#day-email").val().length !=2 || $("#month-id").val() >=32) {
						alert(msgCtrl.getMsg("fail.co.login.find.day"));
						return false;
					}
					return true;
				},
				async : {
					use : true,
					func : function (){
						$("#birthdate-email").val($("#year-email").val() + "-" + $("#month-email").val() + "-" + $("#day-email").val());
						$("#email").val($("#email-first").val() + "@" + $("emailAddr").val());
						document.getElementById('frmIdFind-email').submit();
					}
				}
			});


			var regExp = /[^\w\s]|[\_]/g;
			// id 입력값 체크
			$("#id").keyup(function (){
				if(regExp.test($(this).val())){
					return $(this).val($(this).val().replaceAll(regExp, ""));
				}
			});
		}
	};

	ctrl.exec();

	return ctrl;
});