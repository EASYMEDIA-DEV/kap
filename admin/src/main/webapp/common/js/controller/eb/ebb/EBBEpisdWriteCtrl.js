define(["ezCtrl", "ezVald"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBEpisdWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	//var $formObj = ctrl.obj.find("form").eq(0);
	var $formObj = jQuery("#frmData");

	//var onlineHtml = $.extend({}, $("#onlineList").find("tr.examTr").eq(0));
	//var onlineFileHtml = $.extend({}, $("#onlineList").find("tr.examTr").eq(1));
	var onlineHtml = $("#onlineList").find("tr.examTr").eq(0).clone();
	var onlineFileHtml = $("#onlineList").find("tr.examTr").eq(1).clone(true);


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
	}else{
		$(".jdgmtYn").css("display", "");
		$(".jdgmtYn").find("input:hidden").each(function(){
			$(this).removeClass("notRequired");
		});
	}
	}

	// set model
	ctrl.model = {
		id : {
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
			}

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
						})
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
		immediately : function() {
			//리스트 조회
			filedSet();
			//폼 데이터 처리

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
						actForm.srvSeq = $("#srvSeq").val();//설문순번
						actForm.srvStrtDtm = $("#srvStrtDtm").val();//설문시작일
						actForm.srvEndDtm = $("#srvEndDtm").val();//설문종료일

						actForm.edctnNtctnFileSeq = $("#edctnNtctnFileSeq").val();

						actForm.examSeq = $("#examSeq").val();//시험순번
						actForm.cmptnAutoYn = $("input[name='expsYn']:checked").val();//수료자동여부
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

								debugger;
								onlinePack.thnlFileSeq = $(this).next().find("input:hidden.thnlFileForm").val();

								onlinePack.edctnSeq = actForm.edctnSeq;
								onlinePack.episdOrd = actForm.episdOrd;
								onlinePack.episdYear = actForm.episdYear;
								onlinePack.nm = onlineNm;
								onlinePack.url = onlineUrl;
								onlinePack.time = onlineTime;

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


						//예산, 지출내역 세팅

						var bdgetList = new Array();

						$("input.calPmtForm").each(function(){
							var temp = {};

							temp.edctnSeq =  actForm.edctnSeq;
							temp.episdOrd = actForm.episdOrd;
							temp.cd = $(this).attr("name");
							temp.episdYear = actForm.episdYear;
							temp.pmt = $(this).val();

							bdgetList.push(temp);
						});
						 actForm.bdgetList = bdgetList;



						debugger;
						cmmCtrl.jsonAjax(function(data){
							alert("저장되었습니다.");
							location.href = "./list";
						}, actionUrl, actForm, "text");

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