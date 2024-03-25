<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>


<div id="wrap" class="mypage evaluation">
    <div class="cont-wrap">
        <div class="inner">
            <div class="sub-top-vis-area">
                <div class="page-tit-area t-align-center">
                    <p class="page-tit f-large-title">
                        <span class="for-move">컨설팅 만족도 설문 조사 참여하기</span>
                    </p>
                </div>
            </div>

            <div class="inner-con-box evaluation"><!-- evaluation: 평가하기 대기 페이지 -->
                <div class="cont-for-padding">
                    <p class="f-title1">${rtnData.bsnYear} ${rtnData.cnstgNm} </p>
                    <div class="sort-label-area">
                        <p class="label"><span>${rtnData.cnstgNm}</span></p>
                    </div>

                    <div class="def-list-w">
                        <div class="def-list">
                            <p class="tit f-head">신청업종/분야</p>
                            <p class="txt f-sub-head">
                                <c:if test="${fn:contains(rtnData.cbsnCd, 'NON')}">비금속분야- ${rtnData.cbsnNm}</p></c:if>
                            <c:if test="${fn:contains(rtnData.cbsnCd, 'METAL')}">금속분야- ${rtnData.cbsnNm}</p></c:if>
                            <c:if test="${fn:contains(rtnData.cbsnCd, 'INDUS')}">기타</p></c:if>

                        </div>
                        <div class="def-list">
                            <p class="tit f-head">위원</p>
                            <p class="txt f-sub-head">${survInfo.cmssrNm}</p>
                        </div>
                        <div class="def-list">
                            <p class="tit f-head">지도기간</p>
                            <p class="txt f-sub-head">2023-01-01 ~ 2023-01-01</p>
                        </div>
                        <div class="def-list">
                            <p class="tit f-head">총 문항수</p>
                            <p class="txt f-sub-head">${survInfo.qstnCnt}</p>
                        </div>
                        <div class="def-list">
                            <p class="tit f-head">예상 응답시간</p>
                            <p class="txt f-sub-head">${survInfo.rspnMm}</p>
                        </div>
                    </div>

                    <div class="noti-txt-w">
                        ${survInfo.cntn}
                    </div>
                </div>

                <div class="page-bot-btn-sec">
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" href="/my-page/consulting/detail?detailsKey=${rtnData.cnstgSeq}"><span>신청내역 상세</span></a>
                            <a class="btn-solid small black-bg" href="./surveyStep2?detailsKey=${rtnData.cnstgSeq}"><span>참여하기</span></a>
                        </div>
                        <div class="btn-set">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

