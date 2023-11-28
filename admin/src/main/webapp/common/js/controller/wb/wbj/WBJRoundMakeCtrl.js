define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbj/WBJRoundMakeCtrl"
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
                        var rgnsNm = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".rgnsNm").text()); // 지역
                        var addr = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".addr").text()); // 주소
                        var rprsntTelNo = $.trim(ctrl.obj.find("input[name=delValueList]:checked").parents("tr").find(".rprsntTelNo").text()); // 대표번호

                        $("input[name=placeSeq]").val(seq);

                        var tc = new Array(); // 배열 생성
                        tc.push({titl : titl, rgnsNm : rgnsNm, addr : addr ,rprsntTelNo : rprsntTelNo }); // 배열 값 넣기

                        // 해당 지역 선택 시 테이블 td 그리기
                        var html = '';

                        for(var i = 0; i < 1; i++){
                            html += '<tr>';
                            html += '<td class=\"text-center\">'+tc[i].titl+'</td>';
                            html += '<td class=\"text-center\">'+tc[i].rgnsNm+'</td>';
                            html += '<td class=\"text-center\">'+tc[i].addr+'</td>';
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

