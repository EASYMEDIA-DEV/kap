define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl, ezVald, CodeMirror) {

    "use strict";

    // set controller name
    var exports = {
        controller : "controller/wb/wbf/WBFARoundWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = jQuery("#frmData");

    const structController = (function(){
        const options = {
            'business' : {
                'target':$formObj.find('#business'),
                'text' : '사업유형',
                'btnName' : 'btnPlusBusi'
            },
            'assignment' : {
                'target':$formObj.find('#assignment'),
                'text' : '과제명',
                'btnName' : 'btnPlusAssi'
            }
        };

        const plusArea = (valType) => {
            /* Target Area */
            let option = options[valType];
            /* 기존 구조 Count */
            let structLength = (option['target'].find('[data-order]').length + 1);
            /* text */
            let text = option['text'] + ' ' + structLength;

            let html = '<div class="form-group text-sm" >';
            html += '<fieldset></fieldset>';
            html += '<label class="col-sm-1 control-label">' + text + '<span class="star"> *</span></label>';
            html += '<div class="col-sm-5">';
            html += '<div class="col-sm-6">';
            html += '<input type="text" ' +
                'class="form-control input-sm ' + (valType+'-txt') + '" ' +
                'data-order="' + structLength + '" ' +
                'value="" title="' +
                text + '" maxLength="50" placeholder="' + text + '">';
            html += '</div>'
            html += '<div class="col-sm-6">'
            html += '<button type="button" class="btn btn-info ' + option['btnName'] + '">+</button> '
            html += '<button type="button" class="btn btn-danger btnDelete">-</button>'
            html += '</div>'
            html += '</div>'
            html += '</div>'

            option['target'].append(html);
        }

        const resetOption = (colsId) => {
            /* Target Area */
            let option = options[colsId];
            /* 삭제 후 구조 */
            let struct = option['target'].find('.form-group');
            /* text */
            let text = option['text']

            struct.each((idx, el) =>{
                let changeText = text + ' ' + (idx+1);
                $(el).find('label').html(changeText + '<span class="star"> *</span>');
                $(el).find('input[type=text]').prop('placeholder',changeText).prop('title', changeText).attr('data-order', (idx+1));
            });
        }

        /* 공통 */
        const minusArea = (event) => {
            /* 삭제 전 외부 태그 중 fieldset ID 값 Get */
            var colsId = $(event).closest("fieldset").prop("id");
            $(event).closest('.form-group').remove();
            /* 삭제 후 resetting */
            resetOption(colsId);
        }

        return{
            plus : plusArea,
            minus : minusArea,
        }
    })();

    // set model
    ctrl.model = {
        id : {
            btn_delete : {
                event : {
                    click : function() {
                        //삭제
                        if(confirm("선택한 게시물을 " + msgCtrl.getMsg("confirm.del"))){
                            cmmCtrl.frmAjax(function(data){
                                if(data.respCnt > 0){
                                    alert(msgCtrl.getMsg("success.del.target.none"));
                                    location.replace("./list");
                                }
                            }, "./delete", $formObj, "post", "json")
                        }
                    }
                }
            }
        },
        classname : {
            insertLow : {
                event : {
                    click : function() {
                        //클릭한 로우 위치
                        var row = $(this).prev().val();
                        //전체 지급 차수
                        var giveLength = $(".give-row").length;

                        if(giveLength >= 30){
                            return;
                        }

                        let today = new Date();

                        let year = today.getFullYear(); // 년도
                        let month = today.getMonth() + 1;  // 월
                        let date = today.getDate();  // 날짜

                        var day = year+'-'+month+'-'+date;

                        console.log(giveRow);
                        //차수
                        var giveRow = giveLength+1;
                        var rowItem = '<div class="form-inline text-sm give-row">';
                        rowItem += '<label class="col-sm-1 control-label"></label>';
                        rowItem += '<div class="col-sm-11">';
                        rowItem += '<fieldset></fieldset>';
                        rowItem += '<div class="inline giveText">' +giveRow + '차수</div>';
                        rowItem += ' ';
                        rowItem += '<div class="form-group mr-sm">';
                        rowItem += '<div class="input-group">';
                        rowItem += '<input type="text" class="form-control input-sm datetimepicker_strtDt" name="giveStrtDtList" value=\"'+day+'\" title="시작일" readonly onclick="cmmCtrl.initCalendar(this)"/>';
                        rowItem += '<span class="input-group-btn" style="z-index:0;">';
                        rowItem += '<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">';
                        rowItem += '<em class="ion-calendar"></em>';
                        rowItem += '</button>';
                        rowItem += '</span>';
                        rowItem += '<span class="input-group-addon bg-white b0">~</span>';
                        rowItem += '<input type="text" class="form-control input-sm datetimepicker_endDt" name="giveEndDtList" value=\"'+day+'\" title="종료일" readonly onclick="cmmCtrl.initCalendar(this);"/>';
                        rowItem += '<span class="input-group-btn" style="z-index:0;">';
                        rowItem += '<button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">';
                        rowItem += '<em class="ion-calendar"></em>';
                        rowItem += '</button>';
                        rowItem += '</span>';
                        rowItem += '</div>';
                        rowItem += '</div>';
                        rowItem += '<input type="hidden" class="giveRowHid" name="giveOrd" value=\"'+ giveRow+'\">';
                        rowItem += ' ';
                        rowItem += '<button type="button" class="btn btn-info insertLow">+</button>';
                        rowItem += ' ';
                        rowItem += '<button type="button" class="btn btn-danger deleteLow">-</button>';
                        rowItem += '</div>';
                        rowItem += '</div>';


                        $('#rowInsert').append(rowItem);


                        $.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
                            jQuery(obj).datetimepicker({
                                timepicker : false,
                                format : "Y-m-d",
                                defaultDate : new Date(jQuery("body").data("curtDt")),
                                defaultTime : "00:00",
                                scrollInput : false,
                                scrollMonth : false,
                                scrollTime : false,
                                todayButton: false,
                                onSelectDate : function(selectedDate, selectedObj) {
                                    var strtDt   = selectedDate;
                                    var endDtObj = selectedObj.closest(".input-group").find(".datetimepicker_endDt");
                                    var endDt	 = new Date(endDtObj.val());

                                    if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
                                    {
                                        endDtObj.val(selectedObj.val());
                                    }

                                    endDtObj.datetimepicker("setOptions", { minDate : strtDt, value : endDtObj.val() });

                                    selectedObj.blur();
                                }
                            });
                        });

                        $.each(jQuery(".datetimepicker_endDt"), function(i, obj){
                            jQuery(obj).datetimepicker({
                                timepicker : false,
                                format : "Y-m-d",
                                defaultDate : new Date(jQuery("body").data("curtDt")),
                                defaultTime : "00:00",
                                scrollMonth : false,
                                scrollTime : false,
                                todayButton: false,
                                onSelectDate : function(selectedDate, selectedObj) {
                                    var strtDtObj = selectedObj.closest(".input-group").find(".datetimepicker_strtDt");
                                    var strtDt    = new Date(strtDtObj.val());
                                    var endDt     = selectedDate;

                                    if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
                                    {
                                        strtDtObj.val(selectedObj.val());
                                    }

                                    selectedObj.blur();
                                }
                            });
                        });


                    }
                }
            },
            deleteLow : {
                event : {
                    click : function() {
                        //클릭한 로우 위치
                        var row = $(this).prev().prev().val();

                        $(".give-row").eq(row - 1).remove();

                        for(var i =0 ; i < $(".giveText").length; i++){
                            $(".giveText").eq(i).html(i+1 +"차수");
                            $(".giveRowHid").eq(i).val(i+1);
                        }

                    }
                }
            },
            btnPlusBusi : {
                event : {
                    click : function() {
                        structController.plus('business')
                    }
                }
            },
            btnPlusAssi : {
                event : {
                    click : function() {
                        structController.plus('assignment');
                    }
                }
            },
            btnDelete : {
                event : {
                    click : function(e) {
                        /* - btn 클릭 대상 지우기 */
                        structController.minus(this);
                    }
                }
            },
        },
        immediately : function() {

            var _readOnly = $formObj.data("prcsCd") == "20" ? true : false;

            /* Editor Setting */
            jQuery("textarea[id^='cnts']").each(function(){
                cmmCtrl.setEditor({
                    editor : jQuery(this).attr("id"),
                    height : 400,
                    readOnly : _readOnly
                });
            });

            jQuery(".CodeMirror").find("textarea").addClass("notRequired");

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
            // 유효성 검사
            $formObj.validation({
                after : function() {
                    var isValid = true, editorChk = true;

                    $formObj.find(".ckeditorRequired").each(function() {
                        jQuery(this).val(CKEDITOR.instances[jQuery(this).attr("id")].getData());
                        jQuery(this).val(jQuery(this).val().split("<").join("~!left!~"));
                        jQuery(this).val(jQuery(this).val().split(">").join("~!right!~"));
                        jQuery(this).val(jQuery(this).val().split("\'").join("~!singlecomma!~"));
                        jQuery(this).val(jQuery(this).val().split("\"").join("~!doublecomma!~"));

                        var editorVal = jQuery(this).val().length;

                        if (editorVal < 1)
                        {
                            editorChk = false;

                            alert(msgCtrl.getMsg("fail.co.cog.cnts"));

                            CKEDITOR.instances[jQuery(this).prop("id")].focus();

                            // 에디터 최상단으로 스크롤 이동
                            jQuery(".main-container").scrollTop(jQuery(".main-container").scrollTop() + jQuery(this).parents("fieldset").offset().top - 73);

                            return false;
                        }
                    });

                    if (!editorChk)
                    {
                        isValid = false;
                    }

                    return isValid;
                },
                async : {
                    use : true,
                    func : function (){
                        var actionUrl = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? "./insert" : "./update" );
                        var actionMsg = ( $.trim($formObj.find("input[name=detailsKey]").val()) == "" ? msgCtrl.getMsg("success.ins") : msgCtrl.getMsg("success.upd") );


                        var wbRoundMstDTO = {};
                        wbRoundMstDTO.detailsKey = ctrl.obj.find("#detailsKey").val();
                        wbRoundMstDTO.bsnCd = ctrl.obj.find("#bsnCd").val();
                        wbRoundMstDTO.year = ctrl.obj.find("#year").val();
                        wbRoundMstDTO.episd = ctrl.obj.find("#episd").val();

                        wbRoundMstDTO.accsStrtDtm = ctrl.obj.find("#accsStrtDtm").val();
                        wbRoundMstDTO.accsEndDtm = ctrl.obj.find("#accsEndDtm").val();

                        wbRoundMstDTO.bsnStrtDtm = ctrl.obj.find("#bsnStrtDtm").val();
                        wbRoundMstDTO.bsnEndDtm = ctrl.obj.find("#bsnEndDtm").val();

                        wbRoundMstDTO.expsYn = ctrl.obj.find(":radio[name=expsYn]:checked").val();

                        wbRoundMstDTO.giveList = new Array();

                        $(".giveText").each(function(index, data){
                            var wBOrderMstDto = {};
                            wBOrderMstDto.strtDt = $("input[name=giveStrtDtList]").eq(index).val();
                            wBOrderMstDto.endDt = $("input[name=giveEndDtList]").eq(index).val();
                            wBOrderMstDto.giveOrd = $("input[name=giveOrd]").eq(index).val();

                            wbRoundMstDTO.giveList.push(wBOrderMstDto);
                        });

                        wbRoundMstDTO.bsinList = [];

                        $('.business-txt').each((idx, el)=>{
                            let point = $(el);
                            let WBRoundOptnMstDTO = {};
                            WBRoundOptnMstDTO.optnNm = point.val();
                            WBRoundOptnMstDTO.optnOrd = point.data('order');
                            wbRoundMstDTO.bsinList.push(WBRoundOptnMstDTO);
                        });

                        wbRoundMstDTO.asigtList = [];
                        $('.assignment-txt').each((idx, el)=>{
                            let point = $(el);
                            let WBRoundOptnMstDTO = {};
                            WBRoundOptnMstDTO.optnNm = point.val();
                            WBRoundOptnMstDTO.optnOrd = point.data('order');
                            wbRoundMstDTO.asigtList.push(WBRoundOptnMstDTO);
                        });

                        cmmCtrl.jsonAjax(function(data){
                            alert(actionMsg);
                            location.href = "./list";
                        }, actionUrl, wbRoundMstDTO, "text")

                    }
                },
                msg : {
                    empty : {
                        text : " 입력해주세요."
                    }
                }
            });
        }
    };
    ctrl.exec();

    return ctrl;
});

