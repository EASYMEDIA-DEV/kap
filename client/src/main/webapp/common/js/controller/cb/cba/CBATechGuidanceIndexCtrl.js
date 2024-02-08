define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cba/CBATechGuidanceIndexCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#infoCard").children("a").length; // 위원 카드 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt/9); // 더보기 페이지
    // set model
    ctrl.model = {
        id : {
            goContact : {
                event : {
                    click : function(){
                        var loginYn = $("#goContact").data("seq");
                        if(loginYn){
                            location.href="/foundation/cs/qa/index?inqFir=INQ03&inqSec=기술지도"
                        }else{
                            if(confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")){
                                location.href="/foundation/cs/qa/index?inqFir=INQ03\t%26inqSec=기술지도";
                            }
                        }
                    }
                }
            },
            goQa : {
                event : {
                    click : function(){
                        var loginYn = $(this).data("seq");
                        var cmssrCbsnCd = $(".cmssrCbsnCd").text();
                        if(!loginYn){
                            if(confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")){
                                location.href="/login?rtnUrl=/foundation/cs/qa/index?inqSec="+cmssrCbsnCd;
                            }
                        }else{
                            location.href="/foundation/cs/qa/index?inqSec="+cmssrCbsnCd;
                        }
                    }
                }
            }
        },
        classname : {
            //다운로드
            fileDown : {
                event : {
                    click : function(e) {
                        $(".loading-area").stop().fadeIn(200);
                        var url = $(this).data("url");

                        async function downloadFile() {
                            await new Promise(resolve => setTimeout(resolve, 200)); // 200ms 대기
                            location.href = url;
                            $(".loading-area").stop().fadeOut(200);
                        }

                        downloadFile();
                    }
                }
            },

            //검색 레이어에서 선택시 호출
            moreBtn : {
                event : {
                    click : function(){
                        pageCnt = pageCnt+1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $("#infoCard").find(".popOpen").length // 보이는 게시물

                        if(pageCnt == 2) {
                            $("#infoCard").children("a").slice(openCnt,openCnt+10).show();
                            $("#infoCard").children("a").slice(openCnt,openCnt+10).removeClass("close");
                            $("#infoCard").children("a").slice(openCnt,openCnt+10).addClass("popOpen");
                            $(".moreBtn").hide();
                        } else {
                            if(pageCnt <= page){
                                $("#infoCard").children("a").slice(openCnt+1,openCnt+10).show();
                                $("#infoCard").children("a").slice(openCnt+1,openCnt+10).removeClass("close");
                                $("#infoCard").children("a").slice(openCnt+1,openCnt+10).addClass("popOpen");
                                $(".cntText").text(openCnt+9 +"/"+ chilCnt);
                            } else {
                                $("#infoCard").find(".close").show();
                                $(".moreBtn").hide();
                            }
                        }
                    }
                }
            },
            techApplication : {
                event : {
                    click : function(e){
                        if(e){
                            if(confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")){
                                location.href="/login?rtnUrl=/consulting/tech/application";
                            }
                        }
                    }
                }
            },
            popOpen : {
                event : {
                    click : function() {

                        var memSeq = $(this).find(".memSeq").val();
                        var cmssrCbsnCdNm = $(this).find(".cmssrCbsnCdNm").text();
                        var cssInfo = {};
                        cssInfo.detailsKey = memSeq;
                        cmmCtrl.jsonAjax(function (data) {
                            var info = JSON.parse(data);
                            if (info.cmssrMjrCarerExpsYn == 'Y'){
                                $(".cmssrMjrCarerCntn").html(info.cmssrMjrCarerCntn.replaceAll(/(\n|\r\n)/g, "<br>"));
                                if(info.cmssrCnstgFldCntn) {
                                    $(".cmssrCnstgFldCntn").html(info.cmssrCnstgFldCntn.replaceAll(/(\n|\r\n)/g, "<br>"));
                                }
                                else {
                                    $(".cmssrCnstgFldCntn").text("-");
                                }
                            }else{
                                $(".cmssrMjrCarerCntn").text("-");
                                $(".cmssrCnstgFldCntn").text("-");
                            }
                            $(".cmssrName").text(info.name);
                            $(".email").text(info.email);
                            $(".cmssrCbsnCd").text(cmssrCbsnCdNm);
                        }, './selectDtlInfo', cssInfo, "text");
                        openPopup('memberDetailsPopup');
                    }
                }
            },
            contact : {
                event : {
                    click : function(){

                       /* var cmssrCbsnCd = $(".cmssrCbsnCd").text()
                        location.href="/foundation/cs/qa/index?inqSec="+cmssrCbsnCd;*/
                    }
                }
            }
        },
        immediately : function() {
            if(chilCnt > 9){
                $("#infoCard").children("a").slice(9,chilCnt).hide();
                $("#infoCard").children("a").slice(9,chilCnt).removeClass("popOpen");
                $("#infoCard").children("a").slice(9,chilCnt).addClass("close");
                var openCnt = $("#infoCard").find(".popOpen").length // 보이는 게시물
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