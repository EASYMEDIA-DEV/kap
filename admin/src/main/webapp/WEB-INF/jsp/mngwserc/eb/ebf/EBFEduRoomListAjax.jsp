<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}" data-srch-edu-room-layer="${eduRoomDto.srchEduRoomLayer}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${list.placeSeq}" name="delValueList" class="checkboxSingle notRequired" data-place-seq="${list.placeSeq}"/><%--체크박스--%>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td><%--번호--%>
        <td class="text-center">
          <c:choose>
            <c:when test="${ partsComDto.srchLayer eq 'Y'}">
              ${list.nm}
            </c:when>
            <c:otherwise>
              <a href="javascript:" class="listView" data-details-key="${list.placeSeq}">${list.nm}</a>
            </c:otherwise>
          </c:choose>
        </td><!--교육장명-->
        <td class="text-center">${list.rgnsName}</td><!--지역-->
        <td class="text-center">
          <c:if test="${not empty list.zipcode}">(${list.zipcode})</c:if> ${list.bscAddr}<c:if test="${not empty list.dtlAddr}">, ${list.dtlAddr}</c:if>
        </td><!--주소-->
        <td class="text-center">${ list.rprsntTelNo }</td><!--대표번호-->
        <td class="text-center">${list.regName}</td><!--최초 등록자-->
        <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td><!--최초등록일시-->
        <td class="text-center">${empty list.modName ? '-' : list.modName}</td><!--최종수정자-->
        <td class="text-center">${empty list.modDtm ? '-' : kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td><!--최종 수정일시-->
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="10" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>

