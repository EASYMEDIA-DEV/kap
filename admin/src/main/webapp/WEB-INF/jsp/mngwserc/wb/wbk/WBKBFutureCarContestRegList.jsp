<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/wb/wbk/WBKBFutureCarRegListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>신청팀 ${pageTitle} 검색</h6>
        <form class="form-horizontal" name="frmSearch" method="post" action="">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <!-- 상세로 이동시 및 삭제시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="" />
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchType" value="wbkb" />
                <jsp:param name="srchOption" value="신청일,최초등록일시,최종수정일시" />
            </jsp:include>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">연도<span class="star"> *</span></label>
                    <div class="col-sm-5" style="margin-left: -15px">
                        <div class="col-sm-6" style="margin-left: -15px">
                            <select class="form-control input-sm" id="optYear" name="year" title="연도">
                                <option value="">연도 전체</option>
                                <c:forEach var="optYear" items="${optYearList}" varStatus="status">
                                    <option value="${optYear}">${optYear}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">참여구분</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="cdList" items="${cdDtlList.WBK_PTN}" varStatus="status">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="ptcptTypeList" name="ptcptTypeList" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.ptcptTypeList, cdList.cd)}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">주제</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="wbkbRegCtg" items="${wbkbRegCtg}" varStatus="status">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="themeCdList" name="themeCdList" value="${wbkbRegCtg.cd}" <c:if test="${fn:contains(rtnData.themeCdList, wbkbRegCtg.cd)}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${wbkbRegCtg.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">시상부문</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="cdDtlList" items="${cdDtlList.WBK_AWD}" varStatus="status">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="wdcrmCdList" name="wdcrmCdList" value="${cdDtlList.cd}" <c:if test="${fn:contains(rtnData.wdcrmCdList, cdDtlList.cd)}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${cdDtlList.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">1차결과</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="wbkbRegFrt" items="${wbkbRegFrt}" varStatus="status">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="fresultCdList" name="fresultCdList" value="${wbkbRegFrt.cd}" <c:if test="${fn:contains(rtnData.fresultCdList, wbkbRegFrt.cd)}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${wbkbRegFrt.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">최종결과</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox classType">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <c:forEach var="wbkbRegLrt" items="${wbkbRegLrt}" varStatus="status">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="lresultCdList" name="lresultCdList" value="${wbkbRegLrt.cd}" <c:if test="${fn:contains(rtnData.lresultCdList, wbkbRegLrt.cd)}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${wbkbRegLrt.cdNm}
                            </label>
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
                                    <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>팀장명</option>
                                    <%--<option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>최초등록자</option>--%>
                                    <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>최종수정자</option>
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
                    <em class="ion-play mr-sm"></em>${pageTitle}신청팀 목록(총 <span id="listContainerTotCnt">0</span>건)
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
                    <button type="button" class="btn btn-info btn-sm btn-danger mb-sm" id="btn_delete">삭제</button>
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
                                <input type="checkbox" class="checkboxAll" />
                                <span class="ion-checkmark-round"></span>
                            </label>
                        </th>
                        <th class="text-center">번호</th>
                        <th class="text-center">사업연도</th>
                        <th class="text-center">팀장명</th>
                        <th class="text-center">참여구분</th>
                        <th class="text-center">주제</th>
                        <th class="text-center">시상부문</th>
                        <th class="text-center">서류심사</th>
                        <th class="text-center">1차결과</th>
                        <th class="text-center">최종결과</th>
                        <th class="text-center">핸드폰번호</th>
                        <th class="text-center">이메일</th>
                        <%--<th class="text-center">최초 등록자(아이디)</th>
                        <th class="text-center">최초 등록일시</th>--%>
                        <th class="text-center">최종 수정자(아이디)</th>
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


<!-- 사유 레이어 팝업(Modal) -->
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
