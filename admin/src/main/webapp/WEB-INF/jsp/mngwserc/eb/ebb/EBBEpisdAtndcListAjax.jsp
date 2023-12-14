<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:choose>
  <c:when test="${ not empty rtnData.ptcptList}">
    <c:forEach var="ptcptList" items="${rtnData.ptcptList}" varStatus="status">
      <tr  data-total-count="${rtnData.totalCount}" >
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">
            <input type="checkbox" value="${ptcptList.ptcptSeq}" name="delValueList" class="checkboxSingle notRequired" data-ptcpt_seq="${ptcptList.ptcptSeq}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center">${ptcptList.id}</td>
        <td class="text-center">${ptcptList.name}</td>
        <td class="text-center">${ptcptList.cmpnNm}</td>
        <td class="text-center">${ptcptList.ctgryNm}</td>
        <td class="text-center">${kl:bsnmNoConvert(ptcptList.ptcptBsnmNo)}</td><!--사업자등록번호-->
        <td class="text-center">${ptcptList.deptCdNm} ${ptcptList.deptDtlNm == '' ? '' : '('+=ptcptList.deptDtlNm+=')'} </td><!-- 부서 -->
        <td class="text-center">${ptcptList.pstnCdNm}</td><!-- 직급 -->
        <td class="text-center">${ptcptList.hpNo}</td>
        <td class="text-center">${ptcptList.email}</td>
        <td class="text-center">${ptcptList.eduDtm}</td>
        <c:forEach var="atndcList" items="${ptcptList.atndcList}" varStatus="status">
            <td class="text-center" data-targetDt="${atndcList.edctnDt}">
              <input type="time" class="form-control input-sm" id="atndcDtm" name="atndcDtm" value="${kl:convertDate(atndcList.atndcDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}" title="출석" data-orgatndcHour="${kl:convertDate(atndcList.atndcDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}" data-edctnDt="${atndcList.edctnDt}" data-atndcDt="${kl:convertDate(atndcList.atndcDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd', '-')}" maxlength="50" placeholder="" style="min-width: 150px;"/>
            </td><!--출석-->
            <td class="text-center">
              <input type="time" class="form-control input-sm" id="lvgrmDtm" name="lvgrmDtm" value="${kl:convertDate(atndcList.lvgrmDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}" title="퇴실" data-orglvgrmHour="${kl:convertDate(atndcList.lvgrmDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}" data-edctnDt="${atndcList.edctnDt}" data-lvgrmDt="${kl:convertDate(atndcList.lvgrmDtm, 'yyyy-MM-dd HH:mm', 'yyyy-MM-dd', '-')}" maxlength="50" placeholder="" style="min-width: 150px;"/>

            </td><!--퇴실-->
            <td class="text-center">
              <input type="text" class="form-control input-sm notRequired" id="etcNm" name="etcNm" value="${atndcList.etcNm}" data-edctnDt="${atndcList.edctnDt}" data-orgEtcNm="${atndcList.etcNm}" title="비고"maxlength="50" placeholder="" style="min-width: 150px;"/>
            </td><!--비고-->

        </c:forEach>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="10+${fn:length(tableAtndcList) * 3}" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
    <script>
      var size = $('[data-atndcsize]').data("atndcsize");
      $('[data-atndcsize]').find("tr:last").find("td:last").attr("colspan", 10+size);
    </script>
  </c:otherwise>
</c:choose>



