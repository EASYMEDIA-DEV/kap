<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.seq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.seq}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">
                    <a href="javascript:" class="listView" data-details-key="${list.seq}">
                            ${ list.titl}
                    </a>
                </td>
                <td class="text-center" data-reg-dtm="${list.regDtm}">${list.regDtm}</td>
                <td class="text-center" data-reg-id="${list.regId}">${list.regId}</td>
                <td class="text-center" data-mod-id="${list.modId}">${list.modId}</td>
                <td class="text-center" data-list-dtm="${list.modDtm}">${list.modDtm}</td>
                <td class="text-center" data-use-yn="${list.useYn}">${ kl:decode(list.useYn, 'Y', '노출', '미노출') }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="11" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

