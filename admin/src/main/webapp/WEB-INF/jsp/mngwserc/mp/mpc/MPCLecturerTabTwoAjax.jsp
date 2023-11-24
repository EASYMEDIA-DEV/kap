<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:choose>
    <c:when test="${ not empty rtnData.list}">
        <c:forEach var="list" items="${rtnData.list}" varStatus="status">
            <tr data-total-count="${rtnData.totalCount}">
                <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
                <td class="text-center">${list.episdYear}</td>
                <td class="text-center">${list.episdOrd}</td>
                <td class="text-center">${list.edctnStatusNm}</td>
                <td class="text-center">${list.ctgryCdNm}</td>
                <td class="text-center">${list.nm}</td>
                <td class="text-center">${list.stduyMthdCdNm}</td>
                <td class="text-center">${list.stduyDdCdNm}일/${list.stduyTimeCdNm}시간</td>
                <td class="text-center">${kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} ~ ${kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
                <td class="text-center">${list.isttrName}</td>
                <td class="text-center">${list.ffltnNm} <c:if test="${list.isttrOutCnt ne ''}">외 ${list.isttrOutCnt}명 </c:if></td>
                <td class="text-center">${list.rcrmtMthdCdNm}</td>
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
