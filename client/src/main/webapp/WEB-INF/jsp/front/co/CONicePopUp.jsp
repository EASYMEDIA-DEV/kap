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
		<input type="hidden" id="mobileno" name="mobileno"  value="${rtnData.mobileno}"/>
		<input type="hidden" id="ci" name="ci"  value="${rtnData.ci}"/>
		<input type="hidden" id="resultcode" name="resultcode"  value="${rtnData.resultcode}"/>
		<input type="hidden" id="param1" name="param1"  value="${rtnData.receivedatass.paramsOne}"/>
		<input type="hidden" id="param2" name="param2"  value="${rtnData.receivedatass.paramsTwo}"/>
		<input type="hidden" id="param3" name="param3"  value="${rtnData.receivedatass.paramsThree}"/>
		<input type="hidden" id="param4" name="param4"  value="${rtnData.receivedatass.paramsFour}"/>
		<input type="hidden" id="param5" name="param5"  value="${rtnData.receivedatass.paramsFive}"/>
	</form>

	<script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script> <%-- 2024-04-01 정보 업데이트 불가능한 회원 비밀빈호 찾기 회피기동 --%>

	<script type="text/javascript">
			if('${rtnData.resultcode}' == "0000") {
				if(document.getElementById("param1").value == "ciChk") {
					if(document.getElementById("param2").value != document.getElementById("ci").value) {
						/* 2024-04-01 정보 업데이트 불가능한 회원 비밀빈호 찾기 회피기동 s */
						if(decodeURI(document.getElementById("param3").value) == document.getElementById("name").value &&
								document.getElementById("param4").value == document.getElementById("mobileno").value
						) {
							alert("본인인증에 성공하였습니다.");
							window.opener.name = "nicePop";
							document.form.target = "nicePop";
							document.form.action = "${rtnData.receivedatass.redirectUrl}";
							document.form.submit();
						} else {
							alert("홈페이지에서 비밀번호 찾기 시 입력한 이름, 휴대폰번호와 동일한 정보로 본인인증 해주셔야 인증이 가능합니다.");
						}
						/* 2024-04-01 정보 업데이트 불가능한 회원 비밀빈호 찾기 회피기동 e */
					} else {

						alert("본인인증에 성공하였습니다.");

						window.opener.name = "nicePop";
						document.form.target = "nicePop";
						document.form.action = "${rtnData.receivedatass.redirectUrl}";
						document.form.submit();
					}


				}else if(document.getElementById("param1").value == "updChk") {
					if(document.getElementById("param2").value == document.getElementById("birthdate").value &&
						decodeURI(document.getElementById("param3").value) == document.getElementById("name").value &&
					   document.getElementById("param4").value == document.getElementById("mobileno").value
					) {
						alert("본인인증에 성공하였습니다.");
						window.opener.name = "nicePop";
						document.form.target = "nicePop";
						document.form.action = "${rtnData.receivedatass.redirectUrl}";
						document.form.submit();
					} else {
						alert("본인인증에 실패하였습니다.");
					}
				} else {
					alert("본인인증에 성공하였습니다.");
					window.opener.name = "nicePop";
					document.form.target = "nicePop";
					document.form.action = "${rtnData.receivedatass.redirectUrl}";
					document.form.submit();

				}
				window.close();

			} else {
				alert("본인인증에 실패하였습니다.");
				window.close();
			}


	</script>

	</body>
</html>