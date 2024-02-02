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
					jQuery(".loading-area").stop().fadeIn(200);
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
					jQuery(".loading-area").stop().fadeOut(200);
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
		if(typeof loading == "undefined" || loading =="") {
			loading = true;
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
						jQuery(".loading-area").stop().fadeIn(200);
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
						jQuery(".loading-area").stop().fadeOut(200);
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
						jQuery(".loading-area").stop().fadeIn(200);
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
						jQuery(".loading-area").stop().fadeOut(200);
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
					jQuery(".loading-area").stop().fadeIn(200);
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
					jQuery(".loading-area").stop().fadeOut(200);
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
		var jsonStrData = JSON.stringify(paramData);
		jQuery.ajax({
			headers: {
				'X-Requested-With': 'XMLHttpRequest',
				'X-CSRF-TOKEN': $('meta[name=X-CSRF-TOKEN]').attr('content')
			},
			url : url,
			type : "POST",
			timeout: 300000,
			data : jsonStrData,
			dataType : dataType,
			async: sync,
			contentType: "application/json; charset=utf-8",
			beforeSend : function() {
				if (loading) {
					jQuery(".loading-area").stop().fadeIn(200);
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
					jQuery(".loading-area").stop().fadeOut(200);
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
			location.href = "/";
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


	//파일 정보 송신
	var fn_ajax_file_data = function(callbackAjax, url, formObj, dataType, sync, loading){
		if (typeof dataType == "undefined") {
			dataType = "json";
		}
		if (typeof sync == "undefined") {
			sync = true;
		}
		if (formObj.data("submitFlag") != "Y")
		{
			var frmData = formObj.serializeArray();
			var fileIndex = 0;
			formObj.find("input[type=file]").each(function(){
				if($(this).data("tempSuccessFileData") != undefined)
				{
					$.each($(this).data("tempSuccessFileData"), function(idx, data){
						//alt값  data에 넣어주기.
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
			jQuery.ajax({
				headers: {
					'X-Requested-With': 'XMLHttpRequest',
					'X-CSRF-TOKEN': $('meta[name=X-CSRF-TOKEN]').attr('content')
				},
				url : url,
				type : "POST",
				timeout: 30000,
				data : frmData,
				dataType : dataType,
				async: sync,
				cache : false,
				beforeSend : function() {
					formObj.data("submitFlag", "Y");
					if (loading) {
						$(".dimd").stop().fadeIn(100);
					}
				},
				success : function(data, status, xhr) {
					formObj.data("submitFlag", "N");
					if (callbackAjax) {
						callbackAjax(data);
					}
				},
				error : function(data, status, xhr) {
					formObj.data("submitFlag", "N");
					fn_ajax_error(data, status, xhr);
				},
				complete : function() {
					if (loading) {
						$(".dimd").stop().fadeOut(100);
					}
				}
			});
		}
	}

	/* Ajax File Data */
	var fn_ajax_file = function(callbackAjax, url, formObj, dataType, loading, sync)
	{
		if (typeof dataType == "undefined") {
			dataType = "json";
		}

		if (typeof sync == "undefined") {
			sync = true;
		}

		if (formObj.data("submitFlag") != "Y")
		{
			jQuery.ajax({
				url : url,
				type : "post",
				timeout: 30000,
				data : new FormData(formObj[0]),
				dataType : dataType,
				async: sync,
				cache : false,
				contentType: false,
				processData: false,
				beforeSend : function() {
					formObj.data("submitFlag", "Y");
					if (loading) {
						$(".dimd").stop().fadeIn(100);
					}
				},
				success : function(data, status, xhr) {
					formObj.data("submitFlag", "N");

					if (callbackAjax) {
						callbackAjax(data);
					}
				},
				error : function(data, status, xhr) {
					formObj.data("submitFlag", "N");

					fn_ajax_error(data, status, xhr);
				},
				complete : function() {
					if (loading) {
						$(".dimd").stop().fadeOut(100);
					}
				}
			});
		}
	};
	//FORM태그의 파일 객체 초기화
	var fn_input_file_init = function(fileObj){
		var agent = navigator.userAgent.toLowerCase();
		if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) )
		{
			fileObj.replaceWith(fileObj.clone(true));
		}
		else
		{
			fileObj.val("");
		}
		return fileObj;
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
		return obj.value.length;
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

	//더보기 페이징 처리
	var fn_more_paging = function(totCnt, $formObj, listFormId, pagingFormId){

		// 페이징 세팅
		const pageRowSize	 = parseInt($formObj.find("input[name=listRowSize]").val()); // 한 페이지에 보여지는 데이터의 수
		const pageButtonSize = parseInt($formObj.find("input[name=pageRowSize]").val()); // 한 번에 보여질 페이지 버튼의 수 (홀수여야만 함)
		const totalCount = parseInt(totCnt); // 전체 데이터 수
		const trRowCount = $formObj.find("#"+listFormId).find("tr").size();
		if(totalCount <= trRowCount || totalCount == 0){
			$formObj.find("#"+pagingFormId).hide();
		}else{
			// 페이징 버튼
			var template = '<span>더보기</span>' +
				'<span class="item-count">('+trRowCount+'/'+totalCount+')</span>';
			// 화면 붙이기
			$formObj.find("#"+pagingFormId).html(template);
		}
	}

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
				// 방문교육시 교육장소 받아오기
				$("#edctnPlaceAddr").val(data.sido + " " + data.sigungu);
				// 커서를 상세주소 필드로 이동한다.
				$("#"+dtlAddr).focus();

			}
		}).open({
			left: (document.body.offsetWidth / 2) - (200 / 2),
			top: (window.screen.height / 2) - (300 / 2)
		});
	}

	/**
	 * nice 인증 팝업
	 * @param params
	 * "no" ~ 시작 시 페이지 이동 x
	 * "/id-ref-ff" : 페이지 이동
	 * &파라미터 추가로 넘기고자하는 데이터 전송 최대 5개 /id-find-res&ex1&ex2 최대 5개
	 */

	function fn_nice_certification(params) {
		jQuery.ajax({
			url : "/nice/my-idnttvrfct",
			type : "post",
			data :
				{
					"receivedata" : params
				},
			success : function(data)
			{
				const {form} = document;

				const option = `status=no, menubar=no, toolbar=no, resizable=no, width=500, height=600`;
				document.getElementById('enc_data').value = data.enc_data; // enc_data 값을 설정
				document.getElementById('integrity_value').value = data.integrity_value; // integrity_value 값을 설정
				document.getElementById('token_version_id').value = data.token_version_id; // integrity_value 값을 설정

				window.open('', 'nicePopup', option);

				form.target = 'nicePopup';
				document.getElementById('form').submit();

			},
			error : function(xhr, ajaxSettings, thrownError)
			{
				cmmCtrl.errorAjax(xhr);
				jQuery.jstree.rollback(data.rlbk);
			}
		});
	}

	var fn_email_validation_chk = function (email) {
		let splitEmail = email.toString().split("@");
		var atRegex = /[^@]+@[^@]+/; //@ 체크
		var atDupRegex = /.*@.*@.*/; //@ 2개 이상 체크
		var spaceRegex = /\s/; //공백 체크
		var commaRegex = /\./;
		let multiRegex = /[&=＇,+<>()]+|(\.{2,})/; //& = , ' + , 포함 경우
		let lastRegex = /[0-9!@#$%^&*()_+{}\[\]:;<>,.?\\|`~-]+$/;
		return atRegex.test(email) &&
			!atDupRegex.test(email) &&
			commaRegex.test(splitEmail[1]) &&
			!spaceRegex.test(email) &&
			!multiRegex.test(email) &&
			!lastRegex.test(email) &&
			splitEmail[0].length < 64 &&
			splitEmail[1].length < 255
	}

	var fn_set_calendar = function(){

		jQuery.datetimepicker.setLocale("ko");

		$.each(jQuery(".datetimepicker_input"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y-m-d",
				defaultDate : new Date(),
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
				onSelectDate : function(selectedDate, selectedObj) {
					selectedObj.blur();
				}
			});
		});

		// 게시기간(일자) Start -----
		$.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y.m.d",
				defaultDate : "",
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
				todayButton: false,
				onSelectDate : function(selectedDate, selectedObj) {
					var strtDt   = selectedDate;
					var endDtObj = selectedObj.find(".datetimepicker_endDt");
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
				format : "Y.m.d",
				defaultDate : "",
				defaultTime : "00:00",
				scrollMonth : false,
				scrollTime : false,
				todayButton: false,
				onSelectDate : function(selectedDate, selectedObj) {
					var strtDtObj = selectedObj.find(".datetimepicker_strtDt");
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

	var fn_calendar = function(beforeMonth,afterMonth){
		jQuery.datetimepicker.setLocale("ko");

		// 게시기간(일자) Start -----
		$.each(jQuery(".datetimepicker_strtDt"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y.m.d",
				defaultDate : "",
				defaultTime : "00:00",
				scrollInput : false,
				scrollMonth : false,
				scrollTime : false,
				todayButton: false,
			}).on('change',function(e){
				var strtDt   = new Date($(this).val());
				var endDtObj = jQuery(".datetimepicker_endDt");
				var endDt	 = new Date(endDtObj.val());

				if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
				{
					endDtObj.val($(this).val());
				}

				endDtObj.datetimepicker("setOptions", { minDate : strtDt, value : endDtObj.val() });

			});
		});


		$.each(jQuery(".datetimepicker_endDt"), function(i, obj){
			jQuery(obj).datetimepicker({
				timepicker : false,
				format : "Y.m.d",
				defaultDate : "",
				defaultTime : "00:00",
				scrollMonth : false,
				scrollTime : false,
				todayButton: false,
			}).on('change',function(e){
				var strtDt   = new Date($(this).val());
				var endDtObj = jQuery(".datetimepicker_endDt");
				var endDt	 = new Date(endDtObj.val());

				if (strtDt.getTime() / (1000 * 3600 * 24) > endDt.getTime() / (1000 * 3600 * 24))
				{
					endDtObj.val($(this).val());
				}

				endDtObj.datetimepicker("setOptions", { minDate : strtDt, value : endDtObj.val() });

			});
		});


		var date = new Date();
		var strtObj = jQuery(".datetimepicker_strtDt");
		strtObj.datetimepicker("setOptions", { value : date_format(beforeMonth) });

		var endObj = jQuery(".datetimepicker_endDt");
		endObj.datetimepicker("setOptions", { value : date_format(afterMonth)});
	}

	var date_format = function(trgtPerid){
		var today = new Date();
		var year  = today.getFullYear();
		var month = today.getMonth() + 1;
		var day   = today.getDate();

		var tempDay = new Date(year, month - 1, 1);


		tempDay.setMonth(tempDay.getMonth() - (trgtPerid - 1));
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

		return trgtDay = trgtYear + "." + trgtMonth  + "." + trgtDay;
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
		fileFrmAjax: fn_ajax_file_data,
		fileFrm: fn_ajax_file,
		inputFileInit: fn_input_file_init,
		setPopup : fn_set_popup,
		checkMaxlength : fn_check_maxlength,
		checkContString : check_cont_string,
		listPaging : fn_paging,
		morePaging : fn_more_paging,
		setFormData: fn_list_frm_set_data,
		pad: fn_pad,
		checkUrl: fn_check_url,
		initCalendar: fn_init_calendar,
		niceCertification : fn_nice_certification,
		searchPostCode : fn_postData,
		setCalendar: fn_set_calendar,
		setCalendarInit : fn_calendar,

		//json형식의 데이터를 문자열로 변환 후 자바 컨트롤러에서 @RequestBody BaseDTO baseDto 로 받는다
		jsonAjax : fn_json_ajax_data,

		// 이메일_체크
		getEmailChk : fn_email_validation_chk,

	}
}());