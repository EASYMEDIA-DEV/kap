<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}" data-srch-parts-com-layer="${partsComDto.srchLayer}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.bsnmNo}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.bsnmNo}"/>
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">${list.rprsntNm}</td>
                <td class="text-center srchListView">
                    <c:choose>
                        <c:when test="${ partsComDto.srchLayer eq 'Y'}">
                            ${list.cmpnNm}
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:" class="listView" data-details-key="${list.bsnmNo}" data-cmpn-nm="${list.cmpnNm}">
                                    ${list.cmpnNm}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${list.bsnmNo}</td>
                <td class="text-center" data-ctgry-cd="${list.ctgryCd}">${list.ctgryNm}</td>
                <td class="text-center" data-size-cd="${list.sizeCd}">${list.sizeNm}</td>
                <td class="text-center">${list.cmpnCd}</td>
                <td class="text-center">${list.slsPmt}</td>
                <td class="text-center">${list.mpleCnt}</td>
                <td class="text-center">${list.telNo}</td>
                <td class="text-center">${list.regName}</td>
            </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <tr data-total-count="0">
            <td colspan="16" class="text-center">
                검색결과가 없습니다.<br>
                (등록된 데이터가 없습니다.)
            </td>
        </tr>
    </c:otherwise>
</c:choose>

