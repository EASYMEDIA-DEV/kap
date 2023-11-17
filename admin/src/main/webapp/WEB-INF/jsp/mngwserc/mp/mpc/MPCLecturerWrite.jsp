<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpc/MPCLecturerWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="isttrSeq" name="isttrSeq" value="${rtnInfo.isttrSeq}" />
            <h6 class="mt-lg"> 강사 기본 정보 </h6>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">이름</label>
                    <div class="col-sm-5">${rtnInfo.name}</div>

                    <label class="col-sm-1 control-label">소속</label>
                    <div class="col-sm-5">${rtnInfo.ffltnNm}</div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">휴대폰번호</label>
                    <div class="col-sm-5">${ kl:hpNum(rtnInfo.hpNo)}</div>
                    <label class="col-sm-1 control-label">이메일</label>
                    <div class="col-sm-5">${rtnInfo.email}</div>
                </div>
            </fieldset>
            <fieldset>
                <div class="form-group text-sm">
                    <label class="col-sm-1 control-label">최초 등록일시</label>
                    <div class="col-sm-5">${ kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</div>
                    <label class="col-sm-1 control-label">관련사업</label>
                    <div class="col-sm-5">${ rtnInfo.mngBsnCd }</div>
                </div>
            </fieldset>
            <ul class="nav nav-tabs" id="myTabs">
                <li class="active tabClick"><a data-toggle="tab" href="#dtl">강사 상세정보</a></li>
                <li class="tabClick"><a data-toggle="tab" href="#edu">교육 사업 현황</a></li>
                <span class="dtl-tab" style="margin-left:55%"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
            </ul>

            <div class="tab-content">
                <div id="dtl" class="tab-pane fade in active">
                    <div id="tab1">
                    </div>
                </div>
                <div id="edu" class="tab-pane fade">
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            교육 사업 현황 (총 <span id="listContainerTotCnt">0</span> 건)
                        </h6>
                        <div class="pull-right ml-sm">
                            <select class="form-control input-sm listRowSizeContainer" >
                                <jsp:include page="/WEB-INF/jsp/mngwserc/co/COPageOption.jsp">
                                    <jsp:param name="listRowSize" value="${ rtnData.listRowSize }" />
                                </jsp:include>
                            </select>
                        </div>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">사업년도</th>
                            <th class="text-center">회차</th>
                            <th class="text-center">교육상태</th>
                            <th class="text-center">과정분류</th>
                            <th class="text-center">과정명</th>
                            <th class="text-center">학습방식</th>
                            <th class="text-center">학습시간</th>
                            <th class="text-center">교육기간</th>
                            <th class="text-center">강사</th>
                            <th class="text-center">강사 소속</th>
                            <th class="text-center">모집방식</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="listContainer"/>
                    </table>
                    <!-- 페이징 버튼 -->
                    <div id="pagingContainer"></div>
                </div>
             </div>
            <hr />

            <div class="clearfix">
                <div class="pull-left">
                    <button type="button" class="btn btn-sm btn-default" id="btnList" data-param="${strPam}">목록</button>
                </div>
                <div style="float:right">
                    <button type="submit" class="btn btn-sm btn-success dtl-tab" id="btnSave" >저장</button>
                </div>
            </div>
        </form>
    </div>
</div>
