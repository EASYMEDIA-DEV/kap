<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/co/COLgnCtrl">

    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <span class="for-move">비밀번호 찾기</span>
                </p>
            </div>
        </div>

        <div class="inner-con-box">
            <p class="con-sub-tit f-title2">회원님의 가입 정보로 비밀번호를 확인해보세요.</p>
                <form id="frmPwdFind" name="frmPwdFind" method="post" action="">
                    <!-- CSRF KEY -->
                    <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <input type='hidden' value='3' name='types' readonly>
                    <div class="data-enter-form">
                <div class="row">
                    <div class="th">
                        <p class="title f-head">아이디</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="form-input">
                                        <input type="text" placeholder="아이디 입력"  id="pwdId" name="id" title="아이디" maxlength="12" oninput="this.value=this.value.replace(/[^a-zA-Z0-9]/gi,'');">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="th">
                        <p class="title f-head">이름</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="form-input">
                                        <input type="text" placeholder="이름 입력" id="name" name="name" title="이름" class="nameChk">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="th">
                        <p class="title f-head">휴대폰번호</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="form-input w-longer">
                                        <input type="text" placeholder="휴대폰번호 입력" class="hpNo" id="hpNo-pwd" name="mobileno" title="휴대폰 번호" maxlength="13">
                                    </div>
                                </div>
                                <div class="noti-txt-w">
                                    <p class="bullet-noti-txt f-caption2">* 휴대폰번호를 바꾸신 회원님께서는 과거 휴대폰번호를 입력하여 주시기를 바랍니다</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="page-bot-btn-sec">
                <div class="btn-wrap align-center">
                    <button class="btn-solid small black-bg" type="submit"><span>비밀번호 찾기</span></button>
                </div>
            </div>
        </form>
        </div>
    </div>

    <form name="form" id="form"  action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb">
        <input type="hidden" id="m" name="m" value="service" />
        <input type="hidden" id="token_version_id" name="token_version_id" value="" />
        <input type="hidden" id="enc_data" name="enc_data" />
        <input type="hidden" id="integrity_value" name="integrity_value" />
    </form>
</div>