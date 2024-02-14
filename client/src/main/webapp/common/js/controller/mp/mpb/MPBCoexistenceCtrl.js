define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBCoexistenceCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 10;
    var search = function(obj)
    {
        cmmCtrl.listFrmAjax(function(respObj) {
            $('.trainings-list-w').empty();
            $('.trainings-list-w').append(respObj);
        }, "./listAjax", $formObj, "POST", "html");
    };

    // set model
    ctrl.model = {
        id : {
            ordFlag : {
                event : {
                    change : function() {
                        search($(this));
                    }
                }
            },
        },
        classname : {
            addMore : {
                event : {
                    click : function() {

                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.trainings-list-w').append(respObj);
                            addCount = 10+addCount;
                            //전체 갯수
                            var totalCnt = $('#totalCnt').text();

                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('#firstIndex').val(addCount);
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            cancel : {
                event : {
                    click : function() {
                        if(confirm("신청을 취소하시겠습니까?")) {
                            cmmCtrl.frmAjax(function(data){
                                if (data.respCnt == 100) {
                                    alert("잘못된 접근입니다. 다시 시도바랍니다.");
                                }
                                location.href = "./list";
                            }, "./cancel", $('#cancelFrm'), "post","json");
                        }
                    }
                }
            },
            searchBtn : {
                event : {
                    click : function() {
                        search($(this));

                        // 필터 검색 후 필터 닫기
                        $(".filter-open-btn").attr("title", "필터 열기");
                        $(".info-head .form-select").removeClass("disabled");

                        $(".filter-popup").removeClass("opened");

                        if(window.innerWidth > 1023){
                            $(".filter-popup").stop(true, true).slideUp(300);
                        }else{
                            gsap.to($(".filter-popup .for-flex .for-center"), 0.6, {top: "100%", ease: Power3, onComplete: function(){
                                    $(".filter-popup").hide();
                                }});
                            $("body").removeClass("stop-scroll");
                            $(".dimd").css("z-index", 50).stop(true, true).fadeOut(300);
                        }
                    }
                }
            },
            filterInit : {
                event : {
                    click : function () {
                        $('input[name=statusChk]').prop("checked",false);
                        $('input[name=q]').val('');
                        $('input[name=strtDt]').val('');
                        $('input[name=endDt]').val('');

                        //검색 로직 실행
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            }
        },
        immediately : function(){
            cmmCtrl.setCalendarInit(6,-6);
            $('#firstIndex').val(addCount);
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

