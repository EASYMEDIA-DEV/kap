<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/wb/wbl/WBLSurveyListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
        <form class="form-horizontal" id="frmSearch" name="frmSearch" method="post" action="">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="" />
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchOption" value="등록일,수정일,참여일" />
            </jsp:include>

            <!--기간 검색 종료-->
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">년도/회차</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-4">
                                <select class="form-control input-sm" name="year">
                                    <option value="">선택</option>
                                    <c:set var="startYear" value="2023"/>
                                        <c:forEach begin="0" end="10" var="year" step="1">
                                            <option value="${startYear+year}" <c:if test="${rtnData.year eq startYear+year}">selected</c:if>>${startYear+year}년</option>
                                        </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-4">
                                <select class="form-control input-sm" name="episd">
                                    <option value="">선택</option>
                                    <c:forEach begin="1" end="10" var="episd" step="1">
                                        <option value="${episd}" <c:if test="${rtnData.episd eq episd}">selected</c:if>>${episd}차</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">참여여부</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="cmpltnYnList" value="Y"  <c:if test="${fn:contains(rtnData.cmpltnYnList, 'Y')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 참여
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="cmpltnYnList" value="N"  <c:if test="${fn:contains(rtnData.cmpltnYnList, 'N')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 미참여
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">검색키워드</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-3 pr0">
                                <select class="form-control input-sm" data-name="f">
                                    <option value="">전체</option>
                                    <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>1차사명</option>
                                    <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>1차사코드</option>
                                    <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>2차사명</option>
                                    <option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>2차사코드</option>
                                    <option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>최종등록자</option>
                                    <option value="6" <c:if test="${rtnData.f eq '6'}">selected</c:if>>최종수정자</option>
                                </select>
                            </div>
                            <div class="col-sm-9 pr0">
                                <input type="text" class="form-control input-sm" data-name="q" value="${rtnData.q}" maxlength="30" />
                            </div>
                        </div>
                    </div>
                    <div class="pull-left ml-sm">
                        <button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
                        <button type="button" class="btn btn-default btn-sm" id="btnRefresh">초기화</button>
                    </div>
                </div>
            </fieldset>
            <!--단 처리-->
            <hr class="mt0" />
            <div class="clearfix">
                <h6 class="pull-left mt0">
                    <em class="ion-play mr-sm"></em>${pageTitle} 목록(총 <span id="listContainerTotCnt">0</span>건)
                </h6>
                <div class="pull-right">
                    <select class="form-control input-sm listRowSizeContainer" >
                        <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                            <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                        </jsp:include>
                    </select>
                </div>
                <div class="pull-right mr-sm">
                    <button type="button" class="btn btn-danger btn-sm mb-sm" id="btnDelete">선택삭제</button>
                    <button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
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
                        <th class="text-center">년도</th>
                        <th class="text-center">회차</th>
                        <th class="text-center">1차 부품사</th>
                        <th class="text-center">1차 부품사 코드</th>
                        <th class="text-center">2차 부품사</th>
                        <th class="text-center">2차 부품사 코드</th>
                        <th class="text-center">참여상태</th>
                        <th class="text-center">점수</th>
                        <th class="text-center">응답업체수</th>
                        <th class="text-center">평균점수</th>
                        <th class="text-center">HKMC평균점수</th>
                        <th class="text-center">참여일</th>
                        <th class="text-center">최초등록자</th>
                        <th class="text-center">최초등록일시</th>
                        <th class="text-center">최종수정자</th>
                        <th class="text-center">최종수정일시</th>
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
    </div>
</div>
