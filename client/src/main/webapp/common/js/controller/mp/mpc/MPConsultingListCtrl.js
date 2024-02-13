define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpc/MPConsultingListCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $(".infoCard").length; // 위원 카드 수

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    var search = function(page)
    {
        console.log(page);
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {



            $("#listContainer").html(respObj);

            //전체 갯수
            var totCnt = $("#totalCount").val();

            //총 건수
            console.log(totCnt);

            if(totCnt <= 10 ){
                $(".btn-wrap.add-load.align-center").hide();
            }else{
                $(".btn-wrap.add-load.align-center").show();
                var tempPage = (page === undefined || page == "") ? 1 : page;

                var rtnPage = 0;

                if((tempPage * 10)>totCnt){
                    rtnPage = totCnt
                }else{
                    rtnPage = (tempPage * 10);
                }

                if(rtnPage == totCnt){
                    $(".btn-wrap.add-load.align-center").hide();
                }else{
                    $(".btn-wrap.add-load.align-center").show();
                    $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
                }

            }

            $(".article-total-count.f-body2").find("span").text(totCnt);


            //$(".item-count").text(totCnt);

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);



        }, "./listAjax", $formObj, "POST", "html");
    };

    // set model
    ctrl.model = {
        id : {

            //검색 초기화
            btnRefresh : {
                event : {
                    click : function() {
                        //FORM 데이터 전체 삭제
                        var pageIndex 	= $formObj.find("#pageIndex").val();
                        var listRowSize = $formObj.find("#listRowSize").val();
                        var pageRowSize = $formObj.find("#pageRowSize").val();
                        var csrfKey 	= $formObj.find("#csrfKey").val();
                        var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
                        //$formObj.clearForm();
                        //FORM 전송 필수 데이터 삽입

                        $formObj.find("#pageIndex").val( pageIndex );
                        $formObj.find("#listRowSize").val( listRowSize );
                        $formObj.find(".listRowSizeContainer").val( listRowSize );
                        $formObj.find("#pageRowSize").val( pageRowSize );
                        $formObj.find("#csrfKey").val( csrfKey );
                        $formObj.find("input[name=srchLayer]").val( srchLayer );

                        $("#q").val(null);
                        $("#strtDt").val(null);
                        $("#endDt").val(null);

                        //캘린더 초기화
                        //체크박스 초기화
                        $("input[type='checkbox']").each(function(){
                            $(this).attr("checked", false);
                        });


                        //검색 로직 실행
                        $formObj.find("#btnSearch").click();
                    }
                }
            },
            btnSearch : {
                event : {
                    click : function() {
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },

            ordFlag : {
                event : {
                    change : function(){
                        //정렬조건 변경
                        $formObj.find("ordFlag").val($("#ordFlag").val());
                        search(1);
                    }
                }
            }
        },
        classname : {
            dtlBtn : {
                event : {
                    click : function(){
                        var conSeq = $(this).data("seq");
                        location.href="./detail?detailsKey="+conSeq;
                    }
                }
            },
            survey : {
                event : {
                    click : function(){
                        var conSeq = $(this).data("seq");
                        var bsnYear = $(".bsnYear").val();
                        location.href="./surveyIndex?detailsKey="+conSeq;
                    }
                }
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        var pageIndex = $formObj.find("input[name=pageIndex]").val();
                        search(++pageIndex);
                    }
                }
            }
        },
        immediately : function() {

            cmmCtrl.setCalendarInit(12,0);

            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);
            search();

            $(window).ready(function(){
                var seqLength = $(".cnstgSeq").length;
                var arr = new Array(seqLength);
                var data = {};
                for(var i=0; i<seqLength; i++){
                    arr[i] = $(".cnstgSeq").eq(i).val();
                    data.cnstgSeq =  $(".cnstgSeq").eq(i).val();
                    data.cnstgCd =  $(".cnstgCd").eq(i).val();
                    cmmCtrl.jsonAjax(function(data){
                        var str = data[0].appctnTypeCd;
                        $("."+arr[i]+"appctnCd").text(str.slice(1));
                    }, "./appctnType", data, "json")
                }
            });

            $(document).on('keydown', function(event) {
                // 필터 검색 인지 확인
                if($('.all-srch').css('display') !== 'block') {

                    // 눌린 키가 Enter 키인지 확인
                    if (event.which === 13) {
                        // 다른 이벤트 중지
                        event.preventDefault();
                        cmmCtrl.setFormData($formObj);
                        search(1);
                        return false;
                    }
                }
            });

            // 신청내역 확인에서 신청사항에서 마지막 '/' 삭제
            var appctnType = $("#appctnType").text();
            if(appctnType != '' || appctnType != 'undefined'){
                $("#appctnType").text($("#appctnType").text().slice(0, -1));
            }
        }
    };

    ctrl.exec();

    return ctrl;
});