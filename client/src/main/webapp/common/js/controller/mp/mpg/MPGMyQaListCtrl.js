define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpg/MPGMyQaListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //function
    function makeList(listData) {
        if(listData.list.length > 0) {
            var prntObj = $("#dataList");
            var codeName = "";
            var statusType = "";
            var value = "";

            var dateStringReg = "";
            var dateStringMod = "";
            var options = { year: 'numeric', month: '2-digit', day: '2-digit' };
            var originalDateReg;
            var originalDateMod;
            var formattedDateReg;
            var formattedDateMod;

            listData.list.forEach(function (data, index) {
                if(data.rsumeCd == 'SYN') {
                    codeName = "접수대기";
                    statusType = "";
                }
                else if(data.rsumeCd == 'SYNACK') {
                    codeName = "접수완료";
                    statusType = "waiting";
                }
                else if(data.rsumeCd == 'ACK') {
                    codeName = "답변완료";
                    statusType = "complete";
                }

                dateStringReg = data.regDtm;
                originalDateReg = new Date(dateStringReg);
                formattedDateReg = new Intl.DateTimeFormat('ko-KR', options).format(originalDateReg)
                if(data.modDtm) {
                    dateStringMod = data.modDtm;
                    originalDateMod = new Date(dateStringMod);
                    formattedDateMod = new Intl.DateTimeFormat('ko-KR', options).format(originalDateMod)
                }

                value +=
                    "<tr>" +
                    "    <td class=\"t-align-center\">" + (listData.totalCount - listData.firstIndex - index) + "</td>" +
                    "    <td class=\"t-align-center\">" + data.parntCtgryNm + " > " + data.ctgryNm + "</td>" +
                    "    <td><p class=\"txt-ellipsis\"><a href=\"javascript:\" title=\"링크 이동\" class=\"listView\" data-details-key=\"" + data.qaSeq + "\" data-mem-seq=\"" + data.memSeq + "\" data-rsume-cd=\"" + data.rsumeCd + "\">" + data.titl + "</a></td>" +
                    "    <td class=\"t-align-center\">" + formattedDateReg + "</td>" +
                    "    <td class=\"t-align-center\"><p class=\"box-label bigger " + statusType + "\"><span>" + codeName + "</span></p></td>" +
                    "    <td class=\"t-align-center\">" + (data.modDtm ? formattedDateMod : "-") + "</td>" +
                    "</tr>";
                prntObj.append(value);
                if(listData.pageIndex * listData.listRowSize < listData.totalCount) {
                    $("#divMore").css("display", "flex");
                    $("#pagingVal").text("(" + listData.pageIndex * listData.listRowSize + "/" + listData.totalCount + ")");
                }
                else {
                    $("#divMore").css("display", "none");
                }
                value="";
            });
        }

        $("#pageIndex").val(listData.pageIndex);
    }


    // set model
    ctrl.model = {
        id : {

            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        location.href = "/my-page/member/qa/list?searchText=" + $("#searchText").val();
                    }
                }
            },

            btnMore : {
                event : {
                    click : function() {
                        var pageIndex = Number($("#pageIndex").val());

                        $.ajax({
                            type: "post",
                            url: './listAjax',
                            dataType: "json",
                            data: {
                                "pageIndex" : pageIndex + 1,
                            },
                            success: function (rtnData) {
                                if(rtnData.list.length > 0) {
                                    makeList(rtnData);
                                }
                                else {
                                    alert("더 이상의 문의 내역이 존재하지 않습니다.");
                                }
                            },
                            error: function (xhr, ajaxSettings, thrownError) {
                                alert("조회에 실패했습니다.");
                            }
                        });
                    }
                }
            },

        },
        classname : {

            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        var memSeq = $(this).data("memSeq");
                        var rsumeCd = $(this).data("rsumeCd");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        $formObj.find("input[name=memSeq]").val(memSeq);
                        $formObj.find("input[name=rsumeCd]").val(rsumeCd);
                        location.href = "./select?" + $formObj.serialize();
                    }
                }
            },
            //페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                    }
                }
            }
        },
        immediately : function() {

        }
    };


    ctrl.exec();

    return ctrl;
});

