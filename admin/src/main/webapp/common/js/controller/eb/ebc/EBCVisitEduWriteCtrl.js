define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebc/EBCVisitEduWriteCtrl"
    };

    var $formObj = jQuery("#frmData");
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이

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
            alert(msgCtrl.getMsg("fail.act"));
        }
    };

    //소재지역 분류 조회
    var selectCtgryCdList = function(arg){

        if(arg === undefined){
            arg = $("#scndRgnsCd").data("scndRgnsCd");
        }
        var cdMst= {};
        cdMst.cd = $(arg).val();

        cmmCtrl.jsonAjax(function(data){
            callbackAjaxCtgryCdList(data);
        }, './classTypeList', cdMst, "text");

    }

    //소재지역 분류 2뎁스 세팅
    var callbackAjaxCtgryCdList = function(data){

        var detailList = JSON.parse(data);
        var selectHtml = "<option value=''>2차 선택</option>";

        for(var i =0; i < detailList.length; i++){

            var cd = detailList[i].cd;
            var cdNm = detailList[i].cdNm;

            selectHtml += "<option value='"+cd+"' >"+cdNm+"</option>";
        }
        console.log(selectHtml);

        $("#scndRgnsCd option").remove();
        $("#scndRgnsCd").append(selectHtml);
        console.log(selectHtml);
        var scndRgnsCd = $("#scndRgnsCd").data("scndRgnsCd");
        $("#scndRgnsCd").val(scndRgnsCd).prop("selected", true);
    }

    var changeAppctnFldCd = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find(".checkBoxArea").html(respObj);
        }, "/mngwserc/eb/ebc/changeAppctnFldCd", $formObj, "POST", "html",'',false);
    }


    //강사 테이블 넘버링
    var isttrTable = function(data){

        //tr 갯수 감지해서 카운트해줌, 없으면 없는폼 꺼냄
        var totCnt = $("#isttrContainer").find("tr").size();
        var startCount = 2;

        $("#isttrContainer").find("tr").each(function(idx, data){
            if(idx>1){
                $(this).find("td").eq(0).text(totCnt-idx);
            }
        });

        //삭제했는데 하나도없으면 목록 없다는걸로 돌림
        if($("#isttrContainer").find("tr").size() == 2){
            //돌림
            $("#isttrContainer").find(".notIsttr").css("display", "");
            $("#isttrContainer").find(".notIsttr").find("td").css("display", "");
        }
    }

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
            ctgryCd : {
                event : {
                    change : function () {
                        var selectedCtgryCd = $('#ctgryCd option:selected').val();
                        $formObj.find("input[name=selectCtgryCd]").val(selectedCtgryCd);

                        if (selectedCtgryCd == "COMPANY01001") {
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").show();
                            $(".pay5StarArea").show();
                            $(".tchlg5StarArea").show();
                        }
                        else if (selectedCtgryCd == "COMPANY01002") {
                            $(".sqInfoArea").show();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();
                        }

                        var selectedAppctnFldCd = $('#ctgryCd option:selected').val();
                        $formObj.find("input[name=appctnFldCd]").val(selectedAppctnFldCd);
                        cmmCtrl.frmAjax(function(respObj) {
                            ctrl.obj.find(".checkBoxArea").html(respObj);
                        }, "/mngwserc/eb/ebc/changeCtgryCd", $formObj, "POST", "html",'',false);
                    }
                }
            },
            appctnFldCd : {
                event : {
                    change : function () {
                        var selectedAppctnFldCd = $('#appctnFldCd option:selected').val();
                        $formObj.find("input[name=appctnFldCd]").val(selectedAppctnFldCd);
                        changeAppctnFldCd();
                    }
                }
            },
            // 상세주소 입력 시 체크박스 해제
            placeDtlAddr : {
                event : {
                    input : function () {
                        $("#samePlaceBtn").prop("checked", false);
                    }
                }
            }
        },
        classname : {
            //부품사 찾기
            bsnmNoBtn : {
                event : {
                    click : function() {
                        cmmCtrl.getPartsCompanyMemberLayerPop(function(data){
                            $("#bsnmNo").val(data.seq);
                            $("#memName").val(data.titl);
                        });
                    }
                }
            },
            classType : {
                event : {
                    change : function() {
                        selectCtgryCdList(this);
                    }
                }
            },
            //강사 검색
            eduIsttrSearch : {
                event : {
                    click : function(){
                        cmmCtrl.getLecturerLayerPop(function(data){
                            if(data.choiceCnt  == 0){
                                alert(msgCtrl.getMsg("fail.mpc.notSrchLecturer"));
                            }else{
                                var name, ffltnNm, spclCntn, seq;
                                if(data.choiceCnt>1){
                                    var trObjList = data.trObjList;
                                    for(var i=0; i<trObjList.length; i++){
                                        var exIsttr = $(".setIsttr").clone(true);
                                        name = trObjList[i].name//이름
                                        ffltnNm= trObjList[i].titl//소속
                                        spclCntn= trObjList[i].spclCntn//약력(특이사항)
                                        seq= trObjList[i].seq;//삭제(시퀀스값)

                                        //다중등록할때 시퀀스 체크해서 중복값이면 패스함
                                        var passYn = false;//이 값이 true가 되면 이미 강사 목록에 있으므로 append목록에 추가하지 않는다.
                                        $("#isttrContainer").find("tr").find("input:hidden").each(function(){
                                            if($(this).val() == seq) passYn = true;
                                        });

                                        if(!passYn){
                                            exIsttr.find("td").eq(1).text(name);
                                            exIsttr.find("td").eq(2).text(ffltnNm);
                                            exIsttr.find("td").eq(3).text(spclCntn);
                                            exIsttr.find("input:hidden").val(seq);
                                            $("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
                                        }
                                    }

                                }else{
                                    var exIsttr = $(".setIsttr").clone(true);
                                    name = data.name//이름
                                    ffltnNm = data.titl//소속
                                    spclCntn = data.spclCntn//약력(특이사항)
                                    seq = data.seq//삭제(시퀀스값)

                                    var passYn = false;//이 값이 true가 되면 이미 강사 목록에 있으므로 현재 동작을 취소한다.
                                    $("#isttrContainer").find("tr").find("input:hidden").each(function(){
                                        if($(this).val() == seq) {
                                            alert("이미 추가된 강사입니다.");
                                            passYn = true;
                                        }
                                    });
                                    if(!passYn){
                                        exIsttr.find("td").eq(1).text(name);
                                        exIsttr.find("td").eq(2).text(ffltnNm);
                                        exIsttr.find("td").eq(3).text(spclCntn);
                                        exIsttr.find("input:hidden").val(seq);
                                        $("#isttrContainer").append("<tr>"+exIsttr.html()+"</tr>");
                                    }
                                }

                                $(".notIsttr").css("display", "none");
                                $(".setIsttr").css("display", "none");
                            }
                            isttrTable();
                        });
                    }
                }
            },
            btnOneTrRemove : {
                event : {
                    click : function(){
                        $(this).closest("tr").remove();
                        isttrTable();
                    }
                }
            }
        },
        immediately : function(){
            changeAppctnFldCd();

            var scndRgnsCd = $("#scndRgnsCd").data("scndRgnsCd");
            $("#scndRgnsCd").val(scndRgnsCd).prop("selected", true);

            $("#samePlaceBtn").change(function() {
                // 체크된 경우
                if ($(this).is(":checked")) {
                    var originPartsZipCode = $formObj.find("input[name=zipcode]").val();
                    var originPartsBscAddr = $formObj.find("input[name=bscAddr]").val();
                    var originPartsDtlAddr  =$formObj.find("input[name=dtlAddr]").val();

                    $formObj.find("input[name=placeZipcode]").val(originPartsZipCode);
                    $formObj.find("input[name=placeBscAddr]").val(originPartsBscAddr);
                    $formObj.find("input[name=placeDtlAddr]").val(originPartsDtlAddr);
                }
            });

            /* File Dropzone Setting */
            $formObj.find(".dropzone").each(function(){
                var trgtObj = $(this);
                cmmCtrl.setDropzone(trgtObj, {
                    maxFileCnt  : trgtObj.data("maxFileCnt"),
                    maxFileSize : trgtObj.data("maxFileSize"),
                    fileExtn    : trgtObj.data("imageExtns"),
                    fileFieldNm : trgtObj.data("fileFieldNm")
                })
            });

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

                    jQuery(".dropzone").not(".notRequired").each(function(i){
                        if (jQuery(this).children(".dz-preview").length == 0)
                        {
                            alert(jQuery(this).data("titl") + "를 첨부해주세요.");
                            jQuery(this)[0].scrollIntoView();
                            isValid = false;
                            return false;
                        }
                    });

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1)
                        {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.sm.smc.html"));

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
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );
                        if($formObj.find(".dropzone.dz-started").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "json");
                        }
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                                actionUrl = "./list";
                            }, actionUrl, $formObj, "post", "json")
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

