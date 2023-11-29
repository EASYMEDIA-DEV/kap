<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">${list.year}</td>
                <td class="text-center">${list.episd}</td>
                <td class="text-center">${list.rsumeSttsCdNm}</td>
                <td class="text-center">${list.mngSttsCdNm}</td>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.appctnSeq}">${list.cmpnNm}</a></td>
                <td class="text-center">${list.ctgryCdNm}</td>
                <td class="text-center">${list.sizeCdNm}</td>
                <td class="text-center">${list.bsnmNo}</td>
                <td class="text-center">${list.name}(${list.id})</td>
                <td class="text-center">${list.hpNo}</td>
                <td class="text-center">${list.email}</td>
                <td class="text-center">${not empty list.mngSttsChngDtm ? list.mngSttsChngDtm : "-"}</td>
                <td class="text-center">${not empty list.appctnSttsChngDtm ? list.appctnSttsChngDtm : "-"}</td>
                <td class="text-center">${not empty list.regDtm ? list.regDtm : "-"}</td>
                <td class="text-center">${list.name}(${list.id})</td>
                <td class="text-center">${not empty list.modDtm ? list.modDtm : "-"}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="16" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

