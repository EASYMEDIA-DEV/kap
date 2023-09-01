<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html lang="ko">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <title>FileUploader Callback</title>
	</head>
	<body>
	    <script type="text/javascript">
	    	window.parent.CKEDITOR.tools.callFunction("${CKEditorFuncNum}", "${webPath}");
		</script>
	</body>
</html>