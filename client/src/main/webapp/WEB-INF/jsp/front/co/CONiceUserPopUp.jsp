<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
<!--함수 공통-->
<script type="text/javascript" src="/common/js/controller/co/COCmmCtrl.js"></script>
<!--메시지 공통-->
<script type="text/javascript" src="/common/js/controller/co/COMsgCtrl.js"></script>
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
	<script type="text/javascript">

		if('${rtnData.resultcode}' == "0000") {
			if(document.getElementById("param1").value != document.getElementById("ci").value) {
				alert("본인인증에 실패하였습니다.");


			}else {
				alert("본인인증에 성공하였습니다.");
				opener.document.getElementById("name").innerText = '${rtnData.name}';
				opener.document.getElementById("birth").innerText = '${rtnData.birthdate.substring(0,4)}' + "-" + '${rtnData.birthdate.substring(4,6)}' + "-" + '${rtnData.birthdate.substring(6)}';
				opener.document.getElementById("gndr").innerText = '${rtnData.gender == "1" ? "남" : "여"}';
				opener.document.getElementById("hpNo").innerText = '${rtnData.mobileno.substring(0,3)}'+"-"+'${rtnData.mobileno.substring(3,7)}'+"-"+'${rtnData.mobileno.substring(7)}';
				if('${rtnData.receivedatass.paramsTwo}' == "compChg") {
					opener.document.getElementById("partTypeChg").value = "new";
					opener.document.getElementById("partDtl").style.display = "block";
					opener.document.getElementById("buseoHide").style.display = "none";
					opener.document.getElementById("gikHide").style.display = "none";
					opener.document.getElementById("bsnmNos").value = opener.document.getElementById("bsnmNo").value;
					opener.document.getElementById("ctgryCdIns").value = opener.document.getElementById("ctgryCd").value;
					opener.document.getElementById("sizeCdIns").value = opener.document.getElementById("sizeCd").value
					opener.document.getElementById("popStbsmDt").value = opener.document.getElementById("stbsmDt").value
					opener.document.getElementById("cmpnTel").value = opener.document.getElementById("telNo").value
					opener.document.getElementById("cmpnZipcode").value = opener.document.getElementById("zipcode").value
					opener.document.getElementById("cmpnBscAddr").value = opener.document.getElementById("bscAddr").value
					opener.document.getElementById("cmpnDtlAddr").value = opener.document.getElementById("dtlAddr").value
					opener.document.getElementById("popSlsPmt").value = opener.document.getElementById("slsPmt").value
					opener.document.getElementById("popslsYear").value = opener.document.getElementById("slsYear").value
					opener.document.getElementById("popMpleCnt").value = opener.document.getElementById("mpleCnt").value
					opener.document.getElementById("popMjrPrdct1").value = opener.document.getElementById("mjrPrdct1").value
					opener.document.getElementById("popMjrPrdct2").value = opener.document.getElementById("mjrPrdct2").value
					opener.document.getElementById("popMjrPrdct3").value = opener.document.getElementById("mjrPrdct3").value

					opener.document.getElementById("popQlty5StarCd").value = opener.document.getElementById("qlty5StarCd").value
					opener.document.getElementById("popQlty5StarYear").value = opener.document.getElementById("qlty5StarYear").value
					opener.document.getElementById("popPay5StarCd").value = opener.document.getElementById("pay5StarCd").value
					opener.document.getElementById("popPay5StarYear").value = opener.document.getElementById("pay5StarYear").value
					opener.document.getElementById("popTchlg5StarCd").value = opener.document.getElementById("tchlg5StarCd").value
					opener.document.getElementById("popTchlg5StarYear").value = opener.document.getElementById("tchlg5StarYear").value

					var list1 = [];
					list1.push("");
					list1.push(opener.document.getElementById("nm1").value);
					list1.push(opener.document.getElementById("score1").value);
					list1.push(opener.document.getElementById("year1").value);
					list1.push(opener.document.getElementById("crtfnCmpnNm1").value);

					var list2 = [];
					list2.push("");
					list2.push(opener.document.getElementById("nm2").value);
					list2.push(opener.document.getElementById("score2").value);
					list2.push(opener.document.getElementById("year2").value);
					list2.push(opener.document.getElementById("crtfnCmpnNm2").value);

					var list3 = [];
					list3.push("");
					list3.push(opener.document.getElementById("nm3").value);
					list3.push(opener.document.getElementById("score3").value);
					list3.push(opener.document.getElementById("year3").value);
					list3.push(opener.document.getElementById("crtfnCmpnNm3").value);

					opener.document.getElementById("popSqInfoList1").value = list1;
					opener.document.getElementById("popSqInfoList2").value = list2;
					opener.document.getElementById("popSqInfoList3").value = list3;
					var openerBsnmNoNumElements = opener.document.getElementById("bsnmNo").value;
					var substringValue = openerBsnmNoNumElements.substring(0, 3) + "-" + openerBsnmNoNumElements.substring(3, 5) + "-" + openerBsnmNoNumElements.substring(5);

					opener.document.getElementsByClassName("bsnmNoNum").innerText  = substringValue;
					opener.document.getElementById("formMemCd").value = "CP";

					if(opener.$(".new").css('display') == 'block') {
						opener.$(".cmpnNm").text(opener.$(".cmpn_nm_new").val());
						opener.$(".rprsntNm").text(opener.$(".rprsnt_nm").val());
						opener.$(".ctgryNm").text(opener.$("#ctgryCd").val()=='COMPANY01001' ? '1차' : '2차');
						opener.$(".addrNm").text(opener.$("#bscAddr").val() + " " + opener.$("#dtlAddr").val());
						opener.$(".bsnmNoNum").text(opener.$("#bsnmNo").val().substring(0, 3) + "-" + opener.$("#bsnmNo").val().substring(3, 5) + "-" + opener.$("#bsnmNo").val().substring(5));

						opener.$(".buseo").text()
						opener.$(".gikgub").text();
						opener.$(".cmpnNm").val(opener.$(".cmpn_nm_new").val());
						opener.$(".rprsntNm").val(opener.$(".rprsnt_nm").val());

						let buseonDtl = opener.$("#deptDtlNmOld").val() == "" ? ' ' : "(" + opener.$("#deptDtlNm").val() +")";
						opener.$(".buseo").text(opener.$("#deptCd option:selected").text() +" " + buseonDtl);
						let gikgubDtl = opener.$("#pstnCd option:selected").text() == '기타' ? "(" + opener.$(".pstnNm").val() +")" : '';
						opener.$(".gikgub").text(opener.$("#pstnCd option:selected").text() + " " + gikgubDtl);
					} else {
						let buseonDtl = opener.$("#deptDtlNmOld").val() == "" ? '' : "(" + opener.$("#deptDtlNmOld").val() +")";
						opener.$(".buseo").text(opener.$("#deptCdOld option:selected").text() +" " + buseonDtl);
						let gikgubDtl = opener.$("#pstnCdOld option:selected").text() == '기타' ? "(" + opener.$(".pstnNmOld").val() +")" : '';
						opener.$(".gikgub").text(opener.$("#pstnCdOld option:selected").text() + " " + gikgubDtl);
						opener.$(".bsnmNoNum").text(opener.$("#bsnmNo").val().substring(0, 3) + "-" + opener.$("#bsnmNo").val().substring(3, 5) + "-" + opener.$("#bsnmNo").val().substring(5));
						opener.$(".cmpnNm").text(opener.$(".cmpn_nm").text());
						opener.$(".rprsntNm").text(opener.$(".rsNm").text());
						opener.$(".ctgryNm").text(opener.$(".gubun ").text());
						opener.$(".addrNm").text(opener.$(".addr").text());
						opener.$(".cmpnNm").val(opener.$(".cmpn_nm").text());
						opener.$(".rprsntNm").val(opener.$(".rsNm").text());
					}
				}
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