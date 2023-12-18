<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade ebbChageEpisdLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/eb/ebb/EBBChangeEpisdWriteCtrl">
    <c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:60%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 차수 변경
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
                <h5>선택된 회원
                    <span class="selectChangeMemCnt"></span>명
                </h5>
                <h5>변경할 차수 선택
                </h5>
            </div>
            <form name="frmChangeEpisdData" id="frmChangeEpisdData">

                <!-- CSRF KEY -->
                <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="" />
                <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="" />

                <input type="hidden" class="notRequired" id="prev_episdYear" name="episdYear" value="" />
                <input type="hidden" class="notRequired" id="prev_episdOrd" name="episdOrd" value="" />
                <input type="hidden" class="notRequired" id="stduyMthdCd" name="stduyMthdCd" value="" />

                <input type="hidden" class="notRequired" id="chan_memSeq" name="memSeq" value="" />

                <!-- 레이어 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body">

                        <!--VUE 영역 시작 -->
                        <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                            <table class="table table-hover table-striped" >
                                <thead>
                                <tr>
                                    <th class="text-center">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxAll notRequired" title="전체선택" name="changeChk"/>
                                            <span class="ion-checkmark-round"></span>
                                        </label>
                                    </th>
                                    <th class="text-center">번호</th>
                                    <th class="text-center">과정분류</th>
                                    <th class="text-center">과정명</th>
                                    <th class="text-center">년도</th>
                                    <th class="text-center">회차</th>
                                    <th class="text-center">정원</th>
                                    <th class="text-center">신청인원</th>

                                    <%--<th class="text-center">학습방식</th>
                                    <th class="text-center">학습시간</th>

                                    <th class="text-center">접수기간</th>
                                    <th class="text-center">접수상태</th>
                                    <th class="text-center">교육기간</th>
                                    <th class="text-center">교육상태</th>--%>

                                </tr>

                                </thead>
                                <!-- 리스트 목록 결과 -->
                                <tbody id="changeEpisdListContainer"/>
                            </table>
                            <!-- 페이징 버튼 -->
                            <div id="pagingContainer"/>
                        </div>
                        <!--리스트 종료 -->


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