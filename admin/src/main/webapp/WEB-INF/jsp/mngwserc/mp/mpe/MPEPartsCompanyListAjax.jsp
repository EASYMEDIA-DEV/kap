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
                <td class="text-center">${kl:bsnmNoConvert(list.bsnmNo)}</td>
                <td class="text-center ctgryNm" data-ctgry-cd="${list.ctgryCd}">${list.ctgryNm}</td>
                <td class="text-center" data-size-cd="${list.sizeCd}">${list.sizeNm}</td>
                <td class="text-center">${kl:emptyHypen(list.cmpnCd)}</td>
                <td class="text-center">${kl:emptyHypen(list.slsPmt)}</td>
                <td class="text-center">${kl:emptyHypen(list.mpleCnt)}</td>
                <td class="text-center">${kl:emptyHypen(list.telNo)}</td>
                <td class="text-center">${ empty list.regUserName ? list.regName : list.regUserName }(${ list.regId })</td>
                <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${ not empty list.modId }">
                            ${ empty list.modUserName ? list.modName : list.modUserName }(${ list.modId })
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">${kl:emptyHypen(kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-'))}</td>
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

