<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.chngAuthList}">
        <c:forEach var="list" items="${rtnData.chngAuthList}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td>${ list.bfreAuthCdNm }</td>
                <td>${ list.modAuthCdNm}</td>
                <td>${ list.rsn}</td>
                <td>${ empty list.regDtm ? '-' : kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } / ${list.regName}(${list.regId})</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="4" class="text-center">
                변경 이력이 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

