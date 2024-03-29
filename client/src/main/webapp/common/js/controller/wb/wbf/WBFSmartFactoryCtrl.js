define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbf/WBFSmartFactoryCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 3;
    var fileInput = "";

    // 파일 체크
    var extnCheck = function(obj, extns, maxSize)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;
        var fileArea = $('#'+fileId).closest(".form-group").find('.file-btn-area');

        if (!fileObj)
        {
            isFile = false;
            $('#'+fileId).remove();
            fileArea.prepend(fileInput);
        }
        else
        {
            var file;
            file = obj.files[0];

            var fileExtn = file.name.split(".").pop();
            var fileName = file.name.split(".")[0];

            if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                //파일확장자 체크
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                $('#'+fileId).closest(".form-group").find('.file-list').remove();
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
                        $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                        $('#'+fileId).closest(".form-group").find('.file-list').remove();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }
                }
            }

            if (isFile) {
                $('#'+fileId).closest(".form-group").find('.file-list').remove();

                fileInput = jQuery(obj).clone(true);
                var fileHtml = '<div class="file-list"><p class="file-name"><span class="name">' + fileName + '</span>';
                fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button></div>';
                $('#'+fileId).closest(".form-group").find('.file-list-area').addClass("attached");
                $('#'+fileId).closest(".form-group").find('.file-list-area').append(fileHtml);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
            nextBtnStep2 : {
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
                        var episdSeq = $(this).data("episdSeq");
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
                           }else if (data.resultCode == 300) {
                                if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                    location.href = "/my-page/coexistence/list";
                                }
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
                        var file = $('input[type=file]');
                        var valid = true;

                        file.each(function(i) {
                            if (!$(this).val()) {
                                alert('신청서류를 모두 등록해주세요.');
                                valid = false;
                                return false;
                            }
                        });

                        if(!$("#taskCd").val()) {
                            alert('과제명을 입력해주세요.');
                            valid = false;
                            return false;
                        }
                        if(!$("#bsnTypeCd").val()) {
                            alert('사업유형을 입력해주세요.');
                            valid = false;
                            return false;
                        }
                        if(!$("#smtfnPrsntCd").val()) {
                            alert('스마트화 현재 수준을 입력해주세요.');
                            valid = false;
                            return false;
                        }
                        if(!$("#smtfnTrgtCd").val()) {
                            alert('스마트화 목표 수준을 입력해주세요.');
                            valid = false;
                            return false;
                        }

                        //이용약관 체크여부
                        if (valid) {
                            //이용약관 체크여부
                            if ($('#agreeChk').is(':checked')) {
                                $(".loading-area").stop().fadeIn(200);

                                var sbrdnBsnmNo = $("#sbrdnBsnmNo").val();
                                var episdSeq = $("#episdSeq").val();

                                var wBFBRegisterSearchDTO = {};
                                wBFBRegisterSearchDTO.sbrdnBsnmNo = sbrdnBsnmNo;
                                wBFBRegisterSearchDTO.episdSeq = episdSeq;

                                cmmCtrl.jsonAjax(function(data){
                                    let respData = JSON.parse(data);
                                    if(respData.respCnt == 100) {
                                        if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                            $(".loading-area").stop().fadeOut(200);
                                            location.href = "/my-page/coexistence/list";
                                        }
                                        return false;
                                    } else if(respData.respCnt == 200) {
                                        alert("해당 소속 부품사의 사업자등록번호로 이미 신청한 사업입니다.");
                                        $(".loading-area").stop().fadeOut(200);
                                        return false;
                                    } else if(respData.respCnt == 300) {
                                        alert("해당 소속 부품사의 사업자등록번호로 이미 신청한 사업입니다.\n (종된사업장번호 중복)");
                                        $(".loading-area").stop().fadeOut(200);
                                        return false;
                                    } else {
                                        if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                            cmmCtrl.fileFrm(function(data){
                                                if(data.respCnt > 0) {
                                                    $(".loading-area").stop().fadeOut(200);
                                                    location.href = `./complete?episdSeq=${episdSeq}&appctnSeq=${data.appctnSeq}`;
                                                }
                                            }, "./insert", $formObj, "json");
                                        }
                                        $(".loading-area").stop().fadeOut(200);
                                    }
                                },"./getSbrdmNoCheck",wBFBRegisterSearchDTO, "text");
                                $(".loading-area").stop().fadeOut(200);
                            } else {
                                alert('약관에 동의해주세요.');
                            }
                        }

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
            btnDownload : {
                event : {
                    click : function() {
                        $(".loading-area").stop().fadeIn(200);

                        cmmCtrl.paramAjax(function(data){
                            if(data != null && data.length > 0)
                            {
                                var fileInfo = {
                                    url : data[0].webPath,
                                    name: data[0].orgnFileNm,
                                    size: data[0].fileSize,
                                    fileSeq: data[0].fileSeq,
                                    fileOrd: data[0].fileOrd,
                                    fileDsc: data[0].fileDsc,
                                    type: data[0].fileExtn,
                                    webPath: data[0].webPath
                                };
                                $(".loading-area").stop().fadeOut(200);
                                location.href = `/file/download?fileSeq=${fileInfo.fileSeq}&fileOrd=${fileInfo.fileOrd}`
                            }
                            $(".loading-area").stop().fadeOut(200);
                        }, "/file/list", { fileSeq: $(this).data("fileSeq") }, "json").done(function() {
                            $(".loading-area").stop().fadeOut(200);
                        });
                    }
                }
            },
            downloadAll : {
                event : {
                    click : function() {
                        $(".loading-area").stop().fadeIn(200);
                        var optnFiles = $('.optnFile');
                        var numFiles = optnFiles.length;
                        var currentIndex = 0;

                        function downloadFile() {
                            var currentFile = $(optnFiles[currentIndex]);
                            location.href = "/file/download?fileSeq="+currentFile.val()+"&fileOrd=0";
                            // window.open("/file/download?fileSeq="+currentFile.val()+"&fileOrd=0", '_blank');

                            currentIndex++;

                            if (currentIndex < numFiles) {
                                setTimeout(downloadFile, 1500); // 2초 후에 다음 파일 다운로드
                            } else {
                                $(".loading-area").stop().fadeOut(200);
                            }
                        }

                        downloadFile();
                    }
                }
            },
            btnCancle : {
                event : {
                    click : function() {
                        if(confirm("입력된 내용은 저장되지 않습니다. 취소하시겠습니까?\n")) {
                            location.href = '/coexistence/smartFactory/content';
                        }
                    }
                }
            },
            fileDelete : {
                event : {
                    click : function() {
                        $(this).closest(".form-group").find("input[type=file]").val("");
                        $(this).closest(".form-group").find('.file-list-area').removeClass("attached");
                        $(this).closest(".form-group").find('.file-list').remove();
                    }
                }
            }
        },
        immediately : function(){
            if ($('#msg').val()) {
                alert($('#msg').val());
                location.href="/";
            }

            $('#firstIndex').val(addCount);
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

