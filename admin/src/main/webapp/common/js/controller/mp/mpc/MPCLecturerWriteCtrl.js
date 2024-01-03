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
                    input : function() {
                        dupEmailChk = false;
                    }
                }
            },
            dupEmail : {
                event : {
                    click : function() {
                        if(ctrl.obj.find("#email").val()) {
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
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_006"));
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
                var rsn = $excelObj.find("#rsn").val().trim();
                var frmDataObj    = $formObj.closest("form");

                frmDataObj.find("input[name='rsn']").remove();

                if (rsn != "") {
                    frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

                    //파라미터를 물고 가야함.
                    location.href = "/mngwserc/mp/mpc/excel-down?" + frmDataObj.serialize();

                } else {
                    alert(msgCtrl.getMsg("fail.reason"));
                    return;
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
                        var actionMsg = ( $.trim($formObj.find("input[name='isttrSeq']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

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