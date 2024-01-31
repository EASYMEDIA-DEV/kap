define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplyDtlCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	var mapContainer = document.getElementById('eduRoom'), // 지도를 표시할 div
		mapOption = {
			center: new daum.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level: 4 // 지도의 확대 레벨
		};

	// 지도를 생성합니다
	var map = new daum.maps.Map(mapContainer, mapOption);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	var $lctrFormObj = ctrl.obj.find("form").eq(1);

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

			cmptnPop :{

				event : {
					click: function () {

						// method 1 (새 윈도우 창 열어서 프린트 후 닫기)
						var popUrl = "/my-page/edu-apply/cmPtm?detailsKey=28&episdYear=2024&episdOrd=4&ptcptSeq=137";
						var popOption = "top=10, left=10, width=1080, height=1528, scrollbars=no, status=no, menubar=no, toolbars=no, resizable=no";
						var myWindow = window.open(popUrl, popOption);
						myWindow.document.close();
						myWindow.focus();

						myWindow.onafterprint = function () { //프린터 출력 후 이벤트
							myWindow.close();
						}

						myWindow.print();

					}
				}

			},

			transfer : {
				event : {
					click : function(){
						var edctnSeq = $("#edctnSeq").val();
						var episdYear = $("#episdYear").val();
						var episdOrd = $("#episdOrd").val();
						var ptcptSeq = $("#ptcptSeq").val();

						location.href="./transfer?detailsKey="+edctnSeq+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&ptcptSeq="+ptcptSeq;
					}
				}
			},

			atndcCheck : {
				event: {
					click: function () {
						//QR코드로 접근시 출석체크를 진행한다

						var seqObj = {};
						var ptcptSeq = $("#ptcptSeq").val();

						seqObj.ptcptSeq = ptcptSeq;

						if(confirm("출석 처리 하시겠습니까?")){
							cmmCtrl.jsonAjax(function(data){

								if(data == "Y"){
									alert("출석 처리되었습니다.");
									location.reload();
								}

							}, "/my-page/edu-apply/updateAtndcInfo", seqObj, "text")
						}


					}
				}
			},

			lvgrmCheck : {
				event: {
					click: function () {
						//퇴실체크를 진행한다.

						var seqObj = {};
						var ptcptSeq = $("#ptcptSeq").val();

						seqObj.ptcptSeq = ptcptSeq;

						if(confirm("퇴실 처리 하시겠습니까?")){
							cmmCtrl.jsonAjax(function(data){

								if(data == "Y"){
									alert("퇴실 처리되었습니다.");
									location.reload();
								}

							}, "/my-page/edu-apply/updateLvgrmInfo", seqObj, "text")
						}


					}
				}
			},

			examStart : {
				event: {
					click: function () {
						location.href="/my-page/education/exam?ptcptSeq="+$("#ptcptSeq").val()+"&memSeq="+$("#memSeq").val();

					}
				}
			},

			srvStart : {
				event : {
					click : function() {


						//미참여라서 설문참여 진행
						if($("#srvYn").val() == "N"){
							var edctnSeq = $(this).data("edctnseq");
							var episdYear = $(this).data("episdyear");
							var episdOrd = $(this).data("episdord");
							var srvSeq = $(this).data("srvseq");

							location.href="./srvStep1?detailsKey="+edctnSeq+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&srvSeq="+srvSeq+"&ptcptSeq="+$("#ptcptSeq").val();

						}else{
							alert("이미 설문에 참여 하였습니다.");
						}


					}
				}
			},

			//회차별 온라인강의 목록 팝업
			popupLctrPrevSet : {
				event : {
					click : function(e) {


						var episdYear = ($(e.target).parent().data("episdyear") === undefined) ? $(e.target).data("episdyear") : $(e.target).parent().data("episdyear");
						var episdOrd = ($(e.target).parent().data("episdord") === undefined) ? $(e.target).data("episdord") : $(e.target).parent().data("episdord");

						$("#lctrEpisdYear").val(episdYear);
						$("#lctrEpisdOrd").val(episdOrd);

						//listLcptContainer
						lctrSearch(1);

						openPopup('onlineEduContPopup', e);

					}
				}
			},

			//회차 담당자문의 팝업
			popupPicPrevSet : {
				event : {
					click : function(e) {

						var picNm = ($(e.target).parent().data("picnm") === undefined) ? $(e.target).data("picnm") : $(e.target).parent().data("picnm");
						var picEmail = ($(e.target).parent().data("picemail") === undefined) ? $(e.target).data("picemail") : $(e.target).parent().data("picemail");
						var picTelNo = ($(e.target).parent().data("pictelno") === undefined) ? $(e.target).data("pictelno") : $(e.target).parent().data("pictelno");

						$(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(0).find("td").text(picNm);
						$(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(1).find("td").text(picEmail);
						$(".eduPersonInfoPopup").find("table.basic-table").find("tr").eq(2).find("td").text(picTelNo);

						openPopup('eduPersonInfoPopup', e);

					}
				}
			},

			onlineStep : {
				event : {
					click: function (e) {

						var detailsKey = $("#edctnSeq").val();
						var episdOrd = $("#episdOrd").val();
						var episdYear = $("#episdYear").val();

						location.href="./onlineStep1?detailsKey="+detailsKey+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&ptcptSeq="+$("#ptcptSeq").val();

					}
				}
			},

			applyCancel : {
				event : {
					click: function (e) {
						if(confirm("교육 신청을 취소하시겠습니까?")){

							var ptcptSeq = $("#ptcptSeq").val();

							var seqObj = {};
							seqObj.ptcptSeq = ptcptSeq;

							cmmCtrl.jsonAjax(function(data){

								if(data == "Y"){
									alert("교육 신청이 취소되었습니다.");
									location.reload();
								}

							}, "/my-page/edu-apply/applyCancel", seqObj, "text")



						}
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

							var placeNm = $(e.target).data("nm");//교육장명
							var rprsntTelNo =  $(e.target).data("rprsnttelno");//대표 전화번호
							var zipcode = $(e.target).data("zipcode");//우편번호
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

							$(".educCenterInfoPopup").find("table.basic-table").find("tr").eq(0).find("td").text(placeNm);//교육장명
							$(".educCenterInfoPopup").find("table.basic-table").find("tr").eq(1).find("td").text("["+zipcode+"] "+addr);//주소
							$(".educCenterInfoPopup").find("table.basic-table").find("tr").eq(2).find("td").text(rprsntTelNo);//대표전화

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