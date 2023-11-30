<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="modal fade episdSrchLayer" tabindex="-1" role="dialog" data-controller="controller/wb/wbl/WBLEpisdLayerCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:1500px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 회차 관리
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <hr class="mt0" />

            <form id="formEpids" method="post">
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageEpidsIndex" name="pageIndex" value="1" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageEpidsRowSize" name="pageRowSize" value="10" />
                <input type="hidden" id="listEpidsRowSize" name="listRowSize" value="10" />
                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                <div class="modal-body">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>회차 등록
                        </h6>
                    </div>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">년도/회차</label>
                            <div class="col-sm-4">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <select class="form-control input-sm" name="episdYear">
                                            <option value="">선택</option>
                                            <c:set var="startYear" value="2023"/>
                                            <c:forEach begin="0" end="10" var="year" step="1">
                                                <option value="${startYear+year}">${startYear+year}년</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-4">
                                        <select class="form-control input-sm" name="episdEpisd">
                                            <option value="">선택</option>
                                            <c:forEach begin="1" end="10" var="episd" step="1">
                                                <option value="${episd}" >${episd}차</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">설문지선택</label>
                            <div class="col-sm-6">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <select class="form-control input-sm" name="episdSrvSeq">
                                            <option value="">선택</option>
                                            <c:if test="${ not empty rtnSurveyData.list}">
                                                <c:forEach var="list" items="${rtnSurveyData.list}" varStatus="status">
                                                    <option value="${list.srvSeq}">${list.titl}</option>
                                                </c:forEach>
                                            </c:if>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>회차 목록
                        </h6>
                    </div>
                    <!--VUE 영역 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center" style="width:10%">번호</th>
                                <th class="text-center" style="width:10%">년도</th>
                                <th class="text-center" style="width:10%">회차</th>
                                <th class="text-center">설문지</th>
                                <th class="text-center" style="width:10%">삭제</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="listEpisdContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="pagingEpisdContainer"/>
                    </div>
                </div>
                <div class="modal-footer row">
                    <div class="text-center">
                        <button type="button" class="btn btn-success down mt epidsSave">저장</button>
                    </div>
                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>