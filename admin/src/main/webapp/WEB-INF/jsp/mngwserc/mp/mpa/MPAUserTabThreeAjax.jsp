<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${ kl:emptyHypen(list.parntCtgryCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.ctgryCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.titl)}</td>
                <td class="text-center">${ kl:convertDate(list.startInqrDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                <td class="text-center">${ kl:convertDate(list.endInqrDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                <td class="text-center">${ kl:emptyHypen(list.inqrStts)}</td>

            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="7" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>
