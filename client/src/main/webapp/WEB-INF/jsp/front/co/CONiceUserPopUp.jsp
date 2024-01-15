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
						var formObj = opener.$("formUserSubmit").serialize();
						jQuery.ajax({
							url : "/my-page/member/intrduction/update-company-chg",
							type : "post",
							timeout: 30000,
							data : {
								"bsnmNo" :opener.$("#bsnmNos").val() ,
								"mpaUserDto.name" :opener.$("#formName").val() ,
								"partTypeChg" :opener.$("#partTypeChg").val() ,
								"mpePartsCompanyDTO.cmpnNm" :opener.$("#cmpnNm").val() ,
								"mpePartsCompanyDTO.rprsntNm" :opener.$("#rprsntNm").val() ,
								"mpePartsCompanyDTO.telNo" :opener.$("#cmpnTel").val() ,
								"mpePartsCompanyDTO.zipcode" :opener.$("#cmpnZipcode").val() ,
								"mpePartsCompanyDTO.bscAddr" :opener.$("#cmpnBscAddr").val() ,
								"mpePartsCompanyDTO.dtlAddr" :opener.$("#cmpnDtlAddr").val() ,
								"bsnmChk" :opener.$("#bsnmChk").val() ,
								"mpePartsCompanyDTO.ctgryCd" :opener.$("#ctgryCd").val() ,
								"mpePartsCompanyDTO.sizeCd" :opener.$("#sizeCd").val() ,
								"mpePartsCompanyDTO.stbsmDt" :opener.$(".stbsmDt").val() ,
								"mpePartsCompanyDTO.slsPmt" :opener.$(".slsPmt").val() ,
								"mpePartsCompanyDTO.slsYear" :opener.$(".slsYear").val() ,
								"mpePartsCompanyDTO.mpleCnt" :opener.$(".mpleCnt").val() ,
								"mpePartsCompanyDTO.mjrPrdct1" :opener.$(".mjrPrdct1").val() ,
								"mpePartsCompanyDTO.mjrPrdct2" :opener.$(".mjrPrdct2").val() ,
								"mpePartsCompanyDTO.mjrPrdct3" :opener.$(".mjrPrdct3").val() ,
								"mpePartsCompanyDTO.qlty5StarCd" :opener.$(".qlty5StarCd").val() ,
								"mpePartsCompanyDTO.qlty5StarYear" :opener.$(".qlty5StarYear").val() ,
								"mpePartsCompanyDTO.pay5StarCd" :opener.$(".pay5StarCd").val() ,
								"mpePartsCompanyDTO.pay5StarYear" :opener.$(".pay5StarYear").val() ,
								"mpePartsCompanyDTO.tchlg5StarCd" :opener.$(".tchlg5StarCd").val() ,
								"mpePartsCompanyDTO.tchlg5StarYear" :opener.$(".tchlg5StarYear").val() ,
								"mpePartsCompanyDTO.sqInfoList1" :opener.$(".sqInfoList1").val() ,
								"mpePartsCompanyDTO.sqInfoList2" :opener.$(".sqInfoList2").val() ,
								"mpePartsCompanyDTO.sqInfoList3" :opener.$(".sqInfoList3").val() ,
								"_csrf": opener.$("#csrfKey").val()
							},
							dataType : "json",
							async: false,
							cache : false,
							success : function(data, status, xhr){
								alert("저장 되었습니다.");
							},
							error : function(data, status, xhr){
								alert("오류가 발생했습니다.");
								return false;
							},
							complete : function(){
							}
						});

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