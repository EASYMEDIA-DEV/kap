define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/eba/EBACouseStepCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

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


	var setPtcptInfo = function(){


		//교육 취소, 변경, 삭제의 이유로 변동이 있을경우 알럿띄우고 교육상세로 넘김
		if(1 == 2){
			alert("교육 정보가 변경되었습니다. 다시 신청 바랍니다.");
			location.href="./detail?detailsKey="+$("#edctnSeq").val();
		}


		var seqObj = {};
		seqObj.edctnSeq = $("#edctnSeq").val();
		seqObj.episdYear = $("#episdYear").val();
		seqObj.episdOrd = $("#episdOrd").val();
		seqObj.episdSeq = $("#episdSeq").val();
		seqObj.stduyMthdCd = $("#stduyMthdCd").val();//학습방식, 온라인이면 출석정보 등록 안함

		//정원수 체크
		cmmCtrl.jsonAjax(function(data){
			if(data !=""){
				var rtn = JSON.parse(data);
				//정원여유
				if(rtn.fxnumStta == "S"){
					//alert("등록시작");

					cmmCtrl.frmAjax(function(resultData){

						var rtnData = resultData.rtnData;
						if(rtnData.regStat == "F"){
							alert("이미 해당 회차에 신청한 회원입니다.");
						}else if(rtnData.regStat == "S"){
							alert("저장되었습니다.");
						}

					}, "./setPtcptInfo", $formObj, "post", "json");

					//정원초과
				}else{
					alert("정원이 초과되었습니다.");
					return false;
				}
			}

		}, "/mngwserc/eb/ebb/fxnumChk", seqObj, "text")



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

			setPtcptInfo : {
				event: {
					click: function (e) {

						//gpc 인증 부분






						if($("#agreeChk").is(":checked")){
							setPtcptInfo(this);

						}else{
							alert("개인정보 사용 동의 약관에 동의해주세요.");
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
			},





		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			//cmmCtrl.setFormData($formObj);
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});