<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:forEach var="list" items="${lists.list}" varStatus="status">
    <tr data-total-count="${lists.totalCount}">
        <td class="text-center">
            <label class="checkbox-inline c-checkbox">
                <input type="checkbox" value="${list.popupSeq}" name="delValueList" class="checkboxSingle notRequired" />
                <span class="ion-checkmark-round"></span>
            </label>
        </td>
        <td class="text-center">${lists.totalCount - lists.firstIndex - status.index}</td>
        <td class="text-center pre-title"><a href="javascript:" class="listView" data-details-key="${list.popupSeq}">${list.titl}</a></td>
        <c:choose>
            <c:when test="${list.odtmYn == 'Y'}">
                <td class="text-center">상시</td>
            </c:when>
            <c:otherwise>
                <td class="text-center">
                        ${kl:convertDate(list.postStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')} ~ ${kl:convertDate(list.postEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')}
                </td>
            </c:otherwise>
        </c:choose>
        <td class="text-center">
                ${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '')}
        </td>
        <c:choose>
            <c:when test="${list.expsYn == 'Y'}">
                <td class="text-center">
                    노출
                </td>
            </c:when>
            <c:otherwise>
                <td class="text-center">
                    미노출
                </td>
            </c:otherwise>
        </c:choose>
        <td class="text-center">
                ${list.regName} (${list.regId})
        </td>
    </tr>
</c:forEach>
<c:if test="${fn:length(lists.list) == 0}">
    <tr data-total-count="0">
        <td colspan="7" class="text-center">
            검색결과가 없습니다.<br>
            (등록된 데이터가 없습니다.)
        </td>
    </tr>
</c:if>