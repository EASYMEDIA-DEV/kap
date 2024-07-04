define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdb/BDBCompanyNewsListCtrl"
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
            localStorage.setItem('comNewsSrchVal', $("#srchVal").val());
        }

        cmmCtrl.listFrmAjax(function(respObj) {

            //CALLBACK 처리
            if($formObj.find("#pageIndex").val() == 1) {
                ctrl.obj.find("#listContainer").html(respObj);
            } else {
                ctrl.obj.find("#listContainer").append(respObj);
            }

            if(!$("#listContainer").hasClass("card-list")) {
                $("#listContainer").addClass("card-list");
            }

            //총 건수
            var totCnt = $("#totalCount").val();

            if(totCnt <= 9 ){
                $(".btn-wrap.add-load.align-center").hide();
                totCnt == 0 && $("#listContainer").removeClass("card-list"); // list가 비어있는 경우 css 깨짐 방지

            }else{
                var tempPage = (page === undefined || page == "") ? 1 : page;
                var rtnPage = 0;

                if((tempPage * 9) >= totCnt){
                    $(".btn-wrap.add-load.align-center").hide();
                    rtnPage = totCnt;
                }else{
                    $(".btn-wrap.add-load.align-center").show();
                    rtnPage = (tempPage * 9);
                }
                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }
            $(".article-total-count.f-body2").find("span").text(totCnt);
            $(".item-count").text();

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

        }, "/foundation/board/company-news/selectList", $formObj, "GET", "html");
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
                    click : function(){
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
                        location.href = "./view?detailsKey=" + $formObj.find("input[name=detailsKey]").val() + "&srchVal=" + $formObj.find("input[name=srchVal]").val();
                    }
                }
            }
        },
        immediately : function() {

            if (localStorage.getItem('comNewsSrchVal')) {
                $formObj.find("#srchVal").val(localStorage.getItem('comNewsSrchVal'));
            }
            cmmCtrl.setFormData($formObj);

            // view 페이지에서 뒤로가기, 새로고침 시 이동 시 검색어 유지 // performance.navigation.type 가 1인 경우 새로고침, 2인 경우 뒤로가기
            if (document.referrer.indexOf('board/company-news/view') > 0 || performance.navigation.type === 1 || performance.navigation.type === 2) {
                $('#srchVal').val(localStorage.getItem("comNewsSrchVal"))

            } else {
                localStorage.removeItem("comNewsSrchVal");
                $('#srchVal').val("");

            }
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

