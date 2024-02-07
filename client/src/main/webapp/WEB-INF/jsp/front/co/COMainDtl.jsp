<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<% pageContext.setAttribute("newLine", "\n"); %>
<div class="cont-wrap" data-controller="controller/co/COMainCtrl">
    <!-- main kv 영역 -->
    <!-- 2023-12-19 kv영역 마크업 수정 -->
    <section class="main-kv-sec first-time">
        <div class="roll-swiper-area" id="homeSlider">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                        <div class="swiper-slide list">
                            <div class="txt-wrap">
                                <p class="motion tit" style="color: ${list.mnHexCd}">${fn:replace(list.mnCopy, newLine, '<br>')}</p>
                                <p class="motion txt" style="color: ${list.subHexCd}">${fn:replace(list.subCopy, newLine, '<br>')}</p>
                                <div class="motion btn-wrap">
                                    <c:choose>
                                        <c:when test="${not empty list.urlUrl}">
                                            <a class="btn-solid small white-bg" <c:if test="${list.wnppYn eq 'Y'}">target="_blank"</c:if> href="${list.urlUrl}"><span>자세히 보기</span></a>
                                        </c:when>
                                        <c:otherwise>

                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="img-area">
                                <c:choose>
                                    <c:when test="${list.tagCd eq 'image'}">
                                        <img src="${list.webPath}" alt="${list.fileDsc}">
                                    </c:when>
                                    <c:otherwise>
                                        <video src="${list.webPath}" muted preload="auto" loop autoplay playsinline></video>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="slide-control-area">
            <div class="move-control">
                <div class="swiper-button-prev"></div>
                <div class="swiper-button-next"></div>
                <button class="btn-pause" type="button" title="일시 정지"></button>
            </div>
            <div class="timer-gauge">
                <div class="bar"></div>
            </div>
            <!-- 2024-01-08 마크업 수정 -->
            <div class="pagination-w">
                <p class="page-num cur-page">1</p>
                <p class="page-num total-page"></p>
            </div>
            <!-- //2024-01-08 마크업 수정 -->
        </div>

        <div class="scroll-down">
            <p class="txt">아래로</p>
            <p class="bar"></p>
        </div>
    </section>
    <!-- //2023-12-19 kv영역 마크업 수정 -->


    <!-- 사업 영역 -->
    <section class="biz-line-sec hover-accordion">
        <a class="biz-list acco-list edu" href="/education/intro/content">
            <div class="title-area" href="javascript:">
                <p class="tit">교육사업</p>
                <p class="txt only-mobile">교육과 세미나 진행</p>
            </div>
            <div class="hide-area">
                <p class="txt only-pc">자동차부품사의 인재육성 및 역량 개발을 위한 교육과 세미나를 제공합니다</p>
                <div class="btn-wrap">
                    <div class="btn-text-icon white-circle"><span>자세히 보기</span></div>
                </div>
            </div>
        </a>
        <a class="biz-list acco-list consulting" href="/consulting/tech/content">
            <div class="title-area" href="javascript:">
                <p class="tit">컨설팅사업</p>
                <p class="txt only-mobile">부품사 경쟁력 강화</p>
            </div>
            <div class="hide-area">
                <p class="txt">현장 맞춤형컨설팅을 통해 부품사의 품질기술력 향상과 경쟁력을 강화합니다</p>
                <div class="btn-wrap">
                    <div class="btn-text-icon white-circle"><span>자세히 보기</span></div>
                </div>
            </div>
        </a>
        <a class="biz-list acco-list coexisting" href="/coexistence/business/futureTech/content">
            <div class="title-area" href="javascript:">
                <p class="tit">상생사업</p>
                <p class="txt only-mobile">자금 안정화 지원</p>
            </div>
            <div class="hide-area">
                <p class="txt">부품사의 경쟁력향상과 자금 안정화, 등을 지원합니다.</p>
                <div class="btn-wrap">
                    <div class="btn-text-icon white-circle"><span>자세히 보기</span></div>
                </div>
            </div>
        </a>
        <a class="biz-list acco-list foundation" href="/foundation/intro/greeting/content">
            <div class="title-area" href="javascript:">
                <p class="tit">재단정보</p>
                <p class="txt only-mobile">자동차 산업 발전</p>
            </div>
            <div class="hide-area">
                <p class="txt">대한민국 자동차 산업의 발전을 위해 기대 그 이상을 서비스 합니다</p>
                <div class="btn-wrap">
                    <div class="btn-text-icon white-circle"><span>자세히 보기</span></div>
                </div>
            </div>
        </a>
    </section>


    <!-- 교육 영역 -->
    <section class="training-sec">
        <div class="main-inner">
            <div class="training-list-w offline scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title1 only-pc">품질 및 기술 향상, 인재육성을 위한 <br>교육과 세미나를 진행합니다</p>
                        <p class="sec-tit f-title1 only-mobile">대면 교육</p>
                    </div>
                    <div class="training-swiper-area" id="listContainer">
                    </div>
                </div>
            </div>
            <div class="training-list-w online scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title1 only-pc">온라인 학습을 통해 언제 어디서든 <br>편리하게 학습할 수 있습니다</p>
                        <p class="sec-tit f-title1 only-mobile">온라인 교육</p>
                    </div>
                    <div class="training-swiper-area" id="listContainer2">
                    </div>
 <%--                       <div class="swiper-container training-swiper">
                            <div class="swiper-wrapper marquee_wrapper2">
                                <a class="swiper-slide marquee_item2 accepting" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <img src="/common/images/img-main-training-online-01.jpg" alt="">
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>품질기초</span></p>
                                        </div>
                                        <p class="training-name">꼭 알아야 할 품질 기초</p>
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
                                            <p class="box-label bigger accepting"><span>접수중</span></p>
                                        </div>
                                    </div>
                                    <!-- hover 시 노출되는 영역 -->
                                    <div class="hover-area">
                                        <div class="for-position">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>품질기초</span></p>
                                            </div>
                                            <p class="training-name">꼭 알아야 할 품질 기초</p>
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
                                                <p class="box-label bigger accepting"><span>접수중</span></p>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <a class="swiper-slide marquee_item2 end" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <img src="/common/images/img-main-training-online-02.jpg" alt="">
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>품질기초</span></p>
                                        </div>
                                        <p class="training-name">제조 현장의 품질 실천</p>
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
                                            <p class="training-name">제조 현장의 품질 실천</p>
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
                                                <p class="box-label bigger end"><span>접수마감</span></p>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <a class="swiper-slide marquee_item2 accepting" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <img src="/common/images/img-main-training-online-03.jpg" alt="">
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>품질기초</span></p>
                                        </div>
                                        <p class="training-name">품질을 위한 데이터 분석 기초</p>
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
                                            <p class="training-name">품질을 위한 데이터 분석 기초</p>
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
                                                <p class="box-label bigger accepting"><span>접수중</span></p>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <a class="swiper-slide marquee_item2 waiting" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <!-- <img src="/common/images/img-main-training-online-01.jpg" alt=""> -->
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>품질기초</span></p>
                                        </div>
                                        <p class="training-name">자동차 품질경영시스템 핵심 정복</p>
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
                                            <p class="training-name">자동차 품질경영시스템 핵심 정복</p>
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
                                                <p class="box-label bigger waiting"><span>접수중</span></p>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <a class="swiper-slide marquee_item2 accepting" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <img src="/common/images/img-main-training-online-04.jpg" alt="">
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>SQ인증</span></p>
                                        </div>
                                        <p class="training-name">[SQ인증] 업종별 기술 이해</p>
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
                                            <p class="box-label bigger accepting"><span>접수중</span></p>
                                        </div>
                                    </div>
                                    <!-- hover 시 노출되는 영역 -->
                                    <div class="hover-area">
                                        <div class="for-position">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>SQ인증</span></p>
                                            </div>
                                            <p class="training-name">[SQ인증] 업종별 기술 이해</p>
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
                                                <p class="box-label bigger accepting"><span>접수중</span></p>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <a class="swiper-slide marquee_item2 accepting" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <img src="/common/images/img-main-training-online-01.jpg" alt="">
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>SQ인증</span></p>
                                        </div>
                                        <p class="training-name">[SQ인증] 품질 지도 실무</p>
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
                                            <p class="box-label bigger accepting"><span>접수중</span></p>
                                        </div>
                                    </div>
                                    <!-- hover 시 노출되는 영역 -->
                                    <div class="hover-area">
                                        <div class="for-position">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>SQ인증</span></p>
                                            </div>
                                            <p class="training-name">[SQ인증] 품질 지도 실무</p>
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
                                                <p class="box-label bigger accepting"><span>접수중</span></p>
                                            </div>
                                            <div class="btn-wrap">
                                                <div class="btn-solid small black-bg"><span>더 알아보기</span></div>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <a class="swiper-slide marquee_item2 accepting" href="javascript:">
                                    <!--
                                        접수중: accepting
                                        접수대기: waiting
                                        접수마감: end
                                    -->
                                    <div class="img-area">
                                        <img src="/common/images/img-main-training-online-02.jpg" alt="">
                                    </div>
                                    <div class="txt-area">
                                        <div class="sort-label-area">
                                            <p class="label"><span>품질아카데미</span></p>
                                            <p class="label"><span>SQ인증</span></p>
                                        </div>
                                        <p class="training-name">[SQ인증] 평가원 양성</p>
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
                                            <p class="box-label bigger accepting"><span>접수중</span></p>
                                        </div>
                                    </div>
                                    <!-- hover 시 노출되는 영역 -->
                                    <div class="hover-area">
                                        <div class="for-position">
                                            <div class="sort-label-area">
                                                <p class="label"><span>품질아카데미</span></p>
                                                <p class="label"><span>SQ인증</span></p>
                                            </div>
                                            <p class="training-name">[SQ인증] 평가원 양성</p>
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
                        <div class="swiper-button-next circle-arr btn_next2"></div>
                        <div class="swiper-button-prev circle-arr btn_prev2"></div>--%>
                </div>
            </div>
        </div>
    </section>


    <!-- 사업 tab 영역 -->
    <div class="biz-tab-area">
        <div class="tab-btn-area scroll-motion">
            <div class="txt-tab-swiper for-motion">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <a class="swiper-slide txt-tab-btn active" href="javascript:">
                            <p class="txt">컨설팅사업</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt">상생사업</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt">지원사업</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="biz-tab-con-area">
            <!-- 컨설팅사업 -->
            <section class="consulting-sec scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <a class="sec-tit f-large-title active" href="javascript:">컨설팅사업</a>
                        <p class="sec-txt f-sub-head">현장 맞춤형컨설팅을 통해 부품사의 품질기술력 향상과 경쟁력을 강화합니다</p>
                    </div>
                    <div class="field-list-w">
                        <div class="for-set">
                            <a class="field-list" href="/consulting/tech/content">
                                <div class="thumbnail-img only-mobile">
                                    <img src="/common/images/img-technical guidance-thumbnail-mobile-01.jpg" alt="">
                                </div>
                                <div class="title-area">
                                    <p class="tit">기술지도</p>
                                    <p class="txt">부품사의 품질기술력 향상과 안전한 제조현장 구축을 위해 기술지도 사업을 운영합니다</p>
                                </div>
                                <div class="btn-wrap">
                                    <div class="btn-text-icon black-circle"><span>자세히 보기</span></div>
                                </div>
                            </a>
                            <a class="field-list" href="/consulting/manage/content">
                                <div class="thumbnail-img only-mobile">
                                    <img src="/common/images/img-technical guidance-thumbnail-mobile-02.jpg" alt="">
                                </div>
                                <div class="title-area">
                                    <p class="tit">경영컨설팅</p>
                                    <p class="txt">경영자문과 현장 맞춤형 컨설팅을 통해 부품사의 경쟁력 향상을 적극 지원합니다</p>
                                </div>
                                <div class="btn-wrap">
                                    <div class="btn-text-icon black-circle"><span>자세히 보기</span></div>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </section>

            <!-- 상생사업 -->
            <section class="coexisting-sec scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <a class="sec-tit f-large-title" href="javascript:">상생사업</a>
                        <p class="sec-txt f-sub-head">부품사의 경쟁력향상과 자금 안정화 등을 지원합니다</p>
                    </div>
                    <div class="coexisting-swiper-w">
                        <div class="swiper-container">
                            <div class="swiper-wrapper">
                                <c:forEach var="list" items="${winData.list}" varStatus="status">
                                    <c:set var="cnt" value="${cnt + 1}"></c:set>
                                    <c:if test="${status.index eq 0}">
                                        <div class="swiper-slide hover-accordion">
                                    </c:if>
                                    <c:if test="${status.index >= 0 && status.index <= 3}">
                                        <c:choose>
                                            <c:when test="${not empty list.urlUrl}">
                                                <a class="biz-list acco-list <c:if test="${status.index eq 0}">active</c:if>" href="${list.urlUrl}">
                                            </c:when>
                                            <c:otherwise>
                                                <a class="biz-list acco-list <c:if test="${status.index eq 0}">active</c:if>" href="javascript:">
                                            </c:otherwise>
                                        </c:choose>
                                            <div class="thumbnail-img">
                                                <img src="${list.webPath}" alt="${list.fileDsc}">
                                            </div>
                                            <div class="title-area">
                                                <p class="tit"><span class="inner-motion">${list.bsnNm}</span></p>
                                            </div>
                                            <div class="hide-area">
                                                <p class="txt"><span class="inner-motion">${list.dsc}</span></p>
                                                <div class="btn-wrap">
                                                    <div class="inner-motion">
                                                        <div class="btn-text-icon black-circle"><span>자세히 보기</span></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </a>
                                    </c:if>
                                    <c:if test="${cnt ge 5 &&((cnt mod 4) eq 1)}">
                                        </div>
                                        <div class="swiper-slide hover-accordion">
                                    </c:if>
                                    <c:if test="${status.index > 3}">
                                        <c:choose>
                                            <c:when test="${not empty list.urlUrl}">
                                                <a class="biz-list acco-list  <c:if test="${cnt ge 5 &&((cnt mod 4) eq 1)}">active</c:if>" href="${list.urlUrl}">
                                            </c:when>
                                            <c:otherwise>
                                                <a class="biz-list acco-list  <c:if test="${cnt ge 5 &&((cnt mod 4) eq 1)}">active</c:if>" href="javascript:">
                                            </c:otherwise>
                                        </c:choose>
                                        <div class="thumbnail-img">
                                            <img src="${list.webPath}" alt="${list.fileDsc}">
                                        </div>
                                        <div class="title-area">
                                            <p class="tit"><span class="inner-motion">${list.bsnNm}</span></p>
                                        </div>
                                        <div class="hide-area">
                                            <p class="txt"><span class="inner-motion">${list.dsc}</span></p>
                                            <div class="btn-wrap">
                                                <div class="inner-motion">
                                                    <div class="btn-text-icon black-circle"><span>자세히 보기</span></div>
                                                </div>
                                            </div>
                                        </div>
                                        </a>
                                    </c:if>
                                    <c:if test="${status.last}"></div></c:if>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="swiper-button-prev circle-arr"></div>
                        <div class="swiper-button-next circle-arr"></div>
                        <div class="swiper-pagination"></div>
                    </div>
                </div>
            </section>

            <!-- 지원사업 -->
            <section class="support-sec scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <a class="sec-tit f-large-title" href="javascript:">지원사업</a>
                        <p class="sec-txt f-sub-head">부품사의 품질개선 및 혁신을 위해 자금 지원과 포상을 지원합니다</p>
                    </div>
                    <div class="support-kind-w">
                        <a class="support-list" href="/coexistence/equipment/content">
                            <div class="thumbnail-img">
                                <img src="/common/images/img-main-support-img-01.jpg" alt="">
                            </div>
                            <div class="title-area">
                                <p class="tit">자금지원</p>
                                <p class="txt">실효성 높은 지원을 통해 <br>품질기술력 증진을 도모합니다</p>
                            </div>
                            <div class="btn-wrap">
                                <div class="btn-text-icon black-arrow" href="/coexistence/equipment/content" title="링크 이동"><span>자세히 보기</span></div>
                            </div>
                        </a>
                        <a class="support-list" href="/coexistence/automotive/content">
                            <div class="thumbnail-img">
                                <img src="/common/images/img-main-support-img-02.jpg" alt="">
                            </div>
                            <div class="title-area">
                                <p class="tit">포상지원</p>
                                <p class="txt">기업과 인재를 발굴하여 자동차 <br>부품 산업의 위상을 제고합니다</p>
                            </div>
                            <div class="btn-wrap">
                                <div class="btn-text-icon black-arrow" href="/coexistence/automotive/content" title="링크 이동"><span>자세히 보기</span></div>
                            </div>
                        </a>
                        <a class="support-list" href="/coexistence/survey/index">
                            <div class="thumbnail-img">
                                <img src="/common/images/img-main-support-img-03.jpg" alt="">
                            </div>
                            <div class="title-area">
                                <p class="tit">설문조사</p>
                                <p class="txt">현장의 목소리를 수집하여 <br>동반성장의 토대를 구축합니다</p>
                            </div>
                            <div class="btn-wrap">
                                <div class="btn-text-icon black-arrow" href="/coexistence/survey/index" title="링크 이동"><span>자세히 보기</span></div>
                            </div>
                        </a>
                    </div>
                </div>
            </section>
        </div>
    </div>


    <!-- 공지 영역 -->
    <section class="notice-sec">
        <!-- gird로 작업 -->
        <div class="main-inner">
            <!-- 공지사항 -->
            <div class="noti-box list-noti-st scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title3">공지사항</p>
                        <div class="btn-wrap">
                            <a class="btn-text-icon black-arrow" href="/foundation/board/notice/list" title="링크 이동"><span>전체 보기</span></a>
                        </div>
                    </div>
                    <div class="article-list-w txt-list">
                        <c:forEach var="list" items="${noticeData.list}" varStatus="status">
                            <a class="list-item" href="/foundation/board/notice/view?detailsKey=${list.ntfySeq}" title="링크 이동">
                                <div class="txt-box">
                                    <p class="tit f-head">${list.titl}</p>
                                    <div class="sub-txt f-body2"><p class="date">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '.')}</p><p class="view">조회수 <span>${list.readCnt}</span></p></div><!-- 2024-01-05 태그 수정 -->
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- FAQ -->
            <div class="noti-box faq list-noti-st scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title3">FAQ</p>
                        <div class="btn-wrap">
                            <a class="btn-text-icon black-arrow" href="/foundation/board/faq/list" title="링크 이동"><span>전체 보기</span></a>
                        </div>
                    </div>
                    <div class="article-list-w txt-list">
                        <c:forEach var="list" items="${faqData.list}" varStatus="status">
                            <a class="list-item" href="/foundation/board/faq/list?faqSeq=${list.faqSeq}" title="링크 이동">
                                <div class="txt-box">
                                    <p class="tit f-head">${list.titl}</p>
                                    <span class="sub-txt f-body2">${list.ctgryName}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- 재단소식 -->
            <div class="noti-box scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title3">재단소식</p>
                        <div class="btn-wrap">
                            <a class="btn-text-icon black-arrow" href="/foundation/board/company-news/list" title="링크 이동"><span>전체 보기</span></a>
                        </div>
                    </div>
                    <div class="article-list-w with-thunmbnail">
                        <c:forEach var="list" items="${companyData.list}" varStatus="status">
                            <a class="list-item" href="/foundation/board/company-news/view?detailsKey=${list.fndnNewsSeq}" title="링크 이동">
                                <div class="img-box">
                                    <img src="${list.webPath}" alt="${list.fileDsc}">
                                </div>
                                <div class="txt-box">
                                    <p class="tit f-head">${list.titl}</p>
                                    <span class="sub-txt f-body2">${kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '.')}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- 뉴스레터 -->
            <div class="noti-box newsletter scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title3">뉴스레터</p>
                    </div>
                    <div>
                        <p class="txt f-body1">다양한 자동차부품산업진흥재단의 최신 소식을 가장 먼저 받아보세요.</p>
                        <p class="title f-head">이메일</p>
                        <div class="form-input">
                            <input type="text" name="email" maxlength="50" placeholder="이메일 주소">
                        </div>
                        <div class="form-checkbox newsletter-agree">
                            <input type="checkbox" id="consentChk" name="" onclick="if(this.checked){this.checked=false; openPopup('newsletterAgreePopup', this)}">
                            <label for="consentChk">소식 및 관련 정보 수신 동의</label>
                        </div>
                        <div class="btn-wrap">
                            <button class="btn-solid small black-bg newsletterBtn" type="button"><span>구독하기</span></button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 도움안내 -->
            <div class="noti-box help-guide scroll-motion">
                <div class="for-motion">
                    <div class="sec-tit-area">
                        <p class="sec-tit f-title3">고객지원</p>
                    </div>
                    <div class="help-btn-area">
                        <a class="btn-list manager" href="/foundation/intro/organization/index">
                            <span>담당자 연락처</span>
                        </a>
                        <a class="btn-list edu-schedule" id="eduScheduleBtn" href="javascript:">
                            <span>전체교육 일정</span>
                        </a>
                        <a class="btn-list certificate" href="javascript:">
                            <span>증명서 발급</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- 뉴스레터 구독 수신동의 팝업 -->
    <div class="layer-popup newsletterAgreePopup">
        <div class="for-center">
            <div class="pop-wrap">
                <div class="pop-con-area">
                    <div class="tit-area">
                        <p class="f-title1">자동차부품산업진흥재단의 소식 및 관련 <br class="only-pc"/>정보수신에 관한 동의</p>
                    </div>
                    <div class="con-area">
                        <div class="scroll-area">
                            <div class="p-info-sec">
                                <p class="f-sub-head">자동차부품산업진흥재단의 뉴스레터를 받아보기 위하여 아래의 개인정보 수집ㆍ이용에 대한 내용을 자세히 읽어 보신 후 동의 여부를 결정하여 주시기 바랍니다.</p>
                            </div>
                            <div class="p-cont-sec">
                                <div class="sec-con-area">
                                    <div class="p-list-box-w">
                                        <div class="p-list-box">
                                            <p class="f-body1">1. 개인정보의 수집 및 이용 목적</p>
                                            <div class="ul-txt-list">
                                                <p class="ul-txt f-body2">뉴스레터 이메일 수신 활용</p>
                                            </div>
                                        </div>
                                        <div class="p-list-box">
                                            <p class="f-body1">2. 수집하는 개인정보의 항목</p>
                                            <div class="ul-txt-list">
                                                <p class="ul-txt f-body2">
                                                    개인정보 : 이메일
                                                    <br/>IP Address, 쿠키, 방문 일시, 서비스 이용 기록
                                                </p>
                                                <p class="ul-txt f-caption2">※ 서비스 이용과정에서 아래와 같은 정보들이 자동으로 생성되어 수집될 수 있습니다.</p>
                                            </div>
                                        </div>
                                        <div class="p-list-box">
                                            <p class="f-body1">3. 개인정보의 보유 및 이용 기간</p>
                                            <div class="ul-txt-list">
                                                <p class="ul-txt f-body2">
                                                    개인정보 수집 및 이용에 관한 동의 후 2년간 개인정보를 보유하고 이후 해당 정보를 지체 없이 파기합니다.
                                                    <br/>단, 법률에 의해 보존 의무가 있는 경우에는 법령이 지정한 일정기간 동안 보존합니다.
                                                </p>
                                            </div>
                                        </div>
                                        <div class="p-list-box">
                                            <p class="f-body1">4. 귀하는 위와 같은 개인정보 수집ㆍ이용에 동의하지 않으실 수 있습니다.</p>
                                            <div class="ul-txt-list">
                                                <p class="ul-txt f-body2">동의를 하지 않을 경우 자동차부품산업진흥재단 뉴스레터 정보 수신이 제한될 수 있습니다.</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bot-fix-btn-area">
                        <div class="btn-wrap">
                            <button class="btn-solid small gray-bg btn-role-close" type="button"><span>거부</span></button>
                            <button class="btn-solid small black-bg btn-role-close btn-agree" type="button"><span>동의</span></button>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 메인 팝업 -->
    <div class="layer-popup main-popup">
        <div class="for-center">
            <div class="pop-wrap">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <c:forEach var="list" items="${popData.list}" varStatus="status">
                            <div class="swiper-slide <c:if test="${status.last}">last</c:if>">
                                    <c:choose>
                                        <c:when test="${not empty list.urlUrl}">
                                            <a class="click-area" <c:if test="${list.wnppYn eq 'Y'}">target="_blank"</c:if> href="${list.urlUrl}">
                                        </c:when>
                                        <c:otherwise>
                                            <a class="click-area" href="javascript:">
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${list.tagCd eq 'img'}">
                                            <img src="${list.webPath}" alt="${list.fileDsc}">
                                        </c:when>
                                        <c:otherwise>
                                            <!-- html 형식 -->
                                            <div class="pop-txt-con">
                                                ${list.cntn}
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div class="bot-info-area">
                    <div class="slide-control-area">
                        <div class="move-control">
                            <div class="swiper-button-prev"></div>
                            <div class="swiper-button-next"></div>
                            <button class="btn-pause" type="button" title="일시 정지"></button>
                        </div>
                        <div class="timer-gauge">
                            <div class="bar"></div>
                        </div>
                        <div class="swiper-pagination"></div>
                    </div>
                    <div class="user-opt-area">
                        <div class="form-checkbox">
                            <input type="checkbox" id="dontShowTodayChk" name="">
                            <label for="dontShowTodayChk">오늘 하루 열지 않기</label>
                        </div>
                        <button class="btn-close btn-role-close popClose" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- intro -->
    <div class="intro-motion" style="display: none;">
        <div class="viedeo-area">
            <div class="for-masking">
                <video src="/common/videos/intro.mp4" id="introVideo" muted preload="auto" autoplay playsinline></video>
            </div>
        </div>
    </div>
    <div class="only-vertical-view"></div>
</div>
<!-- content 영역 end -->