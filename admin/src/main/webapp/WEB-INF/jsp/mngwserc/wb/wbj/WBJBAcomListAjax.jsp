<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <c:set var="bsnmNo" value="${list.bsnmNo}" ></c:set>
            <c:set var="bsnmNo1" value="${fn:substring(bsnmNo,0,3) }"> </c:set>
            <c:set var="bsnmNo2" value="${fn:substring(bsnmNo,3,5) }"> </c:set>
            <c:set var="bsnmNo3" value="${fn:substring(bsnmNo,5,9) }"> </c:set>

            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">
                    <label class="checkbox-inline c-checkbox">
                        <input type="checkbox" value="${list.appctnSeq}" name="delValueList" class="checkboxSingle notRequired" />
                        <span class="ion-checkmark-round"></span>
                    </label>
                </td>
                <td class="text-center">${rtnData.totalCount - rtnData.firstIndex - status.index}</td>  <%-- 번호 --%>
                <td class="text-center">${list.year}</td> <%-- 연도 --%>
                <td class="text-center"><a href="javascript:" class="listView" data-year="${list.year}" data-details-key="${list.appctnSeq}" value="${list.bsnmNo}">${list.cmpnSeqNm}</a></td> <%-- 부품사명 --%>
                <td class="text-center">${list.ctgryCdNm}</td> <%-- 구분 --%>
                <td class="text-center">${list.sizeCdNm}</td> <%-- 규모 --%>
                <td class="text-center">${bsnmNo1}-${bsnmNo2}-${bsnmNo3}</td> <%-- 사업자등록번호 --%>
                <td class="text-center">${not empty list.mrtsCdNm ? list.mrtsCdNm : "-"}</td> <%-- 훈격부문 --%>
                <td class="text-center">${list.prizeCdNm}</td> <%-- 포상 --%>
                <td class="text-center">${list.name}</td> <%-- 포상대상자 --%>
                <td class="text-center">${list.hpNo}</td> <%-- 핸드폰번호 --%>
                <td class="text-center">${list.appctnSttsCdNm}</td> <%-- 1차 결과 --%>
                <td class="text-center">${not empty list.mngSttsCdNm ? list.mngSttsCdNm : "-"}</td> <%-- 최종결과 --%>
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
                <td class="text-center">${not empty list.modDtm ? list.modDtm : "-"}</td>  <%-- 최종수정일 --%>
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

