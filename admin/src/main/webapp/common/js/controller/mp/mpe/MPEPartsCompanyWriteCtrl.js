define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPEPartsCompanyWriteCtrl"
    };

    var $formObj = jQuery("#frmData");
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var workChk = true;
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var tabTwo = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#techListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#eduListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "techListContainer", "pagingContainer");
        }, "/mngwserc/mp/mpe/select-tab-two", $formObj, "POST", "html",'',false);

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#eduStatisticsListContainer").html(respObj);
        }, "/mngwserc/mp/mpe/selectEduStatisticsCntList", $formObj, "POST", "html",'',false);

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#consultListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#eduListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "consultListContainer", "pagingContainer");
        }, "/mngwserc/mp/mpe/select-tab-two", $formObj, "POST", "html",'',false);


        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#fundingListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#eduListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "fundingListContainer", "pagingContainer");
        }, "/mngwserc/mp/mpe/select-tab-two", $formObj, "POST", "html",'',false);

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#carTargetListContainer").html(respObj);
        }, "/mngwserc/mp/mpe/selectCarTargetList", $formObj, "POST", "html",'',false);
    }

    var callbackAjaxDelete = function(data){
        if (parseInt(data.respCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        }
        else
        {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

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
            btnBsnmNo : {
                event : {
                    click : function () {
                        // 사업자등록번호 형식 체크 알럿 추가하기


                        workChk = false;
                        if($("#bsnmNo").val() =='' || $("#bsnmNo").val() == undefined) {
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_016"));
                            return;
                        } else {
                            jQuery.ajax({
                                url : "/mngwserc/nice/comp-chk",
                                type : "post",
                                data :
                                    {
                                        "compNum" : $("#bsnmNo").val()
                                    },
                                success : function(data)
                                {
                                    if(data.rsp_cd=='P000') {
                                        if(data.result_cd == '01') {
                                            if(data.comp_status == '1') {
                                                $("#cmpnNm").val(data.comp_name);
                                                $("#rprsntNm").val(data.representive_name);
                                                workChk = true;
                                            } else {
                                                alert(msgCtrl.getMsg("fail.mp.mpa.al_015"));
                                                $("#cmpnNm").val("");
                                                $("#rprsntNm").val("");
                                                $("#bsnmNo").val("");
                                                workChk = false;
                                            }
                                        } else {
                                            alert(msgCtrl.getMsg("fail.mp.mpa.al_015"));
                                            $("#cmpnNm").val("");
                                            $("#rprsntNm").val("");
                                            $("#bsnmNo").val("");
                                            workChk = false;
                                        }
                                    } else {
                                        alert(msgCtrl.getMsg("fail.mp.mpa.al_015"));
                                        $("#cmpnNm").val("");
                                        $("#rprsntNm").val("");
                                        $("#bsnmNo").val("");
                                        workChk = false;
                                    }
                                },
                                error : function(xhr, ajaxSettings, thrownError)
                                {
                                    cmmCtrl.errorAjax(xhr);
                                    jQuery.jstree.rollback(data.rlbk);
                                }
                            });
                        }
                    }
                }
            },
            ctgryCd : {
                event : {
                    change : function () {
                        var ctgryCd =  $("#ctgryCd option:selected").val();
                        if (ctgryCd == "COMPANY01001") {
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").show();
                            $(".pay5StarArea").show();
                            $(".tchlg5StarArea").show();
                       }
                        else if (ctgryCd == "COMPANY01002") {
                            $(".sqInfoArea").show();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();

                        } else {
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();
                        }
                    }
                }
            },
            bsnmNo : {
                event : {
                    input : function() {
                        workChk = false;
                    }
                }
            },
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
            }
        },
        classname : {
            // do something...
        },
        immediately : function(){
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);
            tabTwo();

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                }
            });

            // 유효성 검사
            $formObj.validation({
                after : function(){
                    if(workChk == false) {
                        alert("사업자등록번호를 인증해주세요.");
                        return false;
                    } else {
                        return true;
                    }
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        cmmCtrl.frmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(actionMsg);
                                location.replace("./list");
                            }
                            location.replace("./list");
                            actionUrl = "./list";
                        }, actionUrl, $formObj, "post", "json")
                    }
                },
                msg : {
                    empty : {
                        text : " 입력해주세요."
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

