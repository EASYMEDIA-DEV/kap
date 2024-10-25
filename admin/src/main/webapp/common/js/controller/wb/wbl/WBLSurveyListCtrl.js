define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLSurveyListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

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
        }, "/mngwserc/wb/wbl/select", $formObj, "POST", "html");
    }



    // set model
    ctrl.model = {
        id : {

            //2024-07-11 추가개발 ppt 11 인증번호 동시 발송
            //2024-10-25 인중번호 발송 분리
            btnEmailSend : {
                event : {
                    click : function() {
                        var delActCnt = $("input:checkbox[name='delValueList']:checked").length;

                        var tempForm = {};

                        if (delActCnt > 0)
                        {
                            var sendList = [];

                            $("input:checkbox[name='delValueList']:checked").each(function() {
                                var item = {
                                    cxstnSrvSeq: $(this).val(),
                                    email: $(this).data('email'),
                                    crtfnNo: $(this).data('crtfnno'),
                                    telNo: $(this).data('telno'),
                                    partCmpnNm2: $(this).data('partcmpnnm2')
                                };
                                sendList.push(item);
                            });

                            tempForm.sendList = sendList;

                            if(confirm('인증번호를 발송하시겠습니까?'))
                            {
                                cmmCtrl.jsonAjax(function(data){
                                    try {
                                        var rtn = JSON.parse(data);
                                        console.log(data); // 서버에서 반환한 데이터 확인

                                        if (rtn.respCnt > 0 && rtn.respCnt == delActCnt) {
                                            alert("인증번호가 발송되었습니다.");
                                            search();
                                        } else {
                                            alert("인증번호 발송에 실패했습니다.");
                                        }
                                    } catch (e) {
                                        console.error("Error processing response data:", e);
                                    }
                                }, './submitCrtfnNoEmailList', tempForm , 'text')
                            }
                        }
                        else
                        {
                            alert('인증번호를 발송할 부품사를 선택해주세요');

                            return false;
                        }
                    }
                }
            },
            btnSmsSend : {
                event : {
                    click : function() {
                        var delActCnt = $("input:checkbox[name='delValueList']:checked").length;

                        var tempForm = {};

                        if (delActCnt > 0)
                        {
                            var sendList = [];

                            $("input:checkbox[name='delValueList']:checked").each(function() {
                                var item = {
                                    cxstnSrvSeq: $(this).val(),
                                    email: $(this).data('email'),
                                    crtfnNo: $(this).data('crtfnno'),
                                    telNo: $(this).data('telno'),
                                    partCmpnNm2: $(this).data('partcmpnnm2')
                                };
                                sendList.push(item);
                            });

                            tempForm.sendList = sendList;

                            if(confirm('인증번호를 발송하시겠습니까?'))
                            {
                                cmmCtrl.jsonAjax(function(data){
                                    try {
                                        var rtn = JSON.parse(data);
                                        console.log(data); // 서버에서 반환한 데이터 확인
                                        if (rtn.respCnt > 0 && rtn.respCnt == delActCnt) {
                                            alert("인증번호가 발송되었습니다.");
                                            search();
                                        } else {
                                            alert("인증번호 발송에 실패했습니다.");
                                        }
                                    } catch (e) {
                                        console.error("Error processing response data:", e);
                                    }
                                }, './submitCrtfnNoSmsList', tempForm , 'text')
                            }
                        }
                        else
                        {
                            alert('인증번호를 발송할 부품사를 선택해주세요');

                            return false;
                        }
                    }
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
            btnEpisd : {
                event : {
                    click : function() {
//                        $(".svaSurveySrchLayer").one('show.bs.modal', function() {
                        $(".episdSrchLayer").one('show.bs.modal', function() {
                            var modal = $(this);
                            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                        }).one('choice', function(data, param) {
                            // Remove class for soft backdrop (if not will affect future modals)
                            // fnc(param);
                        }).modal();
                    }
                }
            },
            btnExcelupload : {
                event : {
                    click : function() {
                        $(".episdSurveySrchLayer").one('show.bs.modal', function() {
                            var modal = $(this);
                            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                        }).one('choice', function(data, param) {
                            // Remove class for soft backdrop (if not will affect future modals)
                            // fnc(param);
                        }).modal();
                    }
                }
            },
            btnExcelDown : {
                event : {
                    click: function () {

                        if($("select[name='year']").val() == "" || $("select[name='episd']").val() == ""){
                            alert("년도와 회차를 선택해 주세요");

                            return false;
                        }

                        $(".excel-down").one('show.bs.modal', function() {
                            var modal = $(this);
                            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

                            $excelObj.find("#rsn").val('');

                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                        }).one('choice', function(data, param) {
                            // Remove class for soft backdrop (if not will affect future modals)
                            // fnc(param);
                        }).modal();

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
            }
        },
        immediately : function() {
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


                        //파라미터를 물고 가야함.
                        //location.href = "./excel-down?" + frmDataObj.serialize();

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

