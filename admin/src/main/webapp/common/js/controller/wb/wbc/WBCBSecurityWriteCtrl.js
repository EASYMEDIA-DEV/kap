define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbc/WBCBSecurityWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    // form Object
    var $modalObj = $(".part-modal");
    var $modalObjForm = $(".part-modal").find("form").eq(0);

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail']];

        var memId = rtnData['id'];
        var memName = rtnData['name'];

        $('#mem').val(memId+'('+memName+')');

        $("#befeCtgryCd").val(rtnData['ctgryCd']);

        if(rtnData['pstnCd'] == 'MEM_CD01007'){
            $("#pstnNm").css("display", "block");
        }
        if(rtnData['ctgryCd'] == 'COMPANY01001'){
            $(".companyCate1").css("display", "block");
            $(".SQ").val("");
            $(".companyCate2").css("display", "none");
        }else if(rtnData['ctgryCd'] == 'COMPANY01002'){
            $(".companyCate1").css("display", "none");
            $(".starType").val("");
            $(".companyCate2").css("display", "block");
        }

        $("#bsnmNoPut").val(rtnData['bsnmNo']);

        // 사업자번호 변경
        rtnData['bsnmNo'] = rtnData['bsnmNo'].slice(0,3) + '-' + rtnData['bsnmNo'].slice(3,5) + '-' + rtnData['bsnmNo'].slice(5);

        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                var target = $formObj.find("[id=" + el + ']');
                var tagName = target.prop('tagName');
                /* SELECT || INPUT || P 태그 공통화 */
                if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                if(tagName === 'P') target.html(rtnData[el]);
            }
        });

        for(var i = 0; i < rtnDataCompList.list.length; i++){
            Object.keys(rtnDataCompList.list[i]).forEach((el) => {
                $formObj.find('#nm'+i).val(rtnDataCompList.list[i]['nm']);
                $formObj.find('#score'+i).val(rtnDataCompList.list[i]['score']);
                $formObj.find('#year'+i).val(rtnDataCompList.list[i]['year']);
                $formObj.find('#crtfnCmpnNm'+i).val(rtnDataCompList.list[i]['crtfnCmpnNm']);
            });
        }
    }

    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipcode","bscAddr","dtlAddr");
                    }
                }
            },
            searchPostCode2 : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipcode2","bscAddr2","dtlAddr2");
                    }
                }
            },
            episdYear : {
                event : {
                    change : function() {

                        cmmCtrl.frmAjax(function(data) {

                            var html = "<option value=\"\">선택</option>";
                            for(var i = 0; i< data.episdList.length; i++){
                                html += '<option value=\"'+data.episdList[i]+'\">'+data.episdList[i]+'</option>';
                            }
                            $("#episd").html(html);

                        }, "./getEpisdSelect", $formObj, "post", "json")
                    }
                }
            },
            ctgryCd : {
                event : {
                    change : function() {
                        var ctgryCd = $(this).val();

                        if(ctgryCd == 'COMPANY01001'){
                            $(".companyCate1").css("display", "block");
                            $(".SQ").val("");
                            $(".companyCate2").css("display", "none");
                        }else if(ctgryCd == 'COMPANY01002'){
                            $(".companyCate1").css("display", "none");
                            $(".starType").val("");
                            $(".companyCate2").css("display", "block");
                        }else{
                            alert("부품사 구분은 1차,2차만 등록가능합니다.");
                            if($("#befeCtgryCd").val() == ""){
                                $(this).val($("#befeCtgryCd").val());
                            }else{
                                $(this).val($("#befeCtgryCd").val()).trigger("change");
                            }
                        }

                    }
                }
            },
            pstnCd : {
                event : {
                    change : function() {
                        var pstnCd = $(this).val();

                        if(pstnCd == 'MEM_CD01007'){
                            $("#pstnNm").css("display", "block");
                        }else{
                            $("#pstnNm").val("");
                            $("#pstnNm").css("display", "none");
                        }
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
                            }, "/mngwserc/wb/selModalDetail", $formObj, "post", "json");
                        });
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
            classType : {
            },
        },
        immediately : function() {
            //리스트 조회
            //폼 데이터 처리
            /*cmmCtrl.setFormData($modalObjForm);*/
            /*search();*/ // 2023-12-01 잠시 제거


            var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

            /* Editor Setting */
            jQuery("textarea[id^='cnts']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                    readOnly : _readOnly
                });
            });

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });
            // 유효성 검사
            $formObj.validation({
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

                    if($(".dropzone .dz-preview").length < 1) {
                        alert(msgCtrl.getMsg("fail.notFileRequired"));
                        isValid = false;
                        return false;
                    }

                    jQuery.ajax({
                        url : "./getSbrdnBsnmNoCnt",
                        type : "POST",
                        timeout: 30000,
                        data : $formObj.serializeArray(),
                        dataType : "json",
                        async: false,
                        cache : false,
                        success : function(data, status, xhr){
                            if(data.respCnt > 0 ){
                                alert("이미 해당 회차에 신청한 부품사입니다.\n (종된사업장 중복)");
                                isValid = false;
                            }
                        }
                    });

                    jQuery.ajax({
                        url : "./getBsnmNoCnt",
                        type : "POST",
                        timeout: 30000,
                        data : $formObj.serializeArray(),
                        dataType : "json",
                        async: false,
                        cache : false,
                        success : function(data, status, xhr){
                            if(data.respCnt > 0 ){
                                alert("이미 해당 회차에 신청한 부품사입니다.");
                                isValid = false;
                            }
                        }
                    });

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./insert";
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        //rsume MST
                        var wBEBCarbonCompanyMstInsertDTO = {}; // rsumeMST
                        wBEBCarbonCompanyMstInsertDTO.episdSeq = null;
                        wBEBCarbonCompanyMstInsertDTO.year = ctrl.obj.find("#episdYear").val();
                        wBEBCarbonCompanyMstInsertDTO.episd = ctrl.obj.find("#episd").val();
                        wBEBCarbonCompanyMstInsertDTO.memSeq = ctrl.obj.find("#memSeq").val();
                        wBEBCarbonCompanyMstInsertDTO.appctnBsnmNo = ctrl.obj.find("#bsnmNoPut").val();
                        wBEBCarbonCompanyMstInsertDTO.sbrdnBsnmNo = ctrl.obj.find("#sbrdnBsnmNo").val();

                        wBEBCarbonCompanyMstInsertDTO.memList = new Array();
                        wBEBCarbonCompanyMstInsertDTO.pbsnDtlList = new Array();
                        wBEBCarbonCompanyMstInsertDTO.fileList = new Array();

                        //mem
                        var mPAUserDto = {}; // 회원
                        mPAUserDto.memSeq = ctrl.obj.find("#memSeq").val();
                        mPAUserDto.pstnCd = ctrl.obj.find("#pstnCd").val();
                        mPAUserDto.pstnCdNm = ctrl.obj.find("#pstnNm").val();
                        mPAUserDto.deptCd = ctrl.obj.find("#deptCd").val();
                        mPAUserDto.deptDtlNm = ctrl.obj.find("#deptDtlNm").val();
                        mPAUserDto.telNo = $("input[name=telNo]").eq(0).val();

                        wBEBCarbonCompanyMstInsertDTO.memList.push(mPAUserDto);

                        //company
                        var wBEBCompanyDTO = {}; // 회사

                        wBEBCompanyDTO.bsnmNo = ctrl.obj.find("#bsnmNoPut").val();
                        wBEBCompanyDTO.ctgryCd = ctrl.obj.find("#ctgryCd").val();
                        wBEBCompanyDTO.sizeCd = ctrl.obj.find("#sizeCd").val();
                        wBEBCompanyDTO.stbsmDt = ctrl.obj.find("#stbsmDt").val();
                        wBEBCompanyDTO.telNo = $("input[name=telNo]").eq(1).val();
                        wBEBCompanyDTO.zipcode = ctrl.obj.find("#zipcode").val();
                        wBEBCompanyDTO.bscAddr = ctrl.obj.find("#bscAddr").val();
                        wBEBCompanyDTO.dtlAddr = ctrl.obj.find("#dtlAddr").val();

                        wBEBCompanyDTO.slsPmt = ctrl.obj.find("#slsPmt").val();
                        wBEBCompanyDTO.slsYear = ctrl.obj.find("#slsYear").val();
                        wBEBCompanyDTO.mpleCnt = ctrl.obj.find("#mpleCnt").val();

                        wBEBCompanyDTO.mjrPrdct1 = ctrl.obj.find("#mjrPrdct1").val();
                        wBEBCompanyDTO.mjrPrdct2 = ctrl.obj.find("#mjrPrdct2").val();
                        wBEBCompanyDTO.mjrPrdct3 = ctrl.obj.find("#mjrPrdct3").val();

                        wBEBCompanyDTO.dtlList = new Array();

                        if(ctrl.obj.find("#ctgryCd").val() == 'COMPANY01001'){
                            wBEBCompanyDTO.qlty5starCd = ctrl.obj.find("#qlty5starCd").val();
                            wBEBCompanyDTO.qlty5starYear = ctrl.obj.find("#qlty5starYear").val();
                            wBEBCompanyDTO.pay5starCd = ctrl.obj.find("#pay5starCd").val();
                            wBEBCompanyDTO.pay5starYear = ctrl.obj.find("#pay5starYear").val();
                            wBEBCompanyDTO.tchlg5starCd = ctrl.obj.find("#tchlg5starCd").val();
                            wBEBCompanyDTO.tchlg5starYear = ctrl.obj.find("#tchlg5starYear").val();
                        }else{
                            for(var i = 0 ; i < 3; i++){
                                var wBEBCompanyDtlDTO = {};

                                wBEBCompanyDtlDTO.bsnmNo = ctrl.obj.find("#bsnmNoPut").val();
                                wBEBCompanyDtlDTO.cbsnSeq = ctrl.obj.find("#cbsnSeq").val();

                                wBEBCompanyDtlDTO.nm = $("input[name=nm]").eq(i).val();
                                wBEBCompanyDtlDTO.score = $("input[name=score]").eq(i).val();
                                wBEBCompanyDtlDTO.year = $("input[name=companyYear]").eq(i).val();
                                wBEBCompanyDtlDTO.crtfnCmpnNm = $("input[name=crtfnCmpnNm]").eq(i).val();

                                wBEBCompanyDTO.dtlList.push(wBEBCompanyDtlDTO);
                            }
                        }

                        wBEBCarbonCompanyMstInsertDTO.companyDtl = wBEBCompanyDTO;


                        //pbsnDtl
                        var wBEBCarbonCompanyPbsnDtlDTO = {}; // 상세
                        wBEBCarbonCompanyPbsnDtlDTO.pbsnZipcode = ctrl.obj.find("#zipcode2").val();
                        wBEBCarbonCompanyPbsnDtlDTO.pbsnBscAddr = ctrl.obj.find("#bscAddr2").val();
                        wBEBCarbonCompanyPbsnDtlDTO.pbsnDtlAddr = ctrl.obj.find("#dtlAddr2").val();

                        wBEBCarbonCompanyMstInsertDTO.pbsnDtlList.push(wBEBCarbonCompanyPbsnDtlDTO);

                        //file
                        var cOFileDTO = {};
                        wBEBCarbonCompanyMstInsertDTO.optnFileList = new Array();


                        var tempFile = {};

                        $formObj.find(".dropzone.attachFile").each(function(){
                            if($(this).get(0).dropzone.files != undefined && $(this).get(0).dropzone.files.length > 0){
                                $.each($(this).get(0).dropzone.files, function(idx, data){
                                    //alt값  data에 넣어주기.
                                    data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();
                                    for (let i in data) {
                                        if (data.hasOwnProperty(i)) {
                                            tempFile.status = data.status;
                                            tempFile.width = data.width;
                                            tempFile.height = data.height;
                                            tempFile.webPath = data.webPath;
                                            tempFile.fieldNm = "fileSeq";
                                            tempFile.orgnFileNm = data.orgnFileNm;
                                            tempFile.fileDsc = data.fileDsc;
                                            tempFile.fileSeq = data.fileSeq;
                                            tempFile.fileOrd = data.fileOrd;
                                        }
                                    }
                                })
                            }
                        });

                        wBEBCarbonCompanyMstInsertDTO.optnFileList.push(tempFile);

                        cmmCtrl.jsonAjax(function(data){
                            alert(actionMsg);
                            location.href = "./list";
                        }, actionUrl, wBEBCarbonCompanyMstInsertDTO, "text");

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

    ctrl.exec();

    return ctrl;
});

