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


	//교육상세에서 비로그인->신청하기 눌렀을경우 여기서 일부 신청조건 체크
	var chkInfo = function() {

		var authCd = $("#authCd").val();

		var seqObj = {};
		seqObj.edctnSeq = $("#edctnSeq").val();
		seqObj.detailsKey = $("#edctnSeq").val();
		seqObj.episdYear = $("#episdYear").val();
		seqObj.episdOrd = $("#episdOrd").val();
		seqObj.episdSeq = $("#episdSeq").val();

		seqObj.stduyMthdCd = $("#stduyMthdCd").val();//학습방식, 온라인이면 출석정보 등록 안함

		//부품사 회원이 아닌경우
		if(authCd == "CO"){
			alert("교육신청은 부품사 회원만 신청 가능합니다.");
			location.href="./detail?detailsKey="+$("#edctnSeq").val();
		}else if(authCd == "CS"){
			alert("위원회원은 해당 서비스를 이용 할 수 없습니다.");
			location.href="./detail?detailsKey="+$("#edctnSeq").val();
		}

		//교육 취소, 변경, 삭제의 이유로 변동이 있을경우 알럿띄우고 교육상세로 넘김
		cmmCtrl.jsonAjax(function(data){

			if(data != "A"){
				alert("교육 정보가 변경되었습니다. 다시 신청 바랍니다.");
				location.href="./detail?detailsKey="+$("#edctnSeq").val();
			}

			return false;

		}, "/education/apply/EpisdChk", seqObj, "text")


		//정원수 체크
		cmmCtrl.jsonAjax(function(data){
			if(data !=""){
				var rtn = JSON.parse(data);
				//정원여유
				if(rtn.fxnumStta == "S"){
				}else{
					alert("교육 가능한 인원이 초과되었습니다. ");
					location.href="./detail?detailsKey="+$("#edctnSeq").val();
				}
			}

		}, "/education/apply/fxnumChk", seqObj, "text")

	}

	var setPtcptInfo = function(){


		var authCd = $("#authCd").val();

		var seqObj = {};
		seqObj.edctnSeq = $("#edctnSeq").val();
		seqObj.detailsKey = $("#edctnSeq").val();
		seqObj.episdYear = $("#episdYear").val();
		seqObj.episdOrd = $("#episdOrd").val();
		seqObj.episdSeq = $("#episdSeq").val();

		seqObj.stduyMthdCd = $("#stduyMthdCd").val();//학습방식, 온라인이면 출석정보 등록 안함

		//부품사 회원이 아닌경우
		if(authCd == "CO"){
			alert("교육신청은 부품사 회원만 신청 가능합니다.");
			return false;
		}else if(authCd == "CS"){
			alert("위원회원은 해당 서비스를 이용 할 수 없습니다.");
			return false;
		}



		//교육 취소, 변경, 삭제의 이유로 변동이 있을경우 알럿띄우고 교육상세로 넘김
		cmmCtrl.jsonAjax(function(data){

			if(data != "A"){
				alert("교육 정보가 변경되었습니다. 다시 신청 바랍니다.");
				location.href="./detail?detailsKey="+$("#edctnSeq").val();
			}

			return false;

		}, "/education/apply/EpisdChk", seqObj, "text")


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
						}else if(rtnData.regStat == "R"){
							alert("필수과목 수료후 신청 가능합니다.");

						}else if(rtnData.regStat == "S"){

							if(confirm("위 정보로 교육을 신청하시겠습니까?")){
								//신청 진행
								location.href="/education/apply/step2?detailsKey="+seqObj.edctnSeq+"&episdSeq="+seqObj.episdSeq+"&episdYear="+seqObj.episdYear+"&episdOrd="+seqObj.episdOrd;
							}
						}

					}, "./setPtcptInfo", $formObj, "post", "json");

					//정원초과
				}else{
					alert("교육 가능한 인원이 초과되었습니다. ");
					return false;
				}
			}

		}, "/education/apply/fxnumChk", seqObj, "text")



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

			cancelApply : {
				event : {
					click : function(e){

						if(confirm("교육 취소 시 입력된 정보는 저장되지 않습니다. \n교육 신청을 취소하시겠습니까?")){
							location.href="/education/apply/detail?detailsKey="+$("#edctnSeq").val();
						}

					}
				}
			},

			applyInfo : {
				event : {
					click : function(e){
						var edctnSeq = $("#edctnSeq").val();
						var episdYear = $("#episdYear").val();
						var episdOrd = $("#episdOrd").val();
						var ptcptSeq = $("#ptcptSeq").val();

						location.href="/my-page/edu-apply/detail?detailsKey="+edctnSeq+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&ptcptSeq="+ptcptSeq;

					}
				}
			},




			//GPC 아이디 인증 API
			gpcCheck : {
				event : {
					click : function(e) {

						var seqObj = {};
						var gpcId = $("#gpcId").val();

						seqObj.gpcId = gpcId;


						var passFlag = true;

						//gpcId 인증 체크
						cmmCtrl.jsonAjax(function(data){

							var rtn = JSON.parse(data);
							var kapUserId = rtn.kapUserId; //gpc에서 전달받은 kap아이디
							var isGpcUser = rtn.isGpcUser; //gpc에서 전달받은 gpc가입여부(아이디가 있는지)
							var gndrCd = rtn.gndrCd; //gpc에서 전달받은 gpc아이디로 가입된 성별

							var nowId = $("#memId").val();
							var gndr = $("#gndr").val();

							//입력된 GPC 아이디가 GPC에 회원가입되어 있지 않은 경우 노출
							if(passFlag && isGpcUser =="N"){
								//alert("GPC 홈페이지에 회원가입해주세요.");
								openPopup('joinMemPopup', e);
								passFlag = false;
							}

							//입력된 GPC 아이디에 KAP 아이디가 등록되어 있지 않은 경우 노출
							if(passFlag && kapUserId == ""){
								//alert("GPC 계정에 KAP 아이디를 등록해주세요.");
								openPopup('iDregistPopup', e);
								passFlag = false;
							}

							//입력된 GPC 아이디에 KAP 아이디가 등록되어있지만 현재 신청하려는 kap아이디와 일치하지 않음
							if(passFlag && nowId != kapUserId){
								//alert("GPC 계정의 KAP 아이디를 확인해주세요.");
								openPopup('chkIdPopup', e);
								passFlag = false;
							}

							//입력된 GPC 아이디의 성별과 현재 로그인된 계정의 성별이 다른 경우 노출
							if(passFlag && gndr != gndrCd){
								//alert("GPC 계정의 성별과 KAP 성별이 일치하지 않습니다.");
								openPopup('genderMatchPopup', e);
								passFlag = false;
							}


							if(passFlag){
								console.log("온다 Y");
								$(".for-status-chk").addClass("satisfy");
								$("#gpcPass").val("Y");
							}else{
								console.log("온다 N");
								$(".for-status-chk").removeClass("satisfy");
								$("#gpcPass").val("N");
							}


						}, "/gpc/kapedu/verifyUserId?gpcId="+gpcId, seqObj, "text")



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
						//교육과정 신청

						var gpcId = $("#gpcId").val();
						var gpcYn = $("#gpcYn").val();
						var gpcPass = $("#gpcPass").val();

						if(gpcYn == "Y" && gpcId == ""){
							alert("GPC 아이디를 입력해주세요.");
							return false;
						}

						if(gpcYn == "Y" && gpcPass == "N"){
							alert("GPC 아이디를 인증해주세요.");
							return false;
						}

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


			chkInfo();
		}
	};

	// execute model
	ctrl.exec();

	return ctrl;
});