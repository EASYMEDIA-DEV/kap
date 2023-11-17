<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center">${list.name}</td><!--이름-->
        <td class="text-center">${list.ffltnNm}<td><!--소속-->
        <td class="text-center">${list.spclCntn}</td><!--약력(특이사항)-->
        <td class="text-center">
          <button type="button" class="btn btn-inverse btn-sm" data-isttr_seq="${list.isttrSeq}">
            삭제
          </button>
        </td><!--삭제-->
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="5" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>