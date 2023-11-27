<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 레이어 팝업(Modal) -->
<div class="modal fade MPDCmtKenDaySrchLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/mp/mpd/MPDCmtDtlWriteCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 일일근태현황
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>

            <form>
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
                <input type="hidden" id="listRowSize" name="listRowSize" value="10" />
                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <!-- 검색 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body">
                    <!--기간 검색 시작-->
                    <ul class="nav nav-tabs" id="myTabs" style="display: none;">
                        <li class="tabClick active"><a data-toggle="tab" href="#ken">근태 현황</a></li>
                    </ul>

                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em> 근태 현황 (총 <span id="listContainerKenTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="" />
                                </jsp:include>
                            </select>
                        </div>
                            <div class="input-group">
                                <input type="text" class="notRequired monthInit form-control input-sm datetimepicker_strtDt " name="monthpicker" value=""  readonly onclick="cmmCtrl.initCalendar(this); $(this).focus();"/>
                                <span class="input-group-btn" style="z-index:0;">
                                        <button type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                                <div class="pull-right">
                                <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">엑셀다운로드</button>
                                </div>
                            </div>

                        <div class="pull-right">
                        </div>
                    </div>
                    <!--VUE 영역 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">

                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">이름</th>
                                <th class="text-center">근태옵션</th>
                                <th class="text-center">지도부품사1</th>
                                <th class="text-center">소재지역1</th>
                                <th class="text-center">지도부품사2</th>
                                <th class="text-center">소재지역2</th>
                                <th class="text-center">기타출장</th>
                                <th class="text-center">기타</th>
                                <th class="text-center">근태체크일시</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                         <tbody id="listContainerKen"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="pagingContainerKen"></div>
                    </div>
                </div>
                <div class="modal-footer row">

                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>


<div class="modal fade excel-down-day" tabindex="-1" role="dialog" >
    <div class="modal-dialog modal-lg modal-center" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 엑셀 다운로드
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <div class="modal-body">
                <div class="form-group ">
                    <p><em class="ion-play mr-sm"></em>사유입력</p>
                    <div class="col-sm-12">
                        <textarea maxlength="30" class="col-sm-12 pv" style="resize: vertical;" rows="10" placeholder="사유를 입력하세요." id="rsn" title="사유" oninput="cmmCtrl.checkMaxlength(this);"></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-footer row">
                <div class="text-center">
                    <button type="button" class="btn btn-primary down-day mt">다운로드</button>
                </div>
            </div>
        </div>
    </div>
</div>

