define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/eb/ebh/EBHEduApplicantWriteCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = ctrl.obj.find("form").first();
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"zipcode","bscAddr","dtlAddr");
                    }
                }
            },
            deptDtlNm : {
                event : {
                    input : function() {
                        var regex = /^[0-9a-zA-Z가-힣]*$/;
                        var trg = $(this).val();

                        if(!trg.match(regex)) {
                            $(this).val(trg.substring(0, trg.length-1));
                        }
                    }
                }
            },
            /*sttsCd : {
                event : {
                    change : function() {
                        var sttsCd = document.getElementById('sttsCd');
                        var selectSttsCd = sttsCd.options[sttsCd.selectedIndex].value;

                        if(selectSttsCd == "EDU_STTS_CD04") {
                            $("#stts").val("N");
                        }
                        else {
                            $("#stts").val("Y");
                        }
                    }
                }
            },*/
            pstnCd : {
                event : {
                    change : function() {
                        var pstnCd = document.getElementById('pstnCd');
                        var selectPstn = pstnCd.options[pstnCd.selectedIndex].value;

                        $("#pstnNm").val("");

                        if(selectPstn == "MEM_CD01007") {
                            $("#pstnNm").css('display', 'inline-block');
                        }
                        else {
                            $("#pstnNm").css('display', 'none');
                        }
                    }
                }
            },
            partsCtgryCd : {
                event : {
                    change : function() {
                        var partsCtgryCd = document.getElementById('partsCtgryCd');
                        var selectPartsCtgry = partsCtgryCd.options[partsCtgryCd.selectedIndex].value;

                        $("select[name='qlty5StarCd']").prop("selectedIndex", 0);
                        $("select[name='qlty5StarYear']").prop("selectedIndex", 0);
                        $("select[name='pay5StarCd']").prop("selectedIndex", 0);
                        $("select[name='pay5StarYear']").prop("selectedIndex", 0);
                        $("select[name='tchlg5StarCd']").prop("selectedIndex", 0);
                        $("select[name='tchlg5StarYear']").prop("selectedIndex", 0);

                        for(var i = 0; i < 3; i++) {
                            $('input[name="sqInfoList' + i + '"]').val('');
                            $('select[name="sqInfoList' + i + '"]').prop("selectedIndex", 0);
                        }

                        if(selectPartsCtgry == "COMPANY01001") {
                            $(".starInfo").css('display', 'block');
                            $(".sqInfo").css('display', 'none');
                        }
                        else if(selectPartsCtgry == "COMPANY01002") {
                            $(".starInfo").css('display', 'none');
                            $(".sqInfo").css('display', 'block');
                        }
                        else {
                            $(".starInfo").css('display', 'none');
                            $(".sqInfo").css('display', 'none');
                        }
                    }
                }
            },
            telNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen >= 3 && phoneLen <= 6) {
                            phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                        } else if (phoneLen > 6) {
                            if (phoneLen == 9) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
            cmpnTelNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen >= 3 && phoneLen <= 6) {
                            phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                        } else if (phoneLen > 6) {
                            if (phoneLen == 9) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            }
        },
        classname : {
            listBtn : {
                event : {
                    click : function() {
                        if(confirm(msgCtrl.getMsg("confirm.list"))){
                            location.href="./list?" + $(this).data("strPam");
                        }
                    }
                }
            },
            sqScore : {
                event : {
                    input : function () {
                        var regex = /^[0-9]*\.?[0-9]*$/;
                        var trg = $(this).val();

                        if(!trg.match(regex)) {
                            $(this).val(trg.substring(0, trg.length-1));
                        }
                    }
                }
            }
        },
        immediately : function(){
            // 유효성 검사
            $formObj.validation({
                before: function(){

                },
                after : function() {
                    var isValid = true
                    return isValid;
                },
                customfunc : function(obj, tagid, okval, msg){

                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./update";

                        cmmCtrl.frmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(msgCtrl.getMsg("success.sve"));
                                location.replace("./list");
                            }
                            actionUrl = "./list";
                        }, actionUrl, $formObj, "post", "json")
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

