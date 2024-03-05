<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty fundingList.list}">
        <c:forEach var="list" items="${fundingList.list}" varStatus="status">
            <tr>
                <td class="text-center">${list.bsnNm}</td>
                <td class="text-center">${kl:emptyHypen(list.picCmssrNm)}</td>
                <td class="text-center">${kl:emptyHypen(list.fndnSpprtPmt)}</td>
                <td class="text-center">${kl:emptyHypen(list.nvstmPmt)}</td>
                <td class="text-center">${kl:emptyHypen(list.realGiveDt)}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${list.tchlgOrdMax eq 1}">
                            ${ list.tchlgNm }
                        </c:when>
                        <c:otherwise>
                            ${ list.tchlgNm } 외 ${ list.tchlgOrdMax -1 }
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="6" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>
