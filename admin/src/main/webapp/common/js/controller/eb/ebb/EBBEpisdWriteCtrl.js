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
	var $excelObj = ctrl.obj.parent().find(".excel-down");


	//온라인강의 복사본 생성
	var onlineHtml = $("#onlineList").find("tr.examTr").eq(0).clone(true);
	var onlineFileHtml = $("#onlineList").find("tr.examTr").eq(1).clone(true);
	//온라인강의 복사본 생성 끝


	//설문 개수 조회
	var svCntCheck = function () {
		var srvSeq = $("#srvSeq").val();

		if(!srvSeq) {
			return false;
		}

		var svMst = {};
		svMst.srvSeq = srvSeq;
		svMst.episdSeq = $("#episdSeq").val();

		cmmCtrl.jsonAjax(function(data){
			var rtn = JSON.parse(data);

			// alert(rtn.respCnt);
			if(rtn.respCnt == 0 ){
				$(".eduSrvSearch").attr("disabled", false); //만족도조사 추가버튼 사용 가능
				$("#srvStrtDtm, #srvEndDtm").attr("disabled", false); //설문, 시험 달력 사용 가능
			}
		}, './checkSurveyCnt', svMst, "text")
	}

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
			var totCnt = $("#ptcptListContainer").find("tr:first").data("totalCount");
			//총 건수
			ctrl.obj.find("#ptcptListContainerTotCnt").text(totCnt);

			$(".ptcptField").validation({});

			$(".ptcptField").find(".btnMemAtndc").on("click", function(){
				memAtndcLayer(this);
			});
			console.log(totCnt);

			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "ptcptListContainer", "ptcptPagingContainer");

			if(totCnt>10){
				$(".ptcptField").find("ul.pagination").find("li").on("click", function(){
					if(!confirm("다른 페이지로 이동 시 입력한 값은 저장되지 않습니다. 이동하시겠습니까?")){
						return false;
					}
				});
			}



		}, "/mngwserc/eb/ebb/episdPtcptList", $formObj, "GET", "html");

	}


	//출석부 레이어 세팅
	var memAtndcLayer = function(e){

		var edctnSeq =  $(e).data("edctnseq");
		var episdYear =  $(e).data("episdyear");
		var episdOrd =  $(e).data("episdord");



		var ptcptSeq =  $(e).data("ptcptseq");
		var memSeq =  $(e).data("memseq");

		/*console.log(ptcptSeq);*/

		//출석부 레이어 팝업 호출
		$(".ebbMemAtndcSrchLayer").one('show.bs.modal', function() {
			/*$(this).find("button.tempBtn").attr("data-ptcptSeq", ptcptSeq);*/

			$(".ebbMemAtndcSrchLayer").find("#edctnSeq").val(edctnSeq);
			$(".ebbMemAtndcSrchLayer").find("#episdYear").val(episdYear);
			$(".ebbMemAtndcSrchLayer").find("#episdOrd").val(episdOrd);

			$(".ebbMemAtndcSrchLayer").find("#ptcptSeq").val(ptcptSeq);
			$(".ebbMemAtndcSrchLayer").find("#memSeq").val(memSeq);



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
			$("#examSeq").val(null).prop("disabled", true);
			//$("#examSeq").addClass("notRequired");
			$("input:radio[name='cmptnAutoYn']:radio[value='N']").prop("checked", true);//오프라인평가 진행시 수료자동화여부 무조건 수동 고정
			$(".examNmForm").text("");
			$(".jdgmtYn").find("table").addClass("table-disabled");
		}else{
			//alert("평가 선택 해제");
			$("#examSeq").prop("disabled", false);
			//$("#examSeq").removeClass("notRequired");
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
				$(this).removeClass("comma");
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

		var classNm = "bdgetForm"+(idx+1);
		var totalForm = "<div class="+classNm+" style='width:100%;order:"+(idx+1)+" '>"+copyTitle.html()+lastForm+"</div>";
		$("#bdget").append(totalForm);
		/*$("#bdget").append(copyTitle.html());
		$("#bdget").append(lastForm);*/


	});

	//숫자형 금액 데이터 콤마찍기
	$(".comma").each(function(){
			var commaVal = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			$(this).val(commaVal);
		});

	}
	//협력기관 지출내역 자동계산

	var expnsPmt = function(){

		 var rtnVal = $("#expnsPmt").val();
		rtnVal = (rtnVal ===undefined || rtnVal == null) ? 0 : rtnVal;
		rtnVal = rtnVal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
		$("div.bdgetForm3").find("span.title").find("span").text(rtnVal);
	}

	//예산 지출내역 자동계산
	var totPmtSet = function(){

		//입력한 인풋의 최상단 폼 찾아감


		var totPmt1 =0;
		$(".calPmtForm.bdget01.comma").each(function(idx){
		//$(".calPmtForm.bdget01.numberChk").each(function(idx){
			var inputName = $(this).attr("name");

			if("ED_BDGET_CD01011".indexOf(inputName) == -1 || "ED_BDGET_CD02011".indexOf(inputName) == -1 ){
				if($(this).val() !="" && $(this).val() != undefined){
					totPmt1 = totPmt1 + parseInt($(this).val().replaceAll(",", ""));
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

		$(".calPmtForm.bdget02.comma").each(function(idx){
		//$(".calPmtForm.bdget02.numberChk").each(function(idx){
			var inputName = $(this).attr("name");

			if("ED_BDGET_CD01011".indexOf(inputName) == -1 || "ED_BDGET_CD02011".indexOf(inputName) == -1 ){
				if($(this).val() !="" && $(this).val() != undefined){
					totPmt2 = totPmt2 + parseInt($(this).val().replaceAll(",", ""));
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

		// //tr 갯수 감지해서 카운트해줌, 없으면 없는폼 꺼냄
		// var totCnt = $("#isttrContainer").find("tr").size();
		// var startCount = 2;
		//
		//
		// $("#isttrContainer").find("tr").each(function(idx, data){
		// 	if(idx>1){
		// 		$(this).find("td").eq(0).text(totCnt-idx);
		// 	}
		// });

		var totCnt = $("#isttrContainer").find("tr:visible").size();

		$("#isttrContainer").find("tr:visible").each(function(idx, data){
			$(this).find("td").eq(0).text(totCnt-idx);
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
		$(".eduRoom").css("display", "");

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
	}else if("STDUY_MTHD02" == stduyMthdCd){

			$(".onlineSet").css("display", "");
			$(".eduRoom").css("display", "none");
			$(".onlineSet").find("input:text").each(function(){

				if($(this).closest("tr").attr("class") !="examTr" && ($(this).attr("name") == 'onlineTime' ||  $(this).attr("name") == 'onlineNm' )){
					$(this).addClass("notRequired");
				} else if($(this).closest("tr").attr("class") !="examTr" && ($(this).attr("name") == 'onlineUrl')){
					$(this).removeClass("notRequired");
				}

			});
			$(".onlineSet").find("div.dropzone").each(function(){
				if($(this).closest("tr").attr("class") !="examTr" && $(this).attr("name") != 'onlineTime'){
					//$(this).removeClass("notRequired");
				}
			});


	}else{
		$(".onlineSet").css("display", "");
		$(".eduRoom").css("display", "");

		$(".onlineSet").find("input:text").each(function(){
			if($(this).closest("tr").attr("class") !="examTr" && ($(this).attr("name") == 'onlineTime' ||  $(this).attr("name") == 'onlineNm' )){
				$(this).addClass("notRequired");
			} else if($(this).closest("tr").attr("class") !="examTr" && ($(this).attr("name") == 'onlineUrl')){
				$(this).removeClass("notRequired");
			}

		});
		$(".onlineSet").find("div.dropzone").each(function(){
			if($(this).closest("tr").attr("class") !="examTr" && $(this).attr("name") != 'onlineTime'){
				//$(this).removeClass("notRequired");
			}
		});

	}

	//평가여부 N일경우 평가항목 숨김
	if("N" == jdgmtYn){
		$(".jdgmtYn").css("display", "none");
		$(".jdgmtYn").find("input:hidden").each(function(){
			//$(this).addClass("notRequired");
		});
		$("#examSeq").prop("disabled", true);
	}else{
		$(".jdgmtYn").css("display", "");
		$(".jdgmtYn").find("input:hidden").each(function(){

			if($(this).attr("name") !="otsdExamPtcptYn"){
				//$(this).removeClass("notRequired"); //이거 문제있음 고쳐야됨 오프라인평가 체크는 없애면 안됨
			}

		});

		//오프라인평가면 평가 시퀀스 사용안함
		if($("input[name='otsdExamPtcptYn']:checked").val() =="Y"){
			//$("#examSeq").addClass("notRequired").prop("disabled", true);

			setOtsdSelectBox();
			$("#examSeq").prop("disabled", true);
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

	}


	//교육안내 메일 발송시 에디터 세팅, 레이어가 그려지기전에 에디터 구현을 하면 내용 수정이 안되기때문에 레이어 활성화와 같이  textarea->에디터까지 한세트로 생성해줌
	var setMailEditor = function(){

		$(".editorArea").empty();
		var copyCntn = $("#copyCntn").clone(true);
		copyCntn.css("display", "");

		$(".editorArea").append(copyCntn);
		$(".editorArea").find("textarea").attr("id", "informCntn");
		$(".editorArea").find("textarea").attr("name", "informCntn");

		jQuery("textarea[id='informCntn']").each(function(){
			cmmCtrl.setEditor({
				editor : jQuery(this).attr("id"),
				height : 400
			});
		});

	}

	// set model
	ctrl.model = {
		id : {


			sendEduMail : {
				event : {
					click : function() {

						//교육내용 메일 발송 레이어팝업 호출
						$(".ebbInformMailLayer").one('show.bs.modal', function() {

							setMailEditor();

							var modal = $(this);
							modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

						}).one('hidden.bs.modal', function() {
							// Remove class for soft backdrop (if not will affect future modals)
						}).one('choice', function(data, param) {

						}).modal();

					}
				}
			},
			picEmail : {
				event : {
					input : function() {
						if ($(this).val() != "")
						{
							var check  = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

							if(check.test($(this).val() )) {
								$(this).val("");
								alert("한글입력은 불가능합니다.");
								event.preventDefault();

								return false;
							}
						}
					}
				}
			},

			btnExcel : {
				event : {
					click : function() {
						//사유입력 레이어팝업 활성화
						$excelObj.find("#rsn").val('');
						$excelObj.modal("show");
					}
				}
			},
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

						var resultFlag = true;


						//차수변경 클릭
						var seqObj = {};



						seqObj.edctnSeq = $("#edctnSeq").val();
						seqObj.episdYear = $("#episdYear").val();
						seqObj.episdOrd = $("#episdOrd").val();
						seqObj.episdSeq = $("#episdSeq").val();
						seqObj.stduyMthdCd = $("#stduyMthdCd").val();


						//체크한 회원정보 전부 가져옴
						var chkFlag = true;
						var seqList = new Array();
						$("#ptcptListContainer").find("input:checkbox[name='delValueList']:checked").each(function(){

							if(resultFlag && $(this).hasClass("changeChk")){
								seqList.push($(this).val());
							}else if(resultFlag){
								alert("교육대기인 회원만 선택 가능합니다.");
								resultFlag = false;
							}

						});


						if(resultFlag && seqList.length<1){
							alert("회원을 선택해주세요.");
							return false;

						}


						//선택한 참여자 목록중 교육취소가 하나라도 있으면 전부 취소
						$("#ptcptListContainer").find("input:checkbox[name='delValueList']:checked").each(function(){

							var eduStatus = $(this).closest("tr").find("td").eq(11).text();
							if(eduStatus == "교육취소"){
								alert("교육취소 상태인 참여자는 선택할수 없습니다.");
								resultFlag = false;
							}
						});


						console.log(seqList);
						if(resultFlag){
							seqObj.memSeq = seqList;


							//차수변경 레이어팝업 호출
							$(".ebbChageEpisdLayer").one('show.bs.modal', function() {
								$(".ebbChageEpisdLayer").find("#edctnSeq").val(seqObj.edctnSeq);
								$(".ebbChageEpisdLayer").find("#episdSeq").val(seqObj.episdSeq);
								$(".ebbChageEpisdLayer").find("#prev_episdYear").val(seqObj.episdYear);
								$(".ebbChageEpisdLayer").find("#prev_episdOrd").val(seqObj.episdOrd);

								$(".ebbChageEpisdLayer").find("#chan_memSeq").val(seqObj.memSeq);

								$(".ebbChageEpisdLayer").find("#stduyMthdCd").val(seqObj.stduyMthdCd);//학습방식

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
							alert("과정을 선택해주세요.");
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

						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);

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

			//QR다운로드
			btnQrDownload :{
				event:{
					click:function(){
						var detailsKey = ctrl.obj.find("input[name=detailsKey]").val();
						var episdYear = ctrl.obj.find("select[name=episdYear]").val();
						var episdOrd = ctrl.obj.find("select[name=episdOrd]").val();
						window.open("./qr-image-download?detailsKey=" + detailsKey + "&episdYear=" + episdYear + "&episdOrd=" + episdOrd);
					}
				}
			},

		},
		classname : {

			koreanCustromChk : {
				event : {
					input : function(e) {
						if ($(this).val() != "")
						{
							var regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"'\\]/g;
							var value = $(this).val();
							if (regexp.test(value)) {
								alert("한글만 입력 가능합니다");
								$(this).val("");
								return false;
							}
						}
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

						if ($(e.target).attr("href") == "#svResult"){
							$(".btn-success").hide();
						}else{
							$(".btn-success").show();
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

								if($(this).closest("fieldset").attr("class") == "ptcptField"){
									if(!$(this).prop("disabled")){
										$(this).prop("checked", isChecked);
									}else{
										$(this).prop("checked", false);
									}
								}

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
			//과정 검색
			couseSearch : {
				event : {
					click : function(){
						cmmCtrl.getCouseSrchLayerPop(function(data){

							if(data.choiceCnt  == -1){
								return false;
							}

							if(data.choiceCnt > 1){
								alert(msgCtrl.getMsg("fail.eb.eba.notSrchPlaceCouse1"));

							}else{
								$("#edctnSeq").val(data.edctnSeq);
								$("#ctgryCd").val(data.ctgryCd);

								$("p.ctgryCdNm").text(data.ctgryCdNm);

								$("p.nm").text(data.nm);
								$("p.stduyMthd").text(data.stduyMthd);
								$("#stduyMthdCd").val(data.stduyMthdCd);



								$("p.stduyDtm").text(data.stduyDtm);
								$("#episdList").css("display", "");
								$("#prevEpisd").css("display", "none");
							}

							if(($("#edctnSeq").val() == "" || $("#edctnSeq").val() === undefined)){
								$("#episdList").css("display", "none");
								$("#prevEpisd").css("display", "");
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

								if(data.choiceCnt  == -1){
									return false;
								}

								//두번호출 방지
								// if(data.choiceCnt == 1 && $("#isttrContainer").find("tr").find("input:hidden").length > 1){
								// 	return false;
								// }

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
//													$("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
													$("#isttrContainer").prepend("<tr>"+exIsttr.html()+"</tr>");
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

										$("#isttrContainer").find("tr").find("input:hidden").each(function(index){
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
//												$("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
												$("#isttrContainer").prepend("<tr>"+exIsttr.html()+"</tr>");
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

			//온라인강의 추가/삭제
			btnAdd : {
				event : {
					click : function(){
						onlineHtml.css("display", "").removeClass("examTr");/*.removeClass("notRequired");*/
						var firstTd = onlineHtml.find("td:first");
						var currentValue = parseInt(firstTd.text(), 10);
						firstTd.text(currentValue + 1);
						onlineFileHtml.css("display", "").removeClass("examTr").removeClass("notRequired");
						$("#onlineList").append("<tr>"+onlineHtml.html()+"</tr><tr>"+onlineFileHtml.html()+"</tr>");
						onlineHtml.css("display", "none").addClass("examTr");/*.addClass("notRequired");*/
						onlineFileHtml.css("display", "none").addClass("examTr").addClass("notRequired");

						//onlineHtml.find("input[name='onlineTime']").addClass("notRequired");

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
										/*location.reload();*/
										svCntCheck();
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
			//2023.12.21 참여자 목록 시험 응시 클릭
			btnExamPtcptSeq :{
				event : {
					click : function() {
						//레이어 팝업
						cmmCtrl.paramAjax(function(data){
							$("#exgExamUserDtlLayerContainer").html(data);
							$("#exgExamUserDtlLayer").one('show.bs.modal', function() {
								var modal = $(this);
								modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정
							}).one('hidden.bs.modal', function() {
							}).one('choice', function(data, param) {
								search(1)
							}).modal("show");

						}, "./getExamUserDtl", { ptcptSeq : $(this).data("ptcptSeq"), memSeq : $(this).data("memSeq"), _csrf:$('meta[name=X-CSRF-TOKEN]').attr('content') }, "html");
					}
				}
			},

},
		immediately : function(event) {

			$(".eduDt").change(function(){

				var accsEndDt = $("#accsEndDt").val();
				var edctnStrtDt = $("#edctnStrtDt").val();

				if(accsEndDt !="" && edctnStrtDt !="" ){
					if(accsEndDt == edctnStrtDt || accsEndDt > edctnStrtDt){

						alert("교육시작일이 접수종료일과 같거나 빠를수 없습니다.");
						$("#accsEndDt").val(null);
						$("#edctnStrtDt").val(null);
						return false;
					}
				}

			});


			if($("#edctnSeq").val() == ""){
				$("#episdList").css("display", "none");
				$("#prevEpisd").css("display", "");
			}else{
				$("#episdList").css("display", "");
				$("#prevEpisd").css("display", "none");
			}

			$excelObj.find("button.down").on('click', function(){
				var rsn = $excelObj.find("#rsn").val().trim();
				var frmDataObj    = $formObj.closest("form");

				frmDataObj.find("input[name='rsn']").remove();

				if (rsn != "") {
					frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

					//파라미터를 물고 가야함.
					//location.href = "./excel-down2?" + frmDataObj.serialize();

					$.fileDownload("./excel-down2?" + frmDataObj.serialize() , {
						prepareCallback : function(url){
							jQuery(".loadingbar").stop().fadeIn(200);
						},
						successCallback : function(url){
							jQuery(".loadingbar").stop().fadeOut(200);
							$excelObj.find("button.close").trigger('click');
						},
						failCallback : function(html,url){
							jQuery(".loadingbar").stop().fadeOut(200);
						}
					});


				} else {
					alert(msgCtrl.getMsg("fail.reason"));
					return;
				}

			});

			//신청자 발생시  modifyYn : N으로 변경되고 N일경우 온라인교육 수정이 불가하다
			//추가로 이 경우에는 강사, 만족도조사, 평가, 노출여부만 수정 가능하다.
			//교육이 시작했을경우 노출여부만 수정 가능하다. eduIng =Y

			var copyYn = $("#copyYn").val();

			if(copyYn == "N"){
				var modifyYn = $("#modifyYn").val();
				var eduIng = false;
				//교육 시작일 이전에 수정가능한 목록 제어
				var updateEdDt = $("#edctnStrtDt").val();
				if(updateEdDt != ""){
					var eduDate = new Date(updateEdDt);//교육 시작일
					var nowDate = new Date();//현재날짜

					//현재 날짜가 교육시작일 이후 = 교육중
					if(nowDate>=eduDate){

						$(".eduIsttrSearch").attr("disabled", true);//강사 추가버튼 사용 불가
						$(".btnOneTrRemove").attr("disabled", true);//강사 삭제버튼 사용불가
						$(".eduSrvSearch").attr("disabled", true);//만족도조사 추가버튼 사용불가
						/*$(".srvReset").attr("disabled", true);//설문 초기화버튼 사용불가*/
						$(".eduExamSearch").attr("disabled", true);//평가버튼 사용 불가
						$(".otsdExamPtcptYn ").attr("disabled", true);//오프라인평가버튼 사용불가

						//설문, 시험 달력 사용불가
						$("#srvStrtDtm, #srvEndDtm, #examStrtDtm, #examEndDtm").attr("disabled", true);

						$(".jdgmtYn").find("input").each(function(e){
							$(this).click(function(e){

								e.stopImmediatePropagation();
							});

						});
						eduIng = true;
					}
				}
			}






			//교육 참여자목록 조회
			if($("#detailsKey").val() != ""){
				search();

				// svCntCheck();
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


			if(copyYn == "N"){
				//신청자가 존재하는 단계 이 단계에서는 강사, 만족도 조사, 평가, 노출여부만 수정 가능하다(신청자가 존재하는 상태)
				if($("#modifyYn").val() == "N"){

					$("#episdYear").prop("disabled", true);//회차 년도
					$("#episdOrd").prop("disabled", true);//회차

					$("#cbsnCd").prop("disabled", true);//업종

					$("#accsStrtDt").prop("disabled", true);//접수 시작일
					$("#accsStrtHour").prop("disabled", true);//접수 시작시간
					$("#accsEndDt").prop("disabled", true);//접수 종료일
					$("#accsEndHour").prop("disabled", true);//접수 종료시간

					$("#edctnStrtDt").prop("disabled", true);//교육 시작일
					$("#edctnStrtHour").prop("disabled", true);//교육 시작시간
					$("#edctnEndDt").prop("disabled", true);//교육 종료일
					$("#edctnEndHour").prop("disabled", true);//교육 종료시간

					$("#fxnumCnt").prop("disabled", true);//정원수
					$("input[name='fxnumImpsbYn']").prop("disabled", true);//정원수 제한없음 체크박스
					$("input[name='rcrmtMthdCd']").prop("disabled", true);//모집방식
					$("input[name='cmptnAutoYn']").prop("disabled", true);//수료 자동화 여부

					$("#picNm").prop("disabled", true);//문의 담당자명
					$("#picEmail").prop("disabled", true);//문의 담당자 이메일
					$("#picTelNo").prop("disabled", true);//문의 담당자 전화번호

					$("button.cprtnInsttSearch").prop("disabled", true);//협력기관 검색버튼
					$("button.eduRoomSearch").prop("disabled", true);//교육장소 검색버튼

					//온라인강의 수정 불가처리
					$("#onlineList").find("input:text").prop("disabled", true);
					$("#onlineList").find("button").prop("disabled", true);

					$("#onlineList").find("tr").each(function(){
						$(this).find(".dz-hidden-input").prop("disabled",true).css("cursor", "not-allowed");
						$(this).find(".dropzone").removeClass("dz-clickable").css("cursor", "not-allowed");
						$(this).find(".dz-default.dz-message").hide().css("cursor", "not-allowed");
					});

				}

				//교육이 시작된 상태에서는 노출여부를 제외하고 전부 사용 불가능
				if($("#eduIng").val() == "Y"){

				}
			}

			$(".dz-hidden-input").prop("disabled",true);
			$(".dropzone").removeClass("dz-clickable").css("cursor", "not-allowed");
			$(".dz-default.dz-message").hide();
			$('.dz-remove').hide();


			// 유효성 검사
			$formObj.validation({

				before : function(e) {

					if($("#edctnSeq").val() == ""){
						alert("과정을 선택해주세요");
						$(".couseSearch").focus();
						return  false;
					}

					if($("#episdYear").val() == ""){
						alert("년도를 선택해주세요.");
						$("#episdYear").focus();
						return false;
					}

					if($("#episdOrd").val() == ""){
						alert("회차를 선택해주세요.");
						$("#episdOrd").focus();
						return false;
					}

					var accsStrtDt = $("#accsStrtDt");
					var accsStrtHour = $("#accsStrtHour");
					var accsEndDt = $("#accsEndDt");
					var accsEndHour = $("#accsEndHour");

					var edctnStrtDt = $("#edctnStrtDt");
					var edctnStrtHour = $("#edctnStrtHour");

					var edctnEndDt = $("#edctnEndDt");
					var edctnEndHour = $("#edctnEndHour");

					var attrArray = [accsStrtDt, accsStrtHour, accsEndDt, accsEndHour, edctnStrtDt, edctnStrtHour, edctnEndDt, edctnEndHour];
					var attrArrayText = ["접수시작일시", "접수시작시간시" ,"접수종료일" ,"접수종료시간" ,"교육시작일시" ,"교육시작시간" ,"교육종료일시" ,"교육종료시간"];

					var forStatus = true;
					for(var i=0; i<attrArray.length;i++){
						var attr = attrArray[i].val();
						if(attr == ""){
							var returnText = (attrArrayText[i].indexOf("시간")> -1 ) ? "을 입력 해주세요" :  "를 입력 해주세요";
							alert(attrArrayText[i] + returnText);
							attrArray[i].focus();
							forStatus = false;
							break;
						}
					}
					if(!forStatus) return false;


					//강사목록 유효성 체크
					var checkList= new Array();
					$("input[name='isttrSeq']").each(function(){
						if($(this).val() != undefined && $(this).val() != ""){
							var tempForm = {};
							tempForm.edctnSeq = $("#edctnSeq").val();
							tempForm.episdYear = $("#episdYear").val();
							tempForm.isttrSeq = $(this).val();
							tempForm.episdOrd = $("#episdOrd").val();
							tempForm.episdSeq = $("#episdSeq").val();
							checkList.push(tempForm);
						}
					});


					if(checkList.length == 0){
						alert("강사를 추가 해주세요");
						$(".eduIsttrSearch").focus();
						return false
					}

					//정원
					var fxnumImpsbYn= $("input[name='fxnumImpsbYn']").is(":checked");//제한없음 체크여부
					if(!fxnumImpsbYn){
						if($("#fxnumCnt").val() ==""){
							alert("정원수를 입력해주세요.");
							$("#fxnumCnt").focus();
							return false
						}
					}

					//모집방식

					if($("input[name='rcrmtMthdCd']:checked").length == 0){
						alert("모집방식을 선택해주세요.");
						$("input[name='rcrmtMthdCd']").focus();
						return false
					}

					//문읨담당자
					if($("#picNm").val() == ""){
						alert("담당자명을 입력해주세요.");
						$("#picNm").focus();
						return false
					}
					//담당자이메일
					if($("#picEmail").val() == ""){
						alert("담당자이메일을 입력해주세요.");
						$("#picEmail").focus();
						return false
					}

					var email = $("#picEmail").val();
					var temp2 = /[_a-zA-Z0-9-\.\_]+@[\.a-zA-Z0-9-]+\.(com|net|kr|co\.kr|org)$/;
					var emailTest2 = temp2.test(email);

					if(!emailTest2){
						alert("이메일 양식에 맞게 입력 해주세요");
						$("#picEmail").focus();
						return false;
					}


					if($("#picTelNo").val() == ""){
						alert("담당자전화번호를 입력해주세요.");
						$("#picTelNo").focus();
						return false
					}


					//교육장소
					var stduyMthdCd = $("#stduyMthdCd").val();

					if(stduyMthdCd != "STDUY_MTHD02" && $("#placeSeq").val() == ""){
						alert("교육장소를 선택해주세요");
						$(".eduRoomSearch").focus();
						return false;
					}

					//집체교육이면 안넣음 (온라인 교육일 경우만)
					var stduyMthdCd = $("#stduyMthdCd").val();
					var onlineChk = true;
					if(stduyMthdCd != "STDUY_MTHD01"){

						$("#onlineList > tr").each(function(){

							var onlineNm = $(this).find("[name='onlineNm']").val();

							if(!(onlineNm === undefined) && $(this).attr("class") != "examTr"){
								var onlinePack = {};
								var onlineUrl = $(this).find("[name='onlineUrl']").val();
								var onlineTime = $(this).find("[name='onlineTime']").val();

								if(onlineUrl ===undefined || onlineUrl =="" && onlineChk == true){
									alert("유튜브 URL을 입력해 주세요");
									$("input[name='onlineUrl']").focus();
									onlineChk = false;
								}
							}
						});

					}

					if(!onlineChk){
						return false;
					}

					//만족도조사
					if($("#srvSeq").val() == "") {
						alert("만족도조사를 선택해주세요");
						$(".eduSrvSearch").focus();
						return false;
					}

					if($("#srvStrtDtm").val() == "") {
						alert("만족도조사 시작일시를 입력해주세요");
						$("#srvStrtDtm").focus();
						return false;
					}


					if($("#srvEndDtm").val() == "") {
						alert("만족도조사 종료일시를 입력해주세요");
						$("#srvEndDtm").focus();
						return false;
					}

					//평가
					if($("#examSeq").attr("disabled") === undefined && $("#examSeq").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined && $("#jdgmtYn").val() != "N"){
						alert("평가를 선택해주세요");
						$(".eduExamSearch").focus();
						return false;
					}

					if($("#examStrtDtm").is(":visible") && $("#examStrtDtm").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined && $("#jdgmtYn").val() != "N"){
						alert("평가 시작일시를 선택해주세요");
						$("#examStrtDtm").focus();
						return false;
					}

					if($("#examEndDtm").is(":visible") && $("#examEndDtm").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined && $("#jdgmtYn").val() != "N"){
						alert("평가 종료일시를 선택해주세요");
						$("#examEndDtm").focus();
						return false;
					}



				},
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

						/*if($("#edctnSeq").val() == ""){
							alert("과정을 선택해주세요");
							return  false;
						}*/

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
						//var accsEndDtm = accsEndDt+" "+accsEndHour+":59:59";
						var accsEndDtm = accsEndDt+" "+accsEndHour+":00:00";

						var edctnStrtDtm = edctnStrtDt+" "+edctnStrtHour+":00:00";
						//var edctnEndDtm = edctnEndDt+" "+edctnEndHour+":59:59";
						var edctnEndDtm = edctnEndDt+" "+edctnEndHour+":00:00";

						actForm.edctnSeq = $("#edctnSeq").val();//교육순번

						if($("#cbsnCd").val() !=""){
							actForm.cbsnCd = $("#cbsnCd").val()//업종코드
						}

						actForm.episdYear =$("#episdYear").val();//연도
						actForm.episdOrd =$("#episdOrd").val();//회차정렬

						actForm.episdSeq =$("#episdSeq").val();//회차순번

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
								tempForm.episdSeq = $("#episdSeq").val();
								isttrSeqList.push(tempForm);
							}

						});


						if(isttrSeqList.length == 0){
							alert("강사를 추가 해주세요");
							$(".eduIsttrSearch").focus();
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

						var stduyMthdCd = $("#stduyMthdCd").val();

						if(stduyMthdCd != "STDUY_MTHD02" && $("#placeSeq").val() == ""){
							alert("교육장소를 선택해주세요");
							$(".eduRoomSearch").focus();
							return false;
						}

						if($("#srvSeq").val() == "") {
							alert("만족도조사를 선택해주세요");
							$(".eduSrvSearch").focus();
							return false;
						}

						if($("#srvStrtDtm").val() == "") {
							alert("만족도조사 시작일시를 입력해주세요");
							$("#srvStrtDtm").focus();
							return false;
						}


						if($("#srvEndDtm").val() == "") {
							alert("만족도조사 종료일시를 입력해주세요");
							$("#srvEndDtm").focus();
							return false;
						}

						if($("#examSeq").attr("disabled") === undefined && $("#examSeq").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined && $("#jdgmtYn").val() != "N"){
							alert("평가를 선택해주세요");
							$(".eduExamSearch").focus();
							return false;
						}

						if($("#examStrtDtm").is(":visible") && $("#examStrtDtm").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined && $("#jdgmtYn").val() != "N"){
							alert("평가 시작일시를 선택해주세요");
							$("#examStrtDtm").focus();
							return false;
						}

						if($("#examEndDtm").is(":visible") && $("#examEndDtm").val() == "" && $("input[name='otsdExamPtcptYn']:checked").val() === undefined && $("#jdgmtYn").val() != "N"){
							alert("평가 종료일시를 선택해주세요");
							$("#examEndDtm").focus();
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

						//actForm.cprtnInsttNm = $("#cprtnInsttNm").val();//협력기관
						actForm.cprtnInsttSeq = $("#cprtnInsttSeq").val();//협력기관


						actForm.srvSeq = $("#srvSeq").val();//설문순번
						actForm.srvStrtDtm = $("#srvStrtDtm").val();//설문시작일
						actForm.srvEndDtm = $("#srvEndDtm").val();//설문종료일

						actForm.examStrtDtm = $("#examStrtDtm").val();//시험시작일
						actForm.examEndDtm = $("#examEndDtm").val();//시험종료일




						actForm.edctnNtctnFileSeq = $("#edctnNtctnFileSeq").val();

						actForm.examSeq = $("#examSeq").val();//시험순번
						actForm.otsdExamPtcptYn = $("input[name='otsdExamPtcptYn']:checked").val();//오프라인평가여부
						actForm.cmptnAutoYn = $("input[name='cmptnAutoYn']:checked").val();//수료자동여부
						actForm.expsYn = $("input[name='expsYn']:checked").val();//노출여부
						actForm.edctnCmpltnYn = $("input[name='edctnCmpltnYn']:checked").val();//교육완료여부


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

						actForm.modifyYn = modifyYn;
						actForm.eduIng = eduIng;
						if(modifyYn != "N"){

							//온라인교육 강의 관련 배열 세팅
							var lctrList = new Array();
							var onlineIndex = 0;
							var fileIndex = 1;

							//집체교육이면 안넣음
							var stduyMthdCd = $("#stduyMthdCd").val();
							if(stduyMthdCd != "STDUY_MTHD01"){
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


										/*if(onlineNm ===undefined || onlineNm =="" && resultFlag == true){
											alert("강의명을 입력해 주세요");
											resultFlag = false;
										}*/

										if(onlineUrl ===undefined || onlineUrl =="" && resultFlag == true){
											alert("유튜브 URL을 입력해 주세요");
											$("input[name='onlineUrl']").focus();
											resultFlag = false;
										}

										if(onlineTime ===undefined || onlineTime =="" && resultFlag == true){
											//alert("강의 시간을  입력해 주세요");
											//resultFlag = false;
											onlinePack.time = null;
										}else{
											onlinePack.time = onlineTime;
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
							}

							actForm.lctrList = lctrList;
							//온라인교육 강의 관련 배열 세팅 끝

						}


						//예산, 지출내역 세팅

						var bdgetList = new Array();

						//예산 지출내역 배열에 담기


						$("input.calPmtForm").each(function(){
							var temp = {};

							temp.edctnSeq =  actForm.edctnSeq;
							temp.episdOrd = actForm.episdOrd;
							temp.episdYear = actForm.episdYear;

							temp.cd = $(this).attr("name");

							if("ED_BDGET_CD01011" == $(this).attr("name") || "ED_BDGET_CD02011" == $(this).attr("name")){
								temp.etcNm = $(this).val();
							}else{
								temp.pmt = $(this).val().replaceAll(",", "");
							}




							bdgetList.push(temp);
						});

						//강사 강의시간 배열에 담기
						$("div.bdgetForm4").find("input").each(function(){
							var temp = {};
							temp.edctnSeq =  actForm.edctnSeq;
							temp.episdOrd = actForm.episdOrd;
							temp.episdYear = actForm.episdYear;

							temp.cd = $(this).attr("name");

							var pmt = ($(this).val() ===undefined || $(this).val() == "") ? 0 : $(this).val();
							temp.pmt = pmt;

							bdgetList.push(temp);

						});

						actForm.bdgetList = bdgetList;

						if($("#expnsCprtnInsttNm").val() !="" &&  $("#expnsPmt").val() == ""){
							alert("협력기관 지출금액을 입력해주세요.");
							$("#expnsPmt").focus();
							resultFlag = false;
						}

						if($("#expnsPmt").val() !="" &&  $("#expnsCprtnInsttNm").val() == ""){
							alert("협력기관을 입력해주세요.");
							$(".expnsCprtnInsttSearch").focus();
							resultFlag = false;
						}

						actForm.bdgetExpnsYn = $("input[name='bdgetExpnsYn']:checked").val();
						actForm.expnsCprtnInsttNm = $("#expnsCprtnInsttNm").val(); //지출 협업기관명
						actForm.expnsCprtnInsttSeq = $("#expnsCprtnInsttSeq").val(); //지출 협업기관 번호
						actForm.expnsPmt = $("#expnsPmt").val().replaceAll(",", "");//지출 협업기관 금액

						 //오프라인평가 관련 데이터 세팅
						//if(actForm.otsdExamPtcptYn != undefined && actForm.otsdExamPtcptYn == "Y"){

							var ptcptList = new Array();

						var ptcptCnt = $("#ptcptListContainer").find("tr:first").data("totalCount");

						if(ptcptCnt>0){
							$("#ptcptListContainer").find("tr").each(function(){

								var ptcptForm = {};
								var examScore = $(this).find("input[name='examScore']").val();
								var ptcptSeq = $(this).find("input[name=delValueList]").val();
								var memSeq = $(this).find("input[name=delValueList]").data("memseq");
								var cmptnYn = $(this).find("td").find("select#cmptnYn").val();
								var orgCmptnYn = $(this).find("td").find("#orgCmptnYn").val();//$(this).find("td").find("select#cmptnYn").data("orgcmptnyn");

								var oflnExamDtm = $(this).find("td").find("#oflnExamDtm").val();

								var cmptnChangeYn = "N";//수료여부 변경여부(변경없으면 굳이 안건드려서 수료일, 등등 업데이트 안침
								if(orgCmptnYn != cmptnYn) cmptnChangeYn = "Y";

								ptcptForm.edctnSeq = actForm.edctnSeq;//과정번호
								ptcptForm.episdYear = actForm.episdYear;//회차년도
								ptcptForm.episdOrd = actForm.episdOrd;//회차번호
								ptcptForm.ptcptSeq = ptcptSeq;//참여자 번호
								ptcptForm.memSeq = memSeq;//회원 번호

								ptcptForm.examScore = examScore;//평가점수
								ptcptForm.oflnExamDtm = oflnExamDtm;//오프라인점수 날짜

								ptcptForm.orgCmptnYn = orgCmptnYn//수료여부 원래값
								ptcptForm.cmptnYn = cmptnYn//수료여부
								ptcptForm.otsdExamPtcptYn = actForm.otsdExamPtcptYn;//오프라인 여부
								ptcptForm.cmptnChangeYn = cmptnChangeYn;



								ptcptList.push(ptcptForm);

							});
						}

						actForm.ptcptList = ptcptList;

						//}
						//오프라인평가 관련 데이터 세팅끝

						//수료여부
						if(resultFlag){
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