define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/im/ima/IMAQaPicLayerCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    // var $formObj = ctrl.obj.find("form").eq(0);
    var $formObj = $("#QaPicLayerFrm");

    // function
    //목록 조회
    var picSearch = function(page){
        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            $formObj.find("#picListContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            $formObj.find("#picListContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "picListContainer", "picPagingContainer");
            //입력 값 초기화
            $("#QaPicLayerFrm #inqFirPic").val("");
            $("#QaPicLayerFrm #inqSecPic").val("");
            $("#QaPicLayerFrm #picNm").val("");
            $("#QaPicLayerFrm #piceMail").val("");
            $("#QaPicLayerFrm #detailsKey").val("");
        }, "/mngwserc/im/ima/select-pic", $formObj, "GET", "html");
    }

    // set model
    ctrl.model = {
        id : {
            inqFirPic : {
                event : {
                    change : function() {
                        var inqFir = document.getElementById('inqFirPic');
                        var selectFir = inqFir.options[inqFir.selectedIndex].value;
                        var inqSecOpsions = document.getElementsByClassName("inqSecPic");
                        var addOption;

                        if(selectFir) {
                            for(var i = 0, max = inqSecOpsions.length; i < max; i++) {
                                addOption = inqSecOpsions.item(i);
                                if(addOption.value.indexOf(selectFir) > -1 && addOption.value.length > 5) {
                                    addOption.style.display = "block";
                                }
                                else {
                                    addOption.style.display = "none";
                                }
                            }
                            $("#inqSecPic").prop("selectedIndex", 0);
                        }
                        else {
                            for(var i = 0, max = inqSecOpsions.length; i < max; i++) {
                                addOption = inqSecOpsions.item(i);
                                addOption.style.display = "none";
                            }
                            $("#inqSecPic").prop("selectedIndex", 0);
                        }
                    }
                }
            },
            inqSecPic : {
                event : {
                    change : function() {
                        var parntCtgryCd = $("#QaPicLayerFrm #inqFirPic").val();
                        var ctgryCd = $("#QaPicLayerFrm #inqSecPic").val();

                        jQuery.ajax({
                            type: "post",
                            url: "./pic-cnt-check",
                            data:
                                {
                                    "parntCtgryCd": parntCtgryCd
                                    , "ctgryCd": ctgryCd
                                    , "_csrf": $("#csrfKey").val()
                                },
                            dataType: "json",
                            success: function(r) {
                                if (r.respCnt >= 10) {
                                    alert("1개의 문의유형에 최대 10명까지만 등록 가능합니다.");
                                    $("#QaPicLayerFrm #inqFirPic").val("");
                                    $("#QaPicLayerFrm #inqSecPic").val("");
                                    return false;
                                }
                            }
                        });
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
                            $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                            picSearch();
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
                        picSearch(1);
                    }
                }
            },
            btnPicUpdate : {
                event : {
                    click : function() {
                        //수정
                        var picSeq = $(this).data("detailsKey");
                        if(picSeq > -1){
                            jQuery.ajax({
                                type: "post",
                                url: "./write-pic",
                                data:
                                    {
                                        "picSeq": picSeq
                                        , "_csrf": $("#csrfKey").val()
                                    },
                                dataType: "json",
                                success: function (r) {
                                    if (r && r.picSeq != null && r.picSeq > -1) {
                                        $("#QaPicLayerFrm #detailsKey").val(r.picSeq);
                                        $("#QaPicLayerFrm #inqFirPic").val(r.parntCtgryCd);
                                        $("#QaPicLayerFrm #inqSecPic").val(r.ctgryCd);
                                        $("#QaPicLayerFrm #picNm").val(r.picNm);
                                        $("#QaPicLayerFrm #piceMail").val(r.piceMail);
                                    }
                                }
                            });
                        }
                        else {
                            alert("문제가 발생하였습니다. 페이지를 새로고침 해주세요.")
                        }
                    }
                }
            },
            btnPicDelete : {
                event : {
                    click : function() {
                        //삭제
                        var picSeq = $(this).data("detailsKey");
                        if(picSeq > -1){
                            if(confirm(msgCtrl.getMsg("confirm.del"))){

                                jQuery.ajax({
                                    type: "post",
                                    url: "./delete-pic",
                                    data:
                                        {
                                            "picSeq": picSeq
                                            , "_csrf": $("#csrfKey").val()
                                        },
                                    dataType: "json",
                                    success: function (r) {
                                        if (r && r.respCnt > 0) {
                                            alert(msgCtrl.getMsg("success.del.target.none"));
                                            picSearch();
                                        }
                                        else{
                                            alert("문제가 발생하였습니다. 페이지를 새로고침 해주세요.")
                                        }
                                    }
                                });
                            }
                        }
                        else {
                            alert("문제가 발생하였습니다. 페이지를 새로고침 해주세요.")
                        }
                    }
                }
            }
        },
        immediately : function() {
            //리스트 조회
            picSearch();

            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true;

                    // 이메일 유효성
                    if(isValid) {
                        var mailPattern = /^(?!.*\.{2,})([^@&'=+,<>\s]+)@([^@&'=+,<>\s]+)\.([^@&'=+,<>\s]+)([a-zA-Z가-힣]+)$/;
                        var piceMail = $("#QaPicLayerFrm #piceMail").val();
                        if (!mailPattern.test(piceMail)) {
                            alert("이메일 형태를 확인해주세요.");
                            isValid = false;
                        }
                    }

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = $("#QaPicLayerFrm #detailsKey").val() ? "./update-pic" : "./insert-pic";
                        var actionMsg = $("#QaPicLayerFrm #detailsKey").val() ? msgCtrl.getMsg("success.upd") : msgCtrl.getMsg("success.ins");
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
                        else
                        {
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(actionMsg);
                                    // location.replace("./list");
                                    picSearch();
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


    ctrl.exec();

    return ctrl;
});

