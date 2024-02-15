<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="board-list">
<c:choose>
    <c:when test="${ not empty rtnData.list }">
        <div class="article-list-w card-list"><!-- card-list: 썸네일 있는 경우 -->
            <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                <a class="list-item open foundationListView" href="javascript:" title="링크 이동" data-details-key="${list.fndnNewsSeq}">
                    <div class="img-box">
                        <c:choose>
                            <c:when test="${ not empty list.webPath }">
                                <img src="${ list.webPath }" alt="">
                            </c:when>
                            <c:otherwise>

                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="txt-box">
                        <p class="tit f-head"><span class="new-icon" aria-label="새로운 항목"></span>${list.titl}</p>
                        <span class="sub-txt f-body2">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</span>
                    </div>
                </a>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="no-data-area">
            <div class="txt-box">
                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</div>