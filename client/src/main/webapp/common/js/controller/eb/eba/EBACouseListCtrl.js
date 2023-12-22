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
		console.log("@#@@@");
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
				$(".btn-wrap.add-load.align-center").find(".item-count").text("("+(tempPage*9)+"/"+totCnt+")");
			}

			$(".article-total-count.f-body2").find("span").text(totCnt);


			$(".item-count").text();

			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/education/apply/select", $formObj, "GET", "html");

	}

	var callbackAjaxCdList  = function (cdList){

		console.log(cdList);

		var detailList = cdList.detailList;

		ctrl.obj.find("#cdListContainer").html(cdList);

		$(".detailCdList").html(tempHtml);


	}


	// set model
	ctrl.model = {
		id : {
			btnSearch : {
				event : {
					click : function() {
						//검색버튼 클릭시
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
						$formObj.clearForm();
						//FORM 전송 필수 데이터 삽입
						$formObj.find("#pageIndex").val( pageIndex );
						$formObj.find("#listRowSize").val( listRowSize );
						$formObj.find(".listRowSizeContainer").val( listRowSize );
						$formObj.find("#pageRowSize").val( pageRowSize );
						$formObj.find("#csrfKey").val( csrfKey );
						$formObj.find("input[name=srchLayer]").val( srchLayer );

						//캘린더 초기화
						cmmCtrl.setPeriod(this, "", "", false);

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

						$(".cdListContainer").css("display","none");
						$(".cdListContainer").attr("disabled", true);
						$(".cdListContainer").find("input:checkbox").prop("checked", false);


						$(".classType input:checked").each(function(){
							console.log($(this).val());

							var checkVal = $(this).val();

							var cdnm = $(this).data("cdnm"); //내일 이거 해야됨 클릭한것의 cdnm값 갖고오기
							$("."+checkVal).find(".cdnm").html(cdnm);
							$("."+checkVal).css("display","block");

							$("."+checkVal).find("input:checkbox").attr("disabled", false);
							console.log(cdnm);
							$("."+checkVal).find("input:checkbox").find("span").append(cdnm+"23434");


						});

						if($(".classType input:checked").length == 0){
							$(".cdListContainer").css("display","none");
							$(".cdListContainer").attr("disabled", true);
							$(".cdListContainer").find("input:checkbox").prop("checked", false);
						}

					}
				}
			},


			//페이징 처리
			pageSet : {
				event : {
					click : function() {
						//페이징 이동
						if( $(this).attr("value") != "null" ){
							$formObj.find("input[name=pageIndex]").val($(this).attr("value"));
							search();
						}
					}
				}
			},
			//상세보기
			listView : {
				event : {
					click : function() {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						$formObj.find("input[name=detailsKey]").val(detailsKey);
						location.href = "./write?" + $formObj.serialize();
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
						var episdSeq = $(e.target).closest("div").data("episdseq");
						$("#episdSeq").val(episdSeq);
						$formObj.attr("action", "/education/apply/detail");
						$formObj.submit();

						//cmmCtrl.frmAjax(false, "/education/apply/detail", $formObj, "post", "json")
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