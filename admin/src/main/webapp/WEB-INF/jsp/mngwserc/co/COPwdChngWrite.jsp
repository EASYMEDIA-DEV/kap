<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
  	<head>
		<spring:eval var="siteName" expression="@environment.getProperty('app.site.name')" />
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
          			<div class="container container-xs" data-controller="controller/co/COLgnCtrl" style="position:absolute; top:50%; left:50%; transform:translate(-50%, -50%); width:100%" >
						<form class="card b0 form-validate" id="frmPwdChng" name="frmPwdChng" method="post" action="" autocomplete="off" data-login-id="${ tmpLgnMap.id }">
							<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
              				<div class="card-offset pb0">
                				<div class="card-offset-item text-right">
                  					<div class="btn btn-success btn-circle btn-lg hidden"><em class="ion-checkmark-round"></em></div>
                				</div>
              				</div>
              				<div class="card-heading">
                				<div class="card-title text-center mt-xl"><img src="/common/images/logo.gif" alt="로고" title="로고" /></div>
								<h5 class="text-center text-bold">비밀번호 변경안내</h5>
								<div class="text-left text-sm text-bold">
									<c:choose>
										<c:when test="${ tmpLgnVO.respCd eq '1210' }">
											* 임시비밀번호를 발급받으신 경우 안전한 관리자 이용을 위해 비밀번호 변경이 필요합니다.
										</c:when>
										<c:when test="${ tmpLgnVO.respCd eq '1310' }">
											* 임시비밀번호를 발급받으신 경우 안전한 관리자 이용을 위해 비밀번호 변경이 필요합니다.
										</c:when>
										<c:when test="${ tmpLgnVO.respCd eq '1410' }">
											* 개인정보 보호를 위해 3개월마다 비밀번호를 변경해야 이용이 가능합니다.
										</c:when>
									</c:choose>
								</div>
              				</div>
              				<div class="card-body">
                				<div class="mda-form-group float-label mda-input-group">
                 	 				<div class="mda-form-control">
                    					<input type="password" class="form-control" id="password" name="password" title="기존 비밀번호" placeholder="기존 비밀번호" maxlength="15" autocomplete="off" oninput="this.value=this.value.replace(/[\s]/,'');"/>
                    					<div class="mda-form-control-line"></div>
                  					</div>
                  					<span class="mda-input-group-addon"><em class="ion-ios-locked-outline icon-lg"></em></span>
                				</div>
                				<div class="mda-form-group float-label mda-input-group">
                  					<div class="mda-form-control">
                    					<input type="password" class="form-control passChk " id="newPassword" name="newPassword" title="신규 비밀번호" placeholder="신규 비밀번호" maxlength="15" autocomplete="off" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');"/>
                    					<div class="mda-form-control-line"></div>
                  					</div>
                  					<span class="mda-input-group-addon"><em class="ion-ios-locked-outline icon-lg"></em></span>
                				</div>
                				<div class="mda-form-group float-label mda-input-group">
                  					<div class="mda-form-control">
                    					<input type="password" class="form-control passEqual" id="passwordConfirm" name="passwordConfirm" title="신규 비밀번호 확인" placeholder="신규 비밀번호 확인" maxlength="15" autocomplete="off" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');"/>
                    					<div class="mda-form-control-line"></div>
                  					</div>
                  					<span class="mda-input-group-addon"><em class="ion-ios-locked-outline icon-lg"></em></span>
                				</div>
                				<div class="card-body bg-gray-lighter text-left text-sm">
									<c:choose>
										<c:when test="${ tmpLgnVO.respCd eq '1210' }">
											• 개인정보 및 관리자 페이지의 각종 정보를 위해 반드시 비밀번호를 변경해야 합니다.<br />
										</c:when>
										<c:when test="${ tmpLgnVO.respCd eq '1310' }">
											• 개인정보 및 관리자 페이지의 각종 정보를 위해 반드시 비밀번호를 변경해야 합니다.<br />
										</c:when>
										<c:when test="${ tmpLgnVO.respCd eq '1410' }">
											• 개인정보 및 관리자 페이지의 각종 정보를 위해 90일마다 반드시 비밀번호를 변경해야 합니다.<br />
										</c:when>
									</c:choose>
									• 특수문자,숫자,영문 3가지 이상 조합하여 8자리 이상 ~ 15자리 이하로 입력이 가능합니다. <br />
									(특수 문자 중 다음 문자는 사용하실 수 없습니다.(& < > : ; ? ' " 공백))
								</div>
              				</div>
              				<button type="submit" class="btn btn-primary btn-flat">변경하기</button>
            			</form>
          			</div>
        		</div>
      		</div>
    	</div>
    	
    	<div class="ajaxLoader" id="indi" style="display:none;">
			<div class="loader-inner line-spin-fade-loader">
				<div></div><div></div><div></div><div></div>
				<div></div><div></div><div></div><div></div>
			</div>
        </div>
        
    	<script type="text/javascript" src="/common/js/lib/require.js" data-main="/common/js/site"></script>
	</body>
</html>