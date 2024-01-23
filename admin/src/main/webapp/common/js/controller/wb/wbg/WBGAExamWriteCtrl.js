define(["ezCtrl","ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/wb/wbg/WBGAExamWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    let selPartUser; /* 선택 사용자 ID*/

    var $formObj = ctrl.obj.find("#frmData").eq(0);
    var $formDataObj = ctrl.obj.find("#frm").eq(0);
    var $form;

    //장비 html
    var equipmentInitHtml = "";

    var ctgryVal = $('#ctgryCd').val();

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail'].list];

        /* 사업자번호 변경 */
        let dataBsnmNo = rtnData['bsnmNo'];
        rtnData['bsnmNo'] = dataBsnmNo.slice(0,3) + '-' + dataBsnmNo.slice(3,5) + '-' + dataBsnmNo.slice(5);

        /* Input Hidden Tag Value  */
        $formObj.find(`input[type=hidden][name=id]`).val(rtnData['id']);
        $formObj.find(`input[type=hidden][name=bsnmNo]`).val(dataBsnmNo);

        if(rtnData['pstnCd'] == 'MEM_CD01007'){
            $("#pstnNm").css("display", "block");
        }

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

        $("#bsnmNo").val(dataBsnmNo);

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

    var consultingSearch = function(page, appctnBsnmNo) {
        $('input[name=appctnBsnmNo]').val(appctnBsnmNo);

        cmmCtrl.setFormData($formObj);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        $formObj.data("submitFlag", "N");

        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#conListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "conListContainer", "conPagingContainer");
        }, "./consultinglist.ajax", $formObj, "POST", "html");
    }

    let fnpstnNmShow = function(pstnCd) {
        if(pstnCd == 'MEM_CD01007'){
            $("#pstnNm").css("display", "block");
        }else{
            $("#pstnNm").val("");
            $("#pstnNm").css("display", "none");
        }
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
                        selEpisdList();
                    },
                }
            },
            optEpisd : {
                event : {
                    change : function() {
                        selOptnList();
                    },
                }
            },
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width, height,"zipcode","bscAddr","dtlAddr");
                    }
                }
            },
            pstnCd : {
                event : {
                    change : function() {
                        var pstnCd = $(this).val();

                        if(pstnCd =='MEM_CD01007'){
                            $("#pstnNm").css("display", "block");
                        }else{
                            $("#pstnNm").val("");
                            $("#pstnNm").css("display", "none");
                        }
                    }
                }
            },
            //PDF
            appctnPdfDownload : {
                event : {
                    click : function(){

                        var cmpnNm = $("#cmpnNm").html();
                        var today = new Date();

                        var date = today.getFullYear() +""+ today.getMonth()+1 +""+ today.getDate();


                        var fileName = "시험계측장비_사업현황_"+ cmpnNm +"_"+ date + ".pdf";
                        cmmCtrl.getAppctnPdfDownload(fileName);
                    }
                }
            },
            telNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
        },
        classname : {
            // 회원검색 모달
            btnPartUserModal: {
                event: {
                    click: function () {
                        $("#srchDivide").val("Y");
                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            $formObj.find('#memSeq').val(data.memSeq);
                            cmmCtrl.frmAjax(function(respObj) {
                                /* return data input */
                                setInputValue(respObj);
                                fnpstnNmShow($('#pstnCd').val());
                            }, "/mngwserc/wb/selModalDetail", $formObj, "post", "json");
                        });
                    }
                },
            },
            // 위원검색 모달
            btnCmtSearch: {
                event: {
                    click: function () {
                        cmmCtrl.getCmtSrchPop(function (data) {
                            $formObj.find('#chkCmssrSeq').val(data.seq);
                            $formObj.find('#chkCmssrNm').val(data.name);
                        });
                    }
                }
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if ($(this).closest('#trnfsPagingContainer').length > 0) {
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            trnsfSearch($(this).attr("value"));
                        } else if ($(this).closest('#conPagingContainer').length > 0) {
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            consultingSearch($(this).attr("value"));
                        }
                    }
                }
            },
            mngStatus : {
                event : {
                    change : function() {
                        var selectVal = $(this).val();
                        var selectId = $(this).attr("id");

                        if (selectId == "mngStatus1") {
                            if (selectVal == "PRO_TYPE07001_02_002" || selectVal == "PRO_TYPE04_1_5" || selectVal == "PRO_TYPE07001_02_005") {
                                $(this).next().show();
                            } else {
                                $(this).next().hide();
                                $(this).next().val("");
                            }
                        } else if (selectId == "mngStatus2") {
                            if (selectVal == "PRO_TYPE07001_04_003" || selectVal == "PRO_TYPE07001_04_005") {
                                $(this).next().show();
                            } else {
                                $(this).next().hide();
                                $(this).next().val("");
                            }
                        } else if (selectId == "mngStatus3") {
                            if (selectVal == "PRO_TYPE07001_06_003" || selectVal == "PRO_TYPE07001_06_005") {
                                $(this).next().show();
                            } else {
                                $(this).next().hide();
                                $(this).next().val("");
                            }
                        }
                    }
                }
            },
            insertLow : {
                event : {
                    click : function() {
                        var equipLength = ctrl.obj.find(".equipment").length;

                        $(".equipment").last().after(equipmentInitHtml);
                        ctrl.obj.find(".equipment").last().find(".equipmentNm").text('대상장비 ' + (ctrl.obj.find(".equipment").size())).append('<span class="star"> *</span>');
                        ctrl.obj.find(".equipment").last().find(".tchlgNm").attr("name","euipmentList["+ equipLength +"].tchlgNm");
                        ctrl.obj.find(".equipment").last().find(".tchlgCnt").attr("name","euipmentList["+ equipLength +"].tchlgCnt");
                    }
                }
            },
            deleteLow : {
                event : {
                    click : function() {
                        $(this).parents(".equipment").remove();

                        ctrl.obj.find(".equipment").each(function(index, row){
                            $(this).find(".equipmentNm").text('대상장비 ' + parseInt(index+1)).append('<span class="star"> *</span>');
                            $(this).find(".tchlgNm").attr("name","euipmentList["+ index +"].tchlgNm");
                            $(this).find(".tchlgCnt").attr("name","euipmentList["+ index +"].tchlgCnt");
                        })

                    }
                }
            },
            telNumber : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
        },
        immediately : function() {

            if ($('#ctgryCd').val() == "COMPANY01001") {
                $('#fieldSQ').hide();
            } else if ($('#ctgryCd').val() == "COMPANY01002") {
                $('#fieldStart').hide();
            }

            var pstnCd = $('#pstnCd').val();
            if(pstnCd =='MEM_CD01007'){
                $("#pstnNm").css("display", "block");
            }else{
                $("#pstnNm").val("");
                $("#pstnNm").css("display", "none");
            }

            equipmentInitHtml = $("#equipmentHtml").html();
            $("#equipmentHtml").empty();

            if($.trim($("input[name=detailsKey]").val()) == ""){
                $('.nextHtml').append(equipmentInitHtml);
                $(".deleteLow").remove();
            }

            //리스트 조회
            //폼 데이터 처리
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

            consultingSearch(1,$('input[name=appctnBsnmNo]').val());

            $formObj.validation({
                before : function() {
                    var action = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "insert" : "update" );

                    if (action == "update") {
                        var tabIndex = $('#tabIndex').val();

                        if (tabIndex == 1) {
                            $('#tabIndex2').children().find('input').each(function() {
                                if(!$(this).hasClass("notRequired")) {
                                    $(this).addClass("notRequired");
                                }
                            });

                            $('#tabIndex2').children().find('.dropzone').each(function() {
                                if(!$(this).hasClass("notRequired")) {
                                    $(this).addClass("notRequired");
                                }
                            });

                            $('#tabIndex3').children().find('.dropzone').each(function() {
                                if(!$(this).hasClass("notRequired")) {
                                    $(this).addClass("notRequired");
                                }
                            });
                        } else if (tabIndex == 3) {
                            $('#tabIndex2').children().find('input').each(function() {
                                if(!$(this).hasClass("notRequired")) {
                                    $(this).addClass("notRequired");
                                }
                            });

                            $('#tabIndex1').children().find('.dropzone').each(function() {
                                if(!$(this).hasClass("notRequired")) {
                                    $(this).addClass("notRequired");
                                }
                            });

                            $('#tabIndex2').children().find('.dropzone').each(function() {
                                if(!$(this).hasClass("notRequired")) {
                                    $(this).addClass("notRequired");
                                }
                            });
                        }
                    }
                },
                after : function() {
                    var isValid = true;

                    if( $("#telNo").val().length !=0 && $("#telNo").val().length < 11 ) {
                        alert(msgCtrl.getMsg("fail.mp.mpb.al_011"));
                        isValid = false;
                        return false;
                    }
                    if( $("#compTel").val().length !=0 && $("#compTel").val().length < 11 ) {
                        alert(msgCtrl.getMsg("fail.mp.mpb.al_014"));
                        isValid = false;
                        return false;
                    }

                    $(".dropzone").each(function() {
                        if(!$(this).hasClass("notRequired")) {
                            if ($(this).find('.dz-preview').length < 1) {
                                alert($(this).data("title") + "을 등록해주세요.");
                                isValid = false;
                                return false;
                            }
                        }
                    });

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
                            var equipment = $('.equipment');

                            var beforeUser = $formDataObj.find("input[name='memSeq']").val();
                            var afterUser = $formObj.find("input[name='memSeq']").val();

                            if (beforeUser != afterUser) {
                                $formDataObj.find("input[name='memSeq']").val(afterUser);
                                $formDataObj.find("input[name='wbbTransDTO.bfreMemSeq']").val(beforeUser);
                                $formDataObj.find("input[name='wbbTransDTO.aftrMemSeq']").val(afterUser);
                                $formDataObj.find("input[name='userLogYn']").val('Y');
                                $formDataObj.find("input[name='bsnmNo']").val($formObj.find("input[name='bsnmNo']").val());
                            }

                            if (tabIndex == 2) {
                                $('#tabIndex2').children().find('.comma').each(function () {
                                    $(this).val($(this).val().replaceAll(",",""))
                                });
                            }

                            $formDataObj.append(tabDiv);
                            $formDataObj.append(companyDiv);
                            $formDataObj.append(equipment);
                            $formDataObj.append($('#admMemo'));

                        }


                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, actionForm, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, actionForm, "post", "json")
                        }
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
