<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/bd/bdc/BDCFaqListCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="get">
        <!-- 카테고리별 화면으로 이동시 시퀀스 -->
        <input type="hidden" id="detailsKey" name="detailsKey" value="" />
        <input type="hidden" id="seq" name="seq" value="${faqSeq}" />
        <div class="cont-wrap">
            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">FAQ</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-faq.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-faq-mobile.jpg" alt="">
                    </div>
                </div>
            </div>

            <div class="divide-con-area">
                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
                <!--LNB 종료-->

                <div class="right-con-area">
                    <div class="tab-btn-area scroll-motion">
                        <div class="txt-tab-swiper for-motion">
                            <div class="swiper-container">
                                <div class="swiper-wrapper btnArea">
                                    <a class="swiper-slide txt-tab-btn" id="ctgryCd" href="javascript:">
                                        <p class="txt"><span class="menu-name">전체</span></p>
                                    </a>
                                    <c:forEach var="cdList" items="${cdDtlList.BOARD_TYPE_CD}" varStatus="status">
                                        <c:if test="${fn:contains(cdList.cd, 'FAQ0')}">
                                            <a class="swiper-slide txt-tab-btn" id="ctgryCd" data-ctgry-cd="${cdList.cd}" href="javascript:">
                                                <p class="txt"><span class="menu-name">${cdList.cdNm}</span></p>
                                            </a>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="info-head">
                                    <p class="article-total-count f-body2">총 <span id="listContainerTotCnt">${rtnData.totalCount}</span>건</p>
                                    <div class="form-input srch-input">
                                        <input type="text" id="srchVal" name="srchVal" value="${rtnData.srchVal}" placeholder="검색어를 입력해 주세요.">
                                        <div class="input-btn-wrap">
                                            <button class="delete-btn" title="지우기" type="button"></button>
                                            <button class="srch-btn" id="searchBtn" title="검색"></button>
                                        </div>
                                    </div>
                                </div>

                                <div class="board-list">
                                    <div class="article-list-w accordion-st" id="listContainer">
                                    </div>
                                </div>

                                <div class="btn-wrap add-load align-center moreBtn" id="pagingContainer">
                                    <a class="btn-solid small black-line" href="javascript:"><span>더보기</span><span class="item-count cntText"></span></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>