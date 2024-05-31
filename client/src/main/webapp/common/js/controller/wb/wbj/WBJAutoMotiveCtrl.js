define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJAutoMotiveCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 10;
    var fileInput = "";

    $(".file-list").hide();
    // 파일 체크
    var extnCheck = function(obj, extns, maxSize, maxLengh)
    {
        var fileObj = jQuery(obj).val(), isFile = true;
        var fileId = obj.id;
        var fileLengh = document.querySelectorAll('p.file-name').length;;
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
                $('#'+fileId).closest(".form-group").find('.file-list').empty();
                alert('첨부 가능한 파일 확장자가 아닙니다.');

                isFile = false;
            } else {
                //파일용량 체크 파일갯수 체크
                if (typeof obj.files != "undefined")
                {
                    var fileSize = file.size;
                    var maxFileSize = maxSize * 1024 * 1024;
                    if (fileSize > maxFileSize)
                    {
                        $('#'+fileId).val("");
                        $('#'+fileId).closest(".form-group").find('.file-list').empty();
                        alert("첨부파일 용량은 최대 " + maxSize + "MB까지만 등록 가능합니다.");
                        isFile = false;
                    }

                    if(fileLengh == maxLengh){
                        alert("첨부파일은 최대 " + maxLengh + " 개만 등록 가능합니다.");
                        isFile = false;
                    }
                }

            }
            if (isFile) {
                $(".file-list").show();
                var fileHtml = '<p class="file-name"><span class="name">' + fileName + '</span>';
                fileHtml += '<span class="unit">.' + fileExtn + '</span></p>';
                fileHtml += '<button class="btn-delete fileDelete" title="파일 삭제하기" type="button"></button>';

                $('#'+fileId).closest(".form-group").find('.file-list').append(fileHtml);
                // 파일 추가되면 class 추가
                $('#'+fileId).closest(".form-group").find('.file-list-area').addClass('attached');

                fileInput = jQuery(obj).clone(true);
            }
        }
    };

    // set model
    ctrl.model = {
        id : {
            goHome : {
              event : {
                  click : function(){
                      if(confirm("현재 입력된 정보는 초기화됩니다. 계속하시겠습니까?\n")){
                          location.href = "/coexistence/automotive/content";
                      }
                  }
              }
            },
            infoSameChk :{
                event : {
                    click : function(){
                        var checked = $('#infoSameChk').is(':checked');
                        var name = $("#name").val();
                        var hpNo = $("#hpNo").val();
                        var cmpnNm = $("#cmpnNm").val();
                        var deptNm = $("#deptCdNm").val();
                        var pstnCdNm = $("#pstnCdNm").val();

                        if(checked){
                            $("#rcmndName").val(name);
                            $("#rcmndHpNo").val(hpNo);
                            $("#rcmndCmpnNm").val(cmpnNm);
                            $("#rcmndDeptNm").val(deptNm);
                            $("#rcmndPstnNm").val(pstnCdNm);
                        }else{
                            $("#rcmndName").val("");
                            $("#rcmndHpNo").val("");
                            $("#rcmndCmpnNm").val("");
                            $("#rcmndDeptNm").val("");
                            $("#rcmndPstnNm").val("");
                        }
                    }
                }
            },
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        $('#firstIndex').val(0);
                        $('#pageIndex').val(1);
                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리

                            $('.divide').empty();
                            $('.divide').append(respObj);
                            //addCount = addCount;
                            //전체 갯수
                            //var totalCnt = $(respObj).eq(0).data("totalCount");
                            var totalCnt = $("#totalCnt").val();
                            totalCnt = (totalCnt === undefined)? 0 :totalCnt;

                            //var totalCnt = $("#totalCount").val();

                            $("#totalCount").val(totalCnt);

                            if (($("#pageIndex").val()*10) >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('.add-load').show();
                                $('.item-count').text("("+ ($("#pageIndex").val()*10) + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            //선택버튼 클릭시
            selectChoose : {
                event : {
                    click: function(){
                        if($('input:checkbox[name=chk]:checked').length > 1){
                            alert("부품사를 1개만 선택해주세요.")
                        }else if($('input:checkbox[name=chk]:checked').length == 0){
                            alert("부품사를 선택해주세요.");
                        }else{
                            var clickObj = {};
                            $("#newBsnmNo").val(ctrl.obj.find("input[name=chk]:checked").val());
                            $("#bsnmNoNm").val($.trim(ctrl.obj.find("input[name=chk]:checked").parents("li").find(".srchListView").text()));
                            $("#ctgryNm").val($.trim(ctrl.obj.find("input[name=chk]:checked").parents("li").find(".ctgryNm").val()));
                            $(".btn-role-close").click();
                        }
                    }
                }
            },
            emailSelect : {
                event : {
                    change : function() {
                        var selectValue = document.getElementById('emailSelect').value;
                        $(".secondEmail").val(selectValue)
                    }
                }
            },
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
                        if(episdSeq == ''){
                            alert("현재 신청 가능한 사업이 없습니다.");
                        }else {
                            //신청페이지 로직점검
                            cmmCtrl.paramAjax(function (data) {
                                if (data.resultCode == 999) {
                                    if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                        location.href = "/login?rtnUrl=" + encodeURIComponent(window.location.pathname);
                                    }
                                } else if (data.resultCode == 100) {
                                    alert('해당 사업은 부품사 회원만 신청 가능합니다.');
                                } else if (data.resultCode == 150) {
                                    alert('위원회원은 해당 서비스를 이용할 수 없습니다.');
                                } else if (data.resultCode == 190) {
                                    alert('1,2차 부품사만 신청가능합니다.');
                                } else if (data.resultCode == 400) {
                                    alert('소속 부품사의 사업자등록번호로 이미 신청한 사업입니다.');
                                } else if (data.resultCode == 300) {
                                    if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                        location.href = "/my-page/coexistence/list";
                                    }
                                } else if (data.resultCode == 200) {
                                    location.href = "./step1?episdSeq=" + episdSeq;
                                }
                            }, "./applyChecked", param, "json", false, false, "get");
                        }
                    }
                }
            },
            infoSameChk :{
                event : {
                    keyup : function() {
                        document.getElementById("infoSameChk").checked = false;
                    }
                }
            },
            bsnmNoBtn : {
                event : {
                    click : function(e) {
                        openPopup('partCompanySrchPop', e);
                    }
                }
            },
            addMore : {
                event : {
                    click : function() {

                        var pageIndex = $('#pageIndex').val();
                        $('#pageIndex').val(++pageIndex);

                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.divide').empty();
                            $('.divide').append(respObj);
                            addCount = 10+addCount;
                            //전체 갯수
                            var totalCnt = $("#totalCnt").val();

                            $("#totalCount").val(totalCnt);


                            if (($('#pageIndex').val()*10) >= totalCnt) {
                                $('.add-load').hide();
                            } else {

                                $('.item-count').text("("+ ($('#pageIndex').val()*10) + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            insert : {
                event : {
                    click : function() {
                        var $formObj = $('#frmData');
                        var file = $formObj.find('input[type=file]');
                        var valid = true;
                        var email = $(".firstEmail").val() + "@" + $(".secondEmail").val();
                        $(".newEmail").val(email);

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
                                if(confirm("매출액 등이 최신 정보여야 합니다. 현재 정보로 신청하시겠습니까?\n")){
                                    $(".loading-area").stop().fadeIn(200);
                                    cmmCtrl.fileFrmAjax(function(data){
                                        console.log(JSON.stringify(data));
                                        var appctnSeq = data.WBJAcomDTO.appctnSeq;
                                        $(".loading-area").stop().fadeOut(200);

                                        //콜백함수. 페이지 이동
                                        location.href = "./complete";
                                    }, "./insert", $formObj, "json");
                                }
                            } else {
                                alert('약관에 동의해주세요.')
                            }
                        }
                    }
                }
            },
            searchFile : {
                event : {
                    change : function() {
                        extnCheck(this, "jpg,jpeg,png,pdf,ppt,pptx,xlsx,doc,docx,hwp,hwpx,txt,zip", 50, 1);
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
            }
            /*searchFile : {
                event : {
                    click : function() {
                        imageText = $(this).attr("id");
                    }
                }
            }*/
        },
        immediately : function(){
            if ($('#msg').val()) {
                alert($('#msg').val());
                location.href="/";
            }

            $formObj.find("input[type=file]").fileUpload({
                loading:false,
                sync:true
            },function(data){
                console.log(data);
                //해당 input file 객체에 data(tempFileData) 응답 값이 저장
                $('.empty-txt').text(data[0].orgnFileNm);
            });
            $('#firstIndex').val(addCount);
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

