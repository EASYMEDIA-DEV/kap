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
        <td class="text-center">${list.prntCdNm} > ${list.ctgryCdNm}</td><!--과정분류-->
        <td class="text-center">${list.nm}</td><!--과정명-->
        <td class="text-center">${list.stduyMthdCdNm}</td><!--학습방식-->
        <td class="text-center">${list.stduyDdCdNm}일/${list.stduyTimeCdNm}시간</td><!--학습시간-->
        <td class="text-center">${list.episdYear}</td><!--년도-->
        <td class="text-center">
            <c:choose>
              <c:when test="${ eBBEpisdDTO.srchLayer eq 'Y'}">
                ${list.episdOrd}회차
              </c:when>
            <c:otherwise>
              <a href="javascript:" class="listView" data-details-key="${list.edctnSeq}" data-episd-year="${list.episdYear}" data-episd-ord="${list.episdOrd}">
                  ${list.episdOrd}회차
              </a>
            </c:otherwise>
          </c:choose>
        </td><!--회차-->
        <td class="text-center">${kl:convertDate(list.accsStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} ~ ${kl:convertDate(list.accsEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td><!--접수기간-->
        <td class="text-center">${list.accsStatusNm}</td><!--접수상태-->
        <td class="text-center">${kl:convertDate(list.edctnStrtDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')} ~ ${kl:convertDate(list.edctnEndDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-')}</td><!--교육기간-->
        <td class="text-center">${list.bdgetExpnsNm}</td><!--실적마감여부-->
        <td class="text-center">${list.edctnStatusNm}</td><!--교육상태-->
        <td class="text-center">${list.isttrName} </td><!--이름-->
        <td class="text-center">${list.ffltnNm} <c:if test="${list.isttrOutCnt ne ''}">외 ${list.isttrOutCnt}명 </c:if></td> <!--소속-->
        <td class="text-center">${list.fxnumCnt}</td><!--정원-->
        <td class="text-center">${list.accsCnt}</td><!--신청자수-->
        <td class="text-center">${list.rcrmtMthdCdNm}</td><!--모집 방식-->
        <td class="text-center">${list.picNm}</td><!--담당자 이름-->
        <td class="text-center">${list.picEmail}</td><!--담당자 이메일-->
        <td class="text-center">${list.picTelNo}</td><!--담당자 전화번호-->
        <td class="text-center">${list.placeNm}</td><!--교육장소-->
        <td class="text-center">${list.regName}(${list.regId})</td><!--최초 등록자-->
        <td class="text-center">${list.regDtm}</td><!--최초등록일시-->
        <td class="text-center">${list.modName}(${list.modId})</td><!--최종수정자-->
        <td class="text-center">${ empty list.modDtm ? '-' : kl:convertDate(list.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td><!--최종 수정일시-->
        <td class="text-center">${ kl:decode(list.expsYn, 'Y', '활성', '비활성') }</td><!--노출여부-->
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="25" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>