define(["ezCtrl", "ezVald", "CodeMirror", "controller/co/COMenuCtrl"], function(ezCtrl, ezVald, CodeMirror ,menuCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wba/WBAManagementWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var callbackAjaxDelete = function(data){

        if (parseInt(data.respCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        }
        else
        {
            alert('신청정보가 존재하여 삭제할 수 없습니다.');
        }
    };

    var fn_menu_layer_pop = function(fnc) {
        $(".wbaTreeLayer").one('show.bs.modal', function() {
            var modal = $(this);
            modal.appendTo("body");// 한 화면에 여러개 창이 뜰경우를 위해 위치 선정
            // Add class for soft backdrop
            $(".wbaTreeLayer").find("#btnRefresh").click();
            $(".wbaTreeLayer").find("#btnSearch").click();

        }).one('hidden.bs.modal', function() {
            // Remove class for soft backdrop (if not will affect future modals)
        }).one('choice', function(data, param) {
            // Remove class for soft backdrop (if not will affect future modals)
            fnc(param);
        }).modal();
    }

    // set model
    ctrl.model = {
        id : {
            userMenu : {
                event : {
                    click : function() {
                        fn_menu_layer_pop('user');
                        menuCtrl.setJstree(false, false, { "topNode" : ctrl.obj.data("pageNo"), "d" : "5", "isMenu" :"Y" , "sub" : "615", "menuGubun" : "user"});
                        $('#menuType').val("userMenu");
                        $('#treeName').html("사용자 메뉴 선택");
                    }
                }
            },
            adminMenu : {
                event : {
                    click : function() {
                        fn_menu_layer_pop('admin');
                        menuCtrl.setJstree(false, false, { "topNode" : "1", "d" : "5", "sub" : "597", "menuGubun" : "admin"});
                        $('#menuType').val("adminMenuChoice");
                        $('#treeName').html("관리자 메뉴 선택");
                    }
                }
            },
            adminMenuCompany : {
                event : {
                    click : function() {
                        fn_menu_layer_pop('admin');
                        menuCtrl.setJstree(false, false, { "topNode" : "1", "d" : "5", "sub" : "597", "menuGubun" : "admin"});
                        $('#menuType').val("adminMenuCompany");
                        $('#treeName').html("관리자 메뉴 선택");
                    }
                }
            }
        },
        classname : {
            // do something...
        },
        immediately : function() {

            $(".stepList").each(function(){
                var fileCk = $(this).find("input[name=fileYn]").is(":checked");
                if(fileCk){
                    $(this).find('.dropzone').css("pointer-events","none").css("background-color","#eee");
                }
            });

            $("#btn_update").click(function () {
                //신청자가 있는지 확인
                cmmCtrl.frmAjax(function(result) {
                    if (result > 0) {
                        alert('신청정보가 존재하여 수정할 수 없습니다.');
                    } else {
                        $formObj.submit();
                    }
                },"./applyCount",$formObj);

            });

            $("#btn_delete").click(function () {
                //신청자가 있는지 확인
                cmmCtrl.frmAjax(function(result) {
                    if (result > 0) {
                        alert('신청정보가 존재하여 삭제할 수 없습니다.');
                    } else {
                        if (confirm(msgCtrl.getMsg("confirm.del"))) {
                            cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                        }
                    }
                },"./applyCount",$formObj);
            });

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true, editorChk = true;

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1)
                        {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.co.cog.cnts"));

                            CKEDITOR.instances[jQuery(this).prop("id")].focus();

                            // 에디터 최상단으로 스크롤 이동
                            jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

                            return false;
                        }
                    });

                    //fn_chk_dropzone
                    if (!editorChk)
                    {
                        isValid = false;
                    }

                    $('.stepList').each(function(i) {
                        var fileCk = $(this).find("input[name=fileYn]").is(":checked");
                        var flag = true;
                        var stagNm = $(this).find("input[name='stageNm']").val();

                        if (!stagNm) {
                            alert('단계 ' + (i+1) +'의 단계명을 입력해주세요');
                            $(this).find("input[name='stageNm']").focus();
                            isValid = false;
                            flag = false;
                            return false;
                        } else {
                            if (!fileCk) {
                                $(this).find(".fileListDepth").each(function (j) {
                                    var optnNm = $(this).find("input[name='optnNm']").val();

                                    if (!optnNm) {
                                        alert('단계 ' + (i + 1) + '의 첨부파일명을 입력해주세요');
                                        $(this).find("input[name='optnNm']").focus();
                                        isValid = false;
                                        flag = false;
                                        return false;
                                    } else {
                                        $(this).find(".dropzone").each(function() {
                                            if($(this).find('.dz-preview').length < 1) {
                                                alert('단계 ' + (i + 1) + '의 양식 파일을 등록해주세요');
                                                isValid = false;
                                                flag = false;
                                                return false;
                                            }
                                        });
                                    }
                                    if (!flag) {
                                        return false;
                                    }
                                });
                            }
                        }

                        if (!flag) {
                            isValid = false;
                            return false;
                        }
                    });

                    return isValid;
                }
                ,
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        //사업단계 데이터 셋팅
                        var wbaManageInsertDTO = {};
                        wbaManageInsertDTO.bsnCd = ctrl.obj.find("#bsnCd").val();
                        wbaManageInsertDTO.userMenuSeq = ctrl.obj.find("#userMenuSeq").val();
                        wbaManageInsertDTO.admEpisdMenuSeq = ctrl.obj.find("#admEpisdMenuSeq").val();
                        wbaManageInsertDTO.admAppctnMenuSeq = ctrl.obj.find("#admAppctnMenuSeq").val();
                        wbaManageInsertDTO.qaCtgryCd = ctrl.obj.find("#qaCtgryCd").val();
                        wbaManageInsertDTO.managementDtlList = new Array();
                        wbaManageInsertDTO.detailsKey = ctrl.obj.find("#detailsKey").val();

                        $(".stepListContainer").find(".stepList").each(function(index) {
                            var stepDtl = {};
                            stepDtl.bsnCd = ctrl.obj.find("#bsnCd").val();
                            stepDtl.stageNm = $(this).find("input[name=stageNm]").val();
                            stepDtl.fileYn = $(this).find("input[name=fileYn]").is(":checked") ? 'N': 'Y';
                            stepDtl.stageOrd = index+1;

                            if (stepDtl.fileYn == "Y") {
                                stepDtl.managementOptnList = new Array();
                                $(this).find(".fileListDepth").each(function(i){
                                    var stepOptnDtl = {};
                                    var fileIndex = 0;
                                    if (stepDtl.fileYn == "Y") {
                                        stepOptnDtl.optnOrd = i;
                                        stepOptnDtl.optnNm = $(this).find("input[name=optnNm]").val();
                                        stepOptnDtl.optnFileList = new Array();
                                        var tempFile = {};

                                        $(this).find(".dropzone.attachFile").each(function(){
                                            if($(this).get(0).dropzone.files != undefined && $(this).get(0).dropzone.files.length > 0){
                                                $.each($(this).get(0).dropzone.files, function(idx, data){
                                                    //alt값  data에 넣어주기.
                                                    data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();
                                                    for (let i in data) {
                                                        if (data.hasOwnProperty(i)) {
                                                            tempFile.status = data.status;
                                                            tempFile.width = data.width;
                                                            tempFile.height = data.height;
                                                            tempFile.webPath = data.webPath;
                                                            tempFile.fieldNm = "fileSeq";
                                                            tempFile.orgnFileNm = data.orgnFileNm;
                                                            tempFile.fileDsc = data.fileDsc;
                                                            tempFile.fileSeq = data.fileSeq;
                                                            tempFile.fileOrd = data.fileOrd;
                                                        }
                                                    }
                                                })
                                            }
                                        });
                                        stepOptnDtl.optnFileList.push(tempFile);
                                    }
                                    stepDtl.managementOptnList.push(stepOptnDtl);
                                });
                            }
                            wbaManageInsertDTO.managementDtlList.push(stepDtl);

                        });

                        var detailsKey = $.trim($formObj.find("input[name='detailsKey']").val());
                        var bsnCd = $('#bsnCd').val();

                        if (detailsKey == bsnCd) {
                            cmmCtrl.jsonAjax(function(result){
                                alert(actionMsg);
                                location.href = "./list";
                            }, actionUrl, wbaManageInsertDTO, "text");
                        } else {
                            cmmCtrl.frmAjax(function(result) {
                                if (result > 0) {
                                    alert('중복된 사업코드입니다.');
                                } else {
                                    cmmCtrl.jsonAjax(function(result){
                                        alert(actionMsg);
                                        location.href = "./list";
                                    }, actionUrl, wbaManageInsertDTO, "text");
                                }
                            },"./duplication",$formObj);
                        }
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

