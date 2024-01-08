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
                            $(".monthInit").val( new Date().getFullYear()+"-" +  ("0" + (new Date().getMonth() + 1)).slice(-2)+"-"+("0" + (new Date().getDate())).slice(-2));
                            $("#monthpicker").val(new Date().getFullYear()+"-" +  ("0" + (new Date().getMonth() + 1)).slice(-2)+"-"+("0" + (new Date().getDate())).slice(-2));
                            dayKenInit($("#monthpicker").val());
                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                        }).one('choice', function(data, param) {
                            // Remove class for soft backdrop (if not will affect future modals)
                            // fnc(param);
                        }).modal();
                    }
                }
            },

            //월 근태
            monthKen : {
                event : {
                    click : function() {

                        var year = new Date().getFullYear().toString() ;
                        $(".mtz-monthpicker.mtz-monthpicker-year").val(year);
                        var input_month = ("0" + (new Date().getMonth() + 1));
                        var month = 0 ;
                        if(input_month < 10) {
                            month = input_month.substring(1,2);
                        } else {
                            month = input_month;
                        }


                        $('.ui-state-default.mtz-monthpicker.mtz-monthpicker-month.ui-state-active').attr('class','ui-state-default mtz-monthpicker mtz-monthpicker-month');

                        if(month <= 3) {
                            $("tbody.mtz-monthpicker").find('tr:first').find('td:eq('+(month-1)+')').addClass('ui-state-active');
                        } else {
                            $('.mtz-monthpicker').find('td:eq('+(month-1)+')').addClass('ui-state-active');
                        }


                        $(".MPDCmtKenMonthSrchLayer").one('show.bs.modal', function() {
                            var Month = new Date().getFullYear()+"-" +  ("0" + (new Date().getMonth() + 1)).slice(-2)
                            $(".monthpicker").val(Month);
                            $("#monthpicker").val(Month);
                            tableList();
                            tabFour();

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
                            alert(msgCtrl.getMsg("fail.mp.mpd.al_025"));
                        } else if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.mp.mpd.al_024"));
                        }else{
                            var clickObj = {};
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".listView").text());
                            var name = $("input[name=delValueList]:checked").parents("tr").children("td").eq("3").text();
                            clickObj.titl = titl;
                            clickObj.name = name;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            },

            //리스트 전체 체크박스 선택시
            checkboxAll : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxSingle가 변경됨
                        var trgtArr = $(this).closest("div").find(".checkboxSingle");
                        if (trgtArr.length > 0)
                        {
                            var isChecked = false;
                            if($(this).is(":checked"))
                            {
                                isChecked = true;
                            }
                            $.each(trgtArr, function(){
                                $(this).prop("checked", isChecked);
                            })
                        }
                    }
                }
            },
            checkboxSingle : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxAll이 변경됨
                        var trgtObj = $(this).closest("div");
                        var allCbxCnt = trgtObj.find(".checkboxSingle").length;
                        var selCbxCnt = trgtObj.find(".checkboxSingle:checked").length;
                        if (allCbxCnt == selCbxCnt)
                        {
                            trgtObj.find(".checkboxAll").prop("checked", true);
                        }
                        else
                        {
                            trgtObj.find(".checkboxAll").prop("checked", false);
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
            $("#listContainerMonth").html(respObj);

            // //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            // //총 건수
            $("#listContainerMonthTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerMonth", "pagingContainerMonth");
        }, "/mngwserc/mp/mpd/ken-month", $formObj, "POST", "html",'',false);

    }

    var dayKenInit = function(kenChk) {
        //근태 사업
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
           $("#listContainerKen").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            $("#listContainerKenTotCnt").text(totCnt);
            if(kenChk) {
                $('.chkdd').remove();
            }
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerKen", "pagingContainerKen");
        }, "/mngwserc/mp/mpd/select-tab-four", $formObj, "POST", "html",'',false);

    }

    // execute model
    ctrl.exec();

    return ctrl;
});