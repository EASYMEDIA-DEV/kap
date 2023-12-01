<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade imaQaPicLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/im/ima/IMAQaPicLayerCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:800px;">
        <div class="modal-content" style="height:1050px;">
            <div class="modal-header">
                <h5 class="modal-title" >문의담당자 관리
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </h5>
            </div>
            <form id="QaPicLayerFrm">
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
                <input type="hidden" id="listRowSize" name="listRowSize" value="10" />
                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <!-- 검색 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <%-- 저장 시 수정/등록 판단 시퀀스   --%>
                <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnDto.picSeq}" />
                <div class="modal-body">
                    <%--문의 유형 시작--%>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label text-bold">문의 유형<span class="star"> *</span></label>
                            <div class="col-sm-7">
                                <div class="row">
                                    <div class="col-sm-6 pr0">
                                        <select class="form-control input-sm" data-name="inqFirPic" name="inqFirPic" id="inqFirPic" title="1차 문의 유형">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                <c:if test="${fn:length(cdList.cd) < 6}">
                                                    <option value="${cdList.cd}">${cdList.cdNm}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-6 pr0">
                                        <select class="form-control input-sm" data-name="inqSecPic" name="inqSecPic" id="inqSecPic" title="2차 문의 유형">
                                            <option value="">선택</option>
                                            <c:forEach var="cdList" items="${cdDtlList.INQUIRY_TYPE}" varStatus="status">
                                                <option class="inqSecPic" style="display: none;" value="${cdList.cd}" >${cdList.cdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <%--문의 유형 종료--%>
                    <%--담당자명 시작--%>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-2 control-label text-bold">담당자명<span class="star"> *</span></label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control input-sm" id="picNm" name="picNm" value="${rtnDto.picNm}" title="담당자명" maxlength="4" placeholder="담당자명을 입력해주세요" />
                            </div>
                        </div>
                    </fieldset>
                    <%--담당자명 종료--%>
                    <%--담당자명 종료--%>
                    <fieldset>
                        <div class="form-group text-sm ">
                            <label class="col-sm-2 control-label text-bold">담당자<br />이메일<span class="star"> *</span></label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control input-sm" id="piceMail" name="piceMail" value="${rtnDto.piceMail}" maxlength="50" title="담당자 이메일" placeholder="담당자 이메일을 입력해주세요"  />
                            </div>
                        </div>
                    </fieldset>
                    <%--담당자명 종료--%>
                    <div>
                        <b>* 해당 문의유형으로 1:1문의 접수 시 입력된 이메일로 알림 메일이 발송됩니다.</b>
                    </div>
                    <div class="clearfix">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-sm btn-success">저장</button>
                        </div>
                    </div>

                    <hr class="mt10" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>문의담당자 목록 (총 <span id="picListContainerTotCnt">0</span>건)
                        </h6>
                        <div class="pull-right">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
                    <div>
                        <b>* 이메일이 변경된 경우 해당 내역을 삭제 후 새로 추가 바랍니다.</b>
                    </div>
                    <!--리스트 시작 -->
                    <div class="table-responsive col-sm-12 p0 m0" style="max-height: 640px; overflow-y: auto;">
                        <table class="table table-hover table-striped">
                            <thead>
                            <tr>
                                <th class="text-center">번호</th>
                                <th class="text-center">문의유형</th>
                                <th class="text-center">담당자명</th>
                                <th class="text-center">담당자 이메일</th>
                                <th class="text-center">수정/삭제</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="picListContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="picPagingContainer"/>
                    </div>
                    <!--리스트 종료 -->
                </div>
            </form>
        </div>
    </div>
</div>