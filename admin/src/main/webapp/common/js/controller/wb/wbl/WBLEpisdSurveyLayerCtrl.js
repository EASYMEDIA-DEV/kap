define(["ezCtrl"], function(ezCtrl) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLEpisdSurveyLayerCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);


    // form Object
    var $formObj = ctrl.obj.find("#formEpidsSurvey");

    var episdSurveyEpisdSelect = function(episdSurveyYear){

        $('select[name=episdSurveyEpisd]').children('option:not(:first)').remove();
        $("input[name=episdSurveyYearEpisd]").each(function(){
            var year = $(this).data('year');
            var episd = $(this).data('episd');
            if(year == episdSurveyYear){
                $('select[name=episdSurveyEpisd]').append($('<option></option>').attr('value',episd).text(episd+"차"));
            }
        });
    }
    // set model
    ctrl.model = {
        id : {

        },
        classname : {
            episdSurveySelect : {
                event : {
                    change : function(){
                        var episdSurveyYear = $formObj.find("select[name=episdSurveyYear] option:selected").val();
                        var episdSurveyEpisd = $formObj.find("select[name=episdSurveyEpisd] option:selected").val();
                        var episdSurveyInfo = episdSurveyYear+episdSurveyEpisd;

                        $("input[name=episdSurveySrvSeq]").val('');
                        $(".episdSurveyText").text('');

                        $("input[name=episdSurveyYearEpisd]").each(function(){
                            if ($(this).val() == episdSurveyInfo){
                                $("input[name=episdSurveySrvSeq]").val($(this).data("srv-seq"));
                                $(".episdSurveyText").text($(this).data("titl"));
                                return false;
                            }
                        });
                    }
                }
            },
            episdSurveyYear : {
                event : {
                    change : function(){
                        episdSurveyEpisdSelect($(this).val());
                    }
                }
            },
            epidsSurveySave : {
                event : {
                    click : function(){
                        var episdYear = $formObj.find("select[name=episdSurveyYear] option:selected").val();
                        var episdEpisd = $formObj.find("select[name=episdSurveyEpisd] option:selected").val();
                        var episdSrvSeq = $formObj.find("input[name=episdSurveySrvSeq]").val();

                        if (episdYear == ""){
                            alert('년도를 선택해주세요.');
                            return;
                        }
                        if (episdEpisd == ""){
                            alert('회차를 선택해주세요.');
                            return;
                        }


                        if($(".dropzone .dz-preview").length < 1) {
                            alert('첨부파일을 등록해주세요.')
                            return;
                        }

                        var episdMst = {};
                        episdMst.year = episdYear;
                        episdMst.episd = episdEpisd;
                        episdMst.srvSeq = episdSrvSeq;

                    }
                }
            },
        },
        immediately : function() {
            // 리스트 조회

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

            $('select[name=episdSurveyYear]').children('option:not(:first)').remove();
            var year = "";
            $("input[name=episdSurveyYearEpisd]").each(function(){
                var yearData = $(this).data('year');
                if (year != yearData){
                    $('select[name=episdSurveyYear]').append($('<option></option>').attr('value',yearData).text(yearData+"년"));
                }
                year = yearData;
            });
        }
    };
    ctrl.exec();

    return ctrl;
});

