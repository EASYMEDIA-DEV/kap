define(["ezCtrl", "ezVald", "ezFile" ], function(ezCtrl, ezVald ) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKManagementCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
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
                $('#'+fileId).closest(".form-group").find('.empty-txt').toggleClass("file-name")
                $('#'+fileId).closest(".form-group").find('.empty-txt').text(obj.files[0].name);
            }
        }
    };

    // 이메일 체크
    var emailChk = function(email){
        var mailPattern = /^(?!.*\.{2,})([^@&'=+,<>\s]+)@([^@&'=+,<>\s]+)\.([^@&'=+,<>\s]+)([a-zA-Z가-힣]+)$/;

        if (!mailPattern.test(email)) {
            alert("이메일 형태를 확인해주세요.");
            return false;
        }
        return true;
    };

    var schlChk = function(SchlNm){
        var emptyPattern = /^\s*$/;

        if(emptyPattern.test(SchlNm)){
            alert("학교를 입력해주세요.");
            return false;
        }
        return true;
    };

    var grdChk = function (grd){
        var grdPattern = /^[1-9]$/;

        if(!grdPattern.test(grd)){
            alert("학년을 입력해주세요.");
            return false;
        }
        return true;
    };

    // set model
    ctrl.model = {
        id : {
            emailDomain : {
                event : {
                    change : function () {
                        var chkDomain = $("#emailDomain option:selected").html();
                        if(chkDomain == "직접입력"){
                            $("#rdEmaildomain").val('');
                        }else{
                            $("#rdEmaildomain").val(chkDomain);
                        }
                    }
                },
            },
            rdEmaildomain : {
                    event : {
                        input : function(){
                            var selectBox = $(this).closest('.form-input').next('.form-select').find('#emailDomain');
                            selectBox.val("default");
                        }
                    }
            },
            infoSameChk : {
                event : {
                    click : function () {
                        var name = $("#sameNm").val();
                        var hpNo = $("#sameHpNo").val();
                        var email = $("#sameEmail").val();
                        var emailId = email.split('@')[0];
                        var emailDomain = email.split('@')[1];
                        var check = $('#infoSameChk').is(':checked');

                        if (check) {
                            $("#rdName").val(name);
                            $("#rdHpNo").val(hpNo);
                            $("#rdEmailId").val(emailId);
                            $("#rdEmaildomain").val(emailDomain);
                        }else {
                            $(".infoSame").val("");
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
                            } else if (data.resultCode == 150) {
                                alert('위원회원은 해당 서비스를 이용할 수 없습니다.');
                            } else if (data.resultCode == 300) {
                                if (confirm("이미 신청한 공모전입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
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
                        
                        //팀장 이메일 셋
                        var rdEmail = $("#rdEmailId").val() + '@' + $("#rdEmaildomain").val();
                        $(':input[name="rdEmail"]').val(rdEmail);

                        // 팀원 이메일 셋
                        var partEmailIds = $('.partEmailId').map(function() {
                            return $(this).val();
                        }).get();

                        var partEmailDomains = $('.partEmailDomain').map(function() {
                            return $(this).val();
                        }).get();

                        // $('.Participant').each(function(index) {
                        //     var partEmail = partEmailIds[index] + '@' + partEmailDomains[index];
                        //     $('[name="partList[' + index + '].email"]').val(partEmail);
                        // });

                        // 유효성 체크
                        var valid = true;
                        var rdName = $("#rdName").val();
                        var rdHpNo = $("#rdHpNo").val();
                        var rdGrd = $(':input[name="rdGrd"]').val();
                        var rdSchlNm = $(':input[name="rdSchlNm"]').val();
                        var ptcptType = $(':input[name="ptcptType"]').val();
                        var themeCd =  $(":input:radio[name='themeCd']").is(":checked");
                        var dtlCntn = $("textarea").val();

                        var nmRegExp = /^[a-zA-Z가-힣]+$/;
                        var phNoRegExp = /^([0]{1}[0-9]{1,2})-([1-9]{1}[0-9]{2,3})-([0-9]{4})|(15|16|18)(00|70|44|66|77|88)-[0-9]{4}$/;

                        if( valid ) {
                            if (!nmRegExp.test(rdName)) {
                                alert("이름을 입력해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if (!phNoRegExp.test(rdHpNo)) {
                                alert("휴대번호를 입력해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if(!emailChk(rdEmail)){
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if(!schlChk(rdSchlNm)){
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if(!grdChk(rdGrd)){
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if(!ptcptType){
                                alert("참여구분을 선택해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if(!themeCd){
                                alert("주제를 선택해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if(!dtlCntn){
                                alert("세부 내용을 입력해주세요.");
                                valid = false;
                                return false;
                            }
                        }

                        if( valid ) {
                            if($(".Participant").length>0){

                                $('.Participant').each(function(index) {

                                    var partNm = $(".name").eq(index).val();
                                    if (!nmRegExp.test(partNm)) {
                                        alert("팀원 이름을 입력해주세요.");
                                        valid = false;
                                        return false;
                                    }

                                    var partPhNo = $(".hpNo").eq(index).val();
                                    if(!phNoRegExp.test(partPhNo)){
                                        alert("팀원 휴대번호를 입력해주세요.")
                                        valid = false;
                                        return false;
                                    }

                                    var partEmail = partEmailIds[index] + '@' + partEmailDomains[index];
                                    $('[name="partList[' + index + '].email"]').val(partEmail);
                                    if(!emailChk(partEmail)){
                                        valid = false;
                                        return false;
                                    }

                                    var partSchlNm = $(".schlNm").eq(index).val();
                                    if(!schlChk(partSchlNm)){
                                        valid = false;
                                        return false;
                                    }

                                    var grd = $(".grd").eq(index).val();
                                    if(!grdChk(grd)){
                                        valid = false;
                                        return false;
                                    }
                                })
                            }
                        }

                        var file = $('input[type=file]');

                        if ( valid ){
                            file.each(function(i) {
                                if (!$(this).val()) {
                                    alert('신청서류를 등록해주세요.');
                                    valid = false;
                                    return false;
                                }
                            });
                        }

                        if (valid) {
                            //이용약관 체크여부
                            if ($('#agreeChk').is(':checked')) {

                                cmmCtrl.fileFrm(function(data){
                                    //콜백함수. 페이지 이동
                                    if (data.actCnt == 999) {
                                        if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                            location.href = "/my-page/main";
                                        }
                                    } else {
                                        if (confirm("위 정보로 사업을 신청하시겠습니까?")) {
                                            location.href = "./complete?episdSeq=" + $('input[name=episdSeq]').val();
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
            infoSame : {
                event : {
                    input : function () {
                        $('#infoSameChk').prop('checked', false);
                    }
                }
            },
            fileDelete : {
                event : {
                    click : function() {
                        $(this).closest(".form-group").find("input[type=file]").val("");
                        $(this).closest(".form-group").find('.file-list').empty();
                    }
                }
            },
            ptcptType : {
                event : {
                    click : function () {
                        if( $(this).val() == "WBK_PTN02" ){
                            $("#ptcptBox").remove();
                        }
                    }
                }
            }
        },
        immediately : function(){
            $formObj.validation({
                after : function() {
                    var isValid = true;

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){

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

