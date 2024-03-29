define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpf/MPFCmssrAttendController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = jQuery("#formAttendSubmit");
    var $formObj5 = $("#formUploadSubmit");
    var chgkickImage =0;
    var chglvlImage =0;

    $('input:radio[name=atndcCd]').change( function () {
        if(this.value !='CMSSR_ATTEND_001') {
            $("#guidePartCmpn1").val("");
            $("#guidePartCmpn2").val("");
            $("#rgnsOne").text("-");
            $("#rgnsTwo").text("-");
            $("input[name=etcBsntrp]").val("");
            $("#guidePartCmpn1").attr('disabled', 'true');
            $("#guidePartCmpn2").attr('disabled', 'true');
            $("input[name=etcBsntrp]").attr('disabled', 'true');
        } else {
            $("#guidePartCmpn1").removeAttr('disabled');
            $("#guidePartCmpn2").removeAttr('disabled');
            $("input[name=etcBsntrp]").removeAttr('disabled');
        }
    });
    // set model
    ctrl.model = {
        id : {
            // do something...

            //부품사 선택1
            guidePartCmpn1 : {
                event : {
                    change : function () {
                        if($(this).val() == $("#guidePartCmpn2").val() && $(this).val()!="") {
                            alert("지도부품사2와 동일할수 없습니다.");
                            $("#guidePartCmpn2").val("");
                            $("#rgnsTwo").text("-");
                            $(this).val("");
                            return false;
                        }

                        if($(this).val() =="") {
                            $("#rgnsOne").text("-");
                        } else {
                            let split = $(this).val().split("/");
                            $("#rgnsOne").text(split[1]);
                        }
                    }
                }
            },

            //부품사 선택2
            guidePartCmpn2 : {
                event : {
                    change : function () {
                        if($(this).val() == $("#guidePartCmpn1").val()) {
                            alert("지도부품사1와 동일할수 없습니다.");
                            $("#guidePartCmpn2").val("");
                            $("#rgnsTwo").text("-");
                            return false;
                        }
                        if($(this).val() =="") {
                            $("#rgnsTwo").text("-");
                        } else {
                            let split = $(this).val().split("/");
                            $("#rgnsTwo").text(split[1]);
                        }
                    }
                }
            },

            //팝업 호출
            fileUpload : {
                event : {
                    click : function() {
                        openPopup('paymentInfoManagPopup',this);
                    }
                }
            },

            //킥오프 파일 찾기
            searchFile : {
                event : {
                    change : function (e) {
                        chgkickImage = e.target.files.length;
                        if(e.target.files.length ==0) {
                        } else {
                            $("#emptykickFile").hide();
                            $("#showKickFile").show();
                            $("#fileKickNm").text(e.target.files[0].name);
                            $("#kickOffFile").addClass("attached");

                            var _fileLen = e.target.files[0].length;
                            let _lastDot = e.target.files[0].name.lastIndexOf(".")+1;
                            var _fileExt = e.target.files[0].name.substring(_lastDot,_fileLen);

                            if(e.target.files[0].size > $("#atchUploadMaxSize").val())  {
                                $(".delKick").remove();
                                $("#emptykickFile").show();
                                $("#showKickFile").hide();
                                $("#fileKickNm").text("");
                                $("#kickOffFile").removeClass("attached");
                                chgkickImage = 0;
                                alert("첨부파일 용량은 최대 100MB까지만 등록 가능합니다.");
                                return false;
                            }
                            if(!$("#imgExtns").val().includes(_fileExt)) {
                                $(".delKick").remove();
                                $("#emptykickFile").show();
                                $("#showKickFile").hide();
                                $("#fileKickNm").text("");
                                $("#kickOffFile").removeClass("attached");
                                chgkickImage = 0;
                                alert("첨부 가능한 파일 확장자가 아닙니다.");
                                return false;
                            }

                            var form = new FormData();
                            form.append( "files", e.target.files[0] );
                            form.append( "fileSeq",  $("#fileKickSeq").val() );
                            form.append( "fileOrd", $("#fileKickOrd").val() );
                            form.append( "_csrf", $("#csrfKeyAttend").val() );
                            $(".loading-area").stop().fadeIn(200);
                            jQuery.ajax({
                                url : "./insert-fileUpload"
                                , type : "POST"
                                , processData : false
                                , contentType : false
                                , data : form
                                , success:function(response) {
                                    let data = response.fileName[0];
                                    for(let i in data) {
                                        let html = '<input type="hidden" class="delKick" name=kickFile[0].'+i+' value='+data[i]+'>'
                                        $formObj5.append(html);
                                    }
                                }
                                ,error: function (jqXHR)
                                {
                                    if(jqXHR.statusText =='parsererror' || jqXHR.statusText =='error') {
                                        alert("등록가능한 파일이 아닙니다.");
                                        $(".delKick").remove();
                                        $("#emptykickFile").show();
                                        $("#showKickFile").hide();
                                        $("#fileKickNm").text("");
                                        $("#kickOffFile").removeClass("attached");
                                        chgkickImage = 0;
                                        return false;
                                    }
                                } ,complete : function(){
                                    $(".loading-area").stop().fadeOut(200);
                                }
                            });
                        }

                    }
                }
            },

            //렙업 파일 찾기
            searchLvlFile : {
                event : {
                    change : function (e) {
                        chglvlImage = e.target.files.length;
                        if(e.target.files.length ==0) {
                        } else {
                            $("#emptyLvlFile").hide();
                            $("#showLvlFile").show();
                            $("#fileLvlNm").text(e.target.files[0].name);
                            $("#LvlFile").addClass("attached");

                            var _fileLen = e.target.files[0].length;
                            let _lastDot = e.target.files[0].name.lastIndexOf(".")+1;
                            var _fileExt = e.target.files[0].name.substring(_lastDot,_fileLen);

                            if(e.target.files[0].size > $("#atchUploadMaxSize").val())  {
                                $(".delLvl").remove();
                                $("#emptyLvlFile").show();
                                $("#showLvlFile").hide();
                                $("#fileLvlNm").text("");
                                $("#LvlFile").removeClass("attached");
                                chglvlImage = 0;
                                alert("첨부파일 용량은 최대 100MB까지만 등록 가능합니다.");
                                return false;
                            }
                            if(!$("#imgExtns").val().includes(_fileExt)) {
                                $(".delLvl").remove();
                                $("#emptyLvlFile").show();
                                $("#showLvlFile").hide();
                                $("#fileLvlNm").text("");
                                $("#LvlFile").removeClass("attached");
                                chglvlImage = 0;
                                alert("첨부 가능한 파일 확장자가 아닙니다.");
                                return false;
                            }

                            var form = new FormData();
                            form.append( "files", e.target.files[0] );

                            form.append( "fileSeq",  $("#fileLvlSeq").val() );
                            form.append( "fileOrd", $("#fileLvlOrd").val() );
                            form.append( "_csrf", $("#csrfKeyAttend").val() );
                            $(".loading-area").stop().fadeIn(200);
                            jQuery.ajax({
                                url : "./insert-fileUpload"
                                , type : "POST"
                                , processData : false
                                , contentType : false
                                , data : form
                                , success:function(response) {
                                    $(".loading-area").stop().fadeOut(200);
                                    let data = response.fileName[0];
                                    for(let i in data) {
                                        let html = '<input type="hidden" class="delLvl" name=lvlFile[0].'+i+'  value='+data[i]+'>'
                                        $formObj5.append(html);
                                    }


                                }
                                ,error: function (jqXHR)
                                {
                                    if(jqXHR.statusText =='parsererror' || jqXHR.statusText =='error') {
                                        alert("등록가능한 파일이 아닙니다.");
                                        $(".delLvl").remove();
                                        $("#emptyLvlFile").show();
                                        $("#showLvlFile").hide();
                                        $("#fileLvlNm").text("");
                                        $("#LvlFile").removeClass("attached");
                                        chglvlImage = 0;

                                        return false;
                                    }
                                } ,complete : function(){
                                    $(".loading-area").stop().fadeOut(200);
                                }
                            });
                        }

                    }
                }
            },

            //킥오프 파일 삭제
            delKickFile : {
                event : {
                    click: function () {
                        $(".delKick").remove();
                        $("#emptykickFile").show();
                        $("#showKickFile").hide();
                        $("#fileKickNm").text("");
                        $("#kickOffFile").removeClass("attached");
                        chgkickImage = 0;
                    }
                }
            },

            //렙업 파일 삭제
            delLvlFile : {
                event : {
                    click: function () {
                        $(".delLvl").remove();
                        $("#emptyLvlFile").show();
                        $("#showLvlFile").hide();
                        $("#fileLvlNm").text("");
                        $("#LvlFile").removeClass("attached");
                        chglvlImage = 0;
                    }
                }
            },

            //부품사 선택시 파일 조회
            cmpnListSel : {
                event :{
                    change:function () {
                        if($(this).val()!=""){
                            $(".loading-area").stop().fadeIn(200);
                            jQuery.ajax({
                                url : "./select-file",
                                type : "POST",
                                timeout: 30000,
                                data : {
                                    "cnstgSeq" : $(this).val()
                                },
                                dataType : "json",
                                cache : false,
                                success : function(data, status, xhr){
                                    $("#cnstgSeq").val(data.MPFFileDto.cnstgSeq);
                                    if(data.rtnKick !=null) {
                                        $("#fileKickSeq").val(data.rtnKick.fileSeq);
                                        $("#fileKickOrd").val(data.rtnKick.fileOrd);
                                        $("#prevKickFile").append(
                                            '<a href="/file/view?fileSeq=' + data.rtnKick.fileSeq + '&fileOrd=' + data.rtnKick.fileOrd + '">' + data.rtnKick.orgnFileNm + '</a>'
                                        );
                                    } else {
                                        $("#prevKickFile").empty();
                                    }
                                    if(data.rtnLvl != null) {
                                        $("#fileLvlSeq").val(data.rtnLvl.fileSeq);
                                        $("#fileLvlOrd").val(data.rtnLvl.fileOrd);
                                        $("#prevLvlFile").append(
                                            '<a href="/file/view?fileSeq=' + data.rtnLvl.fileSeq + '&fileOrd=' + data.rtnLvl.fileOrd + '">' + data.rtnLvl.orgnFileNm + '</a>'
                                        );
                                    } else {
                                        $("#prevLvlFile").empty();
                                    }
                                } ,complete : function(){
                                    $(".loading-area").stop().fadeOut(200);
                                }
                            });
                        } else {
                            $("#fileKickSeq").val("");
                            $("#fileKickOrd").val("");
                            $("#fileLvlSeq").val("");
                            $("#fileLvlOrd").val("");
                            $("#prevKickFile").text("");
                            $("#prevLvlFile").text("");
                        }

                    }
                }
            },

            //파일 저장
            fileSave : {
                event : {
                    click : function () {
                        if($("#cnstgSeq").val()==""){
                            alert(msgCtrl.getMsg("fail.mp.mpf.al_007"));
                            return false;
                        }
                        if(confirm(msgCtrl.getMsg("confirm.sve"))) {
                            if(chgkickImage == 1) {
                                console.log(chgkickImage);
                                let html2 = '<input type="hidden" name=kickFile[1].status value="delfile">' +
                                    '<input type="hidden" name=kickFile[1].fileSeq value=' + $("#fileKickSeq").val() + '>' +
                                    '<input type="hidden" name=kickFile[1].fileOrd value=' + $("#fileKickOrd").val() + '>';
                                $formObj5.append(html2);
                            }
                            if(chglvlImage == 1) {
                                let html3 = '<input type="hidden" name=lvlFile[1].status value="delfile">' +
                                    '<input type="hidden" name=lvlFile[1].fileSeq value=' + $("#fileLvlSeq").val() + '>' +
                                    '<input type="hidden" name=lvlFile[1].fileOrd value=' + $("#fileLvlOrd").val() + '>';
                                $formObj5.append(html3);
                            }

                            cmmCtrl.frmAjax(function (respObj) {
                                $(".paymentInfoManagPopup").css('display', 'none');
                                $(".dimd").css('display', 'none');
                                $("body").removeClass("stop-scroll");
                                $(".dimd").css('z-index','');
                                alert(msgCtrl.getMsg("success.sve"));
                                location.reload();
                            }, "./update-consult", $formObj5, "POST", "json");

                        }

                    }
                }
            },
        },
        classname : {
            // do something...



        },
        immediately : function() {
            $("#guidePartCmpn1").attr('disabled', 'true');
            $("#guidePartCmpn2").attr('disabled', 'true');
            $("input[name=etcBsntrp]").attr('disabled', 'true');
            $formObj.validation({
                msg : {
                    confirm : {
                        init : ""
                    }
                },
                after : function(){
                        if( $('input:radio[name=atndcCd]:checked').val() =="CMSSR_ATTEND_001") {
                            if($("#guidePartCmpn1").val()=="" && $("input[name=etcBsntrp]").val()=="" && $("#guidePartCmpn2").val()=="") {
                                alert(msgCtrl.getMsg("fail.mp.mpf.al_005"));
                                return false;
                            }
                        }
                    return true;
                },
                async : {
                    use : true,

                    func : function (){
                        if(confirm(msgCtrl.getMsg("confirm.sve"))) {
                            cmmCtrl.frmAjax(function (respObj) {

                                alert(msgCtrl.getMsg("success.sve"));
                                location.reload();
                                //     document.getElementById("formWthdrwSuccess").submit();
                            }, "./insert-attend", $formObj, "POST", "json", '', );
                        }

                    }
                }
            });
        }
    };

    ctrl.exec();

    return ctrl;
});