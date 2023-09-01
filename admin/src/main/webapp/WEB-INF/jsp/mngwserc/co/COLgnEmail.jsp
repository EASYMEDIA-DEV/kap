<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
  	<head>
		<spring:eval var="siteName" expression="@environment.getProperty('app.site.name')" />
		<spring:eval var="siteProfile" expression="@environment.getProperty('spring.config.activate.on-profile')" />
    	<meta charset="utf-8" />
    	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    	<meta http-equiv="x-ua-compatible" content="IE=edge" />
    	<title>${siteName}</title>
    	<link rel="shortcut icon" href="/common/images/bookmark_logo.ico" />
    	<link rel="stylesheet" href="/bootstrap/vendor/animate.css/animate.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/bootstrap/dist/css/bootstrap.min.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/bootstrap/dist/css/bootstrap-submenu.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/ionicons/css/ionicons.css" />
		<link rel="stylesheet" href="/bootstrap/vendor/select2/dist/css/select2.css" />
    	<link rel="stylesheet" href="/bootstrap/vendor/jquery.bootgrid/dist/jquery.bootgrid.css" />
		<link rel="stylesheet" href="/bootstrap/vendor/loaders.css/loaders.css" />
    	<link rel="stylesheet" href="/bootstrap/css/app.css" />
    	<script type="text/javascript" src="/common/js/lib/jquery/jquery.1.12.4.js"></script>
    	<script type="text/javascript" src="/bootstrap/vendor/bootstrap/dist/js/bootstrap.js"></script>
    	<script type="text/javascript" src="/bootstrap/vendor/bootstrap/dist/js/bootstrap-submenu.js"></script>
    	<script type="text/javascript" src="/common/js/controller/co/COCmmCtrl.js"></script>
		<script type="text/javascript" src="/common/js/controller/co/COMsgCtrl.js"></script>
 	</head>
  	<body id="wrapper">
    	<div class="layout-container" style="height:100%">
      		<div class="page-container bg-blue-grey-900">
        		<div class="container-full">
          			<div class="container container-xs" data-controller="controller/co/COLgnCtrl" style="position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:100%">
            			<form class="card b0 form-validate" id="frmLoginEmail" name="frmLoginEmail" method="post" action="" autocomplete="off">
            				<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
              				<div class="card-offset pb0">
			                	<div class="card-offset-item text-right hidden">
			                  		<div class="btn btn-success btn-circle btn-lg"><em class="ion-checkmark-round"></em></div>
			                	</div>
			              	</div>
              				<div class="card-heading">
			                	<div class="card-title text-center mt-xl"><img src="/common/images/logo.gif" alt="로고" title="로고" /></div>
			              	</div>
              				<div class="card-body">
				                <div class="mda-input-group input-group colorpicker-element">
				                  	<div class="mda-form-control">
										<c:set var="emailAuthNum" value="" />
										<c:choose>
											<c:when test="${ siteProfile ne 'local'  }">
												<c:set var="emailAuthNum" value="" />
											</c:when>
											<c:otherwise>
												<c:set var="emailAuthNum" value="${ tmpEmailAuthNum }" />
											</c:otherwise>
										</c:choose>
										<input type="text" class="form-control numberChk" value="${ emailAuthNum }" name="emailAuthNum" title="인증번호" placeholder="인증번호" maxlength="5" oninput="this.value=this.value.replace(/[^0-9]/gi,'');" />
				                    	<div class="mda-form-control-line"></div>
				                  	</div>
									<span class="input-group-addon bg-transparent b0 pb0" id="authTime">05:00</span>
									<span class="input-group-addon bg-transparent b0 pb0"><button type="button" class="btn btn-default" onclick="location.href='/'">취소</button></span>
				                  	<span class="mda-input-group-addon"><em class="ion-email icon-lg"></em></span>
				                </div>
				                <div class="card-body bg-gray-lighter text-left text-sm mt">
				                	• 이메일로 전송된 인증번호를 5분 안에 입력해 주세요.<br/>
									• 로그인 시 발송되는 인증번호는 관리자계정에 등록하신 이메일 주소에서 확인 가능합니다.<br/>
									• 로그인 관련 문의사항은 관리자에게 문의하세요.
								</div>
							</div>
              				<button type="submit" class="btn btn-primary btn-flat">확인</button>
            			</form>
          			</div>
        		</div>
      		</div>
    	</div>
		<div class="loadingbar" id="indi" style="display:none;">
			<div class="loader-inner line-spin-fade-loader">
				<div></div><div></div><div></div><div></div>
				<div></div><div></div><div></div><div></div>
			</div>
		</div>
		<script type="text/javascript" src="/common/js/lib/require.js" data-main="/common/js/site"></script>
  	</body>
</html>