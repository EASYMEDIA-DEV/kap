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
        <td class="text-center srchListView">
          <a href="javascript:" class="listView" data-details-key="${list.memSeq}">
              ${kl:idMasking(list.id)}
          </a>
        </td>
        <td class="text-center">${kl:nameMasking(list.name)}</td>
        <td class="text-center">${list.cmpnNm}</td>
        <td class="text-center">${kl:bsnmNoConvert(list.workBsnmNo)}</td>
        <td class="text-center">${list.ctgryCdNm}</td>
        <td class="text-center">${list.sizeCdNm}</td>
        <td class="text-center deptCd" value="${list.deptCd}">${list.deptDtlNm}</td>
        <td class="text-center">${list.pstnCdNm}</td>
        <td class="text-center">${kl:phoneMasking(list.hpNo)}</td>
        <td class="text-center">${kl:emailMasking(list.email)}</td>
        <td class="text-center">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
        <td class="text-center">${list.modName}</td>
        <td class="text-center">${kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td>
        <input type="hidden" class="rprsntNm" value="${list.rprsntNm}"/>
        <input type="hidden" class="layerZipcode" value="${list.cmpnZipcode}"/>
        <input type="hidden" class="layerBscAddr" value="${list.cmpnBscAddr}"/>
        <input type="hidden" class="layerDtlAddr" value="${list.cmpnBscDtlAddr}"/>
        <input type="hidden" class="cmpnMpleCnt" value="${list.cmpnMpleCnt}"/>
        <input type="hidden" class="cmpnTelNo" value="${list.cmpnTelNo}"/>
        <input type="hidden" class="cmpnSlsPmt" value="${list.cmpnSlsPmt}"/>
        <input type="hidden" class="cmpnSlsYear" value="${list.cmpnSlsYear}"/>
        <input type="hidden" class="mjrPrdct1" value="${list.mjrPrdct1}"/>
        <input type="hidden" class="mjrPrdct2" value="${list.mjrPrdct2}"/>
        <input type="hidden" class="mjrPrdct3" value="${list.mjrPrdct3}"/>
        <input type="hidden" class="qlty5StarCd" value="${list.qlty5StarCd}"/>
        <input type="hidden" class="qlty5StarYear" value="${list.qlty5StarYear}"/>
        <input type="hidden" class="pay5StartCd" value="${list.pay5StarCd}"/>
        <input type="hidden" class="pay5StarYear" value="${list.pay5StarYear}"/>
        <input type="hidden" class="tchlg5StarCd" value="${list.tchlg5StarCd}"/>
        <input type="hidden" class="tchlg5StarYear" value="${list.tchlg5StarYear}"/>
        <input type="hidden" class="cbsnSeq" value="${list.cbsnSeq}"/>
        <input type="hidden" class="seqNm" value="${list.seqNm}"/>
        <input type="hidden" class="score" value="${list.score}"/>
        <input type="hidden" class="year" value="${list.year}"/>
        <input type="hidden" class="crtfnCmpnNm" value="${list.crtfnCmpnNm}"/>
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

