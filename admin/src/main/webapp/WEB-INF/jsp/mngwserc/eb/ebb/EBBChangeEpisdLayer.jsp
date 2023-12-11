<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade ebbChageEpisdLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/eb/ebb/EBBChangeEpisdWriteCtrl">
    <c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:20%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 차수 변경
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <form name="frmChangeEpisdData" id="frmChangeEpisdData">

                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" id="chan_edctnSeq" name="edctnSeq" value="" />
                <input type="hidden" id="chan_episdYear" name="episdYear" value="" />
                <input type="hidden" id="chan_episdOrd" name="episdOrd" value="" />
                <input type="hidden" id="chan_episdSeq" name="episdSeq" value="" />
                <input type="hidden" id="chan_memSeq" name="memSeq" value="" />

                <!-- 레이어 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body">
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">연도<span class="star text-danger"> *</span></label>
                            <div class="col-sm-6 form-inline">
                                <select class="form-control input-sm wd-sm" name="episdYear" id="episdYear" title="년도" style="min-width: 100px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.CO_YEAR_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.episdYear eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>

                                <select class="form-control input-sm wd-sm" name="episdOrd" id="episdOrd" title="회차" style="min-width: 100px;">
                                    <option value="">선택</option>
                                    <c:forEach var="cdList" items="${episdCdList.ROUND_CD}" varStatus="status">
                                        <option value="${cdList.cd}" <c:if test="${rtnDto.episdOrd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </fieldset>



                </div>

                <div class="modal-footer row">
                    <div class="pull-right">
                        <button type="button" style="display: none;" class="btn btn-sm tempBtn">임시</button>
                        <button type="submit" class="btn btn-sm btn-success">저장</button>
                    </div>
                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>