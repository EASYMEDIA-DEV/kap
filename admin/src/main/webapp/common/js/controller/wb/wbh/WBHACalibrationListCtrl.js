define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbh/WBHACalibrationListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    /*var $examObj = $(".part-modal");*/

    var $examObj = ctrl.obj.parent().find(".excel-down");

    //목록 조회
    // 목록 조회
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
        }, "/mngwserc/wbh/wbha/select", $formObj, "POST", "html");

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
                }
            },
            listRowSize : {
                event : {
                    change : function(){
                        search(1);
                    }
                }
            },
            btnDeleteList : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delType = frmDataObj.data("delType");
                        if (delActCnt > 0)
                        {
                            //삭제 전송
                            cmmCtrl.frmAjax(function(respObj){
                                if(respObj != undefined && respObj.respCnt > 0){
                                    alert("접수 이후의 신청 건은 삭제가 불가합니다.");
                                }else{
                                    if(confirm('삭제하시겠습니까?'))
                                    {
                                        //삭제 전송
                                        cmmCtrl.frmAjax(function(respObj){
                                            if(respObj != undefined && respObj.respCnt > 0){
                                                alert('게시물이 삭제되었습니다.');
                                                $formObj.find("#btnSearch").click();
                                            }
                                            else{
                                                alert(msgCtrl.getMsg("fail.act"));
                                            }
                                        }, "./deleteList", frmDataObj, "POST", "json");
                                    }
                                }

                            }, "./getRsumePbsnCnt", frmDataObj, "POST", "json");
                        }
                        else
                        {
                            alert('삭제할 게시물을 선택해주세요.');

                            return;
                        }
                    }
                }
            },
            //엑셀다운로드
            btnExcelDown : {
                event : {
                    click: function () {
                        //파라미터를 물고 가야함.
                        location.href = "./excel-down?" + $formObj.serialize();
                    }
                }
            },
            //사업관리
            btnExamList : {
                event : {
                    click: function () {
                        //파라미터를 물고 가야함.
                        $examObj.modal("show");
                    }
                }
            },
            validSubmit : {
                event : {
                    click: function () {
                        var wBGAValidDTO = {};
                        wBGAValidDTO.validSeq = ctrl.obj.find("#validSeq").val();
                        wBGAValidDTO.stndSlsPmt = ctrl.obj.find("#stndSlsPmt").val();
                        wBGAValidDTO.dtlList = new Array();

                        $('input:checkbox[name=optnCdList]').each(function (index) {
                            if($(this).is(":checked")==true){
                                var wBGAValidDtlDTO = {};
                                wBGAValidDtlDTO.optnCd = $(this).val()
                                wBGAValidDTO.dtlList.push(wBGAValidDtlDTO);
                            }
                        });

                        cmmCtrl.jsonAjax(function(data){
                            alert("수정이 완료되었습니다.");
                            $examObj.modal("hide");
                        }, "/mngwserc/wbh/wbha/validUpdate", wBGAValidDTO, "text");
                    }
                }
            }
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
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
                        var appctnSeq = $(this).data("appctnSeq");
                        var bsnmNo = $(this).data("bsnmNo");
                        var memSeq = $(this).data("memSeq");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        $formObj.find("input[name=appctnSeq]").val(appctnSeq);
                        $formObj.find("input[name=bsnmNo]").val(bsnmNo);
                        $formObj.find("input[name=memSeq]").val(memSeq);
                        location.href = "./write?" + $formObj.serialize();
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
            var searchInput = $("#btnSearch").closest('fieldset').find('input:text');
            searchInput.on('keypress', function(e){
                if (e.keyCode == 13){
                    $formObj.find("#btnSearch").click();
                }
            })

            //리스트 조회
            search(1);
        }
    };


    ctrl.exec();

    return ctrl;
});

