<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/wb/wbg/WBGAExamListCtrl">
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
            <input type="hidden" id="appctnSeq" name="appctnSeq" value="" />
            <input type="hidden" id="bsnmNo" name="bsnmNo" value="" />
            <input type="hidden" id="memSeq" name="memSeq" value="" />
            <!--기간 검색 시작-->
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchType" value="wbeb" />
            </jsp:include>
            <fieldset>
                <div class="form-inline text-sm">
                    <label class="col-sm-1 control-label">사업회차</label>
                    <div class="col-sm-11">
                        <div class="form-group mr-sm">
                            <select class="form-control input-sm" data-name="f" name="yearSearch">
                                <option value="">연도 전체</option>
                                <c:forEach var="rtnYear" items="${rtnYear}" varStatus="status">
                                    <option value="${rtnYear}" <c:if test="${rtnData.year eq rtnYear }">selected</c:if>>${rtnYear}</option>
                                </c:forEach>
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
                            <c:if test="${fn:contains(cdList.cd, 'PRO_TYPE07')}">
                            <label class="checkbox-inline c-checkbox classType">
                                <input type="checkbox" class="checkboxSingle" data-name="carbonCdList" name="cd" value="${cdList.cd}"/>
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
                <div class="pull-right">
                    <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExamList">사업관리</button>
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
                        <th class="text-center">번호</th>
                        <th class="text-center">사업연도</th>
                        <th class="text-center">진행상태</th>
                        <th class="text-center">관리자<br>상태값</th>
                        <th class="text-center">부품사명</th>
                        <th class="text-center">사업자등록번호</th>
                        <th class="text-center">구분</th>
                        <th class="text-center">규모</th>
                        <th class="text-center">신청자<br>(아이디)</th>
                        <th class="text-center">휴대폰번호</th>
                        <th class="text-center">이메일</th>
                        <th class="text-center">전년도<br>매출액</th>
                        <th class="text-center">담당위원</th>
                        <th class="text-center">장비내역</th>
                        <th class="text-center">수량</th>
                        <th class="text-center">투자금액</th>
                        <th class="text-center">재단지원금</th>
                        <th class="text-center">실지급일</th>
                        <th class="text-center">관리자 등록일</th>
                        <th class="text-center">사용자 수정일</th>
                        <th class="text-center">최종 수정자<br>(아이디)</th>
                        <th class="text-center">최종 수정일시</th>
                    </tr>
                    </thead>
                    <!-- 리스트 목록 결과 -->
                    <tbody id="listContainer"/>
                </table>
                <div class="pull-right mr-sm">
                    <button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
                </div>
                <div class="pull-left mr-sm">
                    <button type="button" class="btn btn-danger btn-sm mb-sm" id="btnDeleteList">선택삭제</button>
                </div>
                <!-- 페이징 버튼 -->
                <div id="pagingContainer"/>
            </div>
            <!--리스트 종료 -->
        </form>

        <!-- 사업관리 레이어 팝업(Modal) -->
        <form class="form-horizontal" name="frmValid" method="post" action="">
            <input type="hidden" id="validSeq" name="validSeq" value="${rtnValid.validSeq}"/>
            <div class="modal fade excel-down" tabindex="-1" role="dialog" >
                <div class="modal-dialog modal-lg modal-center" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" >사업관리
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </h5>
                        </div>
                        <div class="modal-body">
                            <div class="form-group ">
                                <p><em class="ion-play mr-sm"></em>사업신청자격</p>
                                <fieldset></fieldset>
                                <fieldset>
                                    <div class="form-group text-sm" >
                                        <label class="col-sm-3 control-label">전년도매출액(억원 단위)<br>*기준 미만만 신청 가능</label>
                                        <div class="col-sm-3" >
                                            <input type="text" id="stndSlsPmt" class="form-control numberChk" value="${rtnValid.stndSlsPmt}" title="전년도매출액"/>
                                        </div>
                                    </div>
                                </fieldset>


                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-3 control-label">기술지도 업종<span class="star"> *</span></label>
                                        <div class="col-sm-8">
                                            <label class="control-label">금속분야</label><br>
                                            <c:forEach var="cdList" items="${classTypeList.TEC_GUIDE_INDUS}" varStatus="status">
                                                <c:if test="${fn:contains(cdList.cd, 'TEC_GUIDE_METAL')}">
                                                    <c:set var="tempChk" value="N" />
                                                    <c:forEach var="dtlList" items="${rtnValid.dtlList}">
                                                        <c:if test="${tempChk eq 'N'}">
                                                            <c:if test="${dtlList.optnCd eq cdList.cd}">
                                                                <c:set var="tempChk" value="Y" />
                                                            </c:if>
                                                            <c:if test="${dtlList.optnCd ne cdList.cd}">
                                                                <c:set var="tempChk" value="N" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <label class="checkbox-inline c-checkbox classType">
                                                        <input type="checkbox" class="checkboxSingle" name="optnCdList" value="${cdList.cd}" <c:if test="${tempChk eq 'Y'}">checked</c:if>/>
                                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                                    </label>
                                                    <c:if test="${status.index eq 5}"><br></c:if>
                                                </c:if>
                                            </c:forEach>
                                            <br/><br/>
                                            <label class="control-label">비금속분야</label><br>
                                            <c:forEach var="cdList" items="${classTypeList.TEC_GUIDE_INDUS}" varStatus="status">
                                                <c:if test="${fn:contains(cdList.cd, 'TEC_GUIDE_NON')}">
                                                    <c:set var="tempChk" value="N" />
                                                    <c:forEach var="dtlList" items="${rtnValid.dtlList}">
                                                        <c:if test="${tempChk eq 'N'}">
                                                            <c:if test="${dtlList.optnCd eq cdList.cd}">
                                                                <c:set var="tempChk" value="Y" />
                                                            </c:if>
                                                            <c:if test="${dtlList.optnCd ne cdList.cd}">
                                                                <c:set var="tempChk" value="N" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <label class="checkbox-inline c-checkbox classType">
                                                        <input type="checkbox" class="checkboxSingle" name="optnCdList" value="${cdList.cd}" <c:if test="${tempChk eq 'Y'}">checked</c:if>/>
                                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                                    </label>
                                                </c:if>
                                            </c:forEach>
                                            <br/><br/>
                                            <label class="control-label">기타</label><br>
                                            <c:forEach var="cdList" items="${classList.TEC_GUIDE_INDUS}" varStatus="status">
                                                <c:if test="${fn:contains(cdList.cd, 'TEC_GUIDE_INDUS')}">
                                                    <c:set var="tempChk" value="N" />
                                                    <c:forEach var="dtlList" items="${rtnValid.dtlList}">
                                                        <c:if test="${tempChk eq 'N'}">
                                                            <c:if test="${dtlList.optnCd eq cdList.cd}">
                                                                <c:set var="tempChk" value="Y" />
                                                            </c:if>
                                                            <c:if test="${dtlList.optnCd ne cdList.cd}">
                                                                <c:set var="tempChk" value="N" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <label class="checkbox-inline c-checkbox classType">
                                                        <input type="checkbox" class="checkboxSingle" name="optnCdList" value="${cdList.cd}" <c:if test="${tempChk eq 'Y'}">checked</c:if>/>
                                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                                    </label>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </fieldset>
                                <fieldset>
                                    <div class="form-group text-sm">
                                        <label class="col-sm-3 control-label">경영컨설팅 분야<span class="star"> *</span></label>
                                        <div class="col-sm-8">
                                            <c:forEach var="cdList" items="${classList.MNGCNSLT_APP_AREA}" varStatus="status">
                                                <c:if test="${fn:contains(cdList.cd, 'MNGCNSLT_APP_AREA')}">
                                                    <c:set var="tempChk" value="N" />
                                                    <c:forEach var="dtlList" items="${rtnValid.dtlList}">
                                                        <c:if test="${tempChk eq 'N'}">
                                                            <c:if test="${dtlList.optnCd eq cdList.cd}">
                                                                <c:set var="tempChk" value="Y" />
                                                            </c:if>
                                                            <c:if test="${dtlList.optnCd ne cdList.cd}">
                                                                <c:set var="tempChk" value="N" />
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <label class="checkbox-inline c-checkbox classType">
                                                        <input type="checkbox" class="checkboxSingle" name="optnCdList" value="${cdList.cd}" <c:if test="${tempChk eq 'Y'}">checked</c:if>/>
                                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                                    </label>
                                                </c:if>
                                                <c:if test="${status.index eq 5}"><br></c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </fieldset>

                            </div>
                        </div>
                        <div class="modal-footer row">
                            <button type="button" id="validSubmit" class="btn btn-primary down mt">저장</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>