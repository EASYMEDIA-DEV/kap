define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbk/WBKPlaceMakeCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    ctrl.model = {
        id : {
        },
        classname : {
            btnEduPlaceLayerChoice :{
                event : {
                    click: function(){

                        var seq = ctrl.obj.find("input[name=delValueList]:checked").val();
                        var titl = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".srchListView").text()); // 장소명
                        var rgnsNm = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".rgnsName").text()); // 지역
                        var bscAddr = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".bscAddr").text()); // 주소
                        var rprsntTelNo = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".rprsntTelNo").text()); // 대표번호

                        $("input[name=placeSeq]").val(seq);

                        var tc = new Array(); // 배열 생성
                        tc.push({titl : titl, rgnsName : rgnsNm, bscAddr : bscAddr ,rprsntTelNo : rprsntTelNo }); // 배열 값 넣기

                        // 해당 지역 선택 시 테이블 td 그리기
                        var html = '';

                        for(var i = 0; i < 1; i++){
                            html += '<tr>';
                            html += '<td class=\"text-center\">'+tc[i].titl+'</td>';
                            html += '<td class=\"text-center\">'+tc[i].rgnsName+'</td>';
                            html += '<td class=\"text-center\">'+tc[i].bscAddr+'</td>';
                            html += '<td class=\"text-center\">'+tc[i].rprsntTelNo+'</td>';
                            html += '</tr>';
                        }

                        $("#listContainer").empty();
                        $("#listContainer").append(html);

                    }
                }
            },
        },
        immediately : function(){
        }
    };
    // execute model
    ctrl.exec();
    return ctrl;
});

