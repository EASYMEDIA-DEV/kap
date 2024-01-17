define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpb/MPBSmartFactoryCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // 파일 체크
    var extnCheck = function(obj, extns, maxSize)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;

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
                $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass('attached');
                $('#'+fileId).closest(".form-group").find('.file-list-area .file-list').remove();
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
                        $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass('attached');
                        $('#'+fileId).closest(".form-group").find('.file-list-area .file-list').remove();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }
                }
            }

            if (isFile) {
                var fileName = obj.files[0].name;
                var lastIdx = fileName.lastIndexOf('.');
                var onlyFileNm = fileName.substring(0, lastIdx);
                var extension = fileName.substring(lastIdx);

                $('#'+fileId).closest(".form-group").find('.file-list-area.file-list').remove();
                var html = '<div class="file-list">';
                html += `<p class="file-name"><span class="name">${onlyFileNm}</span><span class="unit">${extension}</span></p>`;
                html += '<button class="btn-delete fileRemove" title="파일 삭제하기" type="button"></button>';
                html += '</div>';

                $('#'+fileId).closest(".form-group").find('.file-list-area').append(html);
                $('#'+fileId).closest(".form-group").find('.file-list-area').addClass('attached');
            }
        }
    };

    /* 총 가격 계산 */
    let sumPriceForTtlPmt = function(event) {
        let panelBody = $(event).closest('.tab-con');

        let totalPrice = 0;

        panelBody.find('.priceVal').each(function(idx, el) {
            let element = $(el);
            let price = Number(element.val().replaceAll(",","")) || Number(0);
            totalPrice += price;
        });

        if(panelBody.find('.ttlPmt') != undefined){
            panelBody.find('.ttlPmt').val(totalPrice.toLocaleString('ko-KR'));
        }
    }

    let removeComma = function(commaObj) {
        if(commaObj != null && commaObj.length != 0 && commaObj != undefined) {
            $(commaObj).find('.comma').each(function(idx, el) {
                let price = $(el).val().replaceAll(",", "");
                $(el).val(price);
            });
        }
    }

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
            priceVal : {
                event : {
                    change : function() {
                        sumPriceForTtlPmt(this);
                    },
                    keyup : function() {
                        sumPriceForTtlPmt(this);
                    }
                },
            },
            btnSpprtPop : {
                event : {
                    click : function() {
                        openPopup('paymentInfoManagPopup', this);
                    }
                }
            },
            fileRemove : {
                event : {
                    click : function() {
                        var fileGroup = this.closest(".form-group");

                        $(fileGroup).find('.file-list-area').removeClass('attached');
                        $(fileGroup).find('.file-list-area .file-list').remove();
                        $(fileGroup).find('input[type=file]').val('');

                    }
                }
            },
            btnUpdate : {
                event : {
                    click : function() {

                        var $formObj = $(this).closest('form');

                        var file = $formObj.find('input[type=file]');
                        var valid = true;

                        /*file.each(function(i) {
                            if (!$(this).val()) {
                                alert('신청서류를 모두 등록해주세요.');
                                valid = false;
                                return false;
                            }
                        });*/

                        if(!$formObj.find("#taskCd").val()) {
                            alert('과제명을 입력해주세요.');
                            valid = false;
                            return false;
                        }
                        if(!$formObj.find("#bsnTypeCd").val()) {
                            alert('사업유형을 입력해주세요.');
                            valid = false;
                            return false;
                        }
                        if(!$formObj.find("#smtfnPrsntCd").val()) {
                            alert('스마트화 현재 수준을 입력해주세요.');
                            valid = false;
                            return false;
                        }
                        if(!$formObj.find("#smtfnTrgtCd").val()) {
                            alert('스마트화 목표 수준을 입력해주세요.');
                            valid = false;
                            return false;
                        }

                        //이용약관 체크여부
                        if (valid) {
                            //이용약관 체크여부
                            cmmCtrl.fileFrm(function(data){
                                if (data.actCnt == 999) {
                                    if (confirm("입력하신 종된사업장번호로 신청한 이력이 있습니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                        location.href = "/my-page/main";
                                    }
                                } else {
                                    location.reload();
                                }
                            }, "./update", $formObj, "json");
                        }

                    }
                }
            },
            btnSpprtUpdate : {
                event : {
                    click : function() {

                        var formGiveType;
                        $('.spprt-tab-btn').each(function(idx, el) {
                            if($(el).hasClass('active')){
                                formGiveType = $(el).data('giveType');
                            }
                        })

                        var $formObj = $('.paymentInfoManagPopup').find(`form[data-give-type=${formGiveType}]`);

                        /* 금액 ',' 제거 */
                        removeComma($formObj);

                        var file = $formObj.find('input[type=file]');
                        var valid = true;

                        /*file.each(function(i) {
                            if (!$(this).val()) {
                                alert('신청서류를 모두 등록해주세요.');
                                valid = false;
                                return false;
                            }
                        });*/



                        cmmCtrl.fileFrm(function(data){
                            //location.reload();
                        }, "./update", $formObj, "json");

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
            downloadForm : {
                event : {
                    click : function() {
                        var fielFieldValue = $(this).data("fileSeq");
                        cmmCtrl.paramAjax(function(data){
                            if(data.rtnData != null)
                            {
                                var fileInfo = {
                                    url : data.rtnData[0].webPath,
                                    name: data.rtnData[0].orgnFileNm,
                                    size: data.rtnData[0].fileSize,
                                    fileSeq: data.rtnData[0].fileSeq,
                                    fileOrd: data.rtnData[0].fileOrd,
                                    fileDsc: data.rtnData[0].fileDsc,
                                    type: data.rtnData[0].fileExtn,
                                    webPath: data.rtnData[0].webPath
                                };
                                location.href = `/file/download?fileSeq=${fileInfo.fileSeq}&fileOrd=${fileInfo.fileOrd}`
                            }
                        }, "./file/list", { fileSeq : fielFieldValue }, "json");
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
            cmmCtrl.setCalendar()
        }
    };



    // execute model
    ctrl.exec();
    return ctrl;
});

