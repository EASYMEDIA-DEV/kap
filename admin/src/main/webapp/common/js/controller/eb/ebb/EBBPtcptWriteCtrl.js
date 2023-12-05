define(["ezCtrl", "ezVald"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBPtcptWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");

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

			classType : {
				event : {
					change : function() {
					}
				}
			},

			memberSearch : {
				event : {
					click : function() {


						cmmCtrl.getPartsCompanyMemberLayerPop(function(data){

							var memName = data.memName;//이름
							var memEmail = data.memEmail;//이메일
							var pstnCdNm = data.pstnCdNm//직급
							var deptDtlNm = data.deptDtlNm;//부서명
							var hpNo = data.hpNo//휴대폰번호
							var telNo = data.telNo//회사전화번호
							var bsnmNo = data.bsnmNo;//사업자번호
							var memSeq = data.memSeq;//회원번호


							$(".memName").text(memName);
							$(".memEmail").text(memEmail);
							$(".pstnCdNm").text(pstnCdNm);
							$(".deptDtlNm").text(deptDtlNm);
							$(".hpNo").text(hpNo);
							$(".telNo").text(telNo);

							$("#memSeq").val(memSeq);
							$("#ptcptBsnmNo").val(bsnmNo.replaceAll("-", ""));

						});

					}
				}
			},



		},
		immediately : function() {


			jQuery(".CodeMirror").find("textarea").addClass("notRequired");

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

						//다시한번 정원 중복체크

						var seqObj = {};
						seqObj.edctnSeq = $("#edctnSeq").val();
						seqObj.episdYear = $("#episdYear").val();
						seqObj.episdOrd = $("#episdOrd").val();
						seqObj.episdSeq = $("#episdSeq").val();

						if($("#memSeq").val() == ""){
							alert("회원을 선택해주세요.");
							return false;
						}

						//정원수 체크
						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);
								//정원여유
								if(rtn.fxnumStta == "S"){
									//alert("등록시작");

									cmmCtrl.frmAjax(function(resultData){

										var rtnData = resultData.rtnData;
										if(rtnData.regStat == "F"){
											alert("이미 해당 회차에 신청한 회원입니다.");
										}else if(rtnData.regStat == "S"){
											alert("저장되었습니다.");
											history.back();
										}

									}, "./setPtcptInfo", $formObj, "post", "json");

									//정원초과
								}else{
									alert("정원이 초과되었습니다.");
									return false;
								}
							}

						}, "/mngwserc/eb/ebb/fxnumChk", seqObj, "text")



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