<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">${ list.bfreAuthCdNm}</td><!-- 이전 권한 -->
        <td class="text-center">${ list.modAuthCdNm}</td><!-- 변경 권한 -->
        <td class="text-center">${ empty list.regDtm ? '-' : kl:convertDate(list.regDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '-') } / ${list.regName}(${list.regId})</td><!-- 변경일/ 변경자 -->
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="3" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>

