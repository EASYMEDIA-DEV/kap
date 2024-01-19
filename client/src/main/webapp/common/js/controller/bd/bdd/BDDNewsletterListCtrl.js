define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/bd/bdd/BDDNewsletterListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

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

    //목록 조회
    var search = function(page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            ctrl.obj.find(".board-list").html(respObj);
            var totCnt = $(respObj).eq(0).data("totalCount");
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
        }, "/foundation/board/newsletter/select", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            consentChk : {
                event : {
                    click : function() {
                        openPopup('newsletterAgreePopup', this)
                    }
                }
            },
            refusalBtn : {
                event : {
                    click : function() {
                        $("input:checkbox[name='consentChk']").prop("checked", false);
                        $("#subscribe").removeClass("subscribeBtn");
                    }
                }
            },
            agreeBtn : {
                event : {
                    click : function() {
                        $("input:checkbox[name='consentChk']").prop("checked", true);
                        $("#subscribe").addClass("subscribeBtn");
                    }
                }
            }
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            },
            //페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
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
                            emailRegex = "^[_a-zA-Z0-9-\.\_]+@[\.a-zA-Z0-9-]+\.[a-zA-Z]+$"
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
            }
        },
        immediately : function() {
            //리스트 조회
            search();

        }
    };


    ctrl.exec();

    return ctrl;
});

