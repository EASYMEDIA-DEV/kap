<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.seq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <c:if test="${list.typeCd eq '30'}">
                    <td class="text-center">${  list.faqTypeNm  }</td>
                </c:if>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.seq}">${list.titl}</a></td>
                <td class="text-center">${ list.readCnt }</td>
                <td class="text-center">${ list.regName }</td>
                <td class="text-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                <td class="text-center"><%--<c:if test="${not empty list.modDtm}">${ list.modName }(${ list.modId })</c:if>--%>
                    <c:choose>
                        <c:when test="${not empty list.modDtm}">
                            ${ list.modName }(${ list.modId })
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${ kl:decode(list.modDtm, "", "-", kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '')) }</td>
                <td class="text-center">${ list.mainYn eq 'Y' ? '노출' : '미노출' }</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:when test="${ empty rtnData.list and rtnData.typeCd eq '30'}">
        <tr data-total-count="0">
            <td colspan="10" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="9" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

