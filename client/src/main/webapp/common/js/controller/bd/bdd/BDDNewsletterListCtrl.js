define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdd/BDDNewsletterListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
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

    // 목록 조회
    var search = function (page){

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        if($("#srchVal").val() != ""){
            localStorage.setItem('newsLetterSrchVal', $("#srchVal").val());
        }

        cmmCtrl.listFrmAjax(function(respObj) {

            //CALLBACK 처리
            if($formObj.find("#pageIndex").val() == 1) {
                ctrl.obj.find("#listContainer").html(respObj);
            } else {
                ctrl.obj.find("#listContainer").append(respObj);
            }

            if(!$("#listContainer").hasClass("card-list")) {
                $("#listContainer").addClass("card-list");
            }

            //총 건수
            var totCnt = $("#totalCount").val();

            if(totCnt <= 9){
                $(".btn-wrap.add-load.align-center").hide();
                totCnt == 0 && $("#listContainer").removeClass("card-list"); // list가 비어있는 경우 css 깨짐 방지

            }else{
                var tempPage = (page === undefined || page == "") ? 1 : page;
                var rtnPage = 0;

                if((tempPage * 9) >= totCnt){
                    $(".btn-wrap.add-load.align-center").hide();
                    rtnPage = totCnt
                }else{
                    $(".btn-wrap.add-load.align-center").show();
                    rtnPage = (tempPage * 9);
                }
                $(".btn-wrap.add-load.align-center").find(".item-count").text("("+rtnPage+"/"+totCnt+")");
            }
            $(".article-total-count.f-body2").find("span").text(totCnt);
            $(".item-count").text();

            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

        }, "/foundation/board/newsletter/selectList", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
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
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            }
        },
        classname : {
            moreBtn : {
                event : {
                    click : function(){
                        var pageIndex = $formObj.find("input[name=pageIndex]").val();
                        search(++pageIndex);
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
            if (localStorage.getItem('newsLetterSrchVal')) {
                $formObj.find("#srchVal").val(localStorage.getItem('newsLetterSrchVal'));
            }
            // 페이지 새로고침 시 검색어 유지
            if (performance.navigation.type === 1) {
                $('#srchVal').val(localStorage.getItem("newsLetterSrchVal"));

            } else { // 페이지 첫 진입 시 검색어 초기화
                localStorage.removeItem("newsLetterSrchVal");
                $('#srchVal').val("");
            }

            cmmCtrl.setFormData($formObj);
            search();

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

