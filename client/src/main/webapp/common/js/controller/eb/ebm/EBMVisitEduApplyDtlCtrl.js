define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMVisitEduApplyDtlCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);


	// set model
	ctrl.model = {
		id : {
			visitEduCancelBtn : {
				event : {
					click : function() {
						if(confirm("교육을 취소하시겠습니까?"))
						{
							var memSeq = $("#memSeq").val();
							var vstSeq = $("#vstSeq").val();

							var seqObj = {};
							seqObj.memSeq = memSeq;
							seqObj.vstSeq = vstSeq;

							alert(memSeq);
							alert(vstSeq);
							cmmCtrl.jsonAjax(function(data){
								if(data == "Y"){
									alert("교육 신청이 취소되었습니다.");
									location.reload();
								}
							}, "/my-page/edu-apply/visitEduApplyCancel", seqObj, "text")
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

