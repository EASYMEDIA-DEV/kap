<!doctype html>
<html lang="ko">
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
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
    <script type="text/javascript" src="/common/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="/common/js/gsap.min.js"></script>
    <script type="text/javascript" src="/common/js/ScrollTrigger.min.js"></script>
    <script type="text/javascript" src="/common/js/swiper.min.js"></script>
    <script type="text/javascript" src="/common/js/script.js"></script>
</head>
<script>

    function cmptnPop() {
        // method 1 (새 윈도우 창 열어서 프린트 후 닫기)
        var popUrl = "FO-PC-MYP-02-013.html";
        var popOption = "top=10, left=10, width=1080, height=1528, scrollbars=no, status=no, menubar=no, toolbars=no, resizable=no";
        var myWindow = window.open(popUrl, popOption);
        myWindow.document.close();
        myWindow.focus();

        myWindow.onafterprint = function () { //프린터 출력 후 이벤트
            myWindow.close();
        }

        myWindow.print();
    };

</script>
<body<%-- onload="cmptnPop();"--%>>
<%
    Date nowDate = new Date();
    pageContext.setAttribute("nowDate", nowDate);//현재 시간
%>

<fmt:formatDate pattern="yyyy" value="${nowDate}" var="nowYear" />
<fmt:formatDate pattern="MM" value="${nowDate}" var="nowMonth" />
<fmt:formatDate pattern="dd" value="${nowDate}" var="nowDay" />
<div class="print-popup" style="width: 1080rem; height: 1528rem; padding: 76rem 50rem 50rem;"><!-- style 속성은 디자인 확인용으로 추가. 추후에 삭제 필요 -->
    <div class="print-wrap">
        <p class="print-code">${rtnData.crtfctNo}</p>
        <p class="print-title">수료증</p>

        <div class="def-list-w">
            <div class="def-list">
                <p class="tit f-head">소속</p>
                <p class="txt f-sub-head">${loginMap.cmpnNm}</p>
            </div>
            <div class="def-list">
                <p class="tit f-head">성명</p>
                <p class="txt f-sub-head">${loginMap.name}</p>
            </div>
            <div class="def-list">
                <p class="tit f-head">과정분류</p>
                <p class="txt f-sub-head">${rtnData.prntCdNm}・${rtnData.ctgryCdNm} <c:if test="${not empty rtnData.cbsnCdNm}">(${rtnData.cbsnCdNm})</c:if></p>
            </div>
            <div class="def-list">
                <p class="tit f-head">과정명</p>
                <p class="txt f-sub-head">${rtnData.nm}</p>
            </div>
            <div class="def-list">
                <p class="tit f-head">교육기간</p>
                <p class="txt f-sub-head">
                    ${ empty rtnData.edctnStrtDtm ? '-' : kl:convertDate(rtnData.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }
                    ~
                    ${ empty rtnData.edctnEndDtm ? '-' : kl:convertDate(rtnData.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }</p>
            </div>
            <div class="def-list">
                <p class="tit f-head">수료일자</p>
                <p class="txt f-sub-head">${ empty rtnData.cmptnDtm ? '-' : kl:convertDate(rtnData.cmptnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '-') }</p>
            </div>
        </div>

        <p class="print-con-txt">위 사람은 현대자동차그룹 후원으로 당 재단에서 실시한 교육/세미나 과정을 이수하였기에 이 증서를 수여합니다.</p>
        <p class="print-date">${nowYear}년  ${nowMonth}월  ${nowDay}일</p>

        <div class="print-signature-area">
            <p class="txt">자동차부품산업진흥재단 이사장 안정구</p>
            <img src="/common/images/img-signature.png" alt="이사장 직인">
        </div>
    </div>
</div>
</body>
</html>
