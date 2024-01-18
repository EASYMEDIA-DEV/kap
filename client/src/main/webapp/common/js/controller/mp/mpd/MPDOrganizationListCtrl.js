define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpd/MPDOrganizationListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // function
    function makeList(listData, typeId, cntId, pageId, moreYn) {
        if(listData.list) {
            if(moreYn != 'Y') {
                $("#" + typeId).empty();
            }

            var cmssrType = "";
            if(typeId == "advList") {
                cmssrType = "자문위원";
            }
            else {
                cmssrType = "전문위원";
            }

            if(listData.totalCount < 1 || listData.totalCount == null) {
                $("#" + cntId).text(0);
            }
            else {
                $("#" + cntId).text(listData.totalCount);
            }

            if(listData.totalCount <= listData.listRowSize || listData.pageIndex == (Math.ceil((listData.totalCount-1)/listData.listRowSize) + 1)) {
                $("#" + pageId).css("display", "none");
            }
            else {
                $("#" + pageId).css("display", "flex");
                $("#" + pageId + " a span.item-count").text("(" + listData.pageIndex + "/" + (Math.ceil((listData.totalCount-1)/listData.listRowSize) + 1) + ")");
                $("#" + pageId + " a").data("pageIndex", listData.pageIndex);
            }

            var filePath = "";

            var value = "";
            listData.list.forEach(function (data) {
                if(data.webPath) {
                    filePath = data.webPath;
                }
                else {
                    filePath = "/common/images/@img-foundation-group-member.png";
                }

                value +=
                    "           <a class=\"list-item btnDetail\" href=\"javascript:\" title=\"팝업 열기\" data-mem-seq=\"" + data.memSeq + "\">\n" +
                    "                <div class=\"bg\">\n" +
                    "                    <img src=\"" + filePath + "\" alt=\"\">\n" +
                    "                </div>\n" +
                    "                <div class=\"txt-box\">\n" +
                    "                    <div class=\"names\">\n" +
                    "                        <p class=\"name f-title3\">" + data.name + "</p>\n" +
                    "                        <p class=\"position f-sub-head\">" + cmssrType + "</p>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"labels\">\n" +
                    "                        <p class=\"box-label\"><span>" + data.cmssrCbsnCdNm  + "</span></p>\n" +
                    "                    </div>\n" +
                    "                    <div class=\"btn-wrap\">\n" +
                    "                        <div class=\"btn-text-icon black-circle\"><span>더 알아보기</span></div>\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </a>";
                $("#" + typeId).append(value);
                $("#" + pageId + " a").data("pageIndex", listData.pageIndex);
                $("#" + pageId + " a").data("cmssrCbsnCd", listData.cmssrCbsnCd);
                value="";
            });
        }
        else {
            if(moreYn != 'Y') {
                $("#" + typeId).empty();
            }
        }
    }


    // set model
    ctrl.model = {
        id : {

        },
        classname : {

            btnFilter : {
                event : {
                    click : function() {
                        var cmssrCbsnCd = $(this).data("cmssrCbsnCd");

                        var typeId;
                        var cntId;
                        var pageId;
                        if(cmssrCbsnCd.charAt(10) == 3) {
                            typeId = "advList";
                            cntId = "advCnt";
                            pageId = "advPage";
                        }
                        else {
                            typeId = "proList";
                            cntId = "proCnt";
                            pageId = "proPage";
                        }

                        var cmssrTypeList = new Array();

                        if(cmssrCbsnCd.length < 12) {
                            cmssrTypeList.push(cmssrCbsnCd);
                            cmssrCbsnCd = null;
                        }
                        else {
                            cmssrTypeList.push(cmssrCbsnCd.substring(0, 11));
                        }

                        $.ajax({
                            type: "post",
                            url: './list',
                            dataType: "json",
                            data: {
                                "cmssrCbsnCd" : cmssrCbsnCd,
                                "cmssrTypeList" : cmssrTypeList
                            },
                            success: function (rtnData) {
                                if(rtnData) {
                                    makeList(rtnData, typeId, cntId, pageId);
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

            btnMore : {
                event : {
                    click : function() {
                        var pageIndex = $(this).data("pageIndex");
                        var cmssrCbsnCd = $(this).data("cmssrCbsnCd");
                        var cmssrTypeCd = $(this).data("cmssrTypeCd");

                        var typeId;
                        var cntId;
                        var pageId;
                        if(cmssrTypeCd.charAt(10) == 3) {
                            typeId = "advList";
                            cntId = "advCnt";
                            pageId = "advPage";
                        }
                        else {
                            typeId = "proList";
                            cntId = "proCnt";
                            pageId = "proPage";
                        }

                        if($("#" + cntId).text() <= pageIndex) {
                            return false;
                        }

                        var cmssrTypeList = new Array();
                        cmssrTypeList.push(cmssrTypeCd);

                        $.ajax({
                            type: "post",
                            url: './list',
                            dataType: "json",
                            data: {
                                "pageIndex" : pageIndex + 1,
                                "cmssrTypeList" : cmssrTypeList,
                                "cmssrCbsnCd" : cmssrCbsnCd
                            },
                            success: function (rtnData) {
                                if(rtnData) {
                                    makeList(rtnData, typeId, cntId, pageId, 'Y');
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

            btnDetail : {
                event : {
                    click : function() {
                        var detailsKey = $(this).data("memSeq");

                        $.ajax({
                            type: "post",
                            url: './select',
                            dataType: "json",
                            data: {
                                "detailsKey" : detailsKey
                            },
                            success: function (rtnData) {
                                if(rtnData) {
                                    var cmssrType = "";
                                    if(rtnData.cmssrCbsnCd.charAt(10) == 3) {
                                        cmssrType = "자문위원";
                                    }
                                    else {
                                        cmssrType = "전문위원";
                                    }

                                    $(".memberDetailsPopup #cmssrType").text(cmssrType);
                                    $(".memberDetailsPopup #name").text(rtnData.name);
                                    $(".memberDetailsPopup #email").text(rtnData.email);
                                    $(".memberDetailsPopup #cmssrCbsnCdNm span").text(rtnData.cmssrCbsnCdNm);
                                    $(".memberDetailsPopup #cmssrMjrCarerCntn").text(rtnData.cmssrMjrCarerCntn);
                                    $(".memberDetailsPopup #cmssrCnstgFldCntn").text(rtnData.cmssrCnstgFldCntn);
                                    // $(".memberDetailsPopup #goQa span").text("/foundation/cs/qa/index?inqSec=" + rtnData.cmssrCbsnCdNm);
                                    $(".memberDetailsPopup #goQa").attr("href", "/foundation/cs/qa/index?inqSec=" + rtnData.cmssrCbsnCdNm);

                                    $(".memberDetailsPopup").css("display", "block");
                                    $(".dimd").css("display", "block");
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

            btnClose : {
                event : {
                    click : function() {
                        $(".memberDetailsPopup").css("display", "none");
                        $(".dimd").css("display", "none");
                    }
                }
            },

        },
        immediately : function() {

        }
    };


    ctrl.exec();

    return ctrl;
});

