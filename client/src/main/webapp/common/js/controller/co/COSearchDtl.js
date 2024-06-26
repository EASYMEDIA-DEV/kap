define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    var exports = {
        controller : "controller/co/COSearchDtl"
    };

    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = ctrl.obj.find("form").eq(0);
    var q = $.trim($formObj.find("input[name=q]").val());
    var menuType = $formObj.find("input[name=menuType]").val();


    // 메뉴탭 더보기를 누르면 visibleMenuCnt개의 게시물이 추가됨
    var visibleMenuCnt = 9;
    // 메뉴의 총 갯수
    var menuCnt = $formObj.find("input[name=menuCnt]").val();
    // n개부터 m개까지의 게시물이 추가될때
    // n == menuFirstIndex, m == menuAddCnt
    var menuAddCnt = visibleMenuCnt;
    var menuFirstIndex = menuAddCnt;
    // 더보기 버튼 x/y에서 x를 받아옴
    var menuAddPage = $formObj.find("input[name=menuAddPage]").val();



    // 목록 조회
    var search = function (page, cnt, url){
        //data로 치환해주어야한다.
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#tabContainer").append(respObj);
            if(url.includes("/foundation/board/notice")) {
                $(".normalPost").css("paddingTop", "24px");
            }
            //전체 갯수
            var totCnt = cnt;
            //총 건수
            if(totCnt <= 9 ){
                $(".btn-wrap.add-load.align-center").remove();
            }else{
                var tempPage = (page === undefined || page == "") ? 1 : page;

                var rtnPage = 0;

                if((tempPage * 9)>=totCnt){
                    $(".btn-wrap.add-load.align-center").remove();
                }else{
                    rtnPage = (tempPage * 9);
                }

                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }


            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "tabContainer", "pagingContainer");
        }, url, $formObj, "GET", "html");

    }

    var menuSearch = function (menuAddPage, cnt, menuAddCnt, menuFirstIndex){
        //data로 치환해주어야한다.
        if(menuAddPage != undefined){
            $formObj.find("#pageIndex").val(menuAddPage);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            var $menuTabContainer = ctrl.obj.find("#menuAddContainer");
            $menuTabContainer.append(respObj);

            menuFirstIndex = menuFirstIndex +1;
            //전체 갯수
            var totCnt = cnt;
            //총 건수
            if(totCnt <= 1 ){
                $(".btn-wrap.add-load.align-center").remove();
            }else{
                var tempPage = (menuAddPage === undefined || menuAddPage == "") ? 1 : menuAddPage;
                var rtnPage = 0;

                if((tempPage * visibleMenuCnt)>=totCnt){
                    $(".btn-wrap.add-load.align-center").remove();
                }else{

                    rtnPage = (tempPage * visibleMenuCnt);

                }

                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }

            $(".article-total-count.f-body2").find("span").text(totCnt);

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "menuAddContainer", "pagingContainer");
        },"/search/menu/tab?menuAddCnt="+menuAddCnt+"&menuFirstIndex="+menuFirstIndex+"&menuAddPage="+menuAddPage, $formObj, "GET", "html");

    }

    ctrl.model = {
        id : {
        },
        classname : {
            //추천키워드 클릭시 검색
            frontTotalKeyword : {
                event : {
                    click : function() {
                        location.href="/search?q=" + $(this).text();
                    }
                }
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if(menuType == "newsletter"){
                            var cnt = $("#letterCnt").val();
                            var url = "/foundation/board/newsletter/search/newsletter";
                        }
                        else if(menuType == "education"){
                            var cnt = $("#episdCnt").val();
                            var url = "/education/apply/select/education";
                        }
                        else if(menuType == "foundation"){
                            var cnt = $("#newsCnt").val();
                            var url = "/foundation/board/company-news/tab/list";
                        }
                        else if(menuType == "notice"){
                            var cnt = $("#noticeCnt").val();
                            var url = "/foundation/board/notice/tab/list";
                        }
                        var pageIndex = $formObj.find("input[name=pageIndex]").val();
                        search(++pageIndex,cnt,url);

                    }
                }
            },
            //뉴스레터 상세보기
            nwslttrListView: {
                event: {
                    click: function () {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        var url = "/foundation/board/newsletter/view?detailsKey=" + detailsKey;
                        location.href = url;
                    }
                }
            },
            //재단소식 상세보기
            foundationListView: {
                event: {
                    click: function () {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        var url = "/foundation/board/company-news/view?detailsKey=" + detailsKey;
                        location.href = url;
                    }
                }
            },
            //공지 상세보기
            noticeListView: {
                event: {
                    click: function () {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=mainPostYn]").val($(this).data("mainPostYn"));
                        var url = "/foundation/board/notice/view?detailsKey=" + detailsKey + "&mainPostYn=N";
                        location.href = url;
                    }
                }
            },
            //교육 상세보기
            episdDtl : {
                    event: {
                        click: function () {
                            //상세보기
                            var edctnseq = $(this).data("edctnseq");
                            var url = "/education/apply/detail?detailsKey="+edctnseq;
                            location.href = url;
                        }
                    }
                },
            //메뉴 탭 더보기 버튼
            menuAdd : {
                event: {
                    click: function () {
                        ++menuAddPage;
                        menuFirstIndex = menuAddCnt;
                        menuAddCnt = menuAddCnt + visibleMenuCnt;
                        if(menuAddCnt > menuCnt){
                            menuSearch(menuAddPage, menuCnt, menuCnt, menuFirstIndex);
                        }else{
                            menuSearch(menuAddPage, menuCnt, menuAddCnt, menuFirstIndex);
                        }
                    }
                }
            }
        },
        immediately : function() {
            if(menuType !== "menu"){
                cmmCtrl.setFormData($formObj);
                // 뉴스레터 탭 클릭
                if (menuType == "newsletter") {
                    var cnt = $("#letterCnt").val();
                    var url = "/foundation/board/newsletter/search/newsletter";
                }
                // 교육/세미나 탭 클릭
                else if (menuType == "education") {
                    var cnt = $("#episdCnt").val();
                    var url = "/education/apply/select/education";
                }
                // 재단소식 탭 클릭
                else if (menuType == "foundation") {
                    var cnt = $("#newsCnt").val();
                    var url = "/foundation/board/company-news/tab/list";
                }
                //공지사항 탭 클릭
                else if (menuType == "notice") {
                    var cnt = $("#noticeCnt").val();
                    var url = "/foundation/board/notice/tab/list?mainYn=Y";
                }
                search(1,cnt,url);
            }
            else{
                var menuFirstIndex = 0;
                var menuAddPage = 1;
                menuSearch(menuAddPage, menuCnt, visibleMenuCnt, menuFirstIndex);
            }

            $("#headerSrchFrm").find("input[name=q]").val( q );
            // 유효성 검사
            $formObj.validation({
                before: function(){

                },
                after : function() {
                    var isValid = true
                    return isValid;
                },
                customfunc : function(obj, tagid, okval, msg){

                },
                async : {
                    use : true,
                    func : function (){
                        location.href="/search?q=" + $formObj.find("input[name=q]").val();
                    }
                },
                msg : {
                    confirm : {
                        init : ""
                    }
                }
            });

        }
    };

    ctrl.exec();

    return ctrl;
});
