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
	var $formLayerObj = ctrl.obj.find("form").eq(1);


	// 목록 조회
	var search = function (page){
		//data로 치환해주어야한다.

		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.listFrmAjax(function(respObj) {

			//CALLBACK 처리
			ctrl.obj.find("#listContainer").html(respObj);

			//전체 갯수
			var totCnt = $("#totalCount").val();
			//총 건수

			if(totCnt <= 9 ){
				$(".btn-wrap.add-load.align-center").hide();
			}else{
				var tempPage = (page === undefined || page == "") ? 1 : page;

				var rtnPage = 0;

				if((tempPage * 9)>totCnt){
					$(".btn-wrap.add-load.align-center").hide();
					rtnPage = totCnt
				}else{
					$(".btn-wrap.add-load.align-center").show();
					rtnPage = (tempPage * 9);
				}

				$(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
			}

			$(".article-total-count.f-body2").find("span").text(totCnt);


			$(".item-count").text();

			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/education/apply/select", $formObj, "GET", "html");

	}

	var searchLayer = function (arg){

		var searchForm = {};

		searchForm.ctgryCd = arg;
		cmmCtrl.jsonAjax(function(data){
			var rtn = JSON.parse(data);
			//정원여유

			$(".edctnList").empty();
			var listSize = rtn.list.length;

			if(listSize > 0){
				$.each(rtn.list, function(e){

					var $this = this;

					var addClass =  (e==0)? "swiper-slide txt-tab-btn active edctnSearch" : "swiper-slide txt-tab-btn edctnSearch";

					var temp = "<a class='"+addClass+"' href='javascript:' data-edctnseq="+$this.edctnSeq+"><p class='txt'><span class='menu-name'>"+$this.nm+"</span></p></a>";

					$(".edctnList").append(temp);

				});

				$(".edu-info-div").css("display", "");

				$(".listNotEmpty").css("display", "");
				$(".listEmpty").css("display", "none");
				$(".total-edu-area").removeClass("no-data");
			}else{
				$(".edu-info-div").css("display", "none");

				$(".listNotEmpty").css("display", "none");
				$(".listEmpty").css("display", "");
				$(".total-edu-area").addClass("no-data");
			}



		}, "/education/apply/selectEduList", searchForm, "text");

		$(".txt-depth-tab-swiper .txt-tab-btn").off().on("click", function(){
			$(this).addClass("active").siblings().removeClass("active");
		});

		$(".edctnList").find("a:first").trigger("click");

	}

	var searchEpisdLayer = function (edctnSeq){

		var searchForm = {};

		var now = new Date();	// 현재 날짜 및 시간
		var year = now.getFullYear();	// 연도

		searchForm.detailsKey = edctnSeq;
		searchForm.episdYear = year;

		cmmCtrl.jsonAjax(function(data){

			var rtn = JSON.parse(data);

			var rtnDto = rtn.rtnDto;
			var rtnEpisdList = rtn.rtnEpisdList;


			$(".edctnNmLayer").attr("href", "/education/apply/detail?detailsKey="+rtnDto.edctnSeq);
			$(".edctnNmLayer").find("span").text(rtnDto.nm);//제목변경 nm
			$(".stduyMthdLayer").text(rtnDto.stduyMthdCdNm);//교육타입 변경 stduyMthdCdNm
			$(".stduyDdLayer").text(rtnDto.stduyDdCdNm+"일("+rtnDto.stduyTimeCdNm+"시간)");//교육일자, 시간 변경

			var listSize = rtnEpisdList.length;

			if(listSize>0){
				$(".edu-info-div").css("display", "");
				$(".edu-plan-area").find(".listNotEmpty").find(".round-list").empty();
				$(".edu-plan-area").find(".listNotEmpty").find(".period-bar-list").empty();

				$(".listNotEmpty").css("display", "");
				$(".listEmpty").css("display", "none");
				$(".total-edu-area").removeClass("no-data");


				$.each(rtnEpisdList, function(){

					var copyEpisdSample = $(".copyEpisdSample").find(".round-div").clone(true);
					var copyEpisdSample2 = $(".copyEpisdSample2").find(".period-bar-div").clone(true);

					var $this = this;

					var episdOrd = $this.episdOrd;

					var accsStrtDt = $this.accsStrtDt;
					var accsEndDt = $this.accsEndDt;
					var edctnStrtDt = $this.edctnStrtDt;
					var edctnEndDt = $this.edctnEndDt;

					var accsStrtYear = $this.accsStrtYear;
					var accsEndYear = $this.accsEndYear;
					var edctnStrtYear = $this.edctnStrtYear;
					var edctnEndYear = $this.edctnEndYear;

					var episdYear = $this.episdYear;





					copyEpisdSample.find(".episdOrd").text(episdOrd+"회차");
					copyEpisdSample.find(".episdAccsDt").text(accsStrtDt + " ~ " + accsEndDt);
					if(episdYear != accsStrtYear && episdYear != accsEndYear){
						copyEpisdSample.find(".episdAccsDt").closest("div").addClass("no-this-year");
					}

					copyEpisdSample.find(".episdEduDt").text(edctnStrtDt + " ~ " + edctnEndDt);
					if(episdYear != edctnStrtYear && episdYear != edctnEndYear){
						copyEpisdSample.find(".episdEduDt").closest("div").addClass("no-this-year");
					}

					$(".eduYear").text(episdYear+"년");
					$(".edu-plan-area").find(".listNotEmpty").find(".round-list").append(copyEpisdSample);

					$(".edu-plan-area").find(".listNotEmpty").find(".period-bar-list").append(copyEpisdSample2);

				});

				//과정에 대한 차수 반복
				$(".edu-plan-area").find(".round-list").find(".copyEpisdSample").css("display","");

				schedulePopupFn();

			}else{
				$(".edu-info-div").css("display", "none");
				$(".listNotEmpty").css("display", "none");
				$(".listEmpty").css("display", "");
				$(".total-edu-area").addClass("no-data");



			}

		}, "/education/apply/detailLayer", searchForm, "text");
	}


	//과정분류 조회
	var selectCtgryCdList = function(arg){

		if($(arg).val() == "" || $(arg).val() === undefined){
			arg = $("#ctgryCd").data("ctgrycd");
		}

		var cdMst= {};
		cdMst.cd = $(arg).val();

		cmmCtrl.jsonAjax(function(data){
			callbackAjaxCtgryCdList(data);

			//데이터가 없으면 2뎁스 사용 불가처리 함
			if(arg == "" || arg === undefined){
				$("#ctgryCd").attr("readonly", true).attr("disabled", true);
			}else{
				$("#ctgryCd").attr("readonly", false).attr("disabled", false);
			}

		}, '/education/classTypeList', cdMst, "text");
	}

	//과정분류 2뎁스 세팅
	var callbackAjaxCtgryCdList = function(data){

		var detailList = JSON.parse(data);
		var selectHtml = "<option value=''>전체</option>";

		for(var i =0; i < detailList.length; i++){

			var cd = detailList[i].cd;
			var cdNm = detailList[i].cdNm;

			selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
		}

		$("#ctgryCd option").remove();

		$("#ctgryCd").append(selectHtml);

		var ctgrycd = $("#ctgryCd").data("ctgrycd");

		$("#ctgryCd").val(ctgrycd).prop("selected", true);//조회된 과정분류값 자동선택
	}

	var selectCtgryCdListLayer = function(arg){

		var cdMst= {};
		cdMst.cd = arg;

		cmmCtrl.jsonAjax(function(data){
			callbackAjaxCtgryCdListLayer(data);

			//데이터가 없으면 2뎁스 사용 불가처리 함
			if(arg == "" || arg === undefined){
				$formLayerObj.find("#ctgryCd").attr("readonly", true).attr("disabled", true);
			}else{
				$formLayerObj.find("#ctgryCd").attr("readonly", false).attr("disabled", false);
			}

		}, '/education/classTypeList', cdMst, "text");
	}

	//과정분류 2뎁스 세팅
	var callbackAjaxCtgryCdListLayer = function(data){

		var detailList = JSON.parse(data);

		$formLayerObj.find("#ctgryCd").empty();

		var temp ="";

		var detailLength = detailList.length;

		if(detailLength > 0){


			for(var i =0; i < detailList.length; i++){

				var cd = detailList[i].cd;
				var cdNm = detailList[i].cdNm;


				var addClass =  (i==0)? "swiper-slide active txt-tab-btn searchLayer" : "swiper-slide txt-tab-btn searchLayer";
				temp = temp+"<a class='"+addClass+"' data-ctgrycd="+cd+" href='javascript:'><p class='txt'><span class='menu-name'>"+cdNm+"</span></p></a>";

			}
			$formLayerObj.find("#ctgryCd").append(temp);

			$formLayerObj.find("#ctgryCd").find("a:first").trigger("click");

			txtTabSwiperCreate();

			$(".txt-major-tab-swiper .txt-tab-btn").off().on("click", function(){
				$(this).addClass("active").siblings().removeClass("active");
			});
			$(".txt-tab-swiper.func-tab .txt-tab-btn").off().on("click", function(){
				$(this).addClass("active").siblings().removeClass("active");

				if($(this).parents(".pop-con-area").size() > 0) {
					$(this).parents(".pop-con-area").find(".con-area").scrollTop(0); // 탭 클릭시, 맨 상단 스크롤 이동
					$(this).parents(".pop-con-area").find(".tab-con").hide().eq($(this).index()).show();
				} else {
					$(this).parents(".tab-con-w").find(".tab-con").hide().eq($(this).index()).show();
					// 탭 클릭 시, 맨 처음 탭에 active 가도록 초기화
					if($(this).parents(".tab-con-w").find(".tab-con").eq($(this).index()).find(".txt-depth-tab-swiper").size() > 0 && !$(this).parents(".tab-con-w").find(".tab-con").eq($(this).index()).find(".txt-depth-tab-swiper .swiper-slide:first-child").hasClass("active")) {
						$(this).parents(".tab-con-w").find(".tab-con").eq($(this).index()).find(".txt-depth-tab-swiper .swiper-slide:first-child").addClass("active").siblings().removeClass("active");;

						let depthTabSwiper = tabmenuSwipers[$(this).index()];
						if (depthTabSwiper) {
							depthTabSwiper.slideTo(0, 0);
						}
					}

				}
			});


			$(".edu-info-div").css("display", "");
			$(".listNotEmpty").css("display", "");
			$(".listEmpty").css("display", "none");
			$(".total-edu-area").removeClass("no-data");
		}else{
			$(".edu-info-div").css("display", "none");

			$(".listNotEmpty").css("display", "none");
			$(".listEmpty").css("display", "");
			$(".total-edu-area").addClass("no-data");
		}
		//데이터 있음

		//데이터 없음
	}


	// set model
	ctrl.model = {
		id : {
			btnSearch : {
				event : {
					click : function() {
						cmmCtrl.setFormData($formObj);
						search(1);
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
						//$formObj.clearForm();
						//FORM 전송 필수 데이터 삽입
						$formObj.find("#pageIndex").val( pageIndex );
						$formObj.find("#listRowSize").val( listRowSize );
						$formObj.find(".listRowSizeContainer").val( listRowSize );
						$formObj.find("#pageRowSize").val( pageRowSize );
						$formObj.find("#csrfKey").val( csrfKey );
						$formObj.find("input[name=srchLayer]").val( srchLayer );

						$("#q").val(null);
						$("#strtDt").val(null);
						$("#endDt").val(null);

						//캘린더 초기화
						//cmmCtrl.setPeriod(this, "", "", false);
						$("input[name='srchDateType']:first").prop("checked", true); //기간검색 방식 초기화
						$("#prntCd option:eq(0)").prop("selected", true).trigger("click"); //과정분류 초기화
						//학습방식, 접수상태 초기화
						$("input[type='checkbox']").each(function(){
							$(this).attr("checked", false);
						});


						//검색 로직 실행
						$formObj.find("#btnSearch").click();
					}
				}
			},

			srchOrder : {
				event : {
					change : function(){
						//교육신청 정렬조건 변경
						search(1);
					}
				}
			}

		},
		classname : {

			classType : {
				event : {
					click : function() {
						selectCtgryCdList(this);
					}
				}
			},

			classTypeLayer : {
				event : {
					click : function() {
						selectCtgryCdListLayer($(this).data("prntcd"));
					}
				}
			},
			edctnSearch : {
				event : {
					click : function(e) {
						//전체 팝업일정에서 과정명 클릭할때 실행
						var edctnSeq= $(e.target).closest("a").data("edctnseq");
						searchEpisdLayer(edctnSeq);

					}
				}
			},


			ctgryCdLayer : {
				event : {
					click : function(e) {
						debugger
						var ctgryCd = $(e.target).closest("a").data("ctgrycd");
						searchLayer(ctgryCd);
					}
				}
			},


			//페이징 처리
			pageSet : {
				event : {
					click : function() {
						//페이징 이동

						var pageIndex = $formObj.find("input[name=pageIndex]").val();
						search(++pageIndex);

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

			episdDtl : {
				event : {
					click : function(e){
						var edctnseq = $(this).data("edctnseq");
						location.href="/education/apply/detail?detailsKey="+edctnseq;
					}
				}
			},

			nonMemberDtl : {
				event : {
					click : function(e){
						var edctnseq = $(e.target).closest("div").data("edctnseq");
						location.href="/education/apply/non-member/detail?detailsKey="+edctnseq;
					}
				}
			},

			//회차 담당자문의 팝업
			eduTotCal : {
				event : {
					click : function(e) {

						//openPopup('allTrainingSchedulePopup', e);

					}
				}
			},

			btnBindSearch : {
				event : {
					click : function() {
						$formObj.find("#btnSearch").click();
					}
				}
			},

		},
		immediately : function() {

			cmmCtrl.setCalendar();

			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			search();


			$(".classTypeLayer:first").trigger("click");

			$(document).on('keydown', function(event) {
				// 필터 검색 인지 확인
				if($('.all-srch').css('display') !== 'block') {

					// 눌린 키가 Enter 키인지 확인
					if (event.which === 13) {
						// 다른 이벤트 중지
						event.preventDefault();
						cmmCtrl.setFormData($formObj);
						search(1);
						return false;
					}
				}
			});
		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});