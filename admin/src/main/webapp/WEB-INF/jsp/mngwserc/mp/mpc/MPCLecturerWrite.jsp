<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rtnData" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpc/MPCLecturerWriteCtrl">
        <c:choose>
            <c:when test="${not empty rtnInfo}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 상세/수정</h6>
            </c:when>
            <c:when test="${empty rtnInfo}">
                <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle} 등록</h6>
            </c:when>
        </c:choose>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <c:if test="${not empty rtnInfo}">
                <!-- 현재 페이징 번호 -->
                <input type="hidden" id="pageIndex" name="pageIndex" value="${ rtnData.pageIndex }" />
                <!-- 페이징 버튼 사이즈 -->
                <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
                <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            </c:if>
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnData.detailsKey}" />
            <!-- CSRF KEY -->
            <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="isttrSeq" name="isttrSeq" value="${rtnInfo.isttrSeq}" />
            <c:if test="${not empty rtnInfo}">

                <h6 class="mt-lg"> 강사 기본 정보 </h6>
                <div class="table-responsive col-sm-12 p0 m0">
                    <table class="table">
                        <colgroup>
                            <col style="width:10%;">
                            <col style="width:40%;">
                            <col style="width:10%;">
                            <col style="width:40%;">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">이름</th>
                            <td>${rtnInfo.name}</td>
                            <th scope="row" class="bg-gray-lighter">소속</th>
                            <td>${rtnInfo.ffltnNm}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">휴대폰번호</th>
                            <td>${rtnInfo.hpNo}</td>
                            <th scope="row" class="bg-gray-lighter">이메일</th>
                            <td>${rtnInfo.email}</td>
                        </tr>
                        <tr>
                            <th scope="row" class="bg-gray-lighter">등록일</th>
                            <td> ${kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '-')}</td>
                            <th scope="row" class="bg-gray-lighter">최종접속일</th>
                            <td> - </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <ul class="nav nav-tabs" id="myTabs">
                    <li class="active tabClick"><a data-toggle="tab" href="#">강사 상세정보</a></li>
                    <li class="tabClick"><a data-toggle="tab" href="#edu">교육 사업 현황</a></li>
                    <li class="tabClick"><a data-toggle="tab" href="#win">상생 사업 현황</a></li>
                    <div class="pull-right">
                        <button type="button" class="btn btn-inverse btn-sm mb-sm" id="btnExcelDown">엑셀다운로드</button>
                    </div>
                </ul>
            </c:if>
            <div class="tab-content">
                <div id="dtl" class="tab-pane fade in active">
                    <div id="tab1">
                        <fieldset class="mt-sm">
                            <div class="form-group text-sm">
                                <label class="col-sm-1 control-label">이름<span class="star text-danger"> *</span></label>
                                <div class="col-sm-5">
                                    <input type="text" class="form-control input-sm" id="name" name="name" value="${rtnInfo.name}" title="이름" placeholder="이름 입력" style="width: 200px;"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <label class="col-sm-1 control-label">소속<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="ffltnNm" name="ffltnNm" value="${rtnInfo.ffltnNm}" title="소속" placeholder="소속 입력" style="width: 220px;"/>
                            </div>
                        </fieldset>
                        <fieldset>
                            <label class="col-sm-1 control-label">부서</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="deptNm" name="deptNm" value="${rtnInfo.deptNm}" title="부서" placeholder="부서 입력" style="width: 220px;"/>
                            </div>
                        </fieldset>
                        <fieldset>
                            <label class="col-sm-1 control-label">직급</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="pstnNm" name="pstnNm" value="${rtnInfo.pstnNm}" title="직급" placeholder="직급 입력" style="width: 220px;"/>
                            </div>
                        </fieldset>
                        <fieldset>
                            <label class="col-sm-1 control-label">전화번호</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="telNo" name="telNo" value="${rtnInfo.telNo}" title="전화번호" placeholder="전화번호 입력"  oninput="this.value=this.value.replace(/[^0-9]/g, '')" style="width: 220px;"/>
                            </div>
                        </fieldset>
                        <fieldset>
                            <label class="col-sm-1 control-label">휴대폰번호<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm notRequired" id="hpNo" name="hpNo" value="${rtnInfo.hpNo}" title="휴대폰번호" placeholder="휴대폰번호 입력" style="width: 220px;"/>
                            </div>
                        </fieldset>
                        <fieldset class="form-inline">
                            <label class="col-sm-1 control-label">이메일<span class="star"> *</span></label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control input-sm"  title="이메일" id="email" value="${rtnInfo.email}" name="email" maxlength="50" placeholder="이메일 주소 입력"/>
                                <button type="button" class="btn btn-sm" id="dupEmail" >중복확인</button>
                            </div>
                        </fieldset>
                        <fieldset>
                            <label class="col-sm-1 control-label">특이사항</label>
                            <div class="col-sm-5">
                                <textarea class="form-control input-sm notRequired" id="spclCntn" name="spclCntn" value="${rtnInfo.spclCntn}" title="특이사항" placeholder="특이사항 입력">${rtnInfo.spclCntn}</textarea>
                            </div>
                        </fieldset>
                        <hr />
                        <div class="clearfix">
                            <div class="pull-left">
                                <button type="button" class="btn btn-sm btn-default" id="btnList" data-str-pam="${strPam}">목록</button>
                            </div>
                            <div class="pull-right">
                                <c:choose>
                                    <c:when test="${ not empty rtnInfo}">
                                        <button type="submit" class="btn btn-sm btn-success" >저장</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-sm btn-success">저장</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <c:if test="${ not empty rtnInfo }">
                            <h6 class="mt"><em class="ion-play mr-sm"></em>수정이력</h6>
                            <div class="table-responsive ">
                                <table class="table text-sm">
                                    <tbody>
                                    <tr>
                                        <th>최초 등록자</th>
                                        <td>${ rtnInfo.regName }(${ rtnInfo.regId })</td>
                                        <th>최초 작성일</th>
                                        <td>${ kl:convertDate(rtnInfo.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }</td>
                                    </tr>
                                    <tr>
                                        <th>최종 수정자</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ not empty rtnInfo.modName }">
                                                    ${ rtnInfo.modName }(${ rtnInfo.modId })
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <th>최종 수정일</th>
                                        <td>
                                            <c:choose>
                                                <c:when test="${ not empty rtnInfo.modDtm }">
                                                    ${ kl:convertDate(rtnInfo.modDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm', '') }
                                                </c:when>
                                                <c:otherwise>-</c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
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
        </form>
    </div>
</div>

<div class="modal fade excel-down" tabindex="-1" role="dialog" >
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
                    <button type="button" class="btn btn-primary down mt">다운로드</button>
                </div>
            </div>
        </div>
    </div>
</div>
