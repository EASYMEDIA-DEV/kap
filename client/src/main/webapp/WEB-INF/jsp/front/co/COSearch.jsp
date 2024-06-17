<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<c:set var="totalCnt" value="${ menuCnt + noticeCnt + newsCnt + letterCnt + episdCnt}"/>
<div class="cont-wrap" data-controller="controller/co/COTotalSrchCtrl">
    <div class="inner">
        <div class="page-tit-area t-align-center scroll-motion">
            <div class="for-motion">
                <p class="page-tit f-title1">‘<span class="color-sky">${ q }</span>’에 대해 <span class="color-sky">${ totalCnt}</span>건이 검색되었습니다.</p>
            </div>
        </div>
        <form method="get">
            <input type="hidden" name="pageIndex" value="1" />
            <input type="hidden" name="pageRowSize" value="3" />
            <input type="hidden" name="listRowSize" value="3" />
            <input type="hidden" name="srchYn" value="Y" />
            <input type="hidden" name="f" value="${f}" />
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <div class="srch-wrap scroll-motion">
                <div class="form-input srch-input for-motion">
                    <input type="text" placeholder="검색어를 입력해주세요." value="${ q }" title="검색어" name="q" maxlength="50">
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
        </form>

        <div class="tab-btn-area scroll-motion">
            <div class="txt-tab-swiper for-motion">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <a class="swiper-slide txt-tab-btn active" href="/search?q=${q}">
                            <p class="txt"><span class="menu-name">전체</span>&#40;<span class="item-count">${ totalCnt }</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn " href="/search/menu?q=${q}">
                            <p class="txt"><span class="menu-name">메뉴</span>&#40;<span class="item-count">${ menuCnt }</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="/search/education?q=${q}">
                            <p class="txt"><span class="menu-name">교육/세미나</span>&#40;<span class="item-count">${ episdCnt }</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="/search/notice?q=${q}">
                            <p class="txt"><span class="menu-name">공지사항</span>&#40;<span class="item-count">${ noticeCnt }</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="/search/foundation?q=${q}">
                            <p class="txt"><span class="menu-name">재단소식</span>&#40;<span class="item-count">${ newsCnt }</span>&#41;</p>
                        </a>
                        <a class="swiper-slide txt-tab-btn" href="/search/newsletter?q=${q}">
                            <p class="txt"><span class="menu-name">뉴스레터</span>&#40;<span class="item-count">${ letterCnt }</span>&#41;</p>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-con-box">
            <div class="tab-section scroll-motion active">
                <div class="for-motion">
                    <div class="section-tit-area">
                        <p class="tit f-title2"><span class="menu-name">메뉴</span> (<span class="item-count">${ menuCnt }</span>)</p>
                        <c:if test="${ menuCnt > 0}">
                            <div class="btn-wrap">
                                <a class="btn-text-icon black-arrow" href="/search/menu?q=${q}"  title="전체보기"><span>전체 보기</span></a>
                            </div>
                        </c:if>
                    </div>
                    <div class="tab-con-area">
                        <c:choose>
                            <c:when test="${ menuCnt > 0 }">
                                <div class="menu-depth-info">
                                    <c:forEach var="list" items="${qMenuList}" varStatus="status">
                                        <a class="menu-link" href="${ list.userUrl}">
                                            <c:set var="menuNmList" value="${ fn:split(list.menuNm, '__')}" />
                                            <c:forEach var="menuNm" items="${menuNmList}" varStatus="status">
                                                <p class="menu f-head">${ menuNm }</p>
                                            </c:forEach>
                                        </a>
                                    </c:forEach>
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

            <div class="tab-section scroll-motion " id="episdList" data-cnt="${ episdCnt }">
                <div class="for-motion">
                    <div class="section-tit-area">
                        <p class="tit f-title2"><span class="menu-name">교육/세미나</span> (<span class="item-count">${episdCnt}</span>)</p>
                        <c:if test="${ episdCnt > 0}">
                            <div class="btn-wrap">
                                <a class="btn-text-icon black-arrow" href="/search/education?q=${q}" title="전체보기"><span>전체 보기</span></a>
                            </div>
                        </c:if>
                    </div>
                    <div class="tab-con-area" id="episdContainer">
                        <c:if test="${ episdCnt eq 0 }">
                            <div class="no-data-area">
                                <div class="txt-box">
                                    <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="tab-section scroll-motion " id="noticeList" data-cnt="${ noticeCnt }">
                <div class="for-motion">
                    <div class="section-tit-area">
                        <p class="tit f-title2"><span class="menu-name">공지사항</span> (<span class="item-count">${noticeCnt}</span>)</p>
                        <c:if test="${ noticeCnt > 0}">
                            <div class="btn-wrap">
                                <a class="btn-text-icon black-arrow" href="/search/notice?q=${q}"  title="전체보기"><span>전체 보기</span></a>
                            </div>
                        </c:if>
                    </div>
                    <div class="tab-con-area" id="noticeContainer">
                        <c:if test="${ noticeCnt eq 0 }">
                            <div class="no-data-area">
                                <div class="txt-box">
                                    <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="tab-section scroll-motion " id="foundationList" data-cnt="${ newsCnt }">
                <div class="for-motion">
                    <div class="section-tit-area">
                        <p class="tit f-title2"><span class="menu-name">재단소식</span> (<span class="item-count">${newsCnt}</span>)</p>
                        <c:if test="${ newsCnt > 0}">
                            <div class="btn-wrap">
                                <a class="btn-text-icon black-arrow" href="/search/foundation?q=${q}"  title="전체보기"><span>전체 보기</span></a>
                            </div>
                        </c:if>
                    </div>
                    <div class="tab-con-area">
                        <c:choose>
                            <c:when test="${ newsCnt eq 0 }">
                                <div class="no-data-area">
                                    <div class="txt-box">
                                        <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="article-list-w card-list" id="foundationContainer"><!-- card-list: 썸네일 있는 경우 -->
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div class="tab-section scroll-motion " id="letterList" data-cnt="${ letterCnt }">
                <div class="for-motion">
                    <div class="section-tit-area">
                        <p class="tit f-title2"><span class="menu-name">뉴스레터</span> (<span class="item-count">${letterCnt}</span>)</p>
                        <c:if test="${ letterCnt > 0}">
                            <div class="btn-wrap">
                                <a class="btn-text-icon black-arrow" href="/search/newsletter?q=${q}"  title="전체보기"><span>전체 보기</span></a>
                            </div>
                        </c:if>
                    </div>
                    <div class="tab-con-area">
                        <div class="board-list">
                            <c:choose>
                                <c:when test="${ letterCnt eq 0 }">
                                    <div class="no-data-area">
                                        <div class="txt-box">
                                            <p class="txt f-body1">조회된 데이터가 없습니다.</p>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="article-list-w card-list" id="letterContainer"><!-- card-list: 썸네일 있는 경우 -->
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- content 영역 end -->