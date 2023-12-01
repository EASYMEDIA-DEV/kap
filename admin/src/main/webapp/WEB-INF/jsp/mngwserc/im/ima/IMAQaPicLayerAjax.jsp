<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${ list.parntCtgryNm } > ${ list.ctgryNm }</td>
                <%--<td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.qaSeq}" data-rsume-cd="${list.rsumeCd}">${list.titl}</a></td>--%>
                <td class="text-center">${ list.picNm }</td>
                <td class="text-center">${ list.piceMail }</td>
                <td class="text-center">
                    <a href="javascript:" class="listView btnPicUpdate" data-details-key="${list.picSeq}" data-type="update">[수정]</a>
                    <a href="javascript:" class="listView btnPicDelete" data-details-key="${list.picSeq}" data-type="delete">[삭제]</a>
                </td>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="5" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

