define(["ezCtrl","ezVald"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpa/MPAUserWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

    var dupEmailChk = true;

    //사용자 상세 조회
    var tabOne = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#tab1").html(respObj);
        }, "/mngwserc/mp/mpa/select-tab-one", $formObj, "POST", "html",'',false);

    }

    /**
     * 미래차공모전 리스트 조회
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
        }, "/mngwserc/mp/mpa/select-tab-two", $formObj, "POST", "html",'',false);
    }

    /**
     * 1대1 문의 조회
     */
    var tabThree = function () {
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
        }, "/mngwserc/mp/mpa/select-tab-three", $formObj, "POST", "html",'',false);
    }
    // 목록 조회
    var search = function (page){

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        tabOne();
        tabTwo();
        tabThree();
    }

    var tabReload = function (type , page) {

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        if(type == 'pur') {
            tabTwo();
        } else if(type == 'chat' ) {
            tabThree();
        }
    }
    // set model
    ctrl.model = {
        id : {

            btnWthdrw : {
                event : {
                    click : function() {


                        var actForm = {};

                        //탈퇴사유 유효성체크

                        if($("#wthdrwRsnCd").val() == ""){
                            alert("탈퇴사유를 입력해주세요");
                            $("#wthdrwRsnCd").focus();
                            return false;
                        }

                        //탈퇴사유 코드 가져옴
                        actForm.wthdrwRsnCd = $("#wthdrwRsnCd").val();

                        //선택한 항목이 기타일때만 폼 넣어줌
                        if(actForm.wthdrwRsnCd == "MEM_WTHDRW_005"){
                            actForm.wthdrwRsnEtcNm = $("#wthdrwRsnEtcNm").val();
                        }

                        actForm.memSeq = $("#detailsKey").val();

                        if(confirm("회원 탈퇴를 진행 하시겠습니까?")){
                            cmmCtrl.jsonAjax(function(data){
                                alert("탈퇴 처리되었습니다.");
                                location.href = "./list";
                            }, "./update-wthdrw", actForm, "text");
                        }

                    }
                }
            },

            btnSearch : {
                event : {
                click : function() {
                //검색버튼 클릭시
                    cmmCtrl.setFormData($formObj);
                    search(1);
                }
            }
        },
        searchPostCode : {
            event : {
                click : function() {
                    // searchPostCode();
                    cmmCtrl.searchPostCode(width,height,"zipcode","bscAddr","dtlAddr");

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
                                if(phoneLen == 8) {
                                    phoneNumber = phoneNumber.replace(/(\d{4})(\d+)/, '$1-$2');
                                }else if (phoneLen == 10) {
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
                    input : function(e) {
                        $(this).val(e.target.value.toLowerCase());
                        dupEmailChk = false;
                    }
                }
            },
            dupEmail : {
                event : {
                    click : function() {
                       if (!$("input[name='email']").val() == "") {

                           if(cmmCtrl.getEmailChk($("input[name='email']").val())) {
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

                       }  else {
                           alert(msgCtrl.getMsg("fail.mp.mpa.al_006"));
                           return false;
                       }
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
    },

        // do something...
        wthdrwRsnCd : {
            event : {
                change : function() {
                    if($(this).val() == 'MEM_WTHDRW_005') {
                        $("#wthdrwRsnEtcNm").attr('disabled',false);
                    } else {
                        $("#wthdrwRsnEtcNm").val("");
                        $("#wthdrwRsnEtcNm").attr('disabled',true);

                    }
                }
            }
        }
    },
    immediately : function() {
        //리스트 조회
        //폼 데이터 처리
        $formObj.find("input[name=listRowSize]").val("10");
        $(".listRowSizeContainer").val("10");
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
                if( $("#telNo").val().length !=0 && $("#telNo").val().length < 11 ) {
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
                        location.href = './list';
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