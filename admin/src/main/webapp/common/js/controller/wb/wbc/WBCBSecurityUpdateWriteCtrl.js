define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbc/WBCBSecurityUpdateWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    var trnsfSearch = function (page){
        //data로 치환해주어야한다.
        cmmCtrl.setFormData($formObj);

        $("#listRowSize").val(10);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#trnfsListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "trnfsListContainer", "trnfsPagingContainer");
        }, "./log-list.ajax", $formObj, "POST", "html");
    }


    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData'], respObj['rtnDataCompDetail']];
        var memId = rtnData['id'];
        var memName = rtnData['name'];

        $('#mem').val(memId+'('+memName+')');

        $("#befeCtgryCd").val(rtnData['ctgryCd']);

        if(rtnData['pstnCd'] == 'MEM_CD01007'){
            $("#pstnNm").css("display", "block");
        }
        if(rtnData['ctgryCd'] == 'COMPANY01001'){
            $(".companyCate1").css("display", "block");
            $(".SQ").val("");
            $(".companyCate2").css("display", "none");
        }else if(rtnData['ctgryCd'] == 'COMPANY01002'){
            $(".companyCate1").css("display", "none");
            $(".starType").val("");
            $(".companyCate2").css("display", "block");
        }

        $("#bsnmNoPut1").val(rtnData['bsnmNo']);
        $("#bsnmNoPut2").val(rtnData['bsnmNo']);

        // 사업자번호 변경
        rtnData['bsnmNo'] = rtnData['bsnmNo'].slice(0,3) + '-' + rtnData['bsnmNo'].slice(3,5) + '-' + rtnData['bsnmNo'].slice(5);


        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                var target = $formObj.find("[id=" + el + ']');
                var tagName = target.prop('tagName');
                /* SELECT || INPUT || P 태그 공통화 */
                if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                if(tagName === 'P') target.html(rtnData[el]);
            }
        });

        for(var i = 0; i < rtnDataCompList.list.length; i++){
            Object.keys(rtnDataCompList.list[i]).forEach((el) => {
                $formObj.find('#nm'+i).val(rtnDataCompList.list[i]['nm']);
                if(rtnDataCompList.list[i]['score'] != '') {
                    $formObj.find('#score'+i).val(rtnDataCompList.list[i]['score']);
                }else{
                    $formObj.find('#score'+i).val('');
                }
                if(rtnDataCompList.list[i]['year'] != ''){
                    $formObj.find('#year'+i).val(rtnDataCompList.list[i]['year']);
                }
                $formObj.find('#crtfnCmpnNm'+i).val(rtnDataCompList.list[i]['crtfnCmpnNm']);
            });
        }
    }

    // set model
    ctrl.model = {
        id : {
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipCode","bscAddr","dtlAddr");
                    }
                }
            },
            searchPostCode2 : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,600,"zipcode2","bscAddr2","dtlAddr2");
                    }
                }
            },
            episdYear : {
                event : {
                    change : function() {
                        cmmCtrl.frmAjax(function(data) {

                            var html = "<option value=\"\">선택</option>";
                            for(var i = 0; i< data.episdList.length; i++){
                                html += '<option value=\"'+data.episdList[i]+'\">'+data.episdList[i]+'</option>';
                            }
                            $("#episd").html(html);

                        }, "./getEpisdSelect", $formObj, "post", "json")
                    }
                }
            },
            spprtPmt : {
                event : {
                    change : function() {

                        var a = parseInt($(this).val());
                        var b = parseInt($("#phswPmt").val());
                        if(isNaN(a)){
                            a=0;
                        }else if(isNaN(b)){
                            b=0;
                        }

                        var sum = a + b;

                        $("#sum").val(sum);
                        $("#ttlPmt").val(sum);
                    }
                }
            },
            phswPmt : {
                event : {
                    change : function() {
                        var a = parseInt($(this).val());
                        var b = parseInt($("#spprtPmt").val());

                        if(isNaN(a)){
                            a=0;
                        }else if(isNaN(b)){
                            b=0;
                        }

                        var sum = a + b ;

                        $("#sum").val(sum);
                        $("#ttlPmt").val(sum);

                    }
                }
            },
            ctgryCd : {
                event : {
                    change : function() {
                        var ctgryCd = $(this).val();

                        if(ctgryCd == 'COMPANY01001'){
                            $(".companyCate1").css("display", "block");
                            $(".SQ").val("");
                            $(".companyCate2").css("display", "none");
                        }else if(ctgryCd == 'COMPANY01002'){
                            $(".companyCate1").css("display", "none");
                            $(".starType").val("");
                            $(".companyCate2").css("display", "block");
                        }else{
                            alert("부품사 구분은 1차,2차만 등록가능합니다.");
                            if($("#befeCtgryCd").val() == ""){
                                $(this).val($("#befeCtgryCd").val());
                            }else{
                                $(this).val($("#befeCtgryCd").val()).trigger("change");
                            }
                        }
                    }
                }
            },
            pstnCd : {
                event : {
                    change : function() {
                        var pstnCd = $(this).val();

                        if(pstnCd =='MEM_CD01007'){
                            $("#pstnNm").css("display", "block");
                        }else{
                            $("#pstnNm").val("");
                            $("#pstnNm").css("display", "none");
                        }
                    }
                }
            },
            //PDF
            appctnPdfDownload : {
                event : {
                    click : function(){

                        var cmpnNm = $("#cmpnNm").val();
                        var today = new Date();

                        var date = today.getFullYear() +""+ today.getMonth()+1 +""+ today.getDate();


                        var fileName = "탄소배출저감_사업현황_"+ cmpnNm +"_"+ date + ".pdf";
                        cmmCtrl.getAppctnPdfDownload(fileName);
                    }
                }
            }
        },
        //선급금 해당 여부
        classname : {
            // 회원검색 모달
            btnPartUserModal: {
                event: {
                    click: function () {
                        $("#srchDivide").val("Y");
                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
                            cmmCtrl.frmAjax(function (respObj) {
                                $formObj.find('#memSeq').val(data.memSeq);
                                cmmCtrl.frmAjax(function(respObj) {
                                    console.log(respObj);
                                    /* return data input */
                                    setInputValue(respObj);
                                }, "/mngwserc/wb/selModalDetail", $formObj, "post", "json");
                            }, "/mngwserc/wb/partUserChk", $formObj, "post", "json");
                        });
                    }
                }
            },
            // 위원검색 모달
            btnCmtSearch: {
                event: {
                    click: function () {
                        cmmCtrl.getCmtSrchPop(function (data) {
                            var memName = data.name;

                            $('#picName').val(memName);

                            $("#picCmssrSeq").val(data.seq);
                            $("#chkCmssrSeq").val(data.seq);
                        });

                    }
                }
            },
            pmndvPmtYn : {
                event : {
                    change : function() {
                        if($(this).val() == 'Y'){
                            $("#ab1").css('height' , '100%');
                            $("#ab1").addClass('in');
                        }else{
                            $("#ab1").css('height' , '0px');
                            $("#ab1").removeClass('in');
                        }
                    }
                }
            },
            mngSttsCd : {
                event : {
                    change : function() {
                        var mngSttsCd = $(this).val();

                        if(mngSttsCd =='PRO_TYPE01001_02_002'){
                            $(".rtrnRsnCntn").attr('disabled',false);
                        }else if(mngSttsCd =='PRO_TYPE01001_02_004'){
                            $(".rtrnRsnCntn").attr('disabled',false);
                        }else{
                            $(".rtrnRsnCntn").attr('disabled',true);
                        }
                    }
                }
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        if($(this).closest("#trnfsPagingContainer").length > 0){
                            trnsfSearch($(this).attr("value"));
                        }
                    }
                }
            },
            //상세보기
            listView : {
                event : {
                    click : function() {
                        //상세보기
                        var detailsKey = $(this).data("detailsKey");
                        $formObj.find("input[name=detailsKey]").val(detailsKey);
                        /*location.href = "./write?" + $formObj.serialize();*/
                    }
                }
            },
        },
        immediately : function() {

            trnsfSearch(1);

            var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

            /* Editor Setting */
            jQuery("textarea[id^='cnts']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                    readOnly : _readOnly
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

            var ctgryCd = $('#ctgryCd').val();
            if(ctgryCd == 'COMPANY01001'){
                $(".companyCate1").css("display", "block");
                $(".SQ").val("");
                $(".companyCate2").css("display", "none");
            }else if(ctgryCd == 'COMPANY01002'){
                $(".companyCate1").css("display", "none");
                $(".starType").val("");
                $(".companyCate2").css("display", "block");
            }

            var pstnCd = $('#pstnCd').val();
            if(pstnCd =='MEM_CD01007'){
                $("#pstnNm").css("display", "block");
            }else{
                $("#pstnNm").val("");
                $("#pstnNm").css("display", "none");
            }

            var mngSttsCd = $("#mngSttsCd1").val();

            if(mngSttsCd =='PRO_TYPE01001_02_002'){
                $(".rtrnRsnCntn").attr('disabled',false);
                $(".rtrnRsnCntn").css("display", "block");
            }else if(mngSttsCd =='PRO_TYPE01001_02_004'){
                $(".rtrnRsnCntn").attr('disabled',true);
                $(".rtrnRsnCntn").css("display", "block");
            }else{
                $(".rtrnRsnCntn").attr('disabled',true);
                $(".rtrnRsnCntn").css("display", "none");
            }

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true, editorChk = true;

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
                        jQuery(this).val(jQuery(this).val().split("<").join("~!left!~"));
                        jQuery(this).val(jQuery(this).val().split(">").join("~!right!~"));
                        jQuery(this).val(jQuery(this).val().split("\'").join("~!singlecomma!~"));
                        jQuery(this).val(jQuery(this).val().split("\"").join("~!doublecomma!~"));

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
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./update";
                        var actionMsg = (msgCtrl.getMsg("success.upd"));

                        $(".mngSttsCd").each(function (){
                            if($(this).val() == "PRO_TYPE01001_02_002"){
                                $(this).next().val("PRO_TYPE01001_01_002");
                            }else if($(this).val() == "PRO_TYPE01001_02_004"){
                                $(this).next().val("PRO_TYPE01001_01_004");
                            }else if($(this).val() == "PRO_TYPE01001_02_005"){
                                $(this).next().val("PRO_TYPE01002_01_003");
                            }

                            else if($(this).val() == "PRO_TYPE01002_01_003"){
                                $(this).next().val("PRO_TYPE01001_02_002");
                            }else if($(this).val() == "PRO_TYPE01002_02_004"){
                                $(this).next().val("PRO_TYPE01001_01_005");
                            }else if($(this).val() == "PRO_TYPE01002_02_005"){
                                $(this).next().val("PRO_TYPE01002_01_006");
                            }

                            else if($(this).val() == "PRO_TYPE01003_02_002"){
                                $(this).next().val("PRO_TYPE01003_01_002");
                            }else if($(this).val() == "PRO_TYPE01003_02_003"){
                                $(this).next().val("PRO_TYPE01003_01_003");
                            }

                            else if($(this).val() == "PRO_TYPE01004_02_002"){
                                $(this).next().val("PRO_TYPE01004_01_003");
                            }else if($(this).val() == "PRO_TYPE01004_02_004"){
                                $(this).next().val("PRO_TYPE01004_01_005");
                            }else if($(this).val() == "PRO_TYPE01004_02_005"){
                                $(this).next().val("PRO_TYPE01004_01_006");
                            }

                            else if($(this).val() == "PRO_TYPE01005_02_002"){
                                $(this).next().val("PRO_TYPE01005_01_002");
                            }else if($(this).val() =="PRO_TYPE01005_02_003"){
                                $(this).next().val("PRO_TYPE01005_01_003");
                            }

                            else if($(this).val() =="PRO_TYPE01006_02_002"){
                                $(this).next().val("PRO_TYPE01006_01_002");
                            }else if($(this).val() =="PRO_TYPE01006_02_003"){
                                $(this).next().val("PRO_TYPE01006_01_003");
                            }
                        });

                        $(".spprtMngSttsCd").each(function (){
                            if($(this).val() == "PRO_TYPE03001_02_003"){
                                $(this).next().val("PRO_TYPE03001_01_003");
                            }else if($(this).val() == "PRO_TYPE03001_02_005"){
                                $(this).next().val("PRO_TYPE03001_01_005");
                            }

                            else if($(this).val() == "PRO_TYPE03002_02_003"){
                                $(this).next().val("PRO_TYPE03002_01_003");
                            }else if($(this).val() == "PRO_TYPE03002_02_005"){
                                $(this).next().val("PRO_TYPE03002_01_005");
                            }

                        });


                        cmmCtrl.fileFrmAjax(function(data){
                            alert(actionMsg);
                            location.replace("./list");
                        }, actionUrl, $formObj,  "json");

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

    ctrl.exec();

    return ctrl;
});

