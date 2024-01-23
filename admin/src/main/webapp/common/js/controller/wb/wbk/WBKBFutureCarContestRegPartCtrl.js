define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKBFutureCarContestRegPartCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    let width = 500; //팝업의 너비
    let height = 600; //팝업의 높이
    let selPartUser; /* 선택 사용자 ID*/

    var $formObj = jQuery("#frmData");
    var $formObj1 = jQuery("#rsumeFrm");

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

    var removeOtherStepName = function() {

        if ( $("#rsumeOrd").val() == 1 ){

            $("#pmndvPmtView[data-sttsCd='TYPE03002'] select , #pmndvPmtView[data-sttsCd='TYPE03002'] p ,#pmndvPmtView[data-sttsCd='TYPE03003'] select , #pmndvPmtView[data-sttsCd='TYPE03003'] p ").each(function () {
                // 해당 스텝의 name 속성 제거
                $(this).removeAttr("name");
            });
            // 결과 확인을 위해 console에 로그 출력
            console.log("Select and p names removed");
        }else if( $("#rsumeOrd").val() == 2 ){

        }else if( $("#rsumeOrd").val() == 3 ){

        }

    }

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
            btnPartUserModal : {
                event : {
                    click: function () {

                        search(1);
                        $modalObj.modal("show");
                    }
                }
            },
            regUserSame : {
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
            },
            btnModalSelect : {
                event: {
                    click: function() {
                        let trArea = $modalFormObj.find("#listContainer input[type=checkbox]:checked").parents("tr");

                        if(trArea.length == 0) {
                            alert("회원을 1명 선택해주세요.")
                        }else if(trArea.length !== 0 || trArea != undefined){
                            selPartUser = trArea.find('[data-point=id]').html();
                            $modalFormObj.find("#selPartUser").val(selPartUser);

                            selPartUserData();
                        }
                    }
                }
            },
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

            alterSameRegUserInfo : {
                event: {
                    input: function () {
                        console.log("변경감지");
                        var checked = $("#regUserSame").prop('checked',false);
                    }
                },
            },
        },
        immediately : function() {
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});