<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 교육차수관리 > 참여자 목록 > 개인별 출석부 EBBMemAtndcLayer -> EBBMemAtndcAjax  -->
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade ebbMemAtndcSrchLayer" tabindex="-1" role="dialog" data-controller="controller/eb/ebb/EBBMemAtndcWriteCtrl">
    <c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:40%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 출석 상세
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <form name="frmMemAtndcData" id="frmMemAtndcData">

                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                <input type="hidden" id="edctnSeq" name="edctnSeq" value="" />
                <input type="hidden" id="episdYear" name="episdYear" value="" />
                <input type="hidden" id="episdOrd" name="episdOrd" value="" />

                <input type="hidden" id="ptcptSeq" name="ptcptSeq" value="" />
                <input type="hidden" id="memSeq" name="memSeq" value="" />
                <!-- 레이어 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body memAtndcContainer">


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