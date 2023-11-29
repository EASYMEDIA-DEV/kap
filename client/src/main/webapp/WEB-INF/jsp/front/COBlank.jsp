<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<spring:eval var="siteName" expression="@environment.getProperty('app.site.name.kr')" />
	<title>${siteName}</title>
	<script type="text/javascript">
		//<![CDATA[
		function init()
		{
			<c:if test="${not empty msg}">
				alert("${msg}");
			</c:if>
			<c:choose>
				<c:when test="${fn:indexOf(url, 'javascript:') > 0}">
					location.href = "${url}";
				</c:when>
				<c:otherwise>
					location.replace("${url}");
				</c:otherwise>
			</c:choose>
		}
		//]]>
	</script>
</head>
<body onload="init();">

</body>
</html>