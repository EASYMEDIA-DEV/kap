define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpd/MPDCmtDtmlMonthKenSrchCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    function init() {
        var Month = new Date().getFullYear()+"-" +  ("0" + (new Date().getMonth() + 1)).slice(-2)
        $(".monthInit").val(Month);
        tableList();
        tabFour();
    }



    function tableList()  {
        $("#tableOne").empty();
        $("#tableTwo").empty();
        $("#tableOne").append('<th class="text-center" rowspan="2">번호</th>');
        $("#tableOne").append('<th class="text-center" rowspan="2">이름</th>');
        cmmCtrl.frmAjax(function(respObj) {
            let arrays = respObj.MPDKenDto.days;
            arrays.forEach((element, index) => {
                $("#tableOne").append('<th class="text-center">'+(index+1)+'</th>');
                $("#tableTwo").append('<th class="text-center">'+element+'</th>');
            });
    }, "/mngwserc/mp/mpd/ken-month-table", $formObj, "POST", "json",'',false);

    }


    var tabFour = function() {
        //근태 사업
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainerMonth").html(respObj);

            // //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            // //총 건수
            ctrl.obj.find("#listContainerMonthTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerMonth", "pagingContainerMonth");
        }, "/mngwserc/mp/mpd/ken-month", $formObj, "POST", "html",'',false);

    }

    // set model
    ctrl.model = {
        id : {
        //엑셀다운로드
        btnExcelDown : {
            event : {
                click: function () {
                    //사유입력 레이어팝업 활성화
                    var frmDataObj    = $formObj.closest("form");
                    frmDataObj.find("input[name='rsn']").remove();
                    location.href = "./excel-ken-down?excelType=M&" + frmDataObj.serialize();

                }
            }
        },


    },
    classname : {
        monthpicker : {
            event : {
                change : function () {
                    tableList();
                    tabFour();
                },
            },
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
        pageSet : {
            event : {
                click : function() {

                        $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                        tableList();
                        tabFour();
                }
            }
        },
        //페이징 목록 갯수

        listRowSizeContainer : {
            event : {
                change : function(){
                    //리스트 갯수 변경
                    $formObj.find("input[name=listRowSize]").val($(this).val());
                    tableList();
                    tabFour();
                }
            }
        }
    },
    immediately : function() {
        init();
    }
    };

    // execute model
    ctrl.exec();

    return ctrl;
    });