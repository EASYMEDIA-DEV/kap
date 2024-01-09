define(["ezCtrl", "controller/co/COMenuCtrl"], function(ezCtrl, menuCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wba/WBAManagementTreeCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    var $modalObj = $('.wbaTreeLayer');

    var fn_menuInfo = function(menuSeq,type) {
        jQuery.ajax({
            url:  "/mngwserc/co/cob/cobb/details",
            type: "post",
            data:
                {
                    "menuSeq": menuSeq,
                    "Ran": Math.random(),
                    "_csrf": jQuery("#csrfKey").val()
                },
            dataType: "json",
            success: function (data) {
              var menuInfo = data.rtnData;

              if (type == "userMenu") {
                if (menuInfo.userUrl) {
                    alert('이미 사용 중인 메뉴입니다.');
                    return false;
                }else if(menuInfo.dpth < 5 ){
                    alert('사업명을 선택해주세요.');
                    return false;
                }else {
                    $('#userMenuSeq').val(menuInfo.menuSeq);
                    $('#userMenuName').val(menuInfo.menuNm);

                    $modalObj.modal("hide");
                }
              } else if (type == "adminMenuChoice") {
                  if (menuInfo.admUrl) {
                      alert('이미 사용 중인 메뉴입니다.');
                      return false;
                  }else if(menuInfo.dpth < 4 ){
                      alert('사업명의 하위 메뉴를 선택해주세요.');
                      return false;
                  }else {
                      $('#admEpisdMenuSeq').val(menuInfo.menuSeq);
                      $('#admEpisdMenuName').val(menuInfo.parntName+"("+menuInfo.menuNm+")");

                      $modalObj.modal("hide");
                  }
              } else if (type == "adminMenuCompany") {
                  if (menuInfo.admUrl) {
                      alert('이미 사용 중인 메뉴입니다.');
                      return false;
                  } else {
                      $('#admAppctnMenuSeq').val(menuInfo.menuSeq);
                      $('#admAppctnMenuName').val(menuInfo.parntName+"("+menuInfo.menuNm+")");

                      $modalObj.modal("hide");
                  }
              }
            }
        });
    }

    // create object function

    // set model
    ctrl.model = {
        id : {
            // do something...
        },
        classname : {
            // do something...
            menuChoice : {
                event : {
                    click : function() {
                        var buttonType = $("#menuType").val();
                        var menuSeq = $("#divCategoris").find(".jstree-clicked").parent().attr("treeid");

                        fn_menuInfo(menuSeq,buttonType);

                    }
                }
            }
        },
        immediately : function() {
        }
    };

    // execute model
    ctrl.exec();

    return ctrl;
});