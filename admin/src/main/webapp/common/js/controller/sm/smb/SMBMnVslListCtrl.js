define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smb/SMBMnVslListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var mdCd = $("#mdCd").val();

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
        }, "/mngwserc/sm/smb/"+mdCd+"/select", $formObj, "POST", "html");
    }

    // set model
    ctrl.model = {
        id : {
            btnSearch : {
                event : {
                    click : function() {
                        // cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
            refresh : {
                event : {
                    click : function() {
                        window.location.reload();
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
                                vslSeq : key,
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
            },
            odtmYn : {
                event : {
                    change : function(){
                        var trgtObj = jQuery(this).closest("fieldset");

                        if (jQuery(this).is(":checked"))
                        {
                            jQuery(trgtObj).find("#dStrDt, #dEndDt").addClass("notRequired").prop("disabled", true);
                            jQuery("#dStrDt, #dEndDt").find("input").prop("disabled", true).val("");
                            jQuery("#dStrDt, #dEndDt").siblings("select").prop("disabled", true).find("option:eq(0)").prop("selected", true);
                        }
                        else
                        {
                            jQuery(trgtObj).find("#dStrDt, #dEndDt").removeClass("notRequired").prop("disabled", false);
                            jQuery("#dStrDt, #dEndDt").prop("disabled", false);
                            jQuery("#dStrDt, #dEndDt").siblings("select").prop("disabled", false);
                        }

                        jQuery(trgtObj).find("#dStrDt").datetimepicker("setOptions", { /* maxDate : false */ });
                        jQuery(trgtObj).find("#dStrDt").datetimepicker("reset").val("");

                        jQuery(trgtObj).find("#dEndDt").datetimepicker("setOptions", { minDate : false });
                        jQuery(trgtObj).find("#dEndDt").datetimepicker("reset").val("");
                    }
                }
            }
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