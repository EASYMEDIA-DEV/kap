<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
  <c:when test="${ not empty rtnData.list}">
    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
      <tr data-total-count="${rtnData.totalCount}">
        <c:if test="${rtnData.srchLayer eq 'Y'}">
          <td class="text-center">
            <label class="checkbox-inline c-checkbox">
              <input type="checkbox" value="${list.memSeq}" name="delValueList" class="checkboxSingle notRequired" data-auth-cd="${list.memSeq}"/>
              <span class="ion-checkmark-round"></span>
            </label>
          </td>
        </c:if>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <c:if test="${rtnData.srchLayer eq 'Y'}">
          <td class="text-center srchListView" data-gndr="${list.gndr}">
              ${list.id}
          </td>
          <td class="text-center">${list.name}</td>
        </c:if>
        <c:if test="${rtnData.srchLayer ne 'Y'}">
          <td class="text-center srchListView">
            <a href="javascript:" class="listView" data-details-key="${list.memSeq}">
                ${kl:idMasking(list.id)}
            </a>
          </td>
          <td class="text-center">${kl:nameMasking(list.name)}</td>
        </c:if>

        <td class="text-center">${list.cmpnNm}</td>
        <td class="text-center">${kl:bsnmNoConvert(list.workBsnmNo)}</td>
        <td class="text-center">${list.ctgryCdNm}</td>
        <td class="text-center">${list.sizeCdNm}</td>
        <td class="text-center">${list.deptCdNm} ${list.deptDtlNm == '' ? '' : '('+=list.deptDtlNm+=')'} </td>
        <td class="text-center">${list.pstnCdNm} ${list.pstnCdNm == '기타' ? '('+=list.pstnNm+=')' : '' }</td>

        <c:if test="${rtnData.srchLayer eq 'Y'}">
          <td class="text-center">${list.hpNo}</td>
          <td class="text-center">${list.email}</td>
        </c:if>
        <c:if test="${rtnData.srchLayer ne 'Y'}">
          <td class="text-center">${kl:phoneMasking(list.hpNo)}</td>
          <td class="text-center">${kl:emailMasking(list.email)}</td>
        </c:if>



        <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
        <td class="text-center">${kl:emptyHypen(list.modName)}(${list.modId})</td>
        <td class="text-center">${kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
        <input type="hidden" class="telNo" value="${list.telNo}"/>
        <input type="hidden" class="pstnCd" value="${list.pstnCd}"/>
        <input type="hidden" class="deptCdNm" value="${list.deptCdNm}"/>
        <input type="hidden" class="deptCd" value="${list.deptCd}"/>
        <input type="hidden" class="deptDtlNm" value="${list.deptDtlNm}"/>
        <input type="hidden" class="pstnNm" value="${list.pstnNm}"/>
        <input type="hidden" class="pstnCdNm" value="${list.pstnCdNm}"/>

      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="15" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>

