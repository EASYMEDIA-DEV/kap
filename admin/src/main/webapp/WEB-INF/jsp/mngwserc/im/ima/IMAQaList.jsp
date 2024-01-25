<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/im/ima/IMAQaListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
        <form class="form-horizontal" name="frmSearch" method="post" action="">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="" />
            <input type="hidden" id="rsumeCd" name="rsumeCd" value="" />
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchOption" value="문의 등록일,답변 등록일" />
            </jsp:include>
            <!--기간 검색 종료-->
            <%--문의 유형 시작--%>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">문의 유형</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-3 pr0">
                                <select class="form-control input-sm" data-name="inqFir" id="inqFir">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                        <c:if test="${fn:length(cdList.cd) < 6}">
                                            <option value="${cdList.cd}" <c:if test="${rtnData.inqFir eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-3 pr0">
                                <select class="form-control input-sm" data-name="inqSec" id="inqSec">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                        <option class="inqSec" style="display: none;" value="${cdList.cd}" <c:if test="${rtnData.inqSec eq cdList.cd}">selected</c:if>>${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <%--문의 유형 종료--%>
            <%--진행 상태 시작--%>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">진행상태</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="rsumeCdList" value="SYN" <c:if test="${fn:contains(rtnData.rsumeCdList, 'SYN')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 접수대기
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="rsumeCdList" value="SYNACK" <c:if test="${fn:contains(rtnData.rsumeCdList, 'SYNACK')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 접수완료
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="rsumeCdList" value="ACK" <c:if test="${fn:contains(rtnData.rsumeCdList, 'ACK')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 답변완료
                        </label>
                    </div>
                </div>
            </fieldset>
            <%--진행 상태 종료--%>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">검색키워드</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-3 pr0">
                                <select class="form-control input-sm" data-name="f">
                                    <option value="">전체</option>
                                    <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>제목</option>
                                    <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>작성자</option>
                                    <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>이메일</option>
                                    <option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>연락처</option>
                                </select>
                            </div>
                            <div class="col-sm-9 pr0">
                                <input type="text" class="form-control input-sm" data-name="q" id="q" value="${rtnData.q}" maxlength="50" />
                            </div>
                        </div>
                    </div>
                    <div class="pull-left ml-sm">
                        <button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
                        <button type="button" class="btn btn-default btn-sm" id="btnRefreshSearch">초기화</button>
                    </div>
                </div>
            </fieldset>
            <!--단 처리-->
            <hr class="mt0" />
            <div class="clearfix">
                <h6 class="pull-left mt0">
                    <em class="ion-play mr-sm"></em>${pageTitle} 목록(총 <span id="listContainerTotCnt">0</span>건)
                </h6>
                <div class="pull-right ml-sm">
                    <select class="form-control input-sm listRowSizeContainer" >
                        <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                            <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                        </jsp:include>
                    </select>
                </div>
                <div class="pull-right">
                    <button type="button" id="btnQaPic" class="btn btn-inverse btn-sm mb-sm">문의담당자 관리</button>
                </div>
            </div>
            <!--리스트 시작 -->
            <div class="table-responsive col-sm-12 p0 m0">
                <table class="table table-hover table-striped" >
                    <thead>
                        <tr>
                            <th class="text-center">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
                                    <span class="ion-checkmark-round"></span>
                                </label>
                            </th>
                            <th class="text-center">번호</th>
                            <th class="text-center">1차 문의유형</th>
                            <th class="text-center">2차 문의유형</th>
                            <th class="text-center">제목</th>
                            <th class="text-center">작성자</th>
                            <th class="text-center">이메일</th>
                            <th class="text-center">연락처</th>
                            <th class="text-center">문의 등록일</th>
                            <th class="text-center">답변 등록일</th>
                            <th class="text-center">진행상태</th>
                        </tr>
                    </thead>
                    <!-- 리스트 목록 결과 -->
                    <tbody id="listContainer"/>
                </table>
                <!-- 페이징 버튼 -->
                <div id="pagingContainer"/>
            </div>
            <!--리스트 종료 -->
        </form>
        <jsp:include page="/WEB-INF/jsp/mngwserc/im/ima/IMAQaPicLayer.jsp"></jsp:include>
    </div>
</div>

