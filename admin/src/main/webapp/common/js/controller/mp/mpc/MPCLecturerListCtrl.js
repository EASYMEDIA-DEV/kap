define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/mp/mpc/MPCLecturerListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

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
            btn_delete : {
                event: {
                    click: function () {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        if(confirm("해당 게시물을 삭제하시겠습니까?"))
                        {
                            if(delActCnt == 0) {
                                alert("삭제할 게시물을 선택해주세요.");
                                return false;
                            } else {
                                //삭제 전송
                                cmmCtrl.frmAjax(function(respObj){
                                    if(respObj != undefined && respObj.respCnt > 0){
                                        var msg = "게시물이 삭제되었습니다.";

                                        alert(msg);
                                        $formObj.find("#btnSearch").click();
                                    }
                                }, "./delete", frmDataObj, "POST", "json");
                            }
                        } else {
                            return false;
                        }
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
            }
        },
        classname : {
            pageSet : {
                event : {
                    click : function() {
                        if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=isttrSeq]").val(detailsKey);
                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            },
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
            btnLecturerLayerChoice:{
                event : {
                    click: function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        /*if( choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.mp.mpc.notSrchLecturer1"));
                        } else */if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.mp.mpc.notSrchLecturer"));
                        }else{
                            var clickObj = {};
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());

                            //여러개 선택한경우
                            var trObj = ctrl.obj.find("input[name=delValueList]:checked").parents("tr");

                            if(trObj.length >1) {
                                var trObjList = new Array();
                                trObj.each(function () {
                                    var tempObj = {};
                                    var name = $(this).find("td").eq(2).text().trim();//이름
                                    var spclCntn = $(this).find("td").eq(2).data("spclcntn");//약력(특이사항)
                                    var seq = $(this).find("td").eq(0).find("input[name=delValueList]:checked").val();
                                    var titl= $(this).find(".srchListView").text().trim();

                                    tempObj.name = name;
                                    tempObj.spclCntn = spclCntn;
                                    tempObj.titl = titl;
                                    tempObj.seq = seq;
                                    tempObj.choiceCnt = choiceCnt;

                                    trObjList.push(tempObj)

                                });
                                clickObj.trObjList = trObjList;

                            }

                            var obj = ctrl.obj.find("input[name=delValueList]:checked").parents("tr");
                            var name = obj.find("td").eq(2).text().trim();//이름
                            var spclCntn = obj.find("td").eq(2).data("spclcntn");//약력(특이사항)

                            clickObj.titl = titl;//소속
                            clickObj.spclCntn = spclCntn;//약력
                            clickObj.name = name;//이름

                            clickObj.choiceCnt = choiceCnt;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            },
            layerLecturerAll : {
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
            }
        },
        immediately : function() {
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);
            //레이어 팝업에서 호출 할 수 있다.
            if($formObj.find("input[name=srchLecturerComLayer]").size() == 0){
                search($formObj.find("input[name=pageIndex]").val());
            }
        }
    }

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
            ctrl.obj.find("#lecturerListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            //총 건수
            ctrl.obj.find("#lecturerListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "lecturerListContainer", "lecturerPagingContainer");
        }, "/mngwserc/mp/mpc/select", $formObj, "GET", "html");
    }

    ctrl.exec();

    return ctrl;
});