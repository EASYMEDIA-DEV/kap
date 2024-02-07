define(["ezCtrl", "ezVald"], function(ezCtrl) {

	//교육차수관리  > 신청자 등록

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
			},
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
							var gndr = data.gndr;//이메일

							var pstnNm = data.pstnNm//직급
							var pstnCdNm = data.pstnCdNm//직급상세

							var deptCdNm = data.deptCdNm;//부서명
							var deptDtlNm = data.deptDtlNm;//부서상세

							var hpNo = data.hpNo//휴대폰번호
							var telNo = data.telNo//회사전화번호
							var bsnmNo = data.bsnmNo;//사업자번호
							var memSeq = data.memSeq;//회원번호

							$(".memName").text(memName);
							$(".memEmail").text(memEmail);

							var pstnNmTxt = (pstnNm != '' && !(pstnNm === undefined)) ? pstnCdNm + "(" + pstnNm + ")" : pstnCdNm;
							$(".pstnCdNm").text(pstnNmTxt);

							var deptCdNmTxt = (deptDtlNm != '' && !(deptDtlNm === undefined)) ? deptCdNm + "(" + deptDtlNm + ")" : deptCdNm;
							$(".deptDtlNm").text(deptCdNmTxt);

							$(".hpNo").text(hpNo);
							$(".telNo").text(telNo);

							$("#memSeq").val(memSeq);
							$("#ptcptBsnmNo").val(bsnmNo.replaceAll("-", ""));

							var nowGndr = (gndr == "1") ? "M" : "F";
							$("#gndr").val(nowGndr);

						});

					}
				}
			},
			gpcIdSearch : {		/*추후 gpc 연동 하시길*/
				event : {
					click : function() {

						var seqObj = {};
						var gpcId = $("#gpcId").val();

						seqObj.gpcId = gpcId;

						var passFlag = true;

						//gpcId 인증 체크
						cmmCtrl.jsonAjax(function(data){

							var rtn = JSON.parse(data);
							var kapUserId = rtn.kapUserId; //gpc에서 전달받은 kap아이디
							var isGpcUser = rtn.isGpcUser; //gpc에서 전달받은 gpc가입여부(아이디가 있는지)
							var gndrCd = rtn.gndrCd; //gpc에서 전달받은 gpc아이디로 가입된 성별

							var nowId = $("#memId").val();
							var gndr = $("#gndr").val();

							//입력된 GPC 아이디가 GPC에 회원가입되어 있지 않은 경우 노출
							if(passFlag && isGpcUser =="N"){

								if(confirm("GPC 홈페이지에 회원가입해주세요.")){
									window.open("https://gpc.hyundai.co.kr/gpc/member/sign-up/terms/index.do");
								}
								passFlag = false;
							}

							//입력된 GPC 아이디에 KAP 아이디가 등록되어 있지 않은 경우 노출
							if(passFlag && kapUserId == ""){

								if(confirm("GPC 계정에 KAP 아이디를 등록해주세요.")){
									window.open("https://gpc.hyundai.co.kr/gpc/main/index.do");
								}
								passFlag = false;
							}

							//입력된 GPC 아이디에 KAP 아이디가 등록되어있지만 현재 신청하려는 kap아이디와 일치하지 않음
							if(passFlag && nowId != kapUserId){

								if(confirm("인증된 GPC 계정에 입력된 KAP 아이디와 일치하지 않습니다. \n GPC 사이트로 이동하시겠습니까?")){
									window.open("https://gpc.hyundai.co.kr/gpc/main/index.do");
								}
								passFlag = false;
							}

							//입력된 GPC 아이디의 성별과 현재 로그인된 계정의 성별이 다른 경우 노출
							if(passFlag && gndr != gndrCd){

								if(confirm("GPC 계정의 성별과 KAP 성별이 일치하지 않습니다. \nGPC 사이트로 이동하시겠습니까?")) {
									window.open("https://gpc.hyundai.co.kr/gpc/main/index.do");
								}

								passFlag = false;
							}

							if(passFlag){
								$("#gpcPass").val("Y");
								$("#gpcIdText").text("인증되었습니다.");
							}else{
								$("#gpcPass").val("N");
								$("#gpcIdText").text("");
							}


						}, "/gpc/kapedu/verifyUserId?gpcId="+gpcId, seqObj, "text")
					}
				}
			}
		},
		immediately : function() {

			jQuery(".CodeMirror").find("textarea").addClass("notRequired");


			//gpcId 변경시 인증과 메시지 전부 초기화
			$("#gpcId").change(function(){
				if($("#gpcPass").val() == "Y"){
					$("#gpcPass").val("N");
					$("#gpcIdText").text("");
				}

			});



			// 유효성 검사
			$formObj.validation({
				before : function(e) {

					var isValid = true;

					var gpcYn = $("#gpcYn").val();
					var gpcPass = $("#gpcPass").val();


					if($("#memSeq").val() == ""){
						alert("회원을 선택해주세요.");
						isValid = false;
						return false;
					}

					//교육장소가 gpc일 경우에만 출력하고 체크함
					if(gpcYn == "Y" && $("#gpcId").val() == "") {
						alert("GPC아이디를 입력해주세요.");
						isValid = false;
						return false;
					}



					if(gpcYn == "Y" && gpcPass == "N"){
						alert("GPC 아이디를 인증해주세요.");
						return false;
					}

					return isValid;
				},
				after : function() {
					var isValid = true, editorChk = true;


					if($("#memSeq").val() == ""){
						alert("회원을 선택해주세요.");
						isValid = false;
						return false;
					}

					//교육장소가 gpc일 경우에만 출력하고 체크함
					if(gpcYn == "Y" && $("#gpcId").val() == "") {
						alert("GPC아이디를 입력해주세요.");
						isValid = false;
						return false;
					}

					var gpcYn = $("#gpcYn").val();
					var gpcPass = $("#gpcPass").val();

					if(gpcYn == "Y" && gpcPass == "N"){
						alert("GPC 아이디를 인증해주세요.");
						return false;
					}


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
						seqObj.stduyMthdCd = $("#stduyMthdCd").val();//학습방식, 온라인이면 출석정보 등록 안함

						//정원수 체크
						cmmCtrl.jsonAjax(function(data){
							if(data !=""){
								var rtn = JSON.parse(data);
								//정원여유
								if(rtn.fxnumStta == "S"){

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