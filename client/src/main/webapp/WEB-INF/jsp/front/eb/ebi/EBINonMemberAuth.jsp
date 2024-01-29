<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>

<div class="cont-wrap" data-controller="controller/co/COFormCtrl controller/eb/ebi/EBINonMemberAuthCtrl">

    <div class="sub-top-vis-area">
        <div class="page-tit-area">
            <p class="page-tit f-xlarge-title"><span class="for-move">비회원 신청내역 조회</span></p>
        </div>
    </div>

    <div class="divide-con-area">

        <!--LNB 시작-->
        <jsp:include page="/WEB-INF/jsp/layout/lnb.jsp" />
        <!--LNB 끝-->

        <div class="right-con-area">
            <div class="cont-sec-w">
                <div class="cont-sec no-border scroll-motion">
                    <div class="for-motion">
                        <div class="inner-con-box gray-bg">
                            <p class="con-sub-tit f-title2">교육 신청 당시 입력한 신청자 정보를 입력해주세요.</p>

                            <form id="frmData" name="frmData" method="post" data-del-type="none">
                                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="hidden" id="email" name="email" value="" />

                            <div class="data-enter-form">
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">성명<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" name="name" id="name" title="성명" placeholder="성명 입력" maxlength="50" oninput="this.value = this.value.replace(/[^a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ]/g, '');">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">휴대폰번호<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group">
                                                    <div class="form-input">
                                                        <input type="text" name="hpNo" id="hpNo" title="휴대폰번호" placeholder="휴대폰번호 입력" maxlength="13">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="th">
                                        <p class="title f-head">이메일<span class="essential-mark color-sky">*</span></p>
                                    </div>
                                    <div class="td">
                                        <div class="data-line-w">
                                            <div class="data-line">
                                                <div class="form-group form-email">
                                                    <div class="form-input">
                                                        <input type="text" name="emailSrt" id="emailSrt" title="이메일" placeholder="이메일 입력" maxlength="50">
                                                    </div>
                                                    <div class="form-input">
                                                        <input type="text" name="emailEnd" id="emailEnd" title="이메일" placeholder="직접입력" maxlength="50">
                                                    </div>
                                                    <div class="form-select">
                                                        <select id="emailEndSelect" title="메일 선택">
                                                            <option value="" selected>직접입력</option>
                                                            <option value="naver.com">naver.com</option>
                                                            <option value="gmail.com">gmail.com</option>
                                                            <option value="nate.com">nate.com</option>
                                                            <option value="daum.net">daum.net</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </form>

                            <div class="page-bot-btn-sec">
                                <div class="btn-wrap align-center">
                                    <div class="btn-set">
                                        <a class="btn-solid small black-bg" href="javascript:" id="btnSearch"><span>조회</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>