<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/bd/bdb/BDBCompanyNewsListCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="get">
        <!-- 상세로 이동시 시퀀스 -->
        <input type="hidden" id="detailsKey" name="detailsKey" value="" />
        <div class="cont-wrap">
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">재단소식</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-news.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-news-mobile.jpg" alt="">
                    </div>
                </div>
            </div>
            <div class="divide-con-area">
                <!--LNB 시작-->
                <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
                <!--LNB 종료-->

                <div class="right-con-area">
                    <div class="cont-sec-w">
                        <div class="cont-sec no-border scroll-motion">
                            <div class="for-motion">
                                <div class="info-head">
                                    <p class="article-total-count f-body2">총 <span>${rtnData.totalCount}</span>건</p>
                                    <div class="form-input srch-input">
                                        <input type="text" id="srchVal" name="srchVal" value="${rtnData.srchVal}" placeholder="검색어를 입력해 주세요.">
                                        <div class="input-btn-wrap">
                                            <button class="delete-btn" title="지우기" type="button"></button>
                                            <button class="srch-btn" id="searchBtn" title="검색"></button>
                                        </div>
                                    </div>
                                </div>

                                <div class="board-list">
                                    <c:choose>
                                        <c:when test="${ not empty rtnData.list }">
                                            <div class="article-list-w card-list" id="infoCard"><!-- card-list: 썸네일 있는 경우 -->
                                                <c:forEach var="list" items="${rtnData.list}" varStatus="status">
                                                    <a class="list-item open listView" href="javascript:" title="링크 이동" data-details-key="${list.fndnNewsSeq}">
                                                        <div class="img-box">
                                                            <c:choose>
                                                                <c:when test="${ not empty list.webPath }">
                                                                    <img src="${ list.webPath }" alt="${ list.fileDsc }">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <img src="" alt="">
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                        <div class="txt-box">
                                                            <p class="tit f-head"><span class="new-icon" aria-label="새로운 항목"></span>${list.titl}</p>
                                                            <span class="sub-txt f-body2">${ kl:convertDate(list.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd', '') }</span>
                                                        </div>
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

                                <div class="btn-wrap add-load align-center moreBtn">
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