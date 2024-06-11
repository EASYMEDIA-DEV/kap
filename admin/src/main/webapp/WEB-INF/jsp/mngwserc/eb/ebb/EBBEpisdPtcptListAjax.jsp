<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 교육차수관리 > 참여자 목록 -->
<c:choose>
  <c:when test="${ not empty rtnData.ptcptList}">
    <c:forEach var="ptcptList" items="${rtnData.ptcptList}" varStatus="status">
      <tr  data-total-count="${rtnData.totalCount}" data-typea-count="${rtnData.typeACount}" data-typeb-count="${rtnData.typeBCount}" data-typec-count="${rtnData.typeCCount}" data-typed-count="${rtnData.typeDCount}">
        <td class="text-center">
          <label class="checkbox-inline c-checkbox">

            <c:choose>
              <c:when test="${ptcptList.eduStat eq '교육양도'}">
                <c:set var="trnsfYn" value="Y"/>
              </c:when>
              <c:otherwise>
                <c:set var="trnsfYn" value="N"/>
              </c:otherwise>
            </c:choose>

            <c:choose>

              <c:when test="${ptcptList.eduStat eq '교육대기'}">
                <c:set var="changeChk" value="changeChk"/>
              </c:when>
              <c:otherwise>
                <c:set var="changeChk" value=""/>
              </c:otherwise>

              <%--<c:when test="${ptcptList.eduStat ne '교육대기'}">
                <c:set var="disabledChk" value="disabled"/>
              </c:when>
              <c:otherwise>
                <c:set var="disabledChk" value=""/>
              </c:otherwise>--%>
            </c:choose>
            <input type="checkbox" value="${ptcptList.ptcptSeq}" name="delValueList" class="checkboxSingle notRequired ${changeChk}" data-ptcpt_seq="${ptcptList.ptcptSeq}" data-memSeq="${ptcptList.memSeq}" data-trnsf-yn="${trnsfYn}"/>
            <span class="ion-checkmark-round"></span>
          </label>
        </td>
        <td class="text-center">${ rtnData.totalCount - rtnData.firstIndex - status.index }</td>
        <td class="text-center">${ptcptList.id}</td>
        <td class="text-center">${ptcptList.name}</td><!--이름-->
        <td class="text-center">${ptcptList.cmpnNm}</td><!--부품사명-->
        <td class="text-center">${ptcptList.ctgryNm}</td><!--구분-->
        <td class="text-center">${kl:bsnmNoConvert(ptcptList.ptcptBsnmNo)}</td><!--사업자등록번호-->
        <td class="text-center">${ptcptList.deptCdNm} ${ptcptList.deptDtlNm == '' ? '' : '('+=ptcptList.deptDtlNm+=')'} </td><!-- 부서 -->
        <td class="text-center">${ptcptList.pstnCdNm}</td><!-- 직급 -->
        <td class="text-center">${ptcptList.hpNo}</td><!-- 휴대폰번호 -->
        <td class="text-center" data-email="${ptcptList.email}">${ptcptList.email}</td><!-- 이메일 -->
        <%--<td class="text-center">${ empty ptcptList.regDtm ? '-' : kl:convertDate(ptcptList.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }</td>--%><!-- 가입일 -->
        <td class="text-center">
            ${ empty ptcptList.eduDtm ? '-' : kl:convertDate(ptcptList.eduDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '-') }
        </td><!-- 교육신청일 -->
        <td class="text-center">${ptcptList.eduStat}</td><!-- 교육상태 -->
        <td class="text-center">

          <c:choose>
            <c:when test="${ptcptList.eduStat ne '교육양도'}">
              <c:if test="${eBBEpisdDTO.stduyMthdCd ne 'STDUY_MTHD02'}">
                <a href="#" class="btnMemAtndc" data-ptcptSeq="${ptcptList.ptcptSeq}" data-memSeq="${ptcptList.memSeq}" data-edctnSeq="${eBBEpisdDTO.edctnSeq}" data-episdOrd="${eBBEpisdDTO.episdOrd}" data-episdYear="${eBBEpisdDTO.episdYear}" >${ptcptList.eduAtndc}%</a>
              </c:if>
              <c:if test="${eBBEpisdDTO.stduyMthdCd eq 'STDUY_MTHD02'}">
                ${ptcptList.eduAtndc}%
              </c:if>
            </c:when>
            <c:otherwise>
              -
            </c:otherwise>
          </c:choose>



        </td><!-- 출석 -->
        <td class="text-center  form-inline">

          <c:choose>
            <c:when test="${ptcptList.eduStat ne '교육양도'}">
              <!--오프라인 평가인경우 입력창과 버튼출력, 일반 평가인경우 점수가 표기 -->
              <c:if test="${ptcptList.otsdExamPtcptYn eq 'Y'}">
                <input type="text" class="form-control input-sm numberChk notRequired" name="examScore" value="${ptcptList.examScore}" title="평가점수" maxlength="50" placeholder="" style="width:50px;" />점
              </c:if>
              <c:if test="${ptcptList.otsdExamPtcptYn ne 'Y'}">
                <c:choose>
                  <c:when test="${ not empty ptcptList.examPtcptSeq ||  not empty ptcptList.examScore}">
                    <a href="javascript:" class="btnExamPtcptSeq" data-exam-ptcpt-seq="${ptcptList.examPtcptSeq}" data-ptcpt-seq="${ptcptList.ptcptSeq}" data-mem-seq="${ptcptList.memSeq}">${kl:nvl(ptcptList.examScore, '0')}</a>
                  </c:when>
                  <c:otherwise>
                    -
                  </c:otherwise>
                </c:choose>
              </c:if>
            </c:when>
            <c:otherwise>
              -
            </c:otherwise>
          </c:choose>
        </td>
        <td class="text-center form-inline">

          <c:choose>
            <c:when test="${ptcptList.eduStat ne '교육양도'}">
              <input type="hidden" name="orgCmptnYn" id="orgCmptnYn" value="${ptcptList.cmptnYn}"/>
              <c:if test="${ptcptList.cmptnYn eq 'Y'}">
                <input type="hidden" class="notRequired" name="oflnExamDtm" id="oflnExamDtm" value="${ptcptList.oflnExamDtm}"/>
                <input type="hidden" class="notRequired" name="cmptnYn" id="cmptnYn" value="${ptcptList.cmptnYn}"/>
                수료
              </c:if>
              <c:if test="${ptcptList.cmptnYn eq 'U'}">
                <input type="hidden" class="notRequired" name="oflnExamDtm" id="oflnExamDtm" value="${ptcptList.oflnExamDtm}"/>
                <input type="hidden" class="notRequired" name="cmptnYn" id="cmptnYn" value="${ptcptList.cmptnYn}"/>
                불참
              </c:if>
              <c:if test="${ptcptList.cmptnYn eq 'N'}">
                <input type="hidden" class="notRequired" name="oflnExamDtm" id="oflnExamDtm" value="${ptcptList.oflnExamDtm}"/>
                <select class="form-control input-sm wd-sm" name="cmptnYn" id="cmptnYn" title="수료여부" style="width: 100px" data-org_cmptnYn="${ptcptList.cmptnYn}">
                  <option value="N" <c:if test="${ptcptList.cmptnYn eq 'N'}">selected</c:if>>미수료</option>
                  <option value="Y" <c:if test="${ptcptList.cmptnYn eq 'Y'}">selected</c:if>>수료</option>
                  <option value="U" <c:if test="${ptcptList.cmptnYn eq 'U'}">selected</c:if>>불참</option>
                </select>
              </c:if>

            </c:when>
            <c:otherwise>
              -
            </c:otherwise>

          </c:choose>


          <%--<button type="button" class="btn btn-inverse btn-sm cmptnYnUpdate">
            저장
          </button>--%>
        </td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr data-total-count="0">
      <td colspan="17" class="text-center">
        검색결과가 없습니다.<br>
        (등록된 데이터가 없습니다.)
      </td>
    </tr>
  </c:otherwise>
</c:choose>



