<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">${list.nm}</td><!--교육장명-->
        <td class="text-center">${list.rgnsCdNm}<td><!--지역-->
        <td class="text-center">${list.bscAddr}${listDtlAddr}</td><!--주소-->
        <td class="text-center">${list.rprsntTelNo}</td><!--대표번호-->
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