define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebm/EBMEduApplyOnlineStep1Ctrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);


	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);

	var setUrlTime = function(arg){

		var youtubeXhr2 = new XMLHttpRequest();
		youtubeXhr2.open('GET', 'https://www.youtube.com/watch?v=' + arg, 0);
		youtubeXhr2.send();
		var youtubeTime = youtubeXhr2.responseText.split('"lengthSeconds":"')[1].split('"')[0];

		return youtubeTime;
	}

	var setUrlName = function(arg){

		var youtubeXhr = new XMLHttpRequest();
		youtubeXhr.open('GET', 'https://noembed.com/embed?url=https://www.youtube.com/watch?v=' + arg, 0);
		youtubeXhr.send();

		var youtubeTitle = youtubeXhr.responseText.split('"title":"')[1].split('"')[0];
		var youtubeHqImage = youtubeXhr.responseText.split('"thumbnail_url":"')[1].split('"')[0];
		var youtubeMqImage = youtubeHqImage.replace('hq', 'mq');
		var youtubeSdImage = youtubeHqImage.replace('hq', 'sd');




		return youtubeTitle;
	}

	var lctrSearch = function (page){
		//data로 치환해주어야한다.

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			$("#listLctrContainer").html(respObj);
			if($("#onlineStepYn").val() == "N"){
				$(".onlineStep").hide();
			}


			//이름이 없을경우 추출해서 입력 해준다.
			$("#listLctrContainer").find(".list-item").each(function(){

				var urlKey = $(this).find(".urlKey").data("urlkey");

				var youtubeForm = COYoutubeCtrl.youtubeSearch(youtubeForm, urlKey);

				$(this).find(".urlName").text(youtubeForm.title);
				$(this).find(".urlTime").find("span").text(youtubeForm.duration);

			});

			//전체 갯수
			var totCnt = $("#listLctrContainer").find("#totalCount").val();
			//총 건수

			if(totCnt <= 10 ){
				$(".btn-wrap.align-center").hide();
			}else{
				var tempPage = (page === undefined || page == "") ? 1 : page;
				var tempPage = (page === undefined || page == "") ? 1 : page;

				var rtnPage = 0;

				if((tempPage * 10)>totCnt){
					rtnPage = totCnt
				}else{
					rtnPage = (tempPage * 10);
				}

				if(rtnPage == totCnt){
					$(".btn-wrap.align-center").hide();
				}else{
					$(".btn-wrap.align-center").show();
				}

				$(".btn-wrap.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");

			}

			$(".item-count").text();

			ctrl.obj.find("#listLctrContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listLctrContainer", "pagingContainer");
		}, "/my-page/edu-apply/onlineStep1Select", $formObj, "GET", "html");

	}


	// set model
	ctrl.model = {
		id : {
			btnSubmit : {
				event : {
					click : function() {
						$formObj.submit();
					}
				}
			},
		},
		classname : {

			//온라인강의 페이징 처리
			lctrPageSet : {
				event : {
					click : function() {
						//페이징 이동


						var pageIndex = $formObj.find("input[name=pageIndex]").val();
						lctrSearch(++pageIndex);
					}
				}
			},


			myPageDetail :{
				event : {
					click : function() {
						var detailsKey = $("#edctnSeq").val();
						var episdYear = $("#episdYear").val();
						var episdOrd = $("#episdOrd").val();
						var ptcptSeq = $("#ptcptSeq").val();

						location.href="./detail?detailsKey="+detailsKey+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&ptcptSeq="+ptcptSeq;
					}
				}
			},

			onlineStep : {
				event : {
					click : function() {

						if($("#onlineStepYn").val() == "Y"){

							var detailsKey = $("#edctnSeq").val();
							var episdYear = $("#episdYear").val();
							var episdOrd = $("#episdOrd").val();
							var ptcptSeq = $("#ptcptSeq").val();

							var lctrSeq = $(this).data("lctrseq");

							location.href="./onlineStep2?edctnSeq="+detailsKey+"&episdYear="+episdYear+"&episdOrd="+episdOrd+"&lctrSeq="+lctrSeq+"&ptcptSeq="+ptcptSeq;
						}


					}
				}
			},

		},
		immediately : function() {

			lctrSearch();

			// 유효성 검사
			$formObj.validation({
				async : {
				}
			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});