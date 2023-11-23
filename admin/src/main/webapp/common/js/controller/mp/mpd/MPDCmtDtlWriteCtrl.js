define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpd/MPDCmtDtlWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    let dupEmailChk = false;
    let cmssrCd ;

    //업종/분야 코드 호출
    function commonCodeAjax() {
        cmmCtrl.frmAjax(function(respObj) {
            cmssrCd = respObj.cdDtlList;
        }, "/mngwserc/mp/mpd/cmssrCbsnCd", $formObj, "POST", "json",'',false);
    }

    function datepickerLoad() {
        // 게시기간(일자) Start -----
        $.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
            jQuery(obj).datetimepicker({
                timepicker : false,
                format : "Y-m-d",
                defaultDate : new Date(jQuery("body").data("curtDt")),
                defaultTime : "00:00",
                scrollInput : false,
                scrollMonth : false,
                scrollTime : false,
                todayButton: false,
                onSelectDate : function(selectedDate, selectedObj) {
                    var strtDt   = selectedDate;
                    var endDtObj = selectedObj.closest("fieldset").find(".datetimepicker_endDt");
                    var endDt	 = new Date(endDtObj.val());

                    if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
                    {
                        endDtObj.val(selectedObj.val());
                    }

                    endDtObj.datetimepicker("setOptions", { minDate : strtDt, value : endDtObj.val() });

                    selectedObj.blur();
                }
            });
        });

    }

    //위원 상세 조회
    var tabOne = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#tab1").html(respObj);
        }, "/mngwserc/mp/mpd/select-tab-one", $formObj, "POST", "html",'',false);
    }

    /**
     * 컨설팅 리스트 조회
     */
    var tabTwo = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/mp/mpd/select-tab-two", $formObj, "POST", "html",'',false);
    }

    var tabThree = function() {
        //상생 사업
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainerSan").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerSanTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerSan", "pagingContainerSan");
        }, "/mngwserc/mp/mpd/select-tab-three", $formObj, "POST", "html",'',false);

    }

    var tabFour = function() {

    }
    var tabReload = function (type) {

        if(type == 'cun') {
            tabTwo();
        }else if(type == 'bus' ) {
            tabThree();
        } else if(type == 'ken' ) {
            tabFour();
        }
    }
    function cmssrCdInit(values) {
        $(".cmssrCdDiv").show();
        $("#cmssrCbsnCd").empty();
        $("#cmssrCbsnCd").append("<option value=''>선택</option>");
        cmssrCd.MEM_CD.forEach(item => {
            if (item.cd.includes($("#cmssrTypeCd").val()) && item.dpth == 4 && $("#cmssrTypeCd").val() != '') {
                var selectValue = $("#cmssrCbsnCdSe").val() == item.cd;
                if(!values) {
                    selectValue = false ;
                }
                if(selectValue) {
                    $("#cmssrCbsnCd").append("<option selected value="+item.cd+">"+item.cdNm+"</option>");
                } else {
                    $("#cmssrCbsnCd").append("<option value="+item.cd+">"+item.cdNm+"</option>");

                }
            } else if($("#cmssrTypeCd").val() == 'MEM_CD03004'){
                $(".cmssrCdDiv").hide();
            }
        });
    }
    // set model
    ctrl.model = {
        id : {
        email : {
            event : {
                input : function() {
                    dupEmailChk = false;
                }
            }
        },
        btnPwdInit : {
            event : {
                click : function() {
                    if (confirm(msgCtrl.getMsg("confirm.co.coa.pwdInit")))
                    {
                        $formObj.find("input[name='id']").val(jQuery(this).data("id"));
                        cmmCtrl.frmAjax(function (data){
                            alert(msgCtrl.getMsg("success.co.coa.pwdInit"));
                        }, "/mngwserc/mp/mpa/pwd-init", $formObj, "post", "json", true);
                    }
                }
            }
        },

        //휴대폰 자동 하이픈
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
            //이메일 중복 체크
        dupEmail : {
            event : {
                click : function() {
                    if($("#email").val().trim().length > 0) {
                        cmmCtrl.frmAjax(function(respObj) {
                            console.log(respObj.dupChk);
                            if(respObj.dupChk == 'Y') {
                                dupEmailChk = true;
                                alert(msgCtrl.getMsg("fail.mp.mpd.al_027"));
                            } else {
                                dupEmailChk = false;
                                alert(msgCtrl.getMsg("fail.mp.mpd.al_026"));
                            }
                        }, "/mngwserc/mp/mpa/dup-email", $formObj, "POST", "json",'',false);

                    } else {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_031"));
                    }
              }
            }
        },

        //업종/분야 select 박스
        cmssrTypeCd : {
                event : {
                    change : function() {
                        cmssrCdInit(false);
                    }
                }
            },


    },
    classname : {
        tabClick : {
            event : {
                click : function (e){
                    if(e.target.getAttribute('href').substr(1)!='dtl') {
                        $(".dtl-tab").hide();
                    } else {
                        $(".dtl-tab").show();
                    }
                }
            }
        },
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
        pageSet : {
            event : {
                click : function() {
                    var activeTab = $('#myTabs li.active a').attr('href').substring(1);
                    //페이징 이동
                    if( $(this).attr("value") != "null" ){
                        $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                        tabReload(activeTab);
                    }
                }
            }
        },
        //페이징 목록 갯수

        listRowSizeContainer : {
            event : {
                change : function(){
                    var activeTab = $('#myTabs li.active a').attr('href').substring(1);
                    //리스트 갯수 변경
                    $formObj.find("input[name=listRowSize]").val($(this).val());
                    tabReload(activeTab,1);
                }
            }
        }
    },
    immediately : function() {

        //리스트 조회
        //폼 데이터 처리
        // cmmCtrl.setFormData($formObj);
        commonCodeAjax();
        tabOne();
        tabTwo();
        tabThree();
        cmssrCdInit(true);
        datepickerLoad();



        /* File Dropzone Setting */
        $formObj.find(".dropzone").each(function(){
            var trgtObj = $(this);
            cmmCtrl.setDropzone(trgtObj, {
                maxFileCnt  : trgtObj.data("maxFileCnt"),
                maxFileSize : trgtObj.data("maxFileSize"),
                fileExtn    : trgtObj.data("imageExtns"),
                fileFieldNm : trgtObj.data("fileFieldNm")
            })
        });

        // 유효성 검사
        $formObj.validation({
            after : function(){
                let chk = true;

                const regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+]).{8,16}$/;
                if(jQuery("#email").val() != jQuery("#oldEmail").val()) {
                    if(!dupEmailChk) {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_030"));
                        chk = false;
                    }
                }
                if($("#cmssrTypeCd").val()!= 'MEM_CD03004') {
                    if($("#cmssrCbsnCd").val() =='' || $("#cmssrCbsnCd").val() ==undefined) {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_029"));
                        chk = false;
                    }
                }
                $(".dropzone").not(".notRequired").each(function(i){
                    if ($(this).children(".dz-preview").length == 0)
                    {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_014"));
                        chk = false;
                    } else {
                    }
                });

              return chk;
            },
            async : {
                use : true,
                func : function (){
                    var actionUrl = "/mngwserc/mp/mpa/update";
                    cmmCtrl.fileFrmAjax(function(data){
                        //콜백함수. 페이지 이동
                        if(data.respCnt > 0){
                            alert(msgCtrl.getMsg("fail.mp.mpd.al_004"));
                            // location.replace("./list");
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