define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdc/BDCFaqListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    const urlParams = new URLSearchParams(window.location.search);
    var faqSeq = urlParams.get("faqSeq");

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // 목록 조회
    var search = function (page){

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        if($("#srchVal").val() != ""){
            localStorage.setItem('faqSrchVal', $("#srchVal").val());
        }

        cmmCtrl.listFrmAjax(function(respObj) {

            //CALLBACK 처리
            if($formObj.find("#pageIndex").val() == 1) {
                ctrl.obj.find("#listContainer").html(respObj);
            } else {
                ctrl.obj.find("#listContainer").append(respObj);
            }

            //총 건수
            var totCnt = $("#totalCount").val();

            if(totCnt <= 10 ){
                $(".btn-wrap.add-load.align-center").hide();
                totCnt == 0 && $("#listContainer").removeClass("card-list"); // list가 비어있는 경우 css 깨짐 방지

            }else{
                var tempPage = (page === undefined || page == "") ? 1 : page;
                var rtnPage = 0;

                if((tempPage * 10) >= totCnt){
                    $(".btn-wrap.add-load.align-center").hide();
                    rtnPage = totCnt;
                }else{
                    $(".btn-wrap.add-load.align-center").show();
                    rtnPage = (tempPage * 10);
                }
                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }
            $(".article-total-count.f-body2").find("span").text(totCnt);
            $(".item-count").text();

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

            commonScript.commonMotion();

        }, "/foundation/board/faq/select", $formObj, "GET", "html");
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
            },
            ctgryCd : {
                event : {
                    click : function() {
                        var ctgryCd = $(this).data("ctgryCd");
                        $("#srchVal").val("");
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

            if (localStorage.getItem('faqSrchVal')) {
                $formObj.find("#srchVal").val(localStorage.getItem('faqSrchVal'));
            }

            if (performance.navigation.type === 1) {
                $('#srchVal').val(localStorage.getItem("faqSrchVal"));

            } else {
                localStorage.removeItem("faqSrchVal");
                $('#srchVal').val("");
            }

            cmmCtrl.setFormData($formObj);
            search();

            // 메인페이지에서 faq 게시물 클릭해서 이동한 경우 acco-hide-area display : block 처리
            setTimeout(function() {
                if (faqSeq != null && faqSeq != "") {
                    var selector = '#listContainer [data-details-key="' + faqSeq + '"]';
                    if(!$(selector).length) {
                        $('.moreBtn').trigger('click');
                    }
                    setTimeout(function () {
                        $(selector).addClass("active");
                        $(selector).find(".acco-hide-area").css('display', 'block');
                    }, 100);
                }
            }, 100);

            if($("#srchVal").val() != ""){
                localStorage.setItem('faqSrchVal', $("#srchVal").val());
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