<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <c:set var="bsnmNo" value="${list.bsnmNo}" ></c:set>
            <c:set var="bsnmNo1" value="${fn:substring(bsnmNo,0,3) }"> </c:set>
            <c:set var="bsnmNo2" value="${fn:substring(bsnmNo,3,5) }"> </c:set>
            <c:set var="bsnmNo3" value="${fn:substring(bsnmNo,5,9) }"> </c:set>
            <c:set var="modDtm" value="${kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}"> </c:set>
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.appctnSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>
                <td class="text-center">${list.year}</td>
                <td class="text-center">${list.episd}</td>
                <td class="text-center">${list.mngSttsCd}</td>
                <td class="text-center"><a href="javascript:" class="listView" data-details-key="${list.appctnSeq}" value="${list.bsnmNo}">${list.cmpnNm}</a></td>
                <td class="text-center">${bsnmNo1}-${bsnmNo2}-${bsnmNo3}</td> <%-- 사업자등록번호 --%>
                <td class="text-center">${list.ctgryCdNm}</td>
                <td class="text-center">${list.sizeCdNm}</td>
                <td class="text-center">${list.name}(${list.id})</td>
                <td class="text-center">${list.hpNo}</td>
                <td class="text-center">${list.email}</td>
                <td class="text-center">${kl:convertDate(list.mngSttsChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-')}</td>
                <td class="text-center">${kl:convertDate(list.appctnSttsChngDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd', '-')}</td>
                <td class="text-center">${ list.regName}(${list.regId})</td>
                <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.modDtm}">
                            ${ list.modName }(${list.modId})
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center">
                    <c:choose>
                        <c:when test="${not empty list.modDtm}">
                            ${ modDtm }
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
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

