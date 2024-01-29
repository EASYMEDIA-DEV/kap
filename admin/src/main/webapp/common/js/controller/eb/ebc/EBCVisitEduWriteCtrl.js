define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/eb/ebc/EBCVisitEduWriteCtrl"
    };

    var $formObj = jQuery("#frmData");
    var width = 500; //팝업의 너비
    var height = 600; //팝업의 높이
    var prevVal = "";

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    var search = function (page){
        //data로 치환해주어야한다.
        cmmCtrl.setFormData($formObj);

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }

        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#trsfListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#trsfListContainerTotCnt").val(totCnt);
            $(".trsfField").validation({});
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "trsfListContainer", "trsfPagingContainer");
        }, "/mngwserc/eb/ebc/trsfList", $formObj, "GET", "html");
    }

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

        //삭제했는데 하나도 없으면 목록 없다는 걸로 돌림
        if($("#isttrContainer").find("tr").size() == 2){
            //돌림
            $("#isttrContainer").find(".notIsttr").css("display", "");
            $("#isttrContainer").find(".notIsttr").find("td").css("display", "");
        }
    }

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
            $(".checkBoxArea").validation({});
        }, "/mngwserc/eb/ebc/changeAppctnFldCd", $formObj, "POST", "html",'',false);
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
            searchEduPlacePostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(width,height,"placeZipcode","placeBscAddr","placeDtlAddr");
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

                        } else {
                            $(".sqInfoArea").hide();
                            $(".qlty5StarArea").hide();
                            $(".pay5StarArea").hide();
                            $(".tchlg5StarArea").hide();
                        }
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
            },
            pstnCdSelect: {
                event: {
                    click: function () {
                        var clickVal = $(this).val();
                        if (clickVal == 'MEM_CD01007') {
                            $(".pstnCdInput").show();
                            $("input[name='deptCdNm']").removeClass("notRequired");
                        } else {
                            $(".pstnCdInput").hide();
                            $("input[name='deptCdNm']").addClass("notRequired");
                        }
                    }
                }
            },
            telNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen >= 3 && phoneLen <= 6) {
                            phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                        } else if (phoneLen > 6) {
                            if (phoneLen == 9) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },
            cmpnTelNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneLen >= 3 && phoneLen <= 6) {
                            phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                        } else if (phoneLen > 6) {
                            if (phoneLen == 9) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                            } else {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            }
        },
        classname : {
            //부품사 찾기
            bsnmNoBtn : {
                event : {
                    click : function() {
                        cmmCtrl.getPartsCompanyMemberLayerPop(function (data) {
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
            appctnTypeAll : {
                event : {
                    click : function() {
                        //상위 DIV 안의 checkboxSingle를 찾아야함. 그렇지 않음 페이지 모든 .checkboxSingle가 변경됨
                        var trgtArr = $(this).closest("div").find(".checkboxSingle");
                        if (trgtArr.length > 0)
                        {
                            var isChecked = false;
                            if($(this).is(":checked"))
                            {
                                isChecked = true;
                            }
                            $.each(trgtArr, function(){
                                $(this).prop("checked", isChecked);
                            })
                        }
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
                            }else if(data.choiceCnt > 6) {
                                alert("선택할 수 있는 강사 최대 수는 6명입니다.");
                            }else {
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

                                    $("#checkButton").on("click", function () {
                                        var checkedCount = $(".checkbox:checked").length;

                                        if (checkedCount >= 6) {
                                            alert("6개 이상의 체크박스가 선택되었습니다.");
                                        }
                                    });
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
            },
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            search();
                        }
                    }
                }
            },
            //페이징 목록 갯수

            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            }
        },
        immediately : function(){
            changeAppctnFldCd();
            search();
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //부품사 우편번호와 동일하면 본사와 동일 체크 박스 checked
            var originPartsZipCode = $formObj.find("input[name=zipcode]").val();
            var originPartsBscAddr = $formObj.find("input[name=bscAddr]").val();
            var originPartsDtlAddr  =$formObj.find("input[name=dtlAddr]").val();

            var placeZipcode = $formObj.find("input[name=placeZipcode]").val();
            var placeBscAddr = $formObj.find("input[name=placeBscAddr]").val();
            var placeDtlAddr = $formObj.find("input[name=placeDtlAddr]").val();

            if (placeZipcode == originPartsZipCode && placeBscAddr == originPartsBscAddr
                && placeDtlAddr == originPartsDtlAddr) {
                $("input:checkbox[id='samePlaceBtn']").prop("checked", true);
            }

            /*$('#ctgryCd').focus(function() {
                prevVal = $(this).val();
            }).change(function(){
                if($(this).val() == 'COMPANY01003' || $(this).val() == 'COMPANY01004'){
                    alert("완성차와 기타는 신청이 불가합니다.");
                    $(this).val(prevVal);
                    return false;
                }
            });*/

            var scndRgnsCd = $("#scndRgnsCd").data("scndRgnsCd");
            $("#scndRgnsCd").val(scndRgnsCd).prop("selected", true);

            $("#samePlaceBtn").change(function() {
                // 체크된 경우
                if ($(this).is(":checked")) {
                    $formObj.find("input[name=placeZipcode]").val(originPartsZipCode);
                    $formObj.find("input[name=placeBscAddr]").val(originPartsBscAddr);
                    $formObj.find("input[name=placeDtlAddr]").val(originPartsDtlAddr);

                    var samePlaceAddrArr = jQuery("input[name=placeBscAddr]").val().split(' ');
                    var edctnPlaceAddr;
                    if(samePlaceAddrArr[1] != undefined){
                        edctnPlaceAddr =  samePlaceAddrArr[0] + ' ' + samePlaceAddrArr[1];
                    } else {
                        edctnPlaceAddr = samePlaceAddrArr[0];
                    }

                    $formObj.find("input[name=edctnPlaceAddr]").val(edctnPlaceAddr);
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
                    var isValid = true

                    jQuery(".dropzone").not(".notRequired").each(function(i){
                        if (jQuery(this).children(".dz-preview").length == 0)
                        {
                            alert(jQuery(this).data("titl") + "를 첨부해주세요.");
                            jQuery(this)[0].scrollIntoView();
                            isValid = false;
                            return false;
                        }
                    });

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){

                        // controller에 json으로 넘길 form값
                        var actForm = {};

                        var vstSeq = $("#vstSeq").val();
                        var memSeq = $("#memSeq").val();
                        var bfreMemSeq = $("#bfreMemSeq").val();
                        var memId = $("#memId").val();
                        var vstRsltSeq = $("#vstRsltSeq").val();

                        actForm.vstSeq = vstSeq;
                        actForm.memSeq = memSeq;
                        actForm.bfreMemSeq = bfreMemSeq;
                        actForm.id = memId;
                        actForm.vstRsltSeq = vstRsltSeq;

                        //신청자 정보
                        var deptCd = $("#deptCd").val();
                        var deptDtlNm = $("#deptDtlNm").val();
                        var pstnCd = $("#pstnCdSelect").val();
                        var deptCdNm = $("#deptCdNm").val();
                        var telNo = $("#telNo").val();

                        actForm.deptCd = deptCd;
                        actForm.deptDtlNm = deptDtlNm;
                        actForm.pstnCd = pstnCd;
                        actForm.deptCdNm = deptCdNm;
                        actForm.telNo = telNo;

                        //부품사 정보
                        var ctgryCd = $("#ctgryCd").val();
                        var sizeCd = $("#sizeCd").val();
                        var stbsmDt = $("#stbsmDt").val();
                        var cmpnTelNo = $("#cmpnTelNo").val();
                        var zipcode = $("#zipcode").val();
                        var bscAddr = $("#bscAddr").val();
                        var dtlAddr = $("#dtlAddr").val();
                        var slsPmt = $("#slsPmt").val();
                        var mpleCnt = $("#mpleCnt").val();
                        var slsYear = $("#slsYear").val();
                        var mjrPrdct1 = $("#mjrPrdct1").val();
                        var mjrPrdct2 = $("#mjrPrdct2").val();
                        var mjrPrdct3 = $("#mjrPrdct3").val();
                        var qlty5StarCd = $("#qlty5StarCd").val();
                        var qlty5StarYear = $("#qlty5StarYear").val();
                        var pay5StarCd = $("#pay5StarCd").val();
                        var pay5StarYear = $("#pay5StarYear").val();
                        var tchlg5StarCd = $("#tchlg5StarCd").val();
                        var tchlg5StarYear = $("#tchlg5StarYear").val();

                        actForm.ctgryCd = ctgryCd;
                        actForm.sizeCd = sizeCd;
                        actForm.stbsmDt = stbsmDt;
                        actForm.cmpnTelNo = cmpnTelNo;
                        actForm.zipcode = zipcode;
                        actForm.bscAddr = bscAddr;
                        actForm.dtlAddr = dtlAddr;
                        actForm.slsPmt = slsPmt;
                        actForm.mpleCnt = mpleCnt;
                        actForm.slsYear = slsYear;
                        actForm.mjrPrdct1 = mjrPrdct1;
                        actForm.mjrPrdct2 = mjrPrdct2;
                        actForm.mjrPrdct3 = mjrPrdct3;
                        actForm.qlty5StarCd = qlty5StarCd;
                        actForm.qlty5StarYear = qlty5StarYear;
                        actForm.pay5StarCd = pay5StarCd;
                        actForm.pay5StarYear = pay5StarYear;
                        actForm.tchlg5StarCd = tchlg5StarCd;
                        actForm.tchlg5StarYear = tchlg5StarYear;

                        var index = 1;
                        var sqInfoList1 = new Array();
                        var sqInfoList2 = new Array();
                        var sqInfoList3 = new Array();

                        $(".sqInfoList").each(function(){
                            var temp = {};
                            var cbsnSeq = $(this).find("#cbsnSeq" + index).val();
                            var nm = $(this).find("#nm" + index).val();
                            var score = $(this).find("#score" + index).val();
                            var year = $(this).find("#year" + index).val();
                            var crtfnCmpnNm = $(this).find("#crtfnCmpnNm" + index).val();

                            temp.cbsnSeq = cbsnSeq;
                            temp.nm = nm;
                            temp.score = score;
                            temp.year = year;
                            temp.crtfnCmpnNm = crtfnCmpnNm;

                            if(index == 1) {
                                sqInfoList1.push(cbsnSeq);
                                sqInfoList1.push(nm);
                                sqInfoList1.push(score);
                                sqInfoList1.push(year);
                                sqInfoList1.push(crtfnCmpnNm);

                            } else if(index == 2) {
                                sqInfoList2.push(cbsnSeq);
                                sqInfoList2.push(nm);
                                sqInfoList2.push(score);
                                sqInfoList2.push(year);
                                sqInfoList2.push(crtfnCmpnNm);

                            } else if(index == 3) {
                                sqInfoList3.push(cbsnSeq);
                                sqInfoList3.push(nm);
                                sqInfoList3.push(score);
                                sqInfoList3.push(year);
                                sqInfoList3.push(crtfnCmpnNm);
                            }
                            index++;
                        });

                        actForm.sqInfoList1 = sqInfoList1;
                        actForm.sqInfoList2 = sqInfoList2;
                        actForm.sqInfoList3 = sqInfoList3;

                        // 신청내용
                        var appctnBsnmNo = $("#appctnBsnmNo").val();
                        var appctnRsn = $("#appctnRsn").val();
                        var appctnFldCd = $("#appctnFldCd").val();
                        var appctnThemeCntn = $("#appctnThemeCntn").val();
                        var hopeDt = $("#hopeDt").val();
                        var placeZipcode = $("#placeZipcode").val();
                        var placeBscAddr = $("#placeBscAddr").val();
                        var placeDtlAddr = $("#placeDtlAddr").val();
                        var edctnPlaceAddr = $("#edctnPlaceAddr").val();
                        var ptcptTrgtCntn = $("#ptcptTrgtCntn").val();
                        var ptcptCnt = $("#ptcptCnt").val();
                        var ptcptHh = $("#ptcptHh").val();
                        var itrdcFileSeq = $("#itrdcFileSeq").val();

                        actForm.appctnBsnmNo = appctnBsnmNo;
                        actForm.appctnRsn = appctnRsn;
                        actForm.appctnFldCd = appctnFldCd;
                        actForm.appctnThemeCntn = appctnThemeCntn;
                        actForm.hopeDt = hopeDt;
                        actForm.placeZipcode = placeZipcode;
                        actForm.placeBscAddr = placeBscAddr;
                        actForm.placeDtlAddr = placeDtlAddr;
                        actForm.edctnPlaceAddr = edctnPlaceAddr;
                        actForm.ptcptTrgtCntn = ptcptTrgtCntn;
                        actForm.ptcptCnt = ptcptCnt;
                        actForm.ptcptHh = ptcptHh;
                        actForm.itrdcFileSeq = itrdcFileSeq;

                        var appctnTypeCdList = new Array();
                        $(".checkBoxArea input[type='checkbox']:checked").each(function(){
                            appctnTypeCdList.push($(this).val());
                        });
                       actForm.appctnTypeCdList = appctnTypeCdList;

                        //교육실적
                        var rsltEndYn = $("input[name='rsltEndYn']:checked").val();
                        var cnfrmdTheme = $("#cnfrmdTheme").val();
                        var edctnSttsCd = $("#edctnSttsCd").val();
                        var edctnYear = $("#edctnYear").val();
                        var edctnStrtDt = $("#edctnStrtDtm").val();
                        var edctnStrtHour = $("#edctnStrtHour").val();
                        var edctnEndDt = $("#edctnEndDtm").val();
                        var edctnEndHour = $("#edctnEndHour").val();
                        var edctnPlace = $("#edctnPlace").val();

                        var edctnStrtDtm = edctnStrtDt + " " + edctnStrtHour + ":00:00";
                        var edctnEndDtm = edctnEndDt + " " + edctnEndHour + ":59:59";

                        //교육기간 유효성 체크
                        if(edctnStrtDtm > edctnEndDtm){
                            alert("교육 시작일이 교육 종료일보다 이전날짜로 입력 해주세요.");
                            $("#edctnStrtDt").focus();
                            return false;
                        }
                        if(edctnStrtDt == edctnEndDt){
                            if(edctnStrtHour > edctnEndHour){
                                alert("교육 시작시간이 더 클 수 없습니다.");
                                $("#edctnStrtHour").focus();
                                return false;
                            }
                        }

                        //강사정보
                        var isttrSeqList= new Array();

                        $("input[name='isttrSeq']").each(function(){
                            if($(this).val() != undefined && $(this).val() != ""){
                                var tempForm = {};
                                tempForm.vstSeq = $("#vstSeq").val();
                                tempForm.memSeq = $("#memSeq").val();
                                tempForm.vstRsltSeq = $("#vstRsltSeq").val();
                                tempForm.isttrSeq = $(this).val();
                                isttrSeqList.push(tempForm);
                            }
                        });
                        actForm.isttrSeqList = isttrSeqList;

                        var cmptnCnt = $("#cmptnCnt").val();
                        var ptcptRate = $("#ptcptRate").val();
                        var lctrFileSeq = $("#lctrFileSeq").val();
                        var etcMatlsFileSeq = $("#etcMatlsFileSeq").val();

                        actForm.rsltEndYn = rsltEndYn;
                        actForm.cnfrmdTheme = cnfrmdTheme;
                        actForm.edctnSttsCd = edctnSttsCd;
                        actForm.edctnYear = edctnYear;
                        actForm.edctnStrtDtm = edctnStrtDtm;//교육시작일시
                        actForm.edctnEndDtm = edctnEndDtm;//교육종료일시
                        actForm.edctnPlace = edctnPlace;
                        actForm.cmptnCnt = cmptnCnt;
                        actForm.ptcptRate = ptcptRate / 100;
                        actForm.lctrFileSeq = lctrFileSeq;
                        actForm.etcMatlsFileSeq = etcMatlsFileSeq;

                        //방문교육 결과 옵션 상세
                        var resultOpList = new Array();

                        $(".cnt-list").each(function(){

                            var temp = {};
                            var rsltTypeCd = $(this).data("rsltTypeCd");
                            var optnCd = $(this).find("input:text").attr("name");
                            var rsltVal = $(this).find("input:text").val();

                            temp.vstSeq = parseInt(vstSeq);
                            temp.memSeq = parseInt(memSeq);
                            temp.vstRsltSeq = parseInt(vstRsltSeq);
                            temp.rsltTypeCd = rsltTypeCd;
                            temp.optnCd = optnCd;
                            temp.rsltVal = parseInt(rsltVal);
                            resultOpList.push(temp);
                        });
                        console.log(resultOpList);
                        actForm.resultOpList = resultOpList;

                        //회사소개서 파일 세팅
                        var itrdcFileArray = new Array();
                        if(!($(".itrdcFile").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                            $(".itrdcFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                            $(".itrdcFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                            $.each($(".itrdcFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
                                //alt값  data에 넣어주기.
                                data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

                                for (let i in data) {
                                    if (data.hasOwnProperty(i)) {
                                        var temp = {};
                                        temp.fileSeq = data.fileSeq;
                                        temp.status = data.status;
                                        temp.width = data.width;
                                        temp.height = data.height;
                                        temp.webPath = data.webPath;
                                        temp.fieldNm = "itrdcFileSeq";
                                        temp.orgnFileNm = data.orgnFileNm;
                                        temp.fileDsc = data.fileDsc;
                                        temp.fileOrd = data.fileOrd;

                                        if(itrdcFileArray == "" || (itrdcFileArray[itrdcFileArray.length-1].fileOrd != temp.fileOrd)){
                                            itrdcFileArray.push(temp);
                                        }

                                    }
                                }

                            })
                        }
                        actForm.itrdcFileList = itrdcFileArray;

                        //강의교안 파일 세팅
                        var lctrFileArray = new Array();
                        if(!($(".lctrFile").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                            $(".lctrFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                            $(".lctrFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                            $.each($(".lctrFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
                                //alt값  data에 넣어주기.
                                data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

                                for (let i in data) {
                                    if (data.hasOwnProperty(i)) {
                                        var temp = {};
                                        temp.fileSeq = data.fileSeq;
                                        temp.status = data.status;
                                        temp.width = data.width;
                                        temp.height = data.height;
                                        temp.webPath = data.webPath;
                                        temp.fieldNm = "lctrFileSeq";
                                        temp.orgnFileNm = data.orgnFileNm;
                                        temp.fileDsc = data.fileDsc;
                                        temp.fileOrd = data.fileOrd;

                                        if(lctrFileArray == "" || (lctrFileArray[lctrFileArray.length-1].fileOrd != temp.fileOrd)){
                                            lctrFileArray.push(temp);
                                        }

                                    }
                                }

                            })
                        }
                        actForm.lctrFileList = lctrFileArray;

                        //기타자료 파일 세팅
                        var etcMatlsFileArray = new Array();
                        if(!($(".etcMatlsFile").find(".dropzone.attachFile").eq(0).get(0) === undefined) &&
                            $(".etcMatlsFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files != undefined &&
                            $(".etcMatlsFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files.length > 0) {
                            $.each($(".etcMatlsFile").find(".dropzone.attachFile").eq(0).get(0).dropzone.files, function (idx, data) {
                                //alt값  data에 넣어주기.
                                data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();

                                for (let i in data) {
                                    if (data.hasOwnProperty(i)) {
                                        var temp = {};
                                        temp.fileSeq = data.fileSeq;
                                        temp.status = data.status;
                                        temp.width = data.width;
                                        temp.height = data.height;
                                        temp.webPath = data.webPath;
                                        temp.fieldNm = "etcMatlsFileSeq";
                                        temp.orgnFileNm = data.orgnFileNm;
                                        temp.fileDsc = data.fileDsc;
                                        temp.fileOrd = data.fileOrd;

                                        if(etcMatlsFileArray == "" || (etcMatlsFileArray[etcMatlsFileArray.length-1].fileOrd != temp.fileOrd)){
                                            etcMatlsFileArray.push(temp);
                                        }

                                    }
                                }

                            })
                        }
                        actForm.etcMatlsFileList = etcMatlsFileArray;
                        console.log(actForm);

                        cmmCtrl.jsonAjax(function(data){
                            alert("저장되었습니다.");
                            location.href = "./list";
                        }, "./update", actForm, "text");
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

