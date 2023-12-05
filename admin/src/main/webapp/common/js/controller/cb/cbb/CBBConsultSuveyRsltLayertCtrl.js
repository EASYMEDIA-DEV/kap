define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/cb/cbb/CBBConsultSuveyRsltLayertCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down11");
    var langCd = "kr";
    var gubun = $("#gubun").val();
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
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);

            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "./selectSuveyRslt", $formObj, "GET", "html");
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
        },
        classname : {
            // 페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
                        }
                    }
                }
            },
            // 상세보기
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
            // 페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            },
            //검색 초기화
            btnRefresh : {
                event : {
                    click : function() {
                        //FORM 데이터 전체 삭제
                        var pageIndex 	= $formObj.find("#pageIndex").val();
                        var listRowSize = $formObj.find("#listRowSize").val();
                        var pageRowSize = $formObj.find("#pageRowSize").val();
                        var csrfKey 	= $formObj.find("#csrfKey").val();
                        var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
                        $formObj.clearForm();
                        //FORM 전송 필수 데이터 삽입
                        $formObj.find("#pageIndex").val( pageIndex );
                        $formObj.find("#listRowSize").val( listRowSize );
                        $formObj.find(".listRowSizeContainer").val( listRowSize );
                        $formObj.find("#pageRowSize").val( pageRowSize );
                        $formObj.find("#csrfKey").val( csrfKey );
                        $formObj.find("input[name=srchLayer]").val( srchLayer );

                        //캘린더 초기화
                        cmmCtrl.setPeriod(this, "", "", false);

                        //검색 로직 실행
                        $formObj.find("#btnSearch").click();
                    }
                }
            },
            //엑셀다운로드
            btnExcelDown : {
                event: {
                    click: function () {
                        console.log($(this).data("bsnyear"));
                        //사유입력 레이어팝업 활성화
                        /*$excelObj.find("#rsn").val('');
                        $excelObj.modal("show");*/


                        var excelDownYearChk = $(this).data("bsnyear");

                        var rsn = excelDownYearChk + "년 만족도 종합결과 상세 다운";
                        var frmDataObj  = $formObj.closest("form");

                        frmDataObj.find("input[name='rsn']").remove();
                        frmDataObj.find("input[name='excelBsnYear']").remove();

                        if (rsn != "") {
                            frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));
                            frmDataObj.append($('<input/>', { type: 'hidden',  name: 'excelBsnYear', value: excelDownYearChk, class: 'notRequired' }));

                            //파라미터를 물고 가야함.
                            location.href = "./srvRsltexcel-down?" + frmDataObj.serialize();

                        } else {
                            alert(msgCtrl.getMsg("fail.reason"));
                            return;
                        }
                    }
                }
            },
        },
        immediately : function() {
            // 리스트 조회
            cmmCtrl.setFormData($formObj);

            search($formObj.find("input[name=pageIndex]").val());

            /*$excelObj.find("button.down").on('click', function(){
                var rsn = $excelObj.find("#rsn").val().trim();
                var frmDataObj    = $formObj.closest("form");

                frmDataObj.find("input[name='rsn']").remove();

                if (rsn != "") {
                    frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

                    //파라미터를 물고 가야함.
                    location.href = "./excel-down?" + frmDataObj.serialize();

                } else {
                    alert(msgCtrl.getMsg("fail.reason"));
                    return;
                }

                //사유입력 레이어팝업 비활성화
                $excelObj.modal("hide");
            });*/
        }
    };

    ctrl.exec();

    return ctrl;
});