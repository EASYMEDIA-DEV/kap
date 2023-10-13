/**
 * @license Copyright (c) 2003-2021, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
 */

/**
 * @fileOverview Preview plugin.
 */

(function(){
	
	"use strict";

	/**
	 * Namespace providing a set of helper functions for working with the editor content preview, exposed by the
	 * [Preview](https://ckeditor.com/cke4/addon/preview) plugin.
	 *
	 * @since 4.14.0
	 * @singleton
	 * @class CKEDITOR.plugins.preview
	 */
	CKEDITOR.plugins.preview = {
		/**
		 * Generates the print preview for the given editor.
		 *
		 * **Note**: This function will open a new browser window with the editor's content HTML.
		 *
		 * @param {CKEDITOR.editor} editor The editor instance.
		 * @returns {CKEDITOR.dom.window} A newly created window that contains the preview HTML.
		 */
		createPreview : function(editor){
			var eventData = { contentValue : editor.getData() };
			
			// (https://dev.ckeditor.com/ticket/9907) Allow data manipulation before preview is displayed.
			// Also don't open the preview window when event cancelled.
			if (editor.fire("contentPreview", eventData) === false) 
			{
				return false;
			}

			// In cases where we force reloading the location or setting concrete URL to open,
			// we need a way to pass content to the opened window. We do it by hack with
			// passing it through window's parent property.
			window._cke_htmlToLoad = eventData.contentValue.replace(/\/common_/gi, jQuery("body").data("httpWebSourceUrl") + "/common_");
			
			var url = "/mngwserc/editor-preview/popup.do";
			
			var type = jQuery("textarea[name='" + editor.name + "']").data("type");
			
			if (type)
			{
				url += "?type=" + type;
			}
			
			var previewWindow = window.open(url, "editorPreview", generateWindowOptions(getWindowDimensions()));
			
			return new CKEDITOR.dom.window(previewWindow);
		}
	};

	function getWindowDimensions()
	{
		var screen = window.screen;

		return {
			width : Math.round(screen.width * 0.8),
			height : Math.round(screen.height * 0.7),
			left : Math.round(screen.width * 0.1)
		};
	}

	function generateWindowOptions(dimensions)
	{
		return ["toolbar=yes", "location=no", "status=yes", "menubar=yes", "scrollbars=yes", "resizable=yes", "width=" + dimensions.width, "height=" + dimensions.height, "left=" + dimensions.left].join(",");
	}
})();

/**
 * Event fired when executing the `preview` command that allows for additional data manipulation.
 * With this event, the raw HTML content of the preview window to be displayed can be altered
 * or modified.
 *
 * **Note** This event **should** also be used to sanitize HTML to mitigate possible XSS attacks. Refer to the
 * {@glink guide/dev_best_practices#validate-preview-content Validate preview content} section of the Best Practices
 * article to learn more.
 *
 * @event contentPreview
 * @member CKEDITOR.editor
 * @param {CKEDITOR.editor} editor This editor instance.
 * @param data
 * @param {String} data.dataValue The data that will go to the preview.
 */