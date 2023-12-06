<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade ebbAtndcSrchLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/eb/ebb/EBBAtndcWriteCtrl">
    <c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:97%;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 출석부
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <form name="frmAtndcData" id="frmAtndcData">
                <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.edctnSeq}" />
                <input type="hidden" class="notRequired" id="edctnSeq" name="edctnSeq" value="${rtnDto.edctnSeq}" />
                <input type="hidden" class="notRequired" id="episdSeq" name="episdSeq" value="${rtnDto.episdSeq}" />
                <input type="hidden" class="notRequired" id="episdYear" name="episdYear" value="${rtnDto.episdYear}" />
                <input type="hidden" class="notRequired" id="episdOrd" name="episdOrd" value="${rtnDto.episdOrd}" />
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
                <input type="hidden" id="listRowSize" name="listRowSize" value="10" />
                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <!-- 레이어 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body">
                    <!--기간 검색 시작-->
                    <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                        <jsp:param name="srchText" value="등록/수정기간" />
                        <jsp:param name="srchOption" value="등록일,수정일" />
                    </jsp:include>

                    <!-- 과정정보 -->
                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-12">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>과정정보</h6>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과정분류<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <p class="form-control-static ctgryCdNm">
                                    <c:if test="${rtnDto.prntCdNm ne null}">
                                        ${rtnDto.prntCdNm} > ${rtnDto.ctgryCdNm}
                                    </c:if>
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과정명<span class="star"> *</span></label>
                            <div class="col-sm-11">
                                <p class="form-control-static nm">${rtnDto.nm}</p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습방식<span class="star text-danger"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static stduyMthd">${rtnDto.stduyMthdCdNm}</p>
                            </div>

                            <label class="col-sm-1 control-label">학습시간<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <p class="form-control-static stduyDtm">
                                    <c:if test="${rtnDto.stduyDdCdNm ne null}">
                                        ${rtnDto.stduyDdCdNm}일/${rtnDto.stduyTimeCdNm} 시간
                                    </c:if>
                                </p>
                            </div>
                        </div>
                    </fieldset>

                    <!-- 회차정보 -->
                    <fieldset>
                        <div class="form-group text-sm">
                            <div class="col-sm-12">
                                <h6 class="mt0"><em class="ion-play mr-sm"></em>회차정보</h6>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">회차<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <p class="form-control-static">${rtnDto.episdOrd}회차</p>
                            </div>
                            <label class="col-sm-1 control-label">업종<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <p class="form-control-static">${rtnDto.cbsnCdNm}</p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">접수기간<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">

                                <p class="form-control-static">${kl:convertDate(rtnDto.accsStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}  ~ ${kl:convertDate(rtnDto.accsEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                            </div>

                            <label class="col-sm-1 control-label">교육기간<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <input type="hidden" name="edctnStrtDtm" id="edctnStrtDtm" value="${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                                <input type="hidden" name="edctnEndDtm" id="edctnEndDtm" value="${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}">
                                <p class="form-control-static">${kl:convertDate(rtnDto.edctnStrtDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}  ~ ${kl:convertDate(rtnDto.edctnEndDtm, 'yyyy-MM-dd', 'yyyy-MM-dd', '')}</p>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">강사<span class="star"> *</span></label>
                            <div class="col-sm-11" style="margin-left: -15px">
                                <p class="form-control-static">
                                    <c:set var="isttrSize" value="${fn:length(isttrList)}" />
                                    <c:forEach var="isttrList" items="${isttrList}" varStatus="status">
                                        ${isttrList.name} <c:if test="${isttrSize > 1 && status.count < isttrSize}">, </c:if>

                                    </c:forEach>
                                </p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">정원<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <p class="form-control-static">${rtnDto.fxnumCnt}명</p>
                            </div>
                            <label class="col-sm-1 control-label">모집방식<span class="star"> *</span></label>
                            <div class="col-sm-5" style="margin-left: -15px">
                                <p class="form-control-static">${rtnDto.rcrmtMthdCdNm}</p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">문의담당자<span class="star"> *</span></label>
                            <div class="col-sm-11" style="margin-left: -15px">
                                <p class="form-control-static">${rtnDto.picNm} / ${rtnDto.picEmail} / ${rtnDto.picTelNo}</p>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">교육장소<span class="star"> *</span></label>
                            <div class="col-sm-11" style="margin-left: -15px">
                                <p class="form-control-static">${roomDto.nm}</p>
                            </div>
                        </div>
                    </fieldset>


                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>${pageTitle} 목록 (총 <span id="ptcptListLayerContainerTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
                    <!--VUE 영역 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" id="vueList">
                        <table class="table table-hover table-striped" >
                            <thead>
                            <tr>
                                <th class="text-center" rowspan="3">
                                    <label class="checkbox-inline c-checkbox">
                                        <input type="checkbox" class="checkboxAll notRequired" title="전체선택" name="ptcptChk"/>
                                        <span class="ion-checkmark-round"></span>
                                    </label>
                                </th>
                                <th class="text-center" rowspan="3">번호</th>
                                <th class="text-center" rowspan="3">아이디</th>
                                <th class="text-center" rowspan="3">이름</th>
                                <th class="text-center" rowspan="3">부품사명</th>
                                <th class="text-center" rowspan="3">구분</th>
                                <th class="text-center" rowspan="3">사업자등록번호</th>
                                <th class="text-center" rowspan="3">휴대폰번호</th>
                                <th class="text-center" rowspan="3">이메일</th>
                                <th class="text-center" rowspan="3">교육신청일</th>

                                <th class="text-center" colspan="${fn:length(tableAtndcList) * 3}">교육기간</th>
                                <!--교육기간-->
                                <!--동적으로 늘어나야됨-->
                                <!--교육기간-->

                                <!--가운데를 기준으로 위에는 리스트 길이x3 밑에도 길이x3-->
                            </tr>
                            <tr>
                                <c:forEach var="tableAtndcList" items="${tableAtndcList}" varStatus="status">
                                    <th class="text-center" colspan="3">${tableAtndcList.edctnDt}</th>
                                </c:forEach>
                            </tr>
                            <tr>
                                <c:forEach var="tableAtndcList" items="${tableAtndcList}" varStatus="status">
                                    <th class="text-center">출석</th>
                                    <th class="text-center">퇴실</th>
                                    <th class="text-center">비고</th>
                                </c:forEach>
                            </tr>
                            </thead>

                            <!-- 리스트 목록 결과 -->
                            <tbody id="ptcptListLayerContainer"/>

                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="ptcptPagingLayerContainer"/>
                    </div>
                    <!--리스트 종료 -->
                </div>


                <div class="modal-footer row">
                    <div class="pull-right">
                        <button type="submit" class="btn btn-sm btn-success">저장</button>
                    </div>
                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>