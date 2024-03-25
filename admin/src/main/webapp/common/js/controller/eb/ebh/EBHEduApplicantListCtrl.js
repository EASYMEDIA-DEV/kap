define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebh/EBHEduApplicantListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);



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
        }, "/mngwserc/eb/ebh/select", $formObj, "GET", "html");
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

        return true;
    };


    var callbackAjaxCdList  = function (cdList){

        // console.log(cdList);

        var detailList = cdList.detailList;

        ctrl.obj.find("#cdListContainer").html(cdList);

        $(".detailCdList").html(tempHtml);


    }


    // set model
    ctrl.model = {
        id : {
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
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
            },
            //검색 레이어에서 선택시 호출
            btnCouseSrchLayerChoice:{
                event : {
                    click: function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.eb.eba.notSrchPartsCouse"));
                        }else{
                            var clickObj = {};
                            clickObj.edctnSeq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var trObj = ctrl.obj.find("input[name=delValueList]:checked").parents("tr");

                            if(trObj.length >1){
                                var trObjList = new Array();
                                trObj.each(function(){
                                    var tempObj = {};
                                    var edctnSeq = $(this).find("td").eq(0).find("input[name=delValueList]:checked").val();
                                    var ctgryCd= $(this).find("td").eq(2).data("ctgrycd");//과정분류코드
                                    var ctgryCdNm= $(this).find("td").eq(2).text().trim();//과정분류
                                    var nm= $(this).find("td").eq(3).text().trim();//과정명
                                    var stduyMthd= $(this).find("td").eq(4).text().trim();//학습방식
                                    var jdgmtYn= $(this).find("td").eq(4).data("jdgmtyn");//평가여부
                                    var stduyDtm = $(this).find("td").eq(5).text().trim();//학습시간

                                    tempObj.edctnSeq = edctnSeq;
                                    tempObj.ctgryCd = ctgryCd;
                                    tempObj.ctgryCdNm = ctgryCdNm;
                                    tempObj.nm = nm;
                                    tempObj.stduyMthd = stduyMthd;
                                    tempObj.jdgmtYn = jdgmtYn;

                                    tempObj.stduyDtm = stduyDtm;
                                    tempObj.choiceCnt = choiceCnt;

                                    trObjList.push(tempObj)

                                });
                                clickObj.trObjList = trObjList;

                            }else{
                                var edctnSeq =trObj.find("td").eq(0).find("input[name=delValueList]:checked").val();
                                var ctgryCd= trObj.find("td").eq(2).data("ctgrycd");//과정분류코드
                                var ctgryCdNm= trObj.find("td").eq(2).text().trim();//과정분류
                                var nm= trObj.find("td").eq(3).text().trim();//과정명
                                var stduyMthd= trObj.find("td").eq(4).text().trim();//학습방식
                                var stduyMthdCd= trObj.find("td").eq(4).data("stduymthdcd");//학습방식코드
                                var jdgmtYn= trObj.find("td").eq(4).data("jdgmtyn");//평가여부
                                var stduyDtm = trObj.find("td").eq(5).text().trim();//학습시간

                                clickObj.edctnSeq = edctnSeq;
                                clickObj.ctgryCd = ctgryCd;
                                clickObj.ctgryCdNm = ctgryCdNm;
                                clickObj.nm = nm;
                                clickObj.stduyMthd = stduyMthd;
                                clickObj.stduyMthdCd = stduyMthdCd;
                                clickObj.jdgmtYn = jdgmtYn;

                                clickObj.stduyDtm = stduyDtm;
                                clickObj.choiceCnt = choiceCnt;
                            }

                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            },
            btnStts : {
                event : {
                    click : function() {
                        // 선발/미선발 메시지 분류
                        var sttsVal = $(this).data("sttsVal");
                        var alrMsg = sttsVal == "Y" ? "선발할 게시물을 선택해주세요." : "미선발할 게시물을 선택해주세요.";

                        if(isChecked(alrMsg)) {
                            // 체크된 체크박스의 data-stts-cd 값들을 배열로 추출
                            var checkedValues = $("input:checkbox[name='delValueList']:checked").map(function() {
                                return $(this).data('stts-cd');
                            }).get();

                            // 가져온 배열의 값들 중 '02'라는 단어가 포함된 단어가 있는지 확인 (신청취소 데이터)
                            var containsValue02 = checkedValues.some(function(value) {
                                return value.includes('02');
                            });

                            // 신청취소 확인
                            if(containsValue02) {
                                alert("교육 신청을 취소한 회원이 존재합니다.");
                                return false;
                            }

                            // 가져온 배열의 값들 중 '04'라는 단어가 포함되지 않은 단어가 있는지 확인 (선발대기가 아닌 데이터)
                            var containsNotValue = checkedValues.some(function(value) {
                                return !value.includes('04');
                            });

                            // 선발대기여부 확인
                            if(containsNotValue) {
                                alert("선발대기 상태의 회원만 선택 가능합니다.");
                                return false;
                            }

                            // 선발/미선발 메시지 분류
                            var conMsg = sttsVal == "Y" ? "선택한 회원을 선발하시겠습니까?" : "선택한 회원을 미선발하시겠습니까?";

                            if(confirm(conMsg)) {
                                var delValueList = jQuery("input:checkbox[name='delValueList']:checked").map(function() {
                                    return $(this).val();
                                }).get();

                                jQuery.ajax({
                                    type : "post",
                                    url : "./stts-update",
                                    data :
                                        {
                                            "delValueList" : delValueList
                                            , "stts" : sttsVal
                                            , "_csrf": $("#csrfKey").val()
                                        },
                                    dataType : "json",
                                    success : function(r)
                                    {
                                        console.log(r);
                                        if (r.respCnt > 0)
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
                        }
                    }
                }
            }
        },
        immediately : function() {
            //리스트 조회
            cmmCtrl.setFormData($formObj);
            search();

            var searchInput = $("#btnSearch").closest('fieldset').find('input:text');
            searchInput.on('keypress', function(e){
                if (e.keyCode == 13){
                    $formObj.find("#btnSearch").click();
                }
            })

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});