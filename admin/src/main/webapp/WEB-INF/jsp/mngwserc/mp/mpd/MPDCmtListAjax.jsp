
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>

  <c:when test="${ not empty rtnData.list}">

    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${list.memSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.memSeq}"/>
            <span class="ion-checkmark-round"></span>
          </label>

        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>


        <!-- 문법 -->
        <c:if test="${rtnData.srchLayer eq 'Y'}">
          <td class="text-center">
                ${list.id}
          </td>
          <td class="text-center">${list.name}</td>
        </c:if>
        <c:if test="${rtnData.srchLayer ne 'Y'}">
          <td class="text-center">
            <a href="javascript:" class="listView" data-details-key="${list.memSeq}">
                ${list.id}
            </a>
          </td>
          <td class="text-center">${list.name}</td>
        </c:if>

        <td class="text-center">${ kl:emptyHypen(list.cmssrTypeCdNm)}</td>
        <td class="text-center">${ kl:emptyHypen(list.cmssrCbsnCdNm)}</td>
        <td class="text-center">${ kl:emptyHypen(list.cmssrWorkCdNm)}</td>
        <td class="text-center">${list.hpNo}</td>
        <td class="text-center">${list.email}</td>
        <td class="text-center">${list.cmtModName}(${list.regId})</td>
        <td class="text-center">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
        <td class="text-center">${kl:emptyHypen(list.modName)}${list.modId == null ? '' : '('+=list.modId+=')'} </td>
        <td class="text-center">${ kl:emptyHypen(kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-'))}</td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="13" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>

