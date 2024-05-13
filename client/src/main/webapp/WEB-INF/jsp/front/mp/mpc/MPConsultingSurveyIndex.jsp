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
                                <c:choose>
                                    <c:when test="${rtnData.cnstgCd eq 'CONSULT_GB02'}">
                                        ${rtnData.appctnFldNm}
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${fn:contains(rtnData.cbsnCd, 'NON')}">비금속분야- ${rtnData.cbsnNm}</p></c:if>
                                        <c:if test="${fn:contains(rtnData.cbsnCd, 'METAL')}">금속분야- ${rtnData.cbsnNm}</p></c:if>
                                        <c:if test="${fn:contains(rtnData.cbsnCd, 'INDUS')}">기타</p></c:if>
                                    </c:otherwise>
                                </c:choose>
                        </div>
                        <div class="def-list">
                            <p class="tit f-head">위원</p>
                            <p class="txt f-sub-head">${survInfo.cmssrNm}</p>
                        </div>
                        <c:choose>
                            <c:when test="${rtnData.cnstgCd eq 'CONSULT_GB02'}">
                                <!--경영컨설팅-->
                                <div class="def-list">
                                    <p class="tit f-head">방문일</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].vstDt}">
                                                ${rtnData.rsumeList[0].vstDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="def-list">
                                    <p class="tit f-head">킥오프일</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].guideKickfDt}">
                                                ${rtnData.rsumeList[0].guideKickfDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="def-list">
                                    <p class="tit f-head">현황</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].cnstgPscndCdNm && rtnData.rsumeList[0].cnstgPscndDt}">
                                                ${rtnData.rsumeList[0].cnstgPscndCdNm} ${rtnData.rsumeList[0].cnstgPscndDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <!--기술지도-->
                                <div class="def-list">
                                    <p class="tit f-head">초도방문일</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].vstDt}">
                                                ${rtnData.rsumeList[0].vstDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="def-list">
                                    <p class="tit f-head">지도착수일</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].guideBgnDt}">
                                                ${rtnData.rsumeList[0].guideBgnDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="def-list">
                                    <p class="tit f-head">킥오프일</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].guideKickfDt}">
                                                ${rtnData.rsumeList[0].guideKickfDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="def-list">
                                    <p class="tit f-head">현황</p>
                                    <p class="txt f-sub-head">
                                        <c:choose>
                                            <c:when test="${not empty rtnData.rsumeList[0].guidePscndDt && not empty rtnData.rsumeList[0].guidePscndCdNm}">
                                                ${rtnData.rsumeList[0].guidePscndCdNm} ${rtnData.rsumeList[0].guidePscndDt}
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>

                            </c:otherwise>

                        </c:choose>

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

