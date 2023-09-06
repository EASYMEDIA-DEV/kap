<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!doctype html>
<html lang="ko">
	<head>
		<spring:eval var="siteName" expression="@environment.getProperty('app.site.name')" />
		<spring:eval var="userDomain" expression="@environment.getProperty('app.user-domain')" />
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<title>${siteName}</title>
		<link rel="shortcut icon" href="/common/images/favicon.ico" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no,maximum-scale=1.0,minimum-scale=1.0,target-densitydpi=medium-dpi" />
		<meta name="keywords" content="각 페이지마다 유니크한 KOLON Group keywords"/>
		<meta name="description" content="각 페이지마다 유니크한 KOLON Group description"/>
		<meta property="og:site_name" content="KOLON Industries" id="og-sitename-value"/>
		<meta property="og:type" content="website" id="og-type-value"/>
		<meta property="og:url" content="URL" id="og-url-value"/>
		<meta property="og:title" content="KOLON Industries" id="og-title-value"/>
		<meta property="og:description" content="KOLON Industries 홈페이지에 방문하신 것을 환영합니다." id="og-description-value"/>
		<meta property="og:image" content="img URL" id="og-image-value"/>
		<link rel="apple-touch-icon-precomposed" href="/common/images/ios-favicon.ico" />
		<link rel="stylesheet" href="/common/css/fonts.css" />
		<link rel="stylesheet" href="/common/css/common.css" />
		<link rel="stylesheet" href="/common/css/style_user.css" />
	</head>
	<body>
		<div id="wrap">
			<div class="etc_page">
				<p class="logo"></p>
				<div class="no_srch_area">
					<div class="no_srch_div">
						<h1 class="heading_title_Bold white_txt">잘못된 접근입니다.</h1>
					</div>
					<div class="btn_wrap">
						<a href="${ empty firstUrl ? '/' : firstUrl}" class="color_btn"><span>메인으로 이동</span></a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>