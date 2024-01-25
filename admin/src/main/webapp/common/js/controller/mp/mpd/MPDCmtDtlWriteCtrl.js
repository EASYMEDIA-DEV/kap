define(["ezCtrl", "ezVald", "CodeMirror", "CodeMirror.modeJs"], function(ezCtrl) {

"use strict";

// set controller name
var exports = {
    controller : "controller/mp/mpd/MPDCmtDtlWriteCtrl"
    };

    // get controller object
    var ctrl = new ezCtrl.controller(exports.controller);

    // form Object
    var $formObj = ctrl.obj.find("form").eq(0);

    let dupEmailChk = false;
    let cmssrCd ;
    let kenChk  = false;

    var validation = {
        defaults : {
            notRequiredClass : "notRequired",
            ExceptionRequiredClass : "exceRequired",
            validateType : {
                passChk : {
                    className : "passChk",
                    subClassName : "passChkSub",
                    regExr : "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$",
                    equalClass : "passEqual"
                },
            },
            msg : {
                type : "alert",
                empty : {
                    text : "을(를) 입력해주세요.",
                    password : "을(를) 입력해주세요.",
                    passwordChk : "을(를) 입력해주세요.",
                    radio : "을(를) 선택해주세요.",
                    checkbox : "체크박스 값을 한개 이상 선택해주세요.",
                    select : "을(를) 선택해주세요.",
                    textarea : "을(를) 입력해주세요.",
                    names : {
                    }
                },

            },
            placeholder : true,
            comma : {
                use : true,
                className : "comma"
            },
            async : {
                use : false,
                func : ""
            },
            tooltip : {
                use : false
            },
            loadingbar : {
                use : false,
                autoClose : false
            },
            exceptArea : []
        },
        action : "",
        confirm : "",
        method : {
            Indicator : function(ptarget, target){
                var indicator
                    , documentHeight = $(document).height();

                if($(target).size() > 0){
                    indicator = $(target).remove();

                    indicator.css('position', 'absolute').css('top', '0px').css('left', '0px').css('background-color','#000').css('z-index',1001).css('opacity',0.7)
                        .width('100%').height(documentHeight);

                    indicator.find('img').css('top', ($(window).height() - indicator.find('img').outerHeight()) / 2 + $(window).scrollTop() / 2);
                    indicator.find('img').css('left', ($(window).width()- indicator.find('img').outerWidth()) / 2 + $(window).scrollLeft() / 2);

                    ptarget.after(indicator.show());
                }
            },

        },
        async : {
            use : false,
            func : ""
        },
        developmentMode : ""
    };

    $.fn.validation2 = function(options){
        var $this = this
            , notRequired
            , isOk = true
            , tagType
            , tagTypeValue
            , tagNameValue
            , tagName
            , tagTitle
            , tagID
            , message = ""
            , regResult
            , submitBtnClass
            , action = $this.attr('action');


        var rtnFunc;
        //var version = $.browser.version;

        var msg = {
            empty : {
                text : "",
                password : "",
                passwordChk : "",
                select : "",
                radio : "",
                checkbox : "",
                textarea : ""
            },
            // idChk : "",
            passChk : "",
            passEqualChk : "",

        };

        var func = {
            customFunc : "",
            beforeFunc : "",
            afterFunc : ""
        };

        var settings = jQuery.extend(true, {}, validation.defaults, options);

        notRequired = settings.notRequiredClass;
        submitBtnClass = settings.submitBtnClass;

        func.customFunc = settings.customfunc;
        func.beforeFunc = settings.before;
        func.afterFunc = settings.after;

        msg.empty.text = settings.msg.empty.text;
        msg.empty.password = settings.msg.empty.password;
        msg.empty.passwordChk = settings.msg.empty.passwordChk;
        msg.empty.checkbox = settings.msg.empty.checkbox;
        msg.empty.radio = settings.msg.empty.radio;
        msg.empty.textarea = settings.msg.empty.textarea;
        msg.empty.select = settings.msg.empty.select;

        msg.passChk = settings.msg.passChk;
        msg.passEqualChk = settings.msg.passEqualChk;


        // 경고 메시지를 설정한다.
        var SetMsg = function(obj, className){
            tagType = obj[0].nodeName;
            tagTypeValue = obj.attr('type');
            tagNameValue = obj.attr('name');

            switch (tagType)
            {
                case "INPUT" :
                    if (tagTypeValue == "text" || tagTypeValue == "hidden" || tagTypeValue == "file") message = msg.empty.text;

                    if (tagTypeValue == "password"){
                        //message = 'The password type must be a class.';
                        message = msg.empty.password;
                    }
                    if (tagTypeValue == "radio") message = msg.empty.radio;
                    if (tagTypeValue == "checkbox") message = msg.empty.checkbox;

                    break;
                case "SELECT" : message = msg.empty.select; break;
                case "TEXTAREA" : message = msg.empty.textarea; break;
            }


            if (settings.msg.empty.names.hasOwnProperty(tagNameValue)){
                message = settings.msg.empty.names[tagNameValue];
            }
            else
            {
                if(tagTitle == ""){
                    message = "No title value. [name=" + tagNameValue + "]";
                }else if (message.indexOf(tagTitle) == -1){
                    message = tagTitle + message;

                    if(message == tagTitle) message = "The password type must be a class.\n[passChk or passChkSub or passEqual]";
                }
            }
        };

        // 설정된 메시지를 발생시키고 해당 객체로 포커스를 이동시킨다.
        var GenerateMsg = function(obj, msg){
            if(!obj.hasClass(settings.ExceptionRequiredClass) && !settings.tooltip.use){
                alert(msg);
                if(obj[0].nodeName == 'INPUT' && obj.attr('type') == "text"){
                    obj.val('');
                }

                obj.addClass('error');
                setTimeout(function(){
                    obj.removeClass('error');
                }, 2000);
            }

            if(settings.tooltip.use){
                // jquery ui widget
                obj.tooltip({
                    disabled: true
                }).on("focusin", function () {
                    if(obj.val() == "" || isOk){
                        obj.tooltip("enable").tooltip("open").tooltip({content : msg});
                    }
                }).on("focusout", function () {
                    obj.tooltip("close").tooltip("disable");
                }).on("keyup", function(e){
                    if($.trim($(this).val()) != ""){
                        obj.tooltip("close").tooltip("disable");
                    }
                });
            }

            if(typeof obj.data().focusTargetName != "undefined" && obj.data().focusTargetName != ""){
                $('[name=' + obj.data().focusTargetName + ']').focus();
            }else{
                obj.focus();
            }

            isOk = false;
        };

        // 필수 입력이 아닌 경우 체크
        var NotRequiredCheck = function(obj, single, name){
            var rtnFlag = true;

            if (single){
                rtnFlag = !obj.hasClass(notRequired);

                if (!rtnFlag && $.trim(obj.val()) != "") rtnFlag = true;
            }
            else
            {
                $this.find('input[name='+name+']').each(function(){
                    if ($(this).hasClass(notRequired)) rtnFlag = false;
                });
            }

            return rtnFlag;
        };

        // 사용자 정의 함수 실행
        var UserDefineFunc = function(func, obj, tagid, okval, msg){
            if (typeof func == "function"){
                //isOk = func(obj, tagid);
                return func(obj, tagid, okval, msg);
            }
        };


        // submit btn event mapping
        if(typeof submitBtnClass != "undefined"){
            $('.'+submitBtnClass).click(function(e){
                e.preventDefault();
                $this.submit();
            });
        }

        // 값 또는 태그의 상태에 대한 확인 모음
        var validationType = {
            Empty : function(obj, msg){
                if(!obj.hasClass(settings.validateType.passChk.equalClass)){
                    if(obj.attr("id") == 'cmssrMjrCarerCntn') {
                        $(".dropzone").not(".notRequired").each(function(i){
                            if ($(this).children(".dz-preview").length == 0)
                            {
                                alert(msgCtrl.getMsg("fail.mp.mpd.al_014"));
                                isOk = false;
                            } else {
                                if (NotRequiredCheck(obj, true)) {
                                    if ($.trim(obj.val()).length == 0) GenerateMsg(obj, msg);

                                }
                            }
                        });
                    } else {
                        if (NotRequiredCheck(obj, true)) {
                            if ($.trim(obj.val()).length == 0) GenerateMsg(obj, msg);

                        }
                    }

                } else {
                }
            },
            Radio : function(obj, msg){
                var radio_ok = false;
                tagName = obj.attr('name');

                if (NotRequiredCheck(obj, false, tagName)){
                    if ($('input[name='+tagName+']:checked').length > 0) radio_ok = true;
                    if (!radio_ok) GenerateMsg(obj, msg);
                }
            },
            CheckBox : function(obj, msg){
                var check_ok = false;
                tagName = obj.attr('name');

                if (NotRequiredCheck(obj, false, tagName)){
                    if ($('input[name='+tagName+']:checked').length > 0) check_ok = true;
                    if (!check_ok) GenerateMsg(obj, msg);
                }
            },

        };


        $this.submit(function(e){
            window.onbeforeunload = null;

            isOk = true;
            //isOk = false;

            if(typeof func.beforeFunc === "function"){
                try{
                    func.beforeFunc();
                }catch(e){
                    alert(e);
                }
            }

            $this.find('input:text, input:hidden, input:password, input:radio, input:checkbox, input:file, select, textarea').each(function(){

                tagType = $(this)[0].nodeName;
                tagTypeValue = "";
                tagTitle = $(this).attr('title');
                tagID = $(this).attr('id');

                if (typeof tagTitle == "undefined") tagTitle = "";

                SetMsg($(this));

                switch (tagType)
                {
                    case "INPUT" :

                        tagTypeValue = $(this).attr('type');

                        if (tagTypeValue == "text" || tagTypeValue == "hidden" || tagTypeValue == "password" || tagTypeValue == "file")
                        {
                            if(isOk){
                                if (tagTypeValue == "text" && $(this).val() == $(this).attr('placeholder')){
                                    $(this).val('');
                                    validationType.Empty($(this), message);
                                }
                                else{
                                    validationType.Empty($(this), message);
                                }

                                // if (isOk && $(this).hasClass(settings.validateType.idChk.className)) validationType.IdChk($(this), regExrs.idExr, msg.idChk );
                                if (tagTypeValue == "password")
                                {
                                    if (isOk && $(this).hasClass(settings.validateType.passChk.className)) validationType.PassChk($(this), regExrs.passExr, msg.passChk );
                                    if (isOk && $(this).hasClass(settings.validateType.passChk.subClassName)) validationType.PassChk($(this), regExrs.passExr, msg.passChk );
                                    if (isOk && $(this).hasClass(settings.validateType.passChk.equalClass)) validationType.PassEqualChk($(this), settings.validateType.passChk.className , msg.passEqualChk );
                                }
                            }
                        }
                        else if (tagTypeValue == "radio")
                        {
                            validationType.Radio($(this), message);
                        }
                        else if (tagTypeValue == "checkbox")
                        {
                            validationType.CheckBox($(this), message);
                        }

                        break;
                    case "SELECT" :
                        tagTypeValue = "select";
                        validationType.Empty($(this), message);
                        break;
                    case "TEXTAREA" :
                        tagTypeValue = "textarea";
                        validationType.Empty($(this), message);
                        break;
                }

                rtnFunc = UserDefineFunc(func.customFunc, $(this), tagID, isOk, message);
                if (typeof rtnFunc != "undefined")
                {
                    isOk = rtnFunc;
                    if (!isOk) return false;
                }

                if(!$(this).hasClass(settings.ExceptionRequiredClass) && !settings.tooltip.use){
                    if (!isOk) return false;
                }


            });

            if (!isOk) e.preventDefault();

            if (isOk)
            {
                if(typeof func.afterFunc === "function"){
                    try{
                        isOk = func.afterFunc();
                    }catch(e){
                        isOk = false;
                        alert(e);
                    }
                }

                if(isOk){
                    if(isOk){
                        // if(Feel.Validation.async.use) settings.async = Feel.Validation.async;

                        if (settings.async.use)
                        {
                            e.preventDefault();

                            if (typeof settings.async.func === "function")
                            {
                                settings.async.func($this);
                            }
                        }

                        if (settings.loadingbar.use){
                            // Feel.Validation.method.Indicator($('.layout-container'), $('#indi'));
                            $("body").focus();

                            if(settings.loadingbar.autoClose){
                                setTimeout(function(){
                                    $('#indi').fadeOut(300);
                                }, 1000);
                            }
                        }
                    }
                }else{
                    if(typeof isOk == "undefined"){
                        alert("Return value is not defined.[after function(return true or false)]");
                    }
                    e.preventDefault();
                }
            }
        });
    };

    function init() {
        var Month = new Date().getFullYear()+"-" +  ("0" + (new Date().getMonth() + 1)).slice(-2)
        var path = window.location.pathname;
        if(path != '/mngwserc/mp/mpd/list') {
            kenChk = true;
            $(".monthInit").val(Month);
            commonCodeAjax();
            tabOne();
            tabTwo();
            tabThree();
            cmssrCdInit(true);
            datepickerLoad();
        }
        else {
            kenChk = false;
            $(".monthInit").val( new Date().getFullYear()+"-" +  ("0" + (new Date().getMonth() + 1)).slice(-2)+"-"+("0" + (new Date().getDate())).slice(-2));

        }
            tabFour(kenChk);



    }
    //업종/분야 코드 호출
    function commonCodeAjax() {
        cmmCtrl.frmAjax(function(respObj) {
            cmssrCd = respObj.cdDtlList;
        }, "/mngwserc/mp/mpd/cmssrCbsnCd", $formObj, "POST", "json",'',false);
    }

    function datepickerLoad() {
        // 게시기간(일자) Start -----
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
                    console.log(selectedDate);
                    var strtDt   = selectedDate;
                    var endDtObj = selectedObj.closest("fieldset").find(".datetimepicker_endDt");
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

    }

    //위원 상세 조회
    var tabOne = function () {
        cmmCtrl.frmAjax(function(respObj) {
            ctrl.obj.find("#tab1").html(respObj);
        }, "/mngwserc/mp/mpd/select-tab-one", $formObj, "POST", "html",'',false);
    }

    /**
     * 컨설팅 리스트 조회
     */
    var tabTwo = function () {
        cmmCtrl.listFrmAjax(function(respObj) {
            //CALLBACK 처리
            ctrl.obj.find("#listContainer").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainer", "pagingContainer");
        }, "/mngwserc/mp/mpd/select-tab-two", $formObj, "POST", "html",'',false);
    }

    var tabThree = function() {
        //상생 사업
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainerSan").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerSanTotCnt").text(totCnt);
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerSan", "pagingContainerSan");
        }, "/mngwserc/mp/mpd/select-tab-three", $formObj, "POST", "html",'',false);

    }

    var tabFour = function(kenChk) {

        var year = new Date().getFullYear().toString() ;
        $(".mtz-monthpicker.mtz-monthpicker-year").val(year);

        var input_month = ("0" + (new Date().getMonth() + 1));
        var month = 0 ;
        if(input_month < 10) {
            month = input_month.substring(1,2);
        } else {
            month = input_month;
        }
        $('.ui-state-default.mtz-monthpicker.mtz-monthpicker-month.ui-state-active').attr('class','ui-state-default mtz-monthpicker mtz-monthpicker-month');
        if(month <= 3) {
            $("tbody.mtz-monthpicker").find('tr:first').find('td:eq('+(month-1)+')').addClass('ui-state-active');
        } else {
            $('.mtz-monthpicker').find('td:eq('+(month-1)+')').addClass('ui-state-active');
        }

        //근태 사업
        cmmCtrl.listFrmAjax(function(respObj) {
            $formObj.find("table").eq(0).find(".checkboxAll").prop("checked", false);
            //CALLBACK 처리
            ctrl.obj.find("#listContainerKen").html(respObj);
            //전체 갯수
            var totCnt = $(respObj).eq(0).data("totalCount");
            //총 건수
            ctrl.obj.find("#listContainerKenTotCnt").text(totCnt);
            if(kenChk) {
                $('.chkdd').remove();
            }
            //페이징 처리
            cmmCtrl.listPaging(totCnt, $formObj, "listContainerKen", "pagingContainerKen");
        }, "/mngwserc/mp/mpd/select-tab-four", $formObj, "POST", "html",'',false);

    }
    var tabReload = function (type,page) {

        if(page != undefined){
            $formObj.find("#pageIndex").val(page);
        }
        if(type == 'cun') {
            tabTwo();
        }else if(type == 'san' ) {
            tabThree();
        } else if(type == 'ken' ) {
            tabFour(kenChk);
        }
    }
    function cmssrCdInit(values) {
        $(".cmssrCdDiv").show();
        $("#cmssrCbsnCd").empty();
        $("#cmssrCbsnCd").append("<option value=''>선택</option>");
        cmssrCd.MEM_CD.forEach(item => {
            if (item.cd.includes($("#cmssrTypeCd").val()) && item.dpth == 4 && $("#cmssrTypeCd").val() != '') {
                var selectValue = $("#cmssrCbsnCdSe").val() == item.cd;
                $("#cmssrCbsnCd").removeClass("notRequired");
                if(!values) {
                    selectValue = false ;
                }
                if(selectValue) {
                    $("#cmssrCbsnCd").append("<option selected value="+item.cd+">"+item.cdNm+"</option>");
                } else {
                    $("#cmssrCbsnCd").append("<option value="+item.cd+">"+item.cdNm+"</option>");

                }
            } else if($("#cmssrTypeCd").val() == 'MEM_CD03004'){
                $(".cmssrCdDiv").hide();
                $("#cmssrCbsnCd").addClass("notRequired");
            }
        });
    }

    // set model
    ctrl.model = {
        id : {
        email : {
            event : {
                input : function(e) {
                    $(this).val(e.target.value.toLowerCase());
                    dupEmailChk = false;
                }
            }
        },
        btnPwdInit : {
            event : {
                click : function() {
                    if (confirm(msgCtrl.getMsg("confirm.co.coa.pwdInit")))
                    {
                        $formObj.find("input[name='id']").val(jQuery(this).data("id"));
                        cmmCtrl.frmAjax(function (data){
                            alert(msgCtrl.getMsg("success.co.coa.pwdInit"));
                        }, "/mngwserc/mp/mpa/pwd-init", $formObj, "post", "json", true);
                    }
                }
            }
        },
        //엑셀다운로드
        btnExcelDown : {
            event : {
                click: function () {
                    var frmDataObj    = $formObj.closest("form");
                    frmDataObj.find("input[name='rsn']").remove();
                    location.href = "./excel-ken-down?excelType=D&" + frmDataObj.serialize();
                }
            }
        },

        //휴대폰 자동 하이픈
        hpNo : {
            event : {
                input : function (event) {
                    let phoneNumber = event.target.value.replace(/[^0-9]/g, '');
                    const phoneLen = phoneNumber.length;

                    if (phoneLen > 3 && phoneLen <= 7) {
                        phoneNumber = phoneNumber.replace(/(\d{3})(\d+)/, '$1-$2');
                    } else if (phoneLen > 7) {
                        phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d+)/, '$1-$2-$3');
                    }
                    event.target.value = phoneNumber;
                }
            }
        },
            //이메일 중복 체크
        dupEmail : {
            event : {
                click : function() {
                    if($("#email").val().trim().length > 0) {
                        if(cmmCtrl.getEmailChk($("#email").val())) {
                            cmmCtrl.frmAjax(function(respObj) {
                                if(respObj.dupChk == 'Y') {
                                    dupEmailChk = true;
                                    alert(msgCtrl.getMsg("fail.mp.mpa.al_008"));
                                } else {
                                    dupEmailChk = false;
                                    alert(msgCtrl.getMsg("fail.mp.mpa.al_007"));
                                }
                            }, "/mngwserc/mp/mpa/dup-email", $formObj, "POST", "json",'',false);
                        } else {
                            alert(msgCtrl.getMsg("fail.mp.mpa.al_017"));
                            return false;
                        }

                    } else {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_031"));
                        return false;
                    }
              }
            }
        },

        //업종/분야 select 박스
        cmssrTypeCd : {
                event : {
                    change : function() {
                        cmssrCdInit(false);
                    }
                }
            },



    },
    classname : {
        tabClick : {
            event : {
                click : function (e){
                    if(e.target.getAttribute('href').substr(1)!='dtl') {
                        $(".dtl-tab").hide();
                    } else {
                        $(".dtl-tab").show();
                    }
                }
            }
        },
        monthInit : {
            event : {
                change : function () {
                    var activeTab = $('#myTabs li.active a').attr('href').substring(1);
                        tabReload(activeTab,1);
                }
            }
        },
        classType : {
            event : {
                click : function() {

                    $(".cdListContainer").css("display","none");
                    $(".cdListContainer").attr("disabled", true);
                    $(".cdListContainer").find("input:checkbox").prop("checked", false);

                    $(".classType input:checked").each(function(){
                    console.log($(this).val());

                    var checkVal = $(this).val();

                    console.log(checkVal);

                    $("."+checkVal).css("display","block");

                    $("."+checkVal).find("input:checkbox").attr("disabled", false);


                    });

                    if($(".classType input:checked").length == 0){
                    $(".cdListContainer").css("display","none");
                    $(".cdListContainer").attr("disabled", true);
                    $(".cdListContainer").find("input:checkbox").prop("checked", false);
                    }

                }
            }
    },
        pageSet : {
            event : {
                click : function() {
                    var activeTab = $('#myTabs li.active a').attr('href').substring(1);
                    //페이징 이동
                    if( $(this).attr("value") != "null" ){
                        $formObj.find("input[name=pageIndex]").val($(this).attr("value"));
                        tabReload(activeTab);
                    }
                }
            }
        },
        //페이징 목록 갯수

        listRowSizeContainer : {
            event : {
                change : function(){
                    var activeTab = $('#myTabs li.active a').attr('href').substring(1);
                    //리스트 갯수 변경
                    $formObj.find("input[name=listRowSize]").val($(this).val());
                    tabReload(activeTab,1);
                }
            }
        }
    },
    immediately : function() {

        //리스트 조회
        //폼 데이터 처리
        // cmmCtrl.setFormData($formObj);
        $formObj.find("input[name=listRowSize]").val("10");
        $(".listRowSizeContainer").val("10");
        init();


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
        $formObj.validation2({

            after : function(){
                let chk = true;

                const regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-_=+]).{8,16}$/;
                if(jQuery("#email").val() != jQuery("#oldEmail").val()) {
                    if(!dupEmailChk) {
                        alert(msgCtrl.getMsg("fail.mp.mpd.al_030"));
                        chk = false;
                        return chk;
                    }
                }
                if($("#hpNo").val().length !=13) {
                    alert(msgCtrl.getMsg("fail.mp.mpd.al_033"));
                    chk = false;
                    return chk;
                }

              return chk;
            },
            async : {
                use : true,
                func : function (){
                    var actionUrl = "/mngwserc/mp/mpa/update";
                    if(confirm(msgCtrl.getMsg("confirm.sve"))) {
                        cmmCtrl.fileFrmAjax(function (data) {
                            //콜백함수. 페이지 이동
                            if (data.respCnt > 0) {
                                alert(msgCtrl.getMsg("fail.mp.mpd.al_004"));
                                location.replace("./list");
                            }
                        }, actionUrl, $formObj, "json");
                    }
                },
                error: function(e){
                    console.log(e);
                }
            }
        });


    }
    };

    // execute model
    ctrl.exec();

    return ctrl;
    });