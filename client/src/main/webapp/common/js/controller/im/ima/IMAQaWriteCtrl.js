define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/im/ima/IMAQaWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // function



    // set model
    ctrl.model = {
        id : {

            inqFir : {
                event : {
                    change : function() {
                        var inqFir = document.getElementById('inqFir');
                        var selectFir = inqFir.options[inqFir.selectedIndex].value;
                        var inqSecOpsions = document.getElementsByClassName("inqSec");
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
                            $("#inqSec").prop("selectedIndex", 0);
                        }
                        else {
                            for(var i = 0, max = inqSecOpsions.length; i < max; i++) {
                                addOption = inqSecOpsions.item(i);
                                addOption.style.display = "none";
                            }
                            $("#inqSec").prop("selectedIndex", 0);
                        }
                    }
                }
            },

        },
        classname : {

        },
        immediately : function() {

        }
    };


    ctrl.exec();

    return ctrl;
});

