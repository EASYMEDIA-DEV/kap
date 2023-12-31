define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/mp/MPMemberPartsController"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    //사업자등록 체크
    let bsnmChk = false;

    //사업자 등록 여부 체크
    let bsnmOldNewChk = false;

    let clickIndex = 1;

    let $formObj = $("#formNextSubmit");

    function init() {
        if($("#formMemCd").val() == "CP") {
            if($("#bsnmNosOld").val() != "") {
                $(".partDtl").css("display","none");
            }
            $(".chng").css("display", "none");
            bsnmChk = true;
        }
    }

    function validationCmpn() {
        if(!bsnmChk) {
            alert(msgCtrl.getMsg("fail.mp.join.al_036"));
            return false;
        }
        if($("#bsnmNo").val().trim() =='' || $("#bsnmNo").val() == undefined) {
            alert(msgCtrl.getMsg("fail.mp.join.al_018"));
            $("#bsnmNo").focus();
            return false;
        }
        if(!bsnmOldNewChk) {
            if ($("#ctgryCd").val().trim() == '' || $("#ctgryCd").val() == undefined ) {
                alert(msgCtrl.getMsg("fail.mp.join.al_027"));
                $("#ctgryCd").focus();
                return false;
            }
            if($(".rprsnt_nm").val().trim() =='' || $(".rprsnt_nm").val() == undefined) {
                alert(msgCtrl.getMsg("fail.mp.join.al_037"));
                $(".rprsnt_nm").focus();
                return false;
            }
            if ($("#sizeCd").val().trim() == '' || $("#sizeCd").val() == undefined) {
                alert(msgCtrl.getMsg("fail.mp.join.al_028"));
                $("#sizeCd").focus();
                return false;
            }
            if ($("#stbsmDt").val().trim() == '' || $("#stbsmDt").val() == undefined || $("#stbsmDt").val().length != 10) {
                alert(msgCtrl.getMsg("fail.mp.join.al_029"));
                $("#stbsmDt").focus();
                return false;
            }
            if($("#telNo").val().trim() == '' || $("#telNo").val() == undefined) {
                alert(msgCtrl.getMsg("fail.mp.join.al_030"));
                $("#telNo").focus();
                return false;
            }
            if ($("#zipcode").val().trim() == '' || $("#zipcode").val() == undefined ||
                $("#bscAddr").val().trim() == '' || $("#bscAddr").val() == undefined ||
                $("#dtlAddr").val().trim() == '' || $("#dtlAddr").val() == undefined) {
                alert(msgCtrl.getMsg("fail.mp.join.al_031"));
                $("#dtlAddr").focus();
                return false;
            }
        }
        var list1 = [];
        list1.push("");
        list1.push($("#nm1").val());
        list1.push($("#score1").val());
        list1.push($("#year1").val());
        list1.push($("#crtfnCmpnNm1").val());

        var list2 = [];
        list2.push("");
        list2.push($("#nm2").val());
        list2.push($("#score2").val());
        list2.push($("#year2").val());
        list2.push($("#crtfnCmpnNm2").val());

        var list3 = [];
        list3.push("");
        list3.push($("#nm3").val());
        list3.push($("#score3").val());
        list3.push($("#year3").val());
        list3.push($("#crtfnCmpnNm3").val());

        $(".sqInfoList1").val(list1);
        $(".sqInfoList2").val(list2);
        $(".sqInfoList3").val(list3);
        $(".bsnmChk").val(bsnmOldNewChk);
        return true;
    }

    // set model
    ctrl.model = {
        id : {
            bsnmNo : {
                event : {
                    click : function() {
                        bsnmChk = false;
                        $(".for-status-chk2").removeClass('satisfy');
                        $(".cmpn_nm_new").val('');
                        $(".rprsnt_nm").val('');

                    }
                }
            },

            // do something...
            ctgryCd : {
                event : {
                    change : function() {
                        if($(this).val() == 'COMPANY01001') {
                                btnSqInit();
                                $(".gubunOne").show();
                                $(".gubunTwo").hide();
                        } else if($(this).val() == 'COMPANY01002'){
                                $(".gubunOne").hide();
                                $(".gubunTwo").show();
                        } else {
                            $(".gubunOne").hide();
                            $(".gubunTwo").hide();
                        }
                    }
                }
            },
            nextBtn : {
                event : {
                    click : function() {
                        if(validationCmpn()) {
                            let targetPage = "/member/mp-user-join";
                            document.getElementById("formNextSubmit").action = targetPage; // form의 action 속성 변경
                            document.getElementById("formNextSubmit").submit(); // form 제출
                        }
                    }
                }
            },
            //우편검색
            searchPostCode : {
                event : {
                    click : function() {
                        cmmCtrl.searchPostCode(500,500,"zipcode","bscAddr","dtlAddr");

                    }
                }
            },

            pstnCd : {
                event : {
                    change : function() {
                        $(".pstnNm").val('');
                        if($("#pstnCd").val()!='MEM_CD01007') {
                            $(".pstnNmDis").hide();
                        } else {
                            $(".pstnNmDis").show();
                        }
                    }
                }
            },

        },
        classname : {
            // do something...
            btnBack : {
                event : {
                    click : function () {
                        if (confirm(msgCtrl.getMsg("confirm.backChk"))) {
                            history.back();
                        }
                    }
                }
            },
            btnPopClose : {
                event : {
                    click : function () {
                        if (confirm(msgCtrl.getMsg("confirm.cancleBtn"))) {
                            $(".cleanInit").val("");
                            $(".new").show();
                            $(".old").hide();
                            $(".for-status-chk").removeClass("satisfy");
                            $(".switchingMemberPopup").css('display','none');
                            $(".dimd").css('display','none');
                            $("body").removeClass("stop-scroll");
                        }
                    }
                }
            },


            btnSave : {
                event : {
                    click : function () {
                        if(validationCmpn()) {
                            if (!bsnmOldNewChk) {
                                if($("#bsnmNosOld").val() == "") {

                                    if ($("#deptCd").val() == '') {
                                        alert(msgCtrl.getMsg("fail.mp.join.al_032"));
                                        return false;
                                    }
                                    if ($("#pstnCd").val() == '') {
                                        alert(msgCtrl.getMsg("fail.mp.join.al_034"));
                                        return false;
                                    }
                                    if ($("#pstnCd").val() == 'MEM_CD01007' && $(".pstnNm").val() == '') {
                                        alert(msgCtrl.getMsg("fail.mp.join.al_035"));
                                        return false;
                                    }
                                }
                            } else {
                                if($("#bsnmNosOld").val() == "") {
                                    if ($("#deptCdOld").val() == '') {
                                        alert(msgCtrl.getMsg("fail.mp.join.al_032"));
                                        return false;
                                    }
                                    if ($("#pstnCdOld").val() == '') {
                                        alert(msgCtrl.getMsg("fail.mp.join.al_034"));
                                        return false;
                                    }
                                    if ($("#pstnCdOld").val() == 'MEM_CD01007' && $(".pstnNmOld").val() == '') {
                                        alert(msgCtrl.getMsg("fail.mp.join.al_035"));
                                        return false;
                                    }
                                }

                            }
                            //new
                            if ($(".new").css("display") == 'block') {
                                $(".cmpnNm").text($(".cmpn_nm_new").val());
                                $(".rprsntNm").text($(".rprsnt_nm").val());
                                $(".ctgryNm").text($("#ctgryCd").val()=='COMPANY01001' ? '1차' : '2차');
                                $(".addrNm").text($("#bscAddr").val() + " " + $("#dtlAddr").val());
                                $(".cmpnNm").val($(".cmpn_nm_new").val());
                                $(".rprsntNm").val($(".rprsnt_nm").val());
                                $(".deptCd").val($("#deptCd").val());
                                $(".deptDtlNm").val($("#deptDtlNm").val());
                                $(".pstnNm").val($(".pstnNm").val());
                                $(".pstnCd").val($("#pstnCd").val());
                            } else {
                                //old
                                $(".cmpnNm").text($(".cmpn_nm").text());
                                $(".rprsntNm").text($(".rsNm").text());
                                $(".ctgryNm").text($(".gubun ").text());
                                $(".addrNm").text($(".addr").text());
                                $(".cmpnNm").val($(".cmpn_nm").text());
                                $(".rprsntNm").val($(".rsNm").text());
                                $(".deptCd").val($("#deptCdOld").val());
                                $(".deptDtlNm").val($("#deptDtlNmOld").val());
                                $(".pstnNm").val($(".pstnNmOld").val());
                                $(".pstnCd").val($("#pstnCdOld").val());
                            }
                            $("#btnParts span").text("부품사정보 변경");
                            //form 데이터 넣기
                            $("#bsnmNos").val($("#bsnmNo").val());
                            $(".ctgryCd").val($("#ctgryCd").val());
                            $(".sizeCd").val($("#sizeCd").val())
                            $(".stbsmDt").val($("#stbsmDt").val());
                            $(".cmpnTel").val($("#telNo").val());
                            $(".cmpnZipcode").val($("#zipcode").val());
                            $(".cmpnBscAddr").val($("#bscAddr").val());
                            $(".cmpnDtlAddr").val($("#dtlAddr").val());
                            $(".slsPmt").val($("#slsPmt").val());
                            $(".slsYear").val($("#slsYear").val());
                            $(".mpleCnt").val($("#mpleCnt").val());
                            $(".mjrPrdct1").val($("#mjrPrdct1").val());
                            $(".mjrPrdct2").val($("#mjrPrdct2").val());
                            $(".mjrPrdct3").val($("#mjrPrdct3").val());
                            $(".qlty5StarCd").val($("#qlty5StarCd").val());
                            $(".qlty5StarYear").val($("#qlty5StarYear").val());
                            $(".pay5StarCd").val($("#pay5StarCd").val());
                            $(".pay5StarYear").val($("#pay5StarYear").val());
                            $(".tchlg5StarCd").val($("#tchlg5StarCd").val());
                            $(".tchlg5StarYear").val($("#tchlg5StarYear").val());
                            $(".sqInfoList1").val($(".sqInfoList1").val());
                            $(".sqInfoList2").val($(".sqInfoList2").val());
                            $(".sqInfoList3").val($(".sqInfoList3").val());


                            $(".bsnmNoNum").text($("#bsnmNo").val().substring(0, 3) + "-" + $("#bsnmNo").val().substring(3, 5) + "-" + $("#bsnmNo").val().substring(5));
                            // $("#bsnmChkal(bsnmOldNewChk);
                            $("#stbsmDt").val($("#stbsmDt").val())
                            $("#formMemCd").val("CP");

                            //부품사 변경 시
                            if($("#bsnmNosOld").val() != "" && $("#partTypeChg").val()=="chg") {
                                if (confirm(msgCtrl.getMsg("confirm.sve"))) {
                                    var $formObj5 = $("#formUserSubmit");
                                    cmmCtrl.frmAjax(function(respObj) {
                                        alert(msgCtrl.getMsg("success.upd2"));
                                        // location.reload();
                                    }, "/my-page/member/intrduction/update-company", $formObj5, "POST", "json",'',false);
                                }
                            } else if($("#bsnmNosOld").val() != "" && $("#partTypeChg").val()=="turnOver"){
                                //이직 시
                                $(".partDtl").show();
                                if(confirm(msgCtrl.getMsg("confirm.sve"))) {
                                    cmmCtrl.niceCertification("no&"+$("#ci").val()+"&compChg");
                                  }

                            } else {
                                //신규 등록 시
                                $(".partDtl").show()
                            }


                            $(".switchingMemberPopup").css('display', 'none');
                            $(".dimd").css('display', 'none');
                            $("body").removeClass("stop-scroll");
                            $(".for-status-chk").removeClass("satisfy");


                        }
                    }
                }
            },
            //사업자 등록 번호 인증
            btnCmpnChk : {
                event : {
                    click : function() {
                        //1. db를 조회 한다.
                        if($("#bsnmNo").val() =='' || $("#bsnmNo").val() == undefined) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_018"));
                            bsnmChk = false;
                            return ;
                        }
                        jQuery.ajax({
                            url : "/member/bsnm-select/"+$("#bsnmNo").val(),
                            type : "get",
                            success : function(data)
                            {
                                //2. 있다면 디비 정보를 조회 후 뿌려준다.
                                if(data.rtnData.list.length >=1) {
                                    $(".for-status-chk2").addClass('satisfy');
                                    let datas = data.rtnData.list[0];
                                    $(".old").show();
                                    $(".new").hide();
                                    $(".cmpn_nm").text(datas.cmpnNm);
                                    $(".rprsnt_nm").text(datas.rprsntNm);
                                    $(".gubun").text(datas.ctgryNm);
                                    $(".addr").text(datas.bscAddr+" "+datas.dtlAddr);

                                    if($("#bsnmNosOld").val() != "") {
                                        $(".chng").hide();
                                    }
                                    bsnmChk = true;
                                    bsnmOldNewChk = true;
                                } else {
                                    bsnmOldNewChk = false;
                                    //3. 없다면 나이스 api 호출을 한다.
                                    $(".old").hide();
                                    $(".new").show();
                                    jQuery.ajax({
                                        url : "/nice/comp-chk",
                                        type : "post",
                                        data :
                                            {
                                                "compNum" : $("#bsnmNo").val()
                                            },
                                        success : function(data)
                                        {
                                            //4. 있다면 정보를 입력한다.
                                            //5. 없다면 정보가 없다고 한다.
                                            if(data.rsp_cd=='P000') {
                                                if(data.result_cd == '01') {
                                                    if(data.comp_status == '1') {
                                                        $(".cmpn_nm_new").val(data.comp_name);
                                                        $(".rprsnt_nm").val(data.representive_name);
                                                        $(".for-status-chk2").addClass('satisfy');
                                                        bsnmChk = true;
                                                    } else {
                                                        alert(msgCtrl.getMsg("fail.mp.join.al_019"));
                                                        $("#bsnmNo").val("");
                                                        $(".for-status-chk2").removeClass('satisfy');
                                                        bsnmChk = false;
                                                        return false;
                                                    }
                                                } else {
                                                    alert(msgCtrl.getMsg("fail.mp.join.al_019"));
                                                    $("#bsnmNo").val("");
                                                    $(".for-status-chk2").removeClass('satisfy');
                                                    bsnmChk = false;
                                                    return false;
                                                }
                                            } else {
                                                alert(msgCtrl.getMsg("fail.mp.join.al_019"));
                                                $("#bsnmNo").val("");
                                                $(".for-status-chk2").removeClass('satisfy');
                                                bsnmChk = false;
                                                return false;
                                            }
                                        },
                                        error : function(xhr, ajaxSettings, thrownError)
                                        {
                                            cmmCtrl.errorAjax(xhr);
                                            jQuery.jstree.rollback(data.rlbk);
                                        }
                                    });

                                }
                            },
                            error : function(xhr, ajaxSettings, thrownError)
                            {
                                cmmCtrl.errorAjax(xhr);
                                jQuery.jstree.rollback(data.rlbk);
                            }
                        });
                    }
                }
            },
            //회사전화번호
            telNo : {
                event : {
                    input : function (event) {
                        let phoneNumber = event.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
                        const phoneLen = phoneNumber.length;

                        if (phoneNumber.startsWith('02')) {
                            if (phoneLen >= 3 && phoneLen <= 6) {
                                phoneNumber = phoneNumber.replace(/(\d{2})(\d+)/, '$1-$2');
                            } else if (phoneLen > 6) {
                                if (phoneLen == 9) {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{2})(\d{3,4})(\d+)/, '$1-$2-$3');

                                }
                            }
                        } else {
                            if (phoneLen > 3 && phoneLen <= 7) {
                                phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                            } else if (phoneLen > 7) {
                                if (phoneLen == 10) {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3})(\d+)/, '$1-$2-$3');
                                } else {
                                    phoneNumber = phoneNumber.replace(/(\d{3})(\d{3,4})(\d+)/, '$1-$2-$3');
                                }
                            }
                        }
                        event.target.value = phoneNumber;
                    }
                }
            },

            //sq정보 추가
            btnSqInfoPlus : {
                event : {
                    click : function() {
                        var blockElementsCount = $('.sqCount').filter(function() {
                            return $(this).css('display') == 'block';
                        }).length;
                        clickIndex = blockElementsCount;
                        if(clickIndex >=3) {
                            alert(msgCtrl.getMsg("fail.mp.join.al_026"));
                            return false;
                        }
                        $(".lastBtnIndex" + clickIndex).empty();
                        clickIndex++;
                        $(".data-line"+clickIndex).show();
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-text-icon delete btnSqInfoMinus" type="button"><span>삭제</span></button>');
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-solid small gray-bg btn-add-line btnSqInfoPlus" type="button"><span>SQ 정보 추가</span></button>');
                    }
                }
            },

            //sq정보 삭제
            btnSqInfoMinus : {
                event : {

                    click : function() {
                        var blockElementsCount = $('.sqCount').filter(function() {
                            return $(this).css('display') == 'block';
                        }).length;
                        clickIndex = blockElementsCount;
                        if(clickIndex <=1) {
                            return false;
                        }
                        $(".lastBtnIndex" + clickIndex).empty();
                        $(".data-line"+clickIndex).hide();
                        $("#nm"+clickIndex).val('');
                        $("#year"+clickIndex).val('');
                        $("#score"+clickIndex).val('');
                        $("#crtfnCmpnNm"+clickIndex).val('');
                        clickIndex--;
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-text-icon delete btnSqInfoMinus" type="button"><span>삭제</span></button>');
                        $(".lastBtnIndex"+clickIndex).append('<button class="btn-solid small gray-bg btn-add-line btnSqInfoPlus" type="button"><span>SQ 정보 추가</span></button>');
                    }
                }
            },
        },
        immediately : function() {
            init();
            btnSqInit();
        }

    };

    //sq정보 초기화
    function btnSqInit() {
        for(let i = clickIndex ; i > 0 ; i--) {
            $(".data-line"+i).hide();
            $(".lastBtnIndex" + i).empty();
            $("#nm"+i).val('');
            $("#year"+i).val('');
            $("#score"+i).val('');
            $("#crtfnCmpnNm"+i).val('');
        }
        clickIndex = 1;
        $(".data-line"+clickIndex).show();
        $(".lastBtnIndex"+clickIndex).append('<button class="btn-text-icon delete btnSqInfoMinus" type="button"><span>삭제</span></button>');
        $(".lastBtnIndex"+clickIndex).append('<button class="btn-solid small gray-bg btn-add-line btnSqInfoPlus" type="button"><span>SQ 정보 추가</span></button>');
    }

    ctrl.exec();

    return ctrl;
});