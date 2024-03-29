<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>KAP</title>
    <link rel="shortcut icon" href="/common/images/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="keywords" content="KAP"/>
    <meta name="description" content="KAP"/>
    <meta property="og:site_name" content="KAP" id="og-sitename-value"/>
    <meta property="og:type" content="website" id="og-type-value"/>
    <meta property="og:url" content="URL" id="og-url-value"/>
    <meta property="og:title" content="NOTICE | KAP" id="og-title-value"/>
    <meta property="og:description" content="KAP 홈페이지에 방문하신 것을 환영합니다." id="og-description-value"/>
    <meta property="og:image" content="img URL" id="og-image-value"/>
    <meta name="format-detection" content="telephone=no, address=no, email=no" /><!-- 2024-02-05 meta태그 추가 -->
    <!-- <meta name="twitter:url" content="URL" id="twitter-url-value"/>
    <meta name="twitter:title" content="Main" id="twitter-title-value"/>
    <meta name="twitter:image" content="img URL" />
    <meta name="twitter:description" content="KAP 홈페이지에 방문하신 것을 환영합니다." id="twitter-discription-value"/> -->
    <link rel="stylesheet" href="/common/css/swiper.css" />
    <link rel="stylesheet" href="/common/css/fonts.css" />
    <link rel="stylesheet" href="/common/css/common.css" />
    <link rel="stylesheet" href="/common/css/style.css" />
    <script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="/common/js/gsap.min.js"></script>
    <script type="text/javascript" src="/common/js/ScrollTrigger.min.js"></script>
    <script type="text/javascript" src="/common/js/swiper.min.js"></script>
    <script type="text/javascript" src="/common/js/script.js"></script>
</head>
<body>
<div id="wrap">
    <!-- content 영역 start -->
    <!--
        @ ERROR: err 클래스 추가
        @ 사이트점검안내: site 클래스 추가
    -->
    <div class="etc-wrap err">
        <!-- @ ERROR, 사이트점검안내: etc-inner 클래스 사용 -->
        <div class="etc-inner">
            <h4 class="tit f-xlarge-title">ERROR</h4>
            <p class="txt f-head">요청하신 페이지를 찾을 수 없습니다.</p>
            <p class="info f-body1">
                사이트 이용에 불편을 드려 죄송합니다. <br class="only-mobile"/>페이지 주소가 변경 혹은 삭제되었거나
                <br class="only-pc"/>내부 서버 오류로 인해 <br class="only-mobile"/>요청하신 페이지를 실행하는데 문제가 발생했습니다.
                <br/>잠시 후 다시 시도해주시기 바랍니다.
            </p>
        </div>
    </div>
    <!-- content 영역 end -->
</div>
</body>
</html>
