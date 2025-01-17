define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/im/ima/IMAQaWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $formObj = ctrl.obj.find("form").eq(0);

    // function
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
            $('#'+fileId).closest(".form-group").find(".file-list-area").empty();

            var fileCnt = obj.files.length;

            //파일 개수 체크
            if(fileCnt > 5) {
                console.log("파일 6개 이상");
                $('#'+fileId).val("");
                $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
                alert('첨부파일은 최대 5개까지만 등록 가능합니다.');
            }
            else if(fileCnt > 1) {
                var file;
                for(var i = 0; i < fileCnt; i++) {
                    file = obj.files[i];

                    var fileExtn = file.name.split(".").pop();

                    if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                        //파일확장자 체크
                        $('#'+fileId).val("");
                        $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                        $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
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
                                $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                                $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
                                alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                                isFile = false;
                            }
                            else if(fileSize == 0) {
                                $('#'+fileId).val("");
                                $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                                $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
                                alert("빈 파일은 업로드가 불가능 합니다");
                                isFile = false;
                            }
                        }
                    }

                    if (isFile) {
                        var val = '<div class="file-list">' +
                            '               <p class="file-name">' +
                            '                   <span class="name">' + file.name.split(".")[0] + '</span>' +
                            '                   <span class="unit">.' + file.name.split(".").pop() + '</span>' +
                            '               </p>' +
                            '               <button class="btn-delete delFile" title="파일 삭제하기" type="button" value="' + file.size + '"></button>' +
                            '            </div>';
                        $('#'+fileId).closest(".form-group").find('.file-list-area').append(val);
                    }
                    else {
                        break;
                    }
                }
            }
            else if(fileCnt == 1) {
                var file;
                file = obj.files[0];

                var fileExtn = file.name.split(".").pop();

                if (extns.indexOf(fileExtn.toLowerCase()) < 0) {
                    //파일확장자 체크
                    $('#'+fileId).val("");
                    $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                    // $('#'+fileId).closest(".form-group").find('.empty-txt').text("선택된 파일 없음");
                    // $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                    $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
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
                            $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                            // $('#'+fileId).closest(".form-group").find('.empty-txt').text("선택된 파일 없음");
                            // $('#'+fileId).closest(".form-group").find('.file-list-area').removeClass("attached");
                            $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
                            alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                            isFile = false;
                        }
                        else if(fileSize == 0) {
                            $('#'+fileId).val("");
                            $('#'+fileId).closest(".form-group").find(".file-list-area").empty();
                            $('#'+fileId).closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
                            alert("빈 파일은 업로드가 불가능 합니다");
                            isFile = false;
                        }
                    }
                }

                if (isFile) {
                    var val = '<div class="file-list">' +
                        '               <p class="file-name">' +
                        '                   <span class="name">' + file.name.split(".")[0] + '</span>' +
                        '                   <span class="unit">.' + file.name.split(".").pop() + '</span>' +
                        '               </p>' +
                        '               <button class="btn-delete delFile" title="파일 삭제하기" type="button" value="' + file.size + '"></button>' +
                        '            </div>';
                    // $('#'+fileId).closest(".form-group").find('.file-list-area').addClass("attached");
                    $('#'+fileId).closest(".form-group").find('.file-list-area').append(val);
                }
            }
        }
    };


    // set model
    ctrl.model = {
        id : {

            parntCtgryCd : {
                event : {
                    change : function() {
                        var parntCtgryCd = document.getElementById('parntCtgryCd');
                        var selectFir = parntCtgryCd.options[parntCtgryCd.selectedIndex].value;
                        var ctgryCdOpsions = document.getElementsByClassName("ctgryCd");
                        var addOption;

                        if(selectFir) {
                            for(var i = 0, max = ctgryCdOpsions.length; i < max; i++) {
                                addOption = ctgryCdOpsions.item(i);
                                if(addOption.value.indexOf(selectFir) > -1 && addOption.value.length > 5) {
                                    addOption.style.display = "block";
                                }
                                else {
                                    addOption.style.display = "none";
                                }
                            }
                            $("#ctgryCd").prop("selectedIndex", 0);
                        }
                        else {
                            for(var i = 0, max = ctgryCdOpsions.length; i < max; i++) {
                                addOption = ctgryCdOpsions.item(i);
                                addOption.style.display = "none";
                            }
                            $("#ctgryCd").prop("selectedIndex", 0);
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

            qaSubmit : {
                event : {
                    click : function() {

                        if(confirm('문의를 등록하시겠습니까?')){
                            if ($("#parntCtgryCd").val() == '') {
                                alert(msgCtrl.getMsg("fail.im.ima.al_002"));
                                $("#parntCtgryCd").focus();
                                return false;
                            }
                            if ($("#ctgryCd").val() == '') {
                                alert(msgCtrl.getMsg("fail.im.ima.al_002"));
                                $("#ctgryCd").focus();
                                return false;
                            }
                            if ($("#titl").val() == '') {
                                alert(msgCtrl.getMsg("fail.im.ima.al_003"));
                                $("#titl").focus();
                                return false;
                            }
                            if ($("#cntn").val() == '') {
                                alert(msgCtrl.getMsg("fail.im.ima.al_004"));
                                $("#cntn").focus();
                                return false;
                            }

                            $("#parntCtgryNm").val($("#parntCtgryCd option:selected").text());
                            $("#ctgryNm").val($("#ctgryCd option:selected").text());

                            cmmCtrl.fileFrm(function(data){
                                console.log(data.respCnt);
                                if(data.respCnt > 0) {
                                    alert(msgCtrl.getMsg("fail.im.ima.al_001"));
                                    //콜백함수, 나의 1:1문의 > 해당 문의내역 상세페이지(FO-PC-MYP-07-003)로 링크(_Self)
                                    location.href = "/my-page/member/qa/list";
                                }
                                else {
                                    alert("문제가 발생하였습니다.");
                                    location.reload();
                                }
                            }, "/foundation/cs/qa/insert", $formObj, "json");
                        }
                    }
                }
            },

            cntn : {
                event : {
                    input : function() {
                        $("#cntnCnt").text($("#cntn").val().length);
                    }
                }
            }

        },
        classname : {

            delFile : {
                event : {
                    click : function() {
                        var dataTransfer = new DataTransfer();

                        var fileName = $(this).closest('.file-list').find('.name').text();
                        var fileExtension = $(this).closest('.file-list').find('.unit').text().substring(1);
                        var fileSize = $(this).val();

                        var obj = $("#searchFile")[0].files;
                        var fileCnt = obj.length;
                        var objArray = Array.from(obj);

                        var file;
                        var inputFileName;
                        var inputFileExtension;
                        var inputFileSize;

                        for (var i = 0; i < fileCnt; i++) {
                            file = obj[i];
                            inputFileName = file.name.split('.')[0];
                            inputFileExtension = file.name.split('.').pop();
                            inputFileSize = file.size;

                            if (inputFileName === fileName && inputFileExtension === fileExtension && inputFileSize == fileSize) {
                                objArray.splice(i, 1);
                                $(this).closest('.file-list').remove();
                                break;
                            }
                        }

                        objArray.forEach(file => { dataTransfer.items.add(file); });

                        $('#searchFile')[0].files = dataTransfer.files;

                        $("#searchFile").closest(".form-group").find(".file-list-area").empty();
                        $("#searchFile").closest(".form-group").find(".file-list-area").append('<p class="empty-txt">선택된 파일 없음</p>');
                    }
                }
            }

        },
        immediately : function() {



        }
    };


    ctrl.exec();

    return ctrl;
});

