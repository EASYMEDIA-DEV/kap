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

    var bsnmNoChk = function(bizNum){

        var checkSum = 0;
        var checkID = [1,3,7,1,3,7,1,3,5];
        var bizNum = (bizNum + '').match(/\d{1}/g);

        if (bizNum.length != 10) {
            return false;
        }
        for (var i= 0; i < 9; i++)    checkSum += checkID[i] * Number(bizNum[i]);

        if (10 - ((checkSum + Math.floor(checkID[8] * Number(bizNum[8]) / 10)) % 10) != Number(bizNum[9])){
            return false;
        }

        return true;

    }


    var tabTwo = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#eduStatisticsListContainer").html(respObj);
        }, "/mngwserc/mp/mpe/selectEduStatisticsCntList", $formObj, "POST", "html",'',false);

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#techGuidanceListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#techGuidanceListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "techGuidanceListContainer", "pagingContainer");
        }, "/mngwserc/mp/mpe/selectTechGuidanceList", $formObj, "POST", "html",'',false);

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#manageConsultContainer").html(respObj);
        }, "/mngwserc/mp/mpe/selectManageConsultList", $formObj, "POST", "html",'',false);

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#fundingListContainer").html(respObj);
        }, "/mngwserc/mp/mpe/selectFundingList", $formObj, "POST", "html",'',false);


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
                        workChk = false;
                        if($("#bsnmNo").val() =='' || $("#bsnmNo").val() == undefined) {
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_016"));
                            return;
                        } else {
                            var ajaxData = {
                                bsnmNo:  $("#bsnmNo").val()
                            }

                            jQuery("#frmData").serializeArray().forEach(function (field) {
                                if (field.name == '_csrf') {
                                    ajaxData[field.name] = field.value;
                                }
                            });
                            $.ajax({
                                type: "post",
                                url: './checkBsnmNo',
                                dataType: "json",
                                data:ajaxData,
                                success: function (r) {
                                    if (r.respCnt) {
                                      alert(msgCtrl.getMsg("fail.mp.mpe.al_001"));
                                      $("#bsnmNo").val("");
                                      workChk = false;
                                      return false;
                                    } else {

                                        if(!bsnmNoChk($("#bsnmNo").val())){
                                            alert("사업자번호가 올바르지않습니다");
                                            $("#bsnmNo").val("");
                                            return false;
                                        }

                                        workChk = true;
                                        /*jQuery.ajax({
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
                                        });*/
                                    }
                                },
                                error: function (xhr, ajaxSettings, thrownError) {
                                    alert("인증에 실패했습니다.");
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
                            $(".secondNd").val("");
                            $(".secondNd").removeClass("notRequired");
                            $(".secondNd").addClass("notRequired");
                       }
                        else if (ctgryCd == "COMPANY01002") {
                            $(".firstSt").val("");
                            $(".firstSt").removeClass("notRequired");
                            $(".firstSt").addClass("notRequired");

                            $(".sqInfoArea").show();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();

                        } else {
                            $(".firstSt").val("");
                            $(".firstSt").removeClass("notRequired");
                            $(".firstSt").addClass("notRequired");
                            $(".secondNd").val("");
                            $(".secondNd").removeClass("notRequired");
                            $(".secondNd").addClass("notRequired");
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();
                        }
                    }
                }
            },
            qlty5StarCd : {
                event : {
                    change : function () {
                        if($(this).val()!="" && $("#qlty5StarYear").val() =="") {
                            $("#qlty5StarYear").removeClass("notRequired");
                        } else if($(this).val() =="" && $("#qlty5StarYear").val() !="") {
                            $(this).removeClass("notRequired");
                        } else if($(this).val() =="" && $("#qlty5StarYear").val() =="") {
                            $(this).addClass("notRequired");
                            $("#qlty5StarYear").addClass("notRequired");
                        }
                    }
                }
            },
            qlty5StarYear : {
                event : {
                    change : function () {
                        if($(this).val()!="" && $("#qlty5StarCd").val() =="") {
                            $("#qlty5StarCd").removeClass("notRequired");
                        } else if($(this).val() =="" && $("#qlty5StarCd").val() !="") {
                            $(this).removeClass("notRequired");
                        } else if($(this).val() =="" && $("#qlty5StarCd").val() =="") {
                            $(this).addClass("notRequired");
                            $("#qlty5StarCd").addClass("notRequired");
                        }
                    }
                }
            },


            pay5StarCd : {
                event : {
                    change : function () {
                        if($(this).val()!="" && $("#pay5StarYear").val() =="") {
                            $("#pay5StarYear").removeClass("notRequired");
                        } else if($(this).val() =="" && $("#pay5StarYear").val() !="") {
                            $(this).removeClass("notRequired");
                        } else if($(this).val() =="" && $("#pay5StarYear").val() =="") {
                            $(this).addClass("notRequired");
                            $("#pay5StarYear").addClass("notRequired");
                        }
                    }
                }
            },
            pay5StarYear : {
                event : {
                    change : function () {
                        if($(this).val()!="" && $("#pay5StarCd").val() =="") {
                            $("#pay5StarCd").removeClass("notRequired");
                        } else if($(this).val() =="" && $("#pay5StarCd").val() !="") {
                            $(this).removeClass("notRequired");
                        } else if($(this).val() =="" && $("#pay5StarCd").val() =="") {
                            $(this).addClass("notRequired");
                            $("#pay5StarCd").addClass("notRequired");
                        }
                    }
                }
            },

            tchlg5StarCd : {
                event : {
                    change : function () {
                        if($(this).val()!="" && $("#tchlg5StarYear").val() =="") {
                            $("#tchlg5StarYear").removeClass("notRequired");
                        } else if($(this).val() =="" && $("#tchlg5StarYear").val() !="") {
                            $(this).removeClass("notRequired");
                        } else if($(this).val() =="" && $("#tchlg5StarYear").val() =="") {
                            $(this).addClass("notRequired");
                            $("#tchlg5StarYear").addClass("notRequired");
                        }
                    }
                }
            },
            tchlg5StarYear : {
                event : {
                    change : function () {
                        if($(this).val()!="" && $("#tchlg5StarCd").val() =="") {
                            $("#tchlg5StarCd").removeClass("notRequired");
                        } else if($(this).val() =="" && $("#tchlg5StarCd").val() !="") {
                            $(this).removeClass("notRequired");
                        } else if($(this).val() =="" && $("#tchlg5StarCd").val() =="") {
                            $(this).addClass("notRequired");
                            $("#tchlg5StarCd").addClass("notRequired");
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
            secondNd : {
                event : {
                    input : function (e) {
                        let sqOne = $(this).parent().children().eq(1).val();
                        let sqTwo = $(this).parent().children().eq(2).val();
                        let sqThree = $(this).parent().children().eq(3).val();
                        let sqFour = $(this).parent().children().eq(4).val();
                        if (sqOne == "" && (sqTwo != "" || sqThree != "" || sqFour != "")) {
                            $(this).parent().children().eq(1).removeClass("notRequired");
                        }
                        if (sqTwo == "" && (sqOne != "" || sqThree != "" || sqFour != "")) {
                            $(this).parent().children().eq(2).removeClass("notRequired");
                        }
                        if (sqThree == "" && (sqTwo != "" || sqOne != "" || sqFour != "")) {
                            $(this).parent().children().eq(3).removeClass("notRequired");
                        }
                        if (sqFour == "" && (sqTwo != "" || sqThree != "" || sqOne != "")) {
                            $(this).parent().children().eq(4).removeClass("notRequired");
                        }
                        if (sqOne == "" && sqTwo == "" && sqThree == "" && sqFour == "") {
                            $(this).parent().children().eq(1).addClass("notRequired");
                            $(this).parent().children().eq(2).addClass("notRequired");
                            $(this).parent().children().eq(3).addClass("notRequired");
                            $(this).parent().children().eq(4).addClass("notRequired");
                        }
                        if (sqOne != "" && sqTwo != "" && sqThree != "" && sqFour != "") {
                            $(this).parent().children().eq(1).addClass("notRequired");
                            $(this).parent().children().eq(2).addClass("notRequired");
                            $(this).parent().children().eq(3).addClass("notRequired");
                            $(this).parent().children().eq(4).addClass("notRequired");
                        }
                    }
                }
            }
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

            var edu1 = $("#eduStatisticsListContainer tr:eq(0) td:eq(0)").text();
            var edu2 = $("#eduStatisticsListContainer tr:eq(1) td:eq(0)").text();
            var edu3 = $("#eduStatisticsListContainer tr:eq(2) td:eq(0)").text();
            var edu4 = $("#eduStatisticsListContainer tr:eq(3) td:eq(0)").text();

            $("#cumulativeCnt").text(parseInt(edu1) + parseInt(edu2) + parseInt(edu3) + parseInt(edu4));

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

