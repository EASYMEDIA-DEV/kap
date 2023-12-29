define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/eba/EBACouseDtlCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	var $lctrFormObj = ctrl.obj.find("form").eq(1);

	var mapContainer = document.getElementById('eduRoom'), // 지도를 표시할 div
		mapOption = {
			center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level: 4 // 지도의 확대 레벨
		};

	// 지도를 생성합니다
	var map = new daum.maps.Map(mapContainer, mapOption);

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

			if(totCnt <= 10 ){
				$(".btn-wrap.add-load.align-center").remove();
			}else{

				var tempPage = (page === undefined || page == "") ? 1 : page;

				var rtnPage = 0;

				if((tempPage * 10)>totCnt){
					rtnPage = totCnt
				}else{
					rtnPage = (tempPage * 10);
				}

				$(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
			}

			$(".article-total-count.f-body2").find("span").text(totCnt);


			$(".item-count").text();

			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/education/apply/episdSelect", $formObj, "GET", "html");

	}

	var lctrSearch = function (page){
		//data로 치환해주어야한다.

		if(page != undefined){
			$lctrFormObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			ctrl.obj.find("#listLctrContainer").html(respObj);

			//전체 갯수
			var totCnt = ctrl.obj.find("#listLctrContainer").find("#totalCount").val();
			//총 건수
			console.log(totCnt);
			if(totCnt <= 10 ){
				ctrl.obj.find(".onlineEduContPopup").find(".btn-wrap.align-center").remove();
			}else{
				var tempPage = (page === undefined || page == "") ? 1 : page;
				var tempPage = (page === undefined || page == "") ? 1 : page;

				var rtnPage = 0;

				if((tempPage * 10)>totCnt){
					rtnPage = totCnt
				}else{
					rtnPage = (tempPage * 10);
				}

				ctrl.obj.find(".onlineEduContPopup").find(".btn-wrap.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");

			}

			$(".item-count").text();

			ctrl.obj.find("#listLctrContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $lctrFormObj, "listLctrContainer", "pagingContainer");
		}, "/education/apply/episdLctrDtlList", $lctrFormObj, "GET", "html");

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

			//회차 담당자문의 팝업
			popupPicPrevSet : {
				event : {
					click : function(e) {

						var picNm = $(e.target).parent().data("picnm");
						var picEmail = $(e.target).parent().data("picemail");
						var picTelNo = $(e.target).parent().data("pictelno");

						$(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(0).find("td").text(picNm);
						$(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(1).find("td").text(picEmail);
						$(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(2).find("td").text(picTelNo);

						openPopup('eduPersonInfoPopup', e);

					}
				}
			},

			//회차별 온라인강의 목록 팝업
			popupLctrPrevSet : {
				event : {
					click : function(e) {

						var episdYear = $(e.target).parent().data("episdyear");
						var episdOrd = $(e.target).parent().data("episdord");

						$("#lctrEpisdYear").val(episdYear);
						$("#lctrEpisdOrd").val(episdOrd);

						//listLcptContainer
						lctrSearch(1);

						openPopup('onlineEduContPopup', e);

					}
				}
			},

			//교육장 안내 팝업
			mapBtn : {
				event: {
					click: function (e) {
						if(jQuery(e.target).data("mapchk") == "N") {
							openPopup('educCenterInfoPopup', e, "Y");
						}

						if(jQuery(e.target).data("mapchk") == "Y") {

							//교육장명
							//대표 전화번호
							var bscAddr = $(e.target).data("bscaddr");//기본주소
							var dtlAddr = $(e.target).data("dtladdr");//상세주소

							//주소세팅 시작
							//var addrNm = $(this).prev().attr("addrNm");
							var addr = bscAddr + " " + dtlAddr; //$(this).prev().attr("addr");


							// 주소-좌표 변환 객체를 생성합니다
							var geocoder = new daum.maps.services.Geocoder();

							// 주소로 좌표를 검색합니다
							geocoder.addressSearch(addr, function (result, status) {

								// 정상적으로 검색이 완료됐으면
								if (status === daum.maps.services.Status.OK) {
									console.log(result[0].y);
									console.log(result[0].x);


									var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

									// 결과값으로 받은 위치를 마커로 표시합니다
									var marker = new kakao.maps.Marker({
										map: map,
										position: coords
									});

									// 인포윈도우로 장소에 대한 설명을 표시합니다
									/*var infowindow = new daum.maps.InfoWindow({
										content: '<div style="width:150px;text-align:center;padding:6px 0;">' + dtlAddr + '</div>'
									});
									infowindow.open(map, marker);*/

									// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
									map.setCenter(coords);


								}
							});
							//주소세팅 종료
							map.relayout();
							jQuery(e.target).data("mapchk", "N")
						}

					}
				}
			}





		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			search();

			$(document).on('DOMNodeInserted','.educCenterInfoPopup', function() {
				console.log(1);
				map.relayout();
			});
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});