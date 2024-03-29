define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/eba/EBACouseListCtrl"
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
			var totCnt = $(respObj).eq(0).data("totalCount");
			//총 건수
			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/mngwserc/eb/eba/select", $formObj, "GET", "html");

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

						var $this = this;
						var delList = new Array();

						if($("#listContainer").find("input:checkbox[name='delValueList']:checked").length == 0){
							alert("삭제할 게시물을 선택해주세요.");
							return false;
						}

						$("#listContainer").find("input:checkbox[name='delValueList']:checked").each(function(){
							delList.push($(this).val());
						})
						var seqObj = {};
						seqObj['edctnSeqList'] = delList;
						cmmCtrl.jsonAjax(function(data){
							var rtn = JSON.parse(data);
							if(rtn.respCnt > 0){
								alert('회차가 등록된 게시물이 존재하여 삭제 할 수 없습니다.');
							}else{
								console.log('등록된 회차 없음');
								delCallback($this);
							}
						}, "./deleteChk", seqObj, "text");

						//delCallback(this);
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
						$formObj.find("input[name=detailsKey]").val(detailsKey);
						location.href = "./write?" + $formObj.serialize();
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
			//검색 레이어에서 선택시 호출
			btnCouseSrchLayerChoice:{
				event : {
					click: function(){
						var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
						if(choiceCnt  == -1){
							return false;
						}
						else if(choiceCnt == 0) {
							alert(msgCtrl.getMsg("fail.eb.eba.notSrchPartsCouse"));
						}
						/*else if(choiceCnt > 1){
							alert(msgCtrl.getMsg("fail.eb.eba.notSrchPlaceCouse1"));
						}*/else{
							var clickObj = {};
							clickObj.edctnSeq = ctrl.obj.find("input[name=delValueList]:checked").val();
							var trObj = ctrl.obj.find("input[name=delValueList]:checked").parents("tr");

							if(trObj.length >1){
								var trObjList = new Array();
								trObj.each(function(){
									var tempObj = {};
									var edctnSeq = $(this).find("td").eq(0).find("input[name=delValueList]:checked").val();
									var ctgryCd= $(this).find("td").eq(2).data("ctgrycd");//과정분류코드
									var ctgryCdNm= $(this).find("td").eq(2).text().trim();//과정분류
									var nm= $(this).find("td").eq(3).text().trim();//과정명
									var stduyMthd= $(this).find("td").eq(4).text().trim();//학습방식
									var jdgmtYn= $(this).find("td").eq(4).data("jdgmtyn");//평가여부
									var stduyDtm = $(this).find("td").eq(5).text().trim();//학습시간

									tempObj.edctnSeq = edctnSeq;
									tempObj.ctgryCd = ctgryCd;
									tempObj.ctgryCdNm = ctgryCdNm;
									tempObj.nm = nm;
									tempObj.stduyMthd = stduyMthd;
									tempObj.jdgmtYn = jdgmtYn;

									tempObj.stduyDtm = stduyDtm;
									tempObj.choiceCnt = choiceCnt;

									trObjList.push(tempObj)

								});
								clickObj.trObjList = trObjList;
								clickObj.choiceCnt = choiceCnt;


							}else{
								var edctnSeq =trObj.find("td").eq(0).find("input[name=delValueList]:checked").val();
								var ctgryCd= trObj.find("td").eq(2).data("ctgrycd");//과정분류코드
								var ctgryCdNm= trObj.find("td").eq(2).text().trim();//과정분류
								var nm= trObj.find("td").eq(3).text().trim();//과정명
								var stduyMthd= trObj.find("td").eq(4).text().trim();//학습방식
								var stduyMthdCd= trObj.find("td").eq(4).data("stduymthdcd");//학습방식코드
								var jdgmtYn= trObj.find("td").eq(4).data("jdgmtyn");//평가여부
								var stduyDtm = trObj.find("td").eq(5).text().trim();//학습시간

								clickObj.edctnSeq = edctnSeq;
								clickObj.ctgryCd = ctgryCd;
								clickObj.ctgryCdNm = ctgryCdNm;
								clickObj.nm = nm;
								clickObj.stduyMthd = stduyMthd;
								clickObj.stduyMthdCd = stduyMthdCd;
								clickObj.jdgmtYn = jdgmtYn;

								clickObj.stduyDtm = stduyDtm;
								clickObj.choiceCnt = choiceCnt;
							}

							var passYn = "S";//S:등록가능, F:이미있음, M:현재 수정중인 과정   이 값이 true가 되면 이미 목록에 있으므로 append목록에 추가하지 않는다.


								//다중
								if(clickObj.trObjList != null){

									var trObjList= clickObj.trObjList;
									for(var i=0; i<trObjList.length;i++){
										var rObj = trObjList[i];

										//다중등록할때 시퀀스 체크해서 중복값이면 패스함

										$(".relField").find("input:hidden").each(function(){
											if($(this).val() == rObj.edctnSeq && passYn != "F" && passYn != "M") passYn = "F";

											if($("#detailsKey").val() != "" && $(this).val() == $("#detailsKey").val() && passYn != "F" && passYn != "M") passYn = "M";
										});

										if(passYn =="F" || passYn =="M"){
											break;
										}
									}

								//단건
								}else{

									$(".relField").find("input:hidden").each(function(){
										if($(this).val() == clickObj.edctnSeq && passYn != "F" && passYn != "M" ) {
											passYn = "F";
										}
									});
									if($("#detailsKey").val() != "" && clickObj.edctnSeq== $("#detailsKey").val() && passYn != "F" && passYn != "M") passYn = "M";

								}


							if(passYn == "F"){
								alert("이미 등록된 과정입니다.");
								return false;
							}else if(passYn == "M"){
								alert("현재 수정중인 과정은 선택할 수 없습니다.");
								return false;
							}else{
								ctrl.obj.trigger("choice", [clickObj])
								ctrl.obj.find(".close").click();
							}


						}
					}
				}
			}
		},
		immediately : function() {

			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			//폼 데이터 처리후 2뎁스가 활성화 되어있다면 오픈해준다.
			$(".detailCdList").find("input:checkbox").each(function(){
				//2뎁스 체크박스 값이 있다면 뭉치 활성화 해줌
				if($(this).is(":checked")){
					$(this).closest(".cdListContainer").css("display", "");
				}else{
					//2뎁스 체크박스 값 없으면 비활성화 처리함, 근데 같은 영역안에 체크된거 있으면 예외 처리해줌
					if($(".cdListContainer").eq(0).find("input:checkbox:checked").length == 0){
						$(this).prop("disabled", true);
					}
				}
			});



			search();

			var searchInput = $("#btnSearch").closest('fieldset').find('input:text');
			searchInput.on('keypress', function(e){
				if (e.keyCode == 13){
					$formObj.find("#btnSearch").click();
				}
			})


			$excelObj.find("button.down").on('click', function(){
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



					//파라미터를 물고 가야함.
					/*location.href = "./excel-down?" + frmDataObj.serialize();*/

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