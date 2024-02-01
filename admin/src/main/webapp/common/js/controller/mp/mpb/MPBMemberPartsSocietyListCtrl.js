define(["ezCtrl"], function(ezCtrl) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBMemberPartsSocietyListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

    let popup;
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
        }, "/mngwserc/mp/mpb/select", $formObj, "POST", "html");

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
            btnMpbRefresh : {
                event : {
                    click : function(){
                        //FORM 데이터 전체 삭제
                        var pageIndex 	= $formObj.find("#pageIndex").val();
                        var listRowSize = $formObj.find("#listRowSize").val();
                        var pageRowSize = $formObj.find("#pageRowSize").val();
                        var csrfKey 	= $formObj.find("#csrfKey").val();
                        var srchLayer 	= $formObj.find("input[name=srchLayer]").val();
                        var workBsnmNo 	= $formObj.find("input[name=workBsnmNo]").val();

                        $formObj.clearForm();
                        //FORM 전송 필수 데이터 삽입
                        $formObj.find("#pageIndex").val( pageIndex );
                        $formObj.find("#listRowSize").val( listRowSize );
                        $formObj.find(".listRowSizeContainer").val( listRowSize );
                        $formObj.find("#pageRowSize").val( pageRowSize );
                        $formObj.find("#csrfKey").val( csrfKey );
                        $formObj.find("input[name=srchLayer]").val( srchLayer );
                        $formObj.find("input[name=workBsnmNo]").val( workBsnmNo );

                        //캘린더 초기화
                        cmmCtrl.setPeriod(this, "", "", false);

                        //검색 로직 실행
                        $formObj.find("#btnSearch").click();
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
            btnExcelDown2 : {
                event : {
                    click : function () {
                        cmmCtrl.getUserPopOpen(popup);
                    }
                }
            },
            memberChoice : {
                event : {
                    click : function(){
                        var choiceCnt = ctrl.obj.find("input[name=delValueList]:checked").size();
                        if( choiceCnt > 1){
                            alert(msgCtrl.getMsg("fail.cb.cba.notSrchMember1"));
                        } else if(choiceCnt == 0) {
                            alert(msgCtrl.getMsg("fail.cb.cba.notSrchMember"));
                        } else if($('#srchPage').val() == 'WB') {

                            var wBPartCompanyDTO= {};
                            wBPartCompanyDTO.selMemSeq = ctrl.obj.find("input[name=delValueList]:checked").val();

                            var resCnt = 0;
                            console.log(resCnt);

                            if($("#srchAppctnSeq").val() != ''){
                                wBPartCompanyDTO.appctnSeq = $("#srchAppctnSeq").val();
                                cmmCtrl.jsonAjax(function(respObj){
                                    let response = JSON.parse(respObj);
                                    resCnt = response.respCnt;
                                }, "/mngwserc/wb/partUserChk", wBPartCompanyDTO, "text");
                            }

                            console.log(resCnt);

                            if(resCnt > 0){
                                alert("이관 이력이 있는 회원은 선택이 불가합니다.");
                                return false;
                            } else {
                                var clickObj = {};
                                var memSeq = ctrl.obj.find("input[name=delValueList]:checked").val();
                                var memId = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text()); // 신청자 아이디
                                var memName = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(0)").text(); // 신청자 이름

                                var gndr = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").data("gndr")); // 신청자 성별

                                var memEmail = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(8)").text(); // 이메일
                                var cmpnNm = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(1)").text(); // 회사 이름
                                var hpNo = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(7)").text(); // 핸드폰 번호
                                var bsnmNo = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(2)").text(); // 사업자 번호
                                var telNo = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".telNo").val(); // 회원 일반 전화번호
                                var pstnCd = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".pstnCd").val(); // 직급 - select
                                var pstnCdNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".pstnCdNm").val();
                                var deptCd= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".deptCd").val();
                                var deptDtlNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".deptDtlNm").val();
                                var pstnNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".pstnNm").val();

                                clickObj.bsnmNo = bsnmNo;
                                clickObj.memSeq = memSeq;
                                clickObj.memId = memId;
                                clickObj.memName = memName;
                                clickObj.gndr = gndr;

                                clickObj.memEmail = memEmail;
                                clickObj.cmpnNm = cmpnNm;
                                clickObj.deptDtlNm = deptDtlNm;
                                clickObj.pstnCdNm = pstnCdNm;

                                clickObj.hpNo = hpNo;
                                clickObj.telNo = telNo;
                                clickObj.deptCd = deptCd;
                                if(pstnCd == 'MEM_CD01007'){
                                    $(".pstnCdInput").show();
                                    $(".pstnNm").val(pstnNm);
                                }else{
                                    $(".pstnCdInput").hide();
                                }
                                clickObj.pstnCd = pstnCd;
                                ctrl.obj.trigger("choice", [clickObj])
                                ctrl.obj.find(".close").click();
                            }
                        } else {
                            var clickObj = {};
                            var memSeq = ctrl.obj.find("input[name=delValueList]:checked").val();
                            var memId = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text()); // 신청자 아이디
                            var memName = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(0)").text(); // 신청자 이름

                            var gndr = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").data("gndr")); // 신청자 성별

                            var memEmail = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(8)").text(); // 이메일
                            var cmpnNm = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(1)").text(); // 회사 이름
                            var hpNo = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(7)").text(); // 핸드폰 번호
                            var bsnmNo = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").nextAll("td:eq(2)").text(); // 사업자 번호
                            var telNo = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".telNo").val(); // 회원 일반 전화번호
                            var pstnCd = ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".pstnCd").val(); // 직급 - select
                            var pstnCdNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".pstnCdNm").val();
                            var deptCd= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".deptCd").val();
                            var deptCdNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".deptCdNm").val();
                            var deptDtlNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".deptDtlNm").val();
                            var pstnNm= ctrl.obj.find("input[name=delValueList]:checked").parents("tr").children(".pstnNm").val();

                            $('input[name=memSeq]').val(memSeq);
                            $('input[name=name]').val(memName);
                            $('input[name=memId]').val(memId);
                            $('input[name=memInfo]').val(memName+"("+memId+")");
                            $("#deptCd").val(deptCd);
                            if(pstnCd == 'MEM_CD01007'){
                                $(".pstnCdInput").show();
                                $(".pstnNm").val(pstnNm);
                            }else{
                                $(".pstnCdInput").hide();
                            }
                            $('input[name=deptCd]').val(deptCd);
                            $('input[name=deptDtlNm]').val(deptDtlNm);
                            $('input[name=pstnCd]').val(pstnCd);
                            $('input[name=telNo]').val(telNo);
                            $("#pstnCdSelect").val(pstnCd).prop("selected", true);
                            $('p[name=email]').text(memEmail);
                            $('p[name=hpNo]').text(hpNo);
                            $('p[name=bsnmNo]').text(bsnmNo);

                            clickObj.bsnmNo = bsnmNo;
                            clickObj.memSeq = memSeq;
                            clickObj.memId = memId;
                            clickObj.memName = memName;
                            clickObj.gndr = gndr;

                            clickObj.memEmail = memEmail;
                            clickObj.cmpnNm = cmpnNm;

                            clickObj.deptCdNm = deptCdNm;//부서
                            clickObj.deptDtlNm = deptDtlNm;//부서상세
                            clickObj.pstnNm = pstnNm;//직급
                            clickObj.pstnCdNm = pstnCdNm;//직급상세

                            clickObj.hpNo = hpNo;
                            clickObj.telNo = telNo;
                            clickObj.deptCd = deptCd;
                            clickObj.pstnCd = pstnCd;
                            ctrl.obj.trigger("choice", [clickObj])
                            ctrl.obj.find(".close").click();
                        }
                    }
                }
            }
        },
        classname : {
            classType : {
                event : {
                    click : function() {

                        $(".cdListContainer").css("display","none");
                        $(".cdListContainer").attr("disabled", true);
                        $(".cdListContainer").find("input:checkbox").prop("checked", false);

                        $(".classType input:checked").each(function(){
                            var checkVal = $(this).val();

                            $("."+checkVal).css("display","block");

                            $("."+checkVal).find("input:checkbox").attr("disabled", false);


                        });

                        if($(".classType input:checked").length == 0){
                            $(".cdListContainer").css("display","none");
                            $(".cdListContainer").attr("disabled", true);
                            $(".cdListContainer").find("input:checkbox").prop("checked", false);
                        }

                    }
                }
            },

            //리스트 전체 체크박스 선택시
            checkboxAll : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxSingle가 변경됨
                        var trgtArr = $(this).closest("div").find(".checkboxSingle");
                        if (trgtArr.length > 0)
                        {
                            var isChecked = false;
                            if($(this).is(":checked"))
                            {
                                isChecked = true;
                            }
                            $.each(trgtArr, function(){
                                $(this).prop("checked", isChecked);
                            })
                        }
                    }
                }
            },
            checkboxSingle : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxAll이 변경됨
                        var trgtObj = $(this).closest("div");
                        var allCbxCnt = trgtObj.find(".checkboxSingle").length;
                        var selCbxCnt = trgtObj.find(".checkboxSingle:checked").length;
                        if (allCbxCnt == selCbxCnt)
                        {
                            trgtObj.find(".checkboxAll").prop("checked", true);
                        }
                        else
                        {
                            trgtObj.find(".checkboxAll").prop("checked", false);
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
            }
        },
        immediately : function() {
            //리스트 조회
            //폼 데이터 처리
            cmmCtrl.setFormData($formObj);
            search();

            $excelObj.find("button.down").on('click', function(){
                var rsn = $excelObj.find("#rsn").val().trim();
                var frmDataObj    = $formObj.closest("form");

                frmDataObj.find("input[name='rsn']").remove();

                if (rsn != "") {
                    frmDataObj.append($('<input/>', { type: 'hidden',  name: 'rsn', value: rsn, class: 'notRequired' }));

                    //파라미터를 물고 가야함.
                    location.href = "/mngwserc/mp/mpb/excel-down?" + frmDataObj.serialize();
                    $excelObj.modal("hide");
                } else {
                    alert(msgCtrl.getMsg("fail.reason"));
                    return;
                }

            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});