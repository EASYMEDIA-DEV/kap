<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/eb/ebc/EBCVisitEduListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
        <form class="form-horizontal" id="frmSearch" name="frmSearch" method="post" action="" data-del-type="account">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="" />
            <input type="hidden" id="vstSeq" name="vstSeq" value="" />
            <input type="hidden" id="memSeq" name="memSeq" value="" />
            <input type="hidden" id="vstRsltSeq" name="vstRsltSeq" value="" />
            <input type="hidden" id="appctnBsnmNo" name="appctnBsnmNo" value="" />

            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchOption" value="신청일,수정일,교육시작일,교육종료일" />
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
                            <c:if test="${fn:length(cdList.cd) eq 12 and fn:contains(cdList, 'COMPANY01')}">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" value="${cdList.cd}" />
                                    <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청분야</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'EBC_VISIT_CD01') and fn:length(cdList.cd) eq 17}">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle" data-name="appctnFldList" value="${cdList.cd}" />
                                    <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">교육현황</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="cdList" items="${cdDtlList.EBC_VISIT_CD}" varStatus="status">
                            <c:if test="${fn:contains(cdList, 'EBC_VISIT_CD02') and cdList.cd ne 'EBC_VISIT_CD02'}">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle" data-name="edctnSttsCdList" value="${cdList.cd}" />
                                    <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                </label>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">신청 소재지</label>
                    <div class="col-sm-4">
                        <div class="row">
                            <div class="col-sm-3 pr0">
                                <select class="form-control input-sm classType" id="firstRgnsCd" name="firstRgnsCd" title="소재지 첫번째지역">
                                    <option value="">전체</option>
                                    <c:forEach var="addrList" items="${cdDtlList.ADDR_CD}" varStatus="status">
                                        <c:if test="${fn:contains(addrList.cd,'MAIN')}">
                                            <option value="${addrList.cd}" <c:if test="${rtnDto.firstRgnsCd eq addrList.cd}">selected</c:if>>${addrList.cdNm} </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-3 pr0">
                                <select class="form-control input-sm wd-sm notRequired scndRgnsCd" name="scndRgnsCd" id="scndRgnsCd" title="소재지 두번째지역" data-scndRgnsCd="${rtnDtl.scndRgnsCd}">
                                    <option value="">전체</option>
                                </select>
                            </div>
                        </div>
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
                                    <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>부품사명</option>
                                    <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>사업자등록번호</option>
                                    <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>지역</option>
                                    <option value="4" <c:if test="${rtnData.f eq '4'}">selected</c:if>>신청자 이름</option>
                                    <option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>신청자 휴대폰번호</option>
                                    <option value="6" <c:if test="${rtnData.f eq '6'}">selected</c:if>>신청자 이메일</option>
                                    <option value="7" <c:if test="${rtnData.f eq '7'}">selected</c:if>>최종수정자</option>
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
                        <th class="text-center" rowspan="3">번호</th>
                        <th class="text-center" rowspan="3">교육상태</th>
                        <th class="text-center" colspan="13">신청정보</th>
                        <th class="text-center" colspan="4">교육실적</th>

                        <th class="text-center" rowspan="3">최종 수정자</th>
                        <th class="text-center" rowspan="3">최종 수정일시</th>
                    </tr>
                    <tr>
                        <th class="text-center" colspan="4" style="border-right: 1px solid rgba(162, 162, 162, 0.16);">부품사정보</th>
                        <th class="text-center" colspan="6" style="border-right: 1px solid rgba(162, 162, 162, 0.16);">신청내용</th>
                        <th class="text-center" colspan="3" style="border-right: 1px solid rgba(162, 162, 162, 0.16);">신청정보</th>
                        <th class="text-center" colspan="4">개요</th>
                    </tr>
                    <tr>
                        <th class="text-center">부품사명</th>
                        <th class="text-center">사업자등록번호</th>
                        <th class="text-center" >구분</th>
                        <th class="text-center" style="border-right: 1px solid rgba(162, 162, 162, 0.16);">규모</th>

                        <th class="text-center">신청일시</th>
                        <th class="text-center">신청분야</th>
                        <th class="text-center">교육희망일</th>
                        <th class="text-center">교육장소</th>
                        <th class="text-center">교육인원</th>
                        <th class="text-center" style="border-right: 1px solid rgba(162, 162, 162, 0.16);">교육시간</th>
                        <th class="text-center">이름(아이디)</th>
                        <th class="text-center">휴대폰번호</th>
                        <th class="text-center" style="border-right: 1px solid rgba(162, 162, 162, 0.16);">이메일</th>

                        <th class="text-center">실적마감여부</th>
                        <th class="text-center">확정주제</th>
                        <th class="text-center">교육기간</th>
                        <th class="text-center">수료인원(명)</th>
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
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade excel-down" tabindex="-1" role="dialog">
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