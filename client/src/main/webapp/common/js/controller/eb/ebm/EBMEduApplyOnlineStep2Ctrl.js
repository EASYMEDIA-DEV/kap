define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplyOnlineStep2Ctrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	var lctrSearch = function (page){
		//data로 치환해주어야한다.

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			$("#viewContainer").html(respObj);

			//총 건수
			console.log(respObj);


		}, "/my-page/edu-apply/onlineStep2Select", $formObj, "GET", "html");

	}


	// set model
	ctrl.model = {
		id : {
			btnSubmit : {
				event : {
					click : function() {
						$formObj.submit();
					}
				}
			},
		},
		classname : {

			//온라인강의 페이징 처리
			lctrPageSet : {
				event : {
					click : function() {
						//페이징 이동


						var pageIndex = $lctrFormObj.find("input[name=pageIndex]").val();
						lctrSearch(++pageIndex);
					}
				}
			},


			onlineStep1 : {
				event : {
					click: function () {

						var detailsKey = $("#edctnSeq").val();
						var episdOrd = $("#episdOrd").val();
						var episdYear = $("#episdYear").val();
						var ptcptSeq = $("#ptcptSeq").val();

						location.href="./onlineStep1?detailsKey="+detailsKey+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&ptcptSeq="+ptcptSeq;

					}
				}
			},

			btnPrevStep : {
				event : {
					click: function () {
						$("#nowLctrSeq").val($(this).data("prevseq"));
						lctrSearch();

					}
				}
			},
			btnNextStep : {
				event : {
					click: function () {
						$("#nowLctrSeq").val($(this).data("nextseq"));
						lctrSearch();

					}
				}
			},
			onlineStep3 : {
				event : {
					click: function () {
						var detailsKey = $("#edctnSeq").val();
						var episdOrd = $("#episdOrd").val();
						var episdYear = $("#episdYear").val();
						var ptcptSeq = $("#ptcptSeq").val();

						location.href="./onlineStep3?detailsKey="+detailsKey+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&ptcptSeq="+ptcptSeq;

					}
				}
			},



			onlineStep : {
				event : {
					click : function() {

						if($("#onlineStepYn").val() == "Y"){

							var detailsKey = $("#edctnSeq").val();
							var episdYear = $("#episdYear").val();
							var episdOrd = $("#episdOrd").val();
							var ptcptSeq = $("#ptcptSeq").val();

							var lctrSeq = $(this).data("lctrseq");

							location.href="./onlineStep2?edctnSeq="+detailsKey+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&lctrSeq="+lctrSeq+"&ptcptSeq="+ptcptSeq;
						}


					}
				}
			},

		},
		immediately : function() {

			lctrSearch();

			// 유효성 검사
			$formObj.validation({
				async : {
				}
			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});