<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td>${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td>${ list.regId }</td>
        <td>${ list.regName}</td>
        <td>${ list.deptCdNm}</td>
        <td>${ list.trgtMenuNm }</td>
        <td>${ list.prcsCdNm }</td>
        <td>${ empty list.rsn ? '-' : list.rsn }</td>
        <td>${ list.regIp }</td>
        <td>${ empty list.regDtm ? '-' : kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="9" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>

