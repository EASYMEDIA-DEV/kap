<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="totalCnt" value="${ menuCnt + noticeCnt + newsCnt + letterCnt }"/>
<div class="cont-wrap" data-controller="controller/co/COSearchDtl">
    <div class="inner">
        <div class="page-tit-area t-align-center scroll-motion">
            <div class="for-motion">
                <p class="page-tit f-title1">‘<span class="color-sky">${ q }</span>’에 대해 <span class="color-sky">${ totalCnt}</span>건이 검색되었습니다.</p>
            </div>
        </div>
        <form method="get">
            <input type="hidden" id="pageIndex" name="pageIndex" value="1" />
            <!-- 페이징 버튼 사이즈 -->
            <input type="hidden" id="pageRowSize" name="pageRowSize" value="10" />
            <input type="hidden" id="firstIndex" name="firstIndex" value="0" />
            <input type="hidden" id="listRowSize" name="listRowSize" value="${ rtnData.listRowSize }" />
            <input type="hidden" name="menuType" value="${menuType}" />
            <input type="hidden" name="letterCnt" id="letterCnt" value="${letterCnt}" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="srch-wrap scroll-motion">
                <div class="form-input srch-input for-motion">
                    <input type="text" placeholder="검색어를 입력해 주세요." value="${ q }" title="검색어" name="q" maxlength="50">
                    <div class="input-btn-wrap">
                        <button class="delete-btn" title="지우기" type="button"></button>
                        <button class="srch-btn" title="검색" type="submit"></button>
                    </div>
                </div>
                <c:if test="${ not empty cdDtlList }">
                    <ul class="top-srchs for-motion">
                        <c:forEach var="cdList" items="${cdDtlList.FRONT_TOTAL_KEYWORD}" varStatus="status">
                            <c:if test="${ status.index <= 4}">
                                <li class="srch-item">
                                    <a href="javascript:" class="frontTotalKeyword">${cdList.cdNm}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>

        <div class="tab-btn-area scroll-motion">
            <div class="txt-tab-swiper for-motion">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <c:choose>
                            <c:when test="${menuType == ''}">
                                <a class="swiper-slide txt-tab-btn active" href="/search?q=${q}">
                                    <p class="txt"><span class="menu-name">전체</span>&#40;<span class="item-count">${totalCnt}</span>&#41;</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide txt-tab-btn" href="/search?q=${q}">
                                    <p class="txt"><span class="menu-name">전체</span>&#40;<span class="item-count">${totalCnt}</span>&#41;</p>
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${menuType == 'menu'}">
                                <a class="swiper-slide txt-tab-btn active" href="/search/menu?q=${q}">
                                    <p class="txt"><span class="menu-name">메뉴</span>&#40;<span class="item-count">${menuCnt}</span>&#41;</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide txt-tab-btn " href="/search/menu?q=${q}">
                                    <p class="txt"><span class="menu-name">메뉴</span>&#40;<span class="item-count">${menuCnt}</span>&#41;</p>
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${menuType == 'education'}">
                                <a class="swiper-slide txt-tab-btn active" href="/search/education?q=${q}">
                                    <p class="txt"><span class="menu-name">교육/세미나</span>&#40;<span class="item-count">${episdCnt}</span>&#41;</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide txt-tab-btn " href="/search/education?q=${q}">
                                    <p class="txt"><span class="menu-name">교육/세미나</span>&#40;<span class="item-count">${episdCnt}</span>&#41;</p>
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${menuType == 'notice'}">
                                <a class="swiper-slide txt-tab-btn active" href="/search/notice?q=${q}">
                                    <p class="txt"><span class="menu-name">공지사항</span>&#40;<span class="item-count">${noticeCnt}</span>&#41;</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide txt-tab-btn" href="/search/notice?q=${q}">
                                    <p class="txt"><span class="menu-name">공지사항</span>&#40;<span class="item-count">${noticeCnt}</span>&#41;</p>
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${menuType == 'foundation'}">
                                <a class="swiper-slide txt-tab-btn active" href="/search/foundation?q=${q}">
                                    <p class="txt"><span class="menu-name">재단소식</span>&#40;<span class="item-count">${newsCnt}</span>&#41;</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide txt-tab-btn " href="/search/foundation?q=${q}">
                                    <p class="txt"><span class="menu-name">재단소식</span>&#40;<span class="item-count">${newsCnt}</span>&#41;</p>
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${menuType == 'newsletter'}">
                                <a class="swiper-slide txt-tab-btn active" href="/search/newsletter?q=${q}">
                                    <p class="txt"><span class="menu-name newsletter">뉴스레터</span>&#40;<span class="item-count">${letterCnt}</span>&#41;</p>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a class="swiper-slide txt-tab-btn " href="/search/newsletter?q=${q}">
                                    <p class="txt"><span class="menu-name newsletter">뉴스레터</span>&#40;<span class="item-count">${letterCnt}</span>&#41;</p>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-con-box">

            <c:choose>
                <c:when test="${menuType == 'menu'}">
            <div class="tab-section scroll-motion active">
                <div class="for-motion">
                    <div class="section-tit-area">
                        <p class="tit f-title2"><span class="menu-name">메뉴</span> (<span class="item-count">${ menuCnt }</span>)</p>
                    </div>
                    <div class="tab-con-area">
                        <c:choose>
                            <c:when test="${menuCnt > 0}">
                                <div class="menu-depth-info" id="menuDepthInfo">
                                    <c:forEach var="list" items="${qMenuList}" varStatus="status" begin="0" end="0">
                                        <a class="menu-link" id="menuLink" href="${list.userUrl}">
                                            <c:set var="menuNmList" value="${fn:split(list.menuNm, '__')}" />
                                            <c:forEach var="menuNm" items="${menuNmList}" varStatus="status">
                                                <p class="menu f-head">${menuNm}</p>
                                            </c:forEach>
                                        </a>
                                    </c:forEach>
                                </div>
                                <div class="btn-wrap align-center">
                                    <a class="btn-solid small black-line" id="menuAddBtn" href="javascript:void(0);"><span>더보기</span><span class="item-count"></span></a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="no-data-area">
                                    <div class="txt-box">
                                        <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
                </c:when>

                <c:when test="${menuType == 'newsletter'}">
                    <div class="tab-section scroll-motion " id="letterList" data-cnt="${ letterCnt }">
                        <div class="for-motion">
                            <div class="section-tit-area">
                                <p class="tit f-title2"><span class="menu-name">뉴스레터</span> (<span class="item-count">${letterCnt}</span>)</p>
                            </div>
                            <div class="tab-con-area" id="letterTabContainer">
                            </div>
                            <div class="btn-wrap add-load align-center">
                                <a class="btn-solid small black-line pageSet" href="javascript:"><span>더보기</span><span class="item-count"></span></a>
                            </div>
                        </div>
                    </div>
                </c:when>
            </c:choose>
        </div>
        </form>
        </div>
    </div>
</div>
<!-- content 영역 end -->