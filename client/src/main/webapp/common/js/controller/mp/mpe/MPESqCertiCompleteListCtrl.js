define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPESqCertiCompleteListCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    //목록 조회
    var search = function(page){
        if(page != undefined){
            $formObj.find("input[name=pageIndex]").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#completeListContainer").append(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            ctrl.obj.find("#completeListContainerTotCnt").text(totCnt);
            //더보기 페이징 처리
            cmmCtrl.morePaging(totCnt, $formObj, "completeListContainer", "moreCompletePagingContainer");
        }, "./complete/list", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
            // 페이징 처리
            moreCompletePagingContainer : {
                event : {
                    click : function() {
                        var pageIndex = parseInt($formObj.find("input[name=pageIndex]").val()) + 1;
                        search(pageIndex);
                    }
                }
            },
        },
        classname : {

        },
        immediately : function() {
            //수료 교육 이력
            search(1);
        }
    };

    ctrl.exec();

    return ctrl;
});