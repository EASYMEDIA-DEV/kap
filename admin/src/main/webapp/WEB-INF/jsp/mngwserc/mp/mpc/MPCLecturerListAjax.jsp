<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}" data-srch-layer="${lecturerDto.srchLayer}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.isttrSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.isttrSeq}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center" data-isttr-seq="${list.isttrSeq}">${list.name}</td>
                <td class="text-center srchListView">
                    <c:choose>
                        <c:when test="${ lecturerDto.srchLayer eq 'Y'}">
                            ${list.ffltnNm}
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:" class="listView" data-details-key="${list.isttrSeq}">
                               ${list.ffltnNm}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ list.deptNm ne ''}">
                            ${list.deptNm}
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ list.pstnNm ne ''}">
                            ${list.pstnNm}
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${list.hpNo}</td>
                <td class="text-center">${list.email}</td>
                <td class="text-center">${list.regName}</td>
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

