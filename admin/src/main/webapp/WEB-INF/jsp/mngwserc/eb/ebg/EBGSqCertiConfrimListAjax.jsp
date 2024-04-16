<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyyMMdd" /></c:set>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center" >
                    <%-- 2024-03-29 자격증 상태 발급대기 나 반려시 상태값 노출을 위한 조건문 변경 --%>
                    <c:choose>
                        <c:when test="${list.issueNm eq '발급'}">
                            <c:set var="validEndDt" value="${ kl:convertDate(list.validEndDt, 'yyyy-MM-dd', 'yyyyMMdd', '') }" />
                            <c:choose>
                                <c:when test="${list.useYn eq 'N'}">중지</c:when>
                                <c:when test="${validEndDt < sysDate }">만료</c:when>
                                <c:otherwise>정상</c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            ${list.issueNm}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center" >${ list.examCdNm }</td>
                <td class="text-center" >${ list.cmpnNm }</td>
                <td class="text-center" >${ list.bsnmNo }</td>
                <td class="text-center" >${ list.ctgryNm }</td>
                <td class="text-center" >${ list.sizeNm }</td>
                <td class="text-center srchListView" >
                    <a href="javascript:" class="listView"  data-details-key="${list.examAppctnSeq}">
                        ${ list.name }</br>(${kl:idMasking(list.id)})
                    </a>
                </td>
                <td class="text-center" >${ kl:phoneMasking(list.hpNo) }</td>
                <td class="text-center" >${ kl:emailMasking(list.email) }</td>
                <td class="text-center" >${ kl:convertDate(list.acqsnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</td>
                <td class="text-center" >${ kl:convertDate(list.validEndDt, 'yyyy-MM-dd', 'yyyy.MM.dd', '') }</td>
                <td class="text-center" >${ list.modName }</br>(${kl:idMasking(list.modId)})</td>
                <td class="text-center" >${ kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '') }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="17" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

