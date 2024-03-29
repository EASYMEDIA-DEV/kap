<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 교육차수관리 > 참여자 목록 > 개인별 출석부 EBBMemAtndcLayer -> EBBMemAtndcAjax  -->
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade ebbInformMailLayer" tabindex="-1" role="dialog" data-controller="controller/eb/ebb/EBBInformMailWriteCtrl">
    <c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:40%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 메일발송
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>

            <textarea maxlength="500" class="col-sm-12 pv notRequired ckeditorRequired" style="resize: vertical;display: none;" rows="10" placeholder="" id="copyCntn" name="copyCntn" title="교육내용" oninput="cmmCtrl.checkMaxlength(this);">
            </textarea>


            <form name="mailFrm" id="mailFrm" method="post" >

                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

                <!-- 레이어 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body" style="margin-bottom: 10px;">

                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-11">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>메일 내용</h6>
                            </div>
                        </div>
                    </fieldset>

                    <div class="form-group text-sm" >
                        <div class="col-sm-12 editorArea">

                        </div>
                    </div>



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