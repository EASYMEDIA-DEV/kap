<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div id="wrap" class="member" data-controller="controller/mp/MPUserController"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
    <input type="hidden" value="${rtnData}" name="joinType" class="joinType" id="joinType"/>

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
                    <div class="step-list ongoing"><!-- 완료: completed, 진행 중: ongoing 클래스 추가 -->
                        <p class="step-num">1</p>
                        <p class="step-con">본인인증</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">2</p>
                        <p class="step-con">약관동의</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">3</p>
                        <p class="step-con">회원정보 입력</p>
                    </div>
                    <div class="step-list">
                        <p class="step-num">4</p>
                        <p class="step-con">가입완료</p>
                    </div>
                </div>
            </div>

            <div class="inner-con-box">
                <div class="gray-bg-sec middle-pad dark-gray-bg t-align-center">
                    <div class="phone-certi">
                        <p class="title f-head">휴대폰 본인인증</p>
                        <p class="txt">회원님 명의의 휴대폰을 통해 <br>인증 받으실 수 있습니다.</p>
                    </div>
                </div>

                <div class="page-bot-btn-sec">
                    <div class="btn-wrap align-center">
                        <button class="btn-solid small black-bg" id="myRegister"><span>인증하기</span></button>
                    </div>
                </div>
            </div>
            <div class="noti-txt-w">
                <p class="bullet-noti-txt f-caption2">* 휴대폰 본인인증 완료 시 회원정보입력이 가능합니다.</p>
                <p class="bullet-noti-txt f-caption2">* 본인 인증 시 입력하시는 정보는 공인된 인증기관에서 직접 수집하며, 인증 이외의 용도로 이용되지 않습니다.</p>
            </div>
        </div>
    </div>
    <!-- content 영역 end -->
</div>
    <form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
        <input type="hidden" id="m" name="m" value="service" />
        <input type="hidden" id="token_version_id" name="token_version_id" value="" />
        <input type="hidden" id="enc_data" name="enc_data" />
        <input type="hidden" id="integrity_value" name="integrity_value" />
    </form>