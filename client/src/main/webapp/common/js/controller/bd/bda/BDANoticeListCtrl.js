define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bda/BDANoticeListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // 목록 조회
    var search = function (page){

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        if($("#srchVal").val() != ""){
            localStorage.setItem('noticeSrchVal', $("#srchVal").val());
        }

        cmmCtrl.listFrmAjax(function(respObj) {

            //CALLBACK 처리
            if($formObj.find("#pageIndex").val() == 1) {
                $(".mainPost").css("paddingTop", "24px");
                ctrl.obj.find("#listContainer").html(respObj);

            } else {
                ctrl.obj.find("#listContainer").append(respObj);
                $(".normalPost").css("paddingTop", "24px");
            }

            // 중요공지 수
            var mainPostCnt = parseInt($("a.mainPost").length);
            //총 건수
            var totCnt = parseInt($("#totalCount").val());

            if(totCnt + mainPostCnt <= 10){
                $(".btn-wrap.add-load.align-center").hide();

            }else{
                var tempPage = (page === undefined || page == "") ? 1 : page;
                var rtnPage = 0;

                if((tempPage * 10) > totCnt){
                    $(".btn-wrap.add-load.align-center").hide();
                    rtnPage = totCnt;
                }else{
                    $(".btn-wrap.add-load.align-center").show();
                    rtnPage = (tempPage * 10) - mainPostCnt;
                }
                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }
            $(".article-total-count.f-body2").find("span").text(totCnt);
            $(".item-count").text();

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

        }, "/foundation/board/notice/selectList", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            searchBtn : {
                event : {
                    click : function() {
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            }
        },
        classname : {
            moreBtn : {
                event : {
                    click : function () {
                        var pageIndex = $formObj.find("input[name=pageIndex]").val();
                        search(++pageIndex);
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        $formObj.find("input[name=detailsKey]").val($(this).data("detailsKey"));
                        $formObj.find("input[name=mainPostYn]").val($(this).data("mainPostYn"));
                        location.href = "./view?detailsKey=" + $formObj.find("input[name=detailsKey]").val()
                                        + "&srchVal=" + $formObj.find("input[name=srchVal]").val()
                                        + "&mainPostYn=" + $formObj.find("input[name=mainPostYn]").val();
                    }
                }
            }
        },
        immediately : function() {

            if (localStorage.getItem('noticeSrchVal')) {
                $formObj.find("#srchVal").val(localStorage.getItem('noticeSrchVal'));
            }

            if (performance.navigation.type === 1) {
                $('#srchVal').val(localStorage.getItem("noticeSrchVal"));

            } else {
                localStorage.removeItem("noticeSrchVal");
                $('#srchVal').val("");
            }

            cmmCtrl.setFormData($formObj);
            search();

            $(document).on('keydown', function(event) {
                // 눌린 키가 Enter 키인지 확인
                if (event.which === 13) {
                    // 다른 이벤트 중지
                    if ($('.all-srch').css("display") === "none") {
                        event.preventDefault();
                        cmmCtrl.setFormData($formObj);
                        search(1);
                        return false;
                    }
                }
            });
        }
    };
    ctrl.exec();

    return ctrl;
});