define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplyTrnsfCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

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
				$(".btn-wrap.add-load.align-center").hide();
			}else{
				$(".btn-wrap.add-load.align-center").show();
				var tempPage = (page === undefined || page == "") ? 1 : page;

				var rtnPage = 0;

				if((tempPage * 10)>totCnt){
					rtnPage = totCnt
				}else{
					rtnPage = (tempPage * 10);
				}

				if(rtnPage == totCnt){
					$(".btn-wrap.add-load.align-center").hide();
				}else{
					$(".btn-wrap.add-load.align-center").show();
					$(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
				}

			}

			$(".article-total-count.f-body2").find("span").text(totCnt);


			ctrl.obj.find("#listContainerTotCnt").text(totCnt);

			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/my-page/edu-apply/cmmSelect", $formObj, "GET", "html");

	}

	// set model
	ctrl.model = {
		id : {
			btnSubmit : {
				event : {
					click : function() {
						$formObj.submit();
					}
				}
			},

			searchBtn : {
				event : {
					click : function() {
						search(1);
					}
				}
			},


		},
		classname : {


			srchBtn : {
				event : {
					click : function() {
						search(1);
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

			memRadio : {
				event: {
					click: function (e) {

						//회원 라디오버튼 클릭시 데이터 세팅
						console.log('11');
						var $this = $("input[name='memListeRadioSet']:checked");

						var memSeq = $($this).val();
						var memId = $($this).data("id");
						$("#memId").val(memId);

						var gndr= ($($this).data("gndr") == '2') ? 'F' : 'M';

						$("#gndr").val(gndr);


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
							if(passFlag && (gndr != gndrCd)){
								//alert("GPC 계정의 성별과 KAP 성별이 일치하지 않습니다.");
								openPopup('genderMatchPopup', e);
								passFlag = false;
							}

							if(passFlag){
								$(".for-status-chk").addClass("satisfy");
								$("#gpcPass").val("Y");
							}else{
								$(".for-status-chk").removeClass("satisfy");
								$("#gpcPass").val("N");
							}


						}, "/gpc/kapedu/verifyUserId?gpcId="+gpcId, seqObj, "text")

					}
				}
			},


			setTrnsf : {
				event: {
					click: function (e) {

						var legacyYn = $(this).data("legacyYn");

						if(legacyYn == "Y"){
							alert("2024년 3월 25일 이전 신청자는 관리자에 문의 해주세요");
							return false;
						}

						//교육양도 시작


						//gpc일경우 관련 유효성체크 진행
						var gpcId = $("#gpcId").val();
						var gpcYn = $("#gpcYn").val();
						var gpcPass = $("#gpcPass").val();


						if($("input[name='memListeRadioSet']:checked").length == 0){
							alert("교육 양도할 회원을 선택해주세요.");
							return false;
						}

						if(gpcYn == "Y" && gpcId == ""){
							alert("GPC 아이디를 입력해주세요.");
							return false;
						}

						if(gpcYn == "Y" && gpcPass == "N"){
							alert("GPC 아이디를 인증해주세요.");
							return false;
						}

						/* 2024-04-02 성별 유효성 제거 s */
						/*if($("#gndr").val() != $("#loginGndr").val()) {
							alert("양도 받을 사람과 양도 해주는 사람의 성별이 같아야 합니다.");
							return false;
						}*/
						/* 2024-04-02 성별 유효성 제거 e */

						var seqObj = {};

						//KAP -> GPC 교육양도 데이터 전달 진행 -> *이부분이 정상처리되야 kap내 양도기능 진행함


						var bfrGpcId = $("#bfrGpcId").val();
						var aftGpcId = $("#gpcId").val();
						var kapSeq = $("#edctnSeq").val();
						var kapSchdSeq = $("#episdSeq").val();

						if(confirm("교육을 양도하시겠습니까?")){


							if(gpcYn =="Y"){
								cmmCtrl.jsonAjax(function(data){

									var rtnData = JSON.parse(data);

									var actForm = {};
									actForm.edctnSeq =  $("#edctnSeq").val();
									actForm.episdYear =  $("#episdYear").val();
									actForm.episdOrd =  $("#episdOrd").val();
									actForm.episdSeq =  $("#episdSeq").val();
									actForm.bfreMemSeq =  $("#memSeq").val(); //변경전 회원 번호
									actForm.aftrMemSeq =  $("input[name='memListeRadioSet']:checked").val();//변경후 회원번호
									actForm.ptcptSeq = $("#ptcptSeq").val();
									actForm.gpcId = aftGpcId;


									//KAP-> GPC 대참 로직 성공시 KAP 대참로직 진행
									if(rtnData.result == "S"){

										cmmCtrl.jsonAjax(function(data){

											var trnsfData = JSON.parse(data);

											if(trnsfData.regStat == "S"){
												alert("교육이 양도되었습니다.");
												//location.href="/my-page/edu-apply/detail?detailsKey="+actForm.edctnSeq+"&episdYear="+actForm.episdYear+"&episdOrd="+actForm.episdOrd+"&ptcptSeq="+actForm.ptcptSeq;
												location.href="/my-page/edu-apply/list";

											}else if(trnsfData.regStat == "F"){
												alert("이미 교육에 신청한 회원입니다.");
												return false;
											}else if(trnsfData.regStat == "M"){
												alert("양도한 이력이 있는 회원은 선택이 불가합니다.");
												return false;
											}


										}, "/my-page/edu-apply/setTrnsf", actForm, "text");

									}else{

										alert("KAP -> GPC 통신 실패함");
									}


								}, "/gpc/kapedu/changeEduMem?bfrGpcId="+bfrGpcId+"&aftGpcId="+aftGpcId+"&kapSeq="+kapSeq+"&kapSchdSeq="+kapSchdSeq, seqObj, "text")
							}else{

								var actForm = {};
								actForm.edctnSeq =  $("#edctnSeq").val();
								actForm.episdYear =  $("#episdYear").val();
								actForm.episdOrd =  $("#episdOrd").val();
								actForm.episdSeq =  $("#episdSeq").val();
								actForm.bfreMemSeq =  $("#memSeq").val(); //변경전 회원 번호
								actForm.aftrMemSeq =  $("input[name='memListeRadioSet']:checked").val();//변경후 회원번호
								actForm.ptcptSeq = $("#ptcptSeq").val();
								actForm.gpcId = aftGpcId;

								//KAP-> GPC 대참 로직 성공시 KAP 대참로직 진행
								cmmCtrl.jsonAjax(function(data){

									var trnsfData = JSON.parse(data);

									if(trnsfData.regStat == "S"){
										alert("교육이 양도되었습니다.");
										//location.href="/my-page/edu-apply/detail?detailsKey="+actForm.edctnSeq+"&episdYear="+actForm.episdYear+"&episdOrd="+actForm.episdOrd+"&ptcptSeq="+actForm.ptcptSeq;
										location.href="/my-page/edu-apply/list";

									}else if(trnsfData.regStat == "F"){
										alert("이미 교육에 신청한 회원입니다.");
										return false;
									}else if(trnsfData.regStat == "M"){
										alert("양도한 이력이 있는 회원은 선택이 불가합니다.");
										return false;
									}


								}, "/my-page/edu-apply/setTrnsf", actForm, "text");





							}




						}



					}
				}
			}


		},
		immediately : function(e) {

			search(1);

			$('#q').on('keydown', function(event) {
				// 눌린 키가 Enter 키인지 확인
				if (event.which === 13) {
					// 다른 이벤트 중지
					event.preventDefault();

					search(1);
					return false;
				}
			});

			// 유효성 검사
			$formObj.validation({
				async : {
				}
			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});