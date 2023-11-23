define(["ezCtrl","ezVald"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpb/MPBMemberPartsSocietyWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");
    var $excelObj2 = ctrl.obj.parent().find(".container-fluid");


    var dupEmailChk = true;

    //사용자 상세 조회
    var tabOne = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#tab1").html(respObj);
        }, "/mngwserc/mp/mpb/select-tab-one", $formObj, "POST", "html",'',false);

    }

    /**
     * 교육 사업 조회
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
        }, "/mngwserc/mp/mpb/select-tab-two", $formObj, "POST", "html",'',false);
    }

    /**
     * 컨설티 사업 조회
     */
    var tabThree = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainerBus").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerBusTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerBus", "pagingContainerBus");
        }, "/mngwserc/mp/mpb/select-tab-three", $formObj, "POST", "html",'',false);
    }

    /**
     * 상생 사업 조회 및 미래차공모전 조회
     */
    var tabFour = function () {

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
        }, "/mngwserc/mp/mpb/select-tab-four", $formObj, "POST", "html",'',false);

        //미래차 공모전
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainerFuc").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            if(totCnt > 0) {
                $(".futureCar").show();
            }
            //총 건수
            ctrl.obj.find("#listContainerFucTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerFuc", "pagingContainerFuc");
        }, "/mngwserc/mp/mpa/select-tab-two", $formObj, "POST", "html",'',false);

    }

    /**
     * 1대1 문의 조회
     */
    var tabFive = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainerInqr").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            //총 건수
            ctrl.obj.find("#listContainerInqrTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerInqr", "pagingContainerInqr");
        }, "/mngwserc/mp/mpb/select-tab-five", $formObj, "POST", "html",'',false);

    }
    // 목록 조회
    var search = function (page){

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        tabOne();
        tabTwo();
        tabThree();
        tabFour();
        tabFive();
    }



    var tabReload = function (type,page) {

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        if(type == 'edu') {
            tabTwo();
        } else if(type == 'bus' ) {
            tabThree();
        } else if(type == 'san' ) {
            tabFour();
        } else if(type == 'chat' ) {
            tabFive();
        }
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
        telNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
        },
        searchPostCode : {
            event : {
                click : function() {
                    cmmCtrl.searchPostCode(width,height,"zipcode","bscAddr","dtlAddr");

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
            email : {
                event : {
                    input : function() {
                        dupEmailChk = false;
                    }
                }
            },
            dupEmail : {
                event : {
                    click : function() {
                        cmmCtrl.frmAjax(function(respObj) {
                                if(respObj.dupChk == 'Y') {
                                    dupEmailChk = true;
                                    alert(msgCtrl.getMsg("fail.mp.mpa.al_008"));
                                } else {
                                    dupEmailChk = false;
                                    alert(msgCtrl.getMsg("fail.mp.mpa.al_007"));
                                }
                        }, "/mngwserc/mp/mpa/dup-email", $formObj, "POST", "json",'',false);
                    }
                }
            },

        //엑셀다운로드
        btnExcelDown : {
            event : {
                click: function () {
                    //사유입력 레이어팝업 활성화
                    $excelObj.find("#rsn").val('');
                    $excelObj.modal("show");
                }
            }
        }
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
    //페이징 처리
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
        $(".futureCar").hide();
        cmmCtrl.setFormData($formObj);
        search();

        // 유효성 검사
        $formObj.validation({
            after : function(){
                if(jQuery("#email").val() != jQuery("#oldEmail").val()) {
                    if(!dupEmailChk) {
                        alert(msgCtrl.getMsg("fail.mp.mpa.al_012"));
                        return false;
                    }
                }

                if($("#telNo").val().length < 11) {
                    alert(msgCtrl.getMsg("fail.mp.mpb.al_011"));
                    return false;

                }
                return true;
            },
            async : {
                use : true,
                func : function (){
                    var actionUrl = "/mngwserc/mp/mpa/update";
                    // var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                    cmmCtrl.frmAjax(function(data){
                        if(data.respCnt >=1) {
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_004"));
                        }
                    }, actionUrl, $formObj, "post", "json")
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