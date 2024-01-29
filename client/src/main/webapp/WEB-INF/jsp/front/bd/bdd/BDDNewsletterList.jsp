<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" data-controller="controller/bd/bdd/BDDNewsletterListCtrl">
    <form class="form-horizontal" id="frmData" name="frmData" method="get">
        <!-- 상세로 이동시 시퀀스 -->
        <input type="hidden" id="detailsKey" name="detailsKey" value="" />
        <div class="cont-wrap">
            <div class="sub-top-vis-area basic-page">
                <div class="page-tit-area">
                    <p class="page-tit f-xlarge-title"><span class="for-move">뉴스레터 구독</span></p>
                </div>
                <div class="img-area">
                    <div class="img">
                        <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-newsletter.jpg" alt="">
                        <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-newsletter-mobile.jpg" alt="">
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
                                <div class="letter-subscribe-w">
                                    <div class="txt-area">
                                        <p class="tit f-title1">뉴스레터 신청</p>
                                        <p class="txt f-title3">자동차부품산업진흥재단의 최신 소식을 가장 먼저 받아보세요.</p>
                                    </div>

                                    <div class="form-input">
                                        <input type="text" id="email" name="email" placeholder="이메일 주소" oninput="this.value = this.value.replace(/[^\x00-\x7F]/g, '');">
                                    </div>
                                    <div class="form-checkbox newsletter-agree">
                                        <input type="checkbox" id="consentChk" name="consentChk">
                                        <label for="consentChk" id="consentChkLabel">소식 및 관련 정보 수신 동의</label>
                                    </div>
                                    <div class="btn-wrap">
                                        <button class="btn-solid small black-bg subscribeBtn" id="subscribe" type="button"><span>구독하기</span></button>
                                    </div>
                                </div>

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
                                                    <a class="list-item open listView" href="javascript:" title="링크 이동" data-details-key="${list.nwsltSeq}">
                                                        <div class="img-box">
                                                            <c:choose>
                                                                <c:when test="${ not empty list.webPath }">
                                                                    <img src="${ list.webPath }" alt="">
                                                                </c:when>
                                                                <c:otherwise>
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
    <!-- 뉴스레터 구독 수신동의 팝업 -->
    <div class="layer-popup newsletterAgreePopup first-show" style="display: none;">
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
                            <button class="btn-solid small gray-bg btn-role-close" id="refusalBtn" type="button"><span>거부</span></button>
                            <button class="btn-solid small black-bg btn-role-close btn-agree" id="agreeBtn" type="button"><span>동의</span></button>
                        </div>
                    </div>
                    <div class="user-opt-area">
                        <button class="btn-close btn-role-close" title="팝업 닫기" type="button"><span>닫기</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>