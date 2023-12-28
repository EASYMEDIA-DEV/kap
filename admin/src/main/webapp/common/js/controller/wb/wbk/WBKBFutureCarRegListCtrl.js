define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbk/WBKBFutureCarRegListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

    var callbackAjaxDelete = function(data){
        console.log("콜백 진입 data" + data)
        if (parseInt(data.respCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.del.target.none"));
            //location.href = "./list";
        }
        else
        {
            alert(msgCtrl.getMsg("fail.ing"));
        }
    };

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
            },
            btn_delete : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $("input[name='delValueList']:checked").map(function(){
                            return $(this).val();
                        }).get();
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        if (confirm(msgCtrl.getMsg("confirm.del"))) {
                            cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                        }
                    }
                }
            },
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
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
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
            }
        },
        immediately : function() {
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);

            search($formObj.find("input[name=pageIndex]").val());

            $excelObj.find("button.down").on('click', function(){
                var rsn = $excelObj.find("#rsn").val().trim();
                var frmDataObj    = $formObj.closest("form");

                frmDataObj.find("input[name='rsn']").remove();

                if (rsn != "") {
                    frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

                    //파라미터를 물고 가야함.
                    location.href = "./excel-down?" + frmDataObj.serialize();

                    $excelObj.modal("hide");
                } else {
                    alert(msgCtrl.getMsg("fail.reason"));
                    return;
                }

            });
        }
    };

    //목록 조회
    var search = function (page){
        //data로 치환해주어야한다.
        //cmmCtrl.setFormData($formObj);

        console.log($formObj);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            console.log(respObj)
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