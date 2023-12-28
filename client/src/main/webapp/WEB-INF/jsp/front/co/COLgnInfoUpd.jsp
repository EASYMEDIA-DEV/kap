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
    <!-- <meta name="twitter:url" content="URL" id="twitter-url-value"/>
    <meta name="twitter:title" content="Main" id="twitter-title-value"/>
    <meta name="twitter:image" content="img URL" />
    <meta name="twitter:description" content="KAP 홈페이지에 방문하신 것을 환영합니다." id="twitter-discription-value"/> -->
    <link rel="stylesheet" href="/common/css/swiper.css" />
    <link rel="stylesheet" href="/common/css/fonts.css" />
    <link rel="stylesheet" href="/common/css/common.css" />
    <link rel="stylesheet" href="/common/css/main.css" />
    <link rel="stylesheet" href="/common/css/style.css" />
    <script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="/common/js/gsap.min.js"></script>
    <script type="text/javascript" src="/common/js/ScrollTrigger.min.js"></script>
    <script type="text/javascript" src="/common/js/Draggable.min.js"></script>
    <script type="text/javascript" src="/common/js/swiper.min.js"></script>
    <script type="text/javascript" src="/common/js/lottie-player.js"></script>
    <script type="text/javascript" src="/common/js/bezier-easing.min.js"></script>
    <script type="text/javascript" src="/common/js/jquery.cookies.js"></script>
    <script type="text/javascript" src="/common/js/script.js"></script>
    <script type="text/javascript" src="/common/js/lib/require.js" data-main="/common/js/site"></script>
    <script type="text/javascript" src="/common/js/controller/co/COCmmCtrl.js"></script>
    <script type="text/javascript" src="/common/js/controller/co/COMsgCtrl.js"></script>

</head>
<body>
<div id="wrap" data-controller="controller/co/COLgnCtrl">
    <input type="hidden" id="authCd" name="authCd" value="${tmpLgnVO.authCd}" />
    <input type="hidden" id="ci" name="ci" value="${rtnDtl.ci}" />
    <!-- content 영역 start -->
    <!--
@ 정보 업데이트 안내 : infoup 클래스 추가
        @ ERROR: err 클래스 추가
        @ 사이트점검안내: site 클래스 추가
    -->
    <form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
        <input type="hidden" id="m" name="m" value="service" />
        <input type="hidden" id="token_version_id" name="token_version_id" value="" />
        <input type="hidden" id="enc_data" name="enc_data" />
        <input type="hidden" id="integrity_value" name="integrity_value" />
    </form>
    <div class="etc-wrap infoup" >
        <!-- @ 정보업데이트안내, ERROR, 사이트점검안내: etc-inner 클래스 사용 -->
        <div class="etc-inner">
            <h4 class="tit f-xlarge-title">정보 업데이트 안내</h4>
            <p class="txt f-body1">본 홈페이지가 개편됨에 따라 고객님의 원활한 홈페이지 이용을 위해 개인정보를 업데이트 해주시기 바랍니다.</p>
            <p class="txt f-body1">저희 자동차부품산업진흥재단은 회원님에게 원활한 서비스를 안정적으로 제공하기 위해 최선을 다하고 있으며, 회원님의 소중한 정보들을 보호하기 위해 노력하고 있습니다.</p>
            <div class="btn-wrap">
                <button class="btn-solid small white-bg"  id="myInfoUpd"><span>휴대폰 본인인증</span></button>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
</div>
</body>
</html>
