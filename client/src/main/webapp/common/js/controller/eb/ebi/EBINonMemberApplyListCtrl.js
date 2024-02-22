define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/ebi/EBINonMemberApplyListCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object
	var $formObj = ctrl.obj.find("form").eq(0);


	// 목록 조회
	var search = function (page){
		if(page != undefined){
			$formObj.find("#pageIndex").val(page);
		}

		cmmCtrl.frmAjax(function(rtnData){
			ctrl.obj.find("#listContainer").append(rtnData);
			ctrl.obj.find("#listContainer .info-head .article-total-count span").text($("#btnMore").data("totalCount"));
		}, "./listAjax", $formObj, "POST", "html");

		/*
		cmmCtrl.listFrmAjax(function(respObj) {
			$formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
			//CALLBACK 처리
			ctrl.obj.find("#listContainer").html(respObj);
			//전체 갯수
			var totCnt = $(respObj).eq(0).data("totalCount");
			//총 건수
			ctrl.obj.find("#listContainerTotCnt").text(totCnt);
			//페이징 처리
			cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
		}, "/mngwserc/eb/ebi/select", $formObj, "POST", "html");
		*/
	}


	// set model
	ctrl.model = {
		id : {

			ordData : {
				event : {
					change : function() {
						$formObj.find("#pageIndex").val(1);

						$formObj.submit();
					}
				}
			},

			btnMore : {
				event : {
					click : function() {
						$(this).parent().remove();
						search(parseInt($formObj.find("#pageIndex").val(), 10) + 1);
					}
				}
			}

		},
		classname : {

			//상세보기
			listView : {
				event : {
					click : function() {
						//상세보기
						var detailsKey = $(this).data("detailsKey");
						var ptcptSeq = $(this).data("ptcptSeq");

						var email = $formObj.find("#email").val();
						var hpNo = $formObj.find("#hpNo").val();
						var name = $formObj.find("#name").val();



						var enEdctnSeq = $(this).data("enEdctnSeq");
						var enPtcptSeq = $(this).data("enPtcptSeq");

						$formObj.find("#detailsKey").val(detailsKey);
						$formObj.find("#ptcptSeq").val(ptcptSeq);

						// location.href = "./write?" + $formObj.serialize();
						/*$formObj.attr("action", "ptcptDetail");
						$formObj.submit();*/

						//location.href="./detail?"+$formObj.serialize();
						location.href="./ptcptDetail?enEdctnSeq="+enEdctnSeq+"&enPtcptSeq="+enPtcptSeq+"&email="+email+"&hpNo="+hpNo+"&name="+name;


					}
				}
			},

		},
		immediately : function() {
			//리스트 조회
			//폼 데이터 처리
			cmmCtrl.setFormData($formObj);
			search();

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});