define(["ezCtrl","ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbf/WBFBRegisterCompanyWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    let selPartUser;

    var $targetFormObj = ctrl.obj.find("form").eq(0);

    // form Object
    var $modalObj = $(".part-modal");
    //modalForm
    var $formObj = $modalObj.find("form").eq(0);

    // 목록 조회
    var search = function (page){

        if(page !== undefined){
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
        }, "/mngwserc/wb/selModalData", $formObj, "POST", "html");

    }

    var selPartUserData = function (){
        cmmCtrl.frmAjax(function(respObj) {
            $modalObj.modal("hide");
            /* return data input */
            setInputValue(respObj);
        }, "/mngwserc/wb/selModalDetail", $formObj, "post", "json")

    }

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail']];

        // 사업자번호 변경
        rtnData['bsnmNo'] = rtnData['bsnmNo'].slice(0,3) + '-' + rtnData['bsnmNo'].slice(3,5) + '-' + rtnData['bsnmNo'].slice(5);

        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                let target = $targetFormObj.find("[id=" + el + ']');
                if(target !== undefined) {
                    let tagName = target.prop('tagName');
                    /* SELECT || INPUT || P 태그 공통화 */
                    if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                    if(tagName === 'P') target.html(rtnData[el]);
                }
            }
        });


    }

    // set model
    ctrl.model = {
        id : {
            // 회원검색 모달
            btnPartUserModal : {
                event : {
                    click: function () {
                        search(1);
                        $modalObj.modal("show");
                    }
                }
            },
            btnSearch : {
                event: {
                    click: function () {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
            btnModalSelect : {
                event: {
                    click: function() {
                        let trArea = $formObj.find("#listContainer input[type=checkbox]:checked").parents("tr");

                        if(trArea.length !== 0 || trArea != undefined){
                            selPartUser = trArea.find('[data-point=id]').html();
                            $formObj.find("#selPartUser").val(selPartUser);
                            selPartUserData();
                        }

                    }
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"zipCode","bscAddr","dtlAddr");
                    }
                }
            },
        },
        classname : {

            classType : {
            },


            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") !== "null" ){
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
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});


