define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	//교육차수관리  > 개인별 출석부

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBInformMailWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);


	// form Object
	var $formObj = jQuery("#mailFrm").eq(0);

	// set model
	ctrl.model = {
		id : {
			btnSearch : {
				event : {
					click : function() {



					}
				}
			}
		},
		classname : {

		},
		immediately : function(event) {

			// 유효성 검사
			$formObj.validation({

				before : function(){

					var isReturn = true;

					$formObj.find(".ckeditorRequired").each(function(e) {

						jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
						jQuery(this).val(jQuery(this).val().split("<").join("~!left!~"));
						jQuery(this).val(jQuery(this).val().split(">").join("~!right!~"));
						jQuery(this).val(jQuery(this).val().split("\'").join("~!singlecomma!~"));
						jQuery(this).val(jQuery(this).val().split("\"").join("~!doublecomma!~"));

						var editorVal = jQuery(this).val().length;

						if (editorVal < 1)
						{
							alert("내용을 입력해주세요.");
							$(this).focus();
							CKEDITOR.instances[jQuery(this).prop("id")].focus();

							// 에디터 최상단으로 스크롤 이동
							jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

							return false;
						}
					});

					return isReturn;
				},

				after : function() {
					var isValid = true, editorChk = true;

					if (!editorChk)
					{
						isValid = false;
					}

					return isValid;
				},
				async : {
					use : true,
					func : function (){

						$("#ptcptListContainer").find("input[name='delValueList']:checked").each(function(){

							var ptcptMail = $(this).closest("tr").find("td").eq(10).data("email");

							$formObj.append($('<input/>', {type: 'hidden', name: 'ptcptEmailList', value:ptcptMail }));

						});

						cmmCtrl.frmAjax(function(data){

							alert("메일이 발송되었습니다.");
							ctrl.obj.find(".close").click();

						}, "/mngwserc/eb/ebb/setInformSendMail", $formObj, "post", "json")

					}
				},
				msg : {
					confirm : {
						init : "메일을 발송하시겠습니까?"
					}
				}
			});

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});