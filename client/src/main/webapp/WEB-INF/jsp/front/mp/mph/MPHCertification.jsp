<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="mypage" data-controller="controller/mp/mph/MPHCertificationController"><!-- 마이페이지 mypage 클래스 추가 -->
    <!-- content 영역 start MPHCertification -->
    <div class="cont-wrap"  >
        <form name="formPasswordConfirm" id="formPasswordConfirm" method="post">
            <input type="hidden" class="notRequired" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <!--
              신청 페이지: apply-page 클래스 추가
              그 외 페이지: basic-page 클래스 추가
            -->
        <div class="sub-top-vis-area">
            <div class="page-tit-area">
                <p class="page-tit f-xlarge-title"><span class="for-move">회원정보 수정</span></p>
            </div>
        </div>

        <div class="divide-con-area">
            <div class="lnb-area">
                <div class="for-motion">
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>교육/세미나 사업 신청내역</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>컨설팅 사업 신청내역</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>상생 사업 신청내역</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>SQ평가원 자격증</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>근태 체크</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>나의 1:1 문의</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu active" href="javascript:"><span>정보 수정</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                    <div class="lnb-list">
                        <a class="btn-two-depth single-menu" href="javascript:"><span>회원탈퇴</span></a><!-- 하위메뉴 없을 시 single-menu 클래스 추가 -->
                    </div>
                </div>
            </div>

            <div class="right-con-area">
                <div class="cont-sec-w">
                    <div class="cont-sec no-border scroll-motion">
                        <div class="for-motion">
                            <div class="inner-con-box gray-bg">
                                <p class="con-sub-tit f-title2">회원님의 소중한 개인정보 보호를 위하여 비밀번호를 다시 한 번 입력해주세요.</p>

                                <div class="data-enter-form">
                                    <div class="row">
                                        <div class="th">
                                            <p class="title f-head">비밀번호</p>
                                        </div>
                                        <div class="td">
                                            <div class="data-line-w">
                                                <div class="data-line">
                                                    <div class="form-group">
                                                        <div class="form-input password w-longer">
                                                            <input type="password" placeholder="비밀번호 입력" id="password" name="password" title="비밀번호">
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="page-bot-btn-sec">
                                    <div class="btn-wrap align-center">
                                        <div class="btn-set">
                                            <button class="btn-solid small black-bg" type="submit"><span>확인</span></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>