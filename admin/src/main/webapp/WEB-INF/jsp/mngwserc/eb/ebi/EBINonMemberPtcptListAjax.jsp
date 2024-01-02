<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.ptcptList}">
    <c:forEach var="ptcptList" items="${rtnData.ptcptList}" varStatus="status">
      <tr  data-total-count="${rtnData.totalCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${ptcptList.ptcptSeq}" name="delValueList" class="checkboxSingle notRequired" data-ptcpt-seq="${ptcptList.ptcptSeq}" data-edctn-seq="${ptcptList.edctnSeq}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center">${ptcptList.name}</td><!--이름-->
        <td class="text-center">${ptcptList.ptcptCmpnNm}</td><!--부품사명-->
        <td class="text-center">${kl:bsnmNoConvert(ptcptList.ptcptBsnmNo)}</td><!--사업자등록번호-->
        <td class="text-center">${ptcptList.deptCdNm} ${ptcptList.deptDtlNm == '' ? '' : '('+=ptcptList.deptDtlNm+=')'} </td><!-- 부서 -->
        <td class="text-center">${ptcptList.pstnCdNm}</td><!-- 직급 -->
        <td class="text-center">${ptcptList.hpNo}</td><!-- 휴대폰번호 -->
        <td class="text-center">${ptcptList.email}</td><!-- 이메일 -->
        <td class="text-center">
            ${ empty ptcptList.regDtm ? '-' : kl:convertDate(ptcptList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
        </td><!-- 교육신청일 -->
        <td class="text-center"><button type="button" class="btn btn-info btn-default" id="cancelEdctn" data-ptcpt-seq="${ptcptList.ptcptSeq}" data-edctn-seq="${ptcptList.edctnSeq}">신청취소</button></td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="11" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>



