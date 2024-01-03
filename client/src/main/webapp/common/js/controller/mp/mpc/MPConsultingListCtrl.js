define(["ezCtrl"], function(ezCtrl) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpc/MPConsultingListCtrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    /*var memberSearch = function(){*/
    /*var memberInfo = {};
        cmmCtrl.jsonAjax(function(data){
        }, actionUrl,memberInfo, "json")
    }*/

    // set model
    ctrl.model = {
        id : {
        },
        classname : {
            dtlBtn : {
                event : {
                    click : function(){
                        var conSeq = $(this).data("seq");
                        var statusType = $("."+conSeq+"statusType").text();
                        location.href="./detail?detailsKey="+conSeq+"?statusType="+statusType;
                    }
                }
            }
        },
        immediately : function() {
            $(window).ready(function(){
                var seqLength = $(".cnstgSeq").length;
                var arr = new Array(seqLength);
                var data = {};
                for(var i=0; i<seqLength; i++){
                    arr[i] = $(".cnstgSeq").eq(i).val();
                    data.cnstgSeq =  $(".cnstgSeq").eq(i).val();
                    cmmCtrl.jsonAjax(function(data){
                        var str = data[0].appctnTypeCd;
                        $("."+arr[i]+"appctnCd").text(str.slice(1));
                    }, "./appctnType", data, "json")
                }
            })
        }
    };

    ctrl.exec();

    return ctrl;
});