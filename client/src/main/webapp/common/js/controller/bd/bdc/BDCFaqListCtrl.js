define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdc/BDCFaqListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#infoCard").children("a").length; // 게시물 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt / 9); // 더보기 페이지
    var detailsKey = "";
    var ctgryCd = "";

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //목록 조회
    var search = function(page){
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            if (totCnt == undefined) {
                totCnt = 0;
            }
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            ctrl.obj.find("#ctgryCd").addClass("active");

            var activeTab = $(respObj).eq(0).data("ctgryCdActive");
            commonScript.commonMotion();

            detailsKey = $formObj.find("input[name=detailsKey]").val(ctgryCd);
            if(detailsKey == ctgryCd) {
                $(this).addClass("active");
            }
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "./select", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {

                    }
                }
            },
            ctgryCd : {
                event : {
                    click : function() {
                        ctgryCd = $(this).data("ctgryCd");
                        $formObj.find("input[name=detailsKey]").val(ctgryCd);
                        search();

                        // 디테일키로 갖고 있는 코드값이랑 일치하는 코드 메뉴값에 active 클래스 추가
                    }
                }
            }
        },
        classname : {
            moreBtn : {
                event : {
                    click : function () {

                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {

                    }
                }
            }
        },
        immediately : function() {
            search();

        }
    };


    ctrl.exec();

    return ctrl;
});

