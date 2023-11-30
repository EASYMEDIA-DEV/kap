<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!DOCTYPE html>
<html lang="en">
  	<head>
		<spring:eval var="siteName" expression="@environment.getProperty('app.site.name')" />
    	<meta charset="utf-8" />
    	<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no,maximum-scale=1.0,minimum-scale=1.0,target-densitydpi=medium-dpi" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
						<div class="card-title text-center mb-xl">
							<img src="/common/images/icon-logo-wht.svg" alt="로고" title="로고" />
						</div>
            			<form class="card b0 form-validate" id="frmLogin" name="frmLogin" method="post" action="">
            				<input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
              				<div class="card-offset pb0">
			                	<div class="card-offset-item text-right hidden">
			                  		<div class="btn btn-success btn-circle btn-lg"><em class="ion-checkmark-round"></em></div>
			                	</div>
			              	</div>
              				<div class="card-body">
				                <div class="mda-form-group float-label mda-input-group">
				                  	<div class="mda-form-control">
				                    	<input type="text" class="form-control idChk" id="id" name="id" title="아이디" placeholder="아이디" maxlength="12" oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');"/>
				                    	<div class="mda-form-control-line"></div>
				                  	</div>
				                  	<span class="mda-input-group-addon"><em class="ion-android-person icon-lg"></em></span>
				                </div>
				                <div class="mda-form-group float-label mda-input-group">
				                  	<div class="mda-form-control">
				                    	<input type="password" class="form-control" id="password" name="password" title="비밀번호" placeholder="비밀번호" autocomplete="off" maxlength="15" oninput="this.value=this.value.replace(/[\sㄱ-ㅎ|ㅏ-ㅣ|가-힣<c:out value="&<>:;?\'\""/>]/g,'');"/>
				                    	<div class="mda-form-control-line"></div>
				                  	</div>
				                  	<span class="mda-input-group-addon"><em class="ion-ios-locked-outline icon-lg"></em></span>
				                </div>
				                <div class="card-body bg-gray-lighter text-left text-sm">
				                	• 비밀번호 5회 오류 시 계정 사용이 제한됩니다.&nbsp;&nbsp;<br>&nbsp;&nbsp;(이전 입력 오류 횟수 누적)<br>
									• 로그인 관련 문의사항은 관리자에게 문의하세요.
								</div>
							</div>
              				<button type="submit" class="btn btn-primary btn-flat">로그인</button>
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