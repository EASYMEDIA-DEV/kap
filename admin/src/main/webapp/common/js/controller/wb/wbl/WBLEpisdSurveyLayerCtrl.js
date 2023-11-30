define(["ezCtrl" ,"ezVald"], function(ezCtrl , ezVald) {

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
                                $("input[name=cxstnCmpnEpisdSeq]").val($(this).data("cxstn-cmpn-episd-seq"));
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
            excelSample : {
                event : {
                    click : function(){
                        location.href='./excelSampleDownload';
                    }
                }
            },
        },
        immediately : function() {
            // 리스트 조회


            $('select[name=episdSurveyYear]').children('option:not(:first)').remove();
            var year = "";
            $("input[name=episdSurveyYearEpisd]").each(function(){
                var yearData = $(this).data('year');
                if (year != yearData){
                    $('select[name=episdSurveyYear]').append($('<option></option>').attr('value',yearData).text(yearData+"년"));
                }
                year = yearData;
            });


            //엑셀업로드
            $formObj.validation({
                after : function(){
                    return true;
                },
                async: {
                    use: true,
                    func: function(obj, tagid, okval, msg) {
                        var formData = new FormData($formObj[0]);
                        formData.append('year', $formObj.find("select[name=episdSurveyYear] option:selected").val());
                        formData.append('episd', $formObj.find("select[name=episdSurveyEpisd] option:selected").val());
                        formData.append('srvSeq',$formObj.find("input[name=episdSurveySrvSeq]").val());
                        formData.append('cxstnCmpnEpisdSeq',$formObj.find("input[name=cxstnCmpnEpisdSeq]").val());

                        jQuery.ajax($formObj.prop("action"), {
                            method: $formObj.prop("method"),
                            data :  formData,
                            dataType : "json",
                            processData: false,
                            contentType: false,
                        })
                            .done(function(r) {
                                alert(r.rtnMsg);
                                $(".close").click();
                            })
                            .fail(function() {
                                alert("잠시후 다시 시도 바랍니다.");
                            })
                    }
                },
                customfunc : function (obj, tagid, okval, msg) {
                    if (jQuery(obj).prop("name") == "wblListExcel"
                        && !!!jQuery(obj).val().match(/.+\.(xls|xlsx)$/)) {
                        return !!alert("엑셀파일을 선택해주세요.")
                    }
                },
                loadingbar: {
                    use: true
                },
            });

        }
    };
    ctrl.exec();

    return ctrl;
});

