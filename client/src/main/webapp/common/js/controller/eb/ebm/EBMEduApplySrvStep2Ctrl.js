define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplySrvStep2Ctrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	var submitFlag = true;

	var questionSet = function(surveyType) {


		$(".surveyList").each(function () {
			var $surveyObj = $(this);
			var surveyTypeData = $(this).data('survey-type');
			var cnt = 1;
			var subCnt = 1;
			$("." + surveyTypeData).each(function (index) {                         // 질문, 하위질문 번호를 구분하고 순서를 셋팅
				if ($(this).find('input[name=dpth]').val() == '2') {
					$("." + surveyTypeData + "questionTxt:eq(" + index + ")").text("Q" + eval(cnt - 1) + "-" + subCnt);
					$(this).addClass(eval(cnt - 1) + "-" + subCnt);
					subCnt = subCnt + 1;
				} else {
					$("." + surveyTypeData + "questionTxt:eq(" + index + ")").text("Q" + cnt);
					cnt = cnt + 1;
					subCnt = 1;
				}
			});

		});
	}


	// set model
	ctrl.model = {
		id : {
			btnSubmit : {
				event : {
					click : function() {
						if(submitFlag) {
							$(".loading-area").stop().fadeIn(200);
							$formObj.submit();
						}
						else {
							alert("현재 설문을 처리 중이니 잠시만 기다려주세요.");
						}
					}
				}
			},
		},
		classname : {
			answer : {
				event : {
					click : function() {
						var surveyList = $(this).closest('.survey-list');
						var surveyListInner = $(this).closest('.survey-list-inner');

						var nextNo = $(this).data("next-no");
						var dpth = surveyListInner.find("input[name=dpth]").val()
						if (dpth != 2){

						surveyList.find(".survey-list-inner:not(:eq(0)) input").prop('checked',false);
						surveyList.find('.survey-list-inner:not(:eq(0))').hide();
						surveyList.find('.survey-list-inner:not(:eq(0))').find('.answer').addClass("notRequired");
						}

						if (nextNo != ''){
							if (nextNo.indexOf(',') > 0){
								var nextNoSplit = nextNo.split(',');
								$(nextNoSplit).each(function(i){
									surveyList.find('.'+nextNoSplit[i]).show();
									surveyList.find('.'+nextNoSplit[i]).find('.answer').removeClass("notRequired");
								});
							}else{
								surveyList.find('.'+nextNo).show();
								surveyList.find('.'+nextNo).find('.answer').removeClass("notRequired");
							}
						}
					}
				}
			},
			textAreaSbjctRply : {
				event : {
					click : function() {
						// maxLength 검사
						if ($(this).is("[maxlength]"))
						{
							$(this).keyup(function(event){
								var length = cmmCtrl.checkMaxlength(this);
								$(this).parents(".form-textarea").find(".maxlengthText").text(length);
							});
						}
					}
				}
			},
		},
		immediately : function() {
			questionSet('EDU');

			// 유효성 검사
			$formObj.validation({
				async : {
					use : true,
					func : function (){
						var actionUrl = "./insert";

						var svRepnMst = {};
						svRepnMst.srvSeq = $formObj.find("input[name=srvSeq]").val();
						svRepnMst.rfncCd = $formObj.find("input[name=rfncCd]").val();
						svRepnMst.rfncSeq = $formObj.find("input[name=rfncSeq]").val();
						svRepnMst.memSeq = $formObj.find("input[name=rtnMemSeq]").val();

						svRepnMst.svSurveyQstnRspnDtlList = new Array();

						$(".surveyList").each(function(){
							var svQstnDtl = {};
							var $surveyListObj = $(this);
							svQstnDtl.srvTypeCd = $surveyListObj.find("input[name=srvTypeCd]").val();
							svQstnDtl.qstnSeq = $surveyListObj.find("input[name=qstnSeq]").val();
							var isttrSeq = $surveyListObj.find("input[name=isttrSeq]").val();

							if ($(this).find(".exmplList").size() > 0 ) {
								svQstnDtl.svSurveyExmplRspnDtlList = new Array();
								$(this).find(".exmplList").each(function(index){
									var svExmplDtl = {};
									if(svQstnDtl.srvTypeCd == 'QST03' || svQstnDtl.srvTypeCd == 'QST04') {
										svExmplDtl.sbjctRply = $(this).find(".answer").val();
										svExmplDtl.exmplSeq = $(this).find(".answer:checked").val();
									}else{
										svExmplDtl.exmplSeq = $(this).find(".answer:checked").val();
									}

									if (typeof svExmplDtl.exmplSeq != 'undefined' || typeof svExmplDtl.sbjctRply != 'undefined'){
										svExmplDtl.isttrSeq = isttrSeq;
										svQstnDtl.svSurveyExmplRspnDtlList.push(svExmplDtl);
									}
								});
							}
							svRepnMst.svSurveyQstnRspnDtlList.push(svQstnDtl);
						});

						if(submitFlag) {
							submitFlag = false;

							cmmCtrl.jsonAjax(function(data){
								location.href = "./srvStep3?detailsKey="+$formObj.find("input[name=edctnSeq]").val()+"&episdYear="+$formObj.find("input[name=episdYear]").val()+"&episdOrd="+$formObj.find("input[name=episdOrd]").val()+"&ptcptSeq="+$formObj.find("input[name=ptcptSeq]").val()
							}, actionUrl, svRepnMst, "text")
						}
					}
				},
				msg : {
					empty : {
						text : " 작성해주세요.",
						radio : " 작성해주세요.",
						checkbox : " 작성해주세요.",
					},
					confirm : {
						init : "저장하시겠습니까?"
					}
				}
			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});