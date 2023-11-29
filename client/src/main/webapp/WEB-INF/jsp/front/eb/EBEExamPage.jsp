<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">평가하기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box evaluation"><!-- evaluation: 평가하기 대기 페이지 -->
            <div class="cont-for-padding">
                <p class="f-title1">${ rtnData.nm }</p>
                <div class="sort-label-area">
                    <c:set var="ctgryCdNmList" value="${ fn:split(rtnData.ctgryCdNm, '>')}" />
                    <c:forEach var="cdNm" items="${ctgryCdNmList}" varStatus="status">
                        <p class="label"><span>${ cdNm }</span></p>
                    </c:forEach>
                </div>

                <div class="def-list-w">
                    <div class="def-list">
                        <p class="tit f-head">회차</p>
                        <p class="txt f-sub-head">${ rtnData.episdYear }년 ${ rtnData.episdOrd }차</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">업종</p>
                        <p class="txt f-sub-head">${ rtnData.cbsnCdNm }</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">강사</p>
                        <p class="txt f-sub-head">${ rtnData.isttrNm }</p>
                    </div>
                    <div class="def-list">
                        <p class="tit f-head">총 문항수</p>
                        <p class="txt f-sub-head">${ rtnData.qstnCnt }문항</p>
                    </div>
                </div>

                <div class="noti-txt-w">
                    ${ rtnData.smmryCntn }
                </div>
            </div>

            <div class="page-bot-btn-sec">
                <div class="btn-wrap">
                    <div class="btn-set">
                        <a class="btn-solid small black-bg" href="./exam/participation?ptcptSeq=${rtnData.ptcptSeq}"><span>평가 시작하기</span></a>
                    </div>
                    <div class="btn-set">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->