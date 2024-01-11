define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKBFutureCarContestRegRsumeCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    let selPartUser; /* 선택 사용자 ID*/

    var $formObj = jQuery("#rsumeFrm");

    // form Object
    var $modalObj = $(".part-modal");
    //modalForm
    var $modalFormObj = $modalObj.find("form").eq(0);

    // 목록 조회
    var search = function (page){

        if(page !== undefined){
            $modalFormObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            $modalFormObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $modalFormObj, "listContainer", "pagingContainer");
        }, "./selCoModalData", $modalFormObj, "POST", "html");

    }

    var selPartUserData = function (){
        cmmCtrl.frmAjax(function(respObj) {
            $modalObj.modal("hide");
            /* return data input */
            setInputValue(respObj);
        }, "./selCoModalDetail", $modalFormObj, "post", "json")

    }

    /* 페이지 구성에 맞게 input value set (rtnData keys HTML id 값 동일 처리 필요) */
    var setInputValue = (respObj) => {
        let [rtnData, rtnDataCompList] = [respObj['rtnData']];
        console.log("111==" + rtnData.gndr);

        /* Input Hidden Tag Value  */

        $formObj.find(`input[type=hidden][name=id]`).val(rtnData['id']);

        /* 사용자_회사 정보 */
        Object.keys(rtnData).forEach((el) => {
            if(typeof(rtnData[el]) != "object"){
                let target = $formObj.find(`[id=${el}]`);
                if(target !== undefined) {
                    let tagName = target.prop('tagName');
                    /* SELECT || INPUT || P 태그 공통화 */
                    if(tagName === 'SELECT' || tagName === 'INPUT') target.val(rtnData[el]);
                    if(tagName === 'P') target.html(rtnData[el]);
                }
            }
        });

        /*성별*/
        if($("#gndr").html() == '1'){
            $("#gndr").html('남자');
        } else if($("#gndr").html() == '2'){
            $("#gndr").html('여자');
        }
    }

    //설문지 html
    var examInitHtml = "";
    var pannelInitHtml = "";
    //설문지 순차 순번
    var examListSize = 0;

    var callbackAjaxInsert = function(data){
        if (parseInt(data.actCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.ins"));
            location.href = "./list";
        }
        else
        {
            if(data.excessCntYn == "Y"){
                alert(msgCtrl.getMsg("fail.sm.smb.insert"));
            }else{
                alert(msgCtrl.getMsg("fail.act"));
            }
        }
    };

    var callbackAjaxUpdate = function(data){
        if (parseInt(data.actCnt, 10) > 0)
        {
            alert(msgCtrl.getMsg("success.upd"));
            location.href = "./list";
        }
        else
        {
            if(data.excessCntYn == "Y"){
                alert(msgCtrl.getMsg("fail.sm.smb.insert"));
            }else{
                alert(msgCtrl.getMsg("fail.act"));
            }
        }
    };

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

    /* eqisdSeq Ajax*/
    let selEpisdList = () => {
        let hiddenTag = $formObj.find(`input[type=hidden][name=episdSeq]`);

        if($formObj.find('#optEpisd').val() !== '') {
            cmmCtrl.frmAjax(function (respObj) {
                /* return data input */
                hiddenTag.val(respObj.episdSeqList);
                console.log(respObj.episdSeqList);
            }, "./getEpisdSeq", $formObj, "post", "json")
        } else {
            /* 값 초기화 */
            hiddenTag.val('');
        }
    }

    var rsumeSttsCheck

    // set mod el
    ctrl.model = {
        id : {
            optYear : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($modalFormObj);
                        selEpisdList();
                    },
                }
            },
            // 회원검색 모달
            /*btnPartUserModal : {
                event : {
                    click: function () {

                        console.log(1);
                        search(1);
                        $modalObj.modal("show");
                    }
                }
            },*/
           /* regUserSame : {
                event : {
                    click : function(){
                        var checked = $("#regUserSame").is(':checked');
                        if(checked){
                            var readerNm = $("#name").val();
                            var readerhpNo = $("#hpNo").text();
                            var readerEmail = $("#email").text();
                            $("#rdName").val(readerNm);
                            $("#rdHpNo").val(readerhpNo);
                            $("#rdEmail").val(readerEmail);
                            $("#regUserSame").val("Y");
                        }else{
                            $("#rdName").val("");
                            $("#rdHpNo").val("");
                            $("#rdEmail").val("");
                            $("#regUserSame").val("N");
                        }
                    }
                }
            },*/
            /*btnModalSelect : {
                event: {
                    click: function() {
                        let trArea = $modalFormObj.find("#listContainer input[type=checkbox]:checked").parents("tr");

                        if(trArea.length !== 0 || trArea != undefined){
                            selPartUser = trArea.find('[data-point=id]').html();
                            $modalFormObj.find("#selPartUser").val(selPartUser);

                            selPartUserData();
                        }

                    }
                }
            },*/
            btnSearch : {
                event: {
                    click: function () {
                        //검색버튼 클릭시
                        cmmCtrl.setFormData($modalFormObj);
                        search(1);
                    }
                }
            },
        },
        classname : {
           /* alterSameRegUserInfo : {
                event: {
                    input: function () {
                        console.log("변경감지");
                        var checked = $("#regUserSame").prop('checked',false);
                    }
                },
            },*/
            sttsChange : {
                event : {
                    change : function() {

                        var step = !($(".lastStep").data("laststep") === undefined || $(".lastStep").data("laststep") == '') ? $(".lastStep").data("laststep") : 1;
                        
                        if($("#mngSttsCd"+step).val() == "WBKB_REG_LRT002" ) {
                            $('#rsumeFrm input[name="appctnSttsCd"]').val("WBKB_REG_FRT002")
                            $('#rsumeFrm input[name="mngSttsCd"]').val("WBKB_REG_LRT002")
                        }else if($("#mngSttsCd"+step).val() == "WBKB_REG_LRT003" ) {
                            $('#rsumeFrm input[name="appctnSttsCd"]').val("WBKB_REG_FRT003")
                            $('#rsumeFrm input[name="mngSttsCd"]').val("WBKB_REG_LRT003")
                        }else if($("#mngSttsCd"+step).val() ==  "" ){
                            $('#rsumeFrm input[name="appctnSttsCd"]').val("WBKB_REG_FRT001")
                            $('#rsumeFrm input[name="mngSttsCd"]').val("")
                        }

                        // 수상시 이벤트처리
                        if($("#mngSttsCd3").val() == "WBKB_REG_LRT001"){//수상시
                            $('select[name="wdcrmCd"]').prop("disabled", false);
                            $('select[name="wdcrmCd"]').removeClass("notRequired");
                            $("input[name='hghstWinerYn']").val("N");
                            $('#rsumeFrm input[name="appctnSttsCd"]').val("WBKB_REG_FRT002")
                            $('#rsumeFrm input[name="mngSttsCd"]').val("WBKB_REG_LRT001")
                            $(".rqWdcrmCd").show();
                        }else if($("#mngSttsCd3").val() == "WBKB_REG_LRT004"){//사용자 취소
                            $('#rsumeFrm input[name="appctnSttsCd"]').val("WBKB_REG_FRT003")
                            $('#rsumeFrm input[name="mngSttsCd"]').val("WBKB_REG_LRT004")
                        }else {
                            $('select[name="wdcrmCd"]').prop("disabled", true);
                            $("input[name='hghstWinerYn']").val("N");
                            $("select[name='wdcrmCd']").val('');
                        }
                    },
                },
            },
            wdcrmCdBtn: {
                    event : {
                        change : function() {
                            if( !$("select[name='wdcrmCd']").val() == '' || !$("select[name='wdcrmCd']").val() === undefined ){
                                var wdcrmCd = $("select[name='wdcrmCd']").val();
                                $("input[name='hghstWinerYn']").val("Y");
                                $("#frmData input[name='wdcrmCd']").val(wdcrmCd);
                            }
                    },
                },
             },
         },
        immediately : function() {

            //서류 진행 상태 확인
             var laststep = !($(".lastStep").data("laststep") === undefined || $(".lastStep").data("laststep") == '') ? $(".lastStep").data("laststep") : 1;
            $("#addLow"+laststep).addClass("in");
            $("#rsumeFrm  [name='rsumeOrd']").val(laststep);

            //panel-body에 cursor 이벤트 , pointer-events 이벤트
           $('.panel-body')
                .css('pointer-events', 'none')
                .css('cursor', 'default');
           $("#addLow"+laststep).find('.panel-body')
                .css('pointer-events', '')
                .css('cursor', '');

            //특정 스텝에 불필요한 관리자 상태값 제거
            for(var i = 1; i <= laststep; i++) {
                if (i < 3) {
                    $("#mngSttsCd" + i + " option[value='WBKB_REG_LRT001']").remove();
                    $("#mngSttsCd" + i + " option[value='WBKB_REG_LRT004']").remove();
                }
            }

        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});