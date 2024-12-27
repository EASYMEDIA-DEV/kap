<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${list.edctnSeq}" name="delValueList" class="checkboxSingle notRequired" data-edctn_seq="${list.edctnSeq}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center" data-ctgryCd="${list.ctgryCd}">${list.prntCdNm}</td><!--과정분류-->
        <td class="text-center">
          <c:choose>
              <c:when test="${ eBACouseDTO.srchLayer eq 'Y'}">
                ${list.nm}
              </c:when>
              <c:otherwise>
                <a href="javascript:" class="listView" data-details-key="${list.edctnSeq}">
                    ${list.nm}
                </a>
              </c:otherwise>
          </c:choose>
        </td><!--과정명-->
        <td class="text-center" data-stduyMthdCd="${list.stduyMthdCd}" data-jdgmtYn="${list.jdgmtYn}" data-gpc-yn="${list.gpcYn}">${list.stduyMthdCdNm}</td><!--학습방식-->
        <td class="text-center">${list.stduyDdCdNm}일/${list.stduyTimeCdNm}시간</td><!--학습시간-->
        <td class="text-center">${list.regName}(${list.regId})</td><!--최초 등록자-->
        <td class="text-center">${ empty list.regDtm ? '-' : kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td><!--최초등록일시-->
        <td class="text-center">${list.modName}(${list.modId})</td><!--최종수정자-->
        <td class="text-center">${ empty list.modDtm ? '-' : kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td><!--최종 수정일시-->
        <td class="text-center">${ kl:decode(list.expsYn, 'Y', '사용', '미사용') }</td><!--사용여부-->
        <td class="text-center">${ kl:decode(list.useYn, 'Y', '사용', '미사용') }</td><!--사용자 사용여부-->
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

