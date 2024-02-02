<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage" data-controller="controller/eb/ebm/EBMEduApplyListCtrl">
    <form class="form-horizontal" name="frmSearch" method="post" action="" data-del-type="none">
        <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
        <!-- 페이징 버튼 사이즈 -->
        <input type="hidden" id="pageRowSize" name="pageRowSize" value="${ rtnData.pageRowSize }" />
        <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
        <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />




        <!-- content 영역 start -->
        <div class="cont-wrap">

            <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
            <div class="sub-top-vis-area">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
                </div>
            </div>

            <div class="divide-con-area">

                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />


                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="sec-con-area">
                                    <div class="info-head">
                                        <div class="left">
                                            <p class="article-total-count f-body2">총 <span>1065</span>건</p>
                                            <div class="sort-select">
                                                <div class="form-select txt-select">
                                                    <select id="srchOrder" name="srchOrder" title="정렬 바꾸기">
                                                        <option value="1" selected>접수일자순</option>
                                                        <option value="2">교육일자순</option>
                                                        <option value="3">학습시간 짧은순</option>
                                                        <option value="4">학습시간 긴 순</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="right">
                                            <a class="filter-open-btn" href="javascript:" title="필터 열기">
                                                <span>필터<!--<b class="filter-count">(7)</b>--></span><!-- 2024-01-09 filter-conut 삭제 -->
                                            </a>
                                        </div>
                                    </div>
                                    <div class="filter-popup">
                                        <div class="for-flex">
                                            <div class="for-center">
                                                <div class="filter-con-area">
                                                    <div class="data-enter-form">
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-head">과정분류</p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-group">
                                                                            <div class="form-select">
                                                                                <select name="prntCd" id="prntCd" title="과정 1차 분류" class="classType">
                                                                                    <option value="" selected>전체</option>
                                                                                    <c:forEach var="cdList" items="${classTypeList.CLASS_TYPE}" varStatus="status">
                                                                                        <option value="${cdList.cd}" <c:if test="${rtnDto.prntCd eq cdList.cd}">selected</c:if> >${cdList.cdNm}</option>
                                                                                    </c:forEach>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-select">
                                                                                <select name="ctgryCd" id="ctgryCd" title="과정 2차 분류" disabled=""><!-- 1차 분류 선택 시 disabled 해제 -->
                                                                                    <option value="" selected="">전체</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-head">학습방식</p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="opt-group total-check-w">

                                                                            <div class="form-checkbox total-check">
                                                                                <input type="checkbox" data-name="stduyMthdCdList" id="stduyMthdCd" name="stduyMthdCd">
                                                                                <label for="stduyMthdCd">전체</label>
                                                                            </div>

                                                                            <c:forEach var="cdList" items="${classTypeList.STDUY_MTHD}" varStatus="status">
                                                                                <div class="form-checkbox">
                                                                                    <input type="checkbox" data-name="stduyMthdCdList" id="stduyMthdCd${status.index}" name="stduyMthdCd" value="${cdList.cd}" <c:if test="${fn:contains(rtnData.stduyMthdCd, cdList.cd)}">checked</c:if>>
                                                                                    <label for="stduyMthdCd${status.index}">${cdList.cdNm}</label>
                                                                                </div>
                                                                            </c:forEach>


                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-head">수료여부</p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="opt-group total-check-w">
                                                                            <div class="form-checkbox total-check">
                                                                                <input type="checkbox" data-name="cmptnYnList" id="cmptnYn" name="cmptnYn">
                                                                                <label for="cmptnYn">전체</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="cmptnYnList" id="cmptnYn1" name="cmptnYn" value="Y">
                                                                                <label for="cmptnYn1">수료</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="cmptnYnList" id="cmptnYn2" name="cmptnYn2" value="N">
                                                                                <label for="cmptnYn2">미수료</label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-head">교육상태</p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="opt-group total-check-w">
                                                                            <div class="form-checkbox total-check">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd1" name="sttsCd" value="">
                                                                                <label for="sttsCd1">전체</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd2" name="sttsCd" value="신청">
                                                                                <label for="sttsCd2">신청</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd3" name="sttsCd" value="교육대기">
                                                                                <label for="sttsCd3">교육대기</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox"data-name="sttsCdList"  id="sttsCd4" name="sttsCd" value="교육중">
                                                                                <label for="sttsCd4">교육 중</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd5" name="sttsCd" value="교육완료">
                                                                                <label for="sttsCd5">교육완료</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd6" name="sttsCd" value="신청취소">
                                                                                <label for="sttsCd6">신청취소</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd7" name="sttsCd" value="교육양도">
                                                                                <label for="sttsCd7">교육양도</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd8" name="sttsCd" value="미선발">
                                                                                <label for="sttsCd8">미선발</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" data-name="sttsCdList" id="sttsCd9" name="sttsCd" value="협의중">
                                                                                <label for="sttsCd9">협의중</label>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-head">키워드</p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="form-input srch-input w-longer">
                                                                            <input type="text" name="q" id="q" placeholder="과정명 또는 교육장소 입력">
                                                                            <div class="input-btn-wrap">
                                                                                <button class="delete-btn" title="지우기" type="button"></button>
                                                                                <button class="srch-btn" title="검색"></button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="th">
                                                                <p class="title f-head">기간검색</p>
                                                            </div>
                                                            <div class="td">
                                                                <div class="data-line-w">
                                                                    <div class="data-line">
                                                                        <div class="middle-line">
                                                                            <div class="opt-group">
                                                                                <div class="form-radio">
                                                                                    <input type="radio" id="srchDate1" name="srchDate" value="1" checked>
                                                                                    <label for="srchDate1">교육기간</label>
                                                                                </div>
                                                                                <div class="form-radio">
                                                                                    <input type="radio" id="srchDate2" name="srchDate" value="2" >
                                                                                    <label for="srchDate2">신청일시</label>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="middle-line">
                                                                            <div class="form-group form-calendar">
                                                                                <div class="form-input">
                                                                                    <input type="text" placeholder="2023.01.01" name="strtDt" id="strtDt" >
                                                                                </div>
                                                                                <div class="form-input calendar">
                                                                                    <input type="text" placeholder="2023.01.01"  name="endDt" id="endDt">
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="btn-wrap">
                                                    <div class="btn-set">
                                                        <button class="btn-solid small gray-bg btn-role-close" type="button"><span>닫기</span><!--ㅠ-->
                                                        </button></div>
                                                    <div class="btn-set">
                                                        <button class="btn-solid small gray-bg" id="btnRefresh" type="button"><span>필터 초기화</span></button>
                                                        <button class="btn-solid small black-bg" id="btnSearch" type="button"><span>적용</span></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="trainings-list-w" id="listContainer">

                                    </div>

                                    <div class="btn-wrap add-load align-center">
                                        <a class="btn-solid small black-line pageSet" href="javascript:"><span>더보기</span><span class="item-count">(9/40)</span></a>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>




            </div>
        </div>
        <!-- content 영역 end -->


    </form>


</div>