<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${list.admSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.authCd}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center">
          <a href="javascript:" class="listView ${list.authCd eq '99' ? 'text-danger' : ''}" data-details-key="${list.admSeq}">
              ${ list.id }<c:if test="${ list.authCd eq '99'}"> (*)</c:if>
          </a>
        </td>
        <td class="text-center">${ list.name}</td>
        <td class="text-center">${ list.deptCdNm}</td>
        <td class="text-center">${ list.authCdNm}</td>

        <td class="text-center">${ kl:decode(list.useYn, 'Y', '활성', '비활성') }</td>
        <td class="text-center">${ empty list.lastLgnDtm ? '-' : kl:convertDate(list.lastLgnDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="8" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>