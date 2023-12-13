<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
	</head>
	<body>
	<form name="form" id="form"  method="get" action="${rtnData.receivedatass.redirectUrl}">
		<input type="hidden" id="name" name="name"  value="${rtnData.name}"/>
		<input type="hidden" id="birthdate" name="birthdate"  value="${rtnData.birthdate}"/>
		<input type="hidden" id="gndr" name="gndr"  value="${rtnData.gender}"/>
		<input type="hidden" id="nationalinfo" name="nationalinfo"  value="${rtnData.nationalinfo}"/>
		<input type="hidden" id="mobile_no" name="mobile_no"  value="${rtnData.mobile_no}"/>
		<input type="hidden" id="ci" name="ci"  value="${rtnData.ci}"/>
		<input type="hidden" id="resultcode" name="resultcode"  value="${rtnData.resultcode}"/>
		<input type="hidden" id="param1" name="param1"  value="${rtnData.receivedatass.paramsOne}"/>
		<input type="hidden" id="param2" name="param2"  value="${rtnData.receivedatass.paramsTwo}"/>
		<input type="hidden" id="param3" name="param3"  value="${rtnData.receivedatass.paramsThree}"/>
		<input type="hidden" id="param4" name="param4"  value="${rtnData.receivedatass.paramsFour}"/>
		<input type="hidden" id="param5" name="param5"  value="${rtnData.receivedatass.paramsFive}"/>
		<script type="text/javascript">
			if('${rtnData.resultcode}' == "0000") {
				console.log(document.getElementById("param1").value);
				console.log(document.getElementById("ci").value);
				if(document.getElementById("param1").value != document.getElementById("ci").value) {
					alert("본인인증에 실패하였습니다.");

				}else {
					alert("본인인증에 성공하였습니다.");
					opener.document.getElementById("name").innerText = '${rtnData.name}';
					opener.document.getElementById("birth").innerText = '${rtnData.birthdate.substring(0,4)}' + "-" + '${rtnData.birthdate.substring(4,6)}' + "-" + '${rtnData.birthdate.substring(6)}';
					opener.document.getElementById("gndr").innerText = '${rtnData.gender == "1" ? "남" : "여"}';
					// TODO 휴대폰 임의
					opener.document.getElementById("telNo").innerText = '010-1234-5678';
					<%--opener.document.getElementById("telNo").innerText = '${rtnData.mobile_no.substring(0,3)}' + "-" + '${rtnData.mobile_no.substring(3,7)}' + "-" + '${rtnData.mobile_no.substring(7)}';--%>
				}

				window.close();

			} else {
				alert("본인인증에 실패하였습니다.");
				window.close();
			}


		</script>
	</form>

	</body>
</html>