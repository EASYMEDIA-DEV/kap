<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${not empty rtnData }">
        <div class="trainings-list-w">
            <c:forEach var="appctnList" items="${rtnData}" varStatus="status">
            <div class="training-confirm infoCard openCard">
                <div class="top-info">
                    <div class="training-view-page">
                        <div class="training-list">
                            <!-- <div class="img-area">
                              <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                            </div> -->
                            <c:choose>
                                <c:when test="${appctnList.cnstgCd eq 'CONSULT_GB01'}">
                                    <div class="txt-area">
                                        <input type="hidden" name="cnstgSeq" class="cnstgSeq" value="${appctnList.cnstgSeq}">
                                        <input type="hidden" name="cnstgCd" class="cnstgCd" value="${appctnList.cnstgCd}">
                                        <div class="top-line">
                                            <div class="sort-label-area">
                                                <p class="f-caption2"><span>${appctnList.cnstgNm}</span></p>
                                            </div>
                                                <p class="training-name f-title3 dtlBtn" data-seq="${appctnList.cnstgSeq}">${appctnList.bsnYear} 상주기술지도 </p>
                                        </div>
                                        <div class="group">
                                            <c:choose>
                                                <c:when test="${appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_08' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_09' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_10' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_11' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_12' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS_13'}">
                                                    <p class="index-num f-head ${appctnList.cnstgSeq}statusType">지도단계</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="index-num f-head ${appctnList.cnstgSeq}statusType">신청단계</p>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${fn:contains(appctnList.rsumeSttsNm, '탈락')}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger arr"><span>${appctnList.rsumeSttsNm}</span></p>
                                                    </div>
                                                    <div class="tooltip-wrap">
                                                        <button class="tooltip-btn btn-icon" type="button" title="툴팁 보기"></button>
                                                        <div class="tooltip-box">
                                                            <p class="txt f-caption2">${appctnList.rsltCntn}</p>
                                                            <button class="btn-close" title="툴팁 닫기" type="button"></button>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger accepting"><span>${appctnList.rsumeSttsNm}</span></p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${appctnList.cnstgCd eq 'CONSULT_GB02'}">
                                <div class="txt-area open">
                                    <div class="top-line">
                                        <div class="sort-label-area">
                                            <p class="f-caption2"><span>${appctnList.cnstgNm}</span></p>
                                        </div>
                                            <p class="training-name f-title3 dtlBtn" data-seq="${appctnList.cnstgSeq}">${appctnList.bsnYear} 상주경영컨설팅 </p>
                                    </div>
                                    <div class="group">
                                        <c:choose>
                                            <c:when test="${appctnList.rsumeSttsCd eq 'MNGTECH_STATUS10' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS11' || appctnList.rsumeSttsCd eq 'MNGTECH_STATUS12'}">
                                                <p class="index-num f-head ${appctnList.cnstgSeq}statusType">지도단계</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="index-num f-head ${appctnList.cnstgSeq}statusType">신청단계</p>
                                            </c:otherwise>
                                        </c:choose>
                                            <c:choose>
                                                <c:when test="${fn:contains(appctnList.rsumeSttsNm, '탈락')}">
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger arr"><span>${appctnList.rsumeSttsNm}</span></p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger accepting"><span>${appctnList.rsumeSttsNm}</span></p>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            <div class="bot-info">
                <div class="index-list-w">
                    <div class="list-item">
                        <div class="cont">
                            <div class="cont-area">
                                <div class="info-list-w ">
                                    <div class="info-list">
                                        <p class="tit f-caption2">신청업종</p>
                                        <c:choose>
                                            <c:when test="${appctnList.cnstgCd eq 'CONSULT_GB01'}">
                                                <p class="txt f-body2">${appctnList.cbsnNm}</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="txt f-body2">${appctnList.appctnFldNm}</p>
                                            </c:otherwise>
                                        </c:choose>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">신청사항</p>
                                            <p class="txt f-body2 ${appctnList.cnstgSeq}appctnCd" style="text-overflow: ellipsis"></p>
                                        </div>
                                        <div class="info-list">
                                            <p class="tit f-caption2">신청일자</p>
                                            <p class="txt f-body2">${appctnList.appctnDt}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </div>
        </div>
    <div class="btn-wrap add-load align-center moreBtn">
        <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count cntText"></span></a>
    </div>
</c:when>
<c:otherwise>
    <div class="index-list-w">
        <!-- 데이터 없을 경우 노출되는 영역 -->
        <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </div>
</c:otherwise>
</c:choose>