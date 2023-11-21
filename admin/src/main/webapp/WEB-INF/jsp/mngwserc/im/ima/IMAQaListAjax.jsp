<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${ list.parntCtgryNm }</td>
                <td class="text-center">${ list.ctgryNm }</td>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.qaSeq}" data-rsume-cd="${list.rsumeCd}">${list.titl}</a></td>
                <td class="text-center">${ list.regName }(${ list.regId })</td>
                <td class="text-center">${ list.email }</td>
                <td class="text-center">${ list.hpNo.contains("-") ? list.hpNo : list.hpNo.replaceAll('(\\d{3})(\\d{4})(\\d{4})', '$1-$2-$3') }</td>
                <td class="text-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center">${ kl:decode(list.modDtm, "", "-", kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')) }</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ list.rsumeCd eq 'SYN' }">
                            접수대기
                        </c:when>
                        <c:when test="${ list.rsumeCd eq 'SYNACK' }">
                            답변대기
                        </c:when>
                        <c:when test="${ list.rsumeCd eq 'ACK' }">
                            답변완료
                        </c:when>
                    </c:choose>
                </td>
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

