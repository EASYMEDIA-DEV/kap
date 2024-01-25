define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    var exports = {
        controller : "controller/co/COSearchDtl"
    };

    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = ctrl.obj.find("form").eq(0);
    var q = $.trim($formObj.find("input[name=q]").val());
    var menuType = $formObj.find("input[name=menuType]").val();

    // 목록 조회
    var search = function (page, cnt, url){
        //data로 치환해주어야한다.
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#tabContainer").html(respObj);
            //전체 갯수
            var totCnt = cnt;
            //총 건수
            if(totCnt <= 10 ){
                $(".btn-wrap.add-load.align-center").remove();
            }else{
                var tempPage = (page === undefined || page == "") ? 1 : page;

                var rtnPage = 0;

                if((tempPage * 10)>totCnt){
                    $(".btn-wrap.add-load.align-center").remove();
                }else{
                    rtnPage = (tempPage * 10);
                }

                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }

            $(".article-total-count.f-body2").find("span").text(totCnt);

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "tabContainer", "pagingContainer");
        }, url, $formObj, "GET", "html");

    }

    ctrl.model = {
        id : {

        },
        classname : {
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
                        var pageIndex = $formObj.find("input[name=pageIndex]").val();
                        search(++pageIndex,cnt,url);

                    }
                }
            },
            //상세보기
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
        },
        immediately : function() {

            cmmCtrl.setFormData($formObj);
            if(menuType == "newsletter"){
                var cnt = $("#letterCnt").val();
                var url = "/foundation/board/newsletter/search/newsletter";
            }
            else if(menuType == "education"){
                var cnt = $("#episdCnt").val();
                var url = "/education/apply/select/education";
            }
            search(1,cnt,url);

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

            //통합검색 탭
            if(menuType !=null && menuType != "" && menuType != 'menu') {
                //통합검색 뉴스레터 클릭
                if(menuType == 'newsletter'){
                    console.log("메뉴타입뉴스 : " + menuType);
                    cmmCtrl.listFrmAjax(function(respObj) {
                        $("#tabContainer").html(respObj);
                        //링크연결 여기서
                    }, "/foundation/board/newsletter/search/newsletter", $formObj, "GET", "html", false, false);
                }
                else if(menuType == 'education'){
                    console.log("메뉴타입에듀 : " + menuType);
                    cmmCtrl.listFrmAjax(function(respObj) {
                        $("#tabContainer").html(respObj);
                        //링크연결 여기서
                    }, "/education/apply/select/education", $formObj, "GET", "html", false, false);
                }

            }
        }
    };

    ctrl.exec();

    return ctrl;
});
