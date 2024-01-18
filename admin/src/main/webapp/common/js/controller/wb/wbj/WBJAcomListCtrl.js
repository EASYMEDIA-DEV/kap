define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJAcomListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excelDown");


    //목록 조회
    var search = function(page){
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
        }, "/mngwserc/wb/wbjb/select", $formObj, "POST", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                },
            },
            inqFir : {
                event : {
                    change : function() {
                        var inqFir = document.getElementById('inqFir');
                        var selectFir = inqFir.options[inqFir.selectedIndex].value;
                        var inqSecOpsions = document.getElementsByClassName("inqSec");
                        var addOption;

                        for(var i = 0, max = inqSecOpsions.length; i < max; i++) {
                            addOption = inqSecOpsions.item(i);
                            if(addOption.value.indexOf(selectFir) > -1 && addOption.value.length > 5) {
                                addOption.style.display = "block";
                            }
                            else {
                                addOption.style.display = "none";
                            }
                        }
                    }
                }
            },
            //엑셀다운로드
            btnExcelDown : {
                event : {
                    click: function () {
                        $.fileDownload("./excelDown?" + frmDataObj.serialize() , {
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
                    }
                }
            },
            btnChooseDelete : {
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
                                    var respCnt = respObj.respCnt;

                                    if(respCnt >= 1){
                                        alert("접수 이후의 신청 건은 삭제가 불가합니다.");
                                    }else{
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
                                }, "./getCnt", frmDataObj, "POST", "json");
                            }
                        } else {
                            alert("삭제대상을 선택해주세요.");
                        }

                    }
                }
            },
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
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
                        var bsnmNo = $(this).attr('value');
                        var year = $(this).data('year');
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        $formObj.find("input[name=workBsnmNo]").val(bsnmNo);
                        $formObj.find("input[name=year]").val(year);
                        location.href = "./edit?" + $formObj.serialize();
                    }
                }
            },
            //페이징 목록 갯수
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

            $excelObj.find("buttonDown").on('click', function(){
                var rsn = $excelObj.find("#rsn").val().trim();
                var frmDataObj    = $formObj.closest("form");

                frmDataObj.find("input[name='rsn']").remove();

                if (rsn != "") {
                    frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

                    //파라미터를 물고 가야함.
                    location.href = "./excelDown?" + frmDataObj.serialize();

                } else {
                    alert(msgCtrl.getMsg("fail.reason"));
                    return;
                }

            });
        }
    };


    ctrl.exec();

    return ctrl;
});

