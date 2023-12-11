<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 만족도종합결과(Modal) -->
<c:set var="today" value="<%=new java.util.Date()%>" />
<fmt:formatDate value="${today}" pattern="yyyy" var="start"/>
<div class="modal fade consultSuveyRsltLayer" tabindex="-1" role="dialog" data-controller="controller/cb/cbb/CBBConsultSuveyRsltLayertCtrl">
  <div class="modal-dialog modal-lg modal-center" role="document" style="width:1500px;">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" >▣ 만족도 종합결과 검색
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </h5>
      </div>
      <form>
          <!-- 현재 페이징 번호 -->
          <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
          <!-- 페이징 버튼 사이즈 -->
          <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
          <input type="hidden" id="listRowSize" name="listRowSize" value="10" />
          <!-- CSRF KEY -->
          <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
          <!-- 레이어 여부 -->
          <input type="hidden" name="srchLayer" value="Y" />
          <input type="hidden" id="rtnBsnGubun" name="rtnBsnGubun" value="" />
          <div class="modal-body">
              <fieldset>
                <div class="form-group text-sm">
                  <label class="col-sm-1 control-label">사업연도</label>
                  <div class="col-sm-6">
                    <div class="col-sm-3 pr0">
                      <select class="form-control input-sm" name="rtnBsnYear">
                        <option value="">선택</option>
                        <c:forEach begin="0" end="${start-2013}" var="result" step="1">
                           <option value="${start-result}" >${start-result}</option>
                        </c:forEach>
                      </select>
                    </div>
                    <div class="col-sm-4">
                      <button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
                      <button type="button" class="btn btn-default btn-sm btnRefresh" id="btnRefresh">초기화</button>
                    </div>
                  </div>
                </div>
              </fieldset>

              <hr class="mt0" />

              <div class="clearfix">
                  <h6 class="pull-left mt0">
                    <em class="ion-play mr-sm"></em>만족도 종합결과 목록
                  </h6>
                  <div class="pull-right ml-sm">
                    <select class="form-control input-sm listRowSizeContainer" >
                      <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                        <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                      </jsp:include>
                    </select>
                  </div>
              </div>
              <!--VUE 영역 시작 -->
              <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                <table class="table table-hover table-striped" >
                  <thead>
                  <tr>
                    <th class="text-center">번호</th>
                    <th class="text-center">사업연도</th>
                    <th class="text-center">대상부품사</th>
                    <th class="text-center">설문완료<br/>부품사</th>
                    <th class="text-center">정규부품사</th>
                    <th class="text-center">단기부품사</th>
                    <th class="text-center">담당위원</th>
                    <th class="text-center">종합점수평균</th>
                    <th class="text-center">정규점수평균</th>
                    <th class="text-center">단기점수평균</th>
                    <th class="text-center">엑셀다운로드</th>
                  </tr>
                  </thead>
                  <!-- 리스트 목록 결과 -->
                  <tbody id="listContainer"/>
                </table>
                <!-- 페이징 버튼 -->
                <div id="pagingContainer"/>
              </div>
            </div>
            <div class="modal-footer row">
            </div>
        <!--리스트 종료 -->
      </form>
    </div>
  </div>
</div>
