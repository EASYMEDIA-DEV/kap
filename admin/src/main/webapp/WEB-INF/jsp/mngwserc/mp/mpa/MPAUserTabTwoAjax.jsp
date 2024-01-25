<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${ list.year}</td>
                <td class="text-center">${ kl:emptyHypen(list.name)}</td>
                <td class="text-center">${ kl:emptyHypen(list.ptcptTypeNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.themeCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.wdcrmCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.seoryuCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.firstCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(list.endCdNm)}</td>
                <td class="text-center">${ kl:emptyHypen(kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-'))}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="10" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>
