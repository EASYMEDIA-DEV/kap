<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${not empty rtnData.trsfGuidanceList}">
        <c:forEach var="trsfGuidanceList" items="${rtnData.trsfGuidanceList}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${trsfGuidanceList.bfreMemSeq}</td>
                <td class="text-center">${trsfGuidanceList.aftrMemSeq}</td>
                <td class="text-center"> ${kl:convertDate(trsfGuidanceList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}/(${trsfGuidanceList.regId})</td>
                <td class="text-center form-inline">
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="14" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>



