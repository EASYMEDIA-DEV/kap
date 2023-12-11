define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBChangeEpisdWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);


	// form Object
	var $formObj = jQuery("#frmMemAtndcData").eq(0);


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



			tempBtn : {
				event : {
					click : function() {

						var ptcptSeq = $("button.tempBtn").data("ptcptseq");

						$("#ptcptSeq").val(ptcptSeq);

						cmmCtrl.frmAjax(function(respObj) {

							ctrl.obj.find(".memAtndcContainer").html(respObj);

						}, "/mngwserc/eb/ebb/memAtndcList", $formObj, "POST", "html");
					}
				}
			},

		},
		immediately : function(event) {

			var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

			// 유효성 검사
			$formObj.validation({
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

						var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


						var actionForm = {};

						var resultFlag = true;

						actionForm.edctnSeq = $("#chan_edctnSeq").val();//교육순번
						actionForm.episdSeq = $("#chan_episdSeq").val();//회차순번
						actionForm.episdYear =$("#chan_episdYear").val();//연도
						actionForm.episdOrd = $("#chan_episdOrd").val();//회차정렬


						var memSeq = $("#chan_memSeq").val();
						var memList = new Array();

						if(memSeq.indexOf(",")>-1){
							var memArray= memSeq.split(",");

							for(var i=0; i<memArray.length; i++){

								var memForm = {};

								memForm.edctnSeq = $("#chan_edctnSeq").val();
								memForm.episdSeq = $("#chan_episdSeq").val();
								memForm.episdYear = $("#chan_episdYear").val();
								memForm.episdOrd = $("#chan_episdOrd").val();

								memForm.ptcptSeq = memArray[i];

								memList.push(memForm);
							}
						}else{

							var memForm = {};

							memForm.edctnSeq = $("#chan_edctnSeq").val();
							memForm.episdSeq = $("#chan_episdSeq").val();
							memForm.episdYear = $("#chan_episdYear").val();
							memForm.episdOrd = $("#chan_episdOrd").val();

							memForm.ptcptSeq = memSeq;

							memList.push(memForm);
						}


						actionForm.ptcptList = memList;



						debugger;
						if(resultFlag){

							cmmCtrl.jsonAjax(function(data){
								alert("저장되었습니다.");
								location.href = "./list";
							}, "./changeEpisd", actionForm, "text");
						}



					}
				},
				msg : {
					empty : {
						text : " 입력해주세요."
					}
				}
			});

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});