define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpd/MPDCmtWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");


    let dupIdChk = false;
    let dupEmailChk = false;
    let cmssrCd ;

    //업종/분야 코드 호출
    function commonCodeAjax() {
        cmmCtrl.frmAjax(function(respObj) {
            cmssrCd = respObj.cdDtlList;
        }, "/mngwserc/mp/mpd/cmssrCbsnCd", $formObj, "POST", "json",'',false);
    }
    // set model
    ctrl.model = {
        id : {
            btnSearch : {
                event : {
                click : function() {
                //검색버튼 클릭시
                    cmmCtrl.setFormData($formObj);
                    search(1);
                }
            }
        },
        email : {
            event : {
                input : function() {
                    dupEmailChk = false;
                }
            }
        },
        id : {
                event : {
                    input : function() {
                        dupIdChk = false;
                    }
                }
        },

        //이메일 중복 체크
        dupEmail : {
            event : {
                click : function() {
                    if($("#email").val().trim().length > 0) {
                        if(cmmCtrl.getEmailChk($("#email").val())) {
                            cmmCtrl.frmAjax(function(respObj) {
                                if(respObj.dupChk == 'Y') {
                                    dupEmailChk = true;
                                    alert(msgCtrl.getMsg("fail.mp.mpa.al_008"));
                                } else {
                                    dupEmailChk = false;
                                    alert(msgCtrl.getMsg("fail.mp.mpa.al_007"));
                                }
                            }, "/mngwserc/mp/mpa/dup-email", $formObj, "POST", "json",'',false);
                        } else {
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_017"));
                            return false;
                        }
                    } else {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_031"));
                        return false;
                    }
              }
            }
        },
        //아이디 중복 체크
        dupId : {
                event : {
                    click : function() {
                        if($("#id").val().trim().length > 0) {
                            cmmCtrl.frmAjax(function(respObj) {
                                if(respObj.dupChk == 'Y') {
                                    dupIdChk = true;
                                    alert(msgCtrl.getMsg("fail.mp.mpd.al_009"));
                                } else {
                                    dupIdChk = false;
                                    alert(msgCtrl.getMsg("fail.mp.mpd.al_008"));
                                }
                            }, "/mngwserc/mp/mpa/dup-id", $formObj, "POST", "json",'',false);

                        } else {
                            alert(msgCtrl.getMsg("fail.mp.mpd.al_032"));
                        }
                                      }
                }
        },
        hpNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, '');
                        const phoneLen = phoneNumber.length;

                        if (phoneLen > 3 && phoneLen <= 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                        } else if (phoneLen > 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
                        }
                        event.target.value = phoneNumber;
                    }
                }
        },
        //업종/분야 select 박스
        cmssrTypeCd : {
                event : {
                    change : function() {
                        $(".cmssrCdDiv").show();
                        $("#cmssrCbsnCd").empty();
                        $("#cmssrCbsnCd").append("<option value=''>선택</option>");
                        cmssrCd.MEM_CD.forEach(item => {
                            if (item.cd.includes($(this).val()) && item.dpth == 4 && $(this).val() != '') {
                                $("#cmssrCbsnCd").append("<option value="+item.cd+">"+item.cdNm+"</option>");
                            } else if($(this).val() == 'MEM_CD03004'){
                                $(".cmssrCdDiv").hide();
                            }
                        });
                    }
                }
            },


    },
    classname : {

        classType : {
            event : {
                click : function() {

                    $(".cdListContainer").css("display","none");
                    $(".cdListContainer").attr("disabled", true);
                    $(".cdListContainer").find("input:checkbox").prop("checked", false);

                    $(".classType input:checked").each(function(){
                    console.log($(this).val());

                    var checkVal = $(this).val();

                    console.log(checkVal);

                    $("."+checkVal).css("display","block");

                    $("."+checkVal).find("input:checkbox").attr("disabled", false);


                    });

                    if($(".classType input:checked").length == 0){
                    $(".cdListContainer").css("display","none");
                    $(".cdListContainer").attr("disabled", true);
                    $(".cdListContainer").find("input:checkbox").prop("checked", false);
                    }

                }
            }
    },


    listRowSizeContainer : {
        event : {
            change : function(){
                //리스트 갯수 변경
                $formObj.find("input[name=listRowSize]").val($(this).val());
                search(1);
            }
        }
    }
    },
    immediately : function() {

        //리스트 조회
        //폼 데이터 처리
        // cmmCtrl.setFormData($formObj);
        commonCodeAjax();

        /* File Dropzone Setting */
        $formObj.find(".dropzone").each(function(){
            var trgtObj = $(this);
            cmmCtrl.setDropzone(trgtObj, {
                maxFileCnt  : trgtObj.data("maxFileCnt"),
                maxFileSize : trgtObj.data("maxFileSize"),
                fileExtn    : trgtObj.data("fileExtn"),
                fileFieldNm : trgtObj.data("fileFieldNm")
            })
        });

        // 유효성 검사
        $formObj.validation({
            after : function(){
                let chk = true;

                const regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+]).{8,16}$/;
                if(!dupIdChk) {
                    alert(msgCtrl.getMsg("fail.mp.mpd.al_013"));
                    chk = false;
                    return chk;
                }
                if(!dupEmailChk) {
                    alert(msgCtrl.getMsg("fail.mp.mpd.al_030"));
                    chk = false;
                    return chk;
                }
                if($("#pwd").val() != $("#pwdCon").val()) {
                    alert(msgCtrl.getMsg("fail.mp.mpd.al_028"));
                    chk = false;
                    return chk;

                }
                if(!regex.test($("#pwd").val())) {
                    alert(msgCtrl.getMsg("fail.mp.mpd.al_028"));
                    chk = false;
                    return chk;
                }
                if($("#cmssrTypeCd").val()!= 'MEM_CD03004') {
                    if($("#cmssrCbsnCd").val() =='' || $("#cmssrCbsnCd").val() ==undefined) {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_029"));
                        chk = false;
                        return chk;
                    }
                }
                if($("#hpNo").val().length !=13) {
                    alert(msgCtrl.getMsg("fail.mp.mpd.al_033"));
                    chk = false;
                    return chk;
                }

                $(".dropzone").not(".notRequired").each(function(i){
                    if ($(this).children(".dz-preview").length == 0)
                    {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_014"));
                        chk = false;
                        return chk;
                    } else {
                    }
                });

              return chk;
            },
            async : {
                use : true,
                func : function (){
                    var actionUrl = "/mngwserc/mp/mpd/insert";
                    cmmCtrl.fileFrmAjax(function(data){
                        //콜백함수. 페이지 이동
                        if(data.respCnt > 0){
                            alert(msgCtrl.getMsg("fail.mp.mpd.al_004"));
                            location.replace("./list");
                        }
                    }, actionUrl, $formObj, "json");

                },
                error: function(e){
                    console.log(e);
                }
            }
        });

    }
    };

    // execute model
    ctrl.exec();

    return ctrl;
    });