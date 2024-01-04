define(["ezCtrl", "ezVald"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebi/EBIPtcptWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmData");

	//사업자등록 체크
	let bsnmChk = false;

	// set model
	ctrl.model = {
		id : {
			btnSearch : {
				event : {
					click : function() {
					}
				}
			},
			hpNo : {
				event : {
					input : function (event) {
						var phoneNumber = event.target.value.replace(/[^0-9]/g, '');
						event.target.value = phoneNumber;
					}
				}
			},
			name : {
				event : {
					input : function (event) {
						var name = event.target.value.replace(/[^a-zA-Zㄱ-힣]/g, '');
						event.target.value = name;
						$(this).focus();
					}
				}
			},

			//직급 기타
			pstnCd : {
				event : {
					change : function() {
						var pstnCd = document.getElementById('pstnCd');
						var selectPstn = pstnCd.options[pstnCd.selectedIndex].value;

						$("#pstnNm").val("");

						if(selectPstn == "MEM_CD01007") {
							$("#pstnNmChk").css('display', 'block');
							$("#pstnNm").removeClass("notRequired");
						}
						else {
							$("#pstnNmChk").css('display', 'none');
							$("#pstnNm").addClass("notRequired");
						}
					}
				}
			},

			//사업자 등록 번호 입력
			ptcptBsnmNo : {
				event : {
					input : function() {
						// 현재 입력된 값 가져오기
						var inputValue = $(this).val();

						// 숫자 이외의 문자 제거
						var sanitizedValue = inputValue.replace(/[^\d]/g, '');

						// 값 업데이트
						$(this).val(sanitizedValue);
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

			//사업자 등록 번호 인증
			btnCmpnChk : {
				event : {
					click : function() {
						//1. db를 조회 한다.
						if($("#ptcptBsnmNo").val() =='' || $("#ptcptBsnmNo").val() == undefined) {
							alert("사업자등록번호를 입력해주세요.");
							$("#ptcptBsnmNo").focus();
							bsnmChk = false;
							return ;
						}

						jQuery.ajax({
							url : "/mngwserc/nice/comp-chk",
							type : "post",
							data :
								{
									"compNum" : $("#ptcptBsnmNo").val()
								},
							success : function(data)
							{
								if(data.rsp_cd=='P000') {
									if(data.result_cd == '01') {
										if(data.comp_status == '1') {
											$("#ptcptCmpnNm").val(data.comp_name);
											// $(".rprsnt_nm").val(data.representive_name);
											$(".satisfy").css("display", "block");
											$("#authCheck").val("Y");
											bsnmChk = true;
										} else {
											alert("올바르지 않은 사업자등록번호입니다.\n‘123-45-67890’(- 제외) 형식으로 입력해주세요.");
											$("#ptcptBsnmNo").val("");
											$(".satisfy").css("display", "none");
											bsnmChk = false;
											return false;
										}
									} else {
										alert("올바르지 않은 사업자등록번호입니다.\n‘123-45-67890’(- 제외) 형식으로 입력해주세요.");
										$("#ptcptBsnmNo").val("");
										$(".satisfy").css("display", "none");
										bsnmChk = false;
										return false;
									}
								} else {
									alert("올바르지 않은 사업자등록번호입니다.\n‘123-45-67890’(- 제외) 형식으로 입력해주세요.");
									$("#ptcptBsnmNo").val("");
									$(".satisfy").css("display", "none");
									bsnmChk = false;
									return false;
								}
							},
							error : function(xhr, ajaxSettings, thrownError)
							{
								cmmCtrl.errorAjax(xhr);
								jQuery.jstree.rollback(data.rlbk);
							}
						});
					}
				}
			}

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

						//사업자 등록번호 인증 여부
						if($("#authCheck").val() != "Y") {
							alert("사업자등록번호를 인증해주세요.");
							return false;
						}

						//다시한번 정원 중복체크

						var seqObj = {};
						seqObj.edctnSeq = $("#edctnSeq").val();

						//정원수 체크
						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);
								//정원여유
								if(rtn.fxnumStta == "S"){
									cmmCtrl.frmAjax(function(resultData){
										var rtnData = resultData.rtnData;
										if(rtnData.regStat == "F"){
											alert("이미 등록된 신청자 입니다.");
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
						}, "/mngwserc/eb/ebi/fxnumChk", seqObj, "text")

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