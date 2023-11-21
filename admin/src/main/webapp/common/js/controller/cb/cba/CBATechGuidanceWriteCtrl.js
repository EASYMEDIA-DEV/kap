define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/cb/cba/CBATechGuidanceWriteCtrl"
    };

    var $formObj = jQuery("#frmData");

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var valChk = 'N';
    var idNum = 0;

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
            searchPostCode : {
                event : {
                    click : function() {
                        var idVal = $(this).attr('id');
                        console.log(idVal);
                        if(idVal == "hqAddr"){
                            cmmCtrl.searchPostCode(width,height,"hqZipcode","hqBscAddr","hqDtlAddr");
                        }else{
                            cmmCtrl.searchPostCode(width,height,"zipcode","bscAddr","dtlAddr");
                        }
                    }
                }
            },
            cmpnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".tempRow").size();

                        if(rowCnt < 4){
                            var temp = document.getElementById("dlvryRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dlvryRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dlvryTempDiv").append(newNode);
                            $("#"+newNodeId).children().find("input").val("");
                            $("#"+newNodeId).children(".closeLabel").css("display","block");
                        }else{
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            dpndnPlus : {
                event : {
                    click : function(){
                        var rowCnt = $(".dpTempRow").size();

                        if(rowCnt < 4){
                            var temp = document.getElementById("dpndnRow");
                            var newNode = temp.cloneNode(true);
                            idNum++;
                            var newNodeId = "dpndnRow" + idNum;
                            newNode.id = newNodeId;
                            $("#dpTempDiv").append(newNode);
                            $("#"+newNodeId).children().find("input").val("");
                            $("#"+newNodeId).children(".closeLabel").css("display","block");
                        }else{
                            alert("최대 3개까지 추가할 수 있습니다.");
                        }
                    }
                }
            },
            closeLabel : {
                event : {
                    click : function (){
                        $(this).parent().remove();
                    }
                }
            },
            appctnFldCd : {
                event : {
                    click : function(){
                        var cdVal = $(this).val();
                        if(cdVal == "TEC_GUIDE_APPCTN00"){
                            if($(this).is(":checked")) $("input[name=appctnFldCd]").prop("checked", true);
                            else $("input[name=appctnFldCd]").prop("checked", false);
                        }
                    }
                }
            },
            addrSame : {
                event : {
                    click : function(){
                        var checked = $(".addrSame").is(':checked');
                        var sameVal = $(this).val();
                        if(sameVal == 'Y'){
                            $(this).val("N");
                            $(".factAddr").prop("disabled", false);
                            $("#dtlAddr").prop("readOnly", false);
                        }else{
                            $(this).val("Y");
                            $(".factAddr").prop("disabled", true);
                            $("#dtlAddr").prop("readOnly", true);
                        }
                        if(checked){
                            var hqZipcode = $("#hqZipcode").val();
                            var hqBscAddr = $("#hqBscAddr").val();
                            var hqDtlAddr = $("#hqDtlAddr").val();
                            $("#zipcode").val(hqZipcode);
                            $("#bscAddr").val(hqBscAddr);
                            $("#dtlAddr").val(hqDtlAddr);
                        }
                    }
                }
            },
            ctgryCd : {
                event : {
                    click : function(){
                        var selectVal = $("select[name=ctgryCd]").val();
                        if(selectVal == "COMPANY01001"){
                            $(".sqInfo").hide();
                            $(".fiveStar").show();
                        }else{
                            $(".fiveStar").hide();
                            $(".sqInfo").show();
                        }
                    }
                }
            },
            //부품사 찾기
            bsnmNoBtn : {
                event : {
                    click : function() {
                        cmmCtrl.getPartsCompanyMemberLayerPop(function(data){
                            $("#bsnmNo").val(data.seq);
                            $("#bsnmNoNm").val(data.titl);
                        });
                    }
                }
            },
            cnstgCdRadio : {
                event : {
                    click : function(){
                        var cnstgVal = $("input[name=cnstgCd]:checked").val();
                        if(cnstgVal == "TEC_GUIDE_INDUS14"){
                            $("input[name=etcNm]").removeClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", false);
                        }else{
                            $("input[name=etcNm]").addClass("notRequired");
                            $("input[name=etcNm]").attr("disabled", true);
                        }
                    }
                }
            }
        },
        immediately : function(){

            /* Editor Setting */
            jQuery("textarea[id^='cntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                });
            });

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("fileExtn"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
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

