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

            <%-- 2024-07-08 추가개발 ppt6 수정 s --%>
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchOption" value="등록일,수정일,참여일" />
                <jsp:param name="srchType" value="srvwbl" />
            </jsp:include>
            <!--기간 검색 종료-->
            <%-- 2024-07-08 추가개발 ppt6 수정 e --%>

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
                        <c:forEach var="cdList" items="${cdDtlList.PTCPT_CD}" varStatus="status">
                            <label class="checkbox-inline c-checkbox">
                                <input type="checkbox" class="checkboxSingle" data-name="ptcptCdList" value="${cdList.cd}"  <c:if test="${fn:contains(rtnData.ptcptCdList, cdList.cd)}">checked</c:if>/>
                                <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">완료여부</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="cmpltnYnList" value="Y"  <c:if test="${fn:contains(rtnData.cmpltnYnList, 'Y')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 완료
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="cmpltnYnList" value="N"  <c:if test="${fn:contains(rtnData.cmpltnYnList, 'N')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 미완료
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
                                    <option value="5" <c:if test="${rtnData.f eq '5'}">selected</c:if>>최초등록자</option>
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
                        <%-- 2024-07-08 추가개발 ppt6 수정 s --%>
                        <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                            <jsp:param name="listRowSize" value="100" />
                        </jsp:include>
                        <%-- 2024-07-08 추가개발 ppt6 수정 s --%>
                    </select>
                </div>
                <div class="pull-right mr-sm">
                    <button type="button" class="btn btn-success btn-sm mb-sm btnCrtfnSend" data-send-type="email">Email 인증번호 발송</button> <%-- 2024-07-11 추가개발 ppt 11 추가 --%>
                    <button type="button" class="btn btn-success btn-sm mb-sm btnCrtfnSend" data-send-type="sms">SMS 인증번호 발송</button> <%-- 2024-07-11 추가개발 ppt 11 추가 --%>
                    <button type="button" class="btn btn-danger btn-sm mb-sm" id="btnDelete">선택삭제</button>
                    <button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">엑셀다운로드</button>
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
                        <%-- 2024-07-08 추가개발 ppt 7 항목 수정 s --%>
                        <th class="text-center">번호</th>
                        <th class="text-center">년도</th>
                        <th class="text-center">회차</th>
                        <th class="text-center">1차 부품사</th>
                        <th class="text-center">1차 부품사 코드</th>
                        <th class="text-center">2차 부품사</th>
                        <th class="text-center">2차 부품사 코드</th>
                        <th class="text-center">참여여부</th>
                        <th class="text-center">완료여부</th>
                        <th class="text-center">점수</th>
                        <th class="text-center">응답업체수</th>
                        <th class="text-center">평균점수</th>
                        <th class="text-center">HKMC평균점수</th>
                        <th class="text-center">참여일</th>
                        <th class="text-center">담당자명</th>
                        <th class="text-center">담당자 핸드폰 번호</th>
                        <th class="text-center">담당자 이메일</th>
                        <th class="text-center">인증번호 발송여부</th>
                        <th class="text-center">최초 등록일시</th>
                        <%-- 2024-07-08 추가개발 ppt 7 항목 수정 e --%>
                    </tr>
                    </thead>
                    <!-- 리스트 목록 결과 -->
                    <tbody id="listContainer"/>
                </table>
                <!-- 페이징 버튼 -->
                <div id="pagingContainer"/>
            </div>
            <div class="pull-right mr-sm">
                <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnEpisd">회차관리</button>
                <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelupload">대상 엑셀 업로드</button>
            </div>
            <!--리스트 종료 -->
        </form>
    </div>
</div>


<%--<jsp:include page="/WEB-INF/jsp/mngwserc/sv/sva/SVASurveySrchLayer.jsp">--%>
<%--    <jsp:param name="typeCd" value="WIN" />--%>
<%--</jsp:include>--%>


<jsp:include page="/WEB-INF/jsp/mngwserc/wb/wbl/WBLEpisdLayer.jsp"/>
<jsp:include page="/WEB-INF/jsp/mngwserc/wb/wbl/WBLEpisdSurveyLayer.jsp"/>

<div class="modal fade excel-down" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-center" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >엑셀 다운로드
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
                    <button type="button" class="btn btn-primary down mt">저장</button>
                </div>
            </div>
        </div>
    </div>
</div>
