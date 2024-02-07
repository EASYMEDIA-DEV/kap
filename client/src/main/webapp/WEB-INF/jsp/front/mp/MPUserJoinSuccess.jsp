<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="member"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->

    <script type="text/javascript">

    </script>
    <!-- content 영역 start -->
    <div class="cont-wrap">
        <div class="inner">
            <div class="sub-top-vis-area">
                <div class="page-tit-area t-align-center">
                    <p class="page-tit f-large-title">
                        <span class="for-move">회원가입</span>
                    </p>
                </div>
            </div>

            <div class="apply-step-w">
                <div class="for-move">
                    <div class="step-list completed"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">본인인증</p>
                    </div>
                    <div class="step-list completed">
                        <p class="step-num">2</p>
                        <p class="step-con">약관동의</p>
                    </div>
                    <div class="step-list completed">
                        <p class="step-num">3</p>
                        <p class="step-con">회원정보 입력</p>
                    </div>
                    <div class="step-list ongoing">
                        <p class="step-num">4</p>
                        <p class="step-con">가입완료</p>
                    </div>
                </div>
            </div>

            <div class="status-con-box join-complete"><!-- join-complete: 회원가입 완료 페이지 -->
                <div class="cont-for-padding">
                    <p class="f-title1"><span class="color-sky">회원가입</span>이<br> 완료되었습니다.</p>
                    <p class="detail-txt">자동차부품산업진흥재단 회원이 되신 것을 진심으로 축하드립니다. <br>로그인 후 다양한 서비스를 이용하실 수 있습니다.</p>
                    <div class="def-list-w">
                        <div class="def-list">
                            <p class="tit f-head">회원가입 완료일</p>
                            <p class="txt f-sub-head" id="nowDate" >${kl:convertDate(rtnData.regDtm, 'yyyy-MM-dd HH:mm:ss', 'yyyy.MM.dd HH:mm', '')}</p>
                        </div>
                    </div>
                </div>

                <div class="page-bot-btn-sec">
                    <div class="btn-wrap">
                        <div class="btn-set">
                            <a class="btn-solid small gray-bg" href="/"><span>메인으로</span></a>
                        </div>
                        <div class="btn-set">
                            <a class="btn-solid small black-bg" href="/login"><span>로그인</span></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>