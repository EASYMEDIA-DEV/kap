define(["ezCtrl","ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbb/WBBBCompanyWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    let selPartUser; /* 선택 사용자 ID*/

    var $formObj = ctrl.obj.find("#frmData").eq(0);
    var $formDataObj = ctrl.obj.find("#frm").eq(0);

    // form Object
    var $modalObj = $(".part-modal");
    //modalForm
    var $modalFormObj = $modalObj.find("form").eq(0);

    var ctgryVal = $('#ctgryCd').val();
    
    // 목록 조회
    var search = function (page){

        if(page !== undefined){
            $modalFormObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            $modalFormObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $modalFormObj, "listContainer", "pagingContainer");
        }, "/mngwserc/wb/selModalData", $modalFormObj, "POST", "html");

    }

    var selPartUserData = function (){

        //이관이력 조회

        cmmCtrl.frmAjax(function(respObj) {
            $modalObj.modal("hide");
            /* return data input */
            setInputValue(respObj);
        }, "/mngwserc/wb/selModalDetail", $modalFormObj, "post", "json")

    }

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail'].list];

        /* 사업자번호 변경 */
        let dataBsnmNo = rtnData['bsnmNo'];
        rtnData['bsnmNo'] = dataBsnmNo.slice(0,3) + '-' + dataBsnmNo.slice(3,5) + '-' + dataBsnmNo.slice(5);

        /* Input Hidden Tag Value  */

        $formObj.find(`input[type=hidden][name=id]`).val(rtnData['id']);
        $formObj.find(`input[type=hidden][name=bsnmNo]`).val(dataBsnmNo);

        /* 사용자_회사 정보 */
        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                let target = $formObj.find(`[id=${el}]`);
                if(target !== undefined) {
                    let tagName = target.prop('tagName');
                    /* SELECT || INPUT || P 태그 공통화 */
                    if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                    if(tagName === 'P') target.html(rtnData[el]);
                }
            }
        });

        ctgryVal = $('#ctgryCd').val();

        fieldShowFn(rtnData['ctgryCd']);

        /* SQ List */
        rtnDataCompList.forEach((compList, idx)=>{
            let targetArea = $formObj.find(`[id=sqInfoArea${idx}]`); /* SQ 구역 선택 */
            let target = $(targetArea);
            target.find('#cbsnSeq').val(compList['cbsnSeq']);
            target.find('#nm').val(compList['nm']);
            target.find('#score').val(compList['score']);
            target.find('#year').val(compList['year']);
            target.find('#crtfnCmpnNm').val(compList['crtfnCmpnNm']);
        });
    }

    /* 회차 검색 Ajax */
    let selEpisdList = () => {
        let optEpisd = $formObj.find('#optEpisd');
        if($formObj.find('#optYear').val() !== ''){
            cmmCtrl.frmAjax(function(respObj) {
                /* return data input */
                let html = '<option value="">회차 전체</option>';
                respObj.forEach((el) => {
                    html += '<option value="'+el.episdSeq+'">'+el.episd+'</option>';
                })
                optEpisd.empty().append(html);
            }, "./getEplisds", $formObj, "post", "json")
        } else {
            let html = '<option value="">회차 전체</option>';
            optEpisd.empty().append(html);
        }
    }

    /* 구분값 변경에 따른 SQ 영역 Event Fn l*/
    let fieldShowFn = (value) => {
        let fieldSQ = $formObj.find('#fieldSQ');
        let fieldStart = $formObj.find('#fieldStart');
        if( value != "null" && value != ""){
            if(value == 'COMPANY01001') {
                fieldSQ.hide();
                fieldSQ.find("input,select").prop('disabled', true);
                fieldStart.show();
                fieldStart.find("input,select").prop('disabled', false);
            }
            if(value == 'COMPANY01002') {
                fieldSQ.show();
                fieldSQ.find("input,select").prop('disabled', false);
                fieldStart.hide();
                fieldStart.find("input,select").prop('disabled', true);
            }
        } else {
            fieldSQ.hide();
            fieldSQ.find("input,select").prop('disabled', true);
            fieldStart.hide();
            fieldStart.find("input,select").prop('disabled', true);
        }
    }

    var trnsfSearch = function (page){
        //data로 치환해주어야한다.
        cmmCtrl.setFormData($formObj);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#trnfsListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "trnfsListContainer", "trnfsPagingContainer");
        }, "./log-list.ajax", $formObj, "POST", "html");
    }

    // set model
    ctrl.model = {
        id : {
            ctgryCd : {
                event : {
                    change : function() {
                        var ctgryCd = $(this).val();

                        if (ctgryCd == "COMPANY01001" || ctgryCd == "COMPANY01002") {
                        } else {
                            alert('부품사 구분은 1차,2차만 등록가능합니다.');
                            $(this).val(ctgryVal);
                        }

                        fieldShowFn($(this).val());
                    }
                }
            },
            optYear : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($modalFormObj);
                        selEpisdList();
                    },
                }
            },
            optEpisd : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($modalFormObj);
                        selOptnList();
                    },
                }
            },
            // 회원검색 모달
            btnPartUserModal : {
                event : {
                    click: function () {
                        $modalObj.find("input[name=memCd]").val('CP');
                        search(1);
                        $modalObj.modal("show");
                    }
                }
            },
            btnSearch : {
                event: {
                    click: function () {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($modalFormObj);
                        search(1);
                    }
                }
            },
            btnModalSelect : {
                event: {
                    click: function() {
                        let trArea = $modalFormObj.find("#listContainer input[type=checkbox]:checked");

                        if(trArea.length !== 0 || trArea != undefined){
                            selPartUser = trArea.val();
                            $modalFormObj.find("#selPartUser").val(selPartUser);
                            selPartUserData();
                        }
                    }
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width, height,"zipCode","bscAddr","dtlAddr");
                    }
                }
            },
        },
        classname : {
            checkboxSingle : {
                event : {
                    click : function() {
                        $modalFormObj.find('tbody').find('input[type=checkbox]').prop('checked',false);
                        $(this).prop('checked',true);
                    }
                },
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if ($(this).closest('#trnfsPagingContainer').length > 0) {
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            trnsfSearch($(this).attr("value"));
                        } else {
                            if( $(this).attr("value") !== "null" ){
                                $modalFormObj.find("input[name=pageIndex]").val($(this).attr("value"));
                                search();
                            }
                        }
                    }
                }
            },
            //페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $modalFormObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            },
            mngStatus : {
                event : {
                    change : function() {
                        var selectVal = $(this).val();

                        if (selectVal == "PRO_TYPE04_1_3" || selectVal == "PRO_TYPE04_1_5" || selectVal == "PRO_TYPE04_1_7") {
                            $(this).next().show();
                        } else {
                            $(this).next().hide();
                            $(this).next().val("");
                        }
                    }
                }
            }
        },
        immediately : function() {

            if ($('#ctgryCd').val() == "COMPANY01001") {
                $('#fieldSQ').hide();
            } else if ($('#ctgryCd').val() == "COMPANY01002") {
                $('#fieldStart').hide();
            }
            //리스트 조회
            //폼 데이터 처리
            cmmCtrl.setFormData($modalFormObj);
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });

            if ($formObj.find('input[name=detailsKey]').val()) {
                trnsfSearch(1);
            }

            $formObj.validation({
                after : function() {
                    var isValid = true;

                    /*if($(".dropzone .dz-preview").length < 1) {
                        alert(msgCtrl.getMsg("fail.notFileRequired"));
                        isValid = !isValid;
                    }*/

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){

                        var action = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "insert" : "update" );
                        var actionForm = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? $formObj : $formDataObj );
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        if (action == "update") {
                            var tabIndex = $('#tabIndex').val();
                            var tabDiv = $('#tabIndex'+tabIndex);
                            var companyDiv = $('#compnayDiv');
                            var nextStepNm = tabDiv.next().find('.panel-heading').text();

                            //다음 단계명 넣기
                            $('input[name=nextStageNm]').val(nextStepNm.trim());

                            var beforeUser = $formDataObj.find("input[name='memSeq']").val();
                            var afterUser = $formObj.find("input[name='memSeq']").val();

                            if (beforeUser != afterUser) {
                                $formDataObj.find("input[name='memSeq']").val(afterUser);
                                $formDataObj.find("input[name='wbbTransDTO.bfreMemSeq']").val(beforeUser);
                                $formDataObj.find("input[name='wbbTransDTO.aftrMemSeq']").val(afterUser);
                                $formDataObj.find("input[name='userLogYn']").val('Y');
                                $formDataObj.find("input[name='bsnmNo']").val($formObj.find("input[name='bsnmNo']").val());
                            }
                            $formDataObj.append(tabDiv);
                            $formDataObj.append(companyDiv)
                            $formDataObj.append($('#admMemo'));

                        }


                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, actionForm, "json");
                    }
                },
                msg : {
                    empty : {
                        text : " 입력해주세요."
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});
