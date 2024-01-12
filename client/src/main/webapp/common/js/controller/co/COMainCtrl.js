define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/co/COMainCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	// 2024-01-08 뉴스레터 수신 동의 체크 및 인서트 관련
	function newsletterSubmit(){
		// 뉴스레터 수신 동의 체크
		if(jQuery("#consentChk").is(":checked") == false){
			alert(msgCtrl.getMsg("fail.newsletter.agree"));
			jQuery("#consentChk").focus();
			return false;
		}
		// 뉴스 레터 등록
		if(jQuery("#consentChk").is(":checked") == true ){
			if(confirm(msgCtrl.getMsg("confirm.newsletterBtn"))){
				var newsletterForm = {};
				newsletterForm.email = jQuery("input[name=email]").val();

				cmmCtrl.jsonAjax(function(respObj) {
					if(JSON.parse(respObj).respCnt == "1") {
						alert(msgCtrl.getMsg("success.newsletterSuc"));
						jQuery("input[name=email]").val('');
						jQuery("#consentChk").prop("checked", false);
					}
				}, "/newsletter/insert", newsletterForm, "text");
			}
		}
	}

	// 집체교육 및 집체+온라인 교육 리스트 조회
	var groupselect = function (page){
		//data로 치환해주어야한다.
		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			ctrl.obj.find("#listContainer").html(respObj);
			
			// 집체데이터 리스트 그려준 후 온라인 데이터 호출
			onlineselect();

			//전체 갯수
			var totCnt = $("#totalCount").val();

		}, "/apply/groupselect", $formObj, "GET", "html");
	}

	// 온라인교육 및 집체+온라인 교육 리스트 조회
	var onlineselect = function (page){
		//data로 치환해주어야한다.
		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			ctrl.obj.find("#listContainer2").html(respObj);

			//전체 갯수
			var totCnt = $("#totalCount").val();

			//
			trainingPCFn();
			mainTrainingInitFn();

		}, "/apply/onlineselect", $formObj, "GET", "html");
	}


	// set model
	ctrl.model = {
		id : {
			eduScheduleBtn : {
				event : {
					click : function(){
						eduSchedule(this);
					}
				}
			},

		},
		classname : {
			popClose:{
				event : {
					click : function(){
						// 오늘 하루 보지 않기
						if($("#dontShowTodayChk").is(":checked")) {
							$.cookie("todayPop", 'check', {expires: 1, path: "/"});
						}
					}
				}
			},
			episdDtl : {
				event : {
					click : function(e){
						var edctnseq = $(e.target).closest("div").data("edctnseq");
						location.href="/education/apply/detail?detailsKey="+edctnseq;
					}
				}
			},

		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			groupselect();
			// 메인 로고 모션은 최초 접근시 쿠키 생성 후 해당 쿠키가 만료전까지 미노출 처리

			var introMotion = $.cookie("introMotion");
			if(typeof introMotion != "string" || introMotion != "check"){
				jQuery(".intro-motion").show();
				$.cookie("introMotion", 'check', {expires: 30, path: "/"});
			}else{
				jQuery(".intro-motion").hide();
			}

			// 메인 팝업
			var todayPop = $.cookie("todayPop");
			var mainPopCnt = jQuery(".main-popup").find(".swiper-slide").length;
			if((typeof todayPop != "string" || todayPop != "check") && mainPopCnt > 0){
				jQuery(".main-popup").show();
			}else{
				jQuery(".main-popup").hide();
			}

			jQuery(".newsletterBtn").on("click", function(){
				var emailChkYn = 'N';
				if(jQuery("input[name=email]").val() == ""){
					alert(msgCtrl.getMsg("fail.newsletter.notEmail"));
					jQuery("input[name=email]").focus();
					return false;
				}else{
					var emailRegex, patten, regResult, emailChk;
					emailRegex = "^[_a-zA-Z0-9-\.\_]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$"
					patten = eval("/" + emailRegex + "/g");
					emailChk = jQuery("input[name=email]").val();
					if(!patten.test(emailChk)) {
						alert(msgCtrl.getMsg("fail.newsletter.emailCheck"));
						jQuery("input[name=email]").focus();
						return false;
					}else{
						var newsletterForm = {};
						newsletterForm.email = jQuery("input[name=email]").val();

						cmmCtrl.jsonAjax(function(respObj) {
							if(JSON.parse(respObj).respCnt == "1") {
								alert(msgCtrl.getMsg("fail.newsletter.registeredEmail"));
								jQuery("input[name=email]").focus();
								emailChkYn = "N";
								return false;
							}else{
								newsletterSubmit();
							}
						}, "/newsletter/dup-email", newsletterForm, "text", false, true);
					}
				}
			});

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});