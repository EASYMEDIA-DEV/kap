<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <!--
      신청 페이지: apply-page 클래스 추가
      그 외 페이지: basic-page 클래스 추가
    -->
    <!--
      교육 사업: edu-biz
      컨실팅 사업: consult-biz
      상생 사업: coexisting-biz
    -->
    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-notice.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-notice-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">
        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <%--<div class="lnb-area">
            <div class="for-motion">
                <div class="lnb-list">
                    <a class="btn-two-depth single-menu active" href="javascript:"><span>교육사업 소개</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                </div>
                <div class="lnb-list">
                    <a class="btn-two-depth single-menu" href="javascript:"><span>교육신청</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                </div>
                <div class="lnb-list">
                    <a class="btn-two-depth single-menu" href="javascript:"><span>방문교육</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                </div>
            </div>
        </div>--%>
        <!--LNB 종료-->
        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="btn-wrap outer-fix-btn">
                            <div class="btn-set">
                            </div>
                            <div class="btn-set">
                                <button class="btn-solid small gray-bg"><span>전체교육일정</span></button>
                            </div>
                        </div>

                        <div class="lnb-link-area nonmember">
                            <a class="btn-text-icon black-arrow" href="javascript:"><span>비회원 신청내역 조회</span></a>
                        </div>

                        <div class="info-head">
                            <div class="left">
                                <p class="article-total-count f-body2">총 <span>1065</span>건</p>
                                <div class="sort-select">
                                    <div class="form-select txt-select">
                                        <select id="" title="정렬 바꾸기">
                                            <option value="" selected>신청마감순</option>
                                            <option value="">교육일자순</option>
                                            <option value="">학습시간 짧은순</option>
                                            <option value="">학습시간 긴 순</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="right">
                                <a class="filter-open-btn" href="javascript:" title="필터 열기">
                                    <span>필터<b class="filter-count">(7)</b></span>
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
                                                    <p class="title f-head">접수상태</p>
                                                </div>
                                                <div class="td">
                                                    <div class="data-line-w">
                                                        <div class="data-line">
                                                            <div class="opt-group total-check-w">
                                                                <div class="form-checkbox total-check">
                                                                    <input type="checkbox" id="acceptStateChk1" name="acceptStateChk" checked>
                                                                    <label for="acceptStateChk1">전체</label>
                                                                </div>
                                                                <div class="form-checkbox">
                                                                    <input type="checkbox" id="acceptStateChk2" name="acceptStateChk" checked>
                                                                    <label for="acceptStateChk2">접수대기</label>
                                                                </div>
                                                                <div class="form-checkbox">
                                                                    <input type="checkbox" id="acceptStateChk3" name="acceptStateChk" checked>
                                                                    <label for="acceptStateChk3">접수중</label>
                                                                </div>
                                                                <div class="form-checkbox">
                                                                    <input type="checkbox" id="acceptStateChk4" name="acceptStateChk" checked>
                                                                    <label for="acceptStateChk4">마감</label>
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
                                                                        <label for="searchPeriodRadio2">접수기간</label>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="middle-line">
                                                                <!-- 2023-12-07 수정 -->
                                                                <div class="form-group form-calendar">
                                                                    <div class="form-input">
                                                                        <input type="text" placeholder="2023.01.01">
                                                                    </div>
                                                                    <div class="form-input calendar">
                                                                        <input type="text" placeholder="2023.01.01">
                                                                    </div>
                                                                </div>
                                                                <!-- // 2023-12-07 수정 -->
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


                        <div class="board-list">
                            <div class="training-swiper-area">
                                <div class="swiper-container training-swiper">
                                    <div class="swiper-wrapper marquee_wrapper1">
                                        <a class="swiper-slide marquee_item1 end" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <!-- <img src="/common/images/img-main-training-offline-01.jpg" alt=""> -->
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>세미나</span></p>
                                                    <p class="label"><span>업종별기술세미나</span></p>
                                                    <p class="label"><span>업종별기술세미나</span></p>
                                                    <p class="label"><span>업종별기술세미나</span></p>
                                                    <p class="label"><span>업종별기술세미나</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초꼭 알아야 할 품질 기초꼭 알아야 할 품질 기초꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger end"><span>접수마감</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger"><span>접수마감</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 waiting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="top-label-area">
                                                    <p class="box-label bigger available"><span>비회원 신청 가능</span></p>
                                                </div>

                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger waiting"><span>접수대기</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger waiting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                        <a class="swiper-slide marquee_item1 accepting" href="javascript:">
                                            <!--
                                              접수중: accepting
                                              접수대기: waiting
                                              접수마감: end
                                            -->
                                            <div class="img-area">
                                                <img src="/common/images/img-main-training-offline-01.jpg" alt="">
                                            </div>
                                            <div class="txt-area">
                                                <div class="sort-label-area">
                                                    <p class="label"><span>품질아카데미</span></p>
                                                    <p class="label"><span>품질학교</span></p>
                                                </div>
                                                <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                <div class="date-info-w">
                                                    <div class="list">
                                                        <p class="txt">교육기간</p>
                                                        <p class="date">2023.09.18 10:00 ~ 2023.09.19 11:00 (n일간)</p>
                                                    </div>
                                                    <div class="list">
                                                        <p class="txt">집체교육</p>
                                                        <p class="date"><span class="item">2일(14시간)</span><span class="item">정원30명(모집 후 선발)</span></p>
                                                    </div>
                                                </div>
                                                <div class="status-info-w">
                                                    <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                    <p class="box-label bigger accepting"><span>접수중</span></p>
                                                </div>
                                            </div>
                                            <!-- hover 시 노출되는 영역 -->
                                            <div class="hover-area">
                                                <div class="for-position">
                                                    <div class="sort-label-area">
                                                        <p class="label"><span>품질아카데미</span></p>
                                                        <p class="label"><span>품질학교</span></p>
                                                    </div>
                                                    <p class="training-name">22222꼭 알아야 할 품질 기초</p>
                                                    <div class="date-info-w">
                                                        <div class="list">
                                                            <div class="tit">접수기간</div>
                                                            <div class="txt">2023.08.01 13:00 ~ 2023.08.31 14:00</div>
                                                        </div>
                                                        <div class="list">
                                                            <div class="tit">교육기간</div>
                                                            <div class="txt">2023.09.18 10:00 ~ 2023.09.19 11:00 (3일간)</div>
                                                        </div>
                                                        <div class="list ">
                                                            <div class="tit">집체교육</div>
                                                            <div class="txt status-txt">2일(14시간) / 정원30명(모집 후 선발)</div>
                                                        </div>
                                                    </div>
                                                    <div class="status-info-w">
                                                        <p class="box-label bigger"><span>글로벌상생교육센터(GPC)(경주)</span></p>
                                                        <p class="box-label bigger accepting"><span>접수중</span></p>
                                                    </div>
                                                    <div class="btn-wrap">
                                                        <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
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
<!-- content 영역 end -->