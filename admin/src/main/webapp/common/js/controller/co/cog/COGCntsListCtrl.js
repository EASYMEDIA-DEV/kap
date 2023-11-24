define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/co/cog/COGCntsListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    var menuSeq = $("#menuSeq").val();
    //목록 조회
    var search = function(page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {

            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/contentsid/"+menuSeq+"/select", $formObj, "POST", "html");
    }

    var isChecked = function (msg)
    {
        var chkCnt = jQuery("input:checkbox[name='delValueList']:checked").length;

        if (chkCnt < 1)
        {
            if(msg) {
                alert(msg);
            }
            else {
                alert("게시물을 선택해 주세요.");
            }
            return false;
        }
        else if (chkCnt > 1)
        {
            alert("게시물을 1개 이상 선택할 수 없습니다.");
            return false;
        }

        return true;
    };

    var setProcess = function (option)
    {
        if (confirm(option.confirm))
        {
            var detailsKey = jQuery("input:checkbox[name='delValueList']:checked").val();

            jQuery.ajax({
                type : "post",
                url : "./state-check",
                data :
                    {
                        "detailsKey" : detailsKey
                        , "_csrf": $("#csrfKey").val()
                    },
                dataType : "json",
                success : function(r)
                {
                    var prcsCd = r.prcsCd;

                    if (prcsCd && prcsCd == option.prcsCd)
                    {
                        jQuery.ajax({
                            type : "post",
                            url : "./aprvl-update",
                            data :
                                {
                                    "detailsKey" : detailsKey
                                    , "_csrf": $("#csrfKey").val()
                                },
                            dataType : "json",
                            success : function(r)
                            {
                                var status = r.status;

                                if (status == "Y")
                                {
                                    alert("처리되었습니다.");
                                    location.reload();
                                }
                                else
                                {
                                    alert("잘못된 접근입니다.");
                                }
                            },
                            error : function(xhr, ajaxSettings, thrownError)
                            {
                                alert("잠시후 다시 시도 바랍니다.");
                            }
                        });
                    }
                    else
                    {
                        alert(option.prcsMsg);
                    }
                },
                error : function(xhr, ajaxSettings, thrownError)
                {
                    alert("잠시후 다시 시도 바랍니다.");
                }
            });
        }
    };

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        search(1);
                    }
                }
            },
            //즉시배포 버튼
            btn_immediately : {
                event : {
                    click : function() {
                        if (isChecked("즉시배포할 게시물을 선택해주세요"))
                        {
                            setProcess({
                                "confirm" : "선택한 게시물을 즉시배포 하시겠습니까?",
                                "prcsCd" : "30",
                                "prcsMsg" : "작성중 상태가 아닌 게시물은 즉시배포가 불가합니다"
                            });
                        }
                    }
                }
            },
            //되돌리기 버튼
            btn_back : {
                event : {
                    click : function() {
                        if (isChecked("되돌리기할 게시물을 선택해주세요"))
                        {
                            setProcess({
                                "confirm" : "선택한 게시물을 되돌리기 하시겠습니까?",
                                "prcsCd" : "20",
                                "prcsMsg" : "만료 상태가 아닌 게시물은 되돌리기가 불가합니다"
                            });
                        }
                    }
                }
            },
            //복사 버튼
            btn_copy : {
                event : {
                    click : function() {
                        if (isChecked("복사할 게시물을 선택해주세요"))
                        {
                            if (confirm("선택한 게시물을 복사하시겠습니까?"))
                            {
                                var detailsKey = jQuery("input:checkbox[name='delValueList']:checked").val();

                                jQuery.ajax({
                                    type: "post",
                                    url: "./copy",
                                    data:
                                        {
                                            "detailsKey": detailsKey
                                            , "_csrf": $("#csrfKey").val()
                                        },
                                    dataType: "json",
                                    success: function (r) {
                                        alert(r.msg);
                                        location.reload();
                                    }
                                });
                            }
                        }
                    }
                }
            },
            //데이터 삭제
            delete : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delList = frmDataObj.find("input:checkbox[name='delValueList']:checked");
                        if (delActCnt > 0)
                        {
                            var isOk = true;

                            delList.each(function(){
                                var prcsCd = $(this).data('prcsCd');

                                if(prcsCd == '10') {
                                    alert(msgCtrl.getMsg("fail.co.cog.remove"))

                                    isOk = false;
                                }
                            });

                            if(isOk){
                                if(confirm("선택한 게시물을 삭제하시겠습니까?"))
                                {
                                    //삭제 전송
                                    cmmCtrl.frmAjax(function(respObj){
                                        if(respObj != undefined && respObj.respCnt > 0){
                                            alert(msgCtrl.getMsg("success.del.target.board"));
                                            $formObj.find("#btnSearch").click();
                                        }
                                        else{
                                            alert(msgCtrl.getMsg("fail.act"));
                                        }
                                    }, "./delete", frmDataObj, "POST", "json");
                                }
                            }
                        }
                        else
                        {
                            alert(msgCtrl.getMsg("fail.targetBoard"));

                            return;
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
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
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
            //리스트 조회
            search();
        }
    };


    ctrl.exec();

    return ctrl;
});

