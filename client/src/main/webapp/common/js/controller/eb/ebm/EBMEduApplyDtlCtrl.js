define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplyDtlCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	// set model
	ctrl.model = {
		id : {
			btnSearch : {
				event : {
					click : function() {
						cmmCtrl.setFormData($formObj);
						search(1);
					}
				}
			},
			//검색 초기화
			btnRefresh : {
				event : {
					click : function() {
						//FORM 데이터 전체 삭제
						var pageIndex 	= $formObj.find("#pageIndex").val();
						var listRowSize = $formObj.find("#listRowSize").val();
						var pageRowSize = $formObj.find("#pageRowSize").val();
						var csrfKey 	= $formObj.find("#csrfKey").val();
						var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
						//$formObj.clearForm();
						//FORM 전송 필수 데이터 삽입
						$formObj.find("#pageIndex").val( pageIndex );
						$formObj.find("#listRowSize").val( listRowSize );
						$formObj.find(".listRowSizeContainer").val( listRowSize );
						$formObj.find("#pageRowSize").val( pageRowSize );
						$formObj.find("#csrfKey").val( csrfKey );
						$formObj.find("input[name=srchLayer]").val( srchLayer );

						$("#q").val(null);
						$("#strtDt").val(null);
						$("#endDt").val(null);

						//캘린더 초기화
						//cmmCtrl.setPeriod(this, "", "", false);
						$("input[name='srchDateType']:first").prop("checked", true); //기간검색 방식 초기화
						$("#prntCd option:eq(0)").prop("selected", true).trigger("click"); //과정분류 초기화
						//학습방식, 접수상태 초기화
						$("input[type='checkbox']").each(function(){
							$(this).attr("checked", false);
						});


						//검색 로직 실행
						$formObj.find("#btnSearch").click();
					}
				}
			}

		},
		classname : {



			srvStart : {
				event : {
					click : function() {


						//미참여라서 설문참여 진행
						if(1 == 1){
							var edctnSeq = $(this).data("edctnseq");
							var episdYear = $(this).data("episdyear");
							var episdOrd = $(this).data("episdord");
							var srvSeq = $(this).data("srvseq");

							location.href="./srvStep1?detailsKey="+edctnSeq+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&srvSeq="+srvSeq;

						}else{
							alert("이미 설문에 참여 하였습니다.");
						}


					}
				}
			}

		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);


		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});