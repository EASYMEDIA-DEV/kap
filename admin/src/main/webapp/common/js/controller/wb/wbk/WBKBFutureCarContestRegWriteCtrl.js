define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKBFutureCarContestRegWriteCtrl"
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
            //총 건수r
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
            location.href = "./list";
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
            /*optYear : {
                event : {
                    change : function() {
                        cmmCtrl.setFormData($modalFormObj);
                        selEpisdList();
                    },
                }
            },*/
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
            btnExamWrite :{
                event :{
                    click: function(){
                        event.stopPropagation();

                        var msg = msgCtrl.getMsg("fail.cb.cba.notSrchMember1");
                        if($(".examList").size() < 2){
                            //ctrl.obj.find("#rowInsert").append(examInitHtml);
                            ctrl.obj.append(examInitHtml);
                        }else{
                            alert(msg);
                        }
                    }
                },
            },
            btnExamDelete :{
                event :{
                    click: function(){
                        $(this).parents(".examList").remove();
                        ctrl.obj.find(".examList").each(function(index, row){
                            $(this).find(".examQstnNm").text("시상종류/참여구분");
                        })
                        if($(".examList").size() == 0){
                            $(".btnExamWrite").show();
                        }
                    }
                }
            },
            /*alterSameRegUserInfo : {
                event: {
                    input: function () {
                        console.log("변경감지");
                        var checked = $("#regUserSame").prop('checked',false);
                    }
                },
            },*/
        },
        immediately : function() {
            //episdSeq
            selEpisdList();

            examInitHtml = ctrl.obj.find(".examHtmlTemplage").html();

            ctrl.obj.find(".examHtmlTemplage").remove();
            //if($.trim($("input[name=detailsKey]").val()) == "")
            if($.trim($("input[name=detailsKey]").val()) == "" || $(".examList").size()==0){
                var writeHtml = ctrl.obj.find("#rowInsert").append(examInitHtml);

                writeHtml.find(".btnExamDelete").remove();
            }
            ctrl.obj.show();

            examListSize = $(".examList").size();

            var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;
            var isAddNtfyCntn = false;
            jQuery("textarea[id^='addNtfyCntn']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 300,
                    readOnly : isAddNtfyCntn,
                });
            });

            //폼 데이터 처리
            cmmCtrl.setFormData($modalFormObj);
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

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

            // 유효성 검사
            var resumeStepHtml;
            $formObj.validation({
               /* after : function() {
                    var isValid = true;

                    if($(".dropzone .dz-preview").length < 1) {
                        alert(msgCtrl.getMsg("fail.notFileRequired"));
                        isValid = !isValid;
                    }

                    return isValid;
                },*/
                async : {
                    use : true,
                    func : function () {
                        var checkStepUpdate = 0;

                        var actionUrl = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? "./insert" : "./update");
                        var actionMsg = ($.trim($formObj.find("input[name='detailsKey']").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd"));


                        /* 아코디언 스텝 Ajax set */
                        if ($('#rsumeFrm  input[name="rsumeOrd"]').val() == 1) { // 서류심사
                            resumeStepHtml = $("#pmndvPmtView[data-sttsCd='TYPE03001']");
                        } else if ($('#rsumeFrm  input[name="rsumeOrd"]').val() == 2) { // 1차 심사
                            resumeStepHtml = $("#pmndvPmtView[data-sttsCd='TYPE03002']");
                        } else if ($('#rsumeFrm  input[name="rsumeOrd"]').val() == 3) { //최종 심사
                            resumeStepHtml = $("#pmndvPmtView[data-sttsCd='TYPE03003']");
                        }


                        console.log(resumeStepHtml);
                        //$("#frmDataSpprt").remove();
                        $formObj1.append(resumeStepHtml);


                        //console.log($formObj1);
                        if ($formObj1.size() > 0) {
                            cmmCtrl.fileFrmAjax(function (data) {
                                checkStepUpdate = 1;
                            }, "./updateStep", $formObj1, "json");
                        }

                        // 개인, 팀 신청 구분 set
                        if ($(".examList").size() > 0) {
                            $("#ptcptType").val("WBK_PTN01");
                        } else {
                            $("#ptcptType").val("WBK_PTN02");
                        }


                        if (checkStepUpdate != 1){
                            if ($formObj.find(".dropzone").size() > 0) {
                                cmmCtrl.fileFrmAjax(function (data) {
                                    //콜백함수. 페이지 이동
                                    //alert(msgCtrl.getMsg("success.ins"));
                                    alert(actionMsg);
                                    location.replace("./list");

                                    //}, "./insert", $formObj, "json");
                                }, actionUrl, $formObj, "json");
                            } else {
                                cmmCtrl.frmAjax(function (data) {
                                    //alert(msgCtrl.getMsg("success.ins"));
                                    alert(actionMsg);
                                    location.replace("./list");
                                    //}, "./insert", $formObj, "post", "json")
                                }, actionUrl, $formObj, "post", "json")
                            }
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