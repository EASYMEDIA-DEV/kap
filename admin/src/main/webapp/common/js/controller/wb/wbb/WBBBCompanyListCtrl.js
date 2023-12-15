define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbb/WBBBCompanyListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

    // set model
    ctrl.model = {
        id : {
            optYear : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($formObj);
                        selEpisdList();
                    },
                }
            },
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
                        location.href = "./excel-down?" + $formObj.serialize();
                    }
                }
            },
            deleteBtn : {
                event: {
                    click: function () {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;

                        if (delActCnt > 0)
                        {
                            if(confirm("삭제 처리하겠습니끼?"))
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
                            alert("삭제대상을 선택해주세요.");
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
                        var detailsKey = $(this).data("detailsKey");
                        var bsnmNo = $(this).data("bsnmNo");
                        var memSeq = $(this).data("memSeq");
                        
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        $formObj.find("input[name=bsnmNo]").val(bsnmNo);
                        $formObj.find("input[name=memSeq]").val(memSeq);
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

    /* 회차 검색 Ajax */
    let selEpisdList = () => {
        let target = $formObj.find('#optYear');

        cmmCtrl.frmAjax(function(respObj) {
            /!* return data input *!/
            let html = '<option value="">회차 전체</option>';
            respObj.forEach((el) => {
                html += '<option value="'+el.episd+'">'+el.episd+'</option>';
            });

            $('#optEpisd').empty().append(html);
        }, "./getEplisds", $formObj, "post", "json")

    }

    ctrl.exec();

    return ctrl;
});