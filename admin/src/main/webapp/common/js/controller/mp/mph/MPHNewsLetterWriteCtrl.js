define(["ezCtrl","ezVald"], function(ezCtrl) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/mp/mph/MPHNewsLetterWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    var dupEmailChk = true;

    var tabOne = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#tab1").html(respObj);
        }, "./dtl", $formObj, "POST", "html",'',false);

    }

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
        }, "/mngwserc/mp/mpc/select-tab-two", $formObj, "POST", "html",'',false);
    }

    //레이어 구분, 규모 목록 조회
    var codeSelect = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#selectBoxArea").html(respObj);
        }, "/mngwserc/mp/mpc/codeSelect", $formObj, "GET", "html",'',false);
    }
    var tabReload = function (type) {

        if(type == 'edu') {
            tabTwo();
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
            mngBsnCd : {
                event : {
                    change : function () {
                        var mngBsnCd = ctrl.obj.find("#mngBsnCd option:selected").val();
                        $formObj.find("input[name=mngBsnCd]").val(mngBsnCd);
                        codeSelect();
                    }
                }
            }
        },
        classname : {
            tabClick : {
                event : {
                    click : function (e){
                        console.log(e.target.getAttribute('href').substr(1));
                        if(e.target.getAttribute('href').substr(1)!='dtl') {
                            $(".dtl-tab").hide();
                        } else {
                            $(".dtl-tab").show();
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
            tabOne();
            tabTwo();

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