<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade mpePartsCompanySrchLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/mp/mpe/MPEPartsCompanyListCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 부품사 검색
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <div class="modal-body">
                <form>
                    <!-- 현재 페이징 번호 -->
                    <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
                    <!-- 페이징 버튼 사이즈 -->
                    <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
                    <input type="hidden" id="listRowSize" name="listRowSize" value="10" />
                    <!-- CSRF KEY -->
                    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <!-- 검색 여부 -->
                    <input type="hidden" id="srchPartsComLayer" name="srchPartsComLayer" value="Y" />
                    <!--기간 검색 시작-->
                    <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                        <jsp:param name="srchText" value="등록/수정기간" />
                        <jsp:param name="srchOption" value="등록일,수정일" />
                    </jsp:include>
                    <div id="selectBoxArea"></div>
                    <fieldset class="last-child">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">검색키워드</label>
                            <div class="col-sm-4">
                                <div class="row">
                                    <div class="col-sm-3 pr0">
                                        <select class="form-control input-sm" data-name="f">
                                            <option value="">전체</option>
                                            <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>대표자명</option>
                                            <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>부품사명</option>
                                            <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>지역</option>
                                            <option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>부품사코드</option>
                                            <option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>사업자등록번호</option>
                                            <option value="6" <c:if test="${rtnData.f eq '6'}">selected</c:if>>전화번호</option>
                                            <option value="7" <c:if test="${rtnData.f eq '7'}">selected</c:if>>최초 등록자</option>
                                            <option value="8" <c:if test="${rtnData.f eq '8'}">selected</c:if>>최종 수정자</option>
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
                            <em class="ion-play mr-sm"></em>${pageTitle} 목록 (총 <span id="partsComListContainerTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="" />
                                </jsp:include>
                            </select>
                        </div>
                        <div class="pull-right">
                        </div>
                    </div>
                    <!--VUE 영역 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
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
                                <th class="text-center">대표자명</th>
                                <th class="text-center">부품사명</th>
                                <th class="text-center">부품사코드</th>
                                <th class="text-center">구분</th>
                                <th class="text-center">규모</th>
                                <th class="text-center">사업자등록번호</th>
                                <th class="text-center">지역</th>
                                <th class="text-center">매출액(억원)</th>
                                <th class="text-center">직원수</th>
                                <th class="text-center">전화번호</th>
                                <th class="text-center">최초 등록자</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="partsComListContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="partsComPagingContainer"/>
                    </div>
            </div>
            <div class="modal-footer row">
                <div class="text-center">
                    <button type="button" class="btn btn-success down mt btnPartsCompanyLayerChoice">선택</button>
                </div>
            </div>
            <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>