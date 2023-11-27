
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="modal fade part-modal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-lg modal-center" style="width: fit-content" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >회원 검색
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <div class="modal-body">
                <!-- 부품사 회원 검색 레이어 팝업(Modal) -->
                <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="account">
                    <!-- 현재 페이징 번호 -->
                    <input type="hidden" id="pageIndex" name="pageIndex" value="${ empty rtnData.pageIndex ? 1 : rtnData.pageIndex}" />

                    <!-- 페이징 버튼 사이즈 -->
                    <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ empty rtnData.pageRowSize ? 10 : rtnData.pageRowSize}" />
                    <input type="hidden" id="listRowSize" name="listRowSize" value="${ empty rtnData.listRowSize ? 10 : rtnData.listRowSize}" />
                    <!-- CSRF KEY -->
                    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                    <input type="hidden" id="selPartUser" name="selPartUser" value="" />

                    <!--기간 검색 시작-->
                    <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                        <jsp:param name="srchText" value="등록/수정기간" />
                        <jsp:param name="periodType" value="notSelect" />
                        <jsp:param name="selPer" value="select" />
                    </jsp:include>
                    <!--기간 검색 종료-->
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">구분</label>
                            <div class="col-sm-5">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxAll" />
                                    <span class="ion-checkmark-round"></span> 전체
                                </label>
                                <c:forEach var="cdList" items="${cdDtlList.COMPANY_TYPE}" varStatus="status">
                                    <c:if test="${fn:contains(cdList, 'COMPANY010')}">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" value="${cdList.cd}" />
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
                                    <div class="col-sm-3 pr0">
                                        <select class="form-control input-sm" data-name="f">
                                            <option value="">전체</option>
                                            <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>아이디</option>
                                            <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>이름</option>
                                            <option value="2" <c:if test="${rtnData.f eq '6'}">selected</c:if>>부품사명</option>
                                            <option value="2" <c:if test="${rtnData.f eq '7'}">selected</c:if>>사업자등록번호</option>
                                            <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>휴대폰번호</option>
                                            <option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>이메일</option>
                                            <option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>최종수정자</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-9 pr0">
                                        <input type="text" class="form-control input-sm" id="q" data-name="q" value="${rtnData.q}" maxlength="50" />
                                    </div>
                                </div>
                            </div>
                            <div class="pull-left ml-sm">
                                <button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
                                <button type="button" class="btn btn-default btn-sm" id="btnRefresh">초기화</button>
                            </div>
                        </div>
                    </fieldset>


                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>${pageTitle} 목록 (총 <span id="listContainerTotCnt">0</span> 건)
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
                        </div>
                    </div>
                    <!--VUE 영역 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center"></th>
                                <th class="text-center">번호</th>
                                <th class="text-center">아이디</th>
                                <th class="text-center">이름</th>
                                <th class="text-center">부품사명</th>
                                <th class="text-center">구분</th>
                                <th class="text-center">규모</th>
                                <th class="text-center">사업자등록번호</th>
                                <th class="text-center">휴대폰번호</th>
                                <th class="text-center">이메일</th>
                                <th class="text-center">가입일</th>
                                <th class="text-center">최종 수정자</th>
                                <th class="text-center">수정 일시</th>
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
            <div class="modal-footer row">
                <div class="text-center">
                    <button type="button" id="btnModalSelect" class="btn btn-primary">선택</button>
                </div>
            </div>
        </div>
    </div>
</div>