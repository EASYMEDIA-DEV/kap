define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBEpisdWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");
	//온라인강의 복사본 생성
	var onlineHtml = $("#onlineList").find("tr.examTr").eq(0).clone(true);
	var onlineFileHtml = $("#onlineList").find("tr.examTr").eq(1).clone(true);
	//온라인강의 복사본 생성 끝

	// 교육 참여자 목록 호출
	var search = function (page){
		//data로 치환해주어야한다.
		//cmmCtrl.setFormData($formObj);

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {
			$formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
			//CALLBACK 처리
			ctrl.obj.find("#ptcptListContainer").html(respObj);


			//전체 갯수
			var totCnt = $(respObj).eq(0).data("totalCount");
			//총 건수
			ctrl.obj.find("#ptcptListContainerTotCnt").text(totCnt);

			$(".ptcptField").validation({});

			$(".ptcptField").find(".btnMemAtndc").on("click", function(){
				memAtndcLayer(this);

			});


			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "ptcptListContainer", "ptcptPagingContainer");

			$(".ptcptField").find("ul.pagination").find("li").on("click", function(){
				if(!confirm("다른 페이지로 이동 시 입력한 값은 저장되지 않습니다. 이동하시겠습니까?")){
					return false;
				}
			});

		}, "/mngwserc/eb/ebb/episdPtcptList", $formObj, "GET", "html");

	}


	var memAtndcLayer = function(e){

		var ptcptSeq =  $(e).data("ptcptseq");

		console.log(ptcptSeq);

		//출석부 레이어 팝업 호출
		$(".ebbMemAtndcSrchLayer").one('show.bs.modal', function() {
			$(this).find("button.tempBtn").attr("data-ptcptSeq", ptcptSeq);
			$(this).find("button.tempBtn").trigger("click");

			var modal = $(this);
			modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

		}).one('hidden.bs.modal', function() {
			// Remove class for soft backdrop (if not will affect future modals)
		}).one('choice', function(data, param) {
			var obj = param;
			$("#listContainer3").find("td").eq(0).text(obj.typeNm);
			$("#listContainer3").find("td").eq(1).text(obj.titl);
			$("#srvSeq").val(obj.seq);
		}).modal();

	}


	//오프라인평가 체크시 실행
	var setOtsdSelectBox = function(arg){

		if($("input[name='otsdExamPtcptYn']").is(":checked")){
			//alert("평가 선택");
			$("#examSeq").val(null).prop("disabled", false);
			$("#examSeq").addClass("notRequired");
			$("input:radio[name='cmptnAutoYn']:radio[value='N']").prop("checked", true);//오프라인평가 진행시 수료자동화여부 무조건 수동 고정
			$(".examNmForm").text("");
			$(".jdgmtYn").find("table").addClass("table-disabled");
		}else{
			//alert("평가 선택 해제");
			$("#examSeq").prop("disabled", true);
			$("#examSeq").removeClass("notRequired");
			$(".jdgmtYn").find("table").removeClass("table-disabled");
		}

	}


	//예산 지출내역 filed 세팅
	var bdgetSet = function(data){

	//db에서 불러온 데이터
	$(".bdgetTargetData").each(function(idx){
		var tempFormArray = new Array();
		$(this).find("input:text").each(function(){

			//비고일경우 숫자전용 클래스 제거
			if("ED_BDGET_CD01011".indexOf($(this).attr("name")) > -1 || "ED_BDGET_CD02011".indexOf($(this).attr("name")) > -1 ){
				$(this).removeClass("numberChk");
			}

			//인풋박스 복사
			var tempInput = $(".copyBdgetInput").find("input:hidden").clone();

			//폼 복사
			var tempForm = $(".copyBdgetForm").clone();

			//이게 11번 루트돈다

			tempForm.find("label").text($(this).attr("title"));//라벨 세팅


			tempForm.find("label").next().append($(this));

			tempFormArray.push(tempForm);
		});


		//폼이 짝수인지 홀수인지 확인한다
		var cntChk = false //짝수:false, 홀수:true
		if((tempFormArray.length%2) == 1) cntChk = true;

		//fieldset 갯수 산정
		var fieldset = Math.ceil(tempFormArray.length/2);

		//홀수일경우 마지막 폼의 길이를 11로 설정한다.

		var statIdx = 0;//폼 시작값
		var endIdx = 1;//폼 종료값
		var maxIdx = tempFormArray.length-1;

		var lastForm = "";
		//금액 산정(비고 제외) 비고는 예외로 코드값 ED_BDGET_CD01011를 고정 사용한다.
		for(var i=1; i<=fieldset; i++){

			//console.log(i+"번째 필드셋 들어가는 값  [" + statIdx+"]번째 폼부터  ["+endIdx+"]번째 폼");

			if( i == fieldset && cntChk == true){
				//console.log("홀수로 끝나고 마지막이므로 사이즈 11로 함");
				tempFormArray[statIdx].find("label").next().addClass("col-sm-11");
				lastForm = lastForm+'<fieldset><div class="form-group text-sm">';
				lastForm = lastForm+tempFormArray[statIdx].html();
				lastForm = lastForm+"</div></fieldset>";
			}else{
				tempFormArray[statIdx].find("label").next().addClass("col-sm-5");
				tempFormArray[endIdx].find("label").next().addClass("col-sm-5");


				lastForm = lastForm+'<fieldset class="bdget01"><div class="form-group text-sm">';
				lastForm = lastForm+tempFormArray[statIdx].html();
				lastForm = lastForm+tempFormArray[endIdx].html();
				lastForm = lastForm+"</div></fieldset>";
				//console.log(tempFormArray[statIdx].html());
			}

			statIdx = statIdx+2;
			endIdx = endIdx+2;
			if(statIdx>=maxIdx) statIdx = maxIdx;
			if(endIdx>=maxIdx) endIdx = maxIdx;
		}

		//제목 삽입
		var copyTitle = $(".copyTitle").clone(true);
		var type = $(this).data("type");
		if(idx == 0){
			copyTitle.find("span.title").text("교육예산").addClass(type);
		}else if(idx == 1){
			copyTitle.find("span.title").text("지출내역").addClass(type);
		}

		//인풋 뭉치들 삽입
		copyTitle.css("display", "");
		$("#bdget").append(copyTitle.html());
		$("#bdget").append(lastForm);

	});

	}

	//예산 지출내역 자동계산
	var totPmtSet = function(data){

		//입력한 인풋의 최상단 폼 찾아감
		var parentForm = data.closest("fieldset");

		var totPmt1 =0;
		$(".calPmtForm.bdget01.numberChk").each(function(idx){
			var inputName = $(this).attr("name");

			if("ED_BDGET_CD01011".indexOf(inputName) == -1 || "ED_BDGET_CD02011".indexOf(inputName) == -1 ){
				if($(this).val() !="" && $(this).val() != undefined){
					totPmt1 = totPmt1 + parseInt($(this).val());
				}
			}
		});

		var mtForm = $(".title.bdget01").closest(".mt0");
		totPmt1 = totPmt1.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		if(mtForm.find("span").size()>1){
			mtForm.find("span:last").remove();
			mtForm.append("<span> (합계 : "+totPmt1+"원)</span>");
		}else{
			mtForm.append("<span> (합계 : "+totPmt1+"원)</span>");
		}


		var totPmt2 =0;
		$(".calPmtForm.bdget02.numberChk").each(function(idx){
			var inputName = $(this).attr("name");

			if("ED_BDGET_CD01011".indexOf(inputName) == -1 || "ED_BDGET_CD02011".indexOf(inputName) == -1 ){
				if($(this).val() !="" && $(this).val() != undefined){
					totPmt2 = totPmt2 + parseInt($(this).val());
				}
			}
		});

		var mtForm = $(".title.bdget02").closest(".mt0");
		totPmt2 = totPmt2.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		if(mtForm.find("span").size()>1){
			mtForm.find("span:last").remove();
			mtForm.append("<span> (합계 : "+totPmt2+"원)</span>");
		}else{
			mtForm.append("<span> (합계 : "+totPmt2+"원)</span>");
		}


	}


	//강사 테이블 넘버링
	var isttrTable = function(data){

		//tr 갯수 감지해서 카운트해줌, 없으면 없는폼 꺼냄
		var totCnt = $("#isttrContainer").find("tr").size();
		var startCount = 2;


		$("#isttrContainer").find("tr").each(function(idx, data){
			if(idx>1){
				$(this).find("td").eq(0).text(totCnt-idx);
			}
		});

		//삭제했는데 하나도없으면 목록 없다는걸로 돌림
		if($("#isttrContainer").find("tr").size() == 2){
			//돌림
			$("#isttrContainer").find(".notIsttr").css("display", "");
			$("#isttrContainer").find(".notIsttr").find("td").css("display", "");
		}

	}
	var filedSet = function(data){

		var stduyMthdCd, jdgmtYn;

		if(data === undefined){
			stduyMthdCd = $("#stduyMthdCd").val();
			jdgmtYn = $("#jdgmtYn").val();
		}else{
			stduyMthdCd = data.stduyMthdCd;
			jdgmtYn = data.jdgmtYn;
		}

		//교육방식이 온라인, 블렌디드 일때만 온라인 목차 출력
		if("STDUY_MTHD01" == stduyMthdCd){
		$(".onlineSet").css("display", "none");

		$(".onlineSet").find("input:text").each(function(){
			if($(this).closest("tr").attr("class") !="examTr"){
				$(this).addClass("notRequired");
			}

		});
		$(".onlineSet").find("div.dropzone").each(function(){
			if($(this).closest("tr").attr("class") !="examTr"){
				$(this).addClass("notRequired");
			}
		});
	}else{
		$(".onlineSet").css("display", "");

		$(".onlineSet").find("input:text").each(function(){
			if($(this).closest("tr").attr("class") !="examTr"){
				$(this).removeClass("notRequired");
			}

		});
		$(".onlineSet").find("div.dropzone").each(function(){
			if($(this).closest("tr").attr("class") !="examTr"){
				$(this).removeClass("notRequired");
			}
		});

	}

	//평가여부 N일경우 평가항목 숨김
	if("N" == jdgmtYn){
		$(".jdgmtYn").css("display", "none");
		$(".jdgmtYn").find("input:hidden").each(function(){
			$(this).addClass("notRequired");
		});
		$("#examSeq").prop("disabled", true);
	}else{
		$(".jdgmtYn").css("display", "");
		$(".jdgmtYn").find("input:hidden").each(function(){
			$(this).removeClass("notRequired");
		});

		//오프라인평가면 평가 시퀀스 사용안함
		if($("input[name='otsdExamPtcptYn']:checked").val() =="Y"){
			$("#examSeq").addClass("notRequired").prop("disabled", true);
		}else{
			$("#examSeq").prop("disabled", false);
		}

	}
	}

	//질문 번호 셋팅,점수 셋팅
	var questionSet = function(){

		$(".surveyList").each(function(){
			var surveyTypeData = $(this).data('survey-type');
			var cnt = 1;
			var subCnt = 1;
			$("."+surveyTypeData).each(function(index){                         // 질문, 하위질문 번호를 구분하고 순서를 셋팅
				if ($(this).find('input[name=dpth]').val() == '2'){
					$("."+surveyTypeData+"questionTxt:eq("+index+")").text("└질문"+eval(cnt-1)+"-"+subCnt);
					subCnt = subCnt + 1;
				}else{
					$("."+surveyTypeData+"questionTxt:eq("+index+")").text("질문"+cnt);
					cnt = cnt+1;
					subCnt = 1;
				}
			});
		});

		var EDU01Score =0;
		var EDU02Score =0;
		var EDU03Score =0;
		var EDU04Score =0;
		var EDU05Score =0;
		var totalScore = 0;
		var EDU01Cnt =0;
		var EDU02Cnt =0;
		var EDU03Cnt =0;
		var EDU04Cnt =0;
		var EDU05Cnt =0;
		var totalCnt = 0;

		$("input[name=qstnCd]").each(function(index){

			if ($(this).val()=="EDU01"){
				EDU01Score = EDU01Score + ($("input[name=qstnCdScore]:eq("+index+")").val()*$("input[name=qstnCdCount]:eq("+index+")").val());
				EDU01Cnt = EDU01Cnt + parseInt($("input[name=qstnCdCount]:eq("+index+")").val());
			}else if ($(this).val()=="EDU02"){
				EDU02Score = EDU02Score + ($("input[name=qstnCdScore]:eq("+index+")").val()*$("input[name=qstnCdCount]:eq("+index+")").val());
				EDU02Cnt = EDU02Cnt + parseInt($("input[name=qstnCdCount]:eq("+index+")").val());
			}else if ($(this).val()=="EDU03"){
				EDU03Score = EDU03Score + ($("input[name=qstnCdScore]:eq("+index+")").val()*$("input[name=qstnCdCount]:eq("+index+")").val());
				EDU03Cnt = EDU03Cnt + parseInt($("input[name=qstnCdCount]:eq("+index+")").val());
			}else if ($(this).val()=="EDU04"){
				EDU04Score = EDU04Score + ($("input[name=qstnCdScore]:eq("+index+")").val()*$("input[name=qstnCdCount]:eq("+index+")").val());
				EDU04Cnt = EDU04Cnt + parseInt($("input[name=qstnCdCount]:eq("+index+")").val());
			}else if ($(this).val()=="EDU05"){
				EDU05Score = EDU05Score + ($("input[name=qstnCdScore]:eq("+index+")").val()*$("input[name=qstnCdCount]:eq("+index+")").val());
				EDU05Cnt = EDU05Cnt + parseInt($("input[name=qstnCdCount]:eq("+index+")").val());
			}

			totalScore = totalScore + ($("input[name=qstnCdScore]:eq("+index+")").val()*$("input[name=qstnCdCount]:eq("+index+")").val());
			totalCnt = totalCnt + parseInt($("input[name=qstnCdCount]:eq("+index+")").val());

		})

		if (EDU01Cnt == 0) {
			$("#EDU01Score").text(0);
		}else{
			$("#EDU01Score").text((EDU01Score/EDU01Cnt).toFixed(1));
		}
		if (EDU02Cnt == 0) {
			$("#EDU02Score").text(0);
		}else{
			$("#EDU02Score").text((EDU02Score/EDU02Cnt).toFixed(1));
		}
		if (EDU03Cnt == 0) {
			$("#EDU03Score").text(0);
		}else{
			$("#EDU03Score").text((EDU03Score/EDU03Cnt).toFixed(1));
		}
		if (EDU04Cnt == 0) {
			$("#EDU04Score").text(0);
		}else{
			$("#EDU04Score").text((EDU04Score/EDU04Cnt).toFixed(1));
		}
		if (EDU05Cnt == 0) {
			$("#EDU05Score").text(0);
		}else{
			$("#EDU05Score").text((EDU05Score/EDU05Cnt).toFixed(1));
		}
		if (totalCnt == 0) {
			$("#totalScore").text(0);
		}else{
			$("#totalScore").text((totalScore/totalCnt).toFixed(1));
		}

	}

	// set model
	ctrl.model = {
		id : {
			btn_end_edu : {
				event : {
					click : function() {
						//강제 종강 클릭
						var seqObj = {};
						seqObj.edctnSeq = $("#edctnSeq").val();
						seqObj.episdYear = $("#episdYear").val();
						seqObj.episdOrd = $("#episdOrd").val();
						seqObj.episdSeq = $("#episdSeq").val();


						if(confirm("강제 종강처리하시겠습니까?")){
							//현재 신청한 참여인원 데이터 전부 교육취소 상태로 바꿈, + 교육차수상태를 종강(폐강)으로 변경
							cmmCtrl.jsonAjax(function(data){
								if(data !=""){
									var rtn = JSON.parse(data);
									alert("저장되었습니다.");

									location.href="./list"

								}
							}, "./endEdu", seqObj, "text");
						}


					}
				}
			},

			changeEpisd : {
				event : {
					click : function() {
						//차수변경 클릭
						var seqObj = {};
						seqObj.edctnSeq = $("#edctnSeq").val();
						seqObj.episdYear = $("#episdYear").val();
						seqObj.episdOrd = $("#episdOrd").val();
						seqObj.episdSeq = $("#episdSeq").val();

						//체크한 회원정보 전부 가져옴
						var seqList = new Array();
						$("#ptcptListContainer").find("input:checkbox[name='delValueList']:checked").each(function(){
							seqList.push($(this).val());
						});


						if(seqList.length<1){
							alert("회원을 선택해주세요.");
							return false;

						}
						seqObj.memSeq = seqList;


						//차수변경 레이어팝업 호출
						$(".ebbChageEpisdLayer").one('show.bs.modal', function() {
							$(".ebbChageEpisdLayer").find("#edctnSeq").val(seqObj.edctnSeq);
							$(".ebbChageEpisdLayer").find("#episdSeq").val(seqObj.episdSeq);
							$(".ebbChageEpisdLayer").find("#prev_episdYear").val(seqObj.episdYear);
							$(".ebbChageEpisdLayer").find("#prev_episdOrd").val(seqObj.episdOrd);

							$(".ebbChageEpisdLayer").find("#chan_memSeq").val(seqObj.memSeq);
							//$(this).find("button.tempBtn").attr("data-ptcptSeq", ptcptSeq);
							//$(this).find("button.tempBtn").trigger("click");

							var modal = $(this);
							modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

						}).one('hidden.bs.modal', function() {
							// Remove class for soft backdrop (if not will affect future modals)
						}).one('choice', function(data, param) {
							var obj = param;
							$("#listContainer3").find("td").eq(0).text(obj.typeNm);
							$("#listContainer3").find("td").eq(1).text(obj.titl);
							$("#srvSeq").val(obj.seq);
						}).modal();


					}
				}
			},


			btnSearch : {
				event : {
					click : function() {
						//검색버튼 클릭시
						cmmCtrl.setFormData($formObj);
						search(1);
					}
				}
			},

			episdOrd : {
				event : {
					click : function() {

						var edctnSeq = $("#edctnSeq").val();
						var episdYear = $("#episdYear").val();
						var episdOrd = $("#episdOrd").val();

						if(edctnSeq == ""){
							alert("과정을 먼저 선택해주세요.");
							return false;
						}

						if(episdYear == ""){
							alert("연도를 먼저 입력해주세요.");
							return false;
						}

						if(episdOrd == ""){
							return false;
						}

						var seqObj = {};
						seqObj['edctnSeq'] = edctnSeq;
						seqObj['episdYear'] = episdYear;
						seqObj['episdOrd'] = episdOrd;

						console.log(seqObj);

						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);
								console.log(rtn);
								if(rtn.edctnSeq>0){
									alert("이미 등록된 회차입니다.");

									$("#episdOrd").val("").prop("selected", true);
								}
							}

						}, "./selectChk", seqObj, "text");


					}
				}
			},
			btnAddApp : {
				event : {
					click : function() {
						//신청자등록 시작
						var seqObj = {};
						seqObj.edctnSeq = $("#edctnSeq").val();
						seqObj.episdYear = $("#episdYear").val();
						seqObj.episdOrd = $("#episdOrd").val();
						seqObj.episdSeq = $("#episdSeq").val();


						//정원수 체크
						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);
								//정원여유
								if(rtn.fxnumStta == "S"){
									//alert("정원 여유");
									//레이어팝업 호출
									location.href = "./ptcpt/write?detailsKey="+seqObj.edctnSeq+"&episdYear="+seqObj.episdYear+"&episdOrd="+seqObj.episdOrd+"&episdSeq="+seqObj.episdSeq;

								//정원초과
								}else{
									alert("정원이 초과되었습니다.");
									return false;
								}
							}

						}, "./fxnumChk", seqObj, "text");



					}
				}
			},
			btnEdctnAtndc : {
				event : {
					click : function() {

						//출석부 레이어 팝업 호출
						$(".ebbAtndcSrchLayer").one('show.bs.modal', function() {

							var modal = $(this);
							modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

						}).one('hidden.bs.modal', function() {
							// Remove class for soft backdrop (if not will affect future modals)
						}).one('choice', function(data, param) {
							var obj = param;
							$("#listContainer3").find("td").eq(0).text(obj.typeNm);
							$("#listContainer3").find("td").eq(1).text(obj.titl);
							$("#srvSeq").val(obj.seq);
						}).modal();

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
						if( $(this).attr("value") != "null" ){
							$formObj.find("input[name=pageIndex]").val($(this).attr("value"));
							search();
						}
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
			//과정 검색
			couseSearch : {
				event : {
					click : function(){
						cmmCtrl.getCouseSrchLayerPop(function(data){
							if(data.choiceCnt > 1){
								alert(msgCtrl.getMsg("fail.eb.eba.notSrchPlaceCouse1"));
							}else{
								$("#edctnSeq").val(data.edctnSeq);
								$("#ctgryCd").val(data.ctgryCd);

								$("p.ctgryCdNm").text(data.ctgryCdNm);

								$("p.nm").text(data.nm);
								$("p.stduyMthd").text(data.stduyMthd);
								$("p.stduyDtm").text(data.stduyDtm);
							}

							filedSet(data);


						});
					}
				}
			},

			//교육장 검색
			eduRoomSearch : {
				event : {
					click : function(){
						cmmCtrl.getEduRoomLayerPop(function(data){
							if(data.choiceCnt > 1){
								alert(msgCtrl.getMsg("fail.eb.ebf.notSrchPlaceCom1"));
							}else{

								var placeSeq = data.seq;
								var titl= data.titl;//교육장명
								var rgnsNm= data.rgnsNm;//지역
								var addr= data.addr;//주소
								var rprsntTelNo= data.rprsntTelNo;//대표번호

								$("#placeSeq").val(placeSeq).prop("disabled", false);

								$("tr.setPlace").find("td").eq(0).text(titl);
								$("tr.setPlace").find("td").eq(1).text(rgnsNm);
								$("tr.setPlace").find("td").eq(2).text(addr);
								$("tr.setPlace").find("td").eq(3).text(rprsntTelNo);
								$("tr.notPlace").css("display", "none");
								$("tr.setPlace").css("display", "");

							}


						});
					}
				}
			},
			//설문 검색
			eduSrvSearch : {
				event : {
					click : function(){


						$(".svaSurveySrchLayer").one('show.bs.modal', function() {

							var modal = $(this);
							modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

						}).one('hidden.bs.modal', function() {
							// Remove class for soft backdrop (if not will affect future modals)
						}).one('choice', function(data, param) {
							var obj = param;
							$("#listContainer3").find("td").eq(0).text(obj.typeNm);
							$("#listContainer3").find("td").eq(1).text(obj.titl);
							$("#srvSeq").val(obj.seq);
						}).modal();









					}
				}
			},




			//시험 검색
			eduExamSearch : {
				event : {
					click : function(){
						cmmCtrl.getExamLayerPop(function(data){
							if(data.choiceCnt > 1){
								alert(msgCtrl.getMsg("fail.ex.notSrchExam1"));
							}else{
								var examSeq = data.seq;//시험번호
								var titl = data.titl;//시험 명
								$("#examSeq").val(examSeq).prop("disabled", false);
								$("tr.setExg").find("td").eq(0).text(titl);
								$("tr.notExg").css("display", "none");
								$("tr.setExg").css("display", "");
							}
						});
					}
				}
			},

			//강사 검색
			eduIsttrSearch : {
				event : {
					click : function(){
						cmmCtrl.getLecturerLayerPop(function(data){
							if(data.choiceCnt  == 0){
								alert(msgCtrl.getMsg("fail.mpc.notSrchLecturer"));
							}else{
								var name, ffltnNm, spclCntn, seq;



								if(data.choiceCnt>1){
									var trObjList = data.trObjList;
									for(var i=0; i<trObjList.length; i++){
										var exIsttr = $(".setIsttr").clone(true);
										name = trObjList[i].name//이름
										ffltnNm= trObjList[i].titl//소속
										spclCntn= trObjList[i].spclCntn//약력(특이사항)
										seq= trObjList[i].seq;//삭제(시퀀스값)

										//다중등록할때 시퀀스 체크해서 중복값이면 패스함
										var passYn = false;//이 값이 true가 되면 이미 강사 목록에 있으므로 append목록에 추가하지 않는다.
										$("#isttrContainer").find("tr").find("input:hidden").each(function(){
											if($(this).val() == seq) passYn = true;
										});

										if(!passYn){
											exIsttr.find("td").eq(1).text(name);
											exIsttr.find("td").eq(2).text(ffltnNm);
											exIsttr.find("td").eq(3).text(spclCntn);
											exIsttr.find("input:hidden").val(seq);
											$("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
										}
									}

								}else{
									var exIsttr = $(".setIsttr").clone(true);
									name = data.name//이름
									ffltnNm = data.titl//소속
									spclCntn = data.spclCntn//약력(특이사항)
									seq = data.seq//삭제(시퀀스값)

									var passYn = false;//이 값이 true가 되면 이미 강사 목록에 있으므로 현재 동작을 취소한다.
									$("#isttrContainer").find("tr").find("input:hidden").each(function(){
										if($(this).val() == seq) {
											alert("이미 추가된 강사입니다.");
											passYn = true;
										}
									});
									if(!passYn){
										exIsttr.find("td").eq(1).text(name);
										exIsttr.find("td").eq(2).text(ffltnNm);
										exIsttr.find("td").eq(3).text(spclCntn);
										exIsttr.find("input:hidden").val(seq);
										$("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
									}

								}

								$(".notIsttr").css("display", "none");
								$(".setIsttr").css("display", "none");
							}

							isttrTable();
						});
					}
				}
			},

			//온라인강의 추가/삭제
			btnAdd : {
				event : {
					click : function(){
						onlineHtml.css("display", "").removeClass("examTr").removeClass("notRequired");
						onlineFileHtml.css("display", "").removeClass("examTr").removeClass("notRequired");
						$("#onlineList").append("<tr>"+onlineHtml.html()+"</tr><tr>"+onlineFileHtml.html()+"</tr>");
						onlineHtml.css("display", "none").addClass("examTr").addClass("notRequired");
						onlineFileHtml.css("display", "none").addClass("examTr").addClass("notRequired");

						var trgtObj = $("#onlineList").find(".dropzone:last");
						cmmCtrl.setDropzone(trgtObj, {
							maxFileCnt  : trgtObj.data("maxFileCnt"),
							maxFileSize : trgtObj.data("maxFileSize"),
							fileExtn    : trgtObj.data("fileExtn"),
							fileFieldNm : trgtObj.data("fileFieldNm")
						});

						//강의시간 숫자입력 밸리데이션 체크를 위해서 다시 적용해줌
						$("#onlineList").find("tr:last").validation({});

					}
				}
			},

			//참여자목록 오프라인평가 점수 수정
			examScoreUpdate : {
				event : {
					click : function(){
						var edctnSeq = $("#edctnSeq").val();
						var episdYear = $("#episdYear").val();
						var episdOrd = $("#episdOrd").val();

						var examScore = $(this).closest("tr").find("input[name='examScore']").val();
						var ptcptSeq = $(this).closest("tr").find("input[name=delValueList]").val();

						var ptcptForm = {};

						ptcptForm.edctnSeq = edctnSeq;
						ptcptForm.episdYear = episdYear;
						ptcptForm.episdOrd = episdOrd;
						ptcptForm.ptcptSeq = ptcptSeq;
						ptcptForm.examScore = examScore;

						cmmCtrl.jsonAjax(function(data){
							alert("저장되었습니다.");
						}, "./examScoreUpdate", ptcptForm, "text");


					}
				}
			},

			//참여자목록 수료여부 변경
			cmptnYnUpdate : {
				event : {
					click : function(){
						var edctnSeq = $("#edctnSeq").val();
						var episdYear = $("#episdYear").val();
						var episdOrd = $("#episdOrd").val();

						debugger;


					}
				}
			},


			btnOneTrRemove : {
				event : {
					click : function(){
						$(this).closest("tr").remove();

						isttrTable();
					}
				}
			},

			btnRemove : {
				event : {
					click : function(){
						$(this).closest("tr").next().remove();
						$(this).closest("tr").remove();
					}
				}
			},


			srvReset : {
				event : {
					click : function(){
						if(confirm("응답 내용이 즉시 삭제됩니다. 초기화 하시겠습니까?")){

							var srvSeq = $("#srvSeq").val();

							if (srvSeq!=''){
								var svMst = {};
								svMst.srvSeq = srvSeq;
								svMst.edctnSeq = $("#edctnSeq").val();
								svMst.episdYear = $("#episdYear").val();
								svMst.episdSeq = $("#episdSeq").val();

								cmmCtrl.jsonAjax(function(data){
									var rtn = JSON.parse(data);
									if(rtn.respCnt > -1 ){
										alert('설문 응답이 초기화되었습니다.');
										location.reload();
									}
								}, './deleteSurveyRspn', svMst, "text")

							}
						}
					}
				}
			},

			otsdExamPtcptYn : {
				event : {
					change : function() {
						setOtsdSelectBox(this);
					}
				}
			},
			cmptnAutoYn : {
				event : {
					change : function() {

						if($(this).val() == "Y" && $("input[name='otsdExamPtcptYn']:checked").val() == "Y"){
							alert("오프라인평가를 선택하면 수료자동화여부 '자동'을 선택할수 없습니다.");
							$("input:radio[name='cmptnAutoYn']:radio[value='N']").prop("checked", true);//오프라인평가 진행시 수료자동화여부 무조건 수동 고정
							return false;
						}

					}
				}
			},



},
		immediately : function(event) {

			//교육 참여자목록 조회
			if($(".card-body").data("actiontype") == "update"){
				search();
			}
			
			//질문 번호 셋팅,점수 셋팅
			questionSet();


			//폼 데이터 처리
			filedSet();



			//예산지출내역 폼 조립
			bdgetSet();

			//예산지출내역 자동계산 함수 실행
			$(".calPmtForm").blur(function(){
				totPmtSet(this);
			});


			var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

			/* Editor Setting */
			jQuery("textarea[id^='stduyCntn']").each(function(){
				cmmCtrl.setEditor({
					editor : jQuery(this).attr("id"),
					height : 400,
					readOnly : _readOnly
				});
			});

			jQuery(".CodeMirror").find("textarea").addClass("notRequired");

			/* File Dropzone Setting */
			$formObj.find(".dropzone").each(function(){
				var trgtObj = $(this);
				cmmCtrl.setDropzone(trgtObj, {
					maxFileCnt  : trgtObj.data("maxFileCnt"),
					maxFileSize : trgtObj.data("maxFileSize"),
					fileExtn    : trgtObj.data("fileExtn"),
					fileFieldNm : trgtObj.data("fileFieldNm")
				})
			});
			// 유효성 검사
			$formObj.validation({
				after : function() {
					var isValid = true, editorChk = true;

					$formObj.find(".ckeditorRequired").each(function() {
						jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
						jQuery(this).val(jQuery(this).val().split("<").join("~!left!~"));
						jQuery(this).val(jQuery(this).val().split(">").join("~!right!~"));
						jQuery(this).val(jQuery(this).val().split("\'").join("~!singlecomma!~"));
						jQuery(this).val(jQuery(this).val().split("\"").join("~!doublecomma!~"));

						var editorVal = jQuery(this).val().length;

						if (editorVal < 1)
						{
							editorChk = false;

							alert(msgCtrl.getMsg("fail.co.cog.cnts"));

							CKEDITOR.instances[jQuery(this).prop("id")].focus();

							// 에디터 최상단으로 스크롤 이동
							jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

							return false;
						}
					});

					if (!editorChk)
					{
						isValid = false;
					}

					return isValid;
				},
				async : {
					use : true,
					func : function (){

						var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
						var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

						//유효성체크 플래그
						var resultFlag = true;

						//회차정보 마지막 중복체크 진행
						$("#episdOrd").trigger("change");

						var actForm = {};

						var accsStrtDt = $("#accsStrtDt").val();
						var accsStrtHour = $("#accsStrtHour").val();

						var accsEndDt = $("#accsEndDt").val();
						var accsEndHour = $("#accsEndHour").val();

						var edctnStrtDt = $("#edctnStrtDt").val();
						var edctnStrtHour = $("#edctnStrtHour").val();

						var edctnEndDt = $("#edctnEndDt").val();
						var edctnEndHour = $("#edctnEndHour").val();

						var accsStrtDtm = accsStrtDt+" "+accsStrtHour+":00:00";
						var accsEndDtm = accsEndDt+" "+accsEndHour+":59:59";

						var edctnStrtDtm = edctnStrtDt+" "+edctnStrtHour+":00:00";
						var edctnEndDtm = edctnEndDt+" "+edctnEndHour+":59:59";

						actForm.edctnSeq = $("#edctnSeq").val();//교육순번
						actForm.cbsnCd = $("#cbsnCd").val();//업종코드

						actForm.episdYear =$("#episdYear").val();//연도
						actForm.episdOrd =$("#episdOrd").val();//회차정렬

						actForm.orgEpisdYear =$("#orgEpisdYear").val();//연도(수정시 where절에 사용되는 pk값)
						actForm.orgEpisdOrd =$("#orgEpisdOrd").val();//회차정렬(수정시 where절에 사용되는 pk값)

						//강사목록 세팅
						var isttrSeqList= new Array();
						$("input[name='isttrSeq']").each(function(){
							if($(this).val() != undefined && $(this).val() != ""){
								var tempForm = {};
								tempForm.edctnSeq = $("#edctnSeq").val();
								tempForm.episdYear = $("#episdYear").val();
								tempForm.isttrSeq = $(this).val();
								tempForm.episdOrd = $("#episdOrd").val();
								isttrSeqList.push(tempForm);
							}

						});


						if(isttrSeqList.length == 0){
							alert("강사를 추가 해주세요");
							return false
						}

						actForm.isttrSeqList = isttrSeqList;

						//접수시간 관련 날짜 유효성 체크
						if(accsStrtDtm>accsEndDtm){
							alert("접수 시작일이 접수 종료일보다 이전날짜로 입력 해주세요.");
							$("#accsStrtDt").focus();
							return false;
						}
						if(accsStrtDt == accsEndDt){
							if(accsStrtHour > accsEndHour){
								alert("접수 시작시간이 더 클 수 없습니다.");
								$("#accsStrtHour").focus();
								return false;
							}
						}

						//교육기간 유효성 체크
						if(edctnStrtDtm>edctnEndDtm){
							alert("교육 시작일이 교육 종료일보다 이전날짜로 입력 해주세요.");
							$("#edctnStrtDt").focus();
							return false;
						}
						if(edctnStrtDt == edctnEndDt){
							if(edctnStrtHour > edctnEndHour){
								alert("교육 시작시간이 더 클 수 없습니다.");
								$("#edctnStrtHour").focus();
								return false;
							}
						}

						if($("#placeSeq").val() == ""){
							alert("교육장소를 선택해주세요");
							return false;
						}

						if($("#examSeq").attr("disabled") === undefined && $("#examSeq").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined){
							alert("평가를 선택해주세요");
							return false;
						}




						actForm.accsStrtDtm = accsStrtDtm;//접수시작일시
						actForm.accsEndDtm = accsEndDtm;//접수종료일시
						actForm.edctnStrtDtm = edctnStrtDtm;//교육시작일시
						actForm.edctnEndDtm = edctnEndDtm;//교육종료일시

						actForm.fxnumCnt = $("#fxnumCnt").val();//정원수
						actForm.fxnumImpsbYn = $("input[name='fxnumImpsbYn']:checked").val();//정원제한여부
						actForm.rcrmtMthdCd = $("input[name='rcrmtMthdCd']:checked").val();//모집방법코드
						actForm.picNm = $("#picNm").val();//담당자명
						actForm.picEmail = $("#picEmail").val();//담당자이메일
						actForm.picTelNo = $("#picTelNo").val()//담당자전화번호
						actForm.placeSeq = $("#placeSeq").val();//교육장소순번

						actForm.cprtnInsttNm = $("#cprtnInsttNm").val();//협력기관


						actForm.srvSeq = $("#srvSeq").val();//설문순번
						actForm.srvStrtDtm = $("#srvStrtDtm").val();//설문시작일
						actForm.srvEndDtm = $("#srvEndDtm").val();//설문종료일

						//시험안하면 날짜 안넣어줌 에러남
						if($("#examStrtDtm").attr("class").indexOf("notRequired") < 0){
							actForm.examStrtDtm = $("#examStrtDtm").val();//시험시작일
							actForm.examEndDtm = $("#examEndDtm").val();//시험종료일
						}
						



						actForm.edctnNtctnFileSeq = $("#edctnNtctnFileSeq").val();

						actForm.examSeq = $("#examSeq").val();//시험순번
						actForm.otsdExamPtcptYn = $("input[name='otsdExamPtcptYn']:checked").val();//오프라인평가여부
						actForm.cmptnAutoYn = $("input[name='cmptnAutoYn']:checked").val();//수료자동여부
						actForm.expsYn = $("input[name='expsYn']:checked").val();//노출여부


						//교육 안내문 세팅
						var fileArray = new Array();
						if(!($(".edctnNtctn").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
							$(".edctnNtctn").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
							$(".edctnNtctn").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
							$.each($(".edctnNtctn").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
								//alt값  data에 넣어주기.
								data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

								for (let i in data) {
									if (data.hasOwnProperty(i)) {
										var temp = {};
										temp.fileSeq = data.fileSeq;
										temp.status = data.status;
										temp.width = data.width;
										temp.height = data.height;
										temp.webPath = data.webPath;
										temp.fieldNm = data.fieldNm;
										temp.orgnFileNm = data.orgnFileNm;
										temp.fileDsc = data.fileDsc;
										temp.fileOrd = data.fileOrd;

										if(fileArray == "" || (fileArray[fileArray.length-1].fileOrd != temp.fileOrd)){
											fileArray.push(temp);
										}

									}
								}

							})
						}
						actForm.fileList = fileArray;

						//온라인교육 강의 관련 배열 세팅
						var lctrList = new Array();
						var onlineIndex = 0;
						var fileIndex = 1;

						$("#onlineList > tr").each(function(){

							var onlineNm = $(this).find("[name='onlineNm']").val();

							if(!(onlineNm === undefined) && $(this).attr("class") != "examTr"){
								var onlinePack = {};
								var onlineUrl = $(this).find("[name='onlineUrl']").val();
								var onlineTime = $(this).find("[name='onlineTime']").val();


								onlinePack.thnlFileSeq = $(this).next().find("input:hidden.thnlFileForm").val();

								onlinePack.edctnSeq = actForm.edctnSeq;
								onlinePack.episdOrd = actForm.episdOrd;
								onlinePack.episdYear = actForm.episdYear;
								onlinePack.nm = onlineNm;
								onlinePack.url = onlineUrl;
								onlinePack.time = onlineTime;

								if(onlineNm ===undefined || onlineNm =="" && resultFlag == true){
									alert("강의명을 입력해 주세요");
									resultFlag = false;
								}

								if(onlineUrl ===undefined || onlineUrl =="" && resultFlag == true){
									alert("유튜브 URL을 입력해 주세요");
									resultFlag = false;
								}

								if(onlineTime ===undefined || onlineTime =="" && resultFlag == true){
									alert("강의 시간을  입력해 주세요");
									resultFlag = false;
								}

								var onlinefileArray = new Array();
								if(!($("#onlineList").find(".dropzone.attachFile").eq(fileIndex).get(0) === undefined) &&
									$("#onlineList").find(".dropzone.attachFile").eq(fileIndex).get(0).dropzone.files != undefined &&
									$("#onlineList").find(".dropzone.attachFile").eq(fileIndex).get(0).dropzone.files.length > 0){

									$.each($("#onlineList").find(".dropzone.attachFile").eq(fileIndex).get(0).dropzone.files, function(idx, data){

										//alt값  data에 넣어주기.
										data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

										for (let i in data) {
											if (data.hasOwnProperty(i)) {
												var temp = {};
												temp.fileSeq = data.fileSeq;
												temp.status = data.status;
												temp.width = data.width;
												temp.height = data.height;
												temp.webPath = data.webPath;
												temp.fieldNm = "lctrFileSeq";//data.fieldNm;
												temp.orgnFileNm = data.orgnFileNm;
												temp.fileDsc = data.fileDsc;
												temp.fileOrd = data.fileOrd;

												if(onlinefileArray == "" || (onlinefileArray[onlinefileArray.length-1].fileOrd != temp.fileOrd)){
													onlinefileArray.push(temp);
												}

											}
										}
									})
								}
								fileIndex = fileIndex + 1;
								onlinePack.fileList = onlinefileArray;
								lctrList.push(onlinePack);
							}

							onlineIndex = onlineIndex + 1;
						});


						actForm.lctrList = lctrList;
						//온라인교육 강의 관련 배열 세팅 끝

						//예산, 지출내역 세팅

						var bdgetList = new Array();

						$("input.calPmtForm").each(function(){
							var temp = {};

							temp.edctnSeq =  actForm.edctnSeq;
							temp.episdOrd = actForm.episdOrd;
							temp.episdYear = actForm.episdYear;

							temp.cd = $(this).attr("name");

							if("ED_BDGET_CD01011" == $(this).attr("name") || "ED_BDGET_CD02011" == $(this).attr("name")){
								temp.etcNm = $(this).val();
							}else{
								temp.pmt = $(this).val();
							}




							bdgetList.push(temp);
						});
						 actForm.bdgetList = bdgetList;

						 //오프라인평가 관련 데이터 세팅
						if(actForm.otsdExamPtcptYn != undefined && actForm.otsdExamPtcptYn == "Y"){

							var ptcptList = new Array();
							$("#ptcptListContainer").find("tr").each(function(){

								var ptcptForm = {};
								var examScore = $(this).find("input[name='examScore']").val();
								var ptcptSeq = $(this).find("input[name=delValueList]").val();
								var cmptnYn = $(this).find("td").find("select#cmptnYn").val();
								var orgCmptnYn = $(this).find("td").find("select#cmptnYn").data("orgcmptnyn");

								var cmptnChangeYn = "N";//수료여부 변경여부(변경없으면 굳이 안건드려서 수료일, 등등 업데이트 안침
								if(orgCmptnYn != cmptnYn) cmptnChangeYn = "Y";

								ptcptForm.edctnSeq = actForm.edctnSeq;//과정번호
								ptcptForm.episdYear = actForm.episdYear;//회차년도
								ptcptForm.episdOrd = actForm.episdOrd;//회차번호
								ptcptForm.ptcptSeq = ptcptSeq;//참여자 번호
								ptcptForm.examScore = examScore;//평가점수
								ptcptForm.cmptnYn = cmptnYn//수료여부
								ptcptForm.otsdExamPtcptYn = actForm.otsdExamPtcptYn;//오프라인 여부
								ptcptForm.cmptnChangeYn = cmptnChangeYn;

								

								ptcptList.push(ptcptForm);

							});


							actForm.ptcptList = ptcptList;

						}
						//오프라인평가 관련 데이터 세팅끝

						//수료여부

						if(resultFlag){
							//debugger;
							cmmCtrl.jsonAjax(function(data){
								alert("저장되었습니다.");
								location.href = "./list";
							}, actionUrl, actForm, "text");
						}



					}
				},
				msg : {
					empty : {
						text : " 입력해주세요."
					}
				}
			});

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});