<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 레이어 팝업(Modal) -->
<div class="modal fade MPDCmtKenMonthSrchLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/mp/mpd/MPDCmtDtmlMonthKenSrchCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 월별 출근부
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

                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em> 근태 현황 (총 <span id="listContainerMonthTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="" />
                                </jsp:include>
                            </select>
                        </div>
                            <div class="input-group">
                                <input type="text" class="monthpicker monthInit form-control input-sm "  name="monthpicker" style="margin-left:0.8rem; width:50%" readonly/>
                                <span  style="width:20%;"  class="input-group-btn" style="z-index:0;">
                                        <button  style="margin-bottom:2.5rem ; margin-left:-7.8rem;"  type="button" class="btn btn-inverse btn-sm" onclick="cmmCtrl.initCalendar(this); jQuery(this).parent().prev().focus();">
                                            <em class="ion-calendar"></em>
                                        </button>
                                    </span>
                                <button type="button" class="btn btn-inverse btn-sm " style="margin-bottom:2.5rem ; margin-left:-8.2rem;" id="btnExcelDown" >엑셀다운로드</button>
                            </div>

                        <div class="pull-right">
                        </div>
                    </div>
                    <!--VUE 영역 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">

                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr id="tableOne">
                            </tr>
                            <tr id="tableTwo">
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                         <tbody id="listContainerMonth"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="pagingContainerMonth"></div>
                    </div>
                </div>
                <div class="modal-footer row">

                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>



