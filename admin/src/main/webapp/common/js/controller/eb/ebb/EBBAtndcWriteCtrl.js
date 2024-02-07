define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

	//교육차수관리  > 전체출석부

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebb/EBBAtndcWriteCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = jQuery("#frmAtndcData");
	var $excelObj = ctrl.obj.parent().find("#atndcModal");

	//온라인강의 복사본 생성
	var onlineHtml = $("#onlineList").find("tr.examTr").eq(0).clone(true);
	var onlineFileHtml = $("#onlineList").find("tr.examTr").eq(1).clone(true);
	//온라인강의 복사본 생성 끝

	// 교육 참여자 목록 호출
	var search = function (page){
		//data로 치환해주어야한다.


		cmmCtrl.listFrmAjax(function(respObj) {
			$formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
			//CALLBACK 처리
			ctrl.obj.find("#ptcptListLayerContainer").html(respObj);


			//전체 갯수
			var totCnt = $("#ptcptListContainer").find("tr").data("totalCount");
			//총 건수
			ctrl.obj.find("#ptcptListLayerContainerTotCnt").text(totCnt);

			$(".ptcptField").validation({});

			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "ptcptListLayerContainer", "ptcptPagingLayerContainer");

			$(".ptcptField").find("ul.pagination").find("li").on("click", function(){
				if(!confirm("다른 페이지로 이동 시 입력한 값은 저장되지 않습니다. 이동하시겠습니까?")){
					return false;
				}
			});

		}, "/mngwserc/eb/ebb/episdAtndcList", $formObj, "GET", "html");




	}


	// set model
	ctrl.model = {
		id : {

			btnExcel : {
				event : {
					click : function() {
						//사유입력 레이어팝업 활성화
						$excelObj.find("#rsn").val('');
						$excelObj.modal("show");
					}
				}
			},
			btnSearch : {
				event : {
					click : function() {
						//검색버튼 클릭시
						cmmCtrl.setFormData($formObj);
						search(1);
					}
				}
			}
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

			btnRemove : {
				event : {
					click : function(){
						$(this).closest("tr").next().remove();
						$(this).closest("tr").remove();
					}
				}
			},

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
		immediately : function(event) {

			$excelObj.find("button.atndcDown").on('click', function(){

				var rsn = $("#atndcModal").find("#rsn").val().trim();
				var frmDataObj    = $formObj.closest("form");

				frmDataObj.find("input[name='rsn']").remove();

				if (rsn != "") {
					frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

					//파라미터를 물고 가야함.
					//location.href = "./excel-down3?" + frmDataObj.serialize();
					$.fileDownload("./excel-down3?" + frmDataObj.serialize() , {
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

				} else {
					alert(msgCtrl.getMsg("fail.reason"));
					return;
				}

			});

			//교육 참여자목록 조회
			search();

			var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

			/* Editor Setting */
			jQuery("textarea[id^='stduyCntn']").each(function(){
				cmmCtrl.setEditor({
					editor : jQuery(this).attr("id"),
					height : 400,
					readOnly : _readOnly
				});
			});

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


						var atndcForm = {};




						//출석용 리스트
						var ptcptAtndcList = new Array();

						$("#ptcptListLayerContainer").find("tr").each(function(){

							$(this).find("[data-targetdt]").each(function(){
								var targetDt =$(this).data("targetdt");


								//같은 일자 안에서만 탐색, 한사람의 하루치 데이터가 리스트<폼>로 구성됨
								$(this).find('[data-edctndt='+targetDt+']').each(function(){

									var deleteStack = 0;

									var orgatndcHour, atndcHour, orglvgrmHour, lvgrmHour, orgEtcNm, etcNm;
									var atndcDayForm = {};

									atndcDayForm.ptcptSeq = $(this).closest("tr").data("ptcptseq");
									atndcDayForm.memSeq = $(this).closest("tr").data("memseq");
									atndcDayForm.edctnDt = targetDt;

									orgatndcHour = $(this).data("orgatndchour");//수정전 출석시간
									atndcHour = $(this).val();//수정후 출석시간

									orglvgrmHour = $(this).parent().next().find("input[name='lvgrmDtm']").data("orglvgrmhour");//수정전 퇴실시간
									lvgrmHour = $(this).parent().next().find("input[name='lvgrmDtm']").val();//수정후 퇴실시간

									orgEtcNm = $(this).parent().next().next().find("input[name='etcNm']").data("orgetcnm") === undefined ? "" : $(this).parent().next().next().find("input[name='etcNm']").data("orgetcnm");//수정전 비고
									etcNm = $(this).parent().next().next().find("input[name='etcNm']").val();//수정후 비고


									if(orgatndcHour != atndcHour){
										atndcDayForm.atndcHour = atndcHour;
									}

									if(orglvgrmHour != lvgrmHour){
										atndcDayForm.lvgrmHour = lvgrmHour;
									}

									if(orgEtcNm != etcNm && etcNm !=""){
										atndcDayForm.etcNm = etcNm;
									}

									//변경점이 없는경우 or 애초에 값이 없는경우
									if(orgatndcHour == atndcHour || atndcHour == ""){
										deleteStack++;
									}

									if(orglvgrmHour == lvgrmHour || lvgrmHour == ""){
										deleteStack++;
									}

									if(orgEtcNm == etcNm || (orgEtcNm == "" && etcNm == "")){
										deleteStack++
									}

									if(deleteStack<3){
										ptcptAtndcList.push(atndcDayForm);
									}



								});

							});

						});
						//여기까지가 모든 사람의 출석 데이터 ptcptAtndcList


						atndcForm.edctnSeq = $("#edctnSeq").val();//교육순번
						atndcForm.episdSeq = $("#episdSeq").val();//회차순번
						atndcForm.episdYear =$("#episdYear").val();//연도
						atndcForm.episdOrd =$("#episdOrd").val();//회차정렬
						atndcForm.ptcptList = ptcptAtndcList;

						//debugger;
						cmmCtrl.jsonAjax(function(data){
							alert("저장되었습니다.");
							location.href = "./list";
						}, "./updateAtndc", atndcForm, "text");

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