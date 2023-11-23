define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smg/SMGWinBusinessListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //목록 조회
    var search = function(page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/sm/smg/select", $formObj, "GET", "html");
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
            }
        },
        classname : {
            // 페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        /*if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
                        }*/
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
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
            btnSort : {
                event : {
                    click : function () {

                        var btn = $(this),
                            td = btn.parents("td"),
                            key = td.data("key"),
                            sort = td.data("value"),
                            index = btn.parent().index(),
                            sortType = btn.attr('name') === 'sortUp' ? 'UP' : 'DOWN';

                        if(sortType != null) {

                            //if(sortType == 'UP' && $("pageIndex").val() == '1' && btn.parents('tr').prev().length == 0) {
                            if(sortType == 'UP' && btn.parents('tr').prev().length == 0) {
                                alert(msgCtrl.getMsg("fail.sm.sort.notMoveUp"));
                                return false;
                                //} else if (sortType == 'DOWN' && $("pageIndex").val() == $(".pagination").children().length && btn.parents('tr').next().length == 0) {
                            } else if (sortType == 'DOWN' && btn.parents('tr').next().length == 0) {
                                alert(msgCtrl.getMsg("fail.sm.sort.notMoveDown"));
                                return false;
                            }

                            var ajaxData = {
                                cxstnSeq : key,
                                expsOrd : sort,
                                sortType : sortType
                            }

                            console.log(JSON.stringify(ajaxData, null, 2));

                            $("#frmSearch").serializeArray().forEach(function(field) {
                                console.log(field.name);
                                if (field.name != 'seq') {
                                    ajaxData[field.name] = field.value;
                                }
                            });

                            $.ajax({
                                type: "post",
                                url: "./sort",
                                dataType: "json",
                                data: ajaxData,
                                success: function(r) {
                                    alert('노출 순서가 변경되었습니다.');

                                    cmmCtrl.setFormData($formObj);
                                    search($("pageIndex").val());
                                }
                            })
                        }
                    }
                }
            }
        },
        immediately : function() {
            // 리스트 조회
            // cmmCtrl.setFormData($formObj);
            // search($formObj.find("input[name=pageIndex]").val());
            search();
        }
    };

    ctrl.exec();

    return ctrl;
});