<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@include file="/WEB-INF/jsp/include/el.jspf"%>
<div id="wrap" class="member"><!-- 로그인, 회원가입 페이지 member 클래스 추가 -->
    <div class="cont-wrap" data-controller="controller/co/COLgnCtrl">
    <div class="inner">
        <div class="sub-top-vis-area">
            <div class="page-tit-area t-align-center">
                <p class="page-tit f-large-title">
                    <c:choose>
                        <c:when test="${ tmpLgnVO.respCd eq '1210' }">
                            <span class="for-move">임시 비밀번호 변경 안내</span>
                        </c:when>
                        <c:when test="${ tmpLgnVO.respCd eq '1400'}">
                            <span class="for-move">비밀번호 변경 안내</span>
                        </c:when>
                    </c:choose>

                </p>
            </div>
        </div>

        <div class="inner-con-box">
            <form id="frmPwdChng" name="frmPwdChng" method="post" action="" autocomplete="off" data-login-id="${ tmpLgnMap.id }">
                <!-- CSRF KEY -->
                <input type="hidden" id="csrfKey" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input type="hidden" id="pwdChngType" name="pwdChngType"  value="old"/>
                <p class="con-sub-tit f-title2">안전한 개인정보 보호를 위해 비밀번호를 재설정해주세요.</p>
            <div class="data-enter-form">
                <div class="row">
                    <div class="th">
                        <p class="title f-head">현재 비밀번호</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="form-input password w-longer">
                                        <input type="password" placeholder="현재 비밀번호 입력" id="password" name="password" title="현재 비밀번호" oninput="this.value=this.value.replace(/[\s]/,'');" maxlength="16" autocomplete="off">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="th">
                        <p class="title f-head">새로운 비밀번호</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="for-status-chk for-status-chk1 "><!-- 조건 충족 시 error 클래스 삭제 -->
                                        <div class="form-input w-longer">
                                            <input type="password" placeholder="새로운 비밀번호 입력" id="newPassword" name="newPassword" title="새로운 비밀번호" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');" maxlength="16" autocomplete="off">
                                        </div>
                                        <p class="error-msg">8~16자 이내 영문+숫자+특수문자 조합으로 입력해주세요.</p>
                                    </div>
                                </div>
                                <div class="noti-txt-w">
                                    <p class="bullet-noti-txt f-caption2">* 영문/숫자/특수문자 중 3종류 이상을 조합한 8~16자</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="th">
                        <p class="title f-head">비밀번호 확인</p>
                    </div>
                    <div class="td">
                        <div class="data-line-w">
                            <div class="data-line">
                                <div class="form-group">
                                    <div class="for-status-chk for-status-chk2 "><!-- 조건 충족 시 error 클래스 삭제 -->
                                        <div class="form-input w-longer">
                                            <input type="password" placeholder="비밀번호 확인 입력" id="passwordConfirm" name="passwordConfirm" title="신규 비밀번호 확인" oninput="this.value=this.value.replace(/[\s<c:out value="&<>:;?\'\""/>]/,'');" maxlength="16" autocomplete="off">
                                        </div>
                                        <p class="error-msg">※ 비밀번호가 일치하지 않습니다.
                                        </p>
                                    </div>
                                </div>
                                <div class="noti-txt-w">
                                    <p class="bullet-noti-txt f-caption2">* 재확인을 위해 입력하신 비밀번호를 다시 한번 입력해 주세요.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="page-bot-btn-sec">
                <c:choose>
                    <c:when test="${ tmpLgnVO.respCd eq '1210'}">
                        <div class="btn-wrap align-center">
                            <div class="btn-set">
                                <button type="submit" class="btn-solid small black-bg"><span>변경하기</span></button>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${ tmpLgnVO.respCd eq '1400' }">

                        <div class="btn-wrap">
                            <div class="btn-set">
                                <a id="nextChange" class="btn-solid small gray-bg" href="javascript:"><span>다음에 변경하기</span></a>
                            </div>
                            <div class="btn-set">
                                <button type="submit" class="btn-solid small black-bg" ><span>변경하기</span></button>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
            </div>
            </form>
        </div>
        <c:choose>
            <c:when test="${ tmpLgnVO.respCd eq '1400' }">
                <div class="noti-txt-w">
                    <p class="bullet-noti-txt f-caption2">* 회원님의 개인정보를 안전하게 보호하기 위해 1년 주기로 비밀번호 변경을 권장드립니다.</p> <%-- 2024-07-18 수정 --%>
                </div>
            </c:when>
        </c:choose>

    </div>
</div>
</div>
<!-- content 영역 end -->
