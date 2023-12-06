define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/eb/ebc/EBCVisitEduListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    //소재지역 분류 조회
    var selectCtgryCdList = function(arg){

        if(arg === undefined){
            arg = $("#scndRgnsCd").data("scndRgnsCd");
        }
        var cdMst= {};
        cdMst.cd = $(arg).val();

        cmmCtrl.jsonAjax(function(data){
            callbackAjaxCtgryCdList(data);
        }, './classTypeList', cdMst, "text");

    }


    //소재지역 분류 2뎁스 세팅
    var callbackAjaxCtgryCdList = function(data){

        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>전체</option>";

        for(var i =0; i < detailList.length; i++){

            var cd = detailList[i].cd;
            var cdNm = detailList[i].cdNm;

            selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
        }
        console.log(selectHtml);

        $("#scndRgnsCd option").remove();
        $("#scndRgnsCd").append(selectHtml);
        console.log(selectHtml);
        var scndRgnsCd = $("#scndRgnsCd").data("scndRgnsCd");
        $("#scndRgnsCd").val(scndRgnsCd).prop("selected", true);
    }

    // set model
    ctrl.model = {
        id : {
            btnSearch : {
                event : {
                    click : function() {
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
            listRowSize : {
                event : {
                    change : function(){
                        search(1);
                    }
                }
            },
            btn_delete : {
                event: {
                    click: function () {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        if (delActCnt !== 0) {
                            if(confirm("선택한 게시물을 " + msgCtrl.getMsg("confirm.del")))
                            {
                                //삭제 전송
                                cmmCtrl.frmAjax(function(respObj){
                                    if(respObj != undefined && respObj.respCnt > 0){
                                        var msg = "삭제되었습니다.";

                                        alert(msg);
                                        $formObj.find("#btnSearch").click();
                                    }
                                    else{
                                        alert(msgCtrl.getMsg("fail.act"));
                                    }
                                }, "./delete", frmDataObj, "POST", "json");
                            }
                        } else {
                            alert(msgCtrl.getMsg("fail.sm.smc.target"));
                        }
                    }
                }
            }
        },
        classname : {
            pageSet : {
                event : {
                    click : function() {
                        if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        $formObj.find("input[name=vstSeq]").val($(this).data("detailsKey"));
                        $formObj.find("input[name=memSeq]").val($(this).data("memSeq"));
                        $formObj.find("input[name=vstRsltSeq]").val($(this).data("vstRsltSeq"));
                        $formObj.find("input[name=appctnBsnmNo]").val($(this).data("appctnBsnmNo"));

                        location.href = "./write?" + $formObj.serialize();
                    }
                }
            },
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            },
            classType : {
                event : {
                    change : function() {
                        selectCtgryCdList(this);
                    }
                }
            }
        },
        immediately : function() {
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);

            search($formObj.find("input[name=pageIndex]").val());

        }
    };

    //목록 조회
    var search = function(page){
        //data로 치환해주어야한다.
        //cmmCtrl.setFormData($formObj);
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");

            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "./select", $formObj, "POST", "html");
    }


    ctrl.exec();

    return ctrl;
});