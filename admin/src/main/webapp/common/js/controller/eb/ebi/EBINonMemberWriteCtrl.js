define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebi/EBINonMemberWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");
	var $excelObj = ctrl.obj.parent().find(".excel-down");

	var isChecked = function (msg)
	{
		var chkCnt = jQuery("input:checkbox[name='delValueList']:checked").length;

		if (chkCnt < 1)
		{
			if(msg) {
				alert(msg);
			}
			else {
				alert("게시물을 선택해 주세요.");
			}
			return false;
		}

		return true;
	};

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

		}, "/mngwserc/eb/ebi/nonMemberPtcptList", $formObj, "GET", "html");

	}

	//과정분류 조회
	var selectCtgryCdList = function(arg){
		var cdMst= {};

		if($(arg).val() && $(arg).val() != "-99") {
			cdMst.cd = $(arg).val();
		}
		else if($("#ctgryCd").data("ctgrycd") && $(arg).val() != "-99") {
			cdMst.cd = $("#ctgryCd").data("ctgrycd").substring(0, $("#ctgryCd").data("ctgrycd").length-3);
		}
		else {
			cdMst.cd = null;
		}

		cmmCtrl.jsonAjax(function(data){
			callbackAjaxCtgryCdList(data);
		}, './classTypeList', cdMst, "text");
	}

	//과정분류 2뎁스 세팅
	var callbackAjaxCtgryCdList = function(data){
		var detailList = JSON.parse(data);
		var selectHtml = "<option value=''>선택</option>";
		var cd = '';
		var cdNm = '';

		for(var i = 0; i < detailList.length; i++){
			cd = detailList[i].cd;
			cdNm = detailList[i].cdNm;
			selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
		}

		$("#ctgryCd option").remove();

		$("#ctgryCd").append(selectHtml);

		var ctgrycd = $("#ctgryCd").data("ctgrycd");

		if($("#cd").val() != '-99' && ctgrycd && ctgrycd.indexOf($("#cd").val()) != -1) {
			$("#ctgryCd").val(ctgrycd).prop("selected", true); //조회된 과정분류값 자동선택
		}
		else {
			$("#ctgryCd option:first").prop("selected", true);
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
		// console.log(idx);
		var classNm = "bdgetForm"+(idx+1);
		var totalForm = "<div class="+classNm+" style='width:100%;order:"+(idx+1)+" '>"+copyTitle.html()+lastForm+"</div>";
		$("#bdget").append(totalForm);
		/*$("#bdget").append(copyTitle.html());
		$("#bdget").append(lastForm);*/
	});

	}

	//협력기관 지출내역 자동계산
	var expnsPmt = function(){
		var rtnVal = $("#expnsPmt").val();
		rtnVal = (rtnVal === undefined || rtnVal == null) ? 0 : rtnVal;
		rtnVal = rtnVal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		$("div.bdgetForm3").find("span.title").find("span").text(rtnVal);
	}

	//예산 지출내역 자동계산
	var totPmtSet = function(){

		//입력한 인풋의 최상단 폼 찾아감


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
			//신청자 여러명 신청 취소
			cancelSelectEdctn : {
				event : {
					click : function() {
						if(isChecked("신청취소할 내역을 선택해주세요.")) {
							//선택삭제 진행
							var actionForm = {};

							var seqList = new Array();
							$("#vueList").find("input:checkbox[name='delValueList']:checked").each(function(){
								seqList.push($(this).data("ptcptSeq"));
							});

							if(seqList.length > 0) {
								if(confirm("선택한 신청내역을 취소하시겠습니까?")) {
									actionForm.ptcptSeqList = seqList;
									actionForm.edctnSeq = $("#edctnSeq").val();

									cmmCtrl.jsonAjax(function(data){
										if(data != ""){
											var rtn = JSON.parse(data);

											if(rtn.respCnt > 0){
												alert("취소되었습니다.");
												search();
											}else{
												alert("문제가 발생하였습니다.");
											}
										}
									}, "./updatePtcptList", actionForm, "text");
								}
							}
							else {
								alert("문제가 발생하였습니다.");
							}
						}
					}
				}
			},

			//신청자 여러명 신청 취소
			cancelEdctn : {
				event : {
					click : function() {
						//선택삭제 진행
						var actionForm = {};

						if(confirm("해당 신청내역을 취소하시겠습니까?")) {
							actionForm.ptcptSeq = $(this).data("ptcptSeq");
							actionForm.edctnSeq = $("#edctnSeq").val();

							cmmCtrl.jsonAjax(function(data){
								if(data != ""){
									var rtn = JSON.parse(data);

									if(rtn.respCnt > 0){
										alert("취소되었습니다.");
										search();
									}else{
										alert("문제가 발생하였습니다.");
									}
								}
							}, "./updatePtcpt", actionForm, "text");
						}
						else {
							alert("문제가 발생하였습니다.");
						}
					}
				}
			},

			//신청자 정보 엑셀 다운로드 버튼
			btnExcel : {
				event : {
					click : function() {
						//사유입력 레이어팝업 활성화
						$excelObj.find("#rsn").val('');
						$excelObj.modal("show");
					}
				}
			},

			//정원 제한 없음 클릭 시 정원 수 입력란
			fxnumImpsbYn : {
				event : {
					click : function () {
						if ($(this).prop('checked')) {
							$("#fxnumCnt").val("");
							$("#fxnumCnt").addClass("notRequired");
							$("#fxnumCnt").prop("readonly", true);
						} else {
							$("#fxnumCnt").removeClass("notRequired");
							$("#fxnumCnt").prop("readonly", false);
						}
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

			btnAddApp : {
				event : {
					click : function() {
						//신청자등록 시작
						var seqObj = {};
						seqObj.edctnSeq = $("#edctnSeq").val();


						//정원수 체크
						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);
								//정원여유
								if(rtn.fxnumStta == "S"){
									//alert("정원 여유");
									//레이어팝업 호출
									location.href = "./ptcpt/write?detailsKey="+seqObj.edctnSeq;

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

		},
		classname : {

			//학습 대상 1개만 선택 가능하도록 처리
			cntCheck : {
				event : {
					click : function() {
						var thisVal = $(this).val();
						thisVal = thisVal.substring(0, 11);

						var checkboxes = $(".cntCheck[value*='" + thisVal + "']");

						var checkedCheckboxes = checkboxes.filter(":checked");

						if (checkedCheckboxes.length % 2 === 0) {
							checkboxes.prop("checked", false);
						}
					}
				}
			},

			classType : {
				event : {
					change : function() {
						selectCtgryCdList(this);
					}
				}
			},

			tabClick : {
				event : {
					click : function(e) {

						if($(e.target).attr("href") == "#bdget"){
							$("#bdget").css({"display" : "flex", "flex-wrap" : "wrap"});
						}else{
							$("#bdget").css({"display" : "", "flex-wrap" : ""});
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

			//리스트 전체 체크박스 선택시
			checkboxAll : {
				event : {
					click : function() {
						//상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxSingle가 변경됨
						var trgtArr = $(this).closest("div").find(".checkboxSingle");
						if (trgtArr.length > 0)
						{
							var isChecked = false;
							if($(this).is(":checked"))
							{
								isChecked = true;
							}
							$.each(trgtArr, function(){
								$(this).prop("checked", isChecked);
							})
						}
					}
				}
			},
			checkboxSingle : {
				event : {
					click : function() {
						//상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxAll이 변경됨
						var trgtObj = $(this).closest("div");
						var allCbxCnt = trgtObj.find(".checkboxSingle").length;
						var selCbxCnt = trgtObj.find(".checkboxSingle:checked").length;
						if (allCbxCnt == selCbxCnt)
						{
							trgtObj.find(".checkboxAll").prop("checked", true);
						}
						else
						{
							trgtObj.find(".checkboxAll").prop("checked", false);
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

			//협력기관 검색
			cprtnInsttSearch : {
				event : {
					click : function(){
						cmmCtrl.getEduRoomLayerPop(function(data){
							if(data.choiceCnt > 1){
								alert(msgCtrl.getMsg("fail.eb.ebf.notSrchPlaceCom1"));
							}else{
								var placeSeq = data.seq;
								var titl= data.titl;//교육장명

								$("#cprtnInsttSeq").val(placeSeq);
								$("#cprtnInsttNm").val(titl);
							}
						});
					}
				}
			},

			//예산 지출 협력 기관 검색
			expnsCprtnInsttSearch : {
				event : {
					click : function(){
						cmmCtrl.getEduRoomLayerPop(function(data){
							if(data.choiceCnt > 1){
								alert(msgCtrl.getMsg("fail.eb.ebf.notSrchPlaceCom1"));
							}else{
								var titl= data.titl;//교육장명
								var seq = data.seq;
								$("#expnsCprtnInsttNm").val(titl);
								$("#expnsCprtnInsttSeq").val(seq);
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

									//현재 추가된 강사수
									var nowRow = $("#isttrContainer").find("tr:not(.notIsttr):not(.setIsttr)").size();

									if((trObjList.length+nowRow)>6){
										alert("강사는 6명까지만 입력 가능합니다.");
									}else{

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

										//현재 추가된 강사수
										var nowRow = $("#isttrContainer").find("tr:not(.notIsttr):not(.setIsttr)").size();

										//앞으로 추가될 강사수
										var nextRow = 1;

										if((nextRow+nowRow)>6){
											alert("강사는 6명까지만 입력 가능합니다.");
										}else{
											$("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
										}
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
			}

		},
		immediately : function(event) {

			//신청자 정보 엑셀 다운
			$excelObj.find("button.down").on('click', function(){
				var rsn = $excelObj.find("#rsn").val().trim();
				var frmDataObj    = $formObj.closest("form");

				frmDataObj.find("input[name='rsn']").remove();

				if (rsn != "") {
					frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

					//파라미터를 물고 가야함.
					location.href = "./excel-down2?" + frmDataObj.serialize();

				} else {
					alert(msgCtrl.getMsg("fail.reason"));
					return;
				}

			});

			if($("#cd").val()) {
				selectCtgryCdList();
			}

			//교육 참여자목록 조회
			if($("#detailsKey").val() != ""){
				search();
			}
			
			//질문 번호 셋팅,점수 셋팅
			questionSet();

			//폼 데이터 처리
			filedSet();

			//예산지출내역 폼 조립
			bdgetSet();

			//페이지 로드시 예산지출내역, 협력기관지출내역 한번 계산하고 시작
			totPmtSet();
			expnsPmt();

			//예산지출내역 자동계산 함수 실행
			$(".calPmtForm").blur(function(){
				totPmtSet();
			});

			//협력기관 지출내역 자동계산 함수 실행
			$("#expnsPmt").blur(function(){
				expnsPmt();
			});


			/* Editor Setting */
			jQuery("textarea[id^='pcStduyCntn']").each(function(){
				cmmCtrl.setEditor({
					editor : jQuery(this).attr("id"),
					height : 400,
				});
			});
			jQuery("textarea[id^='mblStduyCntn']").each(function(){
				cmmCtrl.setEditor({
					editor : jQuery(this).attr("id"),
					height : 400,
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

						var actForm = {};

						var ctgryCd = $("#ctgryCd").val();
						var nm = $("#nm").val();
						var smmryNm = $("#smmryNm").val();

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

						//강사목록 세팅
						var isttrSeqList= new Array();
						$("input[name='isttrSeq']").each(function(){
							if($(this).val() != undefined && $(this).val() != ""){
								var tempForm = {};
								tempForm.edctnSeq = $("#edctnSeq").val();
								tempForm.isttrSeq = $(this).val();
								isttrSeqList.push(tempForm);
							}
						});

						if(isttrSeqList.length == 0){
							alert("강사를 추가 해주세요");
							$(".eduIsttrSearch").focus();
							return false
						}

						actForm.isttrSeqList = isttrSeqList; //강사

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
							$(".eduRoomSearch").focus();
							return false;
						}

						var checkedCount = $("input[name='targetCd']:checked").length;
						if(checkedCount < 4) {
							alert("학습 대상은 기타 항목을 제외한 각 항목 별로 1개씩 선택해주세요");
							$(".cntCheck").focus();
							return false;
						}
						if( checkedCount > 4) {
							alert("학습 대상의 각 항목 별로 1개씩만 선택해주세요");
							$(".cntCheck").focus();
							return false;
						}

						actForm.ctgryCd = ctgryCd;//과정분류
						actForm.nm = nm;//과정명
						actForm.smmryNm = smmryNm;//과정요약

						actForm.accsStrtDtm = accsStrtDtm;//접수시작일시
						actForm.accsEndDtm = accsEndDtm;//접수종료일시
						actForm.edctnStrtDtm = edctnStrtDtm;//교육시작일시
						actForm.edctnEndDtm = edctnEndDtm;//교육종료일시

						actForm.fxnumCnt = $("#fxnumCnt").val();//정원수
						actForm.fxnumImpsbYn = $("input[name='fxnumImpsbYn']:checked").val();//정원제한여부

						actForm.picNm = $("#picNm").val();//담당자명
						actForm.picEmail = $("#picEmail").val();//담당자이메일
						actForm.picTelNo = $("#picTelNo").val()//담당자전화번호

						actForm.placeSeq = $("#placeSeq").val();//교육장소순번

						actForm.itrdcCntn = $("#itrdcCntn").val();//과정소개
						actForm.stduyTrgtCntn = $("#stduyTrgtCntn").val();//학습목표

						//학습 대상 체크박스 체크된 요소 가져오기
						var targetCdList = $("input[name='targetCd']:checked").map(function() {
							return $(this).val();
						}).get();
						actForm.targetCdList = targetCdList; //학습대상

						actForm.stduyDdCd = $("#stduyDdCd").val();//학습일
						actForm.stduyTimeCd = $("#stduyTimeCd").val();//학습시간

						actForm.stduySuplsNm = $("#stduySuplsNm").val();//학습준비물

						//actForm.cprtnInsttNm = $("#cprtnInsttNm").val();//협력기관
						actForm.cprtnInsttSeq = $("#cprtnInsttSeq").val();//협력기관

						actForm.pcStduyCntn = $("#pcStduyCntn").val();//pc학습내용
						actForm.mblStduyCntn = $("#mblStduyCntn").val();//모바일학습내용
						



						actForm.edctnNtctnFileSeq = $("#edctnNtctnFileSeq").val();
						actForm.thnlFileSeq = $("#thnlFileSeq").val();

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

						//썸네일 셋팅
						var thnlFileArray = new Array();
						if(!($(".thnl").find(".dropzone.pcThumbFile").eq(0).get(0) === undefined) &&
							$(".thnl").find(".dropzone.pcThumbFile").eq(0).get(0).dropzone.files != undefined &&
							$(".thnl").find(".dropzone.pcThumbFile").eq(0).get(0).dropzone.files.length > 0) {
							$.each($(".thnl").find(".dropzone.pcThumbFile").eq(0).get(0).dropzone.files, function (idx, data) {
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

										if(thnlFileArray == "" || (thnlFileArray[thnlFileArray.length-1].fileOrd != temp.fileOrd)){
											thnlFileArray.push(temp);
										}

									}
								}

							})
						}
						actForm.pcThumbList = thnlFileArray;


						//예산, 지출내역 세팅
						var bdgetList = new Array();

						//예산 지출내역 배열에 담기
						$("input.calPmtForm").each(function(){
							var temp = {};

							temp.edctnSeq =  actForm.edctnSeq;

							temp.cd = $(this).attr("name");

							if("ED_BDGET_CD01011" == $(this).attr("name") || "ED_BDGET_CD02011" == $(this).attr("name")){
								temp.etcNm = $(this).val();
							}else{
								temp.pmt = $(this).val();
							}

							bdgetList.push(temp);
						});

						//강사 강의시간 배열에 담기
						$("div.bdgetForm4").find("input").each(function(){
							var temp = {};
							temp.edctnSeq =  actForm.edctnSeq;

							temp.cd = $(this).attr("name");

							var pmt = ($(this).val() ===undefined || $(this).val() == "") ? 0 : $(this).val();
							temp.pmt = pmt;

							bdgetList.push(temp);

						});

						actForm.bdgetList = bdgetList;

						if($("#expnsCprtnInsttNm").val() !="" &&  $("#expnsPmt").val() == ""){
							alert("협력기관 지출금액을 입력하세요.");
							resultFlag = false;
						}

						if($("#expnsPmt").val() !="" &&  $("#expnsCprtnInsttNm").val() == ""){
							alert("협력기관을 입력하세요.");
							resultFlag = false;
						}

						actForm.bdgetExpnsYn = $("input[name='bdgetExpnsYn']:checked").val();
						actForm.expnsCprtnInsttNm = $("#expnsCprtnInsttNm").val(); //지출 협업기관명
						actForm.expnsCprtnInsttSeq = $("#expnsCprtnInsttSeq").val(); //지출 협업기관 번호
						actForm.expnsPmt = $("#expnsPmt").val();//지출 협업기관 금액

						
						// debugger;
						if(resultFlag){
							//debugger;
							cmmCtrl.jsonAjax(function(data){
								if(data > 0) {
									alert("저장되었습니다.");
									location.href = "./list";
								}
								else {
									alert("문제가 발생하였습니다.");
									location.reload();
								}
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