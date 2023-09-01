<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/bm/bmc/BMCNtfyListCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" data-del-type="board">

            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ empty rtnData.pageIndex ? 1 : rtnData.pageIndex }" />

            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="5"/>

            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="" />

            <input type="hidden" class="notRequired" id="mChecked" name="mChecked" value="" />
            <input type="hidden" class="notRequired" id="mUndetermined" name="mUndetermined" value="" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                <jsp:param name="periodType" value="bmc" />
                <jsp:param name="srchText" value="기간" />
            </jsp:include>

            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">노출여부</label>
                    <div class="col-sm-11">
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxAll" />
                            <span class="ion-checkmark-round"></span> 전체
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="expsYnList" value="Y" <c:if test="${fn:contains(rtnData.expsYnList, 'Y')}">checked</c:if> />
                            <span class="ion-checkmark-round"></span> 노출
                        </label>
                        <label class="checkbox-inline c-checkbox">
                            <input type="checkbox" class="checkboxSingle" data-name="expsYnList" value="N" <c:if test="${fn:contains(rtnData.expsYnList, 'N')}">checked</c:if> />
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
                                    <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>작성자</option>
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
            <input type="hidden" id="listRowSize" name="listRowSize" value="10"/>
            <hr class="mt0" />
            <div class="clearfix">
                <h6 class="pull-left mt0">
                    <em class="ion-play mr-sm"></em>${pageTitle} 목록 (총 <span id="listContainerTotCnt">0</span> 건)
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
                        <th class="text-center">제목</th>
                        <th class="text-center">게시일</th>
                        <th class="text-center">등록일</th>
                        <th class="text-center">노출여부</th>
                        <th class="text-center">작성자</th>
                    </tr>
                    </thead>
                    <!-- 리스트 목록 결과 -->
                    <tbody id="listContainer"/>
                </table>
                <!-- 페이징 버튼 -->
                <div id="pagingContainer"/>
            </div>
        </form>
    </div>
</div>