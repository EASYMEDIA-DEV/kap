define(["ezCtrl", "ezVald","ezFile"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJAutoMotiveCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    var $formObj = $('#frmData');
    var addCount = 10;
    var imageText = "";

    // set model
    ctrl.model = {
        id : {
            goHome : {
              event : {
                  click : function(){
                      if(confirm("현재 입력된 정보는 초기화됩니다. 계속하시겠습니까?\n")){
                          location.href = "/coexistence/automotive/content";
                      }
                  }
              }
            },
            infoSameChk :{
                event : {
                    click : function(){
                        var checked = $('#infoSameChk').is(':checked');
                        var name = $("#name").val();
                        var hpNo = $("#hpNo").val();
                        var cmpnNm = $("#cmpnNm").val();
                        var deptNm = $("#deptCdNm").val();
                        var pstnCdNm = $("#pstnCdNm").val();

                        if(checked){
                            $("#rcmndName").val(name);
                            $("#rcmndHpNo").val(hpNo);
                            $("#rcmndCmpnNm").val(cmpnNm);
                            $("#rcmndDeptNm").val(deptNm);
                            $("#rcmndPstnNm").val(pstnCdNm);
                        }else{
                            $("#rcmndName").val("");
                            $("#rcmndHpNo").val("");
                            $("#rcmndCmpnNm").val("");
                            $("#rcmndDeptNm").val("");
                            $("#rcmndPstnNm").val("");
                        }
                    }
                }
            },
            //검색버튼 클릭시
            btnSearch : {
                event : {
                    click : function() {
                        //검색버튼 클릭시
                        $('#firstIndex').val(0);
                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리

                            $('.divide').empty();
                            $('.divide').append(respObj);
                            addCount = addCount;
                            //전체 갯수
                            var totalCnt = $(respObj).eq(0).data("totalCount");
                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('.add-load').show();
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            //선택버튼 클릭시
            selectChoose : {
                event : {
                    click: function(){
                        if($('input:checkbox[name=chk]:checked').length > 1){
                            alert("부품사를 1개만 선택해주세요.")
                        }else if($('input:checkbox[name=chk]:checked').length == 0){
                            alert("부품사를 선택해주세요.");
                        }else{
                            var clickObj = {};
                            $("#newBsnmNo").val(ctrl.obj.find("input[name=chk]:checked").val());
                            $("#bsnmNoNm").val($.trim(ctrl.obj.find("input[name=chk]:checked").parents("li").find(".srchListView").text()));
                            $("#ctgryNm").val($.trim(ctrl.obj.find("input[name=chk]:checked").parents("li").find(".ctgryNm").val()));
                            $(".btn-role-close").click();
                        }
                    }
                }
            },
            emailSelect : {
                event : {
                    change : function() {
                        var selectValue = document.getElementById('emailSelect').value;
                        $(".secondEmail").val(selectValue)
                    }
                }
            },
        },
        classname : {
            apply : {
                event : {
                    click : function() {
                        var episdSeq = $(this).data("episd");
                        var param = {
                            episdSeq : episdSeq
                        };
                        if(episdSeq == ''){
                            alert("현재 신청 가능한 사업이 없습니다.");
                        }else {
                            //신청페이지 로직점검
                            cmmCtrl.paramAjax(function (data) {
                                if (data.resultCode == 999) {
                                    if (confirm("로그인 후 이용 가능한 서비스입니다.\n로그인하시겠습니까?")) {
                                        location.href = "/login?rtnUrl=" + encodeURIComponent(window.location.pathname);
                                    }
                                } else if (data.resultCode == 100) {
                                    alert('해당 사업은 부품사 회원만 신청 가능합니다.');
                                } else if (data.resultCode == 300) {
                                    if (confirm("이미 신청한 사업입니다.\n신청한 이력은 마이페이지에서 확인 할 수 있습니다.\n마이페이지로 이동하시겠습니까?")) {
                                        location.href = "/my-page/main";
                                    }
                                } else if (data.resultCode == 200) {
                                    location.href = "./step1?episdSeq=" + episdSeq;
                                }
                            }, "./applyChecked", param, "json", false, false, "get");
                        }
                    }
                }
            },
            infoSameChk :{
                event : {
                    keyup : function() {
                        document.getElementById("infoSameChk").checked = false;
                    }
                }
            },
            bsnmNoBtn : {
                event : {
                    click : function(e) {
                        openPopup('partCompanySrchPop', e);
                    }
                }
            },
            addMore : {
                event : {
                    click : function() {
                        $('#firstIndex').val(0);
                        $('#recordCountPerPage').val(parseInt(addCount+10));
                        cmmCtrl.listFrmAjax(function(respObj) {
                            //CALLBACK 처리
                            $('.divide').empty();
                            $('.divide').append(respObj);
                            addCount = 10+addCount;
                            //전체 갯수
                            var totalCnt = $("#totalCount").val();
                            if (addCount >= totalCnt) {
                                $('.add-load').hide();
                            } else {
                                $('.item-count').text("("+ addCount + "/" + totalCnt +")");
                            }
                            //페이징 처리
                        }, "./addRoundMore", $formObj, "POST", "html");
                    }
                }
            },
            insert : {
                event : {
                    click : function() {
                        var email = $(".firstEmail").val() + "@" + $(".secondEmail").val();
                        $(".newEmail").val(email);
                        /*TO-DO
                        * 첨부파일 여부 체크 필요
                        * */


                        //이용약관 체크여부
                        if ($('#agreeChk').is(':checked')) {

                            cmmCtrl.fileFrmAjax(function(data){
                                console.log(JSON.stringify(data));
                                var appctnSeq = data.WBJAcomDTO.appctnSeq;

                                //콜백함수. 페이지 이동
                                location.href = "./complete";
                            }, "./insert", $formObj, "json");
                        } else {
                            alert('약관에 동의해주세요.')
                        }
                    }
                }
            },
            /*searchFile : {
                event : {
                    click : function() {
                        imageText = $(this).attr("id");
                    }
                }
            }*/
        },
        immediately : function(){
            $formObj.find("input[type=file]").fileUpload({
                loading:false,
                sync:true
            },function(data){
                console.log(data);
                //해당 input file 객체에 data(tempFileData) 응답 값이 저장
                $('.empty-txt').text(data[0].orgnFileNm);
            });
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});

