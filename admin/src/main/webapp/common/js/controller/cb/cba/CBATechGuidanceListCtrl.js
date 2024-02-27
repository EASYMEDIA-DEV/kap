define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/cb/cba/CBATechGuidanceListCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");
    var langCd = "kr";
    var gubun = $("#gubun").val();

    //설문 개수 조회
    var svCntCheck = function () {
        var srvSeq = $("#srvSeq").val();

        if(!srvSeq) {
            return false;
        }

        var svMst = {};
        svMst.srvSeq = srvSeq;
        svMst.cnstgSeq = $("#detailsKey").val();

        cmmCtrl.jsonAjax(function(data){
            var rtn = JSON.parse(data);

            // alert(rtn.respCnt);
            if(rtn.respCnt == 0 ){
                $(".srvSearch").attr("disabled", false); //만족도조사 추가버튼 사용 가능
                $("#srvStrtDtm, #srvEndDtm").attr("disabled", false); //설문, 시험 달력 사용 가능
            }
            else {
                $(".srvSearch").attr("disabled", true);
                $("#srvStrtDtm, #srvEndDtm").attr("disabled", true);
            }
        }, './checkSurveyCnt', svMst, "text")
    }

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
        }, "./select", $formObj, "POST", "html");
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
            btnRefresh : {
                event : {
                    click : function(){
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
            listRowSize : {
                event : {
                    change : function(){
                        search(1);
                    }
                }
            },//데이터 삭제
            btnDeleteConsult : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delType = frmDataObj.data("delType");

                        if (delActCnt > 0)
                        {
                            // 계정은 최고 관리자 및 본인 계정은 삭제 불가
                            if(confirm(msgCtrl.getMsg("confirm.del")))
                            {
                                $('input:checkbox[name=delValueList]').each(function (index) {
                                    if($(this).is(":checked")==true){
                                        var rsumeCd = $(this).data("rsumecd");
                                        if(rsumeCd == "MNGTECH_STATUS_01") {
                                            //삭제 전송
                                            cmmCtrl.frmAjax(function(respObj){
                                                if(respObj != undefined && respObj.respCnt > 0){
                                                    var msg = msgCtrl.getMsg("success.del.target.none");
                                                    if(typeof delType!= "undefined" && typeof msgCtrl.getMsg("success.del.target." + delType) != "undefined"){
                                                        msg = msgCtrl.getMsg("success.del.target." + delType);
                                                    }
                                                    alert(msg);
                                                    $formObj.find("#btnSearch").click();
                                                }
                                                else{
                                                    alert(msgCtrl.getMsg("fail.act"));
                                                }
                                            }, "./delete", frmDataObj, "POST", "json");
                                        }else{
                                            alert("접수 이후의 신청 건은 삭제가 불가합니다");
                                            return false;
                                        }
                                    }
                                })
                            }
                        }
                        else
                        {
                            if(typeof delType!= "undefined")
                            {
                                alert(msgCtrl.getMsg("fail.del.target." + frmDataObj.data("delType")));
                            }
                            else
                            {
                                alert(msgCtrl.getMsg("fail.targetBoard"));
                            }
                            return;
                        }
                    }
                }
            },
            //엑셀다운로드
            btnExcelDown : {
                event: {
                    click: function () {
                        //사유입력 레이어팝업 활성화
                        $excelObj.find("#rsn").val('');
                        $excelObj.modal("show");
                    }
                }
            },
            //만족도 종합결과 레이어 관련
            btnSuveyRltPop : {
                event: {
                    click: function () {
                        cmmCtrl.getConsultSuveyRsltPop(function(data){
                        });
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
                        var _csrf = $("#csrfKey").val();
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        // location.href = "./write?" + $formObj.serialize();
                        location.href = "./write?detailsKey=" + detailsKey + "&_csrf=" + _csrf;
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
            //검색 레이어에서 선택시 호출
            btnExamSrchLayerChoice:{
                event : {
                    click: function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if( choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.ex.notSrchExam1"));
                        } else if(choiceCnt == 0){
                            alert(msgCtrl.getMsg("fail.ex.notSrchExam"));
                        }else{
                            var clickObj = {};
                            clickObj.seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text());
                            clickObj.titl = titl;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            }
        },
        immediately : function() {

            //대시보드에서 접근 시 검색 필터 설정
            var dashType= $("#dashBoardType").val();

            if(!(dashType === undefined)){
                //접수대기
                if(dashType == "A"){
                    $("select[name='rsumeSttsCd'] option:eq(1)").prop("selected", true);
                    //접수완료
                }else if(dashType == "B"){
                    $("select[name='rsumeSttsCd'] option:eq(9)").prop("selected", true);
                }
            }

            $('#btnSearch').trigger("click");

            // 리스트 조회
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

    ctrl.exec();

    return ctrl;
});