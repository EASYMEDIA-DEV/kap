define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/consult/CONsultingIndexController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#infoCard").children("a").length; // 위원 카드 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt/9); // 더보기 페이지
    // set model
    ctrl.model = {
        id : {

        },
        classname : {
            //검색 레이어에서 선택시 호출
            open : {
                event : {
                    click : function(){
                       $(".cssMemberDetailsPopup ").show();
                       $("body").addClass("stop-scroll");
                       $(".dimd").css("z-index", `${ dimdIdxNum + $(".layer-popup:visible").length }`).stop(true, true).fadeIn(300);
                       var memSeq = $(this).find(".memSeq").val();
                       var cmssrCbsnCdNm = $(this).find(".cmssrCbsnCdNm").text();
                       var cssInfo = {};
                       cssInfo.detailsKey = memSeq;
                        cmmCtrl.jsonAjax(function (data) {
                            var info = JSON.parse(data);
                            $(".name").text(info.name);
                            $(".email").text(info.email);
                            $(".cmssrCbsnCd").text(cmssrCbsnCdNm);
                            var expsYn = info.cmssrMjrCarerExpsYn;
                            if(expsYn == 'Y'){
                                $(".cmssrMjrCarerCntn").text(info.cmssrMjrCarerCntn);
                            }
                        }, './selectDtlInfo', cssInfo, "text");
                    }
                }
            },
            moreBtn : {
                event : {
                    click : function(){
                        pageCnt = pageCnt+1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $("#infoCard").find(".open").length // 보이는 게시물
                        var closeCnt = $("#infoCard").find(".close").length; // 숨겨진 게시물
                        if(pageCnt <= page){
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).show();
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).removeClass("open");
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).addClass("open");
                            $(".cntText").text(openCnt+9 +"/"+ chilCnt);
                        }else{
                            $("#infoCard").find(".close").show();
                            $(".moreBtn").hide();
                        }
                    }
                }
            },
            techApplication : {
                event : {
                    click : function(){
                       var authCd = $(".techApplication").data("authCd");
                       if(authCd == 'CS'){
                           alert("위원회원은 해당 서비스를 이용 할 수 없습니다.");
                       }else{
                            location.href = '/consulting/tech/application';
                       }
                    }
                }
            }
        },
        immediately : function() {
            if(chilCnt > 9){
                $("#infoCard").children("a").slice(9,chilCnt).hide();
                $("#infoCard").children("a").slice(9,chilCnt).removeClass("open");
                $("#infoCard").children("a").slice(9,chilCnt).addClass("close");
                var openCnt = $("#infoCard").find(".open").length // 보이는 게시물
                var closeCnt = $("#infoCard").find(".close").length; // 숨겨진 게시물
                $(".cntText").text(openCnt +"/"+ chilCnt);
            }else{
                $(".moreBtn").hide();
            }

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});