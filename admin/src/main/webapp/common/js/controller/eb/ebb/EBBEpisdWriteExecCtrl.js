define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBEpisdWriteExecCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");


	// set model
	ctrl.model = {
		id : {





		},
		classname : {

			//교육차수 저장시 실행 -너무 길어서 따로 빼버림
			episdSubmit : {
				event: {
					click: function () {

						if($("#edctnSeq").val() == ""){
							alert("과정을 먼저 선택해주세요");
							return  false;
						}

						if($("#episdYear").val() == ""){
							alert("년도를 선택해주세요.");
							return false;
						}

						if($("#episdOrd").val() == ""){
							alert("회차를 선택하세요.");
							return false;
						}





						//접수기간 유효성 체크

						//교육기간 유효성 체크

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
							return false
						}





					}
				}
			}


		},
		immediately : function(event) {

			// 유효성 검사
			$formObj.validation({

				before : function(e) {



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
							alert("과정을 먼저 선택해주세요");
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
							return false;
						}

						if($("#srvSeq").val() == ""){
							alert("만족도조사를 선택해주세요");
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

						//actForm.cprtnInsttNm = $("#cprtnInsttNm").val();//협력기관
						actForm.cprtnInsttSeq = $("#cprtnInsttSeq").val();//협력기관


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


									if(onlineNm ===undefined || onlineNm =="" && resultFlag == true){
										alert("강의명을 입력해 주세요");
										resultFlag = false;
									}

									if(onlineUrl ===undefined || onlineUrl =="" && resultFlag == true){
										alert("유튜브 URL을 입력해 주세요");
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
								temp.pmt = $(this).val();
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
						//debugger;
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