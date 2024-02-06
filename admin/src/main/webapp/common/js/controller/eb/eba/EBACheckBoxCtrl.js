define(["ezCtrl"], function(ezCtrl) {

	"use strict";

	// set controller name
	var exports = {
		controller : "controller/eb/eba/EBACheckBoxCtrl"
	};

	// get controller object
	var ctrl = new ezCtrl.controller(exports.controller);

	// form Object

	//var 선택삭제

	// 목록 조회


	// set model
	ctrl.model = {
		id : {

		},
		classname : {
			classType : {
				event : {
					change : function() {

						$(".cdListContainer").css("display","none");
						$(".cdListContainer").attr("disabled", true);

						var clickBox = $(this).find("input:checkbox").val();
						if($(this).find("input:checkbox").is(":checked")){
							$(".cdListContainer."+clickBox).find("input:checkbox").prop("checked", true);
						}else{
							$(".cdListContainer."+clickBox).find("input:checkbox").prop("checked", false);
						}

						$(".classType input:checked").each(function(){

							var checkVal = $(this).val();

							var cdnm = $(this).data("cdnm");
							$("."+checkVal).find(".cdnm").html(cdnm);
							$("."+checkVal).css("display","block");

							$("."+checkVal).find("input:checkbox").attr("disabled", false);
						});

						if($(".classType input:checked").length == 0){
							$(".cdListContainer").css("display","none");
							$(".cdListContainer").attr("disabled", true);
							$(".cdListContainer").find("input:checkbox").prop("checked", false);
						}


						//상위 분류코드 전부 선택시 ''전체'도 체크해줌
						var prntCdChkBox = $(".checkbox-inline.c-checkbox.classType:not(.checkbox-inline.c-checkbox.classType:first)");
						if(prntCdChkBox.find("input:checkbox:checked").length == prntCdChkBox.find("input:checkbox").length){

							if($(".detailCdList input[name='ctgryCd']").length == $(".detailCdList input[name='ctgryCd']:checked").length){
								$(".checkbox-inline.c-checkbox.classType:first").find("input:checkbox").prop("checked", true);
							}
						}else{
							$(".checkbox-inline.c-checkbox.classType:first").find("input:checkbox").prop("checked", false);
						}

						//하위 체크박스중 하나라도 빠지면 '전체' 체크박스 해제
						if($(".detailCdList input[name='ctgryCd']").length != $(".detailCdList input[name='ctgryCd']:checked").length){
							$(".classType:first").find("input:checkbox").prop("checked",false);
						}else{
							console.log("전부 다 체크");
							$(".classType:first").find("input:checkbox").prop("checked",true);
						}

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
					, change : function() {
						if($(this).closest("label").attr("class") == "checkbox-inline c-checkbox classType"){
							if($(this).is(":checked")){
								$(this).closest("div").find(".classType:not(.classType:first)").trigger("change");
								$(".cdListContainer").find("input:checkbox").prop("checked",true);
							}
						}
					}
				}
			},
			checkboxSingle : {
				event : {
					click : function() {
						//상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxAll이 변경됨
						var trgtObj = $(this).closest("div");

						//classType 클래스가 없다면 일반 체크박스 이벤트 태움
						if(trgtObj.find(".classType").length == 0){
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
		},
		immediately : function() {

			//교육차수관리 과정분류 체크박스 컨트롤
			$(".detailCdList input[name='ctgryCd']").change(function(){

				var closestClass = $(this).closest(".cdListContainer").attr("class");

				var ctryClass = closestClass.substring(closestClass.indexOf("CLASS"), closestClass.length);

				//클래스명을 받아온다
				//해당 클래스명으로 된 영역의 모든 체크박스가 해제되면 부모레벨의 모든 체크박스도 해제시킴
				if($(this).closest(".cdListContainer").find("input:checkbox:checked").length == 0){
					//$("input[name='prntCd']").prop("checked", false);

					$("input:checkbox."+ctryClass).prop("checked", false);

				}else{
					$("input:checkbox."+ctryClass).prop("checked", true);
				}

				//전부 다 해제 했을경우 전체도 해제
				if($(".detailCdList input[name='ctgryCd']:checked").length == 0 || $(".classType:not(.classType:first)").find("input:checked").length != 3){
					$(".classType").find(".checkboxAll").prop("checked", false);
				}

				//중분류 타입별 체크박스 선택된것 없으면 숨김처리
				if($(".cdListContainer.CLASS01").find("input:checked").length == 0){
					$(".cdListContainer.CLASS01").css("display","none");
					$(".cdListContainer.CLASS01").attr("disabled", true);
					$(".cdListContainer.CLASS01").find("input:checkbox").prop("checked", false);
				}
				if($(".cdListContainer.CLASS02").find("input:checked").length == 0){
					$(".cdListContainer.CLASS02").css("display","none");
					$(".cdListContainer.CLASS02").attr("disabled", true);
					$(".cdListContainer.CLASS02").find("input:checkbox").prop("checked", false);
				}
				if($(".cdListContainer.CLASS03").find("input:checked").length == 0){
					$(".cdListContainer.CLASS03").css("display","none");
					$(".cdListContainer.CLASS03").attr("disabled", true);
					$(".cdListContainer.CLASS03").find("input:checkbox").prop("checked", false);
				}

				//하위 체크박스중 하나라도 빠지면 '전체' 체크박스 해제
				if($(".detailCdList input[name='ctgryCd']").length != $(".detailCdList input[name='ctgryCd']:checked").length){
					$(".checkbox-inline.c-checkbox.classType:first").find("input:checkbox").prop("checked", false);
				}else{
					console.log("전부 다 체크");
					$(".checkbox-inline.c-checkbox.classType:first").find("input:checkbox").prop("checked", true);
				}

			});

		}
	};
	
	// execute model
	ctrl.exec();

	return ctrl;
});