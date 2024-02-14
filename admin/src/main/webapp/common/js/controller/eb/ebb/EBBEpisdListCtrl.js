define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBEpisdListCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);
	var $excelObj = ctrl.obj.parent().find(".excel-down");


	//var 선택삭제
	var delCallback = function(obj){

		var frmDataObj    = $(obj).closest("form");
		var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
		var delType = frmDataObj.data("delType");
		if (delActCnt > 0)
		{
			// 계정은 최고 관리자 및 본인 계정은 삭제 불가
			if(confirm(msgCtrl.getMsg("confirm.del")))
			{
				//삭제 전송
				cmmCtrl.frmAjax(function(respObj){
					if(respObj != undefined && respObj.respCnt > 0){
						var msg = msgCtrl.getMsg("success.del.target.none");
						if(typeof delType!= "undefined" && typeof msgCtrl.getMsg("success.del.target." + delType) != "undefined"){
							msg = msgCtrl.getMsg("success.del.target." + delType);
						}
						alert(msg);
						$formObj.find("#btnSearch").click();
					}
					else{
						alert(msgCtrl.getMsg("fail.act"));
					}
				}, "./delete", frmDataObj, "POST", "json");
			}

		}
		else
		{
			if(typeof delType!= "undefined")
			{
				alert(msgCtrl.getMsg("fail.del.target." + frmDataObj.data("delType")));
			}
			else
			{
				alert(msgCtrl.getMsg("fail.target"));
			}

			return;
		}

	}


	// 목록 조회
	var search = function (page){
		//data로 치환해주어야한다.
		//cmmCtrl.setFormData($formObj);

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {
			$formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
			//CALLBACK 처리
			ctrl.obj.find("#listContainer").html(respObj);
			//전체 갯수

			var totCnt = ctrl.obj.find("#listContainer").find("tr:first").data("totalCount");
			//총 건수
			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/mngwserc/eb/ebb/select", $formObj, "POST", "html");

	}


	var callbackAjaxCdList  = function (cdList){

		console.log(cdList);

		var detailList = cdList.detailList;

		ctrl.obj.find("#cdListContainer").html(cdList);

		$(".detailCdList").html(tempHtml);


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

			//등록 페이지 이동
			btnWrite : {
				event : {
					click : function() {
						//파라미터를 물고 가야함. 목록 버튼 클릭시 검색 조건 물고 넘겨야함.
						$formObj.find("input[name=_csrf]").remove();
						location.href = "./write";
					}
				}
			},

			//검색 초기화
			btnRefresh : {
				event : {
					click : function() {
						//FORM 데이터 전체 삭제
						var pageIndex 	= $formObj.find("#pageIndex").val();
						var listRowSize = $formObj.find("#listRowSize").val();
						var pageRowSize = $formObj.find("#pageRowSize").val();
						var csrfKey 	= $formObj.find("#csrfKey").val();
						var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
						$formObj.clearForm();
						//FORM 전송 필수 데이터 삽입
						$formObj.find("#pageIndex").val( pageIndex );
						$formObj.find("#listRowSize").val( listRowSize );
						$formObj.find(".listRowSizeContainer").val( listRowSize );
						$formObj.find("#pageRowSize").val( pageRowSize );
						$formObj.find("#csrfKey").val( csrfKey );
						$formObj.find("input[name=srchLayer]").val( srchLayer );

						//캘린더 초기화
						cmmCtrl.setPeriod(this, "", "", false);

						//검색 로직 실행
						$formObj.find("#btnSearch").click();
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
			btnCopy : {
				event :{
					click : function() {


						var valList = jQuery("input[name='delValueList']:checked");

						//여러개 체크 할 경우
						if(valList.length>1){
							alert("복사는 1개의 게시물만 가능합니다.");
							return false;
							//과정이 없는경우
						}else if(valList.length == 0){
							alert("복사할 과정을 선택해주세요.");
							return false;
						}
						//복사
						$("#copyYn").val("Y");
						valList.closest("tr").find("td:eq(7) > a").trigger("click")
					}
				}
			},
			btnEdDelete : {
				event : {
					click : function() {
						//선택삭제 진행

						if($("#vueList").find("input:checkbox[name='delValueList']:checked").length == 0){
							alert("삭제할 게시물을 선택해주세요.");
							return false;
						}

						var actionForm = {};

						var seqList = new Array();
						$("#vueList").find("input:checkbox[name='delValueList']:checked").each(function(){
							seqList.push($(this).data("episdseq"));
						});

						actionForm.edctnSeq = 0;
						actionForm.episdOrd = 0;
						actionForm.episdYear = 0;
						actionForm.episdSeqList = seqList;

						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);

								if(rtn.rsn == "F"){
									alert("교육 참여자가 존재합니다.");
								}else{
									alert("삭제되었습니다.");
									location.reload;
								}
							}

						}, "./episdDeleteChk", actionForm, "text");
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
			//상세보기
			listView : {
				event : {
					click : function() {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						var episdYear = $(this).data("episdYear");
						var episdOrd = $(this).data("episdOrd");
						$formObj.find("input[name=detailsKey]").val(detailsKey);
						$formObj.find("input[name=episdYear]").val(episdYear);
						$formObj.find("input[name=episdOrd]").val(episdOrd);


						location.href = "./write?" + $formObj.serialize();
					}
				}
			},
			//페이징 목록 갯수

			//검색 레이어에서 선택시 호출
			btnPartsCompanyLayerChoice:{
				event : {
					click: function(){
						var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
						if( choiceCnt > 1){
							alert(msgCtrl.getMsg("fail.mp.mpe.notSrchPartsCom1"));
						} else if(choiceCnt == 0){
							alert(msgCtrl.getMsg("fail.mp.mpe.notSrchPartsCom"));
						}else{
							var clickObj = {};
							clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
							var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());
							clickObj.titl = titl;
							ctrl.obj.trigger("choice", [clickObj])
							ctrl.obj.find(".close").click();
						}
					}
				}
			},

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

			//대시보드에서 접근시 검색 필터 설정해줌
			var dashType= $("#dashBoardType").val();

			if(!(dashType === undefined)){
				//접수중
				if(dashType == "A"){
					$("input[name='accsStatus']").eq(1).prop("checked", true);
					//교육대기
				}else if(dashType == "B"){
					$("input[name='edctnStatus']").eq(0).prop("checked", true);
					//교육중
				}else if(dashType == "C"){
					$("input[name='edctnStatus']").eq(1).prop("checked", true);
				}
			}


			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			search();

			var searchInput = $("#btnSearch").closest('fieldset').find('input:text');
			searchInput.on('keypress', function(e){
				if (e.keyCode == 13){
					$formObj.find("#btnSearch").click();
				}
			})
			$excelObj.find("button.down").on('click', function(){
				if (confirm("저장하시겠습니까?")){
					var rsn = $excelObj.find("#rsn").val().trim();
					var frmDataObj    = $formObj.closest("form");

					frmDataObj.find("input[name='rsn']").remove();

					if (rsn != "") {
						frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

							$.fileDownload("./excel-down?" + frmDataObj.serialize() , {
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
				}

			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});