define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COFormCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form");
	// set model
	ctrl.model = {
		id : {
			//검색 Keyword Input Enter
			q : {
				event : {
					keydown : function(e) {
						if (e.keyCode == 13)
						{
							e.preventDefault();
							//검색 로직 실행
							$formObj.find("#btnSearch").click();
						}
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
						$formObj.clearForm();
						//FORM 전송 필수 데이터 삽입
						$formObj.find("#pageIndex").val( pageIndex );
						$formObj.find("#listRowSize").val( listRowSize );
						$formObj.find(".listRowSizeContainer").val( listRowSize );
						$formObj.find("#pageRowSize").val( pageRowSize );
						$formObj.find("#csrfKey").val( csrfKey );

						//캘린더 초기화
						cmmCtrl.setPeriod(this, "", "", false);

						//검색 로직 실행
						$formObj.find("#btnSearch").click();
					}
				}
			},
			//데이터 삭제
			btnDelete : {
				event : {
					click : function() {
						var frmDataObj    = $(this).closest("form");
						var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
						var delType = frmDataObj.data("delType");
						if (delActCnt > 0)
						{
							var isOk = true;
							// 계정은 최고 관리자 및 본인 계정은 삭제 불가
							if(delType == "account"){
								frmDataObj.find("input:checkbox[name='delValueList']:checked").each(function(){
									if($(this).data("authCd") == 99 || $("#lgnSsnId").val() == $(this).parent().parent().next().next().find("a").text()){
										isOk = false;
										return;
									}
								});

								if(!isOk){
									alert(msgCtrl.getMsg("fail.co.coa.cantDltAccounts"));
								}
							}

							if(isOk){
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
			/*//검색버튼 클릭시
			btnSearch : {
				event : {
					click : function() {
						//data로 치환해주어야한다.
						cmmCtrl.setFormData($formObj);
					}
				}
			}*/
		},
		classname : {
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
			}
		},
		immediately : function() {

			ctrl.obj.find(".checkboxAll").each(function(){
				var trgtObj = $(this).closest("div");
				var isCheck = false;
				if(trgtObj.find(".checkboxSingle:checked").size() == trgtObj.find(".checkboxSingle").size() && trgtObj.find(".checkboxSingle:checked").size() > 0){
					isCheck = true;
				}
				if(isCheck){
					$(this).attr("checked", true);
				}
			})

		}
	};

	ctrl.exec();

	return ctrl;
});