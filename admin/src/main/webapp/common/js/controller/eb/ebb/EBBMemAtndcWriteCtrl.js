define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBMemAtndcWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);


	// form Object
	var $formObj = jQuery("#frmMemAtndcData").eq(0);


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

						cmmCtrl.frmAjax(function(respObj) {

							ctrl.obj.find(".memAtndcContainer").html(respObj);

						}, "/mngwserc/eb/ebb/memAtndcList", $formObj, "POST", "html");
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

						actionForm.edctnSeq = 0;//교육순번
						actionForm.episdSeq = 0;//회차순번
						actionForm.episdYear = 0;//연도
						actionForm.episdOrd = 0;//회차정렬


						//출석용 리스트
						var ptcptAtndcList = new Array();

						$("#memAtndcListLayerContainer").find("tr").each(function(){

							var deleteStack = 0;


							var memAtndcForm = {};

							memAtndcForm.ptcptSeq = $("#ptcptSeq").val();
							memAtndcForm.edctnDt =$(this).find(".edctnDt").text();


							var atndcHour = $(this).find(".atndcDtm").find("input[name='atndcDtm']").length == 0 ? "":  $(this).find(".atndcDtm").find("input[name='atndcDtm']").val();
							var lvgrmHour = $(this).find(".lvgrmDtm").find("input[name='lvgrmDtm']").length == 0 ? "" :  $(this).find(".lvgrmDtm").find("input[name='lvgrmDtm']").val();
							var orgEtcNm =  $(this).find(".etcNm").data("orgetcnm");
							var etcNm = $(this).find("#etcNm").val();
							if(atndcHour =="" && lvgrmHour != ""){
								alert("퇴실시간입력전에 출석 시간을 입력해주세요");
								resultFlag = false;
							}

							if(atndcHour !="" && lvgrmHour !=""  && atndcHour > lvgrmHour){
								alert("퇴실시간은 출석시간 이후로 입력 해주세요.");
								resultFlag = false;
							}

							var orgatndcHour = "";
							var orglvgrmHour = "";
							if(atndcHour !='' && !(atndcHour === undefined)){
								orgatndcHour = $(this).find(".atndcDtm").data("orgatndchour");
							}
							if(lvgrmHour !='' && !(lvgrmHour === undefined)){
								orglvgrmHour = $(this).find(".atndcDtm").data("orglvgrmHour");
							}

							if(orgatndcHour != atndcHour){
								memAtndcForm.atndcHour = atndcHour;
							}

							if(orglvgrmHour != lvgrmHour){
								memAtndcForm.lvgrmHour = lvgrmHour;
							}

							if(orgEtcNm != etcNm && etcNm !=""){
								memAtndcForm.etcNm = etcNm;
							}


							//변경점이 없는경우 or 애초에 값이 없는경우
							if(orgatndcHour == atndcHour || atndcHour == ""){
								deleteStack++;
							}

							if(orglvgrmHour == lvgrmHour || lvgrmHour == ""){
								deleteStack++;
							}

							if(orgEtcNm == etcNm){
								deleteStack++
							}

							if(deleteStack<3){
								ptcptAtndcList.push(memAtndcForm);
							}

						});

						actionForm.ptcptList = ptcptAtndcList;


						debugger;
						if(resultFlag){

							cmmCtrl.jsonAjax(function(data){
								alert("저장되었습니다.");
								location.href = "./list";
							}, "./updateAtndc", actionForm, "text");
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