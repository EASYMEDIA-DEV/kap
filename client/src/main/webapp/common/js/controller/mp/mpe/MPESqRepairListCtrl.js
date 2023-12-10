define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPESqRepairListCtrl"
    };
    // get controller object
    var ctrlRepair = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrlRepair.obj.find("form").eq(0);
    //목록 조회
    var searchRepair = function(page){
        if(page != undefined){
            $formObj.find("input[name=pageIndex]").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrlRepair.obj.find("#repairListContainer").append(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            ctrlRepair.obj.find("#repairListContainerTotCnt").text(totCnt);
            //더보기 페이징 처리
            cmmCtrl.morePaging(totCnt, $formObj, "repairListContainer", "moreRepairPagingContainer");
        }, "./repair/list", $formObj, "GET", "html");
    }
    // set model
    ctrlRepair.model = {
        id : {
            // 페이징 처리
            moreRepairPagingContainer : {
                event : {
                    click : function() {
                        var pageIndex = parseInt($formObj.find("input[name=pageIndex]").val()) + 1;
                        searchRepair(pageIndex);
                    }
                }
            },
        },
        classname : {

        },
        immediately : function() {
            //수료 교육 이력
            searchRepair(1);
        }
    };

    ctrlRepair.exec();

    return ctrlRepair;
});