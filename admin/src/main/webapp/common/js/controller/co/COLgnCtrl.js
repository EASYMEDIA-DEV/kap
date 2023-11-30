define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COLgnCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// create object
	
	// 로그인 콜백
	var callbackAjaxLogin = function(data){
		var code = data.respCd;
		if (code == "0000")
		{
			if(data.lgnCrtfnYn == "Y"){
				//이메일 인증으로
				alert(msgCtrl.getMsg("success.co.login.auth.email"));
				location.replace("/mngwsercgateway/email");
			}
			else
			{
				// 이메일 인증 없이 로그인 처리
				location.replace(data.rdctUrl);
			}
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
		// 1210:임시 비밀번호, 1310:첫 로그인, 1410:비밀번호 변경주기(3개월) 초과
		else if (code == "1210" || code == "1310" || code == "1410")
		{
			if (code == "1210" || code == "1310")
			{
				alert(msgCtrl.getMsg("fail.co.login.password.temporary"));
			}
			else if (code == "1410")
			{
				alert(msgCtrl.getMsg("fail.co.login.password.changeCycle"));
			}
			
			location.replace("/mngwsercgateway/change-password");
		}
		// 메뉴 접근불가
		else if (code == "2090")
		{
			alert(msgCtrl.getMsg("fail.co.login.menuInacc"));
		}
		// IP 접근불가
		else if (code == "1420")
		{
			alert(msgCtrl.getMsg("fail.http.status.C406"));
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
		}
	};

	//비밀번호 변경 콜백
	var callbackAjaxPwdChng = function(data){
		var rtnCode = data.respCd;

		if (rtnCode == "00")
		{
			alert(msgCtrl.getMsg("success.co.login.password.change"));
			location.replace("/mngwsercgateway/login");
		}
		else
		{
			if (rtnCode == "10")
			{
				alert(msgCtrl.getMsg("fail.co.password.previous_notMatch"));
				jQuery("#password").val("").focus();
			}
			else if (rtnCode == "20" || rtnCode == "30")
			{
				if (rtnCode == "20")
				{
					alert(msgCtrl.getMsg("fail.co.password.confirm"));
				}
				else if (rtnCode == "30")
				{
					alert(msgCtrl.getMsg("fail.co.password.previous"));
				}
				
				jQuery("#newPassword, #passwordConfirm").val("");
				jQuery("#newPassword").focus();
			}
			else if (rtnCode == "40")
			{
				alert(msgCtrl.getMsg("fail.http.status.C401"));
				location.replace("/mngwsercgateway/login");
			}
		}
	};

	//이메일 콜백
	var callbackAjaxLoginEmail = function(data){
		var rtnCode = data.respCd;
		if (rtnCode == "0000")
		{
			location.replace(data.rdctUrl);
		}
		else
		{
			alert(msgCtrl.getMsg("success.co.login.auth.emailNotCofirm"));
			$("input[name=emailAuthNum]").val("").focus();
		}
	};

	// set model
	ctrl.model = {
		id : {
			// do something...
		},
		classname : {
			// do something...
		},
		immediately : function() {
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
	                	cmmCtrl.frmAjax(callbackAjaxLogin, "/mngwsercgateway/login", $formObj1, "POST", "json", true);
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
					
					if (trgtObj.val().indexOf(loginId) > -1)
					{
						alert(msgCtrl.getMsg("fail.co.coc.password.includeId"));
						trgtObj.focus();
						return false;
					}

					if (/([a-zA-Z0-9!@#$%^&*()_\-+={}\[\]<>,.?\/])\1\1/.test(trgtObj.val()))
			        {
						alert(msgCtrl.getMsg("fail.co.coc.password.sameString"));
						trgtObj.focus();
						return false;
			        }
					
					if (!cmmCtrl.checkContString(trgtObj.val(), 4))
					{
						alert(msgCtrl.getMsg("fail.co.coc.password.contString"));
						trgtObj.focus();
						return false;
					}
					
					return true;
				},
				async : {
	                use : true,
	                func : function (){
	                	cmmCtrl.frmAjax(callbackAjaxPwdChng, "./change-password", $formObj2);
	                }
				}
			});

			//이메일 인증 폼
			var timer = null;
			var $formObj3= $("#frmLoginEmail");
			if($formObj3.size() > 0){
				var time = 299;
				var min = 0;
				var sec = 0;
				//타이머 설정
				timer = setInterval(function(){
					min = parseInt(time / 60);
					sec = time % 60;
					$("#authTime").text( cmmCtrl.pad(min, 2) + ":" + cmmCtrl.pad(sec, 2) );
					time--;
					if(time < 0){
						clearInterval( timer );
						location.replace("/");
					}
				}, 1000);
			}
			$formObj3.validation({
				validateType : {

				},
				msg : {
					confirm : {
						init : ""
					}
				},
				async : {
					use : true,
					func : function(){
						cmmCtrl.frmAjax(callbackAjaxLoginEmail, "/mngwsercgateway/email-auth", $formObj3);
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