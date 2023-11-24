define(["ezCtrl"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpd/MPDCmtListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");


    // 목록 조회
    var search = function (page){

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
    }, "/mngwserc/mp/mpd/select", $formObj, "POST", "html");

    }



    // set model
    ctrl.model = {
        id : {
            btnSearch : {
                event : {
                click : function() {
                //검색버튼 클릭시
                    cmmCtrl.setFormData($formObj);
                    search(1);
                }
            }
        },

        //일일 근태
        dayKen : {
            event : {
                click : function() {
                    $(".MPDCmtKenDaySrchLayer").one('show.bs.modal', function() {

                    }).one('hidden.bs.modal', function() {
                        // Remove class for soft backdrop (if not will affect future modals)
                    }).one('choice', function(data, param) {
                        // Remove class for soft backdrop (if not will affect future modals)
                        // fnc(param);
                    }).modal();
                }
            }
        },

        //엑셀다운로드
        btnExcelDown : {
            event : {
                click: function () {

                    //사유입력 레이어팝업 활성화
                    $excelObj.find("#rsn").val('');
                    $excelObj.modal("show");
                }
            }
        },
        btnInsert : {
                event : {
                    click: function () {
                        $formObj.find("input[name=_csrf]").remove();
                        location.href = "./insert";
                    }
                }
        },

        btnUserLayerChoice:{
                event : {
                    click: function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if( choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_013"));
                        } else if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.mp.mpe.al_014"));
                        }else{
                            var clickObj = {};
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());
                            clickObj.titl = titl;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            },

    },
    classname : {
        //검색 레이어에서 선택시 호출
        btnCmtLayerChoice:{
            event : {
                click: function(){
                    var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                    if( choiceCnt > 1){
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_024"));
                    } else if(choiceCnt == 0){
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_025"));
                    }else{
                        var clickObj = {};
                        clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                        var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());
                        clickObj.titl = titl;
                        ctrl.obj.trigger("choice", [clickObj])
                        ctrl.obj.find(".close").click();
                    }
                }
            }
        },
        classType : {
            event : {
                click : function() {

                    $(".cdListContainer").css("display","none");
                    $(".cdListContainer").attr("disabled", true);
                    $(".cdListContainer").find("input:checkbox").prop("checked", false);

                    $(".classType input:checked").each(function(){
                    console.log($(this).val());

                    var checkVal = $(this).val();

                    console.log(checkVal);

                    $("."+checkVal).css("display","block");

                    $("."+checkVal).find("input:checkbox").attr("disabled", false);


                    });

                    if($(".classType input:checked").length == 0){
                    $(".cdListContainer").css("display","none");
                    $(".cdListContainer").attr("disabled", true);
                    $(".cdListContainer").find("input:checkbox").prop("checked", false);
                    }

                }
            }
    },


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
                $formObj.find("input[name=detailsKey]").val(detailsKey);
                location.href = "./dtl-write?" + $formObj.serialize();
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
        //폼 데이터 처리
        cmmCtrl.setFormData($formObj);
        search();

        $excelObj.find("button.down").on('click', function(){
            var rsn = $excelObj.find("#rsn").val().trim();
            var frmDataObj    = $formObj.closest("form");

            frmDataObj.find("input[name='rsn']").remove();

            if (rsn != "") {
            frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

            //파라미터를 물고 가야함.
            location.href = "./excel-down?" + frmDataObj.serialize();

            } else {
            alert(msgCtrl.getMsg("fail.reason"));
            return;
            }

        });
    }
    };

    // execute model
    ctrl.exec();

    return ctrl;
    });