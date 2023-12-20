<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.trndSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.trndSeq}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">${list.typeCdNm}</td>
                <td class="text-center">
                    <a href="javascript:" class="listView" data-details-key="${list.trndSeq}">
                            ${list.titl}
                    </a>
                </td>
                <td class="text-center">${list.regName}(${list.regId})</td>
                <td class="text-center">${list.regDtm}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.modId }">
                            ${list.modName}(${list.modId})
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.modDtm }">
                            ${kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')}
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ kl:decode(list.expsYn, 'Y', '노출', '미노출') }</td>
                <td class="text-center" data-key="${list.trndSeq}" data-value="${list.expsOrd}">
                    <button type="button" class="btn btn-default btn-xs sortUp" name="sortUp" id="btnSort"><i class="ion-arrow-up-b"></i></button>
                    <button type="button" class="btn btn-default btn-xs ml-sm sortDown" name="sortDown" id="btnSort"><i class="ion-arrow-down-b"></i></button>
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

