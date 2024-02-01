<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${not empty rtnData.list}">
        <c:forEach var="item" items="${rtnData.list}" varStatus="status">
            <c:choose>
                <c:when test="${item.appctnSttsCdNm eq '접수전' || item.appctnSttsCdNm eq '대기' || item.appctnSttsCdNm eq '결과대기'}">
                    <c:set var="classType" value="waiting"/>
                </c:when>
                <c:when test="${item.appctnSttsCdNm eq '접수완료' || item.appctnSttsCdNm eq '적합' || item.appctnSttsCdNm eq '선정' }">
                    <c:set var="classType" value="accepting"/>
                </c:when>
                <c:when test="${item.appctnSttsCdNm eq '사용자취소'}">
                    <c:set var="classType" value="end"/>
                </c:when>
                <c:when test="${item.appctnSttsCdNm eq '보완요청' || item.appctnSttsCdNm eq '부적합' || item.appctnSttsCdNm eq '미선정' || item.appctnSttsCdNm eq '탈락'}">
                    <c:set var="classType" value="arr"/>
                </c:when>
            </c:choose>
            <div class="training-confirm" data-bsn-cd="${item.bsnCd}" data-appctn-seq="${item.appctnSeq}">
                <div class="top-info">
                    <div class="training-view-page">
                        <div class="training-list">
                            <div class="txt-area">
                                <div class="top-line">
                                    <div class="sort-label-area">
                                        <p class="label">
                                    <span>
                                        <c:choose>
                                            <c:when test="${not empty item.title}">${item.title}</c:when>
                                            <c:otherwise>경쟁력향상지원</c:otherwise>
                                        </c:choose>
                                    </span>
                                        </p>
                                    </div>
                                    <p class="training-name f-title3">
                                        <a href="./view?bsnCd=${item.bsnCd}&appctnSeq=${item.appctnSeq}">${item.year} ${item.episd}차 ${item.bsnNm}</a></p><!-- 2024-01-19 a태그 추가 -->
                                    </p>
                                </div>
                                <div class="group">
                                    <p class="index-num f-head">${item.rsumeSttsCdNm}</p>
                                    <div class="status-info-w">
                                        <p class="box-label bigger ${classType}"><span>${item.appctnSttsCdNm}</span></p>
                                    </div>
                                    <c:if test="${item.appctnSttsCdNm eq '보완요청' || item.appctnSttsCdNm eq '부적합' || item.appctnSttsCdNm eq '미선정' || item.appctnSttsCdNm eq '탈락'}">
                                        <div class="tooltip-wrap">
                                            <button class="tooltip-btn btn-icon" type="button" title="툴팁 보기"></button>
                                            <div class="tooltip-box">
                                                <p class="txt">${item.rtrnRsnCntn}</p>
                                                <button class="btn-close" title="툴팁 닫기" type="button"></button>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bot-info">
                    <div class="index-list-w">
                        <div class="list-item">
                            <div class="cont">
                                <div class="cont-area">
                                    <div class="info-list-w ">
                                        <div class="info-list">
                                            <p class="tit f-caption2">신청일자</p>
                                            <p class="txt f-body2">${ kl:convertDate(item.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p>
                                        </div>
                                        <div class="info-list">
                                            <div class="tit f-caption2">사업기간</div>
                                            <p class="txt f-body2">${ kl:convertDate(item.bsnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') } ~ <br class="only-pc"/>${ kl:convertDate(item.bsnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="no-data-area has-border"><!-- has-border: 테두리 있을 경우 -->
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>