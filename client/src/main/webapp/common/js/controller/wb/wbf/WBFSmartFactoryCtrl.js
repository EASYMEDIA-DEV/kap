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
                fileInput = jQuery(obj).clone(true);
                $('#'+fileId).closest(".form-group").find('.empty-txt').text(obj.files[0].name);
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
                        var ctgryCd = $('#ctgryCd').val();

                        if(confirm("매출액 등이 최신 정보여야 합니다.\n현재 정보로 신청하시겠습니까?")) {
                            if(ctgryCd == 'COMPANY01002' || ctgryCd == 'COMPANY01001'){
                                location.href = "./step2?episdSeq="+episdSeq;
                            } else {
                                alert("1, 2차 부품사만 신청 가능합니다.\n");
                            }
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
                                cmmCtrl.fileFrm(function(data){
                                    //콜백함수. 페이지 이동
                                    if (data.actCnt == 999) {
                                        if (confirm("입력하신 종된사업장번호로 신청한 이력이 있습니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                            location.href = "/my-page/coexistence/list";
                                        }
                                    } else {
                                        if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                            location.href = "./complete?episdSeq="+$('input[name=episdSeq]').val();
                                        }
                                    }
                                }, "./insert", $formObj, "json");
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
                                location.href = `/file/download?fileSeq=${fileInfo.fileSeq}&fileOrd=${fileInfo.fileOrd}`
                            }
                        }, "/file/list", { fileSeq : $(this).data("fileSeq") }, "json");
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

