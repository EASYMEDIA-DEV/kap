define(["ezCtrl", "ezVald"], function(ezCtrl, ezVald) {
    "use strict";
    // set controller name
    var exports = {
        controller : "controller/mp/mpc/MPConsultingSurveyStep2Ctrl"
    };
    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);
    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    var questionSet = function(surveyType) {


        $(".surveyList").each(function () {
            var $surveyObj = $(this);
            var surveyTypeData = $(this).data('survey-type');
            var cnt = 1;
            var subCnt = 1;
            $("." + surveyTypeData).each(function (index) {                         // 질문, 하위질문 번호를 구분하고 순서를 셋팅
                if ($(this).find('input[name=dpth]').val() == '2') {
                    $("." + surveyTypeData + "questionTxt:eq(" + index + ")").text("Q" + eval(cnt - 1) + "-" + subCnt);
                    $(this).addClass(eval(cnt - 1) + "-" + subCnt);
                    subCnt = subCnt + 1;
                } else {
                    $("." + surveyTypeData + "questionTxt:eq(" + index + ")").text("Q" + cnt);
                    cnt = cnt + 1;
                    subCnt = 1;
                }
            });

        });
    }

    // set model
    ctrl.model = {
        id : {
            btnSubmit : {
                event : {
                    click : function() {
                        $formObj.submit();
                    }
                }
            },
            sameAsSubscriberChk : {
                event : {
                    click : function() {
                        if($(this).is(":checked")){
                            $("input[name=ptcptName]").val($("input[name=rtnPtcptName]").val());
                            $("input[name=ptcptTelno]").val($("input[name=rtnPtcptTelno]").val());
                            $("input[name=ptcptPstn]").val($("input[name=rtnPtcptPstn]").val());
                        }else{
                            $("input[name=ptcptName]").val("");
                            $("input[name=ptcptTelno]").val("");
                            $("input[name=ptcptPstn]").val("");
                        }
                    }
                }
            },
            pdfDownload : {
                event : {
                    click : function() {
                        $("#pdfDownload").hide();
                        $(".page-bot-btn-sec").hide();

                        html2canvas($('.cont-wrap')[0]).then(function (canvas) {
                            var filename = '컨설팅_만족도_설문_' + Date.now() + '.pdf';
                            var doc = new jsPDF('p', 'mm', 'a4');
                            var imgData = canvas.toDataURL('image/png');
                            var imgWidth = 210;
                            var pageHeight = 295;
                            var imgHeight = canvas.height * imgWidth / canvas.width;
                            var heightLeft = imgHeight;
                            var position = 0;
                            doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
                            heightLeft -= pageHeight;
                            while (heightLeft >= 0) {
                                position = heightLeft - imgHeight;
                                doc.addPage();
                                doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
                                heightLeft -= pageHeight;
                            }
                            doc.save(filename);
                        });
                        $("#pdfDownload").show();
                        $(".page-bot-btn-sec").show();

                    }
                }
            },
        },
        classname : {
            answer : {
                event : {
                    click : function() {
                        var surveyList = $(this).closest('.survey-list');
                        var nextNo = $(this).data("next-no");

                        if (nextNo != ''){
                            surveyList.find(".survey-list-inner:not(:eq(0)) input:radio").prop('checked',false).hide();
                            surveyList.find('.survey-list-inner:not(:eq(0))').hide();

                            if (nextNo.indexOf(',') > 0){
                                var nextNoSplit = nextNo.split(',');
                                $(nextNoSplit).each(function(i){
                                    surveyList.find('.'+nextNoSplit[i]).show();
                                });
                            }else{
                                surveyList.find('.'+nextNo).show();
                            }
                        }
                    }
                }
            },
        },
        immediately : function() {
            questionSet('CON');

            // 유효성 검사
            $formObj.validation({
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = "./insert";

                        var svRepnMst = {};
                        svRepnMst.srvSeq = $formObj.find("input[name=srvSeq]").val();
                        svRepnMst.rfncCd = $formObj.find("input[name=rfncCd]").val();
                        svRepnMst.rfncSeq = $formObj.find("input[name=rtnCnstgSeq]").val();
                        svRepnMst.memSeq = $formObj.find("input[name=rtnMemSeq]").val();
                        svRepnMst.ptcptName = $formObj.find("input[name=ptcptName]").val();
                        svRepnMst.ptcptTelno = $formObj.find("input[name=ptcptTelno]").val();
                        svRepnMst.ptcptPstn = $formObj.find("input[name=ptcptPstn]").val();
                        svRepnMst.ptcptEmail = $formObj.find("input[name=rtnPtcptEmail]").val();

                        svRepnMst.svSurveyQstnRspnDtlList = new Array();

                        $(".surveyList").each(function(){
                            var svQstnDtl = {};
                            var $surveyListObj = $(this);
                            svQstnDtl.srvTypeCd = $surveyListObj.find("input[name=srvTypeCd]").val();
                            svQstnDtl.qstnSeq = $surveyListObj.find("input[name=qstnSeq]").val();


                            if ($(this).find(".exmplList").size() > 0 ) {
                                svQstnDtl.svSurveyExmplRspnDtlList = new Array();
                                $(this).find(".exmplList").each(function(index){
                                    var svExmplDtl = {};
                                    if(svQstnDtl.srvTypeCd == 'QST03' || svQstnDtl.srvTypeCd == 'QST04') {
                                        svExmplDtl.sbjctRply = $(this).find(".answer").val();
                                        svExmplDtl.exmplSeq = $(this).find(".answer:checked").val();
                                    }else{
                                        svExmplDtl.exmplSeq = $(this).find(".answer:checked").val();
                                    }

                                    if (typeof svExmplDtl.exmplSeq != 'undefined' || typeof svExmplDtl.sbjctRply != 'undefined'){
                                        svQstnDtl.svSurveyExmplRspnDtlList.push(svExmplDtl);
                                    }
                                });
                            }
                            svRepnMst.svSurveyQstnRspnDtlList.push(svQstnDtl);
                        });

                        cmmCtrl.jsonAjax(function(data){
                            location.href = "./surveyStep3?detailsKey="+$formObj.find("input[name=rtnCnstgSeq]").val()
                        }, actionUrl, svRepnMst, "text")
                    }
                }
            });

        }
    };

    ctrl.exec();

    return ctrl;
});