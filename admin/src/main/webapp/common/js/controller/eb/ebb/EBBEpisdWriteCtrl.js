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

								onlinePack.thnlFileSeq = $(this).next().find("input:hidden").val();

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