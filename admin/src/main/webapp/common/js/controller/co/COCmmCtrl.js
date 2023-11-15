var cmmCtrl = (function(){

	"use strict";

	var fn_replace_null = function(val, chgStr)
	{
		if (val == undefined || val == null || val == "")
		{
			return chgStr;
		}
		else
		{
			return val;
		}
	};

	/* Ajax Normal */
	var fn_ajax = function(callbackAjax, url, dataType, loading, sync)
	{
		if (typeof dataType == "undefined") {
			dataType = "json";
		}

		if (typeof sync == "undefined") {
			sync = true;
		}

		jQuery.ajax({
			url : url,
			type : "post",
			timeout: 30000,
			dataType : dataType,
			async: sync,
			cache : false,
			beforeSend : function(){
				if (loading) {
					jQuery(".loadingbar").stop().fadeIn(200);
				}
			},
			success : function(data, status, xhr){
				if(data.errors != null){
					fn_ajax_error(data, status, xhr);
				}
				else
				{
					formObj.data("submitFlag", "N");
					if (callbackAjax) {
						callbackAjax(data);
					}
				}
			},
			error : function(data, status, xhr){
				fn_ajax_error(data, status, xhr);
			},
			complete : function(){
				if (loading) {
					jQuery(".loadingbar").stop().fadeOut(200);
				}
			}
		});
	};

	/* Ajax Form Data */
	var fn_ajax_data = function(callbackAjax, url, formObj, formActionType, dataRespType, loading, sync)
	{
		//폼 전송 타입
		if (typeof formActionType == "undefined") {
			formActionType = "POST";
		}
		//비동기 여부
		if (typeof sync == "undefined") {
			sync = true;
		}
		//application header json
		if (typeof dataRespType == "undefined") {
			dataRespType = "json";
		}
		//데디터 배열 처리
		if (formObj.data("submitFlag") != "Y") {
			jQuery.ajax({
				url : url,
				type : formActionType,
				timeout: 30000,
				data : formObj.serializeArray(),
				dataType : dataRespType,
				async: sync,
				cache : false,
				beforeSend : function(){
					formObj.data("submitFlag", "Y");
					if (loading) {
						jQuery(".loadingbar").stop().fadeIn(200);
					}
				},
				success : function(data, status, xhr){
					//비동기일때 errors 처리
					if(data.errors != null){
						if(typeof data.statusCode == "undefined"){
							formObj.data("submitFlag", "N");
						}
						fn_ajax_error(data, status, xhr);
					}
					else
					{
						formObj.data("submitFlag", "N");
						if (callbackAjax) {
							callbackAjax(data);
						}
					}
				},
				error : function(data, status, xhr){
					formObj.data("submitFlag", "N");
					fn_ajax_error(data, status, xhr);
				},
				complete : function(){
					if (loading) {
						jQuery(".loadingbar").stop().fadeOut(200);
					}
				}
			});
		}
	};

	//리스트 조회 data 가져오기
	var fn_list_frm_set_data = function(formObj){
		var array = new Array();
		formObj.find("input:text, input:checkbox, select").each(function(){
			if($(this).data("name") != undefined)
			{
				//삭제
				formObj.find("input[type=hidden][name="+$(this).data("name")+"]").remove();
			}
		});
		formObj.find("input:text, input:checkbox, select").each(function(){
			if($(this).data("name") != undefined)
			{
				var inputTag = "";
				var tagType = $(this).attr("type");
				if(tagType == "checkbox")
				{
					if( $(this).is(":checked"))
					{
						inputTag = $("<input />", {
							type : "hidden",
							class : "form-notRequired notRequired",
							name : $(this).data("name"),
							value : $(this).val()
						});
						formObj.append(inputTag);
					}
				}
				else
				{
					inputTag = $("<input />", {
						type : "hidden",
						class : "form-notRequired notRequired",
						name : $(this).data("name"),
						value : $(this).val()
					});
					formObj.append(inputTag);
				}
			}
		})
	}
	/* 리스트 조회 Ajax Form Data */
	var fn_list_ajax_data = function(callbackAjax, url, formObj, formActionType, dataRespType, loading, sync)
	{
		//폼 전송 타입
		if (typeof formActionType == "undefined") {
			formActionType = "POST";
		}
		//비동기 여부
		if (typeof sync == "undefined") {
			sync = true;
		}
		//application header json
		if (typeof dataRespType == "undefined") {
			dataRespType = "json";
		}
		//데디터 배열 처리
		if (formObj.data("submitFlag") != "Y") {
			jQuery.ajax({
				url : url,
				type : formActionType,
				timeout: 30000,
				data : formObj.serializeArray(),
				dataType : dataRespType,
				async: sync,
				cache : false,
				beforeSend : function(){
					formObj.data("submitFlag", "Y");
					if (loading) {
						jQuery(".loadingbar").stop().fadeIn(200);
					}
				},
				success : function(data, status, xhr){
					//비동기일때 errors 처리
					if(data.errors != null){
						if(typeof data.statusCode == "undefined"){
							formObj.data("submitFlag", "N");
						}
						fn_ajax_error(data, status, xhr);
					}
					else
					{
						formObj.data("submitFlag", "N");
						if (callbackAjax) {
							callbackAjax(data);
						}
					}
				},
				error : function(data, status, xhr){
					formObj.data("submitFlag", "N");
					fn_ajax_error(data, status, xhr);
				},
				complete : function(){
					if (loading) {
						jQuery(".loadingbar").stop().fadeOut(200);
					}
				}
			});
		}
	};

	/* Ajax Param Data */
	var fn_ajax_param_data = function(callbackAjax, url, paramData, dataType, loading, sync, type)
	{
		if (typeof dataType == "undefined") {
			dataType = "json";
		}

		if (typeof sync == "undefined") {
			sync = true;
		}

		if (typeof type == "undefined") {
			type = "POST";
		}
		jQuery.ajax({
			url : url,
			type : type,
			timeout: 300000,
			data : paramData,
			dataType : dataType,
			async: sync,
			cache : false,
			beforeSend : function() {
				if (loading) {
					jQuery(".loadingbar").stop().fadeIn(200);
				}
			},
			success : function(data, status, xhr) {
				if(data.errors != null){
					fn_ajax_error(data, status, xhr);
				}
				else
				{
					if (callbackAjax) {
						callbackAjax(data);
					}
				}
			},
			error : function(data, status, xhr) {
				console.log(data)
				fn_ajax_error(data, status, xhr);
			},
			complete : function() {
				if (loading) {
					jQuery(".loadingbar").stop().fadeOut(200);
				}
			}
		});
	};

	/* Ajax Param Data */
	var fn_json_ajax_data = function(callbackAjax, url, paramData, dataType, loading, sync)
	{
		if (typeof dataType == "undefined") {
			dataType = "json";
		}

		if (typeof sync == "undefined") {
			sync = false;
		}
		jQuery.ajax({
			headers: {
				'X-Requested-With': 'XMLHttpRequest',
				'X-CSRF-TOKEN': $('meta[name=X-CSRF-TOKEN]').attr('content')
			},
			url : url,
			type : "POST",
			timeout: 300000,
			data : JSON.stringify(paramData),
			dataType : dataType,
			async: sync,
			contentType: "application/json; charset=utf-8",
			beforeSend : function() {
				if (loading) {
					jQuery(".loadingbar").stop().fadeIn(200);
				}
			},
			success : function(data, status, xhr) {
				if(data.errors != null){
					fn_ajax_error(data, status, xhr);
				}
				else
				{
					if (callbackAjax) {
						callbackAjax(data);
					}
				}
			},
			error : function(data, status, xhr) {
				fn_ajax_error(data, status, xhr);
			},
			complete : function() {
				if (loading) {
					jQuery(".loadingbar").stop().fadeOut(200);
				}
			}
		});
	};

	/* Ajax Error */
	var fn_ajax_error = function(data, status, xhr)
	{
		var loginFlag = true, statusCode = data.status;

		if (statusCode == 401 || statusCode == 403 || statusCode == 406)
		{
			alert(msgCtrl.getMsg("fail.http.status.C" + statusCode));

			if (statusCode != 403)
			{
				loginFlag = false;
			}
		}
		else if(status == "success" && data.respCd == "500"){
			// Valid 실패
			$.each(data.errors, function(index, resp){
				$("#"+resp.fieldNm).focus();
				var msg = "";
				if(resp.msg.indexOf("empty") > -1)
				{
					var title = $("#"+resp.fieldNm).attr("title");
					if(typeof title != "undefined")
					{
						msg = title + ((title.charAt(title.length - 1).charCodeAt(0) - 44032 ) % 28 ? "을" : "를") + eval(resp.msg);
					}
				}
				else
				{
					msg = eval(resp.msg);
				}
				//var html = '<p style="color:red">'+msg+'</p>';
				//$("#"+resp.fieldNm).after(html);
				alert(msg);
				return false;
			});
		}
		else
		{
			alert(msgCtrl.getMsg("fail.act"));
		}

		if (!loginFlag)
		{
			location.href = "/mngwsercgateway/login";
		}
	};

	/* Set Period */
	var fn_set_period = function(obj, periodGubun, trgtPeriod, isStrtFocus)
	{

		var isVaild = false;
		var today = new Date($("body").data("curtDt"));
		if (periodGubun && typeof trgtPeriod === "number")
		{
			var year  = today.getFullYear();
			var month = today.getMonth() + 1;
			var day   = today.getDate();

			var trgtDay;

			if (periodGubun == "y")
			{
				var tempDay = new Date(year - trgtPeriod, month - 1, day);

				var trgtYear  = tempDay.getFullYear();
				var trgtMonth = tempDay.getMonth() + 1;
				var trgtDay   = tempDay.getDate();

				if (trgtMonth < 10)
				{
					trgtMonth = "0" + trgtMonth;
				}

				if (trgtDay < 10)
				{
					trgtDay = "0" + trgtDay;
				}

				trgtDay = trgtYear + "-" + trgtMonth  + "-" + trgtDay;
			}
			else if (periodGubun == "m")
			{
				var tempDay = new Date(year, month - 1, 1);

				tempDay.setMonth(tempDay.getMonth() - (trgtPeriod - 1));
				tempDay.setDate(tempDay.getDate() - 1);

				var trgtYear  = tempDay.getFullYear();
				var trgtMonth = tempDay.getMonth() + 1;
				var trgtDay   = tempDay.getDate();

				if (trgtMonth < 10)
				{
					trgtMonth = "0" + trgtMonth;
				}

				if (day < trgtDay)
				{
					trgtDay = day;
				}

				if (trgtDay < 10)
				{
					trgtDay = "0" + trgtDay;
				}

				trgtDay = trgtYear + "-" + trgtMonth  + "-" + trgtDay;
			}
			else if (periodGubun == "d")
			{
				var tempDay = new Date(year, month - 1, day - trgtPeriod);

				var trgtYear  = tempDay.getFullYear();
				var trgtMonth = tempDay.getMonth() + 1;
				var trgtDay   = tempDay.getDate();

				if (trgtMonth < 10)
				{
					trgtMonth = "0" + trgtMonth;
				}

				if (trgtDay < 10)
				{
					trgtDay = "0" + trgtDay;
				}

				trgtDay = trgtYear + "-" + trgtMonth  + "-" + trgtDay;
			}

			if (trgtDay)
			{
				isVaild = true;
			}
		}

		var formObj= obj ? jQuery(obj).closest("form") : jQuery("#frmSearch");

		var strtDtObj = formObj.find(".datetimepicker_strtDt");
		var strtDtObj2 = formObj.find(".datetimepicker_strtDtm");
		var endDtObj  = formObj.find(".datetimepicker_endDt");
		var endDtObj2  = formObj.find(".datetimepicker_endDtm");

		strtDtObj = $(obj).closest("fieldset").find(".datetimepicker_strtDt");
		strtDtObj2 = $(obj).closest("fieldset").find(".datetimepicker_strtDtm");
		endDtObj = $(obj).closest("fieldset").find(".datetimepicker_endDt");
		endDtObj2 = $(obj).closest("fieldset").find(".datetimepicker_endDtm");

		if (isVaild)
		{
			strtDtObj.datetimepicker("setOptions", { maxDate : new Date(today), value : trgtDay });
			strtDtObj2.datetimepicker("setOptions", { maxDate : new Date(today), defaultTime : "00:00" });
			endDtObj.datetimepicker("setOptions", {  maxDate : new Date('2050-12-31'), minDate : trgtDay, value : today });
			endDtObj2.datetimepicker("setOptions", {  maxDate : new Date('2050-12-31'), minDate : trgtDay, defaultTime : "00:00" });
		}
		else
		{
			if(isStrtFocus == undefined || (isStrtFocus)){
				strtDtObj.focus();
			}

			strtDtObj.datetimepicker("setOptions", { value : today, defaultDate: new Date($("body").data("curtDt")) , maxDate : new Date('2050-12-31'), minDate: new Date('1950-01-01') });
			strtDtObj2.datetimepicker("setOptions", { defaultDate: new Date($("body").data("curtDt")), defaultTime: "00:00" });
			strtDtObj.datetimepicker("reset").val("");
			strtDtObj2.datetimepicker("reset").val("");

			endDtObj.datetimepicker("setOptions", { value : today, defaultDate: new Date($("body").data("curtDt")), maxDate : new Date('2050-12-31'), minDate: new Date('1950-01-01')});
			endDtObj2.datetimepicker("setOptions", { defaultDate: new Date($("body").data("curtDt")), defaultTime: "00:00" });
			endDtObj.datetimepicker("reset").val("");
			endDtObj2.datetimepicker("reset").val("");

		}

	};

	/* 달력 초기화 */
	var fn_init_calendar = function(obj) {
		$(obj).parent().parent().find("input").each(function(index) {
			if ($.trim($(this).val()) == "") {
				// max, min date 설정 및 값 초기화
				$(this).datetimepicker("setOptions", {
					minDate: new Date('1950-01-01'),
					maxDate: new Date('2050-12-31')
				});
				$(this).datetimepicker("reset").val("");
			}
		});
	}

	/* Init Code */
	var fn_init_code = function()
	{
		$.each(jQuery(".selectbox_code"), function(index, obj){
			var slctVal = jQuery(obj).data("slctVal");

			if (slctVal)
			{
				cmmCtrl.paramAjax(function(data){
					var rtnList = data.rtnList;

					if (rtnList && rtnList.length > 0)
					{
						$.each(rtnList, function(index, data){
							jQuery(obj).val(data.cd).trigger("change");

							obj = jQuery(obj).next();
						});
					}
				}, "/mngwserc/co/coe/parnt-data.ajax", { cdId : jQuery(obj).data("cdId"), cd : slctVal });
			}
		});
	};

	/* Set CKEditor */
	var fn_set_editor = function(options)
	{
		if (typeof options.editor == "undefined")
		{
			options.editor = "editor";
		}

		if (typeof options.height == "undefined")
		{
			options.height = 500;
		}

		if (typeof options.readOnly == "undefined")
		{
			options.readOnly = false;
		}

		var trgtObj = CKEDITOR.replace(options.editor, { height : options.height });

		trgtObj.on("instanceReady", function(){
			// Output self-closing tags the HTML4 way, like <br>.
			this.dataProcessor.writer.selfClosingEnd = "/>";

			// Use line breaks for block elements, tables, and lists.
			var dtd = CKEDITOR.dtd;

			for (var e in CKEDITOR.tools.extend({}, dtd.$nonBodyContent, dtd.$block, dtd.$listItem, dtd.$tableContent))
			{
				this.dataProcessor.writer.setRules(e, {
					indent: true,
					breakBeforeOpen: true,
					breakAfterOpen: true,
					breakBeforeClose: true,
					breakAfterClose: true
				});
			}

			this.setReadOnly(options.readOnly);
		});

		trgtObj.on("mode", function(){
			if (this.mode === "source")
			{
				jQuery(".cke_contents").find("textarea").addClass("notRequired");
			}
		});
	};

	/* Set Dropzone */
	var fn_set_dropzone = function(tObj, tOption){
		var _maxFileSize = 5, _maxFileCnt = 10, _fileExtn = null, _fileFieldNm = null;
		var _required = true;
		if (tOption.maxFileSize)
		{
			_maxFileSize = tOption.maxFileSize / 1024 / 1024;
		}

		if (tOption.maxFileCnt)
		{
			_maxFileCnt = tOption.maxFileCnt;
		}

		if (tOption.fileExtn)
		{
			_fileExtn = tOption.fileExtn;
		}

		if (tOption.fileFieldNm)
		{
			_fileFieldNm = tOption.fileFieldNm;
		}

		$(tObj).dropzone({
			url : "/mngwserc/dropzone/upload",
			maxFilesize : _maxFileSize,
			maxThumbnailFilesize : _maxFileSize,
			filesizeBase : 1024,
			thumbnailWidth : 140,
			maxFiles : _maxFileCnt,
			acceptedFiles : _fileExtn,
			addRemoveLinks: true,
			dictRemoveFile: "삭제",
			dictFileTooBig : "errorMaxSize",					// 파일크기오류
			dictInvalidFileType : "errorAcceptedExtension",		// 파일확장자오류
			dictMaxFilesExceeded : "errorMaxCount",				// 파일개수오류
			init : function(){
				var $dropZone = this;
				var fielFieldValue = $(tObj).closest("form").find("input[name="+_fileFieldNm+"]").val();
				//첨부파일 키가 있으면 이미지 조회
				if(fielFieldValue != undefined && fielFieldValue != "")
				{
					//Drop Files 문구 지우기
					$(tObj).addClass("dz-started");
					cmmCtrl.paramAjax(function(data){
						if(data != null)
						{
							$.each(data, function(indx, rowData)
							{
								var file = {
									url : rowData.webPath,
									name: rowData.orgnFileNm,
									size: rowData.fileSize,
									fileSeq: rowData.fileSeq,
									fileOrd: rowData.fileOrd,
									fileDsc: rowData.fileDsc,
									type: rowData.fileExtn,
									webPath: rowData.webPath,
									fieldNm: _fileFieldNm,
									status : "addedfile"
								};

								$dropZone.emit("addedfile", file);
								$dropZone.emit("thumbnail", file, file.webPath);
								$dropZone.emit("complete", file);
								$(file.previewElement).width($dropZone.options.thumbnailWidth)
								$(file.previewElement).find("img").width("100%");

								/*if (!(rowData.fileExtn.indexOf("jp") > -1 || rowData.fileExtn.indexOf("png") > -1)) {
									$(file.previewElement).find(".dz-image img").attr("src", "/common/images/clipboard.svg");
								}*/

								// 대체 텍스트
								var altInput = $("<input />", {
									type : "text",
									class : "form-control input-sm notRequired",
									name : "fileAlt",
									title : file.fileDsc,
									placeholder : "대체 텍스트",
									value : file.fileDsc,
									maxlength : 10
								});
								$(altInput).insertBefore(file._removeLink);

								// 파일 순번
								var inputFileSeq = $("<input />", {
									type : "hidden",
									class : "form-notRequired",
									name : "fileSeq",
									value : file.fileSeq
								});
								$(inputFileSeq).insertBefore(file._removeLink);

								// 파일 정렬
								var inputFileOrd = $("<input />", {
									type : "hidden",
									class : "form-notRequired",
									name : "fileOrd",
									value : file.fileOrd
								});
								$(inputFileOrd).insertBefore(file._removeLink);

								// 파일 타입
								var inputFileExtn = $("<input />", {
									type : "hidden",
									class : "form-notRequired",
									name : "type",
									value : file.type
								});
								$(inputFileExtn).insertBefore(file._removeLink);

								// 다운로드 <a> 태그를 <div dz-filename><span> 태그 안에 넣기
								var imgDownloadTag = $("<a />", {
									href : "/mngwserc/file/view?fileSeq=" + file.fileSeq + "&fileOrd=" + file.fileOrd
								});
								var dzFileNameDiv = $(file.previewElement).find(".dz-filename");
								dzFileNameDiv.append(imgDownloadTag);
								dzFileNameDiv.find("a").append(dzFileNameDiv.find("span"));

								$dropZone.files.push(file)
							})
						}
					}, "/mngwserc/file/list", { fileSeq : fielFieldValue }, "json");
				}

				//파일 등록
				this.on("addedfiles", function(file) {
					jQuery(tObj).closest("form").find("#btnOneDelete, button[type='submit']").prop("disabled", true);
				});

				//등록 성공
				this.on("success", function(file, response) {
					if(response != null && response.length > 0){
						var rtnData = response[0];
						if (rtnData.respCd == "00")
						{
							$(file.previewElement).width(this.options.thumbnailWidth)
							$(file.previewElement).find("img").width("100%");
							// 대체 텍스트 추가
							var fileAlt  = rtnData.orgnFileNm.replace("."+rtnData.fileExtn, "");
							var altInput = jQuery("<input />", {
								type : "text",
								class : "form-control input-sm notRequired",
								name : "fileAlt",
								title : fileAlt,
								placeholder : "대체 텍스트",
								value : fileAlt.length > 10 ? fileAlt.substr(0, 10) : fileAlt,
								maxlength : 10
							});
							file.webPath = rtnData.webPath
							file.fileSeq = fielFieldValue
							file.fieldNm = _fileFieldNm
							file.orgnFileNm = rtnData.orgnFileNm;

							$(altInput).insertBefore(file._removeLink);
						}
						else {
							var message = rtnData.respMsg;

							if (message) {
								alert(message);
							}
							this.removeFile(file);
						}
					}
				});

				//에러
				this.on("error", function(file, code, response) {
					//debugger
					jQuery(tObj).closest("form").data(code, true);
					this.removeFile(file);

					// 세션 끊김 처리
					if(typeof response != "undefined" && (response.status == 401 || response.status == 406)){
						fn_ajax_error(response, null, null);
					}
				});

				// 파일 선택했을 때
				this.on("queuecomplete", function() {
					var trgtObj = jQuery(tObj).closest("form");

					if (trgtObj.data("errorMaxSize")) {
						alert("첨부파일은 최대 " + _maxFileSize + "MB까지만 등록가능합니다.");
					}
					else if (trgtObj.data("errorAcceptedExtension")) {
						alert("등록가능한 확장자가 아닙니다.");
					}
					else if (trgtObj.data("errorMaxCount")) {
						alert("첨부파일은 최대 " + _maxFileCnt + "개까지만 등록가능합니다.");
					}
					trgtObj.removeData();

					jQuery(tObj).closest("form").find("#btnOneDelete, button[type='submit']").prop("disabled", false);
				});

				// 파일 삭제할 때
				this.on("removedfile", function(file){
					//DB에서 조회한 파일만 삭제 시 인풋값 삽입
					if(file.status == "addedfile") {
						var delFile = {
							fieldNm: _fileFieldNm,
							fileSeq: file.fileSeq,
							fileOrd: file.fileOrd,
							status: "delfile"
						};
						$dropZone.files.push(delFile);
					}
				})
			}
		});
	};

	//DROPZONE 파일 등록 여부 확인
	var fn_chk_dropzone = function(formObj){
		var notRequired = true;
		var fileCnt = 0;
		var trgtTit = "";
		var isSend = true;
		formObj.find(".dropzone").each(function(){
			notRequired = $(this).hasClass("notRequired");
			fileCnt     = 0;
			trgtTit     = $(this).data("title");
			if($(this).get(0).dropzone.files != undefined && $(this).get(0).dropzone.files.length > 0){
				$.each($(this).get(0).dropzone.files, function(idx, data){
					if(data.status == "addedfile" || data.status == "success"){
						fileCnt = 1;
					}
				})
			}
			if(!notRequired && fileCnt == 0){
				//첨부파일이 필요하다.
				var lastChar = trgtTit.charAt(trgtTit.length - 1);
				var josa = (lastChar.charCodeAt(0) - 44032) % 28 ? "을" : "를";
				alert(trgtTit + josa + " 등록하세요.");
				isSend = false;
				$(this).focus();
				return false;
			}
		});
		return isSend;
	}

	//DROPZON 파일 정보 송신
	var fn_ajax_file_data = function(callbackAjax, url, formObj, dataType){

		if (typeof dataType == "undefined") {
			dataType = "json";
		}
		var frmData = formObj.serializeArray();
		var fileIndex = 0;
		formObj.find(".dropzone.attachFile").each(function(){
			if($(this).get(0).dropzone.files != undefined && $(this).get(0).dropzone.files.length > 0){
				$.each($(this).get(0).dropzone.files, function(idx, data){
					//alt값  data에 넣어주기.
					data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();
					for (let i in data) {
						if (data.hasOwnProperty(i)) {
							frmData.push({
								name: "fileList["+(fileIndex)+"]." + i, value: data[i]
							});
						}
					}
					fileIndex = fileIndex + 1;
				})
			}
		});
		fileIndex = 0;
		formObj.find(".dropzone.pcThumbFile").each(function(){
			if($(this).get(0).dropzone.files != undefined && $(this).get(0).dropzone.files.length > 0){
				$.each($(this).get(0).dropzone.files, function(idx, data){
					//alt값  data에 넣어주기.
					data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();
					for (let i in data) {
						if (data.hasOwnProperty(i)) {
							frmData.push({
								name: "pcThumbList["+(fileIndex)+"]." + i, value: data[i]
							});
						}
					}
					fileIndex = fileIndex + 1;
				})
			}
		});
		fileIndex = 0;
		formObj.find(".dropzone.moThumbFile").each(function(){
			if($(this).get(0).dropzone.files != undefined && $(this).get(0).dropzone.files.length > 0){
				$.each($(this).get(0).dropzone.files, function(idx, data){
					//alt값  data에 넣어주기.
					data.fileDsc = $(data._removeLink).closest(".dz-preview").find("input[name=fileAlt]").val();
					for (let i in data) {
						if (data.hasOwnProperty(i)) {
							frmData.push({
								name: "moThumbList["+(fileIndex)+"]." + i, value: data[i]
							});
						}
					}
					fileIndex = fileIndex + 1;
				})
			}
		});
		$.ajax({
			url : url,
			type : "post",
			timeout: 300000,
			dataType: dataType,
			data : frmData,
			async: true,
			cache : false,
			beforeSend : function() {
				$(".loadingbar").stop().fadeIn(200);
			},
			success : function(data, status, xhr) {
				if(data.errors != null)
				{
					fn_ajax_error(data, status, xhr);
				}
				else
				{
					//input 삽입 또는 인풋있으면 데이터 삽입
					if(data.fileList != undefined && data.fileList.length > 0){
						$.each(data.fileList, function(idx, data){
							for (let key in data) {
								if (data.hasOwnProperty(key) && key == "fieldNm") {
									formObj.find("input[name="+data[key]+"]").val(data["fileSeq"]);
								}
							}
						});
					}
					if (callbackAjax) {
						callbackAjax(data);
					}
				}
			},
			error : function(data, status, xhr) {
				fn_ajax_error(data, status, xhr);
			},
			complete : function() {
				$(".loadingbar").stop().fadeOut(200);
			}
		});
	}

	/* Set Popup */
	var fn_set_popup = function(pUrl, pName, pSw, pSh)
	{
		//스크린의 크기
		var cw = screen.availWidth;
		var ch = screen.availHeight;
		var sw = pSw;
		var sh = pSh;
		var ml = (cw - sw) / 2;
		var mt = (ch - sh) / 2;

		window.open(pUrl, pName, "width="+sw+",height="+sh+",top="+mt+",left="+ml+",location=no,menubar=no,toolbar=no,scrollbars=yes,resizable=no,copyhistory=no");
	};

	/* Check Maxlength */
	var fn_check_maxlength = function(obj)
	{
		var maxLength = obj.maxLength;

		if (obj.value.length > maxLength)
		{
			obj.value = obj.value.slice(0, maxLength);
		}
	};

	/* Check Continuous String */
	var check_cont_string =  function(str, limit)
	{
		var o, d, p, n = 0, l = limit == null ? 4 : limit;

		for (var i = 0; i < str.length; i++)
		{
			var c = str.charCodeAt(i);

			if (i > 0 && (p = o - c) > -2 && p < 2 && (n = p == d ? n + 1 : 0) > l - 3)
			{
				return false;
			}

			d = p, o = c;
		}

		return true;
	};

	//Vuew 페이지 리스트
	var fn_vue_page_list = function(form, pageTagNm)
	{
		Vue.component(pageTagNm, {
			props: ['total'],
			data: function () {
				const pageRowSize	 = parseInt(form.find("#listRowSize").val()); // 한 페이지에 보여지는 데이터의 수
				const pageButtonSize = parseInt(form.find("#pageRowSize").val()); // 한 번에 보여질 페이지 버튼의 수 (홀수여야만 함)
				const totalCount = Number(this.total); // 전체 데이터 수
				const totalPage = parseInt((totalCount-1)/pageRowSize) + 1; // 전체 페이지 수
				let data = {
					pageRowSize: pageRowSize,
					pageButtonSize: pageButtonSize,

					totalCount: totalCount,
					totalPage: totalPage,

					now:1,
					prev:1,
					next:2,

					start: 1,
					end: this.start + (this.pageButtonSize > this.totalPage) ? this.totalPage : this.start + this.pageButtonSize - 1,
					pageArray: [],
				};

				for(var i=data.start; i<=data.end; i++){
					data.pageArray.push(i);
				}
				return data;
			},
			methods:{
				movePage: function(no){
					this.now = parseInt( no );

					this.totalCount = this.$root.totalCount;
					this.totalPage = Math.ceil(this.totalCount / this.pageRowSize);

					this.prev = (this.now  > 1) ? this.now - 1 : null
					this.next = (this.totalPage !== this.now) ? this.now + 1 : null

					this.start =  (Math.ceil(this.now / this.pageButtonSize) - 1) * this.pageButtonSize + 1;
					this.end = ((this.start + this.pageButtonSize) > this.totalPage ? this.totalPage : (this.start + this.pageButtonSize - 1))

					// Set - page number
					this.pageArray = [];
					for(var i=this.start; i<=this.end; i++){
						this.pageArray.push(i);
					}
					// Ajax ...
				},
				prevPage: function(){
					if(this.prev){
						this.pageSearch(this.prev);
					}
				},
				nextPage: function(){
					if(this.next){
						this.pageSearch(this.next);
					}
				},
				pageSearch : function(i){
					this.$root.search(i);
				}
			},
			template:
				'<div class="text-center">' +
				'<div class="col-sm-12">' +
				'<ul class="pagination">' +
				'<li class="prev"><a  @click=prevPage(prev) data-page="prev" class="button">&lt;</a></li>' +
				'<li v-for="i in pageArray" :class="i == now ? \'active\' : \'\'"  aria-disabled="false"  aria-selected="false"><a href="javascript:" @click=pageSearch(i) :data-page="i">{{ i }}</a></li>' +
				'<li class="next"><a @click=nextPage(next) data-page="next" class="button">&gt;</a></li>' +
				'</ul>' +
				'</div>' +
				'</div>'
		})
	}

	//Vuew 페이지 리스트
	var fn_vue = function(form, id, sendUrl, sendCallBack)
	{
		var vueList = new Vue({
			el: '#' + id,
			data: {
				lists: []
				, totalCount: 1
			},
			created() {

			},
			methods:{
				convertDtm: fn_vue_convert_dtm
				, search: function(){
					//비동기 통신
					cmmCtrl.frmAjax(function(respObj) {
						if(respObj != undefined)
						{
							vueList.lists = respObj.list;
							vueList.totalCount = respObj.totalCount;
							vueList.$children[0].movePage( Number(form.find("#pageIndex").val()));
						}
						else
						{
							alert(msgCtrl.getMsg("fail.act"));
						}
						if (sendCallBack) {
							sendCallBack(respObj);
						}
					}, sendUrl, form, "GET", "json");
				}
			}
		});
		return  vueList
	}

	//VUE 날짜 패턴 변경
	var fn_vue_convert_dtm = function(dtm, currentPattrn, convertPattrn){
		var regX = null;
		var convertX = null;
		if(dtm != undefined && dtm != null)
		{
			//현재 패턴
			if(currentPattrn.toLowerCase() == "yyyy-mm-dd")
			{
				regX = /(\d{4})-(\d{2})-(\d{2})/g;
			}
			else if(currentPattrn.toLowerCase() == "yyyy-mm-dd hh")
			{
				regX = /(\d{4})-(\d{2})-(\d{2}) (\d{2})/g;
			}
			else if(currentPattrn.toLowerCase() == "yyyy-mm-dd hh:mm")
			{
				regX = /(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2})/g;
			}
			else if(currentPattrn.toLowerCase() == "yyyy-mm-dd hh:mm:ss")
			{
				regX = /(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})/g;
			}
			else
			{
				return false;
			}
			//변경 패턴
			if(convertPattrn.toLowerCase() == "yyyy-mm-dd")
			{
				convertX = '$1-$2-$3';
			}
			else if(convertPattrn.toLowerCase() == "yyyy-mm-dd hh")
			{
				convertX = '$1-$2-$3 $4';
			}
			else if(convertPattrn.toLowerCase() == "yyyy-mm-dd hh:mm")
			{
				convertX = '$1-$2-$3 $4:$5';
			}
			else if(convertPattrn.toLowerCase() == "yyyy-mm-dd hh:mm:ss")
			{
				convertX = '$1-$2-$3 $4:$5:$6';
			}
			else
			{
				return false;
			}
			return dtm.replace(regX, convertX);
		}
		else
		{
			return "-";
		}
	}

	// 자릿수 만큼 0 붙이기
	var fn_pad = function(num, size){
		var s = "000000000" + num;
		return s.substring(s.length-size);
	}

	// URL 유효성 체크
	var fn_check_url = function(urlObj) {
		var url = urlObj.val();

		var startUrl = /^\/[^\/]/;			// / (//는 안 됨)
		var expUrl = /^http[s]?\:\/\//i;	// http://, https://

		if(startUrl.test(url) || url.startsWith("javascript") || expUrl.test(url)) {
			return true;
		} else {
			alert(msgCtrl.getMsg("fail.notValidUrl"));
			// urlObj.addClass("error").val("").focus();
			urlObj.val("").focus();
			return false;
		}
	}

	/* Dropzone File Remove */
	jQuery(document).on("click", ".dropzone_remove", function(){
		var dropzone = jQuery(this).closest(".dropzone");
		var fileSeq  = jQuery(this).data("fileSeq");

		if (typeof fileSeq != "undefined")
		{
			var trgtObj = dropzone.find("input[name='" + dropzone.data("delFileNm") + "']");
			var trgtVal = trgtObj.val();

			if (trgtVal)
			{
				trgtObj.val(trgtVal + "," + fileSeq);
			}
			else
			{
				trgtObj.val(fileSeq);
			}
		}

		jQuery(this).closest(".dz-preview").remove();

		if (dropzone.children(".dz-preview").length == 0)
		{
			dropzone.removeClass("dz-started");
		}
	});

	/***
	 * 페이지 로드
	 ***/
	jQuery(document).ready(function(){
		/* 목록 페이지 이동 */
		jQuery("#btnList").on("click", function(e){
			if (confirm(msgCtrl.getMsg("confirm.list")))
			{
				if (sessionStorage.getItem("pageIndex")) {
					var pageIndex = sessionStorage.getItem("pageIndex");
					location.href = "./list?" + jQuery(this).data("strPam") + "&pageIndex=" + pageIndex;

				} else {
					location.href = "./list?" + jQuery(this).data("strPam");
				}
			}
		});
	});

	// Back/forward cache(뒤로/앞으로 가기) 체크
	window.onpageshow = function(event){
		history.scrollRestoration = "manual";
		if (event.persisted)
		{
			location.reload();
		}
	};

	/* 파라미터 추출 */
	jQuery.extend({
		getXssVal : function(targetValue){
			var returnValue = targetValue;

			if (returnValue)
			{
				returnValue = returnValue.replace(/&amp;/g, "&");
				returnValue = returnValue.replace(/&lt;/g, "<");
				returnValue = returnValue.replace(/&gt;/g, ">");
				returnValue = returnValue.replace(/&#34;/g, "\"");
				returnValue = returnValue.replace(/&#37;/g, "%");
				returnValue = returnValue.replace(/&#39;/g, "\'");
			}

			return returnValue;
		},
		getURLParam : function(strParamName) {
			var strHref   = window.location.href;
			var cmpstring = strParamName + "=";
			var bFound    = false;
			var strReturn = "";

			if (strHref.indexOf("?") > -1)
			{
				var aQueryString = strHref.substr(strHref.indexOf("?") + 1).split("&");

				for (var iParam = 0, length = aQueryString.length; iParam < length; iParam++)
				{
					if (aQueryString[iParam].substr(0, cmpstring.length) == cmpstring)
					{
						strReturn = aQueryString[iParam].split("=")[1];
						bFound = true;
						break;
					}
				}
			}

			if (bFound == false)
			{
				return null;
			}

			return strReturn;
		},
		getURLParams: function(){
			return location.search
		}
	});

	/* clearForm */
	jQuery.fn.clearForm = function(){
		return this.each(function(){
			var type = this.type, tag = this.tagName.toLowerCase();

			if (tag === "form")
			{
				return jQuery(":input", this).clearForm();
			}
			if (type === "text" || type === "password" || type === "hidden" || tag === "textarea")
			{
				this.value = "";
			}
			else if (type === "checkbox" || type === "radio")
			{
				this.checked = false;
			}
			else if (tag === "select")
			{
				this.selectedIndex = 0;
			}
		});
	};

	/* form strPam */
	jQuery.fn.strPam = function(){
		var formObj = this, tmpObj = { }, strPam = "";

		$.each(formObj.serializeArray(), function(){
			var $type = formObj.find("[name='" + this.name + "']").prop("type");

			if (tmpObj[this.name])
			{
				if ($type == "checkbox")
				{
					tmpObj[this.name] = tmpObj[this.name] + "," + "|" + this.value + "|";
				}
				else
				{
					tmpObj[this.name] = tmpObj[this.name] + "," + this.value;
				}
			}
			else
			{
				if ($type == "checkbox")
				{
					tmpObj[this.name] = "|" + this.value + "|";
				}
				else
				{
					tmpObj[this.name] = this.value;
				}
			}
		});

		$.each(Object.keys(tmpObj), function(){
			if (strPam)
			{
				strPam += "&";
			}

			strPam += this + "=" + tmpObj[this];
		});

		return strPam;
	};

	//페이징 처리
	var fn_paging = function(totCnt, $formObj, listFormId, pagingFormId){

		// 페이징 세팅
		const pageRowSize	 = parseInt($formObj.find("input[name=listRowSize]").val()); // 한 페이지에 보여지는 데이터의 수
		const pageButtonSize = parseInt($formObj.find("input[name=pageRowSize]").val()); // 한 번에 보여질 페이지 버튼의 수 (홀수여야만 함)
		const totalCount = parseInt(totCnt); // 전체 데이터 수
		const totalPage = parseInt((totalCount-1)/pageRowSize) + 1; // 전체 페이지 수

		let data = {
			pageRowSize: pageRowSize,
			pageButtonSize: pageButtonSize,

			totalCount: totalCount,
			totalPage: totalPage,

			now:0,
			prev:0,
			next:0,

			start: 0,
			end:0,
		};

		data.now = parseInt( $formObj.find("#pageIndex").val() );

		// this.totalCount = this.$root.totalCount;
		data.totalPage = Math.ceil(data.totalCount / data.pageRowSize);
		// 검색결과 없는 경우 totalPage = 1로 설정
		data.totalPage = data.totalPage == 0 ? 1 : data.totalPage ;

		// data.prev = (data.now  > 1) ? data.now - 1 : null
		data.prev = (data.now  > 1) ? data.now - 1 : data.now
		// data.next = (data.totalPage !== data.now && data.totalPage != 0) ? data.now + 1 : null
		data.next = (data.totalPage !== data.now && data.totalPage != 0) ? data.now + 1 : data.now

		data.start =  (Math.ceil(data.now / data.pageButtonSize) - 1) * data.pageButtonSize + 1;
		data.end = ((data.start + data.pageButtonSize) > data.totalPage ? data.totalPage : (data.start + data.pageButtonSize - 1))
		var button='';

		for(var i=data.start; i<=data.end; i++){
			if(i==data.now){
				button += '<li aria-disabled="false" class="active" aria-selected="false" ><a href="javascript:" class="pageSet" value="'+ i +'" data-page="'+ i +'">' + i + '</a></li>';
			}else {
				button += '<li aria-disabled="false" aria-selected="false" ><a href="javascript:" class="pageSet" value="' + i + '" data-page="'+ i +'">' + i + '</a></li>';
			}
		}

		// 페이징 버튼
		var template = '<div class="text-center">' +
			'<div class="col-sm-12">' +
			'<ul class="pagination">' +
			'<li><a href="javascript: " class="pageSet button" value="1" data-page="1">&lt;&lt;</a></li>'+
			'<li><a href="javascript:" class="pageSet button" value="' + data.prev + '" data-page="' + data.prev + '">&lt;</a></li>' +
			button +
			'<li><a href="javascript:" class="pageSet button" value="' + data.next + '" data-page="' + data.next + '">&gt;</a></li>' +
			'<li><a href="javascript:" class="pageSet button" value="' + data.totalPage + '" data-page="' + data.totalPage + '">&gt;&gt;</a></li>' +
			'</ul>' +
			'</div>' +
			'</div>';

		// 화면 붙이기
		$formObj.find("#"+pagingFormId).html(template);
	}

	/* Grid Record Count */
	var fn_grid_record_count = function()
	{
		return 10;
	};

	/* Grid Paging Object */
	var fn_grid_paging_object = function()
	{
		var rtnObj = {
			pageSizes : [10, 30, 50],
			buttonCount : 5,
			info : true,
			messages : {
				first : "첫 페이지",
				previous : "이전 페이지",
				next : "다음 페이지",
				last : "마지막 페이지",
				of : "from {0}",
				itemsPerPage : "건씩 보기",
				page : "페이지 선택",
				empty : "데이터 없음",
				display : "총 {2}건 중 {0}-{1} 건"
			}
		};

		return rtnObj;
	};

	/* Grid Pagination */
	var fn_grid_pagination = function(data)
	{
		jQuery("#pageIndex").val(data.page);

		var paramArr = jQuery("#frmSearch").data("strPam").split("&").map(function(value){
			return value.indexOf("pageIndex=") < 0 ? value : "pageIndex=" + data.page;
		});

		history.replaceState("", "", location.pathname + "?" + paramArr.join("&"));
	};

	/* Grid Request Start */
	var fn_grid_request_start = function(gridObj)
	{
		if (gridObj)
		{
			gridObj.data("kendoGrid").thead.find(".checkbox_all").prop("checked", false);
		}
	};

	/* Grid Request End */
	var fn_grid_request_end = function(gridObj)
	{
		if (gridObj && gridObj.data("kendoGrid").tbody)
		{
			gridObj.data("kendoGrid").tbody.closest(".k-auto-scrollable").scrollTop(0);
		}
	};

	/* Grid Message Object */
	var fn_grid_no_data_message = function()
	{
		var rtnObj = {
			template : "<div style=\"position:absolute; top:50%; left:50%; transform:translate(-50%, -50%);\">검색 결과가 없습니다.<br />(등록된 데이터가 없습니다.)</div>"
		};

		return rtnObj;
	};

	/* 체크박스 선택 삭제 */
	var fn_grid_remove_row = function(gridObj)
	{
		var trgtObj = gridObj.data("kendoGrid");
		var rowsObj = trgtObj.tbody.find("input[name='detailsKey']:checked");

		if (rowsObj.length == 0)
		{
			alert(msgCtrl.getMsg("fail.target"));
		}
		else
		{
			if (confirm(msgCtrl.getMsg("confirm.del")))
			{
				var dataSource = trgtObj.dataSource;

				jQuery(rowsObj).each(function(){
					dataSource.remove(dataSource.get(jQuery(this).val()));
				});

				trgtObj.saveChanges();

				alert(msgCtrl.getMsg("success.del"));
			}
		}
	};

	/* 노출 미노출 선택 */
	var fn_grid_update = function(gridObj)
	{
		var trgtObj = gridObj.data("kendoGrid");
		var rowsObj = trgtObj.tbody.find("input[name='detailsKey']:checked");

		if (rowsObj.length == 0)
		{
			alert(msgCtrl.getMsg("fail.target"));
		}
		else
		{
			if (confirm(msgCtrl.getMsg("confirm.del")))
			{
				var dataSource = trgtObj.dataSource;

				jQuery(rowsObj).each(function(){
					dataSource.remove(dataSource.get(jQuery(this).val()));
				});

				trgtObj.saveChanges();

				alert(msgCtrl.getMsg("success.del"));
			}
		}
	};

	//카카오 주소 호출
	var fn_postData = function(width , height , zipcode ,bscAddr , dtlAddr){
		new daum.Postcode({
			width: width, //생성자에 크기 값을 명시적으로 지정해야 합니다.
			height: height,
			oncomplete: function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 각 주소의 노출 규칙에 따라 주소를 조합한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수

				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}

				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if(data.userSelectedType === 'R'){
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if(data.buildingName !== '' && data.apartment === 'Y'){
						extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if(extraAddr !== ''){
						extraAddr = ' (' + extraAddr + ')';
					}
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				$("#"+zipcode).val(data.zonecode);
				$("#"+bscAddr).val(addr);
				// 커서를 상세주소 필드로 이동한다.
				$("#"+dtlAddr).focus();

			}
		}).open({
			left: (document.body.offsetWidth / 2) - (200 / 2),
			top: (window.screen.height / 2) - (300 / 2)
		});
	}

	//교육 평가지 매핑
	//getExamLayerPop(function(data){data 객체를 받아서 처리});
	var fn_exam_layer_pop = function(fnc){
		$(".exgExamSrchLayer").one('show.bs.modal', function() {
			// Add class for soft backdrop
			$(".exgExamSrchLayer").find("#btnRefresh").click();
			$(".exgExamSrchLayer").find("#btnSearch").click();
		}).one('hidden.bs.modal', function() {
			// Remove class for soft backdrop (if not will affect future modals)
		}).one('choice', function(data, param) {
			// Remove class for soft backdrop (if not will affect future modals)
			fnc(param);
		}).modal();
	}

	return {
		nvl : fn_replace_null,
		bscAjax : fn_ajax,
		frmAjax : fn_ajax_data,
		listFrmAjax : fn_list_ajax_data,
		paramAjax : fn_ajax_param_data,
		errorAjax : fn_ajax_error,
		setPeriod: fn_set_period,
		initCode : fn_init_code,
		setEditor: fn_set_editor,
		setDropzone : fn_set_dropzone,
		chkDropzone : fn_chk_dropzone,
		fileFrmAjax: fn_ajax_file_data,
		setPopup : fn_set_popup,
		checkMaxlength : fn_check_maxlength,
		checkContString : check_cont_string,
		vuePageList: fn_vue_page_list,
		vueConvertDtm: fn_vue_convert_dtm,
		vueContainer: fn_vue,
		listPaging : fn_paging,
		setFormData: fn_list_frm_set_data,
		pad: fn_pad,
		checkUrl: fn_check_url,
		initCalendar: fn_init_calendar,

		gridRecordCount : fn_grid_record_count,
		gridPagingObject : fn_grid_paging_object,
		gridPagination: fn_grid_pagination,
		gridNoDataMessage : fn_grid_no_data_message,
		gridRequestStart : fn_grid_request_start,
		gridRequestEnd : fn_grid_request_end,
		gridRemoveRow : fn_grid_remove_row,
		searchPostCode : fn_postData,

		//json형식의 데이터를 문자열로 변환 후 자바 컨트롤러에서 @RequestBody BaseDTO baseDto 로 받는다
		jsonAjax : fn_json_ajax_data,
		//교육 평가지 매핑
		getExamLayerPop : fn_exam_layer_pop,
	}
}());