var coEditorCtrl = (function(){
    "use strict";
    
    //에디터 담을 배열
    var dextEditorArray = new Array();
    var dextEditorSeparatedArray = new Array();
    var fn_geteditors = function()
    {
        return dextEditorArray;
    }
    jQuery(document).ready(function(){
        //덱스트 공통 적용

        if($(".dextEditor").size() > 0){
            DEXT5.config.InitXml = "dext_editor.xml";
            $(".dextEditor").each(function(q){

                var value = $( this ).html();
                $( this ).html("");
                $("textarea[name="+$( this ).attr("id")+"]").text(value);
                DEXT5.config.WrapPtagSkipTag = 'a';
                //height 값 지정
                DEXT5.config.EditorHolder = $( this ).attr("id");

                dextEditorArray.push(new Dext5editor(DEXT5.config.EditorHolder));

            })
        }
        if($(".dextEditor_Exception").size() > 0) {
            // 에디터 개별 (예외)적용
            $(".dextEditor_Exception").each(function (q) {
                //height 값 지정
                DEXT5.config.EditorHolder = $(this).attr("id");
                dextEditorSeparatedArray.push(new Dext5editor(DEXT5.config.EditorHolder));
            })
        }
    });
    
    //최초적용이 아닌 이벤트로 인해 실행했을경우
    var fn_event_geteditors = function(){
        /**개별적으로 호출 시..**/
        var args = Array.prototype.slice.call(arguments);
        
        if(args.length > 0){
             DEXT5.config.InitXml = "dext_editor.xml";
                args.forEach(function(q){
                    DEXT5.config.EditorHolder = q;
                    dextEditorSeparatedArray.push(new Dext5editor(DEXT5.config.EditorHolder));
                })  
        }else{
        
            //덱스트 공통 적용
            DEXT5.config.InitXml = "dext_editor.xml";
            $(".dextEditor").each(function(q){
                DEXT5.config.EditorHolder = $( this ).attr("id");
                dextEditorArray.push(new Dext5editor(DEXT5.config.EditorHolder));
            })  
        }
    }
    
    /* 에디터에 html태그부터 넣는다 */
    var fn_set_html_value_ex = function(html, id)
    {
        if(DEXT5 != null)
        {
            DEXT5.setHtmlValueEx(html, id);
        }
    }
    
    /* 에디터 디자인 영역에서 커서가 있는 위치에 text 삽입 */
    var fn_set_insert_text = function(text, id)
    {
        if(DEXT5 != null)
        {
            DEXT5.setInsertText(text, id);
        }
    }

    return {
          getEditors : fn_geteditors
        , eventGetEditors : fn_event_geteditors
        , setHtmlValueEx : fn_set_html_value_ex //HTML 데이터를 삽입(헤더 푸터있는 html 원본)
        , setInsertText : fn_set_insert_text //에디터 디자인 영역에서 커서가 있는 위치에 text 삽입
    }
}());

function dext_editor_loaded_event(editor) {
    var textValue = $("textarea[name=" + editor.ID + "]").val();
    var userDomain = $("body").data("userDomain");
    if (userDomain.indexOf("kolon.com") > -1) {
        DEXT5.loadHtmlValueExFromURL("/common/dext5editor/init_prd.html", editor.ID);
    } else {
        DEXT5.loadHtmlValueExFromURL("/common/dext5editor/init.html", editor.ID);
    }

    DEXT5.setBodyValue(textValue, editor.ID);
    
    if($("#" + editor.ID).css("height") != undefined)
    {
        var height = parseInt($("#" + editor.ID).css("height"));
        DEXT5.setSize('100%', height, DEXT5.config.EditorHolder);
    }
}


function dext_editor_loaded_name_event(obj){
    var num = 1;
    $(obj).each(function(){
        DEXT5.setBodyValue($(this).val(), "cntn"+num);
        num++;
    })
}

// 붙여넣기 전 이벤트
/*function dext_editor_beforepaste_event(pasteSource, editor){
    debugger
    if(typeof pasteSource == "string"){
        /!*if(!pasteSource.startsWith("<")){
            pasteSource = "<p></p>" + pasteSource;
        }*!/
    //    DEXT5.setInsertHTML(pasteSource, 'cntn');
    }
   // return pasteSource;
}

// 붙여넣기 이벤트
function dext_editor_onpaste_event(pasteSource,editor){
    debugger
}*/

//데이터 추출
function getDext5WebEditorContentsValue(name, body)
{
    if(DEXT5 != null)
    {
        var editorArray = coEditorCtrl.getEditors();
        if(editorArray != null && editorArray.length > 0)
        {
            var d5Dom = null;
            var value = "";
            var replaceStr = "";
            var isEmptyChk = true;
            for(var q = 0 ; q  < editorArray.length ; q++)
            {
                if(body != undefined)
                {
                    if(body == "html")
                    {
                        value = DEXT5.getHtmlValueEx(editorArray[q].ID)
                    }
                    else
                    {
                        value = DEXT5.getBodyValue(editorArray[q].ID)
                    }
                }
                else
                {
                    value = DEXT5.getBodyValue(editorArray[q].ID)
                }                                                
                replaceStr = DEXT5.getBodyValue(editorArray[q].ID)
                /*replaceStr = replaceStr.replace(/(<([^img|iframe|video>]+)>)/ig,"");
                replaceStr = replaceStr.replace(/&nbsp;/gi,"");*/
                replaceStr = replaceStr.trim();
                if( replaceStr == "" || replaceStr == null || replaceStr == undefined || ( replaceStr != null && typeof replaceStr == "object" && !Object.keys(replaceStr).length ) )
                {
                    isEmptyChk = false;
                }
                else
                {
                    isEmptyChk = true;
                }
                if(replaceStr.length > 0 && isEmptyChk)
                {

                }
                else
                {
                    value = "";
                }
               /* value = value.replace(/&amp;/gi,"&amp;amp;");
                value = value.replace(/&nbsp;/gi,"&amp;nbsp;");
                value = value.replace(/&lt;/gi,"&amp;lt;");
                value = value.replace(/&gt;/gi,"&amp;gt;");*/
                if(DEXT5.isEmpty(name)){
                    value = "";
                }
                if(typeof name!="undefined" && name !="")
                {
                    $("textarea[name="+name+"]").eq(q).val( value );
                }
                else
                {
                    $("textarea[name="+editorArray[q].ID+"]").val( value );
                }
            }
        }
    }
}

