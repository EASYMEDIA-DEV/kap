<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<!-- 사유 레이어 팝업(Modal) -->
<div class="modal fade ebbEpisdCouseSrchLayer" tabindex="-1" role="dialog" data-controller="controller/co/COFormCtrl controller/eb/eba/EBACouseListCtrl">
    <div class="modal-dialog modal-lg modal-center" role="document" style="width:2000px;">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" >▣ 교육과정 검색
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
                <!-- 레이어 여부 -->
                <input type="hidden" name="srchLayer" value="Y" />
                <div class="modal-body">
                    <!--기간 검색 시작-->
                    <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPeriodSearch.jsp">
                        <jsp:param name="srchText" value="등록/수정기간" />
                        <jsp:param name="srchOption" value="등록일,수정일" />
                    </jsp:include>
                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">과정분류</label>
                            <div class="col-sm-11"  style="margin-bottom: 15px;">
                                <label class="checkbox-inline c-checkbox classType">
                                    <input type="checkbox" class="checkboxAll" />
                                    <span class="ion-checkmark-round"></span> 전체
                                </label>

                                <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                    <label class="checkbox-inline c-checkbox classType">
                                        <input type="checkbox" class="checkboxSingle" data-name="prntCdList" name="prntCd" value="${cdList.cd}" data-cdnm="${cdList.cdNm}"/>
                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                    </label>

                                </c:forEach>
                            </div>

                            <!--과정분류 2뎁스 생성구간 시작-->
                            <label class="col-sm-1 control-label"></label>
                            <div class="col-sm-11 detailCdList">
                                <!-- 시작 -->
                                <div class="cdListContainer CLASS01" style="display:none;margin-bottom: 15px;">
                                    <div class="row">
                                        <div class="col-sm-1 pr0 cdnm">

                                        </div>
                                    </div>
                                    <c:forEach  var="cdList" items="${cdList1}" varStatus="status">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}"disabled="true"/>
                                            <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                        </label>
                                    </c:forEach>
                                </div>
                                <div  class="cdListContainer CLASS02" style="display:none;margin-bottom: 15px;">
                                    <div class="row">
                                        <div class="col-sm-1 pr0 cdnm">

                                        </div>
                                    </div>
                                    <c:forEach  var="cdList" items="${cdList2}" varStatus="status">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}" disabled="true"/>
                                            <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                        </label>
                                    </c:forEach>
                                </div>
                                <div class="cdListContainer CLASS03" style="display:none;">
                                    <div class="row">
                                        <div class="col-sm-1 pr0 cdnm">

                                        </div>
                                    </div>
                                    <c:forEach  var="cdList" items="${cdList3}" varStatus="status">
                                        <label class="checkbox-inline c-checkbox">
                                            <input type="checkbox" class="checkboxSingle" data-name="ctgryCdList" name="ctgryCd" value="${cdList.cd}" disabled="true"/>
                                            <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                        </label>
                                    </c:forEach>
                                </div>
                                <!-- 끝 -->

                            </div>
                            <!--과정분류 2뎁스 생성구간 끝-->

                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습방식</label>
                            <div class="col-sm-5">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxAll" />
                                    <span class="ion-checkmark-round"></span> 전체
                                </label>


                                <c:forEach var="cdList" items="${classTypeList.STDUY_MTHD}" varStatus="status">
                                    <label class="checkbox-inline c-checkbox">
                                        <input type="checkbox" class="checkboxSingle" data-name="stduyMthdCdList" value="${cdList.cd}" name="stduyMthdCd" <c:if test="${fn:contains(rtnData.stduyMthdCd, cdList.cd)}">checked</c:if> />
                                        <span class="ion-checkmark-round"></span> ${cdList.cdNm}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">학습시간</label>
                            <div class="col-sm-4">
                                <div class="row">
                                    <div class="col-sm-1 pr0">
                                        학습일
                                    </div>
                                    <div class="col-sm-3 pr0">
                                        <select class="form-control input-sm" name="stduyDdCd">
                                            <option value="">전체</option>
                                            <c:forEach var="cdList" items="${classTypeList.STDUY_DD}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${rtnData.stduyDdCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-sm-1 pr0">
                                        학습시간
                                    </div>
                                    <div class="col-sm-3 pr0">
                                        <select class="form-control input-sm" name="stduyTimeCd">
                                            <option value="">전체</option>
                                            <c:forEach var="cdList" items="${classTypeList.STDUY_TIME}" varStatus="status">
                                                <option value="${cdList.cd}" <c:if test="${rtnData.stduyTimeCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>

                    <fieldset>
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">노출여부</label>
                            <div class="col-sm-5">
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxAll" />
                                    <span class="ion-checkmark-round"></span> 전체
                                </label>
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle" data-name="expsYnList" value="Y" name="expsYn" <c:if test="${fn:contains(rtnData.expsYnList, 'Y')}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 노출
                                </label>
                                <label class="checkbox-inline c-checkbox">
                                    <input type="checkbox" class="checkboxSingle" data-name="expsYnList" value="N" name="expsYn" <c:if test="${fn:contains(rtnData.expsYnList, 'N')}">checked</c:if> />
                                    <span class="ion-checkmark-round"></span> 미노출
                                </label>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset class="last-child">
                        <div class="form-group text-sm">
                            <label class="col-sm-1 control-label">검색키워드</label>
                            <div class="col-sm-4">
                                <div class="row">
                                    <div class="col-sm-3 pr0">
                                        <select class="form-control input-sm" data-name="f">
                                            <option value="">전체</option>
                                            <option value="1" <c:if test="${rtnData.f eq '1'}">selected</c:if>>과정명</option>
                                            <option value="2" <c:if test="${rtnData.f eq '2'}">selected</c:if>>최초등록자</option>
                                            <option value="3" <c:if test="${rtnData.f eq '3'}">selected</c:if>>최종수정자</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-9 pr0">
                                        <input type="text" class="form-control input-sm" data-name="q" value="${rtnData.q}" maxlength="50" />
                                    </div>
                                </div>
                            </div>
                            <div class="pull-left ml-sm">
                                <button type="button" class="btn btn-inverse btn-sm" id="btnSearch">검색</button>
                                <button type="button" class="btn btn-default btn-sm" id="btnRefresh">초기화</button>
                            </div>
                        </div>
                    </fieldset>


                    <hr class="mt0" />

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            <em class="ion-play mr-sm"></em>${pageTitle} 목록 (총 <span id="listContainerTotCnt">0</span> 건)
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
                                <th class="text-center">
                                    <label class="checkbox-inline c-checkbox">
                                        <input type="checkbox" class="checkboxAll notRequired" title="전체선택" />
                                        <span class="ion-checkmark-round"></span>
                                    </label>
                                </th>
                                <th class="text-center">번호</th>
                                <th class="text-center">과정분류</th>
                                <th class="text-center">과정명</th>
                                <th class="text-center">학습방식</th>
                                <th class="text-center">학습시간</th>
                                <th class="text-center">최초 등록자</th>
                                <th class="text-center">최초 등록일시</th>
                                <th class="text-center">최종 수정자</th>
                                <th class="text-center">최종 수정일시</th>
                                <th class="text-center">노출여부</th>
                            </tr>
                            </thead>
                            <!-- 리스트 목록 결과 -->
                            <tbody id="listContainer"/>
                        </table>
                        <!-- 페이징 버튼 -->
                        <div id="pagingContainer"/>
                    </div>
                </div>

                <div class="modal-footer row">
                    <div class="text-center">
                        <button type="button" class="btn btn-success down mt btnCouseSrchLayerChoice">선택</button>
                    </div>
                </div>
                <!--리스트 종료 -->
            </form>
        </div>
    </div>
</div>