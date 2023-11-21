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


						});
					}
				}
			}
		},
		immediately : function() {
			//리스트 조회
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

						$("#episdOrd").trigger("change");

						var actForm = {};



						var accsStrtDt = $("#accsStrtDt").val();
						var accsStrtHour = $("#accsStrtHour").val();
						if(accsStrtHour<10) accsStrtHour = "0"+accsStrtHour;
						var accsEndDt = $("#accsEndDt").val();
						var accsEndHour = $("#accsEndHour").val();
						if(accsEndHour<10) accsEndHour = "0"+accsEndHour;

						var edctnStrtDt = $("#edctnStrtDt").val();
						var edctnStrtHour = $("#edctnStrtHour").val();

						var edctnEndDt = $("#edctnEndDt").val();
						var edctnEndHour = $("#edctnEndHour").val();

						var accsStrtDtm = accsStrtDt+" "+accsStrtHour+":00:00";
						var accsEndDtm = accsEndDt+" "+accsEndHour+":00:00";
						var edctnStrtDtm = edctnStrtDt+" "+edctnEndHour+":59:59";
						var edctnEndDtm = edctnStrtDt+" "+edctnEndHour+":59:59";

						actForm.edctnSeq = $("#edctnSeq").val();//교육순번
						actForm.cbsnCd = $("#cbsnCd").val();//업종코드

						actForm.episdYear =$("#episdYear").val();//연도
						actForm.episdOrd =$("#episdOrd").val();//회차정렬
						actForm.accsStrtDtm = edctnEndDtm;//접수시작일시
						actForm.accsEndDtm = accsEndDtm;//접수종료일시
						actForm.edctnStrtDtm = edctnStrtDtm;//교육시작일시
						actForm.edctnEndDtm = edctnEndDtm;//교육종료일시
						actForm.fxnumCnt = $("#fxnumCnt").val();//정원수
						actForm.fxnumImpsbYn = $("#fxnumImpsbYn").val();//정원제한여부
						actForm.rcrmtMthdCd = $("input[name='rcrmtMthdCd']:checked").val();//모집방법코드
						actForm.picNm = $("#picNm").val();//담당자명
						actForm.picEmail = $("#picEmail").val();//담당자이메일
						actForm.picTelNo = $("#picTelNo").val()//담당자전화번호
						actForm.placeSeq = $("#placeSeq").val();//교육장소순번
						actForm.srvSeq = $("#srvSeq").val();//설문순번
						actForm.examSeq = $("#examSeq").val();//시험순번
						actForm.cmptnAutoYn = $("input[name='cmptnAutoYn']:checked").val();//수료자동여부

						cmmCtrl.jsonAjax(function(data){
							location.href = "./list";
						}, actionUrl, actForm, "text")

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