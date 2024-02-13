define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbb/WBBManagementCtrl"
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

    var fnSleep  = function(delay)
    {
        var start = new Date().getTime();
        while (start + delay > new Date().getTime());
    };

    // set model
    ctrl.model = {
        id : {
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
                           } else if (data.resultCode == 300) {
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

                        if (valid) {
                            //이용약관 체크여부
                            if ($('#agreeChk').is(':checked')) {
                                $(".loading-area").stop().fadeIn(200);
                                cmmCtrl.fileFrm(function(data){
                                    //콜백함수. 페이지 이동
                                    if (data.actCnt == 999) {
                                        if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                            location.href = "/my-page/coexistence/list";
                                        }
                                    } else {
                                        if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                            location.href = "./complete?episdSeq=" + $('input[name=episdSeq]').val();
                                        }
                                    }
                                    $(".loading-area").stop().fadeOut(200);
                                }, "./insert", $formObj, "json");
                            } else {
                                alert('약관에 동의해주세요.');
                            }
                        }
                    }
                }
            },
            insertSkip : {
                event : {
                    click : function() {
                        if(confirm("매출액 등이 최신 정보여야 합니다.\n현재 정보로 신청하시겠습니까?")) {
                            $(".loading-area").stop().fadeIn(200);
                            cmmCtrl.fileFrm(function (data) {
                                //콜백함수. 페이지 이동
                                if (data.actCnt == 999) {
                                    if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                        location.href = "/my-page/coexistence/list";
                                    }
                                } else {
                                    location.href = "./complete?episdSeq=" + $('input[name=episdSeq]').val();
                                }
                                $(".loading-area").stop().fadeOut(200);
                            }, "./insert", $formObj, "json");
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
            downloadAll : {
                event : {
                    click : function() {
                        /*
                        $('.optnFile').each(function() {
                           // location.href = "/file/download?fileSeq="+$(this).val()+"&fileOrd=0";
                           fnSleep(3000); //각 파일별 시간 텀을 준다

                        });
                        */
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

