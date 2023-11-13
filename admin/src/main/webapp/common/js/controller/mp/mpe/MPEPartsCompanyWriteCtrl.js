define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/mpe/MPEPartsCompanyWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var valChk = 'N';

    var callbackAjaxDelete = function(data){

        if (parseInt(data.respCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.del.target.none"));
            location.href = "./list";
        }
        else
        {
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"zipcode","bscAddr","dtlAddr");
                    }
                }
            },
            checkBtn : {
                event : {
                    click : function() {
                        var checkNo = $("#bsnmNo").val();
                        var ajaxData = {
                            bsnmNo : checkNo
                        }

                        jQuery("#frmData").serializeArray().forEach(function(field) {
                            if (field.name == '_csrf') {
                                ajaxData[field.name] = field.value;
                            }
                        });

                        console.log(JSON.stringify(ajaxData, null, 2));

                        if(valChk) {
                            $.ajax({
                                type : "post",
                                url : './checkBsnmNo',
                                dataType : "json",
                                data : ajaxData,
                                success : function(r) {
                                    console.log(r.respCnt);
                                    console.log(r);
                                    if (r.respCnt) {
                                        alert("인증되었습니다.");
                                        $("#cmpnNm").val(r.cmpnNm);
                                        $("#rprsntNm").val(r.rprsntNm);
                                    } else {
                                        alert('DB에 등록된 정보가 없습니다. NICE 인증으로 넘어갑니다.')
                                        $("#cmpnNm").val("nice에서 가져온 회사명");
                                        $("#rprsntNm").val("nice에서 가져온 대표자명");
                                    }
                                },
                                error : function(xhr, ajaxSettings, thrownError) {
                                    alert("인증에 실패했습니다.");
                                }
                            });
                        } else {
                            alert("사업자등록번호를 인증해주세요");
                            return false;
                        }
                    }
                }
            },
            ctgryCd : {
                event : {
                    change : function () {
                        var ctgryCd =  $("#ctgryCd option:selected").val();
                        if (ctgryCd == "COMPANY01001")
                        {
                            $(".sqInfoArea").hide();
                        }
                        else if (ctgryCd == "COMPANY01002")
                        {
                            $(".sqInfoArea").show();
                        }
                    }
                }
            },
            bsnmNo : {
                event : {
                    keyup : function(){

                        var str = this.value;
                        str = str.replace(/[^0-9]/g, "");
                        var tmp = ""
                            , lens1 = 4, lens2 = 6, lens3 = 10
                            , cutlen1 = 3, cutlen2 = 2;

                        if(str.length < lens1){
                            tmp = str;
                        }
                        else if (str.length < lens2)
                        {
                            tmp += str.substr(0, cutlen1);
                            tmp += str.substr(cutlen1);
                        }
                        else if (str.length < lens3)
                        {
                            tmp += str.substr(0, cutlen1);
                            tmp += str.substr(cutlen1, cutlen2);
                            tmp += str.substr(cutlen1 + cutlen2);
                        }
                        else
                        {
                            tmp += str.substr(0, cutlen1);
                            tmp += str.substr(cutlen1, cutlen2);
                            tmp += str.substr(cutlen1 + (cutlen2));
                        }
                        this.value = tmp;
                    }
                }
            }
        },
        classname : {
            // do something...
        },
        immediately : function(){

            /* Editor Setting */
            jQuery("textarea[id^='cntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                });
            });

            $("#btn_delete").click(function () {
                if (confirm(msgCtrl.getMsg("confirm.del"))) {
                    cmmCtrl.frmAjax(callbackAjaxDelete, "./delete", $formObj);
                }
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

                    if (!editorChk)
                    {
                        isValid = false;
                    }

                    return isValid;
                }
                ,
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );

                        cmmCtrl.frmAjax(function(data){
                            if(data.respCnt > 0){
                                alert(actionMsg);
                                location.replace("./list");
                            }
                            actionUrl = "./list";
                        }, actionUrl, $formObj, "post", "json")
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
