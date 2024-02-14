define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COTotalSrchCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);
	// 선택한 메뉴타입
	var menuType = $formObj.find("input[name=menuType]").val();
	// q=검색어
	var q = $.trim($formObj.find("input[name=q]").val());


	// set model
	ctrl.model = {
		id : {

		},
		classname : {
			// do something...
			frontTotalKeyword : {
				event : {
					click : function() {
						location.href="/search?q=" + $(this).text();
					}
				}
			},
			//교육 상세보기
			episdDtl : {
				event: {
					click: function () {
						var edctnseq = $(this).data("edctnseq");
						location.href = "/education/apply/detail?detailsKey="+edctnseq;

					}
				}
			},
			//뉴스레터 상세보기
			nwslttrListView: {
				event: {
					click: function () {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						var url = "/foundation/board/newsletter/view?detailsKey=" + detailsKey;
						location.href = url;
					}
				}
			},
			//재단뉴스 상세보기
			foundationListView: {
				event: {
					click: function () {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						var url = "/foundation/board/company-news/view?detailsKey=" + detailsKey;
						location.href = url;
					}
				}
			},
			//공지 상세보기
			noticeListView: {
				event: {
					click: function () {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						var url = "/foundation/board/notice/view?detailsKey=" + detailsKey + "&mainPostYn=N";
						location.href = url;
					}
				}
			},

		},
		immediately : function() {


			$("#headerSrchFrm").find("input[name=q]").val( q );
			// 유효성 검사
			$formObj.validation({
				before: function(){

				},
				after : function() {
					var isValid = true
					return isValid;
				},
				customfunc : function(obj, tagid, okval, msg){

				},
				async : {
					use : true,
					func : function (){
						location.href="/search?q=" + $formObj.find("input[name=q]").val();
					}
				},
				msg : {
					confirm : {
						init : ""
					}
				}
			});

			//교육 조회
			if( parseInt($("#episdList").data("cnt")) > 0){
				cmmCtrl.listFrmAjax(function(respObj) {
					$("#episdContainer").html(respObj);
					//링크연결 여기서
				}, "/education/apply/select", $formObj, "GET", "html", false, false);
			}
			//공지 조회
			if( parseInt($("#noticeList").data("cnt")) > 0){
				cmmCtrl.listFrmAjax(function(respObj) {
					$("#noticeContainer").html(respObj);
					//링크연결 여기서
				}, "/foundation/board/notice/select?mainYn=Y", $formObj, "GET", "html", false, false);
			}
			//재단뉴스
			if( parseInt($("#foundationList").data("cnt")) > 0) {
				cmmCtrl.listFrmAjax(function (respObj) {
					$("#foundationContainer").html(respObj);
					//링크연결 여기서
				}, "/foundation/board/company-news/select", $formObj, "GET", "html", false, false);
			}
			//뉴스레터
			if( parseInt($("#letterList").data("cnt")) > 0) {
				cmmCtrl.listFrmAjax(function (respObj) {
					$("#letterContainer").html(respObj);
					//링크연결 여기서
				}, "/foundation/board/newsletter/select", $formObj, "GET", "html", false, false);
			}

			// 교육/세미나
			cmmCtrl.listFrmAjax(function(respObj) {
				$("#episdContainer").html(respObj);
				//링크연결 여기서
			}, "/education/apply/select", $formObj, "GET", "html", false, false);

			//메뉴
			cmmCtrl.listFrmAjax(function(respObj) {
				//링크연결 여기서
			}, "/search", $formObj, "GET", "html", false, false);

			//메뉴 탭
			if(menuType !=null && menuType != "" && menuType != 'menu') {
				//뉴스레터 탭 클릭
				if(menuType == 'newsletter')
					cmmCtrl.listFrmAjax(function(respObj) {
						$("#letterTabContainer").html(respObj);
						//링크연결 여기서
					}, "/foundation/board/newsletter/search/newsletter", $formObj, "GET", "html", false, false);
			}
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});