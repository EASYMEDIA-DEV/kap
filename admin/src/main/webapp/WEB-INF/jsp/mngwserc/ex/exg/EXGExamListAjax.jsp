<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}" data-srch-exam-layer="${searchDto.srchExamLayer}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.examSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center srchListView">
                    <c:choose>
                        <c:when test="${ searchDto.srchExamLayer eq 'Y'}">
                            ${list.titl}
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:" class="listView"  data-details-key="${list.examSeq}">
                                    ${list.titl}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center" >${ list.regName }</td>
                <td class="text-center" data-reg-dtm="${list.regDtm}">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" >${ list.modName }</td>
                <td class="text-center" data-list-dtm="${list.modDtm}">${ kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" data-exps-yn="${list.expsYn}">${ list.expsYn eq 'Y' ? '노출' : '미노출' }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="11" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

