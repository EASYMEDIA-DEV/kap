<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="">

    <div class="sub-top-vis-area basic-page">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">${ pageMenuDto.menuNm }</span></p>
        </div>
        <div class="img-area">
            <div class="img">
                <img class="only-pc" src="/common/images/img-sub-top-visual-foundation-Inquiry.jpg" alt="">
                <img class="only-mobile" src="/common/images/img-sub-top-visual-foundation-Inquiry-mobile.jpg" alt="">
            </div>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec scroll-motion">
                    <div class="for-motion">
                        <div class="sec-tit-area">
                            <p class="f-title3">자동차부품산업진흥재단에 궁금한점을 문의해보세요.</p>
                        </div>
                        <div class="sec-con-area">
                            <div class="graphic-sec">
                                <div class="guide-step-w has-border"><!-- has-border: 테두리 있을 때 -->
                                    <div class="step-list">
                                        <p class="step-num">STEP 01</p>
                                        <div class="step-info">
                                            <p class="step-con">문의등록</p>
                                            <p class="box-label bigger"><span>접수대기</span></p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 02</p>
                                        <div class="step-info">
                                            <p class="step-con">담당자 확인</p>
                                            <p class="box-label bigger waiting"><span>접수완료</span></p>
                                        </div>
                                    </div>
                                    <div class="step-list">
                                        <p class="step-num">STEP 03</p>
                                        <div class="step-info">
                                            <p class="step-con">담당자 답변 등록</p>
                                            <p class="box-label bigger complete"><span>답변완료</span></p>
                                        </div>
                                    </div>
                                </div>

                                <div class="divide-flex-box v-align-center">
                                    <div class="left">
                                        <p class="icon-txt tel">
                                            <span>대표전화</span>
                                            <a href="tel:02-3271-2965">02-3271-2965</a>
                                        </p>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small black-bg" href="/foundation/cs/qa/index"><span> 1:1 문의하기</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="sec-con-area">
                            <div class="info-head">
                                <p class="article-total-count f-body2">총 <span>0</span>건</p>
                                <div class="form-input srch-input">
                                    <input type="text" placeholder="검색어를 입력해 주세요.">
                                    <div class="input-btn-wrap">
                                        <button class="delete-btn" title="지우기" type="button"></button>
                                        <button class="srch-btn" title="검색"></button>
                                    </div>
                                </div>
                            </div>
                            <div class="table-sec">
                                <div class="table-box need-scroll"><!-- mobile에서 table 가로스크롤 필요할 경우 need-scroll 클래스 추가 -->
                                    <table class="basic-table">
                                        <caption>경영공시</caption>
                                        <colgroup>
                                            <col style="width: 80rem;">
                                            <col style="width: 200rem;">
                                            <col style="width: 380rem;">
                                            <col style="width: 144rem;">
                                            <col style="width: 144rem;">
                                            <col style="width: 144rem;">
                                        </colgroup>
                                        <thead>
                                        <tr>
                                            <th>번호</th>
                                            <th>문의유형</th>
                                            <th>제목</th>
                                            <th>등록일</th>
                                            <th>진행상태</th>
                                            <th>답변등록일</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td class="t-align-center">9999</td>
                                            <td class="t-align-center">일반 > 회원가입</td>
                                            <td><p class="txt-ellipsis"><a href="javascript:" title="링크 이동">수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요?</a></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                            <td class="t-align-center">2023.09.25 13:00</td>
                                            <td class="t-align-center"><p class="box-label bigger waiting"><span>접수대기</span></p></td>
                                            <td class="t-align-center">-</td>
                                        </tr>
                                        <tr>
                                            <td class="t-align-center">9999</td>
                                            <td class="t-align-center">일반 > 회원가입</td>
                                            <td><p class="txt-ellipsis"><a href="javascript:" title="링크 이동">수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요?</a></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                            <td class="t-align-center">2023.09.25 13:00</td>
                                            <td class="t-align-center"><p class="box-label bigger complete"><span>접수완료</span></p></td>
                                            <td class="t-align-center">-</td>
                                        </tr>
                                        <tr>
                                            <td class="t-align-center">9999</td>
                                            <td class="t-align-center">일반 > 회원가입</td>
                                            <td><p class="txt-ellipsis"><a href="javascript:" title="링크 이동">수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요?</a></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                            <td class="t-align-center">2023.09.25 13:00</td>
                                            <td class="t-align-center"><p class="box-label bigger"><span>답변완료</span></p></td>
                                            <td class="t-align-center">2023.09.25 13:00</td>
                                        </tr>
                                        <tr>
                                            <td class="t-align-center">9999</td>
                                            <td class="t-align-center">일반 > 회원가입</td>
                                            <td><p class="txt-ellipsis"><a href="javascript:" title="링크 이동">수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요? 수료증 발급은 홈페이지에서 언제부터 확인 가능한가요?</a></td><!-- @ 2줄 이상 말줄임 필요 시, <p class="txt-ellipsis"></p> 사용 -->
                                            <td class="t-align-center">2023.09.25 13:00</td>
                                            <td class="t-align-center"><p class="box-label bigger"><span>답변완료</span></p></td>
                                            <td class="t-align-center">2023.09.25 13:00</td>
                                        </tr>
                                        </tbody>
                                    </table>
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