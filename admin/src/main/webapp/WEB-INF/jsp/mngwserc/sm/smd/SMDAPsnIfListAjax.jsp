<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.psnifSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.psnifSeq}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">
                    <a href="javascript:" class="listView" data-details-key="${list.psnifSeq}">
                            ${list.titl}
                    </a>
                </td>
                <td class="text-center">${list.regName}(${list.regId})</td>
                <td class="text-center">${list.regDtm}</td>
                <td class="text-center">${list.modName}(${list.modId})</td>
                <td class="text-center">${list.modDtm}</td>
                <td class="text-center">${ kl:decode(list.expsYn, 'Y', '노출', '미노출') }</td>
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

