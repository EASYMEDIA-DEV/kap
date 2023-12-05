<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
	</head>
	<body>
	<form name="form" id="form"  method="post" action="${rtnData.receivedata}">
		<input type="hidden" id="name" name="name"  value="${rtnData.name}"/>
		<input type="hidden" id="birthdate" name="birthdate"  value="${rtnData.birthdate}"/>
		<input type="hidden" id="gender" name="gender"  value="${rtnData.gender}"/>
		<input type="hidden" id="nationalinfo" name="nationalinfo"  value="${rtnData.nationalinfo}"/>
		<input type="hidden" id="mobile_no" name="mobile_no"  value="${rtnData.mobile_no}"/>
		<input type="hidden" id="ci" name="ci"  value="${rtnData.ci}"/>
		<input type="hidden" id="resultcode" name="resultcode"  value="${rtnData.resultcode}"/>
		<input type="hidden" id="types" name="types"  value="1"/>
	</form>
	<script type="text/javascript">
			if('${rtnData.resultcode}' == "0000") {
				alert("본인인증에 성공하였습니다.");

				window.opener.name = "nicePop";
				document.form.target = "nicePop";
				document.form.action = "${rtnData.receivedata}";
				document.form.submit();

				window.close();

			} else {
				alert("본인인증에 실패하였습니다.");
				window.close();
			}


	</script>

	</body>
</html>