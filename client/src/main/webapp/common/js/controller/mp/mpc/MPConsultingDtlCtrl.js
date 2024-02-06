define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpc/MPConsultingDtlCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $(".infoCard").length; // 위원 카드 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt/2); // 더보기 페이지
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    // set model
    ctrl.model = {
        id : {
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
            moreBtn : {
                event : {
                    click : function(){
                        pageCnt = pageCnt+1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $(".open").length // 보이는 게시물
                        var closeCnt = $(".close").length; // 숨겨진 게시물
                        if(pageCnt <= page){
                            $(".infoCard").slice(openCnt+1,openCnt+10).show();
                            $(".infoCard").slice(openCnt+1,openCnt+10).removeClass("open");
                            $(".infoCard").slice(openCnt+1,openCnt+10).addClass("open");
                            $(".cntText").text(openCnt+2 +"/"+ chilCnt);
                        }else{
                            $(".close").show();
                            $(".moreBtn").hide();
                        }
                    }
                }
            }
        },
        immediately : function() {
            $(window).ready(function(){
                var seqLength = $(".cnstgSeq").length;
                var arr = new Array(seqLength);
                var data = {};
                for(var i=0; i<seqLength; i++){
                    arr[i] = $(".cnstgSeq").eq(i).val();
                    data.cnstgSeq =  $(".cnstgSeq").eq(i).val();
                    cmmCtrl.jsonAjax(function(data){
                        var str = data[0].appctnTypeCd;
                        $("."+arr[i]+"appctnCd").text(str.slice(1));

                    }, "./appctnType", data, "json")
                }
                // 신청내역 확인에서 신청사항에서 마지막 '/' 삭제
                var appctnType = $("#appctnType").text();
                if(appctnType != '' || appctnType != 'undefined'){
                    $("#appctnType").text($("#appctnType").text().slice(0, -1));
                }
            })
        }
    };

    ctrl.exec();

    return ctrl;
});