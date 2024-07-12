define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/sv/sva/SVASurveyWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    var getCate = function(surveyType){                                  // 설문유형이 체크된 설문문항만 보여준다.
        $('.typeCd').each(function(){
            $("[class*='"+$(this).val()+"']").hide();
        });
        $("[class*='"+surveyType.substring(0,3)+"']").show();
        $("input:radio[name ='typeCd']:input[value='"+surveyType+"']").attr("checked", true);
        $("input[name=preTypeCd]").val(surveyType);

        questionInitSet(surveyType+"01")                         // 초기 설문 문항 설정
    }


    var questionInitSet = function(surveyType){
        $('.surveyList').each(function(index) {                             // 설문문항 초기 폼 (객관식(단일선택) 으로 폼을 셋팅한다.
            var filedset = $(this).closest('fieldset');
            var surveyTypeData = filedset.data('survey-type');
            if ($(this).is(":visible")) {
                filedset.find('input').val('')
                    .end()
                    .find('select option:eq(0)').prop("selected",true)
                    .end()
                    .find('input[type=checkbox]').prop("checked",true)
                    .end()
                    .find('.delQuestion').hide()
                    .end()
                    .find(".subNumber").show().attr("display","none").attr("disabled",true)
                    .end()
                    .find(".answer").css("width","80%").attr('placeholder','응답내용을 입력해주세요.')
                    .end()
                    .find(".answerForm").show()
                    .end()
                    .find('.answerForm:not(:eq(0))').remove()
                    .end()
                    .find('.'+surveyType+'questionTxt').attr("rowspan","3")
                    .end()
                    .find(".addSubQuestion").show()
                    .end()
                    .find("input[name=qstn_nm]").removeClass("notRequired")
                    .end()
                    .find(".answer").removeClass("notRequired")
                    .end()
                    .find('input[name=dpth]').val("1")
                    .end()
                    .find('input[name=nonApplicableYn]').prop("checked",false); //2024-07-12 추가개발 ppt 3 추가
                    $("."+surveyTypeData+":not(:eq(0))").remove();
            }else{
                filedset.find("input[name=qstn_nm]").addClass("notRequired")
                    .end()
                    .find(".answer").addClass("notRequired")
                    .end()

            }
        });
        questionSet(surveyType);                                        // 설문 상세 셋팅
    };

    var questionSet = function(surveyType){


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

        var parnt_qstn_ord = "0";
        var qstn_ord = "0";
        var totCnt = 0;
        var surveyTypeCnt = 1;
        var serveyTypeCd = "";

        $('.surveyList').each(function(index){                          // 설문유형 코드(뎁스2,3,4) 까지의 총갯수를 각각 구해 셋팅
             if($(this).is(":visible")){

                var serveyTypeCdData = $(this).data("survey-type");

                totCnt = totCnt+1;
                if (serveyTypeCdData == serveyTypeCd){
                    surveyTypeCnt = surveyTypeCnt + 1;
                }else{
                    $("#"+serveyTypeCd+"Cnt").text(surveyTypeCnt);
                    surveyTypeCnt = 1;
                    serveyTypeCd = serveyTypeCdData;
                }

                $("#"+serveyTypeCdData.substring(0,5)+"Cnt").text($("[class*='"+serveyTypeCdData.substring(0,5)+"'].surveyList").size());
                $("#"+serveyTypeCdData+"Cnt").text(surveyTypeCnt);
                $('#totCnt').text(totCnt);

                qstn_ord = parseInt(qstn_ord) + 1;
                $(this).find('input[name=qstn_ord]').val(qstn_ord);

                if ($('input[name=dpth]:eq('+index+')').val()=='2'){            // 하위문항일때 부모문항을 매칭하기 위해 parnt ord 를 설정
                    $('input[name=parnt_qstn_ord]:eq('+index+')').val(parnt_qstn_ord);
                }else{
                    $('input[name=parnt_qstn_ord]:eq('+index+')').val("0");
                    parnt_qstn_ord = qstn_ord;
                }
             }
         });
    }


    // set model
    ctrl.model = {
        id : {
            btnSubmit : {
                event : {
                    click : function() {

                        $formObj.find(".ckeditorRequired").each(function() {
                            $('input[name=hiddenTextArea]').val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
                        });

                        $formObj.submit();
                    }
                }
            },
        },
        classname : {
            typeCd : {
                event : {
                    click : function() {
                        if(!confirm("설문유형 변경 시 입력한 값이 초기화 처리됩니다.\n변경하시겠습니까?")){
                            $("input[name=typeCd][value="+$('input[name=preTypeCd]').val()+"]").prop("checked",true);           // 컨펌 취소시 다시 이전에 선택된 값으로 변경하기 위해 저장
                            return;
                        }else{
                            getCate($(this).val());
                        }
                    }
                }
            },
            surveyNextNumChk : {
                event : {
                    keyup : function() {
                        if(/[^,0123456789-]/g.test($(this).val())){
                            $(this).val("");
                        }
                    }
                }
            },
            addQuestion : {
                event : {
                    click : function() {                                // 설문문항 추가 폼 (객관식(단일선택) 으로 폼을 셋팅한다.
                        var filedset = $(this).closest('fieldset');
                        var clone = filedset.clone(true);
                        var surveyType = filedset.data("survey-type");
                        var dpth = clone.find('input[name=dpth]').val();

                        clone.find('input').val('')
                            .end()
                            .find('select option:eq(0)').prop("selected",true)
                            .end()
                            .find('input[type=checkbox]').prop("checked",true)
                            .end()
                            .find('.delQuestion').show()
                            .end()
                            .find(".subNumber").show().attr("disabled",true)
                            .end()
                            .find(".answer").css("width","80%")
                            .end()
                            .find(".addAnswer").show()
                            .end()
                            .find(".delAnswer").show()
                            .end()
                            .find(".answerForm").show()
                            .end()
                            .find('.answerForm:not(:eq(0))').remove()
                            .end()
                            .find('.'+surveyType+'questionTxt').attr("rowspan","3")
                            .end()
                            .find(".addSubQuestion").show()
                            .end()
                            .find('input[name=dpth]').val(dpth)
                            .end()
                            .find('.answerForm th').html('응답<span class="star"> *</span>')
                            .end()
                            .find('input[name=nonApplicableYn]').prop("checked",false); //2024-07-12 추가개발 ppt 3 추가

                        if (dpth == "2"){                                    // 하위문항일때 폼 (객관식(단일선택) 으로 폼을 셋팅한다.
                            clone.find(".addSubQuestion").hide()
                                .end()
                                .find(".subNumber").hide()
                                .end()
                                .find(".answer").css("width","100%")
                        }
                        $(filedset).last().after(clone);

                        questionSet(surveyType);

                    }
                }
            },
            addSubQuestion : {
                event : {
                    click : function() {                                 // 하위문항추가일때 폼 (객관식(단일선택) 으로 폼을 셋팅한다.
                        var filedset = $(this).closest('fieldset');
                        var clone = filedset.clone(true);
                        var surveyType = filedset.data("survey-type");

                        clone.find('input').val('')
                            .end()
                            .find('select option:eq(0)').prop("selected",true)
                            .end()
                            .find('input[type=checkbox]').prop("checked",true)
                            .end()
                            .find(".addSubQuestion").hide()
                            .end()
                            .find(".subNumber").hide()
                            .end()
                            .find(".answer").css("width","100%")
                            .end()
                            .find(".addAnswer").show()
                            .end()
                            .find(".delAnswer").show()
                            .end()
                            .find('.delQuestion').css("display","block")
                            .end()
                            .find('.answerForm:not(:eq(0))').remove()
                            .end()
                            .find('.'+surveyType+'questionTxt').attr("rowspan","3")
                            .end()
                            .find('input[name=dpth]').val("2")
                            .end()
                            .find('.answerForm th').html('응답<span class="star"> *</span>')
                            .end()
                            .find('input[name=nonApplicableYn]').prop("checked",false); //2024-07-12 추가개발 ppt 3 추가
                        filedset.find(".subNumber").attr("disabled",false);
                        $(filedset).last().after(clone);
                        questionSet(surveyType);
                    }
                }
            },
            delQuestion : {
                event : {
                    click : function() {                                                            // 문항삭제
                        var filedset = $(this).closest('fieldset');
                        var surveyType = filedset.data("survey-type");
                        var parnt_qstn_ord = parseInt(filedset.find('input[name=parnt_qstn_ord]').val());
                        var qstn_ord = parseInt(filedset.find('input[name=qstn_ord]').val());
                        var sub_qstn_ord = "N";
                        var subNumberCnt = 0;
                        var parentObj;

                        if (parnt_qstn_ord>0){                                                       // 하위문항 삭제시 상위 문항의 하위번호는 초기화한다.
                            if(!confirm("하위 문항 삭제 시 상위 문항에 하위번호는 초기화됩니다.\n삭제하시겠습니까?")){
                                return;
                            }else{
                                $('input[name=qstn_ord]').each(function(index) {
                                    if ($(this).val() == parnt_qstn_ord) {
                                        parentObj = $(this).closest('fieldset');
                                        $(this).closest('fieldset').find('.subNumber').val('');
                                    }
                                });

                                $('input[name=parnt_qstn_ord]').each(function() {                   // 하위문항을 모두 삭제시 상위 문항의 하위번호는 disable 처리한다.
                                    if ($(this).val() == parnt_qstn_ord) {
                                        subNumberCnt = subNumberCnt + 1;
                                    }
                                });

                                if (subNumberCnt == 1){
                                    parentObj.find('.subNumber').attr("disabled",true);
                                }
                            };
                        }else{
                            $('input[name=parnt_qstn_ord]').each(function() {                       // 상위 문항을 삭제시 하위문항을 모두 지운다.
                                if ($(this).val() == qstn_ord) {
                                    sub_qstn_ord = "Y";
                                    return false;
                                }
                            });
                        }

                        if (sub_qstn_ord == 'Y'){
                            if(!confirm("문항 삭제 시 하위문항도 모두 삭제됩니다.\n삭제하시겠습니까?")){
                                return;;
                            }else{
                                $('input[name=parnt_qstn_ord]').each(function() {
                                    if ($(this).val() == qstn_ord) {
                                        $(this).closest('fieldset').remove();
                                    }
                                });
                            }
                        }

                        $(filedset).remove();
                        questionSet(surveyType);
                    }
                }
            },
            addAnswer : {
                event : {
                    click : function() {
                        var filedset = $(this).closest('fieldset');
                        var surveyType = filedset.data("survey-type");

                        var answerFiledset = $(this).closest('.answerForm');
                        var clone = answerFiledset.clone(true);

                        var rowspan = parseInt(filedset.find("."+surveyType+"questionTxt").attr("rowspan"));        // 응답 추가시 질문컬럼의 rowspan을 더 해준다.
                        filedset.find("."+surveyType+"questionTxt").attr("rowspan",rowspan+1);

                        clone.find('input').val('');
                        $(answerFiledset).last().after(clone);
                    }
                }
            },
            delAnswer : {
                event : {
                    click : function() {
                        var filedset = $(this).closest('fieldset');
                        var surveyType = filedset.data("survey-type");

                        var answerFiledset = $(this).closest('.answerForm');
                        var rowspan = parseInt(filedset.find("."+surveyType+"questionTxt").attr("rowspan"));              // 응답 삭제시 질문컬럼의 rowspan을 빼 해준다.

                        if (filedset.find('.answer').length < 2){
                            alert('응답내용은 최소 1개 이상 존재해야 하므로,더 이상 삭제할 수 없습니다.');
                            return;
                        }else{
                            filedset.find("."+surveyType+"questionTxt").attr("rowspan",rowspan-1);
                            $(answerFiledset).remove();
                        }
                    }
                }
            },
            questionType : {                                                                // 설문 유형 변경시 각 코드에 맞는 폼으로 셋팅한다.
                event : {
                    focus : function(){
                        var filedset = $(this).closest('fieldset');
                        filedset.find('input[name=preQuestionType]').val($(this).val());
                    },
                    change : function() {

                        var filedset = $(this).closest('fieldset');
                        var dpth = filedset.find('input[name=dpth]').val();
                        var qstn_ord = parseInt(filedset.find('input[name=qstn_ord]').val());
                        var preQuestionType = filedset.find('input[name=preQuestionType]').val();
                        var surveyType = filedset.data("survey-type");
                        var sub_qstn_ord = "N";
                        var addAnswerCnt = 0;
                        var msr_stnd_cd = $(this).val();

                        if ($(this).val()=='QST05' || $(this).val()=='QST06' || $(this).val()=='QST07'){

                            var stnd_cd = "";
                            var stnd_text = "";
                            $('.questionType').each(function(){
                                if ($(this).val()=='QST05'||$(this).val()=='QST06'||$(this).val()=='QST07'){
                                    if (msr_stnd_cd != $(this).val()){
                                        stnd_cd = $(this).val();
                                        stnd_text = $(this).find('option:selected').text();
                                        return false;
                                    }
                                }
                            });

                            if (stnd_cd != ''){
                                if (stnd_cd != $(this).val()){
                                    alert(stnd_text+' 로만 생성 가능합니다');
                                    $(this).val(stnd_cd).prop("selected",true);
                                    $(this).trigger("change");
                                    return;
                                }
                            }
                        }

                        if ($(this).val()=='QST02' || $(this).val()=='QST03' || $(this).val()=='QST04'){
                            $('input[name=parnt_qstn_ord]').each(function() {
                                if ($(this).val() == qstn_ord) {
                                    sub_qstn_ord = "Y";
                                    return false;
                                }
                            });

                            if (sub_qstn_ord == 'Y'){
                                if(!confirm("문의유형 변경 시 하위문항은 삭제됩니다\n유형을 변경하시겠습니까?")){
                                    $(this).val(preQuestionType).prop("selected",true);
                                    return;
                                }else{
                                    $('input[name=parnt_qstn_ord]').each(function() {
                                        if ($(this).val() == qstn_ord) {
                                            $(this).closest('fieldset').remove();
                                        }
                                    });
                                    questionSet(surveyType);
                                }
                            }
                        }


                        if ($(this).val()=='QST01' ){        // 객관식 단수
                            filedset.find('.subNumber').show()
                                .end()
                                .find(".addSubQuestion").show()
                                .end()
                                .find(".answer").css("width","80%").attr('placeholder','응답내용을 입력해주세요.').attr('title','응답내용')
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find('.answer').removeClass("notRequired")
                                .end()
                                .find('.answerForm:not(:eq(0))').remove()
                                .end()
                                .find('.addAnswer').show()
                                .end()
                                .find('.delAnswer').show()
                                .end()
                                .find(".answerForm").show()
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')
                        }else if($(this).val()=='QST05'){ // 5점척도

                            var addAnswerCnt = 4;

                            filedset.find('.subNumber').show()
                                .end()
                                .find(".addSubQuestion").show()
                                .end()
                                .find(".answer").css("width","80%").attr('placeholder','라벨명을 입력해주세요.').attr('title','라벨명')
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find('.answer').attr("disabled",true).addClass("notRequired")
                                .end()
                                .find('.addAnswer').hide()
                                .end()
                                .find('.delAnswer').hide()
                                .end()
                                .find(".answerForm").show()
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')

                        }else if($(this).val()=='QST06'){   //  7점척도

                            var addAnswerCnt = 6;

                            filedset.find('.subNumber').show()
                                .end()
                                .find(".addSubQuestion").show()
                                .end()
                                .find(".answer").css("width","80%").attr('placeholder','라벨명을 입력해주세요.').attr('title','라벨명')
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find('.answer').attr("disabled",true).addClass("notRequired")
                                .end()
                                .find('.addAnswer').hide()
                                .end()
                                .find('.delAnswer').hide()
                                .end()
                                .find(".answerForm").show()
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')

                        }else if($(this).val()=='QST07'){   //  10점척도

                            var addAnswerCnt = 9;
                            filedset.find('.subNumber').show()
                                .end()
                                .find(".addSubQuestion").show()
                                .end()
                                .find(".answer").css("width","80%").attr('placeholder','라벨명을 입력해주세요.').attr('title','라벨명')
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find('.answer').attr("disabled",true).addClass("notRequired")
                                .end()
                                .find('.addAnswer').hide()
                                .end()
                                .find('.delAnswer').hide()
                                .end()
                                .find(".answerForm").show()
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')
                        }else if($(this).val()=='QST02'){   // 객관식 복수
                            filedset.find('.subNumber').hide()
                                .end()
                                .find(".addSubQuestion").hide()
                                .end()
                                .find(".answer").css("width","100%").attr('placeholder','응답내용을 입력해주세요.').attr('title','응답내용')
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find('.answer').removeClass("notRequired")
                                .end()
                                .find('.answerForm:not(:eq(0))').remove()
                                .end()
                                .find('.addAnswer').show()
                                .end()
                                .find('.delAnswer').show()
                                .end()
                                .find(".answerForm").show()
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')

                        }else if($(this).val()=='QST03' || $(this).val()=='QST04') {   // 주관식 서술 , 주관식 단답
                            filedset.find(".addSubQuestion").hide()
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find('.answerForm:not(:eq(0))').remove()
                                .end()
                                .find('.answer').addClass("notRequired")
                                .end()
                                .find('.answerForm:not(:eq(0))').remove()
                                .end()
                                .find(".answerForm").hide()
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')
                        }

                        if(dpth == "2"){
                            filedset.find(".addSubQuestion").hide()
                                .end()
                                .find(".addSubQuestion").hide()
                                .end()
                                .find('.subNumber').hide()
                                .end()
                                .find("input[name=qstn_nm]").val("")
                                .end()
                                .find("input[name=exmpl_nm]").val("")
                                .end()
                                .find("input[name=next_no]").val("")
                                .end()
                                .find(".answer").css("width","100%")
                                .end()
                                .find('.answerForm th').html('응답<span class="star"> *</span>')

                        }


                        if (addAnswerCnt >0 ) {
                            filedset.find('.answerForm:not(:eq(0))').remove()
                            var answerFiledset = filedset.find('.answerForm')
                            for (var i = 0; i < addAnswerCnt; i++) {
                                var clone = answerFiledset.clone(true);
                                var rowspan = parseInt(filedset.find("."+surveyType+"questionTxt").attr("rowspan"));
                                filedset.find("."+surveyType+"questionTxt").attr("rowspan",rowspan+1);
                                if (i==0){
                                    clone.find('.answer').attr("disabled",false).removeClass("notRequired");
                                    clone.find('th').html('응답(max)<span class="star"> *</span>')
                                }
                                $(answerFiledset).last().after(clone);
                            }
                            $(answerFiledset).find('.answer').attr("disabled",false).removeClass("notRequired");
                            $(answerFiledset).find('th').html('응답(min)<span class="star"> *</span>')

                        }
                    }
                }
            },
        },
        immediately : function() {

            if ($("input[name=detailsKey]").val()== ''){
                getCate('EDU');
            }else{
                var typeCd = $("input[name=typeCd]:checked").val();
                if (typeCd == 'CON'){
                    typeCd = typeCd+'0101';
                }else{
                    typeCd = typeCd+'01';
                }
                questionSet(typeCd);
            }


            if($("#posbChg").val()=='false'){
                $('div *').find('input, textarea ,select').prop("disabled",true);
                $('.addQuestion').hide();
                $('.addSubQuestion').hide();
                $('.delQuestion').hide();
                $('.addAnswer').hide();
                $('.delAnswer').hide();
                $("input[name=expsYn]").prop("disabled",false);
                /* Editor Setting */
                jQuery("textarea[id^='cntn']").each(function(){
                    cmmCtrl.setEditor({
                        editor : jQuery(this).attr("id"),
                        height : 400,
                        readOnly : true
                    });
                });

            }else{
                /* Editor Setting */
                jQuery("textarea[id^='cntn']").each(function(){
                    cmmCtrl.setEditor({
                        editor : jQuery(this).attr("id"),
                        height : 400
                    });
                });
            }

            // 유효성 검사
            $formObj.validation({
                after: function(){
                    var isValid = true

                    $(".surveyNextNumChk").each(function(){
                        if ($(this).val() !="" && $(this).attr("disabled") != "disabled") {

                            var thisObj = $(this);
                            var filedset = $(this).closest('fieldset');
                            var surveyType = filedset.data('survey-type');
                            var qstnOrd = filedset.find("input[name=qstn_ord]").val();
                            var nextNo = $(this).val();
                            var regex = /^([0-9]{1,2}-?[0-9]{1,2})$/;

                            var dpthText = "";
                            $("."+surveyType+"questionTxt").each(function(){
                                var surveyTypefiledset = $(this).closest('fieldset');
                                var dpth = surveyTypefiledset.find('input[name=dpth]').val();
                                var parntQstnOrd = surveyTypefiledset.find("input[name=parnt_qstn_ord]").val();
                                if (dpth==2 && qstnOrd == parntQstnOrd){
                                    dpthText = dpthText+","+$(this).text().replace('└질문','');
                                }
                            });

                            if (nextNo.indexOf(',') > 0){
                                var nextNoSplit = nextNo.split(',');
                                $(nextNoSplit).each(function(i){
                                    if (!regex.test(nextNoSplit[i])) {
                                        isValid = false;
                                        alert("하위번호 형식이 맞지않습니다.");
                                        thisObj.focus();
                                        return false;
                                    }
                                    if(dpthText.indexOf(nextNoSplit[i]) == -1){
                                        isValid = false;
                                        alert("하위번호에 매칭되는 하위문항이 없습니다.");
                                        thisObj.focus();
                                        return false;
                                    }
                                });
                            }else{
                                if (!regex.test(nextNo)) {
                                    isValid = false;
                                    alert("하위번호 형식이 맞지않습니다.");
                                    thisObj.focus();
                                    return false;
                                }

                                if(dpthText.indexOf(nextNo) == -1){
                                    isValid = false;
                                    alert("하위번호에 매칭되는 하위문항이 없습니다.");
                                    thisObj.focus();
                                    return false;
                                }
                            }
                        }
                    })

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
                    });
                    return isValid;

                },
                // after : function() {
                //     var isValid = true, editorChk = true;
                //     $formObj.find(".ckeditorRequired").each(function() {
                //         jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
                //         var editorVal = jQuery(this).val().length;
                //         if (editorVal < 1)
                //         {
                //             editorChk = false;
                //             alert("설문내용을 입력해주세요.");
                //             CKEDITOR.instances[jQuery(this).prop("id")].focus();
                //             // 에디터 최상단으로 스크롤 이동
                //             jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);
                //             return false;
                //         }
                //     });
                //
                //     if (!editorChk)
                //     {
                //         isValid = false;
                //     }
                //
                //     return isValid;
                // },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


                        var svMst = {};
                        svMst.detailsKey = $formObj.find("input[name=detailsKey]").val();
                        svMst.titl = $formObj.find("input[name=titl]").val();
                        svMst.typeCd = $formObj.find("input[name=typeCd]:checked").val();
                        svMst.rspnMm = $formObj.find("select[name=rspnMm] option:selected").val();
                        svMst.cntn = $formObj.find(".ckeditorRequired").val();
                        svMst.expsYn = $formObj.find("input[name=expsYn]:checked").val();
                        svMst.svSurveyQstnDtlList = new Array();

                        $(".surveyList").each(function(){
                            var svQstnDtl = {};
                            var filedset = $(this).closest('fieldset');
                            if ($(this).is(":visible")) {

                                svQstnDtl.qstnOrd = filedset.find("input[name=qstn_ord]").val();
                                svQstnDtl.parntQstnOrd = filedset.find("input[name=parnt_qstn_ord]").val();
                                svQstnDtl.dpth = filedset.find("input[name=dpth]").val();
                                svQstnDtl.qstnNm = filedset.find("input[name=qstn_nm]").val();
                                svQstnDtl.ncsYn = filedset.find("input[name=ncs_yn]").is(":checked") ? "Y" : "N";
                                svQstnDtl.nonApplicableYn = filedset.find("input[name=nonApplicableYn]").is(":checked") ? "Y" : "N"; //2024-07-12 추가개발 ppt 3 추가
                                svQstnDtl.srvTypeCd = filedset.find("select[name=srv_type_cd] option:selected").val();
                                svQstnDtl.ctgryCd = filedset.data('survey-type');


                                if (filedset.find("input[name=exmpl_nm]").size() > 0 ) {
                                    svQstnDtl.svSurveyExmplDtlList = new Array();
                                    filedset.find("input[name=exmpl_nm]").each(function(index){
                                        var svExmplDtl = {};
                                        svExmplDtl.qstnOrd = filedset.find("input[name=qstn_ord]").val();
                                        svExmplDtl.exmplNm = $(this).val();
                                        svExmplDtl.nextNo = filedset.find("input[name=next_no]:eq("+index+")").val();
                                        svQstnDtl.svSurveyExmplDtlList.push(svExmplDtl);
                                    });
                                }
                               svMst.svSurveyQstnDtlList.push(svQstnDtl);
                            }
                        });

                        cmmCtrl.jsonAjax(function(data){
                           alert('저장되었습니다.');
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

