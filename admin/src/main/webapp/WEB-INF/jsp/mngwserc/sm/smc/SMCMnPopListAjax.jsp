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
                <td class="text-center" data-type-cd="${list.typeCd}">${ list.typeCd == "image" ? "이미지" : "HTML"}</td>
                <td class="text-center">
                    <a href="javascript:" class="listView" data-details-key="${list.seq}">
                            ${ list.titl}
                    </a>
                </td>
                <%-- <td class="text-center" data-strt-dtm="${list.strtDtm}">${ empty list.strtDtm ? '-' : kl:convertDate(list.strtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td>--%>
                <td class="text-center" >${ list.odtmYn == 'Y' ? "상시" : kl:convertDate(list.strtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') +=  '</br> ~ </br>' +=  kl:convertDate(list.endDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') } </td>
                <td class="text-center" data-reg-dtm="${list.regDtm}">${list.regDtm}</td>
                <td class="text-center" data-reg-id="${list.regId}">${list.regId}</td>
                <td class="text-center" data-mod-id="${list.modId}">${list.modId}</td>
                <td class="text-center" data-list-dtm="${list.modDtm}">${list.modDtm}</td>
                <td class="text-center" data-use-yn="${list.useYn}">${ kl:decode(list.useYn, 'Y', '노출', '미노출') }</td>
                <td class="text-center" data-key="${list.seq}" data-value="${list.ord}">
                    <button type="button" class="btn btn-default btn-xs sortUp" name="sortUp" id="btnSort"><i class="ion-arrow-up-b"></i></button>
                    <button type="button" class="btn btn-default btn-xs ml-sm sortDown" name="sortDown" id="btnSort"><i class="ion-arrow-down-b"></i></button>
                </td>
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

