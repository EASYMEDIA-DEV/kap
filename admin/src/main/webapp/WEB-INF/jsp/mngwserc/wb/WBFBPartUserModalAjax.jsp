<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.memSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.memSeq}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center" data-point="id">${list.id}</td>
                <td class="text-center">${list.name}</td>
                <td class="text-center">${list.cmpnNm}</td>
                <td class="text-center">${list.ctgryCdNm}</td>
                <td class="text-center">${ list.sizeCdNm}</td>
                <td class="text-center">${ kl:bsnmNoConvert(list.workBsnmNo)}</td>
                <td class="text-center">${list.hpNo}</td>
                <td class="text-center">${list.email}</td>
                <td class="text-center">${list.regDtm}</td>
                <td class="text-center">${list.modName}</td>
                <td class="text-center">${list.modDtm}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="12" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

