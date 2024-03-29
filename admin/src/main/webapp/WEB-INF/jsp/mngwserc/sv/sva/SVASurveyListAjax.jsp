<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.srvSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center" > ${list.typeNm}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ rtnData.srchLayer eq 'Y'}">
                            ${list.titl}
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:" class="listView"  data-details-key="${list.srvSeq}">
                                    ${list.titl}
                            </a>
                        </c:otherwise>
                    </c:choose>

                </td>
                <td class="text-center" >${ list.regName }(${ list.regId })</td>
                <td class="text-center" data-reg-dtm="${list.regDtm}">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" >${ list.modName }(${ list.modId })</td>
                <td class="text-center" data-list-dtm="${list.modDtm}">${ kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center" data-use-yn="${list.expsYn}">${ list.expsYn eq 'Y' ? '노출' : '미노출' }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="11" class="text-center">
                검색결과가 없습니다.<br> (등록된 데이터가 없습니다)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

