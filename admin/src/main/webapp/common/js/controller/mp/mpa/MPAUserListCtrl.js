define(["ezCtrl"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpa/MPAUserListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);
    var $excelObj = ctrl.obj.parent().find(".excel-down");

    let popup;

    /**
     * popup 데이터 바인딩
     * @param data
     */
    function popDataBinding(data) {
        if(opener.document.querySelector(".memSeq")!=null) {
            opener.document.querySelector(".memSeq").value = data.memSeq;
        }
        if(opener.document.querySelector(".id")!=null) {
            opener.document.querySelector(".id").value = data.id;
        }
        if(opener.document.querySelector(".name")!=null) {
            opener.document.querySelector(".name").value = data.name;
        }
        if(opener.document.querySelector(".email")!=null) {
            opener.document.querySelector(".email").value = data.email;
        }
        if(opener.document.querySelector(".email")!=null) {
            opener.document.querySelector(".email").value = data.email;
        }

    }

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
    }, "/mngwserc/mp/mpa/select", $formObj, "POST", "html");

    }


    var callbackAjaxCdList  = function (cdList){

    console.log(cdList);

    var detailList = cdList.detailList;

    ctrl.obj.find("#cdListContainer").html(cdList);

    /*for(var i =0; i<detailList.length; i++){
    var detail = detailList[i];

    var cd = detail.cd;
    var cdNm = detail.cdNm;

    var tempHtml = "";

    tempHtml = +"<label className=\"checkbox-inline c-checkbox\">";
    tempHtml = +"<input type=\"checkbox\" className=\"checkboxSingle\" value=\""+cd+"\"  />";
    tempHtml = +"<span className=\"ion-checkmark-round\"></span>"+cdNm+"</label>";

    }*/


    console.log(tempHtml);

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
        btnChoice : {
                event : {
                    click : function () {
                        const form = $(this).closest('form')[0];
                        const formData = new FormData(form);
                        const jsonObject = {};
                        const size2 = [];

                        for (let pair of formData.entries()) {
                            if (pair[0] === 'delValueList') {
                                jsonObject[pair[0]] = pair[1];
                                size2.push(pair[1]);
                            }
                        }
                        if(size2.length >=2) {

                            alert("오류");
                            return false;
                        }

                        const jsonString = JSON.stringify(jsonObject);
                        const str = jsonString;

                        // memSeq 값을 추출하는 정규식
                        const memSeqRegex = /memSeq=(\d+)/;
                        const memSeqMatch = str.match(memSeqRegex);

                        // id 값을 추출하는 정규식
                        const idRegex = /id=([^,]+)/;
                        const idMatch = str.match(idRegex);

                        // name 값을 추출하는 정규식
                        const nameRegex = /name=([^,]+)/;
                        const nameMatch = str.match(nameRegex);

                        // email 값을 추출하는 정규식
                        const emailRegex = /email=([^,]+)/;
                        const emailMatch = str.match(emailRegex);

                        //데이터 생성
                        const userObject = {
                            memSeq: memSeqMatch ? memSeqMatch[1] : null,
                            id: idMatch ? idMatch[1] : null,
                            name: nameMatch ? nameMatch[1] : null,
                            email: emailMatch ? emailMatch[1] : null,
                        };

                        popDataBinding(userObject)
                        window.close();
                        // cmmCtrl.getUserPopClick(popDataBinding,$(this).closest('form')[0]);

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
                    console.log($(this).val());

                    var checkVal = $(this).val();

                    console.log(checkVal);

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
        const urlParams = new URLSearchParams(window.location.search);
        const memSeq = urlParams.get('popup');
        $(document).find('#btnChoice').hide();
        if(memSeq=='Y') {
            const header = document.querySelector('header');
            const aside = document.querySelector('aside');
            $(document).find('#btnExcelDown').hide();
            $(document).find('#btnChoice').show();

            if (header && header.style.display !== 'none') {
                header.style.display = 'none';
            }
            if (aside && aside.style.display !== 'none') {
                aside.style.display = 'none';
            }
            const mainContainer = document.querySelector('.main-container');
            if (mainContainer) {
                mainContainer.classList.remove('main-container');
            }
        }

        // console.log(memSeq);
        // cmmCtrl.getUserPopChk("btnExcelDown");

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
            location.href = "./excel-down?" + frmDataObj.serialize();

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