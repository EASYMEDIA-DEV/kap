define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbl/WBLSurveyWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");


    var questionSet = function(){

        $(".surveyList").each(function(){
            var surveyTypeData = $(this).data('survey-type');
            var cnt = 1;
            var subCnt = 1;
            $("."+surveyTypeData).each(function(index){                         // 질문, 하위질문 번호를 구분하고 순서를 셋팅
                if ($(this).find('input[name=dpth]').val() == '2'){
                    $("."+surveyTypeData+"questionTxt:eq("+index+")").text("└질문"+eval(cnt-1)+"-"+subCnt);
                    subCnt = subCnt + 1;
                }else{
                    $("."+surveyTypeData+"questionTxt:eq("+index+")").text("질문"+cnt);
                    cnt = cnt+1;
                    subCnt = 1;
                }
            });
        });
    }

    var episdSurveyEpisdSelect = function(episdSurveyYear){

        $('select[name=episd]').children('option:not(:first)').remove();
        $("input[name=episdSurveyYearEpisd]").each(function(){
            var year = $(this).data('year');
            var episd = $(this).data('episd');
            if(year == episdSurveyYear){
                $('select[name=episd]').append($('<option></option>').attr('value',episd).text(episd+"차"));
            }
        });
    }


    // set model
    ctrl.model = {
        id : {
        },
        classname : {


            submitCrtfnNo : {
                event : {
                    click : function(){
                        if(confirm("발송하시겠습니까?")){

                            var tempForm = {};

                            tempForm.sendType = $(this).data("sendType");
                            tempForm.crtfnNo = $(this).data("crtfnNo");
                            // tempForm.picNm = $(this).data("picNm");
                            tempForm.email = $(this).data("email");
                            tempForm.telNo = $(this).data("telNo");
                            tempForm.cxstnSrvSeq = $("#detailsKey").val(); // 2024-07-08 추가개발 ppt 4, 7 추가
                            tempForm.partCmpnNm2 = $('input[name="partCmpnNm2"]').val(); // 2024-07-09 추가개발 ppt 13 추가


                            cmmCtrl.jsonAjax(function(data){
                                /* 2024-07-08 추가개발 ppt 4, 7 추가 s */
                                try {
                                    // console.log(data); // 서버에서 반환한 데이터 확인
                                    if (data.respCnt > 0) {
                                        alert("인증번호가 발송되었습니다.");
                                        $("#sendDtm").text("발송");
                                    } else {
                                        alert("인증번호 발송에 실패했습니다.");
                                    }
                                } catch (e) {
                                    console.error("Error processing response data:", e);
                                }
                                /* 2024-07-08 추가개발 ppt 4, 7 추가 e */
                            }, './submitCrtfnNo', tempForm , 'json')


                        }
                    }
                }
            },
            episdSurveySelect : {
                event : {
                    change : function(){
                        var episdSurveyYear = $formObj.find("select[name=year] option:selected").val();
                        var episdSurveyEpisd = $formObj.find("select[name=episd] option:selected").val();
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
            btnReset : {
                event : {
                    click : function(){
                        if(confirm("초기화 하시겠습니까?")){
                            var srvRspnSeq = $(this).data("srv-rspn-seq");
                            var cxstnSrvSeq = $(this).data("cxstn-srv-seq");

                            var svMst = {};
                            svMst.srvRspnSeq = srvRspnSeq;
                            svMst.cxstnSrvSeq = cxstnSrvSeq;

                            cmmCtrl.jsonAjax(function(data){
                                var rtn = JSON.parse(data);
                                if(rtn.respCnt>0){
                                    location.reload();
                                }
                            }, './updateSurveyRspn', svMst, "text")
                        }
                    }
                }
            },
        },
        immediately : function() {

            questionSet();

            $('select[name=year]').children('option:not(:first)').remove();
            var year = "";
            $("input[name=episdSurveyYearEpisd]").each(function(){
                var yearData = $(this).data('year');
                if (year != yearData){
                    $('select[name=year]').append($('<option></option>').attr('value',yearData).text(yearData+"년"));
                }
                year = yearData;
            });


            // 유효성 검사
            $formObj.validation({
                after : function() {
                    return true;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


                        var svMst = {};
                        svMst.year = $formObj.find("select[name=year] option:selected").val();
                        svMst.episd = $formObj.find("select[name=episd] option:selected").val();
                        svMst.srvSeq = $formObj.find("input[name=episdSurveySrvSeq]").val();
                        svMst.cxstnCmpnEpisdSeq = $formObj.find("input[name=cxstnCmpnEpisdSeq]").val();
                        svMst.partCmpnNm1 = $formObj.find("input[name=partCmpnNm1]").val();
                        svMst.partCmpnCd1 = $formObj.find("input[name=partCmpnCd1]").val();
                        svMst.partCmpnNm2 = $formObj.find("input[name=partCmpnNm2]").val();
                        svMst.partCmpnCd2 = $formObj.find("input[name=partCmpnCd2]").val();
                        svMst.rprsntNm = $formObj.find("input[name=rprsntNm]").val();
                        svMst.bsnmRegNo = $formObj.find("input[name=bsnmRegNo]").val();
                        svMst.picNm = $formObj.find("input[name=picNm]").val();
                        svMst.telNo = $formObj.find("input[name=telNo]").val();
                        svMst.email = $formObj.find("input[name=email]").val();
                        svMst.detailsKey = $formObj.find("input[name=detailsKey]").val();  //2024-11-11 추가

                        cmmCtrl.jsonAjax(function(data){
                           location.replace("./list");
                        }, actionUrl, svMst, "text")
                    }
                }
            });
        }
    };
    ctrl.exec();

    return ctrl;
});

