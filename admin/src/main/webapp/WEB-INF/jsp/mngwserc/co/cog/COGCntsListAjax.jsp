<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.seq}" name="delValueList" class="checkboxSingle notRequired" data-prcs-cd="${list.prcsCd}" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <%--<td><a href="javascript:" class="listView"  data-details-key="${list.seq}">${pageTitle}${list.ver}</a></td>--%>
                <td class="text-center"><a href="javascript:" class="listView"  data-details-key="${list.seq}">${pageTitle}</a></td>
                <td class="text-center">${ list.ver }</td>
                <td class="text-center">${ kl:decode(list.prcsCd, '10', '배포', kl:decode(list.prcsCd, '20', '만료', '작성중')) }</td>
                <td class="text-center">${list.regName}(${list.regId})</td><!--최초 등록자-->
                <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td><!--최초등록일시-->
                <td class="text-center">${empty list.modName ? '-' : list.modName += '(' += list.modId += ')'}</td><!--최종수정자-->
                <td class="text-center">${empty list.modDtm ? '-' : kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td><!--최종 수정일시-->
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="9" class="text-center">
                등록된 데이터가 없습니다.
            </td>
        </tr>
    </c:otherwise>
</c:choose>

