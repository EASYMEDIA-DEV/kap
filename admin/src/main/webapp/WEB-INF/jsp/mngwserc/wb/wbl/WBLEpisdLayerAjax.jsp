<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnEpisdData.list}">
        <c:forEach var="list" items="${rtnEpisdData.list}" varStatus="status">
            <tr data-total-count="${rtnEpisdData.totalCount}">
                <c:if test="${list.rspnCnt > 0}">
                    <input type="hidden" name="episdCheck" value="${fn:substring(list.year,0,4)}${list.episd}">
                </c:if>
                <td class="text-center"> ${ rtnEpisdData.totalCount - rtnEpisdData.firstIndex - status.index }</td>
                <td class="text-center" > ${fn:substring(list.year,0,4)}</td>
                <td class="text-center" > ${list.episd}</td>
                <td class="text-center" > ${list.titl}</td>
                <td class="text-center" > <button type="button" class="btn btn-danger down mt epidsDel" data-rspn-cnt="${list.rspnCnt}" data-details-key="${list.cxstnCmpnEpisdSeq}">삭제</button></td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="5" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>