<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.ptcptList}">
    <c:forEach var="ptcptList" items="${rtnData.ptcptList}" varStatus="status">
      <tr  data-total-count="${rtnData.totalCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${ptcptList.ptcptSeq}" name="delValueList" class="checkboxSingle notRequired" data-edctn_seq="${ptcptList.ptcptSeq}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center">${ptcptList.id}</td>
        <td class="text-center">${ptcptList.name}</td>
        <td class="text-center">${ptcptList.cmpnNm}</td>
        <td class="text-center">${ptcptList.ctgryNm}</td>
        <td class="text-center">${ptcptList.ptcptBsnmNo}</td>
        <td class="text-center">${ptcptList.hpNo}</td>
        <td class="text-center">${ptcptList.email}</td>
        <td class="text-center">${ empty ptcptList.regDtm ? '-' : kl:convertDate(ptcptList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td>
        <td class="text-center">${ptcptList.eduDtm}</td>
        <td class="text-center">${ptcptList.sttsCdNm}</td>
        <td class="text-center">출석</td>
        <td class="text-center">평가</td>
        <td class="text-center">
          <select class="form-control input-sm wd-sm" name="cmptnYn" id="cmptnYn" title="수료여부">
            <option value="N" <c:if test="${ptcptList.cmptnYn eq 'N'}">selected</c:if>>미수료</option>
            <option value="Y" <c:if test="${ptcptList.cmptnYn eq 'Y'}">selected</c:if>>수료</option>
          </select>
        </td>
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



