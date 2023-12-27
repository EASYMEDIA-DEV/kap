define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sv/sva/SVASurveyListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

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
            ctrl.obj.find("#listContainer").html(respObj);

            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/sv/sva/select", $formObj, "POST", "html");
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
            btnLayerRefresh : {
                event : {
                    click : function() {
                        //FORM 데이터 전체 삭제
                        var pageIndex 	= $formObj.find("#pageIndex").val();
                        var listRowSize = $formObj.find("#listRowSize").val();
                        var pageRowSize = $formObj.find("#pageRowSize").val();
                        var csrfKey 	= $formObj.find("#csrfKey").val();
                        var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
                        var typeCdList 	= $formObj.find("input[name=typeCdList]").val();
                        $formObj.clearForm();
                        //FORM 전송 필수 데이터 삽입
                        $formObj.find("#pageIndex").val( pageIndex );
                        $formObj.find("#listRowSize").val( listRowSize );
                        $formObj.find(".listRowSizeContainer").val( listRowSize );
                        $formObj.find("#pageRowSize").val( pageRowSize );
                        $formObj.find("#csrfKey").val( csrfKey );
                        $formObj.find("input[name=srchLayer]").val( srchLayer );
                        $formObj.find("input[name=typeCdList]").val( typeCdList );

                        //캘린더 초기화
                        cmmCtrl.setPeriod(this, "", "", false);

                        //검색 로직 실행
                        $formObj.find("#btnSearch").click();
                    }
                }

            },
            //데이터 삭제
            btnDel : {
                event : {
                    click: function () {
                        var frmDataObj = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delType = frmDataObj.data("delType");
                        if (delActCnt > 0) {
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
                                        alert("매칭된 게시물은 삭제 할 수 없습니다.");
                                    }
                                }, "./delete", frmDataObj, "POST", "json");
                            }
                        }
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

            // 레이어팝업 버튼 클릭시
            srvBtnChoice : {
                event : {
                    click : function() {
                        var clickObj = {};
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if(choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.sv.sva.notSrchServey1"));
                        }else{
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();//설문 조사 번호
                            var typeNm = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find("td").eq(2).text());//설문유형
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find("td").eq(3).text());//제목

                            clickObj.typeNm = typeNm;
                            clickObj.titl = titl;

                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }



                    }
                }
            }
        },
        immediately : function() {
            // 리스트 조회
            cmmCtrl.setFormData($formObj);

            search($formObj.find("input[name=pageIndex]").val());

        }
    };
    ctrl.exec();

    return ctrl;
});

