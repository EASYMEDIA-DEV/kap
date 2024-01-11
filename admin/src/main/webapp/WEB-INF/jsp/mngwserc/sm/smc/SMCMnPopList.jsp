<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/sm/smc/SMCMnPopListCtrl">
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
            <input type="hidden" id="mdCd" name="mdCd" value="pc" />
            <input type="hidden" id="popupSeq" name="popupSeq" value="" />
            <!--기간 검색 시작-->

            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="게시기간" />
                <jsp:param name="useOdtmYn" value="Y" />
                <jsp:param name="startId" value="dStrDt" />
                <jsp:param name="endId" value="dEndDt" />
            </jsp:include>
            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="srchText" value="기간검색" />
                <jsp:param name="srchOption" value="등록일,수정기간" />
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
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" name="tagCd" data-name="tagCdList" value="img" <c:if test="${fn:contains(rtnData.tagCd, 'img')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 이미지
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" name="tagCd" data-name="tagCdList" value="html" <c:if test="${fn:contains(rtnData.tagCd, 'html')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> HTML
                        </label>
                    </div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부</label>
                    <div class="col-sm-5">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" name="expsYn" data-name="expsYnList" value="Y" <c:if test="${fn:contains(rtnData.expsYn, 'Y')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 노출
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" name="expsYn" data-name="expsYnList" value="N" <c:if test="${fn:contains(rtnData.expsYn, 'N')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 미노출
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
                                    <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>제목</option>
                                    <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>최종등록자</option>
                                    <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>최종수정자</option>
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
                    <%--<button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">다중순서변경</button>--%>
                    <%--<button type="button" class="btn btn-primary btn-sm mb-sm" id="btnExpsYn">미노출</button>--%>
                    <button type="button" class="btn btn-danger btn-sm mb-sm" id="btn_delete">선택삭제</button>
                    <button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
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
                        <th class="text-center">구분</th>
                        <th class="text-center">제목</th>
                        <th class="text-center">게시기간</th>
                        <th class="text-center">최초 등록자</th>
                        <th class="text-center">최초 등록일시</th>
                        <th class="text-center">최종 수정자</th>
                        <th class="text-center">최종 수정일시</th>
                        <th class="text-center">노출여부</th>
                        <th class="text-center">노출순서</th>
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