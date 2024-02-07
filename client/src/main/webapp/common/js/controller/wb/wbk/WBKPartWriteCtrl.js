define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKPartWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');

    //팀원 전체 폼
    var initPartHtml = '';
    //팀원 추가 폼
    var partHtml = '';

    var partCtnFunction = function() {
        var partLength = $('.Participant').length;

        $('.partFormNm').last().text("팀원 "+partLength);
    }

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
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
            ptcptType :{
                event : {
                    change : function () {
                       if($(".ptcptType").val() == "WBK_PTN01"){
                           $(".partForm ").empty()
                           $(".partForm").append(initPartHtml);
                       }else if($(".ptcptType").val() == "WBK_PTN02"){
                           $(".partForm ").empty()
                       }else {}
                    }
                }
            },
            addBtn : {
                event : {
                    click : function() {
                        var partLength = $('.Participant').length;

                        if (partLength > 1) {
                            alert('팀원은 최대 2명까지 추가 가능합니다.');
                        } else {
                            $('.partNoti').html('');
                            $('.addPart').last().append(partHtml);
                            // 추가시 마다 배열형식에 이름으로 인풋 name값들 셋팅
                            $('.Participant').last().find(".name").attr("name","partList["+ partLength +"].name");
                            $('.Participant').last().find(".hpNo").attr("name","partList["+ partLength +"].hpNo");
                            $('.Participant').last().find(".email").attr("name","partList["+ partLength +"].email");
                            $('.Participant').last().find(".schlNm").attr("name","partList["+ partLength +"].schlNm");
                            $('.Participant').last().find(".grd").attr("name","partList["+ partLength +"].grd");
                            $('.Participant').last().find(".grdCd").attr("name","partList["+ partLength +"].grdCd");
                            partCtnFunction();
                        }
                    }
                }
            },
            deleteBtn : {
                event : {
                    click : function() {
                        $(this).parents(".Participant").remove();

                        $('.Participant').each(function(index) {
                            // 배열형식에 이름으로 인풋 name값들 셋팅
                            $(this).find(".name").attr("name","partList["+ index +"].name");
                            $(this).find(".hpNo").attr("name","partList["+ index +"].hpNo");
                            $(this).find(".email").attr("name","partList["+ index +"].email");
                            $(this).find(".schlNm").attr("name","partList["+ index +"].schlNm");
                            $(this).find(".grd").attr("name","partList["+ index +"].grd");
                            $(this).find(".grdCd").attr("name","partList["+ index +"].grdCd");
                        });

                    }
                }
            },
            partDomainChk : {
                    event : {
                        change : function() {
                            var selectedOpt = $(this).find('option:selected').html();
                            var relatedInput = $(this).closest('.form-select').prev('.form-input').find('input');

                            if(selectedOpt=="직접입력"){
                                relatedInput.val("");
                            }else{
                                relatedInput.val(selectedOpt);
                            }

                    }
                }
            },
            partEmailDomain : {
                    event : {
                        input : function(){
                            var selectBox = $(this).closest('.form-input').next('.form-select').find('.partDomainChk');
                            selectBox.val("default");
                    }
                }
            }
        },
        immediately : function(){
            initPartHtml = $('.initPartHtml').html();
            partHtml = $('.addPart').html();
            $('.delete').remove();
//            $('.initPartHtml').remove();
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

