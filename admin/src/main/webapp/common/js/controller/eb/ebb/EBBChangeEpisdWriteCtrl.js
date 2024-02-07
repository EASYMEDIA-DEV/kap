define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	//교육차수관리  > 참여자목록 > 차수변경

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBChangeEpisdWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);


	// form Object
	var $formObj = jQuery("#frmChangeEpisdData").eq(0);


	// set model
	ctrl.model = {
		id : {
			btnSearch : {
				event : {
					click : function() {

					}
				}
			}
		},
		classname : {



			tempBtn : {
				event : {
					click : function() {

						var ptcptSeq = $("button.tempBtn").data("ptcptseq");

						$("#ptcptSeq").val(ptcptSeq);


						//선택된인원 명수 세팅
						var memList = new Array();
						var memSeq = $("#chan_memSeq").val();
						if(memSeq.indexOf(",")>-1){
							var memArray= memSeq.split(",");

							for(var i=0; i<memArray.length; i++){
								var memForm = {};
								memList.push(memForm);
							}
						}else{
							var memForm = {};
							memList.push(memForm);
						}
						var memCnt = memList.length;

						$(".selectChangeMemCnt").text(memCnt);

						cmmCtrl.frmAjax(function(respObj) {

							ctrl.obj.find("#changeEpisdListContainer").html(respObj);

						}, "/mngwserc/eb/ebb/selectChangeList", $formObj, "POST", "html");
					}
				}
			},

		},
		immediately : function(event) {

			var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

			// 유효성 검사
			$formObj.validation({
				after : function() {
					var isValid = true, editorChk = true;

					if (!editorChk)
					{
						isValid = false;
					}

					return isValid;
				},
				async : {
					use : true,
					func : function (){

						var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


						var actionForm = {};

						var resultFlag = true;

						actionForm.edctnSeq = $("#edctnSeq").val();//교육순번
						actionForm.episdSeq = $("#episdSeq").val();//회차순번
						actionForm.episdYear =$("#changeEpisdListContainer").find("input[name='delValueList']:checked").data("episdyear");//연도
						actionForm.episdOrd =$("#changeEpisdListContainer").find("input[name='delValueList']:checked").data("episdord");//회차정렬
						actionForm.stduyMthdCd = $("#stduyMthdCd").val();//학습방식

						var fxnumCnt = $("#changeEpisdListContainer").find("input[name='delValueList']:checked").closest("tr").find("td").eq(6).text();//정원수
						var accsCnt = $("#changeEpisdListContainer").find("input[name='delValueList']:checked").closest("tr").find("td").eq(7).text();//신청자
						var fxnumImpsbYn = $("#changeEpisdListContainer").find("input[name='delValueList']:checked").closest("tr").find("td").eq(7).data("fxnum_impsb_yn");//신청자



						var memSeq = $("#chan_memSeq").val();
						var memList = new Array();

						if(memSeq.indexOf(",")>-1){
							var memArray= memSeq.split(",");

							for(var i=0; i<memArray.length; i++){

								var memForm = {};

								memForm.edctnSeq = $("#edctnSeq").val();
								memForm.episdSeq = $("#episdSeq").val();

								memForm.episdYear = $("#prev_episdYear").val();
								memForm.episdOrd = $("#prev_episdOrd").val();

								memForm.ptcptSeq = memArray[i];

								memList.push(memForm);
							}
						}else{

							var memForm = {};

							memForm.edctnSeq = $("#edctnSeq").val();
							memForm.episdSeq = $("#episdSeq").val();

							memForm.episdYear = $("#prev_episdYear").val();
							memForm.episdOrd = $("#prev_episdOrd").val();

							memForm.ptcptSeq = memSeq;

							memList.push(memForm);
						}


						actionForm.ptcptList = memList;

						if($("#prev_episdYear").val() == $("#next_episdYear").val() && $("#prev_episdOrd").val() == $("#next_episdOrd").val()){
							alert("다른 년도,회차를 선택해주세요");
							resultFlag = false;
						}


						var addMemLength= memList.length;//선택한 인원의 수

						//정원 제한없으면 그냥 통과 정원제한없음:N,   정원제한임:Y
						if(fxnumImpsbYn !="N"){
							//현재신청자+변경할 인원수>정원수가 참이면 변경불가 알럿
							if((addMemLength+ Number(accsCnt)) > Number(fxnumCnt)){
								alert("정원이 초과하였습니다.");
								resultFlag = false;
							}
						}



						if(resultFlag){

							cmmCtrl.jsonAjax(function(data){

								var rtnData = JSON.parse(data);
								var respCnt  = rtnData.respCnt;

								if(respCnt == 0){
									alert("저장되었습니다.");
									location.href = "./list";
								}else{
									alert("이미 해당 회차에 신청한 회원입니다.");
									return false;
								}

							}, "./changeEpisd", actionForm, "text");
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