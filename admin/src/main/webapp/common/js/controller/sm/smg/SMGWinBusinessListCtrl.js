define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sm/smg/SMGWinBusinessListCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");
    var $pageObj = jQuery("#frmSearch");
    var langCd = $("#langCd").val();
    var idNum = 0;
    //목록 조회
    // set model
    ctrl.model = {
        id : {
            //검색버튼 클릭시
            busPlus : {
                event : {
                    click : function() {

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
            },
            saveBtn : {
                event : {
                    click : function(){

                        var rows = document.getElementById("temp").getElementsByTagName("tr");
                        var dataList = [];
                        var str = "";
                        for(var r=0; r<rows.length; r++){
                            var cells = rows[r].getElementsByTagName("td");
                            var titl = cells.item(0).lastChild.value;
                            var cntn = cells.item(1).lastChild.value;
                            var urlLink = cells.item(2).lastChild.value;
                            var mainYn = cells.item(3).lastChild.value;

                            dataList.push(titl);
                            dataList.push(cntn);
                            dataList.push(urlLink);
                            dataList.push(mainYn);
                            str = "제목:"+titl+"/컨텐츠:"+cntn+"/urlLink"+urlLink+"/mainYn"+mainYn;
                            console.log(str);
                            $.ajax({
                                type : 'post',
                                url : './insert',
                                data : str,
                                dataType : 'text',
                                dataRespType : 'json',
                                error: function(xhr, status, error){
                                    console.log(error);
                                },
                                success : function(json){
                                    alert(json)
                                }
                            });
                        }

                       // 유효성 체크

                        var title = $(".title").val();
                        var cntn = $(".cntn").val();
                        var urlChk = $(".urlChk").val();

                       if(title == "" || title == null ){
                           alert("사업명을 입력해주세요.");
                           $(".title").focus();
                       }else if(cntn == "" || cntn == null){
                           alert("안내 메시지를 입력해주세요.");
                           $(".cntn").focus();
                       }else if(urlChk == "" || urlChk == null){
                           alert("링크 url을 입력해주세요.");
                           $(".cntn").focus();
                       }

                    }
                }
            },//데이터 삭제
            btnDeleteCheck : {
                event : {
                    click : function() {
                        var frmDataObj    = $(this).closest("form");
                        var delActCnt = $(this).closest("tbody").children("tr").length;
                        var delExpect = document.getElementsByClassName('delArea').length+1;
                        var delTrue = delActCnt - delExpect;
                        if(delTrue == 0){
                            alert("사업 목록 1개 이상은 있어야됩니다.")
                        }else if(delTrue >= 1){
                            var selectId = "#"+$(this).closest("tr").prop('id');
                            $(selectId).css('display', 'none');
                            $(selectId).attr('checked', 'true');
                            $(selectId).attr('class', 'delArea');
                        }
                    }
                }
            },
            busPlus : {
                event : {
                    click : function(){
                        var copyArea = document.getElementById("copyArea");
                        var newArea = copyArea.cloneNode(true);
                        idNum++;
                        newArea.id = "copyArea"+idNum;
                        copyArea.after(newArea);
                        $("#copyArea"+idNum).find("input").val('');
                    }
                }
            },
            mainYn : {
                event : {
                    click : function(){
                        var mainYnVal = $(this).children("option:selected").text();
                        var prev = $(this).parent().prev();
                        var prMainYnVal = prev.find(".mainYnVal")
                        if(mainYnVal == '노출'){
                            prMainYnVal.val('Y');
                        }else{
                            prMainYnVal.val('N');
                        }
                    }
                }
            }
        },
        immediately : function() {

        }
    };


    ctrl.exec();

    return ctrl;
});

