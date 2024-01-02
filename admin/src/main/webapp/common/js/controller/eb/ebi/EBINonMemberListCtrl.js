define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebi/EBINonMemberListCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);
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
			var totCnt = $(respObj).eq(0).data("totalCount");
			//총 건수
			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/mngwserc/eb/ebi/select", $formObj, "POST", "html");

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
			btnRefresh : {
				event : {
					click : function () {
						$(".cdListContainer").css("display","none");
						$(".cdListContainer").attr("disabled", true);
						$(".cdListContainer").find("input:checkbox").prop("checked", false);
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
						valList.closest("tr").find("td:eq(3) > a").trigger("click")
					}
				}
			},
			btnEdDelete : {
				event : {
					click : function() {
						if(isChecked("삭제할 과정을 선택해주세요.")) {
							//선택삭제 진행
							var actionForm = {};

							var seqList = new Array();
							$("#vueList").find("input:checkbox[name='delValueList']:checked").each(function(){
								seqList.push($(this).data("edctnSeq"));
							});

							// actionForm.edctnSeq = 0;
							actionForm.edctnSeqList = seqList;

							cmmCtrl.jsonAjax(function(data){
								if(data !=""){
									var rtn = JSON.parse(data);

									if(rtn.rsn == "F"){
										alert("교육 신청자가 존재합니다.");
									}else{
										alert("삭제되었습니다.");
										location.reload;
									}
								}
							}, "./nonMemberDeleteChk", actionForm, "text");
						}
					}
				}
			},


		},
		classname : {

			classType : {
				event : {
					click : function() {

						$(".cdListContainer").css("display","none");
						$(".cdListContainer").attr("disabled", true);
						$(".cdListContainer").find("input:checkbox").prop("checked", false);


						$(".classType input:checked").each(function(){
							console.log($(this).val());

							var checkVal = $(this).val();

							var cdnm = $(this).data("cdnm"); //내일 이거 해야됨 클릭한것의 cdnm값 갖고오기
							$("."+checkVal).find(".cdnm").html(cdnm);
							$("."+checkVal).css("display","block");

							$("."+checkVal).find("input:checkbox").attr("disabled", false);
							console.log(cdnm);
							$("."+checkVal).find("input:checkbox").find("span").append(cdnm+"23434");


						});

						if($(".classType input:checked").length == 0){
							$(".cdListContainer").css("display","none");
							$(".cdListContainer").attr("disabled", true);
							$(".cdListContainer").find("input:checkbox").prop("checked", false);
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
			//상세보기
			listView : {
				event : {
					click : function() {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						// var episdYear = $(this).data("episdYear");
						// var episdOrd = $(this).data("episdOrd");
						$formObj.find("input[name=detailsKey]").val(detailsKey);
						// $formObj.find("input[name=episdYear]").val(episdYear);
						// $formObj.find("input[name=episdOrd]").val(episdOrd);


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
			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			search();

			$excelObj.find("button.down").on('click', function(){
				var rsn = $excelObj.find("#rsn").val().trim();
				var frmDataObj    = $formObj.closest("form");

				frmDataObj.find("input[name='rsn']").remove();

				if (rsn != "") {
					frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

					//파라미터를 물고 가야함.
					location.href = "./excel-down?" + frmDataObj.serialize();

				} else {
					alert(msgCtrl.getMsg("fail.reason"));
					return;
				}

			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});