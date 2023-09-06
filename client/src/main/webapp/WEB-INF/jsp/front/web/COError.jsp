<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title></title>
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
		<div class="no_srch_area">
			<div class="no_srch_div">
				<h1 class="heading_title_Bold white_txt">서비스 이용에 불편을 드려 죄송합니다</h1>
				<h2 class="page_body_txt_Medium white_txt">방문하려는 웹페이지의 주소가 잘못 입력되었거나, 변경 또는 삭제되어 요청하신 <br />페이지를 찾을 수 없습니다. 다시 한 번 확인 후 접속하시기 바랍니다.</h2>
			</div>
			<div class="btn_wrap">
				<a href="${ empty firstUrl ? '/' : firstUrl}" class="color_btn"><span>메인으로 이동</span></a>
			</div>
		</div>
	</div>
</div>
</body>
</html>