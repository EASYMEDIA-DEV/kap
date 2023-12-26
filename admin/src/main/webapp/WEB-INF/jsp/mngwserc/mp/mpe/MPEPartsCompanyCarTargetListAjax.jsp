<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty carTargetList.list}">
        <c:forEach var="list" items="${carTargetList.list}" varStatus="status">
            <tr>
                <td class="text-center">${list.year}</td>
                <td class="text-center">${list.mngSttsCdNm}</td>
                <td class="text-center">
                   <c:choose>
                        <c:when test="${empty list.mrtsCdNm}">
                            -
                        </c:when>
                        <c:otherwise>
                            ${empty list.mrtsCdNm}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${list.prizeCdNm}</td>
                <td class="text-center">${list.prizePmt}</td>
                <td class="text-center">${list.cmpnNm}</td>
                <td class="text-center">${list.name}</td>
                <td class="text-center">${list.pstnNm}</td>
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
