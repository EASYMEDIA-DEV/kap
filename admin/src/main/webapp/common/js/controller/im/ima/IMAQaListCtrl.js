define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/im/ima/IMAQaListCtrl"
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
        }, "/mngwserc/im/ima/select", $formObj, "GET", "html");
    }

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
            inqFir : {
                event : {
                    change : function() {
                        var inqFir = document.getElementById('inqFir');
                        var selectFir = inqFir.options[inqFir.selectedIndex].value;
                        var inqSecOpsions = document.getElementsByClassName("inqSec");
                        var addOption;

                        if(selectFir) {
                            for(var i = 0, max = inqSecOpsions.length; i < max; i++) {
                                addOption = inqSecOpsions.item(i);
                                if(addOption.value.indexOf(selectFir) > -1 && addOption.value.length > 5) {
                                    addOption.style.display = "block";
                                }
                                else {
                                    addOption.style.display = "none";
                                }
                            }
                            $("#inqSec").prop("selectedIndex", 0);
                        }
                        else {
                            for(var i = 0, max = inqSecOpsions.length; i < max; i++) {
                                addOption = inqSecOpsions.item(i);
                                addOption.style.display = "none";
                            }
                            $("#inqSec").prop("selectedIndex", 0);
                        }
                    }
                }
            },
            btnQaPic : {
                event : {
                    click : function(data) {
                        $(".imaQaPicLayer").one('show.bs.modal', function() {
                            var modal = $(this);
                            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정
                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                            $("#QaPicLayerFrm #detailsKey").val("");
                            $("#QaPicLayerFrm #inqFirPic").val("");
                            $("#QaPicLayerFrm #inqSecPic").val("");
                            $("#QaPicLayerFrm #picNm").val("");
                            $("#QaPicLayerFrm #piceMail").val("");
                        }).one('choice', function(data, param) {
                            // Remove class for soft backdrop (if not will affect future modals)
                            fnc(param);
                        }).modal();
                    }
                }
            }
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
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
                        var rsumeCd = $(this).data("rsumeCd");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        $formObj.find("input[name=rsumeCd]").val(rsumeCd);
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
            search();
        }
    };


    ctrl.exec();

    return ctrl;
});

