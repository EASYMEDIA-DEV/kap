define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/EBCVisitEduController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("frmData");
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var textCntCheck = function (id) {
        var content = $(this).val();
        if(content.length == 0 || content == '') {
            id.text('0자')
        } else {
            id.text(content.length + '자')
        }

        if (content.length > 500) {
            alert('500자까지 입력 가능합니다.');
            $(this).val($(this).val().substring(0, 500));
            id.text('500자');
        }
    }

    var changeAppctnFldCd = function () {

        var actForm = {};
        var appctnFldCd = $("#appctnFldCd").val();
        actForm.appctnFldCd = appctnFldCd;
        cmmCtrl.jsonAjax(function(data){
            var appctnFldCdList = data.cdDtlList
            alert(appctnFldCdList)
            $(".checkBoxArea").html(appctnFldCdList);
        }, './changeAppctnFldCd', actForm, "text")
    }



    // set model
    ctrl.model = {
        id : {
            appctnRsn : {
                event: {
                    keyup : function() {
                        var content = $(this).val();
                        if(content.length == 0 || content == '') {
                            $('#appctnRsnTextCnt').text('0자')
                        } else {
                            $('#appctnRsnTextCnt').text(content.length + '자')
                        }

                        if (content.length > 500) {
                            alert('500자까지 입력 가능합니다.');
                            $(this).val($(this).val().substring(0, 500));
                            $('#appctnRsnTextCnt').text('500자');
                        }
                    }
                }
            },
            appctnThemeCntn : {
                event: {
                    keyup : function() {
                        var content = $(this).val();
                        if(content.length == 0 || content == '') {
                            $('#appctnThemeCntnTextCnt').text('0자')
                        } else {
                            $('#appctnThemeCntnTextCnt').text(content.length + '자')
                        }

                        if (content.length > 500) {
                            alert('500자까지 입력 가능합니다.');
                            $(this).val($(this).val().substring(0, 500));
                            $('#appctnThemeCntnTextCnt').text('500자');
                        }
                    }
                }
            },
            ptcptTrgtCntn : {
                event: {
                    keyup : function() {
                        var content = $(this).val();
                        if(content.length == 0 || content == '') {
                            $('#ptcptTrgtCntnTextCnt').text('0자')
                        } else {
                            $('#ptcptTrgtCntnTextCnt').text(content.length + '자')
                        }

                        if (content.length > 500) {
                            alert('500자까지 입력 가능합니다.');
                            $(this).val($(this).val().substring(0, 500));
                            $('#ptcptTrgtCntnTextCnt').text('500자');
                        }
                    }
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"placeZipcode","placeBscAddr","placeDtlAddr");
                    }
                }
            },
            appctnFldCd : {
                event : {
                    change : function () {
                        var selectedAppctnFldCd = $('#appctnFldCd option:selected').val();
                        $formObj.find("input[name=appctnFldCd]").val(selectedAppctnFldCd);
                        changeAppctnFldCd();
                    }
                }
            }
        },
        classname : {

        },
        immediately : function() {

        }
    };

    ctrl.exec();

    return ctrl;
});