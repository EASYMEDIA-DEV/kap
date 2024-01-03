define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/consult/CONsultingApplWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var idNum = 0;

    var callbackAjaxAddrList = function (data) {
        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>선택</option>";

        for (var i = 0; i < detailList.length; i++) {

            var cd = detailList[i].cd;
            var cdNm = detailList[i].cdNm;

            selectHtml += "<option value='" + cd + "' >" + cdNm + "</option>";
        }

        $("#subAddr option").remove();

        $("#subAddr").append(selectHtml);

        var subAddr = $("#subAddr").data("subAddr");

        $("#subAddr").val(subAddr).prop("selected", true);

    }

    // set model
    ctrl.model = {
        id : {
            sameAsHQChk : {
                event : {
                    click: function(){
                        var cmpnMst = {};
                        cmpnMst.bsnmNo = $(".bsnmNo").val().replaceAll("-", "");
                        cmmCtrl.jsonAjax(function (data) {
                            var info = JSON.parse(data)
                            $("#hqZipcode").val(info.list[0].zipcode);
                            $("#hqBscAddr").val(info.list[0].bscAddr);
                            $("#hqDtlAddr").val(info.list[0].dtlAddr);
                        }, './bsnmNoSearch', cmpnMst, "text");
                    }
                }
            },
            agreeChk : {
                event : {
                    click : function(){
                        var agree = $("#agreeChk").val();
                        if(agree == "N"){
                            $("#agreeChk").val("Y");
                            $("#agreeChk").attr("checked", true);
                        }else{
                            $("#agreeChk").val("N");
                            $("#agreeChk").attr("checked", false)
                        }
                    }
                }
            }
        },
        classname : {
            searchPostCode: {
                event: {
                    click: function () {
                        var idVal = $(this).attr('id');
                        if (idVal == "hqAddr") {
                            cmmCtrl.searchPostCode(width, height, "hqZipcode", "hqBscAddr", "hqDtlAddr");
                        } else {
                            cmmCtrl.searchPostCode(width, height, "zipcode", "bscAddr", "dtlAddr");
                        }
                    }
                }
            },
            cmpnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".tempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dlvryRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dlvryRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dlvryTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children().find(".cmpnPlus").hide();
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                            $("#dlvryTempDiv").children().eq(0).find(".closeLabel").hide();
                            $('#dlvryTempDiv').children().not(':eq(0)').find(".closeLabel").show();
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            closeLabel : {
                event : {
                    click : function(){
                        //거래처별 매출 비중 삭제
                        $(this).parents(".tempRow").remove();
                        // 완성차 의존율 삭제
                        $(this).parents(".dpTempRow").remove();

                    }
                }
            },
            dpndnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".dpTempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dpndnRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dpndnRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dpTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children().find(".dpndnPlus").hide();
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                            $("#dpTempDiv").children().eq(0).find(".closeLabel").hide();
                            $('#dpTempDiv').children().not(':eq(0)').find(".closeLabel").show();
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            mainAddr: {
                event: {
                    click: function () {
                        var mainAddr = {}
                        mainAddr.cd = $(this).val();
                        cmmCtrl.jsonAjax(function (data) {
                            callbackAjaxAddrList(data);
                        }, './subAddrSelect', mainAddr, "text");
                    }
                }
            },cbsnCd: {
                event: {
                    click: function () {
                        var cbsnVal = $("input[name=cbsnCd]:checked").val();
                        if (cbsnVal == "TEC_GUIDE_INDUS01") { // 기타일 때 input 활성화
                            $("input[name=etcNm]").removeClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", false);
                        } else {
                            $("input[name=etcNm]").addClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", true);
                        }
                    }
                }
            },
            submit : {
                event : {
                    click : function(){
                        var agree = $("#agreeChk").val();
                        if(agree = "N"){
                            alert("약관에 동의해주세요");
                            $("#agreeChk").focus()
                            return false;
                        }

                    }
                }
            }
        },
        immediately : function() {
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});