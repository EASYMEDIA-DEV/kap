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
            btnCopy : {
                event :{
                    click : function() {


                        var valList = jQuery("input[name='delValueList']:checked");

                        //여러개 체크 할 경우
                        if(valList.length>1){
                            alert("복사는 1개의 게시물만 가능합니다.");
                            return false;
                            //과정이 없는경우
                        }else if(valList.length == 0){
                            alert("복사할 과정을 선택해주세요.");
                            return false;
                        }
                        //복사
                        $("#copyYn").val("Y");
                        valList.closest("tr").find("td:eq(2) > a").trigger("click")
                    }
                }
            },
            btnEdDelete : {
                event : {
                    click : function() {

                        var $this = this;

                        var delList = new Array();
                        $("#listContainer").find("input:checkbox[name='delValueList']:checked").each(function(){
                            delList.push($(this).val());
                        })
                        var seqObj = {};
                        seqObj['edctnSeqList'] = delList;
                        cmmCtrl.jsonAjax(function(data){
                            var rtn = JSON.parse(data);
                            if(rtn.respCnt>0){
                                console.log('회차가 등록된 게시물이 존재하여 삭제 할 수 없습니다.');
                            }else{
                                console.log('등록된 회차 없음');
                                delCallback($this);
                            }


                        }, "./deleteChk", seqObj, "text");

                        //delCallback(this);


                    }
                }
            },


        },
        classname : {
            classType : {
                event : {
                    click : function() {

                        $(".cdListContainer").css("display","none");
                        $(".cdListContainer").attr("disabled", true);
                        $(".cdListContainer").find("input:checkbox").prop("checked", false);


                        $(".classType input:checked").each(function(){
                            // console.log($(this).val());

                            var checkVal = $(this).val();

                            var cdnm = $(this).data("cdnm"); //내일 이거 해야됨 클릭한것의 cdnm값 갖고오기
                            $("."+checkVal).find(".cdnm").html(cdnm);
                            $("."+checkVal).css("display","block");

                            $("."+checkVal).find("input:checkbox").attr("disabled", false);
                            // console.log(cdnm);
                            $("."+checkVal).find("input:checkbox").find("span").append(cdnm+"23434");


                        });

                        if($(".classType input:checked").length == 0){
                            $(".cdListContainer").css("display","none");
                            $(".cdListContainer").attr("disabled", true);
                            $(".cdListContainer").find("input:checkbox").prop("checked", false);
                        }

                    }
                }
            },
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
            }
        },
        immediately : function() {
            //리스트 조회
            // cmmCtrl.setFormData($formObj);
            search();
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});