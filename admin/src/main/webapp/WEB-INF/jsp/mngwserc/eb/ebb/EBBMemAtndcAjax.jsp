<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 교육차수관리 > 참여자 목록 > 개인별 출석부 EBBMemAtndcLayer -> EBBMemAtndcAjax  -->
<c:choose>
  <c:when test="${ not empty rtnData}">

    <!-- 회원정보 -->
    <fieldset>
      <div class="form-group text-sm">
        <div class="col-sm-12">
          <h6 class="mt0"><em class="ion-play mr-sm"></em>회원정보</h6>
        </div>
      </div>
    </fieldset>
    <fieldset>
      <div class="form-group text-sm">
        <label class="col-sm-1 control-label">아이디<span class="star"> *</span></label>
        <div class="col-sm-11">
          <p class="form-control-static memId">${rtnData[0].id}</p>
        </div>
      </div>
    </fieldset>
    <fieldset>
      <div class="form-group text-sm">
        <label class="col-sm-1 control-label">이름<span class="star"> *</span></label>
        <div class="col-sm-11">
          <p class="form-control-static memName">${rtnData[0].name}</p>
        </div>
      </div>
    </fieldset>


    <hr class="mt0" />
    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
      <table class="table table-hover table-striped">
        <thead>
        <tr>
          <th class="text-center" rowspan="3">교육일</th>
          <th class="text-center" rowspan="3">출석</th>
          <th class="text-center" rowspan="3">퇴실</th>
          <th class="text-center" rowspan="3">비고</th>
        </tr>
        </thead>

        <!-- 리스트 목록 결과 -->
        <tbody id="memAtndcListLayerContainer">
        <c:forEach var="ptcptList" items="${rtnData}" varStatus="status">
          <tr>
            <td class="text-center edctnDt">${ptcptList.edctnDt}</td><!--교육일-->
            <td class="text-center atndcDtm" data-orgatndcHour="${kl:convertDate(ptcptList.atndcDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}">
              <c:if test="${empty ptcptList.atndcDtm}">
                <input type="time" class="notRequired" name="atndcDtm" id="atndcDtm" value="">
              </c:if>
              <c:if test="${not empty ptcptList.atndcDtm}">
                ${kl:convertDate(ptcptList.atndcDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}
              </c:if>
            </td><!--출석-->
            <td class="text-center lvgrmDtm" data-orglvgrmHour="${kl:convertDate(ptcptList.lvgrmDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}">
              <c:if test="${empty ptcptList.lvgrmDtm}">
                <input type="time" class="notRequired" name="lvgrmDtm" id="lvgrmDtm" value="">
              </c:if>
              <c:if test="${not empty ptcptList.lvgrmDtm}">
                ${kl:convertDate(ptcptList.lvgrmDtm, 'yyyy-MM-dd HH:mm', 'HH:mm', '-')}
              </c:if>
            </td><!--퇴실-->

            <td class="text-center etcNm" data-orgEtcNm="${ptcptList.etcNm}">
              <input type="text" class="form-control input-sm notRequired" id="etcNm" name="etcNm" value="${ptcptList.etcNm}" title="비고"maxlength="50" placeholder="" style="min-width: 150px;"/>
            </td>
          </tr>
        </c:forEach>


        </tbody>


      </table>
    </div>
    <!--리스트 종료 -->








  </c:when>
  <c:otherwise>

  </c:otherwise>
</c:choose>