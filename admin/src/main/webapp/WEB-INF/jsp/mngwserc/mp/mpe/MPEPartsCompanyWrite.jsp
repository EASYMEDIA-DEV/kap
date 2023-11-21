<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/include/el.jspf"%>

<c:set var="rtnDto" value="${ not empty rtnInfo ? rtnInfo : rtnData}" />
<c:set var="sqInfoListCnt" value="${ fn:length(sqInfoList.list)}" />
<div class="container-fluid">
    <div class="card-body" data-controller="controller/mp/mpe/MPEPartsCompanyWriteCtrl">
        <h6 class="mt0"><em class="ion-play mr-sm"></em>${pageTitle}</h6>
        <form class="form-horizontal" id="frmData" name="frmData" method="post" >
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="hidden" class="notRequired" id="detailsKey" name="detailsKey" value="${rtnInfo.bsnmNo}" />
            <input type="hidden" class="notRequired" id="eduListContainerTotCnt" name="eduListContainerTotCnt" value="" />
            <c:if test="${not empty rtnInfo.bsnmNo}">
                <ul class="nav nav-tabs" id="myTabs">
                    <li class="active tabClick"><a data-toggle="tab" href="#dtl">부품사 기본정보</a></li>
                    <li class="tabClick"><a data-toggle="tab" href="#edu">부품사 실적정보</a></li>
                    <span class="dtl-tab" style="margin-left:55%"><span style="color:red">*</span>표시는 필수 기재 항목입니다.</span>
                </ul>
            </c:if>
            <div class="tab-content">
                <div id="dtl" class="tab-pane fade in active">
                    <div id="tab1">
                    </div>
                </div>
                <div id="edu" class="tab-pane fade">
                    <h6 class="mt-lg">종합현황</h6>
                    <div class="table table-hover table-striped">
                        <table class="table">
                            <colgroup>
                                <col style="width:30%;">
                                <col style="width:30%;">
                                <col style="width:30%;">
                            </colgroup>
                                <tbody>
                                <tr>
                                    <th class="bg-gray-lighter">교육사업</th>
                                    <th class="bg-gray-lighter">컨설팅사업</th>
                                    <th class="bg-gray-lighter">상생사업</th>
                                </tr>
                                <tr>
                                    <td class="text-center">52</td>
                                    <td class="text-center">52</td>
                                    <td class="text-center">52</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            교육사업
                        </h6>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">구분</th>
                            <th class="text-center">누계</th>
                            <th class="text-center">2023년</th>
                            <th class="text-center">2022년</th>
                            <th class="text-center">2021년</th>
                            <th class="text-center">2020년</th>
                            <th class="text-center">2019년 이전</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="eduListContainer"/>
                    </table>

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            기술지도
                        </h6>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">업종</th>
                            <th class="text-center">지도위원</th>
                            <th class="text-center">지도기간</th>
                            <th class="text-center">초도방문자료</th>
                            <th class="text-center">킥오프자료</th>
                            <th class="text-center">랩업자</th>
                            <th class="text-center">회사소개자료</th>
                            <th class="text-center">공정개선율</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="techListContainer"/>
                    </table>

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            경영컨설팅
                        </h6>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">업종</th>
                            <th class="text-center">지도위원</th>
                            <th class="text-center">지도기간</th>
                            <th class="text-center">초도방문자료</th>
                            <th class="text-center">킥오프자료</th>
                            <th class="text-center">랩업자</th>
                            <th class="text-center">회사소개자료</th>
                            <th class="text-center">공정개선율</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="consultListContainer"/>
                    </table>

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            자금지원
                        </h6>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">구분</th>
                            <th class="text-center">추천위원</th>
                            <th class="text-center">재단지원금</th>
                            <th class="text-center">총투자비</th>
                            <th class="text-center">실지급일</th>
                            <th class="text-center">장비명</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="fundingListContainer"/>
                    </table>

                    <div class="clearfix">
                        <h6 class="pull-left mt0">
                            자동차부품산업대상
                        </h6>
                    </div>
                    <table class="table table-hover table-striped" >
                        <thead>
                        <tr>
                            <th class="text-center">년도</th>
                            <th class="text-center">지원결과</th>
                            <th class="text-center">훈격</th>
                            <th class="text-center">포상부문</th>
                            <th class="text-center">포상금액(만원)</th>
                            <th class="text-center">부품사명</th>
                            <th class="text-center">수상자명</th>
                            <th class="text-center">직급</th>
                        </tr>
                        </thead>
                        <!-- 리스트 목록 결과 -->
                        <tbody id="kapTargetListContainer"/>
                    </table>
                </div>
            </div>
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