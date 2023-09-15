/**
 * @license Copyright (c) 2003-2021, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.language = "ko";
	
	config.toolbar = [
        ["Source"],
        ["Font", "FontSize"],
        ["BGColor", "TextColor"],
        ["Bold", "Italic", "Strike", "Superscript", "Subscript", "Underline", "RemoveFormat"],
        ["Image"],
        ["JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyBlock"],
        ["Undo", "Redo"]
    ];

	config.allowedContent = true;
    config.basicEntities = false;
	config.enterMode = CKEDITOR.ENTER_BR;
	config.entities_additional = "";
    config.extraAllowedContent = "section article";
    config.extraPlugins = "codemirror";
    config.filebrowserImageUploadUrl = "/mngwserc/editor-image/upload.ajax";
    config.fillEmptyBlocks = false;
    config.protectedSource.push(/<a[^>]*>[\s]*<\/a>/g);
	config.protectedSource.push(/<span[^>]*>[\s]*<\/span>/g);
	config.shiftEnterMode = CKEDITOR.ENTER_P;
	config.width = "100%";
};

CKEDITOR.disableAutoInline = true;
CKEDITOR.dtd.a.div = 1;
CKEDITOR.dtd.a.p = 1;
CKEDITOR.dtd.dl.div = 1;
CKEDITOR.dtd.div.dt = 1;
CKEDITOR.dtd.div.dd = 1;
CKEDITOR.dtd.button.div = 1;
CKEDITOR.dtd.a.figure = 1;
CKEDITOR.dtd.$removeEmpty.i = 0;

CKEDITOR.on("dialogDefinition", function(ev){
    // Take the dialog name and its definition from the event data.
    var dialogName = ev.data.name;
    var dialogDefinition = ev.data.definition;
    var dialog = dialogDefinition.dialog;
    var editor = ev.editor;

    if (dialogName == "image")
    {
    	dialogDefinition.onOk = function (e){
    		
            var imageSrcUrl = e.sender.originalElement.$.src;   // image URL                            
            imageSrcUrl = imageSrcUrl.replace(window.location.origin, "");
            var width = e.sender.originalElement.$.width;       // image 너비
            var height = e.sender.originalElement.$.height;     // image 높이

            var imageAlt = e.sender.getContentElement('info', 'txtAlt').getValue();     // image 대체 문자열
            var border = e.sender.getContentElement('info', 'txtBorder').getValue();    // image 테두리
            var hSpace = e.sender.getContentElement('info', 'txtHSpace').getValue();    // image 가로여백
            var vSpace = e.sender.getContentElement('info', 'txtVSpace').getValue();    // image 세로여백
            
            var txtWidth = e.sender.getContentElement('info', 'txtWidth').getValue();   // image text너비
            var txtHeight = e.sender.getContentElement('info', 'txtHeight').getValue(); // image text높이
            
            var txtFloat = $(".cke_dialog_ui_input_select option:selected").val();
            
            //위의 속성값을 추가한  image 태그
            var imgHtml = CKEDITOR.dom.element.createFromHtml('<img src="' + imageSrcUrl +'" width="' + txtWidth + '" height="' + txtHeight + '" alt="'
                + imageAlt+ '" style="border:' + border + 'px solid; margin:' + hSpace + 'px ' + vSpace + 'px; float:' + txtFloat+'" />');

            editor.insertElement(imgHtml);
        };
    }
});