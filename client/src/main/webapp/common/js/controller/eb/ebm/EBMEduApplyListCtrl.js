define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplyListCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);
	var $formLayerObj = ctrl.obj.find("form").eq(1);


	// 신청한 과정 목록 조회
	var search = function (page){
		//data로 치환해주어야한다.

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			ctrl.obj.find("#listContainer").html(respObj);

			//전체 갯수
			var totCnt = $("#totalCount").val();
			//총 건수

			if(totCnt <= 9 ){
				$(".btn-wrap.add-load.align-center").remove();
			}else{
				var tempPage = (page === undefined || page == "") ? 1 : page;

				var rtnPage = 0;

				if((tempPage * 9)>totCnt){
					rtnPage = totCnt
				}else{
					rtnPage = (tempPage * 9);
				}

				$(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");

			}

			$(".article-total-count.f-body2").find("span").text(totCnt);


			$(".item-count").text(totCnt);

			ctrl.obj.find("#listContainerTotCnt").text(totCnt);

			var eduCnt = 0;
			//신청한 과정중 학습중인 과정 갯수만 카운트
			$(".training-confirm").find(".eduStat").each(function(){

				var eduStat = $(this).text();
				if(eduStat == "교육중") eduCnt++;

			});

			$(".user-tit").find(".userEduCnt").text(eduCnt+"개");

			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/my-page/edu-apply/select", $formObj, "GET", "html");

	}


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

			//페이징 처리
			pageSet : {
				event : {
					click : function() {
						//페이징 이동

						var pageIndex = $formObj.find("input[name=pageIndex]").val();
						search(++pageIndex);

					}
				}
			},
			//페이징 목록 갯수
			listRowSizeContainer : {
				event : {
					change : function(){
						//리스트 갯수 변경
						$formObj.find("input[name=listRowSize]").val($(this).val());
						search(1);
					}
				}
			}

		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			search();

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});