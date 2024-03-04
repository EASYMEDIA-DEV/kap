define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/sm/smc/SMCMnPopListCtrl"
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
            btn_delete : {
                event: {
                    click: function () {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        if (delActCnt !== 0) {
                            if(confirm("선택한 게시물을 " + msgCtrl.getMsg("confirm.del")))
                            {
                                //삭제 전송
                                cmmCtrl.frmAjax(function(respObj){
                                    if(respObj != undefined && respObj.respCnt > 0){
                                        var msg = "삭제되었습니다.";

                                        alert(msg);
                                        $formObj.find("#btnSearch").click();
                                    }
                                    else{
                                        alert(msgCtrl.getMsg("fail.act"));
                                    }
                                }, "./delete", frmDataObj, "POST", "json");
                            }
                        } else {
                            alert(msgCtrl.getMsg("fail.sm.smc.target"));
                        }
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
                                popupSeq : key,
                                expsOrd : sort,
                                sortType : sortType
                            }


                            $("#frmSearch").serializeArray().forEach(function(field) {
                                if (field.name != 'popupSeq') {
                                    ajaxData[field.name] = field.value;
                                }
                            });
                            console.log(JSON.stringify(ajaxData, null, 2));

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
            },
            btnExpsYn : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        if (delActCnt != 0) {
                            if(confirm(msgCtrl.getMsg("fail.sm.smc.notuse")))
                            {
                                //삭제 전송
                                cmmCtrl.frmAjax(function(respObj){
                                    if(respObj != undefined && respObj.respCnt > 0){
                                        var msg = "미노출 처리가 완료되었습니다.";

                                        alert(msg);
                                        $formObj.find("#btnSearch").click();
                                    }
                                    else{
                                        alert(msgCtrl.getMsg("fail.act"));
                                    }
                                }, "./use-yn-update", frmDataObj, "POST", "json");
                            }
                        } else {
                            alert(msgCtrl.getMsg("fail.notUse"));
                        }
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
                        $formObj.find("input[name=popupSeq]").val(detailsKey);
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
            }
        },
        immediately : function() {

            var odtmYn = $('#odtmYn').is(":checked");
            if (odtmYn) {
                $(".datetimepicker_strtDt, .datetimepicker_endDt").addClass("notRequired").prop("disabled", true);
                $(".input-group").find("input").prop("disabled", true).val("");
                $(".input-group").siblings("select").prop("disabled", true).find("option:eq(0)").prop("selected", true);
            }

            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);
            search($formObj.find("input[name=pageIndex]").val());
        }
    };

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

            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "./select", $formObj, "POST", "html");
    }


    ctrl.exec();

    return ctrl;
});