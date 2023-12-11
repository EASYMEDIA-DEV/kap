define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function (ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller: "controller/cb/cba/CBATechGuidanceWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var valChk = 'N';
    var idNum = 0;

    var search = function (page){
        //data로 치환해주어야한다.
        //cmmCtrl.setFormData($formObj);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#trsfListContainer").html(respObj);

            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#trsfListContainerTotCnt").val(totCnt);

            $(".ptcptField").validation({});

            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "trsfListContainer", "trsfPagingContainer");

        }, "/mngwserc/cb/cba/trsfList", $formObj, "GET", "html");
    }

    var callbackAjaxDelete = function (data) {

        if (parseInt(data.respCnt, 10) > 0) {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        } else {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    var callbackAjaxBsnmNo = function (data) {

        var detailList = JSON.parse(data);

        for (var i = 0; i < detailList.length; i++) {

            var cmpnNm = detailList[0].cmpnNm
            var bscAddr = detailList[0].bscAddr
            var dtlAddr = detailList[0].dtlAddr
            var zipcode = detailList[0].zipcode
            var bsnmNo = detailList[0].bsnmNo
            var sizeCd = detailList[0].sizeCd
            var rprsntNm = detailList[0].rprsntNm
            var cmpnTelNo = detailList[0].telNo
            var ctgryCd = detailList[0].ctgryCd
            var stbsmDt = detailList[0].stbsmDt
            var cmpnCd = detailList[0].cmpnCd
            var cmpnNfrmlNm = detailList[0].cmpnNfrmlNm

            var cmpnSlsPmt = detailList[0].slsPmt
            var cmpnSlsYear = detailList[0].slsYear
            var cmpnMpleCnt = detailList[0].mpleCnt
            var mjrPrdct1 = detailList[0].mjrPrdct1
            var mjrPrdct2 = detailList[0].mjrPrdct2
            var mjrPrdct3 = detailList[0].mjrPrdct3

            var qlty5StarCd = detailList[0].qlty5StarCd
            var qlty5StarYear = detailList[0].qlty5StarYear
            var pay5StarYear = detailList[0].pay5StarYear
            var pay5StarCd = detailList[0].pay5StarCd
            var tchlg5StarCd = detailList[0].tchlg5StarCd
            var tchlg5StarYear = detailList[0].tchlg5StarYear

            var crtfnCmpnNm1 = detailList[0].crtfnCmpnNm
            var nm1 = detailList[0].nm
            var cbsnSeq1 = detailList[0].cbsnSeq
            var score1 = detailList[0].score
            var year1 = detailList[0].year

            var crtfnCmpnNm2 = detailList[1].crtfnCmpnNm
            var nm2 = detailList[1].nm
            var cbsnSeq2 = detailList[1].cbsnSeq
            var score2 = detailList[1].score
            var year2 = detailList[1].year

            var crtfnCmpnNm3 = detailList[2].crtfnCmpnNm
            var nm3 = detailList[2].nm
            var cbsnSeq3 = detailList[2].cbsnSeq
            var score3 = detailList[2].score
            var year3 = detailList[2].year

            $('#nm').val(nm1);
            $('#score').val(score1);
            $('#crtfnCmpnNm').val(crtfnCmpnNm1);
            $('#cbsnSeq').val(cbsnSeq1);

            $("#nm1").val(nm2);
            $('#score1').val(score2);
            $('#crtfnCmpnNm1').val(crtfnCmpnNm2);
            $('#cbsnSeq1').val(cbsnSeq2);

            $("#nm2").val(nm3);
            $("#score2").val(score3);
            $("#crtfnCmpnNm2").val(crtfnCmpnNm3);
            $("#cbsnSeq2").val(cbsnSeq3);

            $("#ctgryCdSelect").val(ctgryCd).prop("selected", true);
            if (ctgryCd == "COMPANY01001") {
                $(".sqInfo").hide();
                $(".fiveStar").show();
            } else {
                $(".fiveStar").hide();
                $(".sqInfo").show();
            }
            $("#yearSelect").val(year1).prop("selected", true);
            $("#yearSelect1").val(year2).prop("selected", true);
            $("#yearSelect2").val(year3).prop("selected", true);

            $("#sizeCdSelect").val(sizeCd).prop("selected", true);
            $("#qlty5StarCd").val(qlty5StarCd).prop("selected", true);
            $("#qlty5StarYear").val(qlty5StarYear).prop("selected", true);
            $("#pay5StarCd").val(pay5StarCd).prop("selected", true);
            $("#pay5StarYear").val(pay5StarYear).prop("selected", true);
            $("#tchlg5StarCd").val(tchlg5StarCd).prop("selected", true);
            $("#tchlg5StarYear").val(tchlg5StarYear).prop("selected", true);

            $('p[name=rprsntNm]').text(rprsntNm);
            $('p[name=bsnmNo]').text(bsnmNo);
            $('p[name=cmpnNm]').text(cmpnNm);
            $('input[name=zipcode]').val(zipcode);
            $('input[name=bscAddr]').val(bscAddr);
            $('input[name=dtlAddr]').val(dtlAddr);
            $('input[name=rprsntNm]').val(rprsntNm);
            $('input[name=stbsmDt]').val(stbsmDt);

            $('input[name=mjrPrdct1]').val(mjrPrdct1);
            $('input[name=mjrPrdct2]').val(mjrPrdct2);
            $('input[name=mjrPrdct3]').val(mjrPrdct3);

            $('input[name=slsPmt]').val(cmpnSlsPmt);
            $('input[name=cmpnSlsYear]').val(cmpnSlsYear);
            $('input[name=mpleCnt]').val(cmpnMpleCnt);
            $('input[name=cmpnTelNo]').val(cmpnTelNo);

            $('input[name=cmpnNfrmlNm]').val(cmpnNfrmlNm);
            $('input[name=cmpnCd]').val(cmpnCd);

        }
    }
    var callbackAjaxAddrList = function (data) {
        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>전체</option>";

        for (var i = 0; i < detailList.length; i++) {

            var cd = detailList[i].cd;
            var cdNm = detailList[i].cdNm;

            selectHtml += "<option value='" + cd + "' >" + cdNm + "</option>";
        }

        $("#subAddr option").remove();

        $("#subAddr").append(selectHtml);

        var subAddr = $("#subAddr").data("subAddr");

        $("#subAddr").val(subAddr).prop("selected", true);

    }

    // set model
    ctrl.model = {
        id: {
            checkBtn: {
                event: {
                    click: function () {
                        var checkNo = $("#bsnmNo").val();
                        var ajaxData = {
                            bsnmNo: checkNo
                        }

                        jQuery("#frmData").serializeArray().forEach(function (field) {
                            if (field.name == '_csrf') {
                                ajaxData[field.name] = field.value;
                            }
                        });

                        console.log(JSON.stringify(ajaxData, null, 2));

                        if (valChk) {
                            $.ajax({
                                type: "post",
                                url: './checkBsnmNo',
                                dataType: "json",
                                data: ajaxData,
                                success: function (r) {
                                    console.log(r.respCnt);
                                    console.log(r);
                                    if (r.respCnt) {
                                        alert("인증되었습니다.");
                                        $("#cmpnNm").val(r.cmpnNm);
                                        $("#rprsntNm").val(r.rprsntNm);
                                    } else {
                                        alert('DB에 등록된 정보가 없습니다. NICE 인증으로 넘어갑니다.')
                                        $("#cmpnNm").val("nice에서 가져온 회사명");
                                        $("#rprsntNm").val("nice에서 가져온 대표자명");
                                    }
                                },
                                error: function (xhr, ajaxSettings, thrownError) {
                                    alert("인증에 실패했습니다.");
                                }
                            });
                        } else {
                            alert("사업자등록번호를 인증해주세요");
                            return false;
                        }
                    }
                }
            },
            ctgryCd: {
                event: {
                    change: function () {
                        var ctgryCd = $("#ctgryCd option:selected").val();
                        if (ctgryCd == "COMPANY01001") {
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").show();
                            $(".pay5StarArea").show();
                            $(".tchlg5StarArea").show();
                        } else if (ctgryCd == "COMPANY01002") {
                            $(".sqInfoArea").show();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();
                        }
                    }
                }
            },
            pstnCdSelect: {
                event: {
                    click: function () {
                        var clickVal = $(this).val();
                        if (clickVal == 'MEM_CD01007') {
                            $(".pstnCdInput").show();
                            $("input[name='pstnNm']").removeClass("notRequired");
                        } else {
                            $(".pstnCdInput").hide();
                            $("input[name='pstnNm']").addClass("notRequired");
                        }
                    }
                }
            }, btnRefresh : {
                event : {
                    click : function(){
                    }
                }
            },
            allSelector : {
                event : {
                        click : function(){
                        var cdVal = $(this).children("input[name='appctnTypeCd']").val();
                        if (cdVal == "TEC_GUIDE_APPCTN00") {
                            if($(this).children("input[name='appctnTypeCd']").is(":checked"))
                            {
                                $("input[name=appctnTypeCd]").prop("checked", true);
                            }else{
                                $("input[name=appctnTypeCd]").prop("checked", false);
                            }
                        }
                    }
                }
            },
        },
        classname: {
            searchPostCode: {
                event: {
                    click: function () {
                        var idVal = $(this).attr('id');
                        if (idVal == "hqAddr") {
                            cmmCtrl.searchPostCode(width, height, "hqZipcode", "hqBscAddr", "hqDtlAddr");
                        } else {
                            cmmCtrl.searchPostCode(width, height, "zipcode", "bscAddr", "dtlAddr");
                        }
                    }
                }
            },
            cmpnPlus: {
                event: {
                    click: function () {
                        var rowCnt = $(".tempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dlvryRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dlvryRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dlvryTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children(".closeLabel").css("display", "block");
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            dpndnPlus: {
                event: {
                    click: function () {
                        var rowCnt = $(".dpTempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dpndnRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dpndnRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dpTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children(".closeLabel").css("display", "block");
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            closeLabel: {
                event: {
                    click: function () {
                        $(this).parent().remove();
                    }
                }
            },
            addrSame: {
                event: {
                    click: function () {
                        var checked = $(".addrSame").is(':checked');
                        var sameVal = $(this).val();
                        if (sameVal == 'Y') {
                            $(this).val("N");
                            $(".factAddr").prop("disabled", false);
                            $("#dtlAddr").prop("readOnly", false);
                        } else {
                            $(this).val("Y");
                            $(".factAddr").prop("disabled", true);
                            $("#dtlAddr").prop("readOnly", true);
                        }
                        if (checked) {
                            var hqZipcode = $("#hqZipcode").val();
                            var hqBscAddr = $("#hqBscAddr").val();
                            var hqDtlAddr = $("#hqDtlAddr").val();
                            $("#zipcode").val(hqZipcode);
                            $("#bscAddr").val(hqBscAddr);
                            $("#dtlAddr").val(hqDtlAddr);
                        }
                    }
                }
            },
            ctgryCd: {
                event: {
                    click: function () {
                        var selectVal = $("select[name=ctgryCd]").val();
                        if (selectVal == "COMPANY01001") {
                            $(".sqInfo").hide();
                            $(".fiveStar").show();
                        } else {
                            $(".fiveStar").hide();
                            $(".sqInfo").show();
                        }
                    }
                }
            },
            cbsnCd: {
                event: {
                    click: function () {
                        var cbsnVal = $("input[name=cbsnCd]:checked").val();
                        console.log(cbsnVal);
                        if (cbsnVal == "TEC_GUIDE_INDUS01") { // 기타일 때 input 활성화
                            $("input[name=etcNm]").removeClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", false);
                        } else {
                            $("input[name=etcNm]").addClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", true);
                        }
                    }
                }
            },
            btnPartUserModal: {
                event: {
                    click: function () {
                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            var cmpnMst = {};
                            cmpnMst.bsnmNo = data.bsnmNo.replaceAll("-", "");
                            cmmCtrl.jsonAjax(function (data) {
                                callbackAjaxBsnmNo(data);
                            }, './bsnmNoSearch', cmpnMst, "text");
                        });
                    }
                }
            },
            btnCmtSearch: {
                event: {
                    click: function () {
                        cmmCtrl.getCmtSrchPop(function (data) {
                            $("input[name=cmssrSeq]").val(data.seq);
                            $("input[name=cmssrName]").val(data.name);
                        });

                    }
                }
            },
            mainAddr: {
                event: {
                    click: function () {
                        var mainAddr = {}
                        mainAddr.cd = $(this).val();
                        cmmCtrl.jsonAjax(function (data) {
                            callbackAjaxAddrList(data);
                        }, './subAddrSelect', mainAddr, "text");
                    }
                }
            },
            initVstRsltCd: {
                event: {
                    click: function () {
                        var rsltCd = $(this).val();
                        if (rsltCd == "BF_JDGMT_RSLT02") {
                            $(".rsltCntn").show();
                        } else {
                            $(".rsltCntn").hide();
                        }
                    }
                }
            },
            //설문 검색
            srvSearch: {
                event: {
                    click : function(){
                        $(".svaSurveySrchLayer").one('show.bs.modal', function() {

                            var modal = $(this);
                            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정

                        }).one('hidden.bs.modal', function() {
                            // Remove class for soft backdrop (if not will affect future modals)
                        }).one('choice', function(data, param) {
                            var obj = param;
                            $("#listContainer3").find("td").eq(0).text(obj.typeNm);
                            $("#listContainer3").find("td").eq(1).text(obj.titl);
                            $("#srvSeq").val(obj.seq);
                        }).modal();
                    }
                }
            },tabClick : {
                event : {
                    click : function (e){
                        console.log(e.target.getAttribute('href'));
                        if(e.target.getAttribute('href') == "#techGuidance") {
                            $("#episdList").addClass("in active");
                            $("#svResult").removeClass("in active");
                        } else {
                            $("#episdList").removeClass("in active");
                            $("#svResult").addClass("in active");
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
            appctnPdfDownload : {
                event : {
                    click : function(){
                        var fileName = "TechGuide.pdf";
                        cmmCtrl.getAppctnPdfDownload(fileName);
                    }
                }
            },

        },
        immediately: function () {

            /* Editor Setting */
            jQuery("textarea[id^='cntn']").each(function () {
                cmmCtrl.setEditor({
                    editor: jQuery(this).attr("id"),
                    height: 400,
                });
            });

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function () {
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt: trgtObj.data("maxFileCnt"),
                    maxFileSize: trgtObj.data("maxFileSize"),
                    fileExtn: trgtObj.data("fileExtn"),
                    fileFieldNm: trgtObj.data("fileFieldNm")
                })
            });

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                }
            });

            var detailsKey = $("#detailsKey").val();
            if (detailsKey != null || detailsKey != "") {

                // 소재지역 체크 함수
                $("#mainAddr").trigger("click");
                var subAddrCd = $("#subAddr").data("subaddr");
                $("#subAddr").val(subAddrCd).prop("selected", true);

                // 신청사항 체크 함수
                var appctnTypeCd = $(".appctnTypeCd").val();
                var apSize = $(".apTypeCd").size();
                for (var i = 0; i < apSize; i++) {
                    var apVal = $(".apTypeCd").eq(i).val();
                    $("input[name=appctnTypeCd]").each(function () {
                        var chkVal = $(this).val();
                        if (apVal == chkVal) {
                            $(this).prop("checked", true);
                        }
                    })
                }
                var initVstRsltCd = $("#initVstRsltCd").val();
                if (initVstRsltCd == "BF_JDGMT_RSLT02") {
                    $(".rsltCntn").show();
                } else {
                    $(".rsltCntn").hide();
                }

                $(".tempRow").eq(0).find(".close").hide();
                $(".dpTempRow").eq(0).find(".close").hide();
                search();
            }

            // 유효성 검사
            $formObj.validation({
                after: function () {
                    var isValid = true, editorChk = true;

                    $formObj.find(".ckeditorRequired").each(function () {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1) {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.co.cog.cnts"));

                            CKEDITOR.instances[jQuery(this).prop("id")].focus();

                            // 에디터 최상단으로 스크롤 이동
                            jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

                            return false;
                        }
                    });

                    if (!editorChk) {
                        isValid = false;
                    }

                    return isValid;
                }
                ,
                async: {
                    use: true,
                    func: function () {
                        var actionUrl = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update");
                        var actionMsg = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd"));
                        var cmpnNm = $("#cmpnNmText").text();
                        var bsnmNo = $("#bsnmNoText").text();
                        var emailTxt = $("#emailTxt").text();
                        var rprsntNmTxt = $("#rprsntNmTxt").text();
                        var cbsnCd = $("input[name='cbsnCdRadio']:checked").val();

                        var vstDt = $("input[name='vstDt']").val();
                        if (!vstDt) {
                            $("input[name='vstDt']").prop("disabled", true);
                        }
                        var guidePscndDt = $("input[name='guidePscndDt']").val();
                        if (!guidePscndDt) {
                            $("input[name='guidePscndDt']").prop("disabled", true);
                        }
                        var guideBgnDt = $("input[name='guideBgnDt']").val();
                        if (!guideBgnDt) {
                            $("input[name='guideBgnDt']").prop("disabled", true);
                        }
                        var guideKickfDt = $("input[name='guideKickfDt']").val();
                        if (!guideKickfDt) {
                            $("input[name='guideKickfDt']").prop("disabled", true);
                        }
                        var srvStrtDtm = $("input[name='srvStrtDtm']").val();
                        if (!srvStrtDtm) {
                            $("input[name='srvStrtDtm']").prop("disabled", true);
                        }
                        var srvEndDtm = $("input[name='srvEndDtm']").val();
                        if (!srvEndDtm) {
                            $("input[name='srvEndDtm']").prop("disabled", true);
                        }

                        $("#cmpnNm").val(cmpnNm);
                        $("#bsnmNo").val(bsnmNo);
                        $("#email").val(emailTxt);
                        $("#rprsntNm").val(rprsntNmTxt);
                        $("#cbsnCd").val(cbsnCd);


                        if ($formObj.find(".dropzone").size() > 0) {
                            cmmCtrl.fileFrmAjax(function (data) {
                                //콜백함수. 페이지 이동
                                if (data.respCnt > 0) {
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "json");
                        } else {
                            cmmCtrl.frmAjax(function (data) {
                                if (data.respCnt > 0) {
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                                actionUrl = "./list";
                            }, actionUrl, $formObj, "post", "json")
                        }
                    }
                },
                msg: {
                    empty: {
                        text: " 입력해주세요."
                    }
                }
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

