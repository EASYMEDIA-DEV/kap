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

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            addMore : {
                event : {
                    click : function() {
                        $('#firstIndex').val($('#recordCountPerPage').val());
                        $('#recordCountPerPage').val(parseInt(addCount+10));

                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.trainings-list-w').append(respObj);
                            addCount = 10+addCount;
                            //전체 갯수
                            var totalCnt = $('#totalCnt').text();

                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            moveView : {
                event : {
                    click : function() {
                        var bsnCd = $(this).data("bsnCd");
                        var appctnSeq = $(this).data("appctnSeq");
                        location.href = "./view?bsnCd="+bsnCd+"&appctnSeq="+appctnSeq;
                    }
                }
            }
        },
        immediately : function(){

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

