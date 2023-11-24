<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/co/COFormCtrl controller/co/cog/COGCntsListCtrl">
<%--        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 검색</h6>--%>
        <form class="form-horizontal" name="frmData" method="post" action="">
            <!-- 현재 페이징 번호 -->
            <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" id="menuSeq" name="menuSeq" value="${rtnData.menuSeq}" />
            <!-- 상세로 이동시 시퀀스 -->
            <input type="hidden" id="detailsKey" name="detailsKey" value="" />
            <!--단 처리-->
            <%--<hr class="mt0" />--%>
            <div class="clearfix">
                <h6 class="pull-left mt0">
                    <em class="ion-play mr-sm"></em>${pageTitle} (총 <span id="listContainerTotCnt">0</span>건)
                </h6>
                <div class="pull-right">
                    <select class="form-control input-sm listRowSizeContainer" >
                        <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                            <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                        </jsp:include>
                    </select>
                </div>
                <div class="pull-right mr-sm">
                    <button type="button" class="btn btn-inverse btn-sm" id="btnSearch" style="display:none">검색</button>
                </div>
            </div>
            <div class="pull-left mr-sm">
                <button type="button" class="btn btn-primary btn-sm mb-sm" id="btn_immediately">즉시배포</button>
                <button type="button" class="btn btn-warning btn-sm mb-sm" id="btn_back">되돌리기</button>
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
                            <th class="text-center">제목</th>
                            <th class="text-center">버전</th>
                            <th class="text-center">상태</th>
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

            <div class="pull-left mr-sm">
                <button type="button" class="btn btn-danger btn-sm mb-sm" id="delete">선택삭제</button>
                <button type="button" class="btn btn-default btn-sm mb-sm" id="btn_copy">복사</button>
            </div>
            <div class="pull-right mr-sm">
                <button type="button" class="btn btn-info btn-sm mb-sm" id="btnWrite">등록</button>
            </div>
        </form>
    </div>
</div>