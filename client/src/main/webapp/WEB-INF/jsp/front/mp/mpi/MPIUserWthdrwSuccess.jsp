<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="mypage"><!-- 마이페이지 mypage 클래스 추가 -->
    <header id="header"></header>

    <!-- content 영역 start -->
    <div class="cont-wrap">
        <!--
          신청 페이지: apply-page 클래스 추가
          그 외 페이지: basic-page 클래스 추가
        -->
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">회원탈퇴</span></p>
            </div>
        </div>

        <div class="divide-con-area">
            <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
            </div>

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="status-con-box gray-bg no-bg-img">
                                <div class="cont-for-padding">
                                    <p class="f-title1">회원탈퇴가 완료되었습니다.</p>
                                    <p class="detail-txt">지금까지 자동차부품산업진흥재단 서비스를 이용해주셔서 감사합니다.</p>
                                    <div class="def-list-w">
                                        <div class="def-list">
                                            <p class="tit f-head">탈퇴일시</p>
                                            <p class="txt f-sub-head">${rtnData.regDtm}</p>
                                        </div>
                                    </div>
                                    <div class="btn-wrap">
                                        <a class="btn-solid small black-bg" href="/"><span>메인으로</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>