define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdc/BDCFaqListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#listContainerTotCnt").text(); // 게시물 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt / 10); // 더보기 페이지

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //목록 조회
    var search = function(page){
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            if (totCnt == undefined) {
                totCnt = 0;
            }
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

            commonScript.commonMotion();

            if(chilCnt > 10){
                $("#listContainer").children("div.list-item").slice(10,chilCnt).removeClass("open");
                $("#listContainer").children("div.list-item").slice(10,chilCnt).addClass("close");
                $("#listContainer").children("div.list-item").slice(10,chilCnt).hide();

                var openCnt = $("#listContainer").find(".open").length // 보이는 게시물
                $(".cntText").text(openCnt +"/"+ chilCnt);
            }else{
                $(".moreBtn").hide();
            }

        }, "./select", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        location.href = "./list?srchVal=" + $("#srchVal").val();
                    }
                }
            },
            ctgryCd : {
                event : {
                    click : function() {
                        var ctgryCd = $(this).data("ctgryCd");
                        $formObj.find("input[name=detailsKey]").val(ctgryCd);
                        search();
                        $(".btnArea").children().removeClass("active");
                        $(this).addClass("active");
                    }
                }
            }
        },
        classname : {
            moreBtn : {
                event : {
                    click : function () {
                        pageCnt = pageCnt + 1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $("#listContainer").find(".open").length // 보이는 게시물
                        if(pageCnt <= page){
                            $("#listContainer").children("div.list-item").slice(openCnt+1,openCnt+10).show();
                            $("#listContainer").children("div.list-item").slice(openCnt+1,openCnt+10).removeClass("open");
                            $("#listContainer").children("div.list-item").slice(openCnt+1,openCnt+10).addClass("open");
                            $(".cntText").text(openCnt+10 +"/"+ chilCnt);
                        }else{
                            $("#listContainer").find(".close").show();
                            $(".moreBtn").hide();
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        $formObj.find("input[name=detailsKey]").val($(this).data("detailsKey"));
                        var url = "./view?detailsKey=" + $formObj.find("input[name=detailsKey]").val();

                        if($formObj.find("input[name=srchVal]").val() != null) {
                            location.href = url;
                        } else {
                            location.href = url + "&srchVal=" + $formObj.find("input[name=srchVal]").val();
                        }
                    }
                }
            }
        },
        immediately : function() {
            search();
            $(".btnArea").children("a:first").addClass("active");

        }
    };


    ctrl.exec();

    return ctrl;
});

