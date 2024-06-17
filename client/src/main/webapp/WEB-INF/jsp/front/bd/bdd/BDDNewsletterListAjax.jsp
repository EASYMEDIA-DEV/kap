<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/include/el.jspf" %>

<c:choose>
    <c:when test="${ not empty rtnData.list }">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <a class="list-item nwslttrListView" href="javascript:" title="링크 이동"
               data-details-key="${list.nwsltSeq}">
                <div class="img-box">
                    <c:choose>
                        <c:when test="${ not empty list.webPath }">
                            <img src="${ list.webPath }" alt="">
                        </c:when>
                        <c:otherwise>
                            <img src="/common/images/@img-news-foundation-01.jpg" alt="">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="txt-box">
                    <p class="tit f-head">
                    <c:if test="${list.newPostYn eq 'Y'}">
                        <span class="new-icon" aria-label="새로운 항목"></span>
                    </c:if>
                        ${list.titl}</p>
                    <span class="sub-txt f-body2">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</span>
                </div>
            </a>
        </c:forEach>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>