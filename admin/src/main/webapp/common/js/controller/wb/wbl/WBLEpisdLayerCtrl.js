define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLEpisdLayerCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    // form Object
    var $formObj = ctrl.obj.find("#formEpids");

    //목록 조회
    var search = function(page){
        //data로 치환해주어야한다.
        cmmCtrl.setFormData($formObj);


        if(page != undefined){
            $formObj.find("#pageEpidsIndex").val(page);
        }
        console.log($formObj.find("#pageEpidsIndex").val());
        cmmCtrl.listFrmAjax(function(respObj) {

            ctrl.obj.find("#listEpisdContainer").html(respObj);

            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
//            ctrl.obj.find("#listEpisdContainerTotCnt").text(totCnt);

            //페이징 처리
            fn_episdpaging(totCnt, $formObj, "listEpisdContainer", "pagingEpisdContainer");
        }, "/mngwserc/wb/wbl/selectEpisd", $formObj, "POST", "html");
    }

    //페이징 처리
    var fn_episdpaging = function(totCnt, $formObj, listFormId, pagingFormId){

        // 페이징 세팅
        const pageRowSize	 = parseInt($formObj.find("#listEpidsRowSize").val()); // 한 페이지에 보여지는 데이터의 수
        const pageButtonSize = parseInt($formObj.find("#pageEpidsRowSize").val()); // 한 번에 보여질 페이지 버튼의 수 (홀수여야만 함)
        const totalCount = parseInt(totCnt); // 전체 데이터 수
        const totalPage = parseInt((totalCount-1)/pageRowSize) + 1; // 전체 페이지 수

        let data = {
            pageRowSize: pageRowSize,
            pageButtonSize: pageButtonSize,

            totalCount: totalCount,
            totalPage: totalPage,

            now:0,
            prev:0,
            next:0,

            start: 0,
            end:0,
        };

        data.now = parseInt( $formObj.find("#pageEpidsIndex").val() );

        // this.totalCount = this.$root.totalCount;
        data.totalPage = Math.ceil(data.totalCount / data.pageRowSize);
        // 검색결과 없는 경우 totalPage = 1로 설정
        data.totalPage = data.totalPage == 0 ? 1 : data.totalPage ;

        // data.prev = (data.now  > 1) ? data.now - 1 : null
        data.prev = (data.now  > 1) ? data.now - 1 : data.now
        // data.next = (data.totalPage !== data.now && data.totalPage != 0) ? data.now + 1 : null
        data.next = (data.totalPage !== data.now && data.totalPage != 0) ? data.now + 1 : data.now

        data.start =  (Math.ceil(data.now / data.pageButtonSize) - 1) * data.pageButtonSize + 1;
        data.end = ((data.start + data.pageButtonSize) > data.totalPage ? data.totalPage : (data.start + data.pageButtonSize - 1))
        var button='';

        for(var i=data.start; i<=data.end; i++){
            if(i==data.now){
                button += '<li aria-disabled="false" class="active" aria-selected="false" ><a href="javascript:" class="pageSet" value="'+ i +'" data-page="'+ i +'">' + i + '</a></li>';
            }else {
                button += '<li aria-disabled="false" aria-selected="false" ><a href="javascript:" class="pageSet" value="' + i + '" data-page="'+ i +'">' + i + '</a></li>';
            }
        }

        // 페이징 버튼
        var template = '<div class="text-center">' +
            '<div class="col-sm-12">' +
            '<ul class="pagination">' +
            '<li><a href="javascript: " class="pageSet button" value="1" data-page="1">&lt;&lt;</a></li>'+
            '<li><a href="javascript:" class="pageSet button" value="' + data.prev + '" data-page="' + data.prev + '">&lt;</a></li>' +
            button +
            '<li><a href="javascript:" class="pageSet button" value="' + data.next + '" data-page="' + data.next + '">&gt;</a></li>' +
            '<li><a href="javascript:" class="pageSet button" value="' + data.totalPage + '" data-page="' + data.totalPage + '">&gt;&gt;</a></li>' +
            '</ul>' +
            '</div>' +
            '</div>';

        // 화면 붙이기
        $formObj.find("#"+pagingFormId).html(template);
    }


    // set model
    ctrl.model = {
        id : {
            btnLayerSearch : {
                event : {
                    click : function() {
                        cmmCtrl.setFormData($formObj);
                        search(1);
                    }
                }
            },
            listRowSize : {
                event : {
                    change : function(){
                        search(1);
                    }
                }
            },
        },
        classname : {
            // 페이징 처리
            pageSet : {
                event : {
                    click : function() {
                        if( $(this).attr("value") != "null" ){
                            search($(this).attr("value"));
                        }
                    }
                }
            },
            // 페이징 목록 갯수
            listRowSizeContainer : {
                event : {
                    change : function(){
                        //리스트 갯수 변경
                        $formObj.find("#listEpidsRowSize").val($(this).val());
                        search(1);
                    }
                }
            },
            epidsDel : {
                event : {
                    click : function(){
                        var rspnCnt = $(this).data('rspn-cnt');
                        if (rspnCnt > 0){
                            alert('해당 설문에 응답자가 존재하여 삭제할 수 없습니다.');
                            return;
                        }else{
                            if (confirm('선택한 회차를 삭제하시겠습니까?')){
                                var episdMst = {};
                                episdMst.detailsKey = $(this).data('details-key');

                                cmmCtrl.jsonAjax(function(data){
                                    alert('회차가 삭제되었습니다.');
                                    location.replace("./list");
                                }, './deleteEpisd', episdMst, "text")
                            }
                        }
                    }
                }
            },
            epidsSave : {
                event : {
                    click : function(){
                        var episdYear = $formObj.find("select[name=episdYear] option:selected").val();
                        var episdEpisd = $formObj.find("select[name=episdEpisd] option:selected").val();
                        var episdSrvSeq = $formObj.find("select[name=episdSrvSeq] option:selected").val();
                        var episdCheck = episdYear+episdEpisd;

                        if (episdYear ==""){
                            alert('년도를 선택해주세요.');
                            return;
                        }
                        if (episdEpisd ==""){
                            alert('회차를 선택해주세요.');
                            return;
                        }
                        if (episdSrvSeq ==""){
                            alert('설문지를 선택해주세요.');
                            return;
                        }
                        var episdMst = {};
                        episdMst.year = episdYear;
                        episdMst.episd = episdEpisd;
                        episdMst.srvSeq = episdSrvSeq;

                        var episdIsCheck = false;
                        $('input[name=episdCheck]').each(function(index) {
                            if ($(this).val() == episdCheck) {
                                episdIsCheck = true;
                                return false;
                            }
                        });

                        if (episdIsCheck){
                            alert('해당 회차 설문에 응답자가 존재하여 수정할 수 없습니다.');
                            return;
                        }else{
                            cmmCtrl.jsonAjax(function(data){
                                alert('회차등록이 완료되었습니다.');
                                location.replace("./list");
                            }, './insertEpisd', episdMst, "text")
                        }
                    }
                }
            },
        },
        immediately : function() {
            // 리스트 조회

            cmmCtrl.setFormData($formObj);
            search($formObj.find("#pageEpidsIndex").val());

        }
    };
    ctrl.exec();

    return ctrl;
});

