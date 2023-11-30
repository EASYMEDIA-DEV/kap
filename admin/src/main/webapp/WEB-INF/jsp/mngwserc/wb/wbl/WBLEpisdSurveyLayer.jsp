<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="modal fade episdSurveySrchLayer" tabindex="-1" role="dialog" data-controller="controller/wb/wbl/WBLEpisdSurveyLayerCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:1500px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 대상 엑셀 업로드
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <hr class="mt0" />

            <form id="formEpidsSurvey" method="post" enctype="multipart/form-data" action="./insertExcel">
                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" name="episdSurveySrvSeq" class="notRequired">
                <input type="hidden" name="cxstnCmpnEpisdSeq" class="notRequired">

                <c:if test="${ not empty rtnEpisdSurveyData.list}">
                    <c:forEach var="list" items="${rtnEpisdSurveyData.list}" varStatus="status">
                        <input type="hidden" name="episdSurveyYearEpisd" value="${fn:substring(list.year,0,4)}${list.episd}" data-srv-seq="${list.srvSeq}" data-titl="${list.titl}" data-year="${fn:substring(list.year,0,4)}" data-episd="${list.episd}" data-cxstn-cmpn-episd-seq="${list.cxstnCmpnEpisdSeq}">
                    </c:forEach>
                </c:if>

                <div class="modal-body">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>회차 선택
                        </h6>
                    </div>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">년도/회차</label>
                            <div class="col-sm-4">
                                <div class="row">
                                    <div class="col-sm-4">
                                        <select class="form-control input-sm episdSurveySelect episdSurveyYear" name="episdSurveyYear" title="년도">
                                            <option value="">선택</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-4">
                                        <select class="form-control input-sm episdSurveySelect" name="episdSurveyEpisd" title="회차">
                                            <option value="">선택</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">설문지</label>
                            <div class="col-sm-6">
                                <div class="row">
                                    <div class="col-sm-6 episdSurveyText">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>대상 업로드
                        </h6>
                    </div>
                    <fieldset>
                        <div class="form-group">
                            <label for="" class="control-label col-sm-2 col-md-2 pr-xl">파일찾기</label>
                            <div class="col-sm-10 col-md-10">
                                <input type="file" name="wblListExcel" accept=".xls,.xlsx" class="notRequired" title="파일"/>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group">
                            <div class="col-sm-10 col-md-10">
                                <p class="text-bold mt">
                                    <button type="button" class="btn btn-inverse down mt excelSample">양식다운로드</button>
                                </p>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="modal-footer row">
                    <div class="text-center">
                        <button type="submit" class="btn btn-success down mt">저장</button>
                    </div>
                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>