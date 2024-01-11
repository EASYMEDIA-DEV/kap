<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" data-controller="controller/co/COMypageCtrl">
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
                    <p class="page-tit f-xlarge-title"><span class="for-move">교육 사업 신청내역</span></p>
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
                                                    <select id="" title="정렬 바꾸기">
                                                        <option value="" selected="">신청마감순</option>
                                                        <option value="">교육일자순</option>
                                                        <option value="">학습시간 짧은순</option>
                                                        <option value="">학습시간 긴 순</option>
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
                                                                                <select id="" title="과정 1차 분류">
                                                                                    <option value="" selected="">전체</option>
                                                                                    <option value="">option1</option>
                                                                                    <option value="">option2</option>
                                                                                    <option value="">option3</option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-select">
                                                                                <select id="" title="과정 2차 분류" disabled=""><!-- 1차 분류 선택 시 disabled 해제 -->
                                                                                    <option value="" selected="">전체</option>
                                                                                    <option value="">option1</option>
                                                                                    <option value="">option2</option>
                                                                                    <option value="">option3</option>
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
                                                                                <input type="checkbox" id="studyingWayChk1" name="studyingWayChk" checked>
                                                                                <label for="studyingWayChk1">전체</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="studyingWayChk2" name="studyingWayChk" checked>
                                                                                <label for="studyingWayChk2">집체교육</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="studyingWayChk3" name="studyingWayChk" checked>
                                                                                <label for="studyingWayChk3">온라인교육</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="studyingWayChk4" name="studyingWayChk" checked>
                                                                                <label for="studyingWayChk4">집체교육+온라인</label>
                                                                            </div>
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
                                                                                <input type="checkbox" id="completionChk1" name="completionChk" checked>
                                                                                <label for="completionChk1">전체</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="completionChk2" name="completionChk" checked>
                                                                                <label for="completionChk2">수료</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="completionChk3" name="completionChk" checked>
                                                                                <label for="completionChk3">미수료</label>
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
                                                                                <input type="checkbox" id="eduStatusChk1" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk1">전체</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk2" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk2">신청</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk3" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk3">교육대기</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk4" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk4">교육 중</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk5" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk5">교육완료</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk6" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk6">신청취소</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk7" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk7">교육양도</label>
                                                                            </div>
                                                                            <div class="form-checkbox">
                                                                                <input type="checkbox" id="eduStatusChk8" name="eduStatusChk" checked>
                                                                                <label for="eduStatusChk8">미선발</label>
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
                                                                            <input type="text" placeholder="과정명 또는 교육장소 입력">
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
                                                                                    <input type="radio" id="searchPeriodRadio1" name="searchPeriodRadio" checked>
                                                                                    <label for="searchPeriodRadio1">교육기간</label>
                                                                                </div>
                                                                                <div class="form-radio">
                                                                                    <input type="radio" id="searchPeriodRadio2" name="searchPeriodRadio">
                                                                                    <label for="searchPeriodRadio2">신청일시</label>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="middle-line">
                                                                            <div class="form-group form-calendar">
                                                                                <div class="form-input">
                                                                                    <input type="text" placeholder="2023.01.01">
                                                                                </div>
                                                                                <div class="form-input calendar">
                                                                                    <input type="text" placeholder="2023.01.01">
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
                                                        <button class="btn-solid small gray-bg"><span>필터 초기화</span></button>
                                                        <button class="btn-solid small black-bg"><span>적용</span></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="trainings-list-w">
                                        <div class="training-confirm">
                                            <div class="top-info">
                                                <div class="training-view-page">
                                                    <div class="training-list">
                                                        <div class="img-area">
                                                            <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                                        </div>
                                                        <div class="txt-area">
                                                            <div class="top-line">
                                                                <div class="sort-label-area">
                                                                    <p class="label"><span>품질아카데미</span></p>
                                                                    <p class="label"><span>품질학교</span></p>
                                                                </div>
                                                                <p class="training-name f-title1">꼭 알아야 할 품질 기초 두줄로 떨어질 때</p>
                                                            </div>
                                                            <div class="group">
                                                                <p class="f-head">8회차</p>
                                                                <div class="status-info-w">
                                                                    <p class="box-label bigger waiting"><span>교육대기</span></p><!-- 2023-12-18 라벨 값 변경 -->
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bot-info">
                                                <div class="index-list-w">
                                                    <div class="list-item">
                                                        <div class="cont">
                                                            <div class="cont-area">
                                                                <div class="info-list-w ">
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">학습방식</p>
                                                                        <p class="txt f-body2">집체교육</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">신청일시</p>
                                                                        <p class="txt f-body2">2023.02.01 10:00</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">모집방식</p>
                                                                        <p class="txt f-body2">모집 후 선발</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">선발여부</p>
                                                                        <p class="txt f-body2">선발</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">학습시간</p>
                                                                        <p class="txt f-body2">2일/14시간</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">교육장소</p>
                                                                        <p class="txt f-body2">글로벌상생협력센터(GPC)(경주)</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">교육기간</p>
                                                                        <p class="txt f-body2">2023.02.01 10:00 ~ 2023.02.03 10:00 (3일간)</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">수료여부</p>
                                                                        <p class="txt f-body2">미수료</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="training-confirm">
                                            <div class="top-info">
                                                <div class="training-view-page">
                                                    <div class="training-list">
                                                        <div class="txt-area">
                                                            <div class="top-line">
                                                                <div class="sort-label-area">
                                                                    <p class="label"><span>기술</span></p>
                                                                </div>
                                                                <p class="training-name f-title1">방문교육</p>
                                                            </div>
                                                            <div class="status-info-w">
                                                                <p class="box-label bigger"><span>신청</span></p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="bot-info">
                                                <div class="index-list-w">
                                                    <div class="list-item">
                                                        <div class="cont">
                                                            <div class="cont-area">
                                                                <div class="info-list-w ">
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">신청일시</p>
                                                                        <p class="txt f-body2">2023.02.01 10:00</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">교육인원</p>
                                                                        <p class="txt f-body2">00명</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">교육희망일</p>
                                                                        <p class="txt f-body2">2023.02.01</p>
                                                                    </div>
                                                                    <div class="info-list">
                                                                        <p class="tit f-caption2">교육시간</p>
                                                                        <p class="txt f-body2">4시간</p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="btn-wrap add-load align-center">
                                        <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count">(1/50)</span></a>
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