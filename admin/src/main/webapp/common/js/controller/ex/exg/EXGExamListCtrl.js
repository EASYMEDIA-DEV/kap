define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/ex/exg/EXGExamListCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var langCd = "kr";
    var gubun = $("#gubun").val();
    //목록 조회
    var search = function(page){
        //data로 치환해주어야한다.
        //cmmCtrl.setFormData($formObj);
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#ebeExamlistContainer").html(respObj);

            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            ctrl.obj.find("#ebeExamListContainerTotCnt").text(totCnt);

            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "ebeExamlistContainer", "ebeExamPagingContainer");
        }, "/mngwserc/eb/ebe/select", $formObj, "GET", "html");
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
            listRowSize : {
                event : {
                    change : function(){
                        search(1);
                    }
                }
            },
            //데이터 삭제
            btnDeleteExam : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delType = frmDataObj.data("delType");
                        if (delActCnt > 0)
                        {
                            //삭제 전송
                            cmmCtrl.frmAjax(function(respObj){
                                if(respObj != undefined && respObj.respCnt > 0){
                                    alert(msgCtrl.getMsg("fail.ex.deleteCheck"));
                                }else{
                                    if(confirm(msgCtrl.getMsg("confirm.del")))
                                    {
                                        //삭제 전송
                                        cmmCtrl.frmAjax(function(respObj){
                                            if(respObj != undefined && respObj.respCnt > 0){
                                                var msg = msgCtrl.getMsg("success.del.target.none");
                                                if(typeof delType!= "undefined" && typeof msgCtrl.getMsg("success.del.target." + delType) != "undefined"){
                                                    msg = msgCtrl.getMsg("success.del.target." + delType);
                                                }
                                                alert(msg);
                                                $formObj.find("#btnSearch").click();
                                            }
                                            else{
                                                alert(msgCtrl.getMsg("fail.act"));
                                            }
                                        }, "./delete", frmDataObj, "POST", "json");
                                    }
                                }
                            }, "./getExamEdctnEpisdCnt", frmDataObj, "POST", "json");
                        }
                        else
                        {
                            if(typeof delType!= "undefined")
                            {
                                alert(msgCtrl.getMsg("fail.del.target." + frmDataObj.data("delType")));
                            }
                            else
                            {
                                alert(msgCtrl.getMsg("fail.targetBoard"));
                            }

                            return;
                        }
                    }
                }
            },
            odtmYn : {
                event : {
                    change : function(){
                        var trgtObj = jQuery(this).closest("fieldset");

                        if (jQuery(this).is(":checked"))
                        {
                            jQuery(trgtObj).find(".datetimepicker_strtDt, .datetimepicker_endDt").addClass("notRequired").prop("disabled", true);
                            jQuery(".input-group").find("input").prop("disabled", true).val("");
                            jQuery(".input-group").siblings("select").prop("disabled", true).find("option:eq(0)").prop("selected", true);
                        }
                        else
                        {
                            jQuery(trgtObj).find(".datetimepicker_strtDt, .datetimepicker_endDt").removeClass("notRequired").prop("disabled", false);
                            jQuery(".input-group").find("input").prop("disabled", false);
                            jQuery(".input-group").siblings("select").prop("disabled", false);
                        }

                        jQuery(trgtObj).find(".datetimepicker_strtDt").datetimepicker("setOptions", { /* maxDate : false */ });
                        jQuery(trgtObj).find(".datetimepicker_strtDt").datetimepicker("reset").val("");

                        jQuery(trgtObj).find(".datetimepicker_endDt").datetimepicker("setOptions", { minDate : false });
                        jQuery(trgtObj).find(".datetimepicker_endDt").datetimepicker("reset").val("");
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
                        $formObj.clearForm();
                        //FORM 전송 필수 데이터 삽입
                        $formObj.find("#pageIndex").val( pageIndex );
                        $formObj.find("#listRowSize").val( listRowSize );
                        $formObj.find(".listRowSizeContainer").val( listRowSize );
                        $formObj.find("#pageRowSize").val( pageRowSize );
                        $formObj.find("#csrfKey").val( csrfKey );
                        $formObj.find("input[name=srchLayer]").val( srchLayer );

                        //캘린더 초기화
                        cmmCtrl.setPeriod(this, "", "", false);

                        //검색 로직 실행
                        $formObj.find("#btnSearch").click();
                    }
                }
            },
        },
        classname : {
            // 페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
                        }
                    }
                }
            },
            // 상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            },
            // 페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            },
            //검색 레이어에서 선택시 호출
            btnExamSrchLayerChoice:{
                event : {
                    click: function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if( choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.ex.notSrchExam1"));
                        } else if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.ex.notSrchExam"));
                        }else{
                            var clickObj = {};
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());
                            clickObj.titl = titl;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            },

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
        immediately : function() {
            // 리스트 조회
            cmmCtrl.setFormData($formObj);
            //레이어 팝업에서 호출 할 수 있다.
            if($formObj.find("input[name=srchExamLayer]").size() == 0){
                search($formObj.find("input[name=pageIndex]").val());
            }
        }
    };

    ctrl.exec();

    return ctrl;
});