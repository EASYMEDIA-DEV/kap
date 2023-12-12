/*
 * version 0.1.0
 * 사용자 파일 이미지 임시 저장 및 응답 데이터 반환
 */
(function($){
	$.fn.fileUpload = function(config, completeCallBack){
		$(this).change(function(data){
			//파일정보
			var fileName = $(this)[0].files[0].name;
			var fileSize = $(this)[0].files[0].size;
			var fileExtn = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length).toLowerCase();
			var fileValue = $(this).data("value");
			var fileOrd = $(this).data("ord");
			//업로드할 파일 사이즈
			var maxSize = 999999999;
			//업로드할 파일 확장자
			var accept  = "";
			//업로드할 파일 이름 길이
			var maxNameLength  = 50;
			var _this = $(this);
			//파일 업로드 체크
			if($(this).data("maxSize") != undefined && $.trim($(this).data("maxSize")) != "" ){
				maxSize = parseInt($(this).data("maxSize"));
				//파일명 사이즈
				if(fileSize > maxSize || fileSize == 0){
					var msg = msgCtrl.getMsg("fail.file.size");
					cmmCtrl.inputFileInit( $(this) );
					if(fileSize == 0){
						msg = msgCtrl.getMsg("fail.file.no_size");
					}
					alert(msg);
					return false;
				}
			};
			//파일명 확장자 체크
			if($(this).data("accept") != undefined && $.trim($(this).data("accept")) != ""){
				accept  = $(this).data("accept");
				//확장자 체크
				if(accept.indexOf(fileExtn) == -1){
					cmmCtrl.inputFileInit( $(this) );
					alert(msgCtrl.getMsg("fail.file.extn"));
					return false;
				}
			};
			//파일명 길이 체크
			if($(this).data("maxNameLength") != undefined && $.trim($(this).data("maxNameLength")) != "" ){
				orgnFileLength = fileName.substring(1, fileName.lastIndexOf(".")+1).length;
				maxNameLength = parseInt($(this).data("maxNameLength") );
				if(orgnFileLength > maxNameLength){
					cmmCtrl.inputFileInit( $(this) );
					alert(msgCtrl.getMsg("fail.file.length"));
					return false;
				}
			};
			var formObj = $(this).parents("form");
			if(typeof completeCallBack == "function" ){
				var loading 	= config.loading != undefined ? config.loading : true;
				var sync 	= config.sync != undefined ? config.sync : true;

				jQuery.ajax({
					headers: {
						'X-Requested-With': 'XMLHttpRequest',
						'X-CSRF-TOKEN': $('meta[name=X-CSRF-TOKEN]').attr('content')
					},
					url : "/file/upload",
					type : "POST",
					enctype: 'multipart/form-data',
					timeout: 30000,
					data : new FormData(formObj[0]),
					dataType : "json",
					async: sync,
					cache : false,
					contentType: false,
					processData: false,
					beforeSend : function() {
						formObj.data("submitFlag", "Y");
						if (loading) {
							$(".loading-area").stop().fadeIn(100);
						}
					},
					success : function(data, status, xhr) {
						if(data != null && data.length > 0){
							$.each(data, function(i, jsonData){
								jsonData.status = "success";
								jsonData.fieldNm = _this.prop("name");
								if(fileValue != undefined){
									jsonData.fileSeq = fileValue;
								}
								if(fileOrd != undefined){
									jsonData.fileOrd = fileOrd;
								}
							})
						}
						formObj.data("submitFlag", "N");
						_this.data("tempSuccessFileData", data);
						if (completeCallBack) {
							completeCallBack(data);
						}
					},
					error : function(data, status, xhr) {
						formObj.data("submitFlag", "N");
						cmmCtrl.errorAjax(data, status, xhr);

					},
					complete : function() {
						if (loading) {
							$(".loading-area").stop().fadeOut(100);
						}
					}
				});
			}
			return true;
		})
	}
	$.fn.fileInit = function(completeCallBack){
		cmmCtrl.inputFileInit( $(this) );
	}
})(jQuery);