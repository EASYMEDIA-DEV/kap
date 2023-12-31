define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKAFutureCarContestListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var langCd = $("#langCd").val();
    var menuSeq = $("#typeCd").val();
    //목록 조회
    // 목록 조회
    var search = function (page){
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
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/wb/wbka/select", $formObj, "POST", "html");

    }

    var callbackAjaxDelete = function(data){

        if (parseInt(data.respCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        }
        else
        {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
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
                event : {
                    click : function() {

                        var checkedValues = [];

                        //참여자 체크
                        $('.checkboxSingle:checked').each(function () {
                            var dataValue = $(this).closest('td').siblings(".deleteCheck").data('value');
                            checkedValues.push(dataValue);
                        });
                        
                        // 삭제 키
                        var detailsKey = $("input[name='delValueList']:checked").map(function () {
                            return $(this).val();
                        }).get();
                        
                        // 참여자 0이상 체크
                        var hasRegCli = $.grep(checkedValues, function (num) {
                            return num > 0;
                        }).length > 0;

                        //참여자 존재 시 미 삭제
                        if (!hasRegCli) {
                            $formObj.find("input[name=detailsKey]").val(detailsKey);
                            if (confirm(msgCtrl.getMsg("confirm.del"))) {
                                cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                            }
                        }else {
                            alert("신청정보가 존재하여 삭제할 수 없습니다.")
                        }
                    }
                }
            },
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            search($formObj.find("input[name=pageIndex]").val($(this).attr("value")));
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
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        location.href = "./write?" + $formObj.serialize();
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
            }
        },
        immediately : function() {
            //리스트 조회
            search(1);

        }
    };


    ctrl.exec();

    return ctrl;
});

