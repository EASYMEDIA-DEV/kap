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

    var [navigation] = performance.getEntriesByType('navigation');
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
            ctrl.obj.find("#listContainer").append(respObj);

            // 중요공지 수
            var mainPostCnt = parseInt($("a.mainPost").length);
            // 일반공지 수
            var normalPostCnt = parseInt($("a.normalPost").length);
            //총 건수
            var totCnt = parseInt($("#totalCount").val());

            if(totCnt + mainPostCnt <= 10){
                $(".btn-wrap.add-load.align-center").hide();

            }else{
                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+normalPostCnt+"/"+totCnt+")");
            }
            $(".article-total-count.f-body2").find("span").text(totCnt);
            $(".item-count").text();

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            
            // 이전 페이징 기록
            if(page != undefined) {
                $formObj.find("#pageBeforeIndex").val(page);
            }

            // 뒤로가기 시 해당게시물 포커스
            if (event.persisted || navigation.type == 'back_forward' || document.referrer.indexOf('board/notice/view') > 0) {
                $('.list-item[data-details-key=' + localStorage.getItem('detailsKey') + '][data-main-post-yn=' + localStorage.getItem('mainPostYn') + ']').focus();
            }
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
                        // 뒤로가기 시 페이징, 위치 유지해주기 위해 localStorage사용
                        localStorage.setItem('pageIndex', $formObj.find("#pageIndex").val());
                        localStorage.setItem('detailsKey', $(this).data("detailsKey"));
                        localStorage.setItem('mainPostYn', $(this).data("mainPostYn"));
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

            // 뒤로가기 시 localStorage 저장된 페이지 호출
            if (event.persisted || navigation.type == 'back_forward' || document.referrer.indexOf('board/notice/view') > 0) {
                search(localStorage.getItem('pageIndex'));
            } else {
                search();
            }

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