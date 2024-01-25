define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    var exports = {
        controller : "controller/co/COSearchDtl"
    };

    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = ctrl.obj.find("form").eq(0);
    var q = $.trim($formObj.find("input[name=q]").val());
    var endValue = 0;



    // 목록 조회
    var search = function (page){
        //data로 치환해주어야한다.
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#letterTabContainer").html(respObj);
            //전체 갯수
            var totCnt = $("#letterCnt").val();
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

            $(".item-count").text();

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "letterTabContainer", "pagingContainer");
        }, "/foundation/board/newsletter/search/newsletter", $formObj, "GET", "html");

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
                        var pageIndex = $formObj.find("input[name=pageIndex]").val();
                        search(++pageIndex);

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
            search();

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


            var menuType = $formObj.find("input[name=menuType]").val();
            if(menuType !=null && menuType != "" && menuType != 'menu') {
                if(menuType == 'newsletter')
                    cmmCtrl.listFrmAjax(function(respObj) {
                        $("#letterTabContainer").html(respObj);
                        //링크연결 여기서
                    }, "/foundation/board/newsletter/search/newsletter", $formObj, "GET", "html", false, false);
            }

        }
    };

    ctrl.exec();

    return ctrl;
});
