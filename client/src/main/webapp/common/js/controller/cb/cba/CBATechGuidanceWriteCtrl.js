define(["ezCtrl", "ezVald", "ezFile"], function(ezCtrl, ezVald) {

    "use strict";

// set controller name
    var exports = {
        controller : "controller/cb/cba/CBATechGuidanceWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');

    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var idNum = 0;

    var callbackAjaxAddrList = function (data) {
        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>선택</option>";

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

    var extnCheck = function(obj, extns, maxSize)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;

        console.log(obj.files);

        if (!fileObj)
        {
            isFile = false;
        }
        else
        {
            var file;
            file = obj.files[0];

            var fileExtn = file.name.split(".").pop();

            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                //파일확장자 체크
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find('.empty-txt').text("");
                alert('첨부 가능한 파일 확장자가 아닙니다.');

                isFile = false;
            } else {
                //파일용량 체크
                if (typeof obj.files != "undefined")
                {
                    var fileSize = file.size;
                    var maxFileSize = maxSize * 1024 * 1024;

                    if (fileSize > maxFileSize)
                    {
                        $('#'+fileId).val("");
                        $('#'+fileId).closest(".form-group").find('.empty-txt').text("");
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }
                }
            }

            if (isFile) {
                $('#'+fileId).closest(".form-group").find('.file-list-area').addClass("attached");
                $('#'+fileId).closest(".form-group").find('.empty-txt').hide();
                $('#'+fileId).closest(".form-group").find('.file-list').show();
                $('#'+fileId).closest(".form-group").find('.name').text(obj.files[0].name.split(".")[0]);
                $('#'+fileId).closest(".form-group").find('.unit').text("." + obj.files[0].name.split(".").reverse()[0]);
            }
        }
    };
    // set model
    ctrl.model = {
        id : {
            cmpnAddrSameYn : {
                event : {
                    click: function(){
                        var cmpnMst = {};
                        var cmpnAddrYn = $("#cmpnAddrSameYn").val();

                        if(cmpnAddrYn == 'N'){
                            $("#cmpnAddrSameYn").val('Y');
                            cmpnMst.bsnmNo = $(".bsnmNo").val().replaceAll("-", "");
                            cmmCtrl.jsonAjax(function (data) {
                                var info = JSON.parse(data)
                                $("#hqZipcode").val(info.list[0].zipcode);
                                $("#hqBscAddr").val(info.list[0].bscAddr);
                                $("#hqDtlAddr").val(info.list[0].dtlAddr);
                            }, './bsnmNoSearch', cmpnMst, "text");
                        }else{
                            $("#cmpnAddrSameYn").val('N');
                            $("#hqZipcode").val("");
                            $("#hqBscAddr").val("");
                            $("#hqDtlAddr").val("");
                        }
                    }
                }
            },
            agreeChk : {
                event : {
                    click : function(){
                        var agree = $("#agreeChk").val();
                        if(agree == "N"){
                            $("#agreeChk").val("Y");
                            $("#agreeChk").attr("checked", true);
                        }else{
                            $("#agreeChk").val("N");
                            $("#agreeChk").attr("checked", false)
                        }
                    }
                }
            }
        },

        classname : {
            //다운로드
            fileDown : {
                event : {
                    click : function(e) {
                        $(".loading-area").stop().fadeIn(200);
                        var url = $(this).data("url");

                        async function downloadFile() {
                            await new Promise(resolve => setTimeout(resolve, 200)); // 200ms 대기
                            location.href = url;
                            $(".loading-area").stop().fadeOut(200);
                        }

                        downloadFile();
                    }
                }
            },

            searchPostCode: {
                event: {
                    click: function () {
                        $("#cmpnAddrSameYn").val('N');
                        $("#cmpnAddrSameYn").attr("checked", false);
                        var idVal = $(this).attr('id');
                        if (idVal == "hqAddr") {
                            cmmCtrl.searchPostCode(width, height, "hqZipcode", "hqBscAddr", "hqDtlAddr");
                        } else {
                            cmmCtrl.searchPostCode(width, height, "zipcode", "bscAddr", "dtlAddr");
                        }
                    }
                }
            },
            filedelete : {
                event : {
                    click : function(){
                        jQuery(this).closest().find(".name").text("");
                        jQuery(this).closest(".form-group").find(".file-list-area").addClass("file-list-area");
                        jQuery(this).closest(".form-group").find(".file-list").hide();
                        jQuery(this).closest(".form-group").find(".empty-txt").show();
                        jQuery(this).closest(".form-group").find(".searchFile").val("");
                    }
                }
            },
            cmpnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".tempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dlvryRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dlvryRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dlvryTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children().find(".cmpnPlus").hide();
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                            $("#dlvryTempDiv").children().eq(0).find(".closeLabel").hide();
                            $('#dlvryTempDiv').children().not(':eq(0)').find(".closeLabel").show();
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            closeLabel : {
                event : {
                    click : function(){
                        //거래처별 매출 비중 삭제
                        $(this).parents(".tempRow").remove();
                        // 완성차 의존율 삭제
                        $(this).parents(".dpTempRow").remove();

                    }
                }
            },
            dpndnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".dpTempRow").size();

                        if (rowCnt < 3) {
                            var temp = document.getElementById("dpndnRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dpndnRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dpTempDiv").append(newNode);
                            $("#" + newNodeId).children().find("input").val("");
                            $("#" + newNodeId).children().find(".dpndnPlus").hide();
                            $("#" + newNodeId).children(".closeLabel").children(".close").css("display", "block");
                            $("#dpTempDiv").children().eq(0).find(".closeLabel").hide();
                            $('#dpTempDiv').children().not(':eq(0)').find(".closeLabel").show();
                        } else {
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
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
            },cbsnCd: {
                event: {
                    click: function () {
                        var cbsnVal = $("input[name=cbsnCd]:checked").val();
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
            submit : {
                event : {
                    click : function(){
                        var agree = $("#agreeChk").val();
                        var qltyPicCnt = $(".qltyPicCnt").val();
                        var hqZipcode = $("#hqZipcode").val();
                        var hqDtlAddr = $("#hqDtlAddr").val();
                        var mainAddr = $("#mainAddr").val();
                        var subAddr = $("#subAddr").val();
                        var rprsntApprvYn = $("input[name='rprsntApprvYn']:checked").val();
                        var appctnRsnCd = $("input[name='appctnRsnCd']:checked").val();
                        var cbsnCd = $("input[name='cbsnCd']:checked").val();
                        var appctnTypeCd = $("input[name='appctnTypeCd']:checked").val();

                        var dlvrySize = $(".dlvryCmpnNm").length;
                        for(var i =0; i<dlvrySize; i++){
                            if(!$(".dlvryCmpnNm").eq(i).val()){
                                alert("업체명을 입력해주세요.");
                                $(".dlvryCmpnNm").eq(i).focus();
                                return false;
                            }if(!$(".dlvryRate").eq(i).val()){
                                alert("매출비중을 입력해주세요.");
                                $(".dlvryRate").eq(i).focus();
                                return false;
                            }
                        }
                        //완성차 의존율
                        var dpndnSize = $(".dpndnCmpnNm").length;
                        for(var i =0; i<dpndnSize; i++){
                            if(!$(".dpndnCmpnNm").eq(i).val()){
                                alert("업체명을 입력해주세요.");
                                $(".dpndnCmpnNm").eq(i).focus();
                                return false;
                            }if(!$(".dpndnRate").eq(i).val()){
                                alert("의존율을 입력해주세요.");
                                $(".dpndnRate").eq(i).focus();
                                return false;
                            }
                        }
                        if(!qltyPicCnt){
                            alert("품질담당 인원을 입력해주세요.");
                            $(".qltyPicCnt").focus();
                            return false;
                        }

                        if (!hqZipcode) {
                            alert("공장주소를 입력해주세요.");
                            $("#hqZipcode").focus();
                            return false;
                        }

                        if (!hqDtlAddr) {
                            alert("상세주소를 입력해주세요.");
                            $("#hqDtlAddr").focus();
                            return false;
                        }

                        if (mainAddr == "선택") {
                            alert("소재 지역을 선택해주세요.");
                            $("#mainAddr").focus();
                            return false;
                        }

                        if (subAddr == "") {
                            alert("소재 지역을 선택해주세요.");
                            $("#subAddr").focus();
                            return false;
                        }

                        if (!rprsntApprvYn) {
                            alert("대표자 승인여부를 선택해주세요.");
                            $("input[name='rprsntApprvYn']").focus();
                            return false;
                        }

                        if (!appctnRsnCd) {
                            alert("신청사유를 선택해주세요.");
                            $("input[name='appctnRsnCd']").focus();
                            return false;
                        }

                        if (!cbsnCd) {
                            alert("업종을 선택해주세요.");
                            $("input[name='cbsnCd']").focus();
                            return false;
                        }

                        if (cbsnCd == 'TEC_GUIDE_INDUS01') {
                            if (!$("input[name='etcNm']").val()) {
                                alert("업종을 입력해주세요.");
                                $("input[name='etcNm']").focus();
                                return false;
                            }
                        }

                        if (!appctnTypeCd) {
                            alert("신청사항을 선택해주세요.");
                            $("input[name='appctnTypeCd']").focus();
                            return false;
                        }

                        var searchFile = $("#searchFile").val();
                        if (!searchFile) {
                            alert("회사소개서를 등록해주세요.");
                            $("#searchFile").focus();
                            return false;
                        }

                        var searchFile1 = $("#searchFile1").val();
                        if (!searchFile1) {
                            alert("개선활동 추진계획서를 등록해주세요.");
                            $("#searchFile1").focus();
                            return false;
                        }

                        if(agree == "N"){
                            alert("약관에 동의해주세요");
                            $("#agreeChk").focus();
                            return false;
                        }

                        if(confirm("위 정보로 사업을 신청하시겠습니까?")){
                            $(".loading-area").stop().fadeIn(200);
                            cmmCtrl.fileFrm(function(data){
                                $(".loading-area").stop().fadeOut(200);
                                //콜백함수. 페이지 이동
                                location.replace("./complete");
                            }, "./insert", $formObj, "json");
                        }
                    }
                }
            },telRex : {
                /**
                 * 일반 전화번호 input 이벤트
                 */
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
            searchFile : {
                event : {
                    change : function() {
                        extnCheck(this, "jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip", 50);
                    }
                }
            },
            cancelApply : {
                event : {
                    click : function(e){
                        if(e){
                            if(confirm("현재 입력된 정보는 초기화됩니다. 계속하시겠습니까?")){
                                location.href="./content";
                            }
                        }
                    }
                }
            },
            addr : {
                event : {
                    change : function(){
                        $("#cmpnAddrSameYn").val('N');
                        $("#cmpnAddrSameYn").attr("checked", false);
                    }
                }
            }
        },
        immediately : function() {
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});