define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdd/BDDNewsletterListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var chilCnt = $("#infoCard").children("a").length; // 게시물 수
    var pageCnt = 1; // 페이지 카운트
    var page = (chilCnt / 9); // 더보기 페이지

    var agreeYn = 'N';
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    var newsletterSubmit = function(){
        // 뉴스레터 수신 동의 체크
        if($("#consentChk").is(":checked") == false){
            alert(msgCtrl.getMsg("fail.newsletter.agree"));
            $("#consentChk").focus();
            return false;
        }
        // 뉴스 레터 등록
        if($("#consentChk").is(":checked") == true ){
            if(confirm(msgCtrl.getMsg("confirm.newsletterBtn"))){
                var newsletterForm = {};
                newsletterForm.email = $("input[name=email]").val();

                cmmCtrl.jsonAjax(function(respObj) {
                    if(JSON.parse(respObj).respCnt == "1") {
                        alert(msgCtrl.getMsg("success.newsletterSuc"));
                        $("input[name=email]").val('');
                        $("#consentChk").prop("checked", false);
                    }
                }, "/foundation/board/newsletter/insert",  newsletterForm, "text");
            }
        }
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            consentChk : {
                event : {
                    click : function() {
                        var chkYn = $("input:checkbox[name='consentChk']").prop('checked');
                        if(agreeYn == 'Y') {
                            $("input:checkbox[name='consentChk']").prop("checked", true);
                        } else {
                            $("input:checkbox[name='consentChk']").prop("checked", false);
                        }
                        openPopup('newsletterAgreePopup', this);
                    }
                }
            },
            refusalBtn : {
                event : {
                    click : function() {
                        $("input:checkbox[name='consentChk']").prop("checked", false);
                        agreeYn = 'N';
                    }
                }
            },
            agreeBtn : {
                event : {
                    click : function() {
                        $("input:checkbox[name='consentChk']").prop("checked", true);
                        agreeYn = 'Y';
                    }
                }
            },
            searchBtn : {
                event : {
                    click : function() {
                        location.href = "./list?srchVal=" + $("#srchVal").val();
                    }
                }
            }
        },
        classname : {
            moreBtn : {
                event : {
                    click : function(){
                        pageCnt = pageCnt + 1; // 더보기 누를 때마다 1씩 증가
                        var openCnt = $("#infoCard").find(".open").length // 보이는 게시물
                        if(pageCnt < page){
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).show();
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).removeClass("open");
                            $("#infoCard").children("a").slice(openCnt+1,openCnt+10).addClass("open");
                            openCnt = openCnt + 9;
                            $(".cntText").text("(" + openCnt +"/"+ chilCnt + ")");
                        }else{
                            $("#infoCard").find(".close").show();
                            $(".moreBtn").hide();
                        }
                    }
                }
            },
            subscribeBtn : {
                event : {
                    click : function() {

                        var emailChkYn = 'N';
                        if($("input[name=email]").val() == ""){
                            alert(msgCtrl.getMsg("fail.newsletter.notEmail"));
                            $("input[name=email]").focus();
                            return false;
                        }else{
                            var emailRegex, patten, emailChk;
                            emailRegex = "^[_a-zA-Z0-9-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]+$"
                            patten = eval("/" + emailRegex + "/g");
                            emailChk = $("input[name=email]").val();
                            if(!patten.test(emailChk)) {
                                alert(msgCtrl.getMsg("fail.newsletter.emailCheck"));
                                $("input[name=email]").focus();
                                return false;
                            }else{
                                var newsletterForm = {};
                                newsletterForm.email = $("input[name=email]").val();

                                cmmCtrl.jsonAjax(function(respObj) {
                                    if(JSON.parse(respObj).respCnt == "1") {
                                        alert(msgCtrl.getMsg("fail.newsletter.registeredEmail"));
                                        $("input[name=email]").focus();
                                        emailChkYn = "N";
                                        return false;
                                    }else{
                                        newsletterSubmit();
                                    }
                                }, "/foundation/board/newsletter/dup-email", newsletterForm, "text", false, true);
                            }
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        $formObj.find("input[name=detailsKey]").val($(this).data("detailsKey"));
                        location.href = "./view?detailsKey=" + $formObj.find("input[name=detailsKey]").val() + "&srchVal=" + $formObj.find("input[name=srchVal]").val();
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
                $(".cntText").text("(" + openCnt + "/" + chilCnt + ")");
            }else{
                $(".moreBtn").hide();
            }
        }
    };


    ctrl.exec();

    return ctrl;
});

