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
    var $excelObj = ctrl.obj.parent().find(".excel-down");

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