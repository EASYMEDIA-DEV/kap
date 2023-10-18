define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/um/uma/UMAFtpUploadListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");
    var $pageObj = jQuery("#frmSearch");
    var langCd = $("#langCd").val();
    //목록 조회
    var search = function(page){
        if(page != undefined){
            $pageObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
                $pageObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
                //CALLBACK 처리
                ctrl.obj.find("#listContainer").html(respObj);
                //전체 갯수
                var totCnt = $(respObj).eq(0).data("totalCount");
                //총 건수
                ctrl.obj.find("#listContainerTotCnt").text(totCnt);
                //페이징 처리
                cmmCtrl.listPaging(totCnt, $pageObj, "listContainer", "pagingContainer");
            }, "/mngwserc/"+langCd+"/um/uma/select", $pageObj, "POST", "html");
    }

    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {

                        //검색버튼 클릭시
                        cmmCtrl.setFormData($pageObj);
                        search(1);
                    }
                }
            },
            //데이터 삭제
            btnDelete : {
                event : {
                    click : function() {
                        var frmDataObj    = $("#frmSearch")
                        var delActCnt = frmDataObj.find("input:checkbox[name='delValueList']:checked").length;
                        var delType = frmDataObj.data("delType");
                        if (delActCnt > 0)
                        {
                            var isOk = true;
                            if(isOk){
                                if(confirm(msgCtrl.getMsg("confirm.del")))
                                {
                                    //삭제 전송
                                    cmmCtrl.frmAjax(function(respObj){
                                        if(respObj != undefined && respObj.respCnt > 0){
                                            var msg = msgCtrl.getMsg("success.del.target.none");
                                            if(typeof delType!= "undefined" && typeof msgCtrl.getMsg("success.del.target." + delType) != "undefined"){
                                                msg = msgCtrl.getMsg("success.del.target." + delType);
                                            }
                                            alert(msg);
                                            $frmDataObj.find("#btnSearch").click();
                                        }
                                        else{
                                            alert(msgCtrl.getMsg("fail.act"));
                                        }
                                    }, "./delete", frmDataObj, "POST", "json");
                                }
                            }
                        }
                        else
                        {
                            if(typeof delType!= "undefined")
                            {
                                alert(msgCtrl.getMsg("fail.del.target." + frmDataObj.data("delType")));
                            }
                            else
                            {
                                alert(msgCtrl.getMsg("fail.target"));
                            }

                            return;
                        }
                    }
                }
            },
            btnInsert : {
                event : {
                    click : function(){
                        var frmData = $("#frmData")
                        var fileAlt = $('input[name=fileAlt]').val()
                        if(fileAlt == '' || fileAlt == undefined){
                            alert("파일을 첨부해주세요.");
                            return false;
                        }
                    }
                }
            }
        },
        classname : {
            //페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        //페이징 이동
                        if( $(this).attr("value") != "null" ){
                            $pageObj.find("input[name=pageIndex]").val($(this).attr("value"));
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
                        $pageObj.find("input[name=listRowSize]").val($(this).val());
                        search(1);
                    }
                }
            },
            formSubmit : {
                event : {
                    change : function() {

                    }
                }
            }
        },
        immediately : function() {
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

            // 유효성 검사
            $formObj.validation({
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./insert";
                        var actionMsg = msgCtrl.getMsg("success.ins");
                        console.log($formObj.find(".dropzone").size());
                        if($formObj.find(".dropzone").size() > 0)
                        {
                            cmmCtrl.fileFrmAjax(function(data){
                                //콜백함수. 페이지 이동
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    location.replace("./list");
                                }
                            }, actionUrl, $formObj, "json");
                        }
                    }
                }
            });
            //리스트 조회
            search();
        }
    };


    ctrl.exec();

    return ctrl;
});

