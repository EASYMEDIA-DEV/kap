define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/eba/EBACouseListCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);


	// 목록 조회
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


			$(".item-count").text();

			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/education/apply/select", $formObj, "GET", "html");

	}

	//과정분류 조회
	var selectCtgryCdList = function(arg){

		if($(arg).val() == "" || $(arg).val() === undefined){
			arg = $("#ctgryCd").data("ctgrycd");
		}

		var cdMst= {};
		cdMst.cd = $(arg).val();

		cmmCtrl.jsonAjax(function(data){
			callbackAjaxCtgryCdList(data);

			//데이터가 없으면 2뎁스 사용 불가처리 함
			if(arg == "" || arg === undefined){
				$("#ctgryCd").attr("readonly", true).attr("disabled", true);
			}else{
				$("#ctgryCd").attr("readonly", false).attr("disabled", false);
			}

		}, '/education/classTypeList', cdMst, "text");
	}

	//과정분류 2뎁스 세팅
	var callbackAjaxCtgryCdList = function(data){

		var detailList = JSON.parse(data);
		var selectHtml = "<option value=''>전체</option>";

		for(var i =0; i < detailList.length; i++){

			var cd = detailList[i].cd;
			var cdNm = detailList[i].cdNm;

			selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
		}

		$("#ctgryCd option").remove();

		$("#ctgryCd").append(selectHtml);

		var ctgrycd = $("#ctgryCd").data("ctgrycd");

		$("#ctgryCd").val(ctgrycd).prop("selected", true);//조회된 과정분류값 자동선택
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
			},

			srchOrder : {
				event : {
					change : function(){
						//교육신청 정렬조건 변경
						search(1);
					}
				}
			}

		},
		classname : {

			classType : {
				event : {
					click : function() {
						selectCtgryCdList(this);
					}
				}
			},


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
			},

			episdDtl : {
				event : {
					click : function(e){
						var edctnseq = $(e.target).closest("div").data("edctnseq");
						location.href="/education/apply/detail?detailsKey="+edctnseq;
					}
				}
			},

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