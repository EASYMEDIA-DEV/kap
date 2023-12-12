<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.educationList}">
        <c:forEach var="list" items="${rtnData.educationList}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="t-align-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="t-align-center">
                    <div class="sort-label-area">
                        <c:set var="ctgryCdNmList" value="${ fn:split(list.ctgryCdNm, '|')}" />
                        <c:forEach var="cdNm" items="${ctgryCdNmList}" varStatus="status">
                            <p class="label"><span>${ cdNm }</span></p>
                        </c:forEach>
                    </div>
                    ${ list.nm }
                </td>
                <td class="t-align-center">${ list.episdYear }년/<br class="only-pc"/>${ list.episdOrd }회차</td>
                <c:if test="${ searchType eq 'complete' }">
                <td class="t-align-center">${ list.cbsnCdNm }</td>
                </c:if>
                <td class="t-align-center">${ kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') } ~ <br class="only"/>${ kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</td>
                <td class="t-align-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</td>
                <td class="t-align-center">${ kl:convertDate(list.cmptnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="7" class="text-center">
                <div class="txt-box">
                    <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                </div>
            </td>
        </tr>
    </c:otherwise>
</c:choose>

