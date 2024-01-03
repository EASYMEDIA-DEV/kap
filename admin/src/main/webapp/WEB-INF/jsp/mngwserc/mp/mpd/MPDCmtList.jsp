<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpd/MPDCmtKenDaySrchLayer.jsp">
    <jsp:param name="selPer" value="popup" />
</jsp:include>
<jsp:include page="/WEB-INF/jsp/mngwserc/mp/mpd/MPDCmtKenMonthSrchLayer.jsp">
    <jsp:param name="selPer" value="popup" />
</jsp:include>
<div class="container-fluid ">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/mp/mpd/MPDCmtListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
        <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="board">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <input type="hidden" id="popData" name="popData"  />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="lgnSsnId" value="${rtnData.lgnSsnId}">
            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="${ rtnData.detailsKey }" />
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="등록/수정기간" />
                <jsp:param name="periodType" value="notSelect" />
                <jsp:param name="selPer" value="select" />
            </jsp:include>
            <!--기간 검색 종료-->
            <fieldset>

                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">위원구분</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'MEM_CD030')}">
                                <c:if test="${fn:contains(cdList.dpth, '3')}">
                                    <label class="checkbox-inline c-checkbox">
                                        <input type="checkbox" class="checkboxSingle" data-name="cmssrTypeList" value="${cdList.cd}" />
                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                    </label>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">재직여부</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="cdList" items="${cdDtlList.MEM_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'MEM_CD040')}">
                                    <label class="checkbox-inline c-checkbox">
                                        <input type="checkbox" class="checkboxSingle" data-name="cmssrWorkList" value="${cdList.cd}" />
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
                                    <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>휴대폰번호</option>
                                    <option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>이메일</option>
                                    <option value="8" <c:if test="${rtnData.f eq '8'}">selected</c:if>>최초등록자</option>
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
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" id="monthKen">월별 출근부</button>
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" id="dayKen">일일 근태현황</button>
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">엑셀다운로드</button>
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
                        <th class="text-center">아이디</th>
                        <th class="text-center">이름</th>
                        <th class="text-center">위원구분</th>
                        <th class="text-center">업종/분야</th>
                        <th class="text-center">재직여부</th>
                        <th class="text-center">휴대폰번호</th>
                        <th class="text-center">이메일</th>
                        <th class="text-center">최초 등록자</th>
                        <th class="text-center">최초 등록일시</th>
                        <th class="text-center">최종 수정자</th>
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

    </div>
    <div class="clearfix">
        <div class="pull-left">
            <button type="button" class="btn btn-sm btn-default" id="btnDelete" >선택삭제</button>
        </div>
        </form>
        <div style="float:right">
            <button type="button" class="btn btn-sm btn-success dtl-tab" id="btnWrite" >등록</button>
        </div>
    </div>

</div>

<div class="modal fade excel-down" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-center" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 엑셀 다운로드
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <div class="modal-body">
                <div class="form-group ">
                    <p><em class="ion-play mr-sm"></em>사유입력</p>
                    <div class="col-sm-12">
                        <textarea maxlength="30" class="col-sm-12 pv" style="resize: vertical;" rows="10" placeholder="사유를 입력하세요." id="rsn" title="사유" oninput="cmmCtrl.checkMaxlength(this);"></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-footer row">
                <div class="text-center">
                    <button type="button" class="btn btn-primary down mt">다운로드</button>
                </div>
            </div>
        </div>
    </div>

</div>

