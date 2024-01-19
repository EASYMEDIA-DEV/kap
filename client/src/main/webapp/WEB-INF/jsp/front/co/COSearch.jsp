<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div class="cont-wrap">
    <div class="inner">
        <div class="page-tit-area t-align-center scroll-motion">
            <div class="for-motion">
                <p class="page-tit f-title1">‘<span class="color-sky">자동차</span>’에 대해 <span class="color-sky">9</span>건이 검색되었습니다.</p>
            </div>
        </div>

        <div class="srch-wrap scroll-motion">
            <div class="form-input srch-input for-motion">
                <input type="text" placeholder="검색어를 입력해 주세요." value="">
                <div class="input-btn-wrap">
                    <button class="delete-btn" title="지우기" type="button"></button>
                    <button class="srch-btn" title="검색"></button>
                </div>
            </div>
            <ul class="top-srchs for-motion">
                <li class="srch-item">
                    <a href="javascript:">품질입문</a>
                </li>
                <li class="srch-item">
                    <a href="javascript:">미래차다각화</a>
                </li>
                <li class="srch-item">
                    <a href="javascript:">스마트공장</a>
                </li>
                <li class="srch-item">
                    <a href="javascript:">기술지도</a>
                </li>
                <li class="srch-item">
                    <a href="javascript:">경영컨설팅</a>
                </li>
                <li class="srch-item">
                    <a href="javascript:">경영컨설팅</a>
                </li>
                <li class="srch-item">
                    <a href="javascript:">경영컨설팅</a>
                </li>
            </ul>
        </div>

        <div class="tab-btn-area scroll-motion">
            <div class="txt-tab-swiper for-motion">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt"><span class="menu-name">전체</span>&#40;<span class="item-count">25</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn active" href="javascript:">
                            <p class="txt"><span class="menu-name">메뉴</span>&#40;<span class="item-count">5</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt"><span class="menu-name">교육/세미나</span>&#40;<span class="item-count">5</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt"><span class="menu-name">공지사항</span>&#40;<span class="item-count">5</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt"><span class="menu-name">재단소식</span>&#40;<span class="item-count">5</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="javascript:">
                            <p class="txt"><span class="menu-name">뉴스레터</span>&#40;<span class="item-count">0</span>&#41;</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-con-box">
            <div class="tab-section scroll-motion">
                <div class="for-motion">
                    <div class="tab-con-area">
                        <!-- 데이터 없을 경우 노출되는 영역 -->
                        <div class="no-data-area">
                            <div class="txt-box">
                                <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->