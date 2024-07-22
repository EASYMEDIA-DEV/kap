define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbc/WBCSecurityCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 3;
    var fileInput = "";
    var firstFileFlag = 0;

    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipCode","bscAddr","dtlAddr");
                    }
                }
            },
            sameAsHQChk : {
                event : {
                    change : function() {
                        if($(this).is(':checked')){
                            var oriZipCode = $("#oriZipCode").val();
                            var oriBscAddr = $("#oriBscAddr").val();
                            var oriDtlAddr = $("#oriDtlAddr").val();

                            $("#zipCode").val(oriZipCode);
                            $("#bscAddr").val(oriBscAddr);
                            $("#dtlAddr").val(oriDtlAddr);
                        }else{
                            $("#zipCode").val('');
                            $("#bscAddr").val('');
                            $("#dtlAddr").val('');
                        }
                    }
                }
            },
            nextBtn : {
                event : {
                    click : function() {
                        var episdSeq = $(this).data("episdSeq");
                        if(confirm("매출액 등이 최신 정보여야 합니다.\n현재 정보로 신청하시겠습니까?")) {
                            location.href = "./step2?episdSeq="+episdSeq;
                        }
                    }
                }
            }
        },
        classname : {
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
            addMore : {
                event : {
                    click : function() {

                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.divide').append(respObj);
                            addCount = 3+addCount;
                            //전체 갯수
                            var totalCnt = $('#totalCnt').text();

                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('#firstIndex').val(addCount);
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "GET", "html");
                    }
                }
            },
            apply : {
                event : {
                    click : function() {
                        var episdSeq = $(this).data("episd");
                        var param = {
                            episdSeq : episdSeq
                        };

                        //신청페이지 로직점검
                        cmmCtrl.paramAjax(function(data){
                            if (data.resultCode == 999) {
                                if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                    location.href = "/login?rtnUrl="+encodeURIComponent(window.location.pathname);
                                }
                            } else if (data.resultCode == 100) {
                                alert('해당 사업은 부품사 회원만 신청 가능합니다.');
                            } else if (data.resultCode == 150) {
                                alert('위원회원은 해당 서비스를 이용할 수 없습니다.');
                            } else if (data.resultCode == 190) {
                                alert('1,2차 부품사만 신청가능합니다.');
                            } else if (data.resultCode == 200) {
                                location.href = "./step1?episdSeq="+episdSeq;
                            }
                        },"./applyChecked",param, "json", false, false, "get");
                    }
                }
            },
            insert : {
                event : {
                    click : function() {

                        if($("#zipCode").val() == "" || $("#bscAddr").val() == ""){
                            alert("구축사업장 주소를 입력해주세요.");
                            $("#zipCode").focus();
                            return;
                        }

                        if($("#dtlAddr").val() == ""){
                            alert("구축사업장 상세 주소를 입력해주세요.");
                            $("#dtlAddr").focus();
                            return;
                        }

                        if($("#searchFile").val() == "") {
                            alert("신청서류를 모두 등록해주세요.");
                            $("#searchFile").focus();
                            return;
                        }

                        //2024-07-22 첨부파일 유효성 추가 (이전에 첨부파일 1개 -> 5개 변경하면서 위의 유효성은 무의미해짐)
                        var isFileAttached = false;
                        $(".searchFile").each(function() {
                            if ($(this).val() != "") {
                                isFileAttached = true;
                                return false; // 하나라도 첨부되면 루프 중단
                            }
                        });
                        if (!isFileAttached) {
                            alert("신청서류를 모두 등록해주세요.");
                            focus(".fileForm");
                            return false;
                        }

                        //이용약관 체크여부
                        if ($('#agreeChk').is(':checked')) {
                            var pass = false;
                            var sbrdnBsnmNo = $("#sbrdnBsnmNo").val();

                            $(".loading-area").stop().fadeIn(200);

                            if(sbrdnBsnmNo == null || sbrdnBsnmNo == ''){
                                jQuery.ajax({
                                    url : "./getInsertBsnmNoCnt",
                                    type : "POST",
                                    timeout: 30000,
                                    data : $formObj.serializeArray(),
                                    dataType : "json",
                                    async: false,
                                    cache : false,
                                    success : function(data, status, xhr){
                                        if(data.respCnt == 999){
                                            if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                                $(".loading-area").stop().fadeOut(200);
                                                location.href = "/my-page/coexistence/list";
                                            }
                                        }else if(data.respCnt > 0 ){
                                            alert("해당 소속 부품사의 사업자등록번호로 이미 신청한 사업입니다.");
                                            $(".loading-area").stop().fadeOut(200);
                                            return;
                                        }else {
                                            pass = true;
                                        }
                                    }
                                });
                            }else{
                                jQuery.ajax({
                                    url : "./getInsertSbrdnBsnmNoCnt",
                                    type : "POST",
                                    timeout: 30000,
                                    data : $formObj.serializeArray(),
                                    dataType : "json",
                                    async: false,
                                    cache : false,
                                    success : function(data, status, xhr){
                                        if(data.respCnt == 999){
                                            if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                                $(".loading-area").stop().fadeOut(200);
                                                location.href = "/my-page/coexistence/list";
                                            }
                                        }else if(data.respCnt > 0 ){
                                            alert("해당 소속 부품사의 사업자등록번호로 이미 신청한 사업입니다.\n (종된사업장번호 중복)");
                                            $(".loading-area").stop().fadeOut(200);
                                            return;
                                        }else {
                                            pass = true;
                                        }
                                    }
                                });
                            }

                            if(pass){
                                if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                    $(".loading-area").stop().fadeIn(200);

                                    cmmCtrl.fileFrm(function (data) {
                                        $(".loading-area").stop().fadeOut(200);

                                        //콜백함수. 페이지 이동
                                        location.replace("./complete");
                                    }, "./insert", $formObj, "json");
                                }
                            }
                        } else {
                            alert('약관에 동의해주세요.')
                        }
                    }
                }
            },

            //2024-07-10 신청서 파일 5개까지 업로드
            searchFile : {
                event : {
                    change : function() {
                        var fileObj = jQuery(this).val(), isFile = true;
                        var fileId = this.id;
                        var fileArea = $('#'+fileId).closest(".form-group").find('.file-btn-area');
                        var extns = "jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip";
                        var maxSize = 50;

                        if (!fileObj)
                        {
                            isFile = false;
                            $('#'+fileId).remove();
                            fileArea.prepend(fileInput);
                        }
                        else
                        {
                            var file;
                            file = this.files[0];

                            var fileExtn = file.name.split(".").pop();
                            var fileName = file.name.split(".")[0];

                            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                                //파일확장자 체크
                                $('#'+fileId).val("");
                                alert('첨부 가능한 파일 확장자가 아닙니다.');
                                $("#"+fileId+"").removeAttr("data-gtm-form-interact-field-id");

                                isFile = false;
                                return false;
                            } else {
                                //파일용량 체크
                                if (typeof this.files != "undefined")
                                {
                                    var fileSize = file.size;
                                    var maxFileSize = maxSize * 1024 * 1024;

                                    if (fileSize > maxFileSize)
                                    {
                                        $('#'+fileId).val("");
                                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                                        $("#"+fileId+"").removeAttr("data-gtm-form-interact-field-id");

                                        isFile = false;
                                        return false;
                                    }
                                }
                            }
                            //파일 개수 체크
                            if($('#'+fileId).closest(".form-group").find(".file-list-area-wrap").children().length >= 5) {
                                alert('파일 첨부는 5개까지 가능합니다.');
                                isFile = false;
                                return false;
                            }

                            if (isFile) {

                                if(firstFileFlag == 0) {
                                    $('#' + fileId).closest(".form-group").find(".file-list-area-wrap").children().first().remove();
                                    firstFileFlag = -1;
                                }

                                fileInput = jQuery(this).clone(true);
                                var fileHtml = '<div class="file-list-area attached"><div class="file-list"><p class="file-name"><span class="name">' + fileName + '</span>';
                                fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button" data-file-id="' + fileId + '"></button></div></div>';
                                $('#' + fileId).closest(".form-group").find(".file-list-area-wrap").append(fileHtml);

                            }
                        }
                    }
                }
            },
            fileDelete : {
                event : {
                    click : function() {

                        $(this).closest(".file-list-area").remove();

                        var fileId = $(this).data("fileId");

                        $("#"+fileId+"").val("");
                        $("#"+fileId+"").removeAttr("data-gtm-form-interact-field-id");

                        if($("#"+fileId+"").closest(".form-group").find('.file-list-area-wrap').children().length < 1) {
                            var resetHtml = '<div class="file-list-area">'
                                + '<p class="empty-txt">선택된 파일 없음</p>'
                                + '</div>';
                            $("#"+fileId+"").closest(".form-group").find('.file-list-area-wrap').append(resetHtml);

                            firstFileFlag = 0
                        }

                    }
                }
            },
            fileForm : {
                event : {
                    click : function(e) {
                        var breckIdx = 0;
                        var idx = 0;

                        $(this).closest("div").find("input:file").each(function(){

                            if($(this).val() == ""){
                                breckIdx = idx;
                            }else{
                                idx++;
                            }

                        });

                        var forValue = $(this).attr("for").length == 15 ? $(this).attr("for").slice(0, -1) : $(this).attr("for");
                        $(this).attr("for", forValue + breckIdx);

                    }
                }
            }


        },
        immediately : function(){

            if ($('#msg').val()) {
                alert($('#msg').val());
                location.href="/";
            }
            /*$formObj.find("input[type=file]").fileUpload({
                loading:true,
                sync:true
            },function(data){
                // $(".loading-area").stop().fadeIn(200);
                $('.file-list').remove();
                var fileHtml = '<div class="file-list"><p class="file-name"><span class="name">' + data[0].orgnFileNm.replace(data[0].fileExtn,'') + '</span>';
                fileHtml += '<span class="unit">.' + data[0].fileExtn + '</span></p>';
                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button></div>';
                $('.file-list-area').addClass("attached");
                $('.file-list-area').append(fileHtml);
                // $(".loading-area").stop().fadeOut(200);
            });*/
            $('#firstIndex').val(addCount);
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

