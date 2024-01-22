define(["ezCtrl","ezVald"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpc/MPCLecturerWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = $(".excel-down");

    var dupEmailChk = true;

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
        }, "/mngwserc/mp/mpc/selectEduList", $formObj, "GET", "html",'',false);
    }

    var tabThree = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#winBusinessListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#winBusinessListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "winBusinessListContainer", "winBusinessPagingContainer");
        }, "/mngwserc/mp/mpc/selectWinBusinessList", $formObj, "GET", "html",'',false);
    }


    var tabReload = function (type) {

        if(type == 'edu') {
            tabTwo();
        } else if(type == 'win') {
            tabThree();
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
                                }, "/mngwserc/mp/mpc/dup-email", $formObj, "POST", "json",'',false);
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
                        $(".excel-down").find("#rsn").val('');
                        $(".excel-down").modal("show");
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
                            if(phoneLen == 8) {
                                phoneNumber = phoneNumber.replace(/(\d{4})(\d+)/, '$1-$2');
                            }else if (phoneLen == 10) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
            hpNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen > 3 && phoneLen <= 7) {
                            phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                        } else if (phoneLen > 7) {
                            if (phoneLen == 10) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            }

        },
        classname : {
            tabClick : {
                event : {
                    click : function (e){
                        var target = e.target.getAttribute('href').substr(1);
                        if(target == 'edu') {
                            $(".dtl-tab").hide();
                            $(".excel-area").show();
                        } else if (target == 'dtl') {
                            $(".dtl-tab").show();
                            $(".excel-area").hide();
                        } else {
                            $(".dtl-tab").hide();
                            $(".excel-area").hide();
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
            cmmCtrl.setFormData($formObj);
            $(".excel-area").hide();
            tabTwo();
            tabThree();

            $excelObj.find("button.down").on('click', function(){
                if (confirm("저장하시겠습니까?")){
                    var rsn = $excelObj.find("#rsn").val().trim();
                    var frmDataObj    = $formObj.closest("form");

                    frmDataObj.find("input[name='rsn']").remove();

                    if (rsn != "") {
                        frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

                        $.fileDownload("./excel-down?" + frmDataObj.serialize() , {
                            prepareCallback : function(url){
                                jQuery(".loadingbar").stop().fadeIn(200);
                            },
                            successCallback : function(url){
                                jQuery(".loadingbar").stop().fadeOut(200);
                                $excelObj.find("button.close").trigger('click');
                            },
                            failCallback : function(html,url){
                                jQuery(".loadingbar").stop().fadeOut(200);
                            }
                        });
                    } else {
                        alert(msgCtrl.getMsg("fail.reason"));
                        return;
                    }
                }

            });

            // 유효성 검사
            $formObj.validation({
                after : function(){
                    if(jQuery("#email").val() != jQuery("#oldEmail").val()) {
                        if(!dupEmailChk) {
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_012"));
                            return false;
                        }
                    }
                    return true;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='isttrSeq']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = msgCtrl.getMsg("fail.mp.mpc.al_004");

                        cmmCtrl.frmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(actionMsg);
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