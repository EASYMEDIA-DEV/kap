<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td>
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.seq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td>${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td><a href="javascript:" class="listView"  data-details-key="${list.seq}"></a></td>
                <td>${ list.name}</td>
                <td>${ list.titl}</td>
                <td>${ list.mainYn eq 'Y' ? '노출' : '미노출' }</td>
                <td>${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '') }</td>
                <td>${ list.regName }(${ list.regId })</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="8" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

