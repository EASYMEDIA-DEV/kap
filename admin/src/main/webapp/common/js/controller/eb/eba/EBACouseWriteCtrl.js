define(["ezCtrl", "ezVald"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/eba/EBACouseWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");

	// 과정에 소속된 차수 조회
	var search = function (page){

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		$("#stduyMthdCd").attr("disabled", true);
		$("#stduyTimeCd").attr("disabled", true);
		$("#stduyDdCd").attr("disabled", true);


		cmmCtrl.listFrmAjax(function(respObj) {
			$formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
			//CALLBACK 처리
			ctrl.obj.find("#listContainer").html(respObj);
			//전체 갯수
			var totCnt = $(respObj).eq(0).data("totalCount");
			//총 건수
			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/mngwserc/eb/ebb/select", $formObj, "POST", "html");

		$("#stduyMthdCd").attr("disabled", false);
		$("#stduyTimeCd").attr("disabled", false);
		$("#stduyDdCd").attr("disabled", false);

	}

	//과정분류 조회
	var selectCtgryCdList = function(arg){

		if(arg === undefined){
			arg = $("#ctgryCd").data("ctgrycd");
		}

		var cdMst= {};
		cdMst.cd = $(arg).val();

		cmmCtrl.jsonAjax(function(data){
			callbackAjaxCtgryCdList(data);
		}, './classTypeList', cdMst, "text");
	}

	//과정분류 2뎁스 세팅
	var callbackAjaxCtgryCdList = function(data){

		var detailList = JSON.parse(data);
		var selectHtml = "<option value=''>전체</option>";

		for(var i =0; i < detailList.length; i++){

			var cd = detailList[i].cd;
			var cdNm = detailList[i].cdNm;

			selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
		}

		$("#ctgryCd option").remove();

		$("#ctgryCd").append(selectHtml);

		var ctgrycd = $("#ctgryCd").data("ctgrycd");

		$("#ctgryCd").val(ctgrycd).prop("selected", true);//조회된 과정분류값 자동선택
	}

	var setSelectBox = function(arg){

		if($("input[name='jdgmtYn']").is(":checked")){
			$("#cmptnJdgmt option:eq(0)").prop("selected", true);
			$("#cmptnJdgmt").addClass("notRequired");
		}else{
			$("#cmptnJdgmt").removeClass("notRequired");
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
			}
		},
		classname : {

			classType : {
				event : {
					change : function() {
						selectCtgryCdList(this);
					}
				}
			},

			jdgmtYn : {
				event : {
					change : function() {
						setSelectBox(this);
					}
				}
			},

			couseSearch : {
				event : {
					click : function(relStatus){
						//필수 : 0, 선수:1, 후속:2 // EDCTN_REL
						var edctnRelStatus = $(this).data("rel-status");



						cmmCtrl.getCouseSrchLayerPop(function(data){

							//선택한 교육과정이 여러개 일경우
							if(data.trObjList != null){
								var trObjList= data.trObjList;
								for(var i=0; i<trObjList.length;i++){
									var relForm = $(".exmplContainer").clone(true);
									 var rObj = trObjList[i];
									relForm.find("span.nm").text(rObj.nm);
									relForm.find("input.cloneHidden").prop("name", "edctnRel"+edctnRelStatus).val(rObj.edctnSeq);


									//다중등록할때 시퀀스 체크해서 중복값이면 패스함
									var passYn = false;//이 값이 true가 되면 이미 강사 목록에 있으므로 append목록에 추가하지 않는다.
									$(".relField").find("input:hidden").each(function(){
										if($(this).val() == rObj.edctnSeq) passYn = true;
									});

									if(!passYn){
										$(".relForm"+edctnRelStatus).append(relForm.html());
									}

								}


							//선택한 교육과정이 단건일경우
							}else{
								var relForm = $(".exmplContainer").clone(true);
								relForm.find("span.nm").text(data.nm);
								relForm.find("input.cloneHidden").prop("name", "edctnRel"+edctnRelStatus).val(data.edctnSeq);

								//다중등록할때 시퀀스 체크해서 중복값이면 패스함
								var passYn = false;//이 값이 true가 되면 이미 강사 목록에 있으므로 append목록에 추가하지 않는다.
								$(".relField").find("input:hidden").each(function(){
									if($(this).val() == data.edctnSeq) {
										alert("이미 등록된 과정입니다.");
										passYn = true;
									}
								});

								if(!passYn){
									$(".relForm"+edctnRelStatus).append(relForm.html());
								}



							}
						});
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

			//페이징 목록 갯수

			listRowSizeContainer : {
				event : {
					change : function(){
						//리스트 갯수 변경
						$formObj.find("input[name=listRowSize]").val($(this).val());
						search(1);
					}
				}
			}
		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			//cmmCtrl.setFormData($formObj);
			search();


			$(".classType").trigger("change");

			var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

			/* Editor Setting */
			jQuery("textarea[id$='StduyCntn']").each(function(){
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
						if($formObj.find(".dropzone").size() > 0)
						{
							cmmCtrl.fileFrmAjax(function(data){
								//콜백함수. 페이지 이동
								if(data.respCnt > 0){
									alert(actionMsg);
									location.replace("./list");
								}
							}, actionUrl, $formObj, "json");
						}
						else
						{
							cmmCtrl.frmAjax(function(data){
								if(data.respCnt > 0){
									alert(actionMsg);
									location.replace("./list");
								}
								actionUrl = "./list";
							}, actionUrl, $formObj, "post", "json")
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