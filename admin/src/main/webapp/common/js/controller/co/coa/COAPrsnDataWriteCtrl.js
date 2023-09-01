define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/coa/COAPrsnDataWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// create object
	

	// create function
	var callbackAjaxUpdate = function(data){
		if (parseInt(data.respCnt, 10) > 0)
        {
			alert(msgCtrl.getMsg("success.upd"));
			jQuery("input[type='password']").val("");
        }
		else
		{
			alert(msgCtrl.getMsg("fail.act"));
		}
	};
	
	// set model
	ctrl.model = {
		id : {
			// do something...
			email : {
				event : {
					keydown : function(e) {
						if (e.keyCode == 13)
						{
							e.preventDefault();
							//이메일 중복확인
							$(this).focusout();
							$("#frmData").submit();
						}
					}
				}
			}
		},
		classname : {
			// do something...
		},
		immediately : function(){
			// 유효성 검사
			var $formObj = jQuery("#frmData");
			var loginId = $formObj.data("loginId");
			$formObj.validation({
				msg : {
					confirm : {
						custom : [{
							action : "update",
							message : msgCtrl.getMsg("confirm.upd")
						}]
					}
				},
				before: function(){
					ctrl.obj.find("input[name=pwdCnfrm]").addClass("notRequired");
					ctrl.obj.find("input[name=pwd]").addClass("notRequired");
					if($.trim( ctrl.obj.find("input[name=pwd]").val() ) != ""){
						//비밀번호 데이터를 입력했을때 지워야함
						ctrl.obj.find("input[name=pwdCnfrm]").removeClass("notRequired");
					}
					if($.trim( ctrl.obj.find("input[name=pwdCnfrm]").val() ) != ""){
						//비밀번호 데이터를 입력했을때 지워야함
						ctrl.obj.find("input[name=pwd]").removeClass("notRequired");
					}
				},
				after : function(){
					// 비밀번호 정책 위반 확인
					var trgtObj = jQuery("#pwd");
					var isSuccess = true;

					if (trgtObj.val().indexOf(loginId) > -1)
					{
						alert(msgCtrl.getMsg("fail.co.coc.password.includeId"));
						trgtObj.focus();
						return false;
					}

					if (trgtObj.length > 0)
					{
						if (trgtObj.val().indexOf(jQuery("#id").text()) > -1)
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

						// 이전 비밀번호 사용여부 확인
						if(trgtObj.val()!= ""){
							cmmCtrl.frmAjax(function(data){
								if(data.useYn == "N"){
									alert(msgCtrl.getMsg("fail.co.password.previous"));
									isSuccess = false;
								}
							}, "/mngwserc/co/coa/pwd-check"  , $formObj, "post", "json", false, false);
						}
					}
					return isSuccess;
				},
				duplication : {
					use : true,
					type : [{
						type : "email",
						url : "./email-overlap-check",
						textboxId : "email",
						buttonId : "btnEmailOvrlChk"
					}]
				},
				async : {
	                use : true,
	                func : function (){
	                	cmmCtrl.frmAjax(callbackAjaxUpdate, "./api-profile-update", $formObj);
	                }
				}
			});
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});