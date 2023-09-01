define(["ezCtrl", "ezVald", "controller/co/COMenuCtrl"], function(ezCtrl, ezVald, menuCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/coa/COAAdmWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");

	var type = !$formObj.find("input[name='detailsKey']").val() ? "insert" : "update";

	// 저장 콜백
	var callbackAjaxSave = function(data){
		var successMsg = type == "insert" ? "success.ins" : "success.upd";
		if (parseInt(data.respCnt, 10) > 0)
		{
			alert(msgCtrl.getMsg(successMsg));
			location.href = "./list";
		}
		else
		{
			alert(msgCtrl.getMsg("fail.act"));
		}
	}

	// 삭제 콜백
	var callbackAjaxDelete = function(data){
		if (parseInt(data.respCnt) > 0)
		{
			alert(msgCtrl.getMsg("success.del.target.account"));
			location.href = "./list";
		}
		else
		{
			alert(msgCtrl.getMsg("fail.act"));
		}
	}

	// set model
	ctrl.model = {
		id : {
			// do something...
			btnPwdInit : {
				event : {
					click : function() {
						//if (confirm(msgCtrl.getMsg("confirm.co.coa.pwdInit")))
						//{
							$formObj.find("input[name='id']").val(jQuery(this).data("id"));
							cmmCtrl.frmAjax(function (data){
								if(parseInt(data.respCd, 0) > 0)
								{
									alert(msgCtrl.getMsg("success.co.coa.pwdInit"));
								}
								else
								{
									alert(msgCtrl.getMsg("fail.act"));
								}
							}, "/mngwserc/co/coa/pwd-init", $formObj, "post", "json", true);

						//}
					}
				}
			},
			btnList : {
				event : {
					click : function (){
						if(confirm(msgCtrl.getMsg("confirm.list")))
						{
							location.href = "./list?"+$(this).data("param");
						}
					}
				}
			},
			btnOneDelete : {
				event : {
					click : function(){
						// 최고 관리자 또는 본인 계정은 삭제 불가처리
						if($("#authCd").val() == 99 || $("#lgnSsnId").val() == $("input[name='id']").val()){
							alert(msgCtrl.getMsg("fail.co.coa.cantDltAccounts"));
							return;
						}

						if(confirm(msgCtrl.getMsg("confirm.del")))
						{
							cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj, "post", "json");
						}
					}
				}
			}
		},
		classname : {
			// do something...
		},
		immediately : function(){
			menuCtrl.jstreeInfo.types.types.folder.start_drag = false;
			menuCtrl.jstreeInfo.types.types.folder.move_node = false;
			menuCtrl.jstreeInfo.types.types.cms.move_node = false;

			menuCtrl.jstreeInfo.plugins.push("checkbox");

			menuCtrl.jstreeInfo.themes = { "icons" : false };

			menuCtrl.setJstree(true, false, { topNode : "1" , menuSeq:"1", uid : jQuery("#detailsKey").val() }, "./menu-list");


			// 유효성 검사
			$formObj.validation({
				msg : {
					confirm : {
						custom : [{
							action : "insert",
							message : msgCtrl.getMsg("confirm.ins")
						},{
							action : "update",
							message : msgCtrl.getMsg("confirm.upd")
						}]
					}
				},
				after : function(){
					// 비밀번호 정책 위반 확인
					var trgtObj = jQuery("#pwd");
					
					if (trgtObj.length > 0)
					{
						if (trgtObj.val().indexOf(jQuery("#id").val()) > -1)
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
					}

					// 체크된 메뉴 설정
					menuCtrl.setJstreeChecked();

					// 최고관리자가 아닐때에 선택된 메뉴있는지 확인
					if($("#mChecked").val() == "" && $("#authCd").val() != 99){
						alert(msgCtrl.getMsg("fail.co.coa.notSelectMenu"));
						return false;
					}else{
						return true;
					}
				},
				duplication : {
					use : true,
					type : [{
						type : "id",
						url : "/mngwserc/co/coa/id-overlap-check",
						textboxId : "id",
						buttonId : "btnIdOvrlChk"
			        }, {
						type : "email",
						url : "/mngwserc/co/coa/email-overlap-check",
						textboxId : "email",
						buttonId : "btnEmailOvrlChk"
					}]
				},
				async : {
	                use : true,
	                func : function (){
						cmmCtrl.frmAjax(callbackAjaxSave, "./" + type , $formObj);
	                }
				}
			});

			// 최고 관리자는 메뉴선택 불가(모든메뉴 접근 가능)
			if($("input[name='authCd']").val() == "99"){
				setTimeout(function() {
					$("#divCategoris").jstree("uncheck_all");
					$("#divCategoris").find(".jstree-checkbox").prop("disabled", true);
				}, 100);
			}
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});