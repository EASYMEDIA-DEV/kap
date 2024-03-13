<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/wb/wbc/WBCBSecurityListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>보안환경구축 신청부품사 검색</h6>
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
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchType" value="wbeb" />
                <jsp:param name="srchOption" value="신청일,최초등록일시,최종수정일시" />
            </jsp:include>
            <fieldset>
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">사업회차</label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <select class="form-control input-sm" data-name="f" id="episdYear" name="yearSearch">
                                <option value="">연도 전체</option>
                                <c:forEach var="rtnYear" items="${rtnYear}" varStatus="status">
                                    <option value="${rtnYear}" <c:if test="${rtnData.year eq rtnYear }">selected</c:if>>${rtnYear}</option>
                                </c:forEach>
                            </select>
                            <select class="form-control input-sm" data-name="g" id="episd" name="episdSearch">
                                <option value="">회차 전체</option>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">진행상태</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>

                        <c:forEach var="cdList" items="${classTypeList.PRO_TYPE}" varStatus="status">
                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE01')}">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="carbonCdList" name="cd" value="${cdList.cd}"/>
                                <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                            </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">선급금지급</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>

                        <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE03001_02')}">
                                <label class="checkbox-inline c-checkbox classType">
                                    <input type="checkbox" class="checkboxSingle" data-name="pmndvSttsList" name="cd" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.pmndvSttsList, cdList.cd)}">checked</c:if>/>
                                    <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">지원금지급</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>

                        <c:forEach var="cdList" items="${proTypeList.PRO_TYPE}" varStatus="status">
                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE03002_02')}">
                                <label class="checkbox-inline c-checkbox classType">
                                    <input type="checkbox" class="checkboxSingle" data-name="spprtSttsList" name="cd" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.spprtSttsList, cdList.cd)}">checked</c:if>/>
                                    <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset class="last-child">
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">검색키워드</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-9 pr0">
                                <input type="text" class="form-control input-sm" data-name="q" value="${rtnData.q}" maxlength="30" />
                            </div>
                            <div class="pull-left ml-sm">
                                <button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
                                <button type="button" class="btn btn-default btn-sm" id="btnRefresh">초기화</button>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <!--단 처리-->
            <hr class="mt0" />
            <div class="clearfix">
                <h6 class="pull-left mt0">
                    <em class="ion-play mr-sm"></em>보안환경구축 신청부품사 목록(총 <span id="listContainerTotCnt">0</span>건)
                </h6>
                <div class="pull-right ml-sm">
                    <select class="form-control input-sm listRowSizeContainer" >
                        <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                            <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                        </jsp:include>
                    </select>
                </div>
                <div class="pull-right">
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">엑셀다운로드</button>
                    <button type="button" class="btn btn-danger btn-sm mb-sm" id="btnDeleteList">선택삭제</button>
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
                        <th class="text-center">사업연도</th>
                        <th class="text-center">회차</th>
                        <th class="text-center">진행상태</th>
                        <th class="text-center">관리자<br>상태값</th>
                        <th class="text-center">선급금<br>지급</th>
                        <th class="text-center">지원금<br>지급</th>
                        <th class="text-center">부품사명</th>
                        <th class="text-center">사업자등록번호</th>
                        <th class="text-center">종된사업장번호</th>
                        <th class="text-center">구분</th>
                        <th class="text-center">규모</th>
                        <th class="text-center">신청자<br>(아이디)</th>
                        <th class="text-center">휴대폰번호</th>
                        <th class="text-center">이메일</th>
                        <th class="text-center">신청일시</th>-
                        <th class="text-center">사용자 수정일</th>
                        <th class="text-center">최종 수정자<br>(아이디)</th>
                        <th class="text-center">최종 수정일시</th>
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
