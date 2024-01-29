define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbh/WBHAWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var equidHtml = '';
    var equidChagneFunction = function() {
        var equimentLength = $('.equiment').length;
        $('.equimentCnt').text(equimentLength);

        var tchlgTotalCnt = 0;
        $('.equiment').each(function() {
            tchlgTotalCnt = parseInt($(this).find('.tchlgCnt').val()) + tchlgTotalCnt;
        });

        $('.totalCnt').text(tchlgTotalCnt);
    }

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            addBtn : {
                event : {
                    click : function() {
                        var equipLength = $('.equiment').length;

                        if (equipLength > 100) {
                            alert('대상장비는 100개까지만 추가 가능합니다.');
                        } else {
                            $('.addEquiment').last().append(equidHtml);
                            $('.equiment').last().find(".tchlgNm").attr("name","euipmentList["+ equipLength +"].tchlgNm");
                            $('.equiment').last().find(".tchlgCnt").attr("name","euipmentList["+ equipLength +"].tchlgCnt");
                            equidChagneFunction();

                            $('.equiment').each(function(index) {
                                if(index > 0 ){
                                    $('.addEquiment').find(".addBtn").eq(index).remove();
                                }
                            });
                        }
                    }
                }
            },
            deleteBtn : {
                event : {
                    click : function() {
                        $(this).parents(".equiment").remove();

                        $('.equiment').each(function(index) {
                            $(this).find(".tchlgNm").attr("name","euipmentList["+ index +"].tchlgNm");
                            $(this).find(".tchlgCnt").attr("name","euipmentList["+ index +"].tchlgCnt");
                        });

                        equidChagneFunction();
                    }
                }
            },
            tchlgCnt : {
                event : {
                    change : function() {
                        var cnt = $(this).val();

                        if (cnt > 100) {
                            alert('최대 수량은 100개까지 입력가능합니다.');
                            $(this).val(100);
                            $(this).parents('.amount-div').find('.plus').addClass("disabled");
                            $(this).parents('.amount-div').find('.minus').removeClass("disabled");
                        } else if (cnt <= 0) {
                            alert('최소 수량은 1개까지 입력가능합니다.');
                            $(this).val(1);
                            $(this).parents('.amount-div').find('.plus').removeClass("disabled");
                            $(this).parents('.amount-div').find('.minus').addClass("disabled");
                        } else {
                            $(this).parents('.amount-div').find('.plus').removeClass("disabled");
                            $(this).parents('.amount-div').find('.minus').removeClass("disabled");
                        }
                        equidChagneFunction();
                    }
                }
            },
            plus : {
                event : {
                    click : function () {
                        var valueCnt = $(this).parents('.amount-div').find('.tchlgCnt').val();
                        var tchlgCnt = $(this).parents('.amount-div').find('.tchlgCnt');

                        console.log(valueCnt);

                        if (valueCnt == 99) {
                            $(this).parents('.amount-div').find('.plus').addClass("disabled");
                            valueCnt = parseInt(valueCnt) +1;
                            tchlgCnt.val(valueCnt);
                        } else if (valueCnt > 99) {
                            $(this).parents('.amount-div').find('.plus').addClass("disabled");
                        } else {
                            if (valueCnt == 1) {
                                $(this).parents('.amount-div').find('.minus').removeClass("disabled");
                            }
                            valueCnt = parseInt(valueCnt) +1;
                            tchlgCnt.val(valueCnt);
                        }

                        equidChagneFunction();
                    }
                }
            },
            minus : {
                event : {
                    click : function() {
                        var valueCnt = $(this).parents('.amount-div').find('.tchlgCnt').val();
                        var tchlgCnt = $(this).parents('.amount-div').find('.tchlgCnt');

                        if (valueCnt == 1) {
                            $(this).parents('.amount-div').find('.minus').addClass("disabled");
                        } else if (valueCnt == 2) {
                            $(this).parents('.amount-div').find('.minus').addClass("disabled");
                            valueCnt = parseInt(valueCnt) -1;
                            tchlgCnt.val(valueCnt);
                        } else {
                            if (valueCnt == 100) {
                                $(this).parents('.amount-div').find('.plus').removeClass("disabled");
                            }
                            valueCnt = parseInt(valueCnt) -1;
                            tchlgCnt.val(valueCnt);
                        }

                        equidChagneFunction();
                    }
                }
            }
        },
        immediately : function(){
            equidHtml = $('.addEquiment').html();
            $('.delete').remove();
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

